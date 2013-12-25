package org.michenux.vmstax.utils
{
	import mx.core.IUIComponent;
	import mx.printing.FlexPrintJob;

	public class PrintUtils
	{
		public function PrintUtils()
		{
		}
		
		public static function print( comp: IUIComponent ): void {
			var myPrintJob:FlexPrintJob = new FlexPrintJob();
			if(myPrintJob.start() != true)
			{
				return;
			}
			myPrintJob.addObject(comp);
			myPrintJob.send();
		}
	}
}