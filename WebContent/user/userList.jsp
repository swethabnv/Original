<!doctype html>

<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>

<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.User"%>
<%@page import="com.wsndata.data.Branch"%>
<%@page import="com.wsnweb.form.UserListForm"%>

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

	UserListForm userListForm = (UserListForm) request.getAttribute("UserListForm");
	User userLogin = (User) session.getAttribute("userLogin");
	List userList = (ArrayList) session.getAttribute("userList"); 
	List branchList = (ArrayList) session.getAttribute("userBranchList");
	List regionList = (ArrayList) session.getAttribute("regionList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
	String[] groupArray = Utility.get("USER_GROUP").split(",");
	String[] levelArray = Utility.get("USER_LEVEL").split(",");
	long regionNo = 0, districtNo = 0, subDistrictNo = 0, provinceNo = 0, userLevel = 0, level = 0, groupUser = 0;
	int userRoleId=0;
	String userName="", firstName="", lastName="", email="", msg ="", branchCode = "", login = ""; 
	char active;
	login = userLogin.getUserName();
	userLevel = userLogin.getLevel();
	if(userListForm!=null){
		msg = userListForm.getErrMessage();
		branchCode = userListForm.getBranchCode();
		if(!"".equals(userListForm.getRegionNo()))
			regionNo = Long.parseLong(userListForm.getRegionNo()==null?"0":userListForm.getRegionNo());
		if(!"".equals(userListForm.getProvinceNo()))
			provinceNo = Long.parseLong(userListForm.getProvinceNo()==null?"0":userListForm.getProvinceNo());
		if(!"".equals(userListForm.getDistrictNo()))
			districtNo = Long.parseLong(userListForm.getDistrictNo()==null?"0":userListForm.getDistrictNo());
		if(!"".equals(userListForm.getSubDistrictNo()))
			subDistrictNo = Long.parseLong(userListForm.getSubDistrictNo()==null?"0":userListForm.getSubDistrictNo());
		level = userListForm.getLevel();
		groupUser = userListForm.getGroupUser();
	}

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
      	     	$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
 	 });
 	 $("select option:empty").remove();
  }); 

function searchUser(){
	    document.userListForm.cmd.value="Search";
	    document.userListForm.action= "<%=request.getContextPath()%>/UserList.do";
	    document.forms["userFormList"].submit();
}

function goPage(cmd){
	if(cmd == '') {
		document.userListForm.cmd.value="Search";
		document.forms["userListForm"].submit();
	} else {
		var checkList = document.getElementsByName('delUserName');
		//alert(checkList.item());
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if(cmd == 'Active'){
			<% if(userLevel<5) { %>
				if (count > 0){
					if (confirm("คุณต้องการเปลี่ยนสถานะผู้ใช้งานหรือไม่")) {
						document.userListForm.target="";
						document.userListForm.cmd.value="Active";
						document.userListForm.action="UserList.do";
						document.forms["userListForm"].submit();
					}
				}else{
					alert('กรุณาเลือกผู้ใช้งานที่ต้องการเปลี่ยนสถานะ');
				}
			<% } else { %>
				alert("คุณไม่มีสิทธิ์ในการกระทำนี้");
			<% } %>
		} else if(cmd == 'Delete') {
			if (count > 0){
				if (confirm("คุณต้องการลบผู้ใช้งานหรือไม่")) {
					document.userListForm.target="";
					document.userListForm.cmd.value="Delete";
					document.userListForm.action="UserList.do";
					document.forms["userListForm"].submit();
				}
			}else{
				alert('กรุณาเลือกผู้ใช้งานที่ต้องการลบ');
			}
		}
	}
}

function resetPassword(userName){
	<% if(userLevel<5) { %>
 		if (confirm("คุณต้องการรีเซ็ตพาสเวิร์ดหรือไม่")) {
				document.userListForm.userName.value = userName; 
				document.userListForm.cmd.value = "Reset";
				document.userListForm.action= "<%=request.getContextPath()%>/UserList.do";
				document.forms["userListForm"].submit();
		}
		false;
	<% } else { %>
		alert("คุณไม่มีสิทธิ์ในการกระทำนี้");
	<% } %>
}
function accessControl(userName, firstName, lastName){
	<% if(userLevel<5) { %>
 		document.accessControlForm.firstName.value = firstName;
  		document.accessControlForm.lastName.value = lastName;
        document.accessControlForm.userName.value = userName;
        document.accessControlForm.cmd.value="Search";
	    document.accessControlForm.action="AccessControlList.do";
	    document.forms["accessControlForm"].submit();
	<% } else { %>
		alert("คุณไม่มีสิทธิ์ในการกระทำนี้");
	<% } %>
}


function loadEdit(userName,prefix,firstName,lastName,email,branchCode,status)
{	
	if(status=="Active") {
	document.userForm.userName.value = userName; 
	document.userForm.abbrPrefix.value = prefix; 
	document.userForm.firstName.value = firstName;
	document.userForm.lastName.value = lastName;
	document.userForm.email.value = email; 
	document.userForm.branch.value = branchCode;
	document.userForm.cmd.value = "Edit";
	document.userForm.action= "<%=request.getContextPath()%>/User.do";
	document.forms["userForm"].submit();
 	} else {
 		alert("กรุณา Active ผู้ใช้งาน !!");
 	}
}

function addUser(){

        document.userForm.cmd.value="New";
	    document.userForm.action= "<%=request.getContextPath()%>/User.do";
	    document.forms["userForm"].submit();

}

function loadData(){
    //removeOptionsBlank(document.userListForm.subDistrictNo);
    loadMenu("M01");
    showErrorMessage();
    <% if(branchCode !=null &&!"".equals(branchCode)){ %>
  	document.userListForm.branchCode.value = "<%=branchCode%>";
    <% } %>
	<%if(level>0){%>document.userListForm.level.value = "<%=level%>";
	changeLevel("<%=level%>");<% } %>
	<%if(groupUser>0){%>document.userListForm.groupUser.value = "<%=groupUser%>";<% } %>
	<%if(regionNo>=0){%>
	document.userListForm.regionNo.value = "<%=regionNo%>";
	getProvinceInfo('regionNo', 'provinceNo', 'districtNo', 'subDistrictNo');
	<%if(provinceNo>=0){%>
	document.userListForm.provinceNo.value = "<%=provinceNo%>";
	getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo');
	<%if(districtNo>0){%>
	document.userListForm.districtNo.value = "<%=districtNo%>";
	getSubDistrictInfo('provinceNo','districtNo','subDistrictNo');
	<%if(subDistrictNo>0){%>
	document.userListForm.subDistrictNo.value = "<%=subDistrictNo%>";
	<%}}}}%>
}

function removeOptionsBlank(sel) {
    var par;
    par = sel.options[0].parentNode;
    par.removeChild( sel.options[0] );
}

function changeLevel(level) {
	if(level>1 || level==0) {
		document.getElementById("regionNo").disabled = false;
	} else {
		document.getElementById("regionNo").value = "0";
		document.getElementById("regionNo").disabled = true;
	}
	if(level>2 || level==0) {
		document.getElementById("provinceNo").disabled = false;
	} else {
		document.getElementById("provinceNo").value = "0";
		document.getElementById("provinceNo").disabled = true;
	}
	if(level>3 || level==0) {
		document.getElementById("districtNo").disabled = false;
	} else {
		document.getElementById("districtNo").value = "0";
		document.getElementById("districtNo").disabled = true;
	}
	if(level>4 || level==0) {
		document.getElementById("subDistrictNo").disabled = false;
	} else {
		document.getElementById("subDistrictNo").value = "0";
		document.getElementById("subDistrictNo").disabled = true;
	}
}

function getProvinceInfo(objRName, objPName, objDName, objSName)
{
  var regionDD = document.getElementById(objRName);
  var provinceDD = document.getElementById(objPName);
  var districtDD = document.getElementById(objDName);
  var subDistrictDD = document.getElementById(objSName);

  districtDD.options.length = 1;
  districtDD.options[0].value="0";
  districtDD.options[0].text="กรุณาเลือก";
  districtDD.options[0].selected = true;
	
	subDistrictDD.options.length = 1;
	subDistrictDD.options[0].value="0";
	subDistrictDD.options[0].text="กรุณาเลือก";
	subDistrictDD.options[0].selected = true;

  if(regionDD.options[regionDD.selectedIndex].value=='0'){ //ถ้ายังไม่เลือกภูมิภาค ให้จังหวัดเป็นกรุณาเลือก
    provinceDD.options.length = 1;
    provinceDD.options[0].value="0";
    provinceDD.options[0].text="กรุณาเลือก";
    provinceDD.options[0].selected = true;
  }else{														//ถ้าเลือกภูมิภาคแล้ว เข้าหลังบ้าน
    var reqParameters = new Object();
    reqParameters.cmd = "GetProvince";
    reqParameters.regionNo = regionDD.options[regionDD.selectedIndex].value;   
    new Ajax.Request("<%=request.getContextPath()%>/UserList.do",
    {     
      method: 'post',
      asynchronous: false,
      parameters: reqParameters,
      encoding: 'UTF-8',
      onSuccess: function(transport)
      {                             
        var json = transport.responseText.evalJSON(); 
        if(json !=null && json != ""){
          provinceDD.options.length = json.length+1;
          provinceDD.options[0].value="0";
          provinceDD.options[0].text="กรุณาเลือก";
          provinceDD.options[0].selected = true;

          var r = 0;
          for(var i = 0; i < json.length; i++){           
            r=r+1;                              
            provinceDD.options[r].value=json[i].provinceNo;
            provinceDD.options[r].text=json[i].provinceThai;      
          }
        }
      },
      onFailure: function() {
        // alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
      }
    });
  }
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
		new Ajax.Request("<%=request.getContextPath()%>/UserList.do",
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
		new Ajax.Request("<%=request.getContextPath()%>/UserList.do",
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
        		} else {
        			subDistrictDD.options.length = 1;
        			subDistrictDD.options[0].value="0";
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
}
</script>

</head>

<body onload="loadData();">
<dcs:validateForm formName="userListForm" formAction="UserList.do" formBean="UserListForm">
<input type="hidden" name="cmd" value="DirtyList" />
<div class="main-inside">
	 <%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>การจัดการผู้ใช้งาน</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		           <li>ค้นหาข้อมูล</li>
		     	</ul>
		 	</div>
    	</div>	

	 <!-- start insert >> content -->
    <div class="content" style="height:auto;">
    	<div class="inside">
        
        	<div class="content-search">
                <div class="search-header">
                <!--  <p>ค้นหาผู้ใช้งาน</p> -->
                &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาผู้ใช้งาน</font></span>
              
                </div>
                <div class="line-input">
	                <ul>
	                	<li class="topic" style="width: 80px">ชื่อผู้ใช้งาน : </li>
	                	<li class="boxinput" style="width:150px">
	                		<dcs:validateText name="userName" property="userName" maxlength="20" style="width: 140px"/>
	                	</li>
	                	<li class="topic" style="width: 70px;">ชื่อ : </li>
	                	<li class="boxinput" style="width:150px">
	                		<dcs:validateText name="firstName" property="firstName" maxlength="30" style="width: 140px"/>
	                	</li>
	                	<li class="topic" style="width: 70px;">นามสกุล : </li>
	                	<li class="boxinput" style="width:150px">
	                		<dcs:validateText name="lastName" property="lastName" maxlength="30" style="width: 140px"/>
	                	</li>
	                	<li class="topic" style="width: 90px;">ระดับผู้ใช้งาน : </li>
	                	<li class="boxinput" style="width:150px">
                             <select id="level"  name="level" style="width:149px;" onchange="changeLevel(this.value);">
	                        		<option value="0">กรุณาเลือก</option>
		                    		<%if(levelArray !=null && levelArray.length>0){
		                    		int condition = levelArray.length;
		                    		//if(cmd.equals("Edit") && (level>1 && level<5)) { condition = (int)level+1; }
		                    			for(int i=((int)userLevel-1); i<condition; i++){
		                    		 %>
		                    		 	<option value="<%=i+1%>"><%= levelArray[i]%></option>
		                    		 <%}} %>
		               		 </select>
	                	</li>
                	</ul>
                </div>
                
                <div class="line-input">
                    <ul>
                    	<li class="topic" style="width: 80px">ภูมิภาค :</li>
                    	<li class="boxinput" style="width: 150px">
                        	<dcs:validateDropDown name="regionNo" dataSource="<%= regionList %>" property="regionNo" keyField="regionNo" displayField="regionName" onChange="getProvinceInfo('regionNo','provinceNo','districtNo','subDistrictNo')" style="width:149px;" /></li>
                    	<li class="topic" style="width: 70px">จังหวัด : </li>
                    	<li class="boxinput"  style="width:150px">
                        	<dcs:validateDropDown name="provinceNo" dataSource="<%= provinceList %>" property="provinceNo" keyField="provinceNo" displayField="thaiName" style="width:149px;" onChange="getDistrictInfo('provinceNo','districtNo','subDistrictNo')" />
                    	</li>
                    	<li class="topic"  style="width: 70px;">อำเภอ : </li>
                        <li class="boxinput" style="width:150px">
                        	<dcs:validateDropDown name="districtNo" dataSource="<%= districtList %>" property="districtNo" keyField="districtNo" displayField="thaiName" style="width:149px;" onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo')" />
	                     </li>
                    	<li class="topic"  style="width: 90px;">ตำบล : </li>
                        <li class="boxinput" style="width:150px">
                        	<dcs:validateDropDown name="subDistrictNo" dataSource="<%= subDistrictList %>" property="subDistrictNo" keyField="subDistrictNo"  displayField="thaiName" style="width:149px;" />
	                    </li>
                    </ul>
                </div>
                
                <div class="line-input">
                    <ul>
                        <li class="topic" style="width: 80px;padding-right:0px">กลุ่มผู้ใช้งาน :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"></li>
                        <li class="boxinput" style="width: 150px">
                             <select id="groupUser"  name="groupUser" style="width:149px;" >
	                        		<option value="0">กรุณาเลือก</option>
		                    		<%if(groupArray !=null && groupArray.length>0){
		                    			for(int j=0; j<groupArray.length; j++){
		                    		 %>
		                    		 	<option value="<%=j+1%>"><%= groupArray[j]%></option>
		                    		 <%}} %>
		               		 </select>
                        </li>
                    </ul>
              
                	<ul class="btn-search-box">
                    	<li style="padding-left: 572px">
                		<button class="btn-search" onclick="searchUser();" style="padding-bottom: 9px;"></button>
                    	</li>
                    </ul>
                </div>
                
            </div>
            <div class="clear"></div>
            <div class="btn-manage" style="margin-left:0px;">
            	   <a class="btn-add" onclick="addUser();" id="write" ></a>
            	   <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
                   <a class="btn-active" onclick="goPage('Active');" id="active"></a>
            </div>
            <div class="clear"></div>

            <table border="0" width="100%" ><tr>
            <%   if(userList!=null){ %>
            <td><dcs:grid dataSource="<%= userList %>" name="userList" pageSize="<%=userListForm.getDisplayRow()%>" width="100%">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif" />
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delUserName" dataField="userName" headerText="" width="22"  HAlign="center"  />
                 <dcs:textcolumn dataField="userName" headerText="ชื่อผู้ใช้งาน" width="90" sortable="true" HAlign="left"style="cursor:pointer;" cssClass="tel-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                 <dcs:textcolumn dataField="name" headerText="ชื่อ-นามสกุล" width="160" sortable="true" HAlign="left" style="cursor:pointer;" cssClass="address-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                 <dcs:textcolumn dataField="strLevel" headerText="ระดับผู้ใช้" width="80" sortable="true" HAlign="left" style="cursor:pointer;" cssClass="address-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                 <dcs:textcolumn dataField="region" headerText="ภูมิภาค" width="100" sortable="true" HAlign="left" style="cursor:pointer;" cssClass="address-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                 <dcs:textcolumn dataField="province" headerText="จังหวัด" width="100" sortable="true" HAlign="left" style="cursor:pointer;" cssClass="address-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                 <dcs:textcolumn dataField="district" headerText="อำเภอ" width="100" sortable="true" HAlign="left" style="cursor:pointer;" cssClass="address-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                 <dcs:textcolumn dataField="subDistrict" headerText="ตำบล" width="100" sortable="true" HAlign="left" style="cursor:pointer;" cssClass="address-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                 <!--dcs:textcolumn dataField="email" headerText="Email" width="140" sortable="true" HAlign="left" style="cursor:pointer;"cssClass="tel-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{branchName}');"/>-->
                 <!--dcs:textcolumn dataField="branchName" headerText="สาขา" width="180" sortable="true" HAlign="left" style="cursor:pointer;"cssClass="tel-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{branchName}');"/>-->
                 <dcs:textcolumn dataField="status" headerText="สถานะ" width="60" sortable="true" HAlign="left"style="cursor:pointer;" cssClass="age-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                 <dcs:imagecolumn dataField="linkImageReset"  headerText="" toolTip="Reset Password" width="22"  style="cursor:pointer;" HAlign="left" cssClass="manage-sr" onClick="resetPassword('{userName}');"/>
                 <dcs:imagecolumn dataField="linkImageAccess"  headerText="" toolTip="Access Control" width="22" style="cursor:pointer;" HAlign="left" cssClass="manage-sr" onClick="accessControl('{userName}','{firstName}','{lastName}');"/>
                 <dcs:imagecolumn dataField="linkImageEdit" headerText="" toolTip="แก้ไข" width="22" style="cursor:pointer;" HAlign="left" cssClass="manage-sr" onClick="loadEdit('{userName}','{abbrPrefix}','{firstName}','{lastName}','{email}','{branchCode}','{status}');"/>
                
                 </dcs:grid>
            </td>
            <% } %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countUser"))%> รายการ</div>
            <!-- edit by jane -->
            <div style="clear:both;"></div>
        </div>
    </div>
    
   <%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>

<!--  -->
<form name="userForm" method="post" action="<%=request.getContextPath()%>/User.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="userName" />
<input type="hidden" name="abbrPrefix" />
<input type="hidden" name="firstName" />
<input type="hidden" name="lastName" />
<input type="hidden" name="userLevel" />
<input type="hidden" name="province" />
<input type="hidden" name="district" />
<input type="hidden" name="subDistrict" />
<input type="hidden" name="email" />
<input type="hidden" name="branch" />
</form>

<!-- authorize -->
<form name="accessControlForm" method="post" action="<%=request.getContextPath()%>/AccessControlList.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="userName" />
<input type="hidden" name="firstName" />
<input type="hidden" name="lastName" />
</form>


</body>
</html>