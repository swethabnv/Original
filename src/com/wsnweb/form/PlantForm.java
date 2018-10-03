package com.wsnweb.form;
import org.apache.struts.action.ActionForm;
public class PlantForm extends ActionForm {

	private static final long serialVersionUID = 2419025574804919356L;

	private String plantId;
	private String refPlantId;
	private String cmd;
	private String msg;
	private String status;
	
	
	private String[] delBreedGroupId; // การเตรียมพันธุ์พืช
	private String[] delManureTypeId; //  การใช้่ปุ๋ยเคมีและปุ๋ยอินทรีย์
	private String[] delChemicalTypeId; // การใช้สารคุมและกำจัดวัชพืช
	private String[] delAssetId; // การซื้อทรัพย์สินในการเพาะปลูก
	private String[] delCostId; // การซื้อทรัพย์สินในการเพาะปลูก
	private String[] delTypeId; //เอกสารสิทธิ์
	private String[] delHarvDate; // การเก็บเกี่ยว
	private String[] delSaleDate; // การขาย
	
	
	// --- ข้อมูลเกษตรกรผู้เพาะปลูกพืช --- //
	
	private String idCard; 		 // เลขที่บัตรประชาชน
	private String abbrPrefix;   // คำนำหน้า
	private String firstName;
	private String lastName;
	private String addressNo;
	private String moo;
	private String provinceNo;
	private String districtNo;
	private String subDistrictNo;
	private String postCode;
	private String tel; 		// เบอร์บ้าน
	private String mobile; 		// เบอร์มือถือ
	private String email;    
	private String village;      // หมู่บ้าน
	
	// กลุ่มเกษตรกร
	private String farmerGroupId;  		  // กลุ่มเกษตรกร
	private String customerFarmerGroupNo; // เลขที่สมาชิกเกษตรกร
	private String target;    			  // เป้าหมายผลผลิตรวมของกลุ่มเกษตรกร
    // สังกัดสหกรณ์
	private String childFarmerGroupId;
	private String childFarmerGroupName;
	
	
	// --- ข้อมูลบัญชีธนาคาร --- //
   private String accountName;  // ชื่อบัญชี
   private String accountNo;   //  เลขที่บัญชี
   private String bankId;        //  ธนาคาร
   private String branchCode;  //  สาขา
   
   // --- ข้อมูลการลงทะเบียนเกษตรกร --- //
   
   private String periodPlant; 	 // ฤดูการปลูก
   private String breedTypeId;	 // พืชที่ปลูก
   private String plantYear;     // ปีการผลิต
   private String plantNo;       // ครั้งที่
   
   private String certificateFarmerNo;  // เลขที่ใบรับรองเกษตรกร 
   private String registrationNo;       // เลขที่ลงทะเบียน
   private String bookNo;               // เล่มที่
   private String documentNo;           // เลขที่
   private String projectDate;          // วันที่เข้าร่วมโครงการ
   private String officerName;          // เจ้าหน้าที่รับสมัคร
   private String plantStatus;			// สถานะ
   private String reason;				// เหตุผล
   
   // --------------------------//
   
	private String breedGroupId; // ชื่อพันธุ์พืชที่ปลูก
	private String provinceName; // จังหวัด
	private String forecastRecord; 

	private String customerNo; // เลขที่ลูกค้า ธ.ก.ส.
	private String assetId;
	private String assetDate;
	private String amount;
	private String dateAsset;  // ในช่องยอดรวม
	private String assetAmount; // ในช่องยอดรวม
	private String sumCheckbox; // ในช่องยอดรวม
	
	// --------- collect group of item -------//
	
	private String breedItem; //( detailItem, costListItem, saleListItem )
	private String manureItem;
	private String chemiItem;
	private String assetItem;
	private String detailItem;
	private String landrightItem;// แอดเข้ามาทำ wsn1.5
	private String familyItem; // for 1.5
	private String landCheckItem; 
	// ----
	private String soi;
	private String street;
	private String landDocNo;
	private String landDocRai;
	private String landDocNgan;
	private String landDocWah;
	private String qtyPerRai;
	private String plantRai;
	private String plantNgan;
	private String plantWah;
	private String docNo;
	private String docRai;
	private String docNgan;
	private String docWah;
	//
	
	private String prepareAreaId;
	private String sumCheck;
	private String assetDetailAmount;
	private String assetDetailDate;
	
	private String fta;
	private String memberStatus;
	private String remark;
	private String register;
	private String memberType;
	private String income;
	private String docAndType;
	// ----
	
	private String buyerId;
	private String setStatusCmd;
	
	private String landCheckPage; //For Link from Land Check Page (Selling)
	private String landCheckDocNo;
	
	public String getLandCheckItem() {
		return landCheckItem;
	}
	public void setLandCheckItem(String landCheckItem) {
		this.landCheckItem = landCheckItem;
	}
	public String getChildFarmerGroupName() {
		return childFarmerGroupName;
	}
	public void setChildFarmerGroupName(String childFarmerGroupName) {
		this.childFarmerGroupName = childFarmerGroupName;
	}
	public String getChildFarmerGroupId() {
		return childFarmerGroupId;
	}
	public void setChildFarmerGroupId(String childFarmerGroupId) {
		this.childFarmerGroupId = childFarmerGroupId;
	}
	public String getFta() {
		return fta;
	}
	public void setFta(String fta) {
		this.fta = fta;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getAssetDetailAmount() {
		return assetDetailAmount;
	}
	public void setAssetDetailAmount(String assetDetailAmount) {
		this.assetDetailAmount = assetDetailAmount;
	}
	public String getAssetDetailDate() {
		return assetDetailDate;
	}
	public void setAssetDetailDate(String assetDetailDate) {
		this.assetDetailDate = assetDetailDate;
	}
	public String getSumCheck() {
		return sumCheck;
	}
	public void setSumCheck(String sumCheck) {
		this.sumCheck = sumCheck;
	}
	public String getPrepareAreaId() {
		return prepareAreaId;
	}
	public void setPrepareAreaId(String prepareAreaId) {
		this.prepareAreaId = prepareAreaId;
	}
	public String getSoi() {
		return soi;
	}
	
	public String getFamilyItem() {
		return familyItem;
	}
	public void setFamilyItem(String familyItem) {
		this.familyItem = familyItem;
	}
	public String[] getDelHarvDate() {
		return delHarvDate;
	}
	public void setDelHarvDate(String[] delHarvDate) {
		this.delHarvDate = delHarvDate;
	}
	public String getLandrightItem() {
		return landrightItem;
	}
	public void setLandrightItem(String landrightItem) {
		this.landrightItem = landrightItem;
	}
	public String getDetailItem() {
		return detailItem;
	}
	public void setDetailItem(String detailItem) {
		this.detailItem = detailItem;
	}
	public void setSoi(String soi) {
		this.soi = soi;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getLandDocNo() {
		return landDocNo;
	}
	public void setLandDocNo(String landDocNo) {
		this.landDocNo = landDocNo;
	}
	public String getLandDocRai() {
		return landDocRai;
	}
	public void setLandDocRai(String landDocRai) {
		this.landDocRai = landDocRai;
	}
	public String getLandDocNgan() {
		return landDocNgan;
	}
	public void setLandDocNgan(String landDocNgan) {
		this.landDocNgan = landDocNgan;
	}
	public String getLandDocWah() {
		return landDocWah;
	}
	public void setLandDocWah(String landDocWah) {
		this.landDocWah = landDocWah;
	}
	public String getQtyPerRai() {
		return qtyPerRai;
	}
	public void setQtyPerRai(String qtyPerRai) {
		this.qtyPerRai = qtyPerRai;
	}
	public String getPlantRai() {
		return plantRai;
	}
	public void setPlantRai(String plantRai) {
		this.plantRai = plantRai;
	}
	public String getPlantNgan() {
		return plantNgan;
	}
	public void setPlantNgan(String plantNgan) {
		this.plantNgan = plantNgan;
	}
	public String getPlantWah() {
		return plantWah;
	}
	public void setPlantWah(String plantWah) {
		this.plantWah = plantWah;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocRai() {
		return docRai;
	}
	public void setDocRai(String docRai) {
		this.docRai = docRai;
	}
	public String getDocNgan() {
		return docNgan;
	}
	public void setDocNgan(String docNgan) {
		this.docNgan = docNgan;
	}
	public String getDocWah() {
		return docWah;
	}
	public void setDocWah(String docWah) {
		this.docWah = docWah;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(String farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCustomerFarmerGroupNo() {
		return customerFarmerGroupNo;
	}
	public void setCustomerFarmerGroupNo(String customerFarmerGroupNo) {
		this.customerFarmerGroupNo = customerFarmerGroupNo;
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
	
	public String getPeriodPlant() {
		return periodPlant;
	}
	public void setPeriodPlant(String periodPlant) {
		this.periodPlant = periodPlant;
	}
	public String getCertificateFarmerNo() {
		return certificateFarmerNo;
	}
	public void setCertificateFarmerNo(String certificateFarmerNo) {
		this.certificateFarmerNo = certificateFarmerNo;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getProjectDate() {
		return projectDate;
	}
	public void setProjectDate(String projectDate) {
		this.projectDate = projectDate;
	}
	public String getOfficerName() {
		return officerName;
	}
	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}
	public String getRefPlantId() {
		return refPlantId;
	}
	public void setRefPlantId(String refPlantId) {
		this.refPlantId = refPlantId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSumCheckbox() {
		return sumCheckbox;
	}
	public void setSumCheckbox(String sumCheckbox) {
		this.sumCheckbox = sumCheckbox;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getDateAsset() {
		return dateAsset;
	}
	public void setDateAsset(String dateAsset) {
		this.dateAsset = dateAsset;
	}
	public String getAssetAmount() {
		return assetAmount;
	}
	public void setAssetAmount(String assetAmount) {
		this.assetAmount = assetAmount;
	}
	public String[] getDelTypeId() {
		return delTypeId;
	}
	public void setDelTypeId(String[] delTypeId) {
		this.delTypeId = delTypeId;
	}
	public String[] getDelCostId() {
		return delCostId;
	}
	public void setDelCostId(String[] delCostId) {
		this.delCostId = delCostId;
	}
	public String[] getDelSaleDate() {
		return delSaleDate;
	}
	public void setDelSaleDate(String[] delSaleDate) {
		this.delSaleDate = delSaleDate;
	}
	public String getPlantId() {
		return plantId;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public String getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(String breedTypeId) {
		this.breedTypeId = breedTypeId;
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
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getAssetDate() {
		return assetDate;
	}
	public void setAssetDate(String assetDate) {
		this.assetDate = assetDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String[] getDelBreedGroupId() {
		return delBreedGroupId;
	}
	public void setDelBreedGroupId(String[] delBreedGroupId) {
		this.delBreedGroupId = delBreedGroupId;
	}
	public String[] getDelManureTypeId() {
		return delManureTypeId;
	}
	public void setDelManureTypeId(String[] delManureTypeId) {
		this.delManureTypeId = delManureTypeId;
	}
	public String[] getDelChemicalTypeId() {
		return delChemicalTypeId;
	}
	public void setDelChemicalTypeId(String[] delChemicalTypeId) {
		this.delChemicalTypeId = delChemicalTypeId;
	}
	public String[] getDelAssetId() {
		return delAssetId;
	}
	public void setDelAssetId(String[] delAssetId) {
		this.delAssetId = delAssetId;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public String getAbbrPrefix() {
		return abbrPrefix;
	}
	public void setAbbrPrefix(String abbrPrefix) {
		this.abbrPrefix = abbrPrefix;
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
	public String getAddressNo() {
		return addressNo;
	}
	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}
	public String getMoo() {
		return moo;
	}
	public void setMoo(String moo) {
		this.moo = moo;
	}
	public String getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	public String getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(String districtNo) {
		this.districtNo = districtNo;
	}
	public String getSubDistrictNo() {
		return subDistrictNo;
	}
	public void setSubDistrictNo(String subDistrictNo) {
		this.subDistrictNo = subDistrictNo;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPlantYear() {
		return plantYear;
	}
	public void setPlantYear(String plantYear) {
		this.plantYear = plantYear;
	}
	public String getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	public String getForecastRecord() {
		return forecastRecord;
	}
	public void setForecastRecord(String forecastRecord) {
		this.forecastRecord = forecastRecord;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBreedGroupId() {
		return breedGroupId;
	}
	public void setBreedGroupId(String breedGroupId) {
		this.breedGroupId = breedGroupId;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getPlantStatus() {
		return plantStatus;
	}
	public void setPlantStatus(String plantStatus) {
		this.plantStatus = plantStatus;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSetStatusCmd() {
		return setStatusCmd;
	}
	public void setSetStatusCmd(String setStatusCmd) {
		this.setStatusCmd = setStatusCmd;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
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
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getDocAndType() {
		return docAndType;
	}
	public void setDocAndType(String docAndType) {
		this.docAndType = docAndType;
	}
	public String getLandCheckPage() {
		return landCheckPage;
	}
	public void setLandCheckPage(String landCheckPage) {
		this.landCheckPage = landCheckPage;
	}
	public String getLandCheckDocNo() {
		return landCheckDocNo;
	}
	public void setLandCheckDocNo(String landCheckDocNo) {
		this.landCheckDocNo = landCheckDocNo;
	}
	
}
