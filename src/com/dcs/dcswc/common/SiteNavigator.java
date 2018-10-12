package com.dcs.dcswc.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.dcs.dcswc.common.IMenu;

public class SiteNavigator extends TagSupport {	
	private static final long serialVersionUID = -290744671999001024L;
	
	private IMenu menu;
	private String cssClass;
	private String homeIcon;
	private String homeText;
	private String homeURL;
	private String separater;
	
	public IMenu getMenu() {
		return menu;
	}

	public void setMenu(IMenu menu) {
		this.menu = menu;
	}
	
	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getHomeIcon() {
		return homeIcon;
	}

	public void setHomeIcon(String homeIcon) {
		this.homeIcon = homeIcon;
	}

	public String getHomeText() {
		return homeText;
	}

	public void setHomeText(String homeText) {
		this.homeText = homeText;
	}

	public String getSeparater() {
		return separater;
	}

	public void setSeparater(String separater) {
		this.separater = separater;
	}

	public SiteNavigator() {
    	super();
    }
    
	public int doEndTag() throws JspException {
        release();
        return EVAL_PAGE;
    }

    public int doStartTag() {
    	JspWriter  objOut    = null;
    	try {
    		objOut  = this.pageContext.getOut();
	    	StringBuffer objBuf = new StringBuffer();
	    	objBuf.append("<div align=\"left\">");
	    	drawNavigator(objBuf, this.menu);
	    	objBuf.append("</div>");
	    	objOut.println(objBuf);
    	} catch (Exception ex) {
            ex.printStackTrace();            
        } finally {
            if (objOut != null) objOut = null;
        }
        return EVAL_BODY_INCLUDE;
    }
    
    private void drawNavigator(StringBuffer buf, IMenu menu) {
    	//System.out.println(menu.getMenuText());
    	if (menu.getParent()!=null) {
    		drawNavigator(buf, (IMenu)menu.getParent());
    		buf.append("&nbsp;>&nbsp;"+menu.getMenuText());
    	} else {
    		buf.append("Home&nbsp;>&nbsp;"+menu.getMenuText());
    	}
    }

	public String getHomeURL() {
		return homeURL;
	}

	public void setHomeURL(String homeURL) {
		this.homeURL = homeURL;
	}
   
}
