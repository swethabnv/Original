<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.CooperativeGroupListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="GENERATOR" content="Rational Application Developer" />
	<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
	<meta name="viewport" content="width=1280,user-scalable=yes" />

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
	
	CooperativeGroupListForm cooperativeGroupListForm = (CooperativeGroupListForm) request.getAttribute("CooperativeGroupListForm");
	List cooperativeGroupList = (ArrayList) session.getAttribute("cooperativeGroupMasterList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
	String msg = "", cooperativeType = "";
	long districtNo = 0, subDistrictNo = 0, provinceNo = 0;
	if(cooperativeGroupListForm!=null){
		if(!"".equals(cooperativeGroupListForm.getErrMessage())){
			msg = cooperativeGroupListForm.getErrMessage();
		}
		if(!"".equals(cooperativeGroupListForm.getCooperativeType()))
			cooperativeType = cooperativeGroupListForm.getCooperativeType();
		provinceNo = cooperativeGroupListForm.getProvinceNo();
		districtNo = cooperativeGroupListForm.getDistrictNo();
		subDistrictNo = cooperativeGroupListForm.getSubDistrictNo();
	}
	%>
	<script type="text/javascript">
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
 		$("select option:empty").remove();
 		}); 

		function searchFarmerGroup(){
			document.cooperativeGroupListForm.cmd.value="Search";
			document.cooperativeGroupListForm.action= "<%=request.getContextPath()%>/CooperativeGroupList.do";
			document.forms["cooperativeGroupListForm"].submit();
		}

		function goPage(cmd){
			if(cmd == 'Delete'){
				var checkList = document.getElementsByName('delfarmerGroupName');
				var size = checkList.length;
				var count=0;
				for(var i = 0 ; i < size ; i++){
					if(checkList[i].checked){
						count++;
					}
				}
				if (count > 0){
					if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
						document.cooperativeGroupListForm.target="";
						document.cooperativeGroupListForm.cmd.value="Delete";
						document.cooperativeGroupListForm.action="CooperativeGroupList.do";
						document.forms["cooperativeGroupListForm"].submit();
					}
				}else{
					alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
				}
			}else{
				document.cooperativeGroupListForm.cmd.value="Search";
				document.forms["cooperativeGroupListForm"].submit();
			}
		}

		function deleteEachFarmerGroup(FarmerGroupNameClicked){
			alert(FarmerGroupNameClicked+"!!");
			document.cooperativeGroupListForm.delfarmerGroupName.value = FarmerGroupNameClicked;
			document.cooperativeGroupListForm.cmd.value="Delete";
			document.cooperativeGroupListForm.action="<%=request.getContextPath()%>/CooperativeGroupList.do";
			document.forms["cooperativeGroupListForm"].submit();
		}

		function loadEdit(farmerGroupId,farmerGroupName)
		{	
			document.cooperativeGroupForm.farmerGroupId.value = farmerGroupId; 
			document.cooperativeGroupForm.farmerGroupName.value = farmerGroupName; 
			document.cooperativeGroupForm.cmd.value = "Edit";
			document.cooperativeGroupForm.action= "<%=request.getContextPath()%>/CooperativeGroup.do";
			document.forms["cooperativeGroupForm"].submit();

		}

		function addFarmerGroup(){

			document.cooperativeGroupForm.cmd.value="New";
			document.cooperativeGroupForm.farmerGroupId.value = "0";
			document.cooperativeGroupForm.action= "<%=request.getContextPath()%>/CooperativeGroup.do";
			document.forms["cooperativeGroupForm"].submit();

		}

		function addCooperativeGroupFarmer(farmerGroupId,farmerGroupName,target){
			document.cooperativeGroupFarmerForm.farmerGroupId.value = farmerGroupId;
			document.cooperativeGroupFarmerForm.farmerGroupName.value = farmerGroupName;
			document.cooperativeGroupFarmerForm.target.value = target;
			document.cooperativeGroupFarmerForm.action= "<%=request.getContextPath()%>/CooperativeGroupFarmer.do";
			document.forms["cooperativeGroupFarmerForm"].submit();

		}

		function getDistrictInfo(objPName, objDName, objSName)
		{
			var provinceDD = document.getElementById(objPName);
			var districtDD = document.getElementById(objDName);
			var subDistrictDD = document.getElementById(objSName);

			//ถ้ายังไม่เลือกจังหวัด อำเภอ,ตำบลเป็นกรุณาเลือก
			if(provinceDD.options[provinceDD.selectedIndex].value=='' || provinceDD.options[provinceDD.selectedIndex].value=='0'){
				districtDD.options.length = 1;
				districtDD.options[0].value="";
				districtDD.options[0].text="กรุณาเลือก";
				districtDD.options[0].selected = true;
				subDistrictDD.options.length = 1;
				subDistrictDD.options[0].value="";
				subDistrictDD.options[0].text="กรุณาเลือก";
				subDistrictDD.options[0].selected = true;
			} else {
				var reqParameters = new Object();
				reqParameters.cmd = "GetDistrict";
				reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
				new Ajax.Request("<%=request.getContextPath()%>/CooperativeGroupList.do",
				{
					method: 'post',
					asynchronous: false,
					parameters: reqParameters,
					encoding: 'UTF-8',
					onSuccess: function(transport)
					{
						var json = transport.responseText.evalJSON();
						if(json !=null && json != ""){
							districtDD.options.length = json.length+1;
							districtDD.options[0].value="";
							districtDD.options[0].text="กรุณาเลือก";
							districtDD.options[0].selected = true;

							var r = 0;
							for(var i = 0; i < json.length; i++){
								r=r+1;
								districtDD.options[r].value=json[i].districtNo;
								districtDD.options[r].text=json[i].districtThai;
							}
						}
					},
					onFailure: function() {
						//alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
					}
				});
			}
		}

		function getSubDistrictInfo(objPName, objDName, objSName)
		{
			var provinceDD = document.getElementById(objPName);
			var districtDD = document.getElementById(objDName);
			var subDistrictDD = document.getElementById(objSName);

			if(districtDD.options[districtDD.selectedIndex].value==''){ //ถ้ายังไม่เลือกจังหวัด อำเภอเป็นกรุณาเลือก
				subDistrictDD.options.length = 1;
				subDistrictDD.options[0].value="";
				subDistrictDD.options[0].text="กรุณาเลือก";
				subDistrictDD.options[0].selected = true;
			} else {
				var reqParameters = new Object();
				reqParameters.cmd = "GetSubDistrict";
				reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
				reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
				new Ajax.Request("<%=request.getContextPath()%>/CooperativeGroupList.do",
				{
					method: 'post',
					asynchronous: false,
					parameters: reqParameters,
					encoding: 'UTF-8',
					onSuccess: function(transport)
					{
						var json = transport.responseText.evalJSON();
						if(json !=null && json != ""){
							subDistrictDD.options.length = json.length+1;
							subDistrictDD.options[0].value="";
							subDistrictDD.options[0].text="กรุณาเลือก";
							subDistrictDD.options[0].selected = true;

							var r = 0;
							for(var i = 0; i < json.length; i++){
								r=r+1;
								subDistrictDD.options[r].value=json[i].subDistrictNo;
								subDistrictDD.options[r].text=json[i].subDistrictThai;
							}
						} else {
							subDistrictDD.options.length = 1;
							subDistrictDD.options[0].value="";
							subDistrictDD.options[0].text="กรุณาเลือก";
							subDistrictDD.options[0].selected = true;
						}
					},
					onFailure: function() {
						alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
					}
				});
			}
		}

		function loadData(){
			<%if(cooperativeType!=null && !"".equals(cooperativeType)) {%>
				document.cooperativeGroupListForm.cooperativeType.value = "<%=cooperativeType%>";
			<%} if(provinceNo>=0){%>
				document.cooperativeGroupListForm.provinceNo.value = "<%=provinceNo%>";
				getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo');
				<%if(districtNo>0){%>
					document.cooperativeGroupListForm.districtNo.value = "<%=districtNo%>";
					getSubDistrictInfo('provinceNo','districtNo','subDistrictNo');
					<%if(subDistrictNo>0){%>
						document.cooperativeGroupListForm.subDistrictNo.value = "<%=subDistrictNo%>";
			<%}}}%>
			showErrorMessage();
		}

		function showErrorMessage(){
			<% if(msg !=null && !"".equals(msg)){ %>
				alert("<%=msg%>"); 	 
			<%}%>
			loadMenu("M11");
		}
</script>
</head>
<body onload="loadData();">
	<dcs:validateForm formName="cooperativeGroupListForm" formAction="CooperativeGroupList.do" formBean="CooperativeGroupListForm">
	<input type="hidden" name="cmd" value="DirtyList" />
	<div class="main-inside">
		<!-- insert header -->

		<%@include file="/header.jsp" %>
		<div class="navigator">
			<div class="inside">
				<ul>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/CooperativeGroupList.do'>การจัดการสหกรณ์</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li>ค้นหาข้อมูล</li>
				</ul>
			</div>
		</div>	

		<!-- insert header -->
		<!-- start insert >> content -->
		<div class="content">
		<div class="inside">
				<div class="content-search">
					<div class="search-header" >
						<!-- <p>ค้นหาสหกรณ์</p> -->
						&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาสหกรณ์</font></span>
					</div>
					<div class="line-input">
						<ul>
							<li class="topic" style="width: 150px">ชื่อสหกรณ์ : </li>
							<li class="boxinput" style="width:730px">
								<dcs:validateText name="farmerGroupName" property="farmerGroupName" maxlength="100" style="width:738px"/>
							</li>
						</ul>
					</div>
					<div class="line-input">
						<ul>
							<li class="topic" style="width: 150px">จังหวัด : </li>
							<li class="boxinput" style="width:190px">
								<dcs:validateDropDown name="provinceNo" dataSource="<%= provinceList %>" property="provinceNo" keyField="provinceNo" displayField="thaiName" style="width:190px;" onChange="getDistrictInfo('provinceNo','districtNo','subDistrictNo')" />
							</li>
							<li class="topic" style="width: 60px">อำเภอ : </li>
							<li class="boxinput" style="width:190px">
								<dcs:validateDropDown name="districtNo" dataSource="<%= districtList %>" property="districtNo" keyField="districtNo" displayField="thaiName" style="width:190px;" onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo')" />
							</li>
							<li class="topic" style="width: 80px">ตำบล : </li>
							<li class="boxinput" style="width:190px">
								<dcs:validateDropDown name="subDistrictNo" dataSource="<%= subDistrictList %>" property="subDistrictNo" keyField="subDistrictNo"  displayField="thaiName" style="width:190px;" />      
							</li>
						</ul>
					</div>
					<div class="line-input">
						<ul class="btn-search-box">
							<li style="width:170px;padding-left: 748px;">
								<button class="btn-search" onclick="searchFarmerGroup();" style="padding-bottom: 9px;"></button>
							</li>
						</ul>
					</div>
				</div>

				<div class="btn-manage" style="width:95.5%;margin:auto;">
					<a class="btn-add" onclick="addFarmerGroup();" id="write"></a>
					<a class="btn-del" onclick="goPage('Delete');" id="del"></a>
				</div>
				
				<!--  for dcsGrid -->
				<table border="0" width="96%" align="center"><tr>
					<%   if(cooperativeGroupList!=null){ %>
					<td><dcs:grid dataSource="<%= cooperativeGroupList %>" name="farmerGroupList" pageSize="<%=cooperativeGroupListForm.getDisplayRow()%>" width="100%">
						<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
						imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
						<dcs:gridsorter/>                 
						<dcs:checkboxcolumn name="delfarmerGroupName" dataField="farmerGroupId" headerText="" width="30"  HAlign="left" />
						<!-- dcs:rownumcolumn headerText="ลำดับที่" width="80" HAlign="center"/> -->
						<dcs:textcolumn dataField="farmerGroupName" headerText="ชื่อสหกรณ์" style="cursor:pointer;padding-left:10px;" width="200" sortable="true" HAlign="left" cssClass="tel-sr" onClick="loadEdit('{farmerGroupId}','{farmerGroupName}');"/>
						<dcs:textcolumn dataField="provinceName" headerText="จังหวัด" width="100" sortable="true" cssClass="tel-sr" style="cursor:pointer;" HAlign="left" onClick="loadEdit('{farmerGroupId}','{farmerGroupName}');"/>
						<dcs:textcolumn dataField="districtName" headerText="อำเภอ" width="100" sortable="true" cssClass="tel-sr" style="cursor:pointer;" HAlign="left" onClick="loadEdit('{farmerGroupId}','{farmerGroupName}');"/>
						<dcs:textcolumn dataField="subDistrictName" headerText="ตำบล" width="100" sortable="true" cssClass="tel-sr" style="cursor:pointer;" HAlign="left" onClick="loadEdit('{farmerGroupId}','{farmerGroupName}');"/>
						<dcs:textcolumn dataField="countFarmer" headerText="จำนวนสมาชิก" width="80" sortable="true" cssClass="tel-sr" style="cursor:pointer;" HAlign="center" onClick="loadEdit('{farmerGroupId}','{farmerGroupName}');"/>
						<dcs:textcolumn dataField="strTarget" headerText="เป้าหมาย (ตัน)" style="cursor:pointer;padding-right:10px;text-align:right" width="70" sortable="true" HAlign="center" cssClass="tel-sr" onClick="loadEdit('{farmerGroupId}','{farmerGroupName}');"/>
						<dcs:imagecolumn dataField="linkImageFarmerGroupEdit" toolTip="จัดการสมาชิกสหกรณ์" style="cursor:pointer;" headerText=""  width="40" HAlign="center" cssClass="manage-sr" onClick="addCooperativeGroupFarmer('{farmerGroupId}','{farmerGroupName}','{target}');"/>
						<dcs:imagecolumn dataField="linkImageEdit" toolTip="แก้ไข" style="padding-top:2px;cursor:pointer;"  headerText=""  width="40"  HAlign="center" cssClass="manage-sr" onClick="loadEdit('{farmerGroupId}','{farmerGroupName}');"/>
					</dcs:grid></td>
				<% } %></tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countCooperativeGroup"))%> รายการ</div>
				<!-- end dcsGrid -->
				<div class="clear"></div>
			</div>
		</div>

		<!--footer -->
		<%@include file="/footer.jsp" %>
		<!-- end insert >> content --> 

		<!-- end for header -->
	</div>
	</dcs:validateForm>
	<form name="cooperativeGroupForm" method="post" action="<%=request.getContextPath()%>/CooperativeGroup.do">
		<input type="hidden" name="cmd" />
		<input type="hidden" name="farmerGroupName" />
		<input type="hidden" name="farmerGroupId" />
	</form>
	<form name="cooperativeGroupFarmerForm" method="post" action="<%=request.getContextPath()%>/CooperativeGroupFarmer.do">
		<input type="hidden" name="cmd" />
		<input type="hidden" name="farmerGroupId" />
		<input type="hidden" name="farmerGroupName" />
		<input type="hidden" name="target" />
	</form>
</body>
</html>