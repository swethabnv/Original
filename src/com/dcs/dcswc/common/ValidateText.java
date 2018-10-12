package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class ValidateText extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -7460975968362529915L;
	private String mstrRegExp = null;
	private int mintmaxlength = 0;	
	
	public String getRegExp() {
		return mstrRegExp;
	}
	
	public void setRegExp(String mstrRegExp) {
		this.mstrRegExp = mstrRegExp;
	}
	
	public int getMaxlength() {
		return mintmaxlength;
	}

	public void setMaxlength(int mintmaxlength) {
		this.mintmaxlength = mintmaxlength;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateText tag needs to be a child of ValidateForm!");

        String value = getPropertyValue();
        StringBuffer objBuf = null;
		try {
			String name = mstrName.replace('[','_');
			name = name.replace(']', '_');
			objBuf = new StringBuffer();
			objBuf.append("<input type=\"text\"");
			
			if (mintSize>0)
				objBuf.append(" size=\""+mintSize+"\"");
			if (mintmaxlength>0)
				objBuf.append(" maxlength=\""+mintmaxlength+"\"");
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
			if (mstrRegExp!=null)
				objBuf.append(" onblur=\""+"validate_"+objTmp.getFormName()+"_"+name+"();"+"\"");
			if (mstrOnChange!=null)
				objBuf.append(" onchange=\""+mstrOnChange+"\"");
			if (mbolIsReadOnly) 
				objBuf.append(" readonly=\"readonly\"");
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
            throw new JspException("Error: ValidateText is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateText getCopy() {
		ValidateText objItm = new ValidateText();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setRegExp(this.mstrRegExp);
    	objItm.setMaxlength(this.mintmaxlength);
    	return objItm;
	}	
}
