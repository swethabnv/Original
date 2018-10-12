package com.dcs.dcswc.worksheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class WorkSheet extends BodyTagSupport{
	private static final long serialVersionUID = 3678582151856549585L;
	
	private String name = null;
	private List<WSGrid> gridList = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addGrid(WSGrid grid) {
		gridList.add(grid);
	}
	
	public int doEndTag() throws JspException {
        if (this.gridList.size() == 0)
            throw new JspException("Error: No grid defined for the worksheet!");

        JspWriter objOut  = null;
        try {
        	objOut = this.pageContext.getOut();        	
        	objOut.println(drawWorkSheetScript());
        } catch (IOException IOEx) {
            throw new JspException("Error: Exception while writing to client!", IOEx);
        } finally {
            if (objOut != null) objOut = null;
        }
        release();
        return EVAL_PAGE;
    }
	
	public int doStartTag() {
		gridList = new ArrayList<WSGrid>();
        return EVAL_BODY_INCLUDE;
    }
	
	public void release() {
		try {
			name = null;
			if (gridList!=null) gridList=null;
			super.release();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public StringBuffer drawWorkSheetScript() {
		StringBuffer objBuf = null;
    	objBuf = new StringBuffer();
    	objBuf.append("<SCRIPT language=\"JavaScript\">\n");
    	objBuf.append("function calculate"+name+"() {\n");
    	for(int i=0; i<gridList.size(); i++) {
    		WSGrid grid = (WSGrid) gridList.get(i);
    		objBuf.append("   clearValueTable(\""+grid.getName()+"\");\n");
    		objBuf.append("   formatTable(\""+grid.getName()+"\");\n");
    		objBuf.append("   calculateTable(\""+grid.getName()+"\");\n");
    	}
    	objBuf.append("}\n");
    	objBuf.append("calculate"+name+"();\n");
    	objBuf.append("var table"+name+"List=[");
    	for(int i=0; i<gridList.size(); i++) {
    		WSGrid grid = (WSGrid) gridList.get(i);
    		objBuf.append("\""+grid.getName()+"\"");
    		if (i<gridList.size()-1) {
    			objBuf.append(",");
    		}
    	}
    	objBuf.append("];\n");
		objBuf.append("</SCRIPT>\n");
    	return objBuf;
	}
}
