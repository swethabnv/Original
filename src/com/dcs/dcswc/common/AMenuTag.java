package com.dcs.dcswc.common;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;
import com.dcs.strut.exten.IUserRight;

public class AMenuTag extends TagSupport {
	private static final long serialVersionUID = -7697474872344505189L;
	protected String mstrCssClass   = null;
	protected String mstrName       = null;
	protected List<IMenu> mlstDataSource = null;
	protected Map<String, IUserRight> mapUserRight = null;
	protected String mstrWidth      = "100%";	

	public String getCssClass() {
		return this.mstrCssClass;
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
		return this.mstrWidth;
	}
	
	public void setWidth(String mstrWidth) {
		this.mstrWidth = mstrWidth;
	}

	public List<IMenu> getDataSource() {
		return mlstDataSource;
	}

	public void setDataSource(List<IMenu> mlstDataSource) {
		this.mlstDataSource = mlstDataSource;
	}

	public Map<String, IUserRight> getUserRight() {
		return mapUserRight;
	}

	public void setUserRight(Map<String, IUserRight> mapUserRight) {
		this.mapUserRight = mapUserRight;
	}
}
	