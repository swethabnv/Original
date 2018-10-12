package com.dcs.dcswc.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;

public class ValidateTextToList extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = 8001377497595853163L;
	private String mstrRegExp = null;
	private String mstrOnChange = null;
	private boolean mbolAutoSelectAll = false;
	
	public String getRegExp() {
		return mstrRegExp;
	}
	
	public void setRegExp(String mstrRegExp) {
		this.mstrRegExp = mstrRegExp;
	}
	
	public String getOnChange() {
		return mstrOnChange;
	}

	public void setOnChange(String mstrOnChange) {
		this.mstrOnChange = mstrOnChange;
	}
	
	public boolean getAutoSelectAll() {
		return mbolAutoSelectAll;
	}

	public void setAutoSelectAll(boolean mbolAutoSelectAll) {
		this.mbolAutoSelectAll = mbolAutoSelectAll;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateTextToList tag needs to be a child of ValidateForm!");

        StringBuffer objBuf = null;
		try {			
			objBuf = new StringBuffer();
			objBuf.append("<table");

			objBuf.append("><tr>\n");
			objBuf.append("<td width=\"50%\"><input type=\"text\" style=\"width: 100%\"");
			if (mstrName!=null) 
				objBuf.append(" name=\""+mstrName+"_txt\" id=\""+mstrName+"_txt\"></td>\n");
			
			String path = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
			objBuf.append("<td style=\"padding-left: 5px; padding-right: 5px;\">\n");
			objBuf.append("<img src=\""+path+"/dcswc/images/Next.gif\" style=\"cursor: pointer\" onclick=\"addToList(document.getElementById('"+mstrName+"_txt'), document.getElementById('"+mstrName+"'), '"+mstrRegExp+"');");
			if (mstrOnChange!=null) {
				objBuf.append(mstrOnChange);
			}
			objBuf.append("\">\n");
			objBuf.append("<img src=\""+path+"/dcswc/images/Previous.gif\" style=\"cursor: pointer\"  onclick=\"removeFromList(document.getElementById('"+mstrName+"_txt'), document.getElementById('"+mstrName+"'));");
			if (mstrOnChange!=null) {
				objBuf.append(mstrOnChange);
			}
			objBuf.append("\">\n");
			objBuf.append("</td>\n");
			
			objBuf.append("<td width=\"50%\">\n<select style=\"width: 100%\" multiple=\"multiple\"");
			if (mintSize>0)
				objBuf.append(" size=\""+mintSize+"\"");			
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			if (mstrName!=null) {
				objBuf.append(" name=\""+mstrName+"\"");
				objBuf.append(" id=\""+mstrName+"\"");
			}
			
			objBuf.append(">\n");
			
			if (mstrProperty!=null) {
				String strl[] = getPropertyValues();
				if (strl!=null) {
					for (int i=0; i<strl.length; i++) {
						objBuf.append("<OPTION value=\""+strl[i]+"\">"+strl[i]+"</OPTION>\n");
					}
				}
			}
			
			objBuf.append("</select>\n</td>\n");
			objBuf.append("</tr></table>\n");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw new JspException("Error: Unable to write contents!", ex);
        }
        return SKIP_BODY;
    }
	
	public int doEndTag() throws JspException {
        ValidateForm objTmp = null;
        try {
        	objTmp = getValidateForm(this);
            objTmp.addChild(getCopy());
        }
        catch (ClassCastException CCEx) {
            throw new JspException("Error: ValidateTextToList is not a child of ValidateForm", CCEx);
        }
        finally {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateTextToList getCopy() {
		ValidateTextToList objItm = new ValidateTextToList();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setAutoSelectAll(this.mbolAutoSelectAll);
    	objItm.setOnChange(mstrOnChange);
    	return objItm;
	}
	
	public String[] getPropertyStringValue() {
		try {
			if (mstrProperty!=null && !"".equals(mstrProperty)) {
				String obj[] = (String[])PropertyUtils.getProperty(getValidateForm(this).formBean, mstrProperty);				
				return obj;
			}
			return null;
		} catch(Exception ex) {
			System.out.println("Error get property :"+mstrProperty);
			return null;
		}
	}
		
}
