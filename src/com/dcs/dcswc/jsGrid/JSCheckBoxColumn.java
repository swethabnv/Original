package com.dcs.dcswc.jsGrid;

import javax.servlet.jsp.JspException;

public class JSCheckBoxColumn extends AJSColumn{
	private static final long serialVersionUID = -8835411034957135172L;		
	
	private boolean editAble;
	
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
