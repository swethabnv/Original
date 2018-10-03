<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.wsnweb.form.UserForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.PrefixForm"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1">
<meta name="viewport" content="width=1280,user-scalable=yes">
<title><%=Utility.get("WEB_TITLE")%></title>

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

<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	PrefixForm prefixForm = (PrefixForm) request.getAttribute("PrefixForm");
	String cmd = "",msg = "",prefix = "",prevPrefix = "";
	if(prefixForm!=null){
		if(prefixForm.getCmd() !=null && !"".equals(prefixForm.getCmd())){
			cmd = prefixForm.getCmd();
		}
		if(prefixForm.getMsg() !=null && !"".equals(prefixForm.getMsg())){
			msg = prefixForm.getMsg();
		}
		if(prefixForm.getPrevPrefix() !=null && !"".equals(prefixForm.getPrevPrefix())){
			prevPrefix = prefixForm.getPrevPrefix();
		}
		prefix = prefixForm.getPrefix();
	}
%>
<script type="text/javascript">

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
      	     	//$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
 	 });
  }); 

function loadData(){
	document.prefixForm.cmd.value= "<%=cmd%>";
	document.prefixForm.msg.value= "<%=msg%>";
	<% if("Edit".equals(msg)){ %>
		//document.prefixForm.prefix.readOnly = true;
		document.prefixForm.prefix.value = "<%=prefix%>";
		document.prefixForm.prevPrefix.value = "<%=prefix%>";
	<%}%>
	<% if(!"".equals(prevPrefix)){ %>
		document.prefixForm.prevPrefix.value = "<%=prevPrefix%>";
	<%}%>
    showErrorMessage(); 		
    loadMenu("M09");
}
function savePrefix(){
	var saveIs = true;
	if(document.prefixForm.prefix.value == "" ){
		alert("กรุณาใส่คำย่อ ของคำนำหน้า!!");
		document.prefixForm.prefix.focus();
		saveIs = false;
	}else if(document.prefixForm.prefixFull.value == "" ){
		alert("กรุณาใส่คำเต็ม ของคำนำหน้า!!");
		document.prefixForm.prefixFull.focus();
		saveIs = false;
	}
	if(saveIs){
	   	document.prefixForm.prefixFull.value = document.prefixForm.prefixFull.value.trim();
	   	document.prefixForm.cmd.value="Save";
	    document.prefixForm.action= "<%=request.getContextPath()%>/Prefix.do";
	    document.forms["prefixForm"].submit();
	}

}
function closePage(){
   			document.prefixListForm.prefix.value = "";
   			document.prefixListForm.cmd.value = "Search"; 
   			document.prefixListForm.action="<%=request.getContextPath()%>/PrefixList.do";
			document.forms["prefixListForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg) && !"Edit".equals(msg)){ %>
   		alert("<%=msg%>");
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
 <%}}%>
}

function checkAbbr(str) {
    var num = 0;
    
    var patt = /^[A-Za-zก-์]([A-Za-zก-์\. ])+$/;
    var res = patt.test(str.value);
    
    if(!res) {
    	alert("กรุณาใส่คำนำหน้า (ย่อ) เป็นภาษาไทย หรือภาษาอังกฤษ");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    } else {
    	checkPrefix();
    }
}

function checkFull(str) {
    var num = 0;
    
    var patt = /^[A-Za-zก-์]([A-Za-zก-์ -])+$/;
    var res = patt.test(str.value);
    
    if(!res) {
    	alert("กรุณาใส่คำนำหน้า (เต็ม) เป็นภาษาไทย หรือภาษาอังกฤษ");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    }
}

function checkPrefix(){
	var reqParameters = new Object();
	reqParameters.cmd = "GetPrefix";
	document.prefixForm.prefix.value = document.prefixForm.prefix.value.trim();
	reqParameters.prefix = document.prefixForm.prefix.value;
	new Ajax.Request("<%=request.getContextPath()%>/Prefix.do",
	{	
		method: 'post',
		asynchronous: false,
		parameters: reqParameters,
		encoding: 'UTF-8',
		onSuccess: function(transport)
		{					
		    var json = transport.responseText.evalJSON(); 
			if(json !=null && json != "")
			{
			    alert("มีคำนำหน้า \"" + json[0].abbrPrefix + "\" อยู่แล้วในระบบ!!");
				document.prefixForm.prefix.value = json[0].abbrPrefix;
			}
		},onFailure: function() {	
			//alert('เกิดข้อผิดพลาด');
		}
	});
}
</script>
</head>
<body onload="loadData();">
<dcs:validateForm formName="prefixForm" formAction="Prefix.do" formBean="PrefixForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="msg" />
<input type="hidden" name="prevPrefix" />
<div class="main-inside">
<!-- insert header -->

		<%@include file="/header.jsp" %>
		<div class="navigator">
	    <div class="inside">
	 		<ul>
	            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
	            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	            <li><a style="color:#333" href='<%=request.getContextPath()%>/PrefixList.do'>การจัดการคำนำหน้า</a></li>
				<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	           <li>กรอกข้อมูล</li>
	     	</ul>
	 	</div>
    	</div>	
<!-- insert header -->
	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        	<div class="content-keyin">
                <div class="search-header">
               <!--  <p>ข้อมูลคำนำหน้า</p> -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลคำนำหน้า</font></span> 
                </div>
                <div class="clear"></div>
                <div class="line-input-keyin">
                    <ul>                    	
	                    	<li class="topic" style="width: 375px;padding-right:0px">คำนำหน้า (ย่อ) :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
	                    	<li class="boxinput" >
	                    		<dcs:validateText name="prefix" property="prefix" maxlength="20" style="width:190px;" onChange="checkAbbr(this);"/>
	                    	</li>
                    </ul>
                    <ul>	                    	
	                    	<li class="topic" style="width: 375px;padding-right:0px">คำนำหน้า (เต็ม) :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
	                    	<li class="boxinput" >
	                    		<dcs:validateText name="prefixFull" property="prefixFull" maxlength="200" style="width:190px;" onChange="checkFull(this);"/>
	                    	</li>
                    </ul>
                </div>
            			<div class="clear"></div>
			           <table align="center">
			           <tr><td colspan="2">&nbsp;</td></tr>
			           <tr>
				           <td>
				           		  <a class="btn-save" onclick="savePrefix();" id="write"></a>
				           </td>	
				            <td>
				           		  <a class="btn-cancel" onclick="closePage();"></a>
				           </td>			           
			           </tr>
			           </table>
			            </div>
			            <div class="clear"></div>
        </div>
    </div>
    
    
    
	</div>
	<!--footer -->
    <%@include file="/footer.jsp" %>
    <!-- end insert >> content --> 

<!-- end for header -->


</dcs:validateForm>

<form name="prefixListForm"  method="post" action="<%=request.getContextPath()%>/PrefixList.do">
<input type="hidden" name="prefix" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>