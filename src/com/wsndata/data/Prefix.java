package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

import com.dcs.dcswc.common.ICheckOnGrid;

public class Prefix implements Serializable, ICheckOnGrid{


	private static final long serialVersionUID = -3861240331471056926L;
	private String abbrPrefix;
	private String fullPrefix;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	
	private boolean checkBox;// added for prefixList
	
	public String getAbbrPrefix() {
		return abbrPrefix;
	}
	public void setAbbrPrefix(String abbrPrefix) {
		this.abbrPrefix = abbrPrefix;
	}
	public String getFullPrefix() {
		return fullPrefix;
	}
	public void setFullPrefix(String fullPrefix) {
		this.fullPrefix = fullPrefix;
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
	public boolean getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	

}
