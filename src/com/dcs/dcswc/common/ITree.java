package com.dcs.dcswc.common;

import java.util.List;

public interface ITree {
	public List getChild();
	public void setChild(List child);
	public ITree getParent();
	public void setParent(ITree parent);
}
