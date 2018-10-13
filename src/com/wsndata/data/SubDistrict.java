package com.wsndata.data;
import java.io.Serializable;
import java.util.Date;

import com.dcs.dcswc.common.ICheckOnGrid;


public class SubDistrict implements Serializable, ICheckOnGrid{

	
	private static final long serialVersionUID = 4424743919856279207L;
	private long regionNo;
	private long provinceNo;
    private long districtNo;
	private long subDistrictNo;
    private String thaiName;
    private String engName;
    private int postCode;
    private Date lastUpdateDate;
    private String lastUpdateBy;
    private District district;
    
    private boolean checkBox;// added for subDistrictList
	private String regionName;// added for subDistrictList
    private String provinceName;// added for subDistrictList
	private String districtName; // added for subDistrictList
	
	
	public String getRegionName() {
		if (district != null) {
			return district.getRegionName();
		} else {
			return regionName;
		}
	}
	public void setRegionName(String regionName) {
		if (district != null) {
			district.setRegionName(regionName);
		} else {
			this.regionName = regionName;
		}
	}
	public String getProvinceName() {
		if (district != null) {
			return district.getProvinceName();
		} else {
			return provinceName;
		}
	}
	public void setProvinceName(String provinceName) {
		if (district != null) {
			district.setProvinceName(provinceName);
		} else {
			this.provinceName = provinceName;
		}
	}
	public String getDistrictName() {
		if (district != null) {
			return district.getThaiName();
		} else {
			return districtName;
		}
	}
	public void setDistrictName(String districtName) {
		if (district != null) {
			district.setThaiName(districtName);
		} else {
			this.districtName = districtName;
		}
	}
	public long getRegionNo() {
		if (district != null) {
			return district.getRegionNo();
		} else {
			return regionNo;
		}
	}
	public void setRegionNo(long regionNo) {
		if (district != null) {
			district.setRegionNo(regionNo);
		} else {
			this.regionNo = regionNo;
		}
	}
	public long getProvinceNo() {
		if (district != null) {
			return district.getProvinceNo();
		} else {
			return provinceNo;
		}
	}
	public void setProvinceNo(long provinceNo) {
		if (district != null) {
			district.setProvinceNo(provinceNo);
		} else {
			this.provinceNo = provinceNo;
		}
	}
	public long getDistrictNo() {
		if (district != null) {
			return district.getDistrictNo();
		} else {
			return districtNo;
		}
	}
	public void setDistrictNo(long districtNo) {
		if (district != null) {
			district.setDistrictNo(districtNo);
		} else {
			this.districtNo = districtNo;
		}
	}
	public long getSubDistrictNo() {
		return subDistrictNo;
	}
	public void setSubDistrictNo(long subDistrictNo) {
		this.subDistrictNo = subDistrictNo;
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
	public int getPostCode() {
		return postCode;
	}
	public void setPostCode(int postCode) {
		this.postCode = postCode;
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
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
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

    
}
