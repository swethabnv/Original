package com.dcs.dcswc.common;

import java.io.IOException;
import javax.servlet.jsp.JspException;

import com.dcs.util.ParseUtil;

public class ValidateDayRadio extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = 820588225218443260L;
	private static final String[] days = {"Su","Mo","Tu","We","Th","Fr","Sa"};
		
	public int doStartTag() throws JspException {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateDayRadio tag needs to be a child of ValidateForm!");

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
	   
		try{
			//objBuf.append("<INPUT type=\"hidden\" name=\""+mstrName+"\" value=\""+value+"\">");
			for (int i=0; i<days.length; i++) {
				String checked = "";
				if (ParseUtil.parseInt(value)==i) {
					checked = "checked=\"checked\"";
				}
				objBuf.append("<INPUT type=\"radio\" name=\""+mstrName+"\" "+checked+" value=\""+i+"\">"+days[i]+"&nbsp;");
			}			
			//objBuf.append("\n<SCRIPT language=\"JavaScript\">\n");
			//objBuf.append("document.getElementById(\""+mstrName+"\").value=\""+value+"\";\n");			
			//objBuf.append("</SCRIPT>\n");
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
            throw new JspException("Error: ValidateDayRadio is not a child of ValidateForm", CCEx);
        } finally {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateDayRadio getCopy() {
		ValidateDayRadio objItm = new ValidateDayRadio();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	return objItm;
	}
}
