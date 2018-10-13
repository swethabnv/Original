package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;


public class LandCheck implements Serializable, ICheckOnGrid {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1303263032897430415L;
	private long landCheckId;
	private int plantYear;
	private int plantNo;
	private String idCard;
	private String firstName;
	private String lastName;
	private String docNo;
	private long typeId;
    private long docRai;
    private long docNgan;
    private long docWah;
	private Date checkDate;
	private String checkTime;
	private String result;
	private String remark;
	private String checkBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private long breedTypeId;
	private String breedTypeName;
	private String farmerName;
	private String committee1; //new create
	private String committee2; //new create
	private String onwer; //new create
	private long refPlantId; //new create
	private long checkPeriodId; //new create
	private String description; //new create
	private long plantId; //new create
	
	private List<LandCheckImages> landCheckImages;
	private List<LandCheckPrepareSoil> landCheckPrepareSoil;
	private List<LandCheckPlant> landCheckPlant;
	private List<LandCheckManure> landCheckManure;
	private List<LandCheckMixed> landCheckMixed;
	private List<LandCheckDisease> landCheckDisease;
	private List<LandCheckHarvest> landCheckHarvest;
    private LandRightType landRightType;
    
	private String checkDateTh;// added for LandCheckList
	private String landRight;// added for LandCheckList
	private String checkPeriod;// added for LandCheckList
	private String checkResult;// added for LandCheckList
	private String typeName;// added for LandCheck
	private boolean checkBox;// added for LandCheckList
	private String linkImageEdit;// added for LandCheckList
	
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	public long getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}
	public String getBreedTypeName() {
		return breedTypeName;
	}
	public void setBreedTypeName(String breedTypeName) {
		this.breedTypeName = breedTypeName;
	}
	public long getLandCheckId() {
		return landCheckId;
	}
	public void setLandCheckId(long landCheckId) {
		this.landCheckId = landCheckId;
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
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
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
	public String getCheckPeriod() {
		return checkPeriod;
	}
	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
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
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public List<LandCheckImages> getLandCheckImages() {
		return landCheckImages;
	}
	public void setLandCheckImages(List<LandCheckImages> landCheckImages) {
		this.landCheckImages = landCheckImages;
	}
	public List<LandCheckPrepareSoil> getLandCheckPrepareSoil() {
		return landCheckPrepareSoil;
	}
	public void setLandCheckPrepareSoil(
			List<LandCheckPrepareSoil> landCheckPrepareSoil) {
		this.landCheckPrepareSoil = landCheckPrepareSoil;
	}
	public List<LandCheckPlant> getLandCheckPlant() {
		return landCheckPlant;
	}
	public void setLandCheckPlant(List<LandCheckPlant> landCheckPlant) {
		this.landCheckPlant = landCheckPlant;
	}
	public List<LandCheckManure> getLandCheckManure() {
		return landCheckManure;
	}
	public void setLandCheckManure(List<LandCheckManure> landCheckManure) {
		this.landCheckManure = landCheckManure;
	}
	public List<LandCheckMixed> getLandCheckMixed() {
		return landCheckMixed;
	}
	public void setLandCheckMixed(List<LandCheckMixed> landCheckMixed) {
		this.landCheckMixed = landCheckMixed;
	}
	public List<LandCheckDisease> getLandCheckDisease() {
		return landCheckDisease;
	}
	public void setLandCheckDisease(List<LandCheckDisease> landCheckDisease) {
		this.landCheckDisease = landCheckDisease;
	}
	public List<LandCheckHarvest> getLandCheckHarvest() {
		return landCheckHarvest;
	}
	public void setLandCheckHarvest(List<LandCheckHarvest> landCheckHarvest) {
		this.landCheckHarvest = landCheckHarvest;
	}
	public LandRightType getLandRightType() {
		return landRightType;
	}
	public void setLandRightType(LandRightType landRightType) {
		this.landRightType = landRightType;
	}
	public String getCheckDateTh() {
		return checkDateTh;
	}
	public void setCheckDateTh(String checkDateTh) {
		this.checkDateTh = checkDateTh;
	}
	public String getLandRight() {
		return landRight;
	}
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setLinkImageEdit(String linkImageEdit) {
		this.linkImageEdit = linkImageEdit;
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
	public String getCommittee1() {
		return committee1;
	}
	public void setCommittee1(String committee1) {
		this.committee1 = committee1;
	}
	public String getCommittee2() {
		return committee2;
	}
	public void setCommittee2(String committee2) {
		this.committee2 = committee2;
	}
	public String getOnwer() {
		return onwer;
	}
	public void setOnwer(String onwer) {
		this.onwer = onwer;
	}
	public long getRefPlantId() {
		return refPlantId;
	}
	public void setRefPlantId(long refPlantId) {
		this.refPlantId = refPlantId;
	}
	public long getCheckPeriodId() {
		return checkPeriodId;
	}
	public void setCheckPeriodId(long checkPeriodId) {
		this.checkPeriodId = checkPeriodId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getPlantId() {
		return plantId;
	}
	public void setPlantId(long plantId) {
		this.plantId = plantId;
	}
	
}
