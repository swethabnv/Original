package com.dcs.dcswc.grid;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import org.apache.commons.beanutils.PropertyUtils;

public final class CoditionLinkColumn extends AColumnTag
{
	private static final long serialVersionUID = 3560083391583091727L;
	private int    mintMaxLength;
	private String linkShow = null;
	private String linkTo = null;
	
    public CoditionLinkColumn()
    {
        super();
        this.mintMaxLength = -1;
    }

/*------------------------------------------------------------------------------
 * Getters 
 *----------------------------------------------------------------------------*/

    public int getMaxLength()
    {
        return this.mintMaxLength;
    }    
    public String getLinkShow() {
		return linkShow;
	}
	public String getLinkTo() {
		return linkTo;
	}
/*------------------------------------------------------------------------------
 * Setters 
 *----------------------------------------------------------------------------*/

    public void setMaxLength(int pintMaxLen)
    {
        this.mintMaxLength = pintMaxLen;
    }
    public void setLinkShow(String linkShow) {
		this.linkShow = linkShow;
	}
    public void setLinkTo(String linkTo) {
		this.linkTo = linkTo;
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
            
            if (this.linkShow!=null && this.linkTo!=null) {
            	String[] linkShows = getPropertyValues(this.linkShow);
            	String[] linkTos = getPropertyValues(this.linkTo);
            	for(int i=0;i<linkShows.length;i++)
            	{
            		objBuf.append("<a HREF=\"");
     	            objBuf.append(linkTos[i]);
     	            objBuf.append("\">");
     	            strVal = linkShows[i];
     	            objBuf.append(strVal);
     	            objBuf.append("</a>  ");
            	}
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
    
    public String[] getPropertyValues(String variable) {
		try {
			if (variable!=null && !"".equals(variable)) {
				Grid grid = (Grid) this.getParent();
				Object[] objs = (Object[]) PropertyUtils.getProperty(grid.getCurrentItem(), variable);
				if (objs instanceof String[]) {
					return (String[])objs;
				} else if (objs instanceof Long[]) {
					String[] tmp = new String[objs.length];
					for(int i=0; i<objs.length; i++) {
						tmp[i]=((Long)objs[i]).toString();
					}
					return tmp;
				} else if (objs instanceof Integer[]) {
					String[] tmp = new String[objs.length];
					for(int i=0; i<objs.length; i++) {
						tmp[i]=((Integer)objs[i]).toString();
					}
					return tmp;
				}
			}
			return null;
		} catch(Exception ex) {
			System.out.println("Error get property :"+variable);
			return null;
		}
	}

    private CoditionLinkColumn getCopy()
    {
        CoditionLinkColumn objRet = null;

        objRet = new CoditionLinkColumn();
        super.copyAttributesTo(objRet);
        
        objRet.setId(this.getId());
        objRet.setPageContext(this.pageContext);
        objRet.setLinkShow(this.linkShow);
        objRet.setLinkTo(this.linkTo);
        objRet.setParent(this.getParent());
        return objRet;
    }
}
