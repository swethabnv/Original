<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.BankForm"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.Branch"%>
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
    response.setContentType("text/html; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0);

    List branchList = (ArrayList) session.getAttribute("branchList");
    List newBranchList = (ArrayList) session.getAttribute("newBranchList");
    BankForm bankForm = (BankForm)request.getAttribute("BankForm");
    String bankName = "", cmd = "", msg = "";
    long bankId =0;
    
    if(bankForm !=null){
        if(bankForm.getMsg() !=null && !"".equals(bankForm.getMsg())){
            msg = bankForm.getMsg()==null?"":bankForm.getMsg();
        }
        cmd = bankForm.getCmd()==null?"":bankForm.getCmd();
        bankName = bankForm.getBankName()==null?"":bankForm.getBankName();
        bankId= bankForm.getBankId();
    }
%>

<script type="text/javascript">
window.onmousedown = function (e) {
    var el = e.target;
    if (el.tagName.toLowerCase() == 'option' && el.parentNode.hasAttribute('multiple')) {
        e.preventDefault();
        
        // toggle selection
        if (el.hasAttribute('selected')) el.removeAttribute('selected');
        else el.setAttribute('selected', '');

        // hack to correct buggy behavior
        var select = el.parentNode.cloneNode(true);
        el.parentNode.parentNode.replaceChild(select, el.parentNode);
    }
}
function loadData(){
    showErrorMessage(); 
    document.bankForm.bankName.value = "<%= bankName %>";
    document.bankForm.cmd.value        = "<%= cmd %>";
    if( document.bankForm.cmd.value == 'Edit'){
        document.bankForm.bankName.value = "<%= bankName %>";
        document.bankForm.bankId.value = "<%= bankId %>";
    }    
    loadMenu("M07");    
}
function saveBank(){

        if(document.bankForm.bankName.value ==''){
            alert("กรุณาใส่ชื่อธนาคาร");
            document.bankForm.bankName.focus(); 
            return false;   
        }
        
			var branchList = new Array();
			var options = document.bankForm.branch;
			var iLen = options.length;
			
        if(iLen < 1){
            alert("กรุณาเลือกสาขาธนาคาร");
            options.focus(); 
            return false;   
        }
	
		  for (var i=0; i<iLen; i++) {
		    branchList.push(options[i].value);
		  }
				
			document.bankForm.branchCode.value = branchList;
	        
	        document.bankForm.cmd.value="Save";
	        document.bankForm.action= "<%=request.getContextPath()%>/Bank.do";
	        document.bankForm.submit();
}

function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg.substring(0, 18))){%> 
   			closePage();
 	<%}%>
 <%}%>
}

function closePage(){ 
        document.bankListForm.bankName.value = ""; 
        document.bankListForm.cmd.value = "Search"; 
        document.bankListForm.action="<%=request.getContextPath()%>/BankList.do";
        document.forms["bankListForm"].submit();
}


function checkBank(){
        var reqParameters = new Object();
        reqParameters.cmd = "GetBank";
        document.bankForm.bankName.value = document.bankForm.bankName.value.trim();
        reqParameters.bankName = document.bankForm.bankName.value;
        reqParameters.bankId = document.bankForm.bankId.value;
        new Ajax.Request("<%=request.getContextPath()%>/Bank.do",
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
                    alert("มีธนาคาร" + json[0].bankName + "อยู่แล้วในระบบ!!");
                    document.bankForm.bankName.value = "";
                    document.bankForm.bName.value = json[0].bankName;
                    window.setTimeout(function () {document.getElementById('bankName').focus();}, 0);
                }
            },onFailure: function() {   
                // alert('เกิดข้อผิดพลาด');
            }
        });

}

</script>

</head>
<body onload="loadData();">
<dcs:validateForm formName="bankForm" formAction="Bank.do" formBean="BankForm" >
<input type="hidden" name="cmd" />
<input type="hidden" name="bankId" />
<input type="hidden" name="bName" />
<input type="hidden" name="branchCode" />
<div class="main-inside">
    <!-- insert header -->
    <%@include file="/header.jsp" %>
    <div class="navigator">
        <div class="inside">
            <ul>
                <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
                <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
                <li><a style="color:#333" href='<%=request.getContextPath()%>/BankList.do'>การจัดการธนาคาร</a></li>
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
                <!-- <p>ข้อมูลธนาคาร</p>  -->
                &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลธนาคาร</font></span>
                </div>
                <div class="line-input-keyin">
                    <ul>
                        <li class="topic" style="width: 235px;padding-right:0px">ชื่อธนาคาร :</li>
                        <li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
                        <li class="boxinput" ><dcs:validateText name="bankName" property="bankName" maxlength="50" style="width:485px;" onChange="checkBank();"/>
                        </li>
                    </ul>
                </div>
	<div class="line-input-keyin" id="cooperative">
		<table align="center">
			<tbody>
			<tr><td>&nbsp;สาขาทั้งหมด </td><td>&nbsp;</td><td>&nbsp;สาขาที่เลือก&nbsp;<font color="red">*</font></td></tr>
				<tr>
					<td>
					<select style="width: 200px; height: 212px"
						multiple="multiple" name="branch_lst" id="branch_lst">
						<%
							if (branchList != null && branchList.size() > 0) {
									for (int i = 0; i < branchList.size(); i++) {
										Branch branch = (Branch) branchList.get(i);
						%>
						<option value="<%=branch.getBranchCode()%>" title="เลขที่ <%=branch.getAddress()%> <%=branch.getSubDistrictName()%> <%=branch.getDistrictName()%> <%=branch.getProvinceName()%>"><%=branch.getBranchName()%></option>
						<%
							}
								}
						%>
					</select></td>
					<td style="padding-left: 35px; padding-right: 35px;">
						<a class="btn-right" onclick="moveItem(document.getElementById('branch_lst'),document.getElementById('branch'));"></a>
						<br /><a class="btn-left" onclick="moveItem(document.getElementById('branch'),document.getElementById('branch_lst'));"></a>
					</td>
					<td>
					<select style="width: 200px; height: 212px"
						multiple="multiple" name="branch" id="branch" >
						<%
							if (newBranchList != null && newBranchList.size() > 0) {
								for (int i = 0; i < newBranchList.size(); i++) {
										Branch newBranch = (Branch) newBranchList.get(i);
						%>
						<option value="<%=newBranch.getBranchCode()%>" title="เลขที่ <%=newBranch.getAddress()%> <%=newBranch.getSubDistrictName()%> <%=newBranch.getDistrictName()%> <%=newBranch.getProvinceName()%>"><%=newBranch.getBranchName()%></option>
						<%
								}
							}
						%>
					</select></td>
				</tr>				
			</tbody>
		</table>
	</div> 
                <br />
                <div class="clear"></div>
                    <table align="center">
                   
                       <tr> <td><a class="btn-save" onclick="saveBank();" id="write"></a></td>   
                            <td><a class="btn-cancel" onclick="closePage();"></a></td>                    
                       </tr>
                    </table>
             <div class="clear"></div>
        </div>
    </div>
    </div>
    <!--footer -->
    <%@include file="/footer.jsp" %>
</div>
</dcs:validateForm>

	<form name="bankListForm"  method="post" action="<%=request.getContextPath()%>/BankList.do">
		<input type="hidden" name="bankName" />
		<input type="hidden" name="cmd" />
	</form>

</body>
</html>