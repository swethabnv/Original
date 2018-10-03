package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class FarmerGroupAddressListForm extends ListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5554363077059424418L;
	private String farmerGroupName;
	private String sortColumn = "farmerGroupName";
	private String[] delfarmerGroupName;
	
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setFarmerGroupName(String farmerGroupName) {
		this.farmerGroupName = farmerGroupName;
	}

	public String getFarmerGroupName() {
		return farmerGroupName;
	}

	public void setDelfarmerGroupName(String[] delfarmerGroupName) {
		this.delfarmerGroupName = delfarmerGroupName;
	}

	public String[] getDelfarmerGroupName() {
		return delfarmerGroupName;
	}

}
