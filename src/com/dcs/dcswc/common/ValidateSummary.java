package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;

public class ValidateSummary extends TagSupport{
	private static final long serialVersionUID = -6044010508824369997L;
	private String mstrName = null;
	private String mstrCssClass = null;
	private String mstrProperty = null;
	
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
	
	public String getProperty() {
		return mstrProperty;
	}
	
	public void setProperty(String mstrProperty) {
		this.mstrProperty = mstrProperty;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateSummary tag needs to be a child of ValidateForm!");
        String value = getPropertyValue();
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
            objTmp.setValidateSummary(getCopy());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: ValidateSummary is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateSummary getCopy() {
		ValidateSummary objItm = new ValidateSummary();
    	objItm.setPageContext(this.pageContext);
    	objItm.setName(this.mstrName);
    	objItm.setCssClass(this.mstrCssClass);
    	return objItm;
	}
	
	public String getPropertyValue() {
		try {
			if (mstrProperty!=null && !"".equals(mstrProperty)) {
				Object obj = PropertyUtils.getProperty(getValidateForm(this).formBean, mstrProperty);
				if (obj instanceof String) {
					return (String)obj;
				} 
			}
			return null;
		} catch(Exception ex) {
			System.out.println("Error get property :"+mstrProperty);
			return null;
		}
	}
	
	public ValidateForm getValidateForm(Tag tag) {
		Tag parent = tag.getParent();
		if (parent instanceof ValidateForm) {
			return (ValidateForm) parent;
		} else if (parent == null) {
			return null;
		} else {
			return this.getValidateForm(parent);
		}
	}
}
