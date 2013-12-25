package org.michenux.vmstax.events
{
	import flash.events.Event;

	public class ParseFaultEvent extends Event
	{
		public static const PARSE_FAILED: String = "vmstaxParseFailed";
		
		public var message : String ;
		
		public function ParseFaultEvent( message : String )
		{
			this.message = message ;
			super(PARSE_FAILED);
		}
	}
}