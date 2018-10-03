<!doctype html>
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.wsnweb.form.ProvinceForm"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

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

<title><%=Utility.get("WEB_TITLE")%></title>

<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);

	List regionList = (ArrayList) session.getAttribute ("regionList");
	ProvinceForm provinceForm = (ProvinceForm)request.getAttribute("ProvinceForm");
	String thaiName = "",engName = "", cmd = "", msg = "";
	long provinceNo = 0, regionNo = 0;
	
	if(provinceForm !=null){
		msg = provinceForm.getMsg()==null?"":provinceForm.getMsg();
		cmd = provinceForm.getCmd()==null?"":provinceForm.getCmd();
		thaiName = provinceForm.getThaiName()==null?"":provinceForm.getThaiName();
		engName = provinceForm.getEngName()==null?"":provinceForm.getEngName();
		if(!"".equals(provinceForm.getRegionNo())){
			regionNo = Long.parseLong(provinceForm.getRegionNo()==null?"0":provinceForm.getRegionNo());
		}
		if(!"".equals(provinceForm.getProvinceNo()))
			provinceNo = Long.parseLong(provinceForm.getProvinceNo()==null?"0":provinceForm.getProvinceNo());
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
      	     	//$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
 	 });
  }); 


function loadData(){
 		var command = "<%=cmd%>";
		if(command != "" && <%=provinceNo>0%>){
			document.provinceForm.provinceNo.value = "<%= provinceNo %>";
		}
		document.provinceForm.regionNo.value = "<%= regionNo %>";
    	document.provinceForm.engName.value  = "<%= engName %>";
    	document.provinceForm.thaiName.value = "<%= thaiName %>";
    	document.provinceForm.prevRegionNo.value = "<%= regionNo %>";
   	    showErrorMessage(); 	
   	    loadMenu("M05");	
}
function saveProvince(){
		document.provinceForm.thaiName.value = document.provinceForm.thaiName.value.trim();
		document.provinceForm.engName.value = document.provinceForm.engName.value.trim();
		if(document.provinceForm.thaiName.value =='' && document.provinceForm.engName.value =='' && document.provinceForm.regionNo.value ==0){
			alert("กรุณากรอกข้อมูล");	
		 	document.provinceForm.thaiName.focus();
		 	return false; 
		}
		if(document.provinceForm.thaiName.value ==''){
		 	alert("กรุณาใส่ชื่อจังหวัด เป็นภาษาไทย");	
		 	document.provinceForm.thaiName.focus();
		 	return false; 	
		}
		if(document.provinceForm.engName.value ==''){
		 	alert("กรุณาใส่ชื่อจังหวัด เป็นภาษาอังกฤษ");	
		 	document.provinceForm.engName.focus();
		 	return false; 	
		}
		if(document.provinceForm.regionNo.value ==0){
		 	alert("กรุณาเลือกภูมิภาค");	
		 	return false; 	
		}
		if(document.provinceForm.provinceName.value == document.provinceForm.thaiName.value){
			alert("ไม่สามารถบันทึกได้ เนื่องจากมีจังหวัด" + document.provinceForm.thaiName.value + "อยู่แล้วในระบบ!");
			window.setTimeout(function () {document.provinceForm.thaiName.focus();}, 0);
			return false;	
		}
		
	    document.provinceForm.cmd.value="Save";
	    document.provinceForm.action= "<%=request.getContextPath()%>/Province.do";
	    document.forms["provinceForm"].submit();
}

function showErrorMessage(){
	<% if(!"".equals(msg)){ %> 
   		   alert("<%= msg %>");
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
    <% }} %>
}

function closePage(){ 
		document.provinceListForm.provinceName.value = ""; 
		document.provinceListForm.regionNo.value = "0"; 
   		document.provinceListForm.cmd.value = "Search"; 
   		document.provinceListForm.action="<%=request.getContextPath()%>/ProvinceList.do";
		document.forms["provinceListForm"].submit();
}

    function checkThai(str)    {
        var RE = /^[ก-์]+$/;
        if(RE.test(str.value)){
        	checkProvince();
        }else{
            alert("กรุณาใส่ชื่อจังหวัด เป็นภาษาไทย");
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            document.getElementById(str.id).focus();
            }, 0);
        }
    }

    function checkEng(str)    {
        var RE = /^[A-Za-z][A-Za-z ]*$/;
        if(RE.test(str.value)){
        }else{
            alert("กรุณาใส่ชื่อจังหวัด เป็นภาษาอังกฤษ");
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            document.getElementById(str.id).focus();
            }, 0);
        }
    }

function checkProvince(){
	var reqParameters = new Object();
	reqParameters.cmd = "GetProvinceName";
	document.provinceForm.thaiName.value = document.provinceForm.thaiName.value.trim();
	reqParameters.thaiName = document.provinceForm.thaiName.value;
	reqParameters.regionNo = document.provinceForm.regionNo.value;
	reqParameters.provinceNo = document.provinceForm.provinceNo.value;
	new Ajax.Request("<%=request.getContextPath()%>/Province.do",
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
			    alert("มีจังหวัด" + json[0].thaiName + "อยู่แล้วในระบบ!!");
				document.provinceForm.provinceName.value = json[0].thaiName;
				window.setTimeout(function () {document.provinceForm.thaiName.focus();}, 0);
			}
		},onFailure: function() {	
			//alert('เกิดข้อผิดพลาด');
		}
	});
}

</script>
</head>
<body onload="loadData();">
	<dcs:validateForm formName="provinceForm" formAction="Province.do" formBean="ProvinceForm">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="provinceNo" />
	<input type="hidden" name="provinceName" />
	<div class="main-inside">
		<!-- insert header -->

		<%@include file="/header.jsp" %>
		<div class="navigator">
			<div class="inside">
				<ul>
					
					<li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/ProvinceList.do'>การจัดการจังหวัด</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li>กรอกข้อมูล</li>
				</ul>
			</div>
		</div>	
		
		<!-- insert header -->
		<!-- start insert >> content -->
		<div class="content">
			<div class="inside">
				
				<div class="content-keyin">
					<div class="search-header" >
						<!-- <p>ข้อมูลจังหวัด</p> -->
						&nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลจังหวัด</font></span>
					</div>
					
					<div class="line-input-keyin">
						<ul>
							<li class="topic" style="width: 380px;padding-right:0px">ชื่อจังหวัด (ภาษาไทย) :</li>
							<li class="topic" style="width: 1px;padding-right:5px;"><font color="red">*</font></li>
							<li class="boxinput" >
								<dcs:validateText name="thaiName" property="thaiName" maxlength="200" style="width:190px;" onChange="checkThai(this);"/>
							</li>
						</ul>
						<ul>
							<li class="topic" style="width: 380px;padding-right:0px">ชื่อจังหวัด (ภาษาอังกฤษ) :</li>
							<li class="topic" style="width: 1px;padding-right:5px;"><font color="red">*</font></li>
							<li class="boxinput" >
								<dcs:validateText name="engName" property="engName" maxlength="200" style="width:190px;" onChange="checkEng(this);"/>
							</li>
						</ul>
						<ul>
							<li class="topic" style="width: 380px;padding-right:0px">ภูมิภาค :</li>
							<li class="topic" style="width: 1px;padding-right:5px;"><font color="red">*</font></li>
							<li class="boxinput">
								<dcs:validateDropDown name="regionNo" property="regionNo" dataSource="<%= regionList %>" keyField="regionNo" displayField="regionName" isRequire="true" style="width:197px;" />
							</li>
						</ul>
					</div>
				</div>
				<div class="clear"></div>
				
				<table align="center">
					
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr><td><a class="btn-save" href="#" onclick="saveProvince();" id="write"></a></td>	
						<td><a class="btn-cancel" href="#" onclick="closePage();"></a></td>			           
					</tr>
				</table>
				<div class="clear"></div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<!--footer -->
	<%@include file="/footer.jsp" %>
<input type="hidden" name="prevRegionNo" />
</dcs:validateForm>


<form name="provinceListForm"  method="post" action="<%=request.getContextPath()%>/ProvinceList.do">
	<input type="hidden" name="provinceName" />
	<input type="hidden" name="regionNo" />
	<input type="hidden" name="cmd" />
</form>

</body>
</html>