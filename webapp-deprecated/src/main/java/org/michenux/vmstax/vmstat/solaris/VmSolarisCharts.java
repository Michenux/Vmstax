package org.michenux.vmstax.vmstat.solaris;

import java.util.ArrayList;
import java.util.List;
import org.michenux.vmstax.charts.ChartMetaData;
import org.michenux.vmstax.charts.SerieMetaData;

public class VmSolarisCharts
{
  public static List<ChartMetaData> SOLARIS_CHARTS = new ArrayList<ChartMetaData>();
  
  static {
	  SOLARIS_CHARTS.add(getCpuChart());
	  SOLARIS_CHARTS.add(getProcessChart());
	  SOLARIS_CHARTS.add(getMemoryChart());
	  SOLARIS_CHARTS.add(getFaultsChart());
	  SOLARIS_CHARTS.add(getDisk1and2Chart());
	  SOLARIS_CHARTS.add(getDisk3and4Chart());
	  SOLARIS_CHARTS.add(getSwapChart());
	  SOLARIS_CHARTS.add(getPagingChart());
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
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Total CPU Used : </b>\" + item[\"cpuSystem\"] + \"%\\n\"+ \"<b>whose : </b>\\n\"+ \"   user: \" + item[\"cpuUser\"] + \"%\\n\"+ \"   system: \" + ( item[\"cpuSystem\"] - item[\"cpuUser\"] ) + \"%\\n\"+ \"   idle: \" + ( 100 - item[\"cpuSystem\"] ) + \"%\\n\"");

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

    SerieMetaData oProcWSerie = new SerieMetaData();
    oProcWSerie.setType("line");
    oProcWSerie.setDisplayName("Idle procs which have been swapped");
    oProcWSerie.setFieldx("numLine");
    oProcWSerie.setFieldy("procW");
    oProcWSerie.setForm("curve");

    oProcWSerie.setStrokeColor(6737151);
    oProcWSerie.setStrokeWeight(2);
    oProcessChartMetaData.getSeries().add(oProcWSerie);

    return oProcessChartMetaData;
  }

  private static ChartMetaData getMemoryChart()
  {
    ChartMetaData oMemChartMetaData = new ChartMetaData();
    oMemChartMetaData.setId("memchart");
    oMemChartMetaData.setPanelTitle("Memory");
    oMemChartMetaData.setHorizAxisTitle("");
    oMemChartMetaData.setVertAxisTitle("Memory");
    oMemChartMetaData.setVertUnit("kb");
    oMemChartMetaData.setDataTipMode("multiple");

    oMemChartMetaData.setVertAxisMinimum(0);

    oMemChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Free Memory : </b>\" + item[\"memFree\"] +\" Kb\"");

    SerieMetaData oFreeMemSerie = new SerieMetaData();
    oFreeMemSerie.setType("line");
    oFreeMemSerie.setDisplayName("Mem Free");
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
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Interrupts /s : </b>\" + item[\"systemIn\"] + \"\\n\"+ \"<b>System calls /s : </b>\" + item[\"faultSys\"] + \"\\n\"+ \"<b>Context switches /s : </b>\" + item[\"systemCs\"] + \"\\n\"");

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
    oFaultSysSerie.setFieldy("faultSys");
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

  private static ChartMetaData getDisk1and2Chart()
  {
    ChartMetaData oDisk12MetaData = new ChartMetaData();
    oDisk12MetaData.setId("disk12chart");
    oDisk12MetaData.setPanelTitle("Disk 1 & 2 Activity");
    oDisk12MetaData.setHorizAxisTitle("");
    oDisk12MetaData.setVertAxisTitle("# per IO/s");
    oDisk12MetaData.setDataTipMode("multiple");

    oDisk12MetaData.setVertAxisMinimum(0);
    oDisk12MetaData.setVertUnit("o");

    oDisk12MetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Disk 1 : </b>\" + item[\"disk1\"] + \"/s\\n\"+ \"<b>Disk 2 : </b>\" + item[\"disk2\"] + \"/s\"");

    SerieMetaData oDisk1Serie = new SerieMetaData();
    oDisk1Serie.setType("line");
    oDisk1Serie.setDisplayName("Disk 1");
    oDisk1Serie.setFieldx("numLine");
    oDisk1Serie.setFieldy("disk1");
    oDisk1Serie.setForm("curve");

    oDisk1Serie.setStrokeColor(10861646);
    oDisk1Serie.setStrokeWeight(2);
    oDisk12MetaData.getSeries().add(oDisk1Serie);

    SerieMetaData oDisk2Serie = new SerieMetaData();
    oDisk2Serie.setType("line");
    oDisk2Serie.setDisplayName("Disk 2");
    oDisk2Serie.setFieldx("numLine");
    oDisk2Serie.setFieldy("disk2");
    oDisk2Serie.setForm("curve");

    oDisk2Serie.setStrokeColor(14976769);
    oDisk2Serie.setStrokeWeight(2);
    oDisk12MetaData.getSeries().add(oDisk2Serie);

    return oDisk12MetaData;
  }

  private static ChartMetaData getDisk3and4Chart()
  {
    ChartMetaData oDisk34MetaData = new ChartMetaData();
    oDisk34MetaData.setId("disk34chart");
    oDisk34MetaData.setPanelTitle("Disk 3 & 4 Activity");
    oDisk34MetaData.setHorizAxisTitle("");
    oDisk34MetaData.setVertAxisTitle("# per IO/s");
    oDisk34MetaData.setDataTipMode("multiple");

    oDisk34MetaData.setVertAxisMinimum(0);
    oDisk34MetaData.setVertUnit("o");

    oDisk34MetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Disk 3 : </b>\" + item[\"disk3\"] + \"/s\\n\"+ \"<b>Disk 4 : </b>\" + item[\"disk4\"] + \"/s\"");

    SerieMetaData oDisk3Serie = new SerieMetaData();
    oDisk3Serie.setType("line");
    oDisk3Serie.setDisplayName("Disk 3");
    oDisk3Serie.setFieldx("numLine");
    oDisk3Serie.setFieldy("disk3");
    oDisk3Serie.setForm("curve");

    oDisk3Serie.setStrokeColor(10861646);
    oDisk3Serie.setStrokeWeight(2);
    oDisk34MetaData.getSeries().add(oDisk3Serie);

    SerieMetaData oDisk4Serie = new SerieMetaData();
    oDisk4Serie.setType("line");
    oDisk4Serie.setDisplayName("Disk 4");
    oDisk4Serie.setFieldx("numLine");
    oDisk4Serie.setFieldy("disk4");
    oDisk4Serie.setForm("curve");

    oDisk4Serie.setStrokeColor(14976769);
    oDisk4Serie.setStrokeWeight(2);
    oDisk34MetaData.getSeries().add(oDisk4Serie);

    return oDisk34MetaData;
  }

  private static ChartMetaData getSwapChart()
  {
    ChartMetaData oSwapChartMetaData = new ChartMetaData();
    oSwapChartMetaData.setId("swapchart");
    oSwapChartMetaData.setPanelTitle("Swap");
    oSwapChartMetaData.setHorizAxisTitle("");
    oSwapChartMetaData.setVertAxisTitle("Available swap space");
    oSwapChartMetaData.setVertUnit("kb");
    oSwapChartMetaData.setDataTipMode("multiple");

    oSwapChartMetaData.setVertAxisMinimum(0);

    oSwapChartMetaData.setDataTipTemplate(
      "\"<b>Time : </b>\" + item[\"strTime\"] + \"\\n\"+ \"<b>Available space : </b>\" + item[\"memSwap\"] + \"Kb\"");

    SerieMetaData oAvailableSwapSerie = new SerieMetaData();
    oAvailableSwapSerie.setType("line");
    oAvailableSwapSerie.setDisplayName("Available swap space");
    oAvailableSwapSerie.setFieldx("numLine");
    oAvailableSwapSerie.setFieldy("memSwap");
    oAvailableSwapSerie.setForm("curve");

    oAvailableSwapSerie.setStrokeColor(14976769);
    oAvailableSwapSerie.setStrokeWeight(2);
    oSwapChartMetaData.getSeries().add(oAvailableSwapSerie);

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