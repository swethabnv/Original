package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class CloseDueListForm extends ListForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "plantYear";
	private String[] delCloseDue;
	private String plantYear;
	private String plantNo;
	private String farmerGroupId;
	
	public String[] getDelCloseDue() {
		return delCloseDue;
	}

	public void setDelCloseDue(String[] delCloseDue) {
		this.delCloseDue = delCloseDue;
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
	
	public String getFarmerGroupId() {
		return farmerGroupId;
	}

	public void setFarmerGroupId(String farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
