<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BreedGroupForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<head>
<meta http-equiv="Content-Group" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />
<title><%=Utility.get("WEB_TITLE")%></title>

<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
    BreedGroupForm breedGroupForm = (BreedGroupForm)request.getAttribute("BreedGroupForm");
	List breedTypeList = (ArrayList) session.getAttribute ("breedTypeList");
	List breedGroupList = (ArrayList) session.getAttribute ("breedGroupList");
	String RICE_TYPE = Utility.get("RICE_TYPE"); //ประเภทข้าว
	String msg = "",cmd="";
	long breedGroupId=0,breedTypeId=0;
	Integer period = 0;
	String plantPeriodFrom ="0",plantPeriodTo="0",forcastPeriodFrom="0",forcastPeriodTo="0";
	String breedCategory = "";
	if(breedGroupForm!=null){
	if(breedGroupForm.getMsg() !=null && !"".equals(breedGroupForm.getMsg())){
			msg = breedGroupForm.getMsg();
		}
		cmd = breedGroupForm.getCmd();
		breedGroupId = breedGroupForm.getBreedGroupId();
		breedTypeId = breedGroupForm.getBreedTypeId();
		period = breedGroupForm.getPeriod();
		plantPeriodFrom = breedGroupForm.getPlantPeriodFrom()!=null?breedGroupForm.getPlantPeriodFrom():"";
		plantPeriodTo = breedGroupForm.getPlantPeriodTo()!=null?breedGroupForm.getPlantPeriodTo():"";
		forcastPeriodFrom = breedGroupForm.getForcastPeriodFrom()!=null?breedGroupForm.getForcastPeriodFrom():"";
		forcastPeriodTo = breedGroupForm.getForcastPeriodTo()!=null?breedGroupForm.getForcastPeriodTo():"";
		breedCategory = breedGroupForm.getBreedCategory()==null?"0":breedGroupForm.getBreedCategory();
	}

%>
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
  }); 
 
function loadData(){
	document.breedGroupForm.cmd.value = "<%=cmd%>";
	document.breedGroupForm.period.value = "";
<%if(!"".equals(cmd) && !"New".equals(cmd) ){%>
		document.breedGroupForm.breedGroupId.value = "<%=breedGroupId%>";
		document.breedGroupForm.breedTypeId.value = "<%=breedTypeId%>";
		document.breedGroupForm.period.value = "<%=period>0?period:""%>";
		document.breedGroupForm.plantPeriodFrom.value = "<%=plantPeriodFrom%>";
		document.breedGroupForm.plantPeriodTo.value = "<%=plantPeriodTo%>";
		document.breedGroupForm.forcastPeriodFrom.value = "<%=forcastPeriodFrom%>";
		document.breedGroupForm.forcastPeriodTo.value = "<%=forcastPeriodTo%>";
		document.breedGroupForm.breedCategory.value = "<%=breedCategory%>";
	<%}%>
	//removeOptionsBlank(document.breedGroupForm.breedTypeId);
    showErrorMessage(); 
    getBreedGroupList('breedTypeId', 'breedGroupId');	
    loadMenu("M13");
}

function saveBreedGroup(){
	if(document.breedGroupForm.breedGroupName.value == "" )
	{
		alert("กรุณาใส่ชื่อกลุ่มพันธุ์ !!");
		document.breedGroupForm.breedGroupName.focus();
		return false;
	}
	if(document.breedGroupForm.breedTypeId.value == "0" ){
		alert("กรุณาเลือกชนิดพืช !!");
		document.breedGroupForm.breedTypeId.focus();
		return false;
	}else{
	    var breedTypeDD = document.getElementById("breedTypeId");
        breedTypeObjName = breedTypeDD.options[breedTypeDD.selectedIndex].text;
        if(breedTypeObjName == "ข้าว"){
			if( document.breedGroupForm.breedCategory.value == "0" ){
				alert("กรุณาเลือกชนิดข้าว");
				document.breedGroupForm.breedCategory.focus();
				return false;
			}
		}
	}
	if(document.breedGroupForm.period.value == "0" || document.breedGroupForm.period.value == "" )
	{
		alert("กรุณากรอกช่วงอายุพันธุ์พืช !!");
		document.breedGroupForm.period.focus();
		return false;
	}
			
	var plantFrom 		= document.breedGroupForm.plantPeriodFrom.value;
	var plantTo 		= document.breedGroupForm.plantPeriodTo.value;
	var forecastFrom	= document.breedGroupForm.forcastPeriodFrom.value;
	var forecastTo 		= document.breedGroupForm.forcastPeriodTo.value;
	
	if(plantFrom>0 && plantTo==0) {
		alert("กรุณาเลือกเดือนที่สิ้นสุดการเพาะปลูก");
		return false;
	}
	if(forecastFrom>0 && forecastTo==0) {
		alert("กรุณาเลือกเดือนที่สิ้นสุดการเก็บเกี่ยว");
		return false;
	}
	if(plantFrom==0 && plantTo>0) {
		alert("กรุณาเลือกเดือนที่เริ่มต้นการเพาะปลูก");
		return false;
	}
	if(forecastFrom==0 && forecastTo>0) {
		alert("กรุณาเลือกเดือนที่เริ่มต้นการเก็บเกี่ยว");
		return false;
	}
	if(plantFrom>0 || plantTo>0 || forecastFrom>0 || forecastTo>0) {
		if( plantFrom <= plantTo ){//ช่วงเพาะปลูกอยู่ในปีเดียวกัน
			if(( forecastFrom < plantFrom ) || ( forecastFrom < plantTo )){//ถ้าช่วงเก็บเกี่ยวอยู่ในช่วงเพาะปลูก
				if(plantFrom < forecastFrom){//ปลูก กับ เก็บเกี่ยว อยู่ภายในปีเดียวกัน
				alert("เดือนที่คาดว่าจะเก็บเกี่ยว ไม่สามารถอยู่ในช่วงเดือนที่เพาะปลูกได้");
				return false;
				}
			}
			if( (plantFrom == forecastFrom)&&(plantTo == forecastTo) ){
			alert("เดือนที่คาดว่าจะเก็บเกี่ยว ไม่สามารถเป็นเดือนเดียวกับเดือนที่เพาะปลูกได้");
			return false;
			}
		}else{//ช่วงเพาะปลูก อยู่ข้ามปี
			if( forecastFrom < plantTo ){//ถ้าช่วงเก็บเกี่ยวอยู่ในช่วงเพาะปลูก
			alert("เดือนที่คาดว่าจะเก็บเกี่ยว ไม่สามารถอยู่ในช่วงเดือนที่เพาะปลูกได้");
			return false;
			}
			if( plantFrom == forecastFrom ){//ถ้าช่วงเก็บเกี่ยวอยู่ในช่วงเพาะปลูก
			alert("เดือนที่คาดว่าจะเก็บเกี่ยว ไม่สามารถเป็นเดือนเดียวกับเดือนที่เพาะปลูกได้");
			return false;
			}
		}
	}
	
	if(document.breedGroupForm.breedGrpName.value == document.breedGroupForm.breedGroupName.value && document.breedGroupForm.breedTypeId.value == document.breedGroupForm.typeId.value)
	{
		alert("ไม่สามารถบันทึกได้เนื่องจากมีกลุ่มพันธุ์ '" + document.breedGroupForm.breedGrpName.value + "' อยู่แล้วในระบบ");
		document.breedGroupForm.breedGroupName.focus();
		return false;
	}
	
	document.breedGroupForm.cmd.value="Save";
	document.breedGroupForm.action= "<%=request.getContextPath()%>/BreedGroup.do";
	document.forms["breedGroupForm"].submit();
}
function closePage(){
   			document.breedGroupListForm.breedGroupName.value = "";
   			document.breedGroupListForm.cmd.value = "Search"; 
   			document.breedGroupListForm.action="<%=request.getContextPath()%>/BreedGroupList.do";
			document.forms["breedGroupListForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
 	<%}%>
 <%}%>
}
function removeOptionsBlank(sel) {
    var par;
    par = sel.options[0].parentNode;
    par.removeChild( sel.options[0] );
}



function checkBreedGroup(){
 		var reqParameters = new Object();
		reqParameters.cmd = "GetBreedGroup";
		document.breedGroupForm.breedGroupName.value = document.breedGroupForm.breedGroupName.value.trim();
		reqParameters.breedGroupName = document.breedGroupForm.breedGroupName.value;
		reqParameters.breedTypeId = document.breedGroupForm.breedTypeId.value;
		reqParameters.breedGroupId = document.breedGroupForm.breedGroupId.value;
		new Ajax.Request("<%=request.getContextPath()%>/BreedGroup.do",
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
				    alert("มีกลุ่มพันธุ์ '" + json[0].breedGroupName + "' อยู่แล้วในระบบ!!");
					document.breedGroupForm.breedGrpName.value = json[0].breedGroupName;
					document.breedGroupForm.typeId.value = json[0].breedTypeId;
				}
			},onFailure: function() {	
			  //	alert('เกิดข้อผิดพลาด');
			}
		});
}


function getBreedGroupList(objTypeName, objGroupName)
{ 
          
           var breedTypeDD = document.getElementById(objTypeName);
           var breedGroupDD = document.getElementById(objGroupName);
            
            breedTypeObjName = breedTypeDD.options[breedTypeDD.selectedIndex].text;
        	if(breedTypeObjName == "ข้าว"){
       		 	 document.getElementById("breedCategory").disabled = false;
       		}else{
        		 document.getElementById("breedCategory").disabled = true;
        		 document.getElementById("breedCategory").value = "0";
        	}
            
            
            if(breedTypeDD.options[breedTypeDD.selectedIndex].value==""){
                  breedGroupDD.options.length = 1;
                  breedGroupDD.options[0].value="0";
                  breedGroupDD.options[0].text="กรุณาเลือก";
                  breedGroupDD.options[0].selected = true;
            }else{
                 //checkBreedGroup();
            }
}


function checkNumber(str)    {
    var RE = /^\d+$/;
    if(str.value!="") {
    	if(!RE.test(str.value)){
    		alert("กรุณาใส่ค่าที่เป็นตัวเลข");
    		document.getElementById(str.id).value="";
    		//$('#'+str.id).css('border', '1px solid red'); // 22.07.2014
    		setTimeout(function() {document.getElementById(str.id).focus();}, 0);
    	}
    }
}


</script>
 
</head>
<body onload="loadData();">
<dcs:validateForm formName="breedGroupForm" formAction="BreedGroup.do" formBean="BreedGroupForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="breedGroupId" />
<input type="hidden" name="breedGrpName" />
<input type="hidden" name="typeId" />
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/BreedGroupList.do'>การจัดการกลุ่มพันธุ์พืช</a></li>
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
                <!-- <p>ข้อมูลกลุ่มพันธุ์พืช</p> -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลกลุ่มพันธุ์พืช</font></span>
                </div>
               
                
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 335px;padding-right:0px">ชื่อกลุ่มพันธุ์ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput">
						    <dcs:validateText  name="breedGroupName" property="breedGroupName" maxlength="100" style="width:245px" onChange="checkBreedGroup();"/>
                        </li>
                    </ul>
                  </div>
                 <div class="line-input-keyin">
                    <ul>
                    	<li class="topic" style="width: 335px;padding-right:0px">ชื่อชนิดพืช :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
	                    <li class="boxinput" style="width:115px">
	                        <dcs:validateDropDown name="breedTypeId" property="breedTypeId" dataSource="<%= breedTypeList %>"  keyField="breedTypeId"  displayField="breedTypeName" style="width:115px" isRequire="true" onChange="getBreedGroupList('breedTypeId', 'breedGroupId');"/>
	                    </li>
	                    <li class="topic" style="width: 70px;padding-right:0px">ช่วงอายุ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:110px">
						    <dcs:validateText name="period" property="period" maxlength="9" style="width: 70px;" onChange="checkNumber(this);"/>&nbsp;&nbsp;วัน
                        </li>
                    </ul>
                 </div>
                 <!--  Added by Yatphiroon.P 28.04.2015 -->
                 <div class="line-input-keyin">
                    <ul>
                    	<li class="topic" style="width: 335px;padding-right:0px">ชนิดข้าว :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
	                    <li class="boxinput" style="width:115px">
	                 		<select id="breedCategory"  name="breedCategory" style="width:115px;">
		  			   		<option value="0">กรุณาเลือก</option>
		     		 			<% for(int i=0; i < RICE_TYPE.split(",").length; i++){ %> 
		    						<option value="<%= i+1 %>"><%= RICE_TYPE.split(",")[i] %></option>
		    					 <%} %>
		  					</select>
	                    </li>
                    </ul>
                 </div>
                 
                 
                 
                 
                 
                 
                <div class="line-input-keyin">
					<ul>
						<li class="topic" style="width: 335px;padding-right:0px">ช่วงการเพาะปลูก :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"></li>
						<li class="boxinput" style="width: 115px">
						<select id="plantPeriodFrom" name="plantPeriodFrom" style="width:115px;">
				            	<option value="0">กรุณาเลือก</option>
				                <option value="01">มกราคม</option>
				                <option value="02">กุมภาพันธ์</option>
				                <option value="03">มีนาคม</option>
				                <option value="04">เมษายน</option>
				                <option value="05">พฤษภาคม</option>
				                <option value="06">มิถุนายน</option>
				                <option value="07">กรกฎาคม</option>
				                <option value="08">สิงหาคม</option>
				                <option value="09">กันยายน</option>
				                <option value="10">ตุลาคม</option>
				                <option value="11">พฤศจิกายน</option>
				                <option value="12">ธันวาคม</option>
				        </select></li>
						
						<li class="topic" style="width: 70px;padding-right:0px">ถึง :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"></li>
						<li class="boxinput" style="width: 115px">
						<select id="plantPeriodTo" name="plantPeriodTo" style="width:115px;">
				           		<option value="0">กรุณาเลือก</option>
				                <option value="01">มกราคม</option>
				                <option value="02">กุมภาพันธ์</option>
				                <option value="03">มีนาคม</option>
				                <option value="04">เมษายน</option>
				                <option value="05">พฤษภาคม</option>
				                <option value="06">มิถุนายน</option>
				                <option value="07">กรกฎาคม</option>
				                <option value="08">สิงหาคม</option>
				                <option value="09">กันยายน</option>
				                <option value="10">ตุลาคม</option>
				                <option value="11">พฤศจิกายน</option>
				                <option value="12">ธันวาคม</option>
				        </select></li>
					</ul>
				</div>
				<div class="line-input-keyin">
					<ul>
						<li class="topic" style="width: 335px;padding-right:0px">ช่วงการเก็บเกี่ยว :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"></li>
						<li class="boxinput" style="width: 115px">
						<select id="forcastPeriodFrom" name="forcastPeriodFrom" style="width:115px;">
				           		<option value="0">กรุณาเลือก</option>
				                <option value="01">มกราคม</option>
				                <option value="02">กุมภาพันธ์</option>
				                <option value="03">มีนาคม</option>
				                <option value="04">เมษายน</option>
				                <option value="05">พฤษภาคม</option>
				                <option value="06">มิถุนายน</option>
				                <option value="07">กรกฎาคม</option>
				                <option value="08">สิงหาคม</option>
				                <option value="09">กันยายน</option>
				                <option value="10">ตุลาคม</option>
				                <option value="11">พฤศจิกายน</option>
				                <option value="12">ธันวาคม</option>
				           
				        </select></li>
						<li class="topic" style="width: 70px;padding-right:0px">ถึง :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"></li>
						<li class="boxinput" style="width: 115px">
						<select id="forcastPeriodTo" name="forcastPeriodTo" style="width:115px;">
				           		<option value="0">กรุณาเลือก</option>
				                <option value="01">มกราคม</option>
				                <option value="02">กุมภาพันธ์</option>
				                <option value="03">มีนาคม</option>
				                <option value="04">เมษายน</option>
				                <option value="05">พฤษภาคม</option>
				                <option value="06">มิถุนายน</option>
				                <option value="07">กรกฎาคม</option>
				                <option value="08">สิงหาคม</option>
				                <option value="09">กันยายน</option>
				                <option value="10">ตุลาคม</option>
				                <option value="11">พฤศจิกายน</option>
				                <option value="12">ธันวาคม</option>
				        </select></li>
					</ul>
				</div>
						<table align="center">
                             <tr><td colspan="2">&nbsp;</td></tr>
                             <tr><td><a class="btn-save" onclick="saveBreedGroup();" id="write"></a></td>  
                                 <td><a class="btn-cancel" onclick="closePage();"></a></td>                         
                             </tr>
                  		</table>
		    </div>  
        </div>
    </div> 
</div>
 <%@include file="/footer.jsp" %>
</dcs:validateForm>
<form name="breedGroupListForm"  method="post" action="<%=request.getContextPath()%>/BreedGroupList.do">
<input type="hidden" name="breedGroupName" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>
