package com.dcs.dcswc.grid;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import org.apache.commons.beanutils.PropertyUtils;

public final class CheckBoxColumn extends AColumnTag
{
	private static final long serialVersionUID = 4269818630991011588L;
	private static final String defaultDateFmt = "dd/MM/yyyy";
	private String mstrName;
	private Boolean mbolReadonly = false;
	private String dataSource;
	private String showText;
	
	public CheckBoxColumn()
    {
        super();
    }

/*------------------------------------------------------------------------------
 * Overridden methods
 * @see javax.servlet.jsp.tagext.Tag
 *----------------------------------------------------------------------------*/
    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException
    {
        Grid objTmp = null;

        try
        {
            objTmp = (Grid) getParent();
            objTmp.addColumn(getCopy());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: ImageColumn tag is not a child of Grid", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        if (!(this.getParent() instanceof Grid))
            throw new JspException("Error: Column tag needs to be a child of Grid!");

        // This tag does not have body contents.
        return SKIP_BODY;
    }

/*------------------------------------------------------------------------------
 * Methods 
 *----------------------------------------------------------------------------*/
    /* (non-Javadoc)
     * @see com.freeware.gridtag.IColumnTag#renderDetail()
     */
    public void renderDetail(Object pobjValue) throws JspException
    {
    	String       strVal = null;
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
            else
                objBuf.append(" CLASS=\"gridColumn\"");
            if (this.mstrHAlign != null)
                objBuf.append(" ALIGN=\"" + this.mstrHAlign.toLowerCase() + "\"");
            if (this.mstrVAlign != null)
                objBuf.append(" VALIGN=\"" + this.mstrVAlign.toLowerCase() + "\"");
            if (this.mstrBgColor != null)
                objBuf.append(" BGCOLOR=\"" + this.mstrBgColor + "\"");
            if (this.mstrForeColor != null)
                objBuf.append(" COLOR=\"" + this.mstrForeColor + "\"");

            objBuf.append(">");
            objBuf.append("<INPUT type=\"checkbox\" name=\""+mstrName+"\"");
            if (pobjValue instanceof Boolean) {
            	if ((Boolean)pobjValue) 
            	objBuf.append(" checked");
            } else {
            	objBuf.append(" value=\""+pobjValue.toString()+"\"");
            	Object currentItem = ((Grid)getParent()).getCurrentItem();
            	try {
	            	Boolean checkBox = (Boolean) PropertyUtils.getProperty(currentItem, "checkBox");
	            	if (checkBox) {
	            		objBuf.append(" checked");
	            	}
            	} catch(Exception ex) {          		
            	}
            }
            if (this.mbolReadonly)
            	objBuf.append(" disabled");
            objBuf.append(">");
            
            if(this.dataSource!=null)
            {
            	strVal = formatField(pobjValue, this.dataSource);
            	objBuf.append(strVal);
            }
            
            if(this.showText!=null)
            {
            	objBuf.append(this.showText);
            }
            
            objBuf.append("</td>");

            // Write created HTML to output stream.
            this.pageContext.getOut().print(objBuf.toString());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: Anchorcolumn must be a child of Grid!", CCEx);
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
    
    public void renderHeader() throws JspException
    {
        StringBuffer objBuf    = null;

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
            if (this.mstrHeaderText == null || "".equals(this.mstrHeaderText))
            	objBuf.append("<INPUT type=\"checkbox\" name=\"checkAll_"+mstrName+"\" onclick=\"checkAll(this, '"+mstrName+"');\"");
            else
            	objBuf.append(this.mstrHeaderText);
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

    private CheckBoxColumn getCopy()
    {
        CheckBoxColumn objRet = null;

        objRet = new CheckBoxColumn();
        super.copyAttributesTo(objRet);
        objRet.setId(this.getId());
        objRet.setName(this.mstrName);
        objRet.setReadonly(this.mbolReadonly);
        objRet.setPageContext(this.pageContext);
        objRet.setDataSource(this.dataSource);
        objRet.setShowText(this.showText);
        objRet.setParent(this.getParent());
        return objRet;
    }
    
    private String formatField(Object pobjVal, String pstrFmt) throws ClassCastException
    {
        String strRet = null;
        Format objFmt = null;

        try
        {
            if (pobjVal instanceof java.sql.Date || pobjVal instanceof java.util.Date || pobjVal instanceof java.sql.Timestamp)
            {
            	if (pstrFmt==null) pstrFmt=defaultDateFmt;
                objFmt = new SimpleDateFormat(pstrFmt, Locale.US);
                strRet = objFmt.format(pobjVal);
            }
            else if (pobjVal instanceof Number && pstrFmt!=null)
            {
                objFmt = new DecimalFormat(pstrFmt);
                strRet = objFmt.format(pobjVal);
            }
            else
                strRet = pobjVal.toString();
        }
        catch (NullPointerException NPExIgnore)
        {
        }
        catch (IllegalArgumentException IArgExIgnore)
        {
        }
        finally
        {
            if (objFmt != null) objFmt = null;
        }
        if (strRet == null) strRet = Grid.DEFAULT_NULLTEXT;
        return strRet;
    }

	public String getName() {
		return mstrName;
	}

	public void setName(String mstrName) {
		this.mstrName = mstrName;
	}

	public Boolean getReadonly() {
		return mbolReadonly;
	}

	public void setReadonly(Boolean mbolReadonly) {
		this.mbolReadonly = mbolReadonly;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getShowText() {
		return showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
	}
	
}
