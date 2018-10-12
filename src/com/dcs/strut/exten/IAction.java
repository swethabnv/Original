package com.dcs.strut.exten;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public interface IAction {
	public String getProgramID();
	public boolean checkUserLogon();
	public boolean checkAuthorize();
	public ActionForward doAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
