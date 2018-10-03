<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.UserForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css" />

<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/hoverIntent.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/menu.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/dcswc.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/prototype.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script> 
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/scripts.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/superfish.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>

<title><%=Utility.get("WEB_TITLE")%></title>
<%
    response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
    UserForm userForm = (UserForm)request.getAttribute("UserForm");
	String msg = "";
	if(userForm !=null){
		msg = userForm.getMsg()==null?"":userForm.getMsg();
	}
 %>
 
<script language="JavaScript">

// IE
  $(document).ready(function() 
  {
  		$('input[type=text],input[type=checkbox],select:first').focus(); //$('input:text:first').focus();
 		$('input[type=text],input[type=checkbox],select').bind("keydown", function(e) { //input:text
    	var n = $("input[type=text],input[type=checkbox],select").length;
    	if (e.which == 13) { 
     		 e.preventDefault(); //Skip default behavior of the enter key
     	 	var nextIndex = $('input[type=text],input[type=checkbox],select').index(this) + 1; //input:text
     	 	if(nextIndex < n){
      	     	$('input[type=text],input[type=checkbox],select')[nextIndex].focus(); // input:text
      	     	$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
 	 });
  }); 

function showErrorMessage()
{
	<% if(msg !=null && !"".equals(msg)){ %>
		alert("<%= msg %>");  
		<% if(Utility.get("PASSWORD_SUCCESS").equals(msg)) { %>
			logOff();
		<% } %>
    <% } %>
    loadMenu("M02");
}

function logOff(){
		document.changePasswordForm.cmd.value = "LogOff";
		document.changePasswordForm.method = "Post";
		document.changePasswordForm.action="<%=request.getContextPath()%>/User.do";
		document.forms["changePasswordForm"].submit();
}

function changePassword(){	
    var currentPassword = document.changePasswordForm.currentPassword.value;
	var newPassword = document.changePasswordForm.password.value;
	var confirmPassword = document.changePasswordForm.confirmPassword.value;
	
	if(validatePassword(currentPassword, newPassword, confirmPassword)){
		document.changePasswordForm.cmd.value = "ChangePassword";
		document.changePasswordForm.method = "Post";
		document.changePasswordForm.action="<%=request.getContextPath()%>/User.do";
		document.forms["changePasswordForm"].submit();
	}
}


function validatePassword(currentPassword, newPassword, confirmPassword) { 
	if(currentPassword == null || currentPassword ==""){
	    alert("กรุณากรอกรหัสผ่านปัจจุบันของท่าน");
		return false; 
	}
	if(newPassword == null || newPassword ==""){
	     alert("กรุณากรอกรหัสผ่านใหม่"); 
		 return false;
	}
	if(confirmPassword == null || confirmPassword ==""){
	  	alert("กรุณายืนยันรหัสผ่าน");
		return false;
	}
	if(newPassword != confirmPassword){
	  	 alert("รหัสผ่านใหม่กับรหัสผ่านที่ท่านยืนยันไม่ตรงกัน"); 
		 return false; 
	} 
	if(checkThai(newPassword) && checkSpecialChar(newPassword) && checkUpperCaseChar(newPassword) && checkLowerCaseChar(newPassword) && checkNumeric(newPassword) && checkLength(newPassword)){
		return true;
	}else{
		return false;
	}
} 

function checkSpecialChar(newPassword){
	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~_";
	var foundSpecialChar=0;
    for (var i = 0; i < newPassword.length; i++){
		if (iChars.indexOf(newPassword.charAt(i)) != -1) {
			foundSpecialChar = foundSpecialChar+1;
		}
	}
	if(foundSpecialChar>0){
		return true;
	}else{
		 alert("รหัสผ่านต้องประกอบด้วยอักขระพิเศษอย่างน้อย 1 ตัว");
		 return false;
	}
}

function checkUpperCaseChar(newPassword){
	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~_";
	var upperChars = "";
	var foundChar=0;

	for (var i = 0; i < newPassword.length; i++){
	 	if (iChars.indexOf(newPassword.charAt(i)) == -1) {
	 		if(!regIsNumber(newPassword.charAt(i))){
	 			if (newPassword.charAt(i) == newPassword.charAt(i).toUpperCase()) {
					foundChar = foundChar+1;
				}			
	 		}	 	
		}
	}
	if(foundChar>0){
		return true;
	}else{
		alert("รหัสผ่านต้องประกอบด้วยตัวอักษรขนาดใหญ่อย่างน้อย 1 ตัว");
		return false;
	}
}

function checkLowerCaseChar(newPassword){
	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~_";
	var upperChars = "";
	var foundChar=0;

	for (var i = 0; i < newPassword.length; i++){
	 	if (iChars.indexOf(newPassword.charAt(i)) == -1) {
	 		if(!regIsNumber(newPassword.charAt(i))){
	 			if (newPassword.charAt(i) == newPassword.charAt(i).toLowerCase()) {
					foundChar = foundChar+1;
				}			
	 		}	 	
		}
	}
	if(foundChar>0){
		return true;
	}else{
	  	 alert("รหัสผ่านต้องประกอบด้วยตัวอักษรขนาดเล็กอย่างน้อย 1 ตัว");
		 return false;
	}
}

function checkNumeric(newPassword){
	var upperChars = "";
	var foundNumber=0;

	for (var i = 0; i < newPassword.length; i++){
 		if(regIsNumber(newPassword.charAt(i))){			
			foundNumber = foundNumber+1;			
 		}	 	
	}
	if(foundNumber>0){
		return true;
	}else{
		 alert("รหัสผ่านต้องประกอบด้วยตัวเลขอย่างน้อย 1 ตัว"); 
		 return false; 
	}
}

function checkThai(fData) {
 	var reg = new RegExp("[ก-๙]+");  
 	if(!reg.test(fData)) {
		return true;
 	} else {
		alert("รหัสผ่านต้องประกอบด้วยตัวอักษรภาษาอังกฤษ ตัวเลข และอักขระพิเศษ");
		return false;
 	}
}

function regIsNumber(fData) {
 	var reg = new RegExp("^[0-9]$");  
 	return (reg.test(fData));  
}  

function checkLength(newPassword){

	if(newPassword.length > 7 && newPassword.length < 17 ){
		return true;
	}else{
		 alert("รหัสผ่านต้องมีอักขระ 8-16 ตัวอักษร"); 
		 return false;
	}
}
</script>

</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="changePasswordForm" formAction="User.do" formBean="UserForm" formMethod="Post">
<input type="hidden" name="cmd" />
<div class="main-inside" >
<!-- insert header -->
<%@include file="/header.jsp" %>
<!-- insert header -->
		<div class="navigator">
	    	<div class="inside">
	            <ul>
	                <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
	                <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	                <li style="color:#333" >เปลี่ยนรหัสผ่าน</li>
	            </ul>
	        </div>
    	</div>	

	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        	<div class="content-keyin">
                <div class="search-header">
                <!-- <p>เปลี่ยนรหัสผ่าน : </p> -->
                 &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >เปลี่ยนรหัสผ่าน : </font></span>
                
                </div>
                <div class="line-input-keyin">
                  <ul>
                    	<li class="topic" style="width: 375px" >รหัสผ่านปัจจุบัน : </li>
                        <li class="boxinput">
							<dcs:validatePassword name="currentPassword" isRequire="true" style="width:200px" maxlength="16" />
                        </li>
                  </ul>
                </div>
                <div class="line-input-keyin">
                  <ul>
                    	<li class="topic" style="width: 375px" >รหัสผ่านใหม่ :<br/><br/><br/></li>
                        <li class="boxinput">
						    <dcs:validatePassword name="password" isRequire="true" style="width:200px" maxlength="16" /><br/>      
                        	<font color="red" size="4">**รหัสผ่านต้องมี 8-16 ตัวอักษร ประกอบด้วยตัวพิมพ์ใหญ่ <br>ตัวพิมพ์เล็ก ตัวเลข และอักขระพิเศษอย่างน้อยอย่างละ 1 ตัวขึ้นไป</font> 
                        </li>
                  </ul>
                </div>
                <div class="line-input-keyin">
                  <ul>
                    	<li class="topic" style="width: 375px" >ยืนยันรหัสผ่านใหม่ : </li>
                        <li class="boxinput">
						   <dcs:validatePassword name="confirmPassword" isRequire="true" style="width:200px" maxlength="16" />
                        </li>
                  </ul>
                </div>
             	<table align="center">
	          		 <tr><td colspan = "2">&nbsp;</td></tr>
	           		 
	           		 <tr><td colspan = "2"><a class="btn-save" href="#" onclick="changePassword();" id="write"></a></td>	
		            				           
	           	     </tr>
	            </table>
        </div>
    </div>
	</div>
	<!--end content -->
	<!--footer -->
    <%@include file="/footer.jsp" %>
    <!-- end insert >> content --> 


</div>

</dcs:validateForm>


</body>
</html>
