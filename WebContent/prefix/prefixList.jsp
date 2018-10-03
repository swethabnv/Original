<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.UserForm"%>
<%@page import="com.wsnweb.form.PrefixListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.Prefix"%>
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
	
	PrefixListForm prefixListForm = (PrefixListForm) request.getAttribute("PrefixListForm");
	List prefixList = (ArrayList) session.getAttribute("prefixList");
	String msg = "";
	if(prefixListForm!=null){
		if(!"".equals(prefixListForm.getErrMessage())){
		  	msg = prefixListForm.getErrMessage();
		}
	}
%>
<script type="text/javascript">
function searchPrefix(){
	    document.prefixListForm.cmd.value="Search";
	    document.prefixListForm.action= "<%=request.getContextPath()%>/PrefixList.do";
	    document.forms["prefixListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delPrefix');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.prefixListForm.cmd.value="Delete";
				document.prefixListForm.action="PrefixList.do";
				document.forms["prefixListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.prefixListForm.cmd.value="Search";
		document.forms["prefixListForm"].submit();
	}
}

function deleteEachPrefix(prefixNameClicked){
        alert(prefixNameClicked+"!!");
        document.prefixListForm.delPrefix.value = prefixNameClicked;
        document.prefixListForm.cmd.value="Delete";
	    document.prefixListForm.action="<%=request.getContextPath()%>/PrefixList.do";
	    document.forms["prefixListForm"].submit();
}

function loadEdit(prefix,prefixFull)
{	
     //alert(userName+","+firstName+","+lastName+","+email+","+branchCode+","+branchName);

	document.prefixForm.prefix.value = prefix; 
	document.prefixForm.prefixFull.value = prefixFull;
	document.prefixForm.cmd.value = "Edit";
	document.prefixForm.msg.value = "Edit";
	document.prefixForm.action= "<%=request.getContextPath()%>/Prefix.do";
	document.forms["prefixForm"].submit();
 			 
}

function addPrefix(){

        document.prefixForm.cmd.value="New";
	    document.prefixForm.action= "<%=request.getContextPath()%>/Prefix.do";
	    document.forms["prefixForm"].submit();

}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 	<%}%>
 	loadMenu("M09");
}
</script>
</head>
<body onload="showErrorMessage();">
<dcs:validateForm formName="prefixListForm" formAction="PrefixList.do" formBean="PrefixListForm">
<input type="hidden" name="cmd" value="DirtyList" />
<div class="main-inside">
<!-- insert header -->


		<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/PrefixList.do'>การจัดการคำนำหน้า</a></li>
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
                <div class="search-header">
                <!-- <p>ค้นหาคำนำหน้า</p>  -->
                 &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาคำนำหน้า</font></span>
                
                </div>
                <div class="line-input">
                	<ul>
                	<li class="topic" style="width: 400px">คำนำหน้า : </li>
                	<li class="boxinput" style="width:140px">
                	<dcs:validateText name="prefix" property="prefix" maxlength="20" style="width:160px;"/>
                	</li>
                	</ul>
	                <ul class="btn-search-box">
                	<li>
                		<button class="btn-search" onclick="searchPrefix();" style="padding-bottom: 9px;"></button>
                    </li>
                	</ul>
                </div>
			</div>
            
            <div class="btn-manage" style="margin-left:0px;">
            	   <a class="btn-add"  href="#" onclick="addPrefix();" id="write"></a>
                   <a class="btn-del" href="#" onclick="goPage('Delete');" id="del"></a>
            </div>

              <!--  for dcsGrid -->
            <table border="0" width="96%" ><tr>
            <%   if(prefixList!=null){ %>
            <td><dcs:grid dataSource="<%= prefixList %>" name="prefixList" pageSize="<%=prefixListForm.getDisplayRow()%>" width="100%">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delPrefix" dataField="abbrPrefix" headerText="" width="50"  HAlign="left" />
                 <dcs:rownumcolumn headerText="ลำดับที่" width="80" HAlign="center"/>   
                 <dcs:textcolumn dataField="abbrPrefix" headerText="คำนำหน้า" width="80" sortable="true" HAlign="left" cssClass="address-sr" style="cursor:pointer;" onClick="loadEdit('{abbrPrefix}','{fullPrefix}');"/>
                 <dcs:textcolumn dataField="fullPrefix" headerText="คำนำหน้าเต็ม" width="80" sortable="true" HAlign="left" cssClass="address-sr" style="cursor:pointer;" onClick="loadEdit('{abbrPrefix}','{fullPrefix}');"/>
                 <dcs:imagecolumn dataField="linkImageEdit" headerText="" toolTip="แก้ไข" width="30"  HAlign="center" cssClass="manage-sr" style="padding-top:2px;cursor:pointer;" onClick="loadEdit('{abbrPrefix}','{fullPrefix}');"/>
                 </dcs:grid>
            </td>
            <% } %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countPrefix"))%> รายการ</div>
            <!-- end dcsGrid -->
        </div>
    </div>
    
    <!--footer -->
    <%@include file="/footer.jsp" %>
    <!-- end insert >> content --> 

<!-- end for header -->
</div>
</dcs:validateForm>
<form name="prefixForm" method="post" action="<%=request.getContextPath()%>/Prefix.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="msg" />
<input type="hidden" name="prefix" />
<input type="hidden" name="prefixFull" />
</form>
</body>
</html>