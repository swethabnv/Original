package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class BreedTypeListForm extends ListForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "breedTypeName";
	private String[] delBreedType;
	private long breedTypeId;
	private String breedTypeName;
	
	public String[] getDelBreedType() {
		return delBreedType;
	}

	public void setDelBreedType(String[] delBreedType) {
		this.delBreedType = delBreedType;
	}

	public long getBreedTypeId() {
		return breedTypeId;
	}

	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	public String getBreedTypeName() {
		return breedTypeName;
	}

	public void setBreedTypeName(String breedTypeName) {
		this.breedTypeName = breedTypeName;
	}
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
