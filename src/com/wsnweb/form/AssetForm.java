package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class AssetForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long assetId;
	private String assetName;
	private String cmd; 
	private String msg;
	
    
	public long getAssetId() {
		return assetId;
	}

	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
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
    
}
