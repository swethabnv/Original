<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@page import="com.wsndata.data.BreedType"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BreedGroupListForm"%>
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

	BreedGroupListForm  breedGroupListForm = (BreedGroupListForm) request.getAttribute("BreedGroupListForm");
	List breedGroupList = (ArrayList) session.getAttribute ("breedGroupList");
	List breedTypeList = (ArrayList) session.getAttribute ("breedTypeList");
	String msg = "";
	if(breedGroupListForm!=null){
		if(!"".equals(breedGroupListForm.getErrMessage())){
		  	msg = breedGroupListForm.getErrMessage();
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

function searchBreedGroup(){
	    document.breedGroupListForm.cmd.value="Search";
	    document.breedGroupListForm.action= "<%=request.getContextPath()%>/BreedGroupList.do";
	    document.forms["breedGroupListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delBreedGroup');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.breedGroupListForm.cmd.value="Delete";
				document.breedGroupListForm.action="BreedGroupList.do";
				document.forms["breedGroupListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.breedGroupListForm.cmd.value="Search";
		document.forms["breedGroupListForm"].submit();
	}
}

function loadEdit(breedGroupId,breedGroupName,breedTypeId)
{	
	document.breedGroupForm.breedGroupId.value = breedGroupId;
	document.breedGroupForm.breedGroupName.value = breedGroupName;
	document.breedGroupForm.breedTypeId.value = breedTypeId;
	document.breedGroupForm.cmd.value = "Edit";
	document.breedGroupForm.action= "<%=request.getContextPath()%>/BreedGroup.do";
	document.forms["breedGroupForm"].submit();
 			 
}

function addBreedGroup(){

        document.breedGroupForm.cmd.value="New";
	    document.breedGroupForm.action= "<%=request.getContextPath()%>/BreedGroup.do";
	    document.forms["breedGroupForm"].submit();

}
function removeOptionsBlank(sel) {
    var par;
    par = sel.options[0].parentNode;
    par.removeChild( sel.options[0] );
}
function showErrorMessage(){
	//removeOptionsBlank(document.breedGroupListForm.breedTypeId);
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
   loadMenu("M13");
}
</script>
</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="breedGroupListForm" formAction="BreedGroupList.do" formBean="BreedGroupListForm">
<input type="hidden" name="cmd" value="DirtyList" />

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
		           <li>ค้นหาข้อมูล</li>
		     	</ul>
		 	</div>
    	</div>	
    	
		 <!-- content -->
		 
		 
	    <div class="content">
	    	<div class="inside">
	        	<div class="content-search">
	                <div class="search-header">
	                <!-- <p>ค้นหากลุ่มพันธุ์พืช</p> -->
	                 &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหากลุ่มพันธุ์พืช</font></span>
	                </div>
	                <div class="line-input">
	                	<ul>
	                		<li class="topic" style="width: 250px">ชื่อกลุ่มพันธุ์ : </li>
		                	<li class="boxinput" style="width:170px">
								<dcs:validateText name="breedGroupName" property="breedGroupName" style="width:170px;" maxlength="100"/>
							</li>
							<li class="topic" style="width: 100px">ชื่อชนิดพืช : </li>
	                        <li class="boxinput" style="width:150px">
	                            <dcs:validateDropDown name="breedTypeId" property="breedTypeId" dataSource="<%= breedTypeList %>"  style="width:160px;" keyField="breedTypeId"  isRequire="true" displayField="breedTypeName" />
	                        </li>
	                        </ul>
	                        <ul class="btn-search-box">
	                        <li>
	             				<button class="btn-search" onclick="searchBreedGroup();" style="padding-bottom: 9px;"></button>
	             			</li>
	                   </ul>
	                </div>

					<div class="btn-manage" style="margin-left:0px;">
			             <a class="btn-add" onclick="addBreedGroup();" id="write"></a>
			             <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
			        </div>
			        
			    
		                 <!--  for dcsGrid -->
            	<table border="0" width="96%" ><tr>
            	<%   if(breedGroupList!=null){ %>
            	<td><dcs:grid dataSource="<%= breedGroupList %>" name="breedGroupList" pageSize="<%=breedGroupListForm.getDisplayRow()%>" width="100%">
                 	<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                	 <dcs:gridsorter/>                 
                 	<dcs:checkboxcolumn name="delBreedGroup" dataField="breedGroupId" headerText="" width="40"  HAlign="left"  />
                 	<!-- dcs:rownumcolumn headerText="ลำดับที่" width="55" HAlign="center"/> -->
                 	<dcs:textcolumn dataField="breedGroupName" headerText="ชื่อกลุ่มพันธุ์" style="cursor:pointer;" width="70" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{breedGroupId}','{breedGroupName}','{breedTypeId}');"/>
                 	<dcs:textcolumn dataField="breedTypeName" headerText="ชื่อชนิดพืช" style="cursor:pointer;" width="70" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{breedGroupId}','{breedGroupName}','{breedTypeId}');"/>
                 	<dcs:imagecolumn dataField="linkImageEdit" toolTip="แก้ไข" style="padding-top:2px;cursor:pointer;" headerText=""  width="5" HAlign="center" cssClass="manage-sr" onClick="loadEdit('{breedGroupId}','{breedGroupName}','{breedTypeId}');"/>
                 	</dcs:grid>
            	</td>
            	<% } %>
            	</tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countBreedGroup"))%> รายการ</div>
            	<!-- end dcsGrid -->
			        
				</div>
			</div>
		</div>	
		
		
<!--footer -->
<%@include file="/footer.jsp" %>
    
	
</div>


</dcs:validateForm>
<form name="breedGroupForm" method="post" action="<%=request.getContextPath()%>/BreedGroup.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="breedGroupId" />
<input type="hidden" name="breedGroupName" />
<input type="hidden" name="breedTypeId" />
</form>
</body>


</html>