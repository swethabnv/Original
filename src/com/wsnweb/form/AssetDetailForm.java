package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class AssetDetailForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long plantId;
	private long assetId;
	private String assetDate;
	private double amount;
	private long seq;
	
	private String cmd; 
	private String msg;
	public long getPlantId() {
		return plantId;
	}
	public void setPlantId(long plantId) {
		this.plantId = plantId;
	}
	public long getAssetId() {
		return assetId;
	}
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	public String getAssetDate() {
		return assetDate;
	}
	public void setAssetDate(String assetDate) {
		this.assetDate = assetDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
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
