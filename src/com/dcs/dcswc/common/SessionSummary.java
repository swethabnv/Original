package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SessionSummary extends TagSupport{
	private static final long serialVersionUID = -1422722397083403084L;
	private String mstrName = null;
	private String mstrCssClass = null;
	private String attribute = null;
	private boolean autoRemove = true;
	
	public String getCssClass() {
		return mstrCssClass;
	}
	
	public void setCssClass(String mstrCssClass) {
		this.mstrCssClass = mstrCssClass;
	}
	
	public String getName() {
		return mstrName;
	}
	
	public void setName(String mstrName) {
		this.mstrName = mstrName;
	}
	
	public boolean getAutoRemove() {
		return autoRemove;
	}

	public void setAutoRemove(boolean autoRemove) {
		this.autoRemove = autoRemove;
	}
	
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	public int doStartTag() throws JspException
    {
        String value = (String) pageContext.getSession().getAttribute(this.getAttribute());
        StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<div");
			
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			if (mstrName!=null)
				objBuf.append(" id=\""+mstrName+"\"");

			objBuf.append(">");
			if (value!=null)
				objBuf.append(value);
			objBuf.append("</div>");
			pageContext.getOut().println(objBuf);
			
			if (this.getAutoRemove()) {
				 pageContext.getSession().removeAttribute(this.getAttribute());
			}
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public int doEndTag() throws JspException
    {        
        return EVAL_PAGE;
    }
	

	
	

	

	
}
