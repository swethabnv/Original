package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;


public class ValidateTextArea extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = 7166741480946315544L;
	
	private int mintRows=0;
	private int mintCols=0;
	
	public int getCols() {
		return mintCols;
	}

	public void setCols(int mintCols) {
		this.mintCols = mintCols;
	}

	public int getRows() {
		return mintRows;
	}

	public void setRows(int mintRows) {
		this.mintRows = mintRows;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateTextArea tag needs to be a child of ValidateForm!");

        String value = getPropertyValue();
        StringBuffer objBuf = null;

		try {
			objBuf = new StringBuffer();
			objBuf.append("<textarea ");
			
			if (mintRows>0)
				objBuf.append(" rows=\""+mintRows+"\"");
			if (mintCols>0)
				objBuf.append(" cols=\""+mintCols+"\"");
			
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			if (mstrName!=null) {
				objBuf.append(" name=\""+mstrName+"\"");
				objBuf.append(" id=\""+mstrName+"\"");
			}
			if (mstrStyle!=null)
				objBuf.append(" style=\""+mstrStyle+"\"");
			if (mbolIsReadOnly) 
				objBuf.append(" readonly=\"readonly\"");
			
			objBuf.append(">");
			
			if (value!=null)
				objBuf.append(value);
			
			objBuf.append("</textarea>");
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
            throw new JspException("Error: ValidateTextArea is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateTextArea getCopy() {
		ValidateTextArea objItm = new ValidateTextArea();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	return objItm;
	}	
}
