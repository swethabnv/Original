package com.dcs.dcswc.jsGrid;

import javax.servlet.jsp.tagext.TagSupport;

public abstract class AJSColumn extends TagSupport implements IJSColumn{
	private String title = null;
	private String cssClass = null;		
	private String width = null;
	private String HAlign = null;
	private String VAlign = null;
	
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHAlign() {
		return HAlign;
	}
	public void setHAlign(String align) {
		HAlign = align;
	}
	public String getVAlign() {
		return VAlign;
	}
	public void setVAlign(String align) {
		VAlign = align;
	}	
}
