<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.FarmerListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
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

	FarmerListForm  farmerListForm = (FarmerListForm) request.getAttribute("FarmerListForm");
	List farmerList = (ArrayList) session.getAttribute ("farmerList");
	List branchList = (ArrayList) session.getAttribute("userBranchList");
	String msg = "", branchCode ="";
	if(farmerListForm!=null){
		if(!"".equals(farmerListForm.getErrMessage())){
		  	msg = farmerListForm.getErrMessage();
		  	branchCode = String.valueOf(farmerListForm.getBranchCode());
		}
	}
%>
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
  
function searchFarmer(){
	    document.farmerListForm.cmd.value="Search";
	    document.farmerListForm.action= "<%=request.getContextPath()%>/FarmerList.do";
	    document.forms["farmerListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delFarmer');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.farmerListForm.cmd.value="Delete";
				document.farmerListForm.action="FarmerList.do";
				document.forms["farmerListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.farmerListForm.cmd.value="Search";
		document.forms["farmerListForm"].submit();
	}
}

function loadEdit(idCard,effectiveDate)
{	
	document.farmerForm.idCard.value = idCard;
	document.farmerForm.effectiveDate.value = effectiveDate;
	document.farmerForm.cmd.value = "Edit";
	document.farmerForm.action= "<%=request.getContextPath()%>/Farmer.do";
	document.forms["farmerForm"].submit();
 			 
}

function addFarmer(){

        document.farmerForm.cmd.value="New";
	    document.farmerForm.action= "<%=request.getContextPath()%>/Farmer.do";
	    document.forms["farmerForm"].submit();

}

function importList(){
	document.farmerForm.cmd.value="Import";
	document.farmerForm.action= "<%=request.getContextPath()%>/Farmer.do";
    document.forms["farmerForm"].submit();
}

function showErrorMessage(){
  <% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
  <% }%>
  <% if(branchCode !=null &&!"".equals(branchCode)){ %>
  	   //document.farmerListForm.branchCode.value = "<%=branchCode%>";
  <% } %>
   loadMenu("M20");
}
</script>
</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="farmerListForm" formAction="FarmerList.do" formBean="FarmerListForm">
<input type="hidden" name="cmd" value="DirtyList" />
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/FarmerList.do'>การจัดการเกษตรกร</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		           <li>ค้นหาข้อมูล</li>
		     	</ul>
		 	</div>
    	</div>	
    	
		 <!-- content -->
		 
		 
	    <div class="content">
	    	<div class="inside">
	        	<div class="content-search">
	                <div class="search-header">
	                <!-- <p>ค้นหาเกษตรกร</p> -->
	                &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาเกษตรกร</font></span>
	                
	                </div>
	                <div class="line-input">
	                	<ul>
	                		<li class="topic" style="width: 190px">เลขที่บัตรประชาชน : </li>
		                	<li class="boxinput" style="width:150px">
								<dcs:validateText name="idCard" property="idCard" style="width:140px;" maxlength="13" />
							</li>
	                		<li class="topic" style="width: 40px">ชื่อ : </li>
		                	<li class="boxinput" style="width:150px">
								<dcs:validateText name="firstName" property="firstName" style="width:140px;" maxlength="30"/>
							</li>
							<li class="topic" style="width: 40px">สกุล : </li>
		                	<li class="boxinput" style="width:160px">
								<dcs:validateText name="lastName" property="lastName" style="width:140px;" maxlength="30"/>
							</li>
	                   </ul>
	                   <ul style="font-size: 0;">
							<li class="topic" style="width: 130px">
	             				<button class="btn-search" onclick="searchFarmer();" style="padding-bottom: 9px;"></button>
	             			</li>
	                   </ul>
	               </div>
	               
					<div class="btn-manage" style="margin-left:0px;">
			             <a class="btn-add" onclick="addFarmer();" id="write"></a>
			             <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
			        </div>
			       

		        
		         <!--  for dcsGrid -->
            	<table border="0" width="96%" ><tr>
            	<%   if(farmerList!=null){ %>
            	<td><dcs:grid dataSource="<%= farmerList %>" name="farmerList" pageSize="<%=farmerListForm.getDisplayRow()%>" width="100%">
                 	<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                	<dcs:gridsorter/>                 
                 	<dcs:checkboxcolumn name="delFarmer" dataField="idCard" headerText="" width="100"  HAlign="left"/>
                 	<dcs:textcolumn dataField="idCard" style="cursor:pointer;" headerText="เลขที่บัตรประชาชน" width="150" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{idCard}','{effectiveDate}');"/>
                 	<dcs:textcolumn dataField="firstName" style="cursor:pointer;" headerText="ชื่อ" width="150" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{idCard}','{effectiveDate}');"/>
                 	<dcs:textcolumn dataField="lastName" style="cursor:pointer;" headerText="สกุล" width="150" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{idCard}','{effectiveDate}');"/>
                 	<dcs:textcolumn dataField="tel" style="cursor:pointer;" headerText="เบอร์โทร" width="150" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{idCard}','{effectiveDate}');"/>
                 	<dcs:imagecolumn dataField="linkImageEdit" toolTip="แก้ไข" headerText="" style="padding-top:2px;cursor:pointer;" width="30"  HAlign="center" cssClass="manage-sr" onClick="loadEdit('{idCard}','{effectiveDate}');"/>
                 	</dcs:grid>
            	</td>
            	<% } %>
            	</tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countFarmer"))%> รายการ</div>
            	<!-- end dcsGrid -->
			        
				</div>
			</div>
		</div>	
		
		
<!--footer -->
<%@include file="/footer.jsp" %>
    
	
</div>


</dcs:validateForm>
<form name="farmerForm" method="post" action="<%=request.getContextPath()%>/Farmer.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="idCard" />
<input type="hidden" name="effectiveDate" />
</form>
</body>


</html>