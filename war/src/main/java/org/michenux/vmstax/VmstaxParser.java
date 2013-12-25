package org.michenux.vmstax;

import java.util.List;

public interface VmstaxParser {
	/**
	 * @param paramList
	 * @return
	 * @throws VmstaxParseException
	 */
	public List<VmstaxData> parse(List<String> paramList, VmstaxContext p_oVmstaxContext) throws VmstaxParseException;

	/**
	 * @return
	 */
	public String getName();

	/**
	 * @return
	 */
	public String getDisplayName();

	/**
	 * @return
	 */
	public String getIcon();
}