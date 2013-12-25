package org.michenux.vmstax.models
{
	[RemoteClass(alias="org.michenux.vmstax.charts.SerieMetaData")]
	public class SerieMetaData
	{
		public var type: String ; //LineSeries
		public var displayName: String ;
		public var fieldx: String ;
		public var fieldy: String ;
		public var minField: String ;
		public var form: String ;
		
		public var strokeColor : Number = -1 ;
		public var strokeWeight : Number = -1 ;
		public var solidColorColor : Number = -1 ;
		public var solidColorAlpha : Number = -1 ;
		
		public function SerieMetaData()
		{
		}

	}
}