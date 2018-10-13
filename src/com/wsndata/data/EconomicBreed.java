package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

import com.dcs.dcswc.common.ICheckOnGrid;

public class EconomicBreed implements Serializable, ICheckOnGrid{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long breedTypeId;
	private long regionNo;
	private long provinceNo;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	
	private Province province;
	private BreedType breedType;
	
	private String breedTypeName;// added for economicBreedList
	private String provinceName;// added for economicBreedList
	private String breedTypeDel;// added for economicBreedList

	private boolean checkBox;// added for economicBreedList
	
	public long getBreedTypeId() {
		if (breedType != null) {
			return breedType.getBreedTypeId();
		} else {
			return breedTypeId;
		}
	}
	public void setBreedTypeId(long breedTypeId) {
		if (breedType != null) {
			breedType.setBreedTypeId(breedTypeId);
		} else {
			this.breedTypeId = breedTypeId;
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
	public String getBreedTypeName() {
		if (breedType != null) {
			return breedType.getBreedTypeName();
		} else {
			return breedTypeName;
		}
	}
	public void setBreedTypeName(String breedTypeName) {
		if (breedType != null) {
			breedType.setBreedTypeName(breedTypeName);
		} else {
			this.breedTypeName = breedTypeName;
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
	public void setBreedTypeDel(String breedTypeDel) {
		this.breedTypeDel = breedTypeDel;
	}
	public String getBreedTypeDel() {
		return breedTypeDel;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public Province getProvince() {
		return province;
	}
	public void setBreedType(BreedType breedType) {
		this.breedType = breedType;
	}
	public BreedType getBreedType() {
		return breedType;
	}
	
}
