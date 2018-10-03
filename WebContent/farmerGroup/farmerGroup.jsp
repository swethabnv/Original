<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.Province"%>
<%@page import="com.wsndata.data.FarmerGroupTeam"%>
<%@page import="com.wsnweb.form.FarmerGroupForm"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
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

<style type="text/css">
select { width: 129px !important; }
input { width: 120px !important; }
#farmerGroupName,#childFarmerGroupId,#objective { width: 365px !important; }
#addressNo { width: 52px !important; }
#moo { width: 20px !important; }
#teamPosition {  width: 340px !important; }
#provinceNo { width: 149px !important; }
#tel { width: 140px !important; }
#target { text-align: right; }
</style>
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	FarmerGroupForm farmerGroup = (FarmerGroupForm) request.getAttribute("FarmerGroupForm");
	List cooperativeList = (ArrayList) session.getAttribute("cooperativeList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
	List farmerGroupList = (ArrayList) session.getAttribute("farmerGroupMasterList");
	List farmerGroupTeamList = (ArrayList) session.getAttribute("farmerGroupTeamList");
	String msg = "",cmd = "", objective="";
	long farmerId = 0,provinceNo = 0,districtNo =0,subDistrictNo = 0,postCode = 0,moo = 0;
	double target=0;
	if(farmerGroup!=null){
		if(farmerGroup.getMsg() !=null && !"".equals(farmerGroup.getMsg())){
			msg = farmerGroup.getMsg();
		}
		cmd = farmerGroup.getCmd();
		farmerId = farmerGroup.getFarmerGroupId();
		provinceNo = farmerGroup.getProvinceNo();
		districtNo = farmerGroup.getDistrictNo();
		subDistrictNo = farmerGroup.getSubDistrictNo();
		moo = farmerGroup.getMoo();
		target = farmerGroup.getTarget();
		objective = farmerGroup.getObjective();
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
        document.farmerGroupForm.cmd.value = "<%= cmd %>";
        document.farmerGroupForm.moo.value = "";
    <% if(!"".equals(cmd)){ %>
    	<% if(farmerId>0) { %>document.farmerGroupForm.farmerGroupId.value = "<%= farmerId %>";<% } %>
    	<% if(provinceNo>0) { %>
            document.farmerGroupForm.provinceNo.value = "<%=provinceNo%>";
            getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');
        <% } %>
    	<% if(districtNo>0) { %>
            document.farmerGroupForm.districtNo.value = "<%=districtNo%>";
            getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
        <% } %>
    	<% if(subDistrictNo>0) { %>
            document.farmerGroupForm.subDistrictNo.value = "<%=subDistrictNo%>";
            getPostCodeInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
        <% } %>
        <% if("Edit".equals(cmd)){ %>
            //document.farmerGroupForm.farmerGroupName.readOnly = true;
            document.farmerGroupForm.target.value = "<%=target%>";
            document.farmerGroupForm.objective.value = "<%=objective%>";
            var tg = document.getElementById('target').value;
            document.farmerGroupForm.target.value = formatNumber(tg);
            <% if(moo>0) {%>
                document.farmerGroupForm.moo.value = <%= moo %>;
            <% } %>
        <%}%>
    <% } %>
        showErrorMessage(); 
        loadMenu("M10");
    }

    function saveFarmerGroup(){
        var saveIs = true;

        if(document.farmerGroupForm.farmerGroupName.value == "" ){
            alert("กรุณาใส่ชื่อกลุ่ม !!");
            window.setTimeout(function () {
            document.farmerGroupForm.farmerGroupName.focus();
            }, 0);
            saveIs = false;
        //}else if(document.farmerGroupForm.target.value == "" ){
        //    alert("กรุณาใส่จำนวนเป้าหมายของกลุ่ม !!");
        //    window.setTimeout(function () {
        //    document.farmerGroupForm.target.focus();
        //    }, 0);
        //    saveIs = false;
        //}else if(document.farmerGroupForm.addressNo.value == "" ){
        //    alert("กรุณาใส่สถานที่ตั้งของกลุ่ม !!");
        //    window.setTimeout(function () {
        //    document.farmerGroupForm.addressNo.focus();
        //    }, 0);
        //    saveIs = false;
        //}else if(document.farmerGroupForm.provinceNo.value == 0){
        //    alert("กรุณาเลือกจังหวัด !!");
        //    saveIs = false;
        //}else if(document.farmerGroupForm.districtNo.value == 0){
        //    alert("กรุณาเลือกอำเภอ !!");
        //    saveIs = false;
        //}else if(document.farmerGroupForm.subDistrictNo.value == 0){
        //    alert("กรุณาเลือกตำบล !!");
        //    saveIs = false;
        }
        if(saveIs){
        	document.farmerGroupForm.farmerGroupName.value = document.farmerGroupForm.farmerGroupName.value.trim();
            var target = document.getElementById('target');
            document.farmerGroupForm.target.value = target.value.replace(/,/g , "");

            document.farmerGroupForm.cmd.value="Save";
            document.farmerGroupForm.action= "<%=request.getContextPath()%>/FarmerGroup.do";
            document.forms["farmerGroupForm"].submit();
        }
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
    
function checkTel(str) {
    var num = 0;
    var arry = (str.value).split(",");
    var patt = /^[0][1-9]([0-9]{7,8}((-|ต่อ| ต่อ| ต่อ )[0-9]{1,4})?)$/;
    
    for(var i=0;i<arry.length;i++) {
    var res = patt.test(arry[i]);
        if(res) { num++; }
    }
    if(num!=arry.length) {
    	alert("กรุณาใส่เบอร์โทรศัพท์ถูกต้อง");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    }
}
    
function checkFax(str) {
    var num = 0;
    var arry = (str.value).split(",");
    var patt = /^[0][1-9]([0-9]{7})$/;
    
    for(var i=0;i<arry.length;i++) {
    var res = patt.test(arry[i]);
        if(res) { num++; }
    }
    if(num!=arry.length) {
    	alert("กรุณาใส่เบอร์โทรสารให้ถูกต้อง");
    	document.getElementById(str.id).value = "";
    	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
    }
}

    function formatNumber(n) {
        var num = parseFloat(n);
        return num.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
    }

    function checkNumberComma(str) {
    	str.value = (str.value).replace(/,/g,"");
        var RE = /^\d*(\.\d{1})?\d{0,1}$/;
        var RE2 = /^\d{1,3}(?:,\d{3})*(?:\.\d+)?$/;
        var myStr = str.value.split(".");
        if(str.value != "" && myStr[0].length<=10){
            if(RE.test(str.value)){
                document.getElementById(str.id).value = formatNumber(str.value);
            }else if(RE2.test(str.value)){
                var sttNew = str.value.replace(/,/g , "");
                document.getElementById(str.id).value = sttNew;
            }else{
            	document.getElementById(str.id).value = "";
            	alert("กรุณากรอกเป้าหมายเป็นตัวเลขและทศนิยมสองตำแหน่งเท่านั้น");
            	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
            }
        }else{
        	if(str.value=="") {
            	//alert("กรุณาใส่จำนวนเป้าหมายของกลุ่ม !!");
            } else if(myStr[0].length>10) {
            	alert("จำนวนเป้าหมายของกลุ่มไม่ถูกต้อง !!");
            } else {
            	alert("กรุณากรอกเป้าหมายเป็นตัวเลขและทศนิยมสองตำแหน่งเท่านั้น");
            }
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            document.getElementById(str.id).focus();
            }, 0);
        }
    }
    
    function addFarmerGroupTeam() {
        $("#teamId").val("0");
        $("#teamFName").val("");
        $("#teamLName").val("");
        $("#teamPosition").val("");
        openFarmerGroupTeamPopup();
    }
    
    function editFarmerGroupTeam(index) {
        $("#teamId").val(index);
        var teamName = document.getElementsByName('farmerGroupTeamName')[index-1].value.split(" ",2);
        $("#teamFName").val(teamName[0]);
        $("#teamLName").val(teamName[1]);
        $("#teamPosition").val(document.getElementsByName('farmerGroupTeamPosition')[index-1].value);
        openFarmerGroupTeamPopup();
    }
    
    function openFarmerGroupTeamPopup() {
        $("#FarmerGroupTeamPopup").dialog({
            position:{
                my:'center center',
		    	at:'center center'
		    },
		    height: 280,
		    width: 600,
		    modal: true
		});
    }
    
    var numTeam = 0;
    var countRow = 0;
    function saveFarmerGroupTeam() {
    	if($("#teamFName").val().trim()!="" && $("#teamLName").val().trim()!="" && $("#teamPosition").val().trim()!="") {
    	var teamName = $("#teamFName").val().trim()+" "+$("#teamLName").val().trim();
    	var teamPosition = $("#teamPosition").val().trim();
    	var id = $("#teamId").val();
    	if(id!=0) {
    		$("#tName"+id).html(teamName);
    		$("#tPosition"+id).html(teamPosition);
    		$("#farmerGroupTeamName"+id).val(teamName);
    		$("#farmerGroupTeamPosition"+id).val(teamPosition);
    	} else {
    		var size = document.getElementsByName('delTeam').length;
    		countRow = size+1;
    		var addTable = generateRow(countRow,0,teamName,teamPosition);
    		$("#FarmerGroupTeam tbody:first").append(addTable);
    		if(countRow==1) { $("#noData").remove(); }
    	}
    	$("#FarmerGroupTeamPopup").dialog("close");
    	} else {
    		if($("#teamFName").val().trim()=="") { alert("กรุณาใส่ชื่อ"); }
    		else if($("#teamLName").val().trim()=="") { alert("กรุณาใส่นามสกุล"); }
    		else if($("#teamPosition").val().trim()=="") { alert("กรุณาใส่ตำแหน่ง"); }
    	}
    }
    
    function delFarmerGroupTeam(rowNo) {
    	var delTeam = document.getElementsByName('delTeam');
    	var teamId = document.getElementsByName('farmerGroupTeamId');
    	var teamName = document.getElementsByName('farmerGroupTeamName');
    	var teamPosition = document.getElementsByName('farmerGroupTeamPosition');
    	if(rowNo!="") {
			if(teamId[rowNo-1].value!=0) {
    		$(".gridPageOfPage").append("<input type=\"hidden\" name=\"delTeamId\" value=\""+teamId[rowNo-1].value+"\" />");
    	}}
    	if(rowNo>0) { $("#team"+rowNo).remove(); }
    	var addRow = "";
    	countRow = delTeam.length;
    	for(var i=1;i<=delTeam.length;i++) {
    		addRow += generateRow(i,teamId[i-1].value,teamName[i-1].value,teamPosition[i-1].value);
    	}
    	$("#FarmerGroupTeam tbody:first tr").remove();
    	if(countRow==0) {
    		$("#FarmerGroupTeam tbody:first").append("<tr id=\"noData\" class=\"gridRowEven\"><td colspan=\"4\">No records to display!</td></tr>");
    		document.getElementsByName('checkAll_delTeam')[0].checked = false;
    	} else {
    		$("#FarmerGroupTeam tbody:first").append(addRow);
    	}
    }
    
    function delFarmerGroupTeam2() {
    	var delTeam = document.getElementsByName('delTeam');
    	var teamId = document.getElementsByName('farmerGroupTeamId');
    	var delItem = [];
    	var num = 0;
		for(var i = 0 ; i < delTeam.length ; i++){
			if(delTeam[i].checked){
    			if(teamId[i].value!=0) {
    				$(".gridPageOfPage").append("<input type=\"hidden\" name=\"delTeamId\" value=\""+teamId[i].value+"\" />");
    			}
				delItem[num] = "#team"+(i+1);
				num++;
			}
		}
		for(var j = 0 ; j < delItem.length ; j++){
			$(delItem[j]).remove();
		}
		delFarmerGroupTeam("");
    }
    
    function generateRow(noRow, teamId, teamName, teamPosition) {
    	var addRow = "";
    	if(noRow%2==1) {
    		addRow += "<tr id=\"team"+noRow+"\" class=\"gridRowEven\">";
    	} else {
    		addRow += "<tr id=\"team"+noRow+"\" class=\"gridRowOdd\">";
    	}
    	if(teamId==0) { teamId = ""; }
    	addRow += "<td><input type=\"checkbox\" name=\"delTeam\" value=\""+noRow+"\">";
    	addRow += "<input type=\"hidden\" id=\"farmerGroupTeamId"+noRow+"\" name=\"farmerGroupTeamId\" value=\""+teamId+"\" />";
    	addRow += "<input type=\"hidden\" id=\"farmerGroupTeamName"+noRow+"\" name=\"farmerGroupTeamName\" value=\""+teamName+"\"/>";
    	addRow += "<input type=\"hidden\" id=\"farmerGroupTeamPosition"+noRow+"\" name=\"farmerGroupTeamPosition\" value=\""+teamPosition+"\"/></td>";
    	addRow += "<td id=\"tName"+noRow+"\" onclick=\"editFarmerGroupTeam("+noRow+");\" style=\"cursor:pointer;\">"+teamName+"</td>";
    	addRow += "<td id=\"tPosition"+noRow+"\" onclick=\"editFarmerGroupTeam("+noRow+");\" style=\"cursor:pointer;\">"+teamPosition+"</td>";
    	addRow += "<td onclick=\"editFarmerGroupTeam("+noRow+");\"><a class=\"btn-edit\"></a></td></tr>";
    	return addRow;
    }

    function getDistrictInfo(objPName, objDName, objSName, objPostCode)
    {
        var provinceDD = document.getElementById(objPName);
        var districtDD = document.getElementById(objDName);
        var subDistrictDD = document.getElementById(objSName);
        subDistrictDD.options.length=1;
        subDistrictDD.options[0].value="0";
        subDistrictDD.options[0].text="กรุณาเลือก";
        subDistrictDD.options[0].selected = true;
        if(objPostCode != ''){
            var postCodeTxt = document.getElementById(objPostCode);
            postCodeTxt.value = "";
        }
        if(provinceDD.options[provinceDD.selectedIndex].value=='0'){
            districtDD.options.length = 1;
            districtDD.options[0].value="0";
            districtDD.options[0].text="กรุณาเลือก";
            districtDD.options[0].selected = true;
        }else{
            var reqParameters = new Object();
            reqParameters.cmd = "GetDistrict";
            reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
            new Ajax.Request("<%=request.getContextPath()%>/FarmerGroup.do",
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
                    // alert('Error');
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
            reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
            reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
            new Ajax.Request("<%=request.getContextPath()%>/FarmerGroup.do",
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
                        subDistrictDD.options[0].value="";
                        subDistrictDD.options[0].text="กรุณาเลือก";
                        subDistrictDD.options[0].selected = true;
                    }
                },
                onFailure: function() {
                    // alert('Error');
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
        if(objPostCode != ''){
            var postCodeTxt = document.getElementById(objPostCode);
            postCodeTxt.value = "";
        }
        
        var reqParameters = new Object();
        reqParameters.cmd = "GetPostCode";
        reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
        reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;     
        reqParameters.subDistrictNo = subDistrictDD.options[subDistrictDD.selectedIndex].value;   
        new Ajax.Request("<%=request.getContextPath()%>/FarmerGroup.do",
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
    
    function closePage(){
        document.farmerGroupListForm.farmerGroupName.value = "";
        document.farmerGroupListForm.cmd.value = "Search"; 
        document.farmerGroupListForm.action="<%=request.getContextPath()%>/FarmerGroupList.do";
        document.forms["farmerGroupListForm"].submit();
    }
    
    function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
		alert("<%=msg%>");
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
   		<% } %>
 	<%}%>
 	}
</script>
</head>
<body onload="loadData();">
	<dcs:validateForm formName="farmerGroupForm" formAction="FarmerGroup.do" formBean="FarmerGroupForm">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="farmerGroupId"/>
	<div class="main-inside">
		<!-- insert header -->

		<%@include file="/header.jsp" %>
		<div class="navigator">
			<div class="inside">
				<ul>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/FarmerGroupList.do'>การจัดการกลุ่มเกษตรกร</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li>กรอกข้อมูล</li>
				</ul>
			</div>
		</div>	
		<!-- insert header -->
		<!-- start insert >> content -->    

		<div class="content" style="height:auto;">
			<div class="inside">
				<div class="content-keyin">
					<div class="search-header">
						<!-- <p>ข้อมูลกลุ่มเกษตรกร</p> -->
						&nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลกลุ่มเกษตรกร</font></span>
					</div>
					<div class="clear"></div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic" style="width: 110px;padding-right:0px">ชื่อกลุ่ม :</li>
							<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
							<li class="boxinput" style="width:auto">
								<dcs:validateText name="farmerGroupName" property="farmerGroupName" maxlength="100" />
							</li>
							<li class="topic"style="width:90px;padding-right:0px">สังกัดสหกรณ์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateDropDown name="childFarmerGroupId" dataSource="<%= cooperativeList %>" property="childFarmerGroupId" keyField="farmerGroupId" displayField="farmerGroupName"  isRequire="true" />	
							</li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic" style="width: 110px;padding-right:0px">สถานที่ตั้ง ที่อยู่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:61px;">
								<dcs:validateText  name="addressNo" property="addressNo" maxlength="10"/>
							</li>
							<li class="topic" style="width: 40px;padding-right:0px">หมู่ที่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:25px;">
								<dcs:validateText  name="moo" property="moo" maxlength="2" onChange="checkNumber(this)"/>
							</li>
							<li class="topic" style="width: 80px;padding-right:0px">หมู่บ้าน :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateText  name="village" property="village" maxlength="30"/>
							</li>
							<li class="topic" style="width:90px;padding-right:0px">ซอย :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateText  name="soi" property="soi" maxlength="30"/>
							</li>
							<li class="topic" style="width: 90px;padding-right:0px">ถนน :</li>
							<li class="topic" style="width: 0px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateText  name="road" property="road" maxlength="30"/>
							</li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:110px;padding-right:0px">จังหวัด :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:145px;">
								<select id="provinceNo" name="provinceNo" onChange="getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');">
									<option value="0">กรุณาเลือก</option>
									<%if(provinceList !=null && provinceList.size()>0){
										for(int p=1; p<provinceList.size(); p++){
											Province pd = (Province)provinceList.get(p);
											%>
											<option value="<%=pd.getProvinceNo()%>"><%=pd.getThaiName()%></option>
											<%}} %>
								</select>
							</li>
							<li class="topic"style="width:80px;padding-right:0px">อำเภอ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateDropDown name="districtNo" dataSource="<%= districtList %>" property="districtNo" keyField="districtNo" displayField="thaiName" onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode')" isRequire="true"/>
							</li>
							<li class="topic"style="width:90px;padding-right:0px">ตำบล :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateDropDown name="subDistrictNo" dataSource="<%= subDistrictList %>" property="subDistrictNo" keyField="subDistrictNo" displayField="thaiName" isRequire="true" onChange="getPostCodeInfo('provinceNo','districtNo','subDistrictNo','postCode')"/>	
							</li>
							<li class="topic" style="width: 90px;padding-right:0px">รหัสไปรษณีย์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateText name="postCode" property="postCode" maxlength="5" isReadOnly="true"/>
							</li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic" style="width: 110px;padding-right:0px">เบอร์โทรศัพท์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:145px;">
								<dcs:validateText name="tel" property="tel" maxlength="30" onChange="checkTel(this)" />
							</li>

							<li class="topic" style="width: 80px;padding-right:0px">เบอร์โทรสาร :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateText name="fax" property="fax"  maxlength="30" onChange="checkFax(this)" />
							</li>
							<li class="topic" style="width: 90px;padding-right:0px">เป้าหมาย :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:170px;">
								<dcs:validateText name="target" property="target" maxlength="13" onChange="checkNumberComma(this);"/> &nbsp;กก.
							</li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic" style="width: 110px;padding-right:0px">วัตถุประสงค์กลุ่ม :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width: 370px;">
								<dcs:validateText name="objective" property="objective" maxlength="100" />
							</li>
						</ul>
					</div><br />
					<fieldset><legend><font size="5"><b>:: รายละเอียดคณะกรรมการบริหารงาน ::</b></font></legend>
						<div id="cooperative">
							<table align="center" width="96%">
								<tbody>
									<tr><td>
									<a class="btn-add" onclick="addFarmerGroupTeam();"></a>
									<a class="btn-del" onclick="delFarmerGroupTeam2();"></a>
									</td></tr>
									<tr>
										<td><table width="100%" cellspacing="0" cellpadding="0" id="FarmerGroupTeam">
											<thead>
											<tr class="gridHeader">
												<td width="20" class="gridHeader" align="center"><input type="checkbox" name="checkAll_delTeam" onclick="checkAll(this, 'delTeam');" /></td>
												<td width="200" class="address-sr" align="center"><a href="javascript:doSortuserList('userListForm', 'name', true)" class="gridSort">ชื่อ-นามสกุล</a></td>
												<td width="200" class="address-sr" align="center"><a href="javascript:doSortuserList('userListForm', 'district', true)" class="gridSort">ตำแหน่ง</a></td>
												<td width="20" class="manage-sr">&nbsp;</td>
											</tr>
											</thead>
										<tbody>
											<% if(farmerGroupTeamList!=null && farmerGroupTeamList.size()>0) { 
												for(int i=1;i<=farmerGroupTeamList.size();i++) {
													FarmerGroupTeam getItem = (FarmerGroupTeam)farmerGroupTeamList.get(i-1); %>
												<% if(i%2==1) { %>
												<tr id="team<%=i%>" class="gridRowEven">
												<% }else{ %>
												<tr id="team<%=i%>" class="gridRowOdd">
												<% } %>
												<td><input type="checkbox" name="delTeam" value="<%=i%>" />
												<input type="hidden" id="farmerGroupTeamId<%=i%>" name="farmerGroupTeamId" value="<%=getItem.getFarmerGroupTeamId()%>" />
												<input type="hidden" id="farmerGroupTeamName<%=i%>" name="farmerGroupTeamName" value="<%=getItem.getFirstName()%> <%=getItem.getLastName()%>" />
												<input type="hidden" id="farmerGroupTeamPosition<%=i%>" name="farmerGroupTeamPosition" value="<%=getItem.getPosition()%>" /></td>
												<td id="tName<%=i%>" onclick="editFarmerGroupTeam(<%=i%>);" style="cursor:pointer;"><%=getItem.getFirstName()%> <%=getItem.getLastName()%></td>
												<td id="tPosition<%=i%>" onclick="editFarmerGroupTeam(<%=i%>);" style="cursor:pointer;"><%=getItem.getPosition()%></td>
												<td onclick="editFarmerGroupTeam(<%=i%>);"><a class="btn-edit"></a></td>
												</tr>
											<% } %>
											<% }else{ %><tr id="noData" class="gridRowEven">
												<td colspan="4">No records to display!</td>
											</tr><% } %>
										</tbody>
											<tr>
												<td colspan="4" class="gridPageOfPage">&nbsp;</td>
											</tr>
										</table></td>
									</tr>				
								</tbody>
							</table>
						</div>
						<div id="FarmerGroupTeamPopup" title="คณะกรรมการบริหารงาน"  style="display:none;">
							<br /><input type="hidden" id="teamId" name="teamId" value="0" />
							<div class="line-input-keyin">
								<ul>
									<li class="topic"style="width:100px;padding-right:0px">ชื่อ :</li>
									<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
									<li class="boxinput" style="width:130px;">
									<dcs:validateText  name="teamFName" maxlength="30"/></li>
									<li class="topic"style="width:70px;padding-right:0px">นามสกุล :</li>
									<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
									<li class="boxinput" style="width:130px;">
									<dcs:validateText  name="teamLName" maxlength="30"/></li>
								</ul>
							</div>
							<div class="line-input-keyin">
								<ul>
									<li class="topic"style="width:100px;padding-right:0px">ตำแหน่ง :</li>
									<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
									<li class="boxinput" style="width:350px;"><dcs:validateText  name="teamPosition" maxlength="30"/></li>
								</ul>
							</div>
							<br /><table align="center"><tr><td>
							<a class="btn-ok" onclick="saveFarmerGroupTeam();"></a>
							<a class="btn-cancel" onclick="$('#FarmerGroupTeamPopup').dialog('close');"></a>
							</td></tr></table>
						</div>
					</fieldset>
					<div class="clear"></div>
					<table align="center">
						<tr><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td>
								<a class="btn-save" onclick="saveFarmerGroup();" id="write"></a>
							</td>	
							<td>
								<a class="btn-cancel" onclick="closePage();"></a>
							</td>			           
						</tr>
						<tr><td colspan="2">&nbsp;</td></tr>
					</table>
				</div>

				<div class="clear"></div>

			</div>
		</div>

		<!--footer -->
		<%@include file="/footer.jsp" %>

		<!-- end insert >> content --> 

		<!-- end for header -->
	</div>

</dcs:validateForm>
<form name="farmerGroupListForm"  method="post" action="<%=request.getContextPath()%>/FarmerGroupList.do">
	<input type="hidden" name="farmerGroupName" />
	<input type="hidden" name="cmd" />
</form>
</body>
</html>