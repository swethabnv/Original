<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.BreedType"%>
<%@page import="com.wsnweb.form.ReportForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.Province"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1">
<meta name="viewport" content="width=1280,user-scalable=yes">
<title><%=Utility.get("WEB_TITLE")%></title>

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

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<script type="text/javascript">

function loadData(){
	loadMenu("M00");
}

</script>
</head>
<body onload="loadData();">
<dcs:validateForm formName="notPermissionForm" formBean="NotPermissionForm">
	<div class="main-inside"><%@include file="/header.jsp"%>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/PlantList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		           <li>ไม่มีสิทธิ์เข้าใช้</li>
		     	</ul>
		 	</div>
    	</div>	

	<!-- content -->
	<div class="content" style="min-height:420px;">
	<div class="inside" style="text-align:center;">
	<br /><br /><font style="font-size:20px; font-weight:bold;">ไม่มีสิทธิ์เข้าใช้</font>
						<table align="center">
                             <tr><td>&nbsp;</td></tr>
                             <tr>
                                 <td><a class="btn-back" onclick="javascript:window.history.back();"></a></td>                         
                             </tr>
                  		</table>
	</div>
	</div>
	<!--footer -->
	<%@include file="/footer.jsp"%>
	<!-- end insert footer -->
	</div>
</dcs:validateForm>
</body>
</html>