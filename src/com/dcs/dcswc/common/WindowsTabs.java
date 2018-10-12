package com.dcs.dcswc.common;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class WindowsTabs extends BodyTagSupport {
	private static final long serialVersionUID = 2871944424730886690L;
	
	private String mstrName = null;
	private int width = 500;
	private int height = 400;
	private String mstrAlign = null;
	private ArrayList<Tabs> mlstTabs = null;
	
	public String getName() {
		return mstrName;
	}

	public void setName(String mstrName) {
		this.mstrName = mstrName;
	}
	
	public String getAlign() {
		return mstrAlign;
	}
	
	public void setAlign(String mstrAlign) {
		this.mstrAlign = mstrAlign;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void addTabs(Tabs objVal) {
		mlstTabs.add(objVal);
	}
	
	public int doStartTag() throws JspException {
		mlstTabs = new ArrayList<Tabs>();
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		StringBuffer objBuf = null;
		try {			
			objBuf = new StringBuffer();
			objBuf.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\"");
			objBuf.append(" width=\""+width+"\" height=\""+height+"\"");
			if (mstrAlign!=null) {
				objBuf.append(" align=\""+mstrAlign+"\"");
			}
			objBuf.append(">\n");
			objBuf.append("  <tr>\n");
			
			int i=0;
			int totalTitleWidth=0;
			for(; i<mlstTabs.size(); i++) {
				Tabs tabs = (Tabs) mlstTabs.get(i);
				objBuf.append("    <td class=\"windowsTopLeft\" width=\"3\"></td>\n");
				objBuf.append("    <td class=\"windowsTop\" style=\"cursor: pointer;\" id=\""+tabs.getName()+"\"");
				objBuf.append(" width=\""+tabs.getTitleWidth()+"\"");
				objBuf.append(" onclick=\"showTabs_"+mstrName+"('"+tabs.getName()+"')\">\n");
				objBuf.append(tabs.getTitle());
				objBuf.append("    </td>\n");
				objBuf.append("    <td class=\"windowsTopRight\" width=\"3\"></td>\n");
				totalTitleWidth+=tabs.getTitleWidth()+12;
			}
			objBuf.append("    <td class=\"windowsTopNull\" width=\""+(width-totalTitleWidth-5)+"\"></td>\n");
			objBuf.append("    <td class=\"windowsTopRightNull\" width=\"3\"></td>\n");
			objBuf.append("  </tr>\n");
			objBuf.append("  <tr>\n");
			objBuf.append("    <td class=\"windowsLeft\" width=\"3\"></td>\n");
			objBuf.append("    <td colspan=\""+(i*3)+"\" id=\""+mstrName+"\" align=\"center\">\n");
			objBuf.append("    </td>\n");
			objBuf.append("    <td class=\"windowsRight\" width=\"3\"></td>\n");
			objBuf.append("  </tr>\n");
			objBuf.append("  <tr>\n");
			objBuf.append("    <td class=\"windowsBottomLeft\"></td>\n");
			objBuf.append("    <td class=\"windowsBottom\" colspan=\""+(i*3)+"\"></td>\n");
			objBuf.append("    <td class=\"windowsBottomRight\"></td>\n");
			objBuf.append("  </tr>\n");
			objBuf.append("</table>\n");
			objBuf.append("<SCRIPT language=\"JavaScript\">\n");
			objBuf.append(writeShowTabsFunction());
			objBuf.append("</SCRIPT>");
			pageContext.getOut().println(objBuf);
			release();
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write windows tabs contents!", IOEx);
        }
		return EVAL_PAGE;
	}
	
	public void release() {
		try {
			mstrName = null;
			mstrAlign = null;
			if (mlstTabs!=null) mlstTabs=null;
			super.release();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private StringBuffer writeShowTabsFunction() {
		StringBuffer objBuf = new StringBuffer();
		objBuf.append("function showTabs_"+mstrName+"(tabname) {\n");
		for(int i=0; i<mlstTabs.size(); i++) {
			Tabs tabs = (Tabs) mlstTabs.get(i);
			objBuf.append("document.all."+tabs.getName()+".className=\"windowsTop\";\n");
			objBuf.append("document.all."+tabs.getName()+"_div.style.display=\"none\";\n");
		}
		objBuf.append("var tabs = eval(\"document.all.\"+tabname);\n");
		objBuf.append("var tabsdiv = eval(\"document.all.\"+tabname+\"_div\");\n");
		objBuf.append("tabs.className=\"windowsActiveTabs\";\n");
		objBuf.append("tabsdiv.style.display=\"block\";\n");
		objBuf.append("}\n");
		for(int i=0; i<mlstTabs.size(); i++) {
			Tabs tabs = (Tabs) mlstTabs.get(i);
			//objBuf.append("snapToObject(document.all."+tabs.getName()+"_div,document.all."+mstrName+",0,0,0,0);\n");
			objBuf.append("document.all."+mstrName+".appendChild(document.all."+tabs.getName()+"_div);\n");
		}
		objBuf.append("showTabs_"+mstrName+"(\""+((Tabs)mlstTabs.get(0)).getName()+"\");\n");		
		return objBuf;
	}	
}
