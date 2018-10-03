package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class LandApproveListForm extends ListForm {

	private static final long serialVersionUID = 1L;
	
	private String[] delLandCheckId;
	private String idCard;
 	private String firstName;
 	private String lastName;
 	private String sortColumn = "idCard";
 	private String cmd;
	private String msg;
	private int plantYear;
	private int plantNo;
	private String startDate;
 	private String endDate;
 	private String approved;
 	private String approvedDate;
 	private String approvedBy;
 	private String farmerName;
 	private String landRight;
 	private String approveResult;
 	private String docNo;
 	private int plantId;
 	private int SEQ;
 	private int typeId;
	private long farmerGroupId; 
	private String farmerGroupName;
 	
	public String[] getDelLandCheckId() {
		return delLandCheckId;
	}
	public void setDelLandCheckId(String[] delLandCheckId) {
		this.delLandCheckId = delLandCheckId;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;		
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
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	public String getLandRight() {
		return landRight;
	}
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}
	public String getLinkApproved() {
		return "อนุมัติ";
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public int getPlantId() {
		return plantId;
	}
	public void setPlantId(int plantId) {
		this.plantId = plantId;
	}
	public int getSEQ() {
		return SEQ;
	}
	public void setSEQ(int seq) {
		SEQ = seq;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
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
