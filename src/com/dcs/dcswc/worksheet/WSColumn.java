package com.dcs.dcswc.worksheet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class WSColumn extends TagSupport{
	private static final long serialVersionUID = -1090981849963657640L;
	
	private String title = null;
	private String cssClass = null;	
	private boolean editAble = false;
	private boolean visible = true;
	private String formula = null;
	private String formulaField = null;
	private String dataField = null;
	private String width = null;
	private String regExp = null;
	private String regExpField = null;
	private String numberFormat = null;
	private String numberFormatField = null;
	private boolean calculateAll;
	private int minValue = 0;
	private int maxValue = 0;
	
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getDataField() {
		return dataField;
	}
	public void setDataField(String dataField) {
		this.dataField = dataField;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getNumberFormat() {
		return numberFormat;
	}
	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}
	public String getRegExp() {
		return regExp;
	}
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}
	
	public int doEndTag() throws JspException {
        WSGrid objTmp = null;
        try {
            objTmp = (WSGrid) getParent();
            objTmp.addColumn(getCopy());
        } catch (ClassCastException CCEx) {
            throw new JspException("Error: Column is not a child of WSGrid", CCEx);
        } finally {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	public WSColumn getCopy() {
		WSColumn col = new WSColumn();
		col.setTitle(this.title);
		col.setCssClass(this.cssClass);
		col.setEditAble(this.editAble);
		col.setVisible(this.visible);
		col.setFormula(this.formula);
		col.setFormulaField(this.formulaField);
		col.setDataField(this.dataField);
		col.setWidth(this.width);
		col.setRegExp(this.regExp);
		col.setRegExpField(this.regExpField);
		col.setNumberFormat(this.numberFormat);
		col.setNumberFormatField(this.numberFormatField);
		col.setCalculateAll(this.calculateAll);
		col.setMinValue(this.minValue);
		col.setMaxValue(this.maxValue);
		return col;
	}
	
	public int doStartTag() throws JspException {
        if (!(this.getParent() instanceof WSGrid))
            throw new JspException("Error: Column tag needs to be a child of WSGrid!");

        // This tag does not have body contents.
        return SKIP_BODY;
    }
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	public int getMinValue() {
		return minValue;
	}
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public String getFormulaField() {
		return formulaField;
	}
	public void setFormulaField(String formulaField) {
		this.formulaField = formulaField;
	}
	public String getNumberFormatField() {
		return numberFormatField;
	}
	public void setNumberFormatField(String numberFormatField) {
		this.numberFormatField = numberFormatField;
	}
	public String getRegExpField() {
		return regExpField;
	}
	public void setRegExpField(String regExpField) {
		this.regExpField = regExpField;
	}
	public boolean getCalculateAll() {
		return calculateAll;
	}
	public void setCalculateAll(boolean calculateAll) {
		this.calculateAll = calculateAll;
	}
}
