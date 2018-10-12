package com.dcs.dcswc.common;

import java.util.List;

public class ATree implements ITree {
	private List child;
	private String key;
	private String value;
	private ITree parent;
	
	public List getChild() {
		return child;
	}
	public void setChild(List child) {
		this.child = child;
	}
	public ITree getParent() {
		return parent;
	}
	public void setParent(ITree parent) {
		this.parent = parent;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
