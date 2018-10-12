package com.dcs.dcswc.common;

import java.io.IOException;
import javax.servlet.jsp.JspException;

public class ValidateDayCheckBox extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = 5059542427302573785L;
	
	private static final String[] days = {"Su","Mo","Tu","We","Th","Fr","Sa"};
		
	public int doStartTag() throws JspException {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateDayCheckBox tag needs to be a child of ValidateForm!");

        StringBuffer objBuf = null;
		try {			
			objBuf = new StringBuffer();
			objBuf.append(writeCheckBoxList());
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public StringBuffer writeCheckBoxList() {
		StringBuffer objBuf = new StringBuffer();
	    String value = getPropertyValue();
	    if (value==null || value.length()!=7) {
	    	value="0000000";
	    }
	   
		try{
			objBuf.append("<INPUT type=\"hidden\" name=\""+mstrName+"\" value=\""+value+"\">");
			for (int i=0; i<days.length; i++) {
				String checked = "";
				if (!"0".equals(value.substring(i, i+1))) {
					checked = "checked=\"checked\"";
				}
				objBuf.append("<INPUT type=\"checkbox\" name=\""+mstrName+"_chk"+i+"\" "+checked+" onclick=\"updateDayCheckBox('"+mstrName+"')\">"+days[i]+"&nbsp;");
			}			
			objBuf.append("\n<SCRIPT language=\"JavaScript\">\n");
			objBuf.append("applyDayCheckBox('"+mstrName+"','"+value+"');\n");			
			objBuf.append("</SCRIPT>\n");
		} catch(Exception ex) {
		}
		return objBuf;
	}
	
	public int doEndTag() throws JspException {
        ValidateForm objTmp = null;
        try {
        	objTmp = getValidateForm(this);
            objTmp.addChild(getCopy());
        } catch (ClassCastException CCEx) {
            throw new JspException("Error: ValidateDayCheckBox is not a child of ValidateForm", CCEx);
        } finally {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateDayCheckBox getCopy() {
		ValidateDayCheckBox objItm = new ValidateDayCheckBox();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	return objItm;
	}
}
