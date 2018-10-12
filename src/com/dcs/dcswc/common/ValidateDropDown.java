package com.dcs.dcswc.common;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.io.IOException;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;

public class ValidateDropDown extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = 8001377497595853163L;
	private Collection mlstDataSource = null;
	private String mstrKeyField = null;
	private String mstrDisplayField = null;
	private String mstrPropertyList = null;
	private String onChange = null;
	private String[] optionList = null;
	private String[] optionValueList = null;
	
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
	
	public String getDisplayField() {
		return mstrDisplayField;
	}

	public void setDisplayField(String mstrDisplayField) {
		this.mstrDisplayField = mstrDisplayField;
	}

	public String getKeyField() {
		return mstrKeyField;
	}

	public void setKeyField(String mstrKeyField) {
		this.mstrKeyField = mstrKeyField;
	}
	
	public String getPropertyList() {
		return mstrPropertyList;
	}

	public void setPropertyList(String mstrPropertyList) {
		this.mstrPropertyList = mstrPropertyList;
	}
	
	public String[] getOptionList() {
		return optionList;
	}

	public void setOptionList(String[] optionList) {
		this.optionList = optionList;
	}
	
	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	
	public String[] getOptionValueList() {
		return optionValueList;
	}

	public void setOptionValueList(String[] optionValueList) {
		this.optionValueList = optionValueList;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateDropDown tag needs to be a child of ValidateForm!");

        StringBuffer objBuf = null;
		try {			
			objBuf = new StringBuffer();
			objBuf.append("<select ");
			
			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			if (mstrName!=null) {
				objBuf.append(" name=\""+mstrName+"\"");
				objBuf.append(" id=\""+mstrName+"\"");
			}
			if (mstrStyle!=null)
				objBuf.append(" style=\""+mstrStyle+"\"");
			if (onChange!=null)
				objBuf.append(" onchange=\""+onChange+"\"");
			
			objBuf.append(">\n");
			
			if (!mbolIsRequire)
				objBuf.append("<OPTION></OPTION>\n");
			
			if (mstrDisplayField!=null) {
				objBuf.append(writeDropDownItem());
			} else if (mstrPropertyList!=null) {
				objBuf.append(writeDropDownPropertyItem());
			} else if (optionList != null) {
				objBuf.append(writeDropDownOptionItem());
			}
			
			objBuf.append("</select>\n");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public StringBuffer writeDropDownItem() {
		StringBuffer objBuf = new StringBuffer();
		StringTokenizer st = new StringTokenizer(mstrDisplayField,",");	
		String[] token = new String[st.countTokens()];
		int c = 0;
	    while (st.hasMoreTokens()) {
	        token[c++] = st.nextToken();
	    }
	    String value = getPropertyValue();
		try{
			Object objs[] = mlstDataSource.toArray();
			for (int i=0; i<objs.length; i++) {
				Object obj = objs[i];
				String dpdValue = ""+ PropertyUtils.getProperty(obj, mstrKeyField);
				if (dpdValue.equals(value)) {
					objBuf.append("<OPTION value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\" selected=\"selected\">");
				} else {
					objBuf.append("<OPTION value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\">");
				}
				for (int j=0; j<token.length; j++) {
					objBuf.append(PropertyUtils.getProperty(obj, token[j]));
					if (j<token.length-1) objBuf.append(" - ");
				}
				objBuf.append("</OPTION>\n");
			}
		} catch(Exception ex) {	
		}
		return objBuf;
	}
	
	public StringBuffer writeDropDownPropertyItem() {
 		StringBuffer objBuf = new StringBuffer();
		if (mstrProperty!=null) {
			String strl[] = getPropertyValues(mstrPropertyList);
			if (strl!=null) {
				String value = getPropertyValue();
				for (int i=0; i<strl.length; i++) {
					if (strl[i].equals(value)) {
						objBuf.append("<OPTION value=\""+strl[i]+"\" selected=\"selected\">"+strl[i]+"</OPTION>\n");
					} else {
						objBuf.append("<OPTION value=\""+strl[i]+"\">"+strl[i]+"</OPTION>\n");
					}
				}
			}
		}
		return objBuf;
	}
	
	public StringBuffer writeDropDownOptionItem() {
 		StringBuffer objBuf = new StringBuffer();
		if (mstrProperty!=null) {
			String value = getPropertyValue();
			for (int i=0; i<optionList.length; i++) {
				String key = optionList[i];
				if (optionValueList !=null && optionValueList.length>0)
					key = optionValueList[i];
				if (key.equals(value)) {
					objBuf.append("<OPTION value=\""+key+"\" selected=\"selected\">"+optionList[i]+"</OPTION>\n");
				} else {
					objBuf.append("<OPTION value=\""+key+"\">"+optionList[i]+"</OPTION>\n");
				}
			}
		}
		return objBuf;
	}
	
	public int doEndTag() throws JspException {
        ValidateForm objTmp = null;
        try {
        	objTmp = getValidateForm(this);
            objTmp.addChild(getCopy());
        }
        catch (ClassCastException CCEx) {
            throw new JspException("Error: ValidateDropDown is not a child of ValidateForm", CCEx);
        }
        finally {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }	
	
	private ValidateDropDown getCopy() {
		ValidateDropDown objItm = new ValidateDropDown();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setPropertyList(this.mstrPropertyList);
    	objItm.setDataSource(this.mlstDataSource);
    	objItm.setKeyField(this.mstrKeyField);
    	objItm.setOnChange(this.onChange);
    	objItm.setDisplayField(this.mstrDisplayField);
    	objItm.setOptionList(this.optionList);
    	objItm.setOptionValueList(this.optionValueList);
    	return objItm;
	}	
	
}
