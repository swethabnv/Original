<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.SubDistrictForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>

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
	
    SubDistrictForm subDistrictForm = (SubDistrictForm)request.getAttribute("SubDistrictForm");
	List regionList = (ArrayList) session.getAttribute ("regionList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");

	String thName = "", enName="", cmd = "", msg = ""; 
	long regionNo = 0, provinceNo = 0, districtNo=0, subDistrictNo = 0;
	String  prevRegionNo = "", prevProvinceNo = "", prevDistrictNo = "", prevSubDistrictNo = "";
	if(subDistrictForm !=null){
		msg = subDistrictForm.getMsg() == null?"":subDistrictForm.getMsg();
		cmd = subDistrictForm.getCmd() == null?"":subDistrictForm.getCmd();
	   
		thName = subDistrictForm.getThaiName() == null?"":subDistrictForm.getThaiName();
		enName = subDistrictForm.getEngName() == null?"":subDistrictForm.getEngName();
		
		if(!"".equals(subDistrictForm.getRegionNo()))
			regionNo = Long.parseLong(subDistrictForm.getRegionNo() == null?"0":subDistrictForm.getRegionNo());
		if(!"".equals(subDistrictForm.getProvinceNo()))
			provinceNo = Long.parseLong(subDistrictForm.getProvinceNo() == null?"0":subDistrictForm.getProvinceNo());
		if(!"".equals(subDistrictForm.getDistrictNo()))
			districtNo = Long.parseLong(subDistrictForm.getDistrictNo() == null?"0":subDistrictForm.getDistrictNo());
		if(!"".equals(subDistrictForm.getSubDistrictNo()))
			subDistrictNo = Long.parseLong(subDistrictForm.getSubDistrictNo() == null?"0":subDistrictForm.getSubDistrictNo());
	
		prevRegionNo = subDistrictForm.getPrevRegionNo();
		prevProvinceNo = subDistrictForm.getPrevProvinceNo();
		prevDistrictNo = subDistrictForm.getPrevDistrictNo();
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
    showErrorMessage();
    <% if(cmd!="" && subDistrictForm!=null) { %>
	document.subDistrictForm.subDistrictNo.value = "<%= subDistrictNo %>";
    document.subDistrictForm.regionNo.value = "<%= regionNo %>";
    getProvinceInfo('regionNo','provinceNo','districtNo');
    document.subDistrictForm.provinceNo.value = "<%= provinceNo %>";
    getDistrictInfo('regionNo','provinceNo','districtNo');
    document.subDistrictForm.districtNo.value = "<%= districtNo %>";
   	document.subDistrictForm.thaiName.value = "<%= thName+"" %>";
    document.subDistrictForm.engName.value = "<%= enName+"" %>";
    <% } %>
    <% if(prevRegionNo !=null && !"".equals(prevRegionNo)){ 	%>
   			document.subDistrictForm.prevRegionNo.value = "<%= prevRegionNo %>";
   	<% } else { %>
   		    document.subDistrictForm.prevRegionNo.value = "<%= regionNo %>";
   	<% } %>
   	document.subDistrictForm.prevProvinceNo.value = "<%= provinceNo %>";
    document.subDistrictForm.prevDistrictNo.value = "<%= districtNo %>";
    loadMenu("M03");
}
function save(){
		document.subDistrictForm.thaiName.value = document.subDistrictForm.thaiName.value.trim();
		document.subDistrictForm.engName.value = document.subDistrictForm.engName.value.trim();
		if(document.subDistrictForm.thaiName.value =='' && document.subDistrictForm.engName.value =='' && document.subDistrictForm.provinceNo.value==0 && document.subDistrictForm.regionNo.value==0 && document.subDistrictForm.districtNo.value==0 && document.subDistrictForm.postCode.value==''){
			alert("กรุณากรอกข้อมูล");	
			document.subDistrictForm.thaiName.focus();
		 	return false; 	
		}
		if(document.subDistrictForm.thaiName.value ==''){
		 	alert("กรุณาใส่ชื่อตำบล เป็นภาษาไทย");	
		 	document.subDistrictForm.thaiName.focus();
		 	return false; 	
		}
		if(document.subDistrictForm.engName.value ==''){
		 	alert("กรุณาใส่ชื่อตำบล เป็นภาษาอังกฤษ");
		 	document.subDistrictForm.engName.focus();	
		 	return false; 	
		}
		if(document.subDistrictForm.postCode.value==''){
		 	alert("กรุณากรอกรหัสไปรษณีย์");
		 	document.subDistrictForm.postCode.focus();
		 	return false;
		}
		if(document.subDistrictForm.regionNo.value==0){
		 	alert("กรุณาเลือกภูมิภาค");
		 	document.subDistrictForm.regionNo.focus();
		 	return false;
		}
		if(document.subDistrictForm.provinceNo.value==0){
		 	alert("กรุณาเลือกจังหวัด");
		 	document.subDistrictForm.provinceNo.focus();
		 	return false;
		}
		if(document.subDistrictForm.districtNo.value==0){
		 	alert("กรุณาเลือกเขต/อำเภอ");
		 	document.subDistrictForm.districtNo.focus();
		 	return false;
		}
		
	    document.subDistrictForm.cmd.value="Save";
	    document.subDistrictForm.action= "<%=request.getContextPath()%>/SubDistrict.do";
	    document.subDistrictForm.submit();
}

function showErrorMessage(){
	<% if(!"".equals(msg)){ %>
   		 alert("<%=msg%>"); 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
    <% }} %>
}

    function checkThai(str)    {
        var RE = /^[ก-์]+$/;
        if(RE.test(str.value)){
        }else{
            alert("กรุณาใส่ชื่อตำบล เป็นภาษาไทย");
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
            alert("กรุณาใส่ชื่อตำบล เป็นภาษาอังกฤษ");
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            document.getElementById(str.id).focus();
            }, 0);
        }
    }

function closePage(){ 
		document.subDistrictListForm.regionNo.value = 0; 
		document.subDistrictListForm.provinceNo.value = 0; 
		document.subDistrictListForm.districtNo.value = 0; 
   		document.subDistrictListForm.cmd.value = "Search"; 
   		document.subDistrictListForm.action="<%=request.getContextPath()%>/SubDistrictList.do";
		document.forms["subDistrictListForm"].submit();
}

function getProvinceInfo(objRName, objPName, objDName)
{           
          var regionDD = document.getElementById(objRName);
          var provinceDD = document.getElementById(objPName);
          var districtDD = document.getElementById(objDName);
          
          districtDD.options.length = 1;
          districtDD.options[0].value="0";
          districtDD.options[0].text="กรุณาเลือก";
          districtDD.options[0].selected = true;
            
           
            
            if(regionDD.options[regionDD.selectedIndex].value=='0'){ //ถ้ายังไม่เลือกภูมิภาค ให้จังหวัดเป็นกรุณาเลือก
                  provinceDD.options.length = 1;
                  provinceDD.options[0].value="0";
                  provinceDD.options[0].text="กรุณาเลือก";
                  provinceDD.options[0].selected = true;
            }else{														//ถ้าเลือกภูมิภาคแล้ว เข้าหลังบ้าน
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetProvince";
                  reqParameters.regionNo = regionDD.options[regionDD.selectedIndex].value;   
                  new Ajax.Request("<%=request.getContextPath()%>/SubDistrictList.do",
                  {     
                        method: 'post',
                        asynchronous: false,
                        parameters: reqParameters,
                        encoding: 'UTF-8',
                        onSuccess: function(transport)
                        {                             
                            var json = transport.responseText.evalJSON(); 
                              if(json !=null && json != "0"){
                                    provinceDD.options.length = json.length+1;
                                    provinceDD.options[0].value="";
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

function getDistrictInfo(objRName, objPName, objDName)
{ 
			var provinceDD = document.getElementById(objPName);
			var regionDD = document.getElementById(objRName);
			var districtDD = document.getElementById(objDName);
				          
            
            if(provinceDD.options[provinceDD.selectedIndex].value=='0'){ //ถ้ายังไม่เลือกจังหวัด อำเภอเป็นกรุณาเลือก
                  districtDD.options.length = 1;
                  districtDD.options[0].value="0";
                  districtDD.options[0].text="กรุณาเลือก";
                  districtDD.options[0].selected = true;
            }else{														//ถ้าเลือกจังหวัดแล้ว เข้าหลังบ้าน
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetDistrict";
                  reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;   
                  new Ajax.Request("<%=request.getContextPath()%>/SubDistrictList.do",
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
}

function checkPostCode(str)
{
	var RE = /^\d+$/;
	len = document.subDistrictForm.postCode.value.length;
	    if(RE.test(str.value)){
	    	if(len != 5){
		    	alert("กรุณาใส่รหัสไปรษณีย์ 5 หลัก");
		    	document.subDistrictForm.postCode.focus();
		    }else{
		    	
		    }
		}else{
			alert("กรุณาใส่รหัสไปรษณีย์เป็นตัวเลข");
			document.subDistrictForm.postCode.value="";
			document.subDistrictForm.postCode.focus();
		}
	window.setTimeout(function () {document.getElementById('postCode').focus();}, 0);
}

</script> 
</head>
<body onload="loadData();">
<dcs:validateForm formName="subDistrictForm" formAction="SubDistrict.do" formBean="SubDistrictForm">
<input type="hidden" name="subDistrictNo" />
<input type="hidden" name="cmd" />
<div class="main-inside">
<!-- insert header -->

		<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/SubDistrictList.do'>การจัดการตำบล</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		           <li>กรอกข้อมูล</li>
		     	</ul>
	 	 	</div>
    	</div>	
    	
<!-- insert header -->
    <div class="content">
    	<div class="inside">
        
        	<div class="content-keyin">
                <div class="search-header" >
                <!-- <p>ข้อมูลตำบล</p>  -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลตำบล</font></span>
                </div>
               
                
                
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width:380px;padding-right:0px;">ชื่อตำบล(ภาษาไทย) :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" >
	                   		<dcs:validateText name="thaiName" property="thaiName" maxlength="200" style="width:190px;" onChange="checkThai(this);"/>
	                    </li>
                    </ul>
                </div>
				<div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 380px;padding-right:0px">ชื่อตำบล(ภาษาอังกฤษ) :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" >
	                   		<dcs:validateText name="engName" property="engName" maxlength="200" style="width:190px;" onChange="checkEng(this);"/>
	                    </li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 380px;padding-right:0px">รหัสไปรษณีย์ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" >
	                   		<dcs:validateText name="postCode" property="postCode" maxlength="5" style="width:190px;" onChange="checkPostCode(this)"/>
	                    </li>
                    </ul>
                </div>
				 <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width:380px;padding-right:0px;">ภูมิภาค :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput">
						    <dcs:validateDropDown name="regionNo" dataSource="<%= regionList %>" property="regionNo" keyField="regionNo"  displayField="regionName" style="width:197px;" onChange="getProvinceInfo('regionNo','provinceNo','districtNo')" isRequire="true"/>
                        </li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width:380px;padding-right:0px;">จังหวัด :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:170px;">
                            <dcs:validateDropDown name="provinceNo" dataSource="<%= provinceList %>" property="provinceNo" keyField="provinceNo"  displayField="thaiName" style="width:197px;" onChange="getDistrictInfo('regionNo','provinceNo','districtNo')" isRequire="true"/>
                        </li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width:380px;padding-right:0px;">อำเภอ :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:170px;">
                            <dcs:validateDropDown name="districtNo" dataSource="<%= districtList %>" property="districtNo" keyField="districtNo"  displayField="thaiName" style="width:197px;" isRequire="true"/>
                        </li>
                    </ul>
                </div>
               <div class="clear"></div>
           		<table align="center">
			    
			    <tr><td colspan="2">&nbsp;</td></tr>
			    <tr><td><a class="btn-save" href="#" onclick="save();" id="write"></a></td>	
				    <td><a class="btn-cancel" href="#" onclick="closePage();"></a></td>			           
			    </tr>
			    </table>
             <div class="clear"></div>
           </div>
    </div>
	</div>
	<!--footer -->
    <%@include file="/footer.jsp" %>
</div>
<input type="hidden" name="prevDistrictNo" />
<input type="hidden" name="prevProvinceNo" />
<input type="hidden" name="prevRegionNo" />

</dcs:validateForm>


<form name="subDistrictListForm" method="post" action="<%=request.getContextPath()%>/SubDistrictList.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="provinceNo" />
<input type="hidden" name="regionNo" />
<input type="hidden" name="districtNo" />
<input type="hidden" name="subDistrictNo" />
</form>

</body>
</html>