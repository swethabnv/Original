<!doctype html>
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.BankListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.Bank"%>

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
	
	BankListForm bankListForm = (BankListForm) request.getAttribute("BankListForm");
	List bankList = (ArrayList) session.getAttribute ("bankList");
	String msg = "";
	if(bankListForm!=null){
		if(!"".equals(bankListForm.getErrMessage())){
		  	msg = bankListForm.getErrMessage();
		}
	}
%>

<script type="text/javascript">

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delBankId');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("คุณต้องการลบธนาคารที่เลือกใช่หรือไม่?")) {
				document.bankListForm.target="";
				document.bankListForm.cmd.value="Delete";
				document.bankListForm.action="BankList.do";
				document.forms["bankListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกธนาคารที่ต้องการลบ');
		}
	}else{
		document.bankListForm.cmd.value="Search";
		document.forms["bankListForm"].submit();
	}
}

function edit(bankId, bankName){
	document.bankForm.cmd.value="Edit";
    document.bankForm.bankId.value = bankId; 
	document.bankForm.bankName.value = bankName;
	document.bankForm.action= "<%=request.getContextPath()%>/Bank.do";
	document.forms["bankForm"].submit();
}

function addBank(){
        document.bankForm.cmd.value="New";
	    document.bankForm.action= "<%=request.getContextPath()%>/Bank.do";
	    document.forms["bankForm"].submit();

}
function searchBank(){
	    document.bankListForm.cmd.value="Search";
	    document.bankListForm.action= "<%=request.getContextPath()%>/BankList.do";
	    document.forms["bankListForm"].submit();
}

function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 <%}%>
   loadMenu("M07");
}
</script>

</head>  
<body onload="showErrorMessage();">
<dcs:validateForm formName="bankListForm" formAction="BankList.do" formBean="BankListForm">
<input type="hidden" name="cmd" value="DirtyList"/>
<input type="hidden" name="bankId" />
<div class="main-inside">
   <!-- insert header -->
   <%@include file="/header.jsp" %>
    <div class="navigator">
	    <div class="inside">
	 		<ul>
	            <li><a href='<%=request.getContextPath()%>/UserList.do' style="color:#333" >หน้าหลัก</a></li>
	            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
	            <li><a style="color:#333" href='<%=request.getContextPath()%>/BankList.do'>การจัดการธนาคาร</a></li>
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
                <!-- <p>ค้นหาธนาคาร</p>  -->
                &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาธนาคาร</font></span>
                </div>
                <div class="line-input">
                	<ul>
                	<li class="topic" style="width: 345px">ชื่อธนาคาร : </li>
                	<li class="boxinput" style="width:240px">
                	<dcs:validateText name="bankName" property="bankName" maxlength="50" style="width:240px"/>
                	</li>
                	</ul>
                	<ul class="btn-search-box">
                    	<li>
                			<button class="btn-search" onclick="searchBank();" style="padding-top: 7px;"></button>
                    	</li>
                    </ul>
                </div>
			</div>
            
            <div class="btn-manage" style="margin-left:0px;">
            	   <a class="btn-add" href="#" onclick="addBank();" id="write"></a>
                   <a class="btn-del" href="#" onclick="goPage('Delete');" id="del"></a>
            </div>
            
            <div class="clear"></div>
            <div class="clear"></div>

            <!--  for dcsGrid -->
            <table border="0" width="96%" ><tr>
            <%   if(bankList!=null){ %>
            <td><dcs:grid dataSource="<%= bankList %>" name="bankList" pageSize="<%=bankListForm.getDisplayRow()%>" width="100%">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delBankId" dataField="bankId" headerText="" width="200" />
                 <!-- dcs:rownumcolumn headerText="ลำดับที่" width="140" /> -->
                 <dcs:textcolumn dataField="bankName" headerText="ชื่อธนาคาร" HAlign="left" width="220" style="cursor:pointer;" cssClass="manage-sr" sortable="true" onClick="edit('{bankId}','{bankName}');"/>
                 <dcs:imagecolumn dataField="linkImageEdit" headerText=""  width="5"  toolTip="แก้ไข" style="padding-top:2px;cursor:pointer;" HAlign="center" cssClass="manage-sr" onClick="edit('{bankId}','{bankName}');"/>
                 </dcs:grid>
            </td>
            <% } %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countBank"))%> รายการ</div>
            <!-- end dcsGrid -->
            
        </div>
    </div>
	<!--footer -->
    <%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>


<!-- Bank.do -->
<form name="bankForm" method="post" action="<%=request.getContextPath()%>/Bank.do">
<input type="hidden" name="cmd" />
<input type="hidden" name="bankId" />
<input type="hidden" name="bankName" />
</form>
</body>
</html>