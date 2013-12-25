package org.michenux.vmstax.dstat;

import java.util.List;

import org.michenux.vmstax.charts.ChartMetaData;



/**
 * @author Michenux
 *
 */
public interface DStatColParser {

	/**
	 * @param p_sColName
	 * @return
	 */
	public boolean accept( String p_sColName );
	
	/**
	 * @param p_iFirstColIndex
	 */
	public void setColIndexes(int p_iInputFirstColIndex, int p_iOutputFirstColIndex);
	
	/**
	 * @return
	 */
	public int getInputNbCols();

	/**
	 * @return
	 */
	public int getOutputNbCols();
	
	/**
	 * TODO Décrire la méthode parse de la classe DStatParser
	 * @param p_oNextLine
	 */
	public void parse(String[] p_oNextLine, DStatLineMetric oDStatLineMetric) throws Exception;
	
	/**
	 * @return
	 */
	public List<ChartMetaData> getChartMetadatas();
	
	/**
	 * @param p_sHeader
	 */
	public void setColHeader( String p_sHeader );
}
