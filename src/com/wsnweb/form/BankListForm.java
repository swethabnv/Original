package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class BankListForm extends ListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5997124982081629930L;
	private String sortColumn = "bankName";
	private String[] delBankId;
	private String bankName;
	
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String[] getDelBankId() {
		return delBankId;
	}

	public void setDelBankId(String[] delBankId) {
		this.delBankId = delBankId;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
