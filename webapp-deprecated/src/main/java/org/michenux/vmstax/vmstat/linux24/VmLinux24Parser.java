package org.michenux.vmstax.vmstat.linux24;

import java.util.List;
import org.michenux.vmstax.VmstaxParser;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.LineMetric;
import org.michenux.vmstax.vmstat.AbstractVmParser;
import org.springframework.stereotype.Service;

@Service("vmLinux24Parser")
public class VmLinux24Parser extends AbstractVmParser<VmLinux24LineMetric> implements VmstaxParser {
	public LineMetric parseLine(String p_sLine, int p_iLineNumber) {
		VmLinux24LineMetric r_oLineMetric = new VmLinux24LineMetric();
		String[] t_sMetrics = p_sLine.split("\\s+");
		r_oLineMetric.setLineNumber(p_iLineNumber);
		r_oLineMetric.setProcR(Integer.parseInt(t_sMetrics[0]));
		r_oLineMetric.setProcB(Integer.parseInt(t_sMetrics[1]));
		r_oLineMetric.setProcW(Integer.parseInt(t_sMetrics[2]));
		r_oLineMetric.setMemSwap(Integer.parseInt(t_sMetrics[3]));
		r_oLineMetric.setMemFree(Integer.parseInt(t_sMetrics[4])
				+ Integer.parseInt(t_sMetrics[5])
				+ Integer.parseInt(t_sMetrics[6]));
		r_oLineMetric.setMemBuff(Integer.parseInt(t_sMetrics[5]));
		r_oLineMetric.setMemCache(Integer.parseInt(t_sMetrics[6]));
		r_oLineMetric.setSwapIn(Integer.parseInt(t_sMetrics[7]));
		r_oLineMetric.setSwapOut(Integer.parseInt(t_sMetrics[8]));
		r_oLineMetric.setIoIn(Integer.parseInt(t_sMetrics[9]));
		r_oLineMetric.setIoOut(Integer.parseInt(t_sMetrics[10]));
		r_oLineMetric.setSystemIn(Integer.parseInt(t_sMetrics[11]));
		r_oLineMetric.setSystemCs(Integer.parseInt(t_sMetrics[12]));
		r_oLineMetric.setCpuUser(Integer.parseInt(t_sMetrics[13]));
		r_oLineMetric.setCpuSystem(Integer.parseInt(t_sMetrics[14])
				+ r_oLineMetric.getCpuUser());
		if (r_oLineMetric.getCpuSystem() > 100)
			r_oLineMetric.setCpuSystem(100);
		r_oLineMetric.setCpuIdle(100 - r_oLineMetric.getCpuSystem());

		return r_oLineMetric;
	}

	public LineMetric mergeLineMetrics(
			List<VmLinux24LineMetric> p_listLineToMerge) {
		int iNbPoints = p_listLineToMerge.size();
		VmLinux24LineMetric r_oMergeLineMetric = new VmLinux24LineMetric();
		for (VmLinux24LineMetric oLineMetric : p_listLineToMerge) {
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
			r_oMergeLineMetric.setMemBuff(r_oMergeLineMetric.getMemBuff()
					+ oLineMetric.getMemBuff());
			r_oMergeLineMetric.setMemCache(r_oMergeLineMetric.getMemCache()
					+ oLineMetric.getMemCache());
			r_oMergeLineMetric.setSwapIn(r_oMergeLineMetric.getSwapIn()
					+ oLineMetric.getSwapIn());
			r_oMergeLineMetric.setSwapOut(r_oMergeLineMetric.getSwapOut()
					+ oLineMetric.getSwapOut());
			r_oMergeLineMetric.setIoIn(r_oMergeLineMetric.getIoIn()
					+ oLineMetric.getIoIn());
			r_oMergeLineMetric.setIoOut(r_oMergeLineMetric.getIoOut()
					+ oLineMetric.getIoOut());
			r_oMergeLineMetric.setSystemIn(r_oMergeLineMetric.getSystemIn()
					+ oLineMetric.getSystemIn());
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
		r_oMergeLineMetric.setMemBuff(r_oMergeLineMetric.getMemBuff()
				/ iNbPoints);
		r_oMergeLineMetric.setMemCache(r_oMergeLineMetric.getMemCache()
				/ iNbPoints);
		r_oMergeLineMetric
				.setSwapIn(r_oMergeLineMetric.getSwapIn() / iNbPoints);
		r_oMergeLineMetric.setSwapOut(r_oMergeLineMetric.getSwapOut()
				/ iNbPoints);
		r_oMergeLineMetric.setIoIn(r_oMergeLineMetric.getIoIn() / iNbPoints);
		r_oMergeLineMetric.setIoOut(r_oMergeLineMetric.getIoOut() / iNbPoints);
		r_oMergeLineMetric.setSystemIn(r_oMergeLineMetric.getSystemIn()
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
		return VmLinux24Charts.LINUX_CHARTS;
	}

	public String getName() {
		return "vmstat_linux24";
	}

	public String getDisplayName() {
		return "Vmstat Linux 2.4";
	}
	
	@Override
	public String getIcon() {
		return "linux24Icon";
	}
}