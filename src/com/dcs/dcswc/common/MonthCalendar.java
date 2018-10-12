package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONArray;

public class MonthCalendar extends TagSupport {
	private static final long serialVersionUID = 1037922734413048128L;
	
	private String name = null;
	private int year = 0;
	private int month = 0;
	private long[] holidayList;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public long[] getHolidayList() {
		return holidayList;
	}

	public void setHolidayList(long[] holidayList) {
		this.holidayList = holidayList;
	}
	
	public int doStartTag() throws JspException {
        StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<div");

			if (this.name!=null)
				objBuf.append(" id=\""+this.name+"\"");

			objBuf.append(">");

			objBuf.append("</div>\n");
			objBuf.append("<SCRIPT language=\"JavaScript\">\n");
			objBuf.append("var "+this.name+" = new YearCalendarControl();\n");
			if (holidayList!=null && holidayList.length>0) {
				JSONArray jsHolidayList = JSONArray.fromObject(holidayList);
				objBuf.append("var "+this.name+"_holiday = "+jsHolidayList+";\n");
				objBuf.append(this.name+".setHoliday("+this.name+"_holiday);\n");
			}
			objBuf.append(this.name+".setDIV(document.all."+this.name+");\n");
			if (year>0) {
				objBuf.append(this.name+".setYear("+this.year+");\n");
			}
			objBuf.append(this.name+".setName('"+this.name+"');\n");
			objBuf.append(this.name+".writeCalendar();\n");
			objBuf.append("</SCRIPT>\n");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
