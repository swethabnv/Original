package com.dcs.dcswc.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

public class ValidateForm extends BodyTagSupport {
	private static final long serialVersionUID = -7665246562327583435L;
	
	private String mstrFormName = null;
	private String mstrFormAction = null;
	private String mstrFormBean = null;
	private String mstrEnctype = null;
	private String mstrOnSubmit = null;
	private boolean mbolAlert;
	private boolean subForm;
	private String mstrFormMethod = "post";
	private ArrayList<IValidateTag> mstrFormChild = null;
	private ValidateSummary mValSum = null;
	private MessageResources resource = null;
	public Object formBean =null;
	
	public void addChild(IValidateTag objVal) {
		mstrFormChild.add(objVal);
	}
	
	public String getFormAction() {
		return this.mstrFormAction;
	}
	
	public void setFormAction(String mstrFormAction) {
		this.mstrFormAction = mstrFormAction;
	}
	
	public String getFormBean() {
		return this.mstrFormBean;
	}
	
	public void setFormBean(String mstrFormBean) {
		this.mstrFormBean = mstrFormBean;
	}
	
	public String getFormName() {
		return this.mstrFormName;
	}
	
	public void setFormName(String mstrFormName) {
		this.mstrFormName = mstrFormName;
	}
	
	public void setFormMethod(String mstrFormMethod) {
		this.mstrFormMethod = mstrFormMethod;
	}
	
	public String getFormMethod() {
		return this.mstrFormMethod;
	}
	
	public void setOnSubmit(String mstrOnSubmit) {
		this.mstrOnSubmit = mstrOnSubmit;
	}
	
	public String getEnctype() {
		return this.mstrEnctype;
	}
	
	public void setEnctype(String mstrEnctype) {
		this.mstrEnctype = mstrEnctype;
	}
	
	public String getOnSubmit() {
		return this.mstrOnSubmit;
	}
	
	public void setValidateSummary(ValidateSummary mValSum) {
		this.mValSum = mValSum;
	}
	
	public void setAlert(boolean alert) {
		this.mbolAlert = alert;
	}
	
	public int doStartTag() throws JspException {
		StringBuffer objBuf = null;
		try {
			mstrFormChild = new ArrayList<IValidateTag>();
			objBuf = new StringBuffer();
			resource = (MessageResources) pageContext.getServletContext().getAttribute(Globals.MESSAGES_KEY);
			
			if(mstrFormBean!=null)
				formBean = pageContext.getRequest().getAttribute(mstrFormBean);
			
			if (!this.subForm) {
				objBuf.append("<form");
				
				if (mstrFormName!=null)
					objBuf.append(" name=\""+mstrFormName+"\"");
				if (mstrFormAction!=null)
					if (mstrFormAction.startsWith("/"))
						objBuf.append(" action=\""+mstrFormAction+"\"");
					else
						objBuf.append(" action=\""+((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/"+mstrFormAction+"\"");
					
				if (mstrFormMethod!=null)
					if ("post".equals(mstrFormMethod.toLowerCase()) || "get".equals(mstrFormMethod.toLowerCase()))
						objBuf.append(" method=\""+mstrFormMethod+"\"");
					else
						objBuf.append(" method=\"post\"");
				if (mstrEnctype!=null)
					objBuf.append(" enctype=\""+mstrEnctype+"\"");
				
				objBuf.append(" onsubmit=\"return (validate_"+mstrFormName+"()");			
				if (mstrOnSubmit!=null)
					objBuf.append(" && "+mstrOnSubmit);
				objBuf.append(")\"");
				
				objBuf.append(">");
			}
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
			if (!this.subForm) {
				objBuf.append("</form>\n");
			}
			objBuf.append("<div id=\"CalendarPopup\" style=\"position:absolute; width:175; height:130; background-color: threedlightshadow; display: none; z-index:99\"></div>\n");
			objBuf.append("<div id=\"ColorPopup\" style=\"position:absolute; width:150; display: none; z-index:99\"></div>\n");
			
			objBuf.append("<SCRIPT language=\"JavaScript\">\n");
			objBuf.append("var validate_"+mstrFormName+"_TempVar;\n");
			objBuf.append("function validate_"+mstrFormName+"() {\n");
			for (int i=0; i<mstrFormChild.size(); i++) {
				IValidateTag objItm = mstrFormChild.get(i);
				String name = objItm.getName().replace('[','_');
				name = name.replace(']', '_');
				objBuf.append(writeVerifyRequireField(objItm));
				if (objItm instanceof ValidateText) {
					ValidateText txtItm = (ValidateText) objItm;
					if (txtItm.getRegExp()!=null) {
						objBuf.append("  if (!validate_"+mstrFormName+"_"+name+"()) {\n");
						objBuf.append("    return false;\n");
						objBuf.append("  }\n");
					}
				} else if (objItm instanceof ValidateFile){
					ValidateFile txtItm = (ValidateFile) objItm;
					if (txtItm.getFileExt()!=null) {
						objBuf.append("  if (!validate_"+mstrFormName+"_"+name+"()) {\n");
						objBuf.append("    return false;\n");
						objBuf.append("  }\n");
					}
				} else if (objItm instanceof ValidateNumber){
					ValidateNumber txtItm = (ValidateNumber) objItm;
					if (txtItm.getRegExp()!=null) {
						objBuf.append("  if (!validate_"+mstrFormName+"_"+name+"()) {\n");
						objBuf.append("    return false;\n");
						objBuf.append("  }\n");
					}
				}
			}
			
			for (int i=0; i<mstrFormChild.size(); i++) {
				IValidateTag objItm = mstrFormChild.get(i);				
				if (objItm instanceof ValidateTextToList) {
					ValidateTextToList txt2lst = (ValidateTextToList) objItm;
					if (txt2lst.getAutoSelectAll()) {
						//objBuf.append("  selectAllItem(document."+mstrFormName+"."+txt2lst.getName()+");\n");
						objBuf.append("  selectAllItem(document.getElementById('"+txt2lst.getName()+"'));\n");
					}					
				} else if (objItm instanceof ValidateListToList) {
					ValidateListToList txt2lst = (ValidateListToList) objItm;
					if (txt2lst.getAutoSelectAll()) {
						//objBuf.append("  selectAllItem(document."+mstrFormName+"."+txt2lst.getName()+");\n");
						objBuf.append("  selectAllItem(document.getElementById('"+txt2lst.getName()+"'));\n");
					}
				}
			}
			
			for (int i=0; i<mstrFormChild.size(); i++) {
				IValidateTag objItm = mstrFormChild.get(i);
				if (objItm instanceof ValidateText) {
					objBuf.append("document.getElementById('"+objItm.getName()+"').value=document.getElementById('"+objItm.getName()+"').value.trim();\n");
				}
			}
			
			objBuf.append("  return true;\n");
			objBuf.append("}\n\n");
			
			objBuf.append(writeVerifyTextFunction());
			objBuf.append(writeVerifyFileFunction());
			objBuf.append(writeVerifyNumberFunction());
			
			objBuf.append("</SCRIPT>\n");			
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
			mstrFormName = null;
			mstrFormAction = null;
			mstrFormBean = null;
			mstrEnctype = null;
			mstrOnSubmit = null;
			if (mstrFormChild!=null) mstrFormChild=null;
			if (mValSum!=null) mValSum=null;
			super.release();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private StringBuffer writeVerifyRequireField(IValidateTag objItm) {
		StringBuffer objBuf = new StringBuffer();
		if (objItm.getIsRequire()) {
			StringBuffer msg = new StringBuffer(); 
			if (objItm.getErrMessageKey()!=null && objItm.getErrMessageKey().length()>0) {		
				msg.append(getResourceMessage(objItm.getErrMessageKey()));
			} else {
				msg.append(objItm.getName()+" is require");
			}
			
			objBuf.append("  if (document.getElementById('"+objItm.getName()+"').value.trim()=='') {\n");
			if (mValSum!=null)
				objBuf.append("    document.getElementById('"+mValSum.getName()+"').innerHTML='"+msg+"';\n");
			
			if (objItm.getLabel()!=null)
				objBuf.append("    document.getElementById('"+objItm.getLabel()+"').style.color='red';\n");
			
			if (this.mbolAlert==true)
				objBuf.append("    alert('"+msg+"');\n");
			
			objBuf.append("    return false;\n");			
			objBuf.append("  } else {\n");
			
			if (mValSum!=null)
				objBuf.append("    document.getElementById('"+mValSum.getName()+"').innerHTML='';\n");
			
			if (objItm.getLabel()!=null)
				objBuf.append("    document.getElementById('"+objItm.getLabel()+"').style.color='';\n");;
					
			objBuf.append("  }\n");
		}
		return objBuf;
	}
	
	private StringBuffer writeVerifyTextFunction() {
		StringBuffer objBuf = new StringBuffer();
		for (int i=0; i<mstrFormChild.size(); i++) {
			IValidateTag objItm = mstrFormChild.get(i);
			if (objItm instanceof ValidateText) {
				ValidateText txtItm = (ValidateText) objItm;
				if (txtItm.getRegExp()!=null) {
					String name = txtItm.getName().replace('[','_');
					name = name.replace(']', '_');
					StringBuffer msg = new StringBuffer(); 
					if (txtItm.getErrMessageKey()!=null && txtItm.getErrMessageKey().length()>0) {
						msg.append(getResourceMessage(txtItm.getErrMessageKey()));
					} else {
						msg.append("Validate "+txtItm.getName()+" is failed");
					}
					objBuf.append("function validate_"+mstrFormName+"_"+name+"() {\n");
					objBuf.append("  var obj = document.getElementById('"+txtItm.getName()+"');\n");
					objBuf.append("  if (obj.value!='' && !validateExp('"+txtItm.getRegExp()+"',obj.value)) {\n");

					if (mValSum!=null)
						objBuf.append("    document.getElementById('"+mValSum.getName()+"').innerHTML='"+msg+"';\n");
					
					if (objItm.getLabel()!=null)
						objBuf.append("    document.getElementById('"+objItm.getLabel()+"').style.color='red';\n");
					
					if (this.mbolAlert==true)
						objBuf.append("    alert('"+msg+"');\n");
					
					objBuf.append("    return false;\n");
					objBuf.append("  } else {\n");
					
					if (mValSum!=null)
						objBuf.append("    document.getElementById('"+mValSum.getName()+"').innerHTML='';\n");
					
					if (objItm.getLabel()!=null)
						objBuf.append("    document.getElementById('"+objItm.getLabel()+"').style.color='';\n");
					
					objBuf.append("    return true;\n");
					objBuf.append("  }\n");
					objBuf.append("}\n");
				}
			}
		}
		return objBuf;
	}
	
	private StringBuffer writeVerifyFileFunction() {
		StringBuffer objBuf = new StringBuffer();
		for (int i=0; i<mstrFormChild.size(); i++) {
			IValidateTag objItm = mstrFormChild.get(i);
			if (objItm instanceof ValidateFile) {
				ValidateFile filItm = (ValidateFile) objItm;
				if (filItm.getFileExt()!=null) {
					String name = filItm.getName().replace('[','_');
					name = name.replace(']', '_');
					StringBuffer msg = new StringBuffer();
					StringTokenizer fileExt = new StringTokenizer(filItm.getFileExt(),",");
					if (filItm.getErrMessageKey()!=null && filItm.getErrMessageKey().length()>0) {
						msg.append(getResourceMessage(filItm.getErrMessageKey()));
					} else {
						msg.append("Validate "+filItm.getName()+" file type should be "+filItm.getFileExt());
					}
					objBuf.append("function validate_"+mstrFormName+"_"+name+"() {\n");					
					objBuf.append("  var obj = document.getElementById('"+filItm.getName()+"');\n");
					objBuf.append("  var extlist = new Array(");
					while(fileExt.hasMoreTokens()) {
						objBuf.append("\""+fileExt.nextToken()+"\"");
						if (fileExt.hasMoreTokens()) {
							objBuf.append(",");
						}
					}
					
					objBuf.append(");\n");
					objBuf.append("  if (!checkfileType(extlist,obj.value)) {\n");

					if (mValSum!=null)
						objBuf.append("    document.getElementById('"+mValSum.getName()+"').innerHTML='"+msg+"';\n");
					
					if (objItm.getLabel()!=null)
						objBuf.append("    document.getElementById('"+objItm.getLabel()+"').style.color='red';\n");
					
					if (this.mbolAlert==true)
						objBuf.append("    alert('"+msg+"');\n");
					
					objBuf.append("    return false;\n");
					objBuf.append("  } else {\n");
					
					if (mValSum!=null)
						objBuf.append("    document.getElementById('"+mValSum.getName()+"').innerHTML='';\n");
					
					if (objItm.getLabel()!=null)
						objBuf.append("    document.getElementById('"+objItm.getLabel()+"').style.color='';\n");
					
					objBuf.append("    return true;\n");
					objBuf.append("  }\n");
					objBuf.append("}\n");
				}
			}
		}
		return objBuf;
	}
	
	private StringBuffer writeVerifyNumberFunction() {
		StringBuffer objBuf = new StringBuffer();
		for (int i=0; i<mstrFormChild.size(); i++) {
			IValidateTag objItm = mstrFormChild.get(i);
			if (objItm instanceof ValidateNumber) {
				ValidateNumber txtItm = (ValidateNumber) objItm;
				if (txtItm.getRegExp()!=null) {
					String name = txtItm.getName().replace('[','_');
					name = name.replace(']', '_');
					StringBuffer msg = new StringBuffer(); 
					if (txtItm.getErrMessageKey()!=null && txtItm.getErrMessageKey().length()>0) {
						msg.append(getResourceMessage(txtItm.getErrMessageKey()));
					} else {
						msg.append("Validate "+txtItm.getName()+" is failed");
					}
					objBuf.append("function validate_"+mstrFormName+"_"+name+"() {\n");					
					objBuf.append("  var obj = document.getElementById('"+txtItm.getName()+"');\n");
					objBuf.append("  if (!validateExp('"+txtItm.getRegExp()+"',obj.value)");
					if (txtItm.getMaxValue()>0) {
						objBuf.append(" || parseInt(obj.value)>"+txtItm.getMaxValue());
					}
					if (txtItm.getMinValue()<txtItm.getMaxValue()) {
						objBuf.append(" || parseInt(obj.value)<"+txtItm.getMinValue());
					}
					objBuf.append(") {\n");
					if (mValSum!=null)
						objBuf.append("    document.getElementById('"+mValSum.getName()+"').innerHTML='"+msg+"';\n");
					
					if (objItm.getLabel()!=null)
						objBuf.append("    document.getElementById('"+objItm.getLabel()+"').style.color='red';\n");
					
					if (this.mbolAlert==true)
						objBuf.append("    alert('"+msg+"');\n");
					
					objBuf.append("    obj.value=validate_"+mstrFormName+"_TempVar;\n");
					objBuf.append("    return false;\n");
					objBuf.append("  } else {\n");
					
					if (mValSum!=null)
						objBuf.append("    document.getElementById('"+mValSum.getName()+"').innerHTML='';\n");
					
					if (objItm.getLabel()!=null)
						objBuf.append("    document.getElementById('"+objItm.getLabel()+"').style.color='';\n");
					
					objBuf.append("    return true;\n");
					objBuf.append("  }\n");
					objBuf.append("}\n");
				}
			}
		}
		return objBuf;
	}
	
	private String getResourceMessage(String key) {
		if (resource!=null)
			return resource.getMessage(pageContext.getRequest().getLocale(), key);
		else return "";
	}

	public boolean getSubForm() {
		return subForm;
	}

	public void setSubForm(boolean subForm) {
		this.subForm = subForm;
	}	
}
