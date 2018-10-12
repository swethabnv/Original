package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class ValidateHidden extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -8865284869894920546L;

	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateHidden tag needs to be a child of ValidateForm!");

        String value = getPropertyValue();
        StringBuffer objBuf = null;

		try {
			objBuf = new StringBuffer();
			objBuf.append("<input type=\"hidden\"");

			if (mstrName!=null)
				objBuf.append(" name=\""+mstrName+"\"");
			if (value!=null)
				objBuf.append(" value=\""+value+"\"");

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
            throw new JspException("Error: ValidateHidden is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateHidden getCopy() {
		ValidateHidden objItm = new ValidateHidden();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	return objItm;
	}
}
