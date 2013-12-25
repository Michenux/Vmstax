package org.michenux.vmstax.vmstat.solaris;

import java.util.List;

import org.michenux.vmstax.VmstaxParser;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.LineMetric;
import org.michenux.vmstax.vmstat.AbstractVmParser;

public class VmSolarisParser extends AbstractVmParser<VmSolarisLineMetric>
		implements VmstaxParser {
	
	public LineMetric parseLine(String p_sLine, int p_iLineNumber) {
		VmSolarisLineMetric r_oLineMetric = new VmSolarisLineMetric();
		String[] t_sMetrics = p_sLine.split("\\s+");
		r_oLineMetric.setLineNumber(p_iLineNumber);

		r_oLineMetric.setProcR(Integer.parseInt(t_sMetrics[0]));
		r_oLineMetric.setProcB(Integer.parseInt(t_sMetrics[1]));
		r_oLineMetric.setProcW(Integer.parseInt(t_sMetrics[2]));

		r_oLineMetric.setMemSwap(Integer.parseInt(t_sMetrics[3]));
		r_oLineMetric.setMemFree(Integer.parseInt(t_sMetrics[4]));

		r_oLineMetric.setPageRe(Integer.parseInt(t_sMetrics[5]));
		r_oLineMetric.setPageMf(Integer.parseInt(t_sMetrics[6]));
		r_oLineMetric.setPagePi(Integer.parseInt(t_sMetrics[7]));
		r_oLineMetric.setPagePo(Integer.parseInt(t_sMetrics[8]));
		r_oLineMetric.setPageFr(Integer.parseInt(t_sMetrics[9]));
		r_oLineMetric.setPageDe(Integer.parseInt(t_sMetrics[10]));
		r_oLineMetric.setPageSr(Integer.parseInt(t_sMetrics[11]));

		r_oLineMetric.setDisk1(Integer.parseInt(t_sMetrics[12]));
		r_oLineMetric.setDisk2(Integer.parseInt(t_sMetrics[13]));
		r_oLineMetric.setDisk3(Integer.parseInt(t_sMetrics[14]));
		r_oLineMetric.setDisk4(Integer.parseInt(t_sMetrics[15]));

		r_oLineMetric.setSystemIn(Integer.parseInt(t_sMetrics[16]));
		r_oLineMetric.setFaultSys(Integer.parseInt(t_sMetrics[17]));
		r_oLineMetric.setSystemCs(Integer.parseInt(t_sMetrics[18]));

		r_oLineMetric.setCpuUser(Integer.parseInt(t_sMetrics[19]));
		r_oLineMetric.setCpuSystem(Integer.parseInt(t_sMetrics[20])
				+ r_oLineMetric.getCpuUser());
		if (r_oLineMetric.getCpuSystem() > 100)
			r_oLineMetric.setCpuSystem(100);
		r_oLineMetric.setCpuIdle(100 - r_oLineMetric.getCpuSystem());

		return r_oLineMetric;
	}

	public LineMetric mergeLineMetrics(List<VmSolarisLineMetric> lineToMerge) {
		int iNbPoints = lineToMerge.size();
		VmSolarisLineMetric r_oMergeLineMetric = new VmSolarisLineMetric();
		for (VmSolarisLineMetric oLineMetric : lineToMerge) {
			r_oMergeLineMetric.setLineNumber(r_oMergeLineMetric.getLineNumber()
					+ oLineMetric.getLineNumber());

			r_oMergeLineMetric.setProcR(r_oMergeLineMetric.getProcR()
					+ oLineMetric.getProcR());
			r_oMergeLineMetric.setProcB(r_oMergeLineMetric.getProcB()
					+ oLineMetric.getProcB());
			r_oMergeLineMetric.setProcW(r_oMergeLineMetric.getProcW()
					+ oLineMetric.getProcW());

			r_oMergeLineMetric.setMemSwap(r_oMergeLineMetric.getMemSwap()
					+ oLineMetric.getMemSwap());
			r_oMergeLineMetric.setMemFree(r_oMergeLineMetric.getMemFree()
					+ oLineMetric.getMemFree());

			r_oMergeLineMetric.setPageRe(r_oMergeLineMetric.getPageRe()
					+ oLineMetric.getPageRe());
			r_oMergeLineMetric.setPageMf(r_oMergeLineMetric.getPageMf()
					+ oLineMetric.getPageMf());
			r_oMergeLineMetric.setPagePi(r_oMergeLineMetric.getPagePi()
					+ oLineMetric.getPagePi());
			r_oMergeLineMetric.setPagePo(r_oMergeLineMetric.getPagePo()
					+ oLineMetric.getPagePo());
			r_oMergeLineMetric.setPageFr(r_oMergeLineMetric.getPageFr()
					+ oLineMetric.getPageFr());
			r_oMergeLineMetric.setPageDe(r_oMergeLineMetric.getPageDe()
					+ oLineMetric.getPageDe());
			r_oMergeLineMetric.setPageSr(r_oMergeLineMetric.getPageSr()
					+ oLineMetric.getPageSr());

			r_oMergeLineMetric.setDisk1(r_oMergeLineMetric.getDisk1()
					+ oLineMetric.getDisk1());
			r_oMergeLineMetric.setDisk2(r_oMergeLineMetric.getDisk2()
					+ oLineMetric.getDisk2());
			r_oMergeLineMetric.setDisk3(r_oMergeLineMetric.getDisk3()
					+ oLineMetric.getDisk3());
			r_oMergeLineMetric.setDisk4(r_oMergeLineMetric.getDisk4()
					+ oLineMetric.getDisk4());

			r_oMergeLineMetric.setSystemIn(r_oMergeLineMetric.getSystemIn()
					+ oLineMetric.getSystemIn());
			r_oMergeLineMetric.setFaultSys(r_oMergeLineMetric.getFaultSys()
					+ oLineMetric.getFaultSys());
			r_oMergeLineMetric.setSystemCs(r_oMergeLineMetric.getSystemCs()
					+ oLineMetric.getSystemCs());

			r_oMergeLineMetric.setCpuUser(r_oMergeLineMetric.getCpuUser()
					+ oLineMetric.getCpuUser());
			r_oMergeLineMetric.setCpuSystem(r_oMergeLineMetric.getCpuSystem()
					+ oLineMetric.getCpuSystem());
			r_oMergeLineMetric.setCpuIdle(r_oMergeLineMetric.getCpuIdle()
					+ oLineMetric.getCpuIdle());
		}

		r_oMergeLineMetric.setLineNumber(r_oMergeLineMetric.getLineNumber()
				/ iNbPoints);

		r_oMergeLineMetric.setProcR(r_oMergeLineMetric.getProcR() / iNbPoints);
		r_oMergeLineMetric.setProcB(r_oMergeLineMetric.getProcB() / iNbPoints);
		r_oMergeLineMetric.setProcW(r_oMergeLineMetric.getProcW() / iNbPoints);

		r_oMergeLineMetric.setMemSwap(r_oMergeLineMetric.getMemSwap()
				/ iNbPoints);
		r_oMergeLineMetric.setMemFree(r_oMergeLineMetric.getMemFree()
				/ iNbPoints);

		r_oMergeLineMetric
				.setPageRe(r_oMergeLineMetric.getPageRe() / iNbPoints);
		r_oMergeLineMetric
				.setPageMf(r_oMergeLineMetric.getPageMf() / iNbPoints);
		r_oMergeLineMetric
				.setPagePi(r_oMergeLineMetric.getPagePi() / iNbPoints);
		r_oMergeLineMetric
				.setPagePo(r_oMergeLineMetric.getPagePo() / iNbPoints);
		r_oMergeLineMetric
				.setPageFr(r_oMergeLineMetric.getPageFr() / iNbPoints);
		r_oMergeLineMetric
				.setPageDe(r_oMergeLineMetric.getPageDe() / iNbPoints);
		r_oMergeLineMetric
				.setPageSr(r_oMergeLineMetric.getPageSr() / iNbPoints);

		r_oMergeLineMetric.setDisk1(r_oMergeLineMetric.getDisk1() / iNbPoints);
		r_oMergeLineMetric.setDisk2(r_oMergeLineMetric.getDisk2() / iNbPoints);
		r_oMergeLineMetric.setDisk3(r_oMergeLineMetric.getDisk3() / iNbPoints);
		r_oMergeLineMetric.setDisk4(r_oMergeLineMetric.getDisk4() / iNbPoints);

		r_oMergeLineMetric.setSystemIn(r_oMergeLineMetric.getSystemIn()
				/ iNbPoints);
		r_oMergeLineMetric.setFaultSys(r_oMergeLineMetric.getFaultSys()
				/ iNbPoints);
		r_oMergeLineMetric.setSystemCs(r_oMergeLineMetric.getSystemCs()
				/ iNbPoints);

		r_oMergeLineMetric.setCpuUser(r_oMergeLineMetric.getCpuUser()
				/ iNbPoints);
		r_oMergeLineMetric.setCpuSystem(r_oMergeLineMetric.getCpuSystem()
				/ iNbPoints);
		r_oMergeLineMetric.setCpuIdle(r_oMergeLineMetric.getCpuIdle()
				/ iNbPoints);

		return r_oMergeLineMetric;
	}

	public List<ChartMetaData> getChartMetadatas() {
		return VmSolarisCharts.SOLARIS_CHARTS;
	}

	public String getName() {
		return "vmstat_solaris";
	}

	public String getDisplayName() {
		return "Vmstat Solaris";
	}
	
	@Override
	public String getIcon() {
		return "solarisIcon";
	}
}