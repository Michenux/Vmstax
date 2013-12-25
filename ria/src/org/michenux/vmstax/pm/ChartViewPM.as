package org.michenux.vmstax.pm
{
	import com.google.analytics.components.FlexTracker;
	
	import flash.events.Event;
	import flash.events.IEventDispatcher;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	import flash.utils.ByteArray;
	
	import mx.collections.ArrayCollection;
	import mx.containers.ViewStack;
	import mx.controls.Alert;
	import mx.controls.Menu;
	import mx.controls.PopUpMenuButton;
	import mx.core.IUIComponent;
	import mx.events.IndexChangedEvent;
	import mx.events.MenuEvent;
	
	import org.michenux.vmstax.events.GetVmstaxFormatEvent;
	import org.michenux.vmstax.events.ParseEvent;
	import org.michenux.vmstax.models.VmstaxData;
	import org.michenux.vmstax.models.VmstaxFormat;
	import org.michenux.vmstax.models.VmstaxResult;
	import org.michenux.vmstax.utils.PrintUtils;
	import org.michenux.vmstax.views.ChartTab;
	import org.swizframework.utils.logging.SwizLogger;
	
	import plus.TabBarPlus;


	public class ChartViewPM
	{
		
		/**
		* Logger
		*/
		private static var logger:SwizLogger ;
		
		[Inject (source="tracker")]
		public var tracker:FlexTracker ;
		
		/** dispatcher d'evennements de niveau Application */		
		[Dispatcher]
		public var dispatcher:IEventDispatcher;
		
		[Bindable] public var periodicities:ArrayCollection = new ArrayCollection([
			{label:"1s", data: 1},
			{label:"2s", data: 2},
			{label:"3s", data: 3},
			{label:"4s", data: 4},
			{label:"5s", data: 5},
			{label:"6s", data: 6},
			{label:"7s", data: 7},
			{label:"8s", data: 8},
			{label:"9s", data: 9},
			{label:"10s", data: 10},
			{label:"15s", data: 15},
			{label:"20s", data: 20},
			{label:"25s", data: 25},
			{label:"30s", data: 30},
			{label:"35s", data: 35},
			{label:"40s", data: 40},
			{label:"45s", data: 45},
			{label:"50s", data: 50},
			{label:"55s", data: 55},
			{label:"1mn", data: 60},
			{label:"2mn", data: 120},
			{label:"3mn", data: 180},
			{label:"4mn", data: 240},
			{label:"5mn", data: 300},
			{label:"10mn", data: 600},
			{label:"15mn", data: 900},
			{label:"20mn", data: 1200},
			{label:"25mn", data: 1500},
			{label:"30mn", data: 1800}
		] );
		
		[Bindable] 
		public var saveAsImageMenuData:Array = [
			{label: "All charts as single image", data: "allasimage"},
			{label: "All charts as individual images (zip)", data: "allaszip"}
		];
		
		[Bindable] 
		public var comboFilenameOptions:ArrayCollection = new ArrayCollection([
			{label: "At top", data: "top"},
			{label: "Chart panels", data: "chartpanel"},
			{label: "Both", data: "both"},
			{label: "Hide", data: "hide"}
		]);
		
		[Bindable] 
		public var uploadMenuData:ArrayCollection = new ArrayCollection();
			
		[Bindable]
		public var isGraphs: Boolean = false ;
			
		public var tabBar : TabBarPlus ;
		public var tabStacks: ViewStack;
		
		public var filenameOptionsMenuButton:PopUpMenuButton ;
		public var comboPeriodicity: PopUpMenuButton;

		private var refUploadFile:FileReference;
		
		private var selectedFormat: Object ;
			
		private var zipFilter:FileFilter = new FileFilter("Zip", "*.zip;*.ZIP");
		
		/**
		 * Constructeur 
		 */
		public function ChartViewPM() {
			
		}
				
		/**
		 * Initialisation 
		 */
		public function init(): void {
			logger = SwizLogger.getLogger(this);
			logger.info("ChartViewPM.init");
			this.dispatcher.dispatchEvent( new GetVmstaxFormatEvent());
		}
		
		public function changePeriodicity(event:MenuEvent):void {
			if ( this.isGraphs ) {
				event.currentTarget.label= event.label ;
				var chartTab:ChartTab = this.tabStacks.selectedChild as ChartTab;
				chartTab.changePeriodicity(event);
			}
		}
		
		/************* CHARGEMENT DES FORMATS **************************************************/
		
		[EventHandler(event="GetVmstaxFormatSuccessEvent.GET_SUCCESS", properties="vmstaxFormats" )]
		public function updateVmstaxFormats( vmstaxFormats: ArrayCollection):void {
			logger.info("> ChartViewPM.updateVmstaxFormats");
			
			for each (var oVmstaxFormat:VmstaxFormat in vmstaxFormats ) {
				this.uploadMenuData.addItem(
					{	label:oVmstaxFormat.displayName, 
					 	data:oVmstaxFormat.name, 
					 	icon:oVmstaxFormat.icon,
					 	zipped: false
					});
				this.uploadMenuData.addItem(
					{	label:oVmstaxFormat.displayName + " - Zip", 
						data:oVmstaxFormat.name, 
						icon:oVmstaxFormat.icon,
						zipped: true
					});
			}
			this.dispatcher.dispatchEvent( new Event("applicationLoaded"));
			
			logger.info("< ChartViewPM.updateVmstaxFormats");
		}
		
		[EventHandler(event="GetVmstaxFormatFaultEvent.GET_FAILED", properties="message" )]
		public function updateVmstaxFormatFailed( message: String):void {
			Alert.show(message);
		}

		
		/************* GESTION DE L'UPLOAD **************************************************/
		
		/**
		 * Evenement clique sur le bouton d'upload 
		 */
		public function addVmstatTraceFile(event:MenuEvent):void {
			logger.info("> ChartViewPM.addVmstatTraceFile");
			event.currentTarget.label= "Upload Log (" + event.label + ")";
			this.selectedFormat = event.item ;
			this.refUploadFile = new FileReference();	

			var opt: String = "" ;
			if ( selectedFormat.zipped as Boolean ) {
				refUploadFile.browse( [ zipFilter ]);
				opt = "/zip";
			} else {
				refUploadFile.browse();
			}
			refUploadFile.addEventListener(Event.SELECT,onFileSelect);
			refUploadFile.addEventListener(Event.COMPLETE,onFileComplete);
			tracker.trackPageview("/vmstax/uploadfile/" + this.selectedFormat.data + opt );
			
			logger.info("< ChartViewPM.addVmstatTraceFile");
		}
		
		/**
		 * Evenement sur la selection du fichier
		 */
		private function onFileSelect(event:Event):void {
			logger.info("> ChartViewPM.onFileSelect");
			refUploadFile.load();
			logger.info("name = " + refUploadFile.name);
			logger.info("< ChartViewPM.onFileSelect");
		}
		
		/**
		 * Le fichier est chargé, appelle du service distant
		 */
		private function onFileComplete(event:Event):void {
			logger.info("> ChartViewPM.onFileComplete");
			refUploadFile = event.currentTarget as FileReference;

			var data:ByteArray = new ByteArray();
			refUploadFile.data.readBytes(data,0,refUploadFile.data.length);
			
			this.dispatcher.dispatchEvent( new ParseEvent( data, this.selectedFormat.data as String, 
				refUploadFile.name, this.selectedFormat.zipped as Boolean ));			
			logger.info("< ChartViewPM.onFileComplete");
		}
		
		/**
		 * Evenement de traitement du résultat
		 */
		[EventHandler(event="ParseSuccessEvent.PARSE_SUCCESS", properties="vmstaxResult" )]
		public function drawCharts( vmstaxResult: VmstaxResult ):void {
			logger.info("> ChartViewPM.drawCharts");
			
			if ( vmstaxResult.message != null && vmstaxResult.message.length > 0 ) {
				Alert.show(vmstaxResult.message);
			}
			
			for each (var oVmstaxData:VmstaxData in vmstaxResult.vmstaxDatas ) {
				var chartTab: ChartTab = new ChartTab();
				chartTab.drawCharts(oVmstaxData);
				this.tabStacks.addElement(chartTab);
				this.tabStacks.selectedIndex = this.tabStacks.numChildren -1 ;
			}
					
			logger.info("< ChartViewPM.drawCharts");
		}
		
		[EventHandler(event="ParseFaultEvent.PARSE_FAILED", properties="message" )]
		public function vmstaxParseFailed( message: String):void {
			Alert.show(message);
		}

		/************* Option pour le nom du fichier ******************************************/
		
		/**
		 * Evenement clique sur le bouton d'upload 
		 */
		public function changeFilenameOptions(event:MenuEvent):void {
			logger.info("> ChartViewPM.changeFilenameOptions");
			
			if ( this.isGraphs ) {
				
				var currentTab: ChartTab = this.tabStacks.selectedChild as ChartTab;
				event.currentTarget.label= "Filename (" + event.label + ")";
				currentTab.filenameOption = event.item.data ;
				currentTab.filenameOptionIndex = event.index ;
				redrawFilename();
			}
			
			logger.info("< ChartViewPM.changeFilenameOptions");
		}
		
		public function redrawFilename(): void {
			logger.info("> ChartViewPM.redrawFilename");
			
			var currentTab: ChartTab = this.tabStacks.selectedChild as ChartTab;
			currentTab.redrawFilename();
			
			logger.info("< ChartViewPM.redrawFilename");
		}
		
		/************* SAUVEGARDE DES IMAGES **************************************************/
		
		/**
		 * Sauvegarde du graphique en tant qu'image
		 */
		public function saveChartAsImage( event:MenuEvent ):void {
			
			if ( this.isGraphs ) {
				var currentTab:ChartTab = this.tabStacks.selectedChild as ChartTab;
				currentTab.saveChartAsImage(event.item.data);
				tracker.trackPageview("/vmstax/saveasimage/" + event.item.data );			
			}
		}
		
		
		/************* IMPRESSION DES IMAGES **************************************************/
		
		/**
		 * Evenement click sur le bouton d'impression
		 */
		public function printChart( comp: IUIComponent ):void
		{
			if ( this.isGraphs ) {
				PrintUtils.print(comp);
				tracker.trackPageview("/vmstax/printcharts");
			}
		}
		
		/************* IMPRESSION DES IMAGES **************************************************/
		public function changeTab(event:IndexChangedEvent):void { 
			this.isGraphs = event.newIndex != 0 ;
			
			if ( this.isGraphs ) {
				var currentTab:ChartTab = this.tabStacks.selectedChild as ChartTab;
				saveAsImageMenuData = currentTab.newSaveAsImageMenuData ;
				
				Menu(comboPeriodicity.popUp).selectedIndex = currentTab.periodicityIndex ;
				comboPeriodicity.label = this.periodicities.getItemAt(currentTab.periodicityIndex).label;
				
				Menu(filenameOptionsMenuButton.popUp).selectedIndex = currentTab.filenameOptionIndex ;
				filenameOptionsMenuButton.label= "Filename (" 
					+ this.comboFilenameOptions.getItemAt(currentTab.filenameOptionIndex).label + ")";
			}
		} 

	}
}