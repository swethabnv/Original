package com.dcs.dcswc.common;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.io.IOException;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;

public class ValidateListRadio extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -5214132554869425566L;
	private Collection mlstDataSource = null;
	private String mstrKeyField = null;
	private String mstrDisplayField = null;
	private String mstrOnClick = null;
	private String[] radioList = null;
	private String[] radioValueList = null;
	private int mintColumnCount = 0;
	
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
	
	public int getColumnCount() {
		return mintColumnCount;
	}

	public void setColumnCount(int mintColumnCount) {
		this.mintColumnCount = mintColumnCount;
	}
	public String getKeyField() {
		return mstrKeyField;
	}
	public void setKeyField(String mstrKeyField) {
		this.mstrKeyField = mstrKeyField;
	}
	public String getOnClick() {
		return mstrOnClick;
	}
	public void setOnClick(String mstrOnClick) {
		this.mstrOnClick = mstrOnClick;
	}
	public String[] getRadioList() {
		return radioList;
	}
	public void setRadioList(String[] radioList) {
		this.radioList = radioList;
	}
	public String[] getRadioValueList() {
		return radioValueList;
	}
	public void setRadioValueList(String[] radioValueList) {
		this.radioValueList = radioValueList;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateListRadio tag needs to be a child of ValidateForm!");

        StringBuffer objBuf = null;
		try {			
			objBuf = new StringBuffer();
			if (mlstDataSource!=null) {
				if (mintColumnCount<=1)
					objBuf.append(writeRadioList());
				else 
					objBuf.append(writeRadioListTable());
			} else if (radioList!=null) {
				objBuf.append(writeRadioList2());
			}
			
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public StringBuffer writeRadioListTable() {
		StringBuffer objBuf = new StringBuffer();
		StringTokenizer st = new StringTokenizer(mstrDisplayField,",");	
		String[] token = new String[st.countTokens()];
		int c = 0;
	    while (st.hasMoreTokens()) {
	        token[c++] = st.nextToken();
	    }
	    String value = getPropertyValue();
	    objBuf.append("<table>");
	    int col=0;
		try{
			Object objs[] = mlstDataSource.toArray();
			for (int i=0; i<objs.length; i++) {
				Object obj = objs[i];
				
				String dpdValue = ""+ PropertyUtils.getProperty(obj, mstrKeyField);
				boolean checked = false;
				if (dpdValue.equals(value)) checked = true;
							
				if (col==0) {
					objBuf.append("<tr>");
				}
				
				if (checked) {
					objBuf.append("<td><INPUT type=\"radio\" name=\""+mstrName+"\" value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\" checked=\"checked\"");
				} else {
					objBuf.append("<td><INPUT type=\"radio\" name=\""+mstrName+"\" value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\"");
				}
				if (this.mstrOnClick!=null)
					objBuf.append(" onclick=\""+this.mstrOnClick+"\"");
				objBuf.append(" >");
				for (int j=0; j<token.length; j++) {
					objBuf.append(PropertyUtils.getProperty(obj, token[j]));
					if (j<token.length-1) objBuf.append(" - ");
				}
				objBuf.append("</td>");
				col++;
				if (col==mintColumnCount) {
					col=0;
					objBuf.append("</tr>");
				}
				
			}
		} catch(Exception ex) {	
		}
		objBuf.append("</table>");
		return objBuf;
	}
	
	public StringBuffer writeRadioList() {
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
				boolean checked = false;
				if (dpdValue.equals(value)) checked = true;

				if (checked) {
					objBuf.append("<INPUT type=\"radio\" name=\""+mstrName+"\" value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\" checked=\"checked\"");
				} else {
					objBuf.append("<INPUT type=\"radio\" name=\""+mstrName+"\" value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\"");
				}
				if (this.mstrOnClick!=null)
					objBuf.append(" onclick=\""+this.mstrOnClick+"\"");
				objBuf.append(" >");
				for (int j=0; j<token.length; j++) {
					objBuf.append(PropertyUtils.getProperty(obj, token[j]));
					if (j<token.length-1) objBuf.append(" - ");
				}
				if (mintColumnCount==1)
					objBuf.append("<br>\n");
				else	
					objBuf.append("&nbsp;&nbsp;");
			}
		} catch(Exception ex) {	
		}
		return objBuf;
	}
	
	public StringBuffer writeRadioList2() {
		StringBuffer objBuf = new StringBuffer();	

	    String value = getPropertyValue();
	    
		try{
			for (int i=0; i<radioList.length; i++) {
				String v = radioList[i];
				boolean checked = false;
				if (radioValueList!=null && radioValueList.length==radioList.length) {
					v = radioValueList[i];
				}
				if (v.equals(value)) checked = true;
				
				if (checked) {
					objBuf.append("<INPUT type=\"radio\" name=\""+mstrName+"\" value=\""+v+"\" checked=\"checked\"");
				} else {
					objBuf.append("<INPUT type=\"radio\" name=\""+mstrName+"\" value=\""+v+"\"");
				}
				if (this.mstrOnClick!=null)
					objBuf.append(" onclick=\""+this.mstrOnClick+"\"");
				objBuf.append(" >");
				objBuf.append(radioList[i]);
				objBuf.append("&nbsp;&nbsp;");
			}
		} catch(Exception ex) {	
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
            throw new JspException("Error: ValidateListRadio is not a child of ValidateForm", CCEx);
        }
        finally {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }	
	
	private ValidateListRadio getCopy() {
		ValidateListRadio objItm = new ValidateListRadio();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setRadioList(this.radioList);
    	objItm.setRadioValueList(this.radioValueList);
    	objItm.setDataSource(this.mlstDataSource);
    	objItm.setKeyField(this.mstrKeyField);
    	objItm.setDisplayField(this.mstrDisplayField);
    	objItm.setOnClick(this.mstrOnClick);
    	return objItm;
	}

}
