package org.michenux.vmstax.models
{
	public class ApplicationModel
	{
		public static const CHART_VIEW:int = 0;
		
		[Bindable]
		public var activeView:int = CHART_VIEW;
		
		public function ApplicationModel()
		{
			
		}
	}
}