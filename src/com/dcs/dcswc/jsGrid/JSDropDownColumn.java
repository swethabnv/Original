package com.dcs.dcswc.jsGrid;

import javax.servlet.jsp.JspException;

public class JSDropDownColumn extends AJSColumn{
	private static final long serialVersionUID = 1733992631233375864L;
	
	private boolean editAble;
	private String optionList = null;
	private String optionListValue = null;
	private String onChange = null;
	private String dataField = null;
	
	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}
	
	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	
	public String getOptionList() {
		return optionList;
	}

	public void setOptionList(String optionList) {
		this.optionList = optionList;
	}
	
	public String getOptionListValue() {
		return optionListValue;
	}

	public void setOptionListValue(String optionListValue) {
		this.optionListValue = optionListValue;
	}
	
	public boolean getEditAble() {
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

        // This tag does not have body contents.
        return SKIP_BODY;
    }

}
