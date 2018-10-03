<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BuyerForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.dbaccess.RegionHome"%>
<%@page import="com.wsndata.dbaccess.ProvinceHome"%>
<%@page import="com.wsndata.data.Province"%>
<%@page import="com.wsndata.data.District"%>
<%@page import="com.wsndata.data.SubDistrict"%>
<%@page import="com.wsndata.data.BreedType"%>
<%@page import="com.wsndata.data.BreedGroup"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport"
	content="width=device-width, initial-scale=0, maximum-scale=1">
<meta name="viewport" content="width=1280,user-scalable=yes">
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

BuyerForm buyerForm = (BuyerForm) request.getAttribute("BuyerForm");
User userLogin = (User) session.getAttribute("userLogin");
String msg = "",cmd="", moo="",placeMoo="";
long buyerId=0,provinceNo1=0,districtNo1=0,subDistrictNo1=0,provinceNo2=0,districtNo2=0,subDistrictNo2=0;
if(buyerForm!=null){
	if(buyerForm.getMsg() !=null && !"".equals(buyerForm.getMsg())){
		msg = buyerForm.getMsg();
	}
	provinceNo1 = buyerForm.getProvinceNo();
	districtNo1 = buyerForm.getDistrictNo();
	subDistrictNo1 = buyerForm.getSubDistrictNo();
	provinceNo2 = buyerForm.getPlaceProvinceNo();
	districtNo2 = buyerForm.getPlaceDistrictNo();
	subDistrictNo2 = buyerForm.getPlaceSubDistrictNo();
	cmd = buyerForm.getCmd();
	buyerId = buyerForm.getBuyerId();
	moo = (buyerForm.getMoo()==0)?"":String.valueOf(buyerForm.getMoo());
	placeMoo = (buyerForm.getPlaceMoo()==0)?"":String.valueOf(buyerForm.getPlaceMoo());
}
List buyerList = (ArrayList) session.getAttribute("buyerList");
List provinceList = (ArrayList) session.getAttribute("provinceList");
List districtList = (ArrayList) session.getAttribute("districtList");
List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
List placeProvinceList = (ArrayList) session.getAttribute("placeProvinceList");
List placeDistrictList = (ArrayList) session.getAttribute("placeDistrictList");
List placeSubDistrictList = (ArrayList) session.getAttribute("placeSubDistrictList");
List breedTypeList = (ArrayList) session.getAttribute("breedTypePlantList");
List breedGroupList = (ArrayList) session.getAttribute("breedGroupPlantList");
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
		showErrorMessage();
		document.buyerForm.provinceNo.value = "0";
		document.buyerForm.placeProvinceNo.value = "0";
		document.buyerForm.moo.value = "";
		document.buyerForm.placeMoo.value = "";
		<% if(buyerForm!=null) { %>
			document.buyerForm.provinceNo.value = "<%=provinceNo1%>";
			getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');
			document.buyerForm.districtNo.value = "<%=districtNo1%>";
			getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
			document.buyerForm.subDistrictNo.value = "<%=subDistrictNo1%>";
			getPostCodeInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
			document.buyerForm.placeProvinceNo.value = "<%=provinceNo2%>";
			<% if(userLogin.getLevel()<=2 || (userLogin.getLevel()==3 && userLogin.getProvinceNo()==provinceNo2)) { %>getDistrictInfo('placeProvinceNo', 'placeDistrictNo', 'placeSubDistrictNo', 'placePostCode');<% } %>
			document.buyerForm.placeDistrictNo.value = "<%=districtNo2%>";
			<% if(userLogin.getLevel()<=3 || (userLogin.getLevel()==4  && userLogin.getDistrictNo()==districtNo2)) { %>getSubDistrictInfo('placeProvinceNo','placeDistrictNo','placeSubDistrictNo', 'placePostCode');<% } %>
			document.buyerForm.placeSubDistrictNo.value = "<%=subDistrictNo2%>";
			<% if(subDistrictNo2>0) { %>getPostCodeInfo('placeProvinceNo','placeDistrictNo','placeSubDistrictNo', 'placePostCode');<% } %>
			document.buyerForm.moo.value = "<%=moo%>";
			document.buyerForm.placeMoo.value = "<%=placeMoo%>";
		<% } if(cmd !=null && "Edit".equals(cmd)){ %>
			document.buyerForm.buyerId.value = "<%=buyerId%>";
		<% } %>
		document.buyerForm.buyerName.focus();
		loadMenu("M21");
	}

	function saveBuyer(){
		var saveIs = true;
		if(document.buyerForm.buyerName.value == ""){
			alert("กรุณาใส่ชื่อผู้รับซื้อ !!");
			document.buyerForm.buyerName.focus();
			saveIs = false;
		}else if(document.buyerForm.addressNo.value == 0){
			alert("กรุณาใส่ที่อยู่ !!");
			document.buyerForm.addressNo.focus();
			saveIs = false;
		}else if(document.buyerForm.provinceNo.value == 0){
			alert("กรุณาเลือกจังหวัด !!");
			saveIs = false;
		}else if(document.buyerForm.districtNo.value == 0){
			alert("กรุณาเลือกอำเภอ !!");
			saveIs = false;
		}else if(document.buyerForm.subDistrictNo.value == 0){
			alert("กรุณาเลือกตำบล !!");
			saveIs = false;
		}else if(document.buyerForm.placeName.value == ""){
			alert("กรุณาใส่ชื่อสถานที่รับซื้อ !!");
			document.buyerForm.placeName.focus();
			saveIs = false;
		}else if(document.buyerForm.placeAddressNo.value == 0){
			alert("กรุณาใส่ที่อยู่สถานที่รับซื้อ !!");
			document.buyerForm.placeAddressNo.focus();
			saveIs = false;
		}else if(document.buyerForm.placeProvinceNo.value == 0){
			alert("กรุณาเลือกจังหวัด !!");
			saveIs = false;
		}else if(document.buyerForm.placeDistrictNo.value == 0){
			alert("กรุณาเลือกอำเภอ !!");
			saveIs = false;
		}else if(document.buyerForm.placeSubDistrictNo.value == 0){
			alert("กรุณาเลือกตำบล !!");
			saveIs = false;
		}
		if(saveIs){
			document.buyerForm.cmd.value="Save";
			document.buyerForm.action= "<%=request.getContextPath()%>/Buyer.do";
			document.forms["buyerForm"].submit();
		}
	}
    
    function checkDigit(element, str)
    {
        var RE = /^\d+$/;
        if(!RE.test(str.value)){
            alert("กรุณาใส่ค่าที่เป็นตัวเลข");
            document.getElementById(element).value = "";
            window.setTimeout(function () {document.getElementById(element).focus();}, 0);
        }
    }
    
function checkTel(str) {
    var num = 0;
    var arry = (str.value).split(",");
    
    for(var i=0;i<arry.length;i++) {
    var patt = /^[0][1-9]([0-9]{7,8})$/;
    var res = patt.test(arry[i]);
        if(res) { num++; }
    }
    if(num!=arry.length) {
    	alert("กรุณาใส่ค่าที่เป็นตัวเลข ในรูปแบบที่ถูกต้อง");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    }
}
	
	function closePage(){
		document.buyerListForm.buyerName.value = "";
		document.buyerListForm.cmd.value = "Search"; 
		document.buyerListForm.action="<%=request.getContextPath()%>/BuyerList.do";
		document.forms["buyerListForm"].submit();
	}
	
	function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
   		<%}%>
 	<%}%>
 	}

	function getDistrictInfo(objPName, objDName, objSName, objPostCode)
	{
		var provinceDD = document.getElementById(objPName);
		var districtDD = document.getElementById(objDName);
		var subDistrictDD = document.getElementById(objSName);
		if(objPostCode != ''){
			var postCodeTxt = document.getElementById(objPostCode);
			postCodeTxt.value = "";
		}
		subDistrictDD.options.length=1;
		subDistrictDD.options[0].value="0";
		subDistrictDD.options[0].text="กรุณาเลือก";
		subDistrictDD.options[0].selected = true;

		if(provinceDD.options[provinceDD.selectedIndex].value=='0'){
			districtDD.options.length = 1;
			districtDD.options[0].value="0";
			districtDD.options[0].text="กรุณาเลือก";
			districtDD.options[0].selected = true;
		}else{
			var reqParameters = new Object();
			reqParameters.cmd = "GetDistrict";
			reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
			if(objPName=="placeProvinceNo") {
				reqParameters.cmd = "GetDistrict2";
				reqParameters.placeProvinceNo = provinceDD.options[provinceDD.selectedIndex].value;
			}
			new Ajax.Request("<%=request.getContextPath()%>/Buyer.do",
			{    
				method: 'post',
				asynchronous: false,
				parameters: reqParameters,
				encoding: 'UTF-8',
				onSuccess: function(transport)
				{                            
					var json = transport.responseText.evalJSON();
					//alert(json+"!!");
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
					}else{
						districtDD.options.length = json.length;
						districtDD.options[0].value="0";
						districtDD.options[0].text="กรุณาเลือก";
						districtDD.options[0].selected = true;
					}
				},
				onFailure: function() {
					//alert('Error');
				}
			});
		}
	}

	function getSubDistrictInfo(objPName, objDName, objSName, objPostCode)
	{
		var provinceDD = document.getElementById(objPName);
		var districtDD = document.getElementById(objDName);
		var subDistrictDD = document.getElementById(objSName);
		
		if(objPostCode != ''){
			var postCodeTxt = document.getElementById(objPostCode);
			postCodeTxt.value = "";
		}
		if(districtDD.options[districtDD.selectedIndex].value == '0'){
			subDistrictDD.options.length = 1;
			subDistrictDD.options[0].value="0";
			subDistrictDD.options[0].text="กรุณาเลือก";
			subDistrictDD.options[0].selected = true;
		}else{
			var reqParameters = new Object();
			reqParameters.cmd = "GetSubDistrict";
			reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
			reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
			if(objPName=="placeProvinceNo") {
				reqParameters.cmd = "GetSubDistrict2";
				reqParameters.placeProvinceNo = provinceDD.options[provinceDD.selectedIndex].value;
				reqParameters.placeDistrictNo = districtDD.options[districtDD.selectedIndex].value;
			}
			new Ajax.Request("<%=request.getContextPath()%>/Buyer.do",
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
					//alert('Error');
				}
			});
		}
	}

	function getPostCodeInfo(objPName, objDName, objSName, objPostCode)
	{
		var postCodeTxt = document.getElementById(objPostCode);
		var provinceDD = document.getElementById(objPName);
		var districtDD = document.getElementById(objDName);
		var subDistrictDD = document.getElementById(objSName);
		var reqParameters = new Object();
		reqParameters.cmd = "GetPostCode";
		reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
		reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;     
		reqParameters.subDistrictNo = subDistrictDD.options[subDistrictDD.selectedIndex].value; 
			if(objPName=="placeProvinceNo") {
				reqParameters.cmd = "GetPostCode2";
				reqParameters.placeProvinceNo = provinceDD.options[provinceDD.selectedIndex].value;
				reqParameters.placeDistrictNo = districtDD.options[districtDD.selectedIndex].value;
				reqParameters.placeSubDistrictNo = subDistrictDD.options[subDistrictDD.selectedIndex].value; 
			}  
		new Ajax.Request("<%=request.getContextPath()%>/Buyer.do",
		{	
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport)
			{					
				var json = transport.responseText.evalJSON(); 
				if(json !=null && json != ""){
					postCodeTxt.value = json[0].postCode;
				}
			},
			onFailure: function() {	
				//alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
			}
		});
	}
</script>
</head>
<body onload="loadData();">
<dcs:validateForm formName="buyerForm" formAction="Buyer.do"
	formBean="BuyerForm">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="msg" />
	<input type="hidden" name="buyerId" />
	<div class="main-inside"><%@include file="/header.jsp"%>
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
		<li>กรอกข้อมูล</li>
	</ul>
	</div>
	</div>

	<!-- content -->
	<div class="content">
	<div class="inside">

	<div class="content-keyin">
	<div class="search-header"><!-- <p>ข้อมูลผู้ซื้อ</p> -->
	&nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px; color: white;"><font
		class="topic">ข้อมูลผู้ซื้อ</font></span></div>
	<fieldset><legend><font size="5"><b>::
	ข้อมูลผู้รับซื้อ ::</b></font></legend>
	<div class="line-input-keyin">
	<ul>
		<li class="topic" style="width: 95px; padding-right: 0px">ชื่อผู้รับซื้อ :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 400px">
		<dcs:validateText name="buyerName" property="buyerName" maxlength="200" style="width:393px;" />
		</li>
	</ul>
	</div>
	<div class="line-input-keyin">
	<ul>
		<li class="topic" style="width: 95px; padding-right: 0px">ที่อยู่ :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 50px">
			<dcs:validateText name="addressNo" property="addressNo" style="width:50px;" maxlength="10" />
		</li>
		<li class="topic" style="width: 50px; padding-right: 0px">หมู่ที่ :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 30px">
			<dcs:validateText name="moo" property="moo" style="width:30px;" maxlength="2" onChange="checkDigit('moo',this);" />
		</li>
		<li class="topic" style="width: 75px; padding-right: 0px">ซอย :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 150px">
			<dcs:validateText name="soi" property="soi" style="width:150px;" maxlength="100" />
		</li>
		<li class="topic" style="width: 90px; padding-right: 0px">ถนน :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 320px"><dcs:validateText
			name="road" property="road" style="width:318px;" maxlength="100" /></li>
	</ul>
	</div>
	<div class="line-input-keyin">
	<ul>
		<li class="topic" style="width: 95px; padding-right: 0px">จังหวัด
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 150px"><select id="provinceNo"
			name="provinceNo" style="width: 157px;"
			onchange="getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo','postCode');">
			<option value="0">กรุณาเลือก</option>
			<%if(provinceList !=null && provinceList.size()>0){
											for(int p=1; p<provinceList.size(); p++){
												Province pd = (Province)provinceList.get(p);
										%>
			<option value="<%=pd.getProvinceNo()%>"><%=pd.getThaiName()%></option>
			<%}
										}%>
		</select></li>
		<li class="topic" style="width: 74px; padding-right: 0px">อำเภอ :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 160px"><dcs:validateDropDown
			name="districtNo" dataSource="<%= districtList %>"
			property="districtNo" keyField="districtNo" displayField="thaiName"
			isRequire="true"
			onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo','postCode')"
			style="width:157px;" /></li>
		<li class="topic" style="width: 80px; padding-right: 0px">ตำบล :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 150px"><dcs:validateDropDown
			name="subDistrictNo" dataSource="<%= subDistrictList %>"
			property="subDistrictNo" keyField="subDistrictNo"
			displayField="thaiName" isRequire="true"
			onChange="getPostCodeInfo('provinceNo','districtNo','subDistrictNo','postCode')"
			style="width:147px;" /></li>
		<li class="topic" style="width: 80px; padding-right: 0px">รหัสไปรษณีย์
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 70px;"><dcs:validateText
			name="postCode" property="postCode" maxlength="5" style="width:70px"
			isReadOnly="true" /></li>
	</ul>
	</div>
	<div class="line-input-keyin">
	<ul>
		<li class="topic" style="width: 95px; padding-right: 0px">เบอร์มือถือ
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 150px;">
			<dcs:validateText name="mobile" property="mobile" maxlength="10" style="width:150px;" onChange="checkTel(this);"/>
		</li>
		<li class="topic" style="width: 74px; padding-right: 0px">เบอร์บ้าน
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput " style="width: 150px;">
			<dcs:validateText name="tel" property="tel" maxlength="30" style="width:150px;" onChange="checkTel(this);"/>
		</li>
		<li class="topic" style="width: 90px; padding-right: 0px">เบอร์โทรสาร :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 140px;">
			<dcs:validateText name="fax" property="fax" maxlength="30" style="width:140px;" onChange="checkTel(this);"/>
		</li>
	</ul>
	</div>
	</fieldset>
	<br />
	<fieldset><legend><font size="5"><b>::
	ข้อมูลสถานที่รับซื้อ ::</b></font></legend>
	<div class="line-input-keyin">
	<ul>
		<li class="topic" style="width: 95px; padding-right: 0px">ชื่อสถานที่รับซื้อ
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 400px"><dcs:validateText
			name="placeName" property="placeName" maxlength="200"
			style="width:393px;" /></li>
	</ul>
	</div>
	<div class="line-input-keyin">
	<ul>
		<li class="topic" style="width: 95px; padding-right: 0px">ที่อยู่
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font
			color="red">*</font></li>
		<li class="boxinput" style="width: 50px"><dcs:validateText
			name="placeAddressNo" property="placeAddressNo" style="width:50px;"
			maxlength="10" /></li>
		<li class="topic" style="width: 50px; padding-right: 0px">หมู่ที่
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 30px"><dcs:validateText
			name="placeMoo" property="placeMoo" style="width:30px;" maxlength="2"
			onChange="checkDigit('placeMoo',this);" /></li>
		<li class="topic" style="width: 75px; padding-right: 0px">ซอย :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 150px"><dcs:validateText
			name="placeSoi" property="placeSoi" style="width:150px;"
			maxlength="100" /></li>
		<li class="topic" style="width: 65px; padding-right: 0px">ถนน :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 338px"><dcs:validateText
			name="placeRoad" property="placeRoad" style="width:338px;"
			maxlength="100" /></li>
	</ul>
	</div>
	<div class="line-input-keyin">
	<ul>
		<li class="topic" style="width: 95px; padding-right: 0px">จังหวัด
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 150px"><select
			id="placeProvinceNo" name="placeProvinceNo" style="width: 157px;"
			onchange="getDistrictInfo('placeProvinceNo', 'placeDistrictNo', 'placeSubDistrictNo','placePostCode');">
			<option value="0">กรุณาเลือก</option>
			<%if(placeProvinceList !=null && placeProvinceList.size()>0){
											for(int p=1; p<placeProvinceList.size(); p++){
												Province pd = (Province)placeProvinceList.get(p);
										%>
			<option value="<%=pd.getProvinceNo()%>"><%=pd.getThaiName()%></option>
			<%}
										}%>
		</select></li>
		<li class="topic" style="width: 74px; padding-right: 0px">อำเภอ :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 160px"><dcs:validateDropDown
			name="placeDistrictNo" dataSource="<%= placeDistrictList %>"
			property="placeDistrictNo" keyField="districtNo"
			displayField="thaiName" isRequire="true"
			onChange="getSubDistrictInfo('placeProvinceNo', 'placeDistrictNo', 'placeSubDistrictNo','placePostCode')"
			style="width:157px;" /></li>
		<li class="topic" style="width: 55px; padding-right: 0px">ตำบล :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 150px"><dcs:validateDropDown
			name="placeSubDistrictNo" dataSource="<%= placeSubDistrictList %>"
			property="placeSubDistrictNo" keyField="subDistrictNo"
			displayField="thaiName" isRequire="true"
			onChange="getPostCodeInfo('placeProvinceNo', 'placeDistrictNo', 'placeSubDistrictNo','placePostCode')"
			style="width:147px;" /></li>
		<li class="topic" style="width: 90px; padding-right: 0px">รหัสไปรษณีย์
		:</li>
		<li class="topic" style="width: 1px; padding-right: 5px"></li>
		<li class="boxinput" style="width: 80px;"><dcs:validateText
			name="placePostCode" property="placePostCode" maxlength="5"
			style="width:80px" isReadOnly="true" /></li>
	</ul>
	</div>
	</fieldset>
	<br />
	<table align="center" style="margin-top: 10px;">
		<tr>
			<td><a class="btn-save" onclick="saveBuyer();" id="write"></a></td>
			<td><a class="btn-cancel" onclick="closePage();"></a></td>
		</tr>
	</table>
	<div class="clear"></div>
	</div>
	</div>
	</div>
	<!--footer --> <%@include file="/footer.jsp"%>
	</div>
</dcs:validateForm>
<form name="buyerListForm" method="post" action="<%=request.getContextPath()%>/BuyerList.do">
	<input type="hidden" name="buyerName" />
	<input type="hidden" name="cmd" />
</form>
</body>
</html>