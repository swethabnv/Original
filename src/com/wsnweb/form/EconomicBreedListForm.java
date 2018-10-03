package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class EconomicBreedListForm extends ListForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "breedTypeId";
	private String[] delbreedType;
	private String breedTypeId;
	private String breedTypeName;
	private String provinceName;

	public String getBreedTypeName() {
		return breedTypeName;
	}

	public void setBreedTypeName(String breedTypeName) {
		this.breedTypeName = breedTypeName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	private String cmd; 
	private String msg;
	
	
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
	
	public String[] getDelbreedType() {
		return delbreedType;
	}

	public void setDelbreedType(String[] delbreedType) {
		this.delbreedType = delbreedType;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setBreedTypeId(String breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	public String getBreedTypeId() {
		return breedTypeId;
	}

}
