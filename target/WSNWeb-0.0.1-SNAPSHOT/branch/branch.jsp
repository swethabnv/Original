<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BranchForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.dbaccess.RegionHome"%>
<%@page import="com.wsndata.dbaccess.ProvinceHome"%>
<%@page import="com.wsndata.data.Province"%>
<%@page import="com.wsndata.data.District"%>
<%@page import="com.wsndata.data.SubDistrict"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1">
<meta name="viewport" content="width=1280,user-scalable=yes">
<title><%=Utility.get("WEB_TITLE")%></title>
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

<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);

	BranchForm branchForm = (BranchForm) request.getAttribute("BranchForm");
	String msg = "",cmd="",moo="";
	long branchCode = 0,provinceNo = 0,districtNo =0,subDistrictNo = 0,pbranchCode = 0;
	if(branchForm!=null){
	if(branchForm.getMsg() !=null && !"".equals(branchForm.getMsg())){
			msg = branchForm.getMsg();
		}
		cmd = branchForm.getCmd();
		branchCode = branchForm.getBranchCode();
		pbranchCode = branchForm.getPbranchCode();
		provinceNo = branchForm.getProvinceNo();
		districtNo = branchForm.getDistrictNo();
		subDistrictNo = branchForm.getSubDistrictNo();
		moo = branchForm.getMoo()==0?"":String.valueOf(branchForm.getMoo());
	}
	List regionList = (ArrayList) session.getAttribute("branchRegionList");
	List provinceList = (ArrayList) session.getAttribute("branchProvinceList");
	List districtList = (ArrayList) session.getAttribute("branchDistrictList");
	List subDistrictList = (ArrayList) session.getAttribute("branchSubDistrictList");
	
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
    document.branchForm.postCode.value = "";
    document.branchForm.branchName.focus();
	document.branchForm.moo.value = "<%=moo%>";
    <%if (cmd !=null && !"".equals(cmd)) {
		if(provinceNo>0){%>		
			document.branchForm.branchCode.value = "<%=branchCode%>";
			document.branchForm.provinceNo.value = "<%=provinceNo%>";
			getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');
			document.branchForm.districtNo.value = "<%=districtNo%>";
			getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
			document.branchForm.subDistrictNo.value = "<%=subDistrictNo%>";
			getPostCodeInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
	<% } %>
	<% } %>
	loadMenu("M08");
}
function saveBranch(){
 	var saveIs = true;
		if(document.branchForm.branchName.value == ""){
		   alert("กรุณาใส่ชื่อสาขา !!");
		   document.branchForm.branchName.focus();
		   saveIs = false;
		}else if(document.branchForm.address.value == 0){
		   alert("กรุณาใส่ที่อยู่ !!");
		   saveIs = false;
		}else if(document.branchForm.provinceNo.value == 0){
		   alert("กรุณาเลือกจังหวัด !!");
		   saveIs = false;
		}else if(document.branchForm.districtNo.value == 0){
		   alert("กรุณาเลือกอำเภอ !!");
		   saveIs = false;
		}else if(document.branchForm.subDistrictNo.value == 0){
		   alert("กรุณาเลือกตำบล !!");
		   saveIs = false;
		}
		if(saveIs){
	    	document.branchForm.cmd.value="Save";
	    	document.branchForm.action= "<%=request.getContextPath()%>/Branch.do";
	    	document.forms["branchForm"].submit();
	    }
}
function checkMoo(str)    {
    var RE = /^\d+$/;
    if(RE.test(str.value)){
    }else{
    	alert("กรุณาใส่ค่าที่เป็นตัวเลข");
    	document.farmerForm.moo.value="";
    	document.farmerForm.moo.focus();
    }
}
    
function checkTel(str) {
    var num = 0;
    var arry = (str.value).split(",");
    
    for(var i=0;i<arry.length;i++) {
    var patt = /^[0][1-9]([0-9]{7,8}((-|ต่อ| ต่อ| ต่อ )[0-9]{1,4})?)$/;
    var res = patt.test(arry[i]);
        if(res) { num++; }
    }
    if(num!=arry.length) {
    	alert("กรุณาใส่ค่าที่เป็นตัวเลข");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    }
}
    
function checkFax(str) {
    var num = 0;
    var arry = (str.value).split(",");
    
    for(var i=0;i<arry.length;i++) {
    var patt = /^[0][1-9]([0-9]{7,8})$/;
    var res = patt.test(arry[i]);
        if(res) { num++; }
    }
    if(num!=arry.length) {
    	alert("กรุณาใส่ค่าที่เป็นตัวเลข");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    }
}

function closePage(){
   			document.branchListForm.branchName.value = "";
   			document.branchListForm.cmd.value = "Search"; 
   			document.branchListForm.action="<%=request.getContextPath()%>/BranchList.do";
			document.forms["branchListForm"].submit();
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
			var postCodeTxt = document.getElementById(objPostCode);postCodeTxt.value = "";
		}

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
		}else{
			var reqParameters = new Object();
			reqParameters.cmd = "GetDistrict";
			reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;  
			new Ajax.Request("<%=request.getContextPath()%>/Branch.do",
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

		if(districtDD.options[districtDD.selectedIndex].value == ''){
			subDistrictDD.options.length = 1;
			subDistrictDD.options[0].value="";
			subDistrictDD.options[0].text="กรุณาเลือก";
			subDistrictDD.options[0].selected = true;
		}else{
			var reqParameters = new Object();
			reqParameters.cmd = "GetSubDistrict";
			reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
			reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;    
			new Ajax.Request("<%=request.getContextPath()%>/Branch.do",
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
		new Ajax.Request("<%=request.getContextPath()%>/Branch.do",
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
<dcs:validateForm formName="branchForm" formAction="Branch.do" formBean="BranchForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="branchCode" />
<div class="main-inside">

	<%@include file="/header.jsp" %>
    <div class="navigator">
    	<div class="inside">
	 		<ul>
	            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
	            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	            <li><a style="color:#333" href='<%=request.getContextPath()%>/BranchList.do'>การจัดการสาขาธนาคาร</a></li>
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
                <!-- <p>ข้อมูลสาขา</p> -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลสาขา</font></span>
                </div>
                <div class="line-input-keyin">
                    <ul>
	                    <li class="topic" style="width: 120px;padding-right:0px">ชื่อสาขา :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
	                    <li class="boxinput" style="width: 360px">
	                    	<dcs:validateText name="branchName" property="branchName" maxlength="200" style="width:360px;"/>
	                   	</li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                    <ul>
	                    <li class="topic" style="width: 120px;padding-right:0px">สถานที่ตั้ง ที่อยู่ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
	                    <li class="boxinput" style="width:51px">
	                    	<dcs:validateText  name="address" property="address" style="width:50px;" maxlength="50"/>
	                    </li>
	                    <li class="topic" style="width: 40px;padding-right:0px">หมู่ที่ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"></li>
	                    <li class="boxinput" style="width:30px">
	                    	<dcs:validateText  name="moo" property="moo" style="width:30px;" maxlength="2" onChange="checkMoo(this)"/>
	                    </li>
	                    <li class="topic" style="width: 60px;padding-right:0px">ซอย :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"></li>
	                    <li class="boxinput" style="width:140px">
	                    	<dcs:validateText  name="soi" property="soi" style="width:140px;" maxlength="30"/>
	                    </li>
	                    <li class="topic" style="width: 90px;padding-right:0px">ถนน :</li>
                  		<li class="topic" style="width: 0px;padding-right:5px"></li>
	                    <li class="boxinput" style="width:350px">
	                    	<dcs:validateText  name="street" property="street" style="width:350px;" maxlength="30"/>
	                    </li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                  <ul>
                  		<li class="topic"style="width:120px;padding-right:0px">จังหวัด :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput"style="width:150px">
								<select id="provinceNo"  name="provinceNo" style="width:150px;" onChange="getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');">
		                    		<option value="">กรุณาเลือก</option>
		                    		<%if(provinceList !=null && provinceList.size()>0){
		                    			for(int p=0; p<provinceList.size(); p++){
		                    				Province pd = (Province)provinceList.get(p);
		                    		 %>
		                    		 	<option value="<%=pd.getProvinceNo()%>"><%=pd.getThaiName()%></option>
		                    		 <%}} %>
		                    	</select>

                        	</li>
                        	<li class="topic"style="width:50px;padding-right:0px">อำเภอ :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput"style="width:149px">
						    <dcs:validateDropDown name="districtNo" dataSource="<%= districtList %>" property="districtNo" keyField="districtNo"  displayField="thaiName" style="width:149px;" onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode')" isRequire="true"/>
						    </li>
                        	<li class="topic"style="width:80px;padding-right:0px">ตำบล :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
		                    <li class="boxinput"style="width:150px">
		                    <dcs:validateDropDown name="subDistrictNo" dataSource="<%= subDistrictList %>" property="subDistrictNo" keyField="subDistrictNo"  displayField="thaiName"  isRequire="true" onChange="getPostCodeInfo('provinceNo','districtNo','subDistrictNo','postCode')" style="width:150px;"/>	
						    </li>
	                        <li class="topic" style="width: 100px;padding-right:0px">รหัสไปรษณีย์ :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
	                        <li class="boxinput" style="width: 80px;">
	                        	<dcs:validateText name="postCode" property="postCode" maxlength="5" style="width:80px" isReadOnly="true"/>
	                        </li>
                  </ul>
                  </div>
                 <div class="line-input-keyin">
                    <ul>
	                        <li class="topic" style="width: 120px;padding-right:0px">เบอร์โทร :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
	                        <li class="boxinput" style="width: 140px;">
	                        	<dcs:validateText name="tel" property="tel" maxlength="30" style="width:140px;" onChange="checkTel(this)"/>
	                        </li>
                        
	                    	<li class="topic" style="width: 60px;padding-right:0px">แฟกซ์ :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
	                    	<li class="boxinput " style="width: 140px;">
	                    		<dcs:validateText name="fax" property="fax"  maxlength="30" style="width:140px;" onChange="checkFax(this)"/>
	                    	</li> 
	                    	
	                        <li class="topic" style="width: 90px;padding-right:0px">ผู้จัดการสาขา :</li>
                  			<li class="topic" style="width: 1px;padding-right:5px"></li>
	                        <li class="boxinput" style="width: 350px;">
	                        	<dcs:validateText name="manager" property="manager" maxlength="50" style="width:350px;"/>
	                        </li>
	                </ul>
	            </div>
                <div class="clear"></div>
                
           			<table align="center">
			           <tr><td colspan="2">&nbsp;</td></tr>
			           <tr>
				           <td><a class="btn-save" onclick="saveBranch();" id="write"></a></td>	
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
</div>
</dcs:validateForm>
<form name="branchListForm"  method="post" action="<%=request.getContextPath()%>/BranchList.do">
<input type="hidden" name="branchName" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>