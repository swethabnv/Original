package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class LandCheckPlant implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1303263032897430415L;
	private long landCheckId;
	private long landCheckPlantId;
	private int seq;
	private Date checkDate;
	private String plantMethod;
	private String breedTypeLevel;
	private long breedTypeId;
	private String sourceSeed;
	private Date sowDate;
	private Date throwDate;
	private double useRate;
	private String result;
	private String remark;
	private String checker;
	
	private LandCheck landCheck;

	public long getLandCheckId() {
		if (landCheck != null) {
			return landCheck.getLandCheckId();
		} else {
			return landCheckId;
		}
	}
	public void setLandCheckId(long landCheckId) {
		if (landCheck != null) {
			landCheck.setLandCheckId(landCheckId);
		} else {
			this.landCheckId = landCheckId;
		}
	}
	public long getLandCheckPlantId() {
		return landCheckPlantId;
	}
	public void setLandCheckPlantId(long landCheckPlantId) {
		this.landCheckPlantId = landCheckPlantId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getPlantMethod() {
		return plantMethod;
	}
	public void setPlantMethod(String plantMethod) {
		this.plantMethod = plantMethod;
	}
	public String getBreedTypeLevel() {
		return breedTypeLevel;
	}
	public void setBreedTypeLevel(String breedTypeLevel) {
		this.breedTypeLevel = breedTypeLevel;
	}
	public long getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}
	public String getSourceSeed() {
		return sourceSeed;
	}
	public void setSourceSeed(String sourceSeed) {
		this.sourceSeed = sourceSeed;
	}
	public Date getSowDate() {
		return sowDate;
	}
	public void setSowDate(Date sowDate) {
		this.sowDate = sowDate;
	}
	public Date getThrowDate() {
		return throwDate;
	}
	public void setThrowDate(Date throwDate) {
		this.throwDate = throwDate;
	}
	public double getUseRate() {
		return useRate;
	}
	public void setUseRate(double useRate) {
		this.useRate = useRate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public LandCheck getLandCheck() {
		return landCheck;
	}
	public void setLandCheck(LandCheck landCheck) {
		this.landCheck = landCheck;
	}
	
}
