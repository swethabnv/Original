package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class LandCheckPrepareSoil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1303263032897430415L;
	private long landCheckId;
	private long landCheckSoilId;
	private int seq;
	private Date checkDate;
	private String soilType;
	private String sourceWater;
	private String operate;
	private String operateOther;
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
	public long getLandCheckSoilId() {
		return landCheckSoilId;
	}
	public void setLandCheckSoilId(long landCheckSoilId) {
		this.landCheckSoilId = landCheckSoilId;
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
	public String getSoilType() {
		return soilType;
	}
	public void setSoilType(String soilType) {
		this.soilType = soilType;
	}
	public String getSourceWater() {
		return sourceWater;
	}
	public void setSourceWater(String sourceWater) {
		this.sourceWater = sourceWater;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getOperateOther() {
		return operateOther;
	}
	public void setOperateOther(String operateOther) {
		this.operateOther = operateOther;
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
