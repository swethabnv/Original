package com.dcs.strut.exten;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

// for use this action need to define global forward name
//   defaultLogon
//   defaultAuthorizeError
//   defaultLogonError

public abstract class Action extends org.apache.struts.action.Action implements IAction{
	private static final String MESSAGE_ERR_NOSESSION = "";
	private static final String MESSAGE_ERR_NOAUTHORIZE = "";
	private static final String MESSAGE_ERR_CHECKAUTHORIZE = "";
	private static Logger log = Logger.getLogger(Action.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		HttpSession session = request.getSession();
		IForm iform = (IForm) form;
		try {
			IUserRole userRole = (IUserRole) session.getAttribute(IUserRole.SESSION_KEY);
			if (userRole==null && checkUserLogon()) {
				iform.setErrMessage(MESSAGE_ERR_NOSESSION);
				return mapping.findForward("defaultLogonError");
			}
			if (checkAuthorize()) {
				IUserRight userRight = userRole.getUserRightMap().get(getProgramID());
				if (userRight==null || userRight.isAuthorizeNone()) {
					iform.setErrMessage(MESSAGE_ERR_NOAUTHORIZE);
					return mapping.findForward("defaultAuthorizeError");
				} else {
					return doAction(mapping, form, request, response);
				}
			} else {
				return doAction(mapping, form, request, response);
			}
		} catch(Exception ex) {
			log.error(ex, ex);
			iform.setErrMessage(MESSAGE_ERR_CHECKAUTHORIZE);
			return doAction(mapping, form, request, response);
		}
	}
	
	protected ActionForward doError(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("defaultError");
	}
}
