package org.michenux.vmstax.vmstat.aix;

import org.michenux.vmstax.vmstat.VmLineMetric;

public class VmAIXLineMetric extends VmLineMetric
{
  private int procR;
  private int procB;
  private long memAvm;
  private long memFree;
  private int pageRe;
  private int pagePi;
  private int pagePo;
  private int pageFr;
  private int pageSr;
  private int pageCy;
  private int systemCall;
  private int cpuIOWaiting;

  public int getProcR()
  {
    return this.procR;
  }

  public void setProcR(int procR)
  {
    this.procR = procR;
  }

  public int getProcB()
  {
    return this.procB;
  }

  public void setProcB(int procB)
  {
    this.procB = procB;
  }

  public long getMemAvm()
  {
    return this.memAvm;
  }

  public void setMemAvm(long memAvm)
  {
    this.memAvm = memAvm;
  }

  public long getMemFree()
  {
    return this.memFree;
  }

  public void setMemFree(long memFree)
  {
    this.memFree = memFree;
  }

  public int getPageRe()
  {
    return this.pageRe;
  }

  public void setPageRe(int pageRe)
  {
    this.pageRe = pageRe;
  }

  public int getPagePi()
  {
    return this.pagePi;
  }

  public void setPagePi(int pagePi)
  {
    this.pagePi = pagePi;
  }

  public int getPagePo()
  {
    return this.pagePo;
  }

  public void setPagePo(int pagePo)
  {
    this.pagePo = pagePo;
  }

  public int getPageFr()
  {
    return this.pageFr;
  }

  public void setPageFr(int pageFr)
  {
    this.pageFr = pageFr;
  }

  public int getPageSr()
  {
    return this.pageSr;
  }

  public void setPageSr(int pageSr)
  {
    this.pageSr = pageSr;
  }

  public int getPageCy()
  {
    return this.pageCy;
  }

  public void setPageCy(int pageCy)
  {
    this.pageCy = pageCy;
  }

  public int getSystemCall()
  {
    return this.systemCall;
  }

  public void setSystemCall(int systemCall)
  {
    this.systemCall = systemCall;
  }

  public int getCpuIOWaiting()
  {
    return this.cpuIOWaiting;
  }

  public void setCpuIOWaiting(int cpuIOWaiting)
  {
    this.cpuIOWaiting = cpuIOWaiting;
  }
}