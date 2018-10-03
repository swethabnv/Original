<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>

<%@page import="com.wsnweb.form.LandCheckListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.Plant"%>
<%@page import="com.wsndata.data.CheckPeriod"%>
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
	
	LandCheckListForm landCheckListForm = (LandCheckListForm)request.getAttribute("LandCheckListForm");
	List landCheckList = (ArrayList) session.getAttribute("landCheckList");
	List checkPeriodList = (ArrayList) session.getAttribute("checkPeriodList");
	List plantYearList = (ArrayList) session.getAttribute("pYearList");
	List cooperativeList = (ArrayList) session.getAttribute("cooperativeList");
	
	String msg = "", idCard = "", startDate = "", endDate = "", result = "";
	int plantYear = 0, plantNo = 0, countLandCheck = 0;
	long checkPeriodId = 0, farmerGroupId=0;
	if(landCheckListForm!=null){
		if(!"".equals(landCheckListForm.getMsg())){
		  	msg = landCheckListForm.getMsg();
		}
		if(landCheckListForm.getIdCard()!=null)
			idCard = landCheckListForm.getIdCard();
		plantYear = landCheckListForm.getPlantYear();
		plantNo = landCheckListForm.getPlantNo();
		checkPeriodId = landCheckListForm.getCheckPeriodId();
		if(landCheckListForm.getStartDate()!=null)
			startDate = landCheckListForm.getStartDate();
		if(landCheckListForm.getEndDate()!=null)
			endDate = landCheckListForm.getEndDate();
		if(landCheckListForm.getResult()!=null)
			result = landCheckListForm.getResult();
		countLandCheck = landCheckList.size();
		if(landCheckListForm.getFarmerGroupId()>0)
			farmerGroupId = landCheckListForm.getFarmerGroupId();
	}
%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/GridStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/CalendarStyle.css" />

<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/hoverIntent.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/menu.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/dcswc.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/dcswc/dcswc.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/prototype.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.9.1.js"></script> 
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/scripts.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/superfish.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>

<script type="text/javascript">
  //-- Shift Focus to the Next TextBox using the Enter Key using jQuery -- //
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
 	  $("select option:empty").remove();
  }); 

function searchLandCheck(){
	    document.landCheckListForm.cmd.value="Search";
	    document.landCheckListForm.action= "<%=request.getContextPath()%>/LandCheckList.do";
	    document.forms["landCheckListForm"].submit();
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delLandCheckId');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่ ?")) {
				document.landCheckListForm.cmd.value="Delete";
				document.landCheckListForm.action="LandCheckList.do";
				document.forms["landCheckListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
		}
	}else{
		document.landCheckListForm.cmd.value="Search";
		document.forms["landCheckListForm"].submit();
	}
}

function loadEdit(landCheckId)
{
	document.landCheckForm.landCheckId.value = landCheckId;
	document.landCheckForm.cmd.value = "Edit";
	document.landCheckForm.action= "<%=request.getContextPath()%>/LandCheck.do";
	document.forms["landCheckForm"].submit();
 			 
}

function addLandCheck()
{
    document.landCheckForm.cmd.value = "New";
    document.landCheckForm.landCheckId.value = "0";
	document.landCheckForm.action= "<%=request.getContextPath()%>/LandCheck.do";
	document.forms["landCheckForm"].submit();
}
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
  loadMenu("M24");
  document.landCheckListForm.plantNo.value = "";
  <%if(plantYear > 0){%>
  	document.landCheckListForm.plantYear.value = "<%=plantYear%>";
  <%}if(plantNo > 0){%>
  	document.landCheckListForm.plantNo.value = "<%=plantNo%>";
  <%}if(!startDate.equals("")){%>
  	document.landCheckListForm.startDate.value = "<%=startDate%>";
  <%}if(!endDate.equals("")){%>
  	document.landCheckListForm.endDate.value = "<%=endDate%>";
  <%}if(checkPeriodId > 0){%>
  	document.landCheckListForm.checkPeriodId.value = "<%=checkPeriodId%>";
  <%}if(!result.equals("")){%>
  	document.landCheckListForm.result.value = "<%=result%>";
  <% }if(farmerGroupId>0){ %>
  	document.landCheckListForm.farmerGroupId.value = "<%=farmerGroupId %>";
  <% }%>
}

</script>

</head>       
<body onload="showErrorMessage();">
<dcs:validateForm formName="landCheckListForm" formAction="LandCheckList.do" formBean="LandCheckListForm">
<input type="hidden" name="cmd" value="DirtyList"/>
<div class="main-inside">
<%@include file="/header.jsp" %>
	<div class="navigator">
	<div class="inside">
	 	<ul>     
            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
            <li><a style="color:#333" href='<%=request.getContextPath()%>/LandCheckList.do'>การจัดการตรวจแปลง</a></li>
			<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
           <li>ค้นหาข้อมูล</li>
	    </ul>
	</div>
    </div>	
    <!-- start content -->
    <div class="content">
    	<div class="inside">    
        	<div class="content-search">
                <div class="search-header" >
                 &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >รายการตรวจแปลง</font></span>
               
                </div>
                <div class="line-input">
                	<ul>
                		<li class="topic" style="width: 190px;">เลขบัตรประจำตัวประชาชน : </li>
                		<li class="boxinput" style="width: 140px;">
                		<dcs:validateText name="idCard" property="idCard" maxlength="13" style="width:130px" onChange="checkNumber(this);"/>    	
                		</li>
                	</ul>
                	<ul>
                        <li class="topic" style="width: 53px">ชื่อ : </li>
                        <li class="boxinput" style="width:135px">
                            <dcs:validateText name="firstName" property="firstName" style="width:123px" maxlength="30"/>
                        </li>
                   </ul>
                	<ul>
                		<li class="topic"style="width:100px;">ปีการผลิต :</li>
			            <li class="boxinput" style="width: 100px;">
	                      <select id="plantYear"  name="plantYear" style="width:100px;height:29px;" >
		                    		<option value="0">กรุณาเลือก</option>
		                    		<%if(plantYearList !=null && plantYearList.size()>0){
		                    			for(int i=0; i<plantYearList.size(); i++){
		                    				Plant plant = (Plant)plantYearList.get(i);
		                    		 %>
		                    		 	<option value="<%= plant.getPlantYear()%>"><%= plant.getPlantYear()%></option>
		                    		 <%}} %>
		                  </select>
	                    </li>
                	</ul>
                    <ul>
                		<li class="topic" style="width: 80px;">ครั้งที่ : </li>
                		<li class="boxinput" style="width: 90px;">
                		<dcs:validateText name="plantNo" property="plantNo" maxlength="2" style="width: 80px;" />    	
                		</li>
                	</ul>
                </div>
                
                <div class="line-input">
                   <ul>
                    	<li class="topic" style="width: 190px;">วันที่ตรวจตั้งแต่ : </li>
                        <li class="boxinput" style="width:140px;">
                            <dcs:validateDate name="startDate" style="width: 100px;" />
	                    </li>
                   </ul>
                   <ul>
                        <li class="topic" style="width: 53px;">ถึงวันที่ : </li>
                        <li class="boxinput" style="width:135px;">
                            <dcs:validateDate name="endDate" style="width: 100px;" />
                        </li>
                   </ul>
                   <ul>
                        <li class="topic" style="width: 100px;">ระยะเวลาตรวจ : </li>
                        <li class="boxinput" style="width:190px;">
		                  <select id="checkPeriodId"  name="checkPeriodId" style="width:190px;height:29px;">
									<option value="0">กรุณาเลือก</option>
									<%if(checkPeriodList !=null && checkPeriodList.size()>0){
										for(int i=0; i<checkPeriodList.size(); i++){
											CheckPeriod period = (CheckPeriod)checkPeriodList.get(i);
											%>
											<option value="<%=period.getCheckPeriodId()%>"><%=period.getDescription()%></option>
									<%}} %>
							</select>
                        </li>
                    </ul>
                </div>
                
                <div class="line-input">
                	<ul>
                		<li class="topic" style="width: 190px;">ผลการตรวจ : </li>
                		<li class="boxinput" style="width: 105px;">
	                    <select id="result"  name="result" style="width:105px;height:29px;" >
		               		<option value="0">กรุณาเลือก</option>
		               		<option value="P">ผ่าน</option>
		               		<option value="F">ไม่ผ่าน</option>
		                </select>
		                </li>
		            </ul>
		         
		         <div class="line-input">
						<ul>
							<li class="topic" style="width: 80px">สังกัด : </li>
							<li class="boxinput" style="width: 250px">
								<dcs:validateDropDown name="farmerGroupId" dataSource="<%= cooperativeList %>" property="farmerGroupId" keyField="farmerGroupId" displayField="farmerGroupName" style="width:100%"/>
							</li>
						</ul>
					
                    <ul class="btn-search-box">
                    	<li class="boxinput" style="width: 114px; padding-left:465px;">
                			<button class="btn-search" onclick="searchLandCheck();"></button>
                    	</li>
                    </ul>
                </div>
			</div>
          
            
            <!--  for dcsGrid -->
            <div class="btn-manage" style="margin-left:0px;">
            	     <a class="btn-add" onclick="addLandCheck();" id="write"></a>
            		 <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
            </div>
            <table border="0" width="96%" >
            <tr>
            <% if(landCheckList!=null){ %>
            <td><dcs:grid  dataSource="<%= landCheckList %>" name="landCheckList" pageSize="<%=landCheckListForm.getDisplayRow()%>" width="960px">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delLandCheckId" dataField="landCheckId" headerText="" width="30" HAlign="left" />
                 <dcs:textcolumn dataField="idCard" headerText="เลขที่บัตรประชาชน" width="140" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{landCheckId}');"/>
                 <dcs:textcolumn dataField="farmerName" headerText="ชื่อ - นามสกุล" width="180" sortable="true" HAlign="left" cssClass="topic-sr" style="cursor:pointer" onClick="loadEdit('{landCheckId}');"/>
                 <dcs:textcolumn dataField="checkDateTh" headerText="วันที่ตรวจ" width="90" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{landCheckId}');"/>
                 <dcs:textcolumn dataField="checkPeriod" headerText="ระยะการตรวจ" width="110" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{landCheckId}');"/>
                 <dcs:textcolumn dataField="landRight" headerText="เอกสารสิทธิ์/เลขที่" width="130" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{landCheckId}');"/>
                 <dcs:textcolumn dataField="checkResult" headerText="ผลการตรวจ" width="95" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{landCheckId}');"/>
                 <dcs:textcolumn dataField="plantYear" headerText="ปีการผลิต" width="85" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{landCheckId}'); " style="cursor:pointer" />
                 <dcs:textcolumn dataField="plantNo" headerText="ครั้งที่" width="50" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" onClick="loadEdit('{landCheckId}'); " style="cursor:pointer" />
                 <dcs:imagecolumn dataField="linkImageEdit" headerText="" toolTip="แก้ไข" width="30" HAlign="center" cssClass="manage-sr" style="padding-top:2px;cursor:pointer;" onClick="loadEdit('{landCheckId}');" />
                 </dcs:grid>
            </td>
            <% } %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=countLandCheck%> รายการ</div>
            <!-- end dcsGrid -->
            <div class="clear"></div> 
        </div>
    </div>
<!-- footer -->
<%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>

	<form name="landCheckForm" method="post" action="<%=request.getContextPath()%>/LandCheck.do">
		<input type="hidden" name="cmd" />
		<input type="hidden" name="landCheckId" />
	</form>

</body>
</html>
