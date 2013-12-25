package org.michenux.vmstax.vmstat.aix;

import java.util.List;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.LineMetric;
import org.michenux.vmstax.vmstat.AbstractVmParser;
import org.springframework.stereotype.Service;

@Service("vmAIXParser")
public class VmAIXParser extends AbstractVmParser<VmAIXLineMetric> {
	public LineMetric parseLine(String p_sLine, int p_iLineNumber) {
		VmAIXLineMetric r_oLineMetric = new VmAIXLineMetric();
		String[] t_sMetrics = p_sLine.split("\\s+");
		r_oLineMetric.setLineNumber(p_iLineNumber);

		r_oLineMetric.setProcR(Integer.parseInt(t_sMetrics[0]));
		r_oLineMetric.setProcB(Integer.parseInt(t_sMetrics[1]));

		r_oLineMetric
				.setMemAvm((long) (Long.parseLong(t_sMetrics[2]) * 4.096D));
		r_oLineMetric
				.setMemFree((long) (Long.parseLong(t_sMetrics[3]) * 4.096D));

		r_oLineMetric.setPageRe(Integer.parseInt(t_sMetrics[4]));
		r_oLineMetric.setPagePi(Integer.parseInt(t_sMetrics[5]));
		r_oLineMetric.setPagePo(Integer.parseInt(t_sMetrics[6]));
		r_oLineMetric.setPageFr(Integer.parseInt(t_sMetrics[7]));
		r_oLineMetric.setPageSr(Integer.parseInt(t_sMetrics[8]));
		r_oLineMetric.setPageCy(Integer.parseInt(t_sMetrics[9]));

		r_oLineMetric.setSystemIn(Integer.parseInt(t_sMetrics[10]));
		r_oLineMetric.setSystemCall(Integer.parseInt(t_sMetrics[11]));
		r_oLineMetric.setSystemCs(Integer.parseInt(t_sMetrics[12]));

		r_oLineMetric.setCpuUser(Integer.parseInt(t_sMetrics[13]));
		r_oLineMetric.setCpuSystem(Integer.parseInt(t_sMetrics[14])
				+ r_oLineMetric.getCpuUser());
		r_oLineMetric.setCpuIOWaiting(Integer.parseInt(t_sMetrics[16])
				+ r_oLineMetric.getCpuSystem());
		if (r_oLineMetric.getCpuIOWaiting() > 100)
			r_oLineMetric.setCpuIOWaiting(100);
		r_oLineMetric.setCpuIdle(100 - r_oLineMetric.getCpuIOWaiting());

		return r_oLineMetric;
	}

	public LineMetric mergeLineMetrics(List<VmAIXLineMetric> lineToMerge) {
		int iNbPoints = lineToMerge.size();
		VmAIXLineMetric r_oMergeLineMetric = new VmAIXLineMetric();
		for (VmAIXLineMetric oLineMetric : lineToMerge) {
			r_oMergeLineMetric.setLineNumber(r_oMergeLineMetric.getLineNumber()
					+ oLineMetric.getLineNumber());

			r_oMergeLineMetric.setProcR(r_oMergeLineMetric.getProcR()
					+ oLineMetric.getProcR());
			r_oMergeLineMetric.setProcB(r_oMergeLineMetric.getProcB()
					+ oLineMetric.getProcB());

			r_oMergeLineMetric.setMemAvm(r_oMergeLineMetric.getMemAvm()
					+ oLineMetric.getMemAvm());
			r_oMergeLineMetric.setMemFree(r_oMergeLineMetric.getMemFree()
					+ oLineMetric.getMemFree());

			r_oMergeLineMetric.setPageRe(r_oMergeLineMetric.getPageRe()
					+ oLineMetric.getPageRe());
			r_oMergeLineMetric.setPagePi(r_oMergeLineMetric.getPagePi()
					+ oLineMetric.getPagePi());
			r_oMergeLineMetric.setPagePo(r_oMergeLineMetric.getPagePo()
					+ oLineMetric.getPagePo());
			r_oMergeLineMetric.setPageFr(r_oMergeLineMetric.getPageFr()
					+ oLineMetric.getPageFr());
			r_oMergeLineMetric.setPageSr(r_oMergeLineMetric.getPageSr()
					+ oLineMetric.getPageSr());
			r_oMergeLineMetric.setPageCy(r_oMergeLineMetric.getPageCy()
					+ oLineMetric.getPageCy());

			r_oMergeLineMetric.setSystemIn(r_oMergeLineMetric.getSystemIn()
					+ oLineMetric.getSystemIn());
			r_oMergeLineMetric.setSystemCall(r_oMergeLineMetric.getSystemCall()
					+ oLineMetric.getSystemCall());
			r_oMergeLineMetric.setSystemCs(r_oMergeLineMetric.getSystemCs()
					+ oLineMetric.getSystemCs());

			r_oMergeLineMetric.setCpuUser(r_oMergeLineMetric.getCpuUser()
					+ oLineMetric.getCpuUser());
			r_oMergeLineMetric.setCpuSystem(r_oMergeLineMetric.getCpuSystem()
					+ oLineMetric.getCpuSystem());
			r_oMergeLineMetric.setCpuIOWaiting(r_oMergeLineMetric
					.getCpuIOWaiting() + oLineMetric.getCpuIOWaiting());
			r_oMergeLineMetric.setCpuIdle(r_oMergeLineMetric.getCpuIdle()
					+ oLineMetric.getCpuIdle());
		}

		r_oMergeLineMetric.setLineNumber(r_oMergeLineMetric.getLineNumber()
				/ iNbPoints);

		r_oMergeLineMetric.setProcR(r_oMergeLineMetric.getProcR() / iNbPoints);
		r_oMergeLineMetric.setProcB(r_oMergeLineMetric.getProcB() / iNbPoints);

		r_oMergeLineMetric
				.setMemAvm(r_oMergeLineMetric.getMemAvm() / iNbPoints);
		r_oMergeLineMetric.setMemFree(r_oMergeLineMetric.getMemFree()
				/ iNbPoints);

		r_oMergeLineMetric
				.setPageRe(r_oMergeLineMetric.getPageRe() / iNbPoints);
		r_oMergeLineMetric
				.setPagePi(r_oMergeLineMetric.getPagePi() / iNbPoints);
		r_oMergeLineMetric
				.setPagePo(r_oMergeLineMetric.getPagePo() / iNbPoints);
		r_oMergeLineMetric
				.setPageFr(r_oMergeLineMetric.getPageFr() / iNbPoints);
		r_oMergeLineMetric
				.setPageSr(r_oMergeLineMetric.getPageSr() / iNbPoints);
		r_oMergeLineMetric
				.setPageCy(r_oMergeLineMetric.getPageCy() / iNbPoints);

		r_oMergeLineMetric.setSystemIn(r_oMergeLineMetric.getSystemIn()
				/ iNbPoints);
		r_oMergeLineMetric.setSystemCall(r_oMergeLineMetric.getSystemCall()
				/ iNbPoints);
		r_oMergeLineMetric.setSystemCs(r_oMergeLineMetric.getSystemCs()
				/ iNbPoints);

		r_oMergeLineMetric.setCpuUser(r_oMergeLineMetric.getCpuUser()
				/ iNbPoints);
		r_oMergeLineMetric.setCpuSystem(r_oMergeLineMetric.getCpuSystem()
				/ iNbPoints);
		r_oMergeLineMetric.setCpuIOWaiting(r_oMergeLineMetric.getCpuIOWaiting()
				/ iNbPoints);
		r_oMergeLineMetric.setCpuIdle(r_oMergeLineMetric.getCpuIdle()
				/ iNbPoints);

		return r_oMergeLineMetric;
	}

	public List<ChartMetaData> getChartMetadatas() {
		return VmAIXCharts.AIX_CHARTS;
	}

	public String getName() {
		return "vmstat_aix";
	}

	public String getDisplayName() {
		return "Vmstat AIX";
	}

	@Override
	public String getIcon() {
		return "aixIcon";
	}
}