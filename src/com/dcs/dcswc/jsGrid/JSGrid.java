package com.dcs.dcswc.jsGrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class JSGrid extends BodyTagSupport{
	private static final long serialVersionUID = 8782873823618488094L;
	private int mintPageSize     = 10;
	
	private String name = null;
	private String width = null;
	private int border = 0;
	private String cssClass = null;
	private String headerCssClass = null;
	private List<IJSColumn> columnList = null;
	private String dataSource = null;
	private boolean pager;

	public void addColumn(IJSColumn column) {
		columnList.add(column);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getHeaderCssClass() {
		return headerCssClass;
	}
	public void setHeaderCssClass(String headerCssClass) {
		this.headerCssClass = headerCssClass;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public int getBorder() {
		return border;
	}
	public void setBorder(int border) {
		this.border = border;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public boolean getPager() {
		return pager;
	}
	public void setPager(boolean pager) {
		this.pager = pager;
	}
	public void setPageSize(int pintPageSize)
    {
    	if (pintPageSize >= 0)
    		this.mintPageSize = pintPageSize;
    }
	
	public int doEndTag() throws JspException {
        if (this.columnList.size() == 0)
            throw new JspException("Error: No columns defined for the table!");
        JspWriter objOut  = null;
        try {
        	objOut = this.pageContext.getOut();
        	objOut.println("<div id=\""+this.name+"_div\"></div>");
        	objOut.println(drawGridScript());
        } catch (IOException IOEx) {
            throw new JspException("Error: Exception while writing to client!", IOEx);
        } finally {
            if (objOut != null) objOut = null;
        }
        release();
        return EVAL_PAGE;
    }

    public int doStartTag() {
    	columnList = new ArrayList<IJSColumn>();
        return EVAL_BODY_INCLUDE;
    }
    
    public void release() {
		try {
			name = null;
			width = null;
			cssClass = null;
			if (columnList!=null) columnList=null;
			super.release();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
    
    public StringBuffer drawGridScript() throws JspException {
    	StringBuffer objBuf = null;
    	objBuf = new StringBuffer();
    	try {
    		objBuf.append("<script language=\"JavaScript\">\n");
    		if (dataSource==null || dataSource.length()==0)
    			objBuf.append("var "+this.name+"= new Array()\n");
    		else
    			objBuf.append("var "+this.name+"="+this.dataSource+"\n");
    		objBuf.append("var "+this.name+"_tmp\n");
    		objBuf.append("var "+this.name+"_page=0\n");
    		objBuf.append("var "+this.name+"_pageSize="+this.mintPageSize+"\n");
    		objBuf.append("var "+this.name+"_totalPage=Math.ceil("+this.name+".length/"+this.name+"_pageSize)\n");
    		objBuf.append("function writeJSGrid"+this.name+"() {\n");
    		objBuf.append("   var num = 0;");
    		objBuf.append("   var buf =\"<table cellspacing='0' cellpadding='0'");    		
	    	if (this.cssClass != null)
    	            objBuf.append(" class='" + this.cssClass + "'");
	    	if (this.width != null)
                objBuf.append(" width='" + this.width + "'");
	    	if (this.border>0)
	    		objBuf.append(" border='" + this.border + "'");
	    	
	    	objBuf.append("><tr");
	    	if (this.headerCssClass != null)
                objBuf.append(" class='" + this.headerCssClass + "'");
	    	objBuf.append(">\";\n");	    	
	    	
	    	for(int i=0; i<this.columnList.size(); i++) {
	    		IJSColumn column = (IJSColumn) columnList.get(i);
	    		objBuf.append("   buf+=\"<td");
	    		if (column.getWidth()!=null)
	    			objBuf.append(" width='" + column.getWidth() + "'");
	    		objBuf.append(">");
	    		objBuf.append(column.getTitle());
	    		objBuf.append("</td>\";\n");
	    	}
	    	if (this.pager)
	    		objBuf.append("   for(var i="+this.name+"_page*"+this.name+"_pageSize; i<"+this.name+".length&&i<("+this.name+"_page+1)*"+this.name+"_pageSize; i++) {\n");
	    	else
	    		objBuf.append("   for(var i=0; i<"+this.name+".length; i++) {\n");
	    		
	    	objBuf.append("      if ((num%2)==0)\n");
	    	objBuf.append("         buf+=\"<tr class='gridRowEven'>\";\n");
	    	objBuf.append("      else\n");
	    	objBuf.append("         buf+=\"<tr class='gridRowOdd'>\";\n");
	    	int col = this.columnList.size();
	    	for(int i=0; i<this.columnList.size(); i++) {
	    		IJSColumn icolumn = (IJSColumn) this.columnList.get(i);
	    		String cssClass = "";
	    		String hAlign = "";
	    		String vAlign = "";
    			if (icolumn.getCssClass()!=null && icolumn.getCssClass().length()>0) {
    				cssClass = " class='"+icolumn.getCssClass()+"'";
    			}
    			if (icolumn.getHAlign()!=null && icolumn.getHAlign().length()>0) {
    				hAlign = " align='"+icolumn.getHAlign()+"'";
    			}
    			if (icolumn.getVAlign()!=null && icolumn.getVAlign().length()>0) {
    				vAlign = " valign='"+icolumn.getVAlign()+"'";
    			}
	    		if (icolumn instanceof JSColumn) {
	    			JSColumn column = (JSColumn) icolumn;
	    			String fieldExp = getFieldExpression(column.getDataField());
		    		if (column.getLinkUrl()!=null) {
		    			objBuf.append("      var link=\""+column.getLinkUrl()+"\".replace(/%R/g,i);\n");
		    			objBuf.append("      buf+=\"<td"+cssClass+hAlign+vAlign+"><a href=\"+link+\">\"+"+this.name+"[i]."+column.getDataField()+"+\"</a></td>\";\n");
		    		} else if (column.isEditAble()) {
		    			String value = "";
		    			String script = " onfocus='"+this.name+"_tmp=this.value;";
		    			script += " this.value="+this.name+"[\"+i+\"]."+column.getDataField()+"'";
		    			script += " onchange='";
		    			if (column.getRegExp()!=null) {
		    				script += " if (!validateExp("+this.name+"_regExp"+i+",this.value))";
		    				script += " {this.value="+this.name+"_tmp}";
		    				script += " else {"+this.name+"[\"+i+\"]."+column.getDataField()+"=this.value;}";
		    			} else {
		    				script += this.name+"[\"+i+\"]."+column.getDataField()+"=this.value;";		    				
		    			}		    			
		    			script += "'";
		    			if (column.getNumberFormat()!=null) {
		    				script += " onblur='this.value=Number(this.value).numberFormat(\\\""+column.getNumberFormat()+"\\\");'";
		    				value = "'\"+Number("+this.name+"[i]."+column.getDataField()+").numberFormat('"+column.getNumberFormat()+"')+\"'";
		    			} else {
		    				value = "'\"+"+this.name+"[i]."+column.getDataField()+"+\"'";
		    			}
		    			String textBox = "<input"+cssClass+" type='text' name='"+this.name+"_txt"+i+"[\"+i+\"]' value="+value+script+">";
		    			objBuf.append("      buf+=\"<td"+hAlign+vAlign+">"+textBox+"</td>\";\n");
		    		} else {
		    			if (column.getNumberFormat()!=null) {
		    				objBuf.append("      buf+=\"<td"+cssClass+hAlign+vAlign+">\"+Number("+fieldExp+").numberFormat('"+column.getNumberFormat()+"')+\"</td>\";\n");
		    			} else {
		    				objBuf.append("      buf+=\"<td"+cssClass+hAlign+vAlign+">\"+"+fieldExp+"+\"</td>\";\n");
		    			}
		    		}
	    		} else if (icolumn instanceof JSLinkColumn) {
	    			JSLinkColumn column = (JSLinkColumn) icolumn;
	    			if (column.getLinkUrl()!=null) {
		    			objBuf.append("      var link=\""+column.getLinkUrl()+"\".replace(/%R/g,i);\n");
		    			objBuf.append("      buf+=\"<td align='center'"+cssClass+"><a href=\"+link+\">"+column.getText()+"</a></td>\";\n");
	    			}
	    		} else if (icolumn instanceof JSIndexColumn){
	    			objBuf.append("      buf+=\"<td align='center'"+cssClass+">\"+(i+1)+\"</td>\";\n");
	    		} else if (icolumn instanceof JSCheckBoxColumn){
	    			objBuf.append("      buf+=\"<td align='center'"+cssClass+"><input type='checkbox' name='"+this.name+"_chk[\"+i+\"]'></td>\";\n");
	    		} else if (icolumn instanceof JSDropDownColumn) {
	    			JSDropDownColumn column = (JSDropDownColumn) icolumn;
	    			String onChange = "";
	    			String disable = "";
	    			if (column.getOnChange()!=null) {
	    				onChange = " onchange='"+this.name+"[\"+i+\"]."+column.getDataField()+"=this.value;" +column.getOnChange()+"(\\\""+this.name+"\\\",\"+i+\","+i+")'";
	    			}
	    			if (!column.getEditAble()) {
	    				disable = " disabled='disabled'";
	    			}
	    			objBuf.append("      buf+=\"<td align='center'"+cssClass+">\";\n");
	    			objBuf.append("      buf+=\"<select name='"+this.name+"_dpd"+i+"[\"+i+\"]' "+disable+onChange+">\";\n");
	    			objBuf.append("      for(var j=0; j<"+column.getOptionList()+".length; j++) {\n");
	    			objBuf.append("         var select = '';\n");
	    			objBuf.append("         if ("+this.name+"[i]."+column.getDataField()+"=="+column.getOptionListValue()+"[j]) {\n");
	    			objBuf.append("             select = \"selected='selected'\";\n");
	    			objBuf.append("         }\n");
	    			objBuf.append("         buf+=\"<option value='\"+"+column.getOptionListValue()+"[j]+\"'\"+select+\" >\"+"+column.getOptionList()+"[j]+\"</option>\";\n");
	    			
	    			objBuf.append("      }\n");
	    			objBuf.append("      buf+=\"</select></td>\";\n");
	    		}
	    	}
	    	objBuf.append("      buf+=\"</tr>\";\n");
	    	objBuf.append("      num++;\n");
	    	objBuf.append("   }\n");
	    	if (this.pager) {
	    		objBuf.append("   for(var i=num; i<"+this.name+"_pageSize; i++) {\n");
	    		objBuf.append("      if ((i%2)==0)\n");
		    	objBuf.append("         buf+=\"<tr class='gridRowEven'>\";\n");
		    	objBuf.append("      else\n");
		    	objBuf.append("         buf+=\"<tr class='gridRowOdd'>\";\n");
		    	objBuf.append("      buf+=\"<td colspan='"+col+"'>&nbsp;</td></tr>\";\n");
	    		objBuf.append("   }\n");
	    		objBuf.append(drawPager(col));
	    	}
	    	objBuf.append("   buf+=\"</table>\"\n");
	    	objBuf.append("   document.all."+this.name+"_div.innerHTML=buf;\n");
	    	objBuf.append("}\n");
	    	for(int i=0; i<this.columnList.size(); i++) {
	    		IJSColumn icolumn = (IJSColumn) this.columnList.get(i);
	    		if (icolumn instanceof JSColumn) {
	    			JSColumn column = (JSColumn) icolumn;
	    			if (column.getRegExp()!=null) {
	    				objBuf.append("var "+this.name+"_regExp"+i+"='"+column.getRegExp()+"';\n");
	    			}
	    		}
	    	}
	    	objBuf.append("writeJSGrid"+this.name+"();\n");
	    	objBuf.append("</script>\n");

    	} catch(Exception ex) {
    		throw new JspException("Error: Exception while writing to client!", ex);
    	}finally {
        }
    	return objBuf;
    }
    
    public StringBuffer drawPager(int col) throws JspException {
    	StringBuffer objBuf = null;
    	objBuf = new StringBuffer();
    	objBuf.append("if ("+this.name+"_totalPage>0) {\n");
    	objBuf.append("buf+=\"<tr><td colspan='"+col+"'><table border='0' cellspacing='0' cellpadding='0' width='100%'><tr><td align='left' class='gridPager' width='70%'>\";\n");
    	objBuf.append("if ("+this.name+"_pageSize>=0) {\n");
    	objBuf.append("   if ("+this.name+"_page>0) {\n");
    	objBuf.append("      buf+=\"<img src='" + ((HttpServletRequest)pageContext.getRequest()).getContextPath() + "/dcswc/images/First.gif' border='0' style='cursor: pointer' onclick='"+this.name+"_page=0;writeJSGrid"+this.name+"();'>&nbsp;\";\n");
    	objBuf.append("      buf+=\"<img src='" + ((HttpServletRequest)pageContext.getRequest()).getContextPath() + "/dcswc/images/Previous.gif' border='0' style='cursor: pointer' onclick='"+this.name+"_page--;writeJSGrid"+this.name+"();'>&nbsp;\";\n");
    	objBuf.append("   }\n");    	
    	objBuf.append("   if ("+this.name+"_page<"+this.name+"_totalPage-1) {\n");
    	objBuf.append("      buf+=\"<img src='" + ((HttpServletRequest)pageContext.getRequest()).getContextPath() + "/dcswc/images/Next.gif' border='0' style='cursor: pointer' onclick='"+this.name+"_page++;writeJSGrid"+this.name+"();'>&nbsp;\";\n");
    	objBuf.append("      buf+=\"<img src='" + ((HttpServletRequest)pageContext.getRequest()).getContextPath() + "/dcswc/images/Last.gif' border='0' style='cursor: pointer' onclick='"+this.name+"_page="+this.name+"_totalPage-1;writeJSGrid"+this.name+"();'>&nbsp;\";\n");
    	objBuf.append("   }\n");
    	objBuf.append("}\n");
    	objBuf.append("buf+=\"</td>\";\n");    	
    	objBuf.append("buf+=\"<td align='left' class='gridPageOfPage' width='30%'");
    	objBuf.append(">Page \"+("+this.name+"_page+1)+\" of \"+"+this.name+"_totalPage;\n");
    	objBuf.append("buf+=\"</td></tr></table></td></tr>\";\n");
    	objBuf.append("} else {\n");
    	objBuf.append("buf+=\"<tr><td colspan='"+col+"' class='gridPager'></td></tr>\";\n");
    	objBuf.append("}\n");
    	return objBuf;
    }
    
    private String getFieldExpression(String field) {
    	int strExp = field.indexOf("{");
    	if (strExp>=0) {
    		String buf = "\""+field+"\"";
    		strExp = buf.indexOf("{");
    		while(strExp>=0) {
    			int endExp = buf.indexOf("}");
    			String f = buf.substring(strExp+1, endExp);
    			buf = buf.substring(0,strExp)+"\"+"+this.name+"[i]."+f+"+\""+buf.substring(endExp+1,buf.length());
    			strExp = buf.indexOf("{");
    		}
    		return buf;
    	} else {
    		return this.name+"[i]."+field;
    	}    	
    }
}
