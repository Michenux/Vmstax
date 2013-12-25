package org.michenux.vmstax.dstat.mem;

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
public class MemDStatParser implements DStatColParser {

	private int firstInputColIndex ;
	
	private int freeMemCacheOutputColIndex;

	private int nbInputCols = 4;
	private int nbOutputCols = 1;

	private String colHeader;

	/**
	 * Affecte l'objet firstColIndex
	 * 
	 * @param p_oFirstColIndex
	 *            Objet firstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex, int p_iOutputFirstColIndex ) {
		this.firstInputColIndex = p_iInputFirstColIndex ;
		this.freeMemCacheOutputColIndex = p_iOutputFirstColIndex;
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
		long buff = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		long cach = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		long free = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		
		long freeMemCache = ( used + buff + cach ) / 1024 ;
		PropertyUtils.setProperty(p_oDStatLineMetric, "field"
				+ this.freeMemCacheOutputColIndex, freeMemCache);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.michenux.vmstax.dstat.DStatColParser#getChartMetadatas()
	 */
	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();

		ChartMetaData oMemChartMetaData = new ChartMetaData();
		oMemChartMetaData.setId("memchart");
		oMemChartMetaData.setPanelTitle(this.colHeader);
		oMemChartMetaData.setHorizAxisTitle("");
		oMemChartMetaData.setVertAxisTitle("Memory");
		oMemChartMetaData.setVertUnit("kb");
		oMemChartMetaData.setDataTipMode("multiple");

		oMemChartMetaData.setVertAxisMinimum(0);

		StringBuilder sToolTip = new StringBuilder();
		sToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Free Memory : </b>\" + item[\"field");
		sToolTip.append(this.freeMemCacheOutputColIndex);
		sToolTip.append("\"] + \"kb\"");
		oMemChartMetaData
				.setDataTipTemplate(sToolTip.toString());

		SerieMetaData oFreeMemSerie = new SerieMetaData();
		oFreeMemSerie.setType("line");
		oFreeMemSerie.setDisplayName("Mem Free (-/+ buffer/cache)");
		oFreeMemSerie.setFieldx("numLine");
		oFreeMemSerie.setFieldy("field" + this.freeMemCacheOutputColIndex);
		oFreeMemSerie.setForm("curve");

		oFreeMemSerie.setStrokeColor(6737151);
		oFreeMemSerie.setStrokeWeight(2);
		oMemChartMetaData.getSeries().add(oFreeMemSerie);

		listChartMeta.add(oMemChartMetaData);

		return listChartMeta;
	}

	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader;
	}

	@Override
	public boolean accept(String p_sColName) {
		return "memory usage".equals(p_sColName);
	}
}
