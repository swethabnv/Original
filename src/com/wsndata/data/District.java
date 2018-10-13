package com.wsndata.data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;

public class District implements Serializable, ICheckOnGrid {
	
	
	private static final long serialVersionUID = -2800175867564248382L;
	
	private long regionNo;
	private long provinceNo;
	private long districtNo;
    private String thaiName;
    private String engName;
    private Date lastUpdateDate;
    private String lastUpdateBy;
    private List<SubDistrict> subDistrict;
    private Province province;
    
    private boolean checkBox;// added for districtList
    private String provinceName;// added for districtList
	private String regionName;// added for districtList
    
	
	public String getRegionName() {
		if (province != null) {
			return province.getRegionName();
		} else {
			return regionName;
		}
	}
	public void setRegionName(String regionName) {
		if (province != null) {
			province.setRegionName(regionName);
		} else {
			this.regionName = regionName;
		}
	}
	public String getProvinceName() {
		if (province != null) {
			return province.getThaiName();
		} else {
			return provinceName;
		}
	}
	public void setProvinceName(String provinceName) {
		if (province != null) {
			province.setThaiName(provinceName);
		} else {
			this.provinceName = provinceName;
		}
	}
	public long getRegionNo() {
		if (province != null) {
			return province.getRegionNo();
		} else {
			return regionNo;
		}
	}
	public void setRegionNo(long regionNo) {
		if (province != null) {
			province.setRegionNo(regionNo);
		} else {
			this.regionNo = regionNo;
		}
	}
	public long getProvinceNo() {
		if (province != null) {
			return province.getProvinceNo();
		} else {
			return provinceNo;
		}
	}
	public void setProvinceNo(long provinceNo) {
		if (province != null) {
			province.setProvinceNo(provinceNo);
		} else {
			this.provinceNo = provinceNo;
		}
	}
	public long getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(long districtNo) {
		this.districtNo = districtNo;
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
	public List<SubDistrict> getSubDistrict() {
		return subDistrict;
	}
	public void setSubDistrict(List<SubDistrict> subDistrict) {
		this.subDistrict = subDistrict;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
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
