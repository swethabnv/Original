package com.wsnweb.image;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ImageVerificationAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/png");
		
		ImageVerification img = new ImageVerification(response.getOutputStream());
		String code  = img.getVerificationValue();
		HttpSession session = request.getSession();
		session.setAttribute("verification.code", code);
		response.flushBuffer();
		return null;
	}
}
