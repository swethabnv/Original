<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BreedTypeListForm"%>
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

	BreedTypeListForm  breedTypeListForm = (BreedTypeListForm) request.getAttribute("BreedTypeListForm");
	List breedTypeList = (ArrayList) session.getAttribute ("breedTypeList");
	String msg = "";
	if(breedTypeListForm!=null){
		if(!"".equals(breedTypeListForm.getErrMessage())){
		  	msg = breedTypeListForm.getErrMessage();
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
function searchBreedType(){
	    document.breedTypeListForm.cmd.value="Search";
	    document.breedTypeListForm.action= "<%=request.getContextPath()%>/BreedTypeList.do";
	    document.forms["breedTypeListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delBreedType');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.breedTypeListForm.cmd.value="Delete";
				document.breedTypeListForm.action="BreedTypeList.do";
				document.forms["breedTypeListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.breedTypeListForm.cmd.value="Search";
		document.forms["breedTypeListForm"].submit();
	}
}

function loadEdit(breedTypeId, breedTypeName, maxYear)
{	
	document.breedTypeForm.breedTypeId.value = breedTypeId;
	document.breedTypeForm.breedTypeName.value = breedTypeName;
	document.breedTypeForm.maxPerYear.value = maxYear;
	document.breedTypeForm.cmd.value = "Edit";
	document.breedTypeForm.action= "<%=request.getContextPath()%>/BreedType.do";
	document.forms["breedTypeForm"].submit();
 			 
}

function addBreedType(){

        document.breedTypeForm.cmd.value="New";
	    document.breedTypeForm.action= "<%=request.getContextPath()%>/BreedType.do";
	    document.forms["breedTypeForm"].submit();

}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
  loadMenu("M12");
}
</script>
</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="breedTypeListForm" formAction="BreedTypeList.do" formBean="BreedTypeListForm">
<input type="hidden" name="cmd" value="DirtyList" />
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/BreedTypeList.do'>การจัดการชนิดพืช</a></li>
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
	                <!-- <p>ค้นหาชนิดพืช</p> -->
	                &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาชนิดพืช</font></span>
	                </div>
	                <div class="line-input">
	                	<ul>
	                		<li class="topic" style="width: 400px">ชื่อชนิดพืช : </li>
		                	<li class="boxinput" style="width:140px">
								<dcs:validateText name="breedTypeName" property="breedTypeName" maxlength="100" style="width:160px"/>
							</li>
							</ul>
	                        <ul class="btn-search-box">
							<li>
	             				<button class="btn-search" onclick="searchBreedType();" style="padding-bottom: 9px;"></button>
	             			</li>
	                   </ul>
	                </div>
		
					<div class="btn-manage" style="margin-left:0px;">
			             <a class="btn-add" onclick="addBreedType();" id="write"></a>
			             <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
			        </div>
			        
		        
		         <!--  for dcsGrid -->
            	<table border="0" width="96%" ><tr>
            	<%   if(breedTypeList!=null){ %>
            	<td><dcs:grid dataSource="<%= breedTypeList %>" name="breedTypeList" pageSize="<%=breedTypeListForm.getDisplayRow()%>" width="100%">
                 	<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                	 <dcs:gridsorter/>                 
                 	<dcs:checkboxcolumn name="delBreedType" dataField="breedTypeId" headerText="" width="165"  HAlign="left"  />
                 	<!-- dcs:rownumcolumn headerText="ลำดับที่" width="140" style="cursor:pointer;" HAlign="center"/> -->
                 	<dcs:textcolumn dataField="breedTypeName" headerText="ชื่อชนิดพืช" width="200" sortable="true" style="cursor:pointer;" HAlign="left" cssClass="address-sr" onClick="loadEdit('{breedTypeId}','{breedTypeName}','{maxPerYear}');"/>
                 	<dcs:textcolumn dataField="maxPerYear" headerText="จำนวนสูงสุดในการปลูก(ต่อปี)" width="200" sortable="true" style="cursor:pointer;" HAlign="left" cssClass="address-sr" onClick="loadEdit('{breedTypeId}','{breedTypeName}','{maxPerYear}');"/>
                 	<dcs:imagecolumn dataField="linkImageEdit" toolTip="แก้ไข" headerText="" style="padding-top:2px;cursor:pointer;" width="5"  HAlign="center" cssClass="manage-sr" onClick="loadEdit('{breedTypeId}','{breedTypeName}','{maxPerYear}');"/>
                 	</dcs:grid>
            	</td>
            	<% } %>
            	</tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countBreedType"))%> รายการ</div>
            	<!-- end dcsGrid -->
			        
				</div>
			</div>
		</div>	
		
		
<!--footer -->
<%@include file="/footer.jsp" %>
    
	
</div>


</dcs:validateForm>
<form name="breedTypeForm" method="post" action="<%=request.getContextPath()%>/BreedType.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="breedTypeId" />
<input type="hidden" name="breedTypeName" />
<input type="hidden" name="maxPerYear" />
</form>
</body>


</html>