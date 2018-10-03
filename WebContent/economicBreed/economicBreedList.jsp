<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.EconomicBreedListForm"%>
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

	
	List provinceList = (ArrayList) session.getAttribute("ecoProvinceList");
	//List breedTypeList = (ArrayList) session.getAttribute ("ecoBreedTypeList");
	EconomicBreedListForm economicBreedListForm = (EconomicBreedListForm) request.getAttribute("EconomicBreedListForm");
	List economicBreedList = (ArrayList) session.getAttribute("economicBreedList");

	
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
  
function searchEconomicBreed(){
	    document.economicBreedListForm.cmd.value="Search";
	    document.economicBreedListForm.action= "<%=request.getContextPath()%>/EconomicBreedList.do";
	    document.forms["economicBreedListForm"].submit();
	    
}
function removeOptionsBlank(sel) {
    var par;
    par = sel.options[0].parentNode;
    par.removeChild( sel.options[0] );
}
function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delbreedType');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.economicBreedListForm.cmd.value="Delete";
				document.economicBreedListForm.action="EconomicBreedList.do";
				document.forms["economicBreedListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.economicBreedListForm.cmd.value="Search";
		document.forms["economicBreedListForm"].submit();
	}
}

function loadEdit(breedTypeId,provinceNo,regionNo)
{	
	document.economicBreedForm.breedTypeId.value = breedTypeId;
	document.economicBreedForm.province.value = provinceNo+"|"+regionNo;
	document.economicBreedForm.cmd.value = "Edit";
	document.economicBreedForm.msg.value = "Edit";
	document.economicBreedForm.action= "<%=request.getContextPath()%>/EconomicBreed.do";
	document.forms["economicBreedForm"].submit();
}

function addEconomicBreed(){

        document.economicBreedForm.cmd.value="New";
	    document.economicBreedForm.action= "<%=request.getContextPath()%>/EconomicBreed.do";
	    document.forms["economicBreedForm"].submit();

}

function loadData(){
	//removeOptionsBlank(document.economicBreedListForm.provinceName);
    loadMenu("M14");
}

</script>
</head>
<body onload="loadData();">
<dcs:validateForm formName="economicBreedListForm" formAction="EconomicBreedList.do" formBean="EconomicBreedListForm">
<input type="hidden" name="cmd" value="DirtyList" />
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
		           <li>ค้นหาข้อมูล</li>
		     	</ul>
		 	</div>
    	</div>	
    	
		 <!-- content -->
		 
		 
	    <div class="content">
	    	<div class="inside">
	        	<div class="content-search">
	                <div class="search-header">
	               <!-- <p>ค้นหาพืชเศรษฐกิจ</p> --> 
	               &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาพืชเศรษฐกิจ</font></span>
	                </div>
	                <div class="line-input">
	                	<ul>
	                		<li class="topic" style="width: 250px">ชื่อชนิดพืช : </li>
		                	<li class="boxinput" style="width:170px">
								<dcs:validateText name="breedTypeName" property="breedTypeName" style="width:170px;" maxlength="100"/>
							</li>
	                    	<li class="topic" style="width: 100px">จังหวัด : </li>
	                        <li class="boxinput" style="width:150px">
	                            <dcs:validateDropDown name="provinceName" property="provinceName" dataSource="<%= provinceList %>"  keyField="thaiName"  isRequire="true" displayField="thaiName" style="width:160px;"/>
	                        </li>
	                        </ul>
	                        <ul class="btn-search-box">
	                        <li>
	             				<button class="btn-search" onclick="searchEconomicBreed();" style="padding-bottom: 9px;"></button>
	             			</li>
	                   </ul>
	                </div>
					<div class="btn-manage" style="margin-left:0px;">
			             <a class="btn-add" href="#"  onclick="addEconomicBreed();" id="write"></a>
			             <a class="btn-del" href="#"  onclick="goPage('Delete');" id="del"></a>
			        </div>
			        
			        
		         <!--  for dcsGrid -->
            	<table border="0" width="96%" ><tr>
            	<%   if(economicBreedList!=null){ %>
            	<td><dcs:grid dataSource="<%= economicBreedList %>" name="economicBreedList" pageSize="<%=economicBreedListForm.getDisplayRow()%>" width="100%">
                 	<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 	<dcs:gridsorter/>                 
                 	<dcs:checkboxcolumn name="delbreedType" dataField="breedTypeDel" headerText="" width="600"  HAlign="left" />
                 	<dcs:textcolumn dataField="breedTypeName" headerText="ชื่อชนิดพืช" style="cursor:pointer;" width="550" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{breedTypeId}','{provinceNo}','{regionNo}');"/>
                 	<dcs:textcolumn dataField="provinceName" headerText="จังหวัด" style="cursor:pointer;" width="600" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{breedTypeId}','{provinceNo}','{regionNo}');"/>
                 	<dcs:imagecolumn dataField="linkImageEdit" headerText="" style="padding-top:2px;cursor:pointer;" toolTip="แก้ไข" width="30"  HAlign="center" cssClass="manage-sr" onClick="loadEdit('{breedTypeId}','{provinceNo}','{regionNo}');"/>
                 	</dcs:grid>
            	</td>
            	<% } %>
            	</tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countEconomicBreed"))%> รายการ</div>
            	<!-- end dcsGrid -->
				</div>
			</div>
		</div>	
		
		
<!--footer -->
<%@include file="/footer.jsp" %>
    
	
</div>

</dcs:validateForm>
<form name="economicBreedForm" method="post" action="<%=request.getContextPath()%>/EconomicBreed.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="msg" />
<input type="hidden" name="breedTypeId" />
<input type="hidden" name="province" />
</form>
</body>


</html>