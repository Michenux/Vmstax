package org.michenux.vmstax.vmstat.aix;

import java.util.ArrayList;
import java.util.List;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.SerieMetaData;

public class VmAIXCharts
{
  public static List<ChartMetaData> AIX_CHARTS = new ArrayList<ChartMetaData>();
  
  static {
	  AIX_CHARTS.add(getCpuChart());
	  AIX_CHARTS.add(getProcessChart());
	  AIX_CHARTS.add(getAvmChart());
	  AIX_CHARTS.add(getFreeListChart());
	  AIX_CHARTS.add(getFaultsChart());
	  AIX_CHARTS.add(getPagingChart());
	  
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
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Total CPU Used : </b>\" + item[\"cpuIOWaiting\"] + \"%\\n\"+ \"<b>whose : </b>\\n\"+ \"   user: \" + item[\"cpuUser\"] + \"%\\n\"+ \"   system: \" + ( item[\"cpuSystem\"] - item[\"cpuUser\"] ) + \"%\\n\"+ \"   wait: \" + ( item[\"cpuIOWaiting\"] - item[\"cpuSystem\"] ) + \"%\\n\"+ \"   idle: \" + ( 100 - item[\"cpuIOWaiting\"] ) + \"%\\n\"");

    SerieMetaData oCpuWaitSerie = new SerieMetaData();
    oCpuWaitSerie.setType("area");
    oCpuWaitSerie.setDisplayName("Cpu Wait");
    oCpuWaitSerie.setFieldx("numLine");
    oCpuWaitSerie.setFieldy("cpuIOWaiting");
    oCpuWaitSerie.setMinField("cpuSystem");
    oCpuWaitSerie.setForm("curve");
    oCpuWaitSerie.setSolidColorColor(6737151);
    oCpuWaitSerie.setSolidColorAlpha(0.5);
    oCpuWaitSerie.setStrokeColor(6737151);
    oCpuWaitSerie.setStrokeWeight(1);
    oCpuChartMetaData.getSeries().add(oCpuWaitSerie);

    SerieMetaData oCpuSystemSerie = new SerieMetaData();
    oCpuSystemSerie.setType("area");
    oCpuSystemSerie.setDisplayName("Cpu System");
    oCpuSystemSerie.setFieldx("numLine");
    oCpuSystemSerie.setFieldy("cpuSystem");
    oCpuSystemSerie.setMinField("cpuUser");
    oCpuSystemSerie.setForm("curve");
    oCpuSystemSerie.setSolidColorColor(14976769);
    oCpuSystemSerie.setSolidColorAlpha(0.5);
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
    oCpuUserSerie.setSolidColorAlpha(0.4);
    oCpuUserSerie.setStrokeColor(10861646);
    oCpuUserSerie.setStrokeWeight(1);
    oCpuChartMetaData.getSeries().add(oCpuUserSerie);

    return oCpuChartMetaData;
  }

  private static ChartMetaData getProcessChart()
  {
    ChartMetaData oProcessChartMetaData = new ChartMetaData();
    oProcessChartMetaData.setId("processchart");
    oProcessChartMetaData.setPanelTitle("Processes");
    oProcessChartMetaData.setHorizAxisTitle("");
    oProcessChartMetaData.setVertAxisTitle("# of processes");
    oProcessChartMetaData.setDataTipMode("single");

    oProcessChartMetaData.setVertAxisMinimum(0);
    oProcessChartMetaData.setVertAxisInterval(1);
    oProcessChartMetaData.setVertAxisMinorInterval(1);
    oProcessChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Procs in run queue : </b>\" + item[\"procR\"] + \"\\n\"+ \"<b>Procs that are waiting for resources : </b>\" + item[\"procB\"] + \"\\n\"+ \"<b>Idle procs which have been swapped : </b>\" + item[\"procW\"]");

    SerieMetaData oProcRSerie = new SerieMetaData();
    oProcRSerie.setType("line");
    oProcRSerie.setDisplayName("In run queue");
    oProcRSerie.setFieldx("numLine");
    oProcRSerie.setFieldy("procR");
    oProcRSerie.setForm("curve");

    oProcRSerie.setStrokeColor(10861646);
    oProcRSerie.setStrokeWeight(2);
    oProcessChartMetaData.getSeries().add(oProcRSerie);

    SerieMetaData oProcBSerie = new SerieMetaData();
    oProcBSerie.setType("line");
    oProcBSerie.setDisplayName("Waiting for res");
    oProcBSerie.setFieldx("numLine");
    oProcBSerie.setFieldy("procB");
    oProcBSerie.setForm("curve");

    oProcBSerie.setStrokeColor(14976769);
    oProcBSerie.setStrokeWeight(2);
    oProcessChartMetaData.getSeries().add(oProcBSerie);

    return oProcessChartMetaData;
  }

  private static ChartMetaData getAvmChart()
  {
    ChartMetaData oMemChartMetaData = new ChartMetaData();
    oMemChartMetaData.setId("avmchart");
    oMemChartMetaData.setPanelTitle("AIX Virtual Memory");
    oMemChartMetaData.setHorizAxisTitle("");
    oMemChartMetaData.setVertAxisTitle("Virtual Memory");
    oMemChartMetaData.setVertUnit("kb");
    oMemChartMetaData.setDataTipMode("single");

    oMemChartMetaData.setVertAxisMinimum(0);

    oMemChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>AIX Virtual Memory : </b>\" + item[\"memAvm\"] +\" kbytes\"");

    SerieMetaData oFreeMemSerie = new SerieMetaData();
    oFreeMemSerie.setType("line");
    oFreeMemSerie.setDisplayName("AIX Virtual Memory");
    oFreeMemSerie.setFieldx("numLine");
    oFreeMemSerie.setFieldy("memAvm");
    oFreeMemSerie.setForm("curve");

    oFreeMemSerie.setStrokeColor(6737151);
    oFreeMemSerie.setStrokeWeight(2);
    oMemChartMetaData.getSeries().add(oFreeMemSerie);

    return oMemChartMetaData;
  }

  private static ChartMetaData getFreeListChart()
  {
    ChartMetaData oMemChartMetaData = new ChartMetaData();
    oMemChartMetaData.setId("freelistchart");
    oMemChartMetaData.setPanelTitle("Free List");
    oMemChartMetaData.setHorizAxisTitle("");
    oMemChartMetaData.setVertAxisTitle("Free List");
    oMemChartMetaData.setVertUnit("kb");
    oMemChartMetaData.setDataTipMode("single");

    oMemChartMetaData.setVertAxisMinimum(0);

    oMemChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Free List : </b>\" + item[\"memFree\"] +\" kbytes\"");

    SerieMetaData oFreeMemSerie = new SerieMetaData();
    oFreeMemSerie.setType("line");
    oFreeMemSerie.setDisplayName("Free List");
    oFreeMemSerie.setFieldx("numLine");
    oFreeMemSerie.setFieldy("memFree");
    oFreeMemSerie.setForm("curve");

    oFreeMemSerie.setStrokeColor(6737151);
    oFreeMemSerie.setStrokeWeight(2);
    oMemChartMetaData.getSeries().add(oFreeMemSerie);

    return oMemChartMetaData;
  }

  private static ChartMetaData getFaultsChart()
  {
    ChartMetaData oFaultsChartMetaData = new ChartMetaData();
    oFaultsChartMetaData.setId("faultchart");
    oFaultsChartMetaData.setPanelTitle("Faults");
    oFaultsChartMetaData.setHorizAxisTitle("");
    oFaultsChartMetaData.setVertAxisTitle("Per second");
    oFaultsChartMetaData.setVertUnit("o");
    oFaultsChartMetaData.setDataTipMode("single");

    oFaultsChartMetaData.setVertAxisMinimum(0);

    oFaultsChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Interrupts /s : </b>\" + item[\"systemIn\"] + \"\\n\"+ \"<b>System calls /s : </b>\" + item[\"systemCall\"] + \"\\n\"+ \"<b>Context switches /s : </b>\" + item[\"systemCs\"] + \"\\n\"");

    SerieMetaData oFaultInSerie = new SerieMetaData();
    oFaultInSerie.setType("line");
    oFaultInSerie.setDisplayName("Interrupts");
    oFaultInSerie.setFieldx("numLine");
    oFaultInSerie.setFieldy("systemIn");
    oFaultInSerie.setForm("curve");

    oFaultInSerie.setStrokeColor(10861646);
    oFaultInSerie.setStrokeWeight(2);
    oFaultsChartMetaData.getSeries().add(oFaultInSerie);

    SerieMetaData oFaultSysSerie = new SerieMetaData();
    oFaultSysSerie.setType("line");
    oFaultSysSerie.setDisplayName("System calls");
    oFaultSysSerie.setFieldx("numLine");
    oFaultSysSerie.setFieldy("systemCall");
    oFaultSysSerie.setForm("curve");

    oFaultSysSerie.setStrokeColor(14976769);
    oFaultSysSerie.setStrokeWeight(2);
    oFaultsChartMetaData.getSeries().add(oFaultSysSerie);

    SerieMetaData oFaultCsSerie = new SerieMetaData();
    oFaultCsSerie.setType("line");
    oFaultCsSerie.setDisplayName("Context switches");
    oFaultCsSerie.setFieldx("numLine");
    oFaultCsSerie.setFieldy("systemCs");
    oFaultCsSerie.setForm("curve");

    oFaultCsSerie.setStrokeColor(6737151);
    oFaultCsSerie.setStrokeWeight(2);
    oFaultsChartMetaData.getSeries().add(oFaultCsSerie);

    return oFaultsChartMetaData;
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
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Memory swapped from disk : </b>\" + item[\"pagePi\"] + \"kb/s\\n\"+ \"<b>Memory swapped to disk : </b>\" + item[\"pagePo\"] + \"kb/s\"");

    SerieMetaData oSwapInSerie = new SerieMetaData();
    oSwapInSerie.setType("line");
    oSwapInSerie.setDisplayName("Memory swapped from disk kb/s");
    oSwapInSerie.setFieldx("numLine");
    oSwapInSerie.setFieldy("pagePi");
    oSwapInSerie.setForm("curve");

    oSwapInSerie.setStrokeColor(10861646);
    oSwapInSerie.setStrokeWeight(2);
    oPagingMetaData.getSeries().add(oSwapInSerie);

    SerieMetaData oSwapOutSerie = new SerieMetaData();
    oSwapOutSerie.setType("line");
    oSwapOutSerie.setDisplayName("Memory swapped to disk kb/s");
    oSwapOutSerie.setFieldx("numLine");
    oSwapOutSerie.setFieldy("pagePo");
    oSwapOutSerie.setForm("curve");

    oSwapOutSerie.setStrokeColor(14976769);
    oSwapOutSerie.setStrokeWeight(2);
    oPagingMetaData.getSeries().add(oSwapOutSerie);

    return oPagingMetaData;
  }
}