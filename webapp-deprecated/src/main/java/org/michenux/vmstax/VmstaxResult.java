package org.michenux.vmstax;

import java.util.List;

public class VmstaxResult {

	private String message ;
	
	private List<VmstaxData> vmstaxDatas ;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<VmstaxData> getVmstaxDatas() {
		return vmstaxDatas;
	}

	public void setVmstaxDatas(List<VmstaxData> vmstaxDatas) {
		this.vmstaxDatas = vmstaxDatas;
	}
}
