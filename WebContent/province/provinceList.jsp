<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.ProvinceListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.Province"%>
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

	ProvinceListForm provinceListForm = (ProvinceListForm) request.getAttribute("ProvinceListForm");
	List regionList = (ArrayList) session.getAttribute ("regionList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	
	String msg = "";
	if(provinceListForm!=null){
		if(!"".equals(provinceListForm.getErrMessage())){
		  	msg = provinceListForm.getErrMessage();
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
		var checkList = document.getElementsByName('delProvinceNo');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบจังหวัดที่เลือกใช่หรือไม่?")) {
				document.provinceListForm.target="";
				document.provinceListForm.cmd.value="Delete";
				document.provinceListForm.action="ProvinceList.do";
				document.forms["provinceListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกจังหวัดที่ต้องการลบ');
		}
	}else{
		document.provinceListForm.cmd.value="Search";
		document.forms["provinceListForm"].submit();
	}
}

function edit(regionNo, provinceNo, thName, enName){
    document.provinceForm.cmd.value = "Edit"; 
    document.provinceForm.regionNo.value = regionNo; 
    document.provinceForm.provinceNo.value = provinceNo;
	document.provinceForm.thaiName.value = thName;
	document.provinceForm.engName.value = enName;
	document.provinceForm.action= "<%=request.getContextPath()%>/Province.do";
	document.forms["provinceForm"].submit();
}

function addProvince(){
        document.provinceForm.cmd.value="New";
	    document.provinceForm.action= "<%=request.getContextPath()%>/Province.do";
	    document.forms["provinceForm"].submit();
}
function searchProvince(){
	    document.provinceListForm.cmd.value="Search";
	    document.provinceListForm.action= "<%=request.getContextPath()%>/ProvinceList.do";
	    document.forms["provinceListForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 	<%}%>
 	loadMenu("M05");
}
</script>

</head>  
<body onload="showErrorMessage();">
<dcs:validateForm formName="provinceListForm" formAction="ProvinceList.do" formBean="ProvinceListForm">
<input type="hidden" name="cmd" value="DirtyList" />
<input type="hidden" name="provinceNo" />
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
             <!-- <p>ค้นหาจังหวัด</p>  -->
             &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาจังหวัด</font></span>            
             </div>
             <div class="line-input">
	             <ul>
		             <li class="topic" style="width: 200px">ภูมิภาค : </li>
		             <li class="boxinput" style="width:200px">
		             <dcs:validateDropDown name="regionNo" property="regionNo" dataSource="<%= regionList %>" keyField="regionNo"  displayField="regionName" style="width:200px;" isRequire="true"/>
		             </li>
		                        
		             <li class="topic" style="width: 100px">ชื่อจังหวัด : </li>
		             <li class="boxinput" style="width:150px">
		             <dcs:validateText name="provinceName" property="provinceName" maxlength="200" style="width:160px;"/>
		             </li>
	             </ul>
	             <ul class="btn-search-box" >
		             <li>
		             <button class="btn-search" onclick="searchProvince();" style="padding-top: 0px;"></button>
		             </li>
	             </ul>
             </div>
	    </div>
        <div class="btn-manage" style="margin-left:0px;">
             <a class="btn-add" href="#" onclick="addProvince();" id="write"></a>
             <a class="btn-del" href="#" onclick="goPage('Delete');" id="del"></a>
        </div><div class="clear"></div>
      
            
            <!-- dcsGrid -->
            <table border="0" width="96%" ><tr>
            <%  if(provinceList!=null){  %>
            <td><dcs:grid dataSource="<%= provinceList %>" name="provinceList" pageSize="<%=provinceListForm.getDisplayRow()%>" width="100%">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delProvinceNo" dataField="provinceNo" headerText="" width="500"  HAlign="left" />
                 <dcs:textcolumn dataField="thaiName" headerText="จังหวัด" style="cursor:pointer;" width="500" sortable="true" HAlign="left" cssClass="address-sr" onClick="edit('{regionNo}','{provinceNo}','{thaiName}','{engName}');"/>
                
                 <dcs:textcolumn dataField="regionName" headerText="ภูมิภาค" style="cursor:pointer;" width="650" sortable="true" HAlign="left" cssClass="tel-sr" onClick="edit('{regionNo}','{provinceNo}','{thaiName}','{engName}');"/>
                 <dcs:imagecolumn dataField="linkImageEdit" headerText=""  toolTip="แก้ไข" width="30"  style="padding-top:2px;cursor:pointer;" HAlign="center" cssClass="manage-sr" onClick="edit('{regionNo}','{provinceNo}','{thaiName}','{engName}');"/>
                 
                 </dcs:grid>
            </td>
            <%  } 
            %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countProvince"))%> รายการ</div>
            <!-- dcsGrid -->
             <div class="clear"></div>
            
        </div>
    </div>
    
		<!--footer -->
    <%@include file="/footer.jsp" %>
    <!-- end insert >> content --> 

<!-- end for header -->
</div>
</dcs:validateForm>
<form name="provinceForm" method="post" action="<%=request.getContextPath()%>/Province.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="regionNo" />
<input type="hidden" name="provinceNo" />
<input type="hidden" name="thaiName" />
<input type="hidden" name="engName" />

</form>
</body>
</html>