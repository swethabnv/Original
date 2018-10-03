package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class LandCheckListForm extends ListForm {

	private static final long serialVersionUID = -7787169462559493017L;
	private String[] delLandCheckId;
	private String sortColumn = "idCard";
	private String cmd;
	private String msg;
	
	private int plantYear;
	private int plantNo;
	private String idCard;
 	private String firstName;
 	private String lastName;
 	private String startDate;
 	private String endDate;
	private long checkPeriodId;
 	private String checkPeriod;
	private String result;
	private long farmerGroupId; 
	private String farmerGroupName;

	public String[] getDelLandCheckId() {
		return delLandCheckId;
	}

	public void setDelLandCheckId(String[] delLandCheckId) {
		this.delLandCheckId = delLandCheckId;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public long getCheckPeriodId() {
		return checkPeriodId;
	}

	public void setCheckPeriodId(long checkPeriodId) {
		this.checkPeriodId = checkPeriodId;
	}

	public String getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(String checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public long getFarmerGroupId() {
		return farmerGroupId;
	}

	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}

	public String getFarmerGroupName() {
		return farmerGroupName;
	}

	public void setFarmerGroupName(String farmerGroupName) {
		this.farmerGroupName = farmerGroupName;
	}
}
