package com.dcs.dcswc.grid;

import javax.servlet.jsp.JspException;

interface IColumnTag
{
    public String getWidth();
    public int getHeight();
    public int getBorder();

    public String getName();
    public String getBgColor();
    public String getForeColor();
    public String getCssClass();
    public String getHAlign();
    public String getVAlign();
    public String getHeaderText();
    public String getDataField();
    public String getLinkUrl();
    public String getStyle();
    public String getOnClick();

    public boolean getSortable();

    public void setWidth(String pstrWidth);
    public void setHeight(int pintHeight);
    public void setBorder(int pintBorder);

    public void setName(String pstrName);
    public void setBgColor(String pstrColor);
    public void setForeColor(String pstrColor);
    public void setCssClass(String pstrCssClass);
    public void setHAlign(String pstrHAlign);
    public void setVAlign(String pstrVAlign);
    public void setHeaderText(String pstrHdrText);
    public void setDataField(String pstrField);
    public void setLinkUrl(String pstrLinkUrl);
    public void setStyle(String pstrStyle);
    public void setOnClick(String pstrOnClick);

    public void setSortable(boolean pblnSortable);

    public void renderDetail(Object pobjValue) throws JspException;
    public void renderHeader() throws JspException;
    public void renderBlank() throws JspException;

    public void copyAttributesTo(IColumnTag pobjDest);
    
}