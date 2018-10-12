package com.dcs.dcswc.schedule;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONArray;

public class ResourceSchedule extends TagSupport{
	private static final long serialVersionUID = 2967002765379635936L;
	
	private int month;
	private int year;
	private int minMonth = 1;
	private int minYear = 0;
	private int maxMonth = 12;
	private int maxYear = 9999;
	private String mstrResourceHeader;
	private String mstrDetailHeader;
	private String mstrName;
	private String mstrCssClass;
	private String mstrWidth;
	private String onCreateSchedule;
	private String onUpdateSchedule;
	private String onMouseOverSchedule;
	private String onMouseOutSchedule;
	private String onMouseClickSchedule;
	private String onChangeMonth;
	private boolean allowPastScheduleDate;
	private String[] holiday = null;
	private List<Resource> mlstResource = null;
	private String mstrSchedule = null;
	
	public String[] getHoliday() {
		return holiday;
	}
	public void setHoliday(String[] holiday) {
		this.holiday = holiday;
	}
	public List getResource() {
		return mlstResource;
	}
	public void setResource(List<Resource> mlstResource) {
		this.mlstResource = mlstResource;
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	public String getCssClass() {
		return mstrCssClass;
	}
	public void setCssClass(String mstrCssClass) {
		this.mstrCssClass = mstrCssClass;
	}
	public String getName() {
		return mstrName;
	}
	public void setName(String mstrName) {
		this.mstrName = mstrName;
	}
	public int getMinMonth() {
		return minMonth;
	}
	public void setMinMonth(int minMonth) {
		this.minMonth = minMonth;
	}
	public int getMinYear() {
		return minYear;
	}
	public void setMinYear(int minYear) {
		this.minYear = minYear;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMaxMonth() {
		return maxMonth;
	}
	public void setMaxMonth(int maxMonth) {
		this.maxMonth = maxMonth;
	}
	public int getMaxYear() {
		return maxYear;
	}
	public void setMaxYear(int maxYear) {
		this.maxYear = maxYear;
	}
	public String getResourceHeader() {
		return mstrResourceHeader;
	}
	public void setResourceHeader(String mstrResourceHeader) {
		this.mstrResourceHeader = mstrResourceHeader;
	}
	public String getDetailHeader() {
		return mstrDetailHeader;
	}
	public void setDetailHeader(String mstrDetailHeader) {
		this.mstrDetailHeader = mstrDetailHeader;
	}
	public String getWidth() {
		return mstrWidth;
	}
	public void setWidth(String mstrWidth) {
		this.mstrWidth = mstrWidth;
	}
	public String getOnCreateSchedule() {
		return onCreateSchedule;
	}
	public void setOnCreateSchedule(String onCreateSchedule) {
		this.onCreateSchedule = onCreateSchedule;
	}
	public String getOnMouseOverSchedule() {
		return onMouseOverSchedule;
	}
	public void setOnMouseOverSchedule(String onMouseOverSchedule) {
		this.onMouseOverSchedule = onMouseOverSchedule;
	}
	public String getOnUpdateSchedule() {
		return onUpdateSchedule;
	}
	public void setOnUpdateSchedule(String onUpdateSchedule) {
		this.onUpdateSchedule = onUpdateSchedule;
	}
	public String getOnChangeMonth() {
		return onChangeMonth;
	}
	public void setOnChangeMonth(String onChangeMonth) {
		this.onChangeMonth = onChangeMonth;
	}
	public String getOnMouseOutSchedule() {
		return onMouseOutSchedule;
	}
	public void setOnMouseOutSchedule(String onMouseOutSchedule) {
		this.onMouseOutSchedule = onMouseOutSchedule;
	}
	public String getSchedule() {
		return mstrSchedule;
	}
	public void setSchedule(String mstrSchedule) {
		this.mstrSchedule = mstrSchedule;
	}	
	public int doStartTag() throws JspException {
		if (month<1 || month>12 || minMonth<1 || minMonth>12 || maxMonth<1 || maxMonth>12)
			throw new JspException("Error: Unable to write contents!");
		
		JspWriter objOut  = null;
		try {
			objOut = this.pageContext.getOut();
			objOut.println("<div id=\""+this.mstrName+"_div\"></div>");
			objOut.println(drawGridScript());
			
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
		return SKIP_BODY;
	}
	
	 public StringBuffer drawGridScript() throws JspException {
    	StringBuffer objBuf = null;
    	objBuf = new StringBuffer();
    	try {
    		Calendar calendar = Calendar.getInstance(Locale.US);
    		objBuf.append("<script language=\"JavaScript\">\n");
    		objBuf.append("var "+this.mstrName+"= new ResourceSchedule();\n");
    		objBuf.append(this.mstrName+".setPath(\""+((HttpServletRequest)pageContext.getRequest()).getContextPath()+"\");\n");
    		objBuf.append(this.mstrName+".setName(\""+this.mstrName+"\");\n");
    		objBuf.append(this.mstrName+".setMinYear("+this.minYear+");\n");
    		objBuf.append(this.mstrName+".setMaxYear("+this.maxYear+");\n");
    		objBuf.append(this.mstrName+".setMinMonth("+this.minMonth+");\n");
    		objBuf.append(this.mstrName+".setMaxMonth("+this.maxMonth+");\n");
    		objBuf.append(this.mstrName+".setYear("+this.year+");\n");
    		objBuf.append(this.mstrName+".setMonth("+this.month+");\n");
    		objBuf.append(this.mstrName+".setScheduleDiv("+this.mstrName+"_div);\n");
    		objBuf.append(this.mstrName+".setAllowPastScheduleDate("+this.allowPastScheduleDate+");\n");
    		objBuf.append(this.mstrName+".setCurrentDate("+calendar.get(Calendar.YEAR)+","+calendar.get(Calendar.MONTH)+","+calendar.get(Calendar.DATE)+");\n");
    		if (this.mstrWidth!=null) {
    			objBuf.append(this.mstrName+".setWidth(\""+this.mstrWidth+"\");\n");
    		}
    		if (this.onMouseOverSchedule!=null) {
    			objBuf.append(this.mstrName+".setOnMouseOverSchedule(\""+this.onMouseOverSchedule+"\");\n");
    		}
    		if (this.onMouseOutSchedule!=null) {
    			objBuf.append(this.mstrName+".setOnMouseOutSchedule(\""+this.onMouseOutSchedule+"\");\n");
    		}
    		if (this.onMouseClickSchedule!=null) {    			
    			objBuf.append(this.mstrName+".setOnMouseClickSchedule(\""+this.onMouseClickSchedule+"\");\n");
    		}
    		if (this.onCreateSchedule!=null) {
    			objBuf.append(this.mstrName+".setOnCreateSchedule(\""+this.onCreateSchedule+"\");\n");
    		}
    		if (this.onUpdateSchedule!=null) {
    			objBuf.append(this.mstrName+".setOnUpdateSchedule(\""+this.onUpdateSchedule+"\");\n");
    		}
    		if (this.onChangeMonth!=null) {
    			objBuf.append(this.mstrName+".setOnChangeMonth(\""+this.onChangeMonth+"\");\n");
    		}
    		
    		if (this.mstrResourceHeader!=null && this.mstrResourceHeader.length()>0)
    			objBuf.append(this.mstrName+".setResourceHeader(\""+this.mstrResourceHeader+"\");\n");
    		else
    			objBuf.append(this.mstrName+".setResourceHeader(\"&nbsp;\");\n");
    		
    		if (this.mstrDetailHeader!=null && this.mstrDetailHeader.length()>0)
    			objBuf.append(this.mstrName+".setDetailHeader(\""+this.mstrDetailHeader+"\");\n");
    		else
    			objBuf.append(this.mstrName+".setDetailHeader(\"\");\n");
    		
    		if (this.holiday!=null && this.holiday.length>0)
    			objBuf.append(this.mstrName+".setHoliday("+JSONArray.fromObject(this.holiday)+");\n");
    		else
    			objBuf.append(this.mstrName+".setHoliday([]);\n");
    		
    		if (this.mlstResource!=null && this.mlstResource.size()>0)
    			objBuf.append(this.mstrName+".setResource("+JSONArray.fromObject(this.mlstResource)+");\n");
    		else
    			objBuf.append(this.mstrName+".setResource([]);\n");
    		
    		if (this.mstrSchedule!=null && this.mstrSchedule.length()>0)
    			objBuf.append(this.mstrName+".setSchedule("+mstrSchedule+");\n");
    		else 
    			objBuf.append(this.mstrName+".setSchedule([]);\n");
    		objBuf.append(this.mstrName+".drawMonthView();\n");	    		
	    	objBuf.append("</script>\n");

    	} catch(Exception ex) {
    		throw new JspException("Error: Exception while writing to client!", ex);
    	}finally {
        }
    	return objBuf;
    }
	public String getOnMouseClickSchedule() {
		return onMouseClickSchedule;
	}
	public void setOnMouseClickSchedule(String onMouseClickSchedule) {
		this.onMouseClickSchedule = onMouseClickSchedule;
	}
	public boolean getAllowPastScheduleDate() {
		return allowPastScheduleDate;
	}
	public void setAllowPastScheduleDate(boolean allowPastScheduleDate) {
		this.allowPastScheduleDate = allowPastScheduleDate;
	}	
}
