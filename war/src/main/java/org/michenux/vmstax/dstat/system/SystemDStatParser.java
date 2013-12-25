package org.michenux.vmstax.dstat.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.SerieMetaData;
import org.michenux.vmstax.dstat.DStatColParser;
import org.michenux.vmstax.dstat.DStatLineMetric;

public class SystemDStatParser implements DStatColParser {

	private int firstInputColIndex;

	private int intOutputColIndex;
	private int swOutputColIndex;
	
	private String colHeader;
	
	private int nbInputCols = 2;
	private int nbOutputCols = 2;
	
	/**
	 * @see DStatColParser#getNbCols()
	 */
	@Override
	public int getInputNbCols() {
		return this.nbInputCols;
	}
	
	/**
	 * @see DStatColParser#getNbCols()
	 */
	@Override
	public int getOutputNbCols() {
		return this.nbOutputCols;
	}
	
	/**
	 * @see DStatColParser#parse(java.lang.String[])
	 */
	@Override
	public void parse(String[] p_oNextLine, DStatLineMetric p_oDStatLineMetric)
			throws Exception {
		
		int iCurrentInputCol = this.firstInputColIndex;
		
		long interrupt = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.intOutputColIndex,
				interrupt);
		
		long sw = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.swOutputColIndex,
				sw);
	}
	
	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader;
	}
	
	/**
	 * Affecte l'objet firstColIndex 
	 * @param p_oFirstColIndex Objet firstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex, int p_iOutputFirstColIndex ) {
		
		this.firstInputColIndex = p_iInputFirstColIndex ;
		this.intOutputColIndex = p_iOutputFirstColIndex;
		this.swOutputColIndex = p_iOutputFirstColIndex + 1 ;
	}
	
	@Override
	public boolean accept(String p_sColName) {
		return "system".equals(p_sColName);
	}
	
	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();
		ChartMetaData oDiskChartMetaData = new ChartMetaData();
	    oDiskChartMetaData.setId("system");
	    oDiskChartMetaData.setPanelTitle(this.colHeader);
	    oDiskChartMetaData.setHorizAxisTitle("");
	    oDiskChartMetaData.setVertAxisTitle("");
	    oDiskChartMetaData.setDataTipMode("multiple");

	    oDiskChartMetaData.setVertAxisMinimum(0);

	    StringBuilder sToolTip = new StringBuilder();
	    sToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Interruptions : </b>\" + item[\"field");
	    sToolTip.append(this.intOutputColIndex);
	    sToolTip.append("\"] + \" \\n\"+ \"<b>Context switch : </b>\" + item[\"field");
	    sToolTip.append(this.swOutputColIndex);
	    sToolTip.append("\"] + \" \"");
	    oDiskChartMetaData.setDataTipTemplate(sToolTip.toString());

	    SerieMetaData oIntSerie = new SerieMetaData();
	    oIntSerie.setType("line");
	    oIntSerie.setDisplayName("Interruptions");
	    oIntSerie.setFieldx("numLine");
	    oIntSerie.setFieldy("field" + this.intOutputColIndex);
	    oIntSerie.setForm("curve");

	    oIntSerie.setStrokeColor(10861646);
	    oIntSerie.setStrokeWeight(2);
	    oDiskChartMetaData.getSeries().add(oIntSerie);

	    SerieMetaData oWriteSerie = new SerieMetaData();
	    oWriteSerie.setType("line");
	    oWriteSerie.setDisplayName("Context switch");
	    oWriteSerie.setFieldx("numLine");
	    oWriteSerie.setFieldy("field" + this.swOutputColIndex);
	    oWriteSerie.setForm("curve");

	    oWriteSerie.setStrokeColor(14976769);
	    oWriteSerie.setStrokeWeight(2);
	    oDiskChartMetaData.getSeries().add(oWriteSerie);
		
	    listChartMeta.add(oDiskChartMetaData);
		return listChartMeta;
	}
}
