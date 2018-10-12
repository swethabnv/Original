package com.dcs.dcswc.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.servlet.jsp.JspException;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
import com.dcs.util.CollectionUtil;

public class ValidateTreeRadio extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -7460975968362529915L;
	
	private Collection mlstDataSource = null;
	private String mstrKeyField = null;
	private String mstrDisplayField = null;
	private String textWidth = null;
	private String radioWidth = null;
	private String defaultValue = null;
	private String[] radioList = null;
	private String[] radioValueList = null;
	private List<TreeRadio> treeRadioList = null;
	private HashMap<String,TreeRadio> treeRadioMap = null;
	
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
	
	public String getRadioWidth() {
		return radioWidth;
	}

	public void setRadioWidth(String radioWidth) {
		this.radioWidth = radioWidth;
	}

	public String getTextWidth() {
		return textWidth;
	}

	public void setTextWidth(String textWidth) {
		this.textWidth = textWidth;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateTreeRadio tag needs to be a child of ValidateForm!");
        
        StringBuffer objBuf = null;
		try {
			String value = getPropertyValue();
			objBuf = new StringBuffer();
			
			treeRadioList = new ArrayList<TreeRadio>();
			createTreeRadioList(this.mlstDataSource, treeRadioList, "");
			treeRadioMap = CollectionUtil.mapFromList(treeRadioList, "key");
			
			if (value!=null) {
				List treeRadioValue = (List) JSONArray.toCollection(JSONArray.fromObject(value), TreeRadio.class);
				for(int i=0; i<treeRadioValue.size(); i++) {
					TreeRadio treeRadio1 = (TreeRadio)treeRadioValue.get(i);
					TreeRadio treeRadio2 = (TreeRadio)treeRadioMap.get(treeRadio1.getKey());
					if (treeRadio2==null) {
						treeRadioList.add(treeRadio1);
						treeRadioMap.put(treeRadio1.getKey(), treeRadio1);
					} else {
						treeRadio2.setValue(treeRadio1.getValue());
					}
				}
			} 
			
			objBuf.append("  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\"");
			if (mstrCssClass!=null) {
				objBuf.append(" class=\""+mstrCssClass+"\"");
			}
			objBuf.append(">");
			objBuf.append(writeRadioHeader());
			objBuf.append(writeTree(this.mlstDataSource, 1, ""));
			objBuf.append("  </table>\n");
			
			objBuf.append("<SCRIPT language=\"JavaScript\">\n");
			objBuf.append("var "+this.mstrName+" = "+JSONArray.fromObject(treeRadioList).toString()+";\n");
			objBuf.append("</SCRIPT>\n");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public StringBuffer writeRadioHeader() {
		StringBuffer objBuf = new StringBuffer();
		String radioWidth = "";
		String textWidth = "";	
		if (this.radioWidth!=null) {
			radioWidth = " width=\""+this.radioWidth+"\"";
		}
		if (this.textWidth!=null) {
			textWidth = " width=\""+this.textWidth+"\"";
		}
		try {
			objBuf.append("<tr><td"+textWidth+">&nbsp;</td>");
			for (int i=0; i<radioList.length; i++) {
				objBuf.append("<td align=\"center\""+radioWidth+"><b>"+radioList[i]+"</b></td>");
			}
			objBuf.append("</tr>");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return objBuf;
	}
	
	public StringBuffer writeTree(Collection mlstDataSource, int level, String prefix) {
		StringBuffer objBuf = new StringBuffer();
		if (mlstDataSource==null) return objBuf;
		Object objs[] = mlstDataSource.toArray();
		try {
			for (int i=0; i<objs.length; i++) {
				ITree obj = (ITree) objs[i];
				String keyValue = ""+ PropertyUtils.getProperty(obj, mstrKeyField);
				String displayValue = ""+ PropertyUtils.getProperty(obj, mstrDisplayField);
								
//				if (obj.getChild()!=null && obj.getChild().size()>0) {
//					objBuf.append("<tr><td style=\"padding-left: "+((level-1)*20)+"px\">");
//					objBuf.append(displayValue);
//					objBuf.append("</td><td colspan=\""+radioList.length+"\"></td></tr>");
//					objBuf.append(writeTree(obj.getChild(), level+1, prefix+keyValue+","));
//				} else {					
					if (level==1) {
						objBuf.append("<tr><td style=\"padding-left: "+((level-1)*20)+"px\">");
						objBuf.append("<b>"+displayValue+"</b>");
					} else {
						objBuf.append("<tr><td style=\"padding-left: "+((level-1)*20)+"px\">");
						objBuf.append(displayValue);
					}
					objBuf.append("</td>");
					for (int j=0; j<radioList.length; j++) {
						String v = radioList[j];
						if (radioValueList!=null && radioValueList.length==radioList.length) {
							v = radioValueList[j];
						}
						objBuf.append("<td align=\"center\"><INPUT type=\"radio\" name=\""+this.mstrName+"_"+keyValue+"\" value=\""+v+"\"");
						if (obj.getChild()!=null && obj.getChild().size()>0) {
							objBuf.append(" onclick=\"setChildRadioValue(this,'"+this.mstrName+"','"+prefix+keyValue+"','"+v+"');\"");
						} else {
							objBuf.append(" onclick=\"setTreeRadioValue('"+this.mstrName+"','"+prefix+keyValue+"','"+v+"');\"");
						}
						TreeRadio value = (TreeRadio) treeRadioMap.get(prefix+keyValue);
						if (value!=null && v.equals(value.getValue())) {
							objBuf.append(" checked=\"checked\"");
						}
						objBuf.append("></td>");
					}
					objBuf.append("</tr>");
					if (obj.getChild()!=null && obj.getChild().size()>0) {
						objBuf.append(writeTree(obj.getChild(), level+1, prefix+keyValue+","));
					}
//				}
			}
		} catch(Exception ex) {	
			ex.printStackTrace();
		}
		return objBuf;
	}
	
	public void createTreeRadioList(Collection mlstDataSource, List<TreeRadio> result, String prefix) {
		Object objs[] = mlstDataSource.toArray();
		try {
			for (int i=0; i<objs.length; i++) {
				ITree obj = (ITree) objs[i];
				String keyValue = ""+ PropertyUtils.getProperty(obj, mstrKeyField);
				if (obj.getChild()!=null && obj.getChild().size()>0) {
					createTreeRadioList(obj.getChild(), result, prefix+keyValue+",");
				} else {
					TreeRadio treeRadio = new TreeRadio();
					treeRadio.setKey(prefix+keyValue);
					if (this.defaultValue!=null)
						treeRadio.setValue(this.defaultValue);
					else 
						treeRadio.setValue("");
					result.add(treeRadio);
				}
			}
		} catch(Exception ex) {	
			ex.printStackTrace();
		}
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
            throw new JspException("Error: ValidateTreeRadio is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateTreeRadio getCopy() {
		ValidateTreeRadio objItm = new ValidateTreeRadio();
    	super.copyAttributesTo(objItm);
    	objItm.mlstDataSource = this.mlstDataSource;
    	objItm.mstrDisplayField = this.mstrDisplayField;
    	objItm.mstrKeyField = this.mstrKeyField;
    	objItm.radioList = this.radioList;
    	objItm.radioValueList = this.radioValueList;
    	return objItm;
	}	

}
