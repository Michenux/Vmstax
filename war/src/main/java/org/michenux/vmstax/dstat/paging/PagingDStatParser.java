package org.michenux.vmstax.dstat.paging;

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
public class PagingDStatParser implements DStatColParser {

	private int firstInputColIndex ;
	
	private int pageInOutputColIndex ;
	private int pageOutOutputColIndex ;
	
	private int nbInputCols = 2;
	private int nbOutputCols = 2;
	
	private String colHeader ;
	
	/**
	 * Affecte l'objet firstColIndex 
	 * @param p_oFirstColIndex Objet firstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex, int p_iOutputFirstColIndex ) {
		
		this.firstInputColIndex = p_iInputFirstColIndex ;
		this.pageInOutputColIndex = p_iOutputFirstColIndex;
		this.pageOutOutputColIndex = p_iOutputFirstColIndex + 1 ;
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
	public void parse(String[] p_oNextLine, DStatLineMetric p_oDStatLineMetric) throws Exception {

		int iCurrentInputCol = this.firstInputColIndex;
		
		long pageIn = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		if ( pageIn != 0 ) {
			pageIn = pageIn / 1024 ;
		}
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.pageInOutputColIndex,
				pageIn);
		
		long pageOut = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		if ( pageOut != 0 ) {
			pageOut = pageOut / 1024 ;
		}
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.pageOutOutputColIndex,
				pageOut);
	}


	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();
				
		ChartMetaData oPagingMetaData = new ChartMetaData();
	    oPagingMetaData.setId("pagingchart");
	    oPagingMetaData.setPanelTitle(this.colHeader);
	    oPagingMetaData.setHorizAxisTitle("");
	    oPagingMetaData.setVertAxisTitle("Per second");
	    oPagingMetaData.setDataTipMode("multiple");

	    oPagingMetaData.setVertAxisMinimum(0);
	    oPagingMetaData.setVertUnit("kb");

	    StringBuilder oToolTip = new StringBuilder();
	    oToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Memory swapped from disk : </b>\" + item[\"field");
	    oToolTip.append(this.pageInOutputColIndex);
	    oToolTip.append("\"] + \"kb/s\\n\"+ \"<b>Memory swapped to disk : </b>\" + item[\"field");
	    oToolTip.append(this.pageOutOutputColIndex);
	    oToolTip.append("\"] + \"kb/s\"");
	    
	    oPagingMetaData.setDataTipTemplate(oToolTip.toString());

	    SerieMetaData oSwapInSerie = new SerieMetaData();
	    oSwapInSerie.setType("line");
	    oSwapInSerie.setDisplayName("Memory swapped from disk kb/s");
	    oSwapInSerie.setFieldx("numLine");
	    oSwapInSerie.setFieldy("field" + this.pageInOutputColIndex);
	    oSwapInSerie.setForm("curve");

	    oSwapInSerie.setStrokeColor(10861646);
	    oSwapInSerie.setStrokeWeight(2);
	    oPagingMetaData.getSeries().add(oSwapInSerie);

	    SerieMetaData oSwapOutSerie = new SerieMetaData();
	    oSwapOutSerie.setType("line");
	    oSwapOutSerie.setDisplayName("Memory swapped to disk kb/s");
	    oSwapOutSerie.setFieldx("numLine");
	    oSwapOutSerie.setFieldy("field" + this.pageOutOutputColIndex);
	    oSwapOutSerie.setForm("curve");

	    oSwapOutSerie.setStrokeColor(14976769);
	    oSwapOutSerie.setStrokeWeight(2);
	    oPagingMetaData.getSeries().add(oSwapOutSerie);
		
	    listChartMeta.add(oPagingMetaData);
		return listChartMeta;
	}

	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader ;
	}

	@Override
	public boolean accept(String p_sColName) {
		return "paging".equals(p_sColName);
	}
}
