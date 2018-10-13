package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class PlantChemical implements Serializable
{
	private static final long serialVersionUID = 4873864794688803541L;	
	private long plantChemicalId;
	private String formula;
	private String chemicalName;
	private double qtyPerRai;
	private double costPerRai;
	private int cseq;
	private String period;
	private Date dateUse;
	
	private long plantId;
	private long breedTypeId;
	private long breedGroupId;
	private String docNo;
	private long typeId;
	private int seq;
	private long chemicalTypeId; //for popup
	
	private PlantDetail plantDetail;
	private ChemicalType chemicalType;
	private String typeName;
	
	public ChemicalType getChemicalType() {
		return chemicalType;
	}
	public void setChemicalType(ChemicalType chemicalType) {
		this.chemicalType = chemicalType;
	}
	public long getPlantChemicalId() {
		return plantChemicalId;
	}
	public void setPlantChemicalId(long plantChemicalId) {
		this.plantChemicalId = plantChemicalId;
	}
	public int getCseq() {
		return cseq;
	}
	public void setCseq(int cseq) {
		this.cseq = cseq;
	}
	public PlantDetail getPlantDetail() {
		return plantDetail;
	}
	public void setPlantDetail(PlantDetail plantDetail) {
		this.plantDetail = plantDetail;
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
	public long getChemicalTypeId() {
		return chemicalTypeId;
	}
	public void setChemicalTypeId(long chemicalTypeId) {
		this.chemicalTypeId = chemicalTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getChemicalName() {
		return chemicalName;
	}
	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
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
	
	
	
}
