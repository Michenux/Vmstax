package org.michenux.vmstax.dstat.swap;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.SerieMetaData;
import org.michenux.vmstax.dstat.DStatColParser;
import org.michenux.vmstax.dstat.DStatLineMetric;

/**
 * @author Michenux
 * 
 */
public class SwapDStatParser implements DStatColParser {

	private int firstInputColIndex ;
	
	private int usedOutputColIndex;

	private int nbInputCols = 2;
	private int nbOutputCols = 1;

	private String colHeader ;
	
	/**
	 * Affecte l'objet firstColIndex
	 * 
	 * @param p_oFirstColIndex
	 *            Objet firstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex, int p_iOutputFirstColIndex ) {
		this.firstInputColIndex = p_iInputFirstColIndex ;
		this.usedOutputColIndex = p_iOutputFirstColIndex;
	}

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

		// used buff cach free
		int iCurrentInputCol = this.firstInputColIndex;
		
		long used = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		long free = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);

		if ( used != 0 ) {
			used = used / 1024 ;
		}
		
		PropertyUtils.setProperty(p_oDStatLineMetric, "field"
				+ this.usedOutputColIndex, used);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.michenux.vmstax.dstat.DStatColParser#getChartMetadatas()
	 */
	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();

		ChartMetaData oSwapChartMetaData = new ChartMetaData();
	    oSwapChartMetaData.setId("swapchart");
	    oSwapChartMetaData.setPanelTitle("Swap");
	    oSwapChartMetaData.setHorizAxisTitle("");
	    oSwapChartMetaData.setVertAxisTitle("Swap Used");
	    oSwapChartMetaData.setVertUnit("kb");
	    oSwapChartMetaData.setDataTipMode("multiple");

	    oSwapChartMetaData.setVertAxisMinimum(0);

	    StringBuilder sToolTip = new StringBuilder();
	    sToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Swap Used : </b>\" + item[\"field");
	    sToolTip.append(this.usedOutputColIndex);
	    sToolTip.append("\"] + \"Kb\"");
	    
	    oSwapChartMetaData.setDataTipTemplate(sToolTip.toString());

	    SerieMetaData oSwapUsedSerie = new SerieMetaData();
	    oSwapUsedSerie.setType("line");
	    oSwapUsedSerie.setDisplayName("Swap Used");
	    oSwapUsedSerie.setFieldx("numLine");
	    oSwapUsedSerie.setFieldy("field" + this.usedOutputColIndex);
	    oSwapUsedSerie.setForm("curve");

	    oSwapUsedSerie.setStrokeColor(14976769);
	    oSwapUsedSerie.setStrokeWeight(2);
	    oSwapChartMetaData.getSeries().add(oSwapUsedSerie);

		listChartMeta.add(oSwapChartMetaData);

		return listChartMeta;
	}

	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader;
	}

	@Override
	public boolean accept(String p_sColName) {
		return "swap".equals(p_sColName) || "swp/total".equals(p_sColName);
	}
}
