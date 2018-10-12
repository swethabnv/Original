package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import net.sf.json.JSONArray;

public class ValidateDate extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -5886321656267893729L;
	private String mstrDate = null;
	private boolean[] holiday = null;

	public String getDate() {
		return mstrDate;
	}
	
	public void setDate(String mstrDate) {
		this.mstrDate = mstrDate;
	}
	
	public boolean[] getHoliday() {
		return holiday;
	}

	public void setHoliday(boolean[] holiday) {
		this.holiday = holiday;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateText tag needs to be a child of ValidateForm!");

        String value = getPropertyValue();
        StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<input type=\"text\" readonly=\"readonly\"");
			
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
			else if (mstrDate!=null)
				objBuf.append(" value=\""+mstrDate+"\"");
			if (mstrStyle!=null)
				objBuf.append(" style=\""+mstrStyle+"\"");
			if (mstrOnChange!=null)
				objBuf.append(" onchange=\""+mstrOnChange+"\"");
			
			objBuf.append(">");			
			objBuf.append("<img border=\"0\" style=\"cursor:pointer;\" src=\""+((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/dcswc/images/calendar.jpg\"");
			if (!mbolIsReadOnly) 			
				//objBuf.append(" onclick=\"showCalendar(document."+objTmp.getFormName()+"."+mstrName+")\"");
				objBuf.append(" onclick=\"showCalendar(document.getElementById('"+mstrName+"'))\"");
			objBuf.append(" >\n");			
			String path = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
			objBuf.append("<SCRIPT language=\"JavaScript\">\n");
			if (holiday!=null && holiday.length>0) {
				JSONArray json = JSONArray.fromObject(holiday);
				objBuf.append("holidayList = "+ json+"\n");
				objBuf.append("calendarControl.setHoliday(holidayList);\n");
			}
			objBuf.append("calendarControl.setNextImage(\""+path+"/dcswc/images/Next.gif\");\n");
			objBuf.append("calendarControl.setPreviousImage(\""+path+"/dcswc/images/Previous.gif\");\n");				
			objBuf.append("</SCRIPT>");
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
            throw new JspException("Error: ValidateText is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateDate getCopy() {
		ValidateDate objItm = new ValidateDate();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setDate(this.mstrDate);
    	objItm.setHoliday(this.holiday);
    	return objItm;
	}	
}
