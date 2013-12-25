package org.michenux.vmstax;

import java.util.List;

public abstract interface VmstaxService {
	/**
	 * @param paramArrayOfByte
	 * @param paramString
	 * @param fileName
	 * @param p_bZip
	 * @return
	 * @throws Exception
	 */
	public VmstaxResult getVmstaxData(byte[] paramArrayOfByte,
			String paramString, String fileName, boolean p_bZip)
			throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	public abstract List<VmstaxFormat> getVmstaxFormats() throws Exception;
}