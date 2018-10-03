package com.wsnweb.form;

import java.util.Date;

import com.dcs.strut.exten.ListForm;

public class CooperativeGroupFarmerForm extends ListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 699207956962053755L;
	private long farmerGroupId;
	private String idCard;
	private Date effectiveDate;
	private String firstName;
	private String lastName;
	private String farmerName;
	private String farmerGroupName;
	private String farmerGroupType;
	private double target;
	private String sortColumn = "farmerName";
	private String[] delfarmerGroupName;
	private String[] delFarmer;
	private String cmd; 
	private String msg;
	
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
	public String getFarmerGroupType() {
		return farmerGroupType;
	}
	public void setFarmerGroupType(String farmerGroupType) {
		this.farmerGroupType = farmerGroupType;
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
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String[] getDelfarmerGroupName() {
		return delfarmerGroupName;
	}
	public void setDelfarmerGroupName(String[] delfarmerGroupName) {
		this.delfarmerGroupName = delfarmerGroupName;
	}
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
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
	
	
	public double getTarget() {
		return target;
	}
	public void setTarget(double target) {
		this.target = target;
	}
	public void setDelFarmer(String[] delFarmer) {
		this.delFarmer = delFarmer;
	}
	public String[] getDelFarmer() {
		return delFarmer;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}
	
	
}
