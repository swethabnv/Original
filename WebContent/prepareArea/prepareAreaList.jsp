<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.form.PrepareAreaListForm"%>
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

    PrepareAreaListForm prepareAreaListForm = (PrepareAreaListForm) request.getAttribute("PrepareAreaListForm");
    long breedTypeId = 0;
    String msg =""; 
    if(prepareAreaListForm!=null){
        msg = prepareAreaListForm.getErrMessage();
        breedTypeId = prepareAreaListForm.getBreedTypeId();
    }

    List prepareAreaList = (ArrayList) session.getAttribute("prepareAreaList");
    List breedTypeList = (ArrayList) session.getAttribute("breedTypePlantList");
    %>
    <script type="text/javascript">


    function searchPrepareArea(){
        document.prepareAreaListForm.cmd.value="Search";
        document.prepareAreaListForm.action= "<%=request.getContextPath()%>/PrepareAreaList.do";
        document.forms["prepareAreaListForm"].submit();
    }

    function goPage(cmd){
        if(cmd == 'Delete'){
            var checkList = document.getElementsByName('delPrepareArea');
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
                    document.prepareAreaListForm.cmd.value="Delete";
                    document.prepareAreaListForm.action="PrepareAreaList.do";
                    document.forms["prepareAreaListForm"].submit();
                }
            }else{
                alert('กรุณาเลือกข้อมูลที่จะทำการลบ');
            }
        }else{
            document.prepareAreaListForm.cmd.value="Search";
            document.forms["prepareAreaListForm"].submit();
        }
    }

    function loadEdit(prepareAreaId)
    {   
        document.prepareAreaForm.prepareAreaId.value = prepareAreaId;
        document.prepareAreaForm.cmd.value = "Edit";
        document.prepareAreaForm.action= "<%=request.getContextPath()%>/PrepareArea.do";
        document.forms["prepareAreaForm"].submit();
    }

    function addPrepareArea(){
        document.prepareAreaForm.cmd.value="New";
        document.prepareAreaForm.action= "<%=request.getContextPath()%>/PrepareArea.do";
        document.forms["prepareAreaForm"].submit();
    }

    function loadData(){
        showErrorMessage();
        loadMenu("M22");
    }

    function showErrorMessage(){
        <% if(msg !=null && !"".equals(msg)){ %>
            alert("<%=msg%>"); 
        <%}%>
    }
    </script>

</head>

<body onload="loadData();">
    <dcs:validateForm formName="prepareAreaListForm" formAction="PrepareAreaList.do" formBean="PrepareAreaListForm">
    <input type="hidden" name="cmd" value="DirtyList" />
    <div class="main-inside">
<!-- insert header -->
<%@include file="/header.jsp" %>
       <div class="navigator">
         <div class="inside">
            <ul>
                <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
                <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
                <li><a style="color:#333" href='<%=request.getContextPath()%>/PrepareAreaList.do'>การจัดการแปลงก่อนเตรียมดิน</a></li>
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
                        &nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ค้นหาการจัดการแปลงก่อนเตรียมดิน</font></span>

                    </div>
                    <div class="line-input">
                        <ul>
                            <li class="topic" style="width: 270px">ชื่อ : </li>
                            <li class="boxinput">
                                <dcs:validateText name="prepareAreaName" property="prepareAreaName" maxlength="20" style="width: 240px"/>
                            </li>
                        </ul>
                        <ul class="btn-search-box">
                            <li style="width: 80px;">
                                <button class="btn-search" onclick="searchPrepareArea();" style="padding-bottom: 9px;"></button>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="clear"></div>
                <div class="btn-manage" style="margin-left:0px;">
                    <a class="btn-add" href="#" onclick="addPrepareArea();" id="write"></a>
                    <a class="btn-del" href="#" onclick="goPage('Delete');" id="del" ></a>
                </div>
                <!--  for dcsGrid -->
                <table border="0" width="96%" ><tr>
                    <%   if(prepareAreaList!=null){ %>
                    <td>
                    <dcs:grid dataSource="<%= prepareAreaList %>" name="prepareAreaList" pageSize="<%=prepareAreaListForm.getDisplayRow()%>" width="100%">
                        <dcs:gridpager imgFirst="/dcswc/images/First.gif" imgPrevious="/dcswc/images/Previous.gif" 
                        imgNext="/dcswc/images/Next.gif" imgLast="/dcswc/images/Last.gif"/>
                        <dcs:gridsorter/>                 
                        <dcs:checkboxcolumn name="delPrepareArea" dataField="prepareAreaId" headerText="" width="40"  HAlign="left"  />
                        <!-- dcs:rownumcolumn headerText="ลำดับที่" width="60" HAlign="center" /> -->
                        <dcs:textcolumn dataField="prepareAreaName" headerText="ชื่อ" width="70" style="cursor:pointer;" sortable="true" HAlign="left" cssClass="address-sr" onClick="loadEdit('{prepareAreaId}');"/>
                        <dcs:textcolumn dataField="pprepareAreaName" headerText="ภายใต้" width="70" sortable="true" HAlign="left" cssClass="address-sr" style="cursor:pointer;" onClick="loadEdit('{prepareAreaId}');"/>
                        <!-- dcs:textcolumn dataField="breedTypeName" headerText="ชื่อพืช" width="70" sortable="true" HAlign="left" cssClass="address-sr" style="cursor:pointer;" onClick="loadEdit('{prepareAreaId}');"/-->
                        <!-- dcs:textcolumn dataField="breedGroupName" headerText="ชื่อพันธุ์" width="60" sortable="true" HAlign="left" cssClass="tel-sr" style="cursor:pointer;" onClick="loadEdit('{prepareAreaId}');"/-->
                        <dcs:imagecolumn dataField="linkImageEdit" headerText="" toolTip="แก้ไข" style="cursor:pointer;padding-top:2px"  width="5"  HAlign="center" cssClass="manage-sr" onClick="loadEdit('{prepareAreaId}');"/>
                    </dcs:grid>
                    </td><% } %>
                </tr></table>
				<div style="text-align:center; font-style:Italic;">ผลการค้นหาทั้งหมด <%=String.valueOf(session.getAttribute("countPrepareArea"))%> รายการ</div>
                <!-- end dcsGrid -->
                <div class="clear"></div>

                <!-- edit by jane -->
            </div>
        </div>

        <%@include file="/footer.jsp" %>
    </div>
    </dcs:validateForm>

    <form name="prepareAreaForm" method="post" action="<%=request.getContextPath()%>/PrepareArea.do">
        <input type="hidden" name="cmd" />
        <input type="hidden" name="prepareAreaId" />
    </form>
</body>
</html>