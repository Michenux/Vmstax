package org.michenux.vmstax.dstat.network;

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
public class NetworkDStatParser implements DStatColParser {

	private int firstInputColIndex ;
	
	private int recvOutputColIndex ;
	private int sentOutputColIndex ;
	
	private int nbInputCols = 2;
	private int nbOutputCols = 2;
	
	private String colHeader ;
	
	/**
	 * Affecte l'objet firstColIndex 
	 * @param p_oFirstColIndex Objet firstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex, int p_iOutputFirstColIndex ) {
		
		this.firstInputColIndex = p_iInputFirstColIndex ;
		this.recvOutputColIndex = p_iOutputFirstColIndex;
		this.sentOutputColIndex = p_iOutputFirstColIndex + 1 ;
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
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.recvOutputColIndex,
				recv);
		
		long sent = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		if ( sent != 0 ) {
			sent = sent / 1024 ;
		}
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.sentOutputColIndex,
				sent);
	}


	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();
				
		ChartMetaData oNetworkMetaData = new ChartMetaData();
	    oNetworkMetaData.setId("netchart");
	    oNetworkMetaData.setPanelTitle(this.colHeader);
	    oNetworkMetaData.setHorizAxisTitle("");
	    oNetworkMetaData.setVertAxisTitle("Per second");
	    oNetworkMetaData.setDataTipMode("multiple");

	    oNetworkMetaData.setVertAxisMinimum(0);
	    oNetworkMetaData.setVertUnit("kb");

	    StringBuilder oToolTip = new StringBuilder();
	    oToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Received : </b>\" + item[\"field");
	    oToolTip.append(this.recvOutputColIndex);
	    oToolTip.append("\"] + \"kb/s\\n\"+ \"<b>Sent : </b>\" + item[\"field");
	    oToolTip.append(this.sentOutputColIndex);
	    oToolTip.append("\"] + \"kb/s\"");
	    
	    oNetworkMetaData.setDataTipTemplate(oToolTip.toString());

	    SerieMetaData oRecvSerie = new SerieMetaData();
	    oRecvSerie.setType("line");
	    oRecvSerie.setDisplayName("Received kb/s");
	    oRecvSerie.setFieldx("numLine");
	    oRecvSerie.setFieldy("field" + this.recvOutputColIndex);
	    oRecvSerie.setForm("curve");

	    oRecvSerie.setStrokeColor(10861646);
	    oRecvSerie.setStrokeWeight(2);
	    oNetworkMetaData.getSeries().add(oRecvSerie);

	    SerieMetaData oSentSerie = new SerieMetaData();
	    oSentSerie.setType("line");
	    oSentSerie.setDisplayName("Sent kb/s");
	    oSentSerie.setFieldx("numLine");
	    oSentSerie.setFieldy("field" + this.sentOutputColIndex);
	    oSentSerie.setForm("curve");

	    oSentSerie.setStrokeColor(14976769);
	    oSentSerie.setStrokeWeight(2);
	    oNetworkMetaData.getSeries().add(oSentSerie);
		
	    listChartMeta.add(oNetworkMetaData);
		return listChartMeta;
	}

	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader ;
	}

	@Override
	public boolean accept(String p_sColName) {
		return "net/total".equals(p_sColName) || p_sColName.startsWith("net/");
	}
}
