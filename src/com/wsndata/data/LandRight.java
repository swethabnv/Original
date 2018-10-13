package com.wsndata.data;

import java.io.Serializable;
import java.util.Set;

import com.dcs.dcswc.common.ICheckOnGrid;
public class LandRight implements Serializable, ICheckOnGrid {

	private static final long serialVersionUID = 2035042907154861083L;
	private long breedTypeId;  // is not in table
	//private long breedGroupId; // is not in table
	
	private String docNo;
	private long plantId;
    private long docRai;
    private long docNgan;
    private long docWah;
    private String irrigationIn; // char(1)
    private int seq;
    private long typeId;
    private long regionNo;
    private long provinceNo;
    private long districtNo;
    private long subDistrictNo;
   
   //  private long plantRai;
   //  private long plantNgan;
   //  private long plantWah;
    private double rentPrice;
    private String ownRent; // char(1)
    private String addressNo;
    private int moo;
    private long irrigationAreaId ;
    private String landStatus;
    private String landType;
    private String landTypeOther;
    
    private Plant plant;
    private LandRightType landRightType;
  //  private PlantDetail plantDetail;
    private Set<PlantDetail> plantDetail;
    
    
  //  private String landRight;// added for landRightList
    private String typeName;// added for landRightList
    private String farmerName;// added for landCheck
    private boolean checkBox;// added for landRightList
	private String linkImageEdit;// added for landRightList
	
	private String approvedBy; // added for landApproveList
	private String approved; // added for landApproveList
	private String approvedDate; // added for landApproveList
	private String idCard; // added for landApproveList
	private int plantYear; // added for landApproveList
	private int plantNo; // added for landApproveList
	private String landRight; // added for landApproveList
	private String linkApproved; // added for landApproveList
	private String linkNotApproved; // added for landApproveList

    private String waterSource ;

	private SubDistrict subDistrict;
	private District district;
	private Province province;
	private Coordinates coordinates;
	
	public Set<PlantDetail> getPlantDetail() {
		return plantDetail;
	}
	public void setPlantDetail(Set<PlantDetail> plantDetail) {
		this.plantDetail = plantDetail;
	}
	/*
	public PlantDetail getPlantDetail() {
		return plantDetail;
	}
	public void setPlantDetail(PlantDetail plantDetail) {
		this.plantDetail = plantDetail;
	}*/
	public Plant getPlant() {
		return plant;
	}
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
	
	public String getAddressNo() {
		return addressNo;
	}
	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}
	public int getMoo() {
		return moo;
	}
	public void setMoo(int moo) {
		this.moo = moo;
	}
	public long getIrrigationAreaId() {
		return irrigationAreaId;
	}
	public void setIrrigationAreaId(long irrigationAreaId) {
		this.irrigationAreaId = irrigationAreaId;
	}
	public String getLandStatus() {
		return landStatus;
	}
	public void setLandStatus(String landStatus) {
		this.landStatus = landStatus;
	}
	public String getLandType() {
		return landType;
	}
	public void setLandType(String landType) {
		this.landType = landType;
	}
	public String getLandTypeOther() {
		return landTypeOther;
	}
	public void setLandTypeOther(String landTypeOther) {
		this.landTypeOther = landTypeOther;
	}
	public String getOwnRent() {
		return ownRent;
	}
	public void setOwnRent(String ownRent) {
		this.ownRent = ownRent;
	}
	public double getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(double rentPrice) {
		this.rentPrice = rentPrice;
	}
	public void setLinkImageEdit(String linkImageEdit) {
		this.linkImageEdit = linkImageEdit;
	}
	
	public long getPlantId() {
		if (plant != null) {
			return plant.getPlantId();
		} else {
			return plantId;
		}
	}
	public void setPlantId(long plantId) {
		if (plant != null) {
			plant.setPlantId(plantId);
		} else {
			this.plantId = plantId;
		}
	}
	public long getBreedTypeId() {
			return breedTypeId;
		
	}
	public void setBreedTypeId(long breedTypeId) {

			this.breedTypeId = breedTypeId;
		
	}
	
	
	/*
	public long getDocNo() {
		return docNo;
	}
	public void setDocNo(long docNo) {
		this.docNo = docNo;
	}*/
	
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public long getDocRai() {
		return docRai;
	}
	public void setDocRai(long docRai) {
		this.docRai = docRai;
	}
	public long getDocNgan() {
		return docNgan;
	}
	public void setDocNgan(long docNgan) {
		this.docNgan = docNgan;
	}
	public long getDocWah() {
		return docWah;
	}
	public void setDocWah(long docWah) {
		this.docWah = docWah;
	}

	
	public String getIrrigationIn() {
		return irrigationIn;
	}
	public void setIrrigationIn(String irrigationIn) {
		this.irrigationIn = irrigationIn;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public long getTypeId() {

			return typeId;
		
	}
	public void setTypeId(long typeId) {

			this.typeId = typeId;
		
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
	public LandRightType getLandRightType() {
		return landRightType;
	}
	public void setLandRightType(LandRightType landRightType) {
		this.landRightType = landRightType;
	}
	/*
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}
	public String getLandRight() {
		return landRight;
	}*/
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public int getPlantYear() {
		return plantYear;
	}
	public void setPlantYear(int plantYear) {
		this.plantYear = plantYear;
	}
	public int getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(int plantNo) {
		this.plantNo = plantNo;
	}
	public String getLandRight() {
		return landRight;
	}
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}
	public String getLinkApproved() {
		return linkApproved;
	}
	public void setLinkApproved(String linkApproved) {
		this.linkApproved = linkApproved;
	}
	public String getLinkNotApproved() {
		return linkNotApproved;
	}
	public void setLinkNotApproved(String linkNotApproved) {
		this.linkNotApproved = linkNotApproved;
	}
	public String getWaterSource() {
		return waterSource;
	}
	public void setWaterSource(String waterSource) {
		this.waterSource = waterSource;
	}
	public SubDistrict getSubDistrict() {
		return subDistrict;
	}
	public void setSubDistrict(SubDistrict subDistrict) {
		this.subDistrict = subDistrict;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
    
}
