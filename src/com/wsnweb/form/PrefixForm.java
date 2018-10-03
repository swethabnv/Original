package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.Prefix;

public class PrefixForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
    private String prevPrefix;
    private String prefix;
    private String prefixFull;
	private String cmd; 
	private String msg;

	public String getPrevPrefix() {
		return prevPrefix;
	}
	public void setPrevPrefix(String prevPrefix) {
		this.prevPrefix = prevPrefix;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getPrefixFull() {
		return prefixFull;
	}
	public void setPrefixFull(String prefixFull) {
		this.prefixFull = prefixFull;
	}
    
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void loadToBean(Object o) {
		Prefix pObj = (Prefix)o;
		pObj.setAbbrPrefix(this.prefix);
		pObj.setFullPrefix(this.prefixFull);
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			Prefix pObj = (Prefix)o;
			this.prefix = pObj.getAbbrPrefix();
			this.prefixFull = pObj.getFullPrefix();
		}
	}
    
}
