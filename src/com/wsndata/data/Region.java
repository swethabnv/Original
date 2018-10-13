package com.wsndata.data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;

public class Region implements Serializable, ICheckOnGrid {
	
	private static final long serialVersionUID = 2505718617928909966L;
    private long regionNo;
    private String regionName;
    private Date lastUpdateDate;
    
	private String lastUpdateBy;
    
    private List<Province> province;
    
    private boolean checkBox;// added for regionList
    
    
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
	
	public List<Province> getProvince() {
		return province;
	}
	public void setProvince(List<Province> province) {
		this.province = province;
	}
	public long getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(long regionNo) {
		this.regionNo = regionNo;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
	public String getLinkImageDel() {
		return "images/btn-delete.png";
	}
}
