<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.DistrictListForm"%>
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

	DistrictListForm districtListForm = (DistrictListForm) request.getAttribute("DistrictListForm");
	List regionList = (ArrayList) session.getAttribute ("regionList");
	List provinceList = (ArrayList) session.getAttribute("provinceList");
	List districtList = (ArrayList) session.getAttribute("districtList");
	
	String msg = "";
	if(districtListForm!=null){
		if(!"".equals(districtListForm.getErrMessage())){
		  	msg = districtListForm.getErrMessage();
		}
	}
%>


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
  
function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
   		 alert("<%=msg%>"); 	 
 	<%}%>
 	loadMenu("M04");
}


function removeOptionsBlank(sel) {
    var par;
    par = sel.options[0].parentNode;
    par.removeChild( sel.options[0] );
}

function getProvince(regionObj){
    // get province list from regionCode	
	var regionCode = regionObj.value;
	document.districtListForm.cmd.value="GetProvince";
	document.districtListForm.action= "<%=request.getContextPath()%>/DistrictList.do";
	document.forms["districtListForm"].submit();
	
	
}

function goPage(cmd){
	if(cmd == 'Delete'){
		var checkList = document.getElementsByName('delDistrictNo');
		var size = checkList.length;
		var count=0;
		for(var i = 0 ; i < size ; i++){
			if(checkList[i].checked){
				count++;
			}
		}
		if (count > 0){
			if (confirm("ต้องการลบอำเภอ/เขตที่เลือก ใช่หรือไม่?")) {
				document.districtListForm.target="";
				document.districtListForm.cmd.value="Delete";
				document.districtListForm.action="DistrictList.do";
				document.forms["districtListForm"].submit();
			}
		}else{
			alert('กรุณาเลือกอำเภอ/เขตที่ต้องการลบ');
		}
	}else{
		document.districtListForm.cmd.value="Search";
		document.forms["districtListForm"].submit();
	}
}

function edit(regionNo, provinceNo, districtNo, thaiName, engName ){
    document.districtForm.cmd.value = "Edit";
	document.districtForm.regionNo.value = regionNo; 
	document.districtForm.provinceNo.value = provinceNo; 
	document.districtForm.districtNo.value = districtNo; 
    document.districtForm.thaiName.value = thaiName; 
    document.districtForm.engName.value = engName;
	document.districtForm.action= "<%=request.getContextPath()%>/District.do";
	document.forms["districtForm"].submit();
}

function addDistrict(){
	  
        document.districtForm.cmd.value="New";
	    document.districtForm.action= "<%=request.getContextPath()%>/District.do";
	    document.forms["districtForm"].submit();

}
function searchDistrict(){
	    document.districtListForm.cmd.value="Search";
	    document.districtListForm.action= "<%=request.getContextPath()%>/DistrictList.do";
	    document.forms["districtListForm"].submit();
}
function getProvinceInfo(objRName, objPName)
{           
 
          var regionDD 		= document.getElementById(objRName);
          var provinceDD 	= document.getElementById(objPName);
          
            
            if((regionDD.options[regionDD.selectedIndex].value=='')||(regionDD.options[regionDD.selectedIndex].value==0)){ //ถ้ายังไม่เลือกภูมิภาค ให้จังหวัดเป็นกรุณาเลือก
                  provinceDD.options.length = 1;
                  provinceDD.options[0].value="";
                  provinceDD.options[0].text="กรุณาเลือก";
                  provinceDD.options[0].selected = true;
            }else{														//ถ้าเลือกภูมิภาคแล้ว เข้าหลังบ้าน
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetProvince";
                  reqParameters.regionNo = regionDD.options[regionDD.selectedIndex].value;   
                  new Ajax.Request("<%=request.getContextPath()%>/DistrictList.do",
                  {     
                        method: 'post',
                        asynchronous: false,
                        parameters: reqParameters,
                        encoding: 'UTF-8',
                        onSuccess: function(transport)
                        {                             
                            var json = transport.responseText.evalJSON(); 
                              if(json !=null && json != ""){
                                    provinceDD.options.length = json.length+1;
                                    provinceDD.options[0].value="";
                                    provinceDD.options[0].text="กรุณาเลือก";
                                    provinceDD.options[0].selected = true;
                                    
                                    var r = 0;
                                    for(var i = 0; i < json.length; i++){           
                                          r=r+1;                              
                                          provinceDD.options[r].value=json[i].provinceNo;
                                          provinceDD.options[r].text=json[i].provinceThai;      
                                    }
                              }
                        },
                        onFailure: function() { 
                             // alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
                        }
                  });
            }
}


</script>
</head>  
<body onload="showErrorMessage();">
<dcs:validateForm formName="districtListForm" formAction="DistrictList.do" formBean="DistrictListForm">
<input type="hidden" name="cmd" value="DirtyList" />
<input type="hidden" name="districtNo" />
<div class="main-inside">
    <!-- insert header -->
	<%@include file="/header.jsp" %>
	<div class="navigator">
	    <div class="inside">
		 		<ul>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
		            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
		            <li><a style="color:#333" href='<%=request.getContextPath()%>/DistrictList.do'>การจัดการอำเภอ</a></li>
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
                <div class="search-header" style="text-align:left;">
                 <!-- <p>ค้นหาอำเภอ</p>  -->
                 &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาอำเภอ</font></span>
                 </div>
                <div class="line-input">
                	<ul>
                		<li class="topic" style="width: 100px">ภูมิภาค : </li>
                        <li class="boxinput" style="width:170px;">
                            <dcs:validateDropDown name="regionNo" property="regionNo" dataSource="<%= regionList %>" keyField="regionNo"  displayField="regionName" style="width:170px;" onChange="getProvinceInfo('regionNo','provinceNo')" isRequire="true"/>
                        </li>
                        
                		<li class="topic" style="width:60px;">จังหวัด : </li>
                        <li class="boxinput" style="width:170px">
                            <dcs:validateDropDown name="provinceNo" property="provinceNo" dataSource="<%= provinceList %>"  keyField="provinceNo"  displayField="thaiName" style="width:170px;" isRequire="true"/>
                        </li>
                        
                        <li class="topic" style="width:60px;">อำเภอ : </li>
                        <li style="width:150px">
                            <dcs:validateText name="districtName" property="districtName" maxlength="200" style="width:160px;"/>
                        </li>
                
                	</ul>
                    <ul class="btn-search-box">
                    	<li>
                			<button class="btn-search" onclick="searchDistrict();" style="padding-bottom: 9px"></button>
                    	</li>
                    </ul>
                </div>
                
                

			</div>


            <div class="btn-manage" style="margin-left:0px;">
            	   <a class="btn-add" onclick="addDistrict();" id="write"></a>
                   <a class="btn-del" onclick="goPage('Delete');" id="del"></a>
            </div>
              <div class="clear"></div>
         
            <table border="0" width="96%" ><tr>
            <%   if(districtList!=null){ %>
            <td><dcs:grid dataSource="<%= districtList %>" name="districtList" pageSize="<%=districtListForm.getDisplayRow()%>" width="100%">
                 <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                                 imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                 <dcs:gridsorter/>                 
                 <dcs:checkboxcolumn name="delDistrictNo" dataField="districtNo" headerText="" width="130"  HAlign="left"  />
                 <dcs:textcolumn dataField="thaiName" headerText="อำเภอ" width="250" style="cursor:pointer;" sortable="true" HAlign="left" cssClass="address-sr" onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{thaiName}','{engName}');"/>
                 <dcs:textcolumn dataField="provinceName" headerText="จังหวัด" style="cursor:pointer;" width="250" sortable="true" HAlign="left" cssClass="address-sr" onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{thaiName}','{engName}');"/>
                 <dcs:textcolumn dataField="regionName" headerText="ภูมิภาค" style="cursor:pointer;" width="250" sortable="true" HAlign="left" cssClass="tel-sr" onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{thaiName}','{engName}');"/>
                 <dcs:imagecolumn dataField="linkImageEdit" headerText=""  toolTip="แก้ไข" style="padding-top:2px;cursor:pointer;" width="30"  HAlign="center" cssClass="manage-sr" onClick="edit('{regionNo}','{provinceNo}','{districtNo}','{thaiName}','{engName}');"/>
                
                 </dcs:grid>
            </td>
            <% } %>
            </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countDistrict"))%> รายการ</div>
            <!-- edit by jane -->
            
            
        </div>
    </div>
    <%@include file="/footer.jsp" %>
 
</div>

</dcs:validateForm>

<form name="districtForm" method="post" action="<%=request.getContextPath()%>/District.do">

<input type="hidden" name="cmd" />
<input type="hidden" name="regionNo" />
<input type="hidden" name="provinceNo" />
<input type="hidden" name="districtNo" />
<input type="hidden" name="thaiName" />
<input type="hidden" name="engName" />
</form>
</body>
</html>