package com.dcs.dcswc.common;

import java.io.IOException;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;


public class ValidateCheckBox extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -8845764822571572066L;
	
	protected String mstrOnClick = null;
	
	public String getOnClick() {
		return mstrOnClick;
	}

	public void setOnClick(String mstrOnClick) {
		this.mstrOnClick = mstrOnClick;
	}

	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateCheckBox tag needs to be a child of ValidateForm!");

        boolean value = getPropertyBooleanValue();
        StringBuffer objBuf = null;

		try {
			objBuf = new StringBuffer();
			objBuf.append("<input type=\"checkbox\"");

			if (mstrName!=null)
				objBuf.append(" name=\""+mstrName+"\"");
			if (mstrOnChange!=null)
				objBuf.append(" onchange=\""+mstrOnChange+"\"");
			if (mstrOnClick!=null)
				objBuf.append(" onclick=\""+mstrOnClick+"\"");
			if (value)
				objBuf.append(" checked");

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
            throw new JspException("Error: ValidateCheckBox is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	public boolean getPropertyBooleanValue() {
		try {
			if (mstrProperty!=null && !"".equals(mstrProperty)) {
				Object obj = PropertyUtils.getProperty(getValidateForm(this).formBean, mstrProperty);
				if (obj instanceof Boolean) {
					return ((Boolean)obj).booleanValue();
				} 
			}
			return false;
		} catch(Exception ex) {
			System.out.println("Error get property :"+mstrProperty);
			return false;
		}
	}
	
	private ValidateCheckBox getCopy() {
		ValidateCheckBox objItm = new ValidateCheckBox();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setOnClick(this.mstrOnClick);
    	return objItm;
	}	
}
