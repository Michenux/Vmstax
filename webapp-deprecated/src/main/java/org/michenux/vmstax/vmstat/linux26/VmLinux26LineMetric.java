package org.michenux.vmstax.vmstat.linux26;

import org.michenux.vmstax.vmstat.VmLineMetric;

public class VmLinux26LineMetric extends VmLineMetric
{
  private int procR;
  private int procB;
  private long memSwap;
  private long memFree;
  private long memBuff;
  private long memCache;
  private int swapIn;
  private int swapOut;
  private int ioIn;
  private int ioOut;
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

  public long getMemBuff()
  {
    return this.memBuff;
  }

  public void setMemBuff(long memBuff)
  {
    this.memBuff = memBuff;
  }

  public long getMemCache()
  {
    return this.memCache;
  }

  public void setMemCache(long memCache)
  {
    this.memCache = memCache;
  }

  public int getIoIn()
  {
    return this.ioIn;
  }

  public void setIoIn(int ioIn)
  {
    this.ioIn = ioIn;
  }

  public int getIoOut()
  {
    return this.ioOut;
  }

  public void setIoOut(int ioOut)
  {
    this.ioOut = ioOut;
  }

  public int getCpuIOWaiting()
  {
    return this.cpuIOWaiting;
  }

  public void setCpuIOWaiting(int cpuIOWaiting)
  {
    this.cpuIOWaiting = cpuIOWaiting;
  }

  public int getSwapIn()
  {
    return this.swapIn;
  }

  public void setSwapIn(int swapIn)
  {
    this.swapIn = swapIn;
  }

  public int getSwapOut()
  {
    return this.swapOut;
  }

  public void setSwapOut(int swapOut)
  {
    this.swapOut = swapOut;
  }
}