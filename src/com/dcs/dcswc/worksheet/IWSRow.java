package com.dcs.dcswc.worksheet;

import java.io.Serializable;

public interface IWSRow extends Serializable{
	public boolean isEditAble();
	public void setEditAble(boolean editAble);
	public String getCssClass();
	public void setCssClass(String cssClass);
	public String getStyle();
	public void setStyle(String style);
	public boolean getVisible();
	public void setVisible(boolean visible);
}
