package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class BreedGroupListForm extends ListForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "breedGroupName";
	private String[] delBreedGroup;
	private long breedTypeId;
	private long breedGroupId;
	private String breedGroupName;
	

	public long getBreedTypeId() {
		return breedTypeId;
	}

	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setBreedGroupId(long breedGroupId) {
		this.breedGroupId = breedGroupId;
	}

	public long getBreedGroupId() {
		return breedGroupId;
	}

	public void setBreedGroupName(String breedGroupName) {
		this.breedGroupName = breedGroupName;
	}

	public String getBreedGroupName() {
		return breedGroupName;
	}

	public void setDelBreedGroup(String[] delBreedGroup) {
		this.delBreedGroup = delBreedGroup;
	}

	public String[] getDelBreedGroup() {
		return delBreedGroup;
	}

}
