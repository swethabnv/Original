<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.UserForm"%>
<%@page import="com.wsnweb.form.CooperativeGroupAddForm"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1">
<meta name="viewport" content="width=1280,user-scalable=yes">

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/GridStyle.css" />

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
	
	CooperativeGroupAddForm cooperativeGroupFarmerForm = (CooperativeGroupAddForm) request.getAttribute("CooperativeGroupAddForm");
	List farmerGroupList = (ArrayList) session.getAttribute("farmerGroupList");
	List farmerList = (ArrayList) session.getAttribute("farmerList");
	String farmerGroupName= "";
	String msg = "",cmd = "",idCard = "",fname="",lname="";
	long tmptarget = 0,farmerGroupId =0;
	if(cooperativeGroupFarmerForm!=null){
		farmerGroupId = cooperativeGroupFarmerForm.getFarmerGroupId();
		farmerGroupName = cooperativeGroupFarmerForm.getFarmerGroupName();
		tmptarget = cooperativeGroupFarmerForm.getTmptarget();
		msg = cooperativeGroupFarmerForm.getMsg()==null?"":cooperativeGroupFarmerForm.getMsg();
		cmd = cooperativeGroupFarmerForm.getCmd();
		idCard = cooperativeGroupFarmerForm.getIdCard();
		fname = cooperativeGroupFarmerForm.getFirstName();
		lname = cooperativeGroupFarmerForm.getLastName();
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
      	     	$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
 	 });
  }); 

function loadData(){
	document.cooperativeGroupAddForm.farmerGroupId.value= "<%=farmerGroupId%>";
	document.cooperativeGroupAddForm.farmerGroupName.value= "<%=farmerGroupName%>";
    <% if(cmd !=null && "Search".equals(cmd)){ %>
         document.cooperativeGroupAddForm.idCard.value = "<%=idCard%>";
         document.cooperativeGroupAddForm.firstName.value = "<%=fname%>";
   		 document.cooperativeGroupAddForm.lastName.value = "<%=lname%>";
   		 
 	<%}%>
 	showErrorMessage();
 	loadMenu("M11");
}
function searchFarmer(){
	    document.cooperativeGroupAddForm.cmd.value = "Search";
	    document.cooperativeGroupAddForm.action= "<%=request.getContextPath()%>/CooperativeGroupAdd.do";
	    //document.forms["farmerGroupFarmerForm"].submit();
	   document.forms["cooperativeGroupAddForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		alert("<%=msg%>");
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
   		<% } %>
 	<%}%>
}
function goPage(cmd){
	if(cmd == 'Add'){
		 checkList = document.getElementsByName('delFarmer');
		 size = checkList.length;
		 count=0;
		// var str = [] ;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			//	str.push(checkList[i].value);
			}
		}
		if (count > 0){
			if (confirm("ต้องการเพิ่มเกษตรกรที่เลือกเข้ากลุ่มใช่หรือไม่ ?")) {
				document.cooperativeGroupAddForm.cmd.value = "Add";
				document.cooperativeGroupAddForm.farmerGroupId.value = "<%=farmerGroupId%>";
				document.cooperativeGroupAddForm.action="<%=request.getContextPath()%>/CooperativeGroupAdd.do";
				document.forms["cooperativeGroupAddForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการเพิ่ม');
		}
	}else{
		document.cooperativeGroupAddForm.cmd.value="Search";
		document.forms["cooperativeGroupAddForm"].submit();
	}
}
function closePage(){
   	window.close();
}

</script>
</head>
<body onload="loadData();">
<dcs:validateForm formName="cooperativeGroupAddForm" formAction="CooperativeGroupAdd.do" formBean="CooperativeGroupAddForm">
    <input type="hidden" name="cmd" value="DirtyList" />
    <input type="hidden" name="farmerGroupId"/>
	<input type="hidden" name="farmerGroupName"/>
<div class="main-inside" style="padding:15px;text-align: center;">
	<table id = "farmerTable" >
	    <tr>
	    	<td colspan="7" style="text-align: left;padding-left: 25px">
				<font style="font-weight:bold;">ชื่อสหกรณ์:</font> <%=farmerGroupName %>
			</td>
		</tr>
		<tr>
	    	<td style="width:160px; text-align: right;">
				<font>เลขที่บัตรประชาชน:</font>
			</td>
			<td style="width: 120px">
				<dcs:validateText name="idCard" property="idCard" style="width:120px;" maxlength="13"/>
			</td>
			<td style="width:40px; text-align: right;">
				<font>ชื่อ:</font>
			</td>
			<td style="width: 120px">
				<dcs:validateText name="firstName" property="firstName" style="width:120px;" maxlength="30"/>
			</td>
			<td style="width:60px; text-align: right;">
				<font>นามสกุล:</font>
			</td>
			<td style="width: 120px">
				<dcs:validateText name="lastName" property="lastName" style="width:120px;" maxlength="30"/>
			</td>
			<td style="width:170px; text-align: center">
				<button class="btn-search" onclick="searchFarmer();" style="padding-bottom: 9px;"></button>
			</td>
		</tr>
	</table>
	<br />
	<!-------------------------------รายชื่อ--------------------------------------------------->
	<div style="padding-left: 0px; margin-left:0px;">
            	<table border="0" width="96%" style="margin:auto">
            	<tr>
            	<%   if(farmerList!=null){ %>
            	<td><dcs:grid dataSource="<%= farmerList %>" name="farmerList" pageSize="<%=cooperativeGroupFarmerForm.getDisplayRow()%>" width="100%">
                 	<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                	<dcs:gridsorter/>                 
                 	<dcs:checkboxcolumn name="delFarmer" dataField="idCard" headerText="" width="150"  HAlign="left"/>
                 	<dcs:textcolumn dataField="idCard" style="cursor:pointer;" headerText="เลขที่บัตรประชาชน" width="250" sortable="true" HAlign="left" cssClass="address-sr" />
                 	<dcs:textcolumn dataField="firstName" style="cursor:pointer;" headerText="ชื่อ" width="200" sortable="true" HAlign="left" cssClass="address-sr" />
                 	<dcs:textcolumn dataField="lastName" style="cursor:pointer;" headerText="นามสกุล" width="200" sortable="true" HAlign="left" cssClass="address-sr" />
                 	</dcs:grid>
            	</td>
            	<% } %>
            	</tr>
            	</table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countFarmer"))%> รายการ</div>
            <!-- end dcsGrid -->
	<br />
	</div>
	<!--  xxx -->
	<table align="center"><tr><td>
			 <a style="background:url(<%=request.getContextPath()%>/images/btn-ok.png); no-repeat; width:113px; height:28px; display:block; float:left; margin-right:5px;" href="#"  onclick="goPage('Add');" id=""></a>
             <a style="background:url(<%=request.getContextPath()%>/images/btn-cancel.png) no-repeat; width:113px; height:28px; display:block; float:left;" href="#" onclick="closePage();" id=""></a>
	</td></tr></table>
	<br />
</div>        
</dcs:validateForm>
</body>
</html>