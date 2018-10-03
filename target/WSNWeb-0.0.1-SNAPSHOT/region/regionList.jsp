<!doctype html>
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsndata.data.Region"%>
<%@page import="com.wsnweb.form.RegionListForm"%>
<%@page import="com.wsnweb.util.Utility"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

<title><%=Utility.get("WEB_TITLE")%></title>

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


<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	RegionListForm regionListForm = (RegionListForm) request.getAttribute("RegionListForm");
	List regionList = (ArrayList) session.getAttribute ("regionList");
	String msg = "";
	if(regionListForm!=null){
		if(!"".equals(regionListForm.getErrMessage())){
		  	msg = regionListForm.getErrMessage();
		}
	}
%>

<script type="text/javascript">

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delRegionNo');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("คุณต้องการลบภูมิภาคที่เลือกใช่หรือไม่?")) {
				document.regionListForm.target="";
				document.regionListForm.cmd.value="Delete";
				document.regionListForm.action="RegionList.do";
				document.forms["regionListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกภูมิภาคที่ต้องการลบ');
		}
	}else{
		document.regionListForm.cmd.value="Search";
		document.forms["regionListForm"].submit();
	}
}

function edit(regionNo, regionName){
	document.regionForm.cmd.value="Edit";
    document.regionForm.regionNo.value = regionNo; 
	document.regionForm.regionName.value = regionName;
	document.regionForm.action= "<%=request.getContextPath()%>/Region.do";
	document.forms["regionForm"].submit();
}

function addRegion(){
        document.regionForm.cmd.value="New";
	    document.regionForm.action= "<%=request.getContextPath()%>/Region.do";
	    document.forms["regionForm"].submit();

}
function searchRegion(){
	    document.regionListForm.cmd.value="Search";
	    document.regionListForm.action= "<%=request.getContextPath()%>/RegionList.do";
	    document.forms["regionListForm"].submit();
}

function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
   loadMenu("M06");
}
</script>

</head>  
<body onload="showErrorMessage();">
<dcs:validateForm formName="regionListForm" formAction="RegionList.do" formBean="RegionListForm">
<input type="hidden" name="cmd" value="DirtyList"/>
<input type="hidden" name="regionNo" />
<div class="main-inside">
   <!-- insert header -->
   <%@include file="/header.jsp" %>
    <div class="navigator">
	    <div class="inside">
	 		<ul>
	            <li><a href='<%=request.getContextPath()%>/UserList.do' style="color:#333" >หน้าหลัก</a></li>
	            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	            <li><a style="color:#333" href='<%=request.getContextPath()%>/RegionList.do'>การจัดการภูมิภาค</a></li>
				<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	           <li>ค้นหาข้อมูล</li>
	     	</ul>
	  </div>
   	</div>	
     <!-- insert header -->
	 <!-- start insert >> content -->
    <div class="content">
    	<div class="inside">
        	<div class="content-search">
                <div class="search-header" style="text-align: left">
                <!-- <p>ค้นหาภูมิภาค</p>  -->
                &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาภูมิภาค</font></span>
                </div>
                <div class="line-input">
                	<ul>
                	<li class="topic" style="width: 400px">ชื่อภูมิภาค : </li>
                	<li class="boxinput" style="width:140px">
                	<dcs:validateText name="regionName" property="regionName" maxlength="50" style="width:160px"/>
                	</li>
                	</ul>
                      
                	<ul class="btn-search-box">
                    	<li>
                			<button class="btn-search" onclick="searchRegion();" style="padding-top: 7px;"></button>
                    	</li>
                    </ul>
                </div>
			</div>
            
            <div class="btn-manage" style="margin-left:0px;">
            	   <a class="btn-add" onclick="addRegion();" id="write"></a>
                   <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
            </div>
            
            <div class="clear"></div>
            <div class="clear"></div>

            <!--  for dcsGrid -->
            <table border="0" width="96%" ><tr>
            <%   if(regionList!=null){ %>
            <td><dcs:grid dataSource="<%= regionList %>" name="regionList" pageSize="<%=regionListForm.getDisplayRow()%>" width="100%">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delRegionNo" dataField="regionNo" headerText="" width="165" />
                 <!-- dcs:rownumcolumn headerText="ลำดับที่" width="140" /> -->
                 <dcs:textcolumn dataField="regionName" headerText="ภูมิภาค" width="200" style="cursor:pointer;" sortable="true" onClick="edit('{regionNo}','{regionName}');"/>
                 <dcs:imagecolumn dataField="linkImageEdit" headerText=""  width="5"  toolTip="แก้ไข" style="padding-top:2px;cursor:pointer;" HAlign="center" cssClass="manage-sr" onClick="edit('{regionNo}','{regionName}');"/>
                 </dcs:grid>
            </td>
            <% } %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countRegion"))%> รายการ</div>
            <!-- end dcsGrid -->
            
        </div>
    </div>
	<!--footer -->
    <%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>


<!-- Region.do -->
<form name="regionForm" method="post" action="<%=request.getContextPath()%>/Region.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="regionNo" />
<input type="hidden" name="regionName" />
</form>
</body>
</html>