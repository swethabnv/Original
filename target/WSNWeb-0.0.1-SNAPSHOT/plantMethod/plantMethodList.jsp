<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.PlantMethodListForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.BreedType" %>

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

    PlantMethodListForm plantMethodListForm = (PlantMethodListForm) request.getAttribute("PlantMethodListForm");
    long breedTypeId = 0;
    String msg =""; 
    if(plantMethodListForm!=null){
        msg = plantMethodListForm.getErrMessage();
        breedTypeId = plantMethodListForm.getBreedTypeId();
    }

    List plantMethodList = (ArrayList) session.getAttribute("plantMethodList");
    List breedTypeList = (ArrayList) session.getAttribute("breedTypePlantList");
    %>
    <script type="text/javascript">

    //IE
        $(document).ready(function() 
        {
        	$("select option:empty").remove();
        	//$("#plantMethodName").focus();
        	//$('#plantMethodName').val($("#plantMethodName").val());
        });

    function searchPlantMethod(){
        document.plantMethodListForm.cmd.value="Search";
        document.plantMethodListForm.action= "<%=request.getContextPath()%>/PlantMethodList.do";
        document.forms["plantMethodListForm"].submit();
    }

    function goPage(cmd){
        if(cmd == 'Delete'){
            var checkList = document.getElementsByName('delPlantMethod');
            //alert(checkList.item());
            var size = checkList.length;
            var count=0;
            for(var i = 0 ; i < size ; i++){
                if(checkList[i].checked){
                    count++;
                }
            }
            if (count > 0){
                if (confirm("ต้องการลบข้อมูลที่เลือกใช่หรือไม่?")) {
                    document.plantMethodListForm.cmd.value="Delete";
                    document.plantMethodListForm.action="PlantMethodList.do";
                    document.forms["plantMethodListForm"].submit();
                }
            }else{
                alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
            }
        }else{
            document.plantMethodListForm.cmd.value="Search";
            document.forms["plantMethodListForm"].submit();
        }
    }

    function loadEdit(plantMethodId)
    {   
        document.plantMethodForm.plantMethodId.value = plantMethodId;
        document.plantMethodForm.cmd.value = "Edit";
        document.plantMethodForm.action= "<%=request.getContextPath()%>/PlantMethod.do";
        document.forms["plantMethodForm"].submit();
    }

    function addPlantMethod(){
        document.plantMethodForm.cmd.value="New";
        document.plantMethodForm.action= "<%=request.getContextPath()%>/PlantMethod.do";
        document.forms["plantMethodForm"].submit();
    }

    function loadData(){
        showErrorMessage();
        loadMenu("M23");
    }

    function showErrorMessage(){
        <% if(msg !=null && !"".equals(msg)){ %>
            alert("<%=msg%>"); 
        <%}%>
    }
    </script>

</head>

<body onload="loadData();">
    <dcs:validateForm formName="plantMethodListForm" formAction="PlantMethodList.do" formBean="PlantMethodListForm">
    <input type="hidden" name="cmd" value="DirtyList" />
    <div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
       <div class="navigator">
         <div class="inside">
            <ul>
                <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
                <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
                <li><a style="color:#333" href='<%=request.getContextPath()%>/PlantMethodList.do'>การจัดการวิธีการปลูก</a></li>
                <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
                <li>ค้นหาข้อมูล</li>
            </ul>
        </div>
        </div>  

        <!-- start insert >> content -->
        <div class="content">
            <div class="inside">

                <div class="content-search">
                    <div class="search-header">
                        <!--  <p>ค้นหาผู้ใช้งาน</p> -->
                        &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาการจัดการวิธีปลูก</font></span>

                    </div>
                    <div class="line-input">
                        <ul>
                            <li class="topic" style="width: 270px">ชื่อ : </li>
                            <li class="boxinput">
                                <dcs:validateText name="plantMethodName" property="plantMethodName" maxlength="20" style="width: 240px"/>
                            </li>
                        </ul>
                        <ul class="btn-search-box">
                            <li style="width: 80px;">
                                <button class="btn-search" onclick="searchPlantMethod();" style="padding-bottom: 9px;"></button>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="clear"></div>
                <div class="btn-manage" style="margin-left:0px;">
                    <a class="btn-add" onclick="addPlantMethod();" id="write"></a>
                    <a class="btn-del" onclick="goPage('Delete');" id="del" ></a>
                </div>
                <!--  for dcsGrid -->
                <table border="0" width="96%" ><tr>
                    <%   if(plantMethodList!=null){ %>
                    <td>
                    <dcs:grid dataSource="<%= plantMethodList %>" name="plantMethodList" pageSize="<%=plantMethodListForm.getDisplayRow()%>" width="100%">
                        <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                        imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                        <dcs:gridsorter/>                 
                        <dcs:checkboxcolumn name="delPlantMethod" dataField="plantMethodId" headerText="" width="70"  HAlign="left"  />
                        <!-- dcs:rownumcolumn headerText="ลำดับที่" width="60" HAlign="center" /> -->
                        <dcs:textcolumn dataField="plantMethodName" headerText="ชื่อวิธีการปลูก" width="75" style="cursor:pointer;" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{plantMethodId}');"/>
                        <!-- dcs:textcolumn dataField="breedTypeName" headerText="ชื่อพืช" width="70" sortable="true" HAlign="left" cssClass="address-sr" style="cursor:pointer;" onClick="loadEdit('{plantMethodId}');"/-->
                        <!-- dcs:textcolumn dataField="breedGroupName" headerText="ชื่อพันธุ์" width="60" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer;" onClick="loadEdit('{plantMethodId}');"/-->
                        <dcs:imagecolumn dataField="linkImageEdit" headerText="" toolTip="แก้ไข" style="cursor:pointer;padding-top:2px"  width="5"  HAlign="center" cssClass="manage-sr" onClick="loadEdit('{plantMethodId}');"/>
                    </dcs:grid>
                    </td><% } %>
                </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countPlantMethod"))%> รายการ</div>
                <!-- end dcsGrid -->
                <div class="clear"></div>

                <!-- edit by jane -->
            </div>
        </div>

        <%@include file="/footer.jsp" %>
    </div>
    </dcs:validateForm>

    <form name="plantMethodForm" method="post" action="<%=request.getContextPath()%>/PlantMethod.do">
        <input type="hidden" name="cmd" />
        <input type="hidden" name="plantMethodId" />
    </form>
</body>
</html>