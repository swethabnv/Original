package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class ValidateFile extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -2922758814938141238L;
	
	private String mstrFileExt = null;
	
	public String getFileExt() {
		return mstrFileExt;
	}
	
	public void setFileExt(String mstrFileExt) {
		this.mstrFileExt = mstrFileExt;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateFile tag needs to be a child of ValidateForm!");

        String value = getPropertyValue();
        StringBuffer objBuf = null;
		try {
			String name = mstrName.replace('[','_');
			name = name.replace(']', '_');
			objBuf = new StringBuffer();
			objBuf.append("<input type=\"file\"");
			
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
			if (mstrFileExt!=null)
				objBuf.append(" onblur=\""+"validate_"+objTmp.getFormName()+"_"+name+"();"+"\"");
			
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
            throw new JspException("Error: ValidateFile is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateFile getCopy() {
		ValidateFile objItm = new ValidateFile();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setFileExt(this.mstrFileExt);
    	return objItm;
	}
}
