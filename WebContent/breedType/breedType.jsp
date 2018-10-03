<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BreedTypeForm"%>
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
    BreedTypeForm breedTypeForm = (BreedTypeForm)request.getAttribute("BreedTypeForm");
	List breedTypeList = (ArrayList) session.getAttribute ("breedTypeList");
	String msg = "", cmd="", maxPerYear = "";
	long breedTypeId=0;
	if(breedTypeForm!=null){
	if(breedTypeForm.getMsg() !=null && !"".equals(breedTypeForm.getMsg())){
			msg = breedTypeForm.getMsg();
		}
		breedTypeId = breedTypeForm.getBreedTypeId();
		maxPerYear = breedTypeForm.getMaxPerYear()==0?"":String.valueOf(breedTypeForm.getMaxPerYear());
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
	document.breedTypeForm.breedTypeId.value = "<%=breedTypeId%>";
	document.breedTypeForm.maxPerYear.value = "<%=maxPerYear%>";
    showErrorMessage(); 		
    loadMenu("M12");
}
function saveBreedType(){
	if(document.breedTypeForm.breedTypeName.value == "" ){
		alert("กรุณาใส่ชื่อชนิดพืช!!");
		document.breedTypeForm.breedTypeName.focus();
		return false;
	}
    if(document.breedTypeForm.maxPerYear.value == "" ){
		alert("กรุณาใส่จำนวนสูงสุดในการปลูกแต่ละปี!!");
		$(function() {
  			$("#maxPerYear").focus();
		});
		return false;
	}
	
	if(document.breedTypeForm.breedTypeName.value == document.breedTypeForm.typeName.value)
	{
		alert("ไม่สามารถบันทึกได้เนื่องจากมีพืชชนิด '" + document.breedTypeForm.typeName.value + "' อยู่แล้วในระบบ");
		document.breedTypeForm.breedTypeName.focus();
		return false;
	}
	
	document.breedTypeForm.breedTypeName.value = document.breedTypeForm.breedTypeName.value.trim();
	
	document.breedTypeForm.cmd.value="Save";
	document.breedTypeForm.action= "<%=request.getContextPath()%>/BreedType.do";
	document.forms["breedTypeForm"].submit();
}
function closePage(){
   			document.breedTypeListForm.breedTypeName.value = "";
   			document.breedTypeListForm.cmd.value = "Search"; 
   			document.breedTypeListForm.action="<%=request.getContextPath()%>/BreedTypeList.do";
			document.forms["breedTypeListForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
 	<%}%>
 <%}%>
}
function checkBreedType(){
 		var reqParameters = new Object();
		reqParameters.cmd = "GetBreedType";
		reqParameters.breedTypeName = document.breedTypeForm.breedTypeName.value;
		new Ajax.Request("<%=request.getContextPath()%>/BreedType.do",
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
				    alert("มีพืชชนิด '" + json[0].breedTypeName + "' อยู่แล้วในระบบ!!");
					document.breedTypeForm.typeName.value = json[0].breedTypeName;
				}
			},onFailure: function() {	
				//alert('เกิดข้อผิดพลาด');
			}
		});
}

function checkValidate(str)    {
    var RE = /^\d+$/;
    if(str.value!="") {
    	if(!RE.test(str.value)){
			$(function() {
  				$("#maxPerYear").val("");
  				alert("กรุณาใส่ค่าที่เป็นตัวเลขเท่านั้น");
  				setTimeout(function() {$("#maxPerYear").focus();}, 0);
  				return false;
			});
    	}
    }
}


</script>
 
</head>
<body onload="loadData();">
<dcs:validateForm formName="breedTypeForm" formAction="BreedType.do" formBean="BreedTypeForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="breedTypeId" />
<input type="hidden" name="typeName" />
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
		           <li>กรอกข้อมูล</li>
		     	</ul>
		 	</div>
    	</div>	
    	
	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        
        	<div class="content-keyin">
                <div class="search-header">
                <!-- <p>ข้อมูลชนิดพืช</p>  -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลชนิดพืช</font></span>
                </div>
               
                
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width:375px;padding-right:0px">ชื่อชนิดพืช :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:200px">
						    <dcs:validateText name="breedTypeName" property="breedTypeName" maxlength="100" style="width:190px" />
                        </li>
                    </ul>
                    	<ul>
                    	<li class="topic" style="width:375px;padding-right:0px">จำนวนสูงสุดในการปลูกแต่ละปี</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:200px">
						    <dcs:validateText name="maxPerYear" property="maxPerYear" maxlength="4" style="width:190px" onChange="checkValidate(this);"/>
                        </li>
                    </ul>
                </div>

						<table align="center">
							
                             <tr><td colspan="2">&nbsp;</td></tr>
                             <tr><td><a class="btn-save" href="#" onclick="saveBreedType();" id="write"></a></td>  
                                 <td><a class="btn-cancel" href="#" onclick="closePage();"></a></td>                         
                             </tr>
                  		</table>
	         	
		    </div>  
			        

        </div>
    </div>

 					
            
            
</div>


   <%@include file="/footer.jsp" %>
     


</dcs:validateForm>
<form name="breedTypeListForm"  method="post" action="<%=request.getContextPath()%>/BreedTypeList.do">
<input type="hidden" name="breedTypeName" />
<input type="hidden" name="cmd" />
</form>
</body>
</html>
