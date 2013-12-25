package org.michenux.vmstax.models
{
	import mx.binding.utils.BindingUtils;
	import mx.charts.AxisRenderer;
	import mx.charts.GridLines;
	import mx.charts.Legend;
	import mx.charts.LineChart;
	import mx.charts.LinearAxis;
	import mx.charts.chartClasses.Series;
	import mx.charts.series.AreaSeries;
	import mx.charts.series.LineSeries;
	import mx.graphics.SolidColor;
	import mx.graphics.SolidColorStroke;
	import mx.graphics.Stroke;
	
	import spark.components.Panel;
	import spark.layouts.VerticalLayout;

	public class ChartBuilder
	{
		public function ChartBuilder()
		{
		}

		public function buildChart( p_oChartMetaData: ChartMetaData, p_oVmstaxData: VmstaxData ): Panel {
			
			// Axe horizontal
			var oHAxis: LinearAxis = new LinearAxis();
			if ( p_oChartMetaData.horizAxisTitle != null )
				oHAxis.title = p_oChartMetaData.horizAxisTitle;
			oHAxis.minimum = 0 ;
			BindingUtils.bindProperty(oHAxis,"maximum", p_oVmstaxData, "timeAxisMaximum" );
			BindingUtils.bindProperty(p_oChartMetaData,"horizAxisMaximum", p_oVmstaxData, "timeAxisMaximum" );
			oHAxis.labelFunction = p_oVmstaxData.formatDuree ;
			oHAxis.alignLabelsToInterval = true ;
						
			// Axe vertical
			var oVAxis: LinearAxis = new LinearAxis();
			if ( p_oChartMetaData.vertAxisTitle != null )
				oVAxis.title= p_oChartMetaData.vertAxisTitle;
			if ( p_oChartMetaData.vertAxisMinimum != -1 )
				oVAxis.minimum = p_oChartMetaData.vertAxisMinimum ;
			if ( p_oChartMetaData.vertAxisMaximum != -1 )
				oVAxis.maximum = p_oChartMetaData.vertAxisMaximum ;
			if ( p_oChartMetaData.vertAxisInterval != -1 )
				oVAxis.interval = p_oChartMetaData.vertAxisInterval ;
			if ( p_oChartMetaData.vertAxisMinorInterval != -1 )
				oVAxis.minorInterval = p_oChartMetaData.vertAxisMinorInterval ;
			if ( p_oChartMetaData.vertUnit != null ) {
				switch( p_oChartMetaData.vertUnit ) {
					case "o" : oVAxis.labelFunction = p_oChartMetaData.verticalNumberLabel; break ;
					case "b" : oVAxis.labelFunction = p_oChartMetaData.verticalByteSizeLabel; break ;
					case "kb" : oVAxis.labelFunction = p_oChartMetaData.verticalKByteSizeLabel; break ;
				}
			}
			var oVAxisRenderer:AxisRenderer = new AxisRenderer();
			oVAxisRenderer.setStyle("verticalAxisTitleAlignment","vertical");
			oVAxisRenderer.axis = oVAxis;
			
			// Grid Line
			var oGridLines : GridLines = new GridLines();
			oGridLines.setStyle("horizontalChangeCount",1);
			oGridLines.setStyle("verticalChangeCount",1);
			oGridLines.setStyle("direction","both");
			var oGridStroke: SolidColorStroke = new SolidColorStroke();
			oGridStroke.weight = 1 ;
			oGridStroke.alpha = .1 ;
			oGridLines.setStyle("horizontalStroke", oGridStroke);
			oGridLines.setStyle("verticalStroke", oGridStroke );
			
			// Series
			var series:Array = new Array();
			for each(var oSerieMetaData:SerieMetaData in p_oChartMetaData.series ) {
				var oSerie: Series ;
				if ( oSerieMetaData.type == "line" ) {
					var oLineSerie:LineSeries = new LineSeries();
					oLineSerie.xField = oSerieMetaData.fieldx;
					oLineSerie.yField = oSerieMetaData.fieldy;
					oLineSerie.horizontalAxis = oHAxis;
					oLineSerie.verticalAxis = oVAxis ;
					if ( oSerieMetaData.form != null )
						oLineSerie.setStyle("form", oSerieMetaData.form );
					if ( oSerieMetaData.strokeColor != -1 && oSerieMetaData.strokeWeight != -1 )
						oLineSerie.setStyle("lineStroke", 
							new Stroke(oSerieMetaData.strokeColor, oSerieMetaData.strokeWeight));
					oSerie = oLineSerie ;	
				}
				else {
					var oAreaSerie:AreaSeries = new AreaSeries();
					oAreaSerie.xField = oSerieMetaData.fieldx;
					oAreaSerie.yField = oSerieMetaData.fieldy;
					oAreaSerie.verticalAxis = oVAxis ;
					oAreaSerie.horizontalAxis = oHAxis;
					if ( oSerieMetaData.minField != null )
						oAreaSerie.minField = oSerieMetaData.minField ;
					if ( oSerieMetaData.strokeColor != -1 && oSerieMetaData.strokeWeight != -1 )
						oAreaSerie.setStyle("areaStroke", 
							new Stroke(oSerieMetaData.strokeColor, oSerieMetaData.strokeWeight));
					if ( oSerieMetaData.solidColorColor != -1 && oSerieMetaData.solidColorAlpha != -1 )
						oAreaSerie.setStyle("areaFill", 
							new SolidColor(oSerieMetaData.solidColorColor, oSerieMetaData.solidColorAlpha));
//					if ( oSerieMetaData.form != null )
//						oAreaSerie.setStyle("form", oSerieMetaData.form );
					oSerie = oAreaSerie ;
				}
				if ( oSerieMetaData.displayName != null )
					oSerie.displayName = oSerieMetaData.displayName ;
				series.push(oSerie);
			} 
			
			// Line Chart
			var oLineChart: LineChart = new LineChart();
			oLineChart.percentWidth = 100 ;
			oLineChart.percentHeight = 100 ;
			oLineChart.showDataTips = true;
			oLineChart.verticalAxis = oVAxis ;
			oLineChart.horizontalAxis = oHAxis ;
			oLineChart.backgroundElements = [oGridLines] ;
			oLineChart.series = series ;
			oLineChart.dataTipMode = p_oChartMetaData.dataTipMode ;
			oLineChart.dataTipFunction = p_oChartMetaData.chartDataTips ;
			oLineChart.verticalAxisRenderers.push(oVAxisRenderer);
			
			BindingUtils.bindProperty(oLineChart,"dataProvider", p_oVmstaxData, "metrics" );
			
			// Légende
			var oLegendLine:Legend = new Legend();
			oLegendLine.dataProvider = oLineChart ;
			oLegendLine.direction = "horizontal";
		
			// Panel Chart
			var r_oPanel: Panel = new Panel();
			r_oPanel.layout = new VerticalLayout();
			r_oPanel.title = p_oChartMetaData.panelTitle ;
			r_oPanel.percentWidth = 100 ;

			r_oPanel.addElement(oLineChart);
			r_oPanel.addElement(oLegendLine);
			
			p_oChartMetaData.panel = r_oPanel ;
			
			return r_oPanel ;
		}
	}
}