<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.Calendar"%><html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wsnweb.form.ReportForm"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsndata.data.Region"%>
<%@page import="com.wsndata.data.Province"%>
<%@page import="com.wsndata.data.FarmerGroup"%>
<%@page import="com.wsndata.data.BreedType"%>
<%@page import="com.wsndata.data.BreedGroup"%>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="GENERATOR" content="Rational Application Developer" />
  <meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
  <meta name="viewport" content="width=1280,user-scalable=yes" />
  <title><%=Utility.get("WEB_TITLE")%></title>

  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/style.css" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/CalendarStyle.css" />

  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/dcswc/dcswc.js"></script>
  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/hoverIntent.js"></script>
  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/json2.js"></script>
  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/prototype.js"></script>
  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/jsdiv/jquery-1.10.2.js"></script>
  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/jsdiv/jquery-ui.js"></script>
  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/scripts.js"></script>
  <script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/superfish.js"></script>

  <%
  response.setHeader("Cache-Control", "no-cache");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", 0);

  List regionList = (ArrayList) session.getAttribute("regionList");
  List provinceList = (ArrayList) session.getAttribute("provinceList");
  List farmerGroupList = (ArrayList) session.getAttribute("farmerGroupList");
  List cooperativeList = (ArrayList) session.getAttribute("cooperativeList");
  List breedTypeList = (ArrayList) session.getAttribute("breedTypeList");
  List breedGroupList = (ArrayList) session.getAttribute("breedGroupList");
  List monthsList = (ArrayList) session.getAttribute("Months");
  List yearsList = (ArrayList) session.getAttribute("Years");
  List plantYearsList = (ArrayList) session.getAttribute("plantYears");
  List plantNoList = (ArrayList) session.getAttribute("plantNo");

  ReportForm reportForm = (ReportForm) session.getAttribute("ReportForm");
  String rep = "",msg = "";
  long branchCode = 0,provinceNo = 0;
  if(reportForm!=null){
    rep = reportForm.getRep();
    msg = reportForm.getMsg();
    provinceNo = reportForm.getProvinceNo();
  }
  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
  Calendar cal = Calendar.getInstance();
  cal.setTime(new Date());
  int year = cal.get(Calendar.YEAR)+543;
  int month = cal.get(Calendar.MONTH)+1;
  int day = cal.get(Calendar.DATE);
  String toDay = String.format("%02d/%02d/%d",day,month,year);
  %>
  <style type="text/css">
    [id^="line"], #seasonBox { display: none; }
    input[type="checkbox"] { width:auto; height:auto; }
  </style>
  
  <script type="text/javascript"> 
  $(window).scroll( function() { 
 	var scrolled_val = $(document).scrollTop().valueOf();
 	topPos = scrolled_val;
  });
  
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
  </script>
  <script type="text/javascript">
    function loadData(){
      document.reportForm.rep.value = "<%=rep%>";
      var line1 = document.getElementById("line1");
      var line2 = document.getElementById("line2");
      var line3 = document.getElementById("line3");
      var line4_1 = document.getElementById("line4-1");
      var line4_2 = document.getElementById("line4-2");
      var line5 = document.getElementById("line5");
      var line6 = document.getElementById("line6");
      var lineFta = document.getElementById("lineFta");
      var lineFmGrp = document.getElementById("lineFarmerGroup");
      
      var line2_1 = document.getElementById("line2-1");
      var line5_1 = document.getElementById("line5-1");
      var seasonBox = document.getElementById("seasonBox");
      
      if("R000" == document.reportForm.rep.value){
        line1.style.display = "block";
        line2.style.display = "block";
        line4_1.style.display = "block";
        line4_2.style.display = "block";
        lineFmGrp.style.display = "inline";
      } else if("R001" == document.reportForm.rep.value){
        line1.style.display = "block";
        line2.style.display = "block";
        line4_1.style.display = "block";
        line4_2.style.display = "block";
        lineFmGrp.style.display = "inline";
        line6.style.display = "block";
	  } else if("R002" == document.reportForm.rep.value){
        line1.style.display = "block";
        line2.style.display = "block";
        line4_1.style.display = "block";
        line4_2.style.display = "block";
        lineFmGrp.style.display = "inline";
        line6.style.display = "block";
	  } else if("R003" == document.reportForm.rep.value){
        line1.style.display = "block";
        line2.style.display = "block";
        line4_1.style.display = "block";
        line4_2.style.display = "block";
        line6.style.display = "block";
	  } else if("R004" == document.reportForm.rep.value){
        line1.style.display = "block";
        line2.style.display = "block";
        line4_1.style.display = "block";
        line4_2.style.display = "block";
        lineFmGrp.style.display = "inline";
        line6.style.display = "block";
	  } else if("R005" == document.reportForm.rep.value){
        line1.style.display = "block";
        line2.style.display = "block";
        line4_1.style.display = "block";
        line4_2.style.display = "block";
        lineFmGrp.style.display = "inline";
        line6.style.display = "block";
	  } else if("R006" == document.reportForm.rep.value){
        line1.style.display = "block";
        line2.style.display = "block";
        line4_1.style.display = "block";
        line4_2.style.display = "block";
        line6.style.display = "block";
	  } else if("R007" == document.reportForm.rep.value){
        line1.style.display = "block";
        line3.style.display = "block";
        lineFta.style.display = "inline";
        line4_1.style.display = "block";
        line6.style.display = "block";
        document.getElementById("provinceNo").style.width = "170px";
	  } else if("R008" == document.reportForm.rep.value){
        line1.style.display = "block";
        line3.style.display = "block";
        lineFta.style.display = "inline";
        line4_1.style.display = "block";
        line6.style.display = "block";
        document.getElementById("provinceNo").style.width = "170px";
	  } else if("R009" == document.reportForm.rep.value){
        line3.style.display = "block";
        line5.style.display = "block";
        line6.style.display = "block";
        lineFta.style.display = "inline";
        document.getElementById("regionNo").style.width = "170px";
        document.getElementById("breedType").style.width = "170px";
	  } else if("R010" == document.reportForm.rep.value){
        line3.style.display = "block";
        line5.style.display = "block";
        line6.style.display = "block";
        lineFta.style.display = "inline";
        document.getElementById("regionNo").style.width = "170px";
        document.getElementById("breedType").style.width = "170px";
	  } else if("R011" == document.reportForm.rep.value){
        line1.style.display = "block";
        line2.style.display = "block";
        line4_1.style.display = "block";
        line6.style.display = "block";
        document.getElementById("breedType").style.width = "170px";
         line5_1.style.display = "block";
	  } else if("R012" == document.reportForm.rep.value){
        line1.style.display = "block";
        line4_1.style.display = "block";
        line6.style.display = "block";
        document.getElementById("breedType").style.width = "170px";
         line5_1.style.display = "block";
	  }
        getFarmerGroup('provinceNo','cooperativeId','farmerGroupId');
	  
	  var year = new Date().getFullYear();
	  document.getElementById('forecastYearStart').value = year;
	  document.getElementById('forecastYearEnd').value = year;
	  
      loadMenu("M19");
    }

    function print(){
      document.reportForm.cmd.value = "";
      document.reportForm.action = "";
      document.reportForm.target = "";
        
      if("R000" == document.reportForm.rep.value){
			if(document.reportForm.endDate.value==''){
			 	alert("กรุณาเลือกวันที่สิ้นสุด");
			 	return false;
			}
      } else if("R001" == document.reportForm.rep.value){
			if(document.reportForm.endDate.value==''){
			 	alert("กรุณาเลือกวันที่สิ้นสุด");
			 	return false;
			}
      } else if("R002" == document.reportForm.rep.value){
			if(document.reportForm.endDate.value==''){
			 	alert("กรุณาเลือกวันที่สิ้นสุด");
			 	return false;
			}
      } else if("R003" == document.reportForm.rep.value){
			if(document.reportForm.endDate.value==''){
			 	alert("กรุณาเลือกวันที่สิ้นสุด");
			 	return false;
			}
      } else if("R004" == document.reportForm.rep.value){
			if(document.reportForm.endDate.value==''){
			 	alert("กรุณาเลือกวันที่สิ้นสุด");
			 	return false;
			}
      } else if("R005" == document.reportForm.rep.value){
			if(document.reportForm.endDate.value==''){
			 	alert("กรุณาเลือกวันที่สิ้นสุด");
			 	return false;
			}
      } else if("R006" == document.reportForm.rep.value){
			if(document.reportForm.endDate.value==''){
			 	alert("กรุณาเลือกวันที่สิ้นสุด");
			 	return false;
			}
      } else if("R011" == document.reportForm.rep.value){
			if(document.reportForm.endDate.value==''){
			 	alert("กรุณาเลือกวันที่สิ้นสุด");
			 	return false;
			}
      }
      document.reportForm.cmd.value = "Print"; 
      
      if("R009" == document.reportForm.rep.value || "R010" == document.reportForm.rep.value){
			var foreStartM = document.reportForm.forecastMonthStart.value;
			var foreStartY = document.reportForm.forecastYearStart.value;
			var foreEndM = document.reportForm.forecastMonthEnd.value;
			var foreEndY = document.reportForm.forecastYearEnd.value;
			
			var moths = monthDiff(new Date(foreStartY, foreStartM, 1), new Date(foreEndY, foreEndM, 1));
			if(moths < 1 || moths > 12) {
			 	alert("กรุณาเลือกช่วงเวลาภายใน 1-12 เดือน");
			 	return false;
			} else if(document.reportForm.regionNo.value==''){
			 	alert("กรุณาเลือกภูมิภาค");
			 	return false;
			}
			document.reportForm.cmd.value = "PrintItext"; 
      }
      
      document.reportForm.action="<%=request.getContextPath()%>/Report.do";
      document.reportForm.target = "_blank";
      document.forms["reportForm"].submit();
    }
    
    function monthDiff(d1, d2) {
       var months;
       months = (d2.getFullYear() - d1.getFullYear()) * 12;
       months -= d1.getMonth();
       months += d2.getMonth()+1;
       return months;
    }

    function getSeason(objBreedType)
    {
      var breedTypeDD = document.getElementById(objBreedType);
      var seasonDD = document.getElementById("seasonBox");
      var breedTypeObjName = breedTypeDD.options[breedTypeDD.selectedIndex].text;
      
      if(breedTypeObjName == "ข้าว") {
        seasonDD.style.display = "inline";
      } else {
        seasonDD.style.display = "none";
        document.getElementsByName("season")[0].checked = false;
        document.getElementsByName("season")[1].checked = false;
      }
    }

    function getBreedGroupInfo(objBreedType, objBreedGroup)
    {
      var breedTypeDD = document.getElementById(objBreedType);
      var breedGroupDD = document.getElementById(objBreedGroup);

      var breedTypeObjId = breedTypeDD.value;
      var breedTypeObjName = breedTypeDD.options[breedTypeDD.selectedIndex].text;

      if(breedTypeDD.options[breedTypeDD.selectedIndex].value == 0){
        breedGroupDD.options.length = 1;
        breedGroupDD.options[0].value="";
        breedGroupDD.options[0].text="กรุณาเลือก";
        breedGroupDD.options[0].selected = true;
      }else{
        var reqParameters = new Object();
        reqParameters.cmd = "GetBreedGroup";
        reqParameters.breedTypeId = breedTypeDD.options[breedTypeDD.selectedIndex].value;
        new Ajax.Request("<%=request.getContextPath()%>/Report.do",
        {
          method: 'post',
          asynchronous: false,
          parameters: reqParameters,
          encoding: 'UTF-8',
          onSuccess: function(transport)
          {
            var json = transport.responseText.evalJSON(); 
            if(json !=null && json != ""){

              breedGroupDD.options.length = json.length+1;
              breedGroupDD.options[0].value="";
              breedGroupDD.options[0].text="กรุณาเลือก";
              breedGroupDD.options[0].selected = true;
              var r = 0;
              for(var i = 0; i < json.length; i++){
                r=r+1;
                breedGroupDD.options[r].value=json[i].breedGroupId;
                breedGroupDD.options[r].text=json[i].breedGroupName;
              }
            }
          },
          onFailure: function() { 
            alert('Error');
          }
        });
      }
    }

    function getCooperative(objProvNo, objCooperative, objFarmerGroup)
    {
      var provinceDD = document.getElementById(objProvNo);
      var cooperativeDD = document.getElementById(objCooperative);
      var farmerGroupDD = document.getElementById(objFarmerGroup);

      var provinceTxt = document.reportForm.provinceName;
      var provinceObjNo = provinceDD.value;
      
      farmerGroupDD.options.length = 1;
      farmerGroupDD.options[0].value="";
      farmerGroupDD.options[0].text="กรุณาเลือก";
      farmerGroupDD.options[0].selected = true;

      if(provinceDD.options[provinceDD.selectedIndex].value == 0){
        cooperativeDD.options.length = 1;
        cooperativeDD.options[0].value="";
        cooperativeDD.options[0].text="กรุณาเลือก";
        cooperativeDD.options[0].selected = true;
      }else{
        var reqParameters = new Object();
        reqParameters.cmd = "GetCooperative";
        reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;
        new Ajax.Request("<%=request.getContextPath()%>/Report.do",
        {
          method: 'post',
          asynchronous: false,
          parameters: reqParameters,
          encoding: 'UTF-8',
          onSuccess: function(transport)
          {
            var json = transport.responseText.evalJSON(); 
              cooperativeDD.options.length = json.length+1;
              cooperativeDD.options[0].value="0";
              cooperativeDD.options[0].text="กรุณาเลือก";
              cooperativeDD.options[0].selected = true;
            if(json !=null && json != ""){
              var r = 0;
              for(var i = 0; i < json.length; i++){
                r=r+1;
                cooperativeDD.options[r].value=json[i].farmerGroupId;
                cooperativeDD.options[r].text=json[i].farmerGroupName;
              }
            }
          },
          onFailure: function() { 
            alert('Error');
          }
        });
      }
    }

    function getFarmerGroup(objProvNo, objCooperative, objFarmerGroup)
    {
      var provinceDD = document.getElementById(objProvNo);
      var cooperativeDD = document.getElementById(objCooperative);
      var farmerGroupDD = document.getElementById(objFarmerGroup);

      var cooperativeObjNo = cooperativeDD.value;
      var cooperativeObjName = cooperativeDD.options[cooperativeDD.selectedIndex].text;

      if(cooperativeDD.options[cooperativeDD.selectedIndex].value == 0){
        farmerGroupDD.options.length = 1;
        farmerGroupDD.options[0].value="";
        farmerGroupDD.options[0].text="กรุณาเลือก";
        farmerGroupDD.options[0].selected = true;
      }else{
        var reqParameters = new Object();
        reqParameters.cmd = "GetFarmerGroup";
        reqParameters.cooperativeId = cooperativeDD.options[cooperativeDD.selectedIndex].value;
        new Ajax.Request("<%=request.getContextPath()%>/Report.do",
        {
          method: 'post',
          asynchronous: false,
          parameters: reqParameters,
          encoding: 'UTF-8',
          onSuccess: function(transport)
          {
            var json = transport.responseText.evalJSON(); 
              farmerGroupDD.options.length = json.length+1;
              farmerGroupDD.options[0].value="0";
              farmerGroupDD.options[0].text="กรุณาเลือก";
              farmerGroupDD.options[0].selected = true;
            if(json !=null && json != ""){
              var r = 0;
              for(var i = 0; i < json.length; i++){
                r=r+1;
                farmerGroupDD.options[r].value=json[i].farmerGroupId;
                farmerGroupDD.options[r].text=json[i].farmerGroupName;
              }
            }
          },
          onFailure: function() { 
            alert('Error');
          }
        });
      }
    }
  </script>
</head>
<body onload="loadData();">
  <dcs:validateForm formName="reportForm" formAction="Report.do" formBean="ReportForm">
  <input type="hidden" name="rep" />
  <input type="hidden" name="cmd" />
  <input type="hidden" name="breedTypeItem" />
  <div class="main-inside">
    <%@include file="/header.jsp"%>
    <div class="navigator">
      <div class="inside">
        <ul>
          <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
          <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
          <li><a style="color:#333" >รายงาน</a></li>
        </ul>
      </div>
    </div>

    <!-- content -->
    <div class="content">
      <div class="inside">

        <div class="content-keyin">
          <div class="search-header">
            <!-- <p> msg </p>  -->
            &nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" ><%=msg%></font></span>
          </div>
          <div class="line-input-keyin" id="line1">
            <ul>
              <li class="topic" style="width: 150px;padding-right:0px">ปีการผลิต :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
              <li class="boxinput" style="width: 180px">
                <select id="plantYear" name="plantYear" style="width:100px;">
                  <% if(plantYearsList !=null && plantYearsList.size()>0){
                    for(int i=0; i<plantYearsList.size(); i++){
                      int plantYears = (Integer) plantYearsList.get(i);
                      %>
                      <option value="<%=plantYears%>"><%=plantYears%></option>
                      <%}} %>
                </select>
              </li>
              <li class="topic" style="width: 80px;padding-right:0px">ชนิดพืช :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 150px">
                <select id="breedTypeId"  name="breedTypeId" style="width:100px;" onChange="getSeason('breedTypeId');">
		           <%if(breedTypeList !=null && breedTypeList.size()>0){
		             for(int d=0; d<breedTypeList.size(); d++){
		               BreedType breedType = (BreedType)breedTypeList.get(d);
		               %>
		               <option value="<%=breedType.getBreedTypeId()%>"><%=breedType.getBreedTypeName()%></option>
		        <%}} %></select>
              </li>
              <span id="seasonBox" style="width:auto;padding-left:0;padding-top: 5px;">
              <li class="topic" style="width: 150px;padding-right:0px">ประเภท :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 150px">
                <input type='checkbox' id='cisneros' name='season' value='1' /><label for="cisneros"> นาปี</label>
                <input type='checkbox' id='doubleCrop' name='season' value='2' /><label for="doubleCrop"> นาปรัง</label>
              </li>
              </span>
            </ul>
          </div>
          
          <div class="line-input-keyin" id="line2">
            <ul>
              <li class="topic" style="width: 150px;padding-right: 0px">สิ้นสุดวันที่ :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
              <li class="boxinput" style="width: 180px">
                <dcs:validateDate name="endDate" style="width: 142px;" date="<%=toDay%>" />
              </li>
              <li class="topic" style="width: 130px;padding-right:0px 0px;text-align:left;"><font color="red" >ตย. 31/12/2557</font></li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 90px"><font color="red"></font></li>
              <%if(!"R011".equals(reportForm.getRep()) && !"R012".equals(reportForm.getRep())){ %>
              <li class="topic" style="width: 100px;padding-right:0px;">FTA :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 170px">
                <input type='checkbox' id='ftaYes' name='fta' value='1' /><label for="ftaYes"> ใช่</label>
                <input type='checkbox' id='ftaNo' name='fta' value='2' /><label for="ftaNo"> ไม่ใช่</label>
              </li>
              <%} %>
            </ul>
          </div>
          
          <div class="line-input-keyin" id="line3">
            <ul>
              <li class="topic" style="width: 150px;padding-right: 0px">ตั้งแต่เดือน :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
              <li class="boxinput" style="width: 180px">
                <select id="forecastMonthStart"  name="forecastMonthStart" style="width:100px;" >
		           <%if(monthsList !=null && monthsList.size()>0){
		             for(int a=0; a<monthsList.size(); a++){
		               %>
		               <option value="<%= a%>"><%= monthsList.get(a)%></option>
		        <%}} %></select>
                <select id="forecastYearStart"  name="forecastYearStart" style="width:65px;" >
		           <%if(yearsList !=null && yearsList.size()>0){
		             for(int b=0; b<yearsList.size(); b++){
		               %>
		               <option value="<%=Integer.valueOf(yearsList.get(b).toString())-543%>"><%=yearsList.get(b)%></option>
		        <%}} %></select>
              </li>
              <li class="topic" style="width: 80px;padding-right:0px;">ถึงเดือน :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
              <li class="boxinput" style="width: 170px">
                <select id="forecastMonthEnd"  name="forecastMonthEnd" style="width:100px;" >
		           <%if(monthsList !=null && monthsList.size()>0){
		             for(int a=0; a<monthsList.size(); a++){
		               %>
		               <option value="<%= a%>"><%= monthsList.get(a)%></option>
		        <%}} %></select>
                <select id="forecastYearEnd"  name="forecastYearEnd" style="width:65px;" >
		           <%if(yearsList !=null && yearsList.size()>0){
		             for(int b=0; b<yearsList.size(); b++){
		               %>
		               <option value="<%=Integer.valueOf(yearsList.get(b).toString())-543%>"><%=yearsList.get(b)%></option>
		        <%}} %></select>
              </li>
              <span id="lineFta" style="width:auto;padding-left:0;">
              <li class="topic" style="width: 80px;padding-right:0px;">FTA :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 170px">
                <input type='checkbox' id='ftaY' name='fta' value='1' /><label for="ftaY"> ใช่</label>
                <input type='checkbox' id='ftaN' name='fta' value='2' /><label for="ftaN"> ไม่ใช่</label>
              </li>
              </span>
            </ul>
          </div>
          
          <div class="line-input-keyin" id="line4-1">
            <ul>
              <li class="topic" style="width: 150px;padding-right:0px">จังหวัด :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 180px">
                <select id="provinceNo"  name="provinceNo" style="width:150px;" onChange="getCooperative('provinceNo','cooperativeId','farmerGroupId');" >
		           <%if(provinceList !=null && provinceList.size()>0){
		             for(int a=0; a<provinceList.size(); a++){
		               Province prov = (Province)provinceList.get(a);
		               %>
		               <option value="<%= prov.getProvinceNo()%>"><%= prov.getThaiName()%></option>
		        <%}} %></select>
              </li>
              <li class="topic" style="width: 80px;padding-right:0px">สหกรณ์ :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 360px">
                <select id="cooperativeId"  name="cooperativeId" style="width:360px;" onChange="getFarmerGroup('provinceNo','cooperativeId','farmerGroupId');">
		           <%if(cooperativeList !=null && cooperativeList.size()>0){
		             for(int b=0; b<cooperativeList.size(); b++){
		               FarmerGroup cooGrp = (FarmerGroup)cooperativeList.get(b);
		               %>
		               <option value="<%= cooGrp.getFarmerGroupId()%>"><%= cooGrp.getFarmerGroupName()%></option>
		        <%}} %></select>
              </li>
            </ul>
          </div>
          
          <div class="line-input-keyin" id="line4-2">
            <ul>
              <span id="lineFarmerGroup" style="width:auto;padding-left:0;">
              <li class="topic" style="width: 150px;padding-right:0px">กลุ่มเกษตรกร :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 150px">
                <select id="farmerGroupId"  name="farmerGroupId" style="width:150px;">
		           <%if(farmerGroupList !=null && farmerGroupList.size()>0){
		             for(int c=0; c<farmerGroupList.size(); c++){
		               FarmerGroup farmGrp = (FarmerGroup)farmerGroupList.get(c);
		               %>
		               <option value="<%= farmGrp.getFarmerGroupId()%>"><%= farmGrp.getFarmerGroupName()%></option>
		        <%}} %></select>
              </li>
              </span>
            </ul>
          </div>
          
          <div class="line-input-keyin" id="line5">
            <ul>
              <li class="topic" style="width: 150px;padding-right:0px">ภูมิภาค :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 180px">
                <select id="regionNo"  name="regionNo" style="width:150px;">
		           <%if(regionList !=null && regionList.size()>0){
		             for(int i=0; i<regionList.size(); i++){
		               Region reg = (Region)regionList.get(i);
		               %>
		               <option value="<%= reg.getRegionNo()%>"><%= reg.getRegionName()%></option>
		        <%}} %></select>
              </li>
              <li class="topic" style="width: 80px;padding-right:0px">ชนิดพืช :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 150px">
                <select id="breedType"  name="breedType" style="width:150px;" onChange="getBreedGroupInfo('breedType','breedGroup');">
		           <%if(breedTypeList !=null && breedTypeList.size()>0){
		             for(int d=0; d<breedTypeList.size(); d++){
		               BreedType breedType = (BreedType)breedTypeList.get(d);
		               %>
		               <option value="<%=breedType.getBreedTypeId()%>"><%=breedType.getBreedTypeName()%></option>
		        <%}} %></select>
              </li>
              <li class="topic" style="width: 100px;padding-right:0px">ประเภท :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 150px">
                <select id="breedGroup"  name="breedGroup" style="width:150px;">
		           <%if(breedGroupList !=null && breedGroupList.size()>0){
		             for(int e=0; e<breedGroupList.size(); e++){
		               BreedGroup breedGrp = (BreedGroup)breedGroupList.get(e);
		               %>
		               <option value="<%= breedGrp.getBreedGroupId()%>"><%= breedGrp.getBreedGroupName()%></option>
		        <%}} %></select>
              </li>
            </ul>
          </div>
          
          <div class="line-input-keyin" id="line5-1">
          	<ul>
	          	
	            <li class="topic" style="width: 150px;padding-right:0px">ประเภทข้าว :</li>
          		<li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              	<li class="boxinput" style="width: 180px">
                	<input type="radio" name="humidType" value="0" checked="checked" /> ข้าวสดก่อนลดความชื้น<br>
                </li>
                <li class="boxinput" style="width: 180px">
  					<input type="radio" name="humidType" value="1" /> ข้าวหลังลดความชื้น<br>
              	</li>  
            </ul>
          </div>
          
          <div class="line-input-keyin" id="line6">
            <ul>
              <li class="topic" style="width: 150px;padding-right:0px">ปลูกเพื่อ :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 180px">
                <input type="checkbox" id="consume" name='objective' value="1" /><label for="consume"> บริโภค</label>
                <input type="checkbox" id="seed" name='objective' value="2" /><label for="seed"> ทำเมล็ดพันธุ์</label>
              </li>
              <li class="topic" style="width: 80px;padding-right:0px">การผลิตแบบ :</li>
              <li class="topic" style="width: 1px;padding-right:5px"><font color="red"></font></li>
              <li class="boxinput" style="width: 230px">
                <input type="checkbox" id="genOrganic" name='qualification' value="1" /><label for="genOrganic"> อินทรีย์แท้</label>
                <input type="checkbox" id="organic" name='qualification' value="2" /><label for="organic"> อินทรีย์</label>
                <input type="checkbox" id="general" name='qualification' value="4" /><label for="general"> ไม่อินทรีย์</label>
              </li>
            </ul>
          </div>

          <table align="center">
            <tr><td>&nbsp;</td></tr>
            <tr><td><input type="button" class="btn-report" onclick="print();" /></td></tr>
          </table>

        </div>
      </div>
    </div>
    <!--footer -->
    <%@include file="/footer.jsp"%>
  </div>
  </dcs:validateForm>
</body>
</html>