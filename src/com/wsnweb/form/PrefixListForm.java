package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class PrefixListForm extends ListForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "abbrPrefix";
	private String[] delPrefix;
	private String prefix;
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String[] getDelPrefix() {
		return delPrefix;
	}

	public void setDelPrefix(String[] delPrefix) {
		this.delPrefix = delPrefix;
	}
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
