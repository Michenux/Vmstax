package org.michenux.vmstax.dstat.cpu;

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
public class CpuDStatParser implements DStatColParser {

	private int firstInputColIndex ;
	
	private int userOutputColIndex ;
	private int sysOutputColIndex ;
	private int idleOutputColIndex ;
	private int waitOutputColIndex ;
//	private int hiqOutputColIndex ;
//	private int siqOutputColIndex ;
	
	private int nbInputCols = 6;
	private int nbOutputCols = 4;

	private String colHeader;

	/**
	 * Affecte l'objet firstColIndex
	 * 
	 * @param p_oFirstColIndex
	 *            Objet firstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex, int p_iOutputColIndex) {
		this.firstInputColIndex = p_iInputFirstColIndex;
		
		this.userOutputColIndex = p_iOutputColIndex;
		this.sysOutputColIndex = p_iOutputColIndex + 1 ;
		this.idleOutputColIndex = p_iOutputColIndex + 2 ;
		this.waitOutputColIndex = p_iOutputColIndex + 3 ;
//		this.hiqOutputColIndex = p_iOutputColIndex + 4 ;
//		this.siqOutputColIndex = p_iOutputColIndex + 5 ;
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

		// user / sys/ idl / wait / hiq / siq
		int iCurrentInputCol = this.firstInputColIndex;
		
		long user = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.userOutputColIndex,
				user);
		
		long sys = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]) + user;
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.sysOutputColIndex,
				sys);
		
		long idle = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.idleOutputColIndex,
				idle);
		
		long wait = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]) + sys;
		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.waitOutputColIndex,
				wait);
		
//		long hiq = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
//		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.hiqOutputColIndex,
//				hiq);
//		
//		long siq = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
//		PropertyUtils.setProperty(p_oDStatLineMetric, "field" + this.siqOutputColIndex,
//				siq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.michenux.vmstax.dstat.DStatColParser#getChartMetadatas()
	 */
	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();
		ChartMetaData oCpuChartMetaData = new ChartMetaData();
		oCpuChartMetaData.setId("cpuchart");
		oCpuChartMetaData.setPanelTitle(this.colHeader); // TODO: A améliorer
		oCpuChartMetaData.setHorizAxisTitle("");
		oCpuChartMetaData.setVertAxisTitle("Percentage");
		oCpuChartMetaData.setDataTipMode("single");
		oCpuChartMetaData.setHorizAxisMaximum(4);
		oCpuChartMetaData.setVertAxisMaximum(100);
		oCpuChartMetaData.setVertAxisMinimum(0);
		oCpuChartMetaData.setVertAxisInterval(10);

		StringBuilder sDataTipTemplate = new StringBuilder();
		sDataTipTemplate
				.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ ");
		sDataTipTemplate.append("\"<b>Total CPU Used : </b>\" + item[\"field");
		sDataTipTemplate.append((this.waitOutputColIndex));
		sDataTipTemplate.append("\"] + \"%\\n\"+");
		sDataTipTemplate
				.append("\"<b>whose : </b>\\n\"+ \"   user: \" + item[\"field");
		sDataTipTemplate.append(this.userOutputColIndex);
		sDataTipTemplate.append("\"] + \"%\\n\"+  ");
		sDataTipTemplate.append("\"   system: \" + ( item[\"field");
		sDataTipTemplate.append(this.sysOutputColIndex);
		sDataTipTemplate.append("\"] - item[\"field");
		sDataTipTemplate.append(this.userOutputColIndex);
		sDataTipTemplate.append("\"] ) + \"%\\n\"+ ");
		sDataTipTemplate.append("\"   wait: \" + ( item[\"field");
		sDataTipTemplate.append(this.waitOutputColIndex);
		sDataTipTemplate.append("\"] - item[\"field");
		sDataTipTemplate.append(this.sysOutputColIndex);
		sDataTipTemplate.append("\"] ) + \"%\\n\"+ ");
		sDataTipTemplate.append("\"   idle: \" + ( item[\"field");
		sDataTipTemplate.append(this.idleOutputColIndex);
		sDataTipTemplate.append("\"] ) + \"%\\n\"");

		oCpuChartMetaData.setDataTipTemplate(sDataTipTemplate.toString());

		SerieMetaData oCpuWaitSerie = new SerieMetaData();
		oCpuWaitSerie.setType("area");
		oCpuWaitSerie.setDisplayName("Cpu Wait");
		oCpuWaitSerie.setFieldx("numLine");
		oCpuWaitSerie.setFieldy("field" + this.waitOutputColIndex);
		oCpuWaitSerie.setMinField("field" + this.sysOutputColIndex);
		oCpuWaitSerie.setForm("curve");
		oCpuWaitSerie.setSolidColorColor(6737151);
		oCpuWaitSerie.setSolidColorAlpha(0.5D);
		oCpuWaitSerie.setStrokeColor(6737151);
		oCpuWaitSerie.setStrokeWeight(1);
		oCpuChartMetaData.getSeries().add(oCpuWaitSerie);

		SerieMetaData oCpuSystemSerie = new SerieMetaData();
		oCpuSystemSerie.setType("area");
		oCpuSystemSerie.setDisplayName("Cpu System");
		oCpuSystemSerie.setFieldx("numLine");
		oCpuSystemSerie.setFieldy("field" + this.sysOutputColIndex);
		oCpuSystemSerie.setMinField("field" + this.userOutputColIndex);
		oCpuSystemSerie.setForm("curve");
		oCpuSystemSerie.setSolidColorColor(14976769);
		oCpuSystemSerie.setSolidColorAlpha(0.5D);
		oCpuSystemSerie.setStrokeColor(14976769);
		oCpuSystemSerie.setStrokeWeight(1);
		oCpuChartMetaData.getSeries().add(oCpuSystemSerie);

		SerieMetaData oCpuUserSerie = new SerieMetaData();
		oCpuUserSerie.setType("area");
		oCpuUserSerie.setDisplayName("Cpu User");
		oCpuUserSerie.setFieldx("numLine");
		oCpuUserSerie.setFieldy("field" + this.userOutputColIndex);
		oCpuUserSerie.setForm("curve");
		oCpuUserSerie.setSolidColorColor(10861646);
		oCpuUserSerie.setSolidColorAlpha(0.4D);
		oCpuUserSerie.setStrokeColor(10861646);
		oCpuUserSerie.setStrokeWeight(1);
		oCpuChartMetaData.getSeries().add(oCpuUserSerie);
		
		listChartMeta.add(oCpuChartMetaData);
		
		// hiq / siq
		
//		ChartMetaData oHiqSiqChartMetaData = new ChartMetaData();
//	    oHiqSiqChartMetaData.setId("hiqsiqchart");
//	    oHiqSiqChartMetaData.setPanelTitle(this.colHeader + " hiq/siq");
//	    oHiqSiqChartMetaData.setHorizAxisTitle("");
//	    oHiqSiqChartMetaData.setVertAxisTitle("");
//	    oHiqSiqChartMetaData.setDataTipMode("multiple");
//
//	    oHiqSiqChartMetaData.setVertAxisMinimum(0);
//
//	    StringBuilder sToolTip = new StringBuilder();
//	    sToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Hiq : </b>\" + item[\"field");
//	    sToolTip.append(this.hiqOutputColIndex);
//	    sToolTip.append("\"] + \"\\n\"+ \"<b>Siq : </b>\" + item[\"field");
//	    sToolTip.append(this.siqOutputColIndex);
//	    sToolTip.append("\"]");
//	    oHiqSiqChartMetaData.setDataTipTemplate(sToolTip.toString());
//
//	    SerieMetaData oHiqSerie = new SerieMetaData();
//	    oHiqSerie.setType("line");
//	    oHiqSerie.setDisplayName("Hiq");
//	    oHiqSerie.setFieldx("numLine");
//	    oHiqSerie.setFieldy("field" + this.hiqOutputColIndex);
//	    oHiqSerie.setForm("curve");
//
//	    oHiqSerie.setStrokeColor(10861646);
//	    oHiqSerie.setStrokeWeight(2);
//	    oHiqSiqChartMetaData.getSeries().add(oHiqSerie);
//
//	    SerieMetaData oSiqSerie = new SerieMetaData();
//	    oSiqSerie.setType("line");
//	    oSiqSerie.setDisplayName("Siq");
//	    oSiqSerie.setFieldx("numLine");
//	    oSiqSerie.setFieldy("field" + this.siqOutputColIndex);
//	    oSiqSerie.setForm("curve");
//
//	    oSiqSerie.setStrokeColor(14976769);
//	    oSiqSerie.setStrokeWeight(2);
//	    oHiqSiqChartMetaData.getSeries().add(oSiqSerie);
//		
//	    listChartMeta.add(oHiqSiqChartMetaData);
		
		return listChartMeta;
	}

	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader;
	}

	@Override
	public boolean accept(String p_sColName) {
		return p_sColName.equals("total cpu usage") || p_sColName.startsWith("cpu");
	}
}
