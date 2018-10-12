package com.dcs.dcswc.worksheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;

public class WSGrid extends BodyTagSupport{
	private static final long serialVersionUID = 7220791365820098507L;
	
	private String name = null;
	private String width = null;
	private int border = 0;
	private String cssClass = null;
	private String headerCssClass = null;
	private boolean customHeader;
	private List<WSColumn> columnList = null;
	private List<IWSRow> dataSource = null;

	public List<IWSRow> getDataSource() {
		return dataSource;
	}
	public void setDataSource(List<IWSRow> dataSource) {
		this.dataSource = dataSource;
	}
	public void addColumn(WSColumn column) {
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
	public boolean getCustomHeader() {
		return customHeader;
	}

	public void setCustomHeader(boolean customHeader) {
		this.customHeader = customHeader;
	}
	
	public int doEndTag() throws JspException {
        if (this.columnList.size() == 0)
            throw new JspException("Error: No columns defined for the table!");
        JspWriter objOut  = null;
        try {
        	objOut = this.pageContext.getOut();        	
        	objOut.println(drawWSGrid());
        } catch (IOException IOEx) {
            throw new JspException("Error: Exception while writing to client!", IOEx);
        } finally {
            if (objOut != null) objOut = null;
        }
        release();
        return EVAL_PAGE;
    }
	
	public void release() {
		try {
			if (columnList!=null) columnList=null;
			super.release();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

    public int doStartTag() {
    	columnList = new ArrayList<WSColumn>();
        return EVAL_BODY_INCLUDE;
    }
    
    public StringBuffer drawWSGrid() throws JspException {
    	StringBuffer objBuf = null;
    	WorkSheet objTmp = null;
    	objBuf = new StringBuffer();
    	try {
        	objTmp = getWorkSheet(this);
            objTmp.addGrid(this);
    	} catch(Exception ex) {}
    	try {
    		WSCell[][] cell = new WSCell[dataSource.size()][columnList.size()];
    		
    		if (!this.customHeader) {
		    	objBuf.append("<table cellspacing=\"0\" cellpadding=\"0\"");
		    	if (this.cssClass != null)
	    	            objBuf.append(" class=\"" + this.cssClass + "\"");
		    	if (this.width != null)
	                objBuf.append(" width=\"" + this.width + "\"");
		    	if (this.border>0)
		    		objBuf.append(" border=\"" + this.border + "\"");
		    	
		    	objBuf.append("><tr");
		    	if (this.headerCssClass != null)
	                objBuf.append(" class=\"" + this.headerCssClass + "\"");
		    	objBuf.append(">");

		    	for(int i=0; i<columnList.size(); i++) {
		    		WSColumn column = (WSColumn) columnList.get(i);
		    		if (column.getVisible()) {
			    		objBuf.append("<td");
			    		if (column.getWidth()!=null)
			    			objBuf.append(" width=\"" + column.getWidth() + "\"");
			    		objBuf.append(">");
		
			    		objBuf.append(column.getTitle());
			    		objBuf.append("</td>");
		    		}
		    	}
		    	objBuf.append("</tr>");	    	
    		}
    		
	    	for(int i=0; i<dataSource.size(); i++) {
	    		IWSRow row = dataSource.get(i);
	    		if (row.getVisible()) {
		    		if ((i % 2) == 0)
		    			objBuf.append("<tr class=\"gridRowEven\"");
	                else
	                	objBuf.append("<tr class=\"gridRowOdd\"");
		    		
		    		if (row.getStyle()!=null && row.getStyle().length()>0) {
		    			objBuf.append(" style=\""+row.getStyle()+"\"");
		    		}
		    		objBuf.append(">");
	    		}
	    		for(int j=0; j<columnList.size(); j++) {
	    			WSColumn column = (WSColumn) columnList.get(j);
	    			String value = getValue(row, column.getDataField());

	    			cell[i][j] = new WSCell();
	    			cell[i][j].setLoadValue(value);
	    			cell[i][j].setValue(value);
	    			
	    			if (column.getNumberFormatField()!=null && column.getNumberFormatField().length()>0)
	    				cell[i][j].setNumberFormat(getValue(row, column.getNumberFormatField()));
	    			else
	    				cell[i][j].setNumberFormat(column.getNumberFormat());
	    			
	    			if (column.getFormulaField()!=null && column.getFormulaField().length()>0)
	    				cell[i][j].setFormula(getValue(row, column.getFormulaField()));
	    			else
	    				cell[i][j].setFormula(column.getFormula());
	    			
	    			cell[i][j].setEditAble(column.isEditAble() && row.isEditAble());
	    			if (column.getVisible() && row.getVisible()) {
		    			String cellname = name+"_"+i+"_"+j;
		    			String cellobj = name+"["+i+"]["+j+"]";
		    			objBuf.append("<td id=\""+cellname+"\"");
		    			if (column.getCssClass() != null)
		                    objBuf.append(" class=\"" + column.getCssClass() + "\"");
		    			objBuf.append(">");
		    			if (column.isEditAble() && row.isEditAble()) {
		    				objBuf.append("<input name=\""+cellname+"_txt\" id=\""+cellname+"_txt\" type=\"text\" value=\""+value+"\" style=\"width: 100%;\"");
		    				if (column.getCssClass() != null)
			                    objBuf.append(" class=\"" + column.getCssClass() + "\"");
		    				objBuf.append(" onfocus=\"this.value=Number("+cellobj+".value).roundNumber(2);\"");
		    				
		    				if (cell[i][j].getNumberFormat()!=null && cell[i][j].getNumberFormat().length()>0) {	
		    					objBuf.append(" onblur=\"this.value=Number("+cellobj+".value).numberFormat('"+cell[i][j].getNumberFormat()+"');\"");
		    				}
		    				
		    				String regExp = null;
		    				if (column.getRegExpField()!=null && column.getRegExpField().length()>0) 
		    					regExp = getValue(row, column.getRegExpField());
		    				else
		    					regExp = column.getRegExp();
		    				
		    				objBuf.append(" onchange=\"");
		    				if (regExp!=null && regExp.length()>0) {
			    				objBuf.append("if (validateExp('"+regExp+"',this.value)");	
			    				if (column.getMaxValue()>0) {
									objBuf.append(" && parseInt(this.value)<="+column.getMaxValue());
								}
								if (column.getMinValue()<column.getMaxValue()) {
									objBuf.append(" && parseInt(this.value)>="+column.getMinValue());
								}
		    					objBuf.append(") {");
		    				}
	    					
		    					objBuf.append(cellobj+".value=this.value;");
//		    				if (objTmp==null) {
//		    					objBuf.append("clearValueTable('"+name+"');calculateTable('"+name+"');");
//		    				} else {
//		    					objBuf.append("calculate"+objTmp.getName()+"();");
//		    				}
		    				if (objTmp==null) {
		    					objBuf.append("clearValueTable('"+name+"');calculateTable('"+name+"');");
		    				} else {
		    					if (column.getCalculateAll())
		    					objBuf.append("calculate"+objTmp.getName()+"();");
		    					else
		    					objBuf.append("reCalculate('"+cellobj+"', table"+objTmp.getName()+"List);");	
		    				}
		    				if (regExp!=null && regExp.length()>0)
		    					objBuf.append("} else {this.value="+cellobj+".value}");
		    				objBuf.append("\">");
		    			} else {
		    				objBuf.append(value);
		    			}
		    			objBuf.append("</td>");
	    			}
	    		}
	    		if (row.getVisible()) {
	    			objBuf.append("</tr>");
	    		}
	    	}
	    	if (!this.customHeader) {
	    		objBuf.append("</table>\n");
	    	}
	    	objBuf.append("\n<SCRIPT language=\"JavaScript\">\n");
	    	JSONArray json = JSONArray.fromObject(cell);
	    	objBuf.append(name+" = "+json+";\n");
	    	if (objTmp==null) {
	    		objBuf.append("clearValueTable(\""+name+"\");\n");
	    		objBuf.append("formatTable(\""+name+"\");\n");
	    		objBuf.append("calculateTable(\""+name+"\");\n");
	    	}
			objBuf.append("</SCRIPT>\n");
    	} catch(Exception ex) {
    		throw new JspException("Error: Exception while writing to client!", ex);
    	}finally {
            if (objTmp != null) objTmp = null;
        }
    	return objBuf;
    }

    public String getValue(Object src, String property) {
		try {
			if (property!=null && !"".equals(property)) {
				Object obj = PropertyUtils.getProperty(src, property);
				if (obj instanceof String) {
					return (String)obj;
				} if (obj instanceof Integer) {
					return ((Integer)obj).toString();
				} else if (obj instanceof Long) {
					return ((Long)obj).toString();
				} else if (obj instanceof Float) {
					return ((Float)obj).toString();
				} else if (obj instanceof Double) {
					return ((Double)obj).toString();
				} else if (obj instanceof Boolean) {
					return ((Boolean)obj).toString();
				} 
			}
			return "";
		} catch(Exception ex) {
			System.out.println("Error get property :"+property);
			return null;
		}
	}
    
    public WorkSheet getWorkSheet(Tag tag) {
		Tag parent = tag.getParent();
		if (parent instanceof WorkSheet) {
			return (WorkSheet) parent;
		} else if (parent == null) {
			return null;
		} else {
			return this.getWorkSheet(parent);
		}
	}	
}
