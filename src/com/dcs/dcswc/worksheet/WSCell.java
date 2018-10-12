package com.dcs.dcswc.worksheet;

import java.io.Serializable;

public class WSCell implements Serializable{
	private static final long serialVersionUID = -5736434053722333974L;
	
	private boolean editAble = false;
	private String formula = null;
	private String numberFormat = null;
	private String value = null;
	private String loadValue = null;
	
	public boolean getEditAble() {
		return editAble;
	}
	public boolean isEditAble() {
		return editAble;
	}
	public void setEditAble(boolean editAble) {
		this.editAble = editAble;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLoadValue() {
		return loadValue;
	}
	public void setLoadValue(String loadValue) {
		this.loadValue = loadValue;
	}
	public String getNumberFormat() {
		return numberFormat;
	}
	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}
}
