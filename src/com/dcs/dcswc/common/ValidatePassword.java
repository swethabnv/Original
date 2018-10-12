package com.dcs.dcswc.common;

import java.io.IOException;
import javax.servlet.jsp.JspException;

public class ValidatePassword extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -8135248369488765609L;
	
	private int mintmaxlength = 0;
	
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
            throw new JspException("Error: ValidatePassword tag needs to be a child of ValidateForm!");

        StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<input type=\"password\"");
			
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
			if (mstrStyle!=null)
				objBuf.append(" style=\""+mstrStyle+"\"");
			
			objBuf.append(">");			
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
            throw new JspException("Error: ValidatePassword is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidatePassword getCopy() {
		ValidatePassword objItm = new ValidatePassword();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setMaxlength(this.mintmaxlength);
    	return objItm;
	}
}
