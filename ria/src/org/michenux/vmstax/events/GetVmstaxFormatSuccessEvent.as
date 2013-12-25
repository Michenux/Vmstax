package org.michenux.vmstax.events
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;

	public class GetVmstaxFormatSuccessEvent extends Event
	{
		public static const GET_SUCCESS: String = "getVmstaxFormatSuccess";
		
		public var vmstaxFormats: ArrayCollection ;
		
		public function GetVmstaxFormatSuccessEvent( vmstaxFormats : ArrayCollection )
		{
			this.vmstaxFormats = vmstaxFormats ;
			super(GET_SUCCESS);
		}
	}
}