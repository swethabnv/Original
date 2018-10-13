package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class HarvestDetail implements Serializable {
	

	private static final long serialVersionUID = -8578596752203350619L;
	private long plantId;
	private Date harvestDate;
	private long breedTypeId;
	private long breedGroupId;
	private String docNo;
	private long typeId;
	private int seq;
	
	private double totalHarvest;
	private double remainSell;
	private double consumeProduct;
	private double breedProduct;
	private PlantDetail plantDetail;
	

	public Date getHarvestDate() {
		return harvestDate;
	}
	public void setHarvestDate(Date harvestDate) {
		this.harvestDate = harvestDate;
	}
	public double getTotalHarvest() {
		return totalHarvest;
	}
	public void setTotalHarvest(double totalHarvest) {
		this.totalHarvest = totalHarvest;
	}
	public double getRemainSell() {
		return remainSell;
	}
	public void setRemainSell(double remainSell) {
		this.remainSell = remainSell;
	}
	public PlantDetail getPlantDetail() {
		return plantDetail;
	}
	public void setPlantDetail(PlantDetail plantDetail) {
		this.plantDetail = plantDetail;
	}
	// plantId
	public long getPlantId() {
		if (plantDetail != null) {
			return plantDetail.getPlantId();
		} else {
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
		if (plantDetail != null) {
			return plantDetail.getTypeId();
		} else {
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
		if (plantDetail != null) {
			return plantDetail.getSeq();
		} else {
			return seq;
		}
	}
	public void setSeq(int seq) {
		if (plantDetail != null) {
			plantDetail.setBreedGroupId(seq);
		} else {
			this.seq = seq;
		}
	}
	public double getConsumeProduct() {
		return consumeProduct;
	}
	public void setConsumeProduct(double consumeProduct) {
		this.consumeProduct = consumeProduct;
	}
	public double getBreedProduct() {
		return breedProduct;
	}
	public void setBreedProduct(double breedProduct) {
		this.breedProduct = breedProduct;
	}

	
}
