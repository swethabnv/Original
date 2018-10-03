<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BuyerListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.Province"%>
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

	BuyerListForm  buyerListForm = (BuyerListForm) request.getAttribute("BuyerListForm");
	List buyerList = (ArrayList) session.getAttribute ("buyerList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
	String msg = "";
	long districtNo = 0, subDistrictNo = 0, provinceNo = 0;
	if(buyerListForm!=null){
		if(!"".equals(buyerListForm.getErrMessage())){
		  	msg = buyerListForm.getErrMessage();
		}
		if(!"".equals(buyerListForm.getProvinceNo()))
			provinceNo = Long.parseLong(buyerListForm.getProvinceNo()==null?"0":buyerListForm.getProvinceNo());
		if(!"".equals(buyerListForm.getDistrictNo()))
			districtNo = Long.parseLong(buyerListForm.getDistrictNo()==null?"0":buyerListForm.getDistrictNo());
		if(!"".equals(buyerListForm.getSubDistrictNo()))
			subDistrictNo = Long.parseLong(buyerListForm.getSubDistrictNo()==null?"0":buyerListForm.getSubDistrictNo());
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

function searchBuyer(){
	    document.buyerListForm.cmd.value="Search";
	    document.buyerListForm.action= "<%=request.getContextPath()%>/BuyerList.do";
	    document.forms["buyerListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delBuyer');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.buyerListForm.cmd.value="Delete";
				document.buyerListForm.action="BuyerList.do";
				document.forms["buyerListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.buyerListForm.cmd.value="Search";
		document.forms["buyerListForm"].submit();
	}
}

function loadEdit(buyerId,buyerName)
{	
	document.buyerForm.buyerId.value = buyerId;
	document.buyerForm.buyerName.value = buyerName;
	document.buyerForm.cmd.value = "Edit";
	document.buyerForm.action= "<%=request.getContextPath()%>/Buyer.do";
	document.forms["buyerForm"].submit();
 			 
}

function addBuyer(){

        document.buyerForm.cmd.value="New";
	    document.buyerForm.action= "<%=request.getContextPath()%>/Buyer.do";
	    document.forms["buyerForm"].submit();

}

function getDistrictInfo(objPName, objDName, objSName)
{
	var provinceDD = document.getElementById(objPName);
	var districtDD = document.getElementById(objDName);
	var subDistrictDD = document.getElementById(objSName);
		subDistrictDD.options.length = 1;
		subDistrictDD.options[0].value="";
		subDistrictDD.options[0].text="กรุณาเลือก";
		subDistrictDD.options[0].selected = true;
	
	if(provinceDD.options[provinceDD.selectedIndex].value==''){ //ถ้ายังไม่เลือกจังหวัด อำเภอเป็นกรุณาเลือก
		districtDD.options.length = 1;
		districtDD.options[0].value="";
		districtDD.options[0].text="กรุณาเลือก";
		districtDD.options[0].selected = true;
	} else {
		var reqParameters = new Object();
		reqParameters.cmd = "GetDistrict";
		reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
		new Ajax.Request("<%=request.getContextPath()%>/BuyerList.do",
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
		new Ajax.Request("<%=request.getContextPath()%>/BuyerList.do",
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

function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
	<%if(provinceNo>0){%>
		document.buyerListForm.provinceNo.value = "<%=provinceNo%>";
		getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo');
	<%if(districtNo>0){%>
		document.buyerListForm.districtNo.value = "<%=districtNo%>";
		getSubDistrictInfo('provinceNo','districtNo','subDistrictNo');
	<%if(subDistrictNo>0){%>
		document.buyerListForm.subDistrictNo.value = "<%=subDistrictNo%>";
	<%}}}%>
  loadMenu("M21");
}
</script>
</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="buyerListForm" formAction="BuyerList.do"
	formBean="BuyerListForm">
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
			href='<%=request.getContextPath()%>/BuyerList.do'>การจัดการผู้ซื้อ</a></li>
		<li><img
			src="<%=request.getContextPath()%>/images/arrow-navigator.png"
			width="4" height="8" /></li>
		<li>ค้นหาข้อมูล</li>
	</ul>
	</div>
	</div>

	<!-- content -->


	<div class="content">
	<div class="inside">
	<div class="content-search">
	<div class="search-header"><!-- <p>ค้นหาผู้ซื้อ</p> --> &nbsp;<span
		style="vertical-align: -7px; color: white;"><font
		class="topic">ค้นหาข้อมูลผู้ซื้อ</font></span></div>
	<div class="line-input">
	<ul>
		<li class="topic" style="width: 150px">ชื่อผู้รับซื้อ :</li>
		<li class="boxinput" style="width: 718px"><dcs:validateText
			name="buyerName" property="buyerName" style="width:718px;"
			maxlength="200" /></li>
	</ul>
	</div>
	<div class="line-input">
	<ul>
		<li class="topic" style="width: 150px">จังหวัด :</li>
		<li class="boxinput" style="width: 190px"><select id="provinceNo"
			name="provinceNo" style="width: 190px;"
			onchange="getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo');">
			<option value="">กรุณาเลือก</option>
			<%if(provinceList !=null && provinceList.size()>0){
											for(int p=1; p<provinceList.size(); p++){
												Province pd = (Province)provinceList.get(p);
										%>
			<option value="<%=pd.getProvinceNo()%>"><%=pd.getThaiName()%></option>
			<%}
										}%>
		</select></li>
		<li class="topic" style="width: 60px">อำเภอ :</li>
		<li class="boxinput" style="width: 190px"><dcs:validateDropDown
			name="districtNo" dataSource="<%= districtList %>"
			property="districtNo" keyField="districtNo" displayField="thaiName"
			style="width:190px;"
			onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo')" />
		</li>
		<li class="topic" style="width: 60px">ตำบล :</li>
		<li class="boxinput" style="width: 190px"><dcs:validateDropDown
			name="subDistrictNo" dataSource="<%= subDistrictList %>"
			property="subDistrictNo" keyField="subDistrictNo"
			displayField="thaiName" style="width:190px;" /></li>
	</ul>
	</div>

	<div class="line-input">
	<ul class="btn-search-box">
		<li class="boxinput" style="width: 170px; padding-left: 727px;">
		<button class="btn-search" onclick="searchBuyer();"
			style="padding-bottom: 9px;"></button>
		</li>
	</ul>
	</div>
	<div class="btn-manage" style="margin-left: 0px;"><a
		class="btn-add" onclick="addBuyer();" id="write"></a> <a
		class="btn-del" onclick="goPage('Delete');" id="del"></a></div>



	<!--  for dcsGrid -->
	<table border="0" width="96%">
		<tr>
			<%   if(buyerList!=null){ %>
			<td><dcs:grid dataSource="<%= buyerList %>" name="buyerList"
				pageSize="<%=buyerListForm.getDisplayRow()%>" width="100%">
				<dcs:gridpager imgFirst="/dcswc/images/First.gif"
					imgPrevious="/dcswc/images/Previous.gif"
					imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif" />
				<dcs:gridsorter />
				<dcs:checkboxcolumn name="delBuyer" dataField="buyerId"
					headerText="" width="55" HAlign="left" />
				<!-- dcs:rownumcolumn headerText="ลำดับที่" width="80" HAlign="center" cssClass="tel-sr" /> -->
				<dcs:textcolumn dataField="buyerName" headerText="ชื่อผู้รับซื้อ" width="150" style="cursor:pointer;" sortable="true" HAlign="left"
					cssClass="address-sr" onClick="loadEdit('{buyerId}','{buyerName}');" />
				<dcs:textcolumn dataField="provinceName" headerText="จังหวัด" width="100" sortable="true" HAlign="left" cssClass="tel-sr"
					style="cursor:pointer;" onClick="loadEdit('{buyerId}','{buyerName}');" />
				<dcs:textcolumn dataField="districtName" headerText="อำเภอ" width="100" sortable="true" HAlign="left" cssClass="tel-sr"
					style="cursor:pointer;" onClick="loadEdit('{buyerId}','{buyerName}');" />
				<dcs:textcolumn dataField="subDistrictName" headerText="ตำบล" width="100" sortable="true" HAlign="left" cssClass="tel-sr"
					style="cursor:pointer;" onClick="loadEdit('{buyerId}','{buyerName}');" />
				<dcs:textcolumn dataField="mobile" headerText="เบอร์โทร" width="100" sortable="true" HAlign="left" cssClass="tel-sr"
					style="cursor:pointer;" onClick="loadEdit('{buyerId}','{buyerName}');" />
				<dcs:imagecolumn dataField="linkImageEdit" headerText="" toolTip="แก้ไข" style="cursor:pointer;padding-top:2px" width="40"
					HAlign="center" cssClass="manage-sr" onClick="loadEdit('{buyerId}','{buyerName}');" />
			</dcs:grid></td>
			<% } %>
		</tr>
	</table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countBuyer"))%> รายการ</div>
	<!-- end dcsGrid --></div>
	</div>
	</div>


	<!--footer --> <%@include file="/footer.jsp"%>


	</div>


</dcs:validateForm>
<form name="buyerForm" method="post"
	action="<%=request.getContextPath()%>/Buyer.do"><input
	type="hidden" name="cmd" /> <input type="hidden" name="msg" /> <input
	type="hidden" name="buyerId" /> <input type="hidden" name="buyerName" />
</form>
</body>


</html>