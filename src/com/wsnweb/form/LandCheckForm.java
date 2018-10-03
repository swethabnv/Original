package com.wsnweb.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.wsndata.data.LandCheck;

public class LandCheckForm extends ActionForm {
	private static final long serialVersionUID = 8169053540850803596L;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
	
	private long landCheckId;
	private int plantYear;
	private int plantNo;
	private String season;
	private String idCard;
 	private String firstName;
 	private String lastName;
	private long docId;
	private long typeId;
	private String docNo;
	private String typeName;
    private long docRai;
    private long docNgan;
    private long docWah;
	private long checkPeriod;
	private String checkDate;
	private String checkTime;
	private String checkHour;
	private String checkMinute;
	private String result;
	private String remark;
	private String checkBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private long breedTypeId;
	private long refPlantId;
	
	//For detailImagePopup
	private int index;
	private String imageData;
	private String imageId;
	private String viewImageStatus;
	private FormFile imageFormFile;
	
	//Coordinates
	private String[] delCooId;
	private String[] CoordinatesId;
	//private long coordinatesId;
	private String coordinatesData;
	
	//Prepare Soil
	private long[] prepareSoilId;
	private String[] prepareSoilDate;
	private String[] prepareSoilType;
	private String[] prepareSoilWater;
	private String[] prepareSoilOperate;
	private String[] prepareSoilOperateOther;
	private String[] prepareSoilRemark;
	private String[] prepareSoilCheckBy;
	private String[] prepareSoilResult;
	
	//Plant
	private long[] plantId;
	private String[] plantDate;
	private String[] plantMethod;
	private String[] plantType;
	private long[] plantName;
	private String[] plantSource;
	private String[] plantSowDate;
	private String[] plantThrowDate;
	private double[] plantUse;
	private String[] plantRemark;
	private String[] plantCheckBy;
	private String[] plantResult;
	
	//Manure
	private long[] manureId;
	private String[] manureDate;
	private long[] manureType;
	private String[] manureOther;
	private String[] manureStatus;
	private String[] manureName;
	private String[] manureFormula;
	private String[] manureBuyDate;
	private String[] manureSourceBuy;
	private double[] manureUseRate;
	private double[] manureTotalUse;
	private String[] manureRemark;
	private String[] manureCheckBy;
	private String[] manureResult;
	
	//Mixed
	private long[] mixedId;
	private String[] mixedDate;
	private long[] mixedBreedType;
	private long[] mixedChildId;
	private long[] mixedEliminateId;
	private String[] mixedEliminateOther;
	private String[] mixedRemark;
	private String[] mixedCheckBy;
	private String[] mixedResult;
	
	//Disease
	private long[] diseaseId;
	private String[] diseaseDate;
	private long[] diseaseChecking;
	private long[] diseaseChildId;
	private long[] diseaseTypeId;
	private String[] diseaseLevel;
	private String[] diseaseOther;
	private String[] diseaseTradingName;
	private String[] diseaseCommonName;
	private String[] diseaseDangerousName;
	private String[] diseaseManDate;
	private String[] diseaseExpDate;
	private String[] diseaseSourceBuy;
	private String[] diseaseUseDate;
	private double[] diseaseUseRate1;
	private double[] diseaseUseRate2;
	private String[] diseaseRemark;
	private String[] diseaseCheckBy;
	private String[] diseaseResult;
	
	//Harvest
	private long[] harvestId;
	private String[] harvestDain;
	private String[] harvestDainRemark;
	private String[] harvestQuality;
	private String[] harvestFarmStatus;
	private String[] harvester;
	private String[] harvestCleaning;
	private String[] harvestPackaging;
	private String[] harvestMoving;
	private String[] harvestObservers;
	private String[] harvestObserversDate;
	private String[] harvestDainDate;
	private String[] harvestQualityDate;
	private String[] harvestFarmStatusDate;
	private String[] harvesterDate;
	private String[] harvestCleaningDate;
	private String[] harvestPackagingDate;
	private String[] harvestMovingDate;
	private double[] harvestTotal;
	private double[] harvestKeep;
	private double[] harvestSell;
	private double[] harvestSalePrice;
	private String[] harvestResult;
	private String[] harvestCheckBy;
	private String[] harvestRemark;
	
	//Delete
	private String[] delPreId;
	private String[] delPlaId;
	private String[] delManId;
	private String[] delMixId;
	private String[] delDisId;
	private String[] delHarId;
	
	private String cmd; 
	private String msg;

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

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public long getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(long checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckHour() {
		return checkHour;
	}

	public void setCheckHour(String checkHour) {
		this.checkHour = checkHour;
	}

	public String getCheckMinute() {
		return checkMinute;
	}

	public void setCheckMinute(String checkMinute) {
		this.checkMinute = checkMinute;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getViewImageStatus() {
		return viewImageStatus;
	}

	public void setViewImageStatus(String viewImageStatus) {
		this.viewImageStatus = viewImageStatus;
	}

	public FormFile getImageFormFile() {
		return imageFormFile;
	}

	public void setImageFormFile(FormFile imageFormFile) {
		this.imageFormFile = imageFormFile;
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

	public String getCoordinatesData() {
		return coordinatesData;
	}

	public void setCoordinatesData(String coordinatesData) {
		this.coordinatesData = coordinatesData;
	}

	public String[] getDelCooId() {
		return delCooId;
	}

	public void setDelCooId(String[] delCooId) {
		this.delCooId = delCooId;
	}

	public String[] getCoordinatesId() {
		return CoordinatesId;
	}

	public void setCoordinatesId(String[] CoordinatesId) {
		this.CoordinatesId = CoordinatesId;
	}

	public long getBreedTypeId() {
		return breedTypeId;
	}

	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	public long getRefPlantId() {
		return refPlantId;
	}

	public void setRefPlantId(long refPlantId) {
		this.refPlantId = refPlantId;
	}

	public long[] getPrepareSoilId() {
		return prepareSoilId;
	}

	public void setPrepareSoilId(long[] prepareSoilId) {
		this.prepareSoilId = prepareSoilId;
	}

	public String[] getPrepareSoilDate() {
		return prepareSoilDate;
	}

	public void setPrepareSoilDate(String[] prepareSoilDate) {
		this.prepareSoilDate = prepareSoilDate;
	}

	public String[] getPrepareSoilType() {
		return prepareSoilType;
	}

	public void setPrepareSoilType(String[] prepareSoilType) {
		this.prepareSoilType = prepareSoilType;
	}

	public String[] getPrepareSoilWater() {
		return prepareSoilWater;
	}

	public void setPrepareSoilWater(String[] prepareSoilWater) {
		this.prepareSoilWater = prepareSoilWater;
	}

	public String[] getPrepareSoilOperate() {
		return prepareSoilOperate;
	}

	public void setPrepareSoilOperate(String[] prepareSoilOperate) {
		this.prepareSoilOperate = prepareSoilOperate;
	}

	public String[] getPrepareSoilOperateOther() {
		return prepareSoilOperateOther;
	}

	public void setPrepareSoilOperateOther(String[] prepareSoilOperateOther) {
		this.prepareSoilOperateOther = prepareSoilOperateOther;
	}

	public String[] getPrepareSoilRemark() {
		return prepareSoilRemark;
	}

	public void setPrepareSoilRemark(String[] prepareSoilRemark) {
		this.prepareSoilRemark = prepareSoilRemark;
	}

	public String[] getPrepareSoilCheckBy() {
		return prepareSoilCheckBy;
	}

	public void setPrepareSoilCheckBy(String[] prepareSoilCheckBy) {
		this.prepareSoilCheckBy = prepareSoilCheckBy;
	}

	public String[] getPrepareSoilResult() {
		return prepareSoilResult;
	}

	public void setPrepareSoilResult(String[] prepareSoilResult) {
		this.prepareSoilResult = prepareSoilResult;
	}

	public long[] getPlantId() {
		return plantId;
	}

	public void setPlantId(long[] plantId) {
		this.plantId = plantId;
	}

	public String[] getPlantDate() {
		return plantDate;
	}

	public void setPlantDate(String[] plantDate) {
		this.plantDate = plantDate;
	}

	public String[] getPlantMethod() {
		return plantMethod;
	}

	public void setPlantMethod(String[] plantMethod) {
		this.plantMethod = plantMethod;
	}

	public String[] getPlantType() {
		return plantType;
	}

	public void setPlantType(String[] plantType) {
		this.plantType = plantType;
	}

	public long[] getPlantName() {
		return plantName;
	}

	public void setPlantName(long[] plantName) {
		this.plantName = plantName;
	}

	public String[] getPlantSource() {
		return plantSource;
	}

	public void setPlantSource(String[] plantSource) {
		this.plantSource = plantSource;
	}

	public String[] getPlantSowDate() {
		return plantSowDate;
	}

	public void setPlantSowDate(String[] plantSowDate) {
		this.plantSowDate = plantSowDate;
	}

	public String[] getPlantThrowDate() {
		return plantThrowDate;
	}

	public void setPlantThrowDate(String[] plantThrowDate) {
		this.plantThrowDate = plantThrowDate;
	}

	public double[] getPlantUse() {
		return plantUse;
	}

	public void setPlantUse(double[] plantUse) {
		this.plantUse = plantUse;
	}

	public String[] getPlantRemark() {
		return plantRemark;
	}

	public void setPlantRemark(String[] plantRemark) {
		this.plantRemark = plantRemark;
	}

	public String[] getPlantCheckBy() {
		return plantCheckBy;
	}

	public void setPlantCheckBy(String[] plantCheckBy) {
		this.plantCheckBy = plantCheckBy;
	}

	public String[] getPlantResult() {
		return plantResult;
	}

	public void setPlantResult(String[] plantResult) {
		this.plantResult = plantResult;
	}

	public long[] getManureId() {
		return manureId;
	}

	public void setManureId(long[] manureId) {
		this.manureId = manureId;
	}

	public String[] getManureDate() {
		return manureDate;
	}

	public void setManureDate(String[] manureDate) {
		this.manureDate = manureDate;
	}

	public long[] getManureType() {
		return manureType;
	}

	public void setManureType(long[] manureType) {
		this.manureType = manureType;
	}

	public String[] getManureOther() {
		return manureOther;
	}

	public void setManureOther(String[] manureOther) {
		this.manureOther = manureOther;
	}

	public String[] getManureStatus() {
		return manureStatus;
	}

	public void setManureStatus(String[] manureStatus) {
		this.manureStatus = manureStatus;
	}

	public String[] getManureName() {
		return manureName;
	}

	public void setManureName(String[] manureName) {
		this.manureName = manureName;
	}

	public String[] getManureFormula() {
		return manureFormula;
	}

	public void setManureFormula(String[] manureFormula) {
		this.manureFormula = manureFormula;
	}

	public String[] getManureBuyDate() {
		return manureBuyDate;
	}

	public void setManureBuyDate(String[] manureBuyDate) {
		this.manureBuyDate = manureBuyDate;
	}

	public String[] getManureSourceBuy() {
		return manureSourceBuy;
	}

	public void setManureSourceBuy(String[] manureSourceBuy) {
		this.manureSourceBuy = manureSourceBuy;
	}

	public double[] getManureUseRate() {
		return manureUseRate;
	}

	public void setManureUseRate(double[] manureUseRate) {
		this.manureUseRate = manureUseRate;
	}

	public double[] getManureTotalUse() {
		return manureTotalUse;
	}

	public void setManureTotalUse(double[] manureTotalUse) {
		this.manureTotalUse = manureTotalUse;
	}

	public String[] getManureRemark() {
		return manureRemark;
	}

	public void setManureRemark(String[] manureRemark) {
		this.manureRemark = manureRemark;
	}

	public String[] getManureCheckBy() {
		return manureCheckBy;
	}

	public void setManureCheckBy(String[] manureCheckBy) {
		this.manureCheckBy = manureCheckBy;
	}

	public String[] getManureResult() {
		return manureResult;
	}

	public void setManureResult(String[] manureResult) {
		this.manureResult = manureResult;
	}

	public long[] getMixedId() {
		return mixedId;
	}

	public void setMixedId(long[] mixedId) {
		this.mixedId = mixedId;
	}

	public String[] getMixedDate() {
		return mixedDate;
	}

	public void setMixedDate(String[] mixedDate) {
		this.mixedDate = mixedDate;
	}

	public long[] getMixedBreedType() {
		return mixedBreedType;
	}

	public void setMixedBreedType(long[] mixedBreedType) {
		this.mixedBreedType = mixedBreedType;
	}

	public long[] getMixedChildId() {
		return mixedChildId;
	}

	public void setMixedChildId(long[] mixedChildId) {
		this.mixedChildId = mixedChildId;
	}

	public long[] getMixedEliminateId() {
		return mixedEliminateId;
	}

	public void setMixedEliminateId(long[] mixedEliminateId) {
		this.mixedEliminateId = mixedEliminateId;
	}

	public String[] getMixedEliminateOther() {
		return mixedEliminateOther;
	}

	public void setMixedEliminateOther(String[] mixedEliminateOther) {
		this.mixedEliminateOther = mixedEliminateOther;
	}

	public String[] getMixedRemark() {
		return mixedRemark;
	}

	public void setMixedRemark(String[] mixedRemark) {
		this.mixedRemark = mixedRemark;
	}

	public String[] getMixedCheckBy() {
		return mixedCheckBy;
	}

	public void setMixedCheckBy(String[] mixedCheckBy) {
		this.mixedCheckBy = mixedCheckBy;
	}

	public String[] getMixedResult() {
		return mixedResult;
	}

	public void setMixedResult(String[] mixedResult) {
		this.mixedResult = mixedResult;
	}

	public long[] getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(long[] diseaseId) {
		this.diseaseId = diseaseId;
	}

	public String[] getDiseaseDate() {
		return diseaseDate;
	}

	public void setDiseaseDate(String[] diseaseDate) {
		this.diseaseDate = diseaseDate;
	}

	public long[] getDiseaseChecking() {
		return diseaseChecking;
	}

	public void setDiseaseChecking(long[] diseaseChecking) {
		this.diseaseChecking = diseaseChecking;
	}

	public long[] getDiseaseChildId() {
		return diseaseChildId;
	}

	public void setDiseaseChildId(long[] diseaseChildId) {
		this.diseaseChildId = diseaseChildId;
	}

	public long[] getDiseaseTypeId() {
		return diseaseTypeId;
	}

	public void setDiseaseTypeId(long[] diseaseTypeId) {
		this.diseaseTypeId = diseaseTypeId;
	}

	public String[] getDiseaseLevel() {
		return diseaseLevel;
	}

	public void setDiseaseLevel(String[] diseaseLevel) {
		this.diseaseLevel = diseaseLevel;
	}

	public String[] getDiseaseOther() {
		return diseaseOther;
	}

	public void setDiseaseOther(String[] diseaseOther) {
		this.diseaseOther = diseaseOther;
	}

	public String[] getDiseaseTradingName() {
		return diseaseTradingName;
	}

	public void setDiseaseTradingName(String[] diseaseTradingName) {
		this.diseaseTradingName = diseaseTradingName;
	}

	public String[] getDiseaseCommonName() {
		return diseaseCommonName;
	}

	public void setDiseaseCommonName(String[] diseaseCommonName) {
		this.diseaseCommonName = diseaseCommonName;
	}

	public String[] getDiseaseDangerousName() {
		return diseaseDangerousName;
	}

	public void setDiseaseDangerousName(String[] diseaseDangerousName) {
		this.diseaseDangerousName = diseaseDangerousName;
	}

	public String[] getDiseaseManDate() {
		return diseaseManDate;
	}

	public void setDiseaseManDate(String[] diseaseManDate) {
		this.diseaseManDate = diseaseManDate;
	}

	public String[] getDiseaseExpDate() {
		return diseaseExpDate;
	}

	public void setDiseaseExpDate(String[] diseaseExpDate) {
		this.diseaseExpDate = diseaseExpDate;
	}

	public String[] getDiseaseSourceBuy() {
		return diseaseSourceBuy;
	}

	public void setDiseaseSourceBuy(String[] diseaseSourceBuy) {
		this.diseaseSourceBuy = diseaseSourceBuy;
	}

	public String[] getDiseaseUseDate() {
		return diseaseUseDate;
	}

	public void setDiseaseUseDate(String[] diseaseUseDate) {
		this.diseaseUseDate = diseaseUseDate;
	}

	public double[] getDiseaseUseRate1() {
		return diseaseUseRate1;
	}

	public void setDiseaseUseRate1(double[] diseaseUseRate1) {
		this.diseaseUseRate1 = diseaseUseRate1;
	}

	public double[] getDiseaseUseRate2() {
		return diseaseUseRate2;
	}

	public void setDiseaseUseRate2(double[] diseaseUseRate2) {
		this.diseaseUseRate2 = diseaseUseRate2;
	}

	public String[] getDiseaseRemark() {
		return diseaseRemark;
	}

	public void setDiseaseRemark(String[] diseaseRemark) {
		this.diseaseRemark = diseaseRemark;
	}

	public String[] getDiseaseCheckBy() {
		return diseaseCheckBy;
	}

	public void setDiseaseCheckBy(String[] diseaseCheckBy) {
		this.diseaseCheckBy = diseaseCheckBy;
	}

	public String[] getDiseaseResult() {
		return diseaseResult;
	}

	public void setDiseaseResult(String[] diseaseResult) {
		this.diseaseResult = diseaseResult;
	}

	public long[] getHarvestId() {
		return harvestId;
	}

	public void setHarvestId(long[] harvestId) {
		this.harvestId = harvestId;
	}

	public String[] getHarvestDain() {
		return harvestDain;
	}

	public void setHarvestDain(String[] harvestDain) {
		this.harvestDain = harvestDain;
	}

	public String[] getHarvestDainRemark() {
		return harvestDainRemark;
	}

	public void setHarvestDainRemark(String[] harvestDainRemark) {
		this.harvestDainRemark = harvestDainRemark;
	}

	public String[] getHarvestQuality() {
		return harvestQuality;
	}

	public void setHarvestQuality(String[] harvestQuality) {
		this.harvestQuality = harvestQuality;
	}

	public String[] getHarvestFarmStatus() {
		return harvestFarmStatus;
	}

	public void setHarvestFarmStatus(String[] harvestFarmStatus) {
		this.harvestFarmStatus = harvestFarmStatus;
	}

	public String[] getHarvester() {
		return harvester;
	}

	public void setHarvester(String[] harvester) {
		this.harvester = harvester;
	}

	public String[] getHarvestCleaning() {
		return harvestCleaning;
	}

	public void setHarvestCleaning(String[] harvestCleaning) {
		this.harvestCleaning = harvestCleaning;
	}

	public String[] getHarvestPackaging() {
		return harvestPackaging;
	}

	public void setHarvestPackaging(String[] harvestPackaging) {
		this.harvestPackaging = harvestPackaging;
	}

	public String[] getHarvestMoving() {
		return harvestMoving;
	}

	public void setHarvestMoving(String[] harvestMoving) {
		this.harvestMoving = harvestMoving;
	}

	public String[] getHarvestObservers() {
		return harvestObservers;
	}

	public void setHarvestObservers(String[] harvestObservers) {
		this.harvestObservers = harvestObservers;
	}

	public String[] getHarvestObserversDate() {
		return harvestObserversDate;
	}

	public void setHarvestObserversDate(String[] harvestObserversDate) {
		this.harvestObserversDate = harvestObserversDate;
	}

	public String[] getHarvestDainDate() {
		return harvestDainDate;
	}

	public void setHarvestDainDate(String[] harvestDainDate) {
		this.harvestDainDate = harvestDainDate;
	}

	public String[] getHarvestQualityDate() {
		return harvestQualityDate;
	}

	public void setHarvestQualityDate(String[] harvestQualityDate) {
		this.harvestQualityDate = harvestQualityDate;
	}

	public String[] getHarvestFarmStatusDate() {
		return harvestFarmStatusDate;
	}

	public void setHarvestFarmStatusDate(String[] harvestFarmStatusDate) {
		this.harvestFarmStatusDate = harvestFarmStatusDate;
	}

	public String[] getHarvesterDate() {
		return harvesterDate;
	}

	public void setHarvesterDate(String[] harvesterDate) {
		this.harvesterDate = harvesterDate;
	}

	public String[] getHarvestCleaningDate() {
		return harvestCleaningDate;
	}

	public void setHarvestCleaningDate(String[] harvestCleaningDate) {
		this.harvestCleaningDate = harvestCleaningDate;
	}

	public String[] getHarvestPackagingDate() {
		return harvestPackagingDate;
	}

	public void setHarvestPackagingDate(String[] harvestPackagingDate) {
		this.harvestPackagingDate = harvestPackagingDate;
	}

	public String[] getHarvestMovingDate() {
		return harvestMovingDate;
	}

	public void setHarvestMovingDate(String[] harvestMovingDate) {
		this.harvestMovingDate = harvestMovingDate;
	}

	public double[] getHarvestTotal() {
		return harvestTotal;
	}

	public void setHarvestTotal(double[] harvestTotal) {
		this.harvestTotal = harvestTotal;
	}

	public double[] getHarvestKeep() {
		return harvestKeep;
	}

	public void setHarvestKeep(double[] harvestKeep) {
		this.harvestKeep = harvestKeep;
	}

	public double[] getHarvestSell() {
		return harvestSell;
	}

	public void setHarvestSell(double[] harvestSell) {
		this.harvestSell = harvestSell;
	}

	public double[] getHarvestSalePrice() {
		return harvestSalePrice;
	}

	public void setHarvestSalePrice(double[] harvestSalePrice) {
		this.harvestSalePrice = harvestSalePrice;
	}

	public String[] getHarvestResult() {
		return harvestResult;
	}

	public void setHarvestResult(String[] harvestResult) {
		this.harvestResult = harvestResult;
	}

	public String[] getHarvestCheckBy() {
		return harvestCheckBy;
	}

	public void setHarvestCheckBy(String[] harvestCheckBy) {
		this.harvestCheckBy = harvestCheckBy;
	}

	public String[] getHarvestRemark() {
		return harvestRemark;
	}

	public void setHarvestRemark(String[] harvestRemark) {
		this.harvestRemark = harvestRemark;
	}

	public String[] getDelPreId() {
		return delPreId;
	}

	public void setDelPreId(String[] delPreId) {
		this.delPreId = delPreId;
	}

	public String[] getDelPlaId() {
		return delPlaId;
	}

	public void setDelPlaId(String[] delPlaId) {
		this.delPlaId = delPlaId;
	}

	public String[] getDelManId() {
		return delManId;
	}

	public void setDelManId(String[] delManId) {
		this.delManId = delManId;
	}

	public String[] getDelMixId() {
		return delMixId;
	}

	public void setDelMixId(String[] delMixId) {
		this.delMixId = delMixId;
	}

	public String[] getDelDisId() {
		return delDisId;
	}

	public void setDelDisId(String[] delDisId) {
		this.delDisId = delDisId;
	}

	public String[] getDelHarId() {
		return delHarId;
	}

	public void setDelHarId(String[] delHarId) {
		this.delHarId = delHarId;
	}

	public void loadToBean(Object obj) {
		LandCheck lcObj = (LandCheck)obj;
		lcObj.setPlantYear(this.plantYear);
		lcObj.setPlantNo(this.plantNo);
		lcObj.setIdCard(this.idCard);
		lcObj.setFirstName(this.firstName);
		lcObj.setLastName(this.lastName);
		lcObj.setDocNo(this.docNo);
		lcObj.setTypeName(this.typeName);
		lcObj.setDocRai(this.docRai);
		lcObj.setDocNgan(this.docNgan);
		lcObj.setDocWah(this.docWah);
		lcObj.setCheckPeriodId(this.checkPeriod);
		lcObj.setCheckTime(this.checkHour+ ":" + this.checkMinute);
		lcObj.setResult(this.result);
		lcObj.setRemark(this.remark);
		lcObj.setCheckBy(this.checkBy);
		lcObj.setLastUpdateDate(this.lastUpdateDate);
		lcObj.setLastUpdateBy(this.lastUpdateBy);
		lcObj.setBreedTypeId(this.breedTypeId);
		lcObj.setRefPlantId(this.refPlantId);
		lcObj.setTypeId(this.typeId);
	}
	
	public void loadFromBean(Object obj) {
		if(obj !=null){
			LandCheck lcObj = (LandCheck)obj;
			this.landCheckId = lcObj.getLandCheckId();
			this.plantYear = lcObj.getPlantYear();
			this.plantNo = lcObj.getPlantNo();
			this.idCard = lcObj.getIdCard();
			this.firstName = lcObj.getFirstName();
			this.lastName = lcObj.getLastName();
			this.docNo = lcObj.getDocNo();
			this.typeName = lcObj.getTypeName();
			this.docRai = lcObj.getDocRai();
			this.docNgan = lcObj.getDocNgan();
			this.docWah = lcObj.getDocWah();
			this.checkPeriod = lcObj.getCheckPeriodId();
			this.checkDate = formatter.format(lcObj.getCheckDate());
			this.checkTime = lcObj.getCheckTime();
			this.result = lcObj.getResult();
			this.remark = lcObj.getRemark();
			this.checkBy = lcObj.getCheckBy();
			this.lastUpdateDate = lcObj.getLastUpdateDate();
			this.lastUpdateBy = lcObj.getLastUpdateBy();
			this.breedTypeId = lcObj.getBreedTypeId();
			this.refPlantId = lcObj.getRefPlantId();
			this.typeId = lcObj.getTypeId();
		}
	}
	
}
