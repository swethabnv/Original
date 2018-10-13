package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class CostDetail implements Serializable {
	

	private static final long serialVersionUID = -8578596752203350619L;
	private long costId;
	private double amount;
	private Date costDate;
	private long plantId;
	private long breedTypeId;
	private long breedGroupId;
	private int seq;
	private String docNo;
	private long typeId;
	
	private PlantDetail plantDetail;
	private Cost cost;
	
	
	
	public Date getCostDate() {
		return costDate;
	}
	public void setCostDate(Date costDate) {
		this.costDate = costDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Cost getCost() {
		return cost;
	}
	public void setCost(Cost cost) {
		this.cost = cost;
	}
	public PlantDetail getPlantDetail() {
		return plantDetail;
	}
	public void setPlantDetail(PlantDetail plantDetail) {
		this.plantDetail = plantDetail;
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
			plantDetail.setSeq(seq);
		} else {
			this.seq = seq;
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
	public long getTypeId() 
	{
		if (plantDetail != null) {
			return plantDetail.getTypeId();
		} else {
			return typeId;
		}
	}
	public void setTypeId(long typeId) 
	{
		if (plantDetail != null) {
			plantDetail.setTypeId(typeId);
		} else {
			this.typeId = typeId;
		}
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
	// costId
	public long getCostId() {
		if (cost!=null) 
			return cost.getCostId();
		else
			return costId;
	}
	public void setCostId(long costId) {
		if (cost!=null)
			cost.setCostId(costId);
		else
			this.costId = costId;
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

}
