package org.michenux.vmstax.vmstat;

import org.michenux.vmstax.charts.LineMetric;

public class VmLineMetric extends LineMetric
{
  private int cpuUser;
  private int cpuSystem;
  private int cpuIdle;
  private int systemIn;
  private int systemCs;

  public int getCpuUser()
  {
    return this.cpuUser;
  }

  public void setCpuUser(int cpuUser)
  {
    this.cpuUser = cpuUser;
  }

  public int getCpuSystem()
  {
    return this.cpuSystem;
  }

  public void setCpuSystem(int cpuSystem)
  {
    this.cpuSystem = cpuSystem;
  }

  public int getCpuIdle()
  {
    return this.cpuIdle;
  }

  public void setCpuIdle(int cpuIdle)
  {
    this.cpuIdle = cpuIdle;
  }

  public int getSystemIn()
  {
    return this.systemIn;
  }

  public void setSystemIn(int systemIn)
  {
    this.systemIn = systemIn;
  }

  public int getSystemCs()
  {
    return this.systemCs;
  }

  public void setSystemCs(int sysemCs)
  {
    this.systemCs = sysemCs;
  }
}