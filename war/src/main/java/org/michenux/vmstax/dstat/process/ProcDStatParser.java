package org.michenux.vmstax.dstat.process;

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
public class ProcDStatParser implements DStatColParser {

	private int firstInputColIndex;

	private int runOutputColIndex;
	private int blkOutputColIndex;

	private int nbInputCols = 3;
	private int nbOutputCols = 2;

	private String colHeader;

	/**
	 * Affecte l'objet firstColIndex
	 * 
	 * @param p_oFirstColIndex
	 *            Objet firstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex,
			int p_iOutputFirstColIndex) {
		this.firstInputColIndex = p_iInputFirstColIndex;
		this.runOutputColIndex = p_iOutputFirstColIndex;
		this.blkOutputColIndex = p_iOutputFirstColIndex + 1;
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

		long run = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		long blk = (long) Double.parseDouble(p_oNextLine[iCurrentInputCol++]);
		long newProc = (long) Double
				.parseDouble(p_oNextLine[iCurrentInputCol++]);

		PropertyUtils.setProperty(p_oDStatLineMetric, "field"
				+ this.runOutputColIndex, run);
		PropertyUtils.setProperty(p_oDStatLineMetric, "field"
				+ this.blkOutputColIndex, blk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.michenux.vmstax.dstat.DStatColParser#getChartMetadatas()
	 */
	@Override
	public List<ChartMetaData> getChartMetadatas() {
		List<ChartMetaData> listChartMeta = new ArrayList<ChartMetaData>();

		ChartMetaData oProcessChartMetaData = new ChartMetaData();
		oProcessChartMetaData.setId("processchart");
		oProcessChartMetaData.setPanelTitle(this.colHeader);
		oProcessChartMetaData.setHorizAxisTitle("");
		oProcessChartMetaData.setVertAxisTitle("# of processes");
		oProcessChartMetaData.setDataTipMode("multiple");

		oProcessChartMetaData.setVertAxisMinimum(0);
		oProcessChartMetaData.setVertAxisInterval(1);
		oProcessChartMetaData.setVertAxisMinorInterval(1);
		
		StringBuilder sToolTip = new StringBuilder();
		sToolTip.append("\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Number of waiting processes : </b>\" + item[\"field");
		sToolTip.append(this.runOutputColIndex);
		sToolTip.append("\"] + \"\\n\"+ \"<b>Processes in uninterruptible sleep : </b>\" + item[\"field");
		sToolTip.append(this.blkOutputColIndex);
		sToolTip.append("\"]");
		
		oProcessChartMetaData
				.setDataTipTemplate(sToolTip.toString());

		SerieMetaData oProcWaitSerie = new SerieMetaData();
		oProcWaitSerie.setType("line");
		oProcWaitSerie.setDisplayName("Processes waiting for run time");
		oProcWaitSerie.setFieldx("numLine");
		oProcWaitSerie.setFieldy("field" + this.runOutputColIndex);
		oProcWaitSerie.setForm("curve");

		oProcWaitSerie.setStrokeColor(10861646);
		oProcWaitSerie.setStrokeWeight(2);
		oProcessChartMetaData.getSeries().add(oProcWaitSerie);

		SerieMetaData oProcBSerie = new SerieMetaData();
		oProcBSerie.setType("line");
		oProcBSerie.setDisplayName("Processes in uninterruptible sleep");
		oProcBSerie.setFieldx("numLine");
		oProcBSerie.setFieldy("field" + this.blkOutputColIndex);
		oProcBSerie.setForm("curve");

		oProcBSerie.setStrokeColor(14976769);
		oProcBSerie.setStrokeWeight(2);
		oProcessChartMetaData.getSeries().add(oProcBSerie);

		listChartMeta.add(oProcessChartMetaData);

		return listChartMeta;
	}

	@Override
	public void setColHeader(String p_sHeader) {
		this.colHeader = p_sHeader;
	}

	@Override
	public boolean accept(String p_sColName) {
		return "procs".equals(p_sColName);
	}
}
