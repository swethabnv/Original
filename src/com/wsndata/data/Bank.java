package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.dcs.dcswc.common.ICheckOnGrid;

public class Bank implements Serializable, ICheckOnGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6445167717864972717L;
	private long bankId;
	private String bankName;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private Date createDate;
	private String createBy;
	private Set<Branch> bankBranch;
	private boolean checkBox;// added for breedTypeList
	
	
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Set<Branch> getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(Set<Branch> bankBranch) {
		this.bankBranch = bankBranch;
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
