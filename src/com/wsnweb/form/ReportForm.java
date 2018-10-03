package com.wsnweb.form;
import org.apache.struts.action.ActionForm;
public class ReportForm extends ActionForm {

	private static final long serialVersionUID = -105045553168509866L;
	
	private long plantYear;
	private long plantNo;
	private String endDate;
	private int forecastMonthStart;
	private int forecastYearStart;
	private int forecastMonthEnd;
	private int forecastYearEnd;
	private long provinceNo;
	private long regionNo;
	private long breedTypeId;
	private long breedType;
	private long breedGroup;
	private String breedTypeItem;
	private long cooperativeId;
	private long farmerGroupId;
	private String[] season;
	private String[] fta;
	private String[] objective;
	private String[] qualification;
	private int humidType;
	
	private String cmd; 
	private String msg;
	private String rep;
	
	public long getPlantYear() {
		return plantYear;
	}
	public void setPlantYear(long plantYear) {
		this.plantYear = plantYear;
	}
	public long getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(long plantNo) {
		this.plantNo = plantNo;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getForecastMonthStart() {
		return forecastMonthStart;
	}
	public void setForecastMonthStart(int forecastMonthStart) {
		this.forecastMonthStart = forecastMonthStart;
	}
	public int getForecastYearStart() {
		return forecastYearStart;
	}
	public void setForecastYearStart(int forecastYearStart) {
		this.forecastYearStart = forecastYearStart;
	}
	public int getForecastMonthEnd() {
		return forecastMonthEnd;
	}
	public void setForecastMonthEnd(int forecastMonthEnd) {
		this.forecastMonthEnd = forecastMonthEnd;
	}
	public int getForecastYearEnd() {
		return forecastYearEnd;
	}
	public void setForecastYearEnd(int forecastYearEnd) {
		this.forecastYearEnd = forecastYearEnd;
	}
	public long getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(long provinceNo) {
		this.provinceNo = provinceNo;
	}
	public long getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(long regionNo) {
		this.regionNo = regionNo;
	}
	public long getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}
	public long getBreedType() {
		return breedType;
	}
	public void setBreedType(long breedType) {
		this.breedType = breedType;
	}
	public long getBreedGroup() {
		return breedGroup;
	}
	public void setBreedGroup(long breedGroup) {
		this.breedGroup = breedGroup;
	}
	public String getBreedTypeItem() {
		return breedTypeItem;
	}
	public void setBreedTypeItem(String breedTypeItem) {
		this.breedTypeItem = breedTypeItem;
	}
	public long getCooperativeId() {
		return cooperativeId;
	}
	public void setCooperativeId(long cooperativeId) {
		this.cooperativeId = cooperativeId;
	}
	public long getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	public String[] getSeason() {
		return season;
	}
	public void setSeason(String[] season) {
		this.season = season;
	}
	public String[] getFta() {
		return fta;
	}
	public void setFta(String[] fta) {
		this.fta = fta;
	}
	public String[] getObjective() {
		return objective;
	}
	public void setObjective(String[] objective) {
		this.objective = objective;
	}
	public String[] getQualification() {
		return qualification;
	}
	public void setQualification(String[] qualification) {
		this.qualification = qualification;
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
	public String getRep() {
		return rep;
	}
	public void setRep(String rep) {
		this.rep = rep;
	}
	public int getHumidType() {
		return humidType;
	}
	public void setHumidType(int humidType) {
		this.humidType = humidType;
	}
	
}