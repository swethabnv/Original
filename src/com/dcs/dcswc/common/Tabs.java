package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class Tabs extends BodyTagSupport {
	private static final long serialVersionUID = -4079822785135909452L;

	private String mstrName = null;
	private String mstrTitle = null;
	private int titleWidth = 150;
	
	public String getName() {
		return mstrName;
	}

	public void setName(String mstrName) {
		this.mstrName = mstrName;
	}
	
	public String getTitle() {
		return mstrTitle;
	}

	public void setTitle(String mstrTitle) {
		this.mstrTitle = mstrTitle;
	}
	
	public int getTitleWidth() {
		return titleWidth;
	}

	public void setTitleWidth(int titleWidth) {
		this.titleWidth = titleWidth;
	}
	
	public int doStartTag() throws JspException {
		WindowsTabs objTmp = null;
		try {
			objTmp = (WindowsTabs) getParent();
			pageContext.getOut().println("<div id=\""+mstrName+"_div\" class=\"windows\" style=\"display: none; width="+(objTmp.getWidth()-70)+"\" align=\"center\">\n");			
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write tabs contents!", IOEx);
        }
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		WindowsTabs objTmp = null;
		try {
			pageContext.getOut().println("</div>\n");
			objTmp = (WindowsTabs) getParent();
            objTmp.addTabs(getCopy());
            release();
		} catch (ClassCastException CCEx) {
            throw new JspException("Error: Tabs is not a child of WindowsTabs", CCEx);
        } catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write tabs contents!", IOEx);
        }
		return EVAL_PAGE;
	}
	
	public void release() {
		try {
		    mstrTitle = null;
		    mstrName = null;
			super.release();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Tabs getCopy() {
		Tabs objItm = new Tabs();
    	objItm.setPageContext(this.pageContext);
    	objItm.setName(this.mstrName);
    	objItm.setTitle(this.mstrTitle);
    	objItm.setTitleWidth(this.titleWidth);
    	return objItm;
	}
}
