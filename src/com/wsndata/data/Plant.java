package com.wsndata.data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dcs.dcswc.common.ICheckOnGrid;

public class Plant implements Serializable, ICheckOnGrid
{	
	private static final long serialVersionUID = 5704787066051565942L;
	private long plantId;
	private Long refPlantId;
	private String customerNo;
	private int plantYear;
	private int plantNo;
	private long branchCode;
	private String idCard;
	private Date effectiveDate;
	private Date createDate; 
	private String createBy;
	private Date lastUpdateDate; 
	private String lastUpdateBy;
	private Long farmerGroupId;
	private double target;
	private String flag;
	
	// --- phase 1.5 ---
	private String memberNo;
	private String accountName;
	private String accountNo;
	private Long bankId;
	private String season;
	private String certificate;
	private String registerNo;
	private int bookNo;
	private String documentNo;
	private Date registerDate;
	private String registerBy;
	private Long farmerGroupId2;
	private String memberNo2;
	private double target2;
	/// --- ---
	
	private Branch branch;
	private Farmer farmer;
	private Set<FarmerFamily> farmerFamily;
	private List<PlantManure> plantManure;
	private List<PlantChemical> plantChemical;
	private List<AssetDetail> assetDetail;
	private List<LandRight> landright;
	

	private boolean checkBox; // added for list
	private String linkEdit;  // added for list
	private String linkImageEdit;
	
	private String fullName; // for list in plantlist
	private String firstName;
	private String lastName;
	private String branchName;
	private long breedTypeId;
	private String breedTypeName;
	
	// --------- collect group of item -------//
	private String breedItem;
	private String manureItem;
	private String chemiItem;
	private String assetItem;
	// ---- for wsn 1.5
	private String landrightItem;
	private String familyItem;
	private String detailItem;
	private String landCheckItem;
	
	// ---------- assetDetail --------
	private String assetAmount;
	private String assetDate;
	private String districtName;
	private String fta;
	private String plantStatus;
	private String reason;
	
	private String statusPopup; // for popup status in plantlist
	// ---- for wsn 1.5
	private String status;
	private String linkImageStatus;
	private String remark;
	private String register;
	private String memberType;
	private double income;
	private String[] citizenId;
	private String[] registerId;
	private String farmerGroupName;
	
	public String getLandCheckItem() {
		return landCheckItem;
	}
	public void setLandCheckItem(String landCheckItem) {
		this.landCheckItem = landCheckItem;
	}
	public String getFta() {
		return fta;
	}
	public void setFta(String fta) {
		this.fta = fta;
	}
	public String getDetailItem() {
		return detailItem;
	}
	public void setDetailItem(String detailItem) {
		this.detailItem = detailItem;
	}
	public String getLandrightItem() {
		return landrightItem;
	}
	public void setLandrightItem(String landrightItem) {
		this.landrightItem = landrightItem;
	}
	public String getFamilyItem() {
		return familyItem;
	}
	public void setFamilyItem(String familyItem) {
		this.familyItem = familyItem;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Set<FarmerFamily> getFarmerFamily() {
		return farmerFamily;
	}
	public void setFarmerFamily(Set<FarmerFamily> farmerFamily) {
		this.farmerFamily = farmerFamily;
	}

	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	public int getBookNo() {
		return bookNo;
	}
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public String getRegisterBy() {
		return registerBy;
	}
	public void setRegisterBy(String registerBy) {
		this.registerBy = registerBy;
	}
	public Long getFarmerGroupId2() {
		return farmerGroupId2;
	}
	public void setFarmerGroupId2(Long farmerGroupId2) {
		this.farmerGroupId2 = farmerGroupId2;
	}
	public String getMemberNo2() {
		return memberNo2;
	}
	public void setMemberNo2(String memberNo2) {
		this.memberNo2 = memberNo2;
	}
	public double getTarget2() {
		return target2;
	}
	public void setTarget2(double target2) {
		this.target2 = target2;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAssetAmount() {
		return assetAmount;
	}
	public void setAssetAmount(String assetAmount) {
		this.assetAmount = assetAmount;
	}
	public String getAssetDate() {
		return assetDate;
	}
	public void setAssetDate(String assetDate) {
		this.assetDate = assetDate;
	}
	public Long getRefPlantId() {
		return refPlantId;
	}
	public void setRefPlantId(Long refPlantId) {
		this.refPlantId = refPlantId;
	}
	public double getTarget() {
		return target;
	}
	public void setTarget(double target) {
		this.target = target;
	}
	public Long getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(Long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	public String getBreedItem() {
		return breedItem;
	}
	public void setBreedItem(String breedItem) {
		this.breedItem = breedItem;
	}
	public String getManureItem() {
		return manureItem;
	}
	public void setManureItem(String manureItem) {
		this.manureItem = manureItem;
	}
	public String getChemiItem() {
		return chemiItem;
	}
	public void setChemiItem(String chemiItem) {
		this.chemiItem = chemiItem;
	}
	public String getAssetItem() {
		return assetItem;
	}
	public void setAssetItem(String assetItem) {
		this.assetItem = assetItem;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public long getPlantId() {
		return plantId;
	}
	public void setPlantId(long plantId) {
		this.plantId = plantId;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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
	public long getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
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
	public boolean getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public String getLinkEdit() {
		return "images/btn-edit.png";
	}
	public void setLinkEdit(String linkEdit) {
		this.linkEdit = linkEdit;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	public void setLinkImageEdit(String linkImageEdit) {
		this.linkImageEdit = linkImageEdit;
	}
	public List<LandRight> getLandright() {
		return landright;
	}
	public void setLandright(List<LandRight> landright) {
		this.landright = landright;
	}

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatusPopup() {
		return statusPopup;
	}
	public void setStatusPopup(String statusPopup) {
		this.statusPopup = statusPopup;
	}
	public String getPlantStatus() {
		return plantStatus;
	}
	public void setPlantStatus(String plantStatus) {
		this.plantStatus = plantStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLinkImageStatus() {
		if(status==null) {
			linkImageStatus = "images/ico_blank.gif";
		} else if(status.equals("A")) {
			linkImageStatus = "images/ico_active.gif";
		} else if(status.equals("C")) {
			linkImageStatus = "images/ico_cancel.gif";
		}
		return linkImageStatus;
	}
	public void setLinkImageStatus(String linkImageStatus) {
		this.linkImageStatus = linkImageStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public String[] getCitizenId() {
		return citizenId;
	}
	public void setCitizenId(String[] citizenId) {
		this.citizenId = citizenId;
	}
	public String[] getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String[] registerId) {
		this.registerId = registerId;
	}
	public String getFarmerGroupName() {
		return farmerGroupName;
	}
	public void setFarmerGroupName(String farmerGroupName) {
		this.farmerGroupName = farmerGroupName;
	}
	
}
