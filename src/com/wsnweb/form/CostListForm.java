package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class CostListForm extends ListForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "costName";
	private String[] delCost;
	private long costId;
	private String costName;
	
	public String[] getDelCost() {
		return delCost;
	}

	public void setDelCost(String[] delCost) {
		this.delCost = delCost;
	}

	public long getCostId() {
		return costId;
	}

	public void setCostId(long costId) {
		this.costId = costId;
	}

	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
