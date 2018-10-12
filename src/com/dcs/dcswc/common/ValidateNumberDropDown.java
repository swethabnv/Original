package com.dcs.dcswc.common;

import java.io.IOException;
import javax.servlet.jsp.JspException;

public class ValidateNumberDropDown extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = 8001377497595853163L;
	private String onChange = null;
	private int minValue = 0;
	private int maxValue = 100;
	private int defaultValue = 0;
	
	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	
	public int getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
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
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateNumberDropDown tag needs to be a child of ValidateForm!");

        StringBuffer objBuf = null;
		try {			
			objBuf = new StringBuffer();
			objBuf.append("<select ");
			
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			if (mstrName!=null) {
				objBuf.append(" name=\""+mstrName+"\"");
				objBuf.append(" id=\""+mstrName+"\"");
			}
			if (mstrStyle!=null)
				objBuf.append(" style=\""+mstrStyle+"\"");
			if (onChange!=null)
				objBuf.append(" onchange=\""+onChange+"\"");
			
			objBuf.append(">\n");
			
			objBuf.append(writeDropDownItem());

			objBuf.append("</select>\n");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public StringBuffer writeDropDownItem() {
		StringBuffer objBuf = new StringBuffer();
	    String value = getPropertyValue();
	    if (value==null || "0".equals(value)) value=String.valueOf(defaultValue);
		try {
			for (int i=minValue; i<=maxValue; i++) {
				if (String.valueOf(i).equals(value)) {
					objBuf.append("<OPTION value=\""+i+"\" selected=\"selected\">");
				} else {
					objBuf.append("<OPTION value=\""+i+"\">");
				}
				objBuf.append(i+"</OPTION>\n");
			}
		} catch(Exception ex) {	
		}
		return objBuf;
	}
	
	
	
	public int doEndTag() throws JspException {
        ValidateForm objTmp = null;
        try {
        	objTmp = getValidateForm(this);
            objTmp.addChild(getCopy());
        }
        catch (ClassCastException CCEx) {
            throw new JspException("Error: ValidateNumberDropDown is not a child of ValidateForm", CCEx);
        }
        finally {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }	
	
	private ValidateNumberDropDown getCopy() {
		ValidateNumberDropDown objItm = new ValidateNumberDropDown();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);

    	objItm.setOnChange(this.onChange);

    	return objItm;
	}	
	
}
