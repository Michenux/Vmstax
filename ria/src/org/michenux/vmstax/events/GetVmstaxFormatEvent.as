package org.michenux.vmstax.events
{
	import flash.events.Event;

	public class GetVmstaxFormatEvent extends Event
	{
		public static const GET: String = "getVmstaxFormat";
						
		public function GetVmstaxFormatEvent()
		{
			super(GET);
		}
	}
}