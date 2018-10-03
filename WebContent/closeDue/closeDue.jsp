<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@page import="com.wsnweb.util.Utility"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.CloseDueForm"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.Plant"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

<title><%=Utility.get("WEB_TITLE")%></title>

<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
    CloseDueForm closeDueForm = (CloseDueForm)request.getAttribute("CloseDueForm");
	List plantYearList = (ArrayList) session.getAttribute("plantYearList");
	List closeDueList = (ArrayList) session.getAttribute ("closeDueList");
	List corpGroupList = (ArrayList) session.getAttribute("cooperativeGroupMasterList");
	String msg = "";
	long closeDueId=0;
	if(closeDueForm!=null){
	if(closeDueForm.getMsg() !=null && !"".equals(closeDueForm.getMsg())){
			msg = closeDueForm.getMsg();
		}
		closeDueId = closeDueForm.getCloseDueId();
	}

%>
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

 <script type="text/javascript">
function loadData(){
	$(document).ready(function() {
		$("select option:empty").remove();
	});
	document.closeDueForm.closeDueId.value = "<%=closeDueId%>";
    showErrorMessage(); 		
    loadMenu("M26");
}
function saveCloseDue(){
	if(document.closeDueForm.plantYear.value == 0){
		alert("กรุณาใส่ปีการผลิต !!");
		document.closeDueForm.plantYear.focus();
		return false;
	}
	if(document.closeDueForm.plantNo.value == "" || document.closeDueForm.plantNo.value == 0){
		alert("กรุณาใส่ครั้งที่ !!");
		document.closeDueForm.plantNo.focus();
		return false;
	}
	if(document.closeDueForm.farmerGroupId.value == 0){
		alert("กรุณาใส่สังกัดสหกรณ์ !!");
		document.closeDueForm.farmerGroupId.focus();
		return false;
	}
	document.closeDueForm.cmd.value="Save";
	document.closeDueForm.action= "<%=request.getContextPath()%>/CloseDue.do";
	document.forms["closeDueForm"].submit();
}
function closePage(){
   			document.closeDueListForm.plantYear.value = "";
   			document.closeDueListForm.cmd.value = "Search"; 
   			document.closeDueListForm.action="<%=request.getContextPath()%>/CloseDueList.do";
			document.forms["closeDueListForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
 	<%}%>
 <%}%>
}
function checkNumber(str)
{
    var RE = /^\d+$/;
    if(RE.test(str.value)){
    	$('#'+str.id).css('border-color', '');
    }else{
    	alert("กรุณาใส่ค่าที่เป็นตัวเลข");
      	document.getElementById(str.id).value="";
        $('#'+str.id).css('border-color', 'black'); 
		setTimeout(function() {
     		 document.getElementById(str.id).focus();
   		}, 0);
    }
}
</script>
 
</head>
<body onload="loadData();">
<dcs:validateForm formName="closeDueForm" formAction="CloseDue.do" formBean="CloseDueForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="closeDueId" />
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
	<div class="navigator">
		<div class="inside">
			<ul>
				<li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
				<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
				<li><a style="color:#333" href='<%=request.getContextPath()%>/CloseDueList.do'>การปิดยอด</a></li>
				<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
				<li>กรอกข้อมูล</li>
			</ul>
		</div>
	</div>
	
	<!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        	<div class="content-keyin">
                <div class="search-header">
	                <!-- <p>ข้อมูลการปิดยอด</p> -->
	                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลการปิดยอด</font></span>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 300px;padding-right:0px">ปีการผลิต :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:140px">
						    <select id="plantYear"  name="plantYear" style="width:135px;height:29px;" >
		                    		<option value="0">กรุณาเลือก</option>
		                    		<%if(plantYearList !=null && plantYearList.size()>0){
		                    			for(int i=0; i<plantYearList.size(); i++){
		                    				Plant plant = (Plant)plantYearList.get(i);
		                    		 %>
		                    		 	<option value="<%= plant.getPlantYear()%>" <%if(closeDueForm.getPlantYear()==plant.getPlantYear()){%>selected="selected"<%}%>><%= plant.getPlantYear()%></option>
		                    		 <%}} %>
		                    	</select>
                        </li>
                    	<li class="topic" style="width: 100px;padding-right:0px">ครั้งที่ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:140px">
						    <dcs:validateText name="plantNo" property="plantNo" maxlength="100" style="width:160px" onChange="checkNumber(this);" />
                        </li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 300px;padding-right:0px">สังกัดสหกรณ์ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:425px">
						    <dcs:validateDropDown name="farmerGroupId" dataSource="<%= corpGroupList %>" property="farmerGroupId" keyField="farmerGroupId" displayField="farmerGroupName" style="width:425px"/>
                        </li>
                    </ul>
                </div>

						<table align="center">
                             <tr><td colspan="2">&nbsp;</td></tr>
                             
                             <tr><td><a class="btn-save" onclick="saveCloseDue();" id="write"></a></td>  
                                 <td><a class="btn-cancel" onclick="closePage();"></a></td>                         
                             </tr>
                  		</table>
		    </div>  
        </div>
    </div> 
</div>


   <%@include file="/footer.jsp" %>
     


</dcs:validateForm>
<form name="closeDueListForm"  method="post" action="<%=request.getContextPath()%>/CloseDueList.do">
<input type="hidden" name="plantYear" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>
