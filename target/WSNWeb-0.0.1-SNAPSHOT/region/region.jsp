<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.RegionForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

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

<title><%=Utility.get("WEB_TITLE")%></title>

<% 
    response.setContentType("text/html; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);

	//List regionList = (ArrayList) session.getAttribute("regionList");
	RegionForm regionForm = (RegionForm)request.getAttribute("RegionForm");
	String regionName = "", regionNo = "", cmd = "", msg = "";
	
	if(regionForm !=null){
		if(regionForm.getMsg() !=null && !"".equals(regionForm.getMsg())){
			msg = regionForm.getMsg()==null?"":regionForm.getMsg();
		}
		cmd = regionForm.getCmd()==null?"":regionForm.getCmd();
		regionName = regionForm.getRegionName()==null?"":regionForm.getRegionName();
		regionNo= regionForm.getRegionNo()==null?"":regionForm.getRegionNo();
	}
%>

<script type="text/javascript">
function loadData(){
	<% if(cmd.equals("")) { %>
    document.regionForm.regionName.value = "<%= regionName %>";<% } %>
    document.regionForm.cmd.value        = "<%= cmd %>";
    if( document.regionForm.cmd.value == 'Edit'){
   		document.regionForm.regionName.value = "<%= regionName %>";
   		document.regionForm.regionNo.value = "<%= regionNo %>";
   	}
    showErrorMessage(); 	
    loadMenu("M06");	
}
function saveRegion(){
		if(document.regionForm.regionName.value ==''){
		 	alert("กรุณาใส่ชื่อภูมิภาค");
		 	document.regionForm.regionName.focus();	
		 	return false; 	
		}
		
		if(document.regionForm.regName.value == document.regionForm.regionName.value){
		 		alert("ไม่สามารถบันทึกได้ เนื่องจากมี" + document.regionForm.regName.value + "อยู่แล้วในระบบ");
		 		window.setTimeout(function () {document.regionForm.regionName.focus();}, 0);
		 		return false; 	
		}
		
	    document.regionForm.cmd.value="Save";
	    document.regionForm.action= "<%=request.getContextPath()%>/Region.do";
	    document.forms["regionForm"].submit();
}

function showErrorMessage(){
	<% if(!"".equals(msg)){ %>
   		    alert("<%= msg %>");
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
   <%}}%>
}

function closePage(){ 
		document.regionListForm.regionName.value = ""; 
   		document.regionListForm.cmd.value = "Search"; 
   		document.regionListForm.action="<%=request.getContextPath()%>/RegionList.do";
		document.forms["regionListForm"].submit();
}

    function checkThai(str)    {
        var RE = /^[ก-์A-Za-z ]+$/;
        if(RE.test(str.value)){
        	checkRegion();
        }else{
            alert("กรุณาใส่ชื่อภูมิภาค เป็นภาษาไทย หรือภาษาอังกฤษเท่านั้น");
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            document.getElementById(str.id).focus();
            }, 0);
        }
    }


function checkRegion(){
 		var reqParameters = new Object();
		reqParameters.cmd = "GetRegion";
		document.regionForm.regionName.value = document.regionForm.regionName.value.trim();
		reqParameters.regionName = document.regionForm.regionName.value;
		reqParameters.regionNo = document.regionForm.regionNo.value;
		new Ajax.Request("<%=request.getContextPath()%>/Region.do",
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
				    alert("มี" + json[0].regionName + "อยู่แล้วในระบบ!!");
				    window.setTimeout(function () {document.regionForm.regionName.focus();}, 0);
					document.regionForm.regName.value = json[0].regionName;
				}
			},onFailure: function() {	
				// alert('เกิดข้อผิดพลาด');
			}
		});

}

</script>

</head>
<body onload="loadData();">
<dcs:validateForm formName="regionForm" formAction="Region.do" formBean="RegionForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="regionNo" />
<input type="hidden" name="regName" />
<div class="main-inside">
    <!-- insert header -->
	<%@include file="/header.jsp" %>
	<div class="navigator">
	    <div class="inside">
	 		<ul>
	 			<li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
	            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	            <li><a style="color:#333" href='<%=request.getContextPath()%>/RegionList.do'>การจัดการภูมิภาค</a></li>
				<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	           <li>กรอกข้อมูล</li>
	     	</ul>
	  </div>
    </div>	
	<!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        	<div class="content-keyin">
                <div class="search-header" >
                <!-- <p>ข้อมูลภูมิภาค</p>  -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลภูมิภาค</font></span>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 375px;padding-right:0px">ชื่อภูมิภาค :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" ><dcs:validateText name="regionName" property="regionName" maxlength="50" style="width:190px;" onChange="checkThai(this);"/>
	                    </li>
                    </ul>
                </div>
                <br />
                <div class="clear"></div>
           			<table align="center">
			       
			           <tr> <td><a class="btn-save" onclick="saveRegion();" id="write"></a></td>	
				            <td><a class="btn-cancel" onclick="closePage();"></a></td>			           
			           </tr>
			        </table>
             <div class="clear"></div>
        </div>
    </div>
	</div>
	<!--footer -->
    <%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>

<form name="regionListForm"  method="post" action="<%=request.getContextPath()%>/RegionList.do">
<input type="hidden" name="regionName" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>