package org.michenux.vmstax.vmstat.linux24;

import java.util.ArrayList;
import java.util.List;

import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.SerieMetaData;

public class VmLinux24Charts
{
  public static List<ChartMetaData> LINUX_CHARTS = new ArrayList<ChartMetaData>();
  
  static {
	  LINUX_CHARTS.add(getCpuChart());
	  LINUX_CHARTS.add(getProcessChart());
	  LINUX_CHARTS.add(getMemoryChart());
	  LINUX_CHARTS.add(getIOChart());
	  LINUX_CHARTS.add(getSwapChart());
	  LINUX_CHARTS.add(getPagingChart());
  }

  private static ChartMetaData getCpuChart()
  {
    ChartMetaData oCpuChartMetaData = new ChartMetaData();
    oCpuChartMetaData.setId("cpuchart");
    oCpuChartMetaData.setPanelTitle("CPU");
    oCpuChartMetaData.setHorizAxisTitle("");
    oCpuChartMetaData.setVertAxisTitle("Percentage");
    oCpuChartMetaData.setDataTipMode("single");
    oCpuChartMetaData.setHorizAxisMaximum(4);
    oCpuChartMetaData.setVertAxisMaximum(100);
    oCpuChartMetaData.setVertAxisMinimum(0);
    oCpuChartMetaData.setVertAxisInterval(10);
    oCpuChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Total CPU Used : </b>\" + item[\"cpuIOWaiting\"] + \"%\\n\"+ \"<b>whose : </b>\\n\"+ \"   user: \" + item[\"cpuUser\"] + \"%\\n\"+ \"   system: \" + ( item[\"cpuSystem\"] - item[\"cpuUser\"] ) + \"%\\n\"+ \"   idle: \" + ( 100 - item[\"cpuSystem\"] ) + \"%\\n\"");

    SerieMetaData oCpuSystemSerie = new SerieMetaData();
    oCpuSystemSerie.setType("area");
    oCpuSystemSerie.setDisplayName("Cpu System");
    oCpuSystemSerie.setFieldx("numLine");
    oCpuSystemSerie.setFieldy("cpuSystem");
    oCpuSystemSerie.setMinField("cpuUser");
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
    oCpuUserSerie.setFieldy("cpuUser");
    oCpuUserSerie.setForm("curve");
    oCpuUserSerie.setSolidColorColor(10861646);
    oCpuUserSerie.setSolidColorAlpha(0.4D);
    oCpuUserSerie.setStrokeColor(10861646);
    oCpuUserSerie.setStrokeWeight(1);
    oCpuChartMetaData.getSeries().add(oCpuUserSerie);

    return oCpuChartMetaData;
  }

  private static ChartMetaData getProcessChart()
  {
    ChartMetaData oProcessChartMetaData = new ChartMetaData();
    oProcessChartMetaData.setId("processchart");
    oProcessChartMetaData.setPanelTitle("Process");
    oProcessChartMetaData.setHorizAxisTitle("");
    oProcessChartMetaData.setVertAxisTitle("# of processes");
    oProcessChartMetaData.setDataTipMode("multiple");

    oProcessChartMetaData.setVertAxisMinimum(0);
    oProcessChartMetaData.setVertAxisInterval(1);
    oProcessChartMetaData.setVertAxisMinorInterval(1);
    oProcessChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Number of waiting processes : </b>\" + item[\"procR\"] + \"\\n\"+ \"<b>Processes in uninterruptible sleep : </b>\" + item[\"procB\"]");

    SerieMetaData oProcWaitSerie = new SerieMetaData();
    oProcWaitSerie.setType("line");
    oProcWaitSerie.setDisplayName("Processes waiting for run time");
    oProcWaitSerie.setFieldx("numLine");
    oProcWaitSerie.setFieldy("procR");
    oProcWaitSerie.setForm("curve");

    oProcWaitSerie.setStrokeColor(10861646);
    oProcWaitSerie.setStrokeWeight(2);
    oProcessChartMetaData.getSeries().add(oProcWaitSerie);

    SerieMetaData oProcBSerie = new SerieMetaData();
    oProcBSerie.setType("line");
    oProcBSerie.setDisplayName("Processes in uninterruptible sleep");
    oProcBSerie.setFieldx("numLine");
    oProcBSerie.setFieldy("procB");
    oProcBSerie.setForm("curve");

    oProcBSerie.setStrokeColor(14976769);
    oProcBSerie.setStrokeWeight(2);
    oProcessChartMetaData.getSeries().add(oProcBSerie);

    return oProcessChartMetaData;
  }

  private static ChartMetaData getMemoryChart()
  {
    ChartMetaData oMemChartMetaData = new ChartMetaData();
    oMemChartMetaData.setId("memchart");
    oMemChartMetaData.setPanelTitle("Memory");
    oMemChartMetaData.setHorizAxisTitle("");
    oMemChartMetaData.setVertAxisTitle("Memory");
    oMemChartMetaData.setVertUnit("b");
    oMemChartMetaData.setDataTipMode("multiple");

    oMemChartMetaData.setVertAxisMinimum(0);

    oMemChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Free Memory : </b>\" + item[\"memFree\"]");

    SerieMetaData oFreeMemSerie = new SerieMetaData();
    oFreeMemSerie.setType("line");
    oFreeMemSerie.setDisplayName("Mem Free (-/+ buffer/cache)");
    oFreeMemSerie.setFieldx("numLine");
    oFreeMemSerie.setFieldy("memFree");
    oFreeMemSerie.setForm("curve");

    oFreeMemSerie.setStrokeColor(6737151);
    oFreeMemSerie.setStrokeWeight(2);
    oMemChartMetaData.getSeries().add(oFreeMemSerie);

    return oMemChartMetaData;
  }

  private static ChartMetaData getIOChart()
  {
    ChartMetaData oIOChartMetaData = new ChartMetaData();
    oIOChartMetaData.setId("iochart");
    oIOChartMetaData.setPanelTitle("IO/Blocs activity");
    oIOChartMetaData.setHorizAxisTitle("");
    oIOChartMetaData.setVertAxisTitle("Block/s");
    oIOChartMetaData.setVertUnit("o");
    oIOChartMetaData.setDataTipMode("multiple");

    oIOChartMetaData.setVertAxisMinimum(0);

    oIOChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Block In/s : </b>\" + item[\"ioIn\"] + \"\\n\"+ \"<b>Block Out/s : </b>\" + item[\"ioOut\"]");

    SerieMetaData oIOInSerie = new SerieMetaData();
    oIOInSerie.setType("line");
    oIOInSerie.setDisplayName("Block In/s");
    oIOInSerie.setFieldx("numLine");
    oIOInSerie.setFieldy("ioIn");
    oIOInSerie.setForm("curve");

    oIOInSerie.setStrokeColor(10861646);
    oIOInSerie.setStrokeWeight(2);
    oIOChartMetaData.getSeries().add(oIOInSerie);

    SerieMetaData oIOOutSerie = new SerieMetaData();
    oIOOutSerie.setType("line");
    oIOOutSerie.setDisplayName("Block Out/s");
    oIOOutSerie.setFieldx("numLine");
    oIOOutSerie.setFieldy("ioOut");
    oIOOutSerie.setForm("curve");

    oIOOutSerie.setStrokeColor(14976769);
    oIOOutSerie.setStrokeWeight(2);
    oIOChartMetaData.getSeries().add(oIOOutSerie);

    return oIOChartMetaData;
  }

  private static ChartMetaData getSwapChart()
  {
    ChartMetaData oSwapChartMetaData = new ChartMetaData();
    oSwapChartMetaData.setId("swapchart");
    oSwapChartMetaData.setPanelTitle("Swap");
    oSwapChartMetaData.setHorizAxisTitle("");
    oSwapChartMetaData.setVertAxisTitle("Swap Used");
    oSwapChartMetaData.setVertUnit("kb");
    oSwapChartMetaData.setDataTipMode("multiple");

    oSwapChartMetaData.setVertAxisMinimum(0);

    oSwapChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Swap Used : </b>\" + item[\"memSwap\"] + \"Kb\"");

    SerieMetaData oSwapUsedSerie = new SerieMetaData();
    oSwapUsedSerie.setType("line");
    oSwapUsedSerie.setDisplayName("Swap Used");
    oSwapUsedSerie.setFieldx("numLine");
    oSwapUsedSerie.setFieldy("memSwap");
    oSwapUsedSerie.setForm("curve");

    oSwapUsedSerie.setStrokeColor(14976769);
    oSwapUsedSerie.setStrokeWeight(2);
    oSwapChartMetaData.getSeries().add(oSwapUsedSerie);

    return oSwapChartMetaData;
  }

  private static ChartMetaData getPagingChart()
  {
    ChartMetaData oPagingMetaData = new ChartMetaData();
    oPagingMetaData.setId("pagingchart");
    oPagingMetaData.setPanelTitle("Paging Activity");
    oPagingMetaData.setHorizAxisTitle("");
    oPagingMetaData.setVertAxisTitle("Per second");
    oPagingMetaData.setDataTipMode("multiple");

    oPagingMetaData.setVertAxisMinimum(0);
    oPagingMetaData.setVertUnit("kb");

    oPagingMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Memory swapped from disk : </b>\" + item[\"swapIn\"] + \"kb/s\\n\"+ \"<b>Memory swapped to disk : </b>\" + item[\"swapOut\"] + \"kb/s\"");

    SerieMetaData oSwapInSerie = new SerieMetaData();
    oSwapInSerie.setType("line");
    oSwapInSerie.setDisplayName("Memory swapped from disk kb/s");
    oSwapInSerie.setFieldx("numLine");
    oSwapInSerie.setFieldy("swapIn");
    oSwapInSerie.setForm("curve");

    oSwapInSerie.setStrokeColor(10861646);
    oSwapInSerie.setStrokeWeight(2);
    oPagingMetaData.getSeries().add(oSwapInSerie);

    SerieMetaData oSwapOutSerie = new SerieMetaData();
    oSwapOutSerie.setType("line");
    oSwapOutSerie.setDisplayName("Memory swapped to disk kb/s");
    oSwapOutSerie.setFieldx("numLine");
    oSwapOutSerie.setFieldy("swapOut");
    oSwapOutSerie.setForm("curve");

    oSwapOutSerie.setStrokeColor(14976769);
    oSwapOutSerie.setStrokeWeight(2);
    oPagingMetaData.getSeries().add(oSwapOutSerie);

    return oPagingMetaData;
  }
}