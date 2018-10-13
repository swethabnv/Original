package com.wsndata.data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;

public class Province implements Serializable, ICheckOnGrid{

	/**
	 * 
	 */
	private static final long serialVersionUID = -166596086169030693L;
	private long regionNo;
	private long provinceNo;
	private String thaiName;
	private String engName;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private Region region;
	private List<District> district;
	
	private boolean checkBox;// added for provinceList
	private String regionName;// added for provinceList
	
	
	
	public String getRegionName() {
		if (region != null) {
			return region.getRegionName();
		} else {
			return regionName;
		}
	}
	public void setRegionName(String regionName) {
		if (region != null) {
			region.setRegionName(regionName);
		} else {
			this.regionName = regionName;
		}
	}
	public long getRegionNo() {
		if (region != null) {
			return region.getRegionNo();
		} else {
			return regionNo;
		}
	}
	public void setRegionNo(long regionNo) {
		if (region != null) {
			region.setRegionNo(regionNo);
		} else {
			this.regionNo = regionNo;
		}
	}
	public long getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(long provinceNo) {
		this.provinceNo = provinceNo;
	}
	public String getThaiName() {
		return thaiName;
	}
	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
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
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public List<District> getDistrict() {
		return district;
	}
	public void setDistrict(List<District> district) {
		this.district = district;
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
