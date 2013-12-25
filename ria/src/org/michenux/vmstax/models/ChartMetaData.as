package org.michenux.vmstax.models
{
	import mx.charts.HitData;
	import mx.charts.chartClasses.IAxis;
	import mx.collections.ArrayCollection;
	
	import org.michenux.vmstax.utils.DateUtils;
	import org.michenux.vmstax.utils.SizeUtils;
	
	import r1.deval.D;
	
	import spark.components.Panel;
	
	[RemoteClass(alias="org.michenux.vmstax.charts.ChartMetaData")]
	[Bindable]
	public class ChartMetaData
	{
		public var id: String ;
		public var panelTitle: String ;
		
		public var horizAxisTitle: String ;
		public var horizAxisMaximum: Number ;
		
		public var vertAxisTitle: String ;
		public var vertAxisMinimum: Number = -1 ;
		public var vertAxisMaximum: Number = -1 ;
		public var vertAxisInterval: Number = -1 ;
		public var vertAxisMinorInterval: Number = -1 ;
		public var vertUnit : String ;
		
		public var dataTipMode: String = "multiple" ;
		public var dataTipTemplate: String = "";
		
		public var series: ArrayCollection ;
		
		[Transient]
		public var panel: Panel ;
				
		public function ChartMetaData() {
		}
		
		/**
		 * Personnalisation du texte dans les info bulles du graph cpu 
 		*/
		public function chartDataTips(hd:HitData):String {
			var withSeconds: Boolean = horizAxisMaximum < 3600 ;
        	var withHours: Boolean = horizAxisMaximum >= 3600 ;
        	var withDays: Boolean = horizAxisMaximum >= (3600 * 24);
        	hd.item.strTime = DateUtils.dureeToString(
        		hd.item.numLine, withSeconds, withHours, withDays); ;

 			return D.evalToString(dataTipTemplate, {item: hd.item} );
		}
		
		public function verticalNumberLabel(item:Object, prevValue:Object, axis:IAxis):String {
 		     return SizeUtils.numberToString( Number(item));
		}
		
		public function verticalByteSizeLabel(item:Object, prevValue:Object, axis:IAxis):String {
 		     return SizeUtils.byteSizeToString( Number(item));
		}
		
		public function verticalKByteSizeLabel(item:Object, prevValue:Object, axis:IAxis):String {
 		     return SizeUtils.kbSizeToString( Number(item));
		}
	}
}