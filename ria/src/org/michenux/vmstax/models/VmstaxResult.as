package org.michenux.vmstax.models
{
	import mx.charts.LinearAxis;
	import mx.collections.ArrayCollection;
	
	import org.michenux.vmstax.utils.DateUtils;
	
	[RemoteClass(alias="org.michenux.vmstax.VmstaxResult")]
	public class VmstaxResult
	{
		[Bindable]
		public var message: String ;
		
		[Bindable]
		public var vmstaxDatas: ArrayCollection = new ArrayCollection();
		
		public function VmstaxResult()
		{
		}
	}
}
