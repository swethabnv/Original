<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@page import="com.wsnweb.util.Utility"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.CostForm"%>
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
    CostForm costForm = (CostForm)request.getAttribute("CostForm");
	List costList = (ArrayList) session.getAttribute ("costList");
	String msg = "";
	long costId=0;
	if(costForm!=null){
	if(costForm.getMsg() !=null && !"".equals(costForm.getMsg())){
			msg = costForm.getMsg();
		}
		costId = costForm.getCostId();
	}

%>
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

 <script type="text/javascript">
function loadData(){
	document.costForm.costId.value = "<%=costId%>";
    showErrorMessage(); 		
    loadMenu("M15");
}
function saveCost(){
	var saveIs = true;
	if(document.costForm.costName.value == "" ){
		alert("กรุณาใส่รายการค่าใช้จ่าย !!");
		document.costForm.costName.focus();
		saveIs = false;
	}
	if(saveIs){
	    document.costForm.cmd.value="Save";
	    document.costForm.action= "<%=request.getContextPath()%>/Cost.do";
	    document.forms["costForm"].submit();
	}
}
function closePage(){
   			document.costListForm.costName.value = "";
   			document.costListForm.cmd.value = "Search"; 
   			document.costListForm.action="<%=request.getContextPath()%>/CostList.do";
			document.forms["costListForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
 	<%}%>
 <%}%>
}

function checkCostName(){
 		var reqParameters = new Object();
		reqParameters.cmd = "GetCost";
		document.costForm.costName.value = document.costForm.costName.value.trim();
		reqParameters.costName = document.costForm.costName.value;
		reqParameters.costId = document.costForm.costId.value;
		new Ajax.Request("<%=request.getContextPath()%>/Cost.do",
		{	
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport)
			{					
			    var json = transport.responseText.evalJSON(); 
				if(json !=null && json != "")
				{
				    alert("มี" + json[0].costName + "อยู่แล้วในระบบ!!");
					document.costForm.cstName.value = json[0].costName;
				}
			},onFailure: function() {	
			}
		});
}
</script>
 
</head>
<body onload="loadData();">
<dcs:validateForm formName="costForm" formAction="Cost.do" formBean="CostForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="costId" />
<input type="hidden" name="cstName" />
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
		           <li>กรอกข้อมูล</li>
		     	</ul>
		 	</div>
    	</div>	
    	
	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        	<div class="content-keyin">
                <div class="search-header">
	                <!-- <p>ข้อมูลค่าใช้จ่าย</p> -->
	                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลค่าใช้จ่าย</font></span>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 400px;padding-right:0px">รายการค่าใช้จ่าย :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:140">
						    <dcs:validateText name="costName" property="costName" maxlength="100" style="width:160px" onChange="checkCostName();"/>
                        </li>
                    </ul>
                </div>

						<table align="center">
                             <tr><td colspan="2">&nbsp;</td></tr>
                             
                             <tr><td><a class="btn-save" onclick="saveCost();" id="write"></a></td>  
                                 <td><a class="btn-cancel" onclick="closePage();"></a></td>                         
                             </tr>
                  		</table>
		    </div>  
        </div>
    </div> 
</div>


   <%@include file="/footer.jsp" %>
     


</dcs:validateForm>
<form name="costListForm"  method="post" action="<%=request.getContextPath()%>/CostList.do">
<input type="hidden" name="costName" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>
