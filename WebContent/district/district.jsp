<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.DistrictForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
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

	List regionList = (ArrayList) session.getAttribute ("regionList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	
	DistrictForm districtForm = (DistrictForm)request.getAttribute("DistrictForm");
	String thName = "", enName="", cmd = "", msg = ""; 
	long regionNo = 0, provinceNo = 0, districtNo = 0;
	String  prevRegionNo = "";
	
	if(districtForm !=null){
		msg = districtForm.getMsg()==null?"":districtForm.getMsg();
		cmd = districtForm.getCmd() == null?"":districtForm.getCmd();
		if(!"".equals(districtForm.getRegionNo()))
	    	regionNo = Long.parseLong(districtForm.getRegionNo() == null?"0":districtForm.getRegionNo());
	    if(!"".equals(districtForm.getProvinceNo()))
			provinceNo = Long.parseLong(districtForm.getProvinceNo() == null?"0":districtForm.getProvinceNo());
		if(!"".equals(districtForm.getDistrictNo()))
			districtNo = Long.parseLong(districtForm.getDistrictNo() == null?"0":districtForm.getDistrictNo());
		thName = districtForm.getThaiName() == null?"":districtForm.getThaiName();
		enName = districtForm.getEngName() == null?"":districtForm.getEngName();
		prevRegionNo = districtForm.getPrevRegionNo();

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
      	     	//$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
 	 });
  }); 

function loadData(){
	var command = "<%=cmd%>";
	document.districtForm.districtNo.value = "<%= districtNo %>";
	document.districtForm.regionNo.value   = "<%= regionNo %>";
	getProvinceInfo('regionNo','provinceNo','districtNo')
	document.districtForm.provinceNo.value = "<%= provinceNo %>";
   	document.districtForm.thaiName.value = "<%= thName %>";
   	document.districtForm.engName.value = "<%= enName %>";
   	<% if(prevRegionNo !=null && !"".equals(prevRegionNo)){ 	%>
   			document.districtForm.prevRegionNo.value = "<%= prevRegionNo %>";
   	<% } else { %>
   		    document.districtForm.prevRegionNo.value = "<%= regionNo %>";
   	<% } %>
   	document.districtForm.prevProvinceNo.value = "<%= provinceNo %>";
	
    showErrorMessage(); 	
    loadMenu("M04");	
}
function saveDistrict(){
		document.districtForm.thaiName.value = document.districtForm.thaiName.value.trim();
		document.districtForm.engName.value = document.districtForm.engName.value.trim();
		if(document.districtForm.thaiName.value =='' && document.districtForm.engName.value =='' && document.districtForm.provinceNo.value==0 && document.districtForm.regionNo.value==0){
			alert("กรุณากรอกข้อมูล");	
			document.districtForm.thaiName.focus();
		 	return false; 	
		}
		if(document.districtForm.thaiName.value ==''){
		 	alert("กรุณาใส่ชื่อเขต/อำเภอ เป็นภาษาไทย");	
		 	document.districtForm.thaiName.focus();
		 	return false; 	
		}
		if(document.districtForm.engName.value ==''){
		 	alert("กรุณาใส่ชื่อเขต/อำเภอ เป็นภาษาอังกฤษ");	
		 	document.districtForm.engName.focus();
		 	return false; 	
		}
		if(document.districtForm.regionNo.value==0){
		 	alert("กรุณาเลือกภูมิภาค");
		 	return false;
		}
		if(document.districtForm.provinceNo.value==0){
		 	alert("กรุณาเลือกจังหวัด");
		 	return false;
		}
		
	    document.districtForm.cmd.value="Save";
	    document.districtForm.action= "<%=request.getContextPath()%>/District.do";
	    document.forms["districtForm"].submit();
}

    function checkThai(str)    {
        var RE = /^[ก-์]+$/;
        if(RE.test(str.value)){
        }else{
            alert("กรุณาใส่ชื่อเขต/อำเภอ เป็นภาษาไทย");
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
            alert("กรุณาใส่ชื่อเขต/อำเภอ เป็นภาษาอังกฤษ");
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            document.getElementById(str.id).focus();
            }, 0);
        }
    }

function showErrorMessage(){
	<% if(!"".equals(msg)){ %>
   	    alert("<%= msg %>");
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
   <%}}%>
}

function closePage(){ 
		document.districtListForm.regionNo.value = 0; 
		document.districtListForm.provinceNo.value = 0; 
		document.districtListForm.districtName.value = ""; 
   		document.districtListForm.cmd.value = "Search"; 
   		document.districtListForm.action="<%=request.getContextPath()%>/DistrictList.do";
		document.forms["districtListForm"].submit();
}


function getProvince(regionObj){
    // get province list from regionCode	
	var regionCode = regionObj.value;
	document.districtForm.cmd.value="GetProvince";
	document.districtForm.action= "<%=request.getContextPath()%>/District.do";
	document.forms["districtForm"].submit();
	
	
}
function getProvinceInfo(objRName, objPName)
{           
 
          var regionDD 		= document.getElementById(objRName);
          var provinceDD 	= document.getElementById(objPName);
          
            
            if(regionDD.options[regionDD.selectedIndex].value==''){ //ถ้ายังไม่เลือกภูมิภาค ให้จังหวัดเป็นกรุณาเลือก
                  provinceDD.options.length = 1;
                  provinceDD.options[0].value="";
                  provinceDD.options[0].text="กรุณาเลือก";
                  provinceDD.options[0].selected = true;
            }else{														//ถ้าเลือกภูมิภาคแล้ว เข้าหลังบ้าน
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetProvince";
                  reqParameters.regionNo = regionDD.options[regionDD.selectedIndex].value;   
                  new Ajax.Request("<%=request.getContextPath()%>/District.do",
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
                           //   alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
                        }
                  });
            }
}


</script>  
</head>
<body onload = "loadData();">
<dcs:validateForm formName="districtForm" formAction="District.do" formBean="DistrictForm">
<input type = "hidden" name="districtNo" />
<input type = "hidden" name="cmd" />
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/DistrictList.do'>การจัดการอำเภอ</a></li>
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
                <!-- <p>ข้อมูลอำเภอ</p> -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลอำเภอ</font></span>
                </div>
               
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 380px;padding-right:0px">ชื่ออำเภอ(ภาษาไทย) :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" >
	                    		<dcs:validateText name="thaiName" property="thaiName" maxlength="200" style="width:190px;" onChange="checkThai(this);"/>
	                    </li>
                    </ul>
                </div>
				<div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 380px;padding-right:0px">ชื่ออำเภอ(ภาษาอังกฤษ) :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" >
	                    		<dcs:validateText name="engName" property="engName" maxlength="200" style="width:190px;" onChange="checkEng(this);"/>
	                    </li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 380px;padding-right:0px">ภูมิภาค :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput">
						    <dcs:validateDropDown name="regionNo"  property="regionNo" dataSource="<%= regionList %>" keyField="regionNo"  displayField="regionName" style="width:197px;"  isRequire="true" onChange="getProvinceInfo('regionNo','provinceNo','districtNo')"  />
                        </li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 380px;padding-right:0px">จังหวัด :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:170px">
                            <dcs:validateDropDown name="provinceNo" property="provinceNo" dataSource="<%= provinceList %>"  keyField="provinceNo"  isRequire="true" displayField="thaiName" style="width:197px;" />
                        </li>
                    </ul>
                </div>
                <div class="clear"></div>
                
           			<table align="center">
			          
			           <tr><td colspan="2">&nbsp;</td></tr>
			           <tr><td><a class="btn-save" onclick="saveDistrict();" id="write"></a></td>	
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
<input type="hidden" name="prevProvinceNo" />
<input type="hidden" name="prevRegionNo" />
</dcs:validateForm>
<form name="districtListForm"  method="post" action="<%=request.getContextPath()%>/DistrictList.do">
<input type="hidden" name="districtName" />
<input type="hidden" name="provinceNo" />
<input type="hidden" name="regionNo" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>