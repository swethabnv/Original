package com.dcs.dcswc.common;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import org.apache.commons.beanutils.PropertyUtils;

public class ValidateListToList extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -8456933358323836769L;
	
	private String mstrOnChange = null;
	private String mstrFieldValue = null;
	private String mstrFieldName = null;
	private boolean mbolAutoSelectAll = false;
	private Collection mlstDataSource = null;
	
	public Collection getDataSource() {
		return mlstDataSource;
	}
	
	public void setDataSource(List mlstDataSource) {
		this.mlstDataSource = mlstDataSource;
	}
	
	public void setDataSource(Set mlstDataSource) {
		this.mlstDataSource = mlstDataSource;
	}

	public void setDataSource(Collection mlstDataSource) {
		this.mlstDataSource = mlstDataSource;
	}
	
	public String getOnChange() {
		return mstrOnChange;
	}

	public void setOnChange(String mstrOnChange) {
		this.mstrOnChange = mstrOnChange;
	}
	
	public String getFieldName() {
		return mstrFieldName;
	}

	public void setFieldName(String mstrFieldName) {
		this.mstrFieldName = mstrFieldName;
	}
	
	public String getFieldValue() {
		return mstrFieldValue;
	}

	public void setFieldValue(String mstrFieldValue) {
		this.mstrFieldValue = mstrFieldValue;
	}
	
	public boolean getAutoSelectAll() {
		return mbolAutoSelectAll;
	}

	public void setAutoSelectAll(boolean mbolAutoSelectAll) {
		this.mbolAutoSelectAll = mbolAutoSelectAll;
	}
	
	public int doStartTag() throws JspException
    {
		//ValidateForm objTmp = getValidateForm(this);
        //if (objTmp==null)
        //    throw new JspException("Error: ValidateTextToList tag needs to be a child of ValidateForm!");

        StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<table");

			objBuf.append("><tr>\n");
			objBuf.append("<td width=\"50%\">\n<select style=\"width: 100%\" multiple=\"multiple\"");
			if (mintSize>0)
				objBuf.append(" size=\""+mintSize+"\"");			
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			if (mstrName!=null) {
				objBuf.append(" name=\""+mstrName+"_lst\"");
				objBuf.append(" id=\""+mstrName+"_lst\"");
			}
			objBuf.append(">\n");
			
			HashMap<String, String> map = new HashMap<String, String>();
			if (mlstDataSource!=null && mlstDataSource.size()>0) {
				StringTokenizer st = new StringTokenizer(mstrFieldName,",");
				String[] token = new String[st.countTokens()];
				int c = 0;
			    while (st.hasMoreTokens()) {
			        token[c++] = st.nextToken();
			    }			   
				Object objs[] = mlstDataSource.toArray();
				for (int i=0; i<objs.length; i++) {
					String value = new String();
					for (int j=0; j<token.length; j++) {
						value += ""+ PropertyUtils.getProperty(objs[i], token[j]);
						if (j<token.length-1) value += " - ";
					}
					if (mstrFieldValue!=null && mstrFieldValue.length()>0) {
						String v = ""+ PropertyUtils.getProperty(objs[i], mstrFieldValue);
						if (mstrProperty!=null) {
							String strl[] = getPropertyValues();
							boolean found = false;
							for (int l=0; strl!=null && l<strl.length; l++) {
								if (v.equals(strl[l])) {
									found = true;
									break;
								}
							}
							if (!found) objBuf.append("<OPTION value=\""+v+"\">"+value+"</OPTION>\n");
						} else {
							objBuf.append("<OPTION value=\""+v+"\">"+value+"</OPTION>\n");
						}
						map.put(v, value);
					} else {
						objBuf.append("<OPTION value=\""+value+"\">"+value+"</OPTION>\n");
					}
				}
			}
			objBuf.append("</select>\n");
			String path = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
			objBuf.append("<td style=\"padding-left: 5px; padding-right: 5px;\">\n");
			objBuf.append("<img src=\""+path+"/dcswc/images/Next.gif\" style=\"cursor: pointer\" onclick=\"moveItem(document.getElementById('"+mstrName+"_lst'),document.getElementById('"+mstrName+"'));");
			if (mstrOnChange!=null) {
				objBuf.append(mstrOnChange);
			}
			objBuf.append("\"");
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			objBuf.append(">\n");
			objBuf.append("<img src=\""+path+"/dcswc/images/Previous.gif\" style=\"cursor: pointer\" onclick=\"moveItem(document.getElementById('"+mstrName+"'),document.getElementById('"+mstrName+"_lst'));");
			if (mstrOnChange!=null) {
				objBuf.append(mstrOnChange);
			}
			objBuf.append("\"");
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			objBuf.append(">\n");
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
					if (mstrFieldValue!=null && mstrFieldValue.length()>0) {
						for (int i=0; i<strl.length; i++) {
							String value = map.get(strl[i]);
							objBuf.append("<OPTION value=\""+strl[i]+"\">"+value+"</OPTION>\n");
						}
					} else {
						for (int i=0; i<strl.length; i++) {
							objBuf.append("<OPTION value=\""+strl[i]+"\">"+strl[i]+"</OPTION>\n");
						}
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
        	if (objTmp!=null)
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
	
	private ValidateListToList getCopy() {
		ValidateListToList objItm = new ValidateListToList();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setAutoSelectAll(this.mbolAutoSelectAll);
    	objItm.setDataSource(this.mlstDataSource);
    	objItm.setOnChange(mstrOnChange);
    	objItm.setFieldName(this.mstrFieldName);
    	objItm.setFieldValue(this.mstrFieldValue);
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
