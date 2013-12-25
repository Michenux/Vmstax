package org.michenux.vmstax;

public class VmstaxFormat {
	private String name;
	private String displayName;
	private String icon;

	public VmstaxFormat() {
	}

	public VmstaxFormat(String p_sName, String p_sDisplayName, String p_sIcon) {
		this.name = p_sName;
		this.displayName = p_sDisplayName;
		this.icon = p_sIcon ;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}