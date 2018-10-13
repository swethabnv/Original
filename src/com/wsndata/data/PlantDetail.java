package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PlantDetail implements Serializable{

	/**
	 * 
	 */
	// Tab1 of plant list
	private static final long serialVersionUID = 2459546345805274750L;
	private long plantId;
	private long breedTypeId;
	private long breedGroupId;
	private double qtyPerRai;
	private double costPerRai;
	private int seq;
	private String docNo;
	private long typeId; 
	private String seedFrom; 
	private String seedSelect; 
	private String plantObjective;
	private String qualification; 
	private String standard; //char (1)
    private String plantMethod;
	private String prepareArea; 
	private String prepareAreaOther;
	private String prepareManure; 
	private long plantRai;
	private long plantNgan;
	private long plantWah;
	
	private Date plantDate;
	private Date forecastDate;
	private double forecastRecord;
	private Date publicMarketDate;
	private double publicCrop;
	private String recordForecastBy;
    private Date recordForecastDate;
    
    // added by Yatphiroon.P on 29.04.2015
    private long cooperativeId;
    private long noOfSeed;
	
	private BreedGroup breedGroup;	
	private Set<HarvestDetail> harvestDetail;
	private Set<SellingDetail> sellingDetail;
	private Set<CostDetail> costDetail;
	private List<PlantManure> plantManure;
	private List<PlantChemical> plantChemical;
	private List<AssetDetail> assetDetail;
	private LandRight landRight;
	
	
	
	public Date getPlantDate() {
		return plantDate;
	}
	public void setPlantDate(Date plantDate) {
		this.plantDate = plantDate;
	}
	public Date getForecastDate() {
		return forecastDate;
	}
	public void setForecastDate(Date forecastDate) {
		this.forecastDate = forecastDate;
	}
	public double getForecastRecord() {
		return forecastRecord;
	}
	public void setForecastRecord(double forecastRecord) {
		this.forecastRecord = forecastRecord;
	}
	public Date getPublicMarketDate() {
		return publicMarketDate;
	}
	public void setPublicMarketDate(Date publicMarketDate) {
		this.publicMarketDate = publicMarketDate;
	}
	public double getPublicCrop() {
		return publicCrop;
	}
	public void setPublicCrop(double publicCrop) {
		this.publicCrop = publicCrop;
	}
	public String getRecordForecastBy() {
		return recordForecastBy;
	}
	public void setRecordForecastBy(String recordForecastBy) {
		this.recordForecastBy = recordForecastBy;
	}
	public Date getRecordForecastDate() {
		return recordForecastDate;
	}
	public void setRecordForecastDate(Date recordForecastDate) {
		this.recordForecastDate = recordForecastDate;
	}
	public long getPlantRai() {
		return plantRai;
	}
	public void setPlantRai(long plantRai) {
		this.plantRai = plantRai;
	}
	public long getPlantNgan() {
		return plantNgan;
	}
	public void setPlantNgan(long plantNgan) {
		this.plantNgan = plantNgan;
	}
	public long getPlantWah() {
		return plantWah;
	}
	public void setPlantWah(long plantWah) {
		this.plantWah = plantWah;
	}
	public Set<SellingDetail> getSellingDetail() {
		return sellingDetail;
	}
	public void setSellingDetail(Set<SellingDetail> sellingDetail) {
		this.sellingDetail = sellingDetail;
	}
	public Set<HarvestDetail> getHarvestDetail() {
		return harvestDetail;
	}
	public LandRight getLandRight() {
		return landRight;
	}
	public void setLandRight(LandRight landRight) {
		this.landRight = landRight;
	}
	public List<AssetDetail> getAssetDetail() {
		return assetDetail;
	}
	public void setAssetDetail(List<AssetDetail> assetDetail) {
		this.assetDetail = assetDetail;
	}
	public List<PlantManure> getPlantManure() {
		return plantManure;
	}
	public void setPlantManure(List<PlantManure> plantManure) {
		this.plantManure = plantManure;
	}
	public List<PlantChemical> getPlantChemical() {
		return plantChemical;
	}
	public void setPlantChemical(List<PlantChemical> plantChemical) {
		this.plantChemical = plantChemical;
	}
	
	public void setHarvestDetail(Set<HarvestDetail> harvestDetail) {
		this.harvestDetail = harvestDetail;
	}
	
	public Set<CostDetail> getCostDetail() {
		return costDetail;
	}
	public void setCostDetail(Set<CostDetail> costDetail) {
		this.costDetail = costDetail;
	}
	
	
	public long getPlantId() {
		if (landRight != null) {
			return landRight.getPlantId();
		} else {
			return plantId;
		}
	}
	public void setPlantId(long plantId) {
		if (landRight != null) {
			landRight.setPlantId(plantId);
		} else {
			this.plantId = plantId;
		}
	}
	
	public BreedGroup getBreedGroup() {
		return breedGroup;
	}
	public void setBreedGroup(BreedGroup breedGroup) {
		this.breedGroup = breedGroup;
	}
	public long getBreedTypeId() {		
			return breedTypeId;	
	}
	
	public void setBreedTypeId(long breedTypeId) {
			this.breedTypeId = breedTypeId;
	}
	public long getBreedGroupId() {
			return breedGroupId;
	}
	
	public void setBreedGroupId(long breedGroupId) {
			this.breedGroupId = breedGroupId;
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
	public int getSeq() {
		if (landRight != null) {
			return landRight.getSeq();
		} else {
			return seq;
		}
	}
	public void setSeq(int seq) {
		
		if (landRight != null) {
			landRight.setSeq(seq);
		} else {
			this.seq = seq;
		}
	}
	public String getDocNo() {
		if (landRight != null) {
			return landRight.getDocNo();
		} else {
			return docNo;
		}
	}
	public void setDocNo(String docNo) {
		
		if (landRight != null) {
			landRight.setDocNo(docNo);
		} else {
			this.docNo = docNo;
		}
	}
	public long getTypeId() {
		if (landRight != null) {
			return landRight.getTypeId();
		} else {
			return typeId;
		}
	}
	public void setTypeId(long typeId) {
		
		if (landRight != null) {
			landRight.setTypeId(typeId);
		} else {
			this.typeId = typeId;
		}
	}
	public String getSeedFrom() {
		return seedFrom;
	}
	public void setSeedFrom(String seedFrom) {
		this.seedFrom = seedFrom;
	}
	public String getSeedSelect() {
		return seedSelect;
	}
	public void setSeedSelect(String seedSelect) {
		this.seedSelect = seedSelect;
	}
	public String getPlantObjective() {
		return plantObjective;
	}
	public void setPlantObjective(String plantObjective) {
		this.plantObjective = plantObjective;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getPlantMethod() {
		return plantMethod;
	}
	public void setPlantMethod(String plantMethod) {
		this.plantMethod = plantMethod;
	}
	public String getPrepareArea() {
		return prepareArea;
	}
	public void setPrepareArea(String prepareArea) {
		this.prepareArea = prepareArea;
	}
	public String getPrepareAreaOther() {
		return prepareAreaOther;
	}
	public void setPrepareAreaOther(String prepareAreaOther) {
		this.prepareAreaOther = prepareAreaOther;
	}
	public String getPrepareManure() {
		return prepareManure;
	}
	public void setPrepareManure(String prepareManure) {
		this.prepareManure = prepareManure;
	}
	
	public long getCooperativeId() {
		return cooperativeId;
	}
	public void setCooperativeId(long cooperativeId) {
		this.cooperativeId = cooperativeId;
	}
	public long getNoOfSeed() {
		return noOfSeed;
	}
	public void setNoOfSeed(long noOfSeed) {
		this.noOfSeed = noOfSeed;
	}
	
	
	

}
