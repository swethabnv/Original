package com.dcs.dcswc.jsGrid;

import javax.servlet.jsp.JspException;

public class JSIndexColumn extends AJSColumn {
	private static final long serialVersionUID = -8777956556063945312L;
	
	private String linkUrl = null;
		
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
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
