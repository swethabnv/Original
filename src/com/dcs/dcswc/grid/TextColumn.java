package com.dcs.dcswc.grid;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.jsp.JspException;


public final class TextColumn extends AColumnTag
{
	public static String defaultDateFmt = "dd/MM/yyyy";
	private static final long serialVersionUID = 3560083391583091727L;
	private int    mintMaxLength;
    private String mstrDataFormat;

    public TextColumn()
    {
        super();
        this.mintMaxLength = -1;
    }

/*------------------------------------------------------------------------------
 * Getters 
 *----------------------------------------------------------------------------*/
    public String getDataFormat()
    {
        return this.mstrDataFormat;
    }

    public int getMaxLength()
    {
        return this.mintMaxLength;
    }    

/*------------------------------------------------------------------------------
 * Setters 
 *----------------------------------------------------------------------------*/
    public void setDataFormat(String pstrDataFormat)
    {
        this.mstrDataFormat = pstrDataFormat;
    }

    public void setMaxLength(int pintMaxLen)
    {
        this.mintMaxLength = pintMaxLen;
    }

/*------------------------------------------------------------------------------
 * Overridden methods
 * @see javax.servlet.jsp.tagext.Tag
 *----------------------------------------------------------------------------*/

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
            throw new JspException("Error: Column is not a child of Grid", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }

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

    private String resolveFields(String pstrUrl) throws ClassCastException
    {
        int    intPos = 0;
        int    intEnd = 0;
        String strCol = null;
        String strRet = null;
        Grid objTmp = null;
        
        if (pstrUrl!=null && !pstrUrl.equals("")) {
	        strRet = pstrUrl;
	        objTmp = (Grid) getParent();
	        intPos = strRet.indexOf("{");
	        while (intPos >= 0)
	        {
	            intEnd = strRet.indexOf("}", intPos + 1);
	            if (intEnd != -1)
	            {
	                strCol = strRet.substring(intPos + 1, intEnd); 
	                strRet = strRet.substring(0, intPos) +
	                		objTmp.getColumnValue(strCol) + 
	                		strRet.substring(intEnd + 1);
	            }
	            intPos = strRet.indexOf("{", intPos +1);
	        }
        }
        return strRet;
    }
    
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
            
            if (this.mstrOnClick != null)
            	objBuf.append(" onclick=\"" + resolveFields(this.mstrOnClick) + "\"");
            
            if (this.mstrStyle != null)
            	objBuf.append(" style=\"" + resolveFields(this.mstrStyle) + "\"");
            
            objBuf.append(">");
            
            if (this.mstrLinkUrl!=null) {
	            objBuf.append("<a HREF=\"");
	            objBuf.append(resolveFields(this.mstrLinkUrl));
	            objBuf.append("\">");
            }
            
            strVal = formatField(pobjValue, this.mstrDataFormat);
            if (strVal != null && this.mintMaxLength > 0)
                if (strVal.length() > this.mintMaxLength)
                    strVal = strVal.substring(0, this.mintMaxLength) + "...";

            objBuf.append(strVal);
            if (this.mstrLinkUrl!=null) {
            	objBuf.append("</a>");
            }
            objBuf.append("</td>");

            // Write created HTML to output stream.
            this.pageContext.getOut().print(objBuf.toString());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: Anchorcolumn must be a child of DBGrid!", CCEx);
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

    private TextColumn getCopy()
    {
        TextColumn objRet = null;

        objRet = new TextColumn();
        super.copyAttributesTo(objRet);
        objRet.setDataFormat(this.mstrDataFormat);
        
        objRet.setId(this.getId());
        objRet.setPageContext(this.pageContext);
        objRet.setParent(this.getParent());
        return objRet;
    }
}
