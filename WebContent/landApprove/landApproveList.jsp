<!doctype html>

<%@page import="com.wsnweb.form.LandApproveListForm"%>
<%@page import="com.lowagie.text.Document"%><html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.Plant"%>
<%@page import="com.wsnweb.form.LandCheckListForm" %>

<head>

<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	LandApproveListForm landApproveListForm = (LandApproveListForm)request.getAttribute("LandApproveListForm");
	List landApproveList = (ArrayList) session.getAttribute("landApproveList");
	List plantYearList = (ArrayList) session.getAttribute("pYearList");
	List cooperativeList = (ArrayList) session.getAttribute("cooperativeList");
	String msg = "", idCard = "", startDate = "", endDate = "", approvedDate = "", approved = "";
	int plantYear = 0, plantNo = 0, countApproveList = 0;
	if(landApproveListForm!=null){
		if(!"".equals(landApproveListForm.getMsg())){
		  	msg = landApproveListForm.getMsg();
		}
		if(landApproveListForm.getIdCard()!=null)
			idCard = landApproveListForm.getIdCard();
		plantYear = landApproveListForm.getPlantYear();
		plantNo = landApproveListForm.getPlantNo();
				
		if(landApproveListForm.getStartDate()!=null)
			startDate = landApproveListForm.getStartDate();
		if(landApproveListForm.getEndDate()!=null)
			endDate = landApproveListForm.getEndDate();
		if(landApproveListForm.getApprovedDate()!=null)
			approvedDate = landApproveListForm.getApprovedDate();
		if(landApproveListForm.getApproved()!=null)
			approved = landApproveListForm.getApproved();
		countApproveList = landApproveList.size();
		
	}
 %>
 		 		 		
<title><%=Utility.get("WEB_TITLE")%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
  
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
  loadMenu("M25");
  document.landApproveListForm.plantNo.value = "";
  <%if(plantYear > 0){%>
  	document.landApproveListForm.plantYear.value = "<%=plantYear%>";
  <%}if(plantNo > 0){%>
  	document.landApproveListForm.plantNo.value = "<%=plantNo%>";
  <%}if(!startDate.equals("")){%>
  	document.landApproveListForm.startDate.value = "<%=startDate%>";
  <%}if(!endDate.equals("")){%>
  	document.landApproveListForm.endDate.value = "<%=endDate%>";
  <%}if(!approvedDate.equals("")){%>
  	document.landApproveListForm.approvedDate.value = "<%=approvedDate%>";
  <%}if(!approved.equals("")){%>
  	document.landApproveListForm.approved.value = "<%=approved%>";
  <% } %>
  
  }
function searchLandApprove(){
	    document.landApproveListForm.cmd.value="Search";
	    document.landApproveListForm.action= "<%=request.getContextPath()%>/LandApproveList.do";
	    document.forms["landApproveListForm"].submit();
}
function setApproved(plantId, SEQ, typeId, docNo){
		document.landApproveListForm.cmd.value="Approved";
		document.landApproveListForm.approveResult.value="P";
		document.landApproveListForm.plantId.value=plantId;
		document.landApproveListForm.SEQ.value=SEQ;
		document.landApproveListForm.typeId.value=typeId;
		document.landApproveListForm.docNo.value=docNo;
		document.landApproveListForm.action="<%=request.getContextPath()%>/LandApproveList.do";
		document.forms["landApproveListForm"].submit();
}
function setNotApproved(plantId, SEQ, typeId, docNo){
		document.landApproveListForm.cmd.value="NotApproved";
		document.landApproveListForm.approveResult.value="F";
		document.landApproveListForm.plantId.value=plantId;
		document.landApproveListForm.SEQ.value=SEQ;
		document.landApproveListForm.typeId.value=typeId;
		document.landApproveListForm.docNo.value=docNo;
		document.landApproveListForm.action="<%=request.getContextPath()%>/LandApproveList.do";
		document.forms["landApproveListForm"].submit();
}
function disableLinkApprove(approved,plantId, SEQ, typeId, docNo){
	var result;
	if(approved=="อนุมัติ"){
	 return null;
	}else if(approved=="ไม่อนุมัติ"){ 
	 return null;
	 }else{
	 result = confirm('กรุณายืนยันผลการ อนุมัติ ');
	 if(result){
	  setApproved(plantId, SEQ, typeId, docNo);
	 }else
	  return null;
	 }
}
function disableLinkNotApprove(approved,plantId, SEQ, typeId, docNo){
	var result;
	if(approved=="อนุมัติ"){
	 return null;
	}else if(approved=="ไม่อนุมัติ"){ 
	 return null;
	 }else{
	 result = confirm('กรุณายืนยันผลการ ไม่อนุมัติ ');
	 if(result){
	 setNotApproved(plantId, SEQ, typeId, docNo);
	 }else
	  return null;
	 }
}
</script>

<style type="text/css">
a:visited {
	color: blue; text-decoration: underline;
}
.gridHeader td:first-child, .gridRowOdd td:first-child, .gridRowEven td:first-child {
	padding: 0 5px;
}
</style>
</head>


<body onload="showErrorMessage()">
<dcs:validateForm formName="landApproveListForm" formAction="LandApproveList.do" formBean="LandApproveListForm">
<input type="hidden" name="cmd" value="DirtyList"/>
<input type="hidden" name="approveResult" />
<input type="hidden" name="plantId" />
<input type="hidden" name="SEQ" />
<input type="hidden" name="typeId" />
<input type="hidden" name="docNo" />
<div class="main-inside">
<%@include file="/header.jsp" %>
<div class="navigator">
	<div class="inside">
	 	<ul>     
            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
            <li><a style="color:#333" href='<%=request.getContextPath()%>/LandCheckList.do'>การจัดการตรวจแปลง</a></li>
			<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
           <li>ค้นหารายการอนุมัติ</li>
	    </ul>
	</div>
    </div>
<!--    Start search form-->
    <div class="content">
    	<div class="inside">
    		<div class="content-search">
    			<div class="search-header">
    			&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >รายการอนุมัติการรตรวจแปลง</font></span>
    			</div>
    			<div class="line-input">
                	<ul>
                		<li class="topic" style="width: 165px;">เลขบัตรประจำตัวประชาชน : </li>
                		<li class="boxinput" style="width: 140px;">
                			<dcs:validateText name="idCard" property="idCard" maxlength="13" style="width:130px" onChange="checkNumber(this);"/>
                		</li>
                	</ul>
                	<ul>
                        <li class="topic" style="width: 60px">ชื่อ : </li>
                        <li class="boxinput" style="width:120px">
                            <dcs:validateText name="firstName" property="firstName" style="width:123px" maxlength="30"/>
                        </li>
                   </ul>
                	<ul>
                		<li class="topic" style="width:125px;">ปีการผลิต :</li>
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
                    	<li class="topic" style="width: 90px;">ครั้งที่ : </li>
                		<li class="boxinput" style="width: 100px;">
                		<dcs:validateText name="plantNo" property="plantNo" maxlength="2" style="width: 95px;" />     	
                		</li>
                	</ul>
                </div>
                <div class="line-input">
                   <ul>
                    	<li class="topic" style="width: 165px;">วันที่ทำรายการตั้งแต่ : </li>
                        <li class="boxinput" style="width:140px;">
                            <dcs:validateDate name="startDate" style="width: 100px;" />
	                    </li>
	                    
                   </ul>
                   <ul>
                        <li class="topic" style="width: 60px;">ถึงวันที่ : </li>
                        <li class="boxinput" style="width:135px;">
                             <dcs:validateDate name="endDate" style="width: 100px;" />
                        </li>
                   </ul>
                   <ul>
                        <li class="topic" style="width: 110px;">ผลการอนุมัติ : </li>
                        <li class="boxinput" style="width:100px;">
	                      <select id="approved" name="approved" style="width:100px;height:29px;">
		                    		<option value="">กรุณาเลือก</option>
		                    		<option value="P">อนุมัติ</option>
		                    		<option value="F">ไม่อนุมัติ</option>
		                    		<option value="W">รอการอนุมัติ</option>
		                  </select>
                        </li>
                    </ul>
                </div>
		         <div class="line-input">
						<ul>
							<li class="topic" style="width: 165px">สังกัดสหกรณ์ : </li>
							<li class="boxinput" style="width: 250px">
								<dcs:validateDropDown name="farmerGroupId" dataSource="<%= cooperativeList %>" property="farmerGroupId" keyField="farmerGroupId" displayField="farmerGroupName" style="width:100%"/>
							</li>
						</ul>
					
                    <ul class="btn-search-box">
                    	<li class="boxinput" style="width: 114px; padding-left:797px;">
                			<button class="btn-search" onclick="searchLandApprove();"></button>
                    	</li>
                    </ul>
                </div>
    		</div>
<!--    	Begin Grid-->
			<table border="0" width="96%" >
			<tr>
			<% if(landApproveList!=null){ %>
			<td>
				
				<dcs:grid dataSource="<%= landApproveList %>" name="landApproveList" pageSize="<%=landApproveListForm.getDisplayRow()%>" width="960px">
				<dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                <dcs:gridsorter/>
                
                <dcs:textcolumn dataField="idCard" headerText="เลขที่บัตรประชาชน" width="140" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer"/>
                <dcs:textcolumn dataField="farmerName" headerText="ชื่อ - นามสกุล" width="160" sortable="true" HAlign="left" cssClass="topic-sr" style="cursor:pointer" />
                <dcs:textcolumn dataField="landRight" headerText="เอกสารสิทธิ์/เลขที่" width="150" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" />  
				<dcs:textcolumn dataField="plantYear" headerText="ปีการผลิต" width="85" sortable="true" HAlign="center" cssClass="tel-sr"  style="cursor:pointer" />
				<dcs:textcolumn dataField="plantNo" headerText="ครั้งที่" width="60" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer"  />
				<dcs:textcolumn dataField="approved" headerText="ผลการอนุมัติ" width="100" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer" />
				<dcs:textcolumn dataField="approvedDate" headerText="วันที่ทำรายการ" width="115" sortable="true" HAlign="center" cssClass="tel-sr" style="cursor:pointer"/>
				<dcs:textcolumn dataField="linkApproved" headerText="" width="70" sortable="false" HAlign="center" cssClass="tel-sr" style="cursor:pointer; text-decoration: underline; color: blue" onClick="disableLinkApprove('{approved}','{plantId}','{seq}','{typeId}','{docNo}');"/>
				<dcs:textcolumn dataField="linkNotApproved" headerText="" width="75" sortable="false" HAlign="center" cssClass="tel-sr" style="cursor:pointer; text-decoration: underline; color: blue" onClick="disableLinkNotApprove('{approved}','{plantId}','{seq}','{typeId}','{docNo}');" />
				</dcs:grid>
			</td>
			
			<% } %>
			</tr>
			</table>
			<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=countApproveList%> รายการ</div> 
    		<div class="clear"></div> 
    	</div>
    </div>

<%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>
</body>

</html>