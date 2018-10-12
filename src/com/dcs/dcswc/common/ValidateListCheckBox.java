package com.dcs.dcswc.common;

import java.io.IOException;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;


public class ValidateListCheckBox extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -3431692829777092297L;
	
	private Collection mlstDataSource = null;
	private String mstrKeyField = null;
	private String mstrDisplayField = null;
	protected String mstrOnClick = null;
	private String[] checkList = null;
	private int mintColumnCount = 0;
	
	public String getOnClick() {
		return mstrOnClick;
	}

	public void setOnClick(String mstrOnClick) {
		this.mstrOnClick = mstrOnClick;
	}
	
	public Collection getDataSource() {
		return mlstDataSource;
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
	
	public int getColumnCount() {
		return mintColumnCount;
	}

	public void setColumnCount(int mintColumnCount) {
		this.mintColumnCount = mintColumnCount;
	}
	
	public String[] getCheckList() {
		return checkList;
	}

	public void setCheckList(String[] checkList) {
		this.checkList = checkList;
	}
		
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateListCheckBox tag needs to be a child of ValidateForm!");

        StringBuffer objBuf = null;
		try {			
			objBuf = new StringBuffer();
			if (mlstDataSource!=null) {
				if (mintColumnCount<=1)
					objBuf.append(writeCheckBoxList());
				else 
					objBuf.append(writeCheckBoxListTable());
			} else if (checkList!=null) {
				objBuf.append(writeCheckBoxList2());
			}
			
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public StringBuffer writeCheckBoxListTable() {
		StringBuffer objBuf = new StringBuffer();
		StringTokenizer st = new StringTokenizer(mstrDisplayField,",");	
		String[] token = new String[st.countTokens()];
		int c = 0;
	    while (st.hasMoreTokens()) {
	        token[c++] = st.nextToken();
	    }
	    String[] values = getPropertyValues();
	    objBuf.append("<table");
	    if (mstrCssClass!=null) {
	    	objBuf.append(" class=\""+mstrCssClass+"\"");
	    }
	    objBuf.append(">");
	    int col=0;
		try{
			Object objs[] = mlstDataSource.toArray();
			for (int i=0; i<objs.length; i++) {
				Object obj = objs[i];
				
				String dpdValue = ""+ PropertyUtils.getProperty(obj, mstrKeyField);
				boolean checked = false;
				if (values!=null) {
					for (int j=0; j<values.length; j++) {
						if (dpdValue.equals(values[j])) {
							checked = true;
							break;
						}
					}
				}
				if (col==0) {
					objBuf.append("<tr>");
				}
				
				String onClick = "";
				if (mstrOnClick!=null) {
					onClick = " onclick=\""+mstrOnClick+"\"";
				}
				
				if (checked) {
					objBuf.append("<td><INPUT type=\"checkbox\" name=\""+mstrName+"\" value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\" checked=\"checked\""+onClick+">");
				} else {
					objBuf.append("<td><INPUT type=\"checkbox\" name=\""+mstrName+"\" value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\""+onClick+">");
				}
				
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
	
	public StringBuffer writeCheckBoxList() {
		StringBuffer objBuf = new StringBuffer();
		StringTokenizer st = new StringTokenizer(mstrDisplayField,",");	
		String[] token = new String[st.countTokens()];
		int c = 0;
	    while (st.hasMoreTokens()) {
	        token[c++] = st.nextToken();
	    }
	    String[] values = getPropertyValues();
	    
		try{
			Object objs[] = mlstDataSource.toArray();
			for (int i=0; i<objs.length; i++) {
				Object obj = objs[i];
				
				String dpdValue = ""+ PropertyUtils.getProperty(obj, mstrKeyField);
				boolean checked = false;
				if (values!=null) {
					for (int j=0; j<values.length; j++) {
						if (dpdValue.equals(values[j])) {
							checked = true;
							break;
						}
					}
				}
				
				String onClick = "";
				if (mstrOnClick!=null) {
					onClick = " onclick=\""+mstrOnClick+"\"";
				}
				
				if (checked) {
					objBuf.append("<INPUT type=\"checkbox\" name=\""+mstrName+"\" value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\" checked=\"checked\""+onClick+">");
				} else {
					objBuf.append("<INPUT type=\"checkbox\" name=\""+mstrName+"\" value=\""+PropertyUtils.getProperty(obj, mstrKeyField)+"\""+onClick+">");
				}
				
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
	
	public StringBuffer writeCheckBoxList2() {
		StringBuffer objBuf = new StringBuffer();	

	    String[] values = getPropertyValues();
	    
		try{
			for (int i=0; i<checkList.length; i++) {
				boolean checked = false;
				if (values!=null) {
					for (int j=0; j<values.length; j++) {
						if (checkList[i].equals(values[j])) {
							checked = true;
							break;
						}
					}
				}
				
				String onClick = "";
				if (mstrOnClick!=null) {
					onClick = " onclick=\""+mstrOnClick+"\"";
				}
				
				if (checked) {
					objBuf.append("<INPUT type=\"checkbox\" name=\""+mstrName+"\" value=\""+checkList[i]+"\" checked=\"checked\""+onClick+">");
				} else {
					objBuf.append("<INPUT type=\"checkbox\" name=\""+mstrName+"\" value=\""+checkList[i]+"\""+onClick+">");
				}
				
				objBuf.append(checkList[i]);
				objBuf.append("&nbsp;&nbsp;");
			}
		} catch(Exception ex) {	
		}
		return objBuf;
	}
	
	public int doEndTag() throws JspException
    {
        ValidateForm objTmp = null;

        try
        {
        	objTmp = getValidateForm(this);
            objTmp.addChild(getCopy());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: ValidateListCheckBox is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateListCheckBox getCopy() {
		ValidateListCheckBox objItm = new ValidateListCheckBox();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	objItm.setDataSource(this.mlstDataSource);
    	objItm.setDisplayField(this.mstrDisplayField);
    	objItm.setOnClick(this.mstrOnClick);
    	objItm.setKeyField(this.mstrKeyField);
    	return objItm;
	}

	

}
