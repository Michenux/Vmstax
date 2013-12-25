package org.michenux.vmstax.controllers
{
	import flash.utils.ByteArray;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.michenux.vmstax.events.GetVmstaxFormatFaultEvent;
	import org.michenux.vmstax.events.GetVmstaxFormatSuccessEvent;
	import org.michenux.vmstax.events.ParseFaultEvent;
	import org.michenux.vmstax.events.ParseSuccessEvent;
	import org.michenux.vmstax.models.VmstaxResult;
	import org.swizframework.controller.AbstractController;
	import org.swizframework.utils.logging.SwizLogger;

	public class ApplicationController extends AbstractController
	{
		private static var logger:SwizLogger ;
		
		[Inject(source="vmstaxService")]
		public var vmstaxService:RemoteObject;
		
		[PostConstruct]
		public function init(): void {
			logger = SwizLogger.getLogger(this);
			logger.info("ApplicationController.init");
		}
		
		public function ApplicationController()
		{
		}
		
		[EventHandler(event="ParseEvent.PARSE",properties="data,format,fileName,zipped" )]
		public function parse( data: ByteArray, format: String, fileName: String, zipped: Boolean ): void {
			logger.info("> ApplicationController.parse");
			logger.info("fileName = " + fileName );
			logger.info("format = " + format );
			logger.info("zipped = " + zipped );
			logger.info("vmstax service != null " + (vmstaxService!=null));
			this.executeServiceCall(
				vmstaxService.getVmstaxData(data,format,fileName,zipped), parseSuccess, parseFault);
			logger.info("< ApplicationController.parse");
		}
		
		private function parseSuccess( event: ResultEvent ):void 
		{
			logger.info("> ApplicationController.parseSuccess");
			var vmstaxResult: VmstaxResult = event.result as VmstaxResult;
			this.dispatcher.dispatchEvent( new ParseSuccessEvent(vmstaxResult));
			logger.info("< ApplicationController.parseSuccess");
		}
		
		private function parseFault( event: FaultEvent):void 
		{
			logger.info("> ApplicationController.parseFault");
			this.dispatcher.dispatchEvent( new ParseFaultEvent( event.fault.faultString + " "+ event.fault.faultCode.toString()));
			logger.info("< ApplicationController.parseFault");
		}
		
		
		
		[EventHandler(event="GetVmstaxFormatEvent.GET")]
		public function getVmstaxFormats(): void {
			logger.info("> ApplicationController.getVmstaxFormats");
			this.executeServiceCall(
				vmstaxService.getVmstaxFormats(), getVmstaxFormatSuccess, getVmstaxFormatFault);
			logger.info("< ApplicationController.getVmstaxFormats");
		}
		
		private function getVmstaxFormatSuccess( event: ResultEvent ):void 
		{
			logger.info("> ApplicationController.getVmstaxFormatSuccess");
			this.dispatcher.dispatchEvent( new GetVmstaxFormatSuccessEvent(event.result as ArrayCollection));
			logger.info("< ApplicationController.getVmstaxFormatSuccess");
		}
		
		private function getVmstaxFormatFault( event: FaultEvent):void 
		{
			logger.info("> ApplicationController.getVmstaxFormatFault");
			this.dispatcher.dispatchEvent( new GetVmstaxFormatFaultEvent( event.fault.faultString + " "+ event.fault.faultCode.toString()));
			logger.info("< ApplicationController.getVmstaxFormatFault");
		}
	}
}