/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : AColumnTag.java
 * CREATED: Jul 22, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package com.dcs.dcswc.grid;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class AColumnTag extends TagSupport	implements IColumnTag {
    protected String mstrWidth;
    protected int mintHeight;
    protected int mintBorder;

    protected String mstrName;
    protected String mstrBgColor;
    protected String mstrForeColor;
    protected String mstrCssClass;
    protected String mstrHAlign;
    protected String mstrVAlign;
    protected String mstrHeaderText;
    protected String mstrDataField;
    protected String mstrLinkUrl;
    protected String mstrStyle;
    protected String mstrOnClick;

    protected boolean mblnSortable;

    public AColumnTag()
    {
        super();
        this.mintHeight = -1;
        this.mintBorder = -1;
    }

/*------------------------------------------------------------------------------
 * Getters
 *----------------------------------------------------------------------------*/
    public String getWidth()
    {
        return this.mstrWidth;
    }

    public int getHeight()
    {
        return this.mintHeight;
    }

    public int getBorder()
    {
        return this.mintBorder;
    }

    public String getName()
    {
        return this.mstrName;
    }

    public String getBgColor()
    {
        return this.mstrBgColor;
    }

    public String getForeColor()
    {
        return this.mstrForeColor;
    }

    public String getCssClass()
    {
        return this.mstrCssClass;
    }

    public String getHAlign()
    {
        return this.mstrHAlign;
    }

    public String getVAlign()
    {
        return this.mstrVAlign;
    }

    public String getHeaderText()
    {
        return this.mstrHeaderText;
    }

    public String getDataField()
    {
        return this.mstrDataField;
    }

    public boolean getSortable()
    {
        return this.mblnSortable;
    }
    
    public String getLinkUrl()
    {
        return this.mstrLinkUrl;
    }
    
    public String getStyle()
    {
    	return this.mstrStyle;
    }
    
    public String getOnClick()
    {
        return this.mstrOnClick;
    }

/*------------------------------------------------------------------------------
 * Setters
 *----------------------------------------------------------------------------*/

    public void setWidth(String pstrWidth)
    {
        this.mstrWidth = pstrWidth;
    }

    public void setHeight(int pintHeight)
    {
        this.mintHeight = pintHeight;
    }

    public void setBorder(int pintBorder)
    {
        this.mintBorder = pintBorder;
    }

    public void setName(String pstrName)
    {
        this.mstrName = pstrName;
    }

    public void setBgColor(String pstrColor)
    {
        this.mstrBgColor = pstrColor;
    }

    public void setForeColor(String pstrColor)
    {
        this.mstrForeColor = pstrColor;
    }

    public void setCssClass(String pstrCssClass)
    {
        this.mstrCssClass = pstrCssClass;
    }

    public void setHAlign(String pstrHAlign)
    {
        this.mstrHAlign = pstrHAlign;
    }

    public void setVAlign(String pstrVAlign)
    {
        this.mstrVAlign = pstrVAlign;
    }

    public void setHeaderText(String pstrHdrText)
    {
        this.mstrHeaderText = pstrHdrText;
    }

    public void setDataField(String pstrDataField)
    {
        this.mstrDataField = pstrDataField;
    }

    public void setSortable(boolean pblnSortable)
    {
        this.mblnSortable = pblnSortable;
    }
    
    public void setLinkUrl(String pstrUrl)
    {
        this.mstrLinkUrl = pstrUrl;
    }
    
    public void setStyle(String pstrStyle)
    {
        this.mstrStyle = pstrStyle;
    }
    
    public void setOnClick(String pstrOnClick)
    {
        this.mstrOnClick = pstrOnClick;
    }

/*------------------------------------------------------------------------------
 * Methods
 *----------------------------------------------------------------------------*/

    public void renderHeader() throws JspException
    {
        String       strTxt    = null;
        Grid       objParent = null;
        StringBuffer objBuf    = null;

        try
        {
            if (this.mstrHeaderText == null)
                strTxt = "&nbsp;";
            else
                strTxt = this.mstrHeaderText;

            objParent = (Grid) this.getParent();
            objBuf = new StringBuffer();
            objBuf.append("<td");
            if (this.mstrWidth != null)
                objBuf.append(" WIDTH=\"" + this.mstrWidth + "\"");
            if (this.mintHeight > 0)
                objBuf.append(" HEIGHT=\"" + this.mintHeight + "\"");
            if (this.mstrCssClass != null)
                objBuf.append(" CLASS=\"" + this.mstrCssClass + "\"");
            else
                objBuf.append(" CLASS=\"gridHeader\"");
            if (this.mstrHAlign != null)
                objBuf.append(" ALIGN=\"" + this.mstrHAlign.toLowerCase() + "\"");
            if (this.mstrVAlign != null)
                objBuf.append(" VALIGN=\"" + this.mstrVAlign.toLowerCase() + "\"");

            if (this.mstrBgColor != null)
                objBuf.append(" BGCOLOR=\"" + this.mstrBgColor + "\"");
            if (this.mstrForeColor != null)
                objBuf.append(" COLOR=\"" + this.mstrForeColor + "\"");

            objBuf.append(">");
            if (this.mblnSortable && this.mstrDataField != null &&
                  objParent.supportSorting())
            {
                objBuf.append("<a HREF=\"javascript:doSort"+objParent.getName()+"('");
                objBuf.append(objParent.getValidateForm().getFormName() + "', '");
                objBuf.append(this.mstrDataField + "', ");
                if (this.mstrDataField.equals(objParent.getSortColumn()))
                {
                    if (objParent.getSortAscending())
                        objBuf.append("false");
                    else
                        objBuf.append("true");
                    objBuf.append(")\" CLASS=\"gridSort\">");
                    objBuf.append("<span>" + this.mstrHeaderText);
                    objBuf.append("&nbsp;<img SRC=\"");
                    if (objParent.getSortAscending())
                        objBuf.append(objParent.getAscendingImage());
                    else
                        objBuf.append(objParent.getDescendingImage());
                    objBuf.append("\" BORDER=0 ALIGN=\"absmiddle\"></span>");
                }
                else
                {
                	objBuf.append("true");
                    objBuf.append(")\" CLASS=\"gridSort\">");
                    objBuf.append(strTxt);
                }
                objBuf.append("</a>");
            }
            else
                objBuf.append(strTxt);

            objBuf.append("</td>");

            // Write created HTML to output stream.
            this.pageContext.getOut().print(objBuf.toString());
        }
        catch (IOException IOEx)
        {
            throw new JspException("Error: IOException while writing to client!", IOEx);
        }
        catch (Exception ex)
        {
            throw new JspException("Error: Exception while writing to client!", ex);
        }
        finally
        {
            if (objBuf != null) objBuf = null;
        }
    }

    public void renderBlank() throws JspException
    {
        StringBuffer objBuf = null;

        try
        {
            objBuf = new StringBuffer();
            objBuf.append("<td");
            if (this.mstrWidth != null)
                objBuf.append(" WIDTH=\"" + this.mstrWidth + "\"");
            if (this.mintHeight > 0)
                objBuf.append(" HEIGHT=\"" + this.mintHeight + "\"");
            if (this.mstrCssClass != null)
                objBuf.append(" CLASS=\"" + this.mstrCssClass + "\"");
            if (this.mstrHAlign != null)
                objBuf.append(" ALIGN=\"" + this.mstrHAlign.toLowerCase() + "\"");
            if (this.mstrVAlign != null)
                objBuf.append(" VALIGN=\"" + this.mstrVAlign.toLowerCase() + "\"");

            if (this.mstrBgColor != null)
                objBuf.append(" BGCOLOR=\"" + this.mstrBgColor + "\"");
            if (this.mstrForeColor != null)
                objBuf.append(" COLOR=\"" + this.mstrForeColor + "\"");

            objBuf.append(">&nbsp;</td>");

            // Write created HTML to output stream.
            this.pageContext.getOut().print(objBuf.toString());
        }
        catch (IOException IOEx)
        {
            throw new JspException("Error: IOException while writing to client!", IOEx);
        }
        catch (Exception ex)
        {
            throw new JspException("Error: Exception while writing to client!", ex);
        }
        finally
        {
            if (objBuf != null) objBuf = null;
        }
    }

    public void copyAttributesTo(IColumnTag pobjDest)
    {
        pobjDest.setBgColor(this.mstrBgColor);
        pobjDest.setBorder(this.mintBorder);
        pobjDest.setCssClass(this.mstrCssClass);
        pobjDest.setDataField(this.mstrDataField);
        pobjDest.setForeColor(this.mstrForeColor);
        pobjDest.setHAlign(this.mstrHAlign);
        pobjDest.setHeaderText(this.mstrHeaderText);
        pobjDest.setHeight(this.mintHeight);
        pobjDest.setName(this.mstrName);
        pobjDest.setSortable(this.mblnSortable);
        pobjDest.setVAlign(this.mstrVAlign);
        pobjDest.setWidth(this.mstrWidth);
        pobjDest.setLinkUrl(this.mstrLinkUrl);
        pobjDest.setStyle(this.mstrStyle);
        pobjDest.setOnClick(this.mstrOnClick);
    }
}