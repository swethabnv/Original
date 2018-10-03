<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.AssetListForm"%>
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

	AssetListForm  assetListForm = (AssetListForm) request.getAttribute("AssetListForm");
	List assetList = (ArrayList) session.getAttribute ("assetList");
	String msg = "";
	if(assetListForm!=null){
		if(!"".equals(assetListForm.getErrMessage())){
		  	msg = assetListForm.getErrMessage();
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
function searchAsset(){
	    document.assetListForm.cmd.value="Search";
	    document.assetListForm.action= "<%=request.getContextPath()%>/AssetList.do";
	    document.forms["assetListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delAsset');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.assetListForm.cmd.value="Delete";
				document.assetListForm.action="AssetList.do";
				document.forms["assetListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.assetListForm.cmd.value="Search";
		document.forms["assetListForm"].submit();
	}
}

function loadEdit(assetId,assetName)
{	
	document.assetForm.assetId.value = assetId;
	document.assetForm.assetName.value = assetName;
	document.assetForm.cmd.value = "Edit";
	document.assetForm.action= "<%=request.getContextPath()%>/Asset.do";
	document.forms["assetForm"].submit();
 			 
}

function addAsset(){

        document.assetForm.cmd.value="New";
	    document.assetForm.action= "<%=request.getContextPath()%>/Asset.do";
	    document.forms["assetForm"].submit();

}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
   loadMenu("M16");
}
</script>
</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="assetListForm" formAction="AssetList.do" formBean="AssetListForm">
<input type="hidden" name="cmd" value="DirtyList"/>
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/AssetList.do'>การจัดการทรัพย์สิน</a></li>
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
	                <!-- <p>ค้นหาทรัพย์สิน</p> -->
	                &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาทรัพย์สิน</font></span>
	                </div>
	                <div class="line-input">
	                	<ul>
	                		<li class="topic" style="width: 400px">รายการทรัพย์สิน : </li>
		                	<li class="boxinput" style="width:140px">
								<dcs:validateText name="assetName" property="assetName" style="width:160px;" maxlength="100"/>
							</li>
	                   </ul>
	                   <ul class="btn-search-box">
							<li>
	             				<button class="btn-search" onclick="searchAsset();" style="padding-bottom: 9px;"></button>
	             			</li>
	             			</ul>
	                </div>
					<div class="btn-manage" style="margin-left:0px;">
			             <a class="btn-add" href="#" onclick="addAsset();" id="write"></a>
			             <a class="btn-del" href="#" onclick="goPage('Delete');" id="del"></a>
			        </div>
			        

		        
		         <!--  for dcsGrid -->
            	<table border="0" width="96%" ><tr>
            	<%   if(assetList!=null){ %>
            	<td><dcs:grid dataSource="<%= assetList %>" name="assetList" pageSize="<%=assetListForm.getDisplayRow()%>" width="100%">
                 	<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                	<dcs:gridsorter/>                 
                 	<dcs:checkboxcolumn name="delAsset" dataField="assetId" headerText="" width="165"/>
                 	<!-- dcs:rownumcolumn headerText="ลำดับที่" width="140"/> -->
                 	<dcs:textcolumn dataField="assetName" style="cursor:pointer;" headerText="รายการทรัพย์สิน" width="200" sortable="true" onClick="loadEdit('{assetId}','{assetName}');"/>
                 	<dcs:imagecolumn dataField="linkImageEdit" toolTip="แก้ไข" headerText="" style="padding-top:2px;cursor:pointer;" width="5" onClick="loadEdit('{assetId}','{assetName}');"/>
                 	</dcs:grid>
            	</td>
            	<% } %>
            	</tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countAsset"))%> รายการ</div>
            	<!-- end dcsGrid -->
			        
				</div>
			</div>
		</div>	
		
		
<!--footer -->
<%@include file="/footer.jsp" %>
    
	
</div>


</dcs:validateForm>
<form name="assetForm" method="post" action="<%=request.getContextPath()%>/Asset.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="assetId" />
<input type="hidden" name="assetName" />
</form>
</body>


</html>