package org.michenux.vmstax.dstat.io;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.SerieMetaData;
import org.michenux.vmstax.dstat.DStatColParser;
import org.michenux.vmstax.dstat.DStatLineMetric;

public class IODStatParser implements DStatColParser {

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
		
		long recv = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		if ( recv != 0 ) {
			recv = recv / 1024 ;
		}
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.readOutputColIndex,
				recv);
		
		long sent = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		if ( sent != 0 ) {
			sent = sent / 1024 ;
		}
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.writeOutputColIndex,
				sent);
	}


	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();
				
		ChartMetaData oIOMetaData = new ChartMetaData();
	    oIOMetaData.setId("io");
	    oIOMetaData.setPanelTitle(this.colHeader);
	    oIOMetaData.setHorizAxisTitle("");
	    oIOMetaData.setVertAxisTitle("Per second");
	    oIOMetaData.setDataTipMode("multiple");

	    oIOMetaData.setVertAxisMinimum(0);
	    //oIOMetaData.setVertUnit("kb");

	    StringBuilder oToolTip = new StringBuilder();
	    oToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Read : </b>\" + item[\"field");
	    oToolTip.append(this.readOutputColIndex);
	    oToolTip.append("\"] + \"/s\\n\"+ \"<b>Write : </b>\" + item[\"field");
	    oToolTip.append(this.writeOutputColIndex);
	    oToolTip.append("\"] + \"/s\"");
	    
	    oIOMetaData.setDataTipTemplate(oToolTip.toString());

	    SerieMetaData oReadSerie = new SerieMetaData();
	    oReadSerie.setType("line");
	    oReadSerie.setDisplayName("Read /s");
	    oReadSerie.setFieldx("numLine");
	    oReadSerie.setFieldy("field" + this.readOutputColIndex);
	    oReadSerie.setForm("curve");

	    oReadSerie.setStrokeColor(10861646);
	    oReadSerie.setStrokeWeight(2);
	    oIOMetaData.getSeries().add(oReadSerie);

	    SerieMetaData oWriteSerie = new SerieMetaData();
	    oWriteSerie.setType("line");
	    oWriteSerie.setDisplayName("Write /s");
	    oWriteSerie.setFieldx("numLine");
	    oWriteSerie.setFieldy("field" + this.writeOutputColIndex);
	    oWriteSerie.setForm("curve");

	    oWriteSerie.setStrokeColor(14976769);
	    oWriteSerie.setStrokeWeight(2);
	    oIOMetaData.getSeries().add(oWriteSerie);
		
	    listChartMeta.add(oIOMetaData);
		return listChartMeta;
	}

	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader ;
	}

	@Override
	public boolean accept(String p_sColName) {
		return "io/total".equals(p_sColName) || p_sColName.startsWith("io/");
	}
}
