<!doctype html>
<html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://com.dcs/dcswc" prefix="dcs"%>
<%@page import="com.wsnweb.form.PlantForm"%>
<%@page import="com.wsndata.data.Plant"%>
<%@page import="com.wsndata.data.Province"%>
<%@page import="com.wsndata.data.LandRightType"%>
<%@page import="com.wsndata.data.Asset"%>
<%@page import="com.wsndata.data.Farmer"%>
<%@page import="com.wsndata.data.FarmerGroup" %>
<%@page import="com.wsndata.data.BreedType" %>
<%@page import="com.wsndata.data.BreedGroup" %> 
<%@page import="com.wsndata.data.Branch"%>
<%@page import="com.wsndata.data.ManureType"%>
<%@page import="com.wsndata.data.ChemicalType"%>
<%@page import="com.wsndata.data.PlantManure"%> 
<%@page import="com.wsndata.data.PlantChemical"%>
<%@page import="com.wsndata.data.AssetDetail"%>
<%@page import="com.wsndata.data.Cost"%>
<%@page import="com.wsndata.data.Prefix"%>
<%@page import="com.wsndata.data.IrrigationArea"%>
<%@page import="com.wsndata.data.SeedSelect"%>
<%@page import="com.wsndata.data.PlantMethod"%>
<%@page import="com.wsndata.data.PrepareArea"%>
<%@page import="com.wsndata.data.PrepareManure"%>
<%@page import="com.wsndata.data.LandStatus"%>
<%@page import="com.wsndata.data.LandType"%>
<%@page import="com.wsndata.data.Bank"%>
<%@page import="com.wsndata.data.Coordinates"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/dcswc/css/CalendarStyle.css" />
<style type="text/css">
.detail-header-search ul { padding:0; padding-bottom:5px;padding-left: 4px; }
.detail-header-search ul li { float: left; }
#dynamicLandCheckHistory .detail-header-search ul li:first-child, 
#dynamicLandCheckHistory .topic-header-search ul li:first-child { margin-right: -15px; }
.objectivePlant { float: left; }
</style>

<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/function.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/menu.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/dcswc/dcswc.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/prototype.js"></script>  
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/jsdiv/jquery-1.10.2.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/jsdiv/jquery-ui.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/scripts.js"></script>  
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/superfish.js"></script>
<script language="JavaScript" charset="utf-8" src="<%=request.getContextPath()%>/js/plant.js"></script>
<script type="text/javascript">

   var topPos;
   var intBreedCnt = 0; 		   
   var arrayBreed = new Object();  
   var itemBreed = "";
   var itemBreed1 = "";  		
   var itemOverall = "";
   
   var intManureCnt = 0; 		   
   var arrayManure = new Object(); 
   var itemManure = "";

   var intChemCnt = 0; 			   
   var arrayChem = new Object();  
   var itemChemi = "";
  
   var intAssetCnt = 0;			   
   var arrayAsset = new Object();  
   var itemAsset = "";
  
   var intDetailCnt = 0;			
   var arrayDetail = new Object();  
   var itemDetail = "";

   var intCostCnt = 0;				
   var arrayCost = new Object();   
   var itemCost = "";
  
   var intSellingCnt = 0;			 
   var arraySelling = new Object(); 
   var itemSale = "";
  
   var intHarvestCnt = 0;			
   var arrayHarvest = new Object(); 
   var itemHarvest = "";
 
   var intFamilyCnt = 0; 		    
   var arrayFamily = new Object(); 
   var itemFamily = "";
   var familyArray = new Array();
   var familyIndex = 0;
   
   var intCheckCnt = 0;			   
   var arrayLandCheck = new Object();  
   var itemLandCheck = "";
   
   var totalAreaArray = new Array(); // key is typeId + docNo for plantDetail
   var docAreaArray = new Array();   // key is typeId + docNo for landRight
   
   var breedArray = new Array(); // Total 5 tabs 
   var breedDetailArray = new Array();
   var breedDetailIndex = 0;
    
   var breedArrayTab1 = new Array(); // Tab1
   var breedArrayTab2 = new Array(); // Tab2
   var breedArrayTab3 = new Array(); // Tab3
   var breedArrayTab4 = new Array(); // Tab4
   var breedArrayTab5 = new Array(); // Tab5
   var breedIndex = 0;
   var detailIndex = 0;
   
   var manureArray = new Array();
   var manureDetailArray = new Array(); 
   var manureIndex = 0;
   
   var chemArray = new Array();
   var chemDetailArray = new Array(); 
   var chemIndex = 0;
   
   var assetArray = new Array();
   var assetDetailArray = new Array(); 
   var assetIndex = 0;
 
   var docArray = new Array();
   var docDetailArray = new Array(); 
   var docDetailIndex = 0;
 
   var costArray = new Array();
   var costDetailArray = new Array(); 
   var costIndex = 0;
   
   var harvArray = new Array();
   var harvDetailArray = new Array(); 
   var harvestIndex = 0; 
   
   var saleDetailArray = new Array();
   var subSaleArray = new Array(); 
   var saleIndex = 0;
 
   var dropDownArray = new Array(); 
   var objProvinceNo = 0;
   var objProvNoEdit = 0;
   var objProvinceName = "";
   var objBranchCode = 0;
   
   var harvIdArr = new Object();
   var totalAmount=0;
   var isCheckSum = false;
   var breedTypeObjName = "";
   var breedTypeObjId = "";
   
   var breedGroupObjName = "";
   var globPlantArea = "";
   
   var dateHarvest;
   var sDateArray = new Object(); 
   
   var sumRemainSell = 0; // remainSell
   var sumCrop = 0; // totalSaleCrop
   var sumTotalHarvest = 0; // totalHarvest
   
    var sumConsumeProduct = 0; // ConsumeProduct
    var sumBreedProduct = 0; // BreedProduct
   
   
   var cropArray = new Array();
   var sumHarvArr = new Array() // total harvest summation
   var isEdit = "";
   // --- to get target and farmerGroupId from getProvince()
   var getTargetVal = 0;
   var getFarmerGroupId = 0;
   var globFarmerName = "";
   var globBreedGroupName = "";
   var globBreedTypeName = "";
   var globPlantDate = "";  
   
   var globAssetDate = "";
   var globAssetAmount = "";
  
  $(window).scroll( function() { 
 	var scrolled_val = $(document).scrollTop().valueOf();
 	topPos = scrolled_val;
  });
  $(function() {
    $("#datepicker").datepicker();
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

<% 
    response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	
	String LAND_CHECK_PAGE =  (String)request.getAttribute("LAND_CHECK_PAGE");
	String landCheckDocNo =  (String)request.getAttribute("landCheckDocNo");

    PlantForm plantForm = (PlantForm) request.getAttribute("PlantForm"); // form connect with jsp
    Plant plant	= (Plant) session.getAttribute("plant"); 
    Farmer farmer = (Farmer) session.getAttribute("plantFarmer");
	String PERIOD_PLANT = Utility.get("PERIOD_PLANT"); 
	String OBJECTIVE_PLANT = Utility.get("OBJECTIVE_PLANT");
	String AGRICULTURE_TYPE = Utility.get("AGRICULTURE_TYPE");
	String BREED_SOURCE = Utility.get("BREED_SOURCE");
    String WATER_SOURCE = Utility.get("WATER_SOURCE");
    String LAYER_TYPE2 = Utility.get("LAYER_TYPE2");
	String GAP = Utility.get("GAP"); // วิธีการปลูก
	String LANDCHECK_PERIOD = Utility.get("LANDCHECK_PERIOD"); // ระยะการตรวจ
	List provinceList = (ArrayList) session.getAttribute("plantProvinceList");
	List branchList = (ArrayList) session.getAttribute("plantBranchList");
	List breedGroupList = (ArrayList) session.getAttribute("breedGroupPlantList");
	List breedTypeList = (ArrayList) session.getAttribute("breedTypePlantList");
	String[] yearList = (String[]) session.getAttribute("pYearList");
	List farmerGroupList = (ArrayList) session.getAttribute("plantFarmerGroupList");
	List corpGroupList = (ArrayList) session.getAttribute("plantCorpGroupList");
	List prefixList = (ArrayList) session.getAttribute("plantPrefixList");
	List manureList = (ArrayList) session.getAttribute("plantManureList");
	List chemicalList = (ArrayList) session.getAttribute("plantChemicalList");
	List assetList = (ArrayList) session.getAttribute ("plantAssetList");
	List landrightList= (ArrayList) session.getAttribute ("plantLandrightList");
	List costList = (ArrayList) session.getAttribute("plantCostList");
	List irrList = (ArrayList) session.getAttribute("plantIrrList");
	List bankList = (ArrayList)session.getAttribute("plantBankList");
	List seedList = (ArrayList) session.getAttribute("plantSeedList"); // ข้อมูลการคัดเมล็ดพันธุ์
	List methodList = (ArrayList) session.getAttribute("plantMethodList"); // ข้อมูลวิธีการปลูก
	List buyerList = (ArrayList) session.getAttribute("plantBuyerList"); // รายชื่อผู้ชื้อ
	List prepareAreaList  =  (ArrayList)  session.getAttribute("plantPrepareAreaList"); // ข้อมูลการจัดเตรียมแปลง
	List prepareManureList = (ArrayList)  session.getAttribute("plantPrepareManureList"); // ข้อมูลการใส่ปุ๋ย
	List landStatusList  =  (ArrayList)  session.getAttribute("plantLandStatusList"); // ข้อมูลสภาพดิน
	List landTypeList = (ArrayList)  session.getAttribute("plantLandTypeList"); // ข้อมูลชนิดดิน
	
	String provinceName = "", msg = "", cmd="", breedGroupName="", status ="", soi="";
	String refPlantId="";
	if(plantForm != null){
		 cmd = plantForm.getCmd() == null?"":plantForm.getCmd();
		 msg = plantForm.getMsg() == null?"":plantForm.getMsg();
		 status = plantForm.getStatus() == null?"":plantForm.getStatus();
		 refPlantId = StringUtil.beString(plantForm.getRefPlantId());
	     provinceName = plantForm.getProvinceName()==null?"":plantForm.getProvinceName();
	     breedGroupName = plantForm.getBreedGroupId();
	}
%>
<script type="text/javascript" >	
function getSubPrepareAreaList(prepareId)
{        
        prepareId = prepareId.substring(prepareId.indexOf("_") + 1);
		var reqParameters = new Object();
		reqParameters.cmd = "GetPrepareArea";
		reqParameters.prepareAreaId = prepareId; 
		new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
		{	
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport)
			{	
			   var json = transport.responseText.evalJSON(); 
			   if(transport.responseText != ""){
				   if(json !=null && json != ""){
				        
						var totalRow = json.length;
						var perRow = totalRow/4;
						for(var x=0; x < perRow; x++){ 
						   document.getElementById("subPrepareArea").innerHTML += "<ul><li class='topic' style='width: 370px;padding-right: 5px;'></li>";
						   for(var i=4*x; i < 4*(x+1); i++){
						 		if(i < json.length){
									 document.getElementById("subPrepareArea").innerHTML += generatePrepareArea(json[i].prepareAreaId, json[i].prepareAreaName);   
								}
						   }
						   document.getElementById("subPrepareArea").innerHTML += "</ul>";
					    }
					}
				}else{
				    document.getElementById("subPrepareArea").innerHTML = "";
				}
				
			},
			onFailure: function() {	
			}
		});
}
function removePrepareArea(prepareId)
{        
        prepareId = prepareId.substring(prepareId.indexOf("_") + 1);
		var reqParameters = new Object();
		reqParameters.cmd = "GetPrepareArea";
		reqParameters.prepareAreaId = prepareId; 
		new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
		{	
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport){	
			   var json = transport.responseText.evalJSON(); 
			   if(json !=null && json != ""){
				   document.getElementById("subPrepareArea").innerHTML = ""; 
			   }
			},
			onFailure: function() {	
			}
		});
}
function checkBreed(objBreedType){
      <% if(plant != null){ %>
	    	if(document.plantForm.cmd.value == "Edit"){
		   		 if(breedArrayTab1.length > 0){
       			 	var bTypeId = "<%= plant.getBreedTypeId()%>";     
		     		alert("ไม่สามารถเปลี่ยนประเภทพืชได้  เนื่องจากยังมีข้อมูลอยู่ภายใต้" + breedTypeObjName);
		     		document.getElementById(objBreedType).value = bTypeId;
		     		return false;
		    	} else {
		    		return true;
		    	}
			} else {
				return true;
			}
	<%	} else { // plant is null %>
			if(itemBreed1 != "" || itemManure != "" || itemChemi != ""){
		     	alert("ไม่สามารถเปลี่ยนประเภทพืชได้  เนื่องจากยังมีข้อมูลอยู่ภายใต้" + breedTypeObjName);
		     	document.getElementById(objBreedType).value = breedTypeObjId;
		     	return false;
			} else {
				return true;
			}
	<% } %>
}
// finding plant period 21.07.2014 
function getBreedGroupPeriod(groupId){
		var reqParameters = new Object();
		reqParameters.cmd = "GetPeriod";
		reqParameters.breedGroupId = groupId; 
		new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
		{	
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport)
			{					
			    var json = transport.responseText.evalJSON(); 
				if(json !=null && json != ""){
					 document.plantForm.period.value = Number(json[0].period); // days
					 document.plantForm.breedGrpName.value = json[0].breedGroupName;
				}
			},
			onFailure: function() {	
			}
		});
}

function getBreedGroupInfo(objBreedType, objBreedGroup)
{
        var breedTypeDD = document.getElementById(objBreedType);
        var breedGroupDD = document.getElementById(objBreedGroup);
        
        if(breedTypeDD.options[breedTypeDD.selectedIndex] != undefined){
        	 breedTypeObjName = breedTypeDD.options[breedTypeDD.selectedIndex].text;
        }
        
        if(breedTypeObjName == "ข้าว"){
       		 document.getElementById("periodPlant").disabled = false;
        }else{
        	 document.getElementById("periodPlant").disabled = true;
        	 document.getElementById("periodPlant").value = 0;
        }
	    if(checkBreed(objBreedType) == true){
	      	breedTypeObjId = breedTypeDD.value;   
	      	
	      	if(breedTypeDD.options[breedTypeDD.selectedIndex] != undefined){  
          		breedTypeObjName = breedTypeDD.options[breedTypeDD.selectedIndex].text;
          
	            if(breedTypeDD.options[breedTypeDD.selectedIndex].value == ''){
	                  breedGroupDD.options.length = 1;
	                  breedGroupDD.options[0].value="";
	                  breedGroupDD.options[0].text="กรุณาเลือก";
	                  breedGroupDD.options[0].selected = true;
	            } else {
	                  var reqParameters = new Object();
	                  reqParameters.cmd = "GetBreedGroup";
	                  reqParameters.breedTypeId = breedTypeDD.options[breedTypeDD.selectedIndex].value;     
	                  new Ajax.Request("<%=request.getContextPath()%>/Plant.do", {     
	                        method: 'post',
	                        asynchronous: false,
	                        parameters: reqParameters,
	                        encoding: 'UTF-8',
	                        onSuccess: function(transport){                             
	                            var json = transport.responseText.evalJSON(); 
	                              if(json !=null && json != ""){      
	                                    breedGroupDD.options.length = json.length+1;
	                                    breedGroupDD.options[0].value="";
	                                    breedGroupDD.options[0].text="กรุณาเลือก";
	                                    breedGroupDD.options[0].selected = true;
	                                    var r = 0;
	                                    for(var i = 0; i < json.length; i++){     
	                                          r=r+1;                                          
	                                          breedGroupDD.options[r].value =json[i].breedGroupId;
	                                          breedGroupDD.options[r].text = json[i].breedGroupName;     
	                                    }
	                              } else {
	                              		alert("ไม่พบข้อมูลชนิดพันธุ์พืช");
	                                 	breedGroupDD.options.length = 1;
	                 				 	breedGroupDD.options[0].value="";
	                  					breedGroupDD.options[0].text="กรุณาเลือก";
	                  					breedGroupDD.options[0].selected = true;
	                              }
	                        },
	                        onFailure: function() { 
	                        }
	                  });
	            }
            ////
            } else {
            	 breedGroupDD.options.length = 1;
                 breedGroupDD.options[0].value="";
                 breedGroupDD.options[0].text="กรุณาเลือก";
                 breedGroupDD.options[0].selected = true;
            }
            
         }
}     
function checkIdCard(str){
	var RE = /^\d+$/;
	len = document.plantForm.idCard.value.length;
	    if(RE.test(str.value)){
	    	if(len != 13){
		    	alert("กรุณาใส่รหัสบัตรประจำตัวประชาชน 13 หลัก");
		    	$('#'+str.id).css('border-color', 'black'); // 22.07.2014
				setTimeout(function() {
     		 		document.getElementById(str.id).focus();
   				}, 0);
		    } else {
				    if(validateIdCard(str) == true){
				        $('#'+str.id).css('border-color', ''); // 22.07.2014
				    	var reqParameters = new Object();
						reqParameters.cmd = "GetFarmer";
						reqParameters.idCard = document.plantForm.idCard.value;
						new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
						{	
							method: 'post',
							asynchronous: false,
							parameters: reqParameters,
							encoding: 'UTF-8',
							onSuccess: function(transport){					
					    		var json = transport.responseText.evalJSON(); 
								if(json !=null && json != "")
								{
								    document.getElementById("firstName").value = json[0].firstName;
									document.getElementById("lastName").value = json[0].lastName;
									document.getElementById("addressNo").value = json[0].addressNo;
									if(json[0].moo == 0)
										json[0].moo = "";
									document.getElementById("moo").value = json[0].moo;
									
									if(json[0].provinceNo == 0)
										json[0].provinceNo ="";
									document.getElementById("provinceNo").value = json[0].provinceNo;
									getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', ''); 
									
									if(json[0].districtNo == 0)
										json[0].districtNo ="";
									document.getElementById("districtNo").value = json[0].districtNo;
									getSubDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', ''); 
									
									if(json[0].subDistrictNo == 0)
										json[0].subDistrictNo ="";
									document.getElementById("subDistrictNo").value = json[0].subDistrictNo;
									
									if(json[0].postCode == 0)
										json[0].postCode ="";
									document.getElementById("postCode").value = json[0].postCode;
									document.getElementById("tel").value = json[0].tel;
									document.getElementById("mobile").value = json[0].mobile;
									
							        if(json[0].abbrPrefix != "" && json[0].fullPrefix != ""){
			                            document.getElementById("abbrPrefix").options[0].value = json[0].abbrPrefix;
			                            document.getElementById("abbrPrefix").options[0].text = json[0].fullPrefix;
			                            document.getElementById("abbrPrefix").options[0].selected = true;
		                            }
								   
								    if(json[0].firstName.trim() != "")
										document.plantForm.firstName.readOnly = true;
								    else
								    	document.plantForm.firstName.readOnly = false;
								    	
								    if(json[0].lastName.trim() != "")
										document.plantForm.lastName.readOnly = true;
									else
								    	document.plantForm.lastName.readOnly = false;
								    	
									if(json[0].addressNo.trim() != "")
										document.plantForm.addressNo.readOnly = true;
									else
										document.plantForm.addressNo.readOnly = false;
										
									if(json[0].moo.trim() != "")
										document.plantForm.moo.readOnly = true;
									else
										document.plantForm.moo.readOnly = false;
									
									if(json[0].tel.trim() != "")
										document.plantForm.tel.readOnly = true;
									else
										document.plantForm.tel.readOnly = false;
										
									document.plantForm.postCode.readOnly = true;
								} 
							},
							onFailure: function() {	
								alert("failed");
							}
						});
					}
		    }
		}else{
			alert("กรุณาใส่ค่าที่เป็นตัวเลข");
			document.getElementById(str.id).value="";
		    // Reset all relate fields	
   			document.getElementById("firstName").value = "";
			document.getElementById("lastName").value = "";
			document.getElementById("addressNo").value = "";
			document.getElementById("moo").value = "";		
			document.getElementById("provinceNo").value = ""
			getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', ''); 
			document.getElementById("districtNo").value = "";
			getSubDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', ''); 
			document.getElementById("subDistrictNo").value = "";
			document.getElementById("postCode").value = "";
			document.getElementById("tel").value = "";
			document.getElementById("mobile").value = "";
			
			$('#'+str.id).css('border-color', 'black'); // 22.07.2014
			setTimeout(function() {
     		 	document.getElementById(str.id).focus();
   			}, 0);
		}
}
// retrieve bankBranch from bankId selected
function getBankBranchList(objBank, objBranch)
{ 
            var bankDD = document.getElementById(objBank);
            var branchDD = document.getElementById(objBranch);
            if(bankDD.options[bankDD.selectedIndex].value=="0"){
                  branchDD.options.length = 1;
                  branchDD.options[0].value="";
                  branchDD.options[0].text="กรุณาเลือก";
                  branchDD.options[0].selected = true;
            }else{
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetBankBranch";
                  reqParameters.bankId = bankDD.options[bankDD.selectedIndex].value;   
                  new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
                  {     
                        method: 'post',
                        asynchronous: false,
                        parameters: reqParameters,
                        encoding: 'UTF-8',
                        onSuccess: function(transport)
                        {                             
                            var json = transport.responseText.evalJSON(); 
                              if(json !=null && json != ""){
                                    branchDD.options.length = json.length+1;
                                    branchDD.options[0].value="";
                                    branchDD.options[0].text="กรุณาเลือก";
                                    branchDD.options[0].selected = true;
                                    var r = 0;
                                    for(var i = 0; i < json.length; i++){           
                                          r=r+1;                              
                                          branchDD.options[r].value=json[i].branchCode;
                                          branchDD.options[r].text=json[i].branchName;      
                                    }
                              }
                        },
                        onFailure: function() { 
                        }
                  });
            }
}
function getAddressInfo(objBuyer, objAddress, objProvName, objDistName, objSubDistName)
{ 
            var buyerDD = document.getElementById(objBuyer);
            var provinceDD = document.getElementById(objProvName);
            var districtDD = document.getElementById(objDistName);
			var subDistrictDD = document.getElementById(objSubDistName);            

            if(buyerDD.options[buyerDD.selectedIndex].value == 0)
			{
				  document.getElementById(objAddress).readOnly = false;
				  document.getElementById(objAddress).value = "";
                  provinceDD.options.length = 1;
 				  provinceDD.options[0].value="";
                  provinceDD.options[0].text="กรุณาเลือก";
                  provinceDD.options[0].selected = true;
				  document.getElementById("saleProvinceNo").disabled = false;								
				  districtDD.options[0].value="";
                  districtDD.options[0].text="กรุณาเลือก";
                  districtDD.options[0].selected = true;
				  document.getElementById("saleDistrictNo").disabled = false;	  
				  subDistrictDD.options[0].value="";
                  subDistrictDD.options[0].text="กรุณาเลือก";
                  subDistrictDD.options[0].selected = true;
				  document.getElementById("saleSubDistrictNo").disabled = false;
            } else {
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetBuyerAddress";
                  reqParameters.buyerId = buyerDD.options[buyerDD.selectedIndex].value;   
                  new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
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
									document.getElementById(objBuyer).value = json[0].buyerId;
									document.getElementById(objAddress).value = json[0].buyerAddress;
									document.getElementById(objAddress).readOnly = true;                                   

									provinceDD.options[0].value=json[0].provinceNo;
                                    provinceDD.options[0].text=json[0].provinceName;
                                    provinceDD.options[0].selected = true;							
									document.getElementById(objProvName).disabled = true;	
									districtDD.options[0].value=json[0].districtNo;
                                    districtDD.options[0].text=json[0].districtName;
                                    districtDD.options[0].selected = true;
									document.getElementById(objDistName).disabled = true;	
									subDistrictDD.options[0].value=json[0].subDistrictNo;
                                    subDistrictDD.options[0].text=json[0].subDistrictName;
                                    subDistrictDD.options[0].selected = true;
									document.getElementById(objSubDistName).disabled = true;
                              }
                        },
                        onFailure: function() { 
                        }
                  });
            }
}
function getDistrictInfo(objPName, objDName, objSName, objPostCode)
{ 
           if(objPostCode != ''){
                var postCodeTxt = document.getElementById(objPostCode);
                postCodeTxt.value = "";
           }
           var subDistrictDD = document.getElementById(objSName);
           subDistrictDD.options.length=1;
           subDistrictDD.options[0].value="";
           subDistrictDD.options[0].text="กรุณาเลือก";
           subDistrictDD.options[0].selected = true;
           var provinceDD = document.getElementById(objPName);
           var districtDD = document.getElementById(objDName);
           
           if(provinceDD.options[provinceDD.selectedIndex].value==''){
                  districtDD.options.length = 1;
                  districtDD.options[0].value="";
                  districtDD.options[0].text="กรุณาเลือก";
                  districtDD.options[0].selected = true;
           } else{
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetDistrict";
                  reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;   
                  new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
                  {     
                        method: 'post',
                        asynchronous: false,
                        parameters: reqParameters,
                        encoding: 'UTF-8',
                        onSuccess: function(transport)
                        {                             
                            var json = transport.responseText.evalJSON(); 
                              if(json !=null && json != ""){
                                    districtDD.options.length = json.length+1;
                                    districtDD.options[0].value="";
                                    districtDD.options[0].text="กรุณาเลือก";
                                    districtDD.options[0].selected = true;
                                    var r = 0;
                                    for(var i = 0; i < json.length; i++){           
                                          r=r+1;                              
                                          districtDD.options[r].value=json[i].districtNo;
                                          districtDD.options[r].text=json[i].districtThai;      
                                    }
                              }
                        },
                        onFailure: function() { 
                        }
                  });
            }
}
function getSubDistrictInfo(objPName, objDName, objSName, objPostCode)
{
            if(objPostCode != ''){
                  var postCodeTxt = document.getElementById(objPostCode);
                  postCodeTxt.value = "";
            }
            var provinceDD = document.getElementById(objPName);
            var districtDD = document.getElementById(objDName);
            var subDistrictDD = document.getElementById(objSName);
            
            if(districtDD.options[districtDD.selectedIndex].value == ''){
                  subDistrictDD.options.length = 1;
                  subDistrictDD.options[0].value="";
                  subDistrictDD.options[0].text="กรุณาเลือก";
                  subDistrictDD.options[0].selected = true;
            }else{
                  var reqParameters = new Object();
                  reqParameters.cmd = "GetSubDistrict";
                  reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
                  reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;     
                  new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
                  {     
                        method: 'post',
                        asynchronous: false,
                        parameters: reqParameters,
                        encoding: 'UTF-8',
                        onSuccess: function(transport)
                        {                             
                            var json = transport.responseText.evalJSON(); 
                              if(json !=null && json != ""){
                                    
                                    subDistrictDD.options.length = json.length+1;
                                    subDistrictDD.options[0].value="";
                                    subDistrictDD.options[0].text="กรุณาเลือก";
                                    subDistrictDD.options[0].selected = true;
                                    var r = 0;
                                    for(var i = 0; i < json.length; i++){     
                                          r=r+1;                                          
                                          subDistrictDD.options[r].value=json[i].subDistrictNo;
                                          subDistrictDD.options[r].text=json[i].subDistrictThai; 
                                    }
                              }
                        },
                        onFailure: function() { 
                        }
                  });
            }
}     
function getPostCodeInfo(objPName, objDName, objSName, objPostCode)
{
	    var postCodeTxt = document.getElementById(objPostCode);
		var provinceDD = document.getElementById(objPName);
		var districtDD = document.getElementById(objDName);
		var subDistrictDD = document.getElementById(objSName);
		var reqParameters = new Object();
		reqParameters.cmd = "GetPostCode";
		reqParameters.districtNo = districtDD.options[districtDD.selectedIndex].value;
		reqParameters.provinceNo = provinceDD.options[provinceDD.selectedIndex].value;     
		reqParameters.subDistrictNo = subDistrictDD.options[subDistrictDD.selectedIndex].value;   
		new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
		{	
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport)
			{					
			    var json = transport.responseText.evalJSON(); 
				if(json !=null && json != ""){
					postCodeTxt.value = json[0].postCode;
				}
			},
			onFailure: function() {	}
		});
}	
function getProvince(objBranch, objProvName)
{
		var branchDD = document.getElementById(objBranch);
		var provinceTxt = document.getElementById(objProvName);
		var reqParameters = new Object();
		reqParameters.cmd = "GetProvince";
		
		reqParameters.branchCode = branchDD.options[branchDD.selectedIndex].value; 
		if(reqParameters.branchCode == 0){
		  	 document.getElementById(objProvName).value = "";
		  	 return false;
		}
		new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
		{	
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport){					
			    var json = transport.responseText.evalJSON(); 
				if(json !=null && json != "")
				{
					  document.getElementById(objProvName).value = json[0].thaiName;
					  objProvinceName = json[0].thaiName;
					  objProvinceNo = json[0].provinceNo;
					  objBranchCode = branchDD.value;
					
				} else {
				    document.getElementById(objProvName).value = "";
				}
			},
			onFailure: function() {	}
		});
}	

function getFarmerGroupChild(objFarmerGroup, objChildGroup, objChildName){
		var farmerGroupDD = document.getElementById(objFarmerGroup);
		var childGroupTxt = document.getElementById(objChildGroup);
		var childNameTxt = document.getElementById(objChildName);
		var reqParameters = new Object();
		reqParameters.cmd = "GetFarmerGroupChild";
		reqParameters.farmerGroupId = farmerGroupDD.options[farmerGroupDD.selectedIndex].value; 
		new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
		{	
			method: 'post',
			asynchronous: false,
			parameters: reqParameters,
			encoding: 'UTF-8',
			onSuccess: function(transport){					
			    var json = transport.responseText.evalJSON(); 
				if(json !=null && json != ""){
				       childNameTxt.value = json[0].farmerGroupName;
				       childGroupTxt.value = json[0].farmerGroupId;
				}else{
				 	  childNameTxt.value = "";
				      childGroupTxt.value = "";
				}
			},
			onFailure: function() {	}
		});
}

// branchCode selected affect
function getFarmerGroupFromBranch(objBranch, objFarmerGroup, objTarget){
		 if(objTarget != ''){
              var targetTxt = document.getElementById(objTarget);
              targetTxt.value = "";
         }
	         var branchDD = document.getElementById(objBranch);
	         var FarmerGroupDD = document.getElementById(objFarmerGroup);
	         FarmerGroupDD.options.length = 1;
	         FarmerGroupDD.options[0].value="0";
	         FarmerGroupDD.options[0].text="กรุณาเลือก";
	         FarmerGroupDD.options[0].selected = true;
			 var reqParameters = new Object();
        	 reqParameters.cmd = "GetFarmerGroupFromBranch";
        	 reqParameters.branchCode = branchDD.value;
        	 new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
        	 {     
             	method: 'post',
             	asynchronous: false,
              	parameters: reqParameters,
              	encoding: 'UTF-8',
              	onSuccess: function(transport){   
              	if(transport.responseText == ""){
              		getFarmerGroupId = 0;
              		getTargetVal = "";
              	}          
                var json = transport.responseText.evalJSON();
                if(json !=null && json != ""){     
                      FarmerGroupDD.options.length = json.length+1;
                      FarmerGroupDD.options[0].value="0";
                      FarmerGroupDD.options[0].text="กรุณาเลือก";
                      FarmerGroupDD.options[0].selected = true;
                      var r = 0;
                      for(var i = 0; i < json.length; i++){           
                          r=r+1;                              
                          FarmerGroupDD.options[r].value=json[i].farmerGroupId;
                          FarmerGroupDD.options[r].text=json[i].farmerGroupName;      
                      }
                 }
             },
              onFailure: function() {}
        });
}
function getTargetInfo(objBranch, objGroupId, objTarget){		
	    var targetText = document.getElementById(objTarget);
	    var branchDD = document.getElementById(objBranch);
		var farmerGroupDD = document.getElementById(objGroupId);
		var reqParameters = new Object();
		reqParameters.cmd = "GetTargetInfo";
		reqParameters.branchCode = branchDD.options[branchDD.selectedIndex].value; 
		reqParameters.farmerGroupId = farmerGroupDD.options[farmerGroupDD.selectedIndex].value; 
		if(reqParameters.farmerGroupId == ""){
		  	document.getElementById(objTarget).value = "";
		    targetText.disabled = true;
			return false;  
		}
		new Ajax.Request("<%=request.getContextPath()%>/Plant.do",
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
					   targetText.value = json[0].target;
					   targetText.disabled = false;
				}else{
				   targetText.value = "";
				   targetText.disabled = true;
				}
			},
			onFailure: function() {	}
		});
}		
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/EnableDisableImage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/BlinkUpDownImage.js"></script>
<script  type="text/javascript" >
  var subJob;
  var subJob1;
  var subJob2;
  var subJob3;
  var subJob4;
  var subJob5;
  var subJob6;
function initPageLoad(fromCheckPlantPage){	
	
	$("ul#detail_containTab > li:visible").hide();	
	if(fromCheckPlantPage == 'Y'){	
		$("ul#detail_containTab > li").eq(0).hide();
		$("ul#detail_containTab > li").eq(1).hide();
		$("ul#detail_containTab > li").eq(2).hide();
		$("ul#detail_containTab > li").eq(3).show();
	}else{
		$("ul#detail_containTab > li").eq(0).show();
		$("ul#detail_containTab > li").eq(1).hide();
		$("ul#detail_containTab > li").eq(2).hide();
		$("ul#detail_containTab > li").eq(3).hide();
	}
	
	document.getElementById("CalendarPopup").style.zIndex = "500";
	document.plantForm.refPlantId.value = "<%= refPlantId %>";
	<%if(plant !=null){
		if(plant.getPlantId() >0){ %>
			document.getElementById('idCard').readOnly = true;
		<%}%>
	<%} if("New".equals(cmd)){ %>
		document.getElementById("farmerGroupMember").checked = true;
		isMember(document.getElementById("farmerGroupMember"));
	<%} else if("Add".equals(cmd) || "Fail".equals(cmd)){ %>
	 		alert("<%= msg %>");
	 		document.plantForm.cmd.value = "<%= cmd %>";
	  		document.plantForm.plantId.value = "<%= plantForm.getPlantId() %>";
			document.plantForm.breedTypeId.value = "<%= plantForm.getBreedTypeId()%>";
			document.plantForm.plantYear.value  = "<%= plantForm.getPlantYear()%>";
			document.plantForm.plantNo.value  = "<%= plantForm.getPlantNo()%>";
			getFarmerGroupId = "<%= plantForm.getFarmerGroupId()%>";
			getTargetVal = "<%= plantForm.getTarget()==null?"":plantForm.getTarget() %>";
		    document.plantForm.farmerGroupId.value = "<%= plantForm.getFarmerGroupId() %>";
		    if(document.plantForm.farmerGroupId.value > 0){
		    	 document.getElementById("farmerGroupMember").checked = true;
		    	 isMember(document.getElementById("farmerGroupMember"));
		    	 document.plantForm.target.value = "<%= plantForm.getTarget()==null?"":plantForm.getTarget() %>";
		    	 document.plantForm.customerFarmerGroupNo.value = "<%= plantForm.getCustomerFarmerGroupNo() %>";
		   		 getFarmerGroupChild('farmerGroupId','childFarmerGroupId','childFarmerGroupName');
		    } else {
		    	 document.getElementById("notMember").checked = true;
		    	 document.plantForm.farmerGroupMember.checked = false;
		    	 document.plantForm.customerFarmerGroupNo.value = "";
		    	 document.plantForm.target.value = "";
		    	 document.plantForm.farmerGroupId.value = 0;
		   		 isMember(document.getElementById("notMember"));
		    }
			document.plantForm.idCard.value = "<%= plantForm.getIdCard() %>";
    		document.plantForm.firstName.value  = "<%= plantForm.getFirstName() %>";
		 	document.plantForm.lastName.value = "<%= plantForm.getLastName() %>";
		    document.plantForm.abbrPrefix.value = "<%= plantForm.getAbbrPrefix()%>";
		    document.plantForm.email.value = "<%= plantForm.getEmail() %>";
		    document.plantForm.addressNo.value = "<%= plantForm.getAddressNo()%>";
		    document.plantForm.moo.value = "<%= plantForm.getMoo()%>";
		    document.plantForm.tel.value = "<%= plantForm.getTel()%>";
		    document.plantForm.soi.value ="<%= plantForm.getSoi()%>";
		    document.plantForm.street.value =  "<%= plantForm.getStreet()%>";
		    document.plantForm.village.value = "<%= plantForm.getVillage()%>";
		    document.plantForm.mobile.value = "<%= plantForm.getMobile()%>";
		    document.plantForm.provinceNo.value = "<%= plantForm.getProvinceNo()== null?"":plantForm.getProvinceNo()%>";
		    getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');
		    document.plantForm.districtNo.value = "<%= plantForm.getDistrictNo()== null?"":plantForm.getDistrictNo()%>";
		    getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
		    document.plantForm.subDistrictNo.value = "<%= plantForm.getSubDistrictNo()== null?"":plantForm.getSubDistrictNo()%>";
		    document.plantForm.postCode.value = "<%= plantForm.getPostCode() == null?"":plantForm.getPostCode()%>";
		    getBreedGroupInfo('breedTypeId', 'breedGroupId');
		 
		      <% if("1".equals(plantForm.getFta())){%>
		          document.getElementById("fta").checked = true;
		    	  document.getElementById("noneFta").checked = false;
		      <% } else { %>
		     	  document.getElementById("fta").checked = false;
		     	  document.getElementById("noneFta").checked = true;
		      <% } %>
		    
		    if(breedTypeObjName == "ข้าว"){
		   		 document.plantForm.periodPlant.value = "<%= plantForm.getPeriodPlant() %>";
		   		 document.getElementById("periodPlant").disabled = false;
		    }else{
		   		 document.plantForm.periodPlant.value = 0;
		   		 document.getElementById("periodPlant").disabled = true;
		    }
		    document.plantForm.projectDate.value = "<%= plantForm.getProjectDate() %>";
		    document.plantForm.bankId.value = "<%= plantForm.getBankId() %>";
		    getBankBranchList('bankId','branchCode');
		    
		     <% if("0".equals(plantForm.getBranchCode())){%>
		   		 document.plantForm.branchCode.value  = "";
		     <% } else { %>
		      	 document.plantForm.branchCode.value  = "<%= plantForm.getBranchCode()%>";
		     <% } %>
		      	
		    <% if(plantForm.getLandrightItem() != null){ %>
		    	  loadLandTable("<%= plantForm.getLandrightItem() %>");
		    <% } %>
		    
		    <% if(plantForm.getDetailItem() != null){ %>
		    	  loadBreedTable("<%= plantForm.getDetailItem() %>");
		    <% } %>
		    
		    <%  if(plantForm.getAssetItem() != null && !"".equals(plantForm.getAssetItem())){ %>
		    		itemAsset = "<%= plantForm.getAssetItem() %>";
		    		document.getElementById("dynamicPlantAsset").innerHTML = generateAssetTable(itemAsset);
		    		document.plantForm.assetAmount.value = "<%= plantForm.getAssetDetailAmount() %>";
		    		document.plantForm.dateAsset.value = "<%= plantForm.getAssetDetailDate() %>";
		    		loadAssetDetail();
			 <% }else{ %>
			        <% if(!"".equals(plantForm.getAssetDetailDate()) && plantForm.getAssetDetailDate() != null && !"".equals(plantForm.getAssetDetailAmount()) && plantForm.getAssetDetailAmount() != null){ %>
							globAssetDate =  "<%= plantForm.getAssetDetailDate() %>";
							globAssetAmount = formatNumber("<%= plantForm.getAssetDetailAmount() %>");
					<% } %>
				    document.getElementById("dynamicPlantAsset").innerHTML = generateAssetTable("");  
		     <% } %>
		    
		    <%  if(plantForm.getFamilyItem() != null){ %>
		    	 itemFamily = "<%= plantForm.getFamilyItem() %>";
		    	 loadFarmerFamily();
		    	 document.getElementById("dynamicFamily").innerHTML = generateFamilyTable(itemFamily);  
		    <% } %>
		      <% if(plantForm.getLandCheckItem() != null){ %>
		    	 itemLandCheck = "<%= plantForm.getLandCheckItem() %>";
		    <% } %>
            
            <% if(plantForm.getRegister() != null) {
            if("1".equals(plantForm.getRegister())){%>
                document.getElementById("register1").checked = true;
                document.getElementById("register2").checked = false;
            <% } else if("2".equals(plantForm.getRegister())) { %>
                document.getElementById("register1").checked = false;
                document.getElementById("register2").checked = true;
            <% } } %>
            <% if(plantForm.getMemberType() != null) {
            if("N".equals(plantForm.getMemberType())){%>
                document.getElementById("memberType1").checked = true;
                document.getElementById("memberType2").checked = false;
            <% } else if("S".equals(plantForm.getMemberType())) { %>
                document.getElementById("memberType1").checked = false;
                document.getElementById("memberType2").checked = true;
            <% } } %>
		    document.plantForm.income.value = "<%= plantForm.getIncome()%>";
	<%  } else if("Close".equals(cmd)) { %>
			alert("<%= msg %>");
			cancelPlant();
	<% } else { 
         if(plant !=null && farmer !=null) { %>
	        // Edit mode
	        
	        document.plantForm.cmd.value = "<%= plantForm.getCmd()%>";
	        document.plantForm.plantId.value = "<%= plant.getPlantId() %>";
			document.plantForm.breedTypeId.value = "<%= plant.getBreedTypeId()%>";
			document.plantForm.plantYear.value  = "<%= plant.getPlantYear()%>";
			document.plantForm.plantNo.value  = "<%= plant.getPlantNo()%>";
			getFarmerGroupId = "<%= plant.getFarmerGroupId() == null?"0":plant.getFarmerGroupId().longValue()%>";
			getTargetVal = "<%= plant.getTarget() %>";
			document.plantForm.farmerGroupId.value = "<%= plant.getFarmerGroupId() %>";
		    if(document.plantForm.farmerGroupId.value > 0) {
		    		document.getElementById("farmerGroupMember").checked = true;
		    		isMember(document.getElementById("farmerGroupMember"));
		    		document.plantForm.target.value = "<%= plant.getTarget()%>";
		     		document.plantForm.customerFarmerGroupNo.value = "<%= plant.getMemberNo()==null?"":plant.getMemberNo() %>";
		 		  	getFarmerGroupChild('farmerGroupId','childFarmerGroupId','childFarmerGroupName');
		    }  else {
		    		document.getElementById("notMember").checked = true;
		    		document.plantForm.farmerGroupMember.checked = false;
		    		document.plantForm.customerFarmerGroupNo.value = "";
		    		document.plantForm.target.value = "";
		    		document.plantForm.farmerGroupId.value = 0;
		   			isMember(document.getElementById("notMember"));
		    }
		    document.plantForm.accountName.value = "<%= plant.getAccountName() %>";
		    document.plantForm.accountNo.value = "<%= plant.getAccountNo() %>";
		    document.plantForm.certificateFarmerNo.value = "<%= plant.getCertificate() %>";
		    document.plantForm.registrationNo.value = "<%= plant.getRegisterNo() %>";
		    document.plantForm.bookNo.value = "<%= plant.getBookNo()==0?"":plant.getBookNo() %>";
		    document.plantForm.documentNo.value = "<%= plant.getDocumentNo()==null?"":plant.getDocumentNo() %>";
		    document.plantForm.officerName.value = "<%= plant.getRegisterBy() %>";
			document.plantForm.idCard.value = "<%= plant.getIdCard() %>";
    		document.plantForm.firstName.value  = "<%= farmer.getFirstName() %>";
		 	document.plantForm.lastName.value = "<%= farmer.getLastName() %>";
		    document.plantForm.abbrPrefix.value = "<%= farmer.getAbbrPrefix()%>";
		    document.plantForm.addressNo.value = "<%= farmer.getAddressNo() == null?"":farmer.getAddressNo()%>";
		    document.plantForm.moo.value = "<%= farmer.getMoo()==0?"":farmer.getMoo()%>";
		    document.plantForm.soi.value ="<%= farmer.getSoi()==null?"":farmer.getSoi()%>";
		    document.plantForm.street.value =  "<%= farmer.getStreet()==null?"":farmer.getStreet()%>";
		    document.plantForm.tel.value = "<%= farmer.getTel()==null?"":farmer.getTel()%>";
		    document.plantForm.mobile.value = "<%= farmer.getMobile()== null?"":farmer.getMobile() %>";
		    document.plantForm.email.value = "<%= farmer.getEmail()== null?"":farmer.getEmail() %>";
		    document.plantForm.provinceNo.value = "<%= farmer.getProvinceNo()==0?"":farmer.getProvinceNo()%>";
		    getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');
		    document.plantForm.districtNo.value = "<%= farmer.getDistrictNo()==0?"":farmer.getDistrictNo()%>";
		    getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode');
		    document.plantForm.subDistrictNo.value = "<%= farmer.getSubDistrictNo()==0?"":farmer.getSubDistrictNo()%>";
		    document.plantForm.postCode.value = "<%= farmer.getPostCode() == 0?"":farmer.getPostCode()%>";
		    document.plantForm.village.value = "<%= farmer.getVillage()==null ? "" : farmer.getVillage()%>";
		    getBreedGroupInfo('breedTypeId', 'breedGroupId'); 
		     if(breedTypeObjName == "ข้าว"){
		   		 document.plantForm.periodPlant.value = "<%= plant.getSeason() %>";
		   		 document.getElementById("periodPlant").disabled = false;
		    }else{
		   		 document.plantForm.periodPlant.value = 0;
		   		 document.getElementById("periodPlant").disabled = true;
		    }
		    
		    <% if("1".equals(plant.getFta())){%>
		          document.getElementById("fta").checked = true;
		    	  document.getElementById("noneFta").checked = false;
		      <% } else { %>
		     	  document.getElementById("fta").checked = false;
		     	  document.getElementById("noneFta").checked = true;
		      <% } %>
		    
		    <% if(plant.getRegisterDate()!= null ){ 
		    		String pDate = "";
		    		pDate = Utility.getShortThDate(plant.getRegisterDate());
		    %>
		   	   document.plantForm.projectDate.value = '<%=pDate%>';
		    <% } %>
		    
		    document.plantForm.bankId.value = "<%= plant.getBankId()==null?0:plant.getBankId() %>";
		    getBankBranchList('bankId','branchCode');
		    document.plantForm.branchCode.value  = "<%= plant.getBranchCode()==0?"":plant.getBranchCode()%>";
		    
		     <% if(plant.getLandrightItem() != null){ %>
		    	  loadLandTable("<%=  plant.getLandrightItem() %>");
		    <% } %>
		    
		    <% if(plant.getDetailItem() != null){ %>
		    	  loadBreedTable("<%= plant.getDetailItem() %>");
		    <% } %>
		    
		     <%  if(plant.getAssetItem() != null && !"".equals(plant.getAssetItem())){ %>
		    		itemAsset = "<%= plant.getAssetItem() %>";
		    		document.getElementById("dynamicPlantAsset").innerHTML = generateAssetTable(itemAsset);
		    		document.plantForm.assetAmount.value = "<%= plant.getAssetAmount() %>";
					loadAssetDetail();
			 <% }else{ %>
			       <% if(!"".equals(plant.getAssetDate()) && plant.getAssetDate() != null && !"".equals(plant.getAssetAmount()) && plant.getAssetAmount() != null){%>
		    			globAssetDate =  "<%= plant.getAssetDate() %>";
				    	globAssetAmount = formatNumber("<%= plant.getAssetAmount()%>");
		    	   <% } %>
					 document.getElementById("dynamicPlantAsset").innerHTML = generateAssetTable("");
			 <% } %>
			      
		    <% if(plant.getFamilyItem() != null){ %>
		    	 itemFamily = "<%= plant.getFamilyItem() %>";
		    	 loadFarmerFamily();
		    	 document.getElementById("dynamicFamily").innerHTML = generateFamilyTable(itemFamily);  
		    <% } %>
		    
		    <% if(plant.getLandCheckItem() != null){ %>
		    	 itemLandCheck = "<%= plant.getLandCheckItem() %>";
		    	// loadLandCheck();
		     <% } %>
            
            <% if("1".equals(plant.getRegister())){%>
                document.getElementById("register1").checked = true;
                document.getElementById("register2").checked = false;
            <% } else { %>
                document.getElementById("register1").checked = false;
                document.getElementById("register2").checked = true;
            <% } %>
            <% if("N".equals(plant.getMemberType())){%>
                document.getElementById("memberType1").checked = true;
                document.getElementById("memberType2").checked = false;
            <% } else if("S".equals(plant.getMemberType())) { %>
                document.getElementById("memberType1").checked = false;
                document.getElementById("memberType2").checked = true;
            <% } %>
		    document.plantForm.income.value = "<%= plant.getIncome()%>";
		    checkNumberComma(document.plantForm.income);
	<%  }else{  // delete mode 
	         if("Delete".equals(cmd)){
	%>
	 			alert("<%= msg %>");
	 			document.plantForm.plantId.value = "";
				document.plantForm.breedTypeId.value = "";
				document.plantForm.plantYear.value  = "";
				document.plantForm.plantNo.value  = "";
				document.plantForm.branchCode.value  = "";
		    	document.plantForm.target.value = "";
		    	document.plantForm.farmerGroupId.value = "";
				document.plantForm.idCard.value = "";
    			document.plantForm.firstName.value  = "";
		 		document.plantForm.lastName.value = "";
		    	document.plantForm.abbrPrefix.value = "";
		    	document.plantForm.addressNo.value = "";
		    	document.plantForm.moo.value = "";
		   	 	document.plantForm.tel.value = "";
		    	document.plantForm.provinceNo.value = "";
		    	document.plantForm.districtNo.value = "";
		    	document.plantForm.subDistrictNo.value = "";
		    	document.plantForm.postCode.value = "";
		    	document.plantForm.income.value = "";
	<%	   }else{	%>		
			 	getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode'); // just added on 08.08.2014
			 	getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode'); // just added on 08.08.2014	     	
	<%		
			}
		}
	} %>
	if(objProvinceNo != '' && objProvinceNo > 0){
		document.getElementById("docProvinceNo").value = objProvinceNo;
	}
	getDistrictInfo('docProvinceNo', 'docDistrictNo', 'docSubDistrictNo', ''); 
    getSubDistrictInfo('docProvinceNo','docDistrictNo','docSubDistrictNo', ''); 
	getDistrictInfo('saleProvinceNo', 'saleDistrictNo', 'saleSubDistrictNo', ''); 
	getSubDistrictInfo('saleProvinceNo', 'saleDistrictNo', 'saleSubDistrictNo', '');
	var plantFarmerName = document.plantForm.abbrPrefix.value + " " + document.plantForm.firstName.value + "   " + document.plantForm.lastName.value;
	document.getElementById("lblFarmerName").innerHTML =  plantFarmerName;
	document.getElementById("lblBreedFarmerName").innerHTML = plantFarmerName;
	document.getElementById("lblTabBreed5_1").innerHTML = plantFarmerName;
	document.getElementById("lblAssetFarmerName").innerHTML = plantFarmerName;
	loadMenu("M17");

	if(fromCheckPlantPage == 'Y'){		
		var rIndex = "";
		var rBreedGroupId = "";
		var rBreedGroupName = "";
		var rDocNo = "";
		var landCheckDocNo = "'"+<%=landCheckDocNo%>+"'";
		
		for(var i=1; i< breedArrayTab1.length; i++){ 
			rDocNo = breedArrayTab1[i].split(",")[3];
		   	if(rDocNo == landCheckDocNo){
		   		rIndex = breedArrayTab1[i].split(",")[0];
		   		rBreedGroupId = breedArrayTab1[i].split(",")[7];
		   		rBreedGroupName = breedArrayTab1[i].split(",")[8];
		   		break;
		   	}
	   	}
	   	
		showSaleWindow(rIndex,rBreedGroupId,rBreedGroupName);
	}
}
function getDistrict(provinceNo){
	document.plantForm.cmd.value = "GetDistrict";
	document.plantForm.action = "<%=request.getContextPath()%>/Plant.do";
	document.plantForm.method = "Post";
	document.forms["plantForm"].submit();
}
function getSubDistrict(districtNo){
	document.plantForm.cmd.value = "GetSubDistrict";
	document.plantForm.action = "<%=request.getContextPath()%>/Plant.do";
	document.plantForm.method = "Post";
	document.forms["plantForm"].submit();
}
function getSubObjectivePlant() {
    var text = "";
    text += '<ul><li class="topic" style="width: 324px;">&nbsp;</li>';
    <% String[] layer = LAYER_TYPE2.split(",");
    for(int j=0; j<layer.length; j++) { %>
        text += '<li class="topic" style="width: 10px;padding-right: 1px;">&nbsp;</li><li class="boxinput"style="width: 29px;padding-right: 0px;">';
        text += '<input type="radio" id="subObjective_<%= j+1 %>" name="subObjective" value="<%= layer[j] %>" style="width:15px;" />';
        text += '</li><li class="topic" style="width: 5px;padding-right: 2px;"></li><li class="topic"style="width: 110px;text-align: left;">';
        text += '<font style="font-size: 20px;"><%= layer[j] %></font></li>';
   <% } %>
   text += '</ul>';
    return text;
}
function cancelPlant(){
	 document.plantListForm.cmd.value = "Search";
	 document.plantListForm.breedTypeId.value = 0;
	 document.plantListForm.plantYear.value = 0;
     document.plantListForm.plantNo.value="";
	 document.plantListForm.branchCode.value=0;
	 document.plantListForm.firstName.value="";
	 document.plantListForm.lastName.value="";
	 document.plantListForm.action = "<%=request.getContextPath()%>/PlantList.do";
	 document.plantListForm.method = "Post";
	 document.forms["plantListForm"].submit();	
}
function savePlant() 
{	
     if(breedArray != undefined && breedArray != "" && breedArray != null){	
    	 for(var i = 1; i < breedArray.length; i++)
    	 {
    	    if(breedArray[i] != undefined && breedArray[i] != "")
    	    {
	   	        if(i == 1) {
		       		itemOverall = breedArray[i] + "#" +  costArray[i] + "#" + harvArray[i] + "#" +  saleDetailArray[i];
		    	} else {
		      	    itemOverall  += "$" + breedArray[i] + "#" +  costArray[i] + "#" + harvArray[i] + "#" +  saleDetailArray[i];
				}	
			}
		}
     }
    
	if(document.getElementById("idCard").value == ''){
	 	alert("กรุณาใส่เลขที่บัตรประชาชน ");
	 	document.getElementById("idCard").focus();
	 	return false;
	} else {
		if(document.getElementById("idCard").value.length != 13){
 	    	alert("กรุณาใส่รหัสบัตรประจำตัวประชาชน 13 หลัก");  
 	    	document.getElementById("idCard").focus();
 	 		return false;
 		 } 
	}
	if(document.getElementById("email").value != ''){
	     if(checkEmailFormat(document.getElementById("email")) == false)
	       return false;
	}
	
	if(document.getElementById("farmerGroupMember").checked == true){
			if(document.getElementById("farmerGroupId").value == 0){
				alert("กรุณาเลือกกลุ่มเกษตรกร");
				return false;
			} 
	} 
	if(document.getElementById("firstName").value == ''){
	 	alert("กรุณาใส่ชื่อ");
	 	document.getElementById("firstName").focus();
	 	return false;
	} 
	if(document.getElementById("lastName").value == ''){
	 	alert("กรุณาใส่นามสกุล");
	 	document.getElementById("lastName").focus();
	 	return false;
	} 
	if(document.getElementById("addressNo").value == ''){
		alert("กรุณาใส่บ้านเลขที่");
	 	document.getElementById("addressNo").focus();
	 	return false;
	} 
	if(document.getElementById("provinceNo").value == ''){
		alert("กรุณาใส่จังหวัด");
	 	document.getElementById("provinceNo").focus();
	 	return false;
	} 
	if(document.getElementById("districtNo").value == ''){
		alert("กรุณาใส่อำเภอ");
	 	document.getElementById("districtNo").focus();
	 	return false;
	} 
	if(document.getElementById("subDistrictNo").value == ''){
		alert("กรุณาใส่ตำบล");
	 	document.getElementById("subDistrictNo").focus();
	 	return false;
	}
	if(document.getElementById("breedTypeId").value == 0){
	 	alert("กรุณาเลือกข้อมูลพืชที่เพาะปลูก");
	 	document.getElementById("breedTypeId").focus();
	 	return false;
	} 
	if(document.getElementById("plantNo").value == ''){
	 	alert("กรุณาใส่ครั้งที่");
	 	document.getElementById("plantNo").focus();
	 	return false;	
	} 
	if(document.getElementById("registrationNo").value == ''){
	 	alert("กรุณากรอกเลขที่ทะเบียน ");
	 	document.getElementById("registrationNo").focus();
	 	return false;
	} 
	if(document.getElementById("projectDate").value == ''){
	 	alert("กรุณากรอกวันที่เข้าร่วมโครงการ");
	 	document.getElementById("projectDate").focus();
	 	return false;
	} 
	if(document.getElementById("officerName").value == ''){
	 	alert("กรุณากรอกชื่อเจ้าหน้าที่รับสมัคร");
	 	document.getElementById("officerName").focus();
	 	return false;
	}
	
	  // ชื่อเกษตรกร
	  globFarmerName = document.plantForm.firstName.value + " " + document.plantForm.lastName.value;
	  document.getElementById('lblFarmerName').innerHTML = globFarmerName;
	  document.getElementById('lblBreedFarmerName').innerHTML = globFarmerName;
	  // ชื่อประเภทพืชที่เพาะปลูก
	  var breedTypeDD = document.getElementById("breedTypeId");
      var breedTypeObjId = breedTypeDD.value;     
      globBreedTypeName  = breedTypeDD.options[breedTypeDD.selectedIndex].text;
	  document.getElementById('lblManureBreedTypeName').innerHTML = globBreedTypeName;
	  
	  // เปิด tab ข้อมูลพื้นที่เพาะปลูก      
      document.plantForm.landrightItem.value = itemDetail; 
      document.plantForm.detailItem.value = itemOverall;
      document.plantForm.familyItem.value = itemFamily;
      document.plantForm.assetItem.value = itemAsset;
      document.plantForm.landCheckItem.value = itemLandCheck;
      
        //alert("itemAsset = " + itemAsset);
      	if(itemAsset == "" ){
      		if(document.getElementById("sumCheckbox") != null){
			    var isSumCheck = document.getElementById("sumCheckbox").checked;
			    
				if(isSumCheck == true){
					document.getElementById("assetId").value = 0;
			    	var aCurrency = document.getElementById("assetAmount").value;
					var aAmount = Number(aCurrency.replace(/[^0-9\.]+/g,""));
					document.getElementById("assetAmount").value = aAmount;
					
					// for save into Database
					document.plantForm.assetDetailDate.value = document.getElementById("dateAsset").value;
					document.plantForm.assetDetailAmount.value = document.getElementById("assetAmount").value;
					document.plantForm.sumCheck.value = "true";
				}
			}
		}
      
	  loadPleaseWait();
	  submitProcess();	
}
function submitProcess(){
	 document.plantForm.cmd.value = "Save";
	 document.plantForm.action = "<%=request.getContextPath()%>/Plant.do";
	 document.plantForm.method = "Post";
	 document.forms["plantForm"].submit();
}
function printGap(plantId, typeId, docNo) {
	//alert(plantId +" : "+ typeId +"/"+ docNo);
	document.plantForm.plantId.value = plantId;
	document.plantForm.docAndType.value = docNo+":"+typeId;
	document.plantForm.cmd.value = "Print";
	document.plantForm.action = "<%=request.getContextPath()%>/Plant.do";
	document.forms["plantForm"].submit();
}
 // ------- generate table zone ------ //
 // familyFarmer table
 function generateFamilyTable(data){
         var tableVal = "";
         tableVal += "<div class='topic-header-search' style='width: 870px;'>";
         tableVal += "<ul><li class='check-list-search' style='width: 40px;'>";
         tableVal += "<input type='checkbox' name='delIdCard' onclick='handleClick(this);' /></li>";
 		 tableVal += "<li class='address-sr' style='text-align: center;width: 300px;'>หมายเลขบัตรประจำตัวประชาชน</li>";
 		 tableVal += "<li class='topic-sr' style='text-align: center;width: 400px;'>ชื่อ - นามสกุล</li>";                    
 		 tableVal += "<li class='manage-sr' style='text-align: right;width: 50px;'></li>";
         tableVal += "</ul></div>";
 	     
 	    if(data !=null && data.length>0){
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			familyArray.length = newItemData.length;
 		    for(var i=0; i< newItemData.length;i++)
 		    {
 				if(newItemData[i] != "" && newItemData[i] != undefined && newItemData[i] != "undefined")
 				{
	 				var rowIndex = newItemData[i].split(",")[0];
	 				var idCard = newItemData[i].split(",")[1];
	 				var firstName = newItemData[i].split(",")[2];
	 				firstName = firstName.replace(/ /g, '\u00a0'); 
	 				var	lastName= newItemData[i].split(",")[3];
	 				lastName = lastName.replace(/ /g, '\u00a0');
	 				if(!arrayFamily.hasOwnProperty(idCard)){
	 					arrayFamily[idCard] = idCard;
	 				}
	 				familyArray[rowIndex] = newItemData[i];
	 				tableVal += "<div class='detail-header-search' style='width: 868px;'>";
	 				tableVal += "<ul><li class='check-list-search' style='width: 40px;'>";			                		                	
	 				tableVal += "<input type='checkbox' id='"+rowIndex+"' name='delIdCard' value='" + idCard + "' />";
	 				tableVal += "<label for='delIdCard'></label></li>";
	 				tableVal += "<li class='topic-sr' style='text-align: center;padding-left: 5px;width: 300px;'><a href=javascript:editFamily("+ rowIndex + "," + idCard + ",\'" + firstName + "\',\'" + lastName + "\');><font color=black>" + idCard + "</font></a></li>";
	 				tableVal += "<li class='topic-sr' style='text-align: left;padding-left: 5px;width: 400px;'><a href=javascript:editFamily("+ rowIndex + "," + idCard + ",\'" + firstName + "\',\'" + lastName + "\');><font color=black>" + firstName +  "&nbsp;&nbsp;" + lastName + "</font></a></li>";
	 				tableVal += "<li class='manage-sr' style='text-align: right;width: 50px;'>"; 
	 				tableVal += "<a href=javascript:editFamily(" + rowIndex + "," + idCard + ",\'" + firstName + "\',\'" + lastName +  "\');><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17' /></a>";	
	 				tableVal += "</li></ul></div>";	
 			}
 		}
 	}
 	return tableVal; 
 }
 // landright table
 // ข้อมูลพื้นที่เพาะปลูก
 function generateDetail(data)
 {
         var tableVal = "";
         tableVal += "<div class='topic-header-search' style='width: 982px;'>";
         tableVal += "<ul><li style='width: 30px;'>";
         tableVal += "<input type='checkbox' name='delLandTypeId' onclick='handleClick(this);' /></li>";
 		 tableVal += "<li class='topic-sr' style='text-align: center;width: 150px;'><font style='font-size: 19px;'>ประเภทเอกสารสิทธิ์</font></li>";
 		 tableVal += "<li class='topic-sr' style='text-align: center;width: 140px;'><font style='font-size: 19px;'>เลขที่เอกสารสิทธิ์</font></li>";		                    
 		 tableVal += "<li class='topic-sr' style='text-align: center;width: 70px;'><font style='font-size: 19px;'>ที่ดิน</font></li>";
 		 tableVal += "<li class='topic-sr' style='text-align: center;width: 100px;'><font style='font-size: 19px;'>ขนาดที่ดิน</font></li>";	
 		 tableVal += "<li class='topic-sr' style='text-align: center;width: 150px;'></li>";	
 		 tableVal += "<li class='manage-sr' style='width: 50px;'></li>";
         tableVal += "</ul></div>";
         
         var plantId = document.plantForm.plantId.value;
 	     if(data !=null && data.length>0){
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			docArray.length = newItemData.length;
 		for(var i=0; i< newItemData.length;i++)
 		{
 		  if(newItemData[i] != "" && newItemData[i] != undefined && newItemData[i] != "undefined"){
 		  	var itemDetail = newItemData[i].split(",");
	 			var rowIndex = itemDetail[0];
	 			var typeId = itemDetail[1];
	 			var typeName = itemDetail[2];
	 			if(typeName != undefined) 
	 				typeName = typeName.replace(/ /g, '\u00a0'); 
	 			
	 			var	docNo= itemDetail[3];
	 			if(docNo != undefined) {
	 				docNo = docNo.replace(/ /g, '\u00a0'); 
	 				if(docNo.indexOf('^') > -1){ 
	 					docNo = docNo.replace(/\^/g,','); // replace ^ with ,
	 				}
	 			}
 			
	 			var docRai= itemDetail[4];
	 			var docNgan= itemDetail[5];
	 			var docWah= itemDetail[6];
	 			var totalDocArea = docRai + "-" + docNgan + "-" + docWah;
	 			
	 			var landMoo  = itemDetail[7];
	 			if (landMoo == "")
	 			    landMoo = "''";
	 			    
	 			var provinceNo = itemDetail[8];
	 			if (provinceNo == "")
	 			    provinceNo = "''";
	 			
	 			var districtNo = itemDetail[9];
	 			if (districtNo == "")
	 			    districtNo = "''";
	 			
	 			var subDistrictNo= itemDetail[10];
	 			if (subDistrictNo == "")
	 			    subDistrictNo = "''";
	 			    
	         	var isOwn  = itemDetail[11];  
	         	var landStatus = "";
	         	if(isOwn == "1")
	         	    landStatus = "ตนเอง";
	         	if(isOwn == "0")
	         	    landStatus = "เช่า";
	         	
	         	var rentPrice = itemDetail[12];
	            var ownerShip = isOwn + "," + rentPrice;
	            var waterSource = itemDetail[13];
	            if(waterSource == undefined || waterSource == "" || waterSource == null)
	 				waterSource = 0;
	            var irrigation = itemDetail[14];
	            if(irrigation == undefined || irrigation == "" || irrigation == null)
	 				irrigation = 0;
	            
	 			var soil = itemDetail[15];
	 			var soilType = itemDetail[16];
	 			var soilTypeName = itemDetail[17];
	 			var sumtotal =  ownerShip + "," + waterSource + "," + irrigation + ",'" + soil + "','" + soilType + "'";
	 			
	 			if(soilTypeName != undefined && soilTypeName != "" && soilTypeName != "undefined"){
	 				soilTypeName = soilTypeName.replace(/ /g, '\u00a0');  
	 				if(soilTypeName.indexOf('^') > -1){ 
	 					soilTypeName = soilTypeName.replace(/\^/g,','); // replace ^ with ,
	 				}
	 			}
	 			
	 			var key = docNo + "" + typeId;	
	 			if(!arrayDetail.hasOwnProperty(key))
	 				arrayDetail[key] = key;	
	 				
	 			docArray[rowIndex] = newItemData[i];
 				if(docArray[rowIndex] != "undefined"  && docArray[rowIndex] != undefined && docArray[rowIndex] != "" && docArray[rowIndex] != null)
 				{
	 			    tableVal += "<div class='detail-header-search' style='width: 980px;'><ul style='margin: 0px;'>";
	 				tableVal += "<li style='width: 30px;'>";		
	 				tableVal += "<input type='checkbox' id='" + rowIndex + "' name='delLandTypeId' value='" + typeId + "' />";
	 				tableVal += "<label for='delLandTypeId'></label></li>";		 
	 				tableVal += "<li class='topic-sr' style='text-align: left;width: 155px;'><a href=javascript:edit(\'DocDetailPopup\'," + rowIndex + "," + typeId + ",\'" + typeName + "\',\'" + docNo + "\'," + docRai + "," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + sumtotal +  ",\'" + soilTypeName + "\');><font color=black>" + typeName + "</font></a></li>";
	 				tableVal += "<li class='topic-sr' style='text-align: center;width: 130px;'><a href=javascript:edit(\'DocDetailPopup\'," + rowIndex + "," + typeId + ",\'" + typeName + "\',\'" + docNo + "\'," + docRai + "," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + sumtotal + ",\'"+ soilTypeName + "\');><font color=black>" + docNo + "</font></a></li>";
	 				tableVal += "<li class='topic-sr' style='text-align: center;width: 70px;padding-left: 10px;'><a href=javascript:edit(\'DocDetailPopup\'," + rowIndex + "," + typeId + ",\'" + typeName + "\',\'" + docNo + "\'," + docRai + "," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + sumtotal +  ",\'" + soilTypeName + "\');><font color=black>" + landStatus + "</font></a></li>";
	 				tableVal += "<li class='topic-sr' style='text-align: center;width: 100px;'><a href=javascript:edit(\'DocDetailPopup\'," + rowIndex + "," + typeId + ",\'" + typeName + "\',\'" + docNo + "\'," + docRai + "," + docNgan + "," + docWah + ","  + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + sumtotal + ",\'" + soilTypeName + "\');><font color=black>" + totalDocArea + "</font></a></li>";
	 				tableVal +=	"<li class='topic-sr' style='text-align: center;width: 125px;'><a href=javascript:edit(\'PlantBreedPopup\'," + rowIndex + "," + typeId  + ",\'" + typeName + "\',\'" + docNo + "\'," + docRai + "," + docNgan + "," + docWah + ","  + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + sumtotal + ",\'" + soilTypeName + "\');><font color=blue>ข้อมูลการเพาะปลูก</font></a></li>";	
	 				// --- เพิ่มข้อมูลการตรวจแปลง by Yatphiroon.P on 27.04.2015 ---
	 				tableVal +=	"<li class='topic-sr' style='text-align: center;width: 125px;'><a href=javascript:addLandcheck(\'"+typeId+"\',"+docNo+","+docRai+","+docNgan+","+docWah+");><font color=green>ข้อมูลการตรวจแปลง</font></a></li>";
	 				// ---
	 				// ประวัติการตรวจแปลง
	 				tableVal +=	"<li class='topic-sr' style='text-align: center;width: 130px;'><a href=javascript:edit(\'LandCheckHistory\'," + rowIndex + "," + typeId  + ",\'" + typeName + "\',\'" + docNo + "\'," + docRai + "," + docNgan + "," + docWah + ","  + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + sumtotal + ",\'" + soilTypeName + "\');><font color=red>ประวัติการตรวจแปลง</font></a></li>";
	 				// พิมพ์ GAP
	 				tableVal += "<li class='manage-sr' style='text-align:center;width:25px;padding-top:0px;'><a href=javascript:printGap("+ plantId +","+ typeId  +","+ docNo +");><img src='<%=request.getContextPath()%>/images/btn-print.png' width='16' height='16' title='พิมพ์แบบบันทึกระบบการจัดการคุณภาพ : GAP ข้าว' /></a></li>";
	 				//---------
	 				tableVal += "<li class='manage-sr' style='text-align:center;width:25px;padding-top:0px;'>"; 
	 			    tableVal += "<a href=javascript:edit(\'DocDetailPopup\'," + rowIndex + "," + typeId + ",\'" + typeName + "\',\'" + docNo + "\'," + docRai + "," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + sumtotal + ",\'" + soilTypeName + "\');><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17' /></a>";
	 				tableVal += "</li>";
	 			    tableVal += "</ul></div>";	
 				}
 		  }
 	   }
 	}
 	return tableVal; 
 }
 // plantDetail table
 function generateTable(data)
 {
 	
      var tableVal = "";
      tableVal += "<div class='topic-header-search' style='width:950px;'><ul>";
 	  tableVal += "<li class='tel-sr' style='width: 40px;'>";
 	  tableVal += "<input type='checkbox'  name='delBreedGroupId' onclick='handleClick(this);' /></li>";
 	  tableVal += "<li class='topic-sr' style='text-align: center;width: 120px;'>ชื่อพันธุ์ที่ปลูก</li>";
 	  tableVal += "<li class='address-sr' style='text-align: center;width: 100px;'>วัตถุประสงค์</li>";
 	  tableVal += "<li class='topic-sr' style='text-align: center;width:110px;'>ลักษณะการเกษตร</li>";
 	  tableVal += "<li class='topic-sr' style='text-align: center;width: 200px;'>ประเภทเอกสารสิทธิ์</li>";
 	  tableVal += "<li class='tel-sr' style='text-align: center;width: 112px;'></li>";
 	  tableVal += "<li class='tel-sr' style='text-align: center;width: 70px;'></li>";
 	  tableVal += "<li class='tel-sr' style='text-align: center;width: 80px;'></li>";
 	  tableVal += "<li class='tel-sr' style='text-align: center;width: 50px;'></li>";
 	  tableVal += "</ul></div>";
    	
     if(data !=null && data.length>0)
     {
 		var newItemData= new Array();
 		newItemData = data.split("|");
 		breedDetailArray.length = newItemData.length;
 		totalAreaArray.length = [];
 		totalAreaArray.clear();
 		for(var i=0; i< newItemData.length;i++)
 		{
 			if(newItemData[i] != "" && newItemData[i] != "undefined" && newItemData[i] != undefined)
 			{
 				var rowIndex = newItemData[i].split(",")[0];
    			var typeId = newItemData[i].split(",")[1]; 
    			var titleTypeName = newItemData[i].split(",")[2].replace(/'/g, '');
    			var typeName = newItemData[i].split(",")[2].replace(/ /g, '\u00a0');
    			var docNo = newItemData[i].split(",")[3].replace(/ /g, '\u00a0'); 
 				if(docNo.indexOf('^') > -1) 
 					docNo = docNo.replace(/\^/g,','); // replace ^ with ,
    			
    			var docRai = newItemData[i].split(",")[4]; 
    			var docNgan = newItemData[i].split(",")[5]; 
    			var docWah = newItemData[i].split(",")[6]; 
 				
 				var groupId = newItemData[i].split(",")[7]; 
 				var titleGroupName = newItemData[i].split(",")[8].replace(/'/g, '');
 				var groupName = newItemData[i].split(",")[8].replace(/ /g, '\u00a0'); 
 				var qty = newItemData[i].split(",")[9]; 
 			
 				var plantRai = newItemData[i].split(",")[10]; 
 				var plantNgan = newItemData[i].split(",")[11]; 
 				var plantWah = newItemData[i].split(",")[12]; 
 				
 				// -- Check ผลต่างของ landRightArea กับ  plantDetailArea --
 				var docSum = convertToWah(parseInt(docRai), parseInt(docNgan), parseInt(docWah)); // land พื้นที่ในเอกสารสิทธิ์
	            var plantSum = convertToWah(parseInt(plantRai), parseInt(plantNgan), parseInt(plantWah)); // plant พื้นที่ที่ใช้เพาะปลูกจริงๆ
 				var coKey = typeId + "" + docNo.replace(/'/g, '');
				if(!docAreaArray.hasOwnProperty(coKey)){             
					docAreaArray[coKey] = docSum;
				}
		
				if(!totalAreaArray.hasOwnProperty(coKey)){             
					totalAreaArray[coKey] = plantSum
				} else {
					totalAreaArray[coKey] += plantSum;
				}
 				// --- ---- ---
 				
 				var seedResource = newItemData[i].split(",")[13]; 
 				var seedPicking = newItemData[i].split(",")[14]; 
 				
 				var pObjective = "";
 				var plantObjective = newItemData[i].split(",")[15];
 				if(plantObjective.indexOf('^') > -1){ 
 					pObjective = plantObjective.replace(/\^/g,','); // replace ^ with ,
 					pObjective = pObjective.replace(/'/g, '');
 				}else{
 				    pObjective = plantObjective.replace(/'/g, '');
 				}
 				
 				var agriType = ""; 	
 				var agricultureType = newItemData[i].split(",")[16]; 
 				if(agricultureType.indexOf('^') > -1) {
 					agriType = agricultureType.replace(/\^/g,','); // replace ^ with ,
 					agriType = agriType.replace(/'/g, '');
 				}else{
 				    agriType = agricultureType.replace(/'/g, '');
 				}
 				var gap = newItemData[i].split(",")[17].replace(/ /g, '\u00a0'); 
 				var planting  = newItemData[i].split(",")[18]; 
 				var plotting  = newItemData[i].split(",")[19]; 
 				var plottingName = newItemData[i].split(",")[20].replace(/ /g, '\u00a0'); 
 				var manureUse = newItemData[i].split(",")[21];
 				var plantDate = newItemData[i].split(",")[22]; 
 				var forecastDate = newItemData[i].split(",")[23]; 
 				var forecastRecordDoc = newItemData[i].split(",")[24].replace(/ /g, '\u00a0');
 				// ซื้อจาก สหกรณ์ added by Yatphiroon.P on 29.04.2015
 				var corpId = newItemData[i].split(",")[25]; 
 				var corpName = newItemData[i].split(",")[26].replace(/ /g, '\u00a0');
 				var noOfBreed = newItemData[i].split(",")[27];
 				var row1 = rowIndex + ",'" + typeId + "'," + typeName + "," + docNo + "," + docRai + "," + docNgan + "," + docWah;
 				var row2 = groupId + "," + groupName + "," + qty + "," + plantRai + "," + plantNgan + "," + plantWah;
 				var lastRow = seedResource + "," + seedPicking + "," + plantObjective + "," + agricultureType + "," + gap 
 							+ "," + planting + "," + plotting + "," + plottingName + "," + manureUse 
 							+ "," + plantDate + "," + forecastDate + "," + forecastRecordDoc;
 			   // ซื้อจาก สหกรณ์ added by Yatphiroon.P on 29.04.2015
 			    var aboutCorp = corpId + "," + corpName + "," + noOfBreed;
 				var breedAllRow = row1 + "," + row2 + "," + lastRow + "," + aboutCorp;
 				if(typeId.indexOf("+") > -1) {
				   var splitArray = new Array();
			       splitArray =  typeId.split("+");
				   typeId = splitArray[0];
				}
 				var key = typeId + "" + docNo.replace(/'/g, '') + "" + groupId;
 				if(!arrayBreed.hasOwnProperty(key))
 					arrayBreed[key] = rowIndex;
 		      	breedDetailArray[rowIndex] = newItemData[i];
 		   	
 		    	if(breedDetailArray[rowIndex] != "undefined" && breedDetailArray[rowIndex] != undefined && breedDetailArray[rowIndex] != "" && breedDetailArray[rowIndex] != null)
 		    	{
   	  				tableVal += "<div class='detail-header-search' style='width:948px;'><ul>";
 	  				tableVal += "<li class='tel-sr' style='width: 35px;'>";
       				tableVal += "<input type='checkbox' id='" + rowIndex + "' name='delBreedGroupId' value='" + groupId + "' />";
 	  				tableVal += "<label for='delBreedGroupId'></label></li>";			 
 	  				tableVal += "<li class='topic-sr' style='text-align: center;width: 130px;'>";
 	  				tableVal += "<a href=javascript:editBreed(" + breedAllRow + ",'PlantBreedPopup'); title='" + titleGroupName + "'><font color = 'black'>" + titleGroupName + "</font></a>";
 	  				tableVal += "</li>";
 	  				tableVal += "<li class='address-sr' style='text-align: center;width: 100px;'>";
 	  				tableVal += "<a href=javascript:editBreed(" + breedAllRow + ",'PlantBreedPopup'); title='" + pObjective.split(",")[0] + "'><font color = 'black'>" + pObjective.split(",")[0] + "</font></a>";
 	  				tableVal += "</li>";
 	    			tableVal += "<li class='topic-sr' style='text-align: center;width: 110px;'>";
 	  				tableVal += "<a href=javascript:editBreed(" + breedAllRow + ",'PlantBreedPopup'); title='" + agriType + "'><font color = 'black'>" + agriType + "</font></a>";
 	  				tableVal += "</li>";	
 	    			tableVal += "<li class='topic-sr' style='text-align: center;width: 200px;'>";
 	  				tableVal += "<a href=javascript:editBreed(" + breedAllRow + ",'PlantBreedPopup'); title='" + titleTypeName + " - " + docNo.replace(/'/g, '') + "'><font color = 'black'>" + titleTypeName  + " - " + docNo.replace(/'/g, '') + "</font></a>";
 	  				tableVal += "</li>";	
 	  				tableVal += "<li class='tel-sr' style='text-align: center;width: 112px;background-color: #D4D5F2;'>";
 	  				tableVal += "<a href=javascript:showDetailWindow(" + rowIndex + "," + groupId + "," + groupName + "); title='แจ้งล่วงหน้า 1 เดือน' ><font color=black>แจ้งล่วงหน้า 1 เดือน</font></a>";
 	  				tableVal += "</li>";
 	    			tableVal += "<li class='tel-sr' style='text-align: center;width: 70px;background-color: #C4FFB7;'>";
 	  				tableVal += "<a href=javascript:showCostWindow(" + rowIndex + "," + groupId + "," + groupName + ");  title='ค่าใช้จ่าย' ><font color=black>ค่าใช้จ่าย</font></a>";
 	  				tableVal += "</li>";
 	  				tableVal += "<li class='tel-sr' style='text-align: center;width: 80px;background-color: #FFE6B7;'>";
 	  				tableVal += "<a href=javascript:showSaleWindow(" + rowIndex + "," + groupId + "," + groupName + "); title='ขายผลผลิต' ><font color=black>ขายผลผลิต</font></a>";
 	  				tableVal += "</li>";
 	  				tableVal += "<li class='tel-sr' style='text-align: center;width: 50px;'>";
 	  				tableVal += "<a href=javascript:editBreed(" + breedAllRow + ",'PlantBreedPopup');><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17'/></a>";	
 					tableVal += "</li>";
 	 				tableVal += "</ul></div>";
 	 	   		}
 	 		}
 	  	}
 	}
 	return tableVal; 
 }
 // manure table
 function generateManureTable(data)
 {
        var tableVal = "";
        tableVal += "<div class='topic-header-search' style='width: 890px'>";
        tableVal += "<ul><li class='check-list-search' style='width: 30px'>";
        tableVal +=	"<input type='checkbox' name='delManureTypeId' onclick='handleClick(this);' /></li>";
 		tableVal +=	"<li class='address-sr' style='text-align: center;width: 90px; line-height: 20px;'><font size = '4px'>ประเภทปุ๋ย</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px; line-height: 20px;'><font size = '4px'>ชื่อ/ตราสินค้า</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px; line-height: 20px;'><font size = '4px'>สูตรปุ๋ย</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 100px; line-height: 20px;'><font size = '4px'>อัตราการใช้(กก./ไร่)</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 100px; line-height: 20px;'><font size = '4px'>ราคา(บาท/ไร่)</font></li>";	
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px; line-height: 20px;'><font size = '4px'>ชื่อพันธุ์ที่ปลูก</font></li>";	
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px; line-height: 20px;'><font size = '4px'>ขนาดพื้นที่เพาะปลูก</font></li>";			                    
 		tableVal += "<li class='manage-sr' style='text-align: right;width: 40px;'>&nbsp;</li>";
        tableVal +=	"</ul></div>";
 	     
 	    if(data !=null && data.length>0){
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			manureDetailArray.length = newItemData.length;
 			for(var i=0; i < newItemData.length; i++)
 			{
 				if(newItemData[i] != "" && newItemData[i] != undefined && newItemData[i] != "undefined" )
 				{
 					var rowIndex = newItemData[i].split(",")[0];//intManureCnt
 					var typeId = newItemData[i].split(",")[1];//manureTypeId
 					var typeName = newItemData[i].split(",")[2];//manureTypeName
 					typeName = typeName.replace(/ /g, '\u00a0');
 					var ownBuy = newItemData[i].split(",")[3]; // ownBuy
 					var ownProd = newItemData[i].split(",")[4]; //ownProduce
 					
 				 	var mName = newItemData[i].split(",")[5].replace(/ /g, '\u00a0'); //mName
 					if(mName.indexOf('^') > -1) 
 						mName = mName.replace(/\^/g,','); // replace ^ with ,
 					
 					var	formula= newItemData[i].split(",")[6].replace(/ /g, '\u00a0'); // formula
 					if(formula.indexOf('^') > -1) 
 						formula = formula.replace(/\^/g,','); // replace ^ with ,
 						
 				    var cost = newItemData[i].split(",")[7];
 				    var mPeriodTime =  newItemData[i].split(",")[8];
 					var manureDate =  newItemData[i].split(",")[9];
 					var qty =  newItemData[i].split(",")[10];
 					var key = typeId + "" + rowIndex;
 					if(!arrayManure.hasOwnProperty(key)){
 						arrayManure[key] = rowIndex;
 					}
 				 	manureDetailArray[rowIndex] = newItemData[i];
 					if(manureDetailArray[rowIndex] != "undefined" && manureDetailArray[rowIndex] != undefined && manureDetailArray[rowIndex] != "" && manureDetailArray[rowIndex] != null)
 					{
 						tableVal += "<div class='detail-header-search' style='width: 888px;'>";
 						tableVal += "<ul><li  style='width: 30px'>";			                		                	
 						tableVal += "<input type='checkbox' id='" + rowIndex + "' name='delManureTypeId' value='" + typeId + "' />";
 						tableVal += "<label for='delManureTypeId'></label></li>";		
 						tableVal += "<li class='address-sr' style='text-align: left;width: 90px;'><a href=javascript:editManure(" + rowIndex + "," + typeId + "," + ownBuy + "," + ownProd + ",\'" + mName + "\',\'" + formula + "\','" + cost + "'," + mPeriodTime + ",'" + manureDate + "','" + qty + "');><font color=black>" + typeName + "</font></a></li>";
 						tableVal += "<li class='topic-sr' style='text-align: left;width: 110px;'><a href=javascript:editManure(" + rowIndex + "," + typeId + "," + ownBuy + "," + ownProd + ",\'" + mName + "\',\'" + formula + "\','" + cost + "'," + mPeriodTime + ",'" + manureDate + "','" + qty + "');><font color=black>" + mName + "</font></a></li>";
 						tableVal += "<li class='topic-sr' style='text-align: left;width: 100px;'><a href=javascript:editManure(" + rowIndex + "," + typeId + "," + ownBuy + "," + ownProd + ",\'" + mName + "\',\'" + formula + "\','" + cost + "'," + mPeriodTime + ",'" + manureDate + "','" + qty + "');><font color=black>" + formula + "</font></a></li>";
 						tableVal += "<li class='topic-sr' style='text-align: right;width: 100px;'><a href=javascript:editManure(" + rowIndex + "," + typeId + "," + ownBuy + "," + ownProd + ",\'" + mName + "\',\'" + formula + "\','" + cost + "'," + mPeriodTime + ",'" + manureDate + "','" + qty + "');><font color=black>" + formatNumber(qty) + "</font></a></li>";
 						tableVal += "<li class='topic-sr' style='text-align: right;width: 100px;'><a href=javascript:editManure(" + rowIndex + "," + typeId + "," + ownBuy + "," + ownProd + ",\'" + mName + "\',\'" + formula + "\','" + cost + "'," + mPeriodTime + ",'" + manureDate + "','" + qty + "');><font color=black>" + formatNumber(cost) + "</font></a></li>";
 						tableVal += "<li class='topic-sr' style='text-align: left; padding-left: 20px;width: 110px;'><a href=javascript:editManure(" + rowIndex + "," + typeId + "," + ownBuy + "," + ownProd + ",\'" + mName + "\',\'" + formula + "\','" + cost + "'," + mPeriodTime + ",'" + manureDate + "','" + qty + "');><font color=black>" + globBreedGroupName.replace(/'/g , "") + "</font></a></li>";
 						tableVal += "<li class='topic-sr' style='text-align: left;width: 110px;'><a href=javascript:editManure(" + rowIndex + "," + typeId + "," + ownBuy + "," + ownProd + ",\'" + mName + "\',\'" + formula + "\','" + cost + "'," + mPeriodTime + ",'" + manureDate + "','" + qty + "');><font color=black>" + globPlantArea + "</font></a></li>";
 						tableVal += "<li class='manage-sr' style='text-align: right;width: 40px;'>"; 
 						tableVal += "<a href=javascript:editManure(" + rowIndex + "," + typeId + "," + ownBuy + "," + ownProd + ",\'" + mName + "\',\'" + formula + "\','" + cost + "'," + mPeriodTime + ",'" + manureDate + "','" + qty + "');><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17' /></a>";	
 						tableVal += "</li></ul>";
 						tableVal += "</div>";	
 				}
 			}
 		}
 	}
 	return tableVal;
 }
 // chemi table
 function generateChemicalTable(data)
 {
        var tableVal = "";
        tableVal += "<div class='topic-header-search'  style='width: 860px'>";
        tableVal += "<ul><li class='check-list-search' style='width: 30px;line-height: 20px;'>";
        tableVal +=	"<input type='checkbox' name='delChemicalTypeId' onclick='handleClick(this);' /></li>";
 		tableVal +=	"<li class='address-sr' style='text-align: center;width: 100px;line-height: 20px;'><font size = '4px'>ประเภทสารเคมี</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px;line-height: 20px;'><font size = '4px'>ชื่อสารเคมี</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px;line-height: 20px;'><font size = '4px'>อัตราการใช้ (ซีซี/ไร่)</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px;line-height: 20px;'><font size = '4px'>ราคา (บาท/ไร่)</font></li>";		
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px;line-height: 20px;'><font size = '4px'>ชื่อพันธุ์ที่ปลูก</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 110px;line-height: 20px;'><font size = '4px'>ขนาดพื้นที่เพาะปลูก</font></li>";	                    
 		tableVal += "<li class='manage-sr' style='text-align: center;width: 40px;line-height: 20px;'>&nbsp;</li>";
        tableVal +=	"</ul></div>";
 	    if(data !=null && data.length>0){
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			chemDetailArray.length = newItemData.length;
 			for(var i=0; i< newItemData.length;i++)
 			{
 				if(newItemData[i] != "" && newItemData[i] != undefined && newItemData[i] != "undefined")
 				{  	
 				    var rowIndex = newItemData[i].split(",")[0];
 					var typeId = newItemData[i].split(",")[1];
 					var typeName = newItemData[i].split(",")[2];
 					typeName = typeName.replace(/ /g, '\u00a0'); 
 				
 				    var	formula= newItemData[i].split(",")[3];
 				    if(formula != undefined) 
 						formula = formula.replace(/ /g, '\u00a0'); 
 					if(formula.indexOf('^') > -1) 
 						formula = formula.replace(/\^/g,','); // replace ^ with ,
 					
 					var cost = newItemData[i].split(",")[4];
 					var period = newItemData[i].split(",")[5];
 					var cDate = newItemData[i].split(",")[6];
 					var qty =  newItemData[i].split(",")[7];
 					
 					var key = typeId + "" + rowIndex;
 					if(!arrayChem.hasOwnProperty(key)){
 						arrayChem[key] = rowIndex;
 					}
 					
 				chemDetailArray[rowIndex] = newItemData[i];
 				
 				if(chemDetailArray[rowIndex] != "undefined"  && chemDetailArray[rowIndex] != undefined && chemDetailArray[rowIndex] != "" && chemDetailArray[rowIndex] != null)
 				{
 					tableVal += "<div class='detail-header-search' style='width: 858px;'><ul>";
 					tableVal += "<li class='check-list-search' style='width: 30px;'>";			                	
 					tableVal += "<input type='checkbox' id='" + rowIndex + "' name='delChemicalTypeId' value='" + typeId + "' />";	                	
 					tableVal += "<label for='delChemicalTypeId'></label></li>";		
 					tableVal += "<li class='address-sr' style='text-align: left;width: 100px;line-height: 20px;'><a href=javascript:editChem(" + rowIndex + "," + typeId + ",\"" + formula + "\"," + cost + "," + period + ",'" + cDate + "'," + qty + ");><font size = '4px' color='black'>" + typeName + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: left;width: 110px;line-height: 20px;'><a href=javascript:editChem(" + rowIndex + "," + typeId + ",\"" + formula + "\"," + cost  + "," + period + ",'" + cDate + "'," + qty + ");><font size = '4px' color='black'>" + formula + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width: 110px;line-height: 20px;'><a href=javascript:editChem(" + rowIndex + "," + typeId + ",\"" + formula + "\"," + cost + "," + period + ",'" + cDate + "'," + qty + ");><font size = '4px' color='black'>" + formatNumber(qty) + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width: 110px;line-height: 20px;'><a href=javascript:editChem(" + rowIndex + "," + typeId + ",\"" + formula + "\"," + cost + "," + period + ",'" + cDate + "'," + qty + ");><font size = '4px' color='black'>" + formatNumber(cost) + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: left;padding-left: 20px;width: 110px;line-height: 20px;'><a href=javascript:editChem(" + rowIndex + "," + typeId + ",\"" + formula + "\"," + cost + "," + period + ",'" + cDate + "'," + qty + ");><font size = '4px' color='black'>" + globBreedGroupName.replace(/'/g , "")  + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: left;width: 110px;line-height: 20px;'><a href=javascript:editChem(" + rowIndex + "," + typeId + ",\"" + formula + "\"," + cost + "," + period + ",'" + cDate + "'," + qty + ");><font size = '4px' color='black'>" + globPlantArea + "</font></a></li>";
 					tableVal += "<li class='manage-sr' style='width: 40px;'>"; 
 					tableVal += "<a href=javascript:editChem(" + rowIndex + "," + typeId + ",\"" + formula + "\"," + cost + "," + period + ",'" + cDate + "'," + qty + ");><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17' /></a>";
 					tableVal += "</li>";
 					tableVal += "</ul></div>";
 				}
 			}
 		}
 	}
 	return tableVal; 
 }
 // asset table
 function generateAssetTable(data)
 {
        var tableVal = "";
        tableVal += "<div class='topic-header-search'  style='width: 850px;'>";
        tableVal += "<ul><li class='check-list-search' style='width: 50px;line-height: 20px;'>";
        tableVal +=	"<input type='checkbox' name='delAssetId' onclick='handleClick(this);' /></li>";
 		tableVal +=	"<li class='address-sr' style='text-align: center;width: 200px;line-height: 20px;'><font size = '4px'>ประเภททรัพย์สิน</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: center;width: 200px;line-height: 20px;'><font size = '4px'>วันที่ซื้อ</font></li>";
 		tableVal +=	"<li class='topic-sr' style='text-align: right;width: 200px;line-height: 20px;padding-right:10px;'><font size = '4px'>จำนวนเงิน</font></li>";	
 		tableVal += "<li class='manage-sr' style='width: 50px;line-height: 20px;'></li>";
        tableVal +=	"</ul></div>";
 	    if(data !=null && data.length>0)
 	    {
 	        totalAmount = 0;
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			assetArray.length = newItemData.length;
 			for(var i=0; i< newItemData.length;i++)
 			{
 				if(newItemData[i] != "" && newItemData[i] != undefined && newItemData[i] != "undefined")
 				{
 						var rowIndex = newItemData[i].split(",")[0];
 						var assetId = newItemData[i].split(",")[1];
 						var assetName = newItemData[i].split(",")[2];
 							assetName = assetName.replace(/ /g, '\u00a0'); 
 						var assetDate = newItemData[i].split(",")[3];
 						var amount = newItemData[i].split(",")[4];
 							totalAmount += parseFloat(amount);
 						
 						if(!arrayAsset.hasOwnProperty(assetId))
 							arrayAsset[assetId] = rowIndex;
 		    			
 						assetArray[rowIndex] = newItemData[i];
 						
 					if(assetArray[rowIndex] != "undefined" && assetArray[rowIndex] != undefined && assetArray[rowIndex] != "" && assetArray[rowIndex] != null)
 					{
 						tableVal += "<div class='detail-header-search' style='width: 848px;' >";
 						tableVal += "<ul><li class='check-list-search' style='width: 50px;'>";			                	
 						tableVal += "<input type='checkbox' id='"+rowIndex+"' name='delAssetId' value='" + assetId + "' />";	
 						tableVal += "<label for='delAssetId'></label></li>";	
 						tableVal += "<li class='address-sr' style='text-align: center;width: 200px;'><a href=javascript:editAsset(" + rowIndex + "," + assetId + ",\"" + assetName + "\",\"" + assetDate + "\"," + amount + ");><font color='black' size='4px'>" + assetName + "</font></a></li>";
 						tableVal += "<li class='topic-sr' style='text-align: right;width: 200px;'><a href=javascript:editAsset(" + rowIndex + "," + assetId + ",\"" + assetName + "\",\"" + assetDate + "\"," + amount + ");><font color='black' size='4px'>" + assetDate + "</font></a></li>";
 						tableVal += "<li class='topic-sr' style='text-align: right;width: 200px;'><a href=javascript:editAsset(" + rowIndex + "," + assetId + ",\"" + assetName + "\",\"" + assetDate + "\"," + amount + ");><font color='black' size='4px'>" + formatNumber(amount) + "</font></a></li>";
 			    		tableVal += "<li class='manage-sr' style='width: 50px;'>"; 
 						tableVal += "<a href=javascript:editAsset(" + rowIndex + "," + assetId + ",\"" + assetName + "\",\"" + assetDate + "\"," + amount + ");><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17' /></a>";
 						tableVal += "</li>";	
 						tableVal += "</ul></div>";	
 					}
 				 }
 			}
 		}
 		tableVal += "<div class='detail-header-search'  style='width: 848px'>";
 		tableVal += "<ul><li class='check-list-search' style='width: 50px'>";			                	
 		tableVal += "&nbsp;</li>";	
 		tableVal += "<li class='address-sr' style='text-align: center;width: 200px'>";
 		if(globAssetDate != "" && globAssetAmount != ""){
 		   tableVal += "<input type='checkbox' id='sumCheckbox' name='sumCheckbox' onclick='checkSum(this, dateAsset, assetAmount);' checked />ยอดรวม</li>";
 	       isCheckSum = true;
 		} else {
 		   tableVal += "<input type='checkbox' id='sumCheckbox' name='sumCheckbox' onclick='checkSum(this, dateAsset, assetAmount);' />ยอดรวม</li>";
 		   isCheckSum = false;
 		}
 		
 		tableVal += "<li class='topic-sr' style='text-align: right;width: 300px;'><input type='text' name='dateAsset' id='dateAsset' value='" + globAssetDate + "' style='width: 100px;height: 20px;' onchange='isDate(this);' readonly >";
 		tableVal += "<img border='0' onclick=\"showCalendar(document.getElementById('dateAsset'))\" src=\"<%=request.getContextPath()%>/dcswc/images/calendar.jpg\" style=\"cursor:pointer;\">";
 		tableVal += "<font color='red' style='font-size: 18px'>ตย. 31/12/2557<font></li>";
 	//	tableVal += "<li class='topic-sr' style='text-align: right;width: 200px;padding-right:50px'><input type='text' name='assetAmount' id='assetAmount' value = '" + globAssetAmount + "' maxlength='15' style='width:100px;height:20px;text-align: right;' onchange = 'checkNumberComma(this);'  readonly /></li>";
 		totalAmount = totalAmount + "";
 		tableVal += "<li class='topic-sr' style='text-align: right;width: 200px;padding-right:50px'><input type='text' name='assetAmount' id='assetAmount' value = '" + formatNumber(totalAmount) + "' maxlength='15' style='width:100px;height:20px;text-align: right;' onchange = 'checkNumberComma(this);'  readonly /></li>";
 		tableVal += "</ul></div>";	
 		return tableVal; 
 }
 // cost table
 function generateCostTable(data)
 {
         var tableVal = "";
         tableVal +=  "<div class='topic-header-search'  style='width: 900px'>";
         tableVal +=  "<ul><li style='width: 40px;vertical-align:middle;'>";
         tableVal +=  "<input type='checkbox' name='delCostId' onclick='handleClick(this);' style='vertical-align:top;' /></li>";
 		 tableVal +=  "<li class='topic-sr' style='text-align: center;width: 500px;vertical-align: texttop;'><font style='font-size: 19px;'>ต้นทุน /ค่าใช้จ่าย</font></li>";
 		 tableVal +=  "<li class='topic-sr' style='text-align: center;width: 300px;vertical-align: texttop;'><font style='font-size: 19px;'>จำนวนเงิน</font></li>";
 		 tableVal +=  "<li class='manage-sr' style='text-align: center; width: 30px'></li>";	
         tableVal +=  "</ul></div>";
         var sumCostAmount = 0;
 	     if(data !=null && data.length>0){
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			costDetailArray.length = newItemData.length;
 			for(var i=0; i< newItemData.length;i++){
 			      if(newItemData[i] != undefined && newItemData[i] != "" && newItemData[i]!= "undefined")
 			      {
 						var rowIndex = newItemData[i].split(",")[0];
 						var costId = newItemData[i].split(",")[1];
 						var costName = newItemData[i].split(",")[2];
 						
 						if(costName != undefined)
 							costName= costName.replace(/ /g, '\u00a0');
 							
 						if (costName.indexOf('-') > -1)
							costName = costName.replace(/-/g, ",");
						
 							 
 						var costAmount = newItemData[i].split(",")[3];
 						
 						sumCostAmount += Number(costAmount);
 						var costDate = newItemData[i].split(",")[4]; // วันที่จ่าย
 						
 						if(!arrayCost.hasOwnProperty(costId))
 							arrayCost[costId] = rowIndex;
 						costDetailArray[rowIndex] = newItemData[i];
 											
 						if(costDetailArray[rowIndex] != "undefined" && costDetailArray[rowIndex] != undefined && costDetailArray[rowIndex] != "" && costDetailArray[rowIndex] != null)
 						{
 							tableVal += "<div class='detail-header-search' style='width:900px;'>";
 							tableVal += "<ul><li style='width: 40px'>";
 							tableVal += "<input type='checkbox' id='"+rowIndex+"' name='delCostId' value='" + costId + "' />";
 							tableVal += "<label for='delCostId'></label></li>";
 							tableVal += "<li class='topic-sr' style='text-align: left;width: 500px'><a href=javascript:editCost(" + rowIndex + "," + costId + "," + costAmount + ",'" + costDate + "');><font color=black>" + costName + "</font></a></li>";
 							tableVal += "<li class='topic-sr' style='text-align: right;width: 155px;padding-right:145px'><a href=javascript:editCost(" + rowIndex + "," + costId + "," + costAmount + ",'" + costDate + "');><font color=black>" + formatNumber(costAmount) + "</font></a></li>";
 							tableVal += "<li class='manage-sr' style='text-align: center; width: 40px'>";
 							tableVal += "<a href=javascript:editCost(" + rowIndex + "," + costId + "," + costAmount + ",'" + costDate + "');><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17' /></a>";
 							tableVal +=	"</li></ul></div>";
 						}
 				}
 			}
 	}
 	     // summary
 	     tableVal += "<div class='detail-header-search' style='width: 900px;background-color: #aaaaaa;'><ul>";
         tableVal +=  "<li style='width: 40px;vertical-align:middle;'></li>";
 		 tableVal +=  "<li class='topic-sr' style='text-align: center;width: 500px;vertical-align: texttop;'><font style='font-size: 19px;'><b>สรุปยอดรวม</b></font></li>";
 		 tableVal +=  "<li class='topic-sr' style='text-align: center;width: 300px;vertical-align: texttop;'><font style='font-size: 19px;'><b>"+formatNumber(sumCostAmount)+"</b></font></li>";
 		 tableVal +=  "<li class='manage-sr' style='text-align: center; width: 30px'></li>";	
         tableVal +=  "</ul></div>";
 		 return tableVal; 
 }
 // harvest table
 function generateHarvestTable(data)
 {
  		var tableVal = "<div class='topic-header-search' style='width:900px;'>";
 		tableVal += "<ul><li style='width:20px;'>";
 		tableVal +=	"<input type='checkbox' name='delHarvDate' onclick='handleClick(this);' /></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:100px;'><font style='font-size: 18px;'>วันที่เก็บเกี่ยว</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:150px;'><font style='font-size: 18px;'>ผลผลิตที่ได้ทั้งหมด (กก.)</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:180px;'><font style='font-size: 18px;'>ผลผลิตที่เก็บไว้บริโภค (กก.)</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:180px;'><font style='font-size: 18px;'>ผลผลิตที่เก็บไว้ทำพันธุ์ (กก.)</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:180px;'><font style='font-size: 18px;'>ผลผลิตที่เหลือไว้ขายทั้งหมด</font></li>";
 		tableVal += "<li class='manage-sr' style='width:50px;'></li></ul></div>";
 		sumRemainSell = 0;
		sumTotalHarvest = 0;
		sumConsumeProduct = 0;
		sumBreedProduct = 0;
		
 		 if(data !=null && data.length>0)
 		 {
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			harvDetailArray.length = newItemData.length; // set length to harvDetailArray array
 			sumHarvArr.length = newItemData.length; 
			for(var i=0; i< newItemData.length;i++){
 			   if(newItemData[i] != "" && newItemData[i] != undefined && newItemData[i] != "undefined"){
 					var rowIndex = newItemData[i].split(",")[0];
 					var harvestDate = newItemData[i].split(",")[1];
 	   				var totalHarvest = newItemData[i].split(",")[2];
 	   				var remainSell = newItemData[i].split(",")[3];
 	   				// added on 18.01.2017
 	   				var consumeProduct = newItemData[i].split(",")[4]; 
 	   				var breedProduct = newItemData[i].split(",")[5];
 	   				
 	   				sumTotalHarvest += Number(totalHarvest);
        			sumHarvArr[rowIndex] = remainSell; // summation of remainSell
 	   				sumRemainSell += Number(remainSell);
 	   				
 	   				sumConsumeProduct += Number(consumeProduct); // summation of consume product
					sumBreedProduct += Number(breedProduct); 	 // summation of breed product
 	   				
 	   				
 			    	var key = harvestDate;
 			    	if(!arrayHarvest.hasOwnProperty(key))
 	 	       		 	arrayHarvest[key] = rowIndex;
   	   	        	
   	   	        	harvDetailArray[rowIndex] = newItemData[i];	
 					tableVal += "<div class='detail-header-search' style='width:900px;'><ul>";
 					tableVal +="<li style='width:20px;'>";
 					tableVal += "<input type='checkbox' id='" + rowIndex + "' name='delHarvDate' value='" + harvestDate + "' />";
 					tableVal += "</li>";
 					tableVal += "<li class='topic-sr' style='text-align: center;width:100px; margin-right:-20px;'><a href=javascript:editHarvest("+ rowIndex + ",'" +harvestDate + "'," + totalHarvest + "," + remainSell + "," + consumeProduct + "," +  breedProduct + ");><font color=black>" + harvestDate + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width:150px;'><a href=javascript:editHarvest("+ rowIndex + ",'" + harvestDate + "'," + totalHarvest + "," + remainSell + "," + consumeProduct + "," +  breedProduct + ");><font color=black>" + formatNumber(totalHarvest) + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width:180px;'><a href=javascript:editHarvest("+ rowIndex + ",'" + harvestDate + "'," + totalHarvest + "," + remainSell + "," + consumeProduct + "," +  breedProduct + ");><font color=black>" + formatNumber(consumeProduct) + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width:180px;'><a href=javascript:editHarvest("+ rowIndex + ",'" + harvestDate + "'," + totalHarvest + "," + remainSell + "," + consumeProduct + "," +  breedProduct + ");><font color=black>" + formatNumber(breedProduct) + "</font></a></li>";
 				   tableVal += "<li class='topic-sr' style='text-align: right;width:180px;'><a href=javascript:editHarvest("+ rowIndex + ",'" + harvestDate + "'," + totalHarvest + "," + remainSell + "," + consumeProduct + "," +  breedProduct + ");><font color=black>" + formatNumber(remainSell) + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width:50px;'>";			                	
 					tableVal += "<a href=javascript:editHarvest(" + rowIndex + ",'" + harvestDate + "'," + totalHarvest + "," + remainSell+ "," + consumeProduct + "," +  breedProduct  + ");><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17' /></a>";
 					tableVal +=	"</li></ul></div>";
 				}
 			}
 		}
 		// summary
 		tableVal += "<div class='detail-header-search' style='width: 900px;background-color: #aaaaaa;'><ul>";
 		tableVal += "<li style='width:20px;'></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:100px; margin-right:-20px;'><b> สรุปยอดรวม </b></li>";
 		tableVal += "<li class='topic-sr' style='text-align: right;width:150px;'><b>"+ formatNumber(sumTotalHarvest) + "</b></li>";
 		// start : added by yatphiroon.p on 18/01/2017
 		tableVal += "<li class='topic-sr' style='text-align: right;width:180px;'><b>"+ formatNumber(sumConsumeProduct) + "</b></li>";
 		tableVal += "<li class='topic-sr' style='text-align: right;width:180px;'><b>"+ formatNumber(sumBreedProduct) + "</b></li>";
 		 // finish : added by yatphiroon.p on 18/01/2017
 		tableVal += "<li class='topic-sr' style='text-align: right;width:180px;'><b>"+ formatNumber(sumRemainSell) + "</b></li>";
 		tableVal += "<li class='topic-sr' style='text-align: right;width:50px;'></li></ul></div>";
 		return tableVal; 
 }
 // selling table
 function generateSaleTable(data)
 {
  		var tableVal = "<div class='topic-header-search' style='width:900px;'>";
 		tableVal += "<ul><li style='width:5px;'>";
 		tableVal +=	"<input type='checkbox' name='delSaleDate' onclick='handleClick(this);' /></li>";
 		tableVal += "<li class='topic-sr' style='text-align: right;width:100px;'><font style='font-size: 19px;'>วันที่ขายผลผลิต</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:110px;'><font style='font-size: 19px;'>ผลผลิตที่ขาย (กก.)</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:170px;'><font style='font-size: 19px;'>ราคาขายผลผลิต ต่อ กก.(บาท)</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:140px;'><font style='font-size: 19px;'>จำนวนเงินที่ขายได้</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:260px;'><font style='font-size: 19px;'>ชื่อผู้ซื้อ</font></li>";
 		tableVal += "<li class='manage-sr' style='width:50px;'></li></ul></div>";
 		var sumSaleCrop = 0;
 		var sumSalePrice = 0;
 		var sumSaleAmount = 0;
 		
 		var sumSaleDryCrop = 0;
 		var sumSaleDryPrice = 0;
 		var sumSaleDryAmount = 0;
 		
 		
 		var sumHumid = 0;
 		var sumHumidDry = 0;
 		
 		 if(data !=null && data.length > 0){
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			subSaleArray.length = newItemData.length; // set length to subSale array
 			//saleDetailArray.length = newItemData.length; 
 			for(var i=0; i< newItemData.length;i++){
 			   if(newItemData[i] != "" && newItemData[i] != undefined && newItemData[i] != "undefined"){
 					var rowIndex = newItemData[i].split(",")[0];
 					var saleDate = newItemData[i].split(",")[1];
 	   				var saleCrop = newItemData[i].split(",")[2];
 	   				cropArray[rowIndex] = saleCrop;
 	   				sumSaleCrop  += Number(saleCrop);
        			var salePrice = newItemData[i].split(",")[3];	
        			sumSalePrice += Number(salePrice);
 	   				var saleAmount = newItemData[i].split(",")[4];	
 	   				sumSaleAmount += Number(saleAmount);
 	   				
        			var buyerId = newItemData[i].split(",")[5];
					var buyerName = newItemData[i].split(",")[6];
        			if(buyerName != undefined && buyerName != "" && buyerName != "undefined")
        				buyerName = buyerName.replace(/ /g, '\u00a0');
					
 			   		var buyerAddress = newItemData[i].split(",")[7];
 			    	if(buyerAddress == "null" || buyerAddress == undefined || buyerAddress == null)
 			        	buyerAddress = "";
 			     	if(buyerAddress != undefined && buyerAddress != "" && buyerAddress != "undefined")
 			    		buyerAddress = buyerAddress.replace(/ /g, '\u00a0');
 					
					var provNo = newItemData[i].split(",")[8];
 					var distNo = newItemData[i].split(",")[9];
 					var subDistNo = newItemData[i].split(",")[10];
 					
 					
 					// --- start : added new on 27/12/2016 ---
 					var saleDryCrop = newItemData[i].split(",")[11];
 	   				cropArray[rowIndex] = saleDryCrop;
 	   				sumSaleDryCrop  += Number(saleDryCrop);
 	   				
        			var saleDryPrice = newItemData[i].split(",")[12];	
        			sumSaleDryPrice += Number(saleDryPrice);
        			
 	   				var saleDryAmount = newItemData[i].split(",")[13];	
 	   				sumSaleDryAmount += Number(saleDryAmount);
 					// --- finish: added new on 27/12/2016 ---
 					
 					
 					var humidDry = newItemData[i].split(",")[14];	
        			sumHumidDry += Number(humidDry);
        			
 	   				var humid = newItemData[i].split(",")[15];	
 	   				sumHumid += Number(humid);
 					
 					
 			    	subSaleArray[rowIndex] = newItemData[i];
 			    	
 			    	// Total sale crop = ผลผลิตที่ขาย (กก.)
 			    	var totalSaleCrop = Number(saleCrop) + Number(saleDryCrop);
 			    	// Total sale price = ราคาขายผลผลิต ต่อ กก.(บาท)
 			    	var totalSalePrice = Number(salePrice) + Number(saleDryPrice);
 			    	// Total Sale Amount = จำนวนเงินที่ขายได้
 			    	var totalSaleAmount = Number(saleAmount) + Number(saleDryAmount);
 			    	
 			    	var key = rowIndex + "" + saleDate;
 			    	if(!arraySelling.hasOwnProperty(key))
 	 	       		 	arraySelling[key] = rowIndex;
   	   	        	
 					tableVal += "<div class='detail-header-search' style='width:900px;'><ul>";
 					tableVal +="<li style='width:5px;'>";
 					tableVal += "<input type='checkbox' id='"+rowIndex+"' name='delSaleDate' value='" + saleDate + "' />";
 					tableVal += "</li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width:100px; margin-right:-20px;'><a href=javascript:editSale("+ rowIndex + "," +provNo + "," + distNo + "," + subDistNo + ",'" + saleDate + "'," + saleCrop + "," + salePrice + "," + saleAmount + "," + buyerId + ",\"" + buyerName + "\",\"" + buyerAddress + "\"" + "," + saleDryCrop + "," + saleDryPrice + "," + saleDryAmount + "," + humidDry + "," + humid + ");><font color=black>" + saleDate + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width:110px;'><a href=javascript:editSale("+ rowIndex + "," +provNo + "," + distNo + "," + subDistNo + ",'" + saleDate + "'," + saleCrop + "," + salePrice + "," + saleAmount + "," + buyerId + ",\"" + buyerName + "\",\"" + buyerAddress + "\"" + "," + saleDryCrop + "," + saleDryPrice + "," + saleDryAmount + "," + humidDry + "," + humid  + ");><font color=black>" + formatNumber(totalSaleCrop) + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width:170px;'><a href=javascript:editSale("+ rowIndex + "," +provNo + "," + distNo + "," + subDistNo + ",'" + saleDate + "'," + saleCrop + "," + salePrice + "," + saleAmount + "," + buyerId + ",\"" + buyerName + "\",\"" + buyerAddress + "\"" + "," + saleDryCrop + "," + saleDryPrice + "," + saleDryAmount + "," + humidDry + "," + humid  + ");><font color=black>" + formatNumber(totalSalePrice) + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width:140px;'><a href=javascript:editSale("+ rowIndex + "," +provNo + "," + distNo + "," + subDistNo + ",'" + saleDate + "'," + saleCrop + "," + salePrice + "," + saleAmount + "," + buyerId + ",\"" + buyerName + "\",\"" + buyerAddress + "\"" + "," + saleDryCrop + "," + saleDryPrice + "," + saleDryAmount + "," + humidDry + "," + humid + ");><font color=black>" + formatNumber(totalSaleAmount) + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: center;width:250px;'><a href=javascript:editSale("+ rowIndex + "," +provNo + "," + distNo + "," + subDistNo + ",'" + saleDate + "'," + saleCrop + "," + salePrice + "," + saleAmount + "," + buyerId + ",\"" + buyerName + "\",\"" + buyerAddress + "\"" + "," + saleDryCrop + "," + saleDryPrice + "," + saleDryAmount + "," + humidDry + "," + humid + ");><font color=black>" + buyerName + "</font></a></li>";
 					tableVal += "<li class='topic-sr' style='text-align: right;width: 50px;'>";			                	
 					tableVal += "<a href=javascript:editSale(" + rowIndex + "," + provNo + "," + distNo + "," + subDistNo + ",'" + saleDate + "'," + saleCrop + "," + salePrice + "," + saleAmount + "," + buyerId  + ",\"" + buyerName + "\",\"" + buyerAddress + "\"" + "," + saleDryCrop + "," + saleDryPrice + "," + saleDryAmount+ "," + humidDry + "," + humid  + ");><img src='<%=request.getContextPath()%>/images/btn-edit.png' width='17' height='17' /></a>";
 					tableVal +=	"</li></ul></div>";
 				}
 			}
 		}
 		// summary
 		tableVal += "<div class='detail-header-search' style='width: 900px;background-color: #aaaaaa;'><ul>";
 		tableVal += "<li style='width:5px;'></li>";
 		tableVal += "<li class='topic-sr' style='text-align: right;width: 100px; margin-right:-20px;'><font style='font-size: 19px;'><b>สรุปยอดรวม</b></font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: right;width: 110px;'><font style='font-size: 19px;'><b>" + formatNumber(Number(sumSaleCrop) + Number(sumSaleDryCrop)) + "</b></font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: right;width: 170px;'><font style='font-size: 19px;'><b>" + formatNumber(Number(sumSalePrice) + Number(sumSaleDryPrice)) + "</b></font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: right;width: 140px;'><font style='font-size: 19px;'><b>" + formatNumber(Number(sumSaleAmount) + Number(sumSaleDryAmount)) + "</b></font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width: 250px;'></li>";
 		tableVal += "<li class='manage-sr' style='width:50px;'></li></ul></div>";
 		return tableVal; 
 }
 // landcheck Table
 function generateCheckTable(data)
 {
  		var tableVal = "<div class='topic-header-search' style='width:952px;'><ul>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:100px;'><font style='font-size: 19px;'>วันที่ตรวจ</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:100px;'><font style='font-size: 19px;'>วันที่บันทึก</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: left;width:160px;'><font style='font-size: 19px;'>ระยะเวลาการตรวจ</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:150px;'><font style='font-size: 19px;'>ประเภทเอกสารสิทธิ์</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:150px;'><font style='font-size: 19px;'>เลขที่เอกสารสิทธิ์</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:160px;'><font style='font-size: 19px;'>เจ้าหน้าที่ตรวจ</font></li>";
 		tableVal += "<li class='topic-sr' style='text-align: center;width:80px;'><font style='font-size: 19px;'>ผลการตรวจ</font></li>";
 		tableVal += "<li class='manage-sr' style='width:50px;'></li></ul></div>";
 		
 		 if(data !=null && data.length > 0){
 		 
		 		if(data.indexOf('|') > -1){
		 			data = data.substr(0);
		 		}
		 		 		 
 			var newItemData= new Array();
 			newItemData = data.split("|");
 			for(var i=0; i< newItemData.length;i++){
 			   if(newItemData[i] != "" && newItemData[i] != undefined && newItemData[i] != "undefined"){
 					var rowIndex = newItemData[i].split(",")[0];
 					var landCheckId = newItemData[i].split(",")[1];
 	   				var checkDate = newItemData[i].split(",")[2];
        			var memoDate = newItemData[i].split(",")[3];	
 	   				var checkPeriod = newItemData[i].split(",")[4];	
        			var typeId = newItemData[i].split(",")[5];
        			var typeName = newItemData[i].split(",")[6];
					var docNo = newItemData[i].split(",")[7];
					var checkedBy = newItemData[i].split(",")[8];
					var result = newItemData[i].split(",")[9];
					 if(result == 'P')
					    result = "ผ่าน";
					 else 
					    result = "ไม่ผ่าน";
 					tableVal += "<div class='detail-header-search' style='width:950px;'><ul>";
 					tableVal += "<li class='topic-sr' style='text-align: center;width:100px;'><font color=black>" + checkDate + "</font></li>";
 					tableVal += "<li class='topic-sr' style='text-align: center;width:100px;'><font color=black>" + memoDate + "</font></li>";
 					tableVal += "<li class='topic-sr' style='text-align: left;width:160px;'><font color=black>" + checkPeriod + "</font></li>";
 					tableVal += "<li class='topic-sr' style='text-align: center;width:150px;'><font color=black>" + typeName + "</font></li>";
 					tableVal += "<li class='topic-sr' style='text-align: center;width:150px;'><font color=black>" + docNo + "</font></li>";
 					tableVal += "<li class='topic-sr' style='text-align: center;width:160px;'><font color=black>" + checkedBy + "</font></li>";
 					tableVal += "<li class='topic-sr' style='text-align: center;width:80px;'><font color=black>" + result + "</font></li>";
 					tableVal += "<li class='topic-sr' style='width: 50px;'>";			                	
 					tableVal +=	"</li></ul></div>";
 				}
 			}
 		}
 		
 		
 		return tableVal; 
 }
 // -------- finish generate table zone --------//

</script>

</head>
<body onload="initPageLoad('<%=LAND_CHECK_PAGE%>');">
<dcs:validateForm formName="plantForm" formAction="Plant.do" formBean="PlantForm" formMethod="Post">
<input type="hidden" name="cmd" value="<%= cmd %>" />
<input type="hidden" name="status" value="<%= status %>"/>
<input type="hidden" name="plantId" />
<input type="hidden" name="docAndType" />
<input type="hidden" name="refPlantId" value="<%=refPlantId %>" />
<input type="hidden" name="detailItem" />  
<input type="hidden" name="landrightItem" />
<input type="hidden" name="assetItem" />
<input type="hidden" name="familyItem" />
<input type="hidden" name="landCheckItem" />
<div id="Wait" style="BORDER-RIGHT: grey thin solid; BORDER-TOP: grey thin solid; VISIBILITY: hidden; Z-INDEX: 1; LEFT: 470px;  BORDER-LEFT: grey thin solid; WIDTH: 420px; BORDER-BOTTOM: grey thin solid; POSITION: absolute; TOP: 380px; HEIGHT: 180px; background-color:#ffffff;">
       <table bgColor=#ffffff border=0>
         <tr><td vAlign="bottom" align="center" width=420 height=90> 
             <img src="<%=request.getContextPath()%>/images/wait.gif" border=0>
             </td> </tr>
         <tr><td vAlign='top' align="center" width=420 height=50 >
                                                 กรุณารอสักครู่ ..........
             </td></tr>
        </table>
</div>

<div class="main-inside">
	<%@include file="/header.jsp" %>
    <div class="navigator">
    <div class="inside">
	 	<ul>     
            <li><a style="color:#333" href='<%=request.getContextPath()%>/UserList.do'>หน้าหลัก</a></li>
            <li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
            <li><a style="color:#333" href='<%=request.getContextPath()%>/PlantList.do'>ข้อมูลเกษตรกรผู้เพาะปลูกพันธุ์พืช</a></li>
			<li><img src="<%=request.getContextPath()%>/images/arrow-navigator.png" width="4" height="8" /></li>
           <li>ข้อมูลการเพาะปลูก</li>
	    </ul>
	</div>
    </div>
    <!-- content -->
    <div class="content-color">
    <br />
    	<div class="inside" style="height:900px;">
    			<div class="content-keyin">
    				  <div id="i_containTab">  
				        <ul id="navi_containTab">  
				            <li class="tabNavi1" style="line-height: 32px; background-color: #C4C4C4;">ข้อมูลเกษตรกร</li>  
				            <li class="tabNavi2" style="line-height: 32px;">สมาชิกในครัวเรือน</li>  
				            <li class="tabNavi3" style="line-height: 32px;">ข้อมูลพื้นที่เพาะปลูก</li>  
				            <li class="tabNavi4" style="line-height: 32px;">ข้อมูลการเพาะปลูกพืช</li>
				        </ul>  
				        <ul id="detail_containTab">  
				            <li class="detailContent1">
				            <fieldset style="width: 1010px;">
    				 <fieldset style="width: 1000px;">
    				 <legend><font size="5px;"><b>:: ข้อมูลเกษตรกรผู้เพาะปลูกพืช ::</b></font></legend>
    				 
				    <div class="line-input-keyin">
                    	<ul>
		                    <li class="topic" style="width: 120px;padding-right: 0px;">เลขที่บัตรประชาชน :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
		                    <li class="boxinput" style="width: 150px;">
						    <dcs:validateText name="idCard" property="idCard" maxlength="13" style="width: 145px;" onChange="checkIdCard(this);"/>
                        	</li>
                    
	                    	<li class="topic" style="width: 90px;padding-right: 0px;">คำนำหน้า :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
		                    <li class="boxinput" style="width: 152px;">
		                    <dcs:validateDropDown name="abbrPrefix" property="abbrPrefix" dataSource="<%= prefixList %>" keyField="abbrPrefix" displayField="abbrPrefix"  isRequire="true" style="width: 152px;height: 29px;" />
                        	</li>
                        	
	                    	<li class="topic"style="width: 70px;padding-right: 0px;">ชื่อ : </li>
		                    <li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
		                    <li class="boxinput" style="width: 135px;">
						    <dcs:validateText name="firstName" property="firstName" maxlength="30" style="width: 130px;" onChange="checkInvalidText(this);" />
                        	</li>
                        	
                        	<li class="topic" style="width: 85px;padding-right: 0px;">นามสกุล : </li>
                        	<li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
		                    <li class="boxinput"style="width: 110px;">
						    <dcs:validateText name="lastName" property="lastName" maxlength="30" style="width: 110px;" onChange="checkInvalidText(this);" />
                        	</li>
                        </ul>
    				</div>
	    			<div class="line-input-keyin">
                    	<ul>
	                    	<li class="topic" style="width:120px;padding-right: 0px;">ที่อยู่ :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
		                    <li class="boxinput" style="width: 70px;">
						    <dcs:validateText name="addressNo" property="addressNo" maxlength="50" style="width: 60px;"/>
                        	</li>
	                    	<li class="topic"style="width: 40px;padding-right: 5px;">หมู่ :</li>
		                    <li class="boxinput" style="width: 40px;">
						    <dcs:validateText name="moo" property="moo" maxlength="3" style="width: 30px;" onChange="checkMoo(this);"/>
                        	</li>
                        	
                        	<li class="topic" style="width: 85px;padding-right: 0px;">หมู่บ้าน :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
		                    <li class="boxinput" style="width: 150px;">
						    <dcs:validateText name="village" property="village" maxlength="200" style="width: 145px;" onChange="checkInvalidText(this);" />
                        	</li>
                        	
                        	<li class="topic" style="width: 72px;padding-right: 0px;">ซอย :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
		                    <li class="boxinput" style="width: 130px;">
						    <dcs:validateText name="soi" property="soi" maxlength="200" style="width: 130px;" onChange="checkInvalidText(this);" />
                        	</li>
                        	
                        	<li class="topic" style="width: 90px;padding-right: 0px;">ถนน :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
		                    <li class="boxinput" style="width: 110px;">
						    <dcs:validateText name="street" property="street" maxlength="200" style="width: 110px;" onChange="checkInvalidText(this);" />
                        	</li>
                        </ul> 
    				</div>
    				<div class="line-input-keyin">
                    	<ul>
                    		<li class="topic"style="width: 120px;padding-right: 0px;">จังหวัด :</li>
                  		   <li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
		                    <li class="boxinput"style="width: 155px;">
							<select id="provinceNo"  name="provinceNo" style="width: 155px;" onChange="getDistrictInfo('provinceNo', 'districtNo', 'subDistrictNo', 'postCode');">
		                    <option value="">กรุณาเลือก</option>
		                    	<% if(provinceList !=null && provinceList.size()>0){ 
		                    		for(int p=0; p < provinceList.size(); p++){
		                   				Province pd = (Province)provinceList.get(p);
		                   		 %>
		                   		 	<option value="<%=pd.getProvinceNo()%>"><%=pd.getThaiName()%></option>
		                   		 <% }} %>
		                    </select>
                        	</li>
                        	<li class="topic"style="width: 85px;padding-right: 0px;">อำเภอ :</li>
                        	<li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
		                    <li class="boxinput"style="width: 152px;">
						    <select id="districtNo"  name="districtNo" style="width: 152px;" onChange="getSubDistrictInfo('provinceNo','districtNo','subDistrictNo', 'postCode');">
		                    </select>
						    </li>
                        	<li class="topic"style="width: 70px;padding-right: 0px;">ตำบล :</li>
                  		    <li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
		                    <li class="boxinput"style="width: 140px;">
						    <select id="subDistrictNo"  name="subDistrictNo" style="width: 140px;" onchange="getPostCodeInfo('provinceNo','districtNo','subDistrictNo','postCode');">
		                    </select>	
						    </li>
                    	
	                    	<li class="topic" style="width: 80px;padding-right: 0px;">รหัสไปรษณีย์ :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
		                    <li class="boxinput" style="width: 117px;">
						    <dcs:validateText name="postCode" property="postCode" maxlength="5" style="width: 110px;" isReadOnly="true" /> 
                        	</li>	
                        </ul> 
    				 </div>
	                 <div class="line-input-keyin">
	                 	<ul>
	                    	<li class="topic"style="width: 120px;padding-right: 0px;">เบอร์มือถือ :</li>
	                    	<li class="topic" style="width: 2px;padding-left: 2px;"></li>  
		                   	<li class="boxinput"style="width: 155px;">
					 		<dcs:validateText name="mobile" property="mobile" maxlength="10" style="width: 145px;" onChange="checkTel(this);" />
                        	</li>
	                    	
	                    	<li class="topic" style="width: 85px;padding-right: 0px;">เบอร์บ้าน :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
		                    <li class="boxinput" style="width: 152px;">
						    <dcs:validateText name="tel" property="tel" maxlength="30" style="width: 145px;"  onChange="checkTel(this);"/>
                        	</li>
                        	
	                    	<li class="topic" style="width: 70px;padding-right: 0px;">E-mail :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
		                    <li class="boxinput" style="width:345px">
						    <dcs:validateText name="email" property="email" maxlength="60" style="width: 344px;" onChange="checkEmailFormat(this);" />
                        	</li>              	                        	
	                    </ul>
	                 </div>
	                 <div class="line-input-keyin">
	                 	<ul>
	                    	<li class="topic"style="width: 120px;padding-right: 0px;">รายได้ :</li>
	                    	<li class="topic" style="width: 2px;padding-left: 2px;"></li>  
		                   	<li class="boxinput" style="width: 155px;">
					 			<input type="text" id="income" name="income" maxlength="13" style="width: 145px;" onClick="this.select();" onChange="checkNumberComma(this);" />
                        	</li>
	                    	<li class="topic" style="width: 77px; padding-right: 0px;">บาท/เดือน</li>
	                    </ul>
	                 </div>
	                 <div class="line-input-keyin">
	                    <ul>
	                    	<li class="topic" style="width: 120px;padding-right: 0px;"></li>
	                    	<li class="topic" style="width: 1px;"></li>
	                        <li class="boxinput" style="width: 155px;">
	                        <input type ="checkbox" id="notMember" name="notMember" style="width: 15px;" onclick="isMember(this);"/>
	                        <label for="notMember">&nbsp;ไม่สังกัดกลุ่มเกษตรกร</label></li>                    	
	                    </ul>
	                  </div>	
	                  <div class="line-input-keyin">
	                    <ul>
	                    	<li class="topic" style="width: 120px;padding-right: 0px;"></li>
	                    	<li class="topic" style="width: 1px;"></li>
	                        <li class="boxinput" style="width: 120px;">
	                        <input type ="checkbox" id="farmerGroupMember" name="farmerGroupMember" style="width:15px;" onclick="isMember(this);"/>
	                        <label for="farmerGroupMember">&nbsp;กลุ่มเกษตรกร</label></li>   
	                        <li class="topic" style="width: 10px;">&nbsp;</li>
	                        
	                        <li class="topic" style="width: 105px;padding-right: 0px;">ชื่อกลุ่มเกษตรกร :</li>
                  			<li class="topic" style="width: 1px;padding-left: 1px;"></li>
		                    <li class="boxinput" style="width: 152px;">
	                        <dcs:validateDropDown name="farmerGroupId" property="farmerGroupId" dataSource="<%= farmerGroupList %>" keyField="farmerGroupId" displayField="farmerGroupName"  isRequire="true" style="width:152px;" onChange="getFarmerGroupChild('farmerGroupId', 'childFarmerGroupId', 'childFarmerGroupName');"/>                      		
	                        </li>   
	                        <li class="topic" style="width: 80px;padding-right: 0px;">เลขที่สมาชิก :</li>
                  			<li class="topic" style="width: 0px;padding-left: 0px;"></li>
		                    <li class="boxinput" style="width: 120px;">
	                        	<dcs:validateText name="customerFarmerGroupNo" property="customerFarmerGroupNo" maxlength="50" style="width: 120px;"/>
	                        </li>	
	                        <li class="topic" style="width: 120px;padding-right: 0px;">เป้าหมายการผลิต :</li>
                  			<li class="topic" style="width: 1px;padding-left: 1px;"></li>
		                    <li class="boxinput" style="width: 100px;">
	                        	<dcs:validateText name="target" property="target" maxlength="14" style="width: 60px;" onChange="checkNumberComma(this);"/>&nbsp;ตัน
	                        </li>       	
	                    </ul>
	                  </div>
	                   <div class="line-input-keyin">
	                    <ul>
	                    	<li class="topic" style="width: 120px;padding-right: 0px;"></li>
	                    	<li class="topic" style="width: 1px;"></li>
	                        <li class="boxinput" style="width: 120px;">&nbsp;</li>
	                    	<li class="topic" style="width: 10px;">&nbsp;</li>
	                    	
	                        <li class="topic" style="width: 105px;padding-right: 0px;">สังกัดสหกรณ์ : </li>
                  			<li class="topic" style="width: 1px;padding-left: 1px;"></li>
		                    <li class="boxinput" style="width: 250px;">
		                         <dcs:validateText name="childFarmerGroupName" property="childFarmerGroupName" maxlength="5" style="background-color: white;border: 0px;width:250px;" isReadOnly="true" /> 
		                     </li>    
		                    <li class="topic" style="width: 103px;padding-right: 0px;"></li>
                  			<li class="topic" style="width: 1px;padding-left: 1px;"></li>
		                    <li class="boxinput" style="width: 100px;">
		                         <dcs:validateText name="childFarmerGroupId" property="childFarmerGroupId" style="background-color: white;border: 0px;width: 50px;visibility: hidden;" isReadOnly="true"  />
		                    </li>
	                    </ul>
	                  </div>
	                   <div class="line-input-keyin">
	                    <ul>
	                    	<li class="topic" style="width: 120px;padding-right: 0px;"></li>
	                    	<li class="topic" style="width: 1px;"></li>
	                        <li class="boxinput" style="width: 120px;">&nbsp;</li>
	                    	<li class="topic" style="width: 10px;">&nbsp;</li>
                            <li class="topic"style="width: 105px;padding-right: 0px;">ประเภทสมาชิก :</li>
                            <li class="topic" style="width: 1px;"></li>
                            <li class="boxinput"style="width: 70px;">
                                <input type="radio" id="memberType1" name="memberType" style="width:15px;" value="N"/><label for="memberType1">&nbsp;ปกติ</label>
                            </li>
                            <li class="boxinput"style="width: 70px;">
                                <input type="radio" id="memberType2" name="memberType" style="width:15px;" value="S"/><label for="memberType2">&nbsp;สมทบ</label>
                            </li>
	                    </ul>
	                  </div>
	                </fieldset>
	               <br>
	               <fieldset style="width: 990px;">
    				 <legend><font size="5px;"><b>:: ข้อมูลบัญชีธนาคาร ::</b></font></legend>
                    <div class="line-input-keyin">
	    					<ul>
			                    <li class="topic" style="width: 120px;padding-right: 0px;">ชื่อบัญชี :</li>
                  				<li class="topic" style="width: 2px;padding-left: 2px;"></li>
			                    <li class="boxinput" style="width: 150px;">
	                        	<dcs:validateText name="accountName" property="accountName" style="width: 150px;" maxlength="50" onChange="checkInvalidText(this);" />
	                        	</li>
	                        	
	                        	<li class="topic" style="width: 80px;padding-right: 0px;">เลขที่บัญชี :</li>
                  				<li class="topic" style="width: 2px;padding-left: 2px;"></li>
			                    <li class="boxinput" style="width: 150px;">
	                        	<dcs:validateText name="accountNo" property="accountNo" style="width: 150px;" maxlength="50" onChange="checkNumber(this);" />
	                        	</li>
	                        	<li class="topic"style="width: 70px;padding-right: 0px;">ธนาคาร :</li>
                  				<li class="topic" style="width: 2px;padding-left: 2px;"></li>
			                    <li class="boxinput" style="width: 150px;">
	                        		<select id="bankId"  name="bankId" style="width:150px;" onchange="getBankBranchList('bankId','branchCode');">
		                   			<option value="0">กรุณาเลือก</option>
		                   			<% if(bankList != null && bankList.size() > 0){
		                   				for (int b=0; b < bankList.size(); b++){
		                    				Bank bank = (Bank)bankList.get(b);
		                   			 %>
		                   			<option value="<%=bank.getBankId()%>"><%= bank.getBankName()%></option>
		                   			<% }} %>
		                    		</select>
	                        	</li>
							    <li class="topic" style="width: 50px;padding-right: 0px;">สาขา :</li>
                  				<li class="topic" style="width: 2px;padding-left: 2px;"></li>
			                	<li class="boxinput" style="width: 150px;">
								<select id="branchCode"  name="branchCode" style="width: 150px;">
		                   			<option value="0">กรุณาเลือก</option>
		                    	</select>
	                        	</li>
	                    	</ul>
	                    </div>	
	                  </fieldset>
    				<br>
    				<fieldset style="width: 990px;">
    				<legend><font size="5px;"><b>:: ข้อมูลการลงทะเบียนเกษตรกรผู้เพาะปลูกพืช ::</b></font></legend>
                    <div class="line-input-keyin">
	    				<ul>
	    					    <li class="topic" style="width: 135px;padding-right: 0px;">พืชที่ปลูก :</li>
                  				<li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
			                    <li class="boxinput" style="width: 152px;">
	                        	<select id="breedTypeId"  name="breedTypeId" style="width: 149px;" onChange="getBreedGroupInfo('breedTypeId', 'breedGroupId');">
	                        		<option value="">กรุณาเลือก</option>
		                    		<%if(breedTypeList !=null && breedTypeList.size()>0){
		                    			for(int b=0; b<breedTypeList.size(); b++){
		                    				BreedType breed = (BreedType)breedTypeList.get(b);
		                    		 %>
		                    		 	<option value="<%= breed.getBreedTypeId()%>"><%= breed.getBreedTypeName()%></option>
		                    		 <%}} %>
		                        </select>
	                        	</li>
	                        	<li class="topic"style="width: 100px;padding-right: 0px;">ฤดูการปลูก :</li>
                  				<li class="topic" style="width: 2px;padding-left: 2px;"></li>
			                    <li class="boxinput" style="width: 152px;">
	                        		<select id="periodPlant"  name="periodPlant" style="width: 149px;" disabled="disabled" onChange="setPlantNo('periodPlant','plantNo');" >
	                        			<option value="0">กรุณาเลือก</option>
		                    			<% for(int b=0; b < PERIOD_PLANT.split(",").length; b++){ %> 	
		                    				<option value="<%= PERIOD_PLANT.split(",")[b]%>"><%= PERIOD_PLANT.split(",")[b] %></option>
		                    			<% } %>
		                        	</select>
	                        	</li>
	                        	<li class="topic"style="width: 80px;padding-right: 0px;">ปีการผลิต :</li>
                  				<li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
			                    <li class="boxinput" style="width: 152px;">
	                        	<dcs:validateDropDown name="plantYear"  property="plantYear"  optionList="<%=yearList %>"   optionValueList="<%=yearList %>" isRequire="true" style="width: 139px;" />
	                        	</li>
	                        	
	                        	<li class="topic" style="width: 50px;padding-right: 0px;">ครั้งที่ :</li>
                  				<li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
			                    <li class="boxinput"style="width: 90px;"> 
							    <dcs:validateText name="plantNo" property="plantNo" maxlength="1" style="width: 80px;" onChange="checkNumber(this);setPeriodPlant('periodPlant','plantNo');" />
	                        	</li>
	                    	</ul>
	                   </div>	
	                    
	                   <div class="line-input-keyin">
	                      <ul>
	                    	<li class="topic"style="width: 135px;padding-right: 0px;">เลขที่ใบรับรองเกษตรกร :</li>
                  			<li class="topic" style="width: 1px;padding-left: 2px;"></li>
			                <li class="boxinput" style="width: 142px;">
	                        	<dcs:validateText name="certificateFarmerNo" property="certificateFarmerNo" maxlength="50" style="width: 142px;" />
	                        </li>
	                    	
	                    	<li class="topic" style="width: 110px;padding-right: 0px;">เลขที่ลงทะเบียน :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
			                <li class="boxinput"style="width: 152px;">
							    <dcs:validateText name="registrationNo" property="registrationNo" maxlength="30" style="width: 142px;" />
	                        </li>	
	                    	
			                <li class="topic" style="width: 80px;padding-right: 0px;">เล่มที่ :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
			                <li class="boxinput" style="width: 142px;">
	                        	<dcs:validateText name="bookNo" property="bookNo" style="width: 132px;" maxlength="11" onChange="checkNumber(this);" />
	                        </li>
	                        	
	                        <li class="topic"style="width: 60px;padding-right: 0px;">เลขที่ :</li>
                  			<li class="topic" style="width: 2px;padding-left: 2px;"></li>
			                <li class="boxinput"style="width: 80px;">
							    <dcs:validateText name="documentNo" property="documentNo" style="width: 80px;" maxlength="30" />
						    </li>                                 	                        	
	                      </ul>
	                    </div>
                     <div class="line-input-keyin">
                        <ul>
                            <li class="topic"style="width: 135px;padding-right: 0px;">สมัครเข้าร่วมโครงการ :</li>
                            <li class="topic" style="width: 1px;padding-left:2px;"></li>
                            <li class="boxinput"style="width: 70px;">
                                <input type="radio" id="register1" name="register" style="width:15px;" value="1"/><label for="register1">&nbsp;ใช่</label>
                            </li>
                            <li class="boxinput"style="width: 70px;">
                                <input type="radio" id="register2" name="register" style="width:15px;" value="0"/><label for="register2">&nbsp;ไม่ใช่</label>
                            </li>
                            <li class="topic" style="width: 110px;padding-right: 0px;">วันที่เข้าร่วม :</li>
                            <li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
                            <li class="boxinput"style="width: 280px;">        
                                <dcs:validateDate name="projectDate" style="width: 132px;"  /><font color="red" >ตย. 31/12/2557</font>
                            </li>
                        </ul>
                     </div>
                        <div class="line-input-keyin">
                            <ul>
                                <li class="topic"style="width: 135px;padding-right: 0px;">ชื่อเจ้าหน้าที่รับสมัคร :</li>
                                <li class="topic" style="width: 2px;padding-left: 2px;"><font color="red">*</font></li>
                                <li class="boxinput"style="width: 130px;">
                                <dcs:validateText name="officerName" property="officerName" style="width: 130px;" maxlength="100" />
                                </li>     
                                
                               <li class="topic" style="width: 17px;"></li>
                               <li class="boxinput" style="width:55px;">
                               <input type ="radio" id="fta" name="fta" style="width:15px;" value = "1"/>
                               <label for="fta">&nbsp;FTA</label></li>   
                               
                               <li class="topic" style="width:90px;">
                               <input type ="radio" id="noneFta" name="fta" style="width:15px;" value = "0" checked="checked" />
                               <label for="noneFta">&nbsp;ไม่ใช่ FTA</label></li>   
                               <li class="topic" style="width: 2px;">&nbsp;</li>
                            </ul>
                        </div>

	                    <br />
	                    <div id="save_part">
			    		   <table align="center">
			    				<tr><td><a class="btn-save" href="#" onclick="savePlant();" id="write" style="margin-right: 0px;"></a></td>	
				    				 <td><a class="btn-cancel" href="#" onclick="cancelPlant();"></a></td>				           
			   					</tr>
			    		   </table>
			   		 	</div>
				     </fieldset>
				     </fieldset>
				 </li>
				    <li class="detailContent2">
				  <br>
				    <fieldset id="FamilyFieldSet" style="width: 990px;visibility: hidden;">
    				 <legend>:: รายชื่อสมาชิกในครัวเรือน ::</legend>
    				    <div class="btn-manage" id="FamilyButton" style="margin-left: 0px;padding-left: 50px;">
			 				<a class="btn-add" href="#" onclick="showWindow('FarmerFamilyPopup',0);"></a>
			 				<a class="btn-del" href="#" onclick="goDeleteFamily();"></a>
						</div>
    				    <br />
        				<div id="dynamicFamily" title="สมาชิกในครัวเรือน" style="padding-left: 50px;" >
       	    			</div>
       	    			<br />
       	    			<div id="FamilySavePart" style="padding-left: 300px;">
							<div class='btn-manage' align='center'>
								<a class="btn-save" href="#" onclick="savePlant();" id="write_FamilyTab" ></a>
								<a class="btn-cancel" href="#" onclick="exit();"></a>
							</div>
       	    			</div>
        				<br />
					</fieldset>
				  </li> 
				  <li class="detailContent3">
				  <br>
				    <fieldset style="width: 990px;">
    				 <legend>:: ข้อมูลพื้นที่เพาะปลูก ::</legend>
    				    <div class="btn-manage" style="margin-left: 0px;padding-left: 10px;">
			 				<a class="btn-add" href="#" onclick="showWindow('DocDetailPopup',0);"></a>
			 				<a class="btn-del" href="#" onclick="goDeleteDocDetail();"></a>
						</div>
    				    <br />
						<!-- start dynamic grid for land right Document -->
        				<div id="dynamicDocDetail" style="padding-left: 5px;">
       	    			</div>
       	    			<br />
       	    			<div id="DocDetailSavePart" style="padding-left: 300px;">
							<div class='btn-manage' align='center'>
								<a class="btn-save" href="#" onclick="savePlant();" id="write_DetailTab" ></a>
								<a class="btn-cancel" href="#" onclick="exit();"></a>
							</div>
       	    			</div>
        				<!-- finish dynamic grid for land right Document -->
        				<br />
					</fieldset>
				  </li> 
				<li class="detailContent4">
       			<!--  ข้อมูลการเพาะปลูกพืช-->
       			<br>
           		    <fieldset  style="width: 990px;">
            		<legend>:: ข้อมูลการเพาะปลูกพืช ::</legend>
            		<div class="btn-manage" style="margin-left: 0px;padding-left: 10px;">
			   			<a class="btn-add" href="#" onclick="showWindow('PlantBreedPopup',0);"></a>
			   			<a class="btn-del" href="#" onclick="goDeleteBreed();"></a>
		   			</div>
		   			<br/>
	         		<div id="dynamicPlantBreed" style="width: 960px;padding-left: 5px;">
	         		</div>
	         		<br />
	         		<div id="DocPlantBreedSavePart" style="padding-left: 300px;">
							<div class='btn-manage' align='center'>
								<a class="btn-save" href="#" onclick="savePlant();" id="write_BreedTab"></a>
								<a class="btn-cancel" href="#" onclick="exit();"></a>
							</div>
       	    			</div>
					<br />
	           </fieldset>		
    	  </li>
    	  </ul>  
	</div>  	
    </div>
  </div>
    <br/>
<!-- ------- FarmerFamily --------- -->
<div id="FarmerFamilyPopup" title="สมาชิกในครัวเรือน" style="VISIBILITY:hidden;display:none;">
	   <table id="familyTable" style="width: 490px;padding: 5px;">
	   <tr><td style="padding-left: 5px;text-align: right;">เลขที่บัตรประชาชน : <font color="red" >*</font></td>
		   	<td>&nbsp;</td>
		    <td><dcs:validateText name="familyIdCard" property="familyIdCard" maxlength="13"  style="width:180px;" onChange="checkFamilyIdCard(this);" /></td>
 	  </tr><tr><td style="padding-left: 5px;text-align: right;">ชื่อ : <font color="red" >*</font></td>
		   	<td>&nbsp;</td>
		    <td><dcs:validateText name="familyFirstName" property="familyFirstName" maxlength="30"  style="width:180px;" onChange="checkInvalidText(this);" /></td>
	  </tr><tr><td style="padding-left: 5px;text-align: right;">นามสกุล : <font color="red" >*</font></td>
		    <td>&nbsp;</td>
		    <td><dcs:validateText name="familyLastName" property="familyLastName" maxlength="30"  style="width:180px;" onChange="checkInvalidText(this);" /></td>
	  </tr>
	  </table>
	  <table id="familyButton" style="width: 490px;padding: 5px;"><tr>
		   <td colspan="3" style="padding-left: 30px;">
		   		<img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" style="padding-left: 100px;" onclick="javascript:saveFamily();" />
		   		<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="javascript:closeWindow('FarmerFamilyPopup',0);"/>
		   </td> </tr>
   	  </table>
 </div>  
<!-- ------- PlantBreedPopup --------- -->    
<div id="PlantBreedPopup" title="การเตรียมพันธุ์พืช"  style="VISIBILITY:hidden;display:none;">
<!--  ******************************** 5tabs -->
<div id="i_containBreedTab" >
			 <ul id="navi_containBreedTab">
       			 <li class="tabBreed1" style="line-height: 32px; background-color: #C4C4C4;">ข้อมูลพืชที่เพาะปลูก</li>
       			 <li class="tabBreed2" style="line-height: 32px;">ข้อมูลการใส่ปุ๋ย</li>
        		 <li class="tabBreed3" style="line-height: 32px;">ข้อมูลการใช้สารเคมี</li>
       			 <li class="tabBreed4" style="line-height: 32px;">ข้อมูลทรัพย์สิน</li>
        		 <li class="tabBreed5" style="line-height: 32px;">แจ้งล่วงหน้าก่อนเก็บเกี่ยว</li>
   			 </ul>
   			  <ul id="detail_containBreedTab">
   			     <!--  tabBreed1 is clicked -->
       			 <li class="detailBreed1">
       			 <fieldset style="width: 950px;" >
				 <div class="line-input-keyin">
       			  	<ul>
                    	<li class="topic"style="width: 200px;text-align: left;"><font style="font-size: 20px;"> ข้อมูลพืชที่เพาะปลูกของ : </font></li>
                    	<li class="topic" style="width: 1px;padding-right: 5px"></li>  
                   		<li class="boxinput"style="width: 200px;"><font style="font-size: 20px;"><label id="lblBreedFarmerName"></label></font></li>
                    </ul>
       			  </div>
       			 <fieldset style="width: 930px;">
            		<legend><b>::ข้อมูลพันธุ์พืช::</b></legend>
       			 <div class="line-input-keyin">
                    <ul>
                    	<li class="topic" style="width: 80px;padding-right: 0px;text-align: left;"><font style="font-size: 20px;">เอกสารสิทธิ์ :</font></li>
                  		<li class="topic" style="width: 1px;padding-left: 3px;"><font color = 'red'>*</font></li>  
		                <li class="boxinput" style="width:135px;">
							<select id="landRightTypeId"  name="landRightTypeId"  style="width: 135px;" onchange="getValueFromLandright(this);">
		    				<option value="">กรุณาเลือก</option>
							</select>
                        </li>
                        <li class="topic"style="width: 107px;padding-left: 8px;text-align: left;"><font style="font-size: 20px;">เลขที่ :</font></li>
                        <li class="topic" style="width: 1px;padding-left: 3px;"><font color = 'red'>*</font></li> 
		                <li class="boxinput"style="width: 130px;">
						    <dcs:validateText name="landDocNo" property="landDocNo" maxlength="60"  style="width: 70px;" onChange="checkInvalidText(this);" />
						</li>
                        <li class="topic" style="width: 120px;padding-left: 5px;text-align: left;"><font style="font-size: 20px;">ขนาดที่ดิน :</font></li>
                  		<li class="topic" style="width: 1px;padding-left: 3px;">&nbsp;</li>  
		                <li class="boxinput" style="width: 270px;">
						    <dcs:validateText name="landDocRai" property="landDocRai" maxlength="60"  style="width: 50px;" onChange="checkNumber(this);" />&nbsp;<font style="font-size: 20px;">ไร่</font>&nbsp;
				 			<dcs:validateText name="landDocNgan" property="landDocNgan" maxlength="60"  style="width: 50px;" onChange="checkNumber(this);" />&nbsp;<font style="font-size: 20px;">งาน</font>&nbsp;
				 			<dcs:validateText name="landDocWah" property="landDocWah" maxlength="60"  style="width: 50px;" onChange="checkNumber(this);" />&nbsp;<font style="font-size: 20px;">วา</font>
					    </li>
                     </ul> 
    			 </div>
       			 <div class="line-input-keyin">
                    	<ul>
                    		<li class="topic" style="width: 80px;padding-right: 0px;text-align: left;"><font style="font-size: 20px;">พันธุ์ที่ปลูก :</font></li>
                  		    <li class="topic" style="width: 1px;padding-left: 3px;"><font color = 'red'>*</font></li>  
		                    <li class="boxinput"style="width: 135px;">
							<select id="breedGroupId"  name="breedGroupId" style="width: 135px;" onChange="getBreedGroupName('breedGroupId');">
		     					<option value="">กรุณาเลือก</option>
		     					<%	if(breedGroupList !=null && breedGroupList.size()>0){
		           						for(int i=0; i < breedGroupList.size(); i++){
		               						BreedGroup breedGroup = (BreedGroup)breedGroupList.get(i);
		     					%>
		           		 				<option value="<%= breedGroup.getBreedGroupId() %>"><%= breedGroup.getBreedGroupName() %></option>
		     					<%		}
		     						} 
		     					%>
							</select>
                        	</li>
                        	<li class="topic"style="width:120px;padding-left:8px;text-align: left;"><font style="font-size: 20px;">อัตราเมล็ดพันธุ์ที่ใช้ :</font></li>
                        	<li class="topic" style="width: 1px;padding-right:0px;"></li> 
		                    <li class="boxinput" style="width:130px;">
						   	<dcs:validateText name="qtyPerRai" property="qtyPerRai" maxlength="10"  style="width:70px;" onChange="checkNumberComma(this);"  />&nbsp;<font style="font-size: 20px;">กก./ไร่</font>
						    </li>
						    
                        	<li class="topic"style="width:120px;padding-left:5px;text-align: left;"><font style="font-size: 20px;">ขนาดพื้นที่เพาะปลูก :</font></li>
                  		    <li class="topic" style="width: 1px;padding-left: 3px;"><font color = 'red'>*</font></li>  
		                    <li class="boxinput"style="width:270px;">
						   		<dcs:validateText name="plantRai" property="plantRai" maxlength="60"  style="width:50px;" onChange="checkNumber(this);" />&nbsp;<font style="font-size: 20px;">ไร่</font>&nbsp;
		  						<dcs:validateText name="plantNgan" property="plantNgan" maxlength="60"  style="width:50px;" onChange="checkNumber(this);" />&nbsp;<font style="font-size: 20px;">งาน</font>&nbsp;
		 						<dcs:validateText name="plantWah" property="plantWah" maxlength="60"  style="width:50px;" onChange="checkNumber(this);" />&nbsp;<font style="font-size: 20px;">วา</font>
							 </li>
                        </ul> 
    				 </div>
       			 </fieldset>
				<fieldset style="width: 930px;">
				<legend><b>::ข้อมูลแหล่งที่มาเมล็ด::</b></legend>
				 <div class="line-input-keyin">
				 <ul>	
                   <% for(int i=0; i < BREED_SOURCE.split(",").length; i++){ %> 	
				   		<li class="topic" style="width:10px; padding-right:1px;"></li>
                    	<li class="boxinput" style="width:29px; padding-right:0px;"> 
                    		<input type ="checkbox" id="seedResource_<%= i+1 %>" name="seedResource" value="<%= BREED_SOURCE.split(",")[i] %>" onclick="showCooperative(this);" style="width:15px;"  />
                    	</li>
                  		<li class="topic" style="width: 2px;padding-right: 2px;"></li>  
		                <li class="topic"style="width: 157px;text-align: left;"><font style="font-size: 20px;"><%= BREED_SOURCE.split(",")[i] %></font></li>   
				    <% } %>  
	                	
                    </ul> 
				 </div>
				 
				 <div id="divCoorperative" style="display:none;" class="line-input-keyin">
				 	<ul>
	                	 <div class="line-input-keyin">
							 <dcs:validateDropDown name="corpGroupId" property="corpGroupId" dataSource="<%= corpGroupList %>" keyField="farmerGroupId" displayField="farmerGroupName"  isRequire="true" style="width:260px;" />                      		
						     <dcs:validateText name="noOfBreed" maxlength="10" style="width: 60px;" onChange="checkNumber(this);" /> กก.
						 </div>
                    </ul> 
				 </div>
				</fieldset>
			 <fieldset style="width: 930px;">
            	<legend><b>::ข้อมูลการคัดเมล็ดพันธุ์::</b></legend>
				<div class="line-input-keyin">
				<% if(seedList != null && seedList.size() > 0 ){ 
					double totalRow = seedList.size();
					long perRow = Math.round(Math.ceil(totalRow/3));
					for(int i=0; i < perRow; i++){  %>
					<ul>
					 <% for(int j=3*i; j< 3*(i+1); j++){
					 		if(j < seedList.size()){
			               		SeedSelect seed = (SeedSelect)seedList.get(j);
						%>
				        <li class="topic" style="width:10px; padding-right:1px;"></li>
                    	<li class="boxinput" style="width:29px; padding-right:0px;"> 
							<input type ="checkbox" id="picking_<%= seed.getSeedSelectId() %>" name="picking" value="<%= seed.getSeedSelectName() %>" style="width:15px;" />
						</li>
						<li class="topic" style="width: 2px;padding-right:2px;">&nbsp;</li>  
		                <li class="topic"style="width: 250px;text-align: left;"><font style="font-size: 20px;"><%= seed.getSeedSelectName() %></font></li>
						<% }} %>
					</ul>
				<% }
				} %>
				</div>
			</fieldset>
			<fieldset style="width: 930px;">
			<legend><b>::ข้อมูลวัตถุประสงค์ในการปลูก::</b></legend>
				<div class="line-input-keyin">
				  <ul>	   
				  	<% for(int i=0; i < OBJECTIVE_PLANT.split(",").length; i++){ %>	        		
		              	<li class="topic" style="width: 10px;padding-right: 1px;">&nbsp;</li>
		              	<li class="boxinput"style="width: 29px;padding-right: 0px;">
                      		<input type="radio" id="objective_<%= i+1 %>" name="objective" value="<%= OBJECTIVE_PLANT.split(",")[i] %>" style="width:15px;" onclick="enableTextbox(this);" />
                      	</li>
                  	  	<li class="topic" style="width: 5px;padding-right: 2px;"></li>
		              	<li class="topic"style="width: 110px;text-align: left;"><label for="objective_<%= i+1 %>" style="font-size: 20px;"><%= OBJECTIVE_PLANT.split(",")[i] %></label></li>     		
		            <% } %>
                   </ul> 
				</div>
				<div class="line-input-keyin" id="SubObjectivePlant"></div>
			</fieldset>
				<fieldset style="width: 930px;">
            		<legend><b>::ข้อมูลลักษณะการเกษตร::</b></legend>
				<div class="line-input-keyin">
				  <ul>	   
				  	<% for(int i=0; i < AGRICULTURE_TYPE.split(",").length; i++){ %> 	
		              	<li class="topic" style="width: 10px;padding-right: 1px;">&nbsp;</li>
		              	<li class="boxinput" style="width: 29px;padding-right: 0px;">
                      	<input type ="radio" id="agricultureType_<%= i+1 %>" name="agricultureType" value="<%= AGRICULTURE_TYPE.split(",")[i] %>" style="width:15px;" />
                      	</li>
                  	  	<li class="topic" style="width: 5px;padding-right: 2px;"></li>
		              	<li class="topic"style="width: 110px;text-align: left;"><font style="font-size: 20px;"> <%= AGRICULTURE_TYPE.split(",")[i] %></font></li>	
		            <% } %>
                   </ul> 
				</div>
			</fieldset>
			<fieldset style="width: 930px;">
            <legend><b>::ความประสงค์ในการทำมาตรฐานการปลูก (GAP)::</b></legend>	
				<div class="line-input-keyin">
				  <ul>	 
				   	<% for(int i=0; i < GAP.split(",").length; i++){ %> 	
		              	<li class="topic" style="width: 10px;padding-right: 1px;">&nbsp;</li>
		              	<li class="boxinput"style="width: 29px;padding-right: 0px;">
                      	<input type ="radio" id="gap_<%= i+1 %>" name="gap" value="<%= GAP.split(",")[i] %>" style="width: 15px;" />
                      	</li>
                  	  	<li class="topic" style="width: 5px;padding-right: 2px;"></li>
		              	<li class="topic"style="width: 110px;text-align: left;"><font style="font-size: 20px;"><%= GAP.split(",")[i] %></font></li>	
		            <% } %>     
                   </ul> 
			</div>
			</fieldset>
			<fieldset style="width: 930px;">
			<legend><b>::ข้อมูลวิธีการปลูก::</b></legend>
			<div class="line-input-keyin">
				<% if(methodList != null && methodList.size() > 0){ 
					double totalRow1 = methodList.size();
					long perRow1 = Math.round(Math.ceil(totalRow1/5));
					for(int m1=0; m1 < perRow1; m1++){  %>
					<ul>
					 <%  for(int m2=5*m1; m2 < 5*(m1+1); m2++){
					 		if(m2 < methodList.size()){
			               		PlantMethod method = (PlantMethod)methodList.get(m2);
						%>
						<li class="topic" style="width: 10px;padding-right: 1px">&nbsp;</li>
		              	<li class="boxinput"style="width: 30px;padding-right: 0px;">
                      	<input type ="checkbox" id="planting_<%= method.getPlantMethodId() %>" name="planting" value="<%= method.getPlantMethodName() %>"  style="width:15px;"/>
                      	</li>
                      	<li class="topic" style="width: 5px;padding-right: 2px;"></li>
		              	<li class="topic"style="width: 80px;text-align: left;"><font style="font-size: 20px;"><%= method.getPlantMethodName()%></font></li> 
						<%  }
						} %>
					</ul>
				<% }} %>	
			</div>
		</fieldset>
		<fieldset style="width: 930px;">
			<legend><b>::ข้อมูลการจัดเตรียมแปลง::</b></legend>
			<div class="line-input-keyin">
				<% if(prepareAreaList != null && prepareAreaList.size() > 0){ 
					double totalRow = prepareAreaList.size();
					long perRow = Math.round(Math.ceil(totalRow/5));
					for(int x=0; x < perRow; x++){  %>
					<ul>
					 <% for(int y=5*x; y < 5*(x+1); y++){
					 		if(y < prepareAreaList.size()){
			               		PrepareArea area = new PrepareArea();
			               		Object[] object = (Object[])prepareAreaList.get(y);
			               		area.setPrepareAreaId(Long.parseLong(object[0].toString()));
								area.setPrepareAreaName(object[1].toString());
						%>
				          <li class="topic" style="width: 10px;padding-right: 1px;"></li>
				          <li class="boxinput" style="width: 30px;padding-right: 0px;">
		                     <input type ="checkbox" id="plot_<%= area.getPrepareAreaId() %>" name="plot" value="<%= area.getPrepareAreaName() %>" onclick="enableTextbox(this);" style="width: 15px;"/>
		                  </li>
						  <li class="topic" style="width: 5px;padding-right: 2px;"></li>
				          <li class="topic"style="width: 80px;text-align: left;"><font style="font-size: 20px;"><%=  area.getPrepareAreaName() %></font></li>
				       	
						<% }
						} 
						%>
					</ul>
				<% }
				} %>
			</div>
			<div id="subPrepareArea" class="line-input-keyin">
			</div>
		</fieldset>
		<fieldset style="width: 930px;">
				 <legend><b>::ข้อมูลการจัดเตรียมแปลง/การใส่ปุ๋ย::</b></legend>
				<div class="line-input-keyin">
				   <ul>	                    
		              <li class="topic" style="width: 71px;padding-right: 0px;text-align: left;"><font style="font-size: 20px;">อินทรีย์ :</font></li>
                  	  <li class="topic" style="width: 1px;padding-right: 5px;"></li> 
                  	  
                  	  <% if(prepareManureList != null && prepareManureList.size() > 0) {
                  	 		 for(int i=0; i < prepareManureList.size(); i++) { 
                  	  			PrepareManure manure = (PrepareManure)prepareManureList.get(i);
                  	  			if("O".equals(manure.getPrepareManureType()))
                  	  			{
                  	   %>
				   	 			<li class="topic" style="width: 5px;">&nbsp;</li>
		              			<li class="boxinput"style="width: 20px;padding-right: 0px;">
                      				<input type ="checkbox" id="manureUse_<%= manure.getPrepareManureId() %>" name="manureUse" value="<%= manure.getPrepareManureName() %>" style="width:15px;"/>
                      			</li>
                      			<li class="topic" style="width: 2px;padding-right: 2px;"></li>
		             	    	<li class="topic"style="width: 100px;text-align: left;"><font style="font-size: 20px;"><%= manure.getPrepareManureName() %></font></li>
				     <%			}	
				     		 } 
				     	 }
				     %>  
                   </ul>
				</div>
				<div class="line-input-keyin">
				   <ul>	        
				       <li class="topic" style="width: 80px;padding-right: 0px;text-align: left;"><font style="font-size: 20px;">อนินทรีย์ :</font></li>
                  	   <li class="topic" style="width: 1px;padding-right: 5px;"></li>  
				            
				        <% if(prepareManureList != null && prepareManureList.size() > 0) {
                  	 		 for(int i=0; i < prepareManureList.size(); i++) { 
                  	  			PrepareManure manure = (PrepareManure)prepareManureList.get(i);
                  	  			if("I".equals(manure.getPrepareManureType()))
                  	  			{
                  	   %>
				   	 			 <li class="topic" style="width: 5px;padding-right: 1px;">&nbsp;</li>
		              			 <li class="boxinput"style="width: 20px;padding-right: 0px;">
                      				<input type ="checkbox" id="manureUse_<%= manure.getPrepareManureId() %>" name="manureUse" value="<%= manure.getPrepareManureName() %>" style="width:15px;"/>
                      	 		 </li>
                  	  	 		 <li class="topic" style="width: 2px;padding-right: 2px;"></li>
		              	 		 <li class="topic"style="width: 100px;text-align: left;"><font style="font-size: 20px;"><%= manure.getPrepareManureName() %></font></li>
				     <% 		}
				     		}
				    	 } 
				    %>  
                   </ul>
				</div>
			</fieldset>
			<fieldset style="width: 930px;">
			<legend><b>::ข้อมูลช่วงเวลาการเพาะปลูก::</b></legend>
				<div class="line-input-keyin">
			 	<ul>
                    <li class="topic" style="width: 150px;padding-right: 0px;text-align: left;"><font style="font-size: 20px;">วันที่เพาะปลูก :</font></li>
	 	   			<li class="topic" style="width: 3px;padding-left: 2px;"><font color="red">*</font></li>  
			  		<li class="boxinput"style="width: 280px;">
						<dcs:validateDate name="plantDate" style="width:130px;" />
						<font color="red" style="font-size: 18px;">ตย. 31/12/2557</font>
					</li>
					<li class="topic"style="width: 230px;padding-right: 0px;"><font style="font-size: 20px;">ประมาณการผลผลิตที่คาดว่าจะได้รับ :</font></li>
					<li class="topic" style="width: 1px;padding-left: 2px;"><font color="red">*</font></li>  
					<li class="boxinput"style="width: 200px;">
					<dcs:validateText name="forecastRecordDoc" maxlength="10"  style="width: 130px;" onChange="checkNumberComma(this);" /><font style="font-size: 20px;">กก.</font>
					</li>
				</ul>
				</div>
				<div class="line-input-keyin">
					 <ul>
			  			<li class="topic" style="width: 150px;padding-right: 0px;text-align: left;"><font style="font-size: 20px;">วันที่คาดว่าจะเก็บเกี่ยว : </font></li>
			 			<li class="topic" style="width: 3px;padding-left: 2px;"><font color="red">*</font></li> 
			  			<li class="boxinput"style="width: 280px;">
							<dcs:validateDate name="forecastDate" style="width: 130px;" />
							<font color="red" style="font-size: 18px;">ตย. 31/12/2557</font>
			 			</li>
					</ul>
				</div>
		</fieldset>
	    <div id="tabBreed1_Save" class="line-input-keyin">
 		<table align="center">
	   	   <tr style="text-align: center;">
		   		<td colspan="3" style="text-align: center;padding-top: 15px;">
		   			<img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" onclick="savePlantBreed('tab1');"/>
		   			<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="javascript:closeWindow('PlantBreedPopup',0);" />
		   		</td>
	   	   </tr>
   		</table>
   		</div>
	<!-- finish ข้อมูลการเพาะปลูกพืช -->
    </fieldset>
    </li>
        <li class="detailBreed2">
    	<!--  tabBreed2 is clicked -->
	       	<fieldset style="width: 910px;" > 
	       	     <!-- ข้อมูลการใส่ปุ๋ย -->
	       	     <br>
	       	     <legend>:: ข้อมูลการใส่ปุ๋ย ::</legend>
	       	     <div class="btn-manage" style="margin-left: 0px;">
					 <a class="btn-add" href="#" onclick="showWindow('PlantManurePopup',0);"></a>
					 <a class="btn-del" href="#" onclick="goDeleteManure();"></a>
				 </div>
				 <br/>
		         <div id="dynamicPlantManure">
		         </div>
		         <br/>
			</fieldset>
			<div id="tabBreed2_Save" class="line-input-keyin">
		 		<table align="center">
			   	   <tr style="text-align: center;">
				   		<td colspan="3" style="text-align: center;padding-top: 15px;">
				   			<img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" onclick="savePlantBreed('tab2');"/>
				   			<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="javascript:closeWindow('PlantBreedPopup',0);" />
				   		</td>
			   	   </tr>
		   		</table>
	   		</div>
        </li>
       	<li class="detailBreed3">
       	 	 <!--   tabBreed3 is clicked -->
       	 	 <fieldset style="width: 910px;" > 
       	 	 <br>
       	 	 <legend>:: ข้อมูลการใส่สารเคมี ::</legend>
       	     <!-- ข้อมูลการใส่สารเคมี --> 
				<div class="btn-manage" style="margin-left: 0px;">
		        	<a class="btn-add" href="#" onclick="showWindow('PlantChemicalPopup',0);"></a>
		        	<a class="btn-del" href="#" onclick="goDeleteChemical();" ></a>
	       	    </div>
	       	    <br />
	       	    <div id="dynamicPlantChemical">
	        	</div>
	        	<br />
			 </fieldset>
			  <div id="tabBreed3_Save" class="line-input-keyin">
		 		<table align="center">
			   	   <tr style="text-align: center;">
				   		<td colspan="3" style="text-align: center;padding-top: 15px;">
				   			<img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn"  onclick="savePlantBreed('tab3');"/>
				   			<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn"  onclick="javascript:closeWindow('PlantBreedPopup',0);" />
				   		</td>
			   	   </tr>
		   		</table>
	   		</div>
       	</li>
        <li class="detailBreed4">
         <!--   tabBreed4 is clicked -->
         <fieldset style="width: 910px;" > 
         <br>
       	 	 <legend>:: ข้อมูลทรัพย์สิน ::</legend>
       	 	 <div class="btn-manage" style="margin-left: 0px;">
			     <a class="btn-add" href="#" onclick="showWindow('PlantAssetPopup',0);"></a>
			     <a class="btn-del" href="#" onclick="goDeleteAsset();"></a>
		     </div>
		     <br />
         	 <div id="dynamicPlantAsset">
        	 </div>
			<br />
        </fieldset>
        <div id="tabBreed4_Save" class="line-input-keyin">
		 		<table align="center">
			   	   <tr style="text-align: center;">
				   		<td colspan="3" style="text-align: center;padding-top: 15px;">
				   			<img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn"  onclick="savePlantBreed('tab4');"/>
				   			<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="javascript:closeWindow('PlantBreedPopup',0);" />
				   		</td>
			   	   </tr>
		   		</table>
	   		</div>
        </li>
       	<li class="detailBreed5">
       	 <!--   tabBreed5 is  clicked -->
       	<fieldset style="width: 890px;" >
       	    <br>
       	 	<div class="line-input-keyin">
       			<ul>
               		<li class="topic"style="width: 270px;text-align: left;padding-left: 10px;"><font style="font-size: 20px;">ข้อมูลแจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยวจริงของ : </font></li>
               		<li class="topic" style="width: 1px;padding-right: 5px;"></li>  
               		<li class="boxinput"style="width: 200px;"><font style="font-size: 20px;"><label id ="lblTabBreed5_1"></label></font><input type="hidden" name = "noticeTypeId" id="noticeTypeId" /></li>
                   
               		<li class="topic"style="width: 120px;text-align: left;"><font style="font-size: 20px;">พืชที่ปลูก : </font></li>
               		<li class="topic" style="width: 1px;padding-right: 5px;"></li>  
               		<li class="boxinput"style="width: 200px;"><font style="font-size: 20px;"> <label id ="lblTabBreed5_2"></label></font><input type="hidden" name = "noticeDocNo" id="noticeDocNo" /></li>
             	</ul>
       		 </div>
       		<div class="line-input-keyin">
       			<ul>
                    <li class="topic"style="width: 270px;text-align: left;padding-left: 10px;"><font style="font-size: 20px;"> พันธุ์ที่ปลูก : </font></li>
                    <li class="topic" style="width: 1px;padding-right: 5px"></li>  
                   	<li class="boxinput"style="width: 200px;"><font style="font-size: 20px;"><label id ="lblTabBreed5_3"></label><input type="hidden" name = "noticeBreedGroupId" id="noticeBreedGroupId"/></font></li>
                   		
                  	<li class="topic"style="width: 120px;text-align: left;"><font style="font-size: 20px;"> ขนาดพื้นที่ปลูก : </font></li>
                    <li class="topic" style="width: 1px;padding-right: 5px;"></li>  
                   	<li class="boxinput"style="width: 200px;"><font style="font-size: 20px;"><label id ="lblTabBreed5_4"></label></font><input type="hidden" name = "noticeBreedTypeId" id="noticeBreedTypeId" /></li>
                </ul>
       		</div>
       		<br>
       		<fieldset style="width:880px;">
			<legend><b>:: แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยวจริง ::</b></legend>
       			<table style="width:880px;padding: 5px;" border="0">
       			    <tr>
						<td style="width: 155px; text-align: right;">เดือนที่ผลผลิตออกสู่ตลาด :</td>
						<td style="width: 8px;padding: 0px;"><font color="red">*</font></td>
						<td style="width: 270px;text-align: left;">
						<dcs:validateDate name="publicMarketDate" style="width: 136px;" onChange="isDate('publicMarketDate')"/>
						<font color="red" style="font-size: 18px;">ตย. 31/12/2557</font>
						</td>
							<td style="width: 240px; text-align: right;">ประมาณการผลผลิตที่คาดว่าจะออกสู่ตลาด :</td>
							<td style="width: 8px;padding: 0px;"><font color="red">*</font></td>
							<td style="text-align: left;width: 165px;">
								<dcs:validateText name="publicCrop" style="width: 136px;" maxlength="10" onChange="checkNumberComma(this);" /> กก.</td>
					</tr>
					<tr>
						<td style="text-align: right;">วันที่บันทึก :</td>
						<td style="padding: 0px;"></td>
						<td style="text-align: left;">
							<dcs:validateDate name="recordForecastDate" style="width: 136px;" onChange="isDate('recordForecastDate');"/>
							<font color="red" style="font-size: 18px;">ตย. 31/12/2557</font></td>
						<td style="text-align: right;">ลงชื่อผู้บันทึก :</td>
						<td style="padding: 0px;"></td>
						<td style="text-align: left;">
						<dcs:validateText name="recordForecastBy" maxlength="100" style="width: 136px;" onChange="checkInvalidText(this);"/></td>
				    </tr>
				</table>
       		</fieldset>
       	   <!--   tabBreed5 is  clicked -->
       	   <br>
       	   <div id="tabBreed5_Save" class="line-input-keyin">
			<table><tr><td style="padding-left: 310px;">
			   		<img src="<%=request.getContextPath() %>/images/btn-ok.png" id="btnSubmit" class="btn" onclick="savePlantBreed('tab5');"/>
			   		<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="closeWindow('PlantBreedPopup',0);"/>
		   	</td></tr></table>
  			</div>
  			</fieldset>
       	</li>
       </ul>
    </div>
<!-- ********************************* finish 5tabs -->
</div>	      		    
<!-------------------------- Div : เอกสารสิทธิ์  Doc detail------------------------------>
<div id="DocDetailPopup" title="ข้อมูลพื้นที่เพาะปลูก" style="VISIBILITY:hidden;display:none;">
	<div style="width: 920px;">
		<table style="width:920px;padding: 5px;" border="0">
		<tr><td colspan="9" style="text-align: left;padding: 5px;padding-left: 20px"><font>พื้นที่เพาะปลูก: <font color=gray ><label id="lblFarmerName" >ชื่อเกษตรกร</label></font></font></td>
		</tr>
		</table>
	</div>
	<fieldset style="width:920px;">
		<legend><b>:: ข้อมูลเอกสารสิทธิ์ ::</b></legend>
	<table style="width:910px;padding: 0px;">
	<tr>
	 <td style="width: 135px;text-align: right;">เอกสารสิทธิ์ :</td>
	 	<td style="padding:1px"><font color="red">*</font></td>
	 	<td style="text-align: left;">
			<select id="typeId"  name="typeId"  style="width:140px; height:29px" >
		    <option value="">กรุณาเลือก</option>
		    <%if (landrightList !=null && landrightList.size()>0){
		        for (int p=0; p < landrightList.size(); p++){
		             LandRightType land = (LandRightType)landrightList.get(p);
		    %>
		        <option value="<%=land.getTypeId()%>"><%=land.getTypeName()%></option>
		    <%}} %>
		    </select>
	   </td>
	 	<td style="width: 70px;text-align: right;"> เลขที่ :</td>
	 	<td style="padding:1px;"><font color="red">*</font></td>
		<td style="text-align: left;">
			<dcs:validateText name="docNo" property="docNo" maxlength="60"  style="width:126px;" onChange="checkInvalidText(this);" />
		</td>
		<td style="width: 70px;text-align: right;">ขนาดที่ดิน  :</td>
		<td style="padding:1px;"><font color="red">*</font></td>
		<td style="width:110px;">
			  <dcs:validateText name="docRai" property="docRai" maxlength="10" style="width:50px;" onChange="checkNumber(this);" />&nbsp;ไร่
		</td>
	 	<td style="padding:1px"></td>
		<td style="width:110px;">
			 <dcs:validateText name="docNgan" property="docNgan" maxlength="10" style="width:50px;" onChange="checkNumber(this);" />&nbsp;งาน
		</td>
		<td style="padding:1px"></td>
		<td style="width:110px;">
			<dcs:validateText name="docWah" property="docWah" maxlength="10" style="width:50px;" onChange="checkNumber(this);" />&nbsp;วา
		</td>
	</tr>
	</table>
	<table style="width:910px;padding: 0px;">
	 <tr>
	 <td style="text-align: right;width: 170px;">ที่ตั้งแปลง หมู่ที่ :</td>
	 	<td style="padding: 1px;width: 1px;"></td>
	 	<td style="text-align: left;width: 60px;">
	 <dcs:validateText name="landMoo" property="landMoo" maxlength="2"  style="width: 50px;" onChange="checkNumber(this);" />
	 </td>
	 	<td style="text-align: right;width: 190px;">จังหวัด :</td>
	 	<td style="padding:1px"><font color="red">*</font></td>
		<td style="text-align: left;width: 140px;">
			<select id="docProvinceNo"  name="docProvinceNo" style="width: 133px;" onChange="getDistrictInfo('docProvinceNo', 'docDistrictNo', 'docSubDistrictNo', ''); ">
		         <option value="">กรุณาเลือก</option>
		         <% if(provinceList !=null && provinceList.size()>0){
		              for(int p=0; p < provinceList.size(); p++){
		                 Province pd = (Province)provinceList.get(p);
		          %>
		                 <option value="<%=pd.getProvinceNo()%>"><%=pd.getThaiName()%></option>
		         <%}} %>
		    </select>
		</td>
		<td style="text-align: right;width: 70px;">อำเภอ :</td>
	 	<td style="padding:1px"><font color="red">*</font></td>
		<td style="text-align: left;width: 130px;">
			 <select id="docDistrictNo"  name="docDistrictNo" style="width: 130px;" onChange="getSubDistrictInfo('docProvinceNo','docDistrictNo','docSubDistrictNo', ''); ">
			</select>
		</td>
		<td style="text-align: right;width: 60px;">ตำบล :</td>
	 	<td style="padding:1px"><font color="red">*</font></td>
		<td style="text-align: left;width: 130px;">
			 <select id="docSubDistrictNo"  name="docSubDistrictNo" style="width:130px;" >
			</select>
		</td>
	</tr>
	</table>
	<table style="width:910px;padding: 0px;">
    <tr>
	 	<td style="text-align: right;width: 130px;"></td>
		<td style="text-align: left;width: 140px;">
			<input type ="radio" id="ownRai"  name="ownRai" onclick="checkOwner(this);" />  ที่ตนเอง
			<input type ="radio" id="rentRai" name="rentRai" onclick="checkOwner(this);" />  ที่เช่า 
		</td>
		<td style="text-align: right;width: 70px;">อัตราค่าเช่า :</td>
		<td style="width: 2px;">&nbsp;</td>
		<td style="text-align: left;"  colspan = "4">
			<dcs:validateText name="rentPrice" property="rentPrice" maxlength="14" style="width:127px;" onChange="checkNumberComma(this);" isReadOnly="true" />&nbsp;บาท/ไร่
		</td>
	</tr>
	</table>
	</fieldset>
    <!--  ข้อมูลแหล่งน้ำ -->
    <fieldset style="width:920px;">
        <legend><b>:: ข้อมูลแหล่งน้ำ ::</b></legend>
        <table border="0" style="width:910px;padding: 5px;" border="0" >
            <% String[] waterSource = WATER_SOURCE.split(",");
            if(waterSource != null && waterSource.length > 0){ 
                    double totalRow = waterSource.length;
                    long perRow = Math.round(Math.ceil(totalRow/2));
                    for(int x=0; x < perRow; x++){ 
            %>
            <tr>
                <% if(x > 0){ %>
                    <td style="height: 23px;width:130px;">&nbsp;</td>
                <% }else{ %>
                    <td style="text-align: right;height: 23px;width:130px;">แหล่งน้ำที่ใช้ :</td>
                <% } %>
                <td style="width:50px;padding-right:0px">&nbsp;</td>
                    <% for(int y=2*x; y < 2*(x+1); y++){
                        if(y < waterSource.length){
                    %>
                    <td style="width: 350px;padding: 5px;" colspan="3">
                         <input type="radio" id="waterSource_<%= y+1 %>" name="waterSource" value="<%= waterSource[y] %>" style="width:20px;" /><label for="waterSource_<%= y+1 %>"><%= waterSource[y] %></label>
                    </td>
                        <% }
                        } 
                        %>
            </tr>
                 <% }
              } %>
        </table>
    </fieldset>
	<!--  ข้อมูลเขตชลประทาน -->
	<fieldset style="width:920px;">
		<legend><b>:: ข้อมูลชลประทาน ::</b></legend>
		<table border="0" style="width:910px;padding: 5px;" border="0" >
			<% if(irrList != null && irrList.size() > 0){ 
					double totalRow = irrList.size();
					long perRow = Math.round(Math.ceil(totalRow/2));
					for(int x=0; x < perRow; x++){ 
		    %>
			<tr>
				<td style="width:50px;padding-right:0px">&nbsp;</td>
				<% if(x > 0){ %>
	   				<td style="text-align: left;height: 23px;width:130px;">&nbsp;</td>
	   			<% }else{ %>
	   				<td style="text-align: left;height: 23px;width:130px;">เขตชลประทาน :</td>
	   			<% } %>
					<% for(int y=2*x; y < 2*(x+1); y++){
					 	if(y < prepareAreaList.size()){
			               	IrrigationArea irrArea = (IrrigationArea)irrList.get(y);
					%>
					<td style="width: 350px;padding: 5px;" colspan="3">
				         <input type="radio" id="irrigation_<%= irrArea.getIrrigationAreaId() %>" name="irrigation" value="<%= irrArea.getIrrigationAreaName() %>" style="width:20px;" /><%= irrArea.getIrrigationAreaName() %>
				    </td>
						<% }
						} 
						%>
			</tr>
		         <% }
			  } %>
		</table>
	</fieldset>
	<!-- จบข้อมูลเขตชลประทาน  -->
	<fieldset style="width:920px;">
		<legend><b>:: ข้อมูลสภาพที่ดิน ::</b></legend>
		<table border="0" style="width:910px;padding: 5px;" border="0" >
			<% if(landStatusList != null && landStatusList.size() > 0){ 
					double totalRow = landStatusList.size();
					long perRow = Math.round(Math.ceil(totalRow/4));
					for(int x=0; x < perRow; x++){ 
		    %>
			<tr>
				<td style="width:50px;padding-right:0px">&nbsp;</td>
				<td style="text-align: left;height: 23px;width:130px;">ข้อมูลสภาพที่ดิน :</td>
					<% for(int y=4*x; y < 4*(x+1); y++){
					 	if(y < landStatusList.size()){
			               	LandStatus landStatus = (LandStatus)landStatusList.get(y);
					%>
						<td style="width:175px; padding:5px;">
					         <input type="checkbox" id="soil_<%= landStatus.getLandStatusId() %>"  name="soil"  value="<%= landStatus.getLandStatusName() %>" style="width:20px;" /><%= landStatus.getLandStatusName() %>
					    </td>
						<% }
						} 
						%>
			</tr>
		         <% }
			  } %>
		</table>
	</fieldset>
	<fieldset style="width:920px;">
	<legend><b>:: ข้อมูลชนิดดิน ::</b></legend>
		<table border="0" style="width:910px;padding: 5px;" border="0" >
			<tr>
				<td style="width:50px;padding-right:0px">&nbsp;</td>
				<td style="text-align: left;height: 23px;width:130px;">ข้อมูลชนิดดิน :</td>
			<% if(landTypeList != null && landTypeList.size() > 0){ 
					double totalRow = landTypeList.size();
					long perRow = Math.round(Math.ceil(totalRow/4));
					for(int x=0; x < perRow; x++){ 
						if(x>0) {
		    %>
	   			<td>&nbsp;</td><td>&nbsp;</td>
					<%} for(int y=4*x; y < 4*(x+1); y++){
					 	if(y < landTypeList.size()){
			               	LandType landType = (LandType)landTypeList.get(y);
					%>
						<td style="width:175px; padding:5px;">
				         	<input type="checkbox" id="soilType_<%= landType.getLandTypeId() %>" name="soilType" value="<%= landType.getLandTypeName() %>" style="width:20px;" onclick="enableTextbox(this);" /><%= landType.getLandTypeName() %>		    
				    	</td>
						<% if(landType.getLandTypeName().equals("อื่นๆ")) {
						if((y+1)%4==0) { %></tr><tr><td>&nbsp;</td><td>&nbsp;</td><% } %>
						<td style="padding:5px;">&nbsp;ระบุ &nbsp;<dcs:validateText name="soilTypeName" property="soilTypeName" maxlength="100"  style="width:130px;" isReadOnly="true" onChange="checkInvalidText(this);" /></td>
						<% y++; }
						}
					} %>
			</tr>
		         <% }
			  } %>
		</table>
	</fieldset>
	<br>
	<div id = "docDetail_savePart">
	<table>
		<tr>
		   <td style="padding-left: 350px">
			   <img src="<%=request.getContextPath() %>/images/btn-ok.png" id="btnSubmit" class="btn"  onclick="saveLandright();"/>
			   <img src="<%=request.getContextPath() %>/images/btn-cancel.png"  class="btn" onclick="closeWindow('DocDetailPopup',0);"/>
		   </td>
		</tr>
   </table>
   </div>
   <br />
</div>
<!-------------------------- Finish : เอกสารสิทธิ์  Doc detail------------------------------>
</div>

<!------------------------------ Div การใช้ปุ๋ยเคมีและปุ๋ยอินทรีย์  for detailBreed2----------------------------------->
<div id="PlantManurePopup" title="ข้อมูลการใส่ปุ๋ย" style="VISIBILITY:hidden;display:none;" >
	<table  border="0" style="width: 500px;padding: 5px;" id="plantManureTable" align="center" >
		<tr height="21">
		 	<td width="70px">&nbsp;</td>
			<td style="text-align: left;width: 470px;" >พืชที่ปลูก : &nbsp; <label id="lblManureBreedTypeName" ></label></td>
			<td style="text-align: left;padding-left: 10px;" >พันธุ์ที่ปลูก: &nbsp; <label id="lblManureBreedGroupName" ></label></td>
		</tr>  
		<tr height="21">
		 	<td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px;" width="280px">ประเภทปุ๋ย :<font color = "red">*</font></td>
			<td style="text-align: left;padding: 5px;" >
			<select id="manureTypeId"  name="manureTypeId" style="width:180px;">
			     <option value="">กรุณาเลือก</option>
			     <%if(manureList !=null && manureList.size()>0){
			           for(int i=0; i<manureList.size(); i++){
			               ManureType manure = (ManureType)manureList.get(i);
			     %>
			            <option value="<%= manure.getTypeId() %>"><%= manure.getTypeName() %></option>
			     <%}} %>
			</select>
			</td>
		</tr>  
		<tr height="21">
		    <td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px;" width="280px">&nbsp;</td>
			<td style="text-align: left;" colspan = "2">
				<input type="radio" id="ownProduce" name="ownProduce" style="width:15px;" onclick="enableTextbox(this);" />&nbsp;ผลิตเอง&nbsp;
				<input type="radio" id="ownBuy" name="ownBuy" style="width:15px;" onclick="enableTextbox(this);" />&nbsp;ซื้อ&nbsp;
			</td>
		</tr>   
		<tr height="21">
			 <td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px" width="280px">ชื่อ/ตราสินค้า :</td>
			<td style="text-align: left;padding: 5px;" >
				<dcs:validateText name="mName" property="mName" maxlength="100"  style="width:180px;" onChange="checkInvalidText(this);"/></td>
		</tr>
		<tr height="21">
			 <td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px" width="280px">สูตรปุ๋ย :</td>
			<td style="text-align: left;padding: 5px;" >
				<dcs:validateText name="mFormula" property="mFormula" maxlength="100"  style="width:180px;" onChange="checkInvalidText(this);"/>
			</td>
		</tr>
		<tr height="21">
			<td width="5px">&nbsp;</td>
		   <td style="text-align: right;padding: 5px;padding-right: 0px" width="280px">ราคา :</td>
		   <td style="text-align: left;padding: 5px;width: 300px;">
		        <dcs:validateText name="mCostPerRai" property="mCostPerRai" style="width:180px;" onChange="checkNumberComma(this);" isReadOnly="true" />&nbsp;บาท/ไร่</td>
		</tr>
		<tr height="21">
		<td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px;" width="280px">ช่วงเวลาการใส่ :<font color = "red">*</font></td>
			<td style="text-align: left;padding: 5px;" >
			<select id="mPeriodTime"  name="mPeriodTime" style="width:183px;">
			     <option value="0">กรุณาเลือก</option>
			     <option value="1">ก่อนการเพาะปลูก</option>
			     <option value="2">ระหว่างการเพาะปลูก</option>
			     <option value="3">หลังการเพาะปลูก</option>
			</select>
			</td>
		</tr>
		<tr height="21">
		<td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px;" width="280px">วันที่ใส่ปุ๋ย :<font color = "red">*</font></td>
			<td style="text-align: left;padding: 5px;width: 650px;" >
				<dcs:validateDate name="manureDate" style="width:180px;" onChange="isDate('manureDate');"/>
				<font color="red" style="font-size: 18px;">ตย. 31/12/2557</font>
			</td>
		</tr>
		<tr height="21">
			<td width="5px">&nbsp;</td>
		   <td style="text-align: right;padding: 5px;padding-right: 0px" width="280px">อัตราการใช้ :</td>
		   <td style="text-align: left;padding: 5px;width:300px;" >
		   		<dcs:validateText name="mQtyPerRai" property="mQtyPerRai" maxlength="10"  style="width:180px;" onChange="checkNumberComma(this);" />&nbsp;กก./ไร่ 
		   	</td>
		</tr> 
		<tr height="21">
			<td colspan="4" style="text-align: center;padding-top: 10px">
			   <img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" onclick="savePlantManure();"/>
			   <img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="closeWindow('PlantManurePopup',0);"/>
			</td>	   
		</tr>
	</table>
</div>
<!------------------------------ Div สารเคมี ----------------------------------->
<div id="PlantChemicalPopup" title="การใช้สารเคมีคุมและกำจัดวัชพืช" style="VISIBILITY:hidden;display:none;">
<table  border="0" style="width: 500px;padding: 5px;" id="plantChemiTable" align="center" >
		<tr height="21">
		 	<td width="70px">&nbsp;</td>
			<td style="text-align: left;width: 470px;" >พืชที่ปลูก : &nbsp; <label id="lblChemiBreedTypeName" ></label></td>
			<td style="text-align: left;padding-left: 10px;" >พันธุ์ที่ปลูก: &nbsp; <label id="lblChemiBreedGroupName" ></label></td>
		</tr>  
		<tr height="21">
		 	<td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px;" width="280px">ประเภทสารเคมี :<font color = "red">*</font></td>
			<td style="text-align: left;padding: 5px;" >
			 <select id="chemicalTypeId"  name="chemicalTypeId" style="width:180px;">
		     <option value="">กรุณาเลือก</option>
		     <%if(chemicalList !=null && chemicalList.size()>0){
		           for(int i=0; i<chemicalList.size(); i++){
		               ChemicalType chemical = (ChemicalType)chemicalList.get(i);
		     %>
		            <option value="<%= chemical.getTypeId() %>"><%= chemical.getTypeName() %></option>
		     <%}} %>
		  </select>
			</td>
		</tr>  
		<tr height="21">
			 <td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px" width="280px">ชื่อสารเคมี :</td>
			<td style="text-align: left;padding: 5px;" >
				<dcs:validateText name="cFormula" property="cFormula" maxlength="100"  style="width:180px;" onChange="checkInvalidText(this);" />
			</td>
		</tr>
		<tr height="21">
			<td width="5px">&nbsp;</td>
		   <td style="text-align: right;padding: 5px;padding-right: 0px" width="280px">ราคา :</td>
		   <td style="text-align: left;padding: 5px;width: 300px;">
		        <dcs:validateText name="cCostPerRai" maxlength="10"  property="cCostPerRai" style="width:180px;" onChange="checkNumberComma(this);" />&nbsp;บาท/ไร่
		   </td>
		</tr>
		<tr height="21">
		<td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px;" width="280px">ช่วงเวลาการใส่ :<font color = "red">*</font></td>
			<td style="text-align: left;padding: 5px;" >
			<select id="cPeriodTime"  name="cPeriodTime" style="width:183px;">
			     <option value="0">กรุณาเลือก</option>
			     <option value="1">ก่อนการเพาะปลูก</option>
			     <option value="2">ระหว่างการเพาะปลูก</option>
			     <option value="3">หลังการเพาะปลูก</option>
			</select>
			</td>
		</tr>
		<tr height="21">
		<td width="5px">&nbsp;</td>
			<td style="text-align: right;padding: 5px;padding-right: 0px;" width="280px">วันที่ใช้สารเคมี :<font color = "red">*</font></td>
			<td style="text-align: left;padding: 5px;width: 650px;" >
				<dcs:validateDate name="chemicalDate" style="width:180px;" onChange="isDate('chemicalDate');"/>
				<font color="red" style="font-size: 18px;">ตย. 31/12/2557</font>
			</td>
		</tr>
		<tr height="21">
			<td width="5px">&nbsp;</td>
		   <td style="text-align: right;padding: 5px;padding-right: 0px" width="280px">อัตราการใช้ :</td>
		   <td style="text-align: left;padding: 5px;width:300px;" >
		   		<dcs:validateText name="cQtyPerRai" property="cQtyPerRai" maxlength="10"  style="width:180px;" onChange="checkNumberComma(this);" />&nbsp;ซีซี/ไร่ 
		   	</td>
		</tr> 
		<tr height="21">
			<td colspan="4" style="text-align: center;padding-top: 10px">
			    <img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" onclick="savePlantChemical();"/>
		  		<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="closeWindow('PlantChemicalPopup',0);" /> 
			</td>	   
		</tr>
	</table>
</div>
<!------------------------------ Div ทรัพย์สิน ----------------------------------->
<div id="PlantAssetPopup" title="ข้อมูลทรัพย์สิน" style="VISIBILITY:hidden;display:none;">
      <div class="line-input-keyin"></div>
      <div class="line-input-keyin">
      	<ul>
		  <li class="topic" style="width:0px;padding-right:0px"></li>
		  <li class="topic" style="width:120px;padding-right:0px">
		   <font style="font-size: 20px">ข้อมูลทรัพย์สิน :</font></li>
           <li class="topic" style="width: 2px;padding-left:2px"></li>
		   <li class="boxinput" style="width:150px">
				<label id="lblAssetFarmerName"><font style="font-size: 20px"> &nbsp;&nbsp; </font></label>
          </li>
       </ul>
     </div>
     <div class="line-input-keyin">
       <ul>
		  <li class="topic" style="width:130px;padding-right:0px"> <font style="font-size: 20px;">ชื่อทรัพย์สิน :</font></li>
          <li class="topic" style="width: 2px;padding-left:2px"><font color="red">*</font></li>
		  <li class="boxinput" style="width:180px">
			  <select id="assetId"  name="assetId" style="width:180px;">
			     <option value="">กรุณาเลือก</option>
			     <%if(assetList !=null && assetList.size()>0){
			           for(int i=0; i < assetList.size(); i++){
			               Asset asset = (Asset)assetList.get(i);
			               	if(asset.getAssetId() > 0){
			     %>
			           		  <option value="<%= asset.getAssetId() %>"><%= asset.getAssetName() %></option>
			     <%		}
			       	  } 
			       }
			     %>
			  </select>
          </li>
      </ul>
    </div>
	<div class="line-input-keyin">
       <ul>
		  <li class="topic" style="width:130px;padding-right:0px"><font style="font-size: 20px;">วันที่ซื้อ :</font></li>
          <li class="topic" style="width: 2px;padding-left:2px"><font color="red">*</font></li>
		  <li class="boxinput" style="width:300px;">
		       <dcs:validateDate name="assetDate" property="assetDate" style="width:170px;" onChange="isDate('assetDate');" /><font color="red" style="font-size: 18px;">ตย. 31/12/2557</font>
          </li>
      </ul>
    </div>
    <div class="line-input-keyin">
       <ul>
		  <li class="topic" style="width:130px;padding-right:0px"><font style="font-size: 20px;">จำนวนเงิน :</font></li>
          <li class="topic" style="width: 2px;padding-left:2px"><font color = "red">*</font></li>
		  <li class="boxinput" style="width:230px;">
			 <dcs:validateText name="amount" property="amount" style="width:170px;" maxlength="12"  onChange="checkNumberComma(this);" />&nbsp;บาท
          </li>
      </ul>
    </div>
     <div class="line-input-keyin">
       <table> <tr height="20">
	   	    <td colspan="4" style="text-align: left;padding-left: 140px;">
	   			<img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" onclick="saveAssetDetail();"/>
	   			<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="closeWindow('PlantAssetPopup',0);" />
	   	    </td>
	   	 </tr>
       </table>
    </div>
</div>
<!---------------------  Start >> CostListForm : ต้นทุน/ค่าใช้จ่าย  ---------------------->
<div id="CostListForm" title="ต้นทุน/ค่าใช้จ่าย" style="VISIBILITY:hidden;display:none;">

       <table id="costListTable">
		 <tr ><td colspan="3" style="text-align: left;padding: 5px;padding-left: 20px">
				<font style="font-size:20px;">ชนิดพืช: <font color=gray > <label id = "lblCostListBreedType"></label> </font></font>&nbsp; &nbsp; &nbsp; 
				<font style="font-size:20px;">กลุ่มพันธุ์: <font color=gray > <label id = "lblCostBreedGroup"></label></font></font>
			  </td></tr>
		</table>
		<br />
  		 <!---------------------------------------------------พันธุ์พืช--------------------------------------------------->
		<div style="padding-left: 0px;margin-left: 0px">
			<fieldset  style="width: 900px;">
			<div>
				<table style="border-color: #FFE1E1; background-color: #FAF3F3" >
					<tr>
						<td class="field" align="left" width="900px">&nbsp; &nbsp; &nbsp;<b>ต้นทุน/ค่าใช้จ่าย</b></td>
						<td align="right"><img id="subJob_hider5" onclick="subJob5.swapUpDown();" /></td>
				    </tr>
				</table>
			</div>
			<div id="subJob_hider5">  
				<div class="btn-manage" style="margin-left:0px;">
					<a class="btn-add" href="#" onclick="showWindow('CostDetailPopup',0);" ></a>
					<a class="btn-del" href="#" onclick="goDeleteCostDetail();" ></a>
			    </div>
			<br />
			<!-- dynamic grid -->
	        	<div id="dynamicCostDetail" style="width: 900px;padding-left:5px">
	        	</div>
        	<!-- end dynamic grid -->
		    </div>
	    </fieldset>
	    <br />
		<table>
			<tr><td style="padding: 5px;padding-left: 250px">
		 	<img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" style="padding-left: 120px" onclick="saveCostList();"/>
		 	<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="closeWindow('CostListForm',0);" />
			</td></tr>
		</table>
		<br />
	</div>
	<br />
</div>
<!---------------------  Start >> CostDetailPopup : ต้นทุน/ค่าใช้จ่าย  ---------------------->
<div id="CostDetailPopup" title="ต้นทุน /ค่าใช้จ่าย " style="VISIBILITY:hidden;display:none;">
	<table style="width: 600px;padding: 5px" id="costDetailTable">
   	<tr>
   		<td colspan="3" style="text-align: left;padding: 5px"><font>กลุ่มพันธุ์: <font color="grey"><label id = "lblCostDetailBreedGroup"></label></font></font></td>
 	</tr>
   	<tr>
		<td style="text-align: right;padding-right: 0px;width:115px">ต้นทุน / ค่าใช้จ่าย : </td>
		<td style="width: 5px;padding-right: 0px"><font color="red">*</font></td>
		<td style="text-align: left;padding: 5px;width:250px">
			<select id="costId" name="costId" style="width:200px;" >
		          <option value="">กรุณาเลือก</option>
		         	<%if(costList !=null && costList.size()>0){
		                for(int i=0; i<costList.size(); i++){
		                    	Cost cost = (Cost)costList.get(i);
		            %>
		           <option value="<%= cost.getCostId() %>"><%= cost.getCostName() %></option>
		            <%}} %>
		     </select>
		</td>
	</tr>
 	<tr>
		<td style="text-align: right;padding-right: 0px">จำนวนเงิน : </td>
		<td style="padding-right: 0px"><font color="red">*</font></td>
		<td style="text-align: left;padding: 5px"><dcs:validateText name="costAmount" property="costAmount" maxlength="12" style="width:196px;" onChange="checkNumberComma(this);" /></td>
	 </tr>
	 <tr>
		<td style="text-align: right;padding-right: 0px">วันที่จ่าย : </td>
		<td style="padding-right: 0px"><font color="red">*</font></td>
		<td style="text-align: left;padding: 5px">
		<dcs:validateDate name="costDate" property="costDate" style="width:195px;" onChange="isDate('costDate');" /><font color="red" style="font-size: 18px;">ตย. 31/12/2557</font>
		</td>
	 </tr>
	 <tr>
	 	<td colspan ="3"> &nbsp;</td>
	 </tr>
	 <tr>
	 	<td colspan = "3" style="text-align: center;">
	       <img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" onclick="saveCostDetail();"/>
		   <img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="closeWindow('CostDetailPopup',0);" />
		</td>
	 </tr>
   </table>
</div>
<!----------------------- Start >> SaleListForm  : การขายการผลิต  ----------------------->
<div id="SaleListForm" title="รายละเอียดผลผลิตที่เก็บเกี่ยวได้จริง/ข้อมูลการขายสินค้า" style="VISIBILITY:hidden;display:none;">
	<div class="content-keyin">
	<table id = "saleListTable">
		<tr><td colspan="3" style="text-align: left;padding: 5px;padding-left: 20px">
			<font>ชนิดพืช: <font color=gray >ข้าว</font></font>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			<font>กลุ่มพันธุ์: <font color=gray ><label id="lblSaleBreedGroup" ></label></font></font>
		</td></tr>
	</table>
	<br />
   	<div style="margin-left:0px; padding-top:6px; padding-bottom:10px;margin-bottom:10x; background-color:#9EA2A1; no-repeat center bottom; width:925px; height:18px; font-size:20px; color:#FFF;" >&nbsp; &nbsp; &nbsp;ข้อมูลการขายผลผลิต ::</div>
	<br />
	<div style="padding-left: 0px">
		<fieldset style="width: 900px;">
			<div>
				<table style="border-color: #FFE1E1; background-color: #FAF3F3" >
					<tr>
						<td class="field" align="left" width="900px">&nbsp; &nbsp; &nbsp;<b>รายละเอียดผลผลิตที่เก็บเกี่ยวได้จริง/ข้อมูลการขายสินค้า</b></td>
						<!--<td align="right"><img id="subJob_hider6" onclick="subJob6.swapUpDown();" /></td>-->
					</tr>
				</table>
			</div>
			<div id="subJob_hider6">  
			<div class="btn-manage" style="margin-left:0px;">
					<a class="btn-add" href="#" onclick="showWindow('HarvestDetailPopup',0);"></a>
					<a class="btn-del" href="#" onclick="goDeleteHarvestDetail();" ></a>
			</div>
			<br />
			<!--  dynamic grid  -->
			<div id = "dynamicHarvestDetail" style="width: 900px;padding-left:5px;"></div>
			<!--  dynamic grid  -->  
			</div>	
		</fieldset>
	<br />
	</div>
	<br />
	<!---------------------------------------------------รายละเอียดการขายผลผลิต--------------------------------------------------->
	<div style="padding-left: 0px">
		<fieldset style="width: 900px;">
			<div>
				<table style="border-color: #FFE1E1; background-color: #FAF3F3" >
					<tr>
						<td class="field" align="left" width="900px">&nbsp; &nbsp; &nbsp;<b>รายละเอียดการขายผลผลิต</b></td>
						<!-- <td align="right"><img id="subJob_hider6" onclick="subJob6.swapUpDown();" /></td> -->
					</tr>
				</table>
			</div>
			<div id="subJob_hider6">  
			<div class="btn-manage" style="margin-left:0px;">
					<a class="btn-add" href="#" onclick="showWindow('SaleDetailPopup',0);"></a>
					<a class="btn-del" href="#" onclick="goDeleteSellingDetail();" ></a>
			</div>
			<br />
			<!--  dynamic grid  -->
			<div id = "dynamicSaleDetail" style="width: 900px;padding-left:5px;"></div>
			<!--  dynamic grid  -->  
			</div>	
		</fieldset>
	<br />
	      <table>
				<tr><td style="padding: 5px;padding-left: 250px;">
			 	<img src="<%=request.getContextPath() %>/images/btn-ok.png" style="padding-left: 120px" class="btn" onclick="saveHarvestAndSell();"/>
			 	<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="closeWindow('SaleListForm',0);" />
				</td></tr>
		 </table>
	</div>
	<br />
	</div>
</div>
<!---------------------  Start >> SaleDetailPopup : การขายการผลิต  ---------------------->
<div id="SaleDetailPopup" title="รายละเอียดการขายผลผลิต" style="VISIBILITY:hidden;display:none;">
<table id="saleDetailTable" style="width: 550px;padding: 5px;">
	   <tr ><td colspan = "3" style="text-align: left;padding-left: 20px;">
	   		   <font style="font-size: 20px;">พืชที่ปลูก : </font><font color=gray ><label id ="lblSaleDetailBreedType"></label></font>&nbsp;&nbsp;&nbsp;&nbsp;
	   		   <font style="font-size: 20px;">กลุ่มพันธุ์ : </font><font color=gray ><label id ="lblSaleDetailBreedGroup"></label></font>&nbsp;&nbsp;&nbsp;&nbsp;
	   		   <font style="font-size: 20px;">ขนาดพื้นที่ปลูก: </font><font color=gray ><label id ="lblSaleDetailArea"></label></font>
	   </td></tr >
	   <tr > <td colspan = "3" style="text-align: left;padding: 5px;padding-left: 20px">
	   		   <font style="font-size: 20px">ผลผลิตที่เก็บเกี่ยวได้จริง: </font><font color=gray ><label id ="lblSaleDetailHarvest"></label>&nbsp;</font>
	   		</td>
	   </tr>
	   </table>
	    <fieldset style="width: 555px;">
    				 <legend><font size="5px;"><b> รายละเอียดการขายผลผลิต </b></font></legend>
	 <table id="saleDetailTable1" style="width: 550px;padding: 5px;">  
	   <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px;width: 170px;">วันที่ขายผลผลิต (ว/ด/ป) :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px">
		   		<dcs:validateDate name="saleDate" property="saleDate" style="width: 196px;" onChange="isDate('saleDate')"/>
		    	<font color="red" style="font-size: 18px">ตย. 31/12/2557</font>
		    </td>
	   </tr>
	   <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ผลผลิตที่ขายข้าวสด :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px;" ><dcs:validateText name="saleCrop" property="saleCrop" maxlength="10"  style="width:196px;" onChange="checkNumberComma(this);" />&nbsp;กก.</td>
	   </tr>
	   <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ความชื้น :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px;" ><dcs:validateText name="humid" property="humid" maxlength="10"  style="width:196px;" onChange="checkNumberComma(this);"  />%</td>
	   </tr>
	   <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ราคาขายผลผลิตข้าวสดต่อ กก. :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px;"><dcs:validateText name="salePrice" property="salePrice" style="width:196px;" onChange="checkNumberComma(this);" />&nbsp;บาท</td>
	   </tr>
		<tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">จำนวนเงินที่ขายข้าวสดได้ :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px;"><dcs:validateText name="saleAmount" maxlength="12"  property="saleAmount" style="width:196px;" isReadOnly="true" />&nbsp; บาท</td>  
	   </tr>
	   <!-- start : Added on 27/12/2016 -->
	   <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ผลผลิตที่ขายข้าวแห้ง :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px;" ><dcs:validateText name="saleDryCrop" property="saleDryCrop" maxlength="10"  style="width:196px;" onChange="checkNumberComma(this);" />&nbsp;กก.</td>
	   </tr>
	     <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ความชื้น :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px;" ><dcs:validateText name="humidDry" property="humidDry" maxlength="10"  style="width:196px;" onChange="checkNumberComma(this);" />%</td>
	   </tr>
	   <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ราคาขายผลผลิตข้าวแห้งต่อ กก. :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px;"><dcs:validateText name="saleDryPrice" property="saleDryPrice" style="width:196px;" onChange="checkNumberComma(this);" />&nbsp;บาท</td>
	   </tr>
		<tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">จำนวนเงินที่ขายข้าวแห้งได้ :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px;"><dcs:validateText name="saleDryAmount" property="saleDryAmount" maxlength="12" style="width:196px;" isReadOnly="true" />&nbsp; บาท</td>  
	   </tr>
	   
	   <!-- finish : Added on 27/12/2016 -->
	    <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ชื่อผู้ซื้อ :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px">	
		      <dcs:validateDropDown name="buyerId" property="buyerId" dataSource="<%= buyerList %>" keyField="buyerId" displayField="buyerName" isRequire="true" style="width:200px;"  onChange="getAddressInfo('buyerId', 'buyerAddress', 'saleProvinceNo', 'saleDistrictNo', 'saleSubDistrictNo');" /> 
		   </td>
	   </tr>
	   <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ที่ตั้งของผู้ซื้อ :</td>
		   <td style="padding:0px"></td>
		   <td style="text-align: left;padding: 5px"><dcs:validateText name="buyerAddress" property="buyerAddress" maxlength="200"  style="width:196px;" onChange="checkInvalidText(this);" /></td>
	   </tr>
	   <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">จังหวัด :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px">
		   <select id="saleProvinceNo"  name="saleProvinceNo" style="width:200px;" onChange="getDistrictInfo('saleProvinceNo', 'saleDistrictNo', 'saleSubDistrictNo', '');" >
		          <option value="">กรุณาเลือก</option>
		         	<%if(provinceList !=null && provinceList.size()>0){
		                for(int p=0; p<provinceList.size(); p++){
		                    	Province pd = (Province)provinceList.get(p);
		            %>
		           <option value="<%=pd.getProvinceNo()%>"><%=pd.getThaiName()%></option>
		            <%}} %>
		     </select></td>
	   </tr>
	    <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">อำเภอ :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px">
		   		<select id="saleDistrictNo"  name="saleDistrictNo" style="width:200px;" onChange="getSubDistrictInfo('saleProvinceNo', 'saleDistrictNo', 'saleSubDistrictNo', '');">
		 	 	</select></td>
	   </tr>
	    <tr>
		   <td style="text-align: right;padding: 5px;padding-right: 0px">ตำบล :</td>
		   <td style="padding:0px"><font color=red>*</font></td>
		   <td style="text-align: left;padding: 5px">
		    	<select id="saleSubDistrictNo"  name="saleSubDistrictNo" style="width:200px;" >
		 	 	</select>
		   </td>
	   </tr>
	   <tr><td colspan ="3"> &nbsp;</td></tr>
	   </table>
	   </fieldset>
	   <table id="saleDetailTable2" style="width: 550px;padding: 5px;">  
	   <tr>
		   <td colspan="3" style="padding: 5px;text-align: center">
		   <img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" onclick="javascript:saveSaleDetail();" style="padding-left: 70px;"/>
		   <img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="javascript:closeWindow('SaleDetailPopup',0);"/>
		   </td>
	   </tr>
   </table>
 </div>
<!-------------- HarvestDetail Popup -------------->
<div id="HarvestDetailPopup" title="รายละเอียดผลผลิตที่เก็บเกี่ยวได้จริง" style="VISIBILITY:hidden;display:none;">
<table id="harvDetailTable" style="width: 500px;padding: 5px;">
	   <tr >
	   		<td colspan="3" style="text-align: left;padding: 5px;padding-left: 10px;">
	   		<font style="font-size: 20px">พืชที่ปลูก: <font color=gray ><label id ="lblHarvestDetailBreedType"></label></font></font>
	   		&nbsp;&nbsp;<font style="font-size: 20px">พันธุ์ที่ปลูก: <font color=gray ><label id ="lblHarvestDetailBreedGroup"></label></font></font>
	   		&nbsp;&nbsp;<font style="font-size: 20px">ขนาดพื้นที่ปลูก: <font color=gray ><label id ="lblHarvestDetailArea"></label></font></font>
	   		</td>
	   </tr>
	   </table>
	   <fieldset style="width: 525px;">
    	 <legend><font size="5px;"><b> รายละเอียดผลผลิตที่เก็บเกี่ยวได้จริง</b></font></legend>
	  <table id="harvDetailTable1" style="width: 500px;padding: 5px;"> 
	   <tr>
			<td style="padding-left: 20px;">เดือนที่เก็บเกี่ยวได้ (ว/ด/ป) : <font color="red" >*</font> </td>
		   	<td colspan = "2">
		   		<dcs:validateDate name="harvestDate" property="harvestDate" style="width: 150px;" />
		   		<font color="red" style="font-size: 18px">ตย. 31/12/2557</font>
		   	</td>
 	  </tr><tr>
		   	<td style="padding-left: 20px;">ผลผลิตทั้งหมดที่เก็บเกี่ยวได้ :  <font color="red" >*</font>  </td>
		   	<td colspan = "2"><dcs:validateText name="totalHarvest" property="totalHarvest" maxlength="10"  style="width:150px;" onChange="checkNumberComma(this);" />&nbsp; กก.</td>
	 </tr>
	  <tr>
		   	<td style="padding-left: 20px;">ผลผลิตที่เก็บไว้บริโภค :  <font color="red" >*</font>  </td>
		   	<td colspan = "2"><dcs:validateText name="consumeProduct" property="consumeProduct" maxlength="10"  style="width:150px;" onChange="checkNumberComma(this);" />&nbsp; กก.</td>
	  </tr>
	  <tr>
		   	<td style="padding-left: 20px;">ผลผลิตที่เก็บไว้ทำพันธุ์ :  <font color="red" >*</font>  </td>
		   	<td colspan = "2"><dcs:validateText name="breedProduct" property="breedProduct" maxlength="10"  style="width:150px;" onChange="checkNumberComma(this);" />&nbsp; กก.</td>
	</tr>
	  <tr>
		   	<td style="padding-left: 20px;">เหลือไว้ขาย : <font color="red" >*</font></td>
		   	<td colspan = "2"><dcs:validateText name="remainSell" property="remainSell" maxlength="10"  style="width:150px;" onChange="checkNumberComma(this);" />&nbsp; กก.</td>
	  </tr>
	  </table>
	  </fieldset>
	  <table id="harvDetailTable2" style="width: 500px;padding: 5px;">
	  <tr><td colspan = "3">&nbsp;</td></tr>
	  <tr>
		   <td colspan="3" style="padding-left: 50px;">
		   		<img src="<%=request.getContextPath() %>/images/btn-ok.png" class="btn" onclick="javascript:saveHarvestDetail();" style="padding-left: 100px;"/>
		   		<img src="<%=request.getContextPath() %>/images/btn-cancel.png" class="btn" onclick="javascript:closeWindow('HarvestDetailPopup',0);"/>
		   </td>
	   </tr>
   </table>
 </div>
 
 <!-- popup ประวัติการตรวจ -->
 <div id="LandCheckHistory" title="ประวัติการตรวจแปลง" style="VISIBILITY:hidden;display:none;" >
	<fieldset style="width: 950px;">
    				 <legend>:: ประวัติการตรวจแปลง ::</legend>
    				    
    				  
						<!-- start dynamic grid for landcheck history -->
        				<div id="dynamicLandCheckHistory" title="ประวัติการตรวจแปลง" style="width: 960px;padding-left: 5px;">
       	    			</div>
       	    			<br />
       	    			<div id="LandCheckHistorySavePart">
							<div class="btn-manage" align="center" style="margin-left: 0;">
								<a class="btn-cancel" style="float: none;" onclick="javascript:closeWindow('LandCheckHistory',0);"></a>
							</div>
       	    			</div>
        				<!-- finish dynamic grid for landcheck history -->
					</fieldset>
</div>
 
 <!------END  popup ประวัติการตรวจ --->

<%@include file="/footer.jsp" %>
</div>
<input type="hidden" name = "isTab1Saved" id="isTab1Saved" value = "no" />
<input type="hidden" name = "period" />
<input type="hidden" name = "prepareAreaId" />
<input type="hidden" name = "breedGrpName" />
<input type="hidden" name = "sumCheck" value="false" />
<input type="hidden" name = "assetDetailAmount" value="" />
<input type="hidden" name = "assetDetailDate" value="" />
</dcs:validateForm>
<form name="plantListForm" method="post">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="breedTypeId" />
	<input type="hidden" name="plantYear" />
	<input type="hidden" name="plantNo" />
	<input type="hidden" name="branchCode" />
	<input type="hidden" name="firstName" />
	<input type="hidden" name="lastName" />
</form>
<form name="landCheckForm" method="post" action="<%=request.getContextPath()%>/LandCheck.do">
	<input type="hidden" name="cmd" />
	<input type="hidden" name="plantYear" />
	<input type="hidden" name="season" />
	<input type="hidden" name="idCard" />
	<input type="hidden" name="firstName" />
	<input type="hidden" name="lastName" />
	<input type="hidden" name="typeId" />
	<input type="hidden" name="docNo" />
	<input type="hidden" name="docRai" />
	<input type="hidden" name="docNgan" />
	<input type="hidden" name="docWah" />
	<input type="hidden" name="landCheckId" />
	<input type="hidden" name="breedTypeId" />
	<input type="hidden" name="refPlantId" />
	<input type="hidden" name="plantNo" />
</form>

</body>
</html>