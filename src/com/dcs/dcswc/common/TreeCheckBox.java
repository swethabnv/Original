package com.dcs.dcswc.common;

import java.util.List;

public class TreeCheckBox implements ITree {
	private List child = null;
	private String value = null;
	private String text = null;
	private ITree parent = null;
	private int index = 0;

	public List getChild() {		
		return this.child;
	}

	public void setChild(List child) {
		this.child = child;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ITree getParent() {
		return parent;
	}

	public void setParent(ITree parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
