package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class Guage extends TagSupport {
	private static final long serialVersionUID = -2843643826043740719L;
	
	private String name = null;
	private int width = 0;
	private int value = 0;
	private int maxValue = 0;
	private int height=20;
	private int markValue = 0;
	private String color = "blue";
	private String underMarkColor = "red";
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMarkValue() {
		return markValue;
	}

	public void setMarkValue(int markValue) {
		this.markValue = markValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public String getUnderMarkColor() {
		return underMarkColor;
	}

	public void setUnderMarkColor(String underMarkColor) {
		this.underMarkColor = underMarkColor;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	
	public int doStartTag() throws JspException {
        StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<table");

			if (this.name!=null)
				objBuf.append(" id=\""+this.name+"\"");
			if (this.width>0)
				objBuf.append(" width=\""+this.width+"\"");

			objBuf.append( "cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolor=\"black\">");
			objBuf.append("<tr>");
			if (value==0) {
				objBuf.append("<td height=\""+height+"\" width=\"100%\"></td>");
			} else if (value==maxValue){
				objBuf.append("<td height=\""+height+"\" bgcolor=\""+color+"\" width=\"100%\"></td>");
			} else {
				int w1 = (width*value/maxValue)-1;
				int w2 = width-2-w1;
				String c = color;
				if (value<markValue) {
					c = underMarkColor;
				}
				objBuf.append("<td height=\""+height+"\" bgcolor=\""+c+"\" width=\""+w1+"\"></td>");
				objBuf.append("<td width=\""+w2+"\"></td>");
			}			
			
			objBuf.append("</tr>");
			objBuf.append("</table>\n");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
}
