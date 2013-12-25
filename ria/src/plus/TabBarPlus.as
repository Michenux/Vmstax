package plus
{
	import mx.collections.IList;
	import mx.events.ListEvent;
	
	import plus.tabskins.TabBarPlusSkin;
	
	import spark.components.TabBar;
	import spark.events.RendererExistenceEvent;
	
	// Quick access to the tab skin
	[Style(name="tabSkin", type="Class", inherit="no")]
	
	//[Style(name="tabCloseBtnSkin", type="Class", inherit="no")]
	
	// Define the gap style since it was dropped in the spark tab
	[Style(name="gap", type="Number", format="Length", inherit="no")]
	
	public class TabBarPlus extends TabBar
	{
		static public const CLOSE_ALWAYS:String = "always";
		static public const CLOSE_NEVER:String = "never";
		
		
		public function TabBarPlus()
		{
			super();
			
			setStyle("skinClass", Class(TabBarPlusSkin));
		}
		
		private var _closePolicy:String = CLOSE_ALWAYS;
		
		[Inspectable(type="String", format="String", enumeration="never,always", defaultValue="always")]
		public function get closePolicy():String { return _closePolicy; }
		public function set closePolicy(val:String):void {  _closePolicy= val; }
		
		protected  function tabAdded(e:RendererExistenceEvent):void
		{
			var tab:TabPlus=  dataGroup.getElementAt(e.index) as TabPlus;    
			var tabSkinCls:Class = getStyle("tabSkin");
			if (tabSkinCls) {
				trace("setting custom skin for tab");
				tab.setStyle("skinClass", tabSkinCls);
			}
			
			// var tabCloseCls:Class = getStyle("tabCloseBtnSkin");
			
			
			tab.canClose= (_closePolicy == CLOSE_ALWAYS);
		}
		
		
		public function setTabClosePolicy(index:int, can:Boolean):void
		{
			var tab:TabPlus=  dataGroup.getElementAt(index) as TabPlus;
			tab.canClose= can;
		}
		
		protected override function partAdded(partName:String, instance:Object):void
		{
			super.partAdded(partName, instance);
			
			if (instance == dataGroup) {
				dataGroup.addEventListener(TabPlus.CLOSE_TAB_EVENT, onCloseTabClicked);
				dataGroup.addEventListener(RendererExistenceEvent.RENDERER_ADD, tabAdded);
			}
		}
		
		protected override function partRemoved(partName:String, instance:Object):void
		{
			super.partRemoved(partName, instance);
			
			if (instance == dataGroup) {
				dataGroup.removeEventListener(TabPlus.CLOSE_TAB_EVENT, onCloseTabClicked);
				dataGroup.removeEventListener(RendererExistenceEvent.RENDERER_ADD, tabAdded);
			}
		}
		
		
		public function onCloseTabClicked(event:ListEvent):void
		{
			var index:int = event.rowIndex;
			//trace("close clicked, index: " + index);
			
			// Perform default action
			// remove the child
			if(dataProvider is IList){
				dataProvider.removeItemAt(index);
			}
			else {
				trace("Bad data provider");
			}
		}
	}
}