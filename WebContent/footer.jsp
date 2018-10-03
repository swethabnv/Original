<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.wsnweb.util.Utility"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css" />

<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/scripts.js"></script>
 
 <div class="container-footer">
    	<div class="line-footer"></div>
        <div class="clear"></div>
        <div class="footer">
        	<div class="inside">
        		<div class="copyright">&copy; 2014 สงวนลิขสิทธิ์ 2557 <%=Utility.get("WEB_TITLE")%></div>
            	<div class="logo-footer"><img src="<%=request.getContextPath() %>/images/logo-footer.png" width="238" height="32" /></div>
            </div>
        </div>
 </div>