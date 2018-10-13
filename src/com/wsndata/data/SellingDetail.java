package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class SellingDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -236982457971190110L;
	private long plantId;
	private long breedTypeId;
	private long breedGroupId;
	private long buyerId;
	
	private Date saleDate;
	private double saleCrop;
	private double salePrice;
	private double amount;
	
	private double saleDryCrop;
	private double saleDryPrice;
	private double dryAmount;
	

	private double humid;
	private double humidDry;
	
	private String buyer;
	private String buyerAddress;
	
	private long regionNo;
	private long provinceNo;
	private long districtNo;
	private long subDistrictNo;
	
	private long saleSeq;
	private String soi;
	private String tel;
	private int moo;
	private String docNo;
	private long typeId;
	private int seq;
	private PlantDetail plantDetail;
	
	
	
	
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public PlantDetail getPlantDetail() {
		return plantDetail;
	}
	public void setPlantDetail(PlantDetail plantDetail) {
		this.plantDetail = plantDetail;
	}
	public long getSaleSeq() {
		return saleSeq;
	}
	public void setSaleSeq(long saleSeq) {
		this.saleSeq = saleSeq;
	}
	public String getSoi() {
		return soi;
	}
	public void setSoi(String soi) {
		this.soi = soi;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getMoo() {
		return moo;
	}
	public void setMoo(int moo) {
		this.moo = moo;
	}
	// docNo
	public String getDocNo() {
		if (plantDetail != null) {
			return plantDetail.getDocNo();
		} else {
			return docNo;
		}
	}
	public void setDocNo(String docNo) {
		if (plantDetail != null) {
			plantDetail.setDocNo(docNo);
		} else {
			this.docNo = docNo;
		}
	}
	// typeId
	public long getTypeId() {
		if (plantDetail != null){
			return plantDetail.getTypeId();
		}else{
			return typeId;
		}
	}
	public void setTypeId(long typeId) {
		if (plantDetail != null) {
			plantDetail.setTypeId(typeId);
		} else {
			this.typeId = typeId;
		}
	}
	// seq
	public int getSeq() {
		if (plantDetail != null){
			return plantDetail.getSeq();
		}else{
			return seq;
		}
	}
	public void setSeq(int seq) {
		if (plantDetail != null) {
			plantDetail.setSeq(seq);
		} else {
			this.seq = seq;
		}
	}
	// plantId
	public long getPlantId() {
		if (plantDetail != null){
			return plantDetail.getPlantId();
		}else{
			return plantId;
		}
	}
	public void setPlantId(long plantId) {
		if (plantDetail != null) {
			plantDetail.setPlantId(plantId);
		} else {
			this.plantId = plantId;
		}
	}
	// breedTypeId
	public long getBreedTypeId() {
		if (plantDetail != null) {
			return plantDetail.getBreedTypeId();
		} else {
			return breedTypeId;
		}
	}
	public void setBreedTypeId(long breedTypeId) {
		if (plantDetail != null) {
			plantDetail.setBreedTypeId(breedTypeId);
		} else {
			this.breedTypeId = breedTypeId;
		}
	}
	// breedGroupId
	public long getBreedGroupId() {
		if (plantDetail != null) {
			return plantDetail.getBreedGroupId();
		} else {
			return breedGroupId;
		}
	}
	public void setBreedGroupId(long breedGroupId) {
		if (plantDetail != null) {
			plantDetail.setBreedGroupId(breedGroupId);
		} else {
			this.breedGroupId = breedGroupId;
		}
	}
	
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public double getSaleCrop() {
		return saleCrop;
	}
	public void setSaleCrop(double saleCrop) {
		this.saleCrop = saleCrop;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
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
	public long getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(long districtNo) {
		this.districtNo = districtNo;
	}
	public long getSubDistrictNo() {
		return subDistrictNo;
	}
	public void setSubDistrictNo(long subDistrictNo) {
		this.subDistrictNo = subDistrictNo;
	}
	public double getSaleDryCrop() {
		return saleDryCrop;
	}
	public void setSaleDryCrop(double saleDryCrop) {
		this.saleDryCrop = saleDryCrop;
	}
	public double getSaleDryPrice() {
		return saleDryPrice;
	}
	public void setSaleDryPrice(double saleDryPrice) {
		this.saleDryPrice = saleDryPrice;
	}
	public double getDryAmount() {
		return dryAmount;
	}
	public void setDryAmount(double dryAmount) {
		this.dryAmount = dryAmount;
	}
	public double getHumid() {
		return humid;
	}
	public void setHumid(double humid) {
		this.humid = humid;
	}
	public double getHumidDry() {
		return humidDry;
	}
	public void setHumidDry(double humidDry) {
		this.humidDry = humidDry;
	}
	
	
}
