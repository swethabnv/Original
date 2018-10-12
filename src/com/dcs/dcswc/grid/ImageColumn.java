package com.dcs.dcswc.grid;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;



public final class ImageColumn extends AColumnTag
{
	private static final long serialVersionUID = 7014610519393549081L;

	public ImageColumn()
    {
        super();
    }

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
            
            
            
            try 
            {
            	String img = (String) pobjValue; 
            	if (this.mstrLinkUrl!=null) {
    	            objBuf.append("<a HREF=\"");
    	            objBuf.append(resolveFields(this.mstrLinkUrl));
    	            objBuf.append("\">");
                }
	            if (img.length()>0) {
	            	objBuf.append("<img src=\""+((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/"+img+"\" >");
	            }
	            if (this.mstrLinkUrl!=null) {
	            	objBuf.append("</a>");
	            }
            }
            catch(ClassCastException ex)
            {
            	objBuf.append(pobjValue);
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

    private ImageColumn getCopy()
    {
        ImageColumn objRet = null;

        objRet = new ImageColumn();
        super.copyAttributesTo(objRet);                
        objRet.setId(this.getId());
        objRet.setPageContext(this.pageContext);
        objRet.setParent(this.getParent());
        return objRet;
    }
}
