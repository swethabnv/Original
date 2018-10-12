package com.dcs.strut.exten;

import java.util.Map;

public interface IUserRole {
	public static final String SESSION_KEY = "SessionUserRole";
	public String getRolename();
	public void setRolename(String rolename);
	public Map<String, IUserRight> getUserRightMap();
	public void setUserRightMap(Map<String, IUserRight> userRightMap);
}
