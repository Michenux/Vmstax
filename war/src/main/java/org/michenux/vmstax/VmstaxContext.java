package org.michenux.vmstax;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.utils.SystemProperty;

public class VmstaxContext {

	private List<String> messages = new ArrayList<String>();
	
	/**
	 * @param p_sMessage
	 */
	public void addMessage( String p_sMessage ) {
		messages.add(p_sMessage);
	}
	
	/**
	 * @return
	 */
	public String messageToString() {
		StringBuilder sMessages = new StringBuilder();
		for( String sMessage : messages ) {
			sMessages.append(sMessage);
		}
		
		return sMessages.toString();
	}
}
