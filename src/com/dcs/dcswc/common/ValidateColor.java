package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

public class ValidateColor extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = 6534379364501817576L;
	private String mstrColor = null;

	public String getColor() {
		return mstrColor;
	}
	
	public void setColor(String mstrColor) {
		this.mstrColor = mstrColor;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateColor tag needs to be a child of ValidateForm!");

        String value = getPropertyValue();
        StringBuffer objBuf = null;
        
		try {
			objBuf = new StringBuffer();
			objBuf.append("<input type=\"text\" readonly=\"readonly\"");
			
			if (mintSize>0)
				objBuf.append(" size=\""+mintSize+"\"");
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			if (mstrName!=null) {
				objBuf.append(" name=\""+mstrName+"\"");
				objBuf.append(" id=\""+mstrName+"\"");
			}
			if (value!=null)
				objBuf.append(" value=\""+value+"\"");
			else if (mstrColor!=null)
				objBuf.append(" value=\""+mstrColor+"\"");
			if (mstrStyle!=null)
				objBuf.append(" style=\""+mstrStyle+"\"");

			objBuf.append(">");
			objBuf.append("<img style=\"cursor:pointer;\" src=\""+((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/dcswc/images/color.gif\" onclick=\"showColor(document."+objTmp.getFormName()+"."+mstrName+")\">");
			if (value!=null && value.length()>0) {
				objBuf.append("<SCRIPT language=\"JavaScript\">");
				objBuf.append("writeColor(document."+objTmp.getFormName()+"."+mstrName+",\""+value+"\");");
				objBuf.append("</SCRIPT>");
			}
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public int doEndTag() throws JspException
    {
		ValidateForm objTmp = null;
        try
        {
        	objTmp = getValidateForm(this);
            objTmp.addChild(getCopy());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: ValidateColor is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateColor getCopy() {
		ValidateColor objItm = new ValidateColor();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setColor(this.mstrColor);
    	return objItm;
	}
}
