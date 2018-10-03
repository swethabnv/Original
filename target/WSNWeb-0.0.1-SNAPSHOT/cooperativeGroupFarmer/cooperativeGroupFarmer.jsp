<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.UserForm"%>
<%@page import="com.wsnweb.form.CooperativeGroupFarmerForm"%>
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css"/>

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
	
	CooperativeGroupFarmerForm cooperativeGroupFarmerForm = (CooperativeGroupFarmerForm) request.getAttribute("CooperativeGroupFarmerForm");
	List farmerGroupList = (ArrayList) session.getAttribute("farmerGroupList");
	List farmerList = (ArrayList) session.getAttribute("farmerList");
	String farmerGroupName= "";
	String msg = "",cmd = "",idCard = "",fname="",lname="";
	double tmptarget = 0;
	long farmerGroupId =0;
	if(cooperativeGroupFarmerForm!=null){
		farmerGroupId = cooperativeGroupFarmerForm.getFarmerGroupId();
		farmerGroupName = cooperativeGroupFarmerForm.getFarmerGroupName();
		tmptarget = cooperativeGroupFarmerForm.getTarget();
		msg = cooperativeGroupFarmerForm.getMsg()==null?"":cooperativeGroupFarmerForm.getMsg();
		cmd = cooperativeGroupFarmerForm.getCmd();
	}
%>

<script language="JavaScript">
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
 	 $('#strTarget').html(convertToDecimal(<%=tmptarget/1000%>));
  }); 

function loadData(){
	document.cooperativeGroupFarmerForm.farmerGroupId.value= "<%=farmerGroupId%>";
	document.cooperativeGroupFarmerForm.farmerGroupName.value= "<%=farmerGroupName%>";
	document.cooperativeGroupFarmerForm.target.value= "<%=tmptarget%>";
    loadMenu("M11");
    showErrorMessage(); 
}
function searchFarmer(){
	    document.cooperativeGroupFarmerForm.cmd.value = "Search";
	    document.cooperativeGroupFarmerForm.action= "<%=request.getContextPath()%>/CooperativeGroupFarmer.do";
	    document.forms["cooperativeGroupFarmerForm"].submit();
}
function convertToDecimal(NumberStr) {
    NumberStr+= '';
    NumberData = NumberStr.split('.');
    Number1 = NumberData[0];
    Number2 = NumberData.length > 1 ? '.' + NumberData[1] : '.00';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(Number1)) {
        Number1 = Number1.replace(rgx, '$1' + ',' + '$2');
    }
    return Number1 + Number2;
}
function showErrorMessage()
{
	<% if(!"".equals(msg)){ %>
   		 alert("<%= msg %>"); 	 
 	<%} %>
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delfarmerGroupName');
		var size = checkList.length;
		var count=0;
		var i;
		for( i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.cooperativeGroupFarmerForm.cmd.value="Delete";
				document.cooperativeGroupFarmerForm.action="CooperativeGroupFarmer.do";
				document.forms["cooperativeGroupFarmerForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	
	}else{
		document.cooperativeGroupFarmerForm.cmd.value="Search";
		document.forms["cooperativeGroupFarmerForm"].submit();
	}
}
function closePage(){
   			document.farmerGroupListForm.farmerGroupName.value = "";
   			document.farmerGroupListForm.cmd.value = "Search"; 
   			document.farmerGroupListForm.action="<%=request.getContextPath()%>/CooperativeGroupList.do";
			document.forms["farmerGroupListForm"].submit();
}

function showWindow()
  {	
     var new_window = window.open("<%=request.getContextPath()%>/CooperativeGroupAdd.do?farmerGroupId=<%=farmerGroupId%>","popup", "toolbar=no, scrollbars=no, resizable=no, top=100, left=150, width=850, height=560");
	 new_window.focus();
	
	 var windowCheckInterval = setInterval(function() { windowCheck(); }, 300);

	 function windowCheck() {
    	if (!new_window || new_window.closed) {
        	clearInterval(windowCheckInterval);
			searchFarmer();
    	}
	}
  }
  
</script>
</head>
<body onload="loadData();">
<dcs:validateForm formName="cooperativeGroupFarmerForm" formAction="CooperativeGroupFarmer.do" formBean="CooperativeGroupFarmerForm">
    <input type="hidden" name="cmd" value="DirtyList" />
	<input type="hidden" name="farmerGroupId"/>
	<input type="hidden" name="farmerGroupName"/>
	<input type="hidden" name="target" value="<%= tmptarget %>"/>
	<input type="hidden" name="delfarmerGroupName" />
<div class="main-inside">
<!-- insert header -->
		<%@include file="/header.jsp" %>
		<div class="navigator">
			<div class="inside">
	    	 	<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/CooperativeGroupList.do'>การจัดการสหกรณ์</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		           <li>การจัดการสมาชิกสหกรณ์</li>
		     	</ul>
		 	</div>
    	</div>	
    	
    	
<!-- insert header -->
	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        
        	<div class="content-search">
                <div class="search-header" >
                <!-- <p>แก้ไขสมาชิกสหกรณ์</p>  -->
                 &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >แก้ไขสมาชิกสหกรณ์</font></span> 
                </div>
	                <div class="line-input">
	                	<ul>
	                    	<li class="topic" style="width: 120px; font-weight: bold;" >ชื่อสหกรณ์ : </li>
	                    	<li class="topic" style="width: 300px; text-align: left;"><%=farmerGroupName %></li>
	                    	<li class="topic" style="width: 380px; font-weight: bold;" >เป้าหมาย : </li>
	                    	<li class="topic" style="width: 130px; text-align: left;"><span id="strTarget" style="padding:0;"></span> &nbsp;ตัน</li>
	                    </ul>
	                </div>
			</div>
            <div class="btn-manage" style="width:95.5%;margin:auto;padding-top:10px;">
            	   <a class="btn-add" onclick="showWindow();"></a>
                   <a class="btn-del" onclick="goPage('Delete');"></a>
            </div>
            <!--  for dcsGrid -->
            <table border="0" width="96%" align="center"><tr>
            <%   if(farmerGroupList!=null){ %>
            <td><dcs:grid dataSource="<%= farmerGroupList %>" name="farmerGroupList" pageSize="<%=cooperativeGroupFarmerForm.getDisplayRow()%>" width="100%">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delfarmerGroupName" dataField="idCard" headerText="" width="30"  HAlign="left" />
                 <dcs:textcolumn dataField="memberNo" headerText="เลขที่สมาชิก" width="90" sortable="true" HAlign="left" cssClass="tel-sr" />
                 <dcs:textcolumn dataField="idCard" headerText="เลขที่บัตรประชาชน" width="140" sortable="true" HAlign="left" cssClass="tel-sr" />
                 <dcs:textcolumn dataField="firstName" headerText="ชื่อ" width="100" sortable="true" HAlign="left" cssClass="tel-sr" />
                 <dcs:textcolumn dataField="lastName" headerText="นามสกุล" width="100" sortable="true" HAlign="left" cssClass="tel-sr" />
                 <dcs:textcolumn dataField="provinceName" headerText="จังหวัด" width="110" sortable="true" HAlign="left" cssClass="tel-sr" />
                 <dcs:textcolumn dataField="districtName" headerText="อำเภอ" width="110" sortable="true" HAlign="left" cssClass="tel-sr" />
                 <dcs:textcolumn dataField="subDistrictName" headerText="ตำบล" width="110" sortable="true" HAlign="left" cssClass="tel-sr" />
                 <dcs:textcolumn dataField="farmerGroupName" headerText="สังกัดกลุ่มเกษตรกร" width="120" sortable="true" HAlign="left" cssClass="tel-sr" />
                 </dcs:grid>
            </td>
           	<% } %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countFarmerGroup"))%> รายการ</div>
            <!-- end dcsGrid -->
					<table align="center">
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td>
								<a class="btn-back" onclick="closePage();"></a>
							</td>			           
						</tr>
					</table>
           <div class="clear"></div>
        </div>
    </div>
    
   	<!--footer -->
    <%@include file="/footer.jsp" %>
    <!-- end insert >> content --> 

<!-- end for header -->
</div>
</dcs:validateForm>
<form name="farmerGroupListForm"  method="post" action="<%=request.getContextPath()%>/FarmerGroupList.do">
<input type="hidden" name="farmerGroupName" />
<input type="hidden" name="cmd" />
</form>
</body>

</html>