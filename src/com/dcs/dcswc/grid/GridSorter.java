package com.dcs.dcswc.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public final class GridSorter extends TagSupport
{
	private static final long serialVersionUID = -3179473508912281645L;
    private String mstrImgAsc;
    private String mstrImgDes;
    private String sortColumnField = "sortColumn";
    private String sortAscendingField = "sortAscending";

    public GridSorter()
    {
        super();
    }

    public String getImageAscending()
    {
        return this.mstrImgAsc;
    }

    public String getImageDescending()
    {
        return this.mstrImgDes;
    }

    public void setImageAscending(String pstrImgAsc)
    {
        this.mstrImgAsc = pstrImgAsc;
    }

    public void setImageDescending(String pstrImgDes)
    {
        this.mstrImgDes = pstrImgDes;
    }

    public int doEndTag() throws JspException
    {
        Grid objTmp = null;

        try
        {
            objTmp = (Grid) getParent();
            objTmp.setSorter(getCopy());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: GridSorter should be a child of Grid", CCEx);
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
            throw new JspException("Error: GridSorter tag needs to be a child of Grid!");

        return SKIP_BODY;
    }

    private GridSorter getCopy()
    {
        GridSorter objRet = null;

        objRet = new GridSorter();
        objRet.setId(this.getId());
        objRet.setImageAscending(this.mstrImgAsc);
        objRet.setImageDescending(this.mstrImgDes);
        objRet.setSortAscendingField(this.sortAscendingField);
        objRet.setSortColumnField(this.sortColumnField);
        objRet.setPageContext(this.pageContext);
        objRet.setParent(this.getParent());
        return objRet;
    }

	public String getSortAscendingField() {
		return sortAscendingField;
	}

	public void setSortAscendingField(String sortAscendingField) {
		this.sortAscendingField = sortAscendingField;
	}

	public String getSortColumnField() {
		return sortColumnField;
	}

	public void setSortColumnField(String sortColumnField) {
		this.sortColumnField = sortColumnField;
	}

}
