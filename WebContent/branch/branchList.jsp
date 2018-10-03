<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BranchListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport"
	content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />
<title><%=Utility.get("WEB_TITLE")%></title>
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	BranchListForm branchListForm = (BranchListForm)request.getAttribute("BranchListForm");
	List branchList = (ArrayList) session.getAttribute("branchList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
	String msg = "";
	long districtNo = 0, subDistrictNo = 0, provinceNo = 0;
	if(branchListForm!=null){
		if(!"".equals(branchListForm.getErrMessage()))
		  	msg = branchListForm.getErrMessage();
		provinceNo = branchListForm.getProvinceNo();
		districtNo = branchListForm.getDistrictNo();
		subDistrictNo = branchListForm.getSubDistrictNo();
	}
%>
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
 	 $("select option:empty").remove();
  }); 
  
function searchBranch(){
	    document.branchListForm.cmd.value="Search";
	    document.branchListForm.action= "<%=request.getContextPath()%>/BranchList.do";
	    document.forms["branchListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delBranchCode');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.branchListForm.cmd.value="Delete";
				document.branchListForm.action="BranchList.do";
				document.forms["branchListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.branchListForm.cmd.value="Search";
		document.forms["branchListForm"].submit();
	}
}

function loadEdit(branchCode)
{	
	document.branchForm.branchCode.value = branchCode; 
	document.branchForm.cmd.value = "Edit";
	document.branchForm.action= "<%=request.getContextPath()%>/Branch.do";
	document.forms["branchForm"].submit();
 			 
}

function addBranch(){
        document.branchForm.cmd.value = "New";
        document.branchForm.branchCode.value = "0";
	    document.branchForm.action= "<%=request.getContextPath()%>/Branch.do";
	    document.forms["branchForm"].submit();
}

function getDistrictInfo(objPName, objDName, objSName)
{
	var provinceDD = document.getElementById(objPName);
	var districtDD = document.getElementById(objDName);
	var subDistrictDD = document.getElementById(objSName);
	
	subDistrictDD.options.length = 1;
	subDistrictDD.options[0].value="0";
	subDistrictDD.options[0].text="กรุณาเลือก";
	subDistrictDD.options[0].selected = true;
	//ถ้ายังไม่เลือกจังหวัด อำเภอเป็นกรุณาเลือก
	if(provinceDD.options[provinceDD.selectedIndex].value=='0'){
		districtDD.options.length = 1;
		districtDD.options[0].value="0";
		districtDD.options[0].text="กรุณาเลือก";
		districtDD.options[0].selected = true;
	} else {
		var reqParameters = new Object();
		reqParameters.cmd = "GetDistrict";
		reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
		new Ajax.Request("<%=request.getContextPath()%>/BranchList.do",
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
        			districtDD.options[0].value="0";
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
        		alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
        	}
        });
	}
}

function getSubDistrictInfo(objPName, objDName, objSName)
{
	var provinceDD = document.getElementById(objPName);
	var districtDD = document.getElementById(objDName);
	var subDistrictDD = document.getElementById(objSName);
				          
	if(districtDD.options[districtDD.selectedIndex].value=='0'){ //ถ้ายังไม่เลือกจังหวัด อำเภอเป็นกรุณาเลือก
		subDistrictDD.options.length = 1;
		subDistrictDD.options[0].value="0";
		subDistrictDD.options[0].text="กรุณาเลือก";
		subDistrictDD.options[0].selected = true;
	} else {
		var reqParameters = new Object();
		reqParameters.cmd = "GetSubDistrict";
		reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
		reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
		new Ajax.Request("<%=request.getContextPath()%>/BranchList.do",
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
        			subDistrictDD.options[0].value="0";
        			subDistrictDD.options[0].text="กรุณาเลือก";
        			subDistrictDD.options[0].selected = true;
        			
        			var r = 0;
        			for(var i = 0; i < json.length; i++){
        				r=r+1;
        				subDistrictDD.options[r].value=json[i].subDistrictNo;
        				subDistrictDD.options[r].text=json[i].subDistrictThai;
        			}
        		}
        	},
        	onFailure: function() {
        		alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
        	}
        });
	}
}

function showErrorMessage(){
	loadMenu("M08");
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>");
   	<%}%>
	<%if(provinceNo>=0){%>
		document.branchListForm.provinceNo.value = "<%=provinceNo%>";
		getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo');
	<%if(districtNo>0){%>
		document.branchListForm.districtNo.value = "<%=districtNo%>";
		getSubDistrictInfo('provinceNo','districtNo','subDistrictNo');
	<%if(subDistrictNo>0){%>
		document.branchListForm.subDistrictNo.value = "<%=subDistrictNo%>";
	<%}}}%>
}
</script>
</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="branchListForm" formAction="BranchList.do"
	formBean="BranchListForm">
	<input type="hidden" name="cmd" value="DirtyList" />
	<div class="main-inside"><!-- insert header --> <%@include
		file="/header.jsp"%>
	<div class="navigator">
	<div class="inside">
	<ul>
		<li><a style="color: #333"
			href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		<li><img
			src="<%=request.getContextPath()%>/images/arrow-navigator.png"
			width="4" height="8" /></li>
		<li><a style="color: #333"
			href='<%=request.getContextPath()%>/BranchList.do'>การจัดการสาขาธนาคาร</a></li>
		<li><img
			src="<%=request.getContextPath()%>/images/arrow-navigator.png"
			width="4" height="8" /></li>
		<li>ค้นหาข้อมูล</li>
	</ul>
	</div>
	</div>


	<!-- insert header --> <!-- start insert >> content -->
	<div class="content" style="height:auto;">
	<div class="inside">

	<div class="content-search">
	<div class="search-header"><!-- <p>ค้นหาสาขา</p> --> &nbsp;<span
		style="vertical-align: -7px; color: white;"><font
		class="topic">ค้นหาสาขา</font></span></div>
	<div class="line-input">
	<ul>
		<li class="topic" style="width: 150px">ชื่อสาขา :</li>
		<li class="boxinput"><dcs:validateText name="branchName" property="branchName" maxlength="200" style="width:718px;" /></li>
	</ul>
	</div>
	<div class="line-input">
	<ul>
		<li class="topic" style="width: 150px">จังหวัด :</li>
		<li class="boxinput" style="width: 190px">
			<dcs:validateDropDown name="provinceNo" dataSource="<%= provinceList %>" property="provinceNo" keyField="provinceNo" displayField="thaiName" style="width:190px;" onChange="getDistrictInfo('provinceNo','districtNo','subDistrictNo')" />
		</li>
		<li class="topic" style="width: 60px">อำเภอ :</li>
		<li class="boxinput" style="width: 190px">
			<dcs:validateDropDown name="districtNo" dataSource="<%= districtList %>" property="districtNo" keyField="districtNo" displayField="thaiName" style="width:190px;" onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo')" />
		</li>
		<li class="topic" style="width: 60px">ตำบล :</li>
		<li class="boxinput" style="width: 190px">
			<dcs:validateDropDown name="subDistrictNo" dataSource="<%= subDistrictList %>" property="subDistrictNo" keyField="subDistrictNo" displayField="thaiName" style="width:190px;" />
		</li>
	</ul>
	</div>
	<div class="line-input">
	<ul class="btn-search-box">
		<li class="boxinput" style="width: 170px; padding-left: 727px;">
		<button class="btn-search" onclick="searchBranch();"></button>
		</li>
	</ul>
	</div>
	</div>

	<div class="btn-manage" style="margin-left: 0px;">
		<a class="btn-add" onclick="addBranch();" id="write"></a>
		<a class="btn-del" onclick="goPage('Delete');" id="del"></a>
	</div>

	<!--  for dcsGrid -->
	<table border="0" width="96%">
		<tr>
			<% if(branchList!=null){ %>
			<td><dcs:grid dataSource="<%= branchList %>" name="branchList" pageSize="<%=branchListForm.getDisplayRow()%>" width="960px">
				<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif" />
				<dcs:gridsorter />
				<dcs:checkboxcolumn name="delBranchCode" dataField="branchCode" headerText="" width="20" HAlign="left" />
				<dcs:textcolumn dataField="branchName" headerText="ชื่อสาขา" width="110" sortable="true" HAlign="left" style="cursor:pointer;" onClick="loadEdit('{branchCode}');" />
				<dcs:textcolumn dataField="provinceName" headerText="จังหวัด" width="60" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer;" onClick="loadEdit('{branchCode}');" />
				<dcs:textcolumn dataField="districtName" headerText="อำเภอ" width="60" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer;" onClick="loadEdit('{branchCode}');" />
				<dcs:textcolumn dataField="subDistrictName" headerText="ตำบล" width="55" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer;" onClick="loadEdit('{branchCode}');" />
				<dcs:textcolumn dataField="tel" headerText="เบอร์โทร" width="50" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer;" onClick="loadEdit('{branchCode}');" />
				<dcs:imagecolumn dataField="linkImageEdit" headerText="" toolTip="แก้ไข" width="20" HAlign="center" cssClass="manage-sr" style="cursor:pointer;" onClick="loadEdit('{branchCode}');" />
			</dcs:grid></td>
			<% } %>
		</tr>
	</table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countBranch"))%> รายการ</div>
	<!-- end dcsGrid -->
	<br />


	<div class="clear"></div>


	</div>
	</div>

	<!--footer --> <%@include file="/footer.jsp"%>
	<!-- end insert >> content --> <!-- end for header --></div>

</dcs:validateForm>
<form name="branchForm" method="post" action="<%=request.getContextPath()%>/Branch.do">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="branchCode" />
</form>
</body>
</html>