package org.michenux.vmstax.events
{
	import flash.events.Event;

	public class GetVmstaxFormatFaultEvent extends Event
	{
		public static const GET_FAILED: String = "getVmstaxFormatFailed";
		
		public var message: String ;
		
		public function GetVmstaxFormatFaultEvent( message : String )
		{
			this.message = message ;
			super(GET_FAILED);
		}
	}
}