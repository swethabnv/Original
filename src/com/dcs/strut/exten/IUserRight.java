package com.dcs.strut.exten;

public interface IUserRight {
	public static final String AUTHORIZE_READ = "R";
	public static final String AUTHORIZE_WRITE = "W";
	public static final String AUTHORIZE_NONE = "N";
	
	public abstract String getAuthorize();
	public abstract void setAuthorize(String authorize);
	public abstract String getRightName();
	public abstract void setRightName(String rightName);
	
	public boolean isAuthorizeRead();
	public boolean isAuthorizeWrite();
	public boolean isAuthorizeNone();
}
