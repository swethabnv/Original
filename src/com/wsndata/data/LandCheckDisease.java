package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class LandCheckDisease implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1303263032897430415L;
	private long landCheckId;
	private long landCheckDiseaseId;
	private int seq;
	private Date checkDate;
	private long checkingDiseaseId;
	private long diseaseChildId;
	private String diseaseOther;
	private long diseaseTypeId;
	private String level;
	private String tradingName;
	private String commonName;
	private String dangerousName;
	private Date manufactureDate;
	private Date expireDate;
	private String sourceBuy;
	private Date useDate;
	private double useRate1;
	private double useRate2;
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
	public long getLandCheckDiseaseId() {
		return landCheckDiseaseId;
	}
	public void setLandCheckDiseaseId(long landCheckDiseaseId) {
		this.landCheckDiseaseId = landCheckDiseaseId;
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
	public long getCheckingDiseaseId() {
		return checkingDiseaseId;
	}
	public void setCheckingDiseaseId(long checkingDiseaseId) {
		this.checkingDiseaseId = checkingDiseaseId;
	}
	public long getDiseaseChildId() {
		return diseaseChildId;
	}
	public void setDiseaseChildId(long diseaseChildId) {
		this.diseaseChildId = diseaseChildId;
	}
	public String getDiseaseOther() {
		return diseaseOther;
	}
	public void setDiseaseOther(String diseaseOther) {
		this.diseaseOther = diseaseOther;
	}
	public long getDiseaseTypeId() {
		return diseaseTypeId;
	}
	public void setDiseaseTypeId(long diseaseTypeId) {
		this.diseaseTypeId = diseaseTypeId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTradingName() {
		return tradingName;
	}
	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getDangerousName() {
		return dangerousName;
	}
	public void setDangerousName(String dangerousName) {
		this.dangerousName = dangerousName;
	}
	public Date getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getSourceBuy() {
		return sourceBuy;
	}
	public void setSourceBuy(String sourceBuy) {
		this.sourceBuy = sourceBuy;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public double getUseRate1() {
		return useRate1;
	}
	public void setUseRate1(double useRate1) {
		this.useRate1 = useRate1;
	}
	public double getUseRate2() {
		return useRate2;
	}
	public void setUseRate2(double useRate2) {
		this.useRate2 = useRate2;
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
