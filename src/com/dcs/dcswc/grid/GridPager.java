package com.dcs.dcswc.grid;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public final class GridPager extends TagSupport
{
	private static final long serialVersionUID = 3215655787211874148L;
	private String mstrImgFirst;
    private String mstrImgPrevious;
    private String mstrImgNext;
    private String mstrImgLast;

    public GridPager()
    {
        super();        
        this.mstrImgFirst = "/dcswc/images/First.gif";
        this.mstrImgPrevious = "/dcswc/images/Previous.gif";
        this.mstrImgNext = "/dcswc/images/Next.gif";
        this.mstrImgLast = "/dcswc/images/Last.gif";
    }

/*------------------------------------------------------------------------------
 * Getters
 *----------------------------------------------------------------------------*/
    public String getImgFirst()
    {
        return this.mstrImgFirst;
    }

    public String getImgPrevious()
    {
        return this.mstrImgPrevious;
    }

    public String getImgNext()
    {
        return this.mstrImgNext;
    }

    public String getImgLast()
    {
        return this.mstrImgLast;
    }

/*------------------------------------------------------------------------------
 * Setters
 *----------------------------------------------------------------------------*/
    public void setImgFirst(String pstrImgFirst)
    {
        this.mstrImgFirst = pstrImgFirst;
    }

    public void setImgPrevious(String pstrImgPreviosu)
    {
        this.mstrImgPrevious = pstrImgPreviosu;
    }

    public void setImgNext(String pstrImgNext)
    {
        this.mstrImgNext = pstrImgNext;
    }

    public void setImgLast(String pstrImgLast)
    {
        this.mstrImgLast = pstrImgLast;
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
            objTmp.setPager(getCopy());
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
    public static int calcTotPage(int totRec, int pageSize) {
    	if ((totRec % pageSize) == 0)
            return totRec / pageSize;
        else
            return (totRec / pageSize) + 1;
    }
    
    
    public void renderPager(int printCurrPage, int printTotRec, int printPageSize) throws JspException
    {
        StringBuffer objBuf = null;

        try
        {
        	Grid objTmp = (Grid) getParent();
            int totPage = calcTotPage(printTotRec, printPageSize);

	        objBuf = new StringBuffer();
	        
	        objBuf.append("<table BORDER=0 CELLSPACING=0 CELLPADDING=0 ");
	        objBuf.append("WIDTH=\"100%\">\r\n");
	        objBuf.append("<tr CLASS=\"gridPager\">\r\n");
	        if (printTotRec>0) {
		        objBuf.append("<td ALIGN=\"left\" WIDTH=\"70%\">\r\n");		        
		        if (totPage>1) {
		        	if (printCurrPage>1) {
				        objBuf.append("<a HREF=\"javascript:doNavigate"+objTmp.getName()+"('"+objTmp.getValidateForm().getFormName()+"', 'F', " + totPage + 
				                        ")\">\r\n");
				        objBuf.append("<img SRC=\"" + this.mstrImgFirst + "\" BORDER=0></a>\r\n");
				        objBuf.append("<a HREF=\"javascript:doNavigate"+objTmp.getName()+"('"+objTmp.getValidateForm().getFormName()+"', 'P', " + totPage +
				                        ")\">\r\n");
				        objBuf.append("<img SRC=\"" + this.mstrImgPrevious + "\" BORDER=0></a>\r\n");
		        	}
		        	if (printCurrPage<totPage) {
				        objBuf.append("<a HREF=\"javascript:doNavigate"+objTmp.getName()+"('"+objTmp.getValidateForm().getFormName()+"', 'N', " + totPage +
				                        ")\">\r\n");
				        objBuf.append("<img SRC=\"" + this.mstrImgNext + "\" BORDER=0></a>\r\n");
				        objBuf.append("<a HREF=\"javascript:doNavigate"+objTmp.getName()+"('"+objTmp.getValidateForm().getFormName()+"', 'L', " + totPage +
				                        ")\">\r\n");
				        objBuf.append("<img SRC=\"" + this.mstrImgLast + "\" BORDER=0></a>\r\n");
		        	}
		        }
		        objBuf.append("</td>\r\n");
		        objBuf.append("<td ALIGN=\"right\" WIDTH=\"30%\" ");
		        objBuf.append("CLASS=\"gridPageOfPage\">\r\n");
		        objBuf.append("Page " + printCurrPage + " of " + totPage);
		        objBuf.append("&nbsp;&nbsp;</td>\r\n");
	        } else {
	        	objBuf.append("<td>&nbsp;</td");
	        }
	        objBuf.append("</tr>\r\n");
	        objBuf.append("</table>\r\n");
	        this.pageContext.getOut().println(objBuf.toString());
        }
        catch (IOException IoEx)
        {
            throw new JspException("Error: While drawing grid pager!", IoEx);
        }
        finally
        {
            if (objBuf != null) objBuf = null;
        }
    }

/*------------------------------------------------------------------------------
 * Helpers
 *----------------------------------------------------------------------------*/
    private GridPager getCopy()
    {
        GridPager objRet = null;

        objRet = new GridPager();
        objRet.setId(this.getId());
        objRet.setPageContext(this.pageContext);
        objRet.setParent(this.getParent());
        objRet.setImgFirst(((HttpServletRequest)pageContext.getRequest()).getContextPath()+this.mstrImgFirst);
        objRet.setImgPrevious(((HttpServletRequest)pageContext.getRequest()).getContextPath()+this.mstrImgPrevious);
        objRet.setImgNext(((HttpServletRequest)pageContext.getRequest()).getContextPath()+this.mstrImgNext);
        objRet.setImgLast(((HttpServletRequest)pageContext.getRequest()).getContextPath()+this.mstrImgLast);
        return objRet;
    }
}
