package plus
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.events.ListEvent;
	
	import spark.components.Button;
	import spark.components.ButtonBarButton;
	
	public class TabPlus extends ButtonBarButton
	{
		public static const CLOSE_TAB_EVENT:String = "closeTab";
		
		private var _closePolicy:String;
		private var _closeIncluded:Boolean;
		
		public function TabPlus()
		{
		}
		
		public function get canClose():Boolean   { return _closeIncluded;     }
		public function set canClose(value:Boolean):void      
		{ 
			_closeIncluded = value; 
			skin.invalidateDisplayList(); 
		}
		
		//--------------------------------------------------------------------------
		//
		//  Skin parts
		//
		//--------------------------------------------------------------------------
		
		[SkinPart(required="false")]
		//
		// A skin part that defines the close button
		public var closeBtn:Button;
		
		
		private function closeBtn_clickHandler(event:MouseEvent):void
		{
			//trace("close clicked");
			dispatchEvent(new ListEvent(CLOSE_TAB_EVENT, true, false, -1, itemIndex));
		}
		
		override protected function partAdded(partName:String, instance:Object):void
		{
			super.partAdded(partName, instance);
			
			if (instance == closeBtn)
			{
				closeBtn.addEventListener(MouseEvent.CLICK, closeBtn_clickHandler);
			}
		}
		
		/**
		 *  @private
		 */
		override protected function partRemoved(partName:String, instance:Object):void
		{
			super.partRemoved(partName, instance);
			
			if (instance == closeBtn)
			{
				closeBtn.removeEventListener(MouseEvent.CLICK, closeBtn_clickHandler);
			}
		}
		
	}
}