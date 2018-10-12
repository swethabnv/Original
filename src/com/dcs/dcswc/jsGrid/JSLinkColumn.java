package com.dcs.dcswc.jsGrid;

import javax.servlet.jsp.JspException;

public class JSLinkColumn extends AJSColumn{
	private static final long serialVersionUID = -4097927810743581615L;
	
	private String text = null;
	private String linkUrl = null;
	
	
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
