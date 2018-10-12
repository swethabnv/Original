package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class ValidateNumber extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -7460975968362529915L;
	private String regExp = null;
	private int minValue = 0;
	private int maxValue = 0;
	

	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateNumber tag needs to be a child of ValidateForm!");

        String value = getPropertyValue();
        StringBuffer objBuf = null;
		try {
			String name = mstrName.replace('[','_');
			name = name.replace(']', '_');
			objBuf = new StringBuffer();
			objBuf.append("<input type=\"text\"");
			
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
			if (mstrStyle!=null)
				objBuf.append(" style=\""+mstrStyle+"\"");				
			if (mbolIsReadOnly) 
				objBuf.append(" readonly=\"readonly\"");
			objBuf.append(" onfocus=\"validate_"+objTmp.getFormName()+"_TempVar=this.value\"");
			objBuf.append(" onblur=\""+"validate_"+objTmp.getFormName()+"_"+name+"();"+"\"");
			if (mstrOnChange!=null)
				objBuf.append(" onchange=\""+mstrOnChange+"\"");
			objBuf.append(">");
			pageContext.getOut().print(objBuf);
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
            throw new JspException("Error: ValidateNumber is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateNumber getCopy() {
		ValidateNumber objItm = new ValidateNumber();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setRegExp(this.regExp);
    	objItm.setMaxValue(this.maxValue);
    	objItm.setMinValue(this.minValue);
    	return objItm;
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

	public String getRegExp() {
		return regExp;
	}

	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}	
}
