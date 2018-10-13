package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class PlantManure implements Serializable{

	private static final long serialVersionUID = 4873864794688803541L;
	
	private long plantManureId;
	private String manureName;
	private String formula;
	private double qtyPerRai;
	private double costPerRai;
	private int mseq;
	private String period;
	private Date dateUse;
	private String manureStatus;
	
	private long plantId;
	private long breedTypeId;
	private long breedGroupId;
	private String docNo;
	private long typeId; // landrightTypeId
	private int seq;
	private long manureTypeId;
	
	private PlantDetail plantDetail;
	private ManureType manureType;
	private String typeName;

	public ManureType getManureType() {
		return manureType;
	}
	public void setManureType(ManureType manureType) {
		this.manureType = manureType;
	}
	public long getPlantManureId() {
		return plantManureId;
	}
	public void setPlantManureId(long plantManureId) {
		this.plantManureId = plantManureId;
	}
	public int getMseq() {
		return mseq;
	}
	public void setMseq(int mseq) {
		this.mseq = mseq;
	}
	
	public long getManureTypeId() {
		return manureTypeId;
	}
	public void setManureTypeId(long manureTypeId) {
		this.manureTypeId = manureTypeId;
	}
	public PlantDetail getPlantDetail() {
		return plantDetail;
	}
	public void setPlantDetail(PlantDetail plantDetail) {
		this.plantDetail = plantDetail;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getManureName() {
		return manureName;
	}
	public void setManureName(String manureName) {
		this.manureName = manureName;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public double getQtyPerRai() {
		return qtyPerRai;
	}
	public void setQtyPerRai(double qtyPerRai) {
		this.qtyPerRai = qtyPerRai;
	}
	public double getCostPerRai() {
		return costPerRai;
	}
	public void setCostPerRai(double costPerRai) {
		this.costPerRai = costPerRai;
	}
	
	// --- seq ---
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
	// --- plantId ---
	public long getPlantId() 
	{
		if (plantDetail != null) {
			return plantDetail.getPlantId();
		} else {
			return plantId;
		}
	}
	public void setPlantId(long plantId) 
	{
		if (plantDetail != null) {
			plantDetail.setPlantId(plantId);
		} else {
			this.plantId = plantId;
		}
	}
	// --- breedTypeId ---
	public long getBreedTypeId() 
	{
		if (plantDetail != null) {
			return plantDetail.getBreedTypeId();
		} else {
			return breedTypeId;
		}
	}
	public void setBreedTypeId(long breedTypeId) 
	{
		if (plantDetail != null) {
			plantDetail.setBreedTypeId(breedTypeId);
		} else {
			this.breedTypeId = breedTypeId;
		}
	}
	// --- breedGroupId ---
	public long getBreedGroupId() {
		if (plantDetail != null) {
			return plantDetail.getBreedGroupId();
		} else {
			return breedGroupId;
		}
	}
	public void setBreedGroupId(long breedGroupId) {
		if (plantDetail != null) {
			plantDetail.setBreedTypeId(breedGroupId);
		} else {
			this.breedGroupId = breedGroupId;
		}
	}
	// --- docNo ---
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
	// --- typeId --- 
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
	
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Date getDateUse() {
		return dateUse;
	}
	public void setDateUse(Date dateUse) {
		this.dateUse = dateUse;
	}
	public String getManureStatus() {
		return manureStatus;
	}
	public void setManureStatus(String manureStatus) {
		this.manureStatus = manureStatus;
	}
}
