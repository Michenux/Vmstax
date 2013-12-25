package org.michenux.vmstax.dstat.disk;

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
public class DiskDStatParser implements DStatColParser {

	private int firstInputColIndex ;
	
	private int readOutputColIndex ;
	private int writeOutputColIndex ;
	
	private int nbInputCols = 2;
	private int nbOutputCols = 2;
	
	private String colHeader ;
	
	/**
	 * Affecte l'objet firstColIndex 
	 * @param p_oFirstColIndex Objet firstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex, int p_iOutputFirstColIndex ) {
		
		this.firstInputColIndex = p_iInputFirstColIndex ;
		this.readOutputColIndex = p_iOutputFirstColIndex;
		this.writeOutputColIndex = p_iOutputFirstColIndex + 1 ;
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
		
		long read = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		if ( read != 0 ) {
			read = read / 1024 ;
		}
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.readOutputColIndex,
				read);
		
		long write = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		if ( write != 0 ) {
			write = write / 1024 ;
		}
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.writeOutputColIndex,
				write);
	}


	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();
		ChartMetaData oDiskChartMetaData = new ChartMetaData();
	    oDiskChartMetaData.setId("diskchart");
	    oDiskChartMetaData.setPanelTitle(this.colHeader);
	    oDiskChartMetaData.setHorizAxisTitle("");
	    oDiskChartMetaData.setVertAxisTitle("kb/s");
	    oDiskChartMetaData.setVertUnit("kb");
	    oDiskChartMetaData.setDataTipMode("multiple");

	    oDiskChartMetaData.setVertAxisMinimum(0);

	    StringBuilder sToolTip = new StringBuilder();
	    sToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Read : </b>\" + item[\"field");
	    sToolTip.append(this.readOutputColIndex);
	    sToolTip.append("\"] + \" kb/s\\n\"+ \"<b>Write : </b>\" + item[\"field");
	    sToolTip.append(this.writeOutputColIndex);
	    sToolTip.append("\"] + \" kb/s\"");
	    oDiskChartMetaData.setDataTipTemplate(sToolTip.toString());

	    SerieMetaData oReaderie = new SerieMetaData();
	    oReaderie.setType("line");
	    oReaderie.setDisplayName("Read");
	    oReaderie.setFieldx("numLine");
	    oReaderie.setFieldy("field" + this.readOutputColIndex);
	    oReaderie.setForm("curve");

	    oReaderie.setStrokeColor(10861646);
	    oReaderie.setStrokeWeight(2);
	    oDiskChartMetaData.getSeries().add(oReaderie);

	    SerieMetaData oWriteSerie = new SerieMetaData();
	    oWriteSerie.setType("line");
	    oWriteSerie.setDisplayName("Write");
	    oWriteSerie.setFieldx("numLine");
	    oWriteSerie.setFieldy("field" + this.writeOutputColIndex);
	    oWriteSerie.setForm("curve");

	    oWriteSerie.setStrokeColor(14976769);
	    oWriteSerie.setStrokeWeight(2);
	    oDiskChartMetaData.getSeries().add(oWriteSerie);
		
	    listChartMeta.add(oDiskChartMetaData);
		return listChartMeta;
	}

	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader ;
	}

	@Override
	public boolean accept(String p_sColName) {
		return "dsk/total".equals(p_sColName) || p_sColName.startsWith("dsk/");
	}
}
