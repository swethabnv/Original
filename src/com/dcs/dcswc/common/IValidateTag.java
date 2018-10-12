package com.dcs.dcswc.common;

public interface IValidateTag {
	public String getCssClass();
	public void setCssClass(String mstrCssClass);
	public String getStyle();
	public void setStyle(String mstrStyle);
	public String getName();
	public void setName(String mstrName);
	public String getProperty();
	public void setProperty(String mstrProperty);
	public int getSize();
	public void setSize(int mintSize);
	public boolean getIsRequire();
	public void setIsRequire(boolean mbolIsRequire);
	public String getLabel();
	public void setLabel(String mstrLabel);
	public String getOnChange();
	public void setOnChange(String mstrOnChange);
	public String getErrMessageKey();
	public void setErrMessageKey(String mstrErrMessageKey);
}
