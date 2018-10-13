package com.wsndata.data;

import java.io.Serializable;

public class ProductionForecast implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3122937247948114395L;
	
	private int pfYear;
	private long breedTypeId;
	private long breedGroupId;
	private long regionNo;
	private long provinceNo;
	private double irrigationInAmount;
	private double irrigationOutAmount;
	
	public int getPfYear() {
		return pfYear;
	}
	public void setPfYear(int pfYear) {
		this.pfYear = pfYear;
	}
	public long getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}
	public long getBreedGroupId() {
		return breedGroupId;
	}
	public void setBreedGroupId(long breedGroupId) {
		this.breedGroupId = breedGroupId;
	}
	public long getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(long regionNo) {
		this.regionNo = regionNo;
	}
	public long getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(long provinceNo) {
		this.provinceNo = provinceNo;
	}
	public double getIrrigationInAmount() {
		return irrigationInAmount;
	}
	public void setIrrigationInAmount(double irrigationInAmount) {
		this.irrigationInAmount = irrigationInAmount;
	}
	public double getIrrigationOutAmount() {
		return irrigationOutAmount;
	}
	public void setIrrigationOutAmount(double irrigationOutAmount) {
		this.irrigationOutAmount = irrigationOutAmount;
	}

	
}
