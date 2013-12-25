package org.michenux.vmstax.events
{
	import flash.events.Event;
	import flash.utils.ByteArray;

	public class ParseEvent extends Event
	{
		public static const PARSE: String = "vmstaxParse";
		
		public var data: ByteArray ;
		public var format: String ;
		public var fileName: String ;
		public var zipped: Boolean ;
		
		public function ParseEvent( pData: ByteArray, pFormat: String, pFileName: String, pZipped: Boolean )
		{
			this.data = pData ;
			this.format = pFormat;
			this.fileName = pFileName ;
			this.zipped = pZipped ;
			super(PARSE);
		}
	}
}