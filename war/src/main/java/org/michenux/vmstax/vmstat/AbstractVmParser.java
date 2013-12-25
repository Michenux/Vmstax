package org.michenux.vmstax.vmstat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.michenux.vmstax.VmstaxContext;
import org.michenux.vmstax.VmstaxData;
import org.michenux.vmstax.VmstaxParseException;
import org.michenux.vmstax.VmstaxParser;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.LineMetric;

public abstract class AbstractVmParser<T> implements VmstaxParser {
	private static final int MAX_POINTS_NUMBER = 210;

	public abstract LineMetric parseLine(String paramString, int paramInt);

	public abstract LineMetric mergeLineMetrics(
			List<T> paramList);

	public abstract List<ChartMetaData> getChartMetadatas();

	public List<VmstaxData> parse(List<String> p_listLines, VmstaxContext p_oVmstaxContext)
			throws VmstaxParseException {
		VmstaxData oVmstaxData = new VmstaxData();
		List<LineMetric> listMetrics = new ArrayList<LineMetric>();
		int iLineNumber = 1;
		int iRealLineNumber = 1;
		System.out.println("number of lines to treat : " + p_listLines.size());

		for (String sLine : p_listLines) {
			sLine = sLine.trim();

			if (!(ignoreLine(sLine))) {
				try {
					listMetrics.add(parseLine(sLine, iLineNumber));
				} catch (Exception e) {
					StringBuffer sb = new StringBuffer();
					sb.append("Parsing error, line : ");
					sb.append(iRealLineNumber);
					if (e.getMessage() != null) {
						sb.append(" (");
						sb.append(e.toString());
						sb.append(')');
					}
					throw new VmstaxParseException(sb.toString(), e);
				}
				++iLineNumber;
			}
			++iRealLineNumber;
		}

		System.out.println("total metrics[size=" + listMetrics.size() + "]");

		if (listMetrics.size() > 210) {
			listMetrics = mergePoints(listMetrics);
			System.out.println("merged metrics[size=" + listMetrics.size()
					+ "]");
		}

		oVmstaxData.setMetrics(listMetrics);
		oVmstaxData.setChartMetadatas(getChartMetadatas());

		List<VmstaxData> r_listVmstax = new ArrayList<VmstaxData>();
		r_listVmstax.add(oVmstaxData);
		
		return r_listVmstax;
	}

	private List<LineMetric> mergePoints(List<LineMetric> p_listMetrics) {
		List<LineMetric> r_mergedMetrics = new ArrayList<LineMetric>();
		List keepForMerge = new ArrayList();

		int iNbPoints = p_listMetrics.size();
		double incStep = iNbPoints / 210.0D;

		System.out.println("inc step = " + incStep);

		double currentStep = incStep;
		Iterator<LineMetric> iterMetrics = p_listMetrics.listIterator();
		while (iterMetrics.hasNext()) {
			LineMetric oCurrentMetric = iterMetrics.next();

			if (oCurrentMetric.getLineNumber() + 1 > currentStep) {
				if (!(keepForMerge.isEmpty())) {
					keepForMerge.add(oCurrentMetric);
					oCurrentMetric = mergeLineMetrics(keepForMerge);
					keepForMerge.clear();
				}
				r_mergedMetrics.add(oCurrentMetric);
				currentStep += incStep;
			} else {
				keepForMerge.add(oCurrentMetric);
			}

		}

		if (!(keepForMerge.isEmpty())) {
			r_mergedMetrics.add(mergeLineMetrics(keepForMerge));
		}

		return r_mergedMetrics;
	}

	public boolean ignoreLine(String p_sLine) {
		return ((p_sLine.isEmpty()) || (p_sLine.charAt(0) == 'p')
				|| (p_sLine.charAt(0) == 'r') || (p_sLine.charAt(0) == 'k')
				|| (p_sLine.charAt(0) == '-') || (p_sLine.contains("mem")));
	}
}