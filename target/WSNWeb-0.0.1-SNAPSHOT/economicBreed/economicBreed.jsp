<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.wsnweb.util.Utility"%><html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.EconomicBreedForm"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.Province"%>
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
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);

	List breedTypeList = (ArrayList) session.getAttribute ("ecoBreedTypeList");
	List provinceList = (ArrayList) session.getAttribute("ecoProvinceList");
	EconomicBreedForm economicBreedForm = (EconomicBreedForm) request.getAttribute("EconomicBreedForm");
	String msg = "",provinceNew = "",provinceEdit = "",cmd="";
	long breedType=0;
	if(economicBreedForm!=null){
	if(economicBreedForm.getMsg() !=null && !"".equals(economicBreedForm.getMsg())){
			msg = economicBreedForm.getMsg();
		}
		provinceNew = economicBreedForm.getProvince();
		provinceEdit = economicBreedForm.getTmpEdit();
		cmd = economicBreedForm.getCmd();
		breedType = economicBreedForm.getBreedTypeId();
	}
	
%>
  
<script type="text/javascript">
// IE
  $(document).ready(function() 
  {
  		$('input[type=text],input[type=checkbox],select:first').focus(); //$('input:text:first').focus();
 		$('input[type=text],input[type=checkbox],select').bind("keydown", function(e) { //input:text
    	var n = $("input[type=text],input[type=checkbox],select").length;
    	if (e.which == 13) { 
     		 e.preventDefault(); //Skip default behavior of the enter key
     	 	var nextIndex = $('input[type=text],input[type=checkbox],select').index(this) + 1; //input:text
     	 	if(nextIndex < n){
      	     	$('input[type=text],input[type=checkbox],select')[nextIndex].focus(); // input:text
      	     	$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
 	 });
  }); 

function loadData(){
		<% if(cmd != null){ %>
		document.economicBreedForm.cmd.value = "<%= cmd %>";
		<% if("New".equals(cmd)) { %>
		document.economicBreedForm.breedTypeId.value = "0";
		document.economicBreedForm.province.value = "0|0";
		<% } else { %>
		document.economicBreedForm.breedTypeId.value = "<%= breedType %>";
		document.economicBreedForm.province.value = "<%= provinceNew %>";
		<% if(economicBreedForm != null) { %>
		document.economicBreedForm.msg.value = "<%= msg %>";
		document.economicBreedForm.tmpEdit.value = "<%= provinceEdit %>";
		<% } } } %>
		//removeOptionsBlank(document.economicBreedForm.breedTypeId);
		showErrorMessage();
		loadMenu("M14");
}

function saveEconomicBreed(){
	var saveIs = true;
	if(document.economicBreedForm.breedTypeId.value == "0" ){
		alert("กรุณาเลือกชนิดพืช!!");
		saveIs = false;
	}else if(document.economicBreedForm.province.value == "0|0" ){
		alert("กรุณาเลือกจังหวัด !!");
		saveIs = false;
	}
	if(saveIs){
	    document.economicBreedForm.cmd.value="Save";
	    document.economicBreedForm.action= "<%=request.getContextPath()%>/EconomicBreed.do";
	    document.forms["economicBreedForm"].submit();
	}
}
function removeOptionsBlank(sel) {
    var par;
    par = sel.options[0].parentNode;
    par.removeChild( sel.options[0] );
}
function closePage(){
   			document.economicBreedListForm.cmd.value = "Search"; 
   			document.economicBreedListForm.breedTypeId.value = ""; 
   			document.economicBreedListForm.provinceNo.value = "0"; 
   			document.economicBreedListForm.action="<%=request.getContextPath()%>/EconomicBreedList.do";
			document.forms["economicBreedListForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg) && !"Edit".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
 	<%}%>
 <%}%>
}
</script>  
</head>
<body onload = "loadData();">
<dcs:validateForm formName="economicBreedForm" formAction="EconomicBreed.do" formBean="EconomicBreedForm">
<input type="hidden" name="cmd" />
<input type="hidden" name="msg" />
<input type="hidden" name="tmpEdit" />
<div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
		<div class="navigator">
	    	<div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/EconomicBreedList.do'>การจัดการพืชเศรษฐกิจ</a></li>
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
                <!--  <p>ข้อมูลพืชเศรษฐกิจ</p> -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลพืชเศรษฐกิจ</font></span>
	            </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 380px;padding-right:0px">ชื่อชนิดพืช :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput">
						    <dcs:validateDropDown name="breedTypeId"  property="breedTypeId" dataSource="<%= breedTypeList %>" keyField="breedTypeId"  displayField="breedTypeName" style="width:200px;"  isRequire="true"/>
                        </li>
                    </ul>
                </div>
                <div class="line-input-keyin">
                  	<ul>
                    	<li class="topic" style="width: 380px;padding-right:0px">จังหวัด :</li>
                  		<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" style="width:170px;">
                        	<select id="province" name="province">
                        	
                        		<% if(provinceList !=null && provinceList.size()>0){
                        			String keyValue = "";
                        			for(int i=0; i<provinceList.size(); i++){
                        				keyValue = "";
                        				Province province = (Province) provinceList.get(i);
                        				keyValue = province.getProvinceNo() +"|" + province.getRegionNo();
                        		 %>
                        				<option value="<%=keyValue%>"><%=province.getThaiName() %></option>
                        		<%}} %>
                        	</select>
                           </li>
                    </ul>
                </div>
                
                
                <div class="clear"></div>
                
           			<table align="center">
			           <tr><td colspan="2">&nbsp;</td></tr>
			           
			           <tr><td><a class="btn-save" href="#" onclick="saveEconomicBreed();" id="write"></a></td>	
				           <td><a class="btn-cancel" href="#" onclick="closePage();"></a></td>			           
			           </tr>
			        </table>
             <div class="clear"></div>
             <div class="clear"></div>
        </div>
    </div>
	</div>
	<!--footer -->
    <%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>
<form name="economicBreedListForm" method="post" action="<%=request.getContextPath()%>/EconomicBreedList.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="breedTypeId" />
<input type="hidden" name="provinceNo" />
</form>
</body>
</html>