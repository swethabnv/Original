package com.dcs.dcswc.jsGrid;

import javax.servlet.jsp.JspException;

public class JSColumn extends AJSColumn{
	private static final long serialVersionUID = -4097927810743581615L;
	
	private String dataField = null;
	private boolean editAble;
	private String regExp = null;
	private String linkUrl = null;
	private String numberFormat = null;
	
	public String getDataField() {
		return dataField;
	}
	public void setDataField(String dataField) {
		this.dataField = dataField;
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
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
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
	
	public int doEndTag() throws JspException {
        JSGrid objTmp = null;
        try {
            objTmp = (JSGrid) getParent();
            objTmp.addColumn(this);
        } catch (ClassCastException CCEx) {
            throw new JspException("Error: Column is not a child of JsGrid", CCEx);
        } finally {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	public int doStartTag() throws JspException {
        if (!(this.getParent() instanceof JSGrid))
            throw new JspException("Error: Column tag needs to be a child of JsGrid!");
        return SKIP_BODY;
    }	
	
}
