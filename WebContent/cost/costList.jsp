<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.CostListForm"%>
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

	CostListForm  costListForm = (CostListForm) request.getAttribute("CostListForm");
	List costList = (ArrayList) session.getAttribute ("costList");
	String msg = "";
	if(costListForm!=null){
		if(!"".equals(costListForm.getErrMessage())){
		  	msg = costListForm.getErrMessage();
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
function searchCost(){
	    document.costListForm.cmd.value="Search";
	    document.costListForm.action= "<%=request.getContextPath()%>/CostList.do";
	    document.forms["costListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delCost');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.costListForm.cmd.value="Delete";
				document.costListForm.action="CostList.do";
				document.forms["costListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.costListForm.cmd.value="Search";
		document.forms["costListForm"].submit();
	}
}

function loadEdit(costId,costName)
{	
	document.costForm.costId.value = costId;
	document.costForm.costName.value = costName;
	document.costForm.cmd.value = "Edit";
	document.costForm.action= "<%=request.getContextPath()%>/Cost.do";
	document.forms["costForm"].submit();
 			 
}

function addCost(){

        document.costForm.cmd.value="New";
	    document.costForm.action= "<%=request.getContextPath()%>/Cost.do";
	    document.forms["costForm"].submit();

}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
  loadMenu("M15");
}
</script>
</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="costListForm" formAction="CostList.do" formBean="CostListForm">
<input type="hidden" name="cmd" value="DirtyList" />
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/CostList.do'>การจัดการต้นทุน/ค่าใช้จ่าย</a></li>
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
	                <!-- <p>ค้นหาค่าใช้จ่าย</p> -->
	                &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาค่าใช้จ่าย</font></span>
	               
	                </div>
	                <div class="line-input">
	                	<ul>
	                		<li class="topic" style="width: 400px">รายการค่าใช้จ่าย: </li>
		                	<li class="boxinput" style="width:140px">
								<dcs:validateText name="costName" property="costName" style="width:160px" maxlength="100"/>
							</li>
						</ul>
						<ul class="btn-search-box">
							<li>
	             				<button class="btn-search" onclick="searchCost();" style="padding-bottom: 9px;"></button>
	             			</li>
	                   </ul>
	                </div>
					<div class="btn-manage" style="margin-left:0px;">
			             <a class="btn-add" href="#" onclick="addCost();" id="write"></a>
			             <a class="btn-del" href="#" onclick="goPage('Delete');" id="del"></a>
			        </div>

		        
		         <!--  for dcsGrid -->
            	<table border="0" width="96%" ><tr>
            	<%   if(costList!=null){ %>
            	<td><dcs:grid dataSource="<%= costList %>" name="costList" pageSize="<%=costListForm.getDisplayRow()%>" width="100%">
                 	<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                	 <dcs:gridsorter/>                 
                 	<dcs:checkboxcolumn name="delCost" dataField="costId" headerText="" width="165"  HAlign="left" />
                 	<!-- dcs:rownumcolumn headerText="ลำดับที่" width="140"/> -->
                 	<dcs:textcolumn dataField="costName" headerText="รายการค่าใช้จ่าย" width="200" sortable="true" HAlign="left" style="cursor:pointer" onClick="loadEdit('{costId}','{costName}');"/>
                 	<dcs:imagecolumn dataField="linkImageEdit" headerText="" style="padding-top:2px;cursor:pointer;" width="5"  HAlign="center" toolTip="แก้ไข" cssClass="manage-sr" onClick="loadEdit('{costId}','{costName}');"/>
                 	</dcs:grid>
            	</td>
            	<% } %>
            	</tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countCost"))%> รายการ</div>
            	<!-- end dcsGrid -->
			        
				</div>
			</div>
		</div>	
		
		
<!--footer -->
<%@include file="/footer.jsp" %>
    
	
</div>


</dcs:validateForm>
<form name="costForm" method="post" action="<%=request.getContextPath()%>/Cost.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="costId" />
<input type="hidden" name="costName" />
</form>
</body>


</html>