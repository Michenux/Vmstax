package org.michenux.vmstax.vmstat.solaris;

import org.michenux.vmstax.vmstat.VmLineMetric;

public class VmSolarisLineMetric extends VmLineMetric
{
  private int procR;
  private int procB;
  private int procW;
  private long memSwap;
  private long memFree;
  private int pageRe;
  private int pageMf;
  private int pagePi;
  private int pagePo;
  private int pageFr;
  private int pageDe;
  private int pageSr;
  private int disk1;
  private int disk2;
  private int disk3;
  private int disk4;
  private int faultSys;

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

  public int getProcW()
  {
    return this.procW;
  }

  public void setProcW(int procW)
  {
    this.procW = procW;
  }

  public long getMemSwap()
  {
    return this.memSwap;
  }

  public void setMemSwap(long memSwap)
  {
    this.memSwap = memSwap;
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

  public int getPageMf()
  {
    return this.pageMf;
  }

  public void setPageMf(int pageMf)
  {
    this.pageMf = pageMf;
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

  public int getPageDe()
  {
    return this.pageDe;
  }

  public void setPageDe(int pageDe)
  {
    this.pageDe = pageDe;
  }

  public int getPageSr()
  {
    return this.pageSr;
  }

  public void setPageSr(int pageSr)
  {
    this.pageSr = pageSr;
  }

  public int getDisk1()
  {
    return this.disk1;
  }

  public void setDisk1(int disk1)
  {
    this.disk1 = disk1;
  }

  public int getDisk2()
  {
    return this.disk2;
  }

  public void setDisk2(int disk2)
  {
    this.disk2 = disk2;
  }

  public int getDisk3()
  {
    return this.disk3;
  }

  public void setDisk3(int disk3)
  {
    this.disk3 = disk3;
  }

  public int getDisk4()
  {
    return this.disk4;
  }

  public void setDisk4(int disk4)
  {
    this.disk4 = disk4;
  }

  public int getFaultSys()
  {
    return this.faultSys;
  }

  public void setFaultSys(int faultSys)
  {
    this.faultSys = faultSys;
  }
}