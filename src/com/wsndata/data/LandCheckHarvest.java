package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class LandCheckHarvest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1303263032897430415L;
	private long landCheckId;
	private long landCheckHarvestId;
	private int seq;
	private String observers;
	private Date observeDate;
	private String dain;
	private String dainRemark;
	private Date dainDate;
	private String harvestQuality;
	private Date harvestQualityDate;
	private String farmStatus;
	private Date farmStatusDate;
	private String harvester;
	private Date harvesterDate;
	private String cleaning;
	private Date cleaningDate;
	private String packaging;
	private Date packagingDate;
	private String moving;
	private Date movingDate;
	private double totalHarvest;
	private double keepHarvest;
	private double sell;
	private double salePrice;
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
	public long getLandCheckHarvestId() {
		return landCheckHarvestId;
	}
	public void setLandCheckHarvestId(long landCheckHarvestId) {
		this.landCheckHarvestId = landCheckHarvestId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getObservers() {
		return observers;
	}
	public void setObservers(String observers) {
		this.observers = observers;
	}
	public Date getObserveDate() {
		return observeDate;
	}
	public void setObserveDate(Date observeDate) {
		this.observeDate = observeDate;
	}
	public String getDain() {
		return dain;
	}
	public void setDain(String dain) {
		this.dain = dain;
	}
	public String getDainRemark() {
		return dainRemark;
	}
	public void setDainRemark(String dainRemark) {
		this.dainRemark = dainRemark;
	}
	public Date getDainDate() {
		return dainDate;
	}
	public void setDainDate(Date dainDate) {
		this.dainDate = dainDate;
	}
	public String getHarvestQuality() {
		return harvestQuality;
	}
	public void setHarvestQuality(String harvestQuality) {
		this.harvestQuality = harvestQuality;
	}
	public Date getHarvestQualityDate() {
		return harvestQualityDate;
	}
	public void setHarvestQualityDate(Date harvestQualityDate) {
		this.harvestQualityDate = harvestQualityDate;
	}
	public String getFarmStatus() {
		return farmStatus;
	}
	public void setFarmStatus(String farmStatus) {
		this.farmStatus = farmStatus;
	}
	public Date getFarmStatusDate() {
		return farmStatusDate;
	}
	public void setFarmStatusDate(Date farmStatusDate) {
		this.farmStatusDate = farmStatusDate;
	}
	public String getHarvester() {
		return harvester;
	}
	public void setHarvester(String harvester) {
		this.harvester = harvester;
	}
	public Date getHarvesterDate() {
		return harvesterDate;
	}
	public void setHarvesterDate(Date harvesterDate) {
		this.harvesterDate = harvesterDate;
	}
	public String getCleaning() {
		return cleaning;
	}
	public void setCleaning(String cleaning) {
		this.cleaning = cleaning;
	}
	public Date getCleaningDate() {
		return cleaningDate;
	}
	public void setCleaningDate(Date cleaningDate) {
		this.cleaningDate = cleaningDate;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public Date getPackagingDate() {
		return packagingDate;
	}
	public void setPackagingDate(Date packagingDate) {
		this.packagingDate = packagingDate;
	}
	public String getMoving() {
		return moving;
	}
	public void setMoving(String moving) {
		this.moving = moving;
	}
	public Date getMovingDate() {
		return movingDate;
	}
	public void setMovingDate(Date movingDate) {
		this.movingDate = movingDate;
	}
	public double getTotalHarvest() {
		return totalHarvest;
	}
	public void setTotalHarvest(double totalHarvest) {
		this.totalHarvest = totalHarvest;
	}
	public double getKeepHarvest() {
		return keepHarvest;
	}
	public void setKeepHarvest(double keepHarvest) {
		this.keepHarvest = keepHarvest;
	}
	public double getSell() {
		return sell;
	}
	public void setSell(double sell) {
		this.sell = sell;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
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
