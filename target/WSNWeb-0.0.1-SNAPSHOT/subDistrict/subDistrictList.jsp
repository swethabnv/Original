<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.SubDistrictListForm"%>
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

<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);

    SubDistrictListForm subDistrictListForm = (SubDistrictListForm)request.getAttribute("SubDistrictListForm");
	List regionList = (ArrayList) session.getAttribute ("regionList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	List subDistrictList = (ArrayList) session.getAttribute("subDistrictList");
	
	String msg = "";
	if(subDistrictListForm!=null){
		if(!"".equals(subDistrictListForm.getErrMessage())){
		  	msg = subDistrictListForm.getErrMessage();
		}
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
  }); 
  
function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delSubDistrictNo');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบตำบล/แขวงที่เลือก ใช่หรือไม่?")) {
				document.subDistrictListForm.target="";
				document.subDistrictListForm.cmd.value="Delete";
				document.subDistrictListForm.action="SubDistrictList.do";
				document.forms["subDistrictListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกรลบตำบล/แขวงที่ต้องการลบ');
		}
	}else{
		document.subDistrictListForm.cmd.value="Search";
		document.forms["subDistrictListForm"].submit();
	}
}

function edit(regionNo, provinceNo, districtNo, subDistrictNo, thaiName, engName, postCode){
    document.subDistrictForm.regionNo.value = regionNo; 
    document.subDistrictForm.provinceNo.value = provinceNo;
    document.subDistrictForm.districtNo.value = districtNo;
    document.subDistrictForm.subDistrictNo.value = subDistrictNo;
    document.subDistrictForm.thaiName.value = thaiName;
	document.subDistrictForm.engName.value = engName;
	document.subDistrictForm.postCode.value = postCode;
	document.subDistrictForm.cmd.value = "Edit";
	document.subDistrictForm.action= "<%=request.getContextPath()%>/SubDistrict.do";
	document.forms["subDistrictForm"].submit();
}

function addSubDistrict(){
	  
        document.subDistrictForm.cmd.value="New";
	    document.subDistrictForm.action= "<%=request.getContextPath()%>/SubDistrict.do";
	    document.forms["subDistrictForm"].submit();

}
function searchSubDistrict(){
	    document.subDistrictListForm.cmd.value="Search";
	    document.subDistrictListForm.action= "<%=request.getContextPath()%>/SubDistrictList.do";
	    document.forms["subDistrictListForm"].submit();
}



function getProvince(regionObj){
    // get province list from regionCode	
	var regionCode = regionObj.value;
	document.subDistrictListForm.cmd.value="GetProvince";
	document.subDistrictListForm.action= "<%=request.getContextPath()%>/SubDistrictList.do";
	document.forms["subDistrictListForm"].submit();
	
	
}


function getDistrict(provObj){
    // get district list from province Code	
	var provinceCode = provObj.value;
	document.subDistrictListForm.cmd.value="GetDistrict";
	document.subDistrictListForm.action= "<%=request.getContextPath()%>/SubDistrictList.do";
	document.forms["subDistrictListForm"].submit();
	
	
}

function removeOptionsBlank(sel) {
    var par;
    par = sel.options[0].parentNode;
    par.removeChild( sel.options[0] );
}

function getProvinceInfo(objRName, objPName, objDName)
{           
 
          var regionDD = document.getElementById(objRName);
          var provinceDD = document.getElementById(objPName);
          var districtDD = document.getElementById(objDName);
          
          districtDD.options.length = 1;
          districtDD.options[0].value="";
          districtDD.options[0].text="กรุณาเลือก";
          districtDD.options[0].selected = true;
            
           
            
            if(((regionDD.options[regionDD.selectedIndex].value=='') || (regionDD.options[regionDD.selectedIndex].value==0))){ //ถ้ายังไม่เลือกภูมิภาค ให้จังหวัดเป็นกรุณาเลือก
                  provinceDD.options.length = 1;
                  provinceDD.options[0].value="";
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
                              alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
                        }
                  });
            }
}

function getDistrictInfo(objRName, objPName, objDName)
{ 
			var provinceDD = document.getElementById(objPName);
			var regionDD = document.getElementById(objRName);
			var districtDD = document.getElementById(objDName);
				          
            
            if(provinceDD.options[provinceDD.selectedIndex].value==''){ //ถ้ายังไม่เลือกจังหวัด อำเภอเป็นกรุณาเลือก
                  districtDD.options.length = 1;
                  districtDD.options[0].value="";
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
                              alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
                        }
                  });
            }
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 	<%}%>
 	loadMenu("M03");
}
</script>
</head>  
<body onload="showErrorMessage();">
<dcs:validateForm formName="subDistrictListForm" formAction="SubDistrictList.do" formBean="SubDistrictListForm">
<input type="hidden" name="cmd" value="DirtyList" />
<input type="hidden" name="subDistrictNo" />
<input type="hidden" name="postCode" />
<div class="main-inside">
<%@include file="/header.jsp" %>
				<div class="navigator">
	    			<div class="inside">
				 		<ul>
				            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
				            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
				            <li><a style="color:#333" href='<%=request.getContextPath()%>/SubDistrictList.do'>การจัดการตำบล</a></li>
							<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
				           <li>ค้นหาข้อมูล</li>
				     	</ul>
			 	 	</div>
    			</div>	
<!-- insert header -->
	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        
        	<div class="content-search">
                <div class="search-header" style="text-align: left">
               <!--  <p>ค้นหาตำบล</p>  -->
                &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาตำบล</font></span>
                </div>
                <div class="line-input">
                <ul>
                 <li class="topic" style="width:120px;">ภูมิภาค : </li>
                        <li class="boxinput" style="width:170px">
                            <dcs:validateDropDown name="regionNo" dataSource="<%= regionList %>" property="regionNo" keyField="regionNo" displayField="regionName" style="width:170px;" onChange="getProvinceInfo('regionNo','provinceNo','districtNo')"  isRequire="true"/>
                        </li>
                
                	<li class="topic" style="width:80px;">จังหวัด : </li>
                        <li class="boxinput" style="width:170px;">
                            <dcs:validateDropDown name="provinceNo" dataSource="<%= provinceList %>" property="provinceNo" keyField="provinceNo" displayField="thaiName" style="width:170px;" onChange="getDistrictInfo('regionNo','provinceNo','districtNo')" isRequire="true"/>
                        </li>
                    <li class="topic" style="width:80px">อำเภอ : </li>
                        <li class="boxinput" style="width:170px;">
                            <dcs:validateDropDown name="districtNo" dataSource="<%= districtList %>" property="districtNo" keyField="districtNo"  displayField="thaiName" style="width:170px;" isRequire="true"/>
                        </li>
                </ul>
                
                <ul>
                		<li class="topic" style="width:120px">ชื่อตำบล : </li>
                		<li class="boxinput" style="width:543px;">
                		<dcs:validateText name="subDistrictName" property="subDistrictName" maxlength="200" style="width:163px;"/>
                		</li>
                </ul>
                 <ul class="btn-search-box">
                		<li>
                			<button class="btn-search" onclick="searchSubDistrict();"></button>
                    	</li>
                </ul>
                    
                </div>
                
            	<!--<div class="line-input">           
                	<ul class="btn-search-box" style="padding-left:725px;">
                    	<li>
                			<button class="btn-search" onclick="searchSubDistrict();"></button>
                    	</li>
                    </ul>
                </div>
			-->
			</div>

            <div class="btn-manage" style="margin-left:0px;">
            	     <a class="btn-add" onclick="addSubDistrict();" id="write"></a>
            		 <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
            </div>
            
            
            <table border="0" width="96%" ><tr>
           
            
            <%   if(subDistrictList!=null){ %>
            <td><dcs:grid dataSource="<%= subDistrictList %>" name="subDistrictList" pageSize="<%= subDistrictListForm.getDisplayRow()%>" width="100%">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                <dcs:checkboxcolumn name="delSubDistrictNo" dataField="subDistrictNo" headerText="" width="80"  HAlign="left" /> 
                 <dcs:textcolumn dataField="thaiName" style="cursor:pointer;" headerText="ตำบล" width="200" sortable="true" HAlign="left" cssClass="address-sr"  onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{subDistrictNo}','{thaiName}','{engName}','{postCode}');"/>
                 <dcs:textcolumn dataField="districtName" style="cursor:pointer;" headerText="อำเภอ" width="200" sortable="true" HAlign="left" cssClass="address-sr"  onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{subDistrictNo}','{thaiName}','{engName}','{postCode}');"/>
                 <dcs:textcolumn dataField="provinceName" style="cursor:pointer;" headerText="จังหวัด" width="200" sortable="true" HAlign="left" cssClass="address-sr"  onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{subDistrictNo}','{thaiName}','{engName}','{postCode}');"/>
                 <dcs:textcolumn dataField="regionName" style="cursor:pointer;" headerText="ภูมิภาค" width="200" sortable="true" HAlign="left" cssClass="address-sr"  onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{subDistrictNo}','{thaiName}','{engName}','{postCode}');"/>
                 <dcs:imagecolumn dataField="linkImageEdit" style="padding-top:2px;cursor:pointer;" toolTip="แก้ไข" headerText=""  width="30"  HAlign="center" cssClass="manage-sr" onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{subDistrictNo}','{thaiName}','{engName}','{postCode}');"/>
                 </dcs:grid>
            </td>
            <% } %>
            
            
            </tr>
            </table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countSubDistrict"))%> รายการ</div>
        </div>
    </div>
    
    <%@include file="/footer.jsp" %>

<!-- end for header -->
</div>

</dcs:validateForm>

<form name="subDistrictForm" method="post" action="<%=request.getContextPath()%>/SubDistrict.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="regionNo" />
<input type="hidden" name="provinceNo" />
<input type="hidden" name="districtNo" />
<input type="hidden" name="subDistrictNo" />
<input type="hidden" name="thaiName" />
<input type="hidden" name="engName" />
<input type="hidden" name="postCode" />
</form>
</body>
</html>