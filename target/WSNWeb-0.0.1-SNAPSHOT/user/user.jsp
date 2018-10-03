<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.wsnweb.form.UserForm"%>
<%@page import="com.wsndata.data.Prefix"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.wsndata.data.FarmerGroup"%>
<%@page import="com.wsndata.data.Region"%>
<%@page import="com.wsndata.data.Province"%>
<%@page import="com.wsndata.data.SubDistrict"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/sumoselect.css" />

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

<style type="text/css">
.clearfix {
	clear: both;
}
select[disabled] {
	border-color:#DBDBDB;
	color: #969696;
}
.SumoSelect { top: 10px; }
.fixBottom ul {padding-bottom: 0px;}
</style>

<title><%=Utility.get("WEB_TITLE")%></title>
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	UserForm userForm = (UserForm)request.getAttribute("UserForm");
	User userLogin = (User) session.getAttribute("userLogin");
	List prefixList = (ArrayList) session.getAttribute("allPrefix");
	List regionList = (ArrayList) session.getAttribute("regionList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	
	List provList = (ArrayList)session.getAttribute("provFilteredByRegionList");
	
	List districtList = (ArrayList) session.getAttribute("districtList");
	List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
	List cooperativeGroupList = (ArrayList) session.getAttribute("cooperativeGroupList");
	List levelList = (ArrayList) session.getAttribute("levelList");
	
	String userTypeList = (String) session.getAttribute("userTypeList");
	String userRegionList =(String) session.getAttribute("userRegionList");
	String userProvinceList =(String) session.getAttribute("userProvinceList");
	String userFarmerGroupList =(String) session.getAttribute("userFarmerGroupList");
	
	
	String[] groupArray = Utility.get("USER_GROUP").split(",");
	String[] levelArray = Utility.get("USER_LEVEL").split(",");
	String userName="",firstName="", lastName="", email="", cmd="", msg="", abbrPrefix="", branchCode="", login="";
	long level=0, farmerGroupId=0, regionNo=0, provinceNo=0, districtNo=0, subDistrictNo=0, userLevel=0;
	if(userForm!=null){
		if(userForm.getMsg()!=null && !"".equals(userForm.getMsg())){
			msg = userForm.getMsg();
		}
		branchCode = userForm.getBranch()==null?"1":userForm.getBranch();
		abbrPrefix = userForm.getAbbrPrefix()==null?"":userForm.getAbbrPrefix();
		cmd = userForm.getCmd()==null?"":userForm.getCmd();
		userName = userForm.getUserName()==null?"":userForm.getUserName();
		firstName = userForm.getFirstName()==null?"":userForm.getFirstName();
		lastName = userForm.getLastName()==null?"":userForm.getLastName();
		email = userForm.getEmail()==null?"":userForm.getEmail();
		level = userForm.getLevel();
		regionNo = userForm.getRegionNo();
		provinceNo = userForm.getProvinceNo();
		districtNo = userForm.getDistrictNo();
		subDistrictNo = userForm.getSubDistrictNo();
		farmerGroupId = userForm.getFarmerGroupId();
	}
	login = userLogin.getUserName();
	userLevel = userLogin.getLevel();
	boolean isReadOnly = false;	
%>

<script type="text/javascript">
	var regionArray = new Array();
	var provinceArray = new Array();
	var farmerGroupArray = new Array();

 // IE
  $(document).ready(function() 
  {
  document.forms['userForm'].reset();
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
	document.userForm.cmd.value = "<%= cmd %>";
	<% if(!cmd.equals("")) { %>
		document.userForm.branch.value 			= "<%= branchCode %>";
		<% if(!abbrPrefix.equals("")) { %>document.userForm.abbrPrefix.value = "<%= abbrPrefix %>";<% } %>
   		document.userForm.userName.value 		= "<%= userName %>";
   		document.userForm.firstName.value 		= "<%= firstName %>";
   		document.userForm.lastName.value 		= "<%= lastName %>";
   		document.userForm.email.value 			= "<%= email %>";
   		document.userForm.level.value			= "<%=level%>";
   		<% if(cmd.equals("Edit")) { %>
   	    document.getElementById("userName").readOnly = true;
		document.userForm.branch.value 			= "<%= branchCode %>";
   	    <% if(!abbrPrefix.equals("")) { %>document.userForm.abbrPrefix.value = "<%= abbrPrefix %>";<% } %>
   		document.userForm.userName.value 		= "<%= userName %>";
   		document.userForm.firstName.value 		= "<%= firstName %>";
   		document.userForm.lastName.value 		= "<%= lastName %>";
   		document.userForm.email.value 			= "<%= email %>";
   		document.userForm.level.value			= "<%=level%>";
   		document.userForm.userType.value			= "<%=userTypeList%>";
  		//For dropdown by Thanaput.s 14/09/2017
		var userTypeSplit = $('#userType').val().split(",");
		for(var i = 0; i < userTypeSplit.length; i++){
			$('#typeGroup')[0].sumo.selectItem(String(userTypeSplit[i]));
		}
   		document.userForm.userRegionNo.value			= "<%=userRegionList%>";
  		//For dropdown by Thanaput.s 14/09/2017
		var regionSplit = $('#userRegionNo').val().split(",");
		for(var i = 0; i < regionSplit.length; i++){
			$('#regionNo')[0].sumo.selectItem(String(regionSplit[i]));
		}
		getProvinceInfo('userRegionNo','userProvinceNo','districtNo','subDistrictNo');
		//End Thanaput.s 14/09/2017
   		document.userForm.userProvinceNo.value			= "<%=userProvinceList%>";
  		//For dropdown by Thanaput.s 14/09/2017
		var provinceSplit = $('#userProvinceNo').val().split(",");
		for(var i = 0; i < provinceSplit.length; i++){
			$('#provinceNo')[0].sumo.selectItem(String(provinceSplit[i]));
		}
		getDistrictInfo('userRegionNo','userProvinceNo','districtNo','subDistrictNo');
		//End Thanaput.s 14/09/2017
   		document.userForm.districtNo.value			= "<%=districtNo%>";
		getSubDistrictInfo('userRegionNo','userProvinceNo','districtNo','subDistrictNo');
   		document.userForm.subDistrictNo.value			= "<%=subDistrictNo%>";
   		document.userForm.userFarmerGroupId.value			= "<%=userFarmerGroupList%>";
  		//For dropdown by Thanaput.s 14/09/2017
		var fgroupSplit = $('#userFarmerGroupId').val().split(",");
		for(var i = 0; i < fgroupSplit.length; i++){
			$('#farmerGroupId')[0].sumo.selectItem(String(fgroupSplit[i]));
		}
		//End Thanaput.s 14/09/2017
   		changeLevel(<%=level%>);
   		<% if(login.equals(userName)) { %>
   			document.userForm.level.disabled = true;
   			document.userForm.farmerGroupId.disabled = true;
   			changeLevel(6);
   		<% } %>
   		
   		document.userForm.msg.value = "Edit";
   		<% } else if(cmd.equals("New")) { %> // new
   		document.getElementById("userName").readOnly = false;
		window.setTimeout(function () { document.getElementById("userName").focus(); }, 0);
   		changeLevel("<%=level%>");
   		<% }} %>
    showErrorMessage(); 
	loadMenu("M01");		
}
function saveUser(){
		if(document.userForm.userName.value ==''){
		 	alert("กรุณาใส่ชื่อผู้ใช้งาน");
		 	document.userForm.userName.focus();	
		 	return false; 	
		}
		if(document.userForm.firstName.value==''){
		 	alert("กรุณาใส่ชื่อ");
		 	document.userForm.firstName.focus();	
		 	return false;
		}
		if(document.userForm.lastName.value==''){
		 	alert("กรุณาใส่นามสกุล");
		 	document.userForm.lastName.focus();
		 	return false;	
		}
	    //  var branch =  document.getElementById("branch").options[document.getElementById("branch").selectedIndex].text;
	    // check email format
	    if (document.userForm.email.value != ''){
			 var str = document.userForm.email.value;
			 var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
			 if(!filter.test(str)){
			 	alert("กรุณาใส่รูปแบบอีเมลให้ถูกต้อง");  
			 	document.userForm.email.focus();
        		return false;
			 }
		}
		var strAlert = "";
		
		
		if(document.userForm.level.value> 1 && document.userForm.userRegionNo.value=='') {
			strAlert += "ภูมิภาค ";
		}
		if(document.userForm.level.value>2 && document.userForm.userProvinceNo.value=='') {
			strAlert += "จังหวัด ";
		}
		if(document.userForm.level.value>3 && document.userForm.districtNo.value=='0') {
			strAlert += "อำเภอ ";
		}
		if(document.userForm.level.value>4 && document.userForm.subDistrictNo.value=='0') {
			strAlert += "ตำบล";
		}
		
		if(strAlert!=""){
		 	alert("กรุณาเลือก"+strAlert);
		 	document.userForm.level.focus();
		 	return false;	
		}
		if(document.userForm.usr.value == document.userForm.userName.value){
		    alert("ชื่อผู้ใช้งาน '" + document.userForm.usr.value + "' ซ้ำ กรุณากรอกข้อมูลผู้ใช้งานใหม่อีกครั้ง");
		 	document.userForm.usr.focus();
		 	return false;
		}
		document.getElementById("level").disabled = false;
		var level = document.userForm.level.value;
		if(level>4) { document.getElementById("subDistrictNo").disabled = false; }
		if(level>3) { document.getElementById("districtNo").disabled = false; }
		if(level>2) { document.getElementById("provinceNo").disabled = false; }
		if(level>1) { document.getElementById("regionNo").disabled = false; }
		
		
		if(document.userForm.userFarmerGroupId.value==''){
			if($('#farmerGroupId option').size() == 0) 
				document.userForm.userFarmerGroupId.value = '0';
			else {
		 		alert("กรุณาใส่สังกัดสหกรณ์");
		 		document.userForm.userFarmerGroupId.focus();
		 		return false;
		 	}	
		}
		
	    document.userForm.cmd.value="Save";
	    document.userForm.action= "<%=request.getContextPath()%>/User.do";
	    document.forms["userForm"].submit();
}

function showErrorMessage(){
	<% if(!"".equals(msg)){ %>
		alert("<%=msg%>"); 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
    <% }} %>
}

function closePage(){
	document.userListForm.userName.value = ""; 
	document.userListForm.email.value = ""; 
	document.userListForm.firstName.value = "";
	document.userListForm.lastName.value = "";
	document.userListForm.cmd.value = "Search"; 
	document.userListForm.action="<%=request.getContextPath()%>/UserList.do";
	document.forms["userListForm"].submit();
}

    function checkNumber(str)    {
        var RE = /^\d+$/;
        if(RE.test(str.value)){
        }else{
            alert("กรุณาใส่ค่าที่เป็นตัวเลข");
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            document.getElementById(str.id).focus();
            }, 0);
        }
    }

    function checkEng(str)    {
        var RE = /^[A-Za-z0-9]+$/;
        if(RE.test(str.value)){
        }else{
            alert("กรุณาใส่ชื่อผู้ใช้ เป็นภาษาอังกฤษ หรือตัวเลข");
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            document.getElementById(str.id).focus();
            }, 0);
        }
    }

function checkDuplicateUser()
{
	var reqParameters = new Object();
	reqParameters.cmd = "GetUserName";
	checkEng(document.userForm.userName);
	document.userForm.userName.value = document.userForm.userName.value.trim().toLowerCase();
	reqParameters.userName = document.userForm.userName.value;
	new Ajax.Request("<%=request.getContextPath()%>/User.do",
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
				if(json[0].userName == document.userForm.userName.value){
					document.userForm.usr.value = json[0].userName;
					alert("ชื่อผู้ใช้งาน '" + json[0].userName + "' ซ้ำ กรุณากรอกข้อมูลผู้ใช้งานใหม่อีกครั้ง");
					document.getElementById("userName").value = "";
					window.setTimeout(function () { document.getElementById("userName").focus(); }, 0);
					return false;
				}
			}
		},onFailure: function() {	
			// alert('เกิดข้อผิดพลาดกรุณากรอกชื่อผู้ใช้งานใหม่อีกครั้ง');
		}
	});
}

function changeLevel(level) 
{
	var regionDD = document.getElementById("regionNo");
	var provinceDD = document.getElementById("provinceNo");
	var districtDD = document.getElementById("districtNo");
	var subDistrictDD = document.getElementById("subDistrictNo");
	var cmd = document.userForm.cmd.value;

	if(cmd!="" && (level>0 && level<6)) {
		if(level == 1) {
			$('#regionNo')[0].sumo.unSelectAll();
			$('#userRegionNo').val("");
			$('#provinceNo')[0].sumo.unSelectAll();
			$('#userProvinceNo').val("");
			$('#districtNo').val(0);
			$('#subDistrictNo').val(0);
		}
		if(level == 2) {
			regionDD.disabled = false;
			$('#userRegionNo').attr('disabled', false);
		}
		if(level == 3) {
			regionDD.disabled = false;
			$('#userRegionNo').attr('disabled', false);
			provinceDD.disabled = false;
			$('#userProvinceNo').attr('disabled', false);
		}
		if(level == 4) {
			regionDD.disabled = false;
			$('#userRegionNo').attr('disabled', false);
			provinceDD.disabled = false;
			$('#userProvinceNo').attr('disabled', false);
			districtDD.disabled = false;
		}
		if(level == 5) {
			regionDD.disabled = false;
			$('#userRegionNo').attr('disabled', false);
			provinceDD.disabled = false;
			$('#userProvinceNo').attr('disabled', false);
			districtDD.disabled = false;
			subDistrictDD.disabled = false;
		}
	}
	if(level<5)
		subDistrictDD.disabled = true;
	if(level<4)
		districtDD.disabled = true;
	if(level<3) {
		provinceDD.disabled = true;
		$('#userProvinceNo').attr('disabled', true);
	}
	if(level<2) {
		regionDD.disabled = true;
		$('#userRegionNo').attr('disabled', true);
	}
	$('#regionNo')[0].sumo.reload();
	$('#provinceNo')[0].sumo.reload();
}

function getRegionInfo(objRName, objPName, objDName, objSName)
{

	//alert("get region!");
	var regionDD = document.getElementById(objRName);
	var provinceDD = document.getElementById(objPName);
	var districtDD = document.getElementById(objDName);

	provinceDD.options.length = 1;
	provinceDD.options[0].value="0";
	provinceDD.options[0].text="กรุณาเลือก";
	provinceDD.options[0].selected = true;
		document.userForm.provinceNo.value = "0";
		document.userForm.districtNo.valueu = "0";
		document.userForm.subDistrictNo.value = "0";

	// if(!regionDD.options[regionDD.selectedIndex].value==''){ //ถ้ายังไม่เลือกภูมิภาค ให้จังหวัดเป็นกรุณาเลือก
  	var reqParameters = new Object();
  	reqParameters.cmd = "GetRegion";
  	new Ajax.Request("<%=request.getContextPath()%>/User.do",
  	{     
  		method: 'post',
  		asynchronous: false,
  		parameters: reqParameters,
  		encoding: 'UTF-8',
  		onSuccess: function(transport)
  		{                             
  			var json = transport.responseText.evalJSON(); 
  			if(json !=null && json != ""){
  				regionDD.options.length = json.length+1;
  				regionDD.options[0].value="0";
  				regionDD.options[0].text="กรุณาเลือก";
  				regionDD.options[0].selected = true;

  				var r = 0;
  				for(var i = 0; i < json.length; i++){
  					r=r+1;
  					regionDD.options[r].value=json[i].regionNo;
  					regionDD.options[r].text=json[i].regionName;
  				}
  			}
  			getCooperativeGroup(objRName,objPName,objDName,objSName,'farmerGroupId');
  		},
  		onFailure: function() {
  			// alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
  		}
  	});
}

function getProvinceInfo(objRName, objPName, objDName, objSName)
{

	var regionDD = document.getElementById(objRName); 
	var provinceDD = document.getElementById(objPName);
	var districtDD = document.getElementById(objDName);
	
	var regionNo = $("#userRegionNo").val(); // added on 29/12/2016

	//alert(regionNo+"!!");

	districtDD.options.length = 1;
	districtDD.options[0].value="0";
	districtDD.options[0].text="กรุณาเลือก";
	districtDD.options[0].selected = true;
	document.userForm.districtNo.value = "0";
	document.userForm.subDistrictNo.value = "0";

	 if(regionNo == "" || regionNo == "0") { // added on 29/12/2016
		//provinceDD.options.length = 1;
		//provinceDD.options[0].value="0";
		//provinceDD.options[0].text="กรุณาเลือก";
		//provinceDD.options[0].selected = true;
		$('#provinceNo option').each(function(i) {
			$(this).remove();
			$('#provinceNo')[0].sumo.reload();
		});
	} else {													//ถ้าเลือกภูมิภาคแล้ว เข้าหลังบ้าน
		var reqParameters = new Object();
		reqParameters.cmd = "GetProvince";
		// reqParameters.regionNo = regionDD.options[regionDD.selectedIndex].value;
		reqParameters.userRegionNo = regionNo; // added on 29/12/2016
		new Ajax.Request("<%=request.getContextPath()%>/User.do",
		{
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport)
			{
				var json = transport.responseText.evalJSON(); 
				if(json !=null && json != ""){
				
					var r = 0;
				    var divVal = "";
					for(var i = 0; i < json.length; i++){
						r=r+1;
						
		     			divVal += "<option value=" + json[i].provinceNo + " />" + json[i].provinceThai+ "</option>"; 
				       
					}
					document.getElementById("provinceNo").innerHTML = divVal;
				}
				
				$('#provinceNo')[0].sumo.reload();
			},
			onFailure: function() {
				// alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
			}
		});
	}
	getCooperativeGroup(objRName,objPName,objDName,objSName,'farmerGroupId');
}

function getDistrictInfo(objRName, objPName, objDName, objSName)
{
	var provinceDD = document.getElementById(objPName);
	var districtDD = document.getElementById(objDName);
	var subDistrictDD = document.getElementById(objSName);
	
	
	var provinceNo = $("#userProvinceNo").val(); // added on 29/12/2016
	

	subDistrictDD.options.length = 1;
	subDistrictDD.options[0].value="0";
	subDistrictDD.options[0].text="กรุณาเลือก";
	subDistrictDD.options[0].selected = true;
	document.userForm.subDistrictNo.value = "0";
	
	// if(provinceDD.options[provinceDD.selectedIndex].value=='0') {						//ถ้ายังไม่เลือกจังหวัด อำเภอเป็นกรุณาเลือก
	if(provinceNo == ""){
		districtDD.options.length = 1;
		districtDD.options[0].value="0";
		districtDD.options[0].text="กรุณาเลือก";
		districtDD.options[0].selected = true;
	} else {														//ถ้าเลือกจังหวัดแล้ว เข้าหลังบ้าน
		var reqParameters = new Object();
		reqParameters.cmd = "GetDistrict";
	//	reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;   
		reqParameters.userProvinceNo = provinceNo;
		new Ajax.Request("<%=request.getContextPath()%>/User.do",
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
				// alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
			}
		});
	}
	getCooperativeGroup(objRName,objPName,objDName,objSName,'farmerGroupId');
}

function getSubDistrictInfo(objRName, objPName, objDName, objSName)
{
	//var provinceDD = document.getElementById(objPName);
	
	
	var provinceNo = $("#userProvinceNo").val(); // added on 29/12/2016
	var districtDD = document.getElementById(objDName);
	var subDistrictDD = document.getElementById(objSName);
	
	
	if( districtDD.options[districtDD.selectedIndex] == undefined || districtDD.options[districtDD.selectedIndex].value == '0'){
		subDistrictDD.options.length = 1;
		subDistrictDD.options[0].value="0";
		subDistrictDD.options[0].text="กรุณาเลือก";
		subDistrictDD.options[0].selected = true;
	} else {
		var reqParameters = new Object();
		reqParameters.cmd = "GetSubDistrict";
		reqParameters.userProvinceNo =  provinceNo;
		//reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
		reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
		new Ajax.Request("<%=request.getContextPath()%>/User.do",
		{
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport)
			{
				var json = transport.responseText.evalJSON(); 
				subDistrictDD.options.length = 1;
				subDistrictDD.options[0].value="0";
				subDistrictDD.options[0].text="กรุณาเลือก";
				subDistrictDD.options[0].selected = true;
				if(json !=null && json != ""){
					subDistrictDD.options.length = json.length+1;

					var r = 0;

					for(var i = 0; i < json.length; i++){
						r=r+1;
						subDistrictDD.options[r].value=json[i].subDistrictNo;
						subDistrictDD.options[r].text=json[i].subDistrictThai;

					}
				}
			},
			onFailure: function() {
				// alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
			}
		});
	}
	getCooperativeGroup(objRName,objPName,objDName,objSName,'farmerGroupId');
}

function getCooperativeGroup(objRName, objPName, objDName, objSName, objCooperative)
{
	//var regionDD = document.getElementById(objRName);
	//var provinceDD = document.getElementById(objPName);
	//alert("get cooperative group");
	var regionNo = $("#userRegionNo").val(); // added on 29/12/2016
	var provinceNo = $("#userProvinceNo").val(); // added on 29/12/2016
	
	var districtDD = document.getElementById(objDName);
	var subDistrictDD = document.getElementById(objSName);
	var cooperativeDD = document.getElementById(objCooperative);

	var reqParameters = new Object();
	reqParameters.cmd = "getCooperative";
	//reqParameters.regionNo = regionDD.options[regionDD.selectedIndex].value;
	//reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
	
	reqParameters.userRegionNo = regionNo;
	reqParameters.userProvinceNo = provinceNo;
	reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
	reqParameters.subDistrictNo = subDistrictDD.options[subDistrictDD.selectedIndex].value;
	new Ajax.Request("<%=request.getContextPath()%>/User.do",
	{     
		method: 'post',
		asynchronous: false,
		parameters: reqParameters,
		encoding: 'UTF-8',
		onSuccess: function(transport)
		{                             
			var json = transport.responseText.evalJSON();
			$('#farmerGroupId option').each(function(i) {
				$(this).remove();
				$('#userFarmerGroupId').val("");
			});
			/*cooperativeDD.options.length = 1;
			cooperativeDD.options[0].value="0";
			cooperativeDD.options[0].text="ทั้งหมด";
			cooperativeDD.options[0].selected = true;*/
			if(json !=null && json != ""){
				/*cooperativeDD.options.length = json.length+1;

				var r = 0;
				for(var i = 0; i < json.length; i++){
					r = r+1;
					cooperativeDD.options[r].value=json[i].farmerGroupId;
					cooperativeDD.options[r].text=json[i].farmerGroupName;
				}*/
				var r = 0;
				var divVal = "";
				for(var i = 0; i < json.length; i++){
					r = r+1;
					divVal += "<option value=" + json[i].farmerGroupId + " />" + json[i].farmerGroupName + "</option>";
				}
				document.getElementById("farmerGroupId").innerHTML = divVal;
			}
			$('#farmerGroupId')[0].sumo.reload();
		},
		onFailure: function() {
			//alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
		}
	});
}   
</script>

</head>
<body onload="loadData();">
<dcs:validateForm formName="userForm" formAction="User.do" formBean="UserForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="msg" />
<input type="hidden" name="usr" />
<input type="hidden" name="branch" />

<div class="main-inside">
      <!-- insert header -->
            <%@include file="/header.jsp" %>
            <div class="navigator">
            <div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>การจัดการผู้ใช้งาน</a></li>
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
                <!--<p>ข้อมูลผู้ใช้งาน</p> -->
                 &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลผู้ใช้งานระบบ</font></span>
              
                </div>
                <fieldset><legend><font size="5"><b>:: ข้อมูลผู้ใช้งาน ::</b></font></legend>
                <div class="line-input-keyin">
                    <ul>
                          <li class="topic" style="width: 80px;padding-right:0px">ชื่อเข้าใช้งาน :</li>
                  		  <li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                          <li class="boxinput" style="width: 145px">
                              <dcs:validateText name="userName" property="userName" maxlength="20"  style="width: 145px;" onChange="checkDuplicateUser();" />
                          </li>
                        <li class="topic" style="width: 80px;padding-right:0px">คำนำหน้า :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"></li>
                        <li class="boxinput" style="width: 155px">
                             <select id="abbrPrefix"  name="abbrPrefix" style="width:155px;" >
		                    		<%if(prefixList !=null && prefixList.size()>0){
		                    			for(int b=0; b<prefixList.size(); b++){
		                    				Prefix pref = (Prefix)prefixList.get(b);
		                    		 %>
		                    		 	<option value="<%= pref.getAbbrPrefix()%>"><%= pref.getAbbrPrefix()%></option>
		                    		 <%}} %>
		               		 </select>
                        </li>
                    
                        <li class="topic" style="width: 50px;padding-right:0px">ชื่อ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width: 145px">
                              <dcs:validateText name="firstName" property="firstName" maxlength="30" style="width: 145px;"/>
                        </li>
                        <li class="topic" style="width: 60px;padding-right:0px">นามสกุล :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width: 145px">
                              <dcs:validateText name="lastName" property="lastName" maxlength="30" style="width: 145px;"/>
                        </li>
                    </ul>
                </div>
                
                <div class="line-input-keyin">
                  <ul>
                              <li class="topic"style="width: 80px;padding-right:0px">เบอร์มือถือ :</li>
                  			  <li class="topic" style="width: 1px;padding-right:5px"></li>
	                          <li class="boxinput" style="width: 145px">
	                                <dcs:validateText name="mobile" property="mobile" maxlength="10"  style="width: 145px;" onChange="checkNumber(this);"/>
	                          </li>
                              <li class="topic"style="width: 80px;padding-right:0px">เบอร์บ้าน :</li>
                  			  <li class="topic" style="width: 1px;padding-right:5px"></li>
	                          <li class="boxinput" style="width: 150px">
	                                <dcs:validateText name="tel" property="tel" maxlength="9"  style="width: 148px;" onChange="checkNumber(this);"/>
	                          </li>
                              <li class="topic"style="width: 55px;padding-right:0px">Email :</li>
                  			  <li class="topic" style="width: 1px;padding-right:5px"></li>
                              <li class="boxinput" style="width: 370px;">
                                  <dcs:validateText name="email" property="email" maxlength="60"  style="width: 370px;"/>
                              </li>
                    </ul>
                </div></fieldset><br />
                
                
                <fieldset><legend><font size="5"><b>:: ระดับผู้ใช้งาน ::</b></font></legend>
                <div class="line-input fixBottom" style="overflow: visible !important;">
                    <ul> 
                          <li class="topic" style="width: 85px; padding-right:0px">ระดับผู้ใช้งาน :</li>
                  		  <li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                          <li class="boxinput" style="width: 150px">
                             <select id="level"  name="level" style="width:150px;" onchange="changeLevel(this.value);">
		                    		<%if(levelArray !=null && levelArray.length>0){
		                    		int condition = levelArray.length;
		                    		//if(cmd.equals("Edit") && (level>1 && level<5)) { condition = (int)level+1; }
		                    			for(int i=((int)userLevel-1); i<condition; i++){
		                    		 %>
		                    		 	<option value="<%=i+1%>"><%= levelArray[i]%></option>
		                    		 <%}} %>
		               		 </select>
                          </li>
                          
                        <li class="topic" style="width: 75px;padding-right:0px">กลุ่มผู้ใช้งาน :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width: 150px">
		               		<input type="hidden" id="userType" name="userType" value="0" />
			           			<select id="typeGroup" name="typeGroup" multiple="multiple" class="SlectBox" placeholder="กรุณาเลือก">
						        	<% if(groupArray != null && groupArray.length > 0 ) {
						        		for(int i = 0; i < groupArray.length; i++){
					        		%>
					            		<option value="<%= groupArray[i] %>"><%= groupArray[i] %></option>
					            	<% } } %>
						        </select>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
	<div class="line-input fixBottom" style="overflow: visible;">
	<ul>
		<li class="topic" style="width: 85px; padding-right: 0px">ภูมิภาค :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 150px">
			<!-- Multi select by Thanaput.S 14/09/2017 -->
			<input type="hidden" id="userRegionNo" name="userRegionNo" value="" />
			<select id="regionNo" name="regionNo" multiple="multiple" class="SlectBox" placeholder="กรุณาเลือก">
			<% if(regionList != null && regionList.size() > 0 ) {
				for(int i = 0; i < regionList.size(); i++){
					Region objRegion = (Region)regionList.get(i);
			%>
				<option value="<%= objRegion.getRegionNo() %>"><%= objRegion.getRegionName() %></option>
			<% } } %>
			</select>
			<!-- End Multi select by Thanaput.S 14/09/2017 -->
		</li>
		<li class="topic" style="width: 75px; padding-right: 0px">จังหวัด :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 150px">
			<!-- Multi select add by Thanaput.S 14/09/2017 -->
			<input type="hidden" id="userProvinceNo" name="userProvinceNo" value="" />
			<select id="provinceNo" name="provinceNo" multiple="multiple" class="SlectBox" placeholder="กรุณาเลือก">
			<% if(provinceList != null && provinceList.size() > 0 ) {
				for(int i = 0; i < provinceList.size(); i++){
					Province objProvince = (Province)provinceList.get(i);
			%>
				<option value="<%= objProvince.getProvinceNo() %>"><%= objProvince.getThaiName() %></option>
			<% } } %>
			</select>
			<!-- End Multi select by Thanaput.S 14/09/2017 -->
		</li>
		<li class="topic" style="width: 50px; padding-right: 0px">อำเภอ :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 150px"><dcs:validateDropDown
			name="districtNo" dataSource="<%= districtList %>"
			property="districtNo" keyField="districtNo" displayField="thaiName"
			onChange="getSubDistrictInfo('userRegionNo','userProvinceNo','districtNo','subDistrictNo')"
			style="width:150px;" isRequire="true"/></li>
		<li class="topic" style="width: 55px; padding-right: 0px">ตำบล :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 150px"><dcs:validateDropDown
			name="subDistrictNo" dataSource="<%= subDistrictList %>"
			onChange="getCooperativeGroup('userRegionNo','userProvinceNo','districtNo','subDistrictNo','farmerGroupId')"
			property="subDistrictNo" keyField="subDistrictNo"
			displayField="thaiName" isRequire="true" style="width:150px;" /></li>
	</ul>
                    <div class="clearfix"></div>
	</div>
	<div class="line-input fixBottom" style="overflow: visible;">
	<ul>
		<li class="topic" style="width: 85px; padding-right: 0px">สังกัดสหกรณ์ :</li>
		<li class="topic" style="width: 1px; padding-right: 5px"><font color="red">*</font></li>
		<li class="boxinput" style="width: 395px">
		
			<!-- Add cooperative group list Multiselect by Thanaput.S 14/09/2017 -->
			<input type="hidden" id="userFarmerGroupId" name="userFarmerGroupId" value="" />
			<select id="farmerGroupId" name="farmerGroupId" multiple="multiple" placeholder="กรุณาเลือก">
			<% if(cooperativeGroupList != null && cooperativeGroupList.size() > 0 ) {
				for(int i = 0; i < cooperativeGroupList.size(); i++){
					FarmerGroup coop = (FarmerGroup)cooperativeGroupList.get(i);
			%>
				<option value="<%= coop.getFarmerGroupId() %>"><%= coop.getFarmerGroupName() %></option>
			<% } } %>
			</select>
			<!-- End Add cooperative group list Multi select by Thanaput.S 14/09/2017 -->
		
		</li>
	</ul>
                    <div class="clearfix"></div>
	</div>
                </fieldset>
                 
                <table align="center">
                	<tr>
                		<td colspan="2">&nbsp;</td>
                	</tr>
                    <tr>
                    	<td><a class="btn-save" onclick="saveUser();" id="write"></a></td>
                    	<td><a class="btn-cancel" onclick="closePage();"></a></td>
                    </tr>
                </table>
            
             <div class="clear"></div>
             <div class="clear"></div>
        </div>
    </div>
      </div>
      <!--footer -->
   <%@include file="/footer.jsp" %>
      <!-- end insert footer -->
    <!-- end insert >> content -->
</div>
</dcs:validateForm>
<form name="userListForm"  method="post" action="<%=request.getContextPath()%>/UserList.do">
<input type="hidden" name="userName" />
<input type="hidden" name="firstName" />
<input type="hidden" name="lastName" />
<input type="hidden" name="email" />
<input type="hidden" name="cmd" />
</form>

	<script language="JavaScript" src="<%=request.getContextPath()%>/js/jquery.sumoselect.js"></script>
	<script type="text/javascript">
	$(document).ready(function () {
		$('.SlectBox').SumoSelect({ csvDispCount: 2, selectAll: false, captionFormat: '{0} รายการ',search: true, searchText: 'ค้นหา' });
		$('#farmerGroupId').SumoSelect({ csvDispCount: 2, selectAll: true, captionFormatAllSelected:'ทั้งหมด', captionFormat: '{0} รายการ',locale :  ['OK', 'Cancel', 'ทั้งหมด'] });
	});
			   			 
			   			 $('#typeGroup').on('sumo:closed', function () {
			   			 	var obj = [], items = '';
			   			 	$('#typeGroup option:selected').each(function(i) {
			   			 		obj.push($(this).val());
			   			 	});
			   			 	for (var i = 0; i < obj.length; i++) {
			   			 		if(i>0) items += ',';
			   			 		items += obj[i];
			   			 	}
			   			 	if(items != '') {
			   			 		$('#userType').val(items);
			   			 	} else {
			   			 		$('#userType').val("");
			   			 	}
			   			 });
			   			 
			   			 $('#regionNo').on('sumo:closed', function () {
			   			 	var obj = [], items = '';
			   			 	$('#regionNo option:selected').each(function(i) {
			   			 		obj.push($(this).val());
			   			 	});
			   			 	for (var i = 0; i < obj.length; i++) {
			   			 		if(i>0) items += ',';
			   			 		items += obj[i];
			   			 	}
			   			 	if(items != '') {
			   			 		if($('#userRegionNo').val() != items){
			   			 			$('#userRegionNo').val(items);
			   			 			getProvinceInfo('userRegionNo','userProvinceNo','districtNo','subDistrictNo','farmerGroupId');
			   			 		}
			   			 	} else {
			   			 		$('#userRegionNo').val("");
			   			 	}
			   			 });
			   			 
			   			 $('#provinceNo').on('sumo:closed', function () {
			   			 	var obj = [], items = '';
			   			 	$('#provinceNo option:selected').each(function(i) {
			   			 		obj.push($(this).val());
			   			 	});
			   			 	for (var i = 0; i < obj.length; i++) {
			   			 		if(i>0) items += ',';
			   			 		items += obj[i];
			   			 	}
			   			 	if(items != '') {
			   			 		if($('#userProvinceNo').val() != items){
			   			 			$('#userProvinceNo').val(items);
			   			 			getDistrictInfo('userRegionNo','userProvinceNo','districtNo','subDistrictNo','farmerGroupId');
			   			 		}
			   			 	} else {
			   			 		$('#userProvinceNo').val("");
			   			 	}
			   			 });
			   			 
			   			 $('#farmerGroupId').on('sumo:closed', function () {
			   			 	var obj = [], items = '';
			   			 	$('#farmerGroupId option:selected').each(function(i) {
			   			 		obj.push($(this).val());
			   			 	});
			   			 	for (var i = 0; i < obj.length; i++) {
			   			 		if(i>0) items += ',';
			   			 		items += obj[i];
			   			 	}
			   			 	if(items != '') {
			   			 		$('#userFarmerGroupId').val(items);
			   			 	} else {
			   			 		$('#userFarmerGroupId').val("");
			   			 	}
			   			 });
	</script>
</body>
</html>
