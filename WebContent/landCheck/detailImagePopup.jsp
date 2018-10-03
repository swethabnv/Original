<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>

<%@page import="java.util.List"%>
<%@page import="com.dcs.util.StringUtil"%>
<%@page import="com.wsnweb.form.LandCheckForm"%>
<%@page import="com.wsndata.data.LandCheckImages"%><html>

<head>
<%
	LandCheckForm landCheckForm = (LandCheckForm)request.getAttribute("LandCheckForm");
	LandCheckImages image=(LandCheckImages)request.getAttribute("ImageDetail");
%>

<title>:: รายละเอียดรูปภาพ ::</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/dcswc/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/dcswc/css/jquery.Jcrop.min.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/dcswc/css/DropdownMenu.css" type="text/css" />
<link rel="stylesheet" href="<%= request.getContextPath()%>/dcswc/css/CalendarStyle.css" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/dcswc/jquery.min.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/dcswc/jquery.Jcrop.min.js"></script>
<script LANGUAGE="javascript" src="<%=request.getContextPath()%>/dcswc/dcswc.js"></script>
<script language="JavaScript" src="<%= request.getContextPath()%>/dcswc/prototype.js"></script>

<script type="text/javascript" language="javascript">
var imageData = new Array();

function goSave()
{
	<%if(landCheckForm!=null && landCheckForm.getIndex()==-100 ){%>
		
			if (document.getElementById('imageFormFile').value.trim()!='')
			{
			  	alert("กรุณากดปุ่มแสดงรูปภาพก่อน");
				return false;	
			}
			<%if( !"view".equals(landCheckForm.getViewImageStatus())){%>
				alert("กรุณาเพิ่มรูปภาพ");
				return false;	
			<%}%>
			
       			var reqParameters = new Object(); 	
		    	reqParameters.cmd = "checkSize";
				new Ajax.Request('<%=request.getContextPath()%>/LandCheck.do',
				{
					method: 'post',
					asynchronous: false,
					parameters: reqParameters,
					encoding:'UTF-8',				
					onSuccess: function(transport) {	
						if (""==transport.responseText) {		
								document.landCheck.cmd.value = "saveDetailImage";
								document.landCheck.action = "LandCheck.do";
								document.landCheck.submit();
						}else{
							alert("ไฟล์ภาพควรมีขนาดไม่เกิน 10 MB");
							return false;
						}
					},
					onFailure: function() {
						alert("Fail !!  ");
					}
				});
	<%}else{%>
		document.landCheck.cmd.value = "saveDetailImage";
		document.landCheck.action = "LandCheck.do";
		document.landCheck.submit();
	<%}%>
}

function showImage()
{
	document.landCheck.preview.disabled = true;
	if(document.getElementById('imageFormFile').value.trim()!=''){
		if (validate_landCheck_imageFormFile()) {	
		    document.landCheck.viewImageStatus.value="<%=StringUtil.beString(landCheckForm.getViewImageStatus())%>";
			document.landCheck.cmd.value = "showImage";
			document.landCheck.action = "LandCheck.do";
			document.landCheck.submit();
		}else{
			document.landCheck.preview.disabled = false;
			alert("กรุณาเลือกเป็นรูปภาพ (นามสกุล jpg/jpeg)");
		}
	}else{
		document.landCheck.preview.disabled = false;
		alert("กรุณาเลือกไฟล์");
	}
}
function load()
{
	<%if(landCheckForm!=null && landCheckForm.getViewImageStatus()!=null && landCheckForm.getViewImageStatus().trim().length()>0 && landCheckForm.getViewImageStatus().equals("success"))
	{%>
		window.opener.document.landCheckForm.imageId.value = document.landCheck.imageId.value;
		window.opener.reload();
		window.close();
	<%}%>
}
</script>
</head>
<body  onload="load();">
<center>
<dcs:validateForm formName="landCheck" formAction="LandCheck.do" formBean="LandCheckForm" enctype="multipart/form-data">
<input type="hidden" name="cmd" value="">
<dcs:validateHidden name="index" property="index"/>
<dcs:validateHidden name="imageData" property="imageData"/>
<dcs:validateHidden name="imageId" property="imageId"/>
<dcs:validateHidden name="landCheckId" property="landCheckId"/>
<input type="hidden" name="viewImageStatus" id="viewImageStatus">
<div style="width:680px; height:460px; border-spacing:0px; padding:0px; background-color:white;">	
<br/>
	<table border="0" width="100%" align="center">	
		<tr>
		<%if(image!=null && image.getImageId()>0){ %>
		<td align="center"><img src="<%=request.getContextPath()%>/LandCheck.do?cmd=image&imageId=<%=image.getImageId()%>"  width="200px" height="130px"  ></td>
		<%}else if(landCheckForm!=null && landCheckForm.getIndex()==-100 ){%>
			<td align="center"><dcs:validateFile name="imageFormFile" fileExt="jpg,jpeg" size="35" isRequire="true"/>
								<input type="button" value="แสดงรูปภาพ" name="preview" onclick="showImage()" /> 
			</td>
		<%}else if(image!=null && image.getImageId()==0 && landCheckForm!=null && landCheckForm.getIndex()>=0){%>
			<td align="center"><img src="<%=request.getContextPath()%>/LandCheck.do?cmd=imageObject&imageId=<%=landCheckForm.getIndex()+1%>" height="320px" /></td>
		<%}else{ %>
		<td align="center">ไม่มีรูปภาพ</td>
		<%} %>
		</tr>
					<%if(landCheckForm !=null && landCheckForm.getViewImageStatus()!= null && landCheckForm.getViewImageStatus().trim().length()>0  ) {
						if("view".equals(landCheckForm.getViewImageStatus())){%>						
							<tr>
								<td  align ="center">
										<br /><img src="<%=request.getContextPath()%>/LandCheck.do?cmd=getImage" id="originalImage" height="320px" /><br/>
								</td>
							</tr>						
					<%}} %>
	</table>
	<br/>
	<table>
	<tr>
		<td align="center">
		<input type="button" name="okBtn" value="Save" onclick="goSave()">&nbsp;&nbsp;&nbsp; 
		<%if(landCheckForm!=null && landCheckForm.getIndex()==-100 ){%>
			<%if(landCheckForm !=null && landCheckForm.getViewImageStatus()!= null && landCheckForm.getViewImageStatus().trim().length()>0  ) {
			    if("view".equals(landCheckForm.getViewImageStatus())){%>
				<input type="button" name="cancelBtn" value="Cancel" onclick="window.opener.reloadClose();window.close();" />
				<%}%>
			<%}else{%>
				<input type="button" name="cancelBtn" value="Cancel" onclick="window.close();" />
			<%} %>
		<%}else{ %>
			<input type="button" name="cancelBtn" value="Cancel" onclick="window.opener.reload();window.close();" />
		<%} %>
		</td>
	</tr></table>
</div>
</dcs:validateForm>
</center>
</body>
</html>