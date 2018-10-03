<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.wsnweb.form.AccessControlListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="com.wsndata.data.Menu"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
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
	
	AccessControlListForm accContListForm = (AccessControlListForm) request.getAttribute("AccessControlListForm");
	List menuList = (ArrayList) session.getAttribute("menuList");
	
	String userName = "", firstName = "", lastName = "", msg ="";
	if(accContListForm!=null){
		userName = accContListForm.getUserName() == null?"": accContListForm.getUserName();
		firstName = accContListForm.getFirstName() == null?"": accContListForm.getFirstName();
		lastName = accContListForm.getLastName() == null?"": accContListForm.getLastName();
		msg =  accContListForm.getMsg() == null?"": accContListForm.getMsg();
	}
%>

<script language="JavaScript">


 var item = "";
 var menuId = "";
 var auth = "";
 var authorizeArray = new Object(); 
 
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
 
 function save(){
    	var cbx = document.querySelectorAll('input[type="checkbox"]');
		for (i = 0, l = cbx.length; i < l; i++) {
 			if(authorizeArray.hasOwnProperty(cbx[i].id)) {
		        if(cbx[i].checked){
              	 	auth += "1";
            	}else{
               		auth += "0";
            	}
            	authorizeArray[cbx[i].id] = auth;	
		    }else{
		    	if(cbx[i].checked){
              	 	auth += "1";
            	}else{
               		auth += "0";
            	}
		       authorizeArray[cbx[i].id] = auth;	
		    }
		    if(auth.length == 3){
		        item += "|" + cbx[i].id + "," +auth;
		    	auth = "";
		    }
		}
      	document.accessControlForm.authArray.value = item;
		document.accessControlForm.cmd.value = "Save"; 
   		document.accessControlForm.action = "<%=request.getContextPath()%>/AccessControlList.do";
		document.forms["accessControlForm"].submit();
 }

function closePage(){
        document.userListForm.userName.value = ""; 
		document.userListForm.email.value = ""; 
   		document.userListForm.firstName.value = "";
   		document.userListForm.lastName.value = "";
   		document.userListForm.branch.value = 0;
   		document.userListForm.cmd.value = "Search"; 
   		document.userListForm.action="<%=request.getContextPath()%>/UserList.do";
		document.forms["userListForm"].submit();
}

function checkAllauth(auth, checktoggle)
{
  	var cbx = document.querySelectorAll('input[type="checkbox"]');
 	for (var i = 0, l = cbx.length; i < l; i++) 
 	{
 		if(cbx[i].name.indexOf(auth)>-1){
      		cbx[i].checked = checktoggle;
      	}
  	}
}

function showErrorMessage(){
	
	loadMenu("M01");
	<% if(msg !=null && !"".equals(msg)){ %>
		alert("<%=msg%>"); 
   		<% if("การบันทึกสิทธิ์สมบูรณ์".equals(msg)){%> 
   			closePage();
 	<%}%>
 <%}%>
 	document.getElementById("M16").style.display = ""; 
}
</script>


</head>
<body onload="showErrorMessage();">
<form name="accessControlForm"  method="post" action="<%=request.getContextPath()%>/AccessControlList.do">
<div class="main-inside">
<input type="hidden" name="userName" value="<%= userName %>"/>
<input type="hidden" name="firstName" value="<%= firstName %>"/>
<input type="hidden" name="lastName" value="<%= lastName %>"/>
<input type="hidden" name="cmd" />
<input type="hidden" name="authArray" />

<%@include file="/header.jsp" %>
	<div class="navigator">
	    <div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>การจัดการผู้ใช้งาน</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		           <li>การจัดการสิทธิ์การใช้งาน</li>
		     	</ul>
		</div>
    </div>	
    <div class="content" style="height:auto;">
    	<div class="inside">
        	<div class="content-keyin">
                <div class="search-header">
                <!-- <p>การจัดการสิทธิ์การใช้งาน</p>  -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;"><font class="topic">การจัดการสิทธิ์การใช้งาน</font></span>
                </div>
                <div class="line-input-keyin">
                	<ul>
                    	<li class="topic" style="width:400px;text-align:left;">ชื่อผู้ใช้งาน : <%= firstName + " " + lastName %></li>
                    </ul>
                </div>
            <!--  -->
            <div class="topic-header-search">
                  <ul>
                  <li class="check-list-seach">
                  </li>
                    <li class="topic-sr" style="width:200px;">Function</li>
                    <li class="topic-sr">Read <input type="checkbox" id="readAll" name="readAll" onchange="checkAllauth('read_',this.checked)"/> </li>
                    <li class="topic-sr">Write <input type="checkbox" id="writeAll" name="writeAll" onchange="checkAllauth('write_',this.checked)"/></li>
                    <li class="topic-sr">Delete <input type="checkbox" id="deleteAll" name="deleteAll" onchange="checkAllauth('delete_',this.checked)"/></li>
                </ul>
            </div>
            <!-- Squared FOUR -->
            <%   if(menuList != null){ 
            		for(int i = 0; i< menuList.size(); i++){
            		Menu menu = (Menu)menuList.get(i);
            		String value = menu.getAuthorizeMap().get(menu.getMenuId());
            	
            		String menuText = value.split(",")[0];
            		String authMenu = value.split(",")[1];
            		boolean isRead = false;
            		boolean isWrite = false;
            		boolean isDel = false;
            		// read
            		if("1".equals(authMenu.substring(0, 1)))
						isRead = true;
					// write
					if("1".equals(authMenu.substring(1, 2)))
					    isWrite = true;
					// delete
					if("1".equals(authMenu.substring(2)))
					    isDel = true;
            %>
            
            <script language="JavaScript">
            function checkedRead(thisMenuId){
            	var menu = thisMenuId.split("_");
            	if(document.getElementsByName("write_"+menu[1])[0].checked || document.getElementsByName("delete_"+menu[1])[0].checked) {
					document.getElementById(thisMenuId).checked = true;
            	}
			}
			</script>
			
            <div class="detail-header-search">
                  <ul>
                  <li class="check-list-seach">
                  </li>
                    <li class="topic-sr" style="width:200px;"> <%= menu.getMtext() %> </li>
                    <li class="topic-sr"> 
                    <% if(isRead == true){ %>
                        <input type="checkbox" id="read_<%= menu.getMenuId() %>" name="read_<%= menu.getMenuId() %>" checked="checked" />
                        
                    <% }else{ %>
                     <input type="checkbox" id="read_<%= menu.getMenuId() %>" name="read_<%= menu.getMenuId() %>"  /> 
                    <% } %>
                    </li>
                    <li class="topic-sr">
                     <% if(isWrite == true){ %> 
                      <input type="checkbox" id="<%= menu.getMenuId() %>" name="write_<%= menu.getMenuId() %>" checked="checked" onclick="checkedRead('read_<%= menu.getMenuId() %>')" />
                       
                     <% } else {%>
                       <input type="checkbox" id="<%= menu.getMenuId() %>" name="write_<%= menu.getMenuId() %>" onclick="checkedRead('read_<%= menu.getMenuId() %>')" /> 
                     <% } %>
                    </li>
                    <li class="topic-sr"> 
                    <% if(isDel == true){ %> 
                       <input type="checkbox" id="<%= menu.getMenuId() %>" name="delete_<%= menu.getMenuId() %>" checked="checked" onclick="checkedRead('read_<%= menu.getMenuId() %>')" />
                    
                    <% } else { %>
                         <input type="checkbox" id="<%= menu.getMenuId() %>" name="delete_<%= menu.getMenuId() %>" onclick="checkedRead('read_<%= menu.getMenuId() %>')" /> 
                    <% } %>
                    </li>
                </ul>
            </div>
            <div class="clear"></div>
            <% } } %>
            
            <!--  -->
            
            <table align="center">
			           <tr><td colspan="2">&nbsp;</td></tr>
			           <tr> <td><a class="btn-save" href="#" onclick="save();" id="write"></a></td>	
				            <td><a class="btn-cancel" href="#" onclick="closePage();"></a></td>			           
			           </tr>
			        </table>
			        <br /><div style="clear:both;"></div>
          </div>
    </div>
	</div>
	<!--footer -->
    <%@include file="/footer.jsp" %>
</div>
</form>

<form name="userListForm"  method="post" action="<%=request.getContextPath()%>/UserList.do">
<input type="hidden" name="userName" />
<input type="hidden" name="firstName" />
<input type="hidden" name="lastName" />
<input type="hidden" name="email" />
<input type="hidden" name="branch" />
<input type="hidden" name="cmd" />
</form>

</body>
</html>