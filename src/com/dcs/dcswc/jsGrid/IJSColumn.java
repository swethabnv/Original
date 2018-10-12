package com.dcs.dcswc.jsGrid;

public interface IJSColumn {
	public String getCssClass();
	public void setCssClass(String cssClass);
	public String getTitle();
	public void setTitle(String title);
	public String getWidth();
	public void setWidth(String width);
    public String getHAlign();
    public String getVAlign();
    public void setHAlign(String pstrHAlign);
    public void setVAlign(String pstrVAlign);
}
