<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@page import="com.wsnweb.util.Utility"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.AssetForm"%>
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
    AssetForm assetForm = (AssetForm)request.getAttribute("AssetForm");
	List assetList = (ArrayList) session.getAttribute ("assetList");
	String msg = "";
	long assetId=0;
	if(assetForm!=null)
	{
		if(assetForm.getMsg() !=null && !"".equals(assetForm.getMsg()))
		{
			msg = assetForm.getMsg();
		}
		assetId = assetForm.getAssetId();
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
	document.assetForm.assetId.value = "<%=assetId%>";
    showErrorMessage(); 	
    loadMenu("M16");	
}
function saveAsset(){
	//var saveIs = true;
	if(document.assetForm.assetName.value == "" ){
		alert("กรุณาใส่รายการทรัพย์สิน !!");
		document.assetForm.assetName.focus();
		//saveIs = false;
		return false;
	}
	
	
	if(document.assetForm.assetName.value == document.assetForm.assName.value){
		 	alert("ไม่สามารถบันทึกได้ เนื่องจากมี" + document.assetForm.assName.value + "อยู่แล้วในระบบ");
		 	return false;
		 //	saveIs = false; 	
	}
	
		document.assetForm.assetName.value = document.assetForm.assetName.value.trim();
	//if(saveIs == true){
	    document.assetForm.cmd.value="Save";
	    document.assetForm.action= "<%=request.getContextPath()%>/Asset.do";
	    document.forms["assetForm"].submit();
	//}

}
function closePage(){
   			document.assetListForm.assetName.value = "";
   			document.assetListForm.cmd.value = "Search"; 
   			document.assetListForm.action="<%=request.getContextPath()%>/AssetList.do";
			document.forms["assetListForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
 	<%}%>
 <%}%>
}

function checkAssetName(){
 		var reqParameters = new Object();
		reqParameters.cmd = "GetAsset";
		document.assetForm.assetName.value = document.assetForm.assetName.value.trim();
		reqParameters.assetName = document.assetForm.assetName.value;
		reqParameters.assetId = document.assetForm.assetId.value;
		new Ajax.Request("<%=request.getContextPath()%>/Asset.do",
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
				    alert("มี" + json[0].assetName + "อยู่แล้วในระบบ!!");
					document.assetForm.assetName.value = "";
					document.assetForm.assName.value = json[0].assetName;
                    window.setTimeout(function () {document.getElementById('assetName').focus();}, 0);
				}
			},onFailure: function() {	
			}
		});

}

</script>
 
</head>
<body onload="loadData();">
<dcs:validateForm formName="assetForm" formAction="Asset.do" formBean="AssetForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="assetId" />
<input type="hidden" name="assName" />
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
		           <li>กรอกข้อมูล</li>
		     	</ul>
		 	</div>
    	</div>	
    	
	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        
        	<div class="content-keyin">
                <div class="search-header" >
                <!-- <p>ข้อมูลทรัพย์สิน</p> -->
                    &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลทรัพย์สิน</font></span>
                </div>
               
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 400px;padding-right:0px">รายการทรัพย์สิน :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:140px">
						    <dcs:validateText name="assetName" property="assetName" maxlength="100" style="width:160px" onChange="checkAssetName();"/>
                        </li>
                    </ul>
                </div>

						<table align="center">
                             
                             <tr><td colspan="2">&nbsp;</td></tr>
                             <tr><td><a class="btn-save" onclick="saveAsset();" id="write"></a></td>  
                                 <td><a class="btn-cancel" onclick="closePage();"></a></td>                         
                             </tr>
                  		</table>
		    </div>  
        </div>
    </div>
</div>


   <%@include file="/footer.jsp" %>
     


</dcs:validateForm>
<form name="assetListForm"  method="post" action="<%=request.getContextPath()%>/AssetList.do">
<input type="hidden" name="assetName" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>
