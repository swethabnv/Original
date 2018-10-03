<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>index</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
var myWindow;
var params = [
    'height='+screen.height,
    'width='+screen.width,
    'scrollbars=yes',
    'toolbar=no',
    'location=no',
    'menubar=no',
    'resizable=yes'
   // 'fullscreen=yes' // only works in IE, but here for completeness
].join(',');

function load()
{
	
	window.open('','_self','');
	window.close();
	window.open("<%=request.getContextPath()%>/Login.do",'mainform', params);
	
}


</script>
</head>
<body onload="load();">
</body>
</html>