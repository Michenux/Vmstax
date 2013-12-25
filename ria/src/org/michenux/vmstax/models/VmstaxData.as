package org.michenux.vmstax.models
{
	import mx.charts.LinearAxis;
	import mx.collections.ArrayCollection;
	
	import org.michenux.vmstax.utils.DateUtils;

	[RemoteClass(alias="org.michenux.vmstax.VmstaxData")]
	public class VmstaxData
	{
		[Bindable]
		public var fileName: String ;
		
		[Bindable]
		public var metrics: ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var chartMetadatas : ArrayCollection = new ArrayCollection();
		
		[Bindable]
		public var timeAxisMaximum : Number = 120 ;
		
		public function VmstaxData()
		{
		}
			
		public function formatDuree(value:Number, prevValue:Number, axis:LinearAxis):String {
			var withSeconds: Boolean = axis.maximum < 3600 ;
			var withHours: Boolean = axis.maximum >= 3600 ;
       		var withDays: Boolean = axis.maximum >= (3600 * 24);
			return DateUtils.dureeToString(value,withSeconds,withHours,withDays );         	
		}
	}
}