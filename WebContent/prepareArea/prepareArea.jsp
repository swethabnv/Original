<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.PrepareAreaListForm"%>
<%@page import="com.wsnweb.form.PrepareAreaForm"%>
<%@page import="com.wsndata.data.BreedType" %>
<%@page import="com.wsndata.data.BreedGroup" %>
<%@page import="com.wsndata.data.PrepareArea" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

<title><%=Utility.get("WEB_TITLE")%></title>

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
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	PrepareAreaForm prepareAreaForm = (PrepareAreaForm)request.getAttribute("PrepareAreaForm");
	String prepareAreaName = "", cmd = "", msg = "";
	long prepareAreaId = 0, pprepareAreaId = 0, breedTypeId = 0, breedGroupId = 0;
	if(prepareAreaForm !=null){
		if(prepareAreaForm.getMsg() !=null && !"".equals(prepareAreaForm.getMsg())){
			msg = prepareAreaForm.getMsg();
		}
		cmd = prepareAreaForm.getCmd();
		prepareAreaId = prepareAreaForm.getPrepareAreaId();
		prepareAreaName = prepareAreaForm.getPrepareAreaName()==null?"":prepareAreaForm.getPrepareAreaName();
		breedTypeId = prepareAreaForm.getBreedTypeId();
		breedGroupId = prepareAreaForm.getBreedGroupId();
		pprepareAreaId = prepareAreaForm.getPprepareAreaId();
	}
	List breedTypeList = (ArrayList) session.getAttribute("breedTypePlantList");
	List breedGroupList = (ArrayList) session.getAttribute("breedGroupPlantList");
	List prepareAreaList = (ArrayList) session.getAttribute("prepareAreaList");
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
 	 $("select option:empty").remove();
  }); 
function loadData(){
	loadMenu("M22");
    <%if (cmd !=null && !"".equals(cmd)) {
		if(prepareAreaId!=0){%>document.prepareAreaForm.prepareAreaId.value = "<%=prepareAreaId%>";<%}%>
		document.prepareAreaForm.prepareAreaName.value = "<%=prepareAreaName%>";
		document.prepareAreaForm.pprepareAreaId.value = "<%=pprepareAreaId%>";
	<%}%>
    showErrorMessage(); 		
}

	function savePrepareArea() {
 	var saveIs = true;
		//if(document.prepareAreaForm.breedTypeId.value == 0){
		//   alert("กรุณาเลือกพืชที่ปลูก !!");
		//   saveIs = false;
		//}else if(document.prepareAreaForm.breedGroupId.value == 0){
		//   alert("กรุณาเลือกพันธุ์ที่ปลูก !!");
		//   saveIs = false;
		//}else 
		if(document.prepareAreaForm.prepareAreaName.value == ""){
		   alert("กรุณาใส่ชื่อ !!");
		   document.prepareAreaForm.prepareAreaName.focus();
		   saveIs = false;
		}
		if(saveIs){
	    	document.prepareAreaForm.prepareAreaName.value = document.prepareAreaForm.prepareAreaName.value.trim();
	    	document.prepareAreaForm.cmd.value="Save";
	    	document.prepareAreaForm.action= "<%=request.getContextPath()%>/PrepareArea.do";
	    	document.forms["prepareAreaForm"].submit();
	    }
	}
	
	function getBreedGroupInfo(objBreedType, objBreedGroup)
	{
		var breedTypeDD = document.getElementById(objBreedType);
		var breedGroupDD = document.getElementById(objBreedGroup);
		
			breedTypeObjId = breedTypeDD.value;     
			breedTypeObjName = breedTypeDD.options[breedTypeDD.selectedIndex].text;

			if(breedTypeDD.options[breedTypeDD.selectedIndex].value == ''){
				breedGroupDD.options.length = 1;
				breedGroupDD.options[0].value="";
				breedGroupDD.options[0].text="กรุณาเลือก";
				breedGroupDD.options[0].selected = true;
			}else{
				var reqParameters = new Object();
				reqParameters.cmd = "GetBreedGroup";
				reqParameters.breedTypeId = breedTypeDD.options[breedTypeDD.selectedIndex].value;     
				new Ajax.Request("<%=request.getContextPath()%>/PrepareArea.do",
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
							breedGroupDD.options.length = json.length+1;
							breedGroupDD.options[0].value="";
							breedGroupDD.options[0].text="กรุณาเลือก";
							breedGroupDD.options[0].selected = true;
							var r = 0;
							for(var i = 0; i < json.length; i++){     
								r=r+1;                                          
								breedGroupDD.options[r].value =json[i].breedGroupId;
								breedGroupDD.options[r].text = json[i].breedGroupName;      

							}
						}else{
							alert("ไม่พบข้อมูลชนิดพันธุ์พืช");
							breedGroupDD.options.length = 1;
							breedGroupDD.options[0].value="";
							breedGroupDD.options[0].text="กรุณาเลือก";
							breedGroupDD.options[0].selected = true;
						}

					},
					onFailure: function() { 
					}
				});
			}
	}

function closePage(){
        	document.prepareAreaListForm.prepareAreaName.value = "";
   			document.prepareAreaListForm.cmd.value = "Search"; 
   			document.prepareAreaListForm.action="<%=request.getContextPath()%>/PrepareAreaList.do";
			document.forms["prepareAreaListForm"].submit();
}

function showErrorMessage(){
	<% if(!"".equals(msg)){ %>
		alert("<%=msg%>");
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
   		<% } %>
    <% } %>
}
</script>

</head>

<body onload="loadData();">
	<dcs:validateForm formName="prepareAreaForm" formAction="PrepareArea.do" formBean="PrepareAreaForm">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="msg" />
	<input type="hidden" name="prepareAreaId" />
	<div class="main-inside">

		<%@include file="/header.jsp" %>
		<div class="navigator">
			<div class="inside">
				<ul>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/PrepareAreaList.do'>การจัดการแปลงก่อนเตรียมดิน</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li>กรอกข้อมูล</li>
				</ul>
			</div>
		</div>

		<!-- content -->
		<div class="content">
			<div class="inside">

				<div class="content-keyin">
					<div class="search-header" >
						<!-- <p>ข้อมูลผู้ซื้อ</p> -->
						&nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลแปลงก่อนเตรียมดิน</font></span>
					</div>
					<div style="width:460px; margin:auto;">
						<div class="line-input-keyin">
							<ul>
								<li class="topic" style="width: 120px;padding-right:0px">ชื่อ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:230px">
									<dcs:validateText name="prepareAreaName" property="prepareAreaName" style="width:230px;" maxlength="200"/>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic" style="width: 120px;padding-right:0px">ภายใต้ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:240px">
									<select id="pprepareAreaId"  name="pprepareAreaId" style="width:238px;">
		                    		<option value="0">กรุณาเลือก</option>
		                    		<%if(prepareAreaList !=null && prepareAreaList.size()>0){
		                    			for(int b=0; b<prepareAreaList.size(); b++){
		                    				PrepareArea prepareArea = (PrepareArea)prepareAreaList.get(b);
		                    		 %>
		                    		 	<option value="<%= prepareArea.getPrepareAreaId()%>"><%= prepareArea.getPrepareAreaName()%></option>
		                    		 <%}} %>
									</select>
								</li>
							</ul>
						</div>
					</div>
           			<table align="center" style="margin-top:10px;">
			           <tr>
				           <td>
				           		  <a class="btn-save" onclick="savePrepareArea();" id="write"></a>
				           </td>	
				            <td>
				           		  <a class="btn-cancel" onclick="closePage();"></a>
				           </td>			           
			           </tr>
			         </table>
			   		<div class="clear"></div>
				</div>
			</div>
		</div>
		<!--footer -->
		<%@include file="/footer.jsp" %>
		</div>
		</dcs:validateForm>
		<form name="prepareAreaListForm"  method="post" action="<%=request.getContextPath()%>/PrepareAreaList.do">
			<input type="hidden" name="prepareAreaName" />
			<input type="hidden" name="cmd" />
		</form>
	</body>
</html>