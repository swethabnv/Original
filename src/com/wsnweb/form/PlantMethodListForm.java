package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class PlantMethodListForm extends ListForm {


	private static final long serialVersionUID = 2791359044569081012L;
	private String sortColumn = "plantMethodName";
	private String[] delPlantMethod;
	private String plantMethodName;
	private long plantMethodId;
	private long breedTypeId;
	private long breedGroupId;
	

	private String cmd;

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	
	public long getPlantMethodId() {
		return plantMethodId;
	}

	public void setPlantMethodId(long plantMethodId) {
		this.plantMethodId = plantMethodId;
	}

	public long getBreedTypeId() {
		return breedTypeId;
	}

	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	public long getBreedGroupId() {
		return breedGroupId;
	}

	public void setBreedGroupId(long breedGroupId) {
		this.breedGroupId = breedGroupId;
	}

	public String[] getDelPlantMethod() {
		return delPlantMethod;
	}

	public void setDelPlantMethod(String[] delPlantMethod) {
		this.delPlantMethod = delPlantMethod;
	}

	public String getPlantMethodName() {
		return plantMethodName;
	}

	public void setPlantMethodName(String plantMethodName) {
		this.plantMethodName = plantMethodName;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
