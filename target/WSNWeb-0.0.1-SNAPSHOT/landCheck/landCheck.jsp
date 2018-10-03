<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="com.wsnweb.util.Utility"%>
<%@page import="com.wsnweb.form.LandCheckForm"%>
<%@page import="com.wsndata.data.Coordinates"%>
<%@page import="com.wsndata.data.Plant"%>
<%@page import="com.wsndata.data.BreedGroup"%>
<%@page import="com.wsndata.data.ManureType"%>
<%@page import="com.wsndata.data.PlantMethod"%>
<%@page import="com.wsndata.data.CheckPeriod"%>
<%@page import="com.wsndata.data.CheckingDisease"%>
<%@page import="com.wsndata.data.CheckingDiseaseChild"%>
<%@page import="com.wsndata.data.MixedBreedType"%>
<%@page import="com.wsndata.data.MixedBreedTypeChild"%>
<%@page import="com.wsndata.data.LandType"%>
<%@page import="com.wsndata.data.EliminateMixedBreed"%>
<%@page import="com.wsndata.data.LandCheck"%>
<%@page import="com.wsndata.data.LandCheckPrepareSoil"%>
<%@page import="com.wsndata.data.LandCheckPlant"%>
<%@page import="com.wsndata.data.LandCheckManure"%>
<%@page import="com.wsndata.data.LandCheckMixed"%>
<%@page import="com.wsndata.data.LandCheckDisease"%>
<%@page import="com.wsndata.data.LandCheckHarvest"%>

<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="GENERATOR" content="Rational Application Developer" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1" />
<meta name="viewport" content="width=1280,user-scalable=yes" />

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
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/landcheck.js"></script>

<title><%=Utility.get("WEB_TITLE")%></title>

<style type="text/css">
.line-input-keyin { font-size:20px; }
.ui-front { z-index: 98 !important; }
.ui-front table { padding-bottom: 15px; }
ul#tab_container { padding-top: 15px; }
#i_containTab { width: auto !important; }
.link { color: #DDDDFF; cursor: pointer; display: block; }
.link:hover { color: #FFFFFF; text-decoration: none; }
.btn-plus { color: #336699 !important; background: url('images/btn-plus.png') left no-repeat; padding-left: 20px; }
.landCheckImages { width: 780px; overflow-x: auto; overflow-y: hidden; white-space: nowrap; }
.showImg { display: inline-block; width: auto; padding-left: 5px; padding-right: 5px; }
.lc-img { height: 140px; }
.lc-manage { opacity: 0; position: relative; bottom: 32px; padding: 2px 2px; }
.lc-manage:hover { opacity: 1; background-color: rgba(0, 0, 0, 0.6); }
.readonly { border: solid 1px #666666 !important; background-color: #F0F0F0; }
.popup-detail ul { padding-bottom:0; }
select { width: 129px; }
fieldset select { width: 329px; }
td input, fieldset input, #CoordinatesPopup input { width: 97% !important; }
td select, fieldset select, #checkPeriod { width: 100% !important; }
#Coordinates input { width: auto !important; }
.time select { width: 55px !important; }
.date input { width: 100px !important; }
.area input { width: 30px !important; }
.check input { width: 20px !important; }
</style>
<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	String[] CONF_HARVESTERS = Utility.get("HARVESTERS").split(",");
	String[] CONF_CLEANING = Utility.get("CLEANING").split(",");
	String[] CONF_PACKAGING = Utility.get("PACKAGING").split(",");
	String[] CONF_MOVING = Utility.get("MOVING").split(",");
	
	
	
	LandCheckForm landCheck = (LandCheckForm) request.getAttribute("LandCheckForm");
	List landCheckList = (ArrayList) session.getAttribute("landCheckList");
	List coordinatesList = (ArrayList) session.getAttribute("coordinatesList");
	List plantYearList = (ArrayList) session.getAttribute("landCheckYearList");
	List breedGroupList = (ArrayList) session.getAttribute("allBreedGroupList");
	List checkPeriodList = (ArrayList) session.getAttribute("checkPeriodList");
	List checkingDiseaseList = (ArrayList) session.getAttribute("checkingDiseaseList");
	List manureTypeList = (ArrayList) session.getAttribute("manureTypeList");
	List plantMethodList = (ArrayList) session.getAttribute("plantMethodList");
	List landTypeList = (ArrayList) session.getAttribute("landTypeList");
	List eliminateMixedBreedList = (ArrayList) session.getAttribute("eliminateMixedBreedList");
	List mixedBreedTypeAll = (ArrayList)session.getAttribute("mixedBreedTypeList");
	List mixedBreedTypeChildAll = (ArrayList)session.getAttribute("mixedBreedTypeChildList");
	List checkingDiseaseAll = (ArrayList)session.getAttribute("checkingDiseaseList");
	List checkingDiseaseChildAll = (ArrayList)session.getAttribute("checkingDiseaseChildList");
	String[] waterSource = Utility.get("WATER_SOURCE").split(",");
	String[] prepareSource = Utility.get("PREPARE_AREA").split(",");
	String[] layerTypeSource = Utility.get("LAYER_TYPE").split(",");
	String[] drainageSource = Utility.get("DRAINAGE").split(",");
	String[] harvestQualitySource = Utility.get("HARVEST_QUALITY").split(",");
	String[] farmStatusSource = Utility.get("FARM_STATUS").split(",");
	String[] harvestersSource = Utility.get("HARVESTERS").split(",");
	String[] cleaningSource = Utility.get("CLEANING").split(",");
	String[] packagingSource = Utility.get("PACKAGING").split(",");
	String[] movingSource = Utility.get("MOVING").split(",");
	String msg = "",cmd = "", idCard="", remark = "", checkBy = "", imageData = "", 
	docNo = "", checkTime = "", checkHour = "", checkMinute = "", result = "";
	long landCheckId=0, plantYear=0,  docRai=0, docNgan=0, docWah=0, typeId=0, checkPeriod=0, plantNo=0, breedTypeId=0, refPlantId=0;
	double target=0;
	long plantId = 0;
	LandCheck landCheckObj = (LandCheck)session.getAttribute("landCheckData");
	List landCheckPrepareSoilList = landCheckObj==null?null:landCheckObj.getLandCheckPrepareSoil();
	List landCheckPlantList = landCheckObj==null?null:landCheckObj.getLandCheckPlant();
	List landCheckManureList = landCheckObj==null?null:landCheckObj.getLandCheckManure();
	List landCheckMixedList = landCheckObj==null?null:landCheckObj.getLandCheckMixed();
	List landCheckDiseaseList = landCheckObj==null?null:landCheckObj.getLandCheckDisease();
	List landCheckHarvestList = landCheckObj==null?null:landCheckObj.getLandCheckHarvest();
	if(landCheck!=null){
		
	
		if(landCheck.getMsg() !=null && !"".equals(landCheck.getMsg())){
			msg = landCheck.getMsg();
		}
		cmd = landCheck.getCmd();
		landCheckId = landCheck.getLandCheckId();
		plantYear = landCheck.getPlantYear();
		plantNo = landCheck.getPlantNo();
		idCard = landCheck.getIdCard();
		typeId = landCheck.getTypeId();
		docNo = landCheck.getDocNo();
		docRai = landCheck.getDocRai();
		docNgan = landCheck.getDocNgan();
		docWah = landCheck.getDocWah();
		checkPeriod = landCheck.getCheckPeriod();
		checkTime = landCheck.getCheckTime();
		checkHour = landCheck.getCheckHour();
		checkMinute = landCheck.getCheckMinute();
		checkBy = landCheck.getCheckBy();
		remark = landCheck.getRemark();
		result = landCheck.getResult();
		imageData = landCheck.getImageData();
		breedTypeId = landCheck.getBreedTypeId();
		refPlantId = landCheck.getRefPlantId();
	}
%>
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
      	     	//$('input[type=text],input[type=checkbox],select')[nextIndex].css('border-color', 'black');	
      		}else{
       	   	    $('input[type=text],input[type=checkbox],select')[nextIndex-1].blur();// input:text
     	 	}
    	}
    	});
  });
  
	var myWindow;
	var imageCnt;
	var imageData = new Array();
	var docType;
	var docNo;
	var docRai;
	var docNgan;
	var docWah;
	var chkPeriod;

    function loadData(){	
		document.landCheckForm.cmd.value = "<%= cmd %>";
		
		if(<%=imageData%> !=null && <%=imageData%> != ""){
			imageData = <%=imageData%>;
			if(0 < imageData.length){
				generateTableImage();
			}
		}
        
    <% if(!"".equals(cmd)){ %>
    	<% if(landCheckId>0) { %>
    		document.landCheckForm.landCheckId.value = "<%= landCheckId %>";
            document.landCheckForm.idCard.value = "<%=idCard%>";
    	<% } %>
    	<% if(plantYear>0) { %>
            document.landCheckForm.plantYear.value = "<%=plantYear%>";
    	<% } if(plantNo>0) { %>
    		var plantNum = <%=plantNo%>;
            document.landCheckForm.season.value = "<%=plantNo%>";
            var season = document.getElementsByName("season");
			if(typeof season !== "undefined" && season.length>0){
				for (var a = 0; a < season.length; a++) {
					if (season[a].value == plantNum) {
						season[a].checked = true;
						break;
					}
				}
			}
            
        <% } if(docRai>0 || docNgan>0 || docWah>0) { %>
            document.landCheckForm.docRai.value = "<%=docRai%>";
            document.landCheckForm.docNgan.value = "<%=docNgan%>";
            document.landCheckForm.docWah.value = "<%=docWah%>";
        <% } else { %>
            document.landCheckForm.docRai.value = "";
            document.landCheckForm.docNgan.value = "";
            document.landCheckForm.docWah.value = "";
        <% } %>
        document.landCheckForm.checkHour.value = "<%=checkHour%>";
        document.landCheckForm.checkMinute.value = "<%=checkMinute%>";
        document.landCheckForm.checkPeriod.value = "<%=checkPeriod%>";
            var getResult = '<%=result%>';
            var resultt = document.getElementsByName("result");
			if(typeof resultt !== "undefined" && resultt.length>0){
				for (var b = 0; b < resultt.length; b++) {
					if (resultt[b].value == getResult) {
						resultt[b].checked = true;
						break;
					}
				}
			}
        document.landCheckForm.result.value = "<%=result%>";
        getLandRight('plantYear', 'season', 'idCard', 'docId');
        document.landCheckForm.docId.value = "<%=docNo%>|<%=typeId%>|<%=docRai%>|<%=docNgan%>|<%=docWah%>|<%=breedTypeId%>|<%=refPlantId%>";
        getLandRightDetail('docId');
        <% 
        if("Edit".equals(cmd)){ %>
            document.landCheckForm.plantYear.disabled = true;
            document.landCheckForm.docId.disabled = true;
            document.landCheckForm.checkPeriod.disabled = true;
            document.landCheckForm.season1.disabled = true;
            document.landCheckForm.season2.disabled = true;
            document.landCheckForm.idCard.readOnly = true;
            //document.landCheckForm.checkHour.value = "<%=checkTime.substring(0,2)%>";
            //document.landCheckForm.checkMinute.value = "<%=checkTime.substring(3)%>";
            document.landCheckForm.remark.value = "<%=remark%>";
            document.landCheckForm.checkBy.value = "<%=checkBy%>";
            
            document.landCheckForm.typeId.value = "<%=typeId%>";
            document.landCheckForm.breedTypeId.value = "<%=breedTypeId%>";
            document.landCheckForm.refPlantId.value = "<%=refPlantId%>";
			var docIdDD = document.landCheckForm.docId;
			docType = docIdDD.options[docIdDD.selectedIndex].text;
			docNo = document.landCheckForm.docNo.value;
			docRai = <%=docRai%>;
			docNgan = <%=docNgan%>;
			docWah = <%=docWah%>;
			chkPeriod = "<%=checkPeriod%>";
			getMixedBreedInfo('checkPeriod', 'inMixedBreedTypeId', 'inMixedChildId');
        <%}%>
    <% } %>
		setDoc();
		setTab('checkPeriod');
        showErrorMessage(); 
        loadMenu("M24");
        
        var inputId = document.getElementById("idCard");
        var elemLen = inputId.value.length;
        inputId.selectionStart = elemLen;
        inputId.selectionEnd = elemLen;
        inputId.focus();

		var checkPeriodVal = document.landCheckForm.checkPeriod.value;
		if(checkPeriodVal == '7'){
			document.getElementById("selling").style.visibility = "visible";
		}else{
			document.getElementById("selling").style.visibility = "hidden";
		}

    }
    
	function setDoc()
	{
		var txtDocType = document.getElementsByName("txtDocType");
		var txtDocNo = document.getElementsByName("txtDocNo");
		var txtDocSize = document.getElementsByName("txtDocSize");
		var txtCheckPeriod = document.getElementsByName("txtCheckPeriod");
		//docRai = docRai + Math.floor((docNgan + Math.floor(docWah / 100)) / 4);
		//docNgan = (docNgan + Math.floor(docWah / 100)) % 4;
		//docWah = docWah % 100
		for (var i = 0; i < txtDocType.length; i++) {
			txtDocType.item(i).innerHTML = docType;
			txtDocNo.item(i).innerHTML = docNo;
			txtDocSize.item(i).innerHTML = docRai + " ไร่ " + docNgan + " งาน " + docWah + " วา";
		}
		for (var i = 0; i < txtCheckPeriod.length; i++) {
			txtCheckPeriod.item(i).innerHTML = chkPeriod;
		}
	}
    
	var imgSeq = 0;
    
	function generateTableImage()
	{
		imageCnt = imageData.length;
		
		var	table = "";	
		table = "<div class=\"landCheckImages\">";		
		for(var i=0;i<imageData.length;i++){
			if(imageData[i]!=null){
				table+= generateRowImage(imageData[i],i);
			}
		}
		table += "</div>";
		document.getElementById('myImage').innerHTML = table;	
	}

	function generateRowImage(detail,number)
	{	
		imgSeq++;
		var table= "";
		
		table += "<span id=\"showImg"+imgSeq+"\" class=\"showImg\">";
		table += "<div id=\"lc-img"+imgSeq+"\" class=\"lc-img\">";
		if(detail.imageId>0){
			table += "<img src='<%=request.getContextPath()%>/LandCheck.do?cmd=image&imageId="+detail.imageId+"&t="+detail.seqIndex+"' style='cursor:pointer' height='130px' onclick='openImage("+detail.imageId+","+detail.imageId +")' >";
		}else{
			table += "<img src='<%=request.getContextPath()%>/LandCheck.do?cmd=imageObject&imageId="+detail.seq+"&t="+detail.seqIndex+"' style='cursor:pointer' height='130px' onclick='openImage("+detail.imageId+","+detail.seq +")' >";
		}
		if(imageCnt > 0){
			table += "<div id=\"lc-manage"+imgSeq+"\" class=\"lc-manage\">";
			if(detail.imageId>0){
				table += "<a class='link' onclick='deleteImage(" + detail.imageId + "," + number + ");return false;'>";
			}else{
				table += "<a class='link' onclick='deleteImageObject(" +(number+1)+ "," + number + ");return false;'>";
			}
			table += "<img src=\"<%=request.getContextPath()%>/images/ico-trash.png\"> ลบรูป</a></div>";
		}
		table += "</div></span>";
		//table += "<tr><td style='color:red' align='center'> " + detail.msg + " </td></tr>";
		return table;
	}
	
	function openImage(type,imageId){
		var w = 960;
		var h = 540;
		var left = (screen.width/2)-(w/2);
		var top = (screen.height/2)-(h/2);
		myWindow = "";
		if(type>0){
			myWindow = window.open("<%=request.getContextPath()%>/LandCheck.do?cmd=image&imageId="+imageId, myWindow, "scrollbars=yes,location=0,width="+w+",height="+h+",left="+left+",top="+top);
		}else{
			myWindow = window.open("<%=request.getContextPath()%>/LandCheck.do?cmd=imageObject&imageId="+imageId, myWindow, "scrollbars=yes,location=0,width="+w+",height="+h+",left="+left+",top="+top);
		}	 
	}
   
    function getCoordinateData(){
    	var coordinateDetail = "";
    	var latitudeVal = document.getElementsByName('latitude');
    	var longitudeVal = document.getElementsByName('longitude');
    	var seq = 0;

		for(var k = 0 ; k < latitudeVal.length ; k++){
			seq = seq +1;
			if(k==0){
				coordinateDetail += seq + ":" + latitudeVal[k].value + ":" + longitudeVal[k].value;		
			}else{
				coordinateDetail += "|" + seq + ":" + latitudeVal[k].value + ":" + longitudeVal[k].value;		
			}
		}
		document.landCheckForm.coordinatesData.value= coordinateDetail;
    }

    function saveData(){
	 	getCoordinateData();
        document.landCheckForm.plantYear.disabled = false;
        document.landCheckForm.docId.disabled = false;
        document.landCheckForm.checkPeriod.disabled = false;
        document.landCheckForm.season1.disabled = false;
        document.landCheckForm.season2.disabled = false;
	
		if(imageData !=null && imageData.length >0){
			for(var i=0;i<imageData.length;i++){	
				var result = JSON.stringify(imageData[i]);	
				var jsonResult = eval('('+result+')');	
				imageData[i] = jsonResult;
			}
		}
		
		var season = document.getElementsByName("season");
		var check = false;
		if(typeof season !== "undefined" && season.length>0){
			for (var a = 0; a < season.length; a++) {
				if (season[a].checked) {
					season = season[a];
					check = true;
					break;
				}
			}
		}
		var result = document.getElementsByName("result");
		var check2 = false;
		if(typeof result !== "undefined" && result.length>0){
			for (var a = 0; a < result.length; a++) {
				if (result[a].checked) {
					check2 = true;
					break;
				}
			}
		}
		
		var plantYear = document.getElementById("plantYear");
		var docId = document.getElementById("docId");
		var checkPeriod = document.getElementById("checkPeriod");
		var checkHour = document.getElementById("checkHour");
		var checkMinute = document.getElementById("checkMinute");
		
		if(plantYear.options[plantYear.selectedIndex].value==0) {
			alert("กรุณาใส่ปีการผลิต");
			return false;
		}
		if(!check) {
			alert("กรุณาใส่ฤดูกาลปลูก");
			return false;
		}
		if(document.landCheckForm.idCard.value == "") {
			alert("กรุณาใส่เลขบัตรประจำตัวประชาชน");
			return false;
		}
		if(docId.options[docId.selectedIndex].value==0) {
			alert("กรุณาใส่เอกสารสิทธิ์");
			return false;
		}
		if(checkPeriod.options[checkPeriod.selectedIndex].value==0) {
			alert("กรุณาใส่ระยะการตรวจ");
			return false;
		}
		if(document.landCheckForm.checkDate.value == "") {
			alert("กรุณาใส่วันที่ตรวจ");
			return false;
		}
		if(checkHour.options[checkHour.selectedIndex].value==0 && checkMinute.options[checkMinute.selectedIndex].value==0) {
			alert("กรุณาใส่เวลาการตรวจ");
			return false;
		}
		if(document.getElementsByName("latitude").length == 0) {
			alert("กรุณาใส่พิกัดแปลงนา");
			return false;
		}
		if(!check2) {
			alert("กรุณาใส่ผลการตรวจ");
			return false;
		}
		if(document.landCheckForm.checkBy.value == "") {
			alert("กรุณาใส่เจ้าหน้าที่ตรวจ");
			return false;
		}
		
		document.landCheckForm.plantNo.value= season.value; //plantNo = season
		document.landCheckForm.imageData.value= imageData.toJSON();
        document.landCheckForm.cmd.value="Save";
        document.landCheckForm.action= "<%=request.getContextPath()%>/LandCheck.do";
        document.forms["landCheckForm"].submit();
    }

    function checkIdCard(objIdCard)    {
		var idCard = document.getElementById(objIdCard);	
        var RE = /^\d{13,13}$/;
        if(RE.test(idCard.value)){
        	// if(validateIdCard(idCard)) {
        		getLandRight('plantYear', 'season', 'idCard', 'docId');
        	//} else {
         	//		document.getElementById("firstName").value = "";
        	//		document.getElementById("lastName").value = "";
        	//	}
        }else{
            alert("กรุณาใส่ค่าที่เป็นตัวเลข");
            document.getElementById(objIdCard).value="";
            window.setTimeout(function () {
            document.getElementById(objIdCard).focus();
            }, 0);
        }
    }
    
	function deleteImage(imageId, number)
	{
		for(var i=0;i<imageData.length;i++){	
			var result = JSON.stringify(imageData[i]);		
			var jsonResult = eval('('+result+')');	
			imageData[i] = jsonResult;
		}
		var chkImageFile=0;
		var radios = document.getElementsByName("imageFile");	
		var check=false;
		if(typeof radios !== "undefined" && radios.length>0){
			for (i = 0; i < radios.length; i++) {
				if ( radios[i].checked) {
					chkImageFile=i;   
				}
			}		
		}
		if(imageCnt > 0){
			var result = confirm("ยืนยันการลบ");
			if (result){
				var reqParameters = new Object(); 	
				reqParameters.cmd = "deleteImage";
				reqParameters.imageId=imageId;
				reqParameters.mainImage=chkImageFile;
				reqParameters.landCheckId=document.landCheckForm.landCheckId.value;
				reqParameters.imageData=imageData.toJSON();
				new Ajax.Request('<%=request.getContextPath()%>/LandCheck.do',
				{
					method: 'post',
					asynchronous: false,
					parameters: reqParameters,
					encoding:'UTF-8',				
					onSuccess: function(transport) {	
						if (""!=transport.responseText) {	
							alert("ลบรูปภาพเรียบร้อยแล้ว !!!");	
							var data =transport.responseText;
							imageData= new Array();
							imageData = eval('('+data+')');								
							generateTableImage();								
						} 
					},
					onFailure: function() {
						alert("Fail !!  ");
					}
				});	
			}else{
				return false;
			}

		}else{
			alert("ไม่สามารถลบได้!");
			return false;
		}
	}
	
	function deleteImageObject(imageId, number)
	{
		for(var i=0;i<imageData.length;i++){	
			var result = JSON.stringify(imageData[i]);		
			var jsonResult = eval('('+result+')');	
			imageData[i] = jsonResult;
		}
		var chkImageFile=0;
		var radios = document.getElementsByName("imageFile");	
		var check=false;
		if(typeof radios !== "undefined" && radios.length>0){
			for (i = 0; i < radios.length; i++) {
				if ( radios[i].checked) {
					chkImageFile=i;   
				}
			}		
		}
		if(imageCnt > 0){
			var result = confirm("ยืนยันการลบ");
			if (result){
				var reqParameters = new Object(); 	
				reqParameters.cmd = "deleteImageObject";
				reqParameters.imageId=imageId;
				reqParameters.mainImage=chkImageFile;
				reqParameters.landCheckId=document.landCheckForm.landCheckId.value;
				reqParameters.imageData=imageData.toJSON();
				new Ajax.Request('<%=request.getContextPath()%>/LandCheck.do',
				{
					method: 'post',
					asynchronous: false,
					parameters: reqParameters,
					encoding:'UTF-8',				
					onSuccess: function(transport) {	
						if (""!=transport.responseText) {		
							alert("ลบรูปภาพเรียบร้อยแล้ว !!!");	
							var data =transport.responseText;
							imageData= new Array();
							imageData = eval('('+data+')');								
							generateTableImage();
						} 
					},
					onFailure: function() {
						alert("Fail !!  ");
					}
				});	
			}else{
				return false;
			}
		}else{
			alert("ไม่สามารถลบได้!");
			return false;
		}
	}
    
    function addImage(){
		var number=-100;
		for(var i=0;i<imageData.length;i++){	
			var result = JSON.stringify(imageData[i]);		
			var jsonResult = eval('('+result+')');	
			imageData[i] = jsonResult;
		}
		var chkImageFile=0;
		var radios = document.getElementsByName("imageFile");	
		var check=false;
		if(typeof radios !== "undefined" && radios.length>0){
			for (i = 0; i < radios.length; i++) {
				if ( radios[i].checked) {
					chkImageFile=i;   
				}
			}		
		}
		document.landCheckForm.imageData.value= imageData.toJSON();
		var reqParameters = new Object(); 	
		reqParameters.cmd = "setValueSession";
		reqParameters.imageData=document.landCheckForm.imageData.value;
		new Ajax.Request('<%=request.getContextPath()%>/LandCheck.do',
		{
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding:'UTF-8',				
			onSuccess: function(transport) {	
				if (""!=transport.responseText) {		
					myWindow = "";
					var w = 800;
					var h = 500;
					var left = (screen.width/2)-(w/2);
					var top = (screen.height/2)-(h/2);
					myWindow = window.open("<%=request.getContextPath()%>/LandCheck.do?cmd=detailImage&w=-1&h=-1&index="+number+"&mainImage="+chkImageFile, "myWindow","toolbar=0,resizable=1,menubar=0,status=0,scrollbars=1,width="+w+",height="+h+",left="+left+",top="+top);	
					//myWindow.moveTo(400,150);		
				}
			},
			onFailure: function() {
				alert("Fail !!  ");
			}
		});
	}
	
	function getLandRight(objPYear, objSeason, objIdCard, objDocId)
	{
		var plantYear = document.getElementById(objPYear);

		var season = document.getElementsByName(objSeason);
		for (var i = 0; i < season.length; i++) {
			if (season[i].checked) {
				season = season[i];
				break;
			}
		}
		var idCard = document.getElementById(objIdCard);
		var docIdDD = document.getElementById(objDocId);
		
		if(plantYear.options[plantYear.selectedIndex].value>0 && season.value>0 && idCard.value!="") {
			var reqParameters = new Object();
			reqParameters.cmd = "getLandRightInfo";
			reqParameters.plantYear = plantYear.options[plantYear.selectedIndex].value;
			reqParameters.plantNo = season.value;
			reqParameters.idCard = idCard.value;
	
			new Ajax.Request("<%=request.getContextPath()%>/LandCheck.do",
			{   
				method: 'post',
				asynchronous: false,
				parameters: reqParameters,
				encoding: 'UTF-8',
				onSuccess: function(transport)
				{
					var returnValue = new Array();
	                var json = transport.responseText.evalJSON();
	                if(json !=null && json != ""){
	                    docIdDD.options.length = json.length+1;
	                    docIdDD.options[0].value="0";
	                    docIdDD.options[0].text="กรุณาเลือก";
	                    docIdDD.options[0].selected = true;
	
	                    var r = 0;
	                    for(var i = 0; i < json.length; i++){
	                        r=r+1;
	                        returnValue = "";
							returnValue = json[i].toString().split(",");
							var value4 = returnValue[4].replace("&", ",");
	                        docIdDD.options[r].value=returnValue[4]+"|"+returnValue[5]+"|"+returnValue[7]+"|"+returnValue[8]+"|"+returnValue[9]+"|"+returnValue[3]+"|"+returnValue[14];
	                        docIdDD.options[r].text=returnValue[11] + "   [" + value4 + "]";
	                        
	                        docIdDD.options[r].title = "เลขที่ "+value4;                 
	                    }
	                   document.getElementById("firstName").value = returnValue[12];
	                   document.getElementById("lastName").value = returnValue[13];
	                } else {
	                   alert("ไม่พบข้อมูลการเพาะปลูก");
	                   document.getElementById("firstName").value = "";
	                   document.getElementById("lastName").value = "";
	                   document.getElementById("docNo").value = "";
	                   document.getElementById("docRai").value = "";
	                   document.getElementById("docNgan").value = "";
	                   document.getElementById("docWah").value = "";
	                   document.getElementById("refPlantId").value = "";
	                   docIdDD.options.length = 1;
	                   docIdDD.options[0].value="0";
	                   docIdDD.options[0].text="กรุณาเลือก";
	                   docIdDD.options[0].selected = true;
	                }
				},
				onFailure: function() { 
					alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
				}
			});
		}
	}
	
	function getLandRightDetail(objDocId)
	{
		var docIdDD = document.getElementById(objDocId);
		var param = docIdDD.options[docIdDD.selectedIndex].value.split("|");
		
		if(docIdDD.options[docIdDD.selectedIndex].value != "0") {
			param[0] = param[0].replace("&", ",");
			document.landCheckForm.docNo.value = param[0];
			document.landCheckForm.typeId.value = param[1];
			document.landCheckForm.docRai.value = param[2];
			document.landCheckForm.docNgan.value = param[3];
			document.landCheckForm.docWah.value = param[4];
			document.landCheckForm.breedTypeId.value = param[5];
			document.landCheckForm.refPlantId.value = param[6];
			docType = docIdDD.options[docIdDD.selectedIndex].text;
			docNo = param[0];
			docRai = parseInt(param[2]);
			docNgan = parseInt(param[3]);
			docWah = parseInt(param[4]);
		}else{
			document.landCheckForm.docNo.value = "";
			document.landCheckForm.typeId.value = "";
			document.landCheckForm.docRai.value = "";
			document.landCheckForm.docNgan.value = "";
			document.landCheckForm.docWah.value = "";
			document.landCheckForm.breedTypeId.value = "";
			document.landCheckForm.refPlantId.value = "";
		}
		setDoc();
		getCoordinateInfo('plantYear',  'season', 'idCard', 'docId');
	}
	
	function getCoordinateInfo(objPYear, objSeason, objIdCard, objDocId)
	{
		var plantYearDD = document.getElementById(objPYear);
		var idCard = document.getElementById(objIdCard);
		var docIdDD = document.getElementById(objDocId);
		var param = docIdDD.options[docIdDD.selectedIndex].value.split("|");

		var season = document.getElementsByName(objSeason);
		for (var i = 0; i < season.length; i++) {
			if (season[i].checked) {
				season = season[i];
				break;
			}
		}
		
		if(plantYearDD.options[plantYearDD.selectedIndex].value>0 && season.value>0 && idCard.value!="") {
			var reqParameters = new Object();
			reqParameters.cmd = "getCoordinateInfo";
			reqParameters.plantYear = plantYearDD.options[plantYearDD.selectedIndex].value;
			reqParameters.plantNo = season.value;
			reqParameters.idCard = idCard.value;
			reqParameters.docNo = param[0];
			reqParameters.typeId = param[1];
			reqParameters.breedTypeId = param[5];
	
			new Ajax.Request("<%=request.getContextPath()%>/LandCheck.do",
			{   
				method: 'post',
				asynchronous: false,
				parameters: reqParameters,
				encoding: 'UTF-8',
				onSuccess: function(transport)
				{
					var element = document.getElementsByName("cooDel");
					for(var i=0; element[i]; ++i){
						element[i].checked = true;
					}
					delCoordinates2();
					
	                var returnValue = new Array();
	                var json = transport.responseText.evalJSON();
	                if(json !=null && json != ""){
	                    var data = [];
	                    var hData = [];
	                    for(var i = 0; i < json.length; i++){
	                        var countRow = element.length+1;
	                        data["Id"] = json[i].coordinatesId;
	                        data["Latitude"] = json[i].latitude+"|left";
	                        data["Longitude"] = json[i].longitude+"|left";
	                        var addTable = generateRow(countRow, "Coordinates", data, hData);
	                        $("#Coordinates tbody:first").append(addTable);
	                        if(countRow==1) { $("#noData").remove(); }
	                    }
	                }
				},
				onFailure: function() { 
					alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
				}
			});
		}
	}
	
	function getMixedBreedInfo(objPeriod, objType, objChild)
	{
		var checkPeriod = document.getElementById(objPeriod);
		var typeDD = document.getElementById(objType);
		var typeChildDD = document.getElementById(objChild);
		
		typeDD.options.length = 1;
		typeDD.options[0].value="0";
		typeDD.options[0].text="กรุณาเลือก";
		typeDD.options[0].selected = true;
		typeChildDD.options.length = 1;
		typeChildDD.options[0].value="0";
		typeChildDD.options[0].text="กรุณาเลือก";
		typeChildDD.options[0].selected = true;
		if(checkPeriod.selectedIndex>0) {
			var reqParameters = new Object();
			reqParameters.cmd = "getMixedBreedInfo";
			reqParameters.checkPeriod = checkPeriod.selectedIndex;
			
			new Ajax.Request("<%=request.getContextPath()%>/LandCheck.do",
			{   
				method: 'post',
				asynchronous: false,
				parameters: reqParameters,
				encoding: 'UTF-8',
				onSuccess: function(transport)
				{
					var returnValue = new Array();
					var json = transport.responseText.evalJSON();
	                if(json !=null && json != ""){
	                	typeDD.options.length = json.length+1;
	
	                    var r = 0;
	                    for(var i = 0; i < json.length; i++){
	                        r=r+1;
	                        typeDD.options[r].value=json[i].mixedBreedTypeId;
	                        typeDD.options[r].text=json[i].description;
	                    }
	                }
				},
				onFailure: function() { 
					//alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
				}
			});
		}
	}
	
	function getMixedBreedTypeInfo(objType, objChild)
	{
		var mixedBreedTypeDD = document.getElementById(objType);
		var mixedBreedTypeChildDD = document.getElementById(objChild);
		
		mixedBreedTypeChildDD.options.length = 1;
		mixedBreedTypeChildDD.options[0].value="0";
		mixedBreedTypeChildDD.options[0].text="กรุณาเลือก";
		mixedBreedTypeChildDD.options[0].selected = true;
		if(mixedBreedTypeDD.selectedIndex>0) {
			var reqParameters = new Object();
			reqParameters.cmd = "getMixedBreedTypeInfo";
			reqParameters.mixedBreedType = mixedBreedTypeDD.options[mixedBreedTypeDD.selectedIndex].value;
			
			new Ajax.Request("<%=request.getContextPath()%>/LandCheck.do",
			{   
				method: 'post',
				asynchronous: false,
				parameters: reqParameters,
				encoding: 'UTF-8',
				onSuccess: function(transport)
				{
					var returnValue = new Array();
					var json = transport.responseText.evalJSON();
	                if(json !=null && json != ""){
	                	mixedBreedTypeChildDD.options.length = json.length+1;
	
	                    var r = 0;
	                    for(var i = 0; i < json.length; i++){
	                        r=r+1;
	                        mixedBreedTypeChildDD.options[r].value=json[i].childId;
	                        mixedBreedTypeChildDD.options[r].text=json[i].description;
	                    }
	                }
				},
				onFailure: function() { 
					//alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
				}
			});
		}
	}
	
	function getDiseaseChildInfo(objChecking, objChild, objType)
	{
		var checkingDiseaseDD = document.getElementById(objChecking);
		var diseaseChildDD = document.getElementById(objChild);
		var diseaseTypeDD = document.getElementById(objType);
		
		diseaseChildDD.options.length = 1;
		diseaseChildDD.options[0].value="0";
		diseaseChildDD.options[0].text="กรุณาเลือก";
		diseaseChildDD.options[0].selected = true;
		diseaseTypeDD.options.length = 1;
		diseaseTypeDD.options[0].value="0";
		diseaseTypeDD.options[0].text="กรุณาเลือก";
		diseaseTypeDD.options[0].selected = true;
		if(checkingDiseaseDD.selectedIndex>0) {
			var reqParameters = new Object();
			reqParameters.cmd = "getDiseaseChildInfo";
			reqParameters.diseaseChecking = checkingDiseaseDD.options[checkingDiseaseDD.selectedIndex].value;
			
			new Ajax.Request("<%=request.getContextPath()%>/LandCheck.do",
			{   
				method: 'post',
				asynchronous: false,
				parameters: reqParameters,
				encoding: 'UTF-8',
				onSuccess: function(transport)
				{
					var returnValue = new Array();
					var json = transport.responseText.evalJSON();
	                if(json !=null && json != ""){
	                	diseaseChildDD.options.length = json.length+1;
	
	                    var r = 0;
	                    for(var i = 0; i < json.length; i++){
	                        r=r+1;
	                        diseaseChildDD.options[r].value=json[i].diseaseChildId;
	                        diseaseChildDD.options[r].text=json[i].description;
	                    }
	                }
				},
				onFailure: function() { 
					//alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
				}
			});
		}
	}
	
	function getDiseaseTypeInfo(objChecking, objChild, objType)
	{
		var checkingDiseaseDD = document.getElementById(objChecking);
		var diseaseChildDD = document.getElementById(objChild);
		var diseaseTypeDD = document.getElementById(objType);
		
		diseaseTypeDD.options.length = 1;
		diseaseTypeDD.options[0].value="0";
		diseaseTypeDD.options[0].text="กรุณาเลือก";
		diseaseTypeDD.options[0].selected = true;
		if(diseaseChildDD.selectedIndex>0) {
			var reqParameters = new Object();
			reqParameters.cmd = "getDiseaseTypeInfo";
			reqParameters.diseaseChildId = diseaseChildDD.options[diseaseChildDD.selectedIndex].value;
			
			new Ajax.Request("<%=request.getContextPath()%>/LandCheck.do",
			{   
				method: 'post',
				asynchronous: false,
				parameters: reqParameters,
				encoding: 'UTF-8',
				onSuccess: function(transport)
				{
					var returnValue = new Array();
					var json = transport.responseText.evalJSON();
	                if(json !=null && json != ""){
	                	diseaseTypeDD.options.length = json.length+1;
	
	                    var r = 0;
	                    for(var i = 0; i < json.length; i++){
	                        r=r+1;
	                        diseaseTypeDD.options[r].value=json[i].diseaseTypeId;
	                        diseaseTypeDD.options[r].text=json[i].description;
	                    }
	                }
				},
				onFailure: function() { 
					//alert('เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง');
				}
			});
		}
	}
    
    function closePage(){
		document.landCheckListForm.cmd.value = "Search"; 
        document.landCheckListForm.action="<%=request.getContextPath()%>/LandCheckList.do";
        document.forms["landCheckListForm"].submit();
    }
    
	function reload()
	{
		var reqParameters = new Object(); 	
		reqParameters.cmd = "reload";
		reqParameters.imageId=document.landCheckForm.imageId.value;
		new Ajax.Request('<%=request.getContextPath()%>/LandCheck.do',
		{
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding:'UTF-8',				
			onSuccess: function(transport) {	
				if (""!=transport.responseText) {		
					var data =transport.responseText.split("#");
					if(data!=null && data.length>0){							
						imageData= new Array();
						imageData = eval('('+data[0]+')');						
						generateTableImage();
					}
				} 
			},
			onFailure: function() {
				alert("Fail !!  ");
			}
		});
	}
    
	function reloadClose()
	{				
		var reqParameters = new Object(); 	
		reqParameters.cmd = "reloadClose";
		reqParameters.imageId=document.landCheckForm.imageId.value;
		new Ajax.Request('<%=request.getContextPath()%>/LandCheck.do',
		{
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding:'UTF-8',				
			onSuccess: function(transport) {	
				if (""!=transport.responseText) {		
					var data =transport.responseText.split("#");
					if(data!=null && data.length>1){							
						imageData= new Array();
						imageData = eval('('+data[0]+')');								
						generateTableImage();
						videoData= new Array();
						videoData = eval('('+data[1]+')');
						generateTableVideo();
					}
				} 
			},
			onFailure: function() {
				alert("Fail !!  ");
			}
		});
	}
    
    function showErrorMessage(){
	<% if(msg !=null && !"".equals(msg)){ %>
		alert("<%=msg%>");
   		<% if(Utility.get("SAVE_SUCCESS").equals(msg)){%> 
   			closePage();
   		<% } %>
 	<%}%>
 	}
 	
 	function goSellPage(){
 		var docIdVal = document.landCheckForm.docId.value;
 		var docId = docIdVal.split("|")[0]; 		
 		document.landCheckForm.landCheckDocNo.value = docId;
		document.landCheckForm.cmd.value = "Edit"; 
 		document.landCheckForm.refPlantId.value = "<%=refPlantId%>";
 		document.landCheckForm.landCheckPage.value = "Y";
        document.landCheckForm.action="<%=request.getContextPath()%>/Plant.do";
       	document.forms["landCheckForm"].submit();
    }
</script>
</head>
<body onload="loadData();">
	<dcs:validateForm formName="landCheckForm" formAction="LandCheck.do" formBean="LandCheckForm" alert="true">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="landCheckId"/>
	<input type="hidden" name="typeId"/>
	<input type="hidden" name="imageId"/>
	<input type="hidden" name="imageData"/>
	<input type="hidden" name="plantNo" />
	<input type="hidden" name="coordinatesData" />
	<input type="hidden" name="breedTypeId" />
	<input type="hidden" name="refPlantId" />
	<input type="hidden" name="landCheckPage" />
	<input type="hidden" name="landCheckDocNo" />

	<div class="main-inside">
		<!-- insert header -->
		<%@include file="/header.jsp" %>
		<div class="navigator">
			<div class="inside">
				<ul>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li><a style="color:#333" href='<%=request.getContextPath()%>/LandCheckList.do'>การจัดการตรวจแปลง</a></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
					<li>กรอกข้อมูล</li>
				</ul>
			</div>
		</div>	
		<!-- insert header -->
		<!-- start insert >> content -->

		<div class="content" style="height:auto;">
			<div class="inside">
				<div class="search-header">
					&nbsp;&nbsp;&nbsp;<span style="vertical-align: -7px;color: white;" ><font class="topic" >ข้อมูลการตรวจแปลง</font></span>
				</div>
				<!-- Landcheck Detail -->
					<div class="line-input-keyin">
						<table width="98%" cellspacing="5" cellpadding="0" class="table-input">
							<tr>
								<td align="right" width="160" height="35">ปีการผลิต :</td>
								<td><font color="red">*</font></td>
								<td width="190"><select id="plantYear"  name="plantYear" onchange="getLandRight('plantYear', 'season', 'idCard', 'docId')" >
									<option value="0">กรุณาเลือก</option>
									<%if(plantYearList !=null && plantYearList.size()>0){
										for(int i=0; i<plantYearList.size(); i++){
											Plant plant = (Plant)plantYearList.get(i);
											%>
											<option value="<%= plant.getPlantYear()%>" ><%= plant.getPlantYear()%></option>
											<%}} %>
								</select></td>
								<td align="right" width="100"></td>
								<td><font color="red">*</font></td>
								<td class="check" width="130"><input type="radio" id="season1" name="season" value="1" onchange="getLandRight('plantYear',  'season', 'idCard', 'docId')" /><label for="season1">นาปี</label>
								<input type="radio" id="season2" name="season" value="2" onchange="getLandRight('plantYear',  'season', 'idCard', 'docId')" /><label for="season2">นาปรัง</label></td>
								<td width="70"></td>
								<td></td>
								<td width="130"></td>
								<td width="130"></td>
							</tr>
							<tr>
								<td align="right" height="35">เลขบัตรประจำตัวประชาชน :</td>
								<td><font color="red">*</font></td>
								<td><dcs:validateText name="idCard" property="idCard" maxlength="13" onChange="checkIdCard('idCard')" /></td>
								<td align="right">ชื่อ :</td>
								<td><font color="red">*</font></td>
								<td><dcs:validateText name="firstName" property="firstName" isReadOnly="true" /></td>
								<td align="right">นามสกุล :</td>
								<td><font color="red">*</font></td>
								<td><dcs:validateText name="lastName" property="lastName" isReadOnly="true" /></td>
								<td></td>
							</tr>
							<tr>
								<td align="right" height="35">เอกสารสิทธิ์ :</td>
								<td><font color="red">*</font></td>
								<td><select  id="docId" name="docId" onchange="getLandRightDetail('docId')">
									<option value="0">กรุณาเลือก</option>
								</select></td>
								<td align="right">เลขที่ :</td>
								<td></td>
								<td class="date" colspan="2"><dcs:validateText name="docNo" property="docNo" maxlength="10" isReadOnly="true" /><div style="float:right">พิกัดแปลงนา :</div></td>
								<td><font color="red">*</font></td>
								<td rowspan="4" colspan="2"><div class="boxinput" style="width:280px; height:140px; overflow-y:scroll;">
								<div id="coordinatesDivList">
									<table align="center" width="100%">
										<tbody>
											<tr><td>
												<a class="btn-add" onclick="addCoordinates();"></a>
												<a class="btn-del" onclick="delCoordinates2();"></a>												
											</td></tr>
											<tr>
												<td><table width="100%" cellspacing="0" cellpadding="0" id="Coordinates">
													<thead>
														<tr class="gridHeader">
															<td width="30" class="gridHeader" align="center"><input type="checkbox" name="checkAll_cooDel" onclick="checkAll(this, 'cooDel');" /></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">ละติจูด ( X )</a></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">ลองติจูด ( Y )</a></td>
															<td width="30" class="manage-sr">&nbsp;</td>
														</tr>
													</thead>
													<tbody>
														<% if(coordinatesList!=null && coordinatesList.size()>0) { 
															for(int i=1;i<=coordinatesList.size();i++) {
																Coordinates getItem = (Coordinates)coordinatesList.get(i-1); %>
																<% if(i%2==1) { %>
																<tr id="coo<%=i%>" class="gridRowEven">
																	<% }else{ %>
																	<tr id="coo<%=i%>" class="gridRowOdd">
																		<% } %>
																		<td><input type="checkbox" name="cooDel" value="<%=i%>" />
																			<input type="hidden" id="coordinatesId<%=i%>" name="coordinatesId" value="<%=getItem.getCoordinatesId()%>" />
																			<input type="hidden" id="latitude<%=i%>" name="latitude" value="<%=getItem.getLatitude()%>" />
																			<input type="hidden" id="longitude<%=i%>" name="longitude" value="<%=getItem.getLongitude()%>" /></td>
																			<td id="txtLatitude<%=i%>" onclick="editCoordinates(<%=i%>);" style="cursor:pointer;"><%=getItem.getLatitude()%></td>
																			<td id="txtLongitude<%=i%>" onclick="editCoordinates(<%=i%>);" style="cursor:pointer;"><%=getItem.getLongitude()%></td>
																			<td onclick="editCoordinates(<%=i%>);"><a class="btn-edit"></a></td>
																		</tr>
																		<% } %>
																		<% }else{ %><tr id="noData" class="gridRowEven">
																		<td class="last-child" colspan="4">No records to display!</td>
																	</tr><% } %>
													</tbody>
													<tr>
														<td colspan="4" class="gridPageOfPage">&nbsp;</td>
													</tr>
												</table></td>
											</tr>				
										</tbody>
									</table>
								</div>
							</div></td>
							</tr>
							<tr class="area">
								<td align="right" height="35">ขนาด :</td>
								<td></td>
								<td><div class="boxinput area" style="width:190px;">
								<dcs:validateText name="docRai" property="docRai" maxlength="3" isReadOnly="true" /> ไร่
								<dcs:validateText name="docNgan" property="docNgan" maxlength="3" isReadOnly="true" /> งาน
								<dcs:validateText name="docWah" property="docWah" maxlength="3" isReadOnly="true" /> วา
								</div></td>
								<td align="right">ระยะการตรวจ :</td>
								<td><font color="red">*</font></td>
								<td colspan="2"><select id="checkPeriod" name="checkPeriod" onchange="setTab('checkPeriod');getMixedBreedInfo('checkPeriod', 'inMixedBreedTypeId', 'inMixedChildId')">
									<option value="0">กรุณาเลือก</option>
									<%if(checkPeriodList !=null && checkPeriodList.size()>0){
										for(int i=0; i<checkPeriodList.size(); i++){
											CheckPeriod period = (CheckPeriod)checkPeriodList.get(i);
											%>
											<option value="<%=period.getCheckPeriodId()%>"><%=period.getDescription()%></option>
									<%}} %>
								</select></td>
								<td></td>
							</tr>
							<tr>
								<td align="right" height="35">วันที่ตรวจ :</td>
								<td><font color="red">*</font></td>
								<td class="date"><dcs:validateDate name="checkDate" property="checkDate" /></td>
								<td align="right">เวลาที่ตรวจ :</td>
								<td><font color="red">*</font></td>
								<td class="time"><select id="checkHour"  name="checkHour">
									<%for(int i=0; i<24; i++){%>
									<option value="<%=String.format("%02d", i)%>"><%=String.format("%02d", i)%></option>
									<%}%>
								</select> :
								<select id="checkMinute"  name="checkMinute">
									<%for(int i=0; i<60; i++){%>
									<option value="<%=String.format("%02d", i)%>"><%=String.format("%02d", i)%></option>
									<%}%>
								</select></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
				
				<fieldset><legend>รายละเอียดการตรวจแปลง</legend>
				<div class="content-keyin">
					<div id="i_containTab">  
						<ul id="navi_containTab">
							<li class="tabNavi1" style="width: 120px; line-height: 32px; background-color: #C4C4C4;">การเตรียมดิน</li>
							<li class="tabNavi2" style="width: 120px; line-height: 32px;">การปลูกข้าว</li>
							<li class="tabNavi3" style="width: 120px; line-height: 32px;">การใช้ปุ๋ย</li>
							<li class="tabNavi4" style="line-height: 32px;">การกำจัดพันธุ์ปน</li>
							<li class="tabNavi5" style="width: 160px; line-height: 32px;">การระบาด/การป้องกันกำจัด</li>
							<li class="tabNavi6" style="line-height: 32px;">การเก็บเกี่ยวข้าวเปลือก</li> <!-- Add new -->
						</ul>
					</div>
				</div>
				<div class="clear"></div>
				<ul id="tab_container">
				<!-- Prepare Soil -->
				<li id="tab-PrepareSoil">
					<div class="line-input-keyin">
						<ul>
							<li class="boxinput" style="width:710px;">
								<a class="btn-add" onclick="addPrepareSoil();"></a>
								<a class="btn-del" onclick="delPrepareSoil();"></a>								
							</li>
						</ul>
					</div>
												<table width="98%" align="center" cellspacing="0" cellpadding="0" id="PrepareSoil">
													<thead>
														<tr class="gridHeader">
															<td width="30" class="gridHeader" align="center"><input type="checkbox" name="checkAll_preDel" onclick="checkAll(this, 'preDel');" /></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">วันที่ปฏิบัติ</a></td>
															<td width="240" class="address-sr" align="center"><a href="#" class="gridSort">รายการปฏิบัติ</a></td>
															<td width="160" class="address-sr" align="center"><a href="#" class="gridSort">ผู้ตรวจการปฏิบัติ</a></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">ผลการปฏิบัติ</a></td>
															<td width="30" class="manage-sr">&nbsp;</td>
														</tr>
													</thead>
													<tbody>
											<% if(landCheckPrepareSoilList!=null && landCheckPrepareSoilList.size()>0) { 
												while(landCheckPrepareSoilList.remove(null));
												for(int i=1;i<=landCheckPrepareSoilList.size();i++) {
													LandCheckPrepareSoil obj = (LandCheckPrepareSoil)landCheckPrepareSoilList.get(i-1);
													if(obj != null) { %>
												<tr id="pre<%=i%>" class="<%=(i%2==1)?"gridRowEven":"gridRowOdd"%>">
												<td><input type="checkbox" name="preDel" value="<%=i%>" />
													<input id="prepareSoilType<%=i%>" name="prepareSoilType" value="<%=obj.getSoilType()%>" type="hidden" />
													<input id="prepareSoilWater<%=i%>" name="prepareSoilWater" value="<%=obj.getSourceWater()%>" type="hidden" />
													<input id="prepareSoilOperateOther<%=i%>" name="prepareSoilOperateOther" value="<%=obj.getOperateOther()%>" type="hidden" />
													<input id="prepareSoilRemark<%=i%>" name="prepareSoilRemark" value="<%=obj.getRemark()%>" type="hidden" />
													<input id="prepareSoilId<%=i%>" name="prepareSoilId" value="<%=obj.getLandCheckSoilId()%>" type="hidden" />
													<input id="prepareSoilDate<%=i%>" name="prepareSoilDate" value="<%=Utility.getThDate(obj.getCheckDate())%>" type="hidden" />
													<input id="prepareSoilOperate<%=i%>" name="prepareSoilOperate" value="<%=obj.getOperate()%>" type="hidden" />
													<input id="prepareSoilCheckBy<%=i%>" name="prepareSoilCheckBy" value="<%=obj.getChecker()%>" type="hidden" />
													<input id="prepareSoilResult<%=i%>" name="prepareSoilResult" value="<%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%>" type="hidden" /></td>
												<td id="txtPrepareSoilDate<%=i%>" onclick="editPrepareSoil(<%=i%>);" style="cursor:pointer;text-align:center;"><%=Utility.getThDate(obj.getCheckDate())%></td>
												<td id="txtPrepareSoilOperate<%=i%>" onclick="editPrepareSoil(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getOperate()%></td>
												<td id="txtPrepareSoilCheckBy<%=i%>" onclick="editPrepareSoil(<%=i%>);" style="cursor:pointer;"><%=obj.getChecker()%></td>
												<td id="txtPrepareSoilResult<%=i%>" onclick="editPrepareSoil(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%></td>
												<td onclick="editPrepareSoil(<%=i%>);"><a class="btn-edit"></a></td>
												</tr>
											<% }} %>
														<% }else{ %>
														<tr id="noData" class="gridRowEven">
															<td class="last-child" colspan="6">No records to display!</td>
														</tr><% } %>
													</tbody>
													<tr>
														<td colspan="6" class="gridPageOfPage">&nbsp;</td>
													</tr>
												</table>
				</li>
				
				<!-- Plant -->
				<li id="tab-Plant" style="display: none;">
					<div class="line-input-keyin">
						<ul>
							<li class="boxinput" style="width:710px;">
								<a class="btn-add" onclick="addPlant();"></a>
								<a class="btn-del" onclick="delPlant();"></a>
							</li>
						</ul>
					</div>
									<table width="98%" align="center" cellspacing="0" cellpadding="0" id="Plant">
										<thead>
											<tr class="gridHeader">
												<td width="30" class="gridHeader" align="center"><input type="checkbox" name="checkAll_plaDel" onclick="checkAll(this, 'plaDel');" /></td>
												<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">วันที่ปฏิบัติ</a></td>
												<td width="240" class="address-sr" align="center"><a href="#" class="gridSort">รายการปฏิบัติ</a></td>
												<td width="160" class="address-sr" align="center"><a href="#" class="gridSort">ผู้ตรวจการปฏิบัติ</a></td>
												<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">ผลการปฏิบัติ</a></td>
												<td width="30" class="manage-sr">&nbsp;</td>
											</tr>
										</thead>
										<tbody>
											<% if(landCheckPlantList!=null && landCheckPlantList.size()>0) { 
												while(landCheckPlantList.remove(null));
												for(int i=1;i<=landCheckPlantList.size();i++) {
													LandCheckPlant obj = (LandCheckPlant)landCheckPlantList.get(i-1);
													if(obj != null) { %>
											<tr id="pla<%=i%>" class="<%=(i%2==1)?"gridRowEven":"gridRowOdd"%>">
												<td><input name="plaDel" value="<%=i%>" type="checkbox" />
													<input id="plantId<%=i%>" name="plantId" value="<%=obj.getLandCheckPlantId()%>" type="hidden" />
													<input id="plantSource<%=i%>" name="plantSource" value="<%=obj.getSourceSeed()%>" type="hidden" />
													<input id="plantUse<%=i%>" name="plantUse" value="<%=obj.getUseRate()%>" type="hidden" />
													<input id="plantRemark<%=i%>" name="plantRemark" value="<%=obj.getRemark()%>" type="hidden" />
													<input id="plantName<%=i%>" name="plantName" value="<%=obj.getBreedTypeId()%>" type="hidden" />
													<input id="plantSowDate<%=i%>" name="plantSowDate" value="<%=Utility.getThDate(obj.getSowDate())%>" type="hidden" />
													<input id="plantThrowDate<%=i%>" name="plantThrowDate" value="<%=Utility.getThDate(obj.getThrowDate())%>" type="hidden" />
													<input id="plantType<%=i%>" name="plantType" value="<%=obj.getBreedTypeLevel()%>" type="hidden" />
													<input id="plantDate<%=i%>" name="plantDate" value="<%=Utility.getThDate(obj.getCheckDate())%>" type="hidden" />
													<input id="plantMethod<%=i%>" name="plantMethod" value="<%=obj.getPlantMethod()%>" type="hidden" />
													<input id="plantCheckBy<%=i%>" name="plantCheckBy" value="<%=obj.getChecker()%>" type="hidden" />
													<input id="plantResult<%=i%>" name="plantResult" value="<%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%>" type="hidden" /></td>
												<td id="txtPlantDate<%=i%>" onclick="editPlant(<%=i%>);" style="cursor:pointer;text-align:center;"><%=Utility.getThDate(obj.getCheckDate())%></td>
												<td id="txtPlantMethod<%=i%>" onclick="editPlant(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getPlantMethod()%></td>
												<td id="txtPlantCheckBy<%=i%>" onclick="editPlant(<%=i%>);" style="cursor:pointer;"><%=obj.getChecker()%></td>
												<td id="txtPlantResult<%=i%>" onclick="editPlant(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%></td>
												<td onclick="editPlant(<%=i%>);"><a class="btn-edit"></a></td>
											</tr>
											<% }} %>
											<% }else{ %>
											<tr id="noData" class="gridRowEven">
												<td class="last-child" colspan="6">No records to display!</td>
											</tr><% } %>
										</tbody>
										<tr>
											<td colspan="6" class="gridPageOfPage">&nbsp;</td>
										</tr>
									</table>
				</li>

				<!-- Manure -->
				<li id="tab-Manure" style="display: none;">
					<div class="line-input-keyin">
						<ul>
							<li class="boxinput" style="width:710px;">
								<a class="btn-add" onclick="addManure();"></a>
								<a class="btn-del" onclick="delManure();"></a>
							</li>
						</ul>
					</div>
												<table width="98%" align="center" cellspacing="0" cellpadding="0" id="Manure">
													<thead>
														<tr class="gridHeader">
															<td width="30" class="gridHeader" align="center"><input type="checkbox" name="checkAll_manDel" onclick="checkAll(this, 'manDel');" /></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">วันที่ปฏิบัติ</a></td>
															<td width="240" class="address-sr" align="center"><a href="#" class="gridSort">ชนิดปุ๋ยที่ใช้</a></td>
															<td width="160" class="address-sr" align="center"><a href="#" class="gridSort">ผู้ตรวจการปฏิบัติ</a></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">ผลการปฏิบัติ</a></td>
															<td width="30" class="manage-sr">&nbsp;</td>
														</tr>
													</thead>
													<tbody>
											<% if(landCheckManureList!=null && landCheckManureList.size()>0) { 
												while(landCheckManureList.remove(null));
												for(int i=1;i<=landCheckManureList.size();i++) {
													LandCheckManure obj = (LandCheckManure)landCheckManureList.get(i-1);
													if(obj != null) { %>
											<tr id="man<%=i%>" class="<%=(i%2==1)?"gridRowEven":"gridRowOdd"%>">
												<td><input type="checkbox" name="manDel" value="<%=i%>" />
													<input id="manureId<%=i%>" name="manureId" value="<%=obj.getLandCheckManureId()%>" type="hidden" />
													<input id="manureOther<%=i%>" name="manureOther" value="<%=obj.getManureOther()%>" type="hidden" />
													<input id="manureStatus<%=i%>" name="manureStatus" value="<%=obj.getManureStatus().equals("B")?"ซื้อ":"ผลิตเอง"%>" type="hidden" />
													<input id="manureName<%=i%>" name="manureName" value="<%=obj.getManureName()%>" type="hidden" />
													<input id="manureFormula<%=i%>" name="manureFormula" value="<%=obj.getFormula()%>" type="hidden" />
													<input id="manureBuyDate<%=i%>" name="manureBuyDate" value="<%=Utility.getThDate(obj.getBuyDate())%>" type="hidden" />
													<input id="manureSourceBuy<%=i%>" name="manureSourceBuy" value="<%=obj.getSourceBuy()%>" type="hidden" />
													<input id="manureUseRate<%=i%>" name="manureUseRate" value="<%=obj.getUseRate()%>" type="hidden" />
													<input id="manureTotalUse<%=i%>" name="manureTotalUse" value="<%=obj.getTotalUse()%>" type="hidden" />
													<input id="manureRemark<%=i%>" name="manureRemark" value="<%=obj.getRemark()%>" type="hidden" />
													<input id="manureDate<%=i%>" name="manureDate" value="<%=Utility.getThDate(obj.getCheckDate())%>" type="hidden" />
													<input id="manureType<%=i%>" name="manureType" value="<%=obj.getManureTypeId()%>" type="hidden" />
													<input id="manureCheckBy<%=i%>" name="manureCheckBy" value="<%=obj.getChecker()%>" type="hidden" />
													<input id="manureResult<%=i%>" name="manureResult" value="<%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%>" type="hidden" /></td>
												<td id="txtManureDate<%=i%>" onclick="editManure(<%=i%>);" style="cursor:pointer;text-align:center;"><%=Utility.getThDate(obj.getCheckDate())%></td>
												<td id="txtManureType<%=i%>" onclick="editManure(<%=i%>);" style="cursor:pointer;text-align:center;"><%=((ManureType)manureTypeList.get((int)obj.getManureTypeId()-1)).getTypeName()%></td>
												<td id="txtManureCheckBy<%=i%>" onclick="editManure(<%=i%>);" style="cursor:pointer;"><%=obj.getChecker()%></td>
												<td id="txtManureResult<%=i%>" onclick="editManure(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%></td>
												<td onclick="editManure(<%=i%>);"><a class="btn-edit"></a></td>
											</tr>
											<% }} %>
														<% }else{ %>
														<tr id="noData" class="gridRowEven">
															<td class="last-child" colspan="6">No records to display!</td>
														</tr><% } %>
													</tbody>
													<tr>
														<td colspan="6" class="gridPageOfPage">&nbsp;</td>
													</tr>
												</table>
				</li>

				<!-- Removal Mixed -->
				<li id="tab-Mixed" style="display: none;">
					<div class="line-input-keyin">
						<ul>
							<li class="boxinput" style="width:710px;">
								<a class="btn-add" onclick="addMixed();"></a>
								<a class="btn-del" onclick="delMixed();"></a>
							</li>
						</ul>
					</div>
												<table width="98%" align="center" cellspacing="0" cellpadding="0" id="Mixed">
													<thead>
														<tr class="gridHeader">
															<td width="30" class="gridHeader" align="center"><input type="checkbox" name="checkAll_mixDel" onclick="checkAll(this, 'mixDel');" /></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">วันที่ปฏิบัติ</a></td>
															<td width="240" class="address-sr" align="center"><a href="#" class="gridSort">รายการลักษณะพันธุ์ปนที่พบในแปลง</a></td>
															<td width="160" class="address-sr" align="center"><a href="#" class="gridSort">ผู้ตรวจการปฏิบัติ</a></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">ผลการปฏิบัติ</a></td>
															<td width="30" class="manage-sr">&nbsp;</td>
														</tr>
													</thead>
													<tbody>
											<% if(landCheckMixedList!=null && landCheckMixedList.size()>0) { 
												while(landCheckMixedList.remove(null));
												for(int i=1;i<=landCheckMixedList.size();i++) {
													LandCheckMixed obj = (LandCheckMixed)landCheckMixedList.get(i-1);
													if(obj != null) { %>
											<tr id="mix<%=i%>" class="<%=(i%2==1)?"gridRowEven":"gridRowOdd"%>">
												<td><input type="checkbox" name="mixDel" value="<%=i%>" />
													<input id="mixedId<%=i%>" name="mixedId" value="<%=obj.getLandCheckMixedId()%>" type="hidden" />
													<input id="mixedChildId<%=i%>" name="mixedChildId" value="<%=obj.getChildId()%>" type="hidden" />
													<input id="mixedEliminateId<%=i%>" name="mixedEliminateId" value="<%=obj.getEliminateMixedBreedId()%>" type="hidden" />
													<input id="mixedEliminateOther<%=i%>" name="mixedEliminateOther" value="<%=obj.getEliminateOther()%>" type="hidden" />
													<input id="mixedRemark<%=i%>" name="mixedRemark" value="<%=obj.getRemark()%>" type="hidden" />
													<input id="mixedDate<%=i%>" name="mixedDate" value="<%=Utility.getThDate(obj.getCheckDate())%>" type="hidden" />
													<input id="mixedBreedType<%=i%>" name="mixedBreedType" value="<%=obj.getMixedBreedTypeId()%>" type="hidden" />
													<input id="mixedCheckBy<%=i%>" name="mixedCheckBy" value="<%=obj.getChecker()%>" type="hidden" />
													<input id="mixedResult<%=i%>" name="mixedResult" value="<%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%>" type="hidden" /></td>
												<td id="txtMixedDate<%=i%>" onclick="editMixed(<%=i%>);" style="cursor:pointer;text-align:center;"><%=Utility.getThDate(obj.getCheckDate())%></td>
												<td id="txtMixedBreedType<%=i%>" onclick="editMixed(<%=i%>);" style="cursor:pointer;"><%=((MixedBreedType)mixedBreedTypeAll.get((int)obj.getMixedBreedTypeId()-1)).getDescription()%> / <%=((MixedBreedTypeChild)mixedBreedTypeChildAll.get((int)obj.getChildId()-1)).getDescription()%></td>
												<td id="txtMixedCheckBy<%=i%>" onclick="editMixed(<%=i%>);" style="cursor:pointer;"><%=obj.getChecker()%></td>
												<td id="txtMixedResult<%=i%>" onclick="editMixed(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%></td>
												<td onclick="editMixed(<%=i%>);"><a class="btn-edit"></a></td>
											</tr>
											<% }} %>
											<% }else{ %>
											<tr id="noData" class="gridRowEven">
												<td class="last-child" colspan="6">No records to display!</td>
											</tr><% } %>
										</tbody>
										<tr>
											<td colspan="6" class="gridPageOfPage">&nbsp;</td>
										</tr>
									</table>
				</li>

				<!-- Disease -->
				<li id="tab-Disease" style="display: none;">
					<div class="line-input-keyin">
						<ul>
							<li class="boxinput" style="width:710px;">
								<a class="btn-add" onclick="addDisease();"></a>
								<a class="btn-del" onclick="delDisease();"></a>
							</li>
						</ul>
					</div>
									<table width="98%" align="center" cellspacing="0" cellpadding="0" id="Disease">
										<thead>
											<tr class="gridHeader">
												<td width="30" class="gridHeader" align="center"><input type="checkbox" name="checkAll_disDel" onclick="checkAll(this, 'disDel');" /></td>
												<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">วันที่ปฏิบัติ</a></td>
												<td width="220" class="address-sr" align="center"><a href="#" class="gridSort">รายการตรวจ</a></td>
												<td width="120" class="address-sr" align="center"><a href="#" class="gridSort">ความรุนแรง</a></td>
												<td width="160" class="address-sr" align="center"><a href="#" class="gridSort">ผู้ตรวจการปฏิบัติ</a></td>
												<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">ผลการปฏิบัติ</a></td>
												<td width="30" class="manage-sr">&nbsp;</td>
											</tr>
										</thead>
										<tbody>
											<% if(landCheckDiseaseList!=null && landCheckDiseaseList.size()>0) { 
												while(landCheckDiseaseList.remove(null));
												for(int i=1;i<=landCheckDiseaseList.size();i++) {
													LandCheckDisease obj = (LandCheckDisease)landCheckDiseaseList.get(i-1);
													if(obj != null) { %>
											<tr id="dis<%=i%>" class="<%=(i%2==1)?"gridRowEven":"gridRowOdd"%>">
												<td><input type="checkbox" name="disDel" value="<%=i%>" />
													<input id="diseaseId<%=i%>" name="diseaseId" value="<%=obj.getLandCheckDiseaseId()%>" type="hidden" />
													<input id="diseaseChildId<%=i%>" name="diseaseChildId" value="<%=obj.getDiseaseChildId()%>" type="hidden" />
													<input id="diseaseTypeId<%=i%>" name="diseaseTypeId" value="<%=obj.getDiseaseTypeId()%>" type="hidden" />
													<input id="diseaseOther<%=i%>" name="diseaseOther" value="<%=obj.getDiseaseOther()%>" type="hidden" />
													<input id="diseaseTradingName<%=i%>" name="diseaseTradingName" value="<%=obj.getTradingName()%>" type="hidden" />
													<input id="diseaseCommonName<%=i%>" name="diseaseCommonName" value="<%=obj.getCommonName()%>" type="hidden" />
													<input id="diseaseDangerousName<%=i%>" name="diseaseDangerousName" value="<%=obj.getDangerousName()%>" type="hidden" />
													<input id="diseaseManDate<%=i%>" name="diseaseManDate" value="<%=Utility.getThDate(obj.getManufactureDate())%>" type="hidden" />
													<input id="diseaseExpDate<%=i%>" name="diseaseExpDate" value="<%=Utility.getThDate(obj.getExpireDate())%>" type="hidden" />
													<input id="diseaseSourceBuy<%=i%>" name="diseaseSourceBuy" value="<%=obj.getSourceBuy()%>" type="hidden" />
													<input id="diseaseUseDate<%=i%>" name="diseaseUseDate" value="<%=Utility.getThDate(obj.getUseDate())%>" type="hidden" />
													<input id="diseaseUseRate1<%=i%>" name="diseaseUseRate1" value="<%=obj.getUseRate1()%>" type="hidden" />
													<input id="diseaseUseRate2<%=i%>" name="diseaseUseRate2" value="<%=obj.getUseRate2()%>" type="hidden" />
													<input id="diseaseRemark<%=i%>" name="diseaseRemark" value="<%=obj.getRemark()%>" type="hidden" />
													<input id="diseaseDate<%=i%>" name="diseaseDate" value="<%=Utility.getThDate(obj.getCheckDate())%>" type="hidden" />
													<input id="diseaseChecking<%=i%>" name="diseaseChecking" value="<%=obj.getCheckingDiseaseId()%>" type="hidden" />
													<input id="diseaseLevel<%=i%>" name="diseaseLevel" value="<%=obj.getLevel()%>" type="hidden" />
													<input id="diseaseCheckBy<%=i%>" name="diseaseCheckBy" value="<%=obj.getChecker()%>" type="hidden" />
													<input id="diseaseResult<%=i%>" name="diseaseResult" value="<%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%>" type="hidden" /></td>
												<td id="txtDiseaseDate<%=i%>" onclick="editDisease(<%=i%>);" style="cursor:pointer;text-align:center;"><%=Utility.getThDate(obj.getCheckDate())%></td>
												<td id="txtDiseaseChecking<%=i%>" onclick="editDisease(<%=i%>);" style="cursor:pointer;"><%=((CheckingDisease)checkingDiseaseAll.get((int)obj.getCheckingDiseaseId()-1)).getDescription()%> / <%=((CheckingDiseaseChild)checkingDiseaseChildAll.get((int)obj.getDiseaseChildId()-1)).getDescription()%></td>
												<td id="txtDiseaseLevel<%=i%>" onclick="editDisease(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getLevel().equals("3")?"มาก":obj.getLevel().equals("2")?"ปานกลาง":"น้อย"%></td>
												<td id="txtDiseaseCheckBy<%=i%>" onclick="editDisease(<%=i%>);" style="cursor:pointer;"><%=obj.getChecker()%></td>
												<td id="txtDiseaseResult<%=i%>" onclick="editDisease(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%></td>
												<td onclick="editDisease(<%=i%>);"><a class="btn-edit"></a></td>
											</tr>
											<% }} %>
											<% }else{ %>
											<tr id="noData" class="gridRowEven">
												<td class="last-child" colspan="7">No records to display!</td>
											</tr><% } %>
										</tbody>
										<tr>
											<td colspan="7" class="gridPageOfPage">&nbsp;</td>
										</tr>
									</table>
				</li>

				<!-- Harvest -->
				<li id="tab-Harvest" style="display: none;">
					<div class="line-input-keyin">
						<ul>
							<li class="boxinput" style="width:710px;">
								<a class="btn-add" onclick="addHarvest();"></a>
								<a class="btn-del" onclick="delHarvest();"></a>
								<a class="btn-sell" id="selling" onclick="goSellPage();"></a>
							</li>
						</ul>
					</div>
												<table width="98%" align="center" cellspacing="0" cellpadding="0" id="Harvest">
													<thead>
														<tr class="gridHeader">
															<td width="30" class="gridHeader" align="center"><input type="checkbox" name="checkAll_harDel" onclick="checkAll(this, 'harDel');" /></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">วันที่ปฏิบัติ</a></td>
															<td width="160" class="address-sr" align="center"><a href="#" class="gridSort">ลักษณะรวงข้าวที่เก็บเกี่ยว</a></td>
															<td width="160" class="address-sr" align="center"><a href="#" class="gridSort">รถเกี่ยว</a></td>
															<td width="160" class="address-sr" align="center"><a href="#" class="gridSort">การทำความสะอาด/การบรรจุภาชนะ/ลักษณะการขนย้าย</a></td>
															<td width="140" class="address-sr" align="center"><a href="#" class="gridSort">ผู้ตรวจการปฏิบัติ</a></td>
															<td width="100" class="address-sr" align="center"><a href="#" class="gridSort">ผลการปฏิบัติ</a></td>
															<td width="30" class="manage-sr">&nbsp;</td>
														</tr>
													</thead>
													<tbody>
											<% if(landCheckHarvestList!=null && landCheckHarvestList.size()>0) { 
												while(landCheckHarvestList.remove(null));
												for(int i=1;i<=landCheckHarvestList.size();i++) {
													LandCheckHarvest obj = (LandCheckHarvest)landCheckHarvestList.get(i-1);
													if(obj != null) { %>
												<tr id="har<%=i%>" class="<%=(i%2==1)?"gridRowEven":"gridRowOdd"%>">
												<td><input type="checkbox" name="harDel" value="<%=i%>" />
													<input id="harvestId<%=i%>" name="harvestId" value="<%=obj.getLandCheckHarvestId()%>" type="hidden" />
													<input id="harvestDain<%=i%>" name="harvestDain" value="<%=obj.getDain()%>" type="hidden" />
													<input id="harvestQuality<%=i%>" name="harvestQuality" value="<%=obj.getHarvestQuality()%>" type="hidden" />
													<input id="harvestFarmStatus<%=i%>" name="harvestFarmStatus" value="<%=obj.getFarmStatus()%>" type="hidden" />
													<input id="harvester<%=i%>" name="harvester" value="<%=obj.getHarvester()%>" type="hidden" />
													<input id="harvestCleaning<%=i%>" name="harvestCleaning" value="<%=obj.getCleaning()%>" type="hidden" />
													<input id="harvestPackaging<%=i%>" name="harvestPackaging" value="<%=obj.getPackaging()%>" type="hidden" />
													<input id="harvestMoving<%=i%>" name="harvestMoving" value="<%=obj.getMoving()%>" type="hidden" />
													<input id="harvestObservers<%=i%>" name="harvestObservers" value="<%=obj.getObservers()%>" type="hidden" />
													<input id="harvestObserversDate<%=i%>" name="harvestObserversDate" value="<%=Utility.getThDate(obj.getObserveDate())%>" type="hidden" />
													<input id="harvestDainRemark<%=i%>" name="harvestDainRemark" value="<%=obj.getDainRemark()%>" type="hidden" />
													<input id="harvestDainDate<%=i%>" name="harvestDainDate" value="<%=Utility.getThDate(obj.getDainDate())%>" type="hidden" />
													<input id="harvestQualityDate<%=i%>" name="harvestQualityDate" value="<%=Utility.getThDate(obj.getHarvestQualityDate())%>" type="hidden" />
													<input id="harvestFarmStatusDate<%=i%>" name="harvestFarmStatusDate" value="<%=Utility.getThDate(obj.getFarmStatusDate())%>" type="hidden" />
													<input id="harvesterDate<%=i%>" name="harvesterDate" value="<%=Utility.getThDate(obj.getHarvesterDate())%>" type="hidden" />
													<input id="harvestCleaningDate<%=i%>" name="harvestCleaningDate" value="<%=Utility.getThDate(obj.getCleaningDate())%>" type="hidden" />
													<input id="harvestPackagingDate<%=i%>" name="harvestPackagingDate" value="<%=Utility.getThDate(obj.getPackagingDate())%>" type="hidden" />
													<input id="harvestMovingDate<%=i%>" name="harvestMovingDate" value="<%=Utility.getThDate(obj.getMovingDate())%>" type="hidden" />
													<input id="harvestTotal<%=i%>" name="harvestTotal" value="<%=obj.getTotalHarvest()%>" type="hidden" />
													<input id="harvestKeep<%=i%>" name="harvestKeep" value="<%=obj.getKeepHarvest()%>" type="hidden" />
													<input id="harvestSell<%=i%>" name="harvestSell" value="<%=obj.getSell()%>" type="hidden" />
													<input id="harvestResult<%=i%>" name="harvestResult" value="<%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%>" type="hidden" />
													<input id="harvestCheckBy<%=i%>" name="harvestCheckBy" value="<%=obj.getChecker()%>" type="hidden" />
													<input id="harvestSalePrice<%=i%>" name="harvestSalePrice" value="<%=obj.getSalePrice()%>" type="hidden" />
													<input id="harvestRemark<%=i%>" name="harvestRemark" value="<%=obj.getRemark()%>" type="hidden" /></td>
												<td id="txtHarvestQualityDate<%=i%>" onclick="editHarvest(<%=i%>);" style="cursor:pointer;text-align:center;"><%=Utility.getThDate(obj.getHarvestQualityDate())%></td>
												<td id="txtHarvestQuality<%=i%>" onclick="editHarvest(<%=i%>);" style="cursor:pointer;text-align:center;"><%=harvestQualitySource[Integer.parseInt(obj.getHarvestQuality())-1]%></td>
												<td id="txtHarvestTotal<%=i%>" onclick="editHarvest(<%=i%>);" style="cursor:pointer;text-align:center;">
													<%if(obj.getHarvester() != null && !"".equals(obj.getHarvester())){ %>
													
														<%=CONF_HARVESTERS[Integer.parseInt(obj.getHarvester())-1]%>	
													<%}%>												
												</td>
												<td id="txtHarvestKeep<%=i%>" onclick="editHarvest(<%=i%>);" style="cursor:pointer;text-align:center;">
													<%if(obj.getCleaning() != null && !"".equals(obj.getCleaning())){ %>
														<%=CONF_CLEANING[Integer.parseInt(obj.getCleaning())-1]%>	
													<%} %>/
													
													<%if(obj.getPackaging() != null && !"".equals(obj.getPackaging())){ %>
														<%=CONF_PACKAGING[Integer.parseInt(obj.getPackaging())-1]%>	
													<%} %>/
													
													<%if(obj.getMoving() != null && !"".equals(obj.getMoving())){ %>
														<%=CONF_MOVING[Integer.parseInt(obj.getMoving())-1]%>	
													<%} %>
													
												</td>
												<td id="txtHarvestCheckBy<%=i%>" onclick="editHarvest(<%=i%>);" style="cursor:pointer;" align="center"><%=obj.getChecker()%></td>
												<td id="txtHarvestResult<%=i%>" onclick="editHarvest(<%=i%>);" style="cursor:pointer;text-align:center;"><%=obj.getResult().equals("P")?"ผ่าน":"ไม่ผ่าน"%></td>
												<td onclick="editHarvest(<%=i%>);"><a class="btn-edit"></a></td>
												</tr>
											<% }} %>
														<% }else{ %>
														<tr id="noData" class="gridRowEven">
															<td class="last-child" colspan="8">No records to display!</td>
														</tr><% } %>
													</tbody>
													<tr>
														<td colspan="8" class="gridPageOfPage">&nbsp;</td>
													</tr>
												</table>
				</li>
				
				<!--  Popup -->
				<div id="CoordinatesPopup" title="รายละเอียดพิกัดแปลงนา" style="display:none;">
					<input type="hidden" id="inCoordinatesId" name="inCoordinatesId" value="0" />
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ประเภทเอกสารสิทธิ์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:250px;" name="txtDocType"></li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">เลขที่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;" name="txtDocNo"></li>
							<li class="topic"style="width:90px;padding-right:0px">ขนาดที่ดิน :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:140px;" name="txtDocSize"></li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ละติจูด ( X ) :</li>
							<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateText  name="inLatitude" maxlength="20"/></li>
							<li class="topic"style="width:100px;padding-right:0px">ลองติจูด ( Y ) :</li>
							<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
							<li class="boxinput" style="width:130px;">
								<dcs:validateText  name="inLongitude" maxlength="20"/></li>
						</ul>
					</div><br />
					<table align="center"><tr><td>
						<a class="btn-ok" onclick="saveCoordinates();"></a>
						<a class="btn-cancel" onclick="$('#CoordinatesPopup').dialog('close');"></a>
					</td></tr></table>
				</div>
					
				<div id="PrepareSoilPopup" title="รายละเอียดการเตรียมดิน" style="display:none;">
					<input type="hidden" id="inPrepareSoilId" name="inPrepareSoilId" value="0" />
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ประเภทเอกสารสิทธิ์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:250px;" name="txtDocType"></li>
						</ul>
					</div>
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">เลขที่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;" name="txtDocNo"></li>
							<li class="topic"style="width:90px;padding-right:0px">ขนาดที่ดิน :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:140px;" name="txtDocSize"></li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ระยะการตรวจ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:200px;" name="txtCheckPeriod"></li>
						</ul>
					</div>
					<fieldset><legend><b>รายละเอียดการเตรียมดิน</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:130px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inPrepareSoilDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin check">
							<ul>
								<li class="topic"style="width:130px;padding-right:0px">ประเภทของดิน :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<%if(landTypeList !=null && landTypeList.size()>0){
										for(int i=0; i<landTypeList.size(); i++){
											LandType landType = (LandType)landTypeList.get(i);
											if(i>0 && i%2==0) { %>
											<li class="topic"style="width:130px;padding-right:0px">&nbsp;</li>
											<li class="topic" style="width: 1px;padding-right:5px"></li>
										<% } %>
											<li class="boxinput" style="width:150px;">
												<label><input type="checkbox" name="inPrepareSoilType" value="<%=landType.getLandTypeId()%>" /><%=landType.getLandTypeName()%></label>
											</li>
									<% }} %>
							</ul>
						</div>
						<div class="line-input-keyin check">
							<ul>
								<li class="topic"style="width:130px;padding-right:0px">แหล่งน้ำที่ใช้ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<%if(waterSource !=null && waterSource.length>0){
										for(int i=0; i<waterSource.length; i++){
											if(i>0 && i%2==0) { %>
											<li class="topic"style="width:130px;padding-right:0px">&nbsp;</li>
											<li class="topic" style="width: 1px;padding-right:5px"></li>
										<% } %>
											<li class="boxinput" style="width:150px;">
												<label><input type="checkbox" name="inPrepareSoilWater" value="<%=waterSource[i]%>" /><%=waterSource[i]%></label>
											</li>
									<% }} %>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:130px;padding-right:0px">รายการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:330px;">
									<select id="inPrepareSoilOperate"  name="inPrepareSoilOperate">
										<option value="0">กรุณาเลือก</option>
										<%if(prepareSource !=null && prepareSource.length>0){
											for(int i=0; i<prepareSource.length; i++){ %>
											<option value="<%=prepareSource[i]%>"><%=prepareSource[i]%></option>
										<% }} %>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:130px;padding-right:0px">วิธีปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:330px;"><dcs:validateText  name="inPrepareSoilOperateOther" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:130px;padding-right:0px">ผลการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:330px;">
									<select id="inPrepareSoilResult"  name="inPrepareSoilResult">
										<option value="0">กรุณาเลือก</option>
										<option value="ผ่าน">ผ่าน</option>
										<option value="ไม่ผ่าน">ไม่ผ่าน</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:130px;padding-right:0px">หมายเหตุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:330px;"><dcs:validateText  name="inPrepareSoilRemark" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:130px;padding-right:0px">ผู้ตรวจการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:330px;"><dcs:validateText  name="inPrepareSoilCheckBy" /></li>
							</ul>
						</div>
					</fieldset><br />
					<table align="center"><tr><td>
						<a class="btn-ok" onclick="savePrepareSoil();"></a>
						<a class="btn-cancel" onclick="$('#PrepareSoilPopup').dialog('close');"></a>
					</td></tr></table>
				</div>
					
				<div id="PlantPopup" title="รายละเอียดการปลูกข้าว" style="display:none;">
					<input type="hidden" id="inPlantId" name="inPlantId" value="0" />
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ประเภทเอกสารสิทธิ์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:250px;" name="txtDocType"></li>
						</ul>
					</div>
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">เลขที่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;" name="txtDocNo"></li>
							<li class="topic"style="width:90px;padding-right:0px">ขนาดที่ดิน :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:140px;" name="txtDocSize"></li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ระยะการตรวจ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:200px;" name="txtCheckPeriod"></li>
						</ul>
					</div>
					<fieldset><legend><b>รายละเอียดการปลูกข้าว</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inPlantDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin check">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วิธีการปลูก :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<%if(plantMethodList !=null && plantMethodList.size()>0){
									for(int i=0; i<plantMethodList.size(); i++){
										PlantMethod method = (PlantMethod)plantMethodList.get(i);
										if(i>0 && i%2==0) { %>
										<li class="topic"style="width:140px;padding-right:0px">&nbsp;</li>
										<li class="topic" style="width: 1px;padding-right:5px"></li>
									<% } %>
										<li class="boxinput" style="width:150px;">
											<label><input type="checkbox" name="inPlantMethod" value="<%=method.getPlantMethodName()%>" /><%=method.getPlantMethodName()%></label>
										</li>
									<% }} %>
							</ul>
						</div>
						<div class="line-input-keyin check">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ประเภทของชั้นพันธุ์ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<%if(layerTypeSource !=null && layerTypeSource.length>0){
										for(int i=0; i<layerTypeSource.length; i++){
											if(i>0 && i%2==0) { %>
											<li class="topic"style="width:140px;padding-right:0px">&nbsp;</li>
											<li class="topic" style="width: 1px;padding-right:5px"></li>
										<% } %>
											<li class="boxinput" style="width:150px;">
												<label><input type="checkbox" name="inPlantType" value="<%=layerTypeSource[i]%>" /><%=layerTypeSource[i]%></label>
											</li>
								<% }} %>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ชื่อพันธุ์ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:330px;">
									<select id="inPlantName"  name="inPlantName">
										<option value="0">กรุณาเลือก</option>
										<%if(breedGroupList !=null && breedGroupList.size()>0){
										for(int i=0; i<breedGroupList.size(); i++){
											BreedGroup obj = (BreedGroup)breedGroupList.get(i);
											%>
											<option value="<%= obj.getBreedGroupId()%>" ><%= obj.getBreedGroupName()%></option>
										<%}} %>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">แหล่งที่มาของเมล็ดพันธุ์ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:330px;"><dcs:validateText  name="inPlantSource" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันที่ (หว่าน/ตกกล้า) :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inPlantSowDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันที่ (ปักดำ/โยน) :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inPlantThrowDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin date">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">อัตราที่ใช้ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:330px;"><input id="inPlantUse" name="inPlantUse" type="text" maxlength="13" onkeypress="return check2Decimal(this,event)"/> กก./ไร่</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ผลการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:330px;">
									<select id="inPlantResult" name="inPlantResult">
										<option value="0">กรุณาเลือก</option>
										<option value="ผ่าน">ผ่าน</option>
										<option value="ไม่ผ่าน">ไม่ผ่าน</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">หมายเหตุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:330px;"><dcs:validateText name="inPlantRemark" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ผู้ตรวจการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:330px;"><dcs:validateText name="inPlantCheckBy" /></li>
							</ul>
						</div>
					</fieldset><br />
					<table align="center"><tr><td>
						<a class="btn-ok" onclick="savePlant();"></a>
						<a class="btn-cancel" onclick="$('#PlantPopup').dialog('close');"></a>
					</td></tr></table>
				</div>
					
				<div id="ManurePopup" title="รายละเอียดการใช้ปุ๋ย" style="display:none;">
					<input type="hidden" id="inManureId" name="inManureId" value="0" />
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ประเภทเอกสารสิทธิ์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:250px;" name="txtDocType"></li>
						</ul>
					</div>
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">เลขที่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;" name="txtDocNo"></li>
							<li class="topic"style="width:90px;padding-right:0px">ขนาดที่ดิน :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:140px;" name="txtDocSize"></li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ระยะการตรวจ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:200px;" name="txtCheckPeriod"></li>
						</ul>
					</div>
					<fieldset><legend><b>รายละเอียดการใช้ปุ๋ย</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inManureDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ชนิดปุ๋ยที่ใช้ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:320px;">
									<select id="inManureType" name="inManureType" onchange="selectOther('inManureType','inManureOther');">
										<option value="0">กรุณาเลือก</option>
										<%if(manureTypeList !=null && manureTypeList.size()>0){
										for(int i=0; i<manureTypeList.size(); i++){
											ManureType mObj = (ManureType)manureTypeList.get(i);
											%>
											<option value="<%= mObj.getTypeId()%>" ><%= mObj.getTypeName()%></option>
										<%}} %>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ระบุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:320px;"><dcs:validateText name="inManureOther" cssClass="readonly" isReadOnly="true" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ชนิด/สูตร :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput check" style="width:60px;">
									<label><input type="radio" name="inManureStatus" value="ซื้อ" onchange="manureStatusChange('inManureStatus')" />ซื้อ</label>
								</li>
								<li class="boxinput check" style="width:80px;">
									<label><input type="radio" name="inManureStatus" value="ผลิตเอง" onchange="manureStatusChange('inManureStatus')" />ผลิตเอง</label>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ชื่อ/ตราสินค้า :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:320px;"><dcs:validateText name="inManureName" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ชนิด/สูตร :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:320px;"><dcs:validateText name="inManureFormula" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันที่ซื้อ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inManureBuyDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">แหล่งที่ซื้อ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:320px;"><dcs:validateText name="inManureSourceBuy" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">อัตราการใช้ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:320px;"><input id="inManureUseRate" name="inManureUseRate" type="text" maxlength="13" onkeypress="return check2Decimal(this,event)"/> กก./ไร่</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ปริมาณที่ใช้รวมทั้งแปลง :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:320px;"><input id="inManureTotalUse" name="inManureTotalUse" type="text" maxlength="13" onkeypress="return check2Decimal(this,event)"/> กก.</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ผลการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:320px;">
									<select id="inManureResult" name="inManureResult">
										<option value="0">กรุณาเลือก</option>
										<option value="ผ่าน">ผ่าน</option>
										<option value="ไม่ผ่าน">ไม่ผ่าน</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">หมายเหตุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:320px;"><dcs:validateText name="inManureRemark" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ผู้ตรวจการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:320px;"><dcs:validateText name="inManureCheckBy" /></li>
							</ul>
						</div>
					</fieldset><br />
					<table align="center"><tr><td>
						<a class="btn-ok" onclick="saveManure();"></a>
						<a class="btn-cancel" onclick="$('#ManurePopup').dialog('close');"></a>
					</td></tr></table>
				</div>
					
				<div id="MixedPopup" title="รายละเอียดการกำจัดพันธุ์ปน" style="display:none;">
					<input type="hidden" id="inMixedId" name="inMixedId" value="0" />
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ประเภทเอกสารสิทธิ์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:250px;" name="txtDocType"></li>
						</ul>
					</div>
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">เลขที่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;" name="txtDocNo"></li>
							<li class="topic"style="width:90px;padding-right:0px">ขนาดที่ดิน :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:140px;" name="txtDocSize"></li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ระยะการตรวจ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:200px;" name="txtCheckPeriod"></li>
						</ul>
					</div>
					<fieldset><legend><b>รายละเอียดการกำจัดพันธุ์ปน</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:170px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inMixedDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:170px;padding-right:0px">ประเภทลักษณะพันธุ์ปน :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inMixedBreedTypeId" name="inMixedBreedTypeId" onchange="getMixedBreedTypeInfo('inMixedBreedTypeId', 'inMixedChildId')">
										<option value="0">กรุณาเลือก</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:170px;padding-right:0px">ลักษณะพันธุ์ปน :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inMixedChildId" name="inMixedChildId">
										<option value="0">กรุณาเลือก</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:170px;padding-right:0px">วิธีการกำจัดพันธุ์ปน :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inMixedEliminateId" name="inMixedEliminateId" onchange="selectOther('inMixedEliminateId','inMixedEliminateOther');">
										<option value="0">กรุณาเลือก</option>
										<%if(eliminateMixedBreedList !=null && eliminateMixedBreedList.size()>0){
										for(int i=0; i<eliminateMixedBreedList.size(); i++){
											EliminateMixedBreed emObj = (EliminateMixedBreed)eliminateMixedBreedList.get(i);
											%>
											<option value="<%= emObj.getEliminateMixedBreedId()%>" ><%= emObj.getDescription()%></option>
										<%}} %>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:170px;padding-right:0px">ระบุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText name="inMixedEliminateOther" cssClass="readonly" isReadOnly="true"/></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:170px;padding-right:0px">ผลการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inMixedResult" name="inMixedResult">
										<option value="0">กรุณาเลือก</option>
										<option value="ผ่าน">ผ่าน</option>
										<option value="ไม่ผ่าน">ไม่ผ่าน</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:170px;padding-right:0px">หมายเหตุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText name="inMixedRemark" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:170px;padding-right:0px">ผู้ตรวจการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText name="inMixedCheckBy" /></li>
							</ul>
						</div>
					</fieldset><br />
					<table align="center"><tr><td>
						<a class="btn-ok" onclick="saveMixed();"></a>
						<a class="btn-cancel" onclick="$('#MixedPopup').dialog('close');"></a>
					</td></tr></table>
				</div>
					
				<div id="DiseasePopup" title="รายละเอียดรายการระบาด/การป้องกันกำจัด"  style="display:none;">
					<input type="hidden" id="inDiseaseId" name="inDiseaseId" value="0" />
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ประเภทเอกสารสิทธิ์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:250px;" name="txtDocType"></li>
						</ul>
					</div>
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">เลขที่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;" name="txtDocNo"></li>
							<li class="topic"style="width:90px;padding-right:0px">ขนาดที่ดิน :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:140px;" name="txtDocSize"></li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ระยะการตรวจ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:200px;" name="txtCheckPeriod"></li>
						</ul>
					</div>
					<fieldset><legend><b>รายละเอียดการระบาด</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inDiseaseDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">การตรวจ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inDiseaseCheckingId" name="inDiseaseCheckingId" onchange="getDiseaseChildInfo('inDiseaseCheckingId','inDiseaseChildId','inDiseaseTypeId')">
										<option value="0">กรุณาเลือก</option>
									<%if(checkingDiseaseList !=null && checkingDiseaseList.size()>0){
										for(int i=0; i<checkingDiseaseList.size(); i++){
											CheckingDisease chkDisease = (CheckingDisease)checkingDiseaseList.get(i);
											%>
											<option value="<%=chkDisease.getCheckingDiseaseId()%>"><%=chkDisease.getDescription()%></option>
									<%}} %>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">รายการที่ระบาด :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inDiseaseChildId" name="inDiseaseChildId" onchange="getDiseaseTypeInfo('inDiseaseCheckingId','inDiseaseChildId','inDiseaseTypeId');selectOther('inDiseaseChildId','inDiseaseOther');">
										<option value="0">กรุณาเลือก</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ระบุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText name="inDiseaseOther" cssClass="readonly" isReadOnly="true" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ลักษณะอาการ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inDiseaseTypeId" name="inDiseaseTypeId">
										<option value="0">กรุณาเลือก</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ความรุนแรง :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inDiseaseLevel" name="inDiseaseLevel">
										<option value="0">กรุณาเลือก</option>
										<option value="1">น้อย</option>
										<option value="2">ปานกลาง</option>
										<option value="3">มาก</option>
									</select>
								</li>
							</ul>
						</div>
					</fieldset>
					<fieldset><legend><b>รายละเอียดการป้องกันกำจัด</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ชื่อการค้า :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText name="inDiseaseTradingName" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ชื่อสามัญ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText name="inDiseaseCommonName" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ทะเบียนวัตถุอันตราย :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText name="inDiseaseDangerousName" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันที่ผลิต :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inDiseaseManDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันหมดอายุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inDiseaseExpDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">แหล่งที่ซื้อ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText name="inDiseaseSourceBuy" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">วันที่ใช้สาร :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inDiseaseUseDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">อัตราที่ใช้ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:300px;"><input id="inDiseaseUseRate1" name="inDiseaseUseRate1" type="text" maxlength="13" onkeypress="return check2Decimal(this,event)"/> ซีซี/น้ำ 20 ลิตร/ไร่</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">อัตราที่ใช้ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:300px;"><input id="inDiseaseUseRate2" name="inDiseaseUseRate2" type="text" maxlength="13" onkeypress="return check2Decimal(this,event)"/> ซีซี/ไร่</li>
							</ul>
						</div>
					</fieldset>
					<fieldset><legend><b>ผลการตรวจ</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ผลการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;">
									<select id="inDiseaseResult" name="inDiseaseResult">
										<option value="0">กรุณาเลือก</option>
										<option value="ผ่าน">ผ่าน</option>
										<option value="ไม่ผ่าน">ไม่ผ่าน</option>
									</select>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">หมายเหตุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText  name="inDiseaseRemark" /></li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:140px;padding-right:0px">ผู้ตรวจการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:300px;"><dcs:validateText  name="inDiseaseCheckBy" /></li>
							</ul>
						</div>
					</fieldset><br />
					<table align="center"><tr><td>
						<a class="btn-ok" onclick="saveDisease();"></a>
						<a class="btn-cancel" onclick="$('#DiseasePopup').dialog('close');"></a>
					</td></tr></table>
				</div>
					
				<div id="HarvestPopup" title="รายละเอียดการเก็บเกี่ยว" style="display:none;">
					<input type="hidden" id="inHarvestId" name="inHarvestId" value="0" />
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic" style="width:130px;padding-right:0px">ประเภทเอกสารสิทธิ์ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:250px;" name="txtDocType"></li>
						</ul>
					</div>
					<div class="line-input-keyin popup-detail">
						<ul>
							<li class="topic" style="width:130px;padding-right:0px">เลขที่ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:130px;" name="txtDocNo"></li>
							<li class="topic" style="width:100px;padding-right:0px">ขนาดที่ดิน :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:140px;" name="txtDocSize"></li>
						</ul>
					</div>
					<div class="line-input-keyin">
						<ul>
							<li class="topic"style="width:130px;padding-right:0px">ระยะการตรวจ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:200px;" name="txtCheckPeriod"></li>
						</ul>
					</div>
					<fieldset><legend><b>วันที่ออกดอก 80%</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic"style="width:110px;padding-right:0px">ข้อสังเกต :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:370px;"><dcs:validateText  name="inHarvestObservers" /></li>
								<li class="topic"style="width:90px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inHarvestObserversDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
							</ul>
						</div>
					</fieldset>
					<fieldset><legend><b>การระบายน้ำออกจากแปลง</b></legend>
						<% if(drainageSource != null && drainageSource.length > 0){
							double totalRow = drainageSource.length;
							long perRow = Math.round(Math.ceil(totalRow/2));
							for(int x=0; x < perRow; x++){  %>
						<div class="line-input-keyin">
							<ul>
								<% for(int y=2*x; y < 2*(x+1); y++){
									if(y < totalRow) { %>
								<li class="boxinput check" style="width:245px;">
									<label><input type="radio" name="inHarvestDain" value="<%=y+1%>$<%=drainageSource[y]%>" onchange="selectDrain('inHarvestDain', 'inHarvestDainRemark')" /><%=drainageSource[y]%></label>
								</li>
								<% if(drainageSource[y].endsWith("เพราะ")) { %>
								<li class="boxinput" style="width:290px;margin-left:-45px;">
									<dcs:validateText name="inHarvestDainRemark" cssClass="readonly" isReadOnly="true" />
								</li>
								<% }
								}
								if(y+1 == totalRow && x+1 == perRow) { 
									if((totalRow)%2 == 0) { %>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="boxinput" style="width:245px;">&nbsp;</li>
								<li class="boxinput" style="width:245px;">&nbsp;</li>
								<% } %>
								<li class="topic" style="width:90px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inHarvestDainDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
								<% } %>
								
								<% } %>
							</ul>
						</div>
						<% }} %>
					</fieldset>
					<fieldset><legend><b>ลักษณะรวงข้าวที่เก็บเกี่ยว</b></legend>
						<% if(harvestQualitySource != null && harvestQualitySource.length > 0){
							double length = harvestQualitySource.length;
							long rows = Math.round(Math.ceil(length/3));
							for(int x=0; x < rows; x++){ %>
						<div class="line-input-keyin">
							<ul>
								<% for(int y=3*x; y < 3*(x+1); y++){
									if(y < length) { %>
								<li class="boxinput check" style="width:160px;">
									<label><input type="radio" name="inHarvestQuality" value="<%=y+1%>$<%=harvestQualitySource[y]%>" /><%=harvestQualitySource[y]%></label>
								</li>
								<% }
								if(y+1 == length && x+1 == rows) { 
									for(int z=y+1; z < 3*rows; z++) { %>
								<li class="boxinput" style="width:160px;">&nbsp;</li>
								<% } %>
								<li class="topic" style="width:95px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inHarvestQualityDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
								<% }} %>
							</ul>
						</div>
						<% }} %>
					</fieldset>
					<fieldset><legend><b>สภาพที่นา</b></legend>
						<% if(farmStatusSource != null && farmStatusSource.length > 0){
							double length = farmStatusSource.length;
							long rows = Math.round(Math.ceil(length/3));
							for(int x=0; x < rows; x++){ %>
						<div class="line-input-keyin">
							<ul>
								<% for(int y=3*x; y < 3*(x+1); y++){
									if(y < length) { %>
								<li class="boxinput check" style="width:160px;">
									<label><input type="radio" name="inHarvestFarmStatus" value="<%=y+1%>$<%=farmStatusSource[y]%>" /><%=farmStatusSource[y]%></label>
								</li>
								<% }
								if(y+1 == length && x+1 == rows) { 
									for(int z=y+1; z < 3*rows; z++) { %>
								<li class="boxinput" style="width:160px;">&nbsp;</li>
								<% } %>
								<li class="topic" style="width:95px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inHarvestFarmStatusDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
								<% }} %>
							</ul>
						</div>
						<% }} %>
					</fieldset>
					<fieldset><legend><b>รถเกี่ยว</b></legend>
						<% if(harvestersSource != null && harvestersSource.length > 0){
							double length = harvestersSource.length;
							long rows = Math.round(Math.ceil(length/2));
							for(int x=0; x < rows; x++){ %>
						<div class="line-input-keyin">
							<ul>
								<% for(int y=2*x; y < 2*(x+1); y++){
									if(y < length) { %>
								<li class="boxinput check" style="width:245px;">
									<label><input type="radio" name="inHarvester" value="<%=y+1%>$<%=harvestersSource[y]%>" /><%=harvestersSource[y]%></label>
								</li>
								<% }
								if(y+1 == length && x+1 == rows) { 
									for(int z=y+1; z < 2*rows; z++) { %>
								<li class="boxinput" style="width:245px;">&nbsp;</li>
								<% } %>
								<li class="topic" style="width:90px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inHarvesterDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
								<% }} %>
							</ul>
						</div>
						<% }} %>
					</fieldset>
					<fieldset><legend><b>การทำความสะอาด</b></legend>
						<% if(cleaningSource != null && cleaningSource.length > 0){
							double length = cleaningSource.length;
							long rows = Math.round(Math.ceil(length/2));
							for(int x=0; x < rows; x++){ %>
						<div class="line-input-keyin">
							<ul>
								<% for(int y=2*x; y < 2*(x+1); y++){
									if(y < length) { %>
								<li class="boxinput check" style="width:245px;">
									<label><input type="radio" name="inHarvestCleaning" value="<%=y+1%>$<%=cleaningSource[y]%>" /><%=cleaningSource[y]%></label>
								</li>
								<% }
								if(y+1 == length && x+1 == rows) { 
									for(int z=y+1; z < 2*rows; z++) { %>
								<li class="boxinput" style="width:245px;">&nbsp;</li>
								<% } %>
								<li class="topic" style="width:90px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inHarvestCleaningDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
								<% }} %>
							</ul>
						</div>
						<% }} %>
					</fieldset>
					<fieldset><legend><b>การบรรจุภาชนะ</b></legend>
						<% if(packagingSource != null && packagingSource.length > 0){
							double length = packagingSource.length;
							for(int x=0; x < length; x++){ %>
						<div class="line-input-keyin">
							<ul>
								<li class="boxinput check" style="width:490px;">
									<label><input type="radio" name="inHarvestPackaging" value="<%=x+1%>$<%=packagingSource[x]%>" /><%=packagingSource[x]%></label>
								</li>
								<% if(x+1 == length) { %>
								<li class="topic" style="width:95px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inHarvestPackagingDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
								<% } %>
							</ul>
						</div>
						<% }} %>
					</fieldset>
					<fieldset><legend><b>ลักษณะการขนย้าย</b></legend>
						<% if(movingSource != null && movingSource.length > 0){
							double length = movingSource.length;
							long rows = Math.round(Math.ceil(length/2));
							for(int x=0; x < rows; x++){ %>
						<div class="line-input-keyin">
							<ul>
								<% for(int y=2*x; y < 2*(x+1); y++){
									if(y < length) { %>
								<li class="boxinput check" style="width:245px;">
									<label><input type="radio" name="inHarvestMoving" value="<%=y+1%>$<%=movingSource[y]%>" /><%=movingSource[y]%></label>
								</li>
								<% }
								if(y+1 == length && x+1 == rows) { 
									for(int z=y+1; z < 2*rows; z++) { %>
								<li class="boxinput" style="width:245px;">&nbsp;</li>
								<% } %>
								<li class="topic" style="width:90px;padding-right:0px">วันที่ปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput date" style="width:130px;"><dcs:validateDate name="inHarvestMovingDate" /></li>
								<li class="topic" style="width: 100px;"><font color="red">e.g. 31/12/2558</font></li>
								<% }} %>
							</ul>
						</div>
						<% }} %>
					</fieldset>
					<fieldset><legend><b>ผลผลิตที่ได้รับ/ผลการตรวจ</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic" style="width:100px;padding-right:0px">ผลผลิตที่ได้ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:130px;"><input id="inHarvestTotal" name="inHarvestTotal" type="text" maxlength="11" onkeypress="return check2Decimal(this,event)" /></li>
								<li class="topic" style="width: 20px;padding-right:5px">ตัน</li>
								
								<li class="topic" style="width:100px;padding-right:0px">ผลการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:130px;">
									<select id="inHarvestResult" name="inHarvestResult">
										<option value="0">กรุณาเลือก</option>
										<option value="ผ่าน">ผ่าน</option>
										<option value="ไม่ผ่าน">ไม่ผ่าน</option>
									</select>
								</li>
								<li class="topic" style="width: 20px;padding-right:5px"></li>
								<li class="topic" style="width:130px;padding-right:0px">ผู้ตรวจการปฏิบัติ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:130px;"><dcs:validateText name="inHarvestCheckBy" /></li>
								<li class="topic" style="width: 20px;padding-right:5px"></li>
								
								
								
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic" style="width:100px;padding-right:0px">หมายเหตุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:690px;"><dcs:validateText name="inHarvestRemark" /></li>
							</ul>
						</div>
						<div class="line-input-keyin" style="visibility: hidden;display: none;">
							<ul>
								
								<li class="topic" style="width:130px;padding-right:0px">เก็บไว้ทำพันธุ์/บริโภค :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:130px;"><input id="inHarvestKeep" name="inHarvestKeep" type="text" maxlength="11" onkeypress="return check2Decimal(this,event)" /></li>
								<li class="topic" style="width: 20px;padding-right:5px">ตัน</li>
								
								<li class="topic" style="width:60px;padding-right:0px">ขายสด :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:130px;"><input id="inHarvestSell" name="inHarvestSell" type="text" maxlength="11" onkeypress="return check2Decimal(this,event)" /></li>
								<li class="topic" style="width: 20px;padding-right:5px">ตัน</li>
								
								<li class="topic" style="width:60px;padding-right:0px">ราคาขาย :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width:130px;"><input id="inHarvestSalePrice" name="inHarvestSalePrice" type="text" maxlength="11" onkeypress="return check2Decimal(this,event)" /></li>
								<li class="topic" style="width: 50px;padding-right:5px">บาท/ตัน</li>
								
							</ul>
						</div>
						
					</fieldset><br />
					<table align="center"><tr><td>
						<a class="btn-ok" onclick="saveHarvest();"></a>
						<a class="btn-cancel" onclick="$('#HarvestPopup').dialog('close');"></a>
					</td></tr></table>
				</div>
			</ul>
			</fieldset>

					<fieldset><legend><b>ผลการตรวจแปลง</b></legend>
						<div class="line-input-keyin">
							<ul>
								<li class="topic" style="width: 140px;padding-right:0px">ผลการตรวจ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput check" style="width:130px;">
									<input type="radio" name="result" id="resultPass" value="P" /><label for="resultPass">ผ่าน</label>
									<input type="radio" name="result" id="resultFail" value="F" /><label for="resultFail">ไม่ผ่าน</label>
								</li>
							</ul>
						</div>
						<div class="line-input-keyin">
							<ul>
								<li class="topic" style="width: 140px;padding-right:0px">หมายเหตุ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"></li>
								<li class="boxinput" style="width: 740px;">
									<dcs:validateText name="remark" property="remark" style="width: 100% !important;" />
								</li>
							</ul>
						</div>
						<div class="line-input-keyin" >
							<ul>
								<li class="topic" style="width: 140px;padding-right:0px;padding-left:0px">เจ้าหน้าที่ตรวจ :</li>
								<li class="topic" style="width: 1px;padding-right:5px"><font color="red">*</font></li>
								<li class="boxinput" style="width:200px;">
									<dcs:validateText name="checkBy" property="checkBy" maxlength="100" />
								</li>
							</ul>
						</div>
					
					<div class="line-input-keyin">
						<ul>
							<li class="topic" style="width: 140px;padding-right:0px;float:left;">รูปภาพ :</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:780px;">
								<div id="myImage"></div>
							</li>
						</ul>
					</div>
					<div class="line-input-keyin" >
						<ul>
							<li class="topic" style="width: 140px;padding-right:0px">&nbsp;</li>
							<li class="topic" style="width: 1px;padding-right:5px"></li>
							<li class="boxinput" style="width:760px;">
								<a href="?addImage" class="btn-plus" onclick="addImage(); return false;">เพิ่มรูปภาพ</a>
							</li>
						</ul>
					</div>
					</fieldset><br />

					<table align="center">
						<tr>
							<td>
								<a class="btn-save" onclick="saveData();" id="write"></a>
							</td>	
							<td>
								<a class="btn-cancel" onclick="closePage();"></a>
							</td>			           
						</tr>
					</table>
			</div>
		</div>
		<br />
		<!--footer -->
		<%@include file="/footer.jsp" %>

		<!-- end insert >> content --> 

		<!-- end for header -->
	</div>

</dcs:validateForm>
<form name="landCheckListForm"  method="post" action="<%=request.getContextPath()%>/LandCheckList.do">
	<input type="hidden" name="cmd" />
</form>
</body>
</html>