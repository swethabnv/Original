package com.dcs.dcswc.common;

import java.util.Date;
import java.util.Locale;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import java.text.SimpleDateFormat;


public class AValidateTag extends TagSupport implements IValidateTag {
	private static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	private static final long serialVersionUID = -7635077389329627941L;
	protected String mstrName = null;
	protected String mstrProperty = null;
	protected String mstrCssClass = null;
	protected String mstrStyle = null;
	protected String mstrLabel = null;
	protected String mstrOnChange = null;
	protected boolean mbolIsReadOnly = false;
	protected String mstrErrMessageKey = "";
	protected boolean mbolIsRequire = false;	
	protected int mintSize = -1;
	
	public String getLabel() {
		return mstrLabel;
	}
	
	public void setLabel(String mstrLabel) {
		this.mstrLabel = mstrLabel;
	}
	
	public boolean getIsRequire() {
		return mbolIsRequire;
	}
	
	public void setIsRequire(boolean mbolIsRequire) {
		this.mbolIsRequire = mbolIsRequire;
	}
	
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
	
	public String getProperty() {
		return mstrProperty;
	}
	
	public void setProperty(String mstrProperty) {
		this.mstrProperty = mstrProperty;
	}
	
	public int getSize() {
		return mintSize;
	}
	
	public void setSize(int mintSize) {
		this.mintSize = mintSize;
	}
	
	public void copyAttributesTo(IValidateTag pobjDest){
        pobjDest.setName(this.getName());
        pobjDest.setCssClass(this.getCssClass());
        pobjDest.setSize(this.getSize());
        pobjDest.setLabel(this.getLabel());
        pobjDest.setProperty(this.getProperty());
        pobjDest.setIsRequire(this.mbolIsRequire);
        pobjDest.setErrMessageKey(this.mstrErrMessageKey);
        pobjDest.setOnChange(this.mstrOnChange);
    }
	
	public String getPropertyValue() {
		try {
			if (mstrProperty!=null && !"".equals(mstrProperty)) {
				Object obj = PropertyUtils.getProperty(getValidateForm(this).formBean, mstrProperty);
				if (obj instanceof String) {
					return (String)obj;
				} if (obj instanceof Integer) {
					return ((Integer)obj).toString();
				} else if (obj instanceof Long) {
					return ((Long)obj).toString();
				} else if (obj instanceof Float) {
					return ((Float)obj).toString();
				} else if (obj instanceof Double) {
					return ((Integer)obj).toString();
				} else if (obj instanceof Boolean) {
					return ((Boolean)obj).toString();
				} else if (obj instanceof Date) {
					return defaultDateFormat.format((Date)obj);
				}
			}
			return null;
		} catch(Exception ex) {
			System.out.println("Error get property :"+mstrProperty);
			return null;
		}
	}
	
	public String[] getPropertyValues() {
		return getPropertyValues(mstrProperty);
	}
	
	public String[] getPropertyValues(String variable) {
		try {
			if (variable!=null && !"".equals(variable)) {
				ActionForm form =  (ActionForm) getValidateForm(this).formBean;
				Object[] objs = (Object[]) PropertyUtils.getProperty(form, variable);
				if (objs instanceof String[]) {
					return (String[])objs;
				} else if (objs instanceof Long[]) {
					String[] tmp = new String[objs.length];
					for(int i=0; i<objs.length; i++) {
						tmp[i]=((Long)objs[i]).toString();
					}
					return tmp;
				} else if (objs instanceof Integer[]) {
					String[] tmp = new String[objs.length];
					for(int i=0; i<objs.length; i++) {
						tmp[i]=((Integer)objs[i]).toString();
					}
					return tmp;
				}
			}
			return null;
		} catch(Exception ex) {
			System.out.println("Error get property :"+variable);
			return null;
		}
	}

	public String getStyle() {
		return mstrStyle;
	}

	public void setStyle(String mstrStyle) {
		this.mstrStyle = mstrStyle;
	}
	
	public ValidateForm getValidateForm(Tag tag) {
		Tag parent = tag.getParent();
		if (parent instanceof ValidateForm) {
			return (ValidateForm) parent;
		} else if (parent == null) {
			return null;
		} else {
			return this.getValidateForm(parent);
		}
	}

	public String getErrMessageKey() {
		return mstrErrMessageKey;
	}

	public void setErrMessageKey(String mstrErrMessageKey) {
		this.mstrErrMessageKey = mstrErrMessageKey;
	}

	public boolean getIsReadOnly() {
		return mbolIsReadOnly;
	}

	public void setIsReadOnly(boolean mbolIsReadOnly) {
		this.mbolIsReadOnly = mbolIsReadOnly;
	}

	public String getOnChange() {
		return mstrOnChange;
	}

	public void setOnChange(String mstrOnChange) {
		this.mstrOnChange = mstrOnChange;
	}
	
}
