package org.michenux.vmstax.pm
{
	import flash.events.EventDispatcher;

	public class MainPM extends EventDispatcher
	{
		[Inject( source="applicationModel.activeView", twoWay="true" )]
		[Bindable]
		public var activeView:int;
		
		public function MainPM()
		{
		}
		
		public function setActiveView( index:int ):void
		{
			activeView = index;
		}
	}
}