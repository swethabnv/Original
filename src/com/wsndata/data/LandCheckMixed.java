package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class LandCheckMixed implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1303263032897430415L;
	private long landCheckId;
	private long landCheckMixedId;
	private int seq;
	private Date checkDate;
	private long mixedBreedTypeId;
	private long childId;
	private long eliminateMixedBreedId;
	private String eliminateOther;
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
	public long getLandCheckMixedId() {
		return landCheckMixedId;
	}
	public void setLandCheckMixedId(long landCheckMixedId) {
		this.landCheckMixedId = landCheckMixedId;
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
	public long getMixedBreedTypeId() {
		return mixedBreedTypeId;
	}
	public void setMixedBreedTypeId(long mixedBreedTypeId) {
		this.mixedBreedTypeId = mixedBreedTypeId;
	}
	public long getChildId() {
		return childId;
	}
	public void setChildId(long childId) {
		this.childId = childId;
	}
	public long getEliminateMixedBreedId() {
		return eliminateMixedBreedId;
	}
	public void setEliminateMixedBreedId(long eliminateMixedBreedId) {
		this.eliminateMixedBreedId = eliminateMixedBreedId;
	}
	public String getEliminateOther() {
		return eliminateOther;
	}
	public void setEliminateOther(String eliminateOther) {
		this.eliminateOther = eliminateOther;
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
