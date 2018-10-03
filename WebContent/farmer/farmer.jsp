<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.FarmerForm"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.Prefix"%>
<%@page import="com.wsndata.data.Province"%>
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
    FarmerForm farmerForm = (FarmerForm)request.getAttribute("FarmerForm");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List prefixList = (ArrayList) session.getAttribute("prefixList");
	List branchList = (ArrayList) session.getAttribute("userBranchList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
	String msg = "",idCard = "",cmd = "",abbrPrefix ="",effectiveDate ="";
	String addressNo ="", moo="";
	long provinceNo = 0,districtNo =0,subDistrictNo = 0,branchCode = 0;
	if(farmerForm!=null){
	if(farmerForm.getMsg() !=null && !"".equals(farmerForm.getMsg())){
			msg = farmerForm.getMsg();
		}
		if(farmerForm.getCmd() !=null && !"".equals(farmerForm.getCmd())){
			cmd = farmerForm.getCmd();
		}
		
		if(cmd !=null && !"New".equals(cmd)){
			idCard = farmerForm.getIdCard();
			abbrPrefix = farmerForm.getAbbrPrefix();
			addressNo = farmerForm.getAddressNo();
			moo = farmerForm.getMoo() == 0?"":String.valueOf(farmerForm.getMoo());
			provinceNo = farmerForm.getProvinceNo();
			districtNo = farmerForm.getDistrictNo();
			subDistrictNo = farmerForm.getSubDistrictNo();
			effectiveDate = farmerForm.getEffectiveDate();
			branchCode = farmerForm.getBranchCode();
		}
	}

%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/CalendarStyle.css" />

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
      	     	//$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
 	 });
  }); 
 
 
function loadData(){
    showErrorMessage();
    <%if (cmd !=null && !"".equals(cmd)) {%>
    	<%if("New".equals(cmd)){%>
    		document.farmerForm.branchCode.value = "1";
    		document.farmerForm.idCard.readOnly = "";
    		document.farmerForm.moo.value = "";
    		document.farmerForm.postCode.value = "";
			document.farmerForm.abbrPrefix.value = "";
    		window.setTimeout(function () {document.farmerForm.idCard.focus();}, 0);
    	<%} if("Edit".equals(cmd)){%>
    		document.farmerForm.branchCode.value = "<%=branchCode%>";
			document.farmerForm.idCard.readOnly = "readonly";
    		document.farmerForm.effectiveDate.value = "<%=effectiveDate%>";
			document.farmerForm.idCard.value = "<%=idCard%>";
			document.farmerForm.abbrPrefix.value = "<%=abbrPrefix%>";
			document.farmerForm.addressNo.value = "<%=addressNo%>";
			document.farmerForm.moo.value = "<%=moo%>";
			<%if(provinceNo>0){%>
			document.farmerForm.provinceNo.value = "<%=provinceNo%>";
			getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');
			<%}if(districtNo>0){%>
			document.farmerForm.districtNo.value = "<%=districtNo%>";
			getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
			<%}if(subDistrictNo>0){%>
			document.farmerForm.subDistrictNo.value = "<%=subDistrictNo%>";
			getPostCodeInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
			<%}%>
    	<%}%>
	<%}%>
    loadMenu("M20");	
}
function saveFarmer(){
	    var saveIs = true;
		if(document.farmerForm.idCard.value == ''){
		 	alert("กรุณาใส่เลขที่บัตรประชาชน ");
		 	document.farmerForm.idCard.focus();
		 	return false;
		} else {
			checkIdCard(document.farmerForm.idCard);
		}
    	if(document.farmerForm.abbrPrefix.value == ''){
		 	alert("กรุณาใส่คำนำหน้า ");
		 	return false;
		}
    	if(document.farmerForm.firstName.value == ''){
		 	alert("กรุณาใส่ชื่อ");
		 	document.farmerForm.firstName.focus();
		 	return false;
		}
		if(document.farmerForm.lastName.value == ''){
		 	alert("กรุณาใส่นามสกุล");
		 	document.farmerForm.lastName.focus();
		 	return false;
		}
		if(document.farmerForm.addressNo.value == ''){
		 	alert("กรุณาใส่ที่อยู่");
		 	document.farmerForm.addressNo.focus();
		 	return false;
		}
		if(document.farmerForm.provinceNo.value == '0'){
		 	alert("กรุณาใส่ที่อยู่จังหวัด");
		 	return false;
		}
    	if(document.farmerForm.districtNo.value == '0'){
		 	alert("กรุณาใส่ที่อยู่อำเภอ");
		 	return false;
		}
		if(document.farmerForm.subDistrictNo.value == '0'){
		 	alert("กรุณาใส่ที่อยู่ตำบล");
		 	return false;
		}
		
	if(saveIs){
	    document.farmerForm.cmd.value="Save";
	    document.farmerForm.action= "<%=request.getContextPath()%>/Farmer.do";
	    document.forms["farmerForm"].submit();
	}
}

function checkIdCard(id)
{
	var RE = /^\d+$/;
	var txt = "";
	len = id.value.length;
	    if(RE.test(id.value)){
	    	if(len == 13){
	    		var idCard = id.value;
	    		var total = 0;
	    		var mul = 13;
	    		for(i=0;i<len-1;i++) {
	    			total = total + idCard.charAt(i) * mul;
	    			mul = mul -1;
	    		}
	    		mod = 11 - (total % 11);
	    		mod2 = mod % 10;
	    		
	    		if(mod2 != idCard.charAt(12)) {
	    			alert("รหัสบัตรประจำตัวประชาชนไม่ถูกต้อง");
	    			window.setTimeout(function () {id.focus();}, 0);
	    			return false;
	    		} else {
	    			return true;
	    		}
		    } else {
				alert("กรุณาใส่รหัสบัตรประจำตัวประชาชนเป็นตัวเลข 13 หลัก");
				window.setTimeout(function () {id.focus();}, 0);
			}
		} else {
			id.value="";
			alert("กรุณาใส่รหัสบัตรประจำตัวประชาชนเป็นตัวเลข 13 หลัก");
			window.setTimeout(function () {id.focus();}, 0);
		}
		return false;
}

function checkMoo(str)    {
    var RE = /^\d+$/;
    if(str.value != "") {
    	if(!RE.test(str.value)){
    		alert("กรุณาใส่ค่าที่เป็นตัวเลข");
    		document.farmerForm.moo.value="";
    		window.setTimeout(function () {document.farmerForm.moo.focus();}, 0);
    	}
    }
}
    
function checkMobile(str) {
    var num = 0;
    
    var patt = /^[0][1-9]([0-9]{7,8})$/;
    var res = patt.test(str.value);
    
    if(!res) {
    	alert("กรุณาใส่เบอร์โทรศัพท์มือถือให้ถูกต้อง");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    }
}
    
function checkTel(str) {
    var num = 0;
    
    var patt = /^[0][1-9]([0-9]{7})$/;
    var res = patt.test(str.value);
    
    if(!res) {
    	alert("กรุณาใส่เบอร์โทรศัพท์บ้านให้ถูกต้อง");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    }
}

function closePage(){
   			document.farmerListForm.farmerName.value = "";
   			document.farmerListForm.cmd.value = "Search"; 
   			document.farmerListForm.action="<%=request.getContextPath()%>/FarmerList.do";
			document.forms["farmerListForm"].submit();
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
            if(objPostCode != ''){
                  var postCodeTxt = document.getElementById(objPostCode);
                  postCodeTxt.value = "";
            }

          var subDistrictDD = document.getElementById(objSName);
          subDistrictDD.options.length=1;
          subDistrictDD.options[0].value="0";
          subDistrictDD.options[0].text="กรุณาเลือก";
          subDistrictDD.options[0].selected = true;
            
            var provinceDD = document.getElementById(objPName);
            var districtDD = document.getElementById(objDName);
            
            if(provinceDD.options[provinceDD.selectedIndex].value=='0'){
                  districtDD.options.length = 1;
                  districtDD.options[0].value="0";
                  districtDD.options[0].text="กรุณาเลือก";
                  districtDD.options[0].selected = true;
            }else{
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetDistrict";
                  reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;   
                  new Ajax.Request("<%=request.getContextPath()%>/Farmer.do",
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
                            //  alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
                        }
                  });
            }
}
	
function getSubDistrictInfo(objPName, objDName, objSName, objPostCode)
{
            if(objPostCode != ''){
                  var postCodeTxt = document.getElementById(objPostCode);
                  postCodeTxt.value = "";
            }

            var provinceDD = document.getElementById(objPName);
            var districtDD = document.getElementById(objDName);
            var subDistrictDD = document.getElementById(objSName);
            
            if(districtDD.options[districtDD.selectedIndex].value == '0'){
                  subDistrictDD.options.length = 1;
                  subDistrictDD.options[0].value="0";
                  subDistrictDD.options[0].text="กรุณาเลือก";
                  subDistrictDD.options[0].selected = true;
            }else{
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetSubDistrict";
                  reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
                  reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;     
                  new Ajax.Request("<%=request.getContextPath()%>/Farmer.do",
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
                           //   alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
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
		new Ajax.Request("<%=request.getContextPath()%>/Farmer.do",
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
<dcs:validateForm formName="farmerForm" formAction="Farmer.do" formBean="FarmerForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="branchCode" />
<input type="hidden" name="effectiveDate" />
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/FarmerList.do'>การจัดการเกษตรกร</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		           <li>กรอกข้อมูล</li>
		     	</ul>
		 	</div>
    	</div>	
    	
	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        
        	<div class="content-keyin">
                <div class="search-header" >
                 <!-- <p>ข้อมูลเกษตรกร</p> -->
	                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลเกษตรกร</font></span>
                </div>
                <div class="line-input-keyin">
                    	<ul>
		                    <li class="topic" style="width:140px;padding-right:0px">เลขที่บัตรประชาชน :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput" style="width:130px">
						   	 	<dcs:validateText name="idCard" property="idCard" maxlength="13" style="width:123px" onChange="checkIdCard(this)"/>
                        	</li>
                        	<li class="topic" style="width:80px;padding-right:0px">คำนำหน้า :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput" style="width:130px">
                        	<select id="abbrPrefix"  name="abbrPrefix" style="width:130px;" >
	                        	<option value="">กรุณาเลือก</option>
		         				<%if(prefixList !=null && prefixList.size()>0){
		                				for(int i=0; i<prefixList.size(); i++){
		                    				Prefix pref = (Prefix)prefixList.get(i);
		           				%>
		           					<option value="<%= pref.getAbbrPrefix() %>"><%= pref.getAbbrPrefix() %></option>
		            			<%}} %>
		     				</select>
                        	</li>
	                    	<li class="topic"style="width:60px;padding-right:0px">ชื่อ :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput"style="width:130px">
						    <dcs:validateText name="firstName" property="firstName" maxlength="30" style="width:123px"/>
                        	</li>
                        	<li class="topic" style="width:90px;padding-right:0px">นามสกุล :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput"style="width:120px">
						    <dcs:validateText name="lastName" property="lastName" maxlength="30" style="width:120px"/>
                        	</li>
                    	</ul>
                    </div>
    				<div class="line-input-keyin">
                    	<ul>
	                    	<li class="topic" style="width:140px;padding-right:0px">ที่อยู่ตามทะเบียนบ้าน :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput" style="width:50px">
						    <dcs:validateText name="addressNo" property="addressNo" maxlength="50" style="width:50px"/>
                        	</li>
	                    	<li class="topic"style="width:35px; padding-right:0px">หมู่ :</li>
		                    <li class="boxinput" style="width:36px">
						    <dcs:validateText name="moo" property="moo" maxlength="2" style="width:30px" onChange="checkMoo(this)"/>
                        	</li>
    						<li class="topic" style="width:80px;padding-right:0px">หมู่บ้าน :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
		                    <li class="boxinput"style="width:130px">
						    <dcs:validateText name="village" property="village" maxlength="200" style="width:122px"/>
		                    </li>
                        	<li class="topic"style="width:60px;padding-right:0px">ซอย :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
		                    <li class="boxinput" style="width:130px">
						    <dcs:validateText name="soi" property="soi" maxlength="200" style="width:123px"/>
		                   </li>
                        	<li class="topic"style="width:90px;padding-right:0px">ถนน :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
		                    <li class="boxinput" style="width:130px">
						    <dcs:validateText name="street" property="street" maxlength="200" style="width:120px"/>
		                   </li>
                    	</ul>
                    </div>
    				<div class="line-input-keyin">
                    	<ul>
    						<li class="topic" style="width:140px;padding-right:0px">จังหวัด :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput"style="width:130px">
		                    <dcs:validateDropDown name="provinceNo" dataSource="<%= provinceList %>" property="provinceNo" keyField="provinceNo"  displayField="thaiName" style="width:132px;" onChange="getDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode')" isRequire="true"/>
                        	</li>
                        	<li class="topic"style="width:80px;padding-right:0px">อำเภอ :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput" style="width:130px">
		                    <dcs:validateDropDown name="districtNo" dataSource="<%= districtList %>" property="districtNo" keyField="districtNo"  displayField="thaiName" style="width:130px;" onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode')" isRequire="true"/>
						   </li>
                        	<li class="topic"style="width:61px;padding-right:0px">ตำบล :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput" style="width:130px">
		                     <dcs:validateDropDown name="subDistrictNo" dataSource="<%= subDistrictList %>" property="subDistrictNo" keyField="subDistrictNo"  displayField="thaiName"  isRequire="true" onChange="getPostCodeInfo('provinceNo','districtNo','subDistrictNo','postCode')" style="width:130px;"/>
						    </li>
	                    	<li class="topic" style="width:90px;padding-right:0px">รหัสไปรษณีย์ :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
		                    <li class="boxinput" style="width:130px">
						    <dcs:validateText name="postCode" property="postCode" maxlength="5" style="width:120px" isReadOnly="true" />
                        	</li>	
                        </ul> 
    				</div>
    				<div class="line-input-keyin">
                    	<ul>
	                    	<li class="topic"style="width:140px;padding-right:0px">เบอร์มือถือ :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
		                    <li class="boxinput" style="width:130px">
						    <dcs:validateText name="mobile" property="mobile" maxlength="10" onChange="checkMobile(this)" style="width:123px"/>
                        	</li>
	                    	<li class="topic"style="width:80px;padding-right:0px">เบอร์บ้าน :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
		                    <li class="boxinput" style="width:130px">
						    <dcs:validateText name="tel" property="tel" maxlength="9" onChange="checkTel(this)" style="width:122px"/>
                        	</li>
                        </ul> 
    				 </div>
    			</div>

						<table align="center">
                             
                             <tr><td colspan="2">&nbsp;</td></tr>
                             <tr><td><a class="btn-save" onclick="saveFarmer();" id="write"></a></td>  
                                 <td><a class="btn-cancel" onclick="closePage();"></a></td>                         
                             </tr>
                  		</table>
		    </div>  
        </div>
    </div>     
<%@include file="/footer.jsp" %>
     
</dcs:validateForm>
<form name="farmerListForm"  method="post" action="<%=request.getContextPath()%>/FarmerList.do">
<input type="hidden" name="farmerName" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>
