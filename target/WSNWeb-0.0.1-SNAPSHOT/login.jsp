<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.LoginForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=Utility.get("WEB_TITLE")%></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="icon" href="favicon.ico" />
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);

	List branchList = (ArrayList)session.getAttribute("branchList");
	LoginForm loginForm = (LoginForm)request.getAttribute("LoginForm"); 
	String msg = "";
	String userName="";
	String showCaptcha = "";
	
	if(loginForm !=null ){ 
    	userName = loginForm.getUserName()==null?"":loginForm.getUserName();
    	msg = loginForm.getMsg()==null?"":loginForm.getMsg();
    	showCaptcha = loginForm.getShowCapcha();
	}

%>


<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/hoverIntent.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/dcswc.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/prototype.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script> 
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/scripts.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/superfish.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script type="text/javascript" >
/*
document.onkeydown = function (event) 
{	
	if (!event) {
		event = window.event;
	}
	var keyCode = event.keyCode;
	if (keyCode == 8) { 
		if (navigator.userAgent.toLowerCase().indexOf("msie") == -1) {
			event.stopPropagation();
		} else {
		    //alert("prevented!!");
			event.returnValue = false;
		}
		return false;
	}
};*/

$(document).unbind('keydown').bind('keydown', function (event) {
    var doPrevent = false;
    if (event.keyCode === 8) {
        var d = event.srcElement || event.target;
        if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD' || d.type.toUpperCase() === 'FILE' || d.type.toUpperCase() === 'EMAIL' )) 
             || d.tagName.toUpperCase() === 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        }
        else {
            doPrevent = true;
        }
    }
    if (doPrevent) {
        event.preventDefault();
    }
});


// document.oncontextmenu = document.body.oncontextmenu = function() {return false;}
window.oncontextmenu = function () {
   return false;
}

function onLoad() {
	window.setTimeout(function () {document.getElementById('userName').focus();}, 0);
	<%if(!msg.equals("")){%>document.getElementById('password').value = "";
	<%if(showCaptcha.equals("1")){%>document.getElementById('verificationCode').value = "";<%}%>
	window.setTimeout(function () {document.getElementById('password').focus();}, 0);<%}%>
	window.opener.close();
}

function login()
{
	var uName = document.loginForm.userName.value;
	var pwd = document.loginForm.password.value;
	if(uName !=null && uName != ''){
		if(pwd != ""){
				document.loginForm.userName.value = uName;
				document.loginForm.password.value = pwd;
				document.loginForm.method = "Post";
				document.forms["loginForm"].submit();
	    }else{
		  alert("กรุณาใส่รหัสผ่าน");
		  window.setTimeout(function () {document.getElementById('password').focus();}, 0);
		}
	}
	else
	{
		alert("กรุณาใส่ชื่อผู้ใช้");
		window.setTimeout(function () {document.getElementById('userName').focus();}, 0);
	}
}



</script>
</head>

<body onload="onLoad();">
<dcs:validateForm formName="loginForm" formAction="Login.do" formBean="LoginForm">
<div class="main">
	<div class="container-login">
    	<div class="inside">
            <div class="logo"><img src="<%=request.getContextPath() %>/images/logo.png" width="370" /></div>
            <div class="box-login">
            	<div class="inside-login">
                	<div class="text-login"><img src="<%=request.getContextPath()%>/images/text-insert.png" width="167" height="49" /></div>
                    <div class="clear"></div>
                    <p><font style="font-size: 20px">กรุณากรอกชื่อผู้ใช้และรหัสผ่านเพื่อเข้าสู่ระบบ</font></p>
                    
                    <div class="clear"></div>
                    <dcs:validateText name="userName" property="userName" maxlength="20" isRequire="true" cssClass="input-login" />
					
                    <div class="clear"></div>
                    <dcs:validatePassword name="password" maxlength="16" isRequire="true" cssClass="input-login" />
				
					<%if("1".equals(showCaptcha)){ %>
						<div class="clear"><font style="font-size: 24px" color="white"><b>VERIFICATION CODE:</b><img src="<%=request.getContextPath()%>/Image.do" height="27" /></font></div>
					 	<div class="clear"></div>
					 	<dcs:validateText name="verificationCode" property="verificationCode" maxlength="16" isRequire="true" cssClass="input-login" />
					<%} %>
				
                    <div class="clear"></div>
                    <button class="btn-login" onclick="login();"></button>
                    
                     <div class="clear">
                     <p style="margin-top: 10px;"><font style="font-size: 19px;color: rgb(131, 0, 0);font-weight: bold"><%= msg %></font></p>
                     </div>
                </div>
            </div>
            <div class="footer-login">
            	<div class="copyright">&copy; 2014 สงวนลิขสิทธิ์ 2557 <%=Utility.get("WEB_TITLE")%></div>
                <div class="logo-footer"><img src="<%=request.getContextPath() %>/images/logo-footer.png" width="238" height="32" /></div>
            </div>
    	</div>
    </div>
</div>
<input type="hidden" name="cmd" value="Login" />
<input type="hidden" name="pwd" />
<input type="hidden" name="showCapcha" value="<%=showCaptcha %>" />
</dcs:validateForm>
</body>
</html>