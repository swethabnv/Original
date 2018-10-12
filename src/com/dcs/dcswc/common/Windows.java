package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class Windows extends BodyTagSupport {
	private static final long serialVersionUID = -4079822785135909452L;

	private String mstrName = null;
	private String mstrTitle = null;
	protected String mstrCssClass = null;
	private String mstrTitleBullet = null;
	private boolean mbolShowTitle = true;
	protected boolean mbolIsCollapse = false;
	private String mstrWidth = "100%";
	
	public String getCssClass() {
		return mstrCssClass;
	}

	public void setCssClass(String mstrCssClass) {
		this.mstrCssClass = mstrCssClass;
	}
	
	public String getName() {
		return mstrName;
	}

	public void setName(String mstrName) {
		this.mstrName = mstrName;
	}
	
	public String getWidth() {
		return mstrWidth;
	}

	public void setWidth(String mstrWidth) {
		this.mstrWidth = mstrWidth;
	}
	
	public String getTitle() {
		return mstrTitle;
	}

	public void setTitle(String mstrTitle) {
		this.mstrTitle = mstrTitle;
	}
	
	public String getTitleBullet() {
		return mstrTitleBullet;
	}

	public void setTitleBullet(String mstrTitleBullet) {
		this.mstrTitleBullet = mstrTitleBullet;
	}
	
	public boolean getIsCollapse() {
		return mbolIsCollapse;
	}

	public void setIsCollapse(boolean mbolIsCollapse) {
		this.mbolIsCollapse = mbolIsCollapse;
	}
	
	public boolean getShowTitle() {
		return mbolShowTitle;
	}
	
	public boolean isShowTitle() {
		return mbolShowTitle;
	}

	public void setShowTitle(boolean mbolShowTitle) {
		this.mbolShowTitle = mbolShowTitle;
	}
	
	public int doStartTag() throws JspException {
		StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\"");
			objBuf.append(" width=\""+mstrWidth+"\"");
			objBuf.append(">\n");
			objBuf.append("  <tr>\n");
			
			if (mbolShowTitle) {
				objBuf.append("    <td class=\"windowsTopLeft \"></td>\n");
				objBuf.append("    <td class=\"windowsTop\"");
				if (mstrCssClass!=null) {
					objBuf.append(" class=\""+mstrCssClass+"\"");
				}
				objBuf.append(">\n");
				
				if (mstrTitleBullet!=null) {
					objBuf.append("<img src=\""+mstrTitleBullet+"\"");
					if (mbolIsCollapse)
						objBuf.append(" onclick=\"toggle('"+mstrName+"');\" style=\"cursor: pointer;\"");
					objBuf.append(">&nbsp&nbsp;");
				}
				objBuf.append("<strong>");
				if (mstrTitle!=null) {
					objBuf.append(mstrTitle);
				}
				
				objBuf.append("</strong></td>\n");
				objBuf.append("    <td class=\"windowsTopRight\"></td>\n");
			} else {
				objBuf.append("    <td class=\"windowsNoTopLeft \"></td>\n");
				objBuf.append("    <td class=\"windowsNoTop\"></td>\n");
				objBuf.append("    <td class=\"windowsNoTopRight\"></td>\n");
			}
			objBuf.append("  </tr>\n");
			objBuf.append("  <tr>\n");
			objBuf.append("    <td class=\"windowsLeft \"></td>\n");
			objBuf.append("    <td class=\"windows\">\n");
			if (mbolIsCollapse)
				objBuf.append("    <span id=\""+mstrName+"\" style=\"display:block;\">");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write grid contents!", IOEx);
        }
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			if (mbolIsCollapse)
				objBuf.append("    </span>");
			objBuf.append("    </td>\n");
			objBuf.append("    <td class=\"windowsRight\"></td>\n");
			objBuf.append("  </tr>\n");
			objBuf.append("  <tr>\n");
			objBuf.append("    <td class=\"windowsBottomLeft\"></td>\n");
			objBuf.append("    <td class=\"windowsBottom\"></td>\n");
			objBuf.append("    <td class=\"windowsBottomRight\"></td>\n");
			objBuf.append("  </tr>\n");
			objBuf.append("</table>\n");
			pageContext.getOut().println(objBuf);
			release();
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write grid contents!", IOEx);
        }
		return EVAL_PAGE;
	}
	
	public void release() {
		try {
			mstrName = null;
			mstrTitle = null;
			mstrCssClass = null;
			mstrTitleBullet = null;
			super.release();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
