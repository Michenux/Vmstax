package org.michenux.vmstax.events
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	
	import org.michenux.vmstax.models.VmstaxResult;

	public class ParseSuccessEvent extends Event
	{
		public static const PARSE_SUCCESS: String = "vmstaxParseSuccess";
		
		public var vmstaxResult : VmstaxResult ;
		
		public function ParseSuccessEvent( vmstaxResult : VmstaxResult )
		{
			this.vmstaxResult = vmstaxResult ;
			super(PARSE_SUCCESS);
		}
	}
}