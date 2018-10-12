package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public class ActionTabs extends AMenuTag {
	private static final long serialVersionUID = -6499025000167244241L;
	
	private String mstrAlign = "right";
	private String mstrActiveTab;
	private boolean refreshPage;
	
	public String getAlign() {
		return mstrAlign;
	}

	public void setAlign(String mstrAlign) {
		this.mstrAlign = mstrAlign;
	}
	
	public String getActiveTab() {
		return mstrActiveTab;
	}

	public void setActiveTab(String mstrActiveTab) {
		this.mstrActiveTab = mstrActiveTab;
	}
	
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\"");
			objBuf.append(" width=\""+mstrWidth+"\"");
			objBuf.append(">\n");
			objBuf.append("  <tr>\n");
			
			if ("right".equals(mstrAlign))
				objBuf.append("    <td class=\"actionTopNull\">&nbsp;</td>\n");
			
			for(int i=0; i<mlstDataSource.size(); i++) {
				IMenu tabs = (IMenu) mlstDataSource.get(i);
				String tabName = this.mstrName+"_"+tabs.getMenuID();
				if (tabs.getVisible()) {
					objBuf.append("    <td class=\"actionTopLeft \"></td>\n");
					objBuf.append("    <td class=\"actionTop\" style=\"cursor: pointer;\" id=\""+tabName+"\"");
					if (tabs.getWidth()!=null)
						objBuf.append(" width=\""+tabs.getWidth()+"\"");
					objBuf.append(" onclick=\"");
					if (!this.refreshPage) {
						objBuf.append("showTabs_"+mstrName+"('"+tabName+"');");
					}
					if (tabs.getMenuAction()!=null && tabs.getMenuAction().length()>0) {
						objBuf.append(tabs.getMenuAction());
					}
					objBuf.append("\">\n");
					objBuf.append(tabs.getMenuText());
					objBuf.append("    </td>\n");
					objBuf.append("    <td class=\"actionTopRight\"></td>\n");
				}
			}
			if ("left".equals(mstrAlign))
				objBuf.append("    <td class=\"actionTopNull\">&nbsp;</td>\n");
			
			objBuf.append("  </tr>\n");
			objBuf.append("</table>\n");
			objBuf.append("<SCRIPT language=\"JavaScript\">\n");
			objBuf.append(writeShowTabsFunction());
			if (this.mstrActiveTab!=null) {
				objBuf.append("showTabs_"+mstrName+"(\""+this.mstrName+"_"+this.mstrActiveTab+"\");\n");
			} else {
				objBuf.append("showTabs_"+mstrName+"(\""+this.mstrName+"_"+((IMenu)mlstDataSource.get(0)).getMenuID()+"\");\n");
			}
			objBuf.append("</SCRIPT>");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write windows tabs contents!", IOEx);
        }
		return EVAL_PAGE;
	}

	private StringBuffer writeShowTabsFunction() {
		StringBuffer objBuf = new StringBuffer();
		objBuf.append("function showTabs_"+mstrName+"(tabname) {\n");
		for(int i=0; i<mlstDataSource.size(); i++) {			
			IMenu tabs = (IMenu) mlstDataSource.get(i);
			String tabName = this.mstrName+"_"+tabs.getMenuID();
			if (tabs.getVisible()) {
				objBuf.append("document.getElementById('"+tabName+"').className=\"actionTop\";\n");
			}
		}
		objBuf.append("var tabs = document.getElementById(tabname);\n");
		objBuf.append("tabs.className=\"actionActiveTabs\";\n");
		objBuf.append("}\n");		
		return objBuf;
	}

	public boolean getRefreshPage() {
		return refreshPage;
	}

	public void setRefreshPage(boolean refreshPage) {
		this.refreshPage = refreshPage;
	}

}
