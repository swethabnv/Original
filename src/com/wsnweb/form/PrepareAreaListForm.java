package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class PrepareAreaListForm extends ListForm {


	private static final long serialVersionUID = 2791359044569081012L;
	private String sortColumn = "prepareAreaName";
	private String[] delPrepareArea;
	private String prepareAreaName;
	private long prepareAreaId;
	private long pprepareAreaId;
	private long breedTypeId;
	private long breedGroupId;
	private String lastUpdateDate;
	private String lastUpdateBy;
	

	private String cmd;

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	
	public long getPrepareAreaId() {
		return prepareAreaId;
	}

	public void setPrepareAreaId(long prepareAreaId) {
		this.prepareAreaId = prepareAreaId;
	}

	public long getPprepareAreaId() {
		return pprepareAreaId;
	}

	public void setPprepareAreaId(long pprepareAreaId) {
		this.pprepareAreaId = pprepareAreaId;
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

	public String[] getDelPrepareArea() {
		return delPrepareArea;
	}

	public void setDelPrepareArea(String[] delPrepareArea) {
		this.delPrepareArea = delPrepareArea;
	}

	public String getPrepareAreaName() {
		return prepareAreaName;
	}

	public void setPrepareAreaName(String prepareAreaName) {
		this.prepareAreaName = prepareAreaName;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	

}
