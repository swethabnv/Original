package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;


public class Cost implements Serializable, ICheckOnGrid {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6067686245083053702L;
	private long costId;
	private String costName;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private List<CostDetail> costDetail;
	
	private boolean checkBox;// added for costList

	
	public List<CostDetail> getCostDetail() {
		return costDetail;
	}
	public void setCostDetail(List<CostDetail> costDetail) {
		this.costDetail = costDetail;
	}
	public long getCostId() {
		return costId;
	}
	public void setCostId(long costId) {
		this.costId = costId;
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	

}
