package org.michenux.vmstax;

public class VmstaxParseException extends Exception
{
  private static final long serialVersionUID = -1566365771542867414L;

  public VmstaxParseException(String p_sMessage)
  {
    super(p_sMessage);
  }

  public VmstaxParseException(String p_sMessage, Exception p_oException) {
    super(p_sMessage, p_oException);
  }
}