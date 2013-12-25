package org.michenux.vmstax;

import java.util.List;

import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.LineMetric;

public class VmstaxData {

	private String fileName ;
	private List<ChartMetaData> chartMetadatas;
	private List<? extends LineMetric> metrics;

	public List<ChartMetaData> getChartMetadatas() {
		return this.chartMetadatas;
	}

	public void setChartMetadatas(List<ChartMetaData> chartMetadatas) {
		this.chartMetadatas = chartMetadatas;
	}

	public List<? extends LineMetric> getMetrics() {
		return this.metrics;
	}

	public void setMetrics(List<? extends LineMetric> metrics) {
		this.metrics = metrics;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
