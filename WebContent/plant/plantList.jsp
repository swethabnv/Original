<!doctype html>
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>

<%@page import="com.wsnweb.form.PlantListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.Branch"%>
<%@page import="com.wsndata.data.BreedType"%>
<%@page import="com.wsndata.data.Plant"%>
<%@page import="com.wsndata.data.Province"%>
<%@page import="com.wsndata.data.FarmerGroup"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/plant.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/GridStyle.css" />
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

<title><%=Utility.get("WEB_TITLE")%></title>
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	PlantListForm plantListForm = (PlantListForm)request.getAttribute("PlantListForm");
	List plantList = (ArrayList) session.getAttribute("plantList");
	List breedTypeList= (ArrayList) session.getAttribute("breedTypePlantList");
	List plantYearList = (ArrayList) session.getAttribute("plantYearList");
	List branchList = (ArrayList) session.getAttribute("branchPlantList");
	List searchProvinceList = (ArrayList) session.getAttribute("searchProvinceList");
	List searchDistrictList =(ArrayList) session.getAttribute("searchDistrictList");
	List searchSubDistrictList =(ArrayList) session.getAttribute("searchSubDistrictList");
	User chkUser = (User) session.getAttribute("userLogin");
	List corpGroupList = (ArrayList) session.getAttribute("cooperativeGroupMasterList");
		
	String msg = "",branchCode = "",plantYear = "",breedTypeId = "", idCard = "", userFarmerGroupId = "", userProvinceNo = "";
	long districtNo = 0, subDistrictNo = 0, provinceNo = 0, cooperateGroupId=0;
	if(plantListForm!=null){
		if(!"".equals(plantListForm.getErrMessage())){
		  	msg = plantListForm.getErrMessage();
		}
		idCard = plantListForm.getIdCard();
		branchCode =  plantListForm.getBranchCode();
		plantYear = plantListForm.getPlantYear();
		breedTypeId = plantListForm.getBreedTypeId();
		provinceNo = plantListForm.getProvinceNo();
		districtNo = plantListForm.getDistrictNo();
		subDistrictNo = plantListForm.getSubDistrictNo();
		cooperateGroupId = plantListForm.getFarmerGroupId();
		userFarmerGroupId = plantListForm.getUserFarmerGroupId();
		userProvinceNo = plantListForm.getUserProvinceNo();
	}
%>


<script type="text/javascript">
  //-- Shift Focus to the Next TextBox using the Enter Key using jQuery -- //
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


var provinceArray = new Array();
var farmerGroupArray = new Array();
var expandedProvince = false;

function searchPlant(){
	//alert($("#userProvinceNo option:first").val());
	//alert( $("#userFarmerGroupId option:first").val());
	
	document.plantListForm.cmd.value="Search";
	document.plantListForm.action= "<%=request.getContextPath()%>/PlantList.do";
	document.forms["plantListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delPlantId');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.plantListForm.cmd.value="Delete";
				document.plantListForm.action="PlantList.do";
				document.forms["plantListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.plantListForm.cmd.value="Search";
		document.forms["plantListForm"].submit();
	}
}

function loadEdit(plantId, idCard)
{
	document.plantForm.plantId.value = plantId;
	document.plantForm.idCard.value = idCard;
	document.plantForm.cmd.value = "Edit";
	document.plantForm.action= "<%=request.getContextPath()%>/Plant.do";
	document.forms["plantForm"].submit();
 			 
}

function addPlant()
{
        document.plantForm.cmd.value = "New";
        document.plantForm.plantId.value = "0";
	    document.plantForm.action= "<%=request.getContextPath()%>/Plant.do";
	    document.forms["plantForm"].submit();
}

function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>");
   	<%}%>
   	loadMenu("M17");
   	
  <%if(breedTypeId !=null && !"".equals(breedTypeId)){%>
  		document.plantListForm.breedTypeId.value = "<%=breedTypeId%>";
  <%}if(plantYear !=null &&!"".equals(plantYear)){%>
  		document.plantListForm.plantYear.value = "<%=plantYear%>";
  <%}if(branchCode !=null &&!"".equals(branchCode)){%>
  		//document.plantListForm.branchCode.value = "<%=branchCode%>"; Not use Comment by Thanaput.s 14/09/2017
  <%}if(userProvinceNo !=null && !"".equals(userProvinceNo)){%>
  		document.plantListForm.userProvinceNo.value = "<%=userProvinceNo%>";
  		//For dropdown by Thanaput.s 14/09/2017
		var provinceSplit = $('#userProvinceNo').val().split(",");
		for(var i = 0; i < provinceSplit.length; i++){
			$('#provinceNo')[0].sumo.selectItem(String(provinceSplit[i]));
		}
  		getDistrictInfo('userProvinceNo','districtNo','subDistrictNo');
  <%}if(districtNo>0){%>
  		document.plantListForm.districtNo.value = "<%=districtNo%>";
  		getSubDistrictInfo('userProvinceNo','districtNo','subDistrictNo');
  <%}if(subDistrictNo>0){%>
  		document.plantListForm.subDistrictNo.value = "<%=subDistrictNo%>";
  <%}if(idCard !=null && !"".equals(idCard)){%>
  	  	document.plantListForm.idCard.value = "<%=idCard%>";
  <% }if(cooperateGroupId>0){ %>
  		//document.plantListForm.farmerGroupId.value = "<%=cooperateGroupId%>";
  <%} if(userFarmerGroupId != null && !"".equals(userFarmerGroupId)){%>
  		document.plantListForm.userFarmerGroupId.value = "<%=userFarmerGroupId%>";
  		//For dropdown by Thanaput.s 14/09/2017
		var farmerGroupSplit = $('#userFarmerGroupId').val().split(",");
		for(var j = 0; j < farmerGroupSplit.length; j++){
			$('#farmerGroupId')[0].sumo.selectItem(String(farmerGroupSplit[j]));
		}
  <% } %>
}


function getDistrictInfo(objPName, objDName, objSName)
{
          var subDistrictDD = document.getElementById(objSName);
          subDistrictDD.options.length=1;
          subDistrictDD.options[0].value="0";
          subDistrictDD.options[0].text="กรุณาเลือก";
          subDistrictDD.options[0].selected = true;
            
            var provinceDD = document.getElementById(objPName); // closed by Yatphiroon.p
            var districtDD = document.getElementById(objDName);
            var provinceNo = $("#userProvinceNo").val(); // added on 10/01/2017
            
           // if(provinceDD.options[provinceDD.selectedIndex].value=='0'){ -- closed by Yatphiroon.P
             if(provinceDD.value == ""){
                  districtDD.options.length = 1;
                  districtDD.options[0].value="0";
                  districtDD.options[0].text="กรุณาเลือก";
                  districtDD.options[0].selected = true;
            }else{
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetDistrict";
                 // reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;  
                  reqParameters.userProvinceNo = provinceDD.value;
                  new Ajax.Request("<%=request.getContextPath()%>/PlantList.do",
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
                        }
                  });
            }
}
function getSubDistrictInfo(objPName, objDName, objSName)
{
         //   var provinceDD = document.getElementById(objPName);
          var provinceNo = $("#userProvinceNo").val(); // added on 10/01/2017
         
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
                 // reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;  
                  reqParameters.userProvinceNo = provinceNo;
                    
                  new Ajax.Request("<%=request.getContextPath()%>/PlantList.do",
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
                        }
                  });
            }
}
function statusPopup(plantId,idCard,plantStatus,reason){
	document.getElementById("tmpIdCard").value = idCard;
	document.getElementById("tmpPlantId").value = plantId;
	if(plantStatus!=null&&plantStatus=="ไม่ผ่าน"){
	//alert(plantStatus);
		    	document.getElementById("stNotPass").checked = true;
		    	document.getElementById("reason").value = reason;
		    	document.getElementById("reason").disabled = false;
	}else {
				document.getElementById("stPass").checked = true;
		    	document.getElementById("reason").value = reason;
		    	document.getElementById("reason").disabled = true;
	}
	isStatus(document.getElementById("stPass"));
		$("#statusPopup").dialog({
			position:{
				my:'center',
				at:'center'},
			height: 250,
			width: 550,
			modal: true
		});
	document.getElementById("statusPopup").style.visibility = 'visible';
}
function changeStatusPopup(plantId,idCard,status,remark){
	document.getElementById("memIdCard").value = idCard;
	document.getElementById("memPlantId").value = plantId;
	var groupUser = <%=chkUser.getGroupUser()%>
	if(groupUser == 5) {
		if(status=="C"){
			document.getElementById("memCancel").checked = true;
		} else if(status=="A") {
			document.getElementById("memActive").checked = true;
		} else {
			document.getElementById("memActive").checked = false;
			document.getElementById("memCancel").checked = false;
		}
		document.getElementById("remark").value = remark.trim();
	
		$("#memberStatusPopup").dialog({
			position:{
				my:'center',
				at:'center'},
			height: 240,
			width: 550,
			modal: true
		});
		document.getElementById("memberStatusPopup").style.visibility = 'visible';
	}
}
function isStatus(obj){
	if(document.getElementById("stPass").checked){
			document.getElementById("reason").value = "";
			document.getElementById("reason").disabled = true;
	}
	if(document.getElementById("stNotPass").checked){
			document.getElementById("reason").disabled = false;
	}
}
function closeWindow(){
	$("#statusPopup").dialog( "close" );
	$("#memberStatusPopup").dialog( "close" );
}
function saveStatus(){
	var tmpIdCard = document.getElementById("tmpIdCard").value;
	var tmpPlantId = document.getElementById("tmpPlantId").value;
	if(document.getElementById("stNotPass").checked && document.getElementById("reason").value == ''){
		alert("กรุณากรอกเหตูผล");
		document.getElementById("reason").focus();
		return false;
	}
	if(document.getElementById("stPass").checked){
		document.plantForm.plantStatus.value = document.getElementById("stPass").value; 
		//alert(document.getElementById("stPass").value);
	}
	if(document.getElementById("stNotPass").checked){
		//alert(document.getElementById("stNotPass").value);
		document.plantForm.plantStatus.value = document.getElementById("stNotPass").value; 
		//document.plantForm.reason.value = document.getElementById("reason").value;
	}
	//alert("id card = "+tmpIdCard);
	//alert("plantId = "+tmpPlantId);
	//alert("plantstatus = "+document.getElementById("pStatusPopup").value);
	//alert("reason = "+document.getElementById("reason").value);
	document.plantForm.idCard.value = tmpIdCard;
	document.plantForm.plantId.value = tmpPlantId;
	//document.plantForm.plantStatus.value = document.getElementById("pStatusPopup").value;
	document.plantForm.reason.value = document.getElementById("reason").value;
	document.plantForm.cmd.value = "Save";
	document.plantForm.action = "<%=request.getContextPath()%>/Plant.do";
	document.plantForm.status.value = "Edit";
	document.plantForm.setStatusCmd.value = "Yes";
	document.plantForm.method = "Post";
	document.forms["plantForm"].submit();
}
function saveMemberStatus(){
	var memIdCard = document.getElementById("memIdCard").value;
	var memPlantId = document.getElementById("memPlantId").value;
	/*var reason = prompt("คุณต้องการที่จะเปลี่ยนสถานะสมาชิก กรุณาระบุเหตุผล", "");
    if (reason != null) {
        alert("เหตุผล : " + reason);
    }*/
	if(document.getElementById("memActive").checked){
		document.plantForm.memberStatus.value = document.getElementById("memActive").value; 
	}
	if(document.getElementById("memCancel").checked){
		document.plantForm.memberStatus.value = document.getElementById("memCancel").value; 
	}
	document.plantForm.idCard.value = memIdCard;
	document.plantForm.plantId.value = memPlantId;
	document.plantForm.remark.value = document.getElementById("remark").value;
	document.plantForm.cmd.value = "Save";
	document.plantForm.action = "<%=request.getContextPath()%>/Plant.do";
	document.plantForm.status.value = "Edit";
	document.plantForm.setStatusCmd.value = "Change";
	document.plantForm.method = "Post";
	document.forms["plantForm"].submit();
}
    
    
     var expandedFarmerGroup = false;    
    function showCheckboxesGroup(){
     	var selectedGrp = [];
		var selectedGrpText = [];
    
        var checkboxes = document.getElementById("farmerGroupId");
        if (!expandedFarmerGroup) {
            checkboxes.style.display = "block";
            expandedFarmerGroup = true;
        } else {
            checkboxes.style.display = "none";
            expandedFarmerGroup = false;
        }
        
        // --- start check region -- 
	    var checkboxesGrp = document.getElementsByName('userFarmerGroup');
		for (var i=0; i < checkboxesGrp.length; i++) {
		    if (checkboxesGrp[i].checked) {
		    
		    	if(!farmerGroupArray.hasOwnProperty(checkboxesGrp[i].value)){
		    		selectedGrp.push(checkboxesGrp[i].value);
		    		var texts = $("label[for='" + checkboxesGrp[i].id + "']").text();
		    		//alert(texts+"!");
		    		selectedGrpText.push(texts);
		    		farmerGroupArray[checkboxesGrp[i].value] = texts;
		    	}
		    	
		    }
		}
		
		if(selectedGrp==""){
			$("#userFarmerGroupId option:first").text("ทั้งหมด");
		} else {
			$("#userFarmerGroupId option:first").text(selectedGrpText);
			$("#userFarmerGroupId option:first").val(selectedGrp);
		}
		
    }

</script>

</head>       
<body onload="showErrorMessage();">
<dcs:validateForm formName="plantListForm" formAction="PlantList.do" formBean="PlantListForm">
<input type="hidden" name="cmd" value="DirtyList"/>
<div class="main-inside">
<%@include file="/header.jsp" %>
	<div class="navigator">
	<div class="inside">
	 	<ul>     
            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
            <li><a style="color:#333" href='<%=request.getContextPath()%>/PlantList.do'>ข้อมูลเกษตรกรผู้เพาะปลูกพันธุ์พืช</a></li>
			<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
           <li>ค้นหาข้อมูล</li>
	    </ul>
	</div>
    </div>	
    <!-- start content -->
    <div class="content">
    	<div class="inside">    
        	<div class="content-search">
                <div class="search-header" >
                 &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >รายการเกษตรกรผู้เพาะปลูก</font></span>
               
                </div>
                <div class="line-input">
                	<ul>
                		<li class="topic" style="width: 165px;">เลขบัตรประจำตัวประชาชน : </li>
                		<li class="boxinput" style="width: 140px;">
                		<dcs:validateText name="idCard" maxlength="13" style="width:130px" onChange="checkNumber(this);"/>    	
                		</li>
                	</ul>
                	<ul>
                        <li class="topic" style="width: 60px">ชื่อ : </li>
                        <li class="boxinput" style="width:120px">
                            <dcs:validateText name="firstName"  property="firstName"  style="width:123px" maxlength="30"/>
                        </li>
                   </ul>
                	<ul>
                		<li class="topic"style="width:90px;">ปีการผลิต :</li>
			            <li class="boxinput" style="width: 135px;">
	                      <select id="plantYear"  name="plantYear" style="width:135px;height:29px;" >
		                    		<option value="0">กรุณาเลือก</option>
		                    		<%if(plantYearList !=null && plantYearList.size()>0){
		                    			for(int i=0; i<plantYearList.size(); i++){
		                    				Plant plant = (Plant)plantYearList.get(i);
		                    		 %>
		                    		 	<option value="<%= plant.getPlantYear()%>"><%= plant.getPlantYear()%></option>
		                    		 <%}} %>
		                  </select>
	                    </li>
                	</ul>
					<ul>
						<li class="topic" style="width: 60px;">ครั้งที่ : </li>
						<li class="boxinput" style="width: 130px;">
						<dcs:validateText name="plantNo" property="plantNo" maxlength="2" style="width: 125px;" onChange="checkNumber(this);"/>    	
						</li>
					</ul>
				</div>
				<div class="line-input" style="overflow: visible;">
					<ul>
                    	<li class="topic" style="width: 165px;">ชนิดพืช : </li>
                        <li class="boxinput" style="width:140px;">
	                    <select id="breedTypeId"  name="breedTypeId" style="width:140px;height:29px;" >
		                    		<option value="0">กรุณาเลือก</option>
		                    		<%if(breedTypeList !=null && breedTypeList.size()>0){
		                    			for(int b=0; b<breedTypeList.size(); b++){
		                    				BreedType breed = (BreedType)breedTypeList.get(b);
		                    		 %>
		                    		 	<option value="<%= breed.getBreedTypeId()%>"><%= breed.getBreedTypeName()%></option>
		                    		 <%}} %>
		                 </select>
	                     </li>
					</ul>
					<ul style="padding-bottom: 0px;">
                        <li class="topic" style="width: 60px;vertical-align:top;">จังหวัด : </li>
                        <li class="boxinput" style="width:130px;height:27px;">
                           
                         <!-- - -->
                         <input type="hidden" id="userProvinceNo" name="userProvinceNo" />
			           			<select id="provinceNo" name="provinceNo" multiple="multiple" class="SlectBox" placeholder="กรุณาเลือก">
						        	<% if(searchProvinceList != null && searchProvinceList.size() > 0 ) {
						        		for(int i = 0; i < searchProvinceList.size(); i++){
					        				Province objProv = (Province)searchProvinceList.get(i);
					        		%>
					            		<option value="<%= objProv.getProvinceNo() %>"><%= objProv.getThaiName() %></option>
					            	<% } } %>
						        </select>
			   			 <!-- - -->
                            
                        </li>
					</ul>
					<ul>
                        <li class="topic" style="width: 80px;">อำเภอ : </li>
                        <li class="boxinput" style="width:135px;">
                        	<dcs:validateDropDown name="districtNo" dataSource="<%= searchDistrictList %>" property="districtNo" keyField="districtNo" displayField="thaiName" style="width:135px;" onChange="getSubDistrictInfo('userProvinceNo','districtNo','subDistrictNo')" isRequire="true"/>
                            
                        </li>
					</ul>
					<ul>
                        <li class="topic" style="width: 60px;">ตำบล : </li>
                        <li class="boxinput" style="width: 135px;">
		                     <dcs:validateDropDown name="subDistrictNo" dataSource="<%= searchSubDistrictList  %>" property="subDistrictNo" keyField="subDistrictNo"  displayField="thaiName" style="width:134px;" isRequire="true"/>
		 				 </li>
					</ul>
					<div style="clear: both;"></div>
				</div>
				<div class="line-input" style="overflow: visible;">
					<ul>
                    	<li class="topic" style="width: 165px;">สังกัด : </li>
                    	<li class="boxinput" style="width: 345px;">
                    	
                    		<!-- cooperative group list -->
                         <input type="hidden" id="userFarmerGroupId" name="userFarmerGroupId" />
			           			<select id="farmerGroupId" name="farmerGroupId" multiple="multiple" class="SlectBox" placeholder="กรุณาเลือก">
						        	<% if(corpGroupList != null && corpGroupList.size() > 0 ) {
						        		for(int i = 0; i < corpGroupList.size(); i++){
						        			FarmerGroup coop = (FarmerGroup)corpGroupList.get(i);
						        	%>
						            		<option value="<%= coop.getFarmerGroupId() %>"><%= coop.getFarmerGroupName() %></option>
					            	<% } } %>
						        </select>
			   			 <!-- -
							<div class="multiselect" >
						        <div class="selectBox" onclick="showCheckboxesGroup()" > 
						            <select id="userFarmerGroupId" name="userFarmerGroupId"  style="width:345px;"  > 
						               		<option value="" selected="selected" >ทั้งหมด </option>
						            </select>
						            <div class="overSelect"></div>
						        </div>
						        <div class="checkboxes" id="farmerGroupId" style="width:345px;" >
						        
						        </div>
				   			 </div>	 -->
							<!-- cooperative group list -->
                    	
                    	</li>
					</ul>
					<ul>
						<li class="topic" style="width: 80px;" >สถานะ : </li>
						<li class="boxinput" style="width: 135px;">
							<select id="plantStatus" name="plantStatus" style="width: 135px; height: 29px;">
								<option value="">กรุณาเลือก</option>
								<option value="1">ผ่าน</option>
								<option value="0">ไม่ผ่าน</option>
							</select>
						</li>
					</ul>
					<div style="clear: both;"></div>
				</div>
				<div class="line-input">
					<ul>
						<li class="boxinput" style="width: 114px; padding-left:846px;">
							<button class="btn-search" onclick="searchPlant();"></button>
						</li>
					</ul>
				</div>
			</div>
			
            <!--  for dcsGrid -->
            <div class="btn-manage" style="margin-left:0px;">
            	     <a class="btn-add" onclick="addPlant();" id="write"></a>
            		 <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
            </div>
            <table border="0" width="96%" >
            <tr>
          	<%   if(plantList!=null){ %>
            <td><dcs:grid  dataSource="<%= plantList %>" name="plantList" pageSize="<%=plantListForm.getDisplayRow()%>" width="960px">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delPlantId" dataField="plantId" headerText="" width="30" HAlign="left" />
                 <dcs:textcolumn dataField="idCard" headerText="เลขที่บัตรประชาชน" width="150" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{plantId}','{idCard}');"/>
                 <dcs:textcolumn dataField="fullName" headerText="ชื่อ - นามสกุล" width="180" sortable="true" HAlign="left" cssClass="topic-sr" style="cursor:pointer" onClick="loadEdit('{plantId}','{idCard}');"/>
                 <dcs:textcolumn dataField="districtName" headerText="อำเภอ" width="150" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{plantId}','{idCard}');"/>
                 <dcs:textcolumn dataField="breedTypeName" headerText="ชนิดพีช" width="80" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{plantId}','{idCard}');"/>
                 <dcs:textcolumn dataField="plantYear" headerText="ปีการผลิต" width="80" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{plantId}','{idCard}');"/>
                 <dcs:textcolumn dataField="plantNo" headerText="ครั้งที่" width="60" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{plantId}','{idCard}'); " style="cursor:pointer" />
                 <dcs:textcolumn dataField="plantStatus" headerText="สถานะ" width="70" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{plantId}','{idCard}');"/>
                 <% if("5".equals(chkUser.getGroupUser())){%>
                 <dcs:textcolumn dataField="statusPopup" headerText="" width="88" sortable="false" HAlign="center" cssClass="tel-sr" style="cursor:pointer; text-decoration: underline; color: blue" onClick="statusPopup('{plantId}','{idCard}','{plantStatus}','{reason}');"/>
                 <% }%>
                 <dcs:imagecolumn dataField="linkImageStatus" headerText="" toolTip="แก้ไขสถานะ" width="26" HAlign="center" cssClass="manage-sr" style="padding-top:2px;cursor:pointer;" onClick="changeStatusPopup('{plantId}','{idCard}','{status}','{remark}');" />
                 <dcs:imagecolumn dataField="linkImageEdit" headerText="" toolTip="แก้ไข" width="26" HAlign="center" cssClass="manage-sr" style="padding-top:2px;cursor:pointer;" onClick="loadEdit('{plantId}','{idCard}');" />
                 </dcs:grid>
            </td>
            <% } %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countPlant"))%> รายการ</div>
            <!-- end dcsGrid -->
            <div class="clear"></div> 
        </div>
        <!-- ------------statusPopup----------- -->
        <div id="statusPopup" title="กำหนดสถานะ" style="width: 960px;padding-left: 5px;VISIBILITY:hidden;display:none;">
        <input type="hidden" id="tmpIdCard" />
        <input type="hidden" id="tmpPlantId" />
       	<div class="line-input-keyin">
	                    	<ul>
	                    	   <li class="topic" style="width: 85px;">สถานะ :</li>
	                    	   <li class="topic" style="width: 80px;">
	                    	   <input type="radio" id="stPass" name="pStatusPopup" style="width:30px;" value="1" onclick="isStatus(this)" checked="checked"/>
	                    	   <label for="stPass">&nbsp;ผ่าน</label></li>
	                    	   
	                    	   <li class="topic" style="width:90px;">
	                    	   <input type="radio" id="stNotPass" name="pStatusPopup" style="width:30px;" value="0" onclick="isStatus(this)"/>
	                    	   <label for="stNotPass">&nbsp;ไม่ผ่าน</label></li>
	                    	</ul>
	                    	<ul>   
	                    	   <li class="topic" style="width: 85px;padding-right: 15px;">เหตุผล :</li>
	                    	   <li class="boxinput" style="width: 130px;" >
	                    	   		<dcs:validateText name="reason" property="reason" style="width: 140px;" maxlength="100"  />
	                    	   </li>
	                    	</ul>
	                    	<div class="clear"></div> 
	                    	<div id="statusSave" class="line-input-keyin" style="padding:20px">
	                    	<table><tr><td style="padding-left: 130px;">
			   					<img src="<%=request.getContextPath() %>/images/btn-ok.png" id="btnSubmit" class="btn" onclick="saveStatus();"/></td>
			   					<td><img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="closeWindow();"/>
		   					</td></tr></table>
		   					</div>
	                    </div>    			
       	
       	</div>
       	<!-- ------------End statusPopup----------- -->
       	
        <!-- ------------memberStatusPopup----------- -->
        <div id="memberStatusPopup" title="กำหนดสถานะสมาชิก" style="width: 960px;padding-left: 5px;VISIBILITY:hidden;display:none;">
        	<input type="hidden" id="memIdCard" />
        	<input type="hidden" id="memPlantId" />
       		<div class="line-input-keyin">
       			<ul>
       				<li class="topic" style="width: 85px;">สถานะ :</li>
       				<li style="width: 120px;">
       					<input type="radio" id="memActive" name="memStatusPopup" style="width:14px;" value="A" />
	                    <label for="memActive" style="color:#00D11D;">เป็นสมาชิก</label>
	                </li>
	                <li style="width: 150px;">
	                	<input type="radio" id="memCancel" name="memStatusPopup" style="width:14px;" value="C" />
	                	<label for="memCancel" style="color:#FF0000;">ออกจากสมาชิก</label>
	                </li>
	            </ul>
	            <ul>
	            	<li class="topic" style="width: 85px;padding-right: 15px;">เหตุผล :</li>
	            	<li class="boxinput" style="width: 130px;" >
	            		<dcs:validateText name="remark" property="remark" style="width: 240px;" maxlength="100"  />
	                </li>
	            </ul>
	            
	            <div class="clear"></div>
	            <div id="memberStatusSave" class="line-input-keyin" style="padding:20px">
	            	<table><tr>
	            		<td style="padding-left: 130px;">
	            			<img src="<%=request.getContextPath() %>/images/btn-ok.png" id="btnSubmit" class="btn" onclick="saveMemberStatus();"/></td>
			   			<td><img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="$('#memberStatusPopup').dialog('close');"/>
		   			</td></tr></table>
		   		</div>
		   	</div>
       	</div>
       	<!-- ------------End memberStatusPopup----------- -->
    </div>
<!-- footer -->
<%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>
	<form name="plantForm" method="post" action="<%=request.getContextPath()%>/Plant.do">
		<input type="hidden" name="cmd" />
		<input type="hidden" name="plantId" />
		<input type="hidden" name="idCard" />
		<input type="hidden" name="setStatusCmd" />
		<input type="hidden" name="status" />
		<input type="hidden" name="reason" />
		<input type="hidden" name="plantStatus" />
		<input type="hidden" name="memberStatus" />
		<input type="hidden" name="remark" />
	</form>
			   			 <script language="JavaScript" src="<%=request.getContextPath()%>/js/jquery.sumoselect.js"></script>
			   			 <script type="text/javascript">
			   			 $(document).ready(function () {
			   			 	$('.SlectBox').SumoSelect({ csvDispCount: 2, selectAll: true, captionFormatAllSelected:'ทั้งหมด', captionFormat: '{0} รายการ',locale :  ['OK', 'Cancel', 'ทั้งหมด']});
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
			   			 	$('#userProvinceNo').val(items);
			   			 	getDistrictInfo('userProvinceNo','districtNo','subDistrictNo');
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
			   			 	$('#userFarmerGroupId').val(items);
			   			 });
			   			 </script>
</body>
</html>
