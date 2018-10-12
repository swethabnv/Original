package com.dcs.dcswc.common;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class NavMenu extends AMenuTag {

	private static final long serialVersionUID = -3388932397385480233L;

	public NavMenu() {
    	super();
    }
    
	public int doEndTag() throws JspException {
        release();
        return EVAL_PAGE;
    }

    public int doStartTag() {
    	drawMenu();
        return EVAL_BODY_INCLUDE;
    }
    
    private void drawMenu() {
    	JspWriter  objOut    = null;
    	Iterator   iterCol   = null;
    	StringBuffer objBuf  = null;
    	try {
    		objOut  = this.pageContext.getOut();
    		objBuf = new StringBuffer();
    		objBuf.append("<table cellspacing=\"0\" border=\"0\" cellpadding=\"0\"");

            if (this.mstrName != null) {
            	objBuf.append(" ID=\"" + this.mstrName + "\"");
                objBuf.append(" NAME=\"" + this.mstrName + "\"");
            }
            
            if (this.mstrWidth != null)
            	objBuf.append(" width=\""+this.mstrWidth+"\"");
            objBuf.append(">\n");
    		for (iterCol = this.mlstDataSource.iterator();iterCol.hasNext();) {                
                IMenu objItm = (IMenu) iterCol.next();
                if (objItm.getVisible()) {
                	objBuf.append(drawMenuItem(objItm, 1));
                }
            }
    		objBuf.append("</table>");
    		objOut.println(objBuf);
    	} catch (IOException IOEx){
            IOEx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();            
        } finally {
            if (objOut != null) objOut = null;
            if (iterCol != null) iterCol = null;
        }
    }
    
    private StringBuffer drawMenuItem(IMenu item, int level) {
    	Iterator   iterCol   = null;
    	StringBuffer objBuf = new StringBuffer();
    	objBuf.append("<TR><TD");
    	if (this.mstrCssClass!=null && !"".equals(this.mstrCssClass))
			objBuf.append(" class=\""+this.mstrCssClass+level+"\"");

    	objBuf.append("><div\n");
    	
    	if (item.getChild()!=null && item.getChild().size()>0)
    		objBuf.append(" onclick=\"toggle('"+mstrName+"_"+item.getMenuID()+"')\"");
    	else if (item.getMenuAction()!=null)
    		objBuf.append(" onclick=\""+item.getMenuAction()+"\"");
    	
    	objBuf.append(">");
    	
    	objBuf.append(item.getMenuText()+"</div>\n");

    	
    	if (item.getChild()!=null && item.getChild().size()>0) {
    		objBuf.append("<SPAN id=\""+mstrName+"_"+item.getMenuID()+"\" style=\"display : none;\">");
    		
    		objBuf.append("<TABLE cellspacing=\"0\" border=\"0\" cellpadding=\"0\"\n");
    		if (this.mstrWidth != null)
             	objBuf.append(" width=\""+this.mstrWidth+"\"");
    		objBuf.append(">");
    		
	    	for (iterCol = item.getChild().iterator();iterCol.hasNext();) { 
	    		IMenu objItm = (IMenu) iterCol.next();
	            objBuf.append(drawMenuItem(objItm, level+1));
	    	}
	    	objBuf.append("</TABLE></SPAN>\n");
    	}
    	objBuf.append("</TD></TR>\n");
    	return objBuf;
    }
    
}
