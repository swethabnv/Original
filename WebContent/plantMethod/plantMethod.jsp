<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>

<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.PlantMethodForm"%>
<%@page import="com.wsndata.data.BreedType" %>
<%@page import="com.wsndata.data.BreedGroup" %>
<%@page import="com.wsndata.data.PlantMethod" %>
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
	
	PlantMethodForm plantMethodForm = (PlantMethodForm)request.getAttribute("PlantMethodForm");
	String plantMethodName = "", cmd = "", msg = "";
	long plantMethodId = 0, breedTypeId = 0, breedGroupId = 0;
	if(plantMethodForm !=null){
		if(plantMethodForm.getMsg() !=null && !"".equals(plantMethodForm.getMsg())){
			msg = plantMethodForm.getMsg();
		}
		cmd = plantMethodForm.getCmd();
		plantMethodId = plantMethodForm.getPlantMethodId();
		plantMethodName = plantMethodForm.getPlantMethodName();
		breedTypeId = plantMethodForm.getBreedTypeId();
		breedGroupId = plantMethodForm.getBreedGroupId();
	}
	List breedTypeList = (ArrayList) session.getAttribute("breedTypePlantList");
	List breedGroupList = (ArrayList) session.getAttribute("breedGroupPlantList");
	List plantMethodList = (ArrayList) session.getAttribute("plantMethodList");
%>

<script type="text/javascript">
 // IE
  $(document).ready(function() 
  {
 	 $("select option:empty").remove();
  }); 
function loadData(){
    <%if (cmd !=null && !"".equals(cmd)) {%>
			document.plantMethodForm.plantMethodId.value = "<%=plantMethodId%>";
		<%if("Edit".equals(cmd)){%>		
			document.plantMethodForm.plantMethodName.value = "<%=plantMethodName%>";
			//document.plantMethodForm.breedTypeId.value = "<%=breedTypeId%>";
			//getBreedGroupInfo('breedTypeId', 'breedGroupId');
			//document.plantMethodForm.breedGroupId.value = "<%=breedGroupId%>";
	<%}}%>
    showErrorMessage();
	loadMenu("M23");		
}

	function savePlantMethod() {
 	var saveIs = true;
		//if(document.plantMethodForm.breedTypeId.value == 0){
		//   alert("กรุณาเลือกพืชที่ปลูก !!");
		//   saveIs = false;
		//}else if(document.plantMethodForm.breedGroupId.value == 0){
		//   alert("กรุณาเลือกพันธุ์ที่ปลูก !!");
		//   saveIs = false;
		//}else 
		if(document.plantMethodForm.plantMethodName.value == ""){
		   alert("กรุณาใส่ชื่อ !!");
		   document.plantMethodForm.plantMethodName.focus();
		   saveIs = false;
		}
		if(saveIs){
	    	document.plantMethodForm.plantMethodName.value = document.plantMethodForm.plantMethodName.value.trim();
	    	document.plantMethodForm.cmd.value="Save";
	    	document.plantMethodForm.action= "<%=request.getContextPath()%>/PlantMethod.do";
	    	document.forms["plantMethodForm"].submit();
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
				new Ajax.Request("<%=request.getContextPath()%>/PlantMethod.do",
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
        	document.plantMethodListForm.plantMethodName.value = "";
   			document.plantMethodListForm.cmd.value = "Search"; 
   			document.plantMethodListForm.action="<%=request.getContextPath()%>/PlantMethodList.do";
			document.forms["plantMethodListForm"].submit();
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
	<dcs:validateForm formName="plantMethodForm" formAction="PlantMethod.do" formBean="PlantMethodForm">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="msg" />
	<input type="hidden" name="plantMethodId" />
	<div class="main-inside">

		<%@include file="/header.jsp" %>
		<div class="navigator">
			<div class="inside">
				<ul>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/PlantMethodList.do'>การจัดการวิธีการปลูก</a></li>
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
						&nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลวิธีการปลูก</font></span>
					</div>
					<div style="width:370px; margin:auto;">
						<div class="line-input-keyin">
							<ul>
								<li class="topic" style="width: 60px;padding-right:0px">ชื่อ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:240px">
									<dcs:validateText name="plantMethodName" property="plantMethodName" style="width:230px;" maxlength="200"/>
								</li>
							</ul>
						</div>
					</div>
           			<table align="center" style="margin-top:10px;">
			           <tr>
				           <td>
				           		  <a class="btn-save" onclick="savePlantMethod();" id="write"></a>
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
		<form name="plantMethodListForm"  method="post" action="<%=request.getContextPath()%>/PlantMethodList.do">
			<input type="hidden" name="plantMethodName" />
			<input type="hidden" name="cmd" />
		</form>
	</body>
</html>