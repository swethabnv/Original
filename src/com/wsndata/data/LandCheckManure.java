package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class LandCheckManure implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1303263032897430415L;
	private long landCheckId;
	private long landCheckManureId;
	private int seq;
	private Date checkDate;
	private long manureTypeId;
	private String manureOther;
	private String formula;
	private Date buyDate;
	private String sourceBuy;
	private String manureStatus;
	private String manureName;
	private double useRate;
	private double totalUse;
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
	public long getLandCheckManureId() {
		return landCheckManureId;
	}
	public void setLandCheckManureId(long landCheckManureId) {
		this.landCheckManureId = landCheckManureId;
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
	public long getManureTypeId() {
		return manureTypeId;
	}
	public void setManureTypeId(long manureTypeId) {
		this.manureTypeId = manureTypeId;
	}
	public String getManureOther() {
		return manureOther;
	}
	public void setManureOther(String manureOther) {
		this.manureOther = manureOther;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public String getSourceBuy() {
		return sourceBuy;
	}
	public void setSourceBuy(String sourceBuy) {
		this.sourceBuy = sourceBuy;
	}
	public String getManureStatus() {
		return manureStatus;
	}
	public void setManureStatus(String manureStatus) {
		this.manureStatus = manureStatus;
	}
	public String getManureName() {
		return manureName;
	}
	public void setManureName(String manureName) {
		this.manureName = manureName;
	}
	public double getUseRate() {
		return useRate;
	}
	public void setUseRate(double useRate) {
		this.useRate = useRate;
	}
	public double getTotalUse() {
		return totalUse;
	}
	public void setTotalUse(double totalUse) {
		this.totalUse = totalUse;
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
