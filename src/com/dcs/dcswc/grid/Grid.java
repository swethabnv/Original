/*------------------------------------------------------------------------------
 * PACKAGE: com.freeware.gridtag
 * FILE   : DBGrid.java
 * CREATED: Jul 20, 2004
 * AUTHOR : Prasad P. Khandekar
 *------------------------------------------------------------------------------
 * Change Log:
 *-----------------------------------------------------------------------------*/
package com.dcs.dcswc.grid;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dcs.dcswc.common.ValidateForm;

public final class Grid extends BodyTagSupport
{
	private static final long serialVersionUID = -1092966441298785603L;

	public static final String DEFAULT_NULLTEXT = "&nbsp";

    private int mintBorder       = 0;
    private int mintCellPadding  = 0;
    private int mintCellSpacing  = 0;    
    private int mintPageSize     = 10;
    private int mintTotalRecords = -1;

    private String mstrWidth        = "100%";
    private String mstrCssClass   = null;
    private String mstrBgColor    = null;
    private String mstrForeColor  = null;
    private String mstrID         = null;
    private String mstrName       = null;
    private String hibernateSessionName = null;

    private List<IColumnTag> mlstColumns = null;
    private GridPager mobjPager = null;
    private GridSorter mobjSorter = null;
    private Object mobjDataSource = null;
    private Object mCurrItem = null;
    private ValidateForm validateForm = null;
    private String pageField = "currentPage";
    private String lastPageField = "lastPage";

/*------------------------------------------------------------------------------
 * Getters
 *----------------------------------------------------------------------------*/

    public ValidateForm getValidateForm()
    {
    	return this.validateForm;
    }
    public String getWidth()
    {
        return this.mstrWidth;
    }

    public int getBorder()
    {
        return this.mintBorder;
    }

    public int getCellSpacing()
    {
        return this.mintCellSpacing;
    }

    public int getCellPadding()
    {
        return this.mintCellPadding;
    }

    public String getBgColor()
    {
        return this.mstrBgColor;
    }

    public String getForeColor()
    {
        return this.mstrForeColor;
    }

    public String getID()
    {
        return this.mstrID;
    }

    public String getName()
    {
        return this.mstrName;
    }

    public Object getDataSource()
    {
        return this.mobjDataSource;
    }
    
    public Object getCurrentItem()
    {
    	return this.mCurrItem;
    }

    public int getPageSize()
    {
        return this.mintPageSize;
    }

    public String getCssClass()
    {
        return this.mstrCssClass;
    }
    
    public int getCurrentPage()
    {
		int currentPage = 1;
    	try {
    		currentPage = (Integer) PropertyUtils.getProperty(this.validateForm.formBean, this.pageField);
    	} catch(Exception ex) {}
        return currentPage;
    }
    
    public void setCurrentPage(int page)
    {
    	try {
    		PropertyUtils.setProperty(this.validateForm.formBean, this.pageField, page);
    	} catch(Exception ex) {}
    }

    public String getSortColumn()
    {
		String sortColumn = null;
		if (this.mobjSorter != null) { 
	    	try {
	    		sortColumn = (String) PropertyUtils.getProperty(this.validateForm.formBean, this.mobjSorter.getSortColumnField());
	    	} catch(Exception ex) {}
		}
        return sortColumn;
    }

    public boolean getSortAscending()
    {
    	Boolean sortAscending = null;
    	if (this.mobjSorter != null) {
	    	try {
	    		sortAscending = (Boolean) PropertyUtils.getProperty(this.validateForm.formBean, this.mobjSorter.getSortAscendingField());
	    	} catch(Exception ex) {}
    	}
        if (sortAscending != null)
            return sortAscending;
        else
        	return true;
    }

    public String getAscendingImage()
    {
        String strTmp = null;
        if (this.mobjSorter != null && this.mobjSorter.getImageAscending()!=null)
            strTmp = this.mobjSorter.getImageAscending();
        return (strTmp == null ? ((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/dcswc/images/ImgAsc.gif" : strTmp);
    }

    public String getDescendingImage()
    {    	
        String strTmp = null;
        if (this.mobjSorter != null && this.mobjSorter.getImageDescending()!=null)
            strTmp = this.mobjSorter.getImageDescending();
        return (strTmp == null ? ((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/dcswc/images/ImgDsc.gif" : strTmp);
    }

    public int getTotalRecords()
    {
    	return this.mintTotalRecords;
    }
    
    public String getHibernateSessionName() {
		return hibernateSessionName;
	}

	public void setHibernateSessionName(String hibernateSessionName) {
		this.hibernateSessionName = hibernateSessionName;
	}

/*------------------------------------------------------------------------------
 * Setters
 *----------------------------------------------------------------------------*/

    public void setWidth(String pstrWidth)
    {
    		this.mstrWidth = pstrWidth;
    }

    public void setBorder(int pintBorder)
    {
    	if (pintBorder >= 0)
    		this.mintBorder = pintBorder;
    }

    public void setCellSpacing(int pintCellSpacing)
    {
    	if (pintCellSpacing >= 0)
    		this.mintCellSpacing = pintCellSpacing;
    }

    public void setCellPadding(int pintCellPadding)
    {
    	if (pintCellPadding >= 0)
    		this.mintCellPadding = pintCellPadding;
    }

    public void setBgColor(String pstrBgColor)
    {
        this.mstrBgColor = pstrBgColor;
    }

    public void setForeColor(String pstrColor)
    {
        this.mstrForeColor = pstrColor;
    }

    public void setID(String pstrID)
    {
        this.mstrID = pstrID;
        this.mstrName = pstrID;
    }

    public void setName(String pstrName)
    {
        this.mstrName = pstrName;
        this.mstrID = pstrName;
    }
    
    public void setDataSource(Object pobjDataSrc) throws UnsupportedOperationException
    {
    	this.mobjDataSource = pobjDataSrc;
    }
    
    public void setPageSize(int pintPageSize)
    {
    	if (pintPageSize >= 0)
    		this.mintPageSize = pintPageSize;
    }

    public void setCssClass(String pstrCssClass)
    {
        this.mstrCssClass = pstrCssClass;
    }

    public void setTotalRecords(int pintTotRec)
    {
    	this.mintTotalRecords = pintTotRec;
    }

/*------------------------------------------------------------------------------
 * Overridden Methods
 * @see javax.servlet.jsp.tagext.Tag
 *----------------------------------------------------------------------------*/

    public int doEndTag() throws JspException
    {
        // If columns are not defined then report an error
        if (this.mlstColumns.size() == 0)
            throw new JspException("Error: No columns defined for the table!");

        drawGrid();
        drawGridScript();
        release();
        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException
    {
        mlstColumns = new ArrayList<IColumnTag>();
    	validateForm = getValidateForm(this);
        if (validateForm==null)
            throw new JspException("Error: Grid tag needs to be a child of ValidateForm!");
        return EVAL_BODY_INCLUDE;
    }
    
    public void release() {
		try {
			mstrCssClass = null;
		    mstrBgColor = null;
		    mstrForeColor = null;
		    mstrID = null;
		    mstrName = null;
		    if (mlstColumns!=null) mlstColumns=null;
		    if (mobjPager!=null) mobjPager=null;
		    if (mobjSorter!=null) mobjSorter=null;
			super.release();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

/*------------------------------------------------------------------------------
 * Methods
 *----------------------------------------------------------------------------*/

    public boolean supportSorting()
    {
        if (this.mobjSorter == null)
            return false;
        return true;
    }

    public void setPager(GridPager pobjPgr)
    {
        this.mobjPager = pobjPgr;
    }

    public void setSorter(GridSorter pobjSort)
    {
        this.mobjSorter = pobjSort;
    }

    public void addColumn(IColumnTag pobjCol)
    {
        this.mlstColumns.add(pobjCol);
    }

    public Object getColumnValue(String pstrCol)
    {
        Object objRet = null;

        try
        {
            if (pstrCol != null)
            {
            	if (this.mobjDataSource instanceof List)
            	{
            		objRet = PropertyUtils.getProperty(this.mCurrItem, pstrCol);
            	}
            }
        }
        catch (IllegalAccessException IAEx)
		{
        	IAEx.printStackTrace();
		}
        catch (InvocationTargetException ITargetEx)
		{
        	ITargetEx.printStackTrace();
		}
        catch (NoSuchMethodException NSMEx)
		{
        	NSMEx.printStackTrace();
		}
        if (objRet == null)
            objRet = new String(Grid.DEFAULT_NULLTEXT);
        return objRet;
    }

/*------------------------------------------------------------------------------
 * Helpers
 *----------------------------------------------------------------------------*/
    private void drawGrid() throws JspException
    {
        int        intTotRec = 0;
        int        intCntr   = 0;
        int        intStart  = 0;
        String     strFld    = null;
        IColumnTag objCol    = null;
        JspWriter  objOut    = null;
        Iterator<IColumnTag>   iterCol   = null;
        Session hsession = null;

        intTotRec = findTotalRecords();
        
        try
        {
        	objOut  = this.pageContext.getOut();
        	if (intTotRec <= 0)
            {
            	drawTableStart();
                drawHeaderRow();
                drawEmptyTable();
                if (this.mobjPager != null)
                {
                    drawPager(intTotRec);
                }
                objOut.println("</table>");
                return;
            }
        	
            if (this.getCurrentPage() == 1)
                intStart = 0;
            else
            {
                intStart = ((this.getCurrentPage() - 1) * this.mintPageSize);
                if (intStart >= intTotRec) {
                    intStart = computeLastPageStart(intTotRec);
                    this.setCurrentPage(GridPager.calcTotPage(intTotRec, this.mintPageSize));
                }
            }

            drawTableStart();
            drawHeaderRow();
            
            if (this.hibernateSessionName!=null && !"".equals(this.hibernateSessionName)) {
            	SessionFactory sf = (SessionFactory) new InitialContext().lookup(this.hibernateSessionName);
            	hsession = sf.getCurrentSession();
            }

            for(intCntr = 0; intCntr < this.mintPageSize; intCntr++)
            {
                if ((intCntr % 2) == 0)
                    objOut.println("<tr CLASS=\"gridRowEven\">");
                else
                    objOut.println("<tr CLASS=\"gridRowOdd\">");

                iterCol = null;
                if (this.mobjDataSource instanceof List) {
                	this.mCurrItem = ((List) this.mobjDataSource).get(intStart + intCntr);
                	if (hsession!=null) {
                		hsession.lock(this.mCurrItem, LockMode.NONE);
                	}
                }

                for (iterCol = this.mlstColumns.iterator();iterCol.hasNext();)
                {
                    objCol = null;
                    objCol = iterCol.next();
                    if (objCol instanceof RowNumColumn)
                        objCol.renderDetail(new Integer(intStart + intCntr + 1));
                    else
                    {
                        strFld = objCol.getDataField();
                        objCol.renderDetail(this.getColumnValue(strFld));
                    }
                }
                objCol = null;
                objOut.println("</tr>");
                if (this.mobjDataSource instanceof List) 
                {
                	if ((intStart + intCntr) == 
                			((List) this.mobjDataSource).size() - 1) break;
                }
            }
            if (intCntr < this.mintPageSize)
                emptyRowsOut(intCntr);

            if (this.mobjPager != null)
            {
                drawPager(intTotRec);
            }
            objOut.println("</table>");
        }
        catch (IOException IOEx)
        {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write grid contents!", IOEx);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new JspException("Error: Unknown error occured!", ex);
        }
        finally
        {
            if (objCol != null)objCol = null;
            if (objOut != null) objOut = null;
            if (iterCol != null) iterCol = null;
        }
    }
    
    private void drawPager(int intTotRec) throws JspException
    {
    	JspWriter objOut  = null;
    	try {
	    	objOut = this.pageContext.getOut();
	    	objOut.println("<tr>");
	        objOut.println("<td COLSPAN=" + this.mlstColumns.size() + " STYLE=\"\">");
	        this.mobjPager.renderPager(this.getCurrentPage(), intTotRec, 
	                        				this.mintPageSize);
	        objOut.println("</td>");
	        objOut.println("</tr>");
    	}
        catch (IOException IOEx)
        {
            throw new JspException("Error: Exception while writing to client!", IOEx);
        }
        finally
        {
            if (objOut != null) objOut = null;
        }
    }

    private void drawEmptyTable() throws JspException
    {
        int       intCntr = 0;
        JspWriter objOut  = null;

        try
        {
        	objOut = this.pageContext.getOut();
            
            drawTableStart();
            for (intCntr = 0; intCntr < this.mintPageSize; intCntr++)
            {
                if ((intCntr % 2) == 0)
                    objOut.println("<tr CLASS=\"gridRowEven\">");
                else
                    objOut.println("<tr CLASS=\"gridRowOdd\">");
                objOut.print("<td COLSPAN=");
                objOut.print(this.mlstColumns.size());
                if (intCntr == 0)
                    objOut.println(">No records to display!</td>");
                else
                    objOut.println(">&nbsp;</td>");
                objOut.println("</tr>");
            }
        }
        catch (IOException IOEx)
        {
            throw new JspException("Error: Exception while writing to client!", IOEx);
        }
        finally
        {
            if (objOut != null) objOut = null;
        }
    }

    private void drawTableStart() throws JspException
    {
        StringBuffer objBuf = null;
        objBuf = new StringBuffer();
        if (this.validateForm.formBean!=null && this.mobjSorter!=null) {
        	try {
		        String sortColumn = this.getSortColumn();
		        Boolean sortAscending = this.getSortAscending();
		        
		        objBuf.append("<input TYPE=\"hidden\" NAME=\""+this.mobjSorter.getSortColumnField()+"\"");
		        if (sortColumn!=null)
		        	objBuf.append(" VALUE=\""+sortColumn+"\"");
		        objBuf.append(">\n");
		        
		        objBuf.append("<input TYPE=\"hidden\" NAME=\""+this.mobjSorter.getSortAscendingField()+"\"");
		        if (sortAscending!=null)
		        	objBuf.append(" VALUE=\""+sortAscending+"\">\n");
		        else 
		        	objBuf.append(" VALUE=\"+1+\">\n");
        	} catch(Exception ex) { }
        }
        objBuf.append("<input TYPE=\"hidden\" NAME=\""+this.getPageField()+"\" VALUE=\""+this.getCurrentPage()+"\">\n");
        objBuf.append("<input TYPE=\"hidden\" NAME=\""+this.getLastPageField()+"\" VALUE=\""+this.getCurrentPage()+"\">\n");
        objBuf.append("<table");
        if (this.mstrCssClass != null)
            objBuf.append(" CLASS=\"" + this.mstrCssClass + "\"");
        objBuf.append(" WIDTH=\"" + this.mstrWidth + "\"");
        objBuf.append(" CELLSPACING=" + this.mintCellSpacing);
        objBuf.append(" CELLPADDING=" + this.mintCellPadding);
        if (this.mstrID != null)
            objBuf.append(" ID=\"" + this.mstrID + "\"");
        if (this.mstrName != null)
            objBuf.append(" NAME=\"" + this.mstrName + "\"");
        if (this.mstrForeColor != null)
            objBuf.append(" COLOR=\"" + this.mstrForeColor + "\"");
        if (this.mstrBgColor != null)
            objBuf.append(" BGCOLOR=\"" + this.mstrBgColor + "\"");
        objBuf.append(">\r\n");
        try
        {
            this.pageContext.getOut().println(objBuf.toString());
        }
        catch (IOException IOEx)
        {
            throw new JspException("Error: Error while writing to client!", IOEx);
        }
        objBuf = null;
    }

    private void drawHeaderRow() throws JspException
    {
        JspWriter  objOut  = null;
        IColumnTag objCol  = null;
        Iterator<IColumnTag>   iterCol = null;

        try
        {
            objOut = this.pageContext.getOut();
            objOut.println("<tr CLASS=\"gridHeader\">");
            for (iterCol = this.mlstColumns.iterator();iterCol.hasNext();)
            {            	
                objCol = null;
                objCol = iterCol.next();
                objCol.renderHeader();
            }
            objCol = null;
            objOut.println("</tr>");
        }
        catch (IOException IOEx)
        {
            throw new JspException("Error: Unable to render grid header!", IOEx);
        }
        finally
        {
            if (objCol != null) objCol = null;
            if (iterCol != null) iterCol = null;
            if (objOut != null) objOut = null;
        }
    }
    
    private void drawGridScript() throws JspException
    {
        StringBuffer objBuf = null;
        objBuf = new StringBuffer();
                
        objBuf.append("<script language=\"JavaScript\">\n");
        if (this.mobjSorter!=null) {
	        objBuf.append("function doSort"+this.mstrName+"(pstrFormName, pstrFld, pstrOrd) {\n");
	        objBuf.append("	 var f = eval(\"document.\"+pstrFormName);\n");
	        objBuf.append("	 if (f.onsubmit()) {\n");
	        objBuf.append("	   f.target=\"\";\n");
	        objBuf.append("	   f.action=\""+this.validateForm.getFormAction()+"\";\n");
	        objBuf.append("	   f."+this.mobjSorter.getSortColumnField()+".value = pstrFld;\n");
	        objBuf.append("	   f."+this.mobjSorter.getSortAscendingField()+".value = pstrOrd;\n");
	        objBuf.append("	   f.submit();\n");
	        objBuf.append("  }\n");
	        objBuf.append("}\n");
        }
        objBuf.append("function doNavigate"+this.mstrName+"(pstrFormName, pstrWhere, pintTot) {\n");
        objBuf.append("  var strTmp;\n");
        objBuf.append("  var intPg;\n");
        objBuf.append("  var f = eval(\"document.\"+pstrFormName);\n");
        objBuf.append("	 if (f.onsubmit()) {\n");
        objBuf.append("	   f.target=\"\";\n");
        objBuf.append("	   f.action=\""+this.validateForm.getFormAction()+"\";\n");
        objBuf.append("	   strTmp = f."+this.getPageField()+".value;\n");
        objBuf.append("	   intPg = parseInt(strTmp);\n");
        objBuf.append("	   if (isNaN(intPg)) intPg = 1;\n");
        objBuf.append("	   if ((pstrWhere == 'F' || pstrWhere == 'P') && intPg == 1) {\n");
        objBuf.append("       alert(\"You are already viewing first page!\");\n");
        objBuf.append("       return;\n");
        objBuf.append("	   } else if ((pstrWhere == 'N' || pstrWhere == 'L') && intPg == pintTot) {\n");
        objBuf.append("       alert(\"You are already viewing last page!\");\n");
        objBuf.append("       return;\n");
        objBuf.append("	   }\n");
        objBuf.append("	   if (pstrWhere == 'F')\n");
        objBuf.append("       intPg = 1;\n");
        objBuf.append("    else if (pstrWhere == 'P')\n");
        objBuf.append("       intPg = intPg - 1;\n");
        objBuf.append("    else if (pstrWhere == 'N')\n");
        objBuf.append("       intPg = intPg + 1;\n");
        objBuf.append("    else if (pstrWhere == 'L')\n");
        objBuf.append("       intPg = pintTot;\n");
        objBuf.append("    if (intPg < 1)\n");
        objBuf.append("      intPg = 1;\n");
        objBuf.append("    if (intPg > pintTot)\n");
        objBuf.append("     intPg = pintTot;\n");
        objBuf.append("    f."+this.getPageField()+".value = intPg;\n");
        objBuf.append("    f.submit();\n");
        objBuf.append("  }\n");
        objBuf.append("}\n");
        objBuf.append("</script>\n");
        try
        {
            this.pageContext.getOut().println(objBuf.toString());
        }
        catch (IOException IOEx)
        {
            throw new JspException("Error: Error while writing to client!", IOEx);
        }
        objBuf = null;
    }

    public int findTotalRecords()
    {
        int intRet  = 0;

        if (this.mobjDataSource == null) return 0;

    	if (this.mobjDataSource instanceof List)
    	{
    		intRet = ((List) this.mobjDataSource).size();
    	}
       
        return intRet;
    }

    private int computeLastPageStart(int pintTotal)
    {
        int intRet      = 0;
        int intLastPage = 0;

        if ((pintTotal % this.mintPageSize) == 0)
            intLastPage = (pintTotal / this.mintPageSize) - 1;
        else
            intLastPage = (pintTotal / this.mintPageSize);

        //this.mintCurrPage = intLastPage;
        intRet = (intLastPage * this.mintPageSize);
        return intRet;
    }

    private void emptyRowsOut(int pintFrom) throws JspException
    {
        int        intCntr = 0;
        Iterator<IColumnTag>   iterCol = null;
        IColumnTag objCol  = null;
        JspWriter  objOut  = null;

        try
        {
            objOut = this.pageContext.getOut();
	        for(intCntr = pintFrom + 1; intCntr < this.mintPageSize; intCntr++)
	        {
	            if ((intCntr % 2) == 0)
	                objOut.println("<tr CLASS=\"gridRowEven\">");
	            else
	                objOut.println("<tr CLASS=\"gridRowOdd\">");
	
	            iterCol = null;
	            for (iterCol = this.mlstColumns.iterator();iterCol.hasNext();)
	            {
	                objCol = null;
	                objCol = iterCol.next();
	                objCol.renderBlank();
	            }
	            objCol = null;
	            objOut.println("</tr>");
	        }
        }
        catch (IOException IoEx)
        {
            throw new JspException("Error: Writing empty rows!", IoEx);
        }
        finally
        {
            if (objOut != null) objOut = null;
            if (iterCol != null) iterCol = null;
            if (objCol != null) objCol = null;
        }
    }
    
    public ValidateForm getValidateForm(Tag tag) {
		Tag parent = tag.getParent();
		if (parent instanceof ValidateForm) {
			return (ValidateForm) parent;
		} else if (parent == null) {
			return null;
		} else {
			return this.getValidateForm(parent);
		}
	}

	public String getPageField() {
		return pageField;
	}

	public void setPageField(String pageField) {
		this.pageField = pageField;
	}

	public String getLastPageField() {
		return lastPageField;
	}

	public void setLastPageField(String lastPageField) {
		this.lastPageField = lastPageField;
	}	

}
