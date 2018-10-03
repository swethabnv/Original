var hasValue = 0;
var isDelete = 0; // 1++ = มีการ Delete list ของ plantManure, plantChemi, assetDetail
var isSave = 0;   // 1++ = มีการ Save
var isAdd = 0;

var tempManure = "";
var manureTempArray = new Array();

var tempChemical = "";
var chemiTempArray = new Array();

var tempAsset = "";

var tempCost = "";
var costTempArray = new Array();

var tempHarvest = "";
var harvTempArray = new Array();

var tempSelling = "";
var sellTempArray = new Array();

var subObjective = "";

function enableTextbox(obj)
{	
	if(obj.value == "อื่นๆ"){
  	     if(obj.name == "soilType") {
	  	 	 if(document.getElementById(obj.id).checked){
			 	  document.getElementById("soilTypeName").readOnly = false;
			 } else {
			 	  document.getElementById("soilTypeName").value = "";
				  document.getElementById("soilTypeName").readOnly = true;
			 }
  	 	 }if(obj.name == "plot"){
  	 	 	 if(document.getElementById(obj.id).checked){
	 		 	  document.getElementById("plottingName").readOnly = false;
	 		 } else {
	 		 	  document.getElementById("plottingName").value = "";
	 			  document.getElementById("plottingName").readOnly = true;
	 		 }
  	 	 }
  	 } else {
  	   	 if(obj.id == "ownBuy") {
  	 		document.getElementById("mCostPerRai").readOnly = false;
  	 		document.getElementById("ownProduce").checked = false;
  	 		document.getElementById("ownBuy").checked = true;
  	     } if(obj.id == "ownProduce") {
  	  	 	document.getElementById("mCostPerRai").readOnly = true;
  	  	 	document.getElementById("mCostPerRai").value = "";
  	  	 	document.getElementById("ownProduce").checked = true;
  	 		document.getElementById("ownBuy").checked = false;
  	  	 } if(obj.name == "plot") {
  	  		 if(document.getElementById(obj.id).checked == true){
  	  			 getSubPrepareAreaList(obj.id);
	 		 } else {
	 			 removePrepareArea(obj.id);
	 		 }
  	 	 } if(obj.name == "objective") {
  	 		 var subHtml = "";
  	  		 if(document.getElementById(obj.id).checked == true && obj.value == "เพื่อทำเมล็ดพันธุ์"){
  	  			subHtml += getSubObjectivePlant();
	 		 }
	 		 document.getElementById("SubObjectivePlant").innerHTML = subHtml;
  	 	 }
  	 }
}

function handleClick(source){
    var checkboxes = document.getElementsByName(source.name);
    for(var i=0, n=checkboxes.length; i<n; i++) {
    	checkboxes[i].checked = source.checked;
    }
}

$(function(){  
    $("ul#navi_containTab > li").click(function(event){  
             var menuIndex=$(this).index();
             $("ul#detail_containTab > li:visible").hide(); 
             if(menuIndex == 1){
                 var idCard    = document.plantForm.idCard.value;
                 var firstName = document.plantForm.firstName.value;
                 var lastName  = document.plantForm.lastName.value;
           		 if(idCard != "" && firstName != "" && lastName != ""){
           		 	    document.getElementById('FamilyFieldSet').style.visibility='visible'; 
           		 }else{
           		 	 alert("กรุณากรอกเลขที่บัตรประชาชน ชื่อและนามสกุลของเกษตรกรให้เรียบร้อย!");
           		 	 menuIndex = 0;
           		 }
             }else{
             	if(menuIndex  > 1){
             	   if(document.getElementById("breedTypeId").value == ""){
             	   		 alert("กรุณาเลือกพืชที่ปลูกให้เรียบร้อย");
           		 		 menuIndex = 0;
             	   }
             	}
             }
             $("ul#detail_containTab > li").eq(menuIndex).show();   
	         $("ul#navi_containTab > li").css("background-color","#DDDDDD");
	         $("ul#navi_containTab > li").eq(menuIndex).css("background-color","#C4C4C4");
    });  
 });  
 $(function(){
		$("ul#navi_containBreedTab > li").click(function(event){
			var menuBreedIndex=$(this).index();
			$("ul#detail_containBreedTab > li:visible").hide();
			if(menuBreedIndex > 0 && document.plantForm.isTab1Saved.value == "no") {
				if(menuBreedIndex == 3){
					menuBreedIndex = 3;
				}else{
					alert("กรุณากรอกข้อมูลพืชที่เพาะปลูกให้เรียบร้อย!");
					menuBreedIndex = 0;
				}
			}
			$("ul#detail_containBreedTab > li").eq(menuBreedIndex).show();
            $("ul#navi_containBreedTab > li").css("background-color","#DDDDDD");
            $("ul#navi_containBreedTab > li").eq(menuBreedIndex).css("background-color","#C4C4C4");
		});
 });

 
 function loadPleaseWait(){                          
	 	document.getElementById('Wait').style.visibility='visible';                              
	}

function isMember(obj)
{	// เช็คว่าเป็น ลูกค้า ธ.ก.ส. หรือไม่
	 if(document.getElementById(obj.id).checked)
	 {
	  	 if(obj.id == "farmerGroupMember") {
          	 document.getElementById("farmerGroupMember").checked = true;
          	 document.getElementById("notMember").checked = false;
          	 
          	 
          	 document.getElementById("farmerGroupId").disabled = false;
          	 document.getElementById("customerFarmerGroupNo").disabled = false;
          	 document.getElementById("target").disabled = false;
          	 
         } if(obj.id == "notMember"){
	  	     document.getElementById("notMember").checked = true;
          	 document.getElementById("farmerGroupMember").checked = false;
          	 
          	 document.getElementById("farmerGroupId").disabled = true;
          	 document.getElementById("customerFarmerGroupNo").disabled = true;
          	 document.getElementById("target").disabled = true;
          	 
          	 document.getElementById("farmerGroupId").value = 0;
          	 document.getElementById("customerFarmerGroupNo").value = "";
          	 document.getElementById("target").value = "";
         } 
      } else {
           // alert(obj.id);
          	if(obj.id == "farmerGroupMember") {
          		document.getElementById("farmerGroupMember").checked = false;
          		document.getElementById("farmerGroupId").disabled = true;
              	document.getElementById("customerFarmerGroupNo").disabled = true;
              	document.getElementById("target").disabled = true;
              	
              	 document.getElementById("farmerGroupId").value = 0;
              	 document.getElementById("customerFarmerGroupNo").value = "";
              	 document.getElementById("target").value = "";
          	}
          	if(obj.id == "notMember"){
          		 document.getElementById("notMember").checked = false;
          		 
          		 document.getElementById("farmerGroupId").value = 0;
              	 document.getElementById("customerFarmerGroupNo").value = "";
              	 document.getElementById("target").value = "";
          
          	}
      }
}
function generatePrepareArea(id,value)
{
	  var tableVal = "";
	  tableVal += "<li class='topic' style='width: 20px;padding-right: 1px;'></li>";
   	  tableVal += "<li class='boxinput' style='width: 20px;padding-right: 0px;'>";
   	  tableVal += "<input type ='checkbox' id=plot_" + id + " name='plot' value=" + value + " style='width: 15px;' onclick='enableTextbox(this);' />";
   	  tableVal += "</li>";
   	  tableVal += "<li class='topic' style='width: 10px;padding-right: 3px;'></li>";
	  tableVal += "<li class='topic' style='width: 60px;text-align: left;'><font style='font-size: 20px;'>" + value + "</font></li>";
	  if(value == "อื่นๆ"){
			tableVal += "<li class='topic' style='width: 5px;'>";
			tableVal += "<input type='text' name='plottingName' id='plottingName' maxlength='30' style='width: 60px;' onChange='javascript:checkInvalidText(this);' readonly />";
			tableVal += "</li>";
	  } 
	 return tableVal; 
}
function unCheckAllBoxes(component, contentName){
		var cbx = document.querySelectorAll('input[type="'+component+'"]');
       	for (i = 0, l = cbx.length; i < l; i++) {
			if(cbx[i].name == contentName){
			   	 	document.getElementById(cbx[i].id).checked = false;
            } 
         }
}

function closeWindow(winName, rowIndex){	
     	if(winName == "PlantBreedPopup"){
     		if(rowIndex <= 0){
	     		$('#PlantBreedPopup').dialog( "close" );
	       		breedIndex = 0;
		   		document.getElementById("landRightTypeId").value = "";
	   		    document.getElementById("landDocNo").value = "";
	   	     	document.getElementById("landDocRai").value = "";
	   		 	document.getElementById("landDocNgan").value = "";
	   		 	document.getElementById("landDocWah").value = "";
	   		 	document.getElementById("landDocNo").readOnly = false;
	   	     	document.getElementById("landDocRai").readOnly = false;
	   		 	document.getElementById("landDocNgan").readOnly = false;
	   		 	document.getElementById("landDocWah").readOnly = false;
     		}
       		document.getElementById("breedGroupId").value = "";
	   		document.getElementById("qtyPerRai").value = "";
	    	document.getElementById("plantRai").value = "";
        	document.getElementById("plantNgan").value = "";
        	document.getElementById("plantWah").value = ""
		    document.getElementById("plantDate").value = "";
		    document.getElementById("forecastDate").value = "";
			document.getElementById("forecastRecordDoc").value = "";	
			
			unCheckAllBoxes("radio", "gap");
       		unCheckAllBoxes("checkbox", "seedResource");
       		unCheckAllBoxes("checkbox", "picking");
       		unCheckAllBoxes("radio", "objective");
         	unCheckAllBoxes("radio", "agricultureType");
       		unCheckAllBoxes("radio", "agricultureType");
       		unCheckAllBoxes("checkbox", "planting");
       		unCheckAllBoxes("checkbox", "plot");
       		unCheckAllBoxes("checkbox", "manureUse");
       		
       		document.getElementById("plantDate").value="";
			document.getElementById("forecastDate").value="";
			document.getElementById("forecastRecordDoc").value="";
			document.getElementById("publicMarketDate").value = "";
			document.getElementById("recordForecastDate").value = "";
			document.getElementById("recordForecastBy").value = "";
			document.getElementById("publicCrop").value = "";
			
			document.getElementById("divCoorperative").style.display = 'none';
			document.getElementById("corpGroupId").value = "0";
			document.getElementById("noOfBreed").value = "0";
			
			document.getElementById("subPrepareArea").innerHTML = "";
			detailIndex = 0;
			
			if(isSave == 0 && isDelete > 0)
			{
				// plantManure
				if(manureTempArray[breedDetailIndex] != undefined && manureTempArray[breedDetailIndex] != ""){
						manureArray[breedDetailIndex] = manureTempArray[breedDetailIndex];
						manureTempArray[breedDetailIndex] = "";
				}
				
				// plantChemi
				if(chemiTempArray[breedDetailIndex] != undefined && chemiTempArray[breedDetailIndex] != ""){
						chemArray[breedDetailIndex] = chemiTempArray[breedDetailIndex];
						chemiTempArray[breedDetailIndex] = "";
				}
				
				// assetDetail
				itemAsset = tempAsset;
			    tempAsset = "";
			}
			
			if(isSave == 0 && isAdd > 0){
				manureArray[breedDetailIndex] = manureTempArray[breedDetailIndex];
				manureTempArray[breedDetailIndex] = "";
				
				chemArray[breedDetailIndex] = chemiTempArray[breedDetailIndex];
				chemiTempArray[breedDetailIndex] = "";
				
				itemAsset = tempAsset;
				tempAsset = "";
			}
			
			isDelete = 0;
			isAdd = 0;
			
    	} if(winName == "FarmerFamilyPopup"){
    		$('#FarmerFamilyPopup').dialog( "close" );
      		farmerIndex = 0;
      		document.getElementById("familyIdCard").value = "";
	   		document.getElementById("familyFirstName").value = "";
	   		document.getElementById("familyLastName").value = "";
    	} if(winName == "PlantManurePopup"){
      		$('#PlantManurePopup').dialog( "close" );
      		manureIndex = 0;
      		document.getElementById("manureTypeId").value = "";
	   		document.getElementById("mFormula").value = "";
	   		document.getElementById("mQtyPerRai").value = "";
	   		document.getElementById("mCostPerRai").value = "";
	   		document.getElementById("mName").value = "";
	   		document.getElementById("mPeriodTime").value = 0;
	   		document.getElementById("manureDate").value = "";
	        document.getElementById("ownBuy").checked = false;
	        document.getElementById("ownProduce").checked = false;
  		}
    	if(winName == "PlantChemicalPopup"){
        	$('#PlantChemicalPopup').dialog( "close" );
        	chemIndex = 0;
        	document.getElementById("chemicalTypeId").value = "";
	  		document.getElementById("cFormula").value = "";
	   		document.getElementById("cCostPerRai").value = "";
	   		document.getElementById("cPeriodTime").value = 0;
	   		document.getElementById("chemicalDate").value = "";
	   		document.getElementById("cQtyPerRai").value = "";
    	}
    	if(winName == "PlantAssetPopup"){
         	$('#PlantAssetPopup').dialog( "close" );
         	assetIndex = 0;
         	document.getElementById("assetId").value = "";
	   		document.getElementById("assetDate").value = "";
	   		document.getElementById("amount").value = "";
    	}
    	if(winName == "DocDetailPopup"){
         	$('#DocDetailPopup').dialog( "close" );
         	docDetailIndex = 0; 	
         	
      		document.getElementById("typeId").disabled = false;
			document.getElementById("docNo").readOnly = false;
			document.getElementById("docRai").readOnly = false;
	    	document.getElementById("docNgan").readOnly = false;
	    	document.getElementById("docWah").readOnly = false;
         	document.getElementById("typeId").value = "";
			document.getElementById("docNo").value = "";
			document.getElementById("docRai").value = "";
			document.getElementById("docNgan").value = "";
			document.getElementById("docWah").value = "";
			document.getElementById("docProvinceNo").value = "";
			document.getElementById("docDistrictNo").value = "";
	    	document.getElementById("docSubDistrictNo").value = "";
	    	document.getElementById("landMoo").value="";	    
			document.getElementById("ownRai").checked = false;
			document.getElementById("rentRai").checked = false;
 			document.getElementById("rentPrice").value = "";
            document.getElementById("rentPrice").disabled = false;
			unCheckAllBoxes("radio", "irrigation");
       		unCheckAllBoxes("checkbox", "soil");
       		unCheckAllBoxes("checkbox", "soilType");
			document.getElementById("soilTypeName").value = "";
			document.getElementById("soilTypeName").readOnly = true;
			hasValue = 0;
			objProvNoEdit = 0;
    	}
    	if(winName == "CostListForm"){
         	$('#CostListForm').dialog( "close" );
         	if(isSave == 0 && isDelete > 0){
        		if(costTempArray[breedIndex] != undefined && costTempArray[breedIndex] != ""){
        			costArray[breedIndex] = costTempArray[breedIndex];
        			costTempArray[breedIndex] = "";
        		}
        	}
         	
         	if(isSave == 0 && isAdd > 0){
         		costArray[breedIndex] = costTempArray[breedIndex];
        		costTempArray[breedIndex] = "";
         	}
         	
         	breedIndex = 0;
         	isDelete = 0;
         	isAdd = 0;
         	
    	}
    	if(winName == "CostDetailPopup"){
         	$('#CostDetailPopup').dialog( "close" );
         	costIndex = 0;
         	document.getElementById("costId").value = "";
			document.getElementById("costAmount").value = "";
    	}
    	if(winName == "SaleListForm"){
          
         	$('#SaleListForm').dialog( "close" );
         	if(isSave == 0 && isDelete > 0){
				if(sellTempArray[breedIndex] != undefined && sellTempArray[breedIndex] != ""){
					saleDetailArray[breedIndex] = sellTempArray[breedIndex];
					sellTempArray[breedIndex] = "";
				} 
				if(harvTempArray[breedIndex] != undefined && harvTempArray[breedIndex] != ""){
					harvArray[breedIndex] = harvTempArray[breedIndex];
					harvTempArray[breedIndex] = "";
				}
						
			}
         	
         	if(isSave == 0 && isAdd > 0){
         		saleDetailArray[breedIndex] = sellTempArray[breedIndex];
				sellTempArray[breedIndex] = "";
				harvArray[breedIndex] = harvTempArray[breedIndex];
				harvTempArray[breedIndex] = "";
         	}
         	
			breedIndex = 0;
			isDelete = 0;
			isAdd = 0;
    	}
    	if(winName == "HarvestDetailPopup"){
         	$('#HarvestDetailPopup').dialog( "close" );
         	harvestIndex = 0;
         	document.getElementById("harvestDate").value="";
        	document.getElementById("totalHarvest").value="";
			document.getElementById("remainSell").value="";
    	}
    	if(winName == "SaleDetailPopup"){
         	$('#SaleDetailPopup').dialog( "close" );
         	saleIndex = 0;
         	document.getElementById("saleDate").value = "";
			document.getElementById("saleCrop").value = "";
			document.getElementById("salePrice").value = "";
			document.getElementById("saleAmount").value = "";
			document.getElementById("humid").value = "";
			
			document.getElementById("saleDryCrop").value = "";
			document.getElementById("saleDryPrice").value = "";
			document.getElementById("saleDryAmount").value = "";
			document.getElementById("humidDry").value = "";
			
			document.getElementById("buyerId").value = 0;
			document.getElementById("buyerAddress").value = "";
			document.getElementById("saleProvinceNo").value = "";
			document.getElementById("saleDistrictNo").value = "";
			document.getElementById("saleSubDistrictNo").value = "";
			$('#saleDate').css('border-color', ''); 
			$('#saleCrop').css('border-color', ''); 
        }
    	if(winName == "LandCheckHistory"){
    		$('#LandCheckHistory').dialog( "close" );
    	}
    	
  } 
 function showWindow(winName, rowIndex)
 {	 
	 	 var plantFarmerName = document.getElementById("abbrPrefix").value + " " + document.getElementById("firstName").value + "   " + document.getElementById("lastName").value;
		 document.getElementById("lblFarmerName").innerHTML =  plantFarmerName;
		 document.getElementById("lblBreedFarmerName").innerHTML = plantFarmerName;
		 document.getElementById("lblTabBreed5_1").innerHTML = plantFarmerName;
		 document.getElementById("lblAssetFarmerName").innerHTML = plantFarmerName;
     	if(winName == "PlantBreedPopup")
     	{
	     	    $("ul#detail_containBreedTab > li:visible").hide();			
		   		$("ul#detail_containBreedTab > li").eq(0).show();
	     		$("ul#detail_containBreedTab > li").eq(1).hide();
	     		$("ul#detail_containBreedTab > li").eq(2).hide();
	     		$("ul#detail_containBreedTab > li").eq(3).hide();
	     		$("ul#detail_containBreedTab > li").eq(4).hide();
		        $("ul#navi_containBreedTab > li").css("background-color","#DDDDDD");
		        $("ul#navi_containBreedTab > li").eq(0).css("background-color","#C4C4C4");
		        
		       isSave = 0;
     		   if(rowIndex > 0) {
   		 			document.getElementById("landDocNo").readOnly = true;
   	     			document.getElementById("landDocRai").readOnly = true;
   		 			document.getElementById("landDocNgan").readOnly = true;
   		 			document.getElementById("landDocWah").readOnly = true;
       			} else{
       			    if(rowIndex == 0){
       					breedIndex = 0;
       					detailIndex = 0;
       					breedDetailIndex = 0;
       					document.getElementById("landRightTypeId").disabled = false;
	       				document.getElementById("landRightTypeId").value = "";
	       				document.getElementById("breedGroupId").value = "";
		   				document.getElementById("qtyPerRai").value = "";
	   		    		document.getElementById("landDocNo").value = "";
	   	     			document.getElementById("landDocRai").value = "";
	   		 			document.getElementById("landDocNgan").value = "";
	   		 			document.getElementById("landDocWah").value = "";
	   		 			document.getElementById("landDocNo").readOnly = false;
	   	     			document.getElementById("landDocRai").readOnly = false;
	   		 			document.getElementById("landDocNgan").readOnly = false;
	   		 			document.getElementById("landDocWah").readOnly = false;
	   		 			
	   		 			loadNoticeTab(rowIndex);
	   		 			loadPlantManure(rowIndex);
	   		 			loadPlantChemi(rowIndex);
	   		 			loadAssetDetail();
	   		 			document.plantForm.isTab1Saved.value = "no";	
       				}else{
       				    // rowIndex == -1
	       				document.getElementById("landRightTypeId").disabled = true;
	   		 			document.getElementById("landDocNo").readOnly = true;
	   	     			document.getElementById("landDocRai").readOnly = true;
	   		 			document.getElementById("landDocNgan").readOnly = true;
	   		 			document.getElementById("landDocWah").readOnly = true;
   		 			}
       					
	    			document.getElementById("plantRai").value = "";
        			document.getElementById("plantNgan").value = "";
        			document.getElementById("plantWah").value = ""
		    		document.getElementById("plantDate").value = "";
		    		document.getElementById("forecastDate").value = "";
					document.getElementById("forecastRecordDoc").value = "";
					document.getElementById("publicMarketDate").value = "";
					document.getElementById("recordForecastDate").value = "";
					document.getElementById("recordForecastBy").value = "";
					document.getElementById("publicCrop").value = "";
       				unCheckAllBoxes("radio", "gap");
       				unCheckAllBoxes("checkbox", "seedResource");
       				unCheckAllBoxes("checkbox", "picking");
       				unCheckAllBoxes("radio", "objective");
       				unCheckAllBoxes("radio", "agricultureType");
       				unCheckAllBoxes("checkbox", "planting");
       				unCheckAllBoxes("checkbox", "plot");
       				unCheckAllBoxes("checkbox", "manureUse");
				}
		     		$(document).scrollTop(50); 
		  				$("#PlantBreedPopup").dialog({
		  				position:{
		      	 		 	my:'center top',
		    			 	at:'center top'},
		    			 	height: 1320,
		      	   		 	width: 1050,
		      	   		 	modal: true
		       		});
    	}
    	if(winName == "FarmerFamilyPopup"){
          	if(rowIndex <= 0){
    			familyIndex = 0;
         		document.getElementById("familyIdCard").value="";
        		document.getElementById("familyFirstName").value="";
				document.getElementById("familyLastName").value="";
    		 }
        	  $(document).scrollTop(100);
	         		$("#FarmerFamilyPopup").dialog({
	         		position:{
	   				 	   my:'center top',
	    				   at:'center top'},
	      	   			width: 540,
	      	   			modal: true
	       	 	});
        }
  		if(winName == "PlantManurePopup"){    
  			document.getElementById("lblManureBreedTypeName").innerHTML = breedTypeObjName;
  			//document.getElementById("lblManureBreedGroupName").innerHTML = breedGroupObjName;
  			if(rowIndex <= 0){
  			    manureIndex = 0;
  				document.getElementById("manureTypeId").value = "";
	   			document.getElementById("mFormula").value = "";
	   			document.getElementById("mQtyPerRai").value = "";
	   			document.getElementById("mCostPerRai").value = "";
	   			document.getElementById("mName").value = "";
	   			document.getElementById("mPeriodTime").value = 0;
	   			document.getElementById("manureDate").value = "";
	        	document.getElementById("ownBuy").checked = false;
	        	document.getElementById("ownProduce").checked = false;
  			}
	  		$(document).scrollTop(100);
	      		$("#PlantManurePopup").dialog({
	      		position:{
	   				 my:'center top',
	    			 at:'center top'},
	     	   		height: 550,
	      	   		width: 550,
	      	   		modal: true
	        });
  		}
    	if(winName == "PlantChemicalPopup"){
    		document.getElementById("lblChemiBreedTypeName").innerHTML = breedTypeObjName;
    		//document.getElementById("lblChemiBreedGroupName").innerHTML = breedGroupObjName;
    		if(rowIndex <= 0){
    		    chemIndex = 0;
	    		document.getElementById("chemicalTypeId").value = "";
		  		document.getElementById("cFormula").value = "";
		   		document.getElementById("cCostPerRai").value = "";
		   		document.getElementById("cPeriodTime").value = 0;
		   		document.getElementById("chemicalDate").value = "";
		   		document.getElementById("cQtyPerRai").value = "";
    		}
	    		$(document).scrollTop(100);
	        	 $("#PlantChemicalPopup").dialog({
	        	 position:{
	   				 my:'center top',
	    			 at:'center top'},
	     	   		height: 500,
	      	   		width: 550,
	      	   		modal: true
	        	});
    	}
    	if(winName == "PlantAssetPopup"){
    		if(rowIndex <= 0){
    			assetIndex = 0;
         		document.getElementById("assetId").value = "";
	   			document.getElementById("assetDate").value = "";
	   			document.getElementById("amount").value = "";
    		} 
    		
    		
    		if(isCheckSum == false) {
    			document.getElementById("assetDate").readOnly = "";
    			$(document).scrollTop(100);
         		$("#PlantAssetPopup").dialog({
         			position:{
   				 		my:'center top',
    			 		at:'center top'},
     	   		 		height: 320,
      	   		 		width: 550,
      	   		 		modal: true
        		});
        	} else {
        		alert("คุณไม่สามารถเพิ่มข้อมูลได้ เนื่องจากคลิกเลือกยอดรวม");
        		return false;
        	}
    	} if(winName == "DocDetailPopup") {
		  	 if(rowIndex <= 0) { 	
		  		// Add Case
		  		docDetailIndex = 0; 
	            document.getElementById("typeId").value = "";
				document.getElementById("docNo").value = "";
				document.getElementById("docRai").value = "";
				document.getElementById("docNgan").value = "";
				document.getElementById("docWah").value = "";
				document.getElementById("landMoo").value="";
				document.getElementById("docProvinceNo").value = "";
				document.getElementById("docDistrictNo").value = "";
		    	document.getElementById("docSubDistrictNo").value = "";
		    	document.getElementById("ownRai").checked = false;
				document.getElementById("rentRai").checked = false;
	 			document.getElementById("rentPrice").value = "";
	 			document.getElementById("soilTypeName").value = "";
	 			document.getElementById("soilTypeName").readOnly = true;
	 			unCheckAllBoxes("radio", "waterSource");
	 			unCheckAllBoxes("radio", "irrigation");
       			unCheckAllBoxes("checkbox", "soil");
       			unCheckAllBoxes("checkbox", "soilType");
       			
       		    document.getElementById("typeId").disabled = false;
				document.getElementById("docNo").readOnly = false;
				document.getElementById("docRai").readOnly = false;
		    	document.getElementById("docNgan").readOnly = false;
		    	document.getElementById("docWah").readOnly = false;
			 }else{
				 // Edit Case
			  	  if(hasValue > 0){
		    			document.getElementById("typeId").disabled = true;
		    			document.getElementById("docNo").readOnly = true;
		    			document.getElementById("docRai").readOnly = true;
		    	    	document.getElementById("docNgan").readOnly = true;
		    	    	document.getElementById("docWah").readOnly = true;
		    	  }else{
		    		    document.getElementById("typeId").disabled = false;
						document.getElementById("docNo").readOnly = false;
						document.getElementById("docRai").readOnly = false;
				    	document.getElementById("docNgan").readOnly = false;
				    	document.getElementById("docWah").readOnly = false;
		    	  }
			 }
	    		$(document).scrollTop(100);
	         		$("#DocDetailPopup").dialog({
	         		position:{
	   				 	   my:'center top',
	    				   at:'center top'},
	      	   			width: 1000,
	      	   			modal: true
	       	 	});
    	} if(winName == "CostListForm") {
    		if(rowIndex <= 0)
    			 breedIndex = 0;
    		
	    	$(document).scrollTop(100);
	         	$("#CostListForm").dialog({
	         	position:{
	   				 my:'center top',
	    			 at:'center top'},
	      	   		width: 1000,
	      	  		modal: true
	        	});
    	} if(winName == "CostDetailPopup"){
    		if(rowIndex <= 0){
    			costIndex = 0;
         		document.getElementById("costId").value = "";
				document.getElementById("costAmount").value = "";
				document.getElementById("costDate").value = "";
    		}
	    	$(document).scrollTop(100);
	         	$("#CostDetailPopup").dialog({
	         	position:{
	   				 my:'center top',
	    			 at:'center top'},
	      	   		width: 650,
	      	   		modal: true
	        	});
    	} if(winName == "SaleListForm") {
   			if(rowIndex <= 0)
    			breedIndex = 0;
	    	$(document).scrollTop(100);
	         	$("#SaleListForm").dialog({
	         	position:{
	   				 my:'center top',
	    			 at:'center top'},
	      	   		width: 1000,
	      	   		modal: true
	       	    });
    	} if(winName == "SaleDetailPopup") {
    		if(itemHarvest == "" && sumTotalHarvest <= 0){
    			alert("กรุณากรอกข้อมูล รายละเอียดผลผลิตที่เก็บเกี่ยวได้จริงก่อนเพิ่มการขายผลผลิต!");
    			return false;
    		}
    		
    		document.getElementById("lblSaleDetailHarvest").innerHTML = formatNumber(sumTotalHarvest) + "  กก.";
    		if(rowIndex <= 0){
    			saleIndex = 0;
         		document.getElementById("saleDate").value = "";
				document.getElementById("saleCrop").value = "";
				document.getElementById("salePrice").value = "";
				document.getElementById("saleAmount").value = "";
				document.getElementById("buyerId").value = 0;
				document.getElementById("buyerAddress").value = "";
				document.getElementById("saleProvinceNo").value = "";
				document.getElementById("saleDistrictNo").value = "";
				document.getElementById("saleSubDistrictNo").value = "";
    		}
    		
	    		$(document).scrollTop(150);
	         	$("#SaleDetailPopup").dialog({
	         	position:{
	   				 my:'center top',
	    			 at:'center top'},
	      	   		 width: 630,
	      	  		 modal: true
	        	});
        } if(winName == "HarvestDetailPopup") {
        	
        	 if(rowIndex <= 0){
    			harvestIndex = 0;
         		document.getElementById("harvestDate").value="";
        		document.getElementById("totalHarvest").value="";
				document.getElementById("remainSell").value="";
				document.getElementById("consumeProduct").value="";
				document.getElementById("breedProduct").value="";
    		 }
        	   $(document).scrollTop(150);
	         	$("#HarvestDetailPopup").dialog({
	         	position:{
	   				 my:'center top',
	    			 at:'center top'},
	      	   		 width: 600,
	      	  		 modal: true
	        	});
        } if(winName == "PlantCheckPopup"){
        	 //alert(winName+"!");
        	 $(document).scrollTop(150);
	         	$("#PlantCheckPopup").dialog({
	         	position:{
	   				 my:'center top',
	    			 at:'center top'},
	      	   		 width: 1000,
	      	  		 modal: true
	        	});
        } if(winName == "LandCheckHistory"){
       	 //alert(winName+"!");
       	 $(document).scrollTop(300);
	         	$("#LandCheckHistory").dialog({
	         	position:{
	   				 my:'center top',
	    			 at:'center top'},
	      	   		 width: 1050,
	      	  		 modal: true
	        	});
       }
        
 	    document.getElementById(winName).style.visibility = 'visible';
  } 
 
 // added by Yatphiroon.P 
 function showCooperative(objSeedSrc){
	 if(objSeedSrc.value == "ซื้อจากสหกรณ์"){
		 if(document.getElementById(objSeedSrc.id).checked){
		     document.getElementById('divCoorperative').style.display = 'block';
		 } else {
			  document.getElementById('divCoorperative').style.display = 'none';
			  document.getElementById("corpGroupId").value = "0";
			  document.getElementById("noOfBreed").value = "0";
		 }
	 }
 }
 
 function checkDoc(objPlant){	
     var docRai = document.getElementById("docRai").value;
     var docNgan =  document.getElementById("docNgan").value;
     var docWah =  document.getElementById("docWah").value;
     var plantRai = document.getElementById("plantRai").value;
     var plantNgan =  document.getElementById("plantNgan").value;
     var plantWah =  document.getElementById("plantWah").value;
     if(docRai == "")
        docRai = 0;
     if(docNgan == "")
        docNgan = 0;
     if(docWah == "")
     	 docWah = 0;
     var sumDocArea = convertToWah(parseInt(docRai), parseInt(docNgan), parseInt(docWah));  
     if(plantRai == "")
        plantRai = 0;
     if(plantNgan == "")
        plantNgan = 0;  
     if(plantWah == "")
     	 plantWah = 0;
     var sumArea = 0;
	  if(objPlant.id == "plantRai") {
   	   plantRaiVal = parseInt(objPlant.value);
   	   sumArea = convertToWah(plantRaiVal, parseInt(plantNgan), parseInt(plantWah));
     }if(objPlant.id == "plantNgan") {
   	   plantNganVal = parseInt(objPlant.value);
   	   sumArea = convertToWah(parseInt(plantRai), plantNganVal, parseInt(plantWah));
     }if(objPlant.id == "plantWah") {
   	   plantWahVal = parseInt(objPlant.value);
   	   sumArea = convertToWah(parseInt(plantRai), parseInt(plantNgan), plantWahVal);
     }if(sumArea > sumDocArea){
           alert("ขนาดที่ดินทั้งหมดในพื้นที่ปลูก ไม่สามารถมีค่ามากกว่าขนาดที่ดินทั้งหมดในเอกสารสิทธิ์ !");
	       objPlant.value="";
       	$('#'+ objPlant.id).css('border-color', 'black'); // 22.07.2014
			setTimeout(function() {
    		   objPlant.focus();
  			}, 0);
  			return false;
	 }
}

 // แปลงเป็น วา
 function convertToWah(rai, ngan, wah)
 {
	// 1  งาน = 100 ตารางวา 
    // 1 ไร่ = 400 ตารางวา
	 var raiToWah = 400 * rai;
     var nganToWah = 100 * ngan;
     var sumWah = raiToWah + nganToWah + wah;
     return sumWah;
}
 
// -----------
 
 function getBreedGroupName(objBreedGroup){
     var breedGroupDD = document.getElementById(objBreedGroup);
     breedGroupObjName = breedGroupDD.options[breedGroupDD.selectedIndex].text;
}     
 
 
//------------- Delete Zone ------------ //
 // delete Landright data
 function goDeleteDocDetail(){
 	var arrayOfItem = new Object();
 	var checkList = document.getElementsByName('delLandTypeId');
 	var size = checkList.length;
 	var count=0;
 	var canDelete = 0;
 	for(var i = 1; i < size; i++){
 		if(checkList[i].checked){
 			arrayOfItem[i] = checkList[i].id;
 			count++;
 		}
 	}
 	if (count > 0){
 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?" )) {
 			var newData = "", rearrange = "";
     		var item = itemDetail.split("|");
     		for(var y = 0; y < item.length; y++)
     		{
 				if(item[y] != null && item[y] != undefined && item[y] != "")
 				{  
 					var itemData= new Array();
 					itemData = item[y].split(",");
 					
 					var index = itemData[0];
 					var typeId = itemData[1];
 					var typeName = itemData[2].replace(/'/g , "");
 					var docNo = itemData[3].replace(/'/g , "");
 					var docRai = itemData[4];
 				    var docNgan = itemData[5];
 				    var docWah = itemData[6];
 				    var key = docNo + "" + typeId;	
 				    
 					if(arrayOfItem[y+1] != index) {  // ตัวที่ไม่ได้อยู่ในรายการที่ถูกติ๊ก
  					   if(item[y] != "" && item[y]!=null && item[y] != undefined && item[y] != "undefined"){
  					 	   newData += "|" +item[y];
  					   }
  					} else { // ถ้าตัวที่ติ๊ก เท่ากับ index	 
					     if(breedArray != undefined && breedArray != "" && breedArray != null)
					     {	
					    	 for(var i = 1; i < breedArray.length; i++)
					    	 {
					    	    if(breedArray[i] != undefined && breedArray[i] != "")
					    	    {
					    	    	if(breedArray[i].indexOf("#") > -1) {
					    	    		 var newItemData= new Array();
		  	 	  						 newItemData = breedArray[i].split("#"); // เปลี่ยนจาก index เป็น y+1
		  	 	  					     var itemData = newItemData[0].split(",");
		  	 	  					     
		  	 	  					     var plantTypeId = itemData[1];
		  	 	  					     var plantDocNo = itemData[3].replace(/'/g , "");
		  	 	  					    		 
		  	 	  					     if(plantTypeId == typeId && plantDocNo == docNo)
		  	 	  					     {
		  	 	  					         canDelete=0;
		  	 	  					         if(docNo.indexOf('^') > -1) 
		  	 	  					        	 docNo = docNo.replace(/\^/g,',');
		  	 	  					         
		  	 	  					    	 alert("ไม่สามารถลบเอกสารสิทธิ์ " + typeName + " เลขที่ " + docNo + " ได้เนื่องจากมีข้อมูลอยู่ในหน้า 'ข้อมูลการเพาะปลูกพืช'");
		  	 	  					    	 return false;
		  	 	  					     } else {
		  	 	  					    	 canDelete++;
		  	 	  					     }
					    	    	}
					    	    }
							}
					     }else{
					    	 delete arrayDetail[key]; 
					    	 delete docArray[index];
					    	 var ddKeyLand = typeId + "+" + docNo; 
						     if(dropDownArray.hasOwnProperty(ddKeyLand)){
	 	 				     	delete dropDownArray[ddKeyLand];
	 	 				     	removeOption("landRightTypeId", ddKeyLand);
						     }
					     }
					     
					     if(canDelete > 0){
					    	 delete arrayDetail[key]; 
					    	 delete docArray[index];
					    	 var ddKeyLand = typeId + "+" + docNo; 
						     if(dropDownArray.hasOwnProperty(ddKeyLand)){
	 	 				     	delete dropDownArray[ddKeyLand];
	 	 				     	removeOption("landRightTypeId", ddKeyLand);
						     }
					     }
  					}
 				}
  			}
     		
     		//--- rewrite Landright --- //
			intDetailCnt = 0;
			docArray.clear();
			newData  = newData.substr(1); // initial landright ใหม่
			if(newData != ""){
				var setItem = newData.split("|");
				for(var z = 0; z < setItem.length; z++){
					var data = new Array();
					data = setItem[z].split(",");
					if(setItem[z] != "" && setItem[z] != undefined)
					{
							var rangeOfData = setItem[z].split(",").length;
							var allData = "";
							for(var a = 1; a < rangeOfData; a++){
								allData += "," + data[a];
							}
							
						    intDetailCnt++;
						    if(intDetailCnt == 1){
	    							rearrange += intDetailCnt + allData;
	    					}else{
	    							rearrange += "|" + intDetailCnt + allData;
	    					}
							docArray[intDetailCnt] = intDetailCnt + allData;
					}
				 }
				itemDetail = rearrange;
			}  else{
				itemDetail = "";
			}
    		
    		// Landright	  
    		var myNodeLand = document.getElementById("dynamicDocDetail");
 			while (myNodeLand.firstChild) {
 				myNodeLand.removeChild(myNodeLand.firstChild);
 			}
 			myNodeLand.innerHTML = generateDetail(itemDetail);
    		
 		}
 	}else{
 			alert('กรุณาเลือกรายการที่ต้องการลบ!!');
 	}
 }
 
 //delete plantDetail
 function goDeleteBreed()
 {
    var breedArr = new Object();
 	var checkList = document.getElementsByName('delBreedGroupId');
 	var size = checkList.length;
 	var count=0;
 	for(var i = 1; i < size; i++){
 		if(checkList[i].checked){
  		    breedArr[i] = checkList[i].id;
 		    count++;
 		}
 	}
 	if (count > 0) {
 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?")){ 
 			var newBreed = "";
 			var newBreed2 = "";
 			var newBreed3 = "";
 			var newBreed5 ="";
 			
 			var newCost = "";
 			var newHarv = "";
 			var newSell = "";
 			
 			
     		var item = itemBreed1.split("|");
     		for(var y = 0; y < item.length; y++){
 				var itemData= new Array();
 				if(item[y] != null && item[y] != undefined && item[y] != "")
 				{
 					itemData = item[y].split(",");
 				    var index = itemData[0];
 				    if(breedArr[y+1] != index){
 				    	newBreed += "|" +item[y];
 				    	newBreed2 += "#" + breedArrayTab2[index];
 				    	newBreed3 += "#" + breedArrayTab3[index];
 				    	newBreed5 += "|" + breedArrayTab5[index];
 				    	newCost += "#" + costArray[index];
 				    	newHarv += "#" + harvArray[index];
 				    	newSell += "#" + saleDetailArray[index];
 				    } else {
 				       var typeId = itemData[1];
 				       var typeName = itemData[2].replace(/'/g , "");
 				       var docNo = itemData[3].replace(/'/g , "");
 				       var docRai = itemData[4];
				       var docNgan = itemData[5];
				       var docWah = itemData[6];
 				       var groupId = itemData[7];
 				       var key = typeId + "" + docNo + "" + groupId;
 				       delete arrayBreed[key];
 				       
 				       // เปลี่ยนจาก index เป็น y+1
 				       if(breedArray[index] != "" && breedArray[index] != undefined){
 				     	    delete breedArray[index];
 				     	    delete breedArrayTab1[index];
 				     	    delete breedArrayTab5[index];
 				       }   
 				       if(manureArray[index] != "" && manureArray[index]!= undefined){
 				            delete manureArray[index];  // arrayManure[]
 				            delete breedArrayTab2[index];
 				            manureDetailArray.clear();
 				       }
 					   if(chemArray[index] != "" && chemArray[index]!= undefined){
    					    delete chemArray[index];   // arrayChem[]
    					    delete breedArrayTab3[index];
    					    chemDetailArray.clear();
 					   }
    				   if(assetArray[index] != "" && assetArray[index]!= undefined){
 				     	    delete assetArray[index];  // arrayAsset[]
 				     	    delete breedArrayTab4[index];
 				       }
 				       if(costArray[index] != "" && costArray[index]!= undefined){
 				     	   delete costArray[index];   // arrayCost[]
 				     	   costDetailArray.clear();
 				       }
 					   if(harvArray[index] != ""  && harvArray[index]!= undefined ){
 						   delete harvArray[index];   // arrayHarvest[]
    					   harvDetailArray.clear();
    				   }
    				   if(saleDetailArray[index] != "" && saleDetailArray[index]!= undefined ){
    					   delete saleDetailArray[index];   // arraySelling[]
    					   subSaleArray.clear();
    			       }
 				    }
 			    }
  			}
     		
     		//---- Rewrite BreedTab1 --- //
     		intBreedCnt = 0;
			newBreed = newBreed.substr(1);
			if(newBreed != ""){
				var rearrangeBreed = "";
				var setItem = newBreed.split("|");
				for(var x = 0; x < setItem.length; x++){
					if(setItem[x] != "" && setItem[x] != undefined && setItem[x] != null){
						var data = new Array();
						data = setItem[x].split(",");
						intBreedCnt++;
					   	if(intBreedCnt == 1){
					   		rearrangeBreed += intBreedCnt + ","+ data[1] + "," + data[2] +","+ data[3]+","+ data[4]+","+ data[5]+","+ data[6]+","+data[7]+","+data[8]+","+data[9]+","+data[10]+","+data[11]+","+data[12]+","+data[13]+","+data[14]+","+data[15]+","+ data[16]+","+data[17]+","+data[18]+","+data[19]+","+data[20]+","+data[21]+","+data[22]+","+data[23]+","+data[24]+","+data[25] + "," + data[26] + "," + data[27] + ","+data[28]; 
					   	}else{
							rearrangeBreed += "|" + intBreedCnt + ","+ data[1] + "," + data[2] +","+ data[3]+","+ data[4]+","+ data[5]+","+ data[6]+","+data[7]+","+data[8]+","+data[9]+","+data[10]+","+data[11]+","+data[12]+","+data[13]+","+data[14]+","+data[15]+","+ data[16]+","+data[17]+","+data[18]+","+data[19]+","+data[20]+","+data[21]+","+data[22]+","+data[23]+","+data[24]+","+data[25] + "," + data[26] + "," + data[27] + ","+data[28]; 
					   	}
						breedArrayTab1[intBreedCnt] = intBreedCnt + "," + data[1] + "," + data[2] +","+ data[3]+","+ data[4]+","+ data[5]+","+ data[6]+","+data[7]+","+data[8]+","+data[9]+","+data[10]+","+data[11]+","+data[12]+","+data[13]+","+data[14]+","+data[15]+","+ data[16]+","+data[17]+","+data[18]+","+data[19]+","+data[20]+","+data[21]+","+data[22]+","+data[23]+","+data[24]+","+data[25] + "," + data[26] + "," + data[27] + ","+data[28]; 
					}
				}
				itemBreed1 = rearrangeBreed;
			} else {
			    itemBreed1 = newBreed;
			}	
    		//---- End rewrite BreedTab1 ---//
			
			/// breedTab2
			var counting2 = 0;
     		breedArrayTab2.clear();
			newBreed2 = newBreed2.substr(1);
			if(newBreed2 != ""){
				var setItem2 = newBreed2.split("#");
				for(var x = 0; x < setItem2.length; x++){
					if(setItem2[x] != "" && setItem2[x] != undefined && setItem2[x] != null)
					{
						var data = new Array();
						data = setItem2[x].split("|");
						
						var allData = "";
						for(var a = 0; a < data.length; a++){
							allData += "|" + data[a];
						}
						if(allData != "")
						   allData = allData.substr(1);
							
						counting2++;
						breedArrayTab2[counting2] = allData; 
						manureArray[counting2] = allData; 
					}
				}
			} 	
		
			// breedTab3
			var counting3 = 0;
     		breedArrayTab3.clear();
			newBreed3 = newBreed3.substr(1);
			if(newBreed3 != ""){
				var setItem3 = newBreed3.split("#");
				for(var x = 0; x < setItem3.length; x++){
					if(setItem3[x] != "" && setItem3[x] != undefined && setItem3[x] != null)
					{
						var data = new Array();
						data = setItem3[x].split("|");
						
						var allData = "";
						for(var a = 0; a < data.length; a++){
							allData += "|" + data[a];
						}
						if(allData != "")
							   allData = allData.substr(1);
						
						counting3++;
						breedArrayTab3[counting3] = allData; 
						chemArray[counting3] = allData; 
					}
				}
			} 
			
			
			// costArray
			var countCost = 0;
			costArray.clear();
			newCost = newCost.substr(1);
			if(newCost != ""){
				var setItemCost = newCost.split("#");
				for(var x = 0; x < setItemCost.length; x++){
					if(setItemCost[x] != "" && setItemCost[x] != undefined && setItemCost[x] != null)
					{
						var data = new Array();
						data = setItemCost[x].split("|");
						
						var allData = "";
						for(var a = 0; a < data.length; a++){
							allData += "|" + data[a];
						}
						if(allData != "")
							   allData = allData.substr(1);
						
						countCost++;
						costArray[countCost] = allData; 
					}
				}
			} 
			
			// harvestArray
			var countHarv = 0;
			harvArray.clear();
			newHarv = newHarv.substr(1);
			if(newHarv != ""){
				var setItemHarv = newHarv.split("#");
				for(var x = 0; x < setItemHarv.length; x++){
					if(setItemHarv[x] != "" && setItemHarv[x] != undefined && setItemHarv[x] != null)
					{
						var data = new Array();
						data = setItemHarv[x].split("|");
						
						var allData = "";
						for(var a = 0; a < data.length; a++){
							allData += "|" + data[a];
						}
						if(allData != "")
							   allData = allData.substr(1);
						
						countHarv++;
						harvArray[countHarv] = allData; 
					}
				}
			} 
			
			// sellingArray
			var countSell = 0;
			saleDetailArray.clear();
			newSell = newSell.substr(1);
			if(newSell != ""){
				var setItemSell = newSell.split("#");
				for(var x = 0; x < setItemSell.length; x++){
					if(setItemSell[x] != "" && setItemSell[x] != undefined && setItemSell[x] != null)
					{
						var data = new Array();
						data = setItemSell[x].split("|");
						
						var allData = "";
						for(var a = 0; a < data.length; a++){
							allData += "|" + data[a];
						}
						if(allData != "")
							   allData = allData.substr(1);
						
						countSell++;
						saleDetailArray[countSell] = allData; 
					}
				}
			} 
			
			
			// ---- Rewrite BreedTab5 --- //
     		var counting5 = 0;
     		breedArrayTab5.clear();
			newBreed5 = newBreed5.substr(1);
			if(newBreed5 != ""){
				var setItem5 = newBreed5.split("|");
				for(var x = 0; x < setItem5.length; x++){
					if(setItem5[x] != "" && setItem5[x] != undefined && setItem5[x] != null){
						var data = new Array();
						data = setItem5[x].split(",");
						counting5++;
						breedArrayTab5[counting5] = counting5 + "," + data[1] + "," + data[2] +","+ data[3]+","+ data[4]; 
					}
				}
			} 	
    		//---- End Rewrite BreedTab5 ---//
			
			// Generate PlantDetail
    		var myNode = document.getElementById("dynamicPlantBreed");
 			while (myNode.firstChild) {
     		   myNode.removeChild(myNode.firstChild);
 			}
    		myNode.innerHTML = generateTable(itemBreed1);
 		}
 	} else {
 		alert('กรุณาเลือกรายการที่ต้องการลบ!!');
 	}	
 }
 

 
 
 function goDeleteFamily(){
    var fArray = new Object();
 	var checkList = document.getElementsByName('delIdCard');
 	var size = checkList.length;
 	var count=0;
 	for(var i = 1 ; i < size ; i++){
 		if(checkList[i].checked){
 			fArray[i] = checkList[i].id;
 			count++;
 		}
 	}
 	if (count > 0){
 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?" )) {
 			var newFamily = "";
     		var item = itemFamily.split("|");
     		for(var y = 0; y < item.length; y++){
 				var itemData= new Array();
 				itemData = item[y].split(",");
 				var index = itemData[0];
 				var idcard = itemData[1];
 				if(fArray[y+1] != index) 				
 					newFamily += "|" +item[y]; // ตัวที่ไม่ได้อยู่ในรายการที่ถูกติ๊ก
 			    else 
 				    delete arrayFamily[idcard];
  			}
    			intFamilyCnt = 0;  
    			familyArray.clear(); 
    			newFamily = newFamily.substr(1);
    			if(newFamily != ""){
    			    var arrangeFamily = "";
    				var setItem = newFamily.split("|");
    				for(var x = 0; x < setItem.length; x++){
 					if(setItem[x] != "" && setItem[x] != undefined && setItem[x] != null){
 						var data = new Array();
 						data = setItem[x].split(",");
 						intFamilyCnt++;
 					   	if(intFamilyCnt == 1)
 							arrangeFamily += intFamilyCnt + "," + data[1] + "," + data[2] + "," + data[3]; 
 						else
 							arrangeFamily += "|" + intFamilyCnt + "," + data[1] + "," + data[2] + "," + data[3]; 
 					
 						familyArray[intFamilyCnt] = intFamilyCnt + "," + data[1] + "," + data[2] + "," + data[3]; 
 					}
  				}
    				itemFamily = arrangeFamily;
    			} else {
    			    itemFamily = newFamily;
    			}
    			
    			var myNode = document.getElementById("dynamicFamily");
 			while (myNode.firstChild) {
     			myNode.removeChild(myNode.firstChild);
 			}
    			myNode.innerHTML = generateFamilyTable(itemFamily);
 		}
 	}else{
 			alert('กรุณาเลือกรายการที่ต้องการลบ!!');
 	}
 }
 
 ///
 function goDeleteManure()
 {
	    var mArray = new Object();
	 	var checkList = document.getElementsByName('delManureTypeId');
	 	var size = checkList.length;
	 	var count=0;
	 	for(var i = 1; i < size ; i++){
	 		if(checkList[i].checked){
	 			mArray[i] = checkList[i].id;
	 			count++;
	 		}
	 	}
	 	if (count > 0){
	 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?" )) {
	 			var newManure = "", rearrange = "";
	 			
	 			// ---- Temporary data of plantManure --- //
	 			tempManure = itemManure; // ไว้ใช้เวลาคลิก ลบ แล้วกด ยกเลิก
	 			manureTempArray[breedDetailIndex] = tempManure;
	 			// ---- Finish temporary data of plantManure ---- //
	 			
	 			var item = itemManure.split("|");
	 			for(var y = 0; y < item.length; y++){
	 				var itemData= new Array();
	 				if(item[y] != null && item[y] != undefined && item[y] != ""){
	 					itemData = item[y].split(",");
	 					var index = itemData[0];
	 					var manureTypeId = itemData[1];
	 					var key = manureTypeId + "" + index;
	 					if(manureDetailArray[index] != ""){
	 						delete manureDetailArray[index];
	 						manureDetailArray.splice(manureDetailArray.indexOf(index), 1);
	 					}
	 					if(mArray[y+1] != index){ // ตัวที่ไม่ได้อยู่ในรายการที่ถูกติ๊ก
	 				    	if(item[y] != "" && item[y]!=null && item[y] != undefined && item[y] != "undefined"){
	 				    		newManure += "|" +item[y];
	 					 	}
	 					}else{
	 					    delete arrayManure[key];
	 					}
	 				}
	  			}
	 			
	 			intManureCnt = 0; 
	 			manureDetailArray.clear(); 
    			newManure = newManure.substr(1);
	    		if(newManure != ""){
	    			var setItem = newManure.split("|");
	    			for(var z = 0; z < setItem.length; z++)
	 				{	
	 					var data = new Array();
	 					data = setItem[z].split(",");
	 					var rowIndex = data[0];
	 					if(setItem[z] != "" && setItem[z] != undefined)
	 					{	
	 						intManureCnt++;
	 					   	if(intManureCnt == 1){
	 							rearrange += intManureCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5]+ "," + data[6] + "," + data[7] + "," + data[8] + "," + data[9] + "," + data[10]; 
	 						}else{
	 							rearrange += "|" + intManureCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5]+ "," + data[6] + "," + data[7] + "," + data[8] + "," + data[9] + "," + data[10]; 
	 						}
	 					   manureDetailArray[intManureCnt] = intManureCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5]+ "," + data[6] + "," + data[7] + "," + data[8] + "," + data[9] + "," + data[10]; 
	 					}
	  				}
	    				  itemManure = rearrange;
	    			}else {
	    				  itemManure = newManure;
	    			}
	    			manureArray[breedDetailIndex] = itemManure; // ตอนแรกเป็น breedIndex
	    		
	    			var myNode = document.getElementById("dynamicPlantManure");
	    			while (myNode.firstChild){
	    				myNode.removeChild(myNode.firstChild);
	    			}
	 				myNode.innerHTML = generateManureTable(itemManure);
	 				
	 				isDelete++; // ได้ลบไปแล้ว
	 		}
	 	}else{
	 		alert('กรุณาเลือกรายการที่ต้องการลบ!!');
	 	}
}
 
 function goDeleteChemical(){
 	var cArray = new Object();
 	var checkList = document.getElementsByName('delChemicalTypeId');
 	var size = checkList.length;
 	var count=0;
 	for(var i = 1; i < size ;i++){
 		if(checkList[i].checked){
 			cArray[i] = checkList[i].id;
 			count++;
 		}
 	}
 	if (count > 0){
 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?" )) {
 			var newChem = "", rearrange = "";
 			
 			// ---- Temporary data of plantChemi --- //
 			tempChemical  = itemChemi; // ไว้ใช้เวลาคลิก ลบ แล้วกด ยกเลิก
 			chemiTempArray [breedDetailIndex] = tempChemical;
 			// ---- Finish temporary data of plantChemi ---- //
 			
     		var item = itemChemi.split("|");
 			for(var y = 0; y < item.length; y++){
 				var itemData= new Array();
 				if(item[y] != null && item[y] != undefined && item[y] != ""){
 					itemData = item[y].split(",");
 					var index = itemData[0];
 					var key = itemData[1] + "" + index;
 					if(chemDetailArray[index] != ""){
 						delete chemDetailArray[index];
 						chemDetailArray.splice(chemDetailArray.indexOf(index), 1);
 					}
 					if(cArray[y+1] != index){ // ตัวที่ไม่ได้อยู่ในรายการที่ถูกติ๊ก
 				    	if(item[y] != "" && item[y]!=null && item[y] != undefined && item[y] != "undefined"){
 				    		newChem += "|" +item[y];
 					 	}
 					}else{
 					    delete arrayChem[key];
 					}
 				}
  			}
 			
 			intChemCnt = 0; 
 			chemDetailArray.clear(); 
 			newChem = newChem.substr(1);
    		if(newChem != ""){
    			var setItem = newChem.split("|");
    			for(var z = 0; z < setItem.length; z++){	
 					var data = new Array();
 					data = setItem[z].split(",");
 					var rowIndex = data[0];
 					if(setItem[z] != "" && setItem[z] != undefined){	
 						intChemCnt++;
 					   	if(intChemCnt == 1){
 					   		rearrange += intChemCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5]+ "," + data[6] + "," + data[7]; 
 						}else{
 							rearrange += "|" + intChemCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5]+ "," + data[6] + "," + data[7]; 
 						}
 					   chemDetailArray[intChemCnt] = intChemCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5]+ "," + data[6] + "," + data[7]; 
 					}
  				}
    				itemChemi = rearrange;
    			}else {
    				itemChemi = newChem;
    			}
    		
    		    chemArray[breedDetailIndex] = itemChemi;
    			var myNode = document.getElementById("dynamicPlantChemical");
    			while (myNode.firstChild) {
    				myNode.removeChild(myNode.firstChild);
    			}
    			myNode.innerHTML = generateChemicalTable(itemChemi);
    			
    			isDelete++; // ได้ลบไปแล้ว
    			
 		}
 	}else{
 		alert('กรุณาเลือกรายการที่ต้องการลบ!!');
 	}
 }
 function goDeleteAsset()
 {
 	var arrayOfItem = new Object();
 	var checkList = document.getElementsByName('delAssetId');
 	var size = checkList.length;
 	var count=0;
 	var sAmount = 0;
 	for(var i = 1; i < size ; i++){
 		if(checkList[i].checked){
 			arrayOfItem[i] = checkList[i].id;
 			count++;
 		}
 	}
 	
 	if (count > 0){
 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?" ))
 		{		
 				var newAsset = "", rearrangeAsset = "";
 			
	 		    // ---- Temporary data of assetDetail --- //
	 			tempAsset = itemAsset; // ไว้ใช้เวลาคลิก ลบ แล้วกด ยกเลิก
	 			// ---- Finish temporary data of plantChemi ---- //
	 			
	     		var item = itemAsset.split("|");
	     		for(var y = 0; y < item.length; y++){
	 				var itemData= new Array();
	 				itemData = item[y].split(",");
	 				var index = itemData[0];
	 				var assId = itemData[1];
	 				if(arrayOfItem[y+1] != index){
	 					newAsset += "|" +item[y]; // ตัวที่ไม่ได้อยู่ในรายการที่ถูกติ๊ก
	 				}else{
	 					delete arrayAsset[assId];
	 				}
	  			}
     		
  				intAssetCnt = 0;  // just added
  				assetArray.clear(); // just added
    			newAsset  = newAsset.substr(1); 
    			if(newAsset != ""){
    				var setItem = newAsset.split("|");
    				for(var x = 0; x < setItem.length; x++){
 					if(setItem[x] != "" && setItem[x] != undefined && setItem[x] != null){
 						var data = new Array();
 						data = setItem[x].split(",");
 						intAssetCnt++;
 					   	if(intAssetCnt == 1){
 							rearrangeAsset += intAssetCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4]; 
 					   	}else{
 							rearrangeAsset += "|" + intAssetCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4]; 
 					   	}
 						sAmount += parseFloat(data[4]);
 						assetArray[intAssetCnt] = intAssetCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4]; 
 					}
  				}
    				itemAsset = rearrangeAsset;
    			}else{
    			    itemAsset = newAsset;
    			}
    			var myNode = document.getElementById("dynamicPlantAsset");
    			while (myNode.firstChild) {
    				myNode.removeChild(myNode.firstChild);
    			}
    			myNode.innerHTML = generateAssetTable(itemAsset);
    			
    			isDelete++; // ได้ลบไปแล้ว
    			
    			sAmount = sAmount + "";
    			document.getElementById("assetAmount").value = formatNumber(sAmount);
 		}
 	}else{
 		var isSumCheck = document.getElementById("sumCheckbox").checked;
 		if(isSumCheck == true){
 		  	alert('คุณไม่สามารถลบยอดรวมได้!!');
 		  	return false;
 	   } else {
 			alert('กรุณาเลือกรายการที่ต้องการลบ!!');
 			return false;
 	   }
 	}
 	
 }
 
 
 function goDeleteCostDetail(){
 	var arrayOfItem = new Object();
 	var checkList = document.getElementsByName('delCostId');
 	var size = checkList.length;
 	var count=0;
 	for(var i = 1; i < size; i++){
 		if(checkList[i].checked){
 			arrayOfItem[i] = checkList[i].id;
 			count++;
 		}
 	}
 	if (count > 0){
 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?" )) {
 				var newData = "", rearrange = "";
	 		    // ---- Temporary data of costDetail --- //
	 			tempCost  = itemCost; // ไว้ใช้เวลาคลิก ลบ แล้วกด ยกเลิก
	 			costTempArray[breedIndex] = tempCost;
	 			// ---- Finish temporary data of costDetail ---- //
	 			
     			var item = itemCost.split("|");
	     		for(var y = 0; y < item.length; y++)
	 			{
	 				var itemData= new Array();
	 				itemData = item[y].split(",");
	 				if(item[y] != null && item[y] != undefined && item[y] != ""){
	 					var index = itemData[0];
	 					var costId = itemData[1];
	 					if(costDetailArray[index] != ""){
	 						delete costDetailArray[index];
	 				    	costDetailArray.splice(costDetailArray.indexOf(index), 1);
	 					}
	 					if(arrayOfItem[y+1] != index){ // ตัวที่ไม่ได้อยู่ในรายการที่ถูกติ๊ก
	 				    	if(item[y] != "" && item[y]!=null && item[y] != undefined && item[y] != "undefined"){
	 					 		 newData += "|" +item[y];
	 					 	}
	 					}else{
	 					    	delete arrayCost[costId];
	 					}
	 				}
	  			}
	  			intCostCnt = 0;
	  			costDetailArray.clear();
    			newData  = newData.substr(1); 
    			if(newData != ""){
    				var setItem = newData.split("|");
    				for(var z = 0; z < setItem.length; z++){	
	 					var data = new Array();
	 					data = setItem[z].split(",");
	 					var rowIndex = data[0];
	 					if(setItem[z] != "" && setItem[z] != undefined){
	 						intCostCnt++;
	 					   	if(intCostCnt == 1){
	 							rearrange += intCostCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4];
	 						}else{
	 							rearrange += "|" + intCostCnt + "," + data[1] + "," + data[2] + "," + data[3]+ "," + data[4];
	 						}
	 						costDetailArray[intCostCnt] = intCostCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4];
	 					}
    				}
    				itemCost = rearrange;
    			}else{
    				itemCost = newData;
    			}
    			costArray[breedIndex] = itemCost;
    			var myNode = document.getElementById("dynamicCostDetail");
	 			while (myNode.firstChild) {
	     			myNode.removeChild(myNode.firstChild);
	 			}
	 			myNode.innerHTML = generateCostTable(itemCost);
	 			
	 			isDelete++; // ได้ลบไปแล้ว
 		}
 	}else{
 			alert('กรุณาเลือกรายการที่ต้องการลบ!!');
 	}
 }
 function goDeleteHarvestDetail()
 {
 	var arrayOfItem = new Object();
 	var checkList = document.getElementsByName('delHarvDate');
 	var size = checkList.length;
 	var count=0;
 	for(var i = 1; i < size; i++){
 		if(checkList[i].checked){
 			arrayOfItem[i] = checkList[i].id;
 			count++;
 		}
 	}
 	
 	if (count > 0){
 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?" )) 
 		{
 			if(itemSale != ""){
 		 		alert("ไม่สามารถลบข้อมูลผลผลิตการเก็บเกี่ยวได้ เนื่องจากยังมีข้อมูลการขายสินค้าอยู่!");
 		 		return false;
 		 	}
 			
 			var newData = "", rearrange = "";
 			
 			// ---- Temporary data of HarvestDetail --- //
 			tempHarvest  = itemHarvest; // ไว้ใช้เวลาคลิก ลบ แล้วกด ยกเลิก
 			harvTempArray[breedIndex] = tempHarvest;
 			// ---- Finish temporary data of HarvestDetail ---- //
 			
     		var item = itemHarvest.split("|");
     		for(var y = 0; y < item.length; y++){
 				if(item[y] != undefined && item[y] != "")
 				{
 					var itemData = new Array();
 	 				itemData = item[y].split(",");
 					var index = itemData[0];
 					var harvDate = itemData[1];
 					var key = harvDate;
 					if(harvDetailArray[index] != ""){
 						delete harvDetailArray[index];
 				    	harvDetailArray.splice(harvDetailArray.indexOf(index), 1);
 					}
 					if(arrayOfItem[y+1] != index){ // ตัวที่ไม่ได้อยู่ในรายการที่ถูกติ๊ก
 				    	if(item[y] != "" && item[y]!=null && item[y] != undefined && item[y] != "undefined"){
 					 		 newData += "|" +item[y];
 					 	}
 					}else{
 					    delete arrayHarvest[key];
 					}
 				}
  			}
     		intHarvestCnt = 0;
  			harvDetailArray.clear();
    			newData  = newData.substr(1); // initial itemHarvest ใหม่
    			if(newData != ""){
    				var setItem = newData.split("|");
    				for(var z = 0; z < setItem.length; z++){	
 					var data = new Array();
 					data = setItem[z].split(",");
 					var rowIndex = data[0];
 					if(setItem[z] != "" && setItem[z] != undefined){
 						intHarvestCnt++;
 					   	if(intHarvestCnt == 1){
 							rearrange += intHarvestCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5];
 						}else{
 							rearrange += "|" + intHarvestCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5];
 						}
 						harvDetailArray[intHarvestCnt] = intHarvestCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5];
 					}
  				}
    				itemHarvest = rearrange;
    			} else {
    				itemHarvest = newData;
    			}
    			
    			harvArray[breedIndex] = itemHarvest;
    			var myNode = document.getElementById("dynamicHarvestDetail");
    			while (myNode.firstChild) {
    				myNode.removeChild(myNode.firstChild);
    			}
    			myNode.innerHTML = generateHarvestTable(itemHarvest);
    			
    			isDelete++; // ได้ลบไปแล้ว
 		}
 	}else{
 			alert('กรุณาเลือกรายการที่ต้องการลบ!!');
 	}
 }
 function goDeleteSellingDetail()
 {
 	var arrayOfItem = new Object();
 	var checkList = document.getElementsByName('delSaleDate');
 	var size = checkList.length;
 	var count=0;
 	for(var i = 0; i < size; i++){
 		if(checkList[i].checked){
 			arrayOfItem[i] = checkList[i].id;
 			count++;
 		}
 	}
 	
 	if (count > 0){
 		if (confirm("คุณต้องการลบข้อมูลใช่หรือไม่?")){
 			var newSale = "", rearrangeSale = "";
 		    // ---- Temporary data of SellingDetail --- //
 			tempSelling  = itemSale; // ไว้ใช้เวลาคลิก ลบ แล้วกด ยกเลิก
 			sellTempArray[breedIndex] = tempSelling;
 			// ---- Finish temporary data of SellingDetail ---- //
 			
     		var item = itemSale.split("|");
     		for(var y = 0; y < item.length; y++){
 				var itemData= new Array();
 				itemData = item[y].split(",");
 				var index = itemData[0];
 				var saleDate = itemData[1];
 				var key = index + "" + saleDate;
 			 	if(subSaleArray[index] != ""){
 					delete subSaleArray[index];
 				    subSaleArray.splice(subSaleArray.indexOf(index), 1);
 				}
 			    if(cropArray[index] != ""){
 					delete cropArray[index];
 				    cropArray.splice(cropArray.indexOf(index), 1);
 				}
 			    if(arrayOfItem[y+1] == index){
 				    delete arraySelling[key];
 			    } else {
 					newSale += "|" +item[y];
 			    }
  			}
  			intSellingCnt = 0;
  			subSaleArray.clear();
  			cropArray.clear();
    			newSale  = newSale.substr(1); 
    			if(newSale != ""){ 
    				var setItem = newSale.split("|");
    				for(var z = 0; z < setItem.length; z++){
 					var data = new Array();
 					data = setItem[z].split(",");
 					var rowIndex = data[0];
 					if(setItem[z] != "" && setItem[z] != undefined){
 						intSellingCnt++;
 					   	if(intSellingCnt == 1)
 							rearrangeSale += intSellingCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] +  "," + data[5] + "," + data[6] + "," + data[7] + "," + data[8] + "," + data[9] + "," + data[10] + "," + data[11] + "," + data[12] + "," + data[13] + "," + data[14] + "," + data[15];
 						else
 							rearrangeSale += "|" + intSellingCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] +  "," + data[5] + "," + data[6] + "," + data[7] + "," + data[8] + "," + data[9] + "," + data[10] + "," + data[11] + "," + data[12] + "," + data[13] + "," + data[14] + "," + data[15];
 						
 						subSaleArray[intSellingCnt] = intSellingCnt + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] +  "," + data[5] + "," + data[6] + "," + data[7] + "," + data[8] + "," + data[9] + "," + data[10] + "," + data[11] + "," + data[12] + "," + data[13] + "," + data[14] + "," + data[15];
 						cropArray[intSellingCnt] = data[2];
 					}
  				}
    				itemSale = rearrangeSale;
    			} else {
    			    itemSale = newSale;
    			}
    			
    		    saleDetailArray[breedIndex] = itemSale;
    		    var myNode = document.getElementById("dynamicSaleDetail");
	 			while (myNode.firstChild) {
	     			myNode.removeChild(myNode.firstChild);
	 			}
    			myNode.innerHTML = generateSaleTable(itemSale);
    			
    			isDelete++; // ได้ลบไปแล้ว
 		}
 	}else{
 			alert('กรุณาเลือกรายการที่ต้องการลบ!!');
 	}

 }
 // ------------ close delete zone -------------------- //
 

 function checkSum(sumObj,id1,id2){
     isCheckSum = sumObj.checked;
     if(itemAsset != null && itemAsset != "" && itemAsset != undefined){
 		  alert("ไม่สามารถเลือกยอดรวมได้เนื่องจากมีข้อมูลอยู่แล้ว");
 		  sumObj.checked = false;
 		  isCheckSum = false;
          return false;
     } else {
         if(isCheckSum == true) {
 			id1.readOnly = false; 
 			id2.readOnly = false; 
     	} else {
     		id1.readOnly = true; 
 			id2.readOnly = true;
 			globAssetDate =  "";
			globAssetAmount = "";
			document.getElementById("assetAmount").value = "";
			document.getElementById("dateAsset").value ="";
    	 }
     } 
 }
 
 // --- load tab --- //
//-------- Farmer Family ---- //
 
 function loadFarmerFamily()
 {
 		 if(itemFamily.indexOf("|") > -1){
 			var newItemData= new Array();
 	 		newItemData = itemFamily.split("|");
 	 		intFamilyCnt = newItemData.length;
		 }else{
			var newItemData = new Array();
			newItemData = itemFamily.split(",");
 			intFamilyCnt = newItemData[0];
		 }
 		
 		for (var id in arrayFamily)   
 		      delete arrayFamily[id]; 
 		arrayFamily = {};
 }

 // -------- Plant Manure -----------//
 function loadPlantManure(rowIndex)
 {
	  if(manureArray[rowIndex] == undefined){
 	   	     itemManure = "";
 			 intManureCnt = 0;
 			 
 			 manureTempArray[rowIndex] = "";
 	  }else{
 			if(manureArray[rowIndex] != "undefined" && manureArray[rowIndex] != "" && manureArray[rowIndex] != null){
 				 itemManure = manureArray[rowIndex];
 				 if(itemManure.indexOf("|") > -1){
 		 			var itemManureArray = new Array();
 		 			itemManureArray = itemManure.split("|");
 		 			intManureCnt = itemManureArray.length;
 				 }else{
 					var itemManureArray = new Array();
 		 			itemManureArray = itemManure.split(",");
 		 			intManureCnt = itemManureArray[0];
 				 }
 			}else{
 				 itemManure = "";
 				 intManureCnt = 0;
 	  	   	}
 			
 			manureTempArray[rowIndex] = manureArray[rowIndex];
 			tempManure = manureTempArray[rowIndex];
 			
   	  }
   	  for(var id in arrayManure){
 		  delete arrayManure[id]; 
 	  }
 	  arrayManure = {};
 	  document.getElementById("dynamicPlantManure").innerHTML = generateManureTable(itemManure);
 }
 function loadPlantChemi(rowIndex)
 {
 	  if(chemArray[rowIndex] == undefined){
 	   	     itemChemi = "";
 			 intChemCnt = 0;
 			 chemiTempArray[rowIndex] = "";
 	  } else {
 			if(chemArray[rowIndex] != "undefined" && chemArray[rowIndex] != "" && chemArray[rowIndex] != null){
 				 itemChemi = chemArray[rowIndex];
 				
				 if(itemChemi.indexOf("|") > -1) {
		 			var itemChemiArray = new Array();
		 			itemChemiArray = itemChemi.split("|");
		 			intChemCnt = itemChemiArray.length;
				 } else {
	 				var itemChemiArray = new Array();
	 				itemChemiArray = itemChemi.split(",");
	 		 		intChemCnt = itemChemiArray[0];
	 		     }
 			} else {
 				itemChemi = "";
 				intChemCnt = 0;
 		  	}
 			chemiTempArray[rowIndex] = chemArray[rowIndex];
 			tempChemical = chemiTempArray[rowIndex];
   	}
   	for (var id in arrayChem){
 	   delete arrayChem[id];  
 	}
 	arrayChem = {};
 	document.getElementById("dynamicPlantChemical").innerHTML = generateChemicalTable(itemChemi);
 }

 function loadAssetDetail(){
         if(itemAsset != ""){
			var newItemData= new Array();
		 	newItemData = itemAsset.split("|");
		 	for(var i=0; i< newItemData.length;i++)
		 	{
		 		 var rowIndex = newItemData[i].split(",")[0];
		 		 assetArray[rowIndex] = newItemData[i];
				 var assetId = newItemData[i].split(",")[1];
		 		 arrayAsset[assetId] = rowIndex;
		 	 }
		 	 intAssetCnt = newItemData.length;
         }
         tempAsset =  itemAsset;
         document.getElementById("dynamicPlantAsset").innerHTML = generateAssetTable(itemAsset);
 }
 
 
 
 function loadNoticeTab(rowIndex)
 {  
			var data = breedArrayTab5[rowIndex];
			if(data != "" && data !=null && data != undefined && data.length > 0) {
	 				var newItemData= new Array();
	 				newItemData = data.split(",");
	 				var publicMarketDate = newItemData[1];
	    			var publicCrop = newItemData[2].replace(/'/g, '');
	    			var recordForecastDate = newItemData[3];	
	    			var recordForecastBy = newItemData[4]; 
	    			
	    			if(publicMarketDate != "" && publicMarketDate != undefined && publicMarketDate != "undefined"){
	    				document.getElementById("publicMarketDate").value = publicMarketDate.replace(/'/g, '');
	    			}else{
	    				document.getElementById("publicMarketDate").value = "";
	    			}
	    			
	    			if(publicCrop != "" && publicCrop != "undefined" && publicCrop != undefined){	
	    				document.getElementById("publicCrop").value = formatNumber(publicCrop);
	    			}else{
	    				document.getElementById("publicCrop").value = "";
	    			}
	    			
	    			if(recordForecastDate != null && recordForecastDate != "" && recordForecastDate != undefined && recordForecastDate != "null"){	
	    				document.getElementById("recordForecastDate").value = recordForecastDate.replace(/'/g, '');
	    			}else{
	    				document.getElementById("recordForecastDate").value = "";
	    			}
	    			
	    			if(recordForecastBy != null && recordForecastBy != "" && recordForecastBy != undefined && recordForecastBy != "null"){	
	    			    document.getElementById("recordForecastBy").value = recordForecastBy.replace(/'/g, '');
	    			}else{
	    				document.getElementById("recordForecastBy").value = "";
	    			}
		    } else{
			    document.getElementById("publicMarketDate").value = "";
	   			document.getElementById("publicCrop").value = "";
	   			document.getElementById("recordForecastDate").value = "";
	   			document.getElementById("recordForecastBy").value = "";
		    }
 }
//----------- รายละเอียด การเก็บเกี่ยว ----------- //
 function loadHarvestDetail(index)
 {      
	    if(harvArray[index]  == undefined ){
 			 itemHarvest = "";
 			 intHarvestCnt = 0;
 		} else {
 			if(harvArray[index] != "undefined" && harvArray[index] != undefined && harvArray[index] != null){
 				 itemHarvest = harvArray[index];
				 if(itemHarvest.indexOf("|") > -1){
		 			 var itemArray = new Array();
		 			 itemArray = itemHarvest.split("|");
		 			 intHarvestCnt = itemArray.length;
				 } else {
					 var itemArray = new Array();
					 itemArray = itemHarvest.split(",");
					 intHarvestCnt = itemArray[0];
				 }
			}else{
				itemHarvest = "";
				intHarvestCnt = 0;
			}
   	   	}
	    
   	   	for (var id in arrayHarvest)   {
 		     delete arrayHarvest[id]; 
 		}
 		arrayHarvest = {};
 		document.getElementById("dynamicHarvestDetail").innerHTML = generateHarvestTable(itemHarvest);
 }
 // รายละเอียดการขายผลผลิต
 function loadSellingDetail(index)
 {     
	    if(saleDetailArray[index] == undefined ){
    	      itemSale = "";
    	      intSellingCnt = 0;
 		} else {
 			if(saleDetailArray[index] != "undefined" && saleDetailArray[index] != undefined && saleDetailArray[index] != ""){
 				itemSale = saleDetailArray[index];
				 if(itemSale.indexOf("|") > -1){
		 			 var itemArray = new Array();
		 			 itemArray = itemSale.split("|");
		 			 intSellingCnt = itemArray.length;
				 } else {
					 var itemArray = new Array();
					 itemArray = itemSale.split(",");
					 intSellingCnt = itemArray[0];
				 }
			}else{
	 			itemSale = "";
	 			intSellingCnt = 0;
			}
   	   	}
   	   	for(var id in arraySelling){
 		    delete arraySelling[id]; 
 		}
   	    arraySelling = {};
 		document.getElementById("dynamicSaleDetail").innerHTML = generateSaleTable(itemSale);
 }
 
 function loadLandTable(landStr)
 {
	   itemDetail ="";
       if(landStr != undefined && landStr != "" && landStr != "undefined")
       {
	 		var newItemData= new Array();
	 		newItemData = landStr.split("|");
	 		docArray.clear();
	 		for(var i=0; i < newItemData.length; i++){
	 			var itemData= new Array();
	 			itemData = newItemData[i].split(",");
	 			var rowIndex =  itemData[0];
	 			if(newItemData[i] != "" && newItemData[i] != undefined  && newItemData[i] != "undefined"){
	 				docArray[rowIndex] = newItemData[i];
	 				itemDetail += "|" + docArray[rowIndex];
	 				var landArray = new Array();
	 				landArray = docArray[rowIndex].split(",");
	 				var key = landArray[1] + "+" + landArray[3];
	 				if(!dropDownArray.hasOwnProperty(key)) {
	 					addObjectToDropdownList("landRightTypeId", landArray[1], landArray[2], landArray[3], landArray[4], landArray[5], landArray[6], rowIndex);
	 				}
	 			}
	 		}
	 			 		
	 		if(itemDetail !=""){
	 			itemDetail = itemDetail.substr(1);
	 			intDetailCnt = docArray.length-1;
	 		}
     }
     document.getElementById("dynamicDocDetail").innerHTML = generateDetail(itemDetail);  // landRitght
 }
 // --- finish load tab --- //
 
 
 function loadBreedTable(breedStr)
 {
       itemBreed1 = "";
       itemBreed5 = "";
       itemCost = "";
       itemHarvest = "";
       itemSale = "";
       if(breedStr !=null && breedStr.length>0)
       {
	 		var newItemData= new Array();
	 		newItemData = breedStr.split("$");
	 		breedArray.clear();
	 		breedArrayTab1.clear();
	 		breedArrayTab2.clear();
	 		breedArrayTab3.clear();
	 		breedArrayTab4.clear();
	 		breedArrayTab5.clear();
	 		var cnt = 1;
	 		for(var i=0; i< newItemData.length;i++)
	 		{
	 			var itemData= new Array();
	 			if(newItemData[i].indexOf('#') > -1)
	 			{
	 				var ddKey = "";
	 				// overall
	 				itemData = newItemData[i].split("#");
	 				var landDetail = itemData[0];
	 				var rowIndex = 0;
	 				if(itemData[0] != undefined  && itemData[0]!= "" && itemData[0] != "undefined" ){
	 					rowIndex = cnt;
	 					
	 					// --- set value to generate table ---
	 					var detailArray = new Array();
	 					detailArray = itemData[0].split(",");
	 					var briefData = "";
	 					for(var t=1; t< detailArray.length; t++){
	 					   briefData += "," + detailArray[t];
	 					}
	 					if(briefData != ""){
	 						briefData = briefData.substr(1);
	 					}
	 					
	 					breedArrayTab1[rowIndex] = rowIndex + "," + briefData;
	 					itemBreed1 += "|" + breedArrayTab1[rowIndex];
	 					
	 				    // plantManure
	 					if(itemData[1] != undefined  && itemData[1]!= "" && itemData[1] != "undefined" ){
	 						breedArrayTab2[rowIndex] = itemData[1];
	 						manureArray[rowIndex] = itemData[1];
	 					}
	 					// plantChemical
	 					if(itemData[2] != undefined  && itemData[2]!= "" && itemData[2] != "undefined" ){
	 				 		breedArrayTab3[rowIndex] = itemData[2];
	 					 	chemArray[rowIndex]= itemData[2];
	 				 	}
	 					// แจ้งล่วงหน้า 1 เดือน
	 					if(itemData[4] != undefined && itemData[4]!="" && itemData[4] != "undefined"){
	 						breedArrayTab5[rowIndex] = itemData[4];
	 						itemBreed5 += "|" + breedArrayTab5[rowIndex];
	 						//alert("breedArrayTab5["+rowIndex+"] = "+breedArrayTab5[rowIndex] + "!");
	 					}
	 					// costDetail
	 					if(itemData[5] != undefined && itemData[5]!="" && itemData[5]!= "undefined"){
	 						costArray[rowIndex] = itemData[5];
	 					}
	 					// harvestDetail
	 					if(itemData[6] != undefined  &&  itemData[6]!="" && itemData[6] != "undefined"){
	 						harvArray[rowIndex] = itemData[6]; 
	 					}
	 					// sellDetail
	 				    if(itemData[7] != undefined &&  itemData[7]!="" && itemData[7] != "undefined"){
	 	  	 			    saleDetailArray[rowIndex] =  itemData[7];
	 	  	 			}
	 				    // set all breeds to breedArray
		 	  	 		breedArray[rowIndex] = breedArrayTab1[rowIndex] + "#" + breedArrayTab2[rowIndex] + "#" + breedArrayTab3[rowIndex] + "#" + itemAsset + "#" + breedArrayTab5[rowIndex];	
	 					cnt++;
	 				}
	 			    
	 								
	 			}
	 		}
	 		 if(itemBreed1 != ""){
	 		    itemBreed1 = itemBreed1.substr(1);
	 			var itemBreedArray = new Array();
	 			itemBreedArray = itemBreed1.split("|");
	 		    intBreedCnt = itemBreedArray.length;
	 		 }else{
	 			intBreedCnt = 0;
	 		 }
     }
    document.getElementById("dynamicPlantBreed").innerHTML = generateTable(itemBreed1);  // plantDetail
 }
 // --- finish load tab --- //
 
//เช็คกรรมสิทธิ์ที่ดิน
 function checkOwner(obj)
 {
 	if(document.getElementById(obj.id).checked) {
 	  	if(obj.id == "rentRai"){
 			document.getElementById("rentRai").checked = true;
 			document.getElementById("ownRai").checked = false;
 			document.getElementById("rentPrice").disabled = false;
 			document.getElementById("rentPrice").readOnly = false;
 		}if(obj.id == "ownRai"){
 			document.getElementById("rentRai").checked = false;
 			document.getElementById("ownRai").checked = true;
 			document.getElementById("rentPrice").disabled = true;
 			document.getElementById("rentPrice").readOnly = true;
 			document.getElementById("rentPrice").value = "";
 		}
      }else{
          	document.getElementById("rentPrice").disabled = true;
 			document.getElementById("rentPrice").readOnly = true;
 			document.getElementById("rentPrice").value = "";
       }
 }
 
 

//เช็คนาปี-นาปรัง
function checkField(obj)
{
	if(document.getElementById(obj.id).checked) {
	  	if(obj.id == "inSeason"){
			document.getElementById("inSeason").checked = true;
			document.getElementById("offSeason").checked = false;
		}if(obj.id == "offSeason"){
			document.getElementById("inSeason").checked = false;
			document.getElementById("offSeason").checked = true;
		}
     }
}
 
 function otherOnClicked(obj)
 {
 	if(document.getElementById(obj.id).checked) 
 	{
     	if(obj.value == "อื่นๆ"){
     		document.getElementById("soilTypeName").disabled = false;
     		document.getElementById("soilTypeName").readOnly = false;
     		document.getElementById("soilTypeName").value = "";
    	    } 
     } else {
     		document.getElementById("soilTypeName").disabled = true;
     		document.getElementById("soilTypeName").readOnly = true;
     		document.getElementById("soilTypeName").value = "";
     }
 }
 
//------------ Farmer Family ----------//

 function saveFamily()
 { 
 	  if(document.getElementById("familyIdCard").value == ""){
 		  alert("กรุณากรอกเลขที่บัตรประชาชน");
 		  document.getElementById("familyIdCard").focus();
 		  return false;
 	  } else { 
 		 if(document.getElementById("familyIdCard").value.length != 13){
 	    	alert("กรุณาใส่รหัสบัตรประจำตัวประชาชน 13 หลัก");  
 	    	document.getElementById("familyIdCard").focus();
 	 		return false;
 		 }
 	  }
 	  
 	  if(document.getElementById("familyFirstName").value == ""){
 		  alert("กรุณากรอกชื่อ");
 		  document.getElementById("familyFirstName").focus();
 		  return false;
 	  } if(document.getElementById("familyLastName").value == ""){
 		  alert("กรุณากรอกนามสกุล");
 		  document.getElementById("familyLastName").focus();
 		  return false;
 	  } if(saveFamilyData() != false){
 	  	 document.getElementById("dynamicFamily").innerHTML = generateFamilyTable(itemFamily); 
       	 closeWindow('FarmerFamilyPopup');
       }
 }
 function saveFamilyData()
 {
 	 	 var fIdCard = document.getElementById("familyIdCard").value;
 	  	 var fFirstName = document.getElementById("familyFirstName").value;
 	  	 var fLastName = document.getElementById("familyLastName").value;
 		
 	  	 if(familyIndex > 0 && familyArray[familyIndex] != "" && familyArray[familyIndex] != undefined){
 	  		    itemFamily = "";
 				for(var q=1; q < familyArray.length; q++){
 					  var rowIndex = familyArray[q].split(",")[0];
 					   if(q == familyIndex){
 						   var prevIdCard  = familyArray[q].split(",")[1];
 						   if(arrayFamily.hasOwnProperty(prevIdCard)){
 							   if(fIdCard != prevIdCard){
 								   if(!arrayFamily.hasOwnProperty(fIdCard)){
 									  familyArray[q] =  q  + "," + fIdCard + "," + fFirstName + "," + fLastName;
 									  delete arrayFamily[prevIdCard];
 								   } else {
 									  alert("เลขที่บัตรประชาชนหมายเลข  '" + fIdCard + "' มีอยู่แล้วในระบบ");
 									  return false;
 								   }
 							   } else{
 								   	  familyArray[q] =  q  + "," + fIdCard + "," + fFirstName + "," + fLastName;	   	  
 							   }  
 						  } else {
 							  familyArray[q] =  q  + "," + fIdCard + "," + fFirstName + "," + fLastName;
 						  }
 				    } 
 				    itemFamily += "|" + familyArray[q];
 				}
 				itemFamily = itemFamily.substr(1);
			    familyIndex = 0;
 		 } else {
 		  	   if(arrayFamily.hasOwnProperty(fIdCard)) {
 			  		alert("มีหมายเลขบัตรประชาชนนี้อยู่แล้วในระบบ");
 			  		return false;
 			  }
 			  arrayFamily[fIdCard] = fIdCard;
 			  intFamilyCnt++;
 			  familyArray[intFamilyCnt] = intFamilyCnt + "," + fIdCard + "," + fFirstName + "," + fLastName;
 		      if(intFamilyCnt == 1) {
 			       itemFamily += intFamilyCnt + "," + fIdCard + "," + fFirstName + "," + fLastName;
 		      } else {
 				   itemFamily += "|" + intFamilyCnt + "," + fIdCard +"," + fFirstName + "," + fLastName; 
 			  }
 		 }
 }
 // ---- finish family farmer ---- //
 // ---- edit function -- //
 // edit Family
 function editFamily(rowIndex, idCard, firstName, lastName){
       familyIndex = rowIndex;
       document.getElementById("familyIdCard").value = idCard;
	   document.getElementById("familyFirstName").value = firstName;
	   document.getElementById("familyLastName").value = lastName;
	   showWindow('FarmerFamilyPopup');
}
 

 function editManure(rowIndex, typeId, ownBuy, ownProd, mName, formula, cost, mPeriodTime, manureDate, qty)
 {
        manureIndex = rowIndex;
        document.getElementById("manureTypeId").value = typeId;
 	   if(ownBuy == 1){
 	       document.getElementById("ownBuy").checked = true;
 	       document.getElementById("ownProduce").checked = false;
 	       document.getElementById("mCostPerRai").readOnly = false;
 	   }
 	   if(ownProd == 1){
 	       document.getElementById("ownBuy").checked = false;
 	       document.getElementById("ownProduce").checked = true;
 	       document.getElementById("mCostPerRai").readOnly = true;
 	   }
 	   document.getElementById("mName").value = mName;
 	   document.getElementById("mFormula").value = formula;
 	   document.getElementById("mCostPerRai").value = formatNumber(cost);
 	   document.getElementById("mPeriodTime").value = mPeriodTime;
 	   document.getElementById("manureDate").value = manureDate;
 	   document.getElementById("mQtyPerRai").value = formatNumber(qty);  
  	   showWindow('PlantManurePopup',rowIndex);
 }
 function editChem(rowIndex, typeId, formula, cost, period, cDate, qty)
 {
 	   chemIndex = rowIndex;
       document.getElementById("chemicalTypeId").value = typeId;
 	   document.getElementById("cFormula").value = formula;
 	   document.getElementById("cCostPerRai").value = formatNumber(cost);
 	   document.getElementById("cQtyPerRai").value = formatNumber(qty);
 	   document.getElementById("cPeriodTime").value =period;
 	   document.getElementById("chemicalDate").value = cDate;
  	   showWindow('PlantChemicalPopup', rowIndex);
 }
 function editAsset(rowIndex, assetId, assetName, assetDate, amount)
 {
 	   assetIndex = rowIndex;
       document.getElementById("assetId").value = assetId;
 	   document.getElementById("assetDate").value = assetDate;
 	   document.getElementById("amount").value = formatNumber(amount);
  	   showWindow('PlantAssetPopup', rowIndex);
 }
 function editSale(rowIndex, provinceNo, districtNo, subDistrictNo, saleDate, saleCrop, salePrice, saleAmount, buyerId, buyerName, buyerAddress, saleDryCrop, saleDryPrice, saleDryAmount, humidDry, humid)
 {
 		saleIndex = rowIndex;
        document.getElementById("saleDate").value = saleDate;
 	    document.getElementById("saleCrop").value = formatNumber(saleCrop);
        document.getElementById("salePrice").value = formatNumber(salePrice);
 	    document.getElementById("saleAmount").value = formatNumber(saleAmount);
 	    
 	    document.getElementById("saleDryCrop").value = formatNumber(saleDryCrop);
        document.getElementById("saleDryPrice").value = formatNumber(saleDryPrice);
	    document.getElementById("saleDryAmount").value = formatNumber(saleDryAmount);
	    
	    document.getElementById("humidDry").value = formatNumber(humidDry);
	    document.getElementById("humid").value = formatNumber(humid);
 	    
        document.getElementById("buyerId").value = buyerId;
        getAddressInfo('buyerId', 'buyerAddress', 'saleProvinceNo', 'saleDistrictNo', 'saleSubDistrictNo');
   		document.getElementById("buyerAddress").value = buyerAddress;
        document.getElementById("saleProvinceNo").value = provinceNo;
 	    document.getElementById("saleDistrictNo").value = districtNo;
 	    document.getElementById("saleSubDistrictNo").value= subDistrictNo;
 	    showWindow('SaleDetailPopup', rowIndex);
 }
 function editDocDetail(docDetailItem, index, groupId, groupName)
 {  
      	 breedIndex = index;
 		 if(docArray[index].trim() != "" && docArray[index] != undefined && docArray[index] != null && docArray[index].trim() != "undefined"){
 		    intDetailCnt = docArray[index].split("|").length;
 		    itemDetail = docArray[index];
 		 } else {
 		    intDetailCnt = 0;
 		    itemDetail = "";
 		 }
 		 for (var key in arrayDetail)   
 		     delete arrayDetail[key]; 
 		 arrayDetail = {};
 		 getBreedGroupPeriod(groupId); // ช่วงเวลาเพาะปลูก - เก็บเกี่ยว
 		 document.getElementById('lblBreedTypePlantDetail').innerHTML = breedTypeObjName;
 		 document.getElementById('lblBreedGroupPlantDetail').innerHTML = groupName;
 		 document.getElementById('lblBreedGroupName').innerHTML = groupName;
   		 document.getElementById("dynamicDocDetail").innerHTML = generateDetail(itemDetail);
 }

 function editCost(rowIndex, id, amt, dtCost)
 {
	    costIndex = rowIndex;
	 	document.getElementById("costId").value = id;
	 	document.getElementById("costAmount").value = formatNumber(amt);
	 	document.getElementById("costDate").value = dtCost;
	  	showWindow('CostDetailPopup', rowIndex);
 }
 function editCostDetail(costListItem, index, groupId, groupName)
 {	
 		breedIndex = index;
 		if(costArray[index].trim() != "" && costArray[index] != undefined  && costArray[index] != null && costArray[index].trim() != "undefined"){
 		    intCostCnt = costArray[index].split("|").length;
 		    itemCost = costArray[index];
 		}else{
 		    intCostCnt = 0;
 		    itemCost = "";
 		}
 		
 		for (var id in arrayCost)   
 		     delete arrayCost[id];  //alert(arrayCost[id]+"!");
 		arrayCost = {};
 		
 		document.getElementById("dynamicCostDetail").innerHTML = generateCostTable(itemCost);
 		document.getElementById('lblCostListBreedType').innerHTML = breedTypeObjName;
 		document.getElementById('lblCostBreedGroup').innerHTML = groupName;
 		document.getElementById('lblCostDetailBreedGroup').innerHTML = groupName;
 		showWindow('CostListForm',index);
 }
 function editHarvest(rowIndex, harvDate, totalHarv, remail, consume, breed)
 {
	    harvestIndex = rowIndex;
	 	document.getElementById("harvestDate").value = harvDate;
	 	document.getElementById("totalHarvest").value = formatNumber(totalHarv);
	 	document.getElementById("consumeProduct").value = formatNumber(consume);
	 	document.getElementById("breedProduct").value = formatNumber(breed);
	 	document.getElementById("remainSell").value = formatNumber(remail);
	  	showWindow('HarvestDetailPopup', rowIndex);
 }
 function editHarvestDetail(harvDetailItem, index, groupId, groupName)
 {	
 		breedIndex = index;	
 		showWindow('SaleListForm', index);
 		document.getElementById('lblSaleBreedGroup').innerHTML = groupName;
 		document.getElementById('lblSaleDetailBreedGroup').innerHTML = groupName;
 		if(harvDetailItem != "" && harvDetailItem != undefined && harvDetailItem != null && harvDetailItem != "undefined")
 	   		 itemHarvest = harvDetailItem;
 		loadHarvestDetail(index);
 		document.getElementById("harvDetailId").value = index;
 } 
 
//---- finish edit function zone ---- ///
 
 //ต้นทุน/ค่าใช้จ่าย
function saveCostList()
{
	isSave++;
	costTempArray[breedIndex] = "";	
 	closeWindow('CostListForm',0);
}
function saveCostDetail(){
	   if(document.getElementById("costId").value == ""){
			alert("กรุณาเลือกประเภทต้นทุน/ค่าใช้จ่าย");  
			document.getElementById("costId").focus();
			return false;
	   }
	   if(document.getElementById("costAmount").value == ""){
			alert("กรุณาใส่ราคา");  
			document.getElementById("costAmount").focus();
			return false;
	   }
	   if(document.getElementById("costDate").value == ""){
			alert("กรุณาใส่วันที่จ่าย");  
			document.getElementById("costDate").focus();
			return false;
	   }
	   if(saveCostData()!= false){
	   		document.getElementById("dynamicCostDetail").innerHTML = generateCostTable(itemCost);
	   		isAdd++;
	   		closeWindow('CostDetailPopup',0);
	   }
}
function saveCostData(){
       var costId = document.getElementById("costId").value;
	   var cObj =  document.getElementById("costId");
	 //  var costName = cObj.options[cObj.selectedIndex].text;
	   var costName = cObj.options[cObj.selectedIndex].text.replace(/,/g , "-");
	  // alert(costName + "!");
	   var costAmount = document.getElementById("costAmount").value.replace(/,/g , "");
	   var costDate = document.getElementById("costDate").value;
	
	   if(costDetailArray[costIndex]!="" && costDetailArray[costIndex] != undefined)
	   {
			  	itemCost = "";
		        for(var i=1; i< costDetailArray.length; i++){ 
		           if(i == costIndex){
		                var prevCostId =  costDetailArray[i].split(",")[1];
		                if(arrayCost.hasOwnProperty(prevCostId))
		                {
		                   if(prevCostId != costId){ //เปลี่ยนประเภททรัพย์สินแต่เป็น index เดิม
		                   		if(!arrayCost.hasOwnProperty(costId)){
		                   		   costDetailArray[i] =  i  + "," + costId + "," + costName + "," + costAmount + "," + costDate;
		                   		   delete arrayCost[prevCostId];
		                   		}else{
		                	       alert("ต้นทุน/ค่าใช้จ่ายซ้ำ");  // 08.05.2015
		                	       return false;
		                	     }
		               	   }else{
		               	       costDetailArray[i] =  i  + "," + costId + "," + costName + "," + costAmount + "," + costDate;
		                   }
		                }else{
				  			   costDetailArray[i] =  i  + "," + costId + "," + costName + "," + costAmount + "," + costDate;
				  		}
				  	}
				  	itemCost += "|" + costDetailArray[i];
				}
				itemCost = itemCost.substr(1);
			    costArray[breedIndex] = itemCost;
	   	        costIndex = 0;
	   } else {
	   	    if(arrayCost.hasOwnProperty(costId)){
	 	       	alert("ต้นทุน/ค่าใช้จ่ายซ้ำ");  // 08.05.2015
			    return false; 
	   	    }
		    intCostCnt  = intCostCnt  + 1;
		    arrayCost[costId] = intCostCnt;	
		    if(intCostCnt  == 1){
			    itemCost  += intCostCnt  + "," + costId + "," + costName + "," + costAmount + "," + costDate;
		    } else {
			    itemCost  += "|" + intCostCnt  + "," + costId +"," + costName + "," + costAmount + "," + costDate; 
			}
		    costArray[breedIndex] = itemCost;
	  }	 
}
function saveHarvestDetail()
{
	    var harvestDate = document.getElementById("harvestDate").value;
		var totalHarvest = document.getElementById("totalHarvest").value.replace(/,/g , "");
	    var remainSell = document.getElementById("remainSell").value.replace(/,/g , "");
		var consumeProduct = document.getElementById("consumeProduct").value.replace(/,/g , "");
	    var breedProduct = document.getElementById("breedProduct").value.replace(/,/g , "");
	    
	    
	 	if(harvestDate == "") {
	 		 alert("กรุณาใส่เดือนที่เก็บเกี่ยวได้ (ว/ด/ป)!");
	 		 document.getElementById("harvestDate").focus();
	 		 return false;
	 	} 
	 	if(totalHarvest == ""){
		     alert("กรุณากรอกค่าผลผลิตที่เก็บเกี่ยวได้ทั้งหมด!");
		     document.getElementById("totalHarvest").focus();
		     return false;
		} 
	 	if(remainSell == ""){
	         alert("กรุณากรอกค่าผลผลิตที่เหลือไว้ขาย!");
	         document.getElementById("remainSell").focus();
	         return false;
	    } 
		if(consumeProduct == ""){
	         alert("กรุณากรอกค่าผลผลิตที่เก็บไว้บริโภค !");
	         document.getElementById("consumeProduct").focus();
	         return false;
	    } 
		if(breedProduct == ""){
	         alert("กรุณากรอกค่าผลผลิตที่เก็บไว้ทำพันธุ์ !");
	         document.getElementById("breedProduct").focus();
	         return false;
	    } 
	 	if(globPlantDate != "" && harvestDate != "" ) { 
		   	if(compareDate(globPlantDate, harvestDate) == false){
		      		 alert("วันที่เก็บเกี่ยว ไม่สามารถเลือกย้อนหลังและไม่สามารถเป็นวันเดียวกันกับวันที่เพาะปลูกได้");
		      		 document.getElementById("harvestDate").focus();
		      		 return false;
		  		} 
		}
	 	
	 	totalHarvest = Number(totalHarvest.replace(/[^0-9\.]+/g,""));
	 	remainSell = Number(remainSell.replace(/[^0-9\.]+/g,""));
	 	consumeProduct = Number(consumeProduct.replace(/[^0-9\.]+/g,""));
	 	breedProduct = Number(breedProduct.replace(/[^0-9\.]+/g,""));
	 	
	 	var totalProduct = remainSell + consumeProduct + breedProduct;
	 	/*
		if(remainSell > totalHarvest){
	    	 alert("ผลผลิตที่เหลือไว้ขาย ไม่สามารถมีมากกว่าผลผลิตที่เก็บเกี่ยวได้ทั้งหมด!");
	         document.getElementById("remainSell").focus();
	         return false;
	    }*/
		
		if(totalProduct > totalHarvest){
			 alert("ยอดรวมผลผลิตที่เหลือไว้ขาย, ผลผลิตที่เก็บไว้บริโภค และ ผลผลิตที่เก็บไว้ทำพันธุ์  ไม่สามารถมีค่ามากกว่าผลผลิตที่เก็บเกี่ยวได้ทั้งหมด!");
	         document.getElementById("remainSell").focus();
	         return false;
			
		}
	    
	    if(saveHarvestData(harvestDate, totalHarvest, remainSell, consumeProduct, breedProduct) != false){
	   		document.getElementById("dynamicHarvestDetail").innerHTML = generateHarvestTable(itemHarvest);		
	   		isAdd++;
	   		closeWindow('HarvestDetailPopup',0);
	    }
}
function saveHarvestData(harvestDate, totalHarvest, remainSell, consumeProduct, breedProduct)
{
	 if(harvDetailArray[harvestIndex] != "" && harvDetailArray[harvestIndex] != undefined && harvDetailArray[harvestIndex] != null)
	 {
			  	itemHarvest = "";
		        for(var i=1; i< harvDetailArray.length; i++){ 
		           if(i == harvestIndex){
		          		var prevHarvIndx =  harvDetailArray[i].split(",")[0];
		                var prevHarvDate =  harvDetailArray[i].split(",")[1];
		                var prevKey = prevHarvDate;
		                var currentKey = harvestDate;
		                if(arrayHarvest.hasOwnProperty(prevKey)){
		                   if(prevKey != currentKey){ 
		                   		if(!arrayHarvest.hasOwnProperty(currentKey)) {
		                   		   harvDetailArray[i] =  i  + "," + harvestDate + "," + totalHarvest + "," + remainSell + "," +  consumeProduct + "," + breedProduct;
		           				   delete arrayHarvest[currentKey];
		           				   sumHarvArr[i] = remainSell;
		                   		} else {
		                	       alert("ข้อมูลซ้ำกัน");
		                	       return false;
		                	    }
		               	   } else {
		               	       harvDetailArray[i] = i + "," + harvestDate + "," + totalHarvest + "," + remainSell + "," +  consumeProduct + "," + breedProduct;
		               	       sumHarvArr[i] = remainSell;
		                   }
		                } else {
				  			   harvDetailArray[i] = i + "," + harvestDate + "," + totalHarvest + "," + remainSell + "," +  consumeProduct + "," + breedProduct;
				  			   sumHarvArr[i] = remainSell;
				  		}
				  	}
				  	itemHarvest += "|" + harvDetailArray[i];
				}
				itemHarvest = itemHarvest.substr(1);
			    harvArray[breedIndex] = itemHarvest;
	   	        harvestIndex = 0;
	   } else {
		    
		   if(arrayHarvest.hasOwnProperty(harvestDate)){
	 	       	alert("เลือกวันที่เก็บเกี่ยวซ้ำ!"); 
			    return false; 
	   	    }
		    intHarvestCnt++;
		    arrayHarvest[harvestDate] = intHarvestCnt;	
		    
		    if(intHarvestCnt  == 1){
			    itemHarvest += intHarvestCnt  + "," + harvestDate + "," + totalHarvest + "," + remainSell + "," +  consumeProduct + "," + breedProduct;
		    } else {
			    itemHarvest += "|" + intHarvestCnt + "," + harvestDate + "," + totalHarvest + "," + remainSell + "," +  consumeProduct + "," + breedProduct;
			}
		    harvArray[breedIndex] = itemHarvest;
	  }
}

//----------- รายละเอียด การขาย----------- //
function saveSaleDetail()
{
     var saleDate = document.getElementById("saleDate").value;
	 var saleCrop = document.getElementById("saleCrop").value.replace(/,/g , "");
     var salePrice= document.getElementById("salePrice").value.replace(/,/g , "");
	 var saleAmount = document.getElementById("saleAmount").value.replace(/,/g , "");
	 
	 // just added on 27/12/2016
	 var saleDryCrop = document.getElementById("saleDryCrop").value.replace(/,/g , "");
     var saleDryPrice= document.getElementById("saleDryPrice").value.replace(/,/g , "");
	 var saleDryAmount = document.getElementById("saleDryAmount").value.replace(/,/g , "");
	 
	 // added on 5/1/2017
	 var humidDry= document.getElementById("humidDry").value.replace(/,/g , "");
	 var humid = document.getElementById("humid").value.replace(/,/g , "");

     var buyerId = document.getElementById("buyerId").value;
     var buyerObj =  document.getElementById("buyerId");
	 var buyerName = buyerObj.options[buyerObj.selectedIndex].text;
     var buyerAddress = document.getElementById("buyerAddress").value;
     var provinceNo= document.getElementById("saleProvinceNo").value;
     var districtNo = document.getElementById("saleDistrictNo").value;
     var subDistrictNo = document.getElementById("saleSubDistrictNo").value;
    
     var canSave = 0;
     
	   if(saleDate == ""){
	       alert("กรุณาเลือกวันที่ขายผลผลิต"); 
	       document.getElementById("saleDate").focus();
	       return false;
	   } 
	   
	   if(saleCrop == ""){
	       alert("กรุณาใส่ผลผลิตที่ขายข้าวสด");  
	       document.getElementById("saleCrop").focus();
	       return false;
	   }  
	   if(humid == ""){
		   alert("กรุณาใส่ % ความชื้นข้าวสด");  
	       document.getElementById("humid").focus();
	       return false;
	   }
	   if(salePrice == ""){
	       alert("กรุณาใส่ราคาขายผลผลิตข้าวสด ต่อ กก."); 
	       document.getElementById("salePrice").focus();
	       return false;
	   } 
	   if(saleAmount == ""){
	       alert("กรุณาจำนวนเงินที่ขายข้าวสดได้ "); 
	       document.getElementById("saleAmount").focus();
	       return false;
	   } 
	   if(saleDryCrop == ""){
	       alert("กรุณาใส่ผลผลิตที่ขายข้าวแห้ง");  
	       document.getElementById("saleDryCrop").focus();
	       return false;
	   } 
	   if(humidDry == ""){
		   alert("กรุณาใส่ % ความชื้นข้าวแห้ง");  
	       document.getElementById("humidDry").focus();
	       return false;
	   }
	   if(saleDryPrice == ""){
	       alert("กรุณาใส่ราคาขายข้าวแห้งผลผลิต ต่อ กก."); 
	       document.getElementById("saleDryPrice").focus();
	       return false;
	   } 
	   if(saleDryAmount == ""){
	       alert("กรุณาจำนวนเงินที่ขายข้าวแห้งได้ "); 
	       document.getElementById("saleDryAmount").focus();
	       return false;
	   } 
	   if(buyerId == 0){
		   alert("กรุณาเลือกชื่อผู้ซื้อ"); 
	       document.getElementById("buyerId").focus();
	       return false;
	   }
	   if(provinceNo == ""){
    	   alert("กรุณาเลือกจังหวัด"); 
	       document.getElementById("saleProvinceNo").focus();
	       return false;
      }
      if(districtNo == ""){
    	   alert("กรุณาเลือกอำเภอ"); 
	       document.getElementById("saleDistrictNo").focus();
	       return false;
      }
       if(subDistrictNo == ""){
    	   alert("กรุณาเลือกอำเภอ"); 
	       document.getElementById("saleSubDistrictNo").focus();
	       return false;
       }
	   // เช็ค remainSell
	   if(sumHarvArr != null && sumHarvArr.length > 0) {
	        var sTotalHarvest = 0;
	   		for(var k = 1; k < sumHarvArr.length; k++){ 
	   			if(sumHarvArr[k] != undefined && sumHarvArr[k] != "undefined" && sumHarvArr[k] != "")
	   				sTotalHarvest += Number(sumHarvArr[k]);
	   		}
	   		sumRemainSell = sTotalHarvest; // global variable
	   } else {
	      if(Number(sumRemainSell) == 0){
	    	  alert("กรุณากรอกข้อมูลรายละเอียดผลผลิตที่เก็บเกี่ยวได้จริง ก่อนบันทึกการขายผลผลิต!");
	    	  return false;
	      }
	      if(sumHarvArr.length <= 0){
	    	  alert("กรุณากรอกข้อมูลรายละเอียดผลผลิตที่เก็บเกี่ยวได้จริง ก่อนบันทึกการขายผลผลิต!");
	    	  return false;
	      } 
	   }
	   
	   // เช็คยอดรวมการขาย
	   var sumSell = 0;
	   var remainSpace = 0;
	   if(cropArray != null && cropArray.length > 0)
	   {
		    // Add Case, when saleIndex = 0 
			if(saleIndex == 0){
				sumSell += Number(saleCrop);
			}
		    for(var k = 1; k < cropArray.length; k++){ 
	   			if(saleIndex == k){ // Edit Case
	   				cropArray[k] = Number(saleCrop);
	   			}
	   			sumSell += Number(cropArray[k]);
	   		} 
	
	   		remainSpace = Number(sumRemainSell) - Number(sumSell);
	   		//alert("remainSpace = " + remainSpace+"!");
	   	    if(Number(remainSpace) >= 0){
	   			canSave++;
	   	    } else {
	   	 	    alert("ยอดรวมทั้งหมดของผลผลิตที่ขาย ต้องมีค่าไม่เกินผลผลิตที่เหลือไว้ขายทั้งหมด");
	         	$('#saleCrop').css('border-color', 'black'); // 22.07.2014
		     	setTimeout(function() {
   		   	 		document.getElementById("saleCrop").focus();
		     	}, 0);
		     	return false;
	   	    }
	  }else{
	  		 if(Number(sumRemainSell) >= Number(saleCrop)){
	  		 	canSave++;
	  		 }else{
	  		 	alert("ยอดรวมทั้งหมดของผลผลิตที่ขาย ต้องมีค่าไม่เกินผลผลิตที่เหลือไว้ขายทั้งหมด");
	         	$('#saleCrop').css('border-color', 'black'); // 22.07.2014
		     	setTimeout(function() {
   		   	 		document.getElementById("saleCrop").focus();
 				 }, 0);
	  		 	return false;
	  		 }
	  }
	  
  if(canSave > 0 && saleDate != "" && saleCrop != "" && salePrice != "" && saleAmount != "" && buyerId != 0 && saleDryCrop != "" && saleDryPrice != "" && saleDryAmount != "" ){
	        saveSaleDetailData(saleDate, saleCrop, salePrice, saleAmount, buyerId, buyerName, buyerAddress, provinceNo, districtNo, subDistrictNo, saleDryCrop, saleDryPrice, saleDryAmount, humidDry, humid);
	      	//	saleIndex = 0;
	    	document.getElementById("dynamicSaleDetail").innerHTML = generateSaleTable(itemSale);
	    	isAdd++;
	    	closeWindow('SaleDetailPopup',0);
	}
}

function saveSaleDetailData(saleDate, saleCrop, salePrice ,saleAmount, buyerId ,buyerName, buyerAddress ,provinceNo ,districtNo ,subDistrictNo, saleDryCrop, saleDryPrice, saleDryAmount, humidDry, humid)
{
	if(subSaleArray[saleIndex] != undefined && subSaleArray[saleIndex] != null && subSaleArray[subSaleArray] != "")
	{
			  	 itemSale = "";
		         for(var i=1; i< subSaleArray.length; i++){ 
				  	 if(i == saleIndex){
				  	    var prevSaleIndex = subSaleArray[i].split(",")[0];
		                var prevSaleDate = subSaleArray[i].split(",")[1];
		      			var prevKey = prevSaleIndex + "" + prevSaleDate;
		      			var currentKey = i + "" + saleDate;
		                if(arraySelling.hasOwnProperty(prevKey)){
		                   if(prevKey != currentKey) { 
		                   		if(!arraySelling.hasOwnProperty(currentKey)) {
		                   		   subSaleArray[i] =  i + "," + saleDate + "," + saleCrop + "," + salePrice +"," + saleAmount + "," + buyerId + "," + buyerName + "," + buyerAddress + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + saleDryCrop + "," + saleDryPrice +"," + saleDryAmount + ","+ humidDry + "," + humid;
		                   		   cropArray[i] = saleCrop;
		                   		   delete arraySelling[prevKey];
		                   		} else {
		                	       alert("มีข้อมูลซ้ำกัน");
		                	       return false;
		                	    }
		               	   } else {
		               	         subSaleArray[i] =  i + "," + saleDate + "," + saleCrop + "," + salePrice +"," + saleAmount + "," + buyerId + "," + buyerName + "," + buyerAddress + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + saleDryCrop + "," + saleDryPrice +"," + saleDryAmount + "," + humidDry + "," + humid;
		                   		 cropArray[i] = saleCrop;
		                   }
		                } else {
				  			    subSaleArray[i] =  i + "," + saleDate + "," + saleCrop + "," + salePrice +"," + saleAmount + "," + buyerId + "," + buyerName + "," + buyerAddress + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + saleDryCrop + "," + saleDryPrice +"," + saleDryAmount + "," + humidDry + "," + humid;		
				  				cropArray[i] = saleCrop;
				  		}
				  	}
				  	  itemSale += "|" + subSaleArray[i];
				}
				itemSale = itemSale.substr(1);
				saleDetailArray[breedIndex] = itemSale;
				saleIndex = 0;
				
				
	   } else {
		    intSellingCnt++;
		    cropArray[intSellingCnt] = saleCrop;
		    var sellKey = intSellingCnt + "" + saleDate;
		    arraySelling[sellKey] = intSellingCnt;	
		    if(intSellingCnt == 1){
			   itemSale += intSellingCnt  + "," + saleDate + "," + saleCrop + "," + salePrice +"," + saleAmount + "," + buyerId  + "," + buyerName +  "," + buyerAddress + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + saleDryCrop + "," + saleDryPrice +"," + saleDryAmount+ "," + humidDry + "," + humid;	
		    } else {
			   itemSale += "|" + intSellingCnt  + "," + saleDate + "," + saleCrop + "," + salePrice +"," + saleAmount + "," + buyerId + "," + buyerName +  "," + buyerAddress + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + saleDryCrop + "," + saleDryPrice +"," + saleDryAmount+ "," + humidDry + "," + humid;	
			}
		   saleDetailArray[breedIndex] = itemSale;
		   
	  }
	
	
}
function addObjectToDropdownList(divId, typeId, typeName, docNo, docRai, docNgan, docWah, rowIndex){ 
		var landRightObj = document.getElementById(divId);
		var landRightOption = document.createElement("option");
		if(docNo.indexOf("^") > -1){
			docNo = docNo.replace(/\^/g,',');
		}
		landRightOption.value = typeId + "+" + docNo; 
		landRightOption.text = typeName+ " ["+docNo+"]";
		var key = typeId + "+" + docNo; 
		landRightOption.title = "เลขที่ "+docNo;
		dropDownArray[key] = typeId + "+" + typeName + "+" + docNo + "+" +  docRai + "+" + docNgan + "+" + docWah + "+" + rowIndex;
	    landRightObj.add(landRightOption);
}

function getValueFromLandright(obj)
{ 
	detailIndex = -1;
	if(obj.value != "" && obj.value != "undefined") {
		var key = obj.value;
		if(dropDownArray[key]!= "" && dropDownArray[key]!= undefined){
			if(dropDownArray[key].indexOf("#") > -1) {
				   var data = dropDownArray[key].split("#")[0];
	    	 	   if(data != "" && data != undefined && data != "undefined"){	
	    	 		    document.getElementById("landDocNo").readOnly = true;
						document.getElementById("landDocRai").readOnly = true;
						document.getElementById("landDocNgan").readOnly = true;
						document.getElementById("landDocWah").readOnly = true;
	    	 		    var newDataArr = new Array();
	                	newDataArr = data.split(",");
	 	  		    }
			} else {
				var splitArray2 = new Array();
				splitArray2 = dropDownArray[key].split("+");
				if(splitArray2.length > 3){
					if(splitArray2[2].indexOf("^") > -1)
						document.getElementById("landDocNo").value = splitArray2[2].replace(/\^/g,',');
					else
						document.getElementById("landDocNo").value = splitArray2[2] + "";
//					if(splitArray2[2]!=""&&splitArray2[2]!=null){
//						document.getElementById("landRightTypeId").title = splitArray2[2] + "";
//						}
					document.getElementById("landDocRai").value = splitArray2[3]+"";
					document.getElementById("landDocNgan").value = splitArray2[4]+"";
					document.getElementById("landDocWah").value = splitArray2[5]+"";
					document.getElementById("landDocNo").readOnly = true;
					document.getElementById("landDocRai").readOnly = true;
					document.getElementById("landDocNgan").readOnly = true;
					document.getElementById("landDocWah").readOnly = true;
					
					// reset data
					document.getElementById("breedGroupId").value = "";
					document.getElementById("qtyPerRai").value = "";
					document.getElementById("plantRai").value = "";
        			document.getElementById("plantNgan").value = "";
        			document.getElementById("plantWah").value = ""
		    		document.getElementById("plantDate").value = "";
		    		document.getElementById("forecastDate").value = "";
					document.getElementById("forecastRecordDoc").value = "";
					document.getElementById("publicMarketDate").value = "";
					document.getElementById("recordForecastDate").value = "";
					document.getElementById("recordForecastBy").value = "";
					document.getElementById("publicCrop").value = "";
					
					document.getElementById("divCoorperative").display = "none";
					document.getElementById("corpGroupId").value = "0";
					document.getElementById("noOfBreed").value = "0";
					
					//breedIndex = splitArray2[6]; // index of plantDetail = index of landright
					loadNoticeTab(0);
          		  	loadPlantManure(0);
          		    loadPlantChemi(0);
          		    loadAssetDetail();
   		       	  	loadHarvestDetail(0);
   		       	  	loadSellingDetail(0);
					
       				unCheckAllBoxes("radio", "gap");
       				unCheckAllBoxes("checkbox", "seedResource");
       				unCheckAllBoxes("checkbox", "picking");
       				unCheckAllBoxes("radio", "objective");
       				unCheckAllBoxes("radio", "agricultureType");
       				unCheckAllBoxes("checkbox", "planting");
       				unCheckAllBoxes("checkbox", "plot");
       				unCheckAllBoxes("checkbox", "manureUse");
					
				}else{
					   var data = dropDownArray[key];
		    	 	   if(data != "" && data != undefined && data != "undefined")
		    	 	   {
		    	 		    var newDataArr = new Array();
		                	newDataArr = data.split(",");
		                	document.getElementById("landDocNo").readOnly = true;
							document.getElementById("landDocRai").readOnly = true;
							document.getElementById("landDocNgan").readOnly = true;
							document.getElementById("landDocWah").readOnly = true;
					   }
				}
			}
		}
	} else {
		document.getElementById("landDocNo").value = "";
		document.getElementById("landDocRai").value = "";
		document.getElementById("landDocNgan").value = "";
		document.getElementById("landDocWah").value = "";
	}
}
function addObjectToDropdownList2(divId, key)
{   
	var landRightObj = document.getElementById(divId);
	var landRightOption = document.createElement("option");
	for (i = 0, l = landRightObj.length; i < l; i++){ 
		  if(landRightObj.value != key){ 
			  landRightOption.value = key; 
				if(dropDownArray[key]!= "" && dropDownArray[key]!= undefined){
					var splitArray = new Array();
					if(dropDownArray[key].indexOf(',') > -1){
						splitArray = dropDownArray[key].split(",");
						landRightOption.text = splitArray[2].replace(/'/g, '');
					}
					landRightObj.add(landRightOption); // add new Key
				}
		  }
	  }
}
function removeOption(divId, oldChild) {
    var select = document.getElementById(divId);
    for (var i=0; i < select.length;  i++) {
    	if (select.options[i].value == oldChild) {
    		select.remove(i);
    	}
    }
}
function saveHarvestAndSell(){
	isSave++;
	sellTempArray[breedIndex] = "";
	harvTempArray[breedIndex] = "";
	closeWindow('SaleListForm',0);
}
function exit(){
	 $("ul#detail_containTab > li:visible").hide();
	 $("ul#detail_containTab > li").eq(0).show();  
	 
	 // added by Jane 08.01.2015
	 $("ul#navi_containTab > li").css("background-color","#DDDDDD");
     $("ul#navi_containTab > li").eq(0).css("background-color","#C4C4C4");
}
//---- ข้อมูลพื้นที่เพาะปลูก ---- //
function saveLandright()
{
	   var autoSave = 1;
	   if( document.getElementById("typeId").value == ""){
			 alert("กรุณาเลือกประเภทเอกสารสิทธิ์"); 
		     document.getElementById("typeId").focus(); 
		     autoSave = 0;
		     return false;
	   }
	   if( document.getElementById("docNo").value == ""){
			alert("กรุณากรอกเลขที่เอกสารสิทธิ์");  
			document.getElementById("docNo").focus();
			autoSave = 0;
			return false;
	   }
	   if( document.getElementById("docRai").value == "" && document.getElementById("docNgan").value == "" &&  document.getElementById("docWah").value == ""){
			alert("กรุณาใส่ขนาดที่ดิน");  
			document.getElementById("docRai").focus();
			autoSave = 0;
			return false;
	   }
	   if( document.getElementById("docProvinceNo").value == ""){
			alert("กรุณาเลือกจังหวัด");  
			document.getElementById("docProvinceNo").focus();
			autoSave = 0;
			return false;
	   }
	   if( document.getElementById("docDistrictNo").value == ""){
			alert("กรุณาเลือกอำเภอ");  
			document.getElementById("docDistrictNo").focus();
			autoSave = 0;
			return false;
	   }
	   if( document.getElementById("docSubDistrictNo").value == ""){
			alert("กรุณาเลือกตำบล");  
			document.getElementById("docSubDistrictNo").focus();
			autoSave = 0;
			return false;
	   }
	   if(document.getElementById("rentRai").checked == true){
	   		if(document.getElementById("rentPrice").value == "" ){
	   			alert("กรุณากรอกอัตราค่าเช่า");  
				document.getElementById("rentPrice").focus();
				autoSave = 0;
				return false;
	   		}
	   }
	   if(autoSave > 0){
	   		if(saveLandrightData()!= false){	
	   			document.getElementById("dynamicDocDetail").innerHTML = generateDetail(itemDetail);
	   			closeWindow('DocDetailPopup',0);
	   		}
	   }
}
function saveLandrightData()
{	
		var	docNo= document.getElementById("docNo").value;
		docNo = docNo.replace(/\,/g,'^');
		
		var landMoo = document.getElementById("landMoo").value;
		var provinceNo = document.getElementById("docProvinceNo").value;
		var districtNo = document.getElementById("docDistrictNo").value;
		var subDistrictNo= document.getElementById("docSubDistrictNo").value;
		var typeId = document.getElementById("typeId").value;
	    var tObj =  document.getElementById("typeId");
	    var typeName = tObj.options[tObj.selectedIndex].text;
	    
	    // ---- ข้อมูลเอกสารสิทธิ์ ----
		var docRai= document.getElementById("docRai").value;
		var docNgan= document.getElementById("docNgan").value;
		var docWah= document.getElementById("docWah").value;
		if(docRai == "")
			docRai = 0;
		if(docNgan == "")
			docNgan = 0;
		if(docWah == "")
			docWah = 0;
			
		var isOwn = 0;
		var ownRai= document.getElementById("ownRai");
		var rentRai =  document.getElementById("rentRai");
		if(ownRai.checked  == true)
           isOwn = 1;
		if(rentRai.checked == true)
      	   isOwn = 0;
		if(ownRai.checked == false && rentRai.checked == false)
      	   isOwn = 2;
     
	    var rentPrice = document.getElementById("rentPrice").value;
	    if(rentPrice == "" || rentPrice == null){
			rentPrice = 0;
	    }else{
		    var currency = rentPrice;
		    var number = Number(currency.replace(/[^0-9\.]+/g,""));
		    rentPrice = number;
		}
	     
	    var ownerShip =  isOwn + "," + rentPrice;
		var waterSource = "", irrigation = "", irrigationId = 0, soil = "", soilType = "";
		
		var rad = document.querySelectorAll('input[type="radio"]');
		for (i = 0, l = rad.length; i < l; i++) {
			if(rad[i].name == "waterSource"){
		   	   if(rad[i].checked){
		   		   var radId = rad[i].id;
		   		   waterSource = radId.substr(radId.indexOf("_") + 1);
        	   }
			} if(rad[i].name == "irrigation"){
			   	   if(rad[i].checked){
			   		   var radId = rad[i].id;
			   		   irrigationId = radId.substr(radId.indexOf("_") + 1);
	        	   }
			}
		}
		
		var cbx = document.querySelectorAll('input[type="checkbox"]');
		for (i = 0, l = cbx.length; i < l; i++) {
			 if(cbx[i].name == "soil"){
			   if(cbx[i].checked){
			         var cbxId = cbx[i].id;
			         soil += "^" + cbxId.substr(cbxId.indexOf("_") + 1);
        	   }
			 } if(cbx[i].name == "soilType"){
            if(cbx[i].checked){ 
           	 	var cbxId = cbx[i].id;
			        soilType += "^" + cbxId.substr(cbxId.indexOf("_") + 1);
        	   }
         }
		}
		
		soil = soil.substr(1);
		soilType = soilType.substr(1);
		
		var soilTypeName = document.getElementById("soilTypeName").value; // textbox
		soilTypeName = soilTypeName.replace(/\,/g,'^');
		
		var sumtotal = waterSource + "," + irrigationId + "," + soil + "," + soilType + "," + soilTypeName;
		var key = docNo + "" + typeId;
	  	if(typeId == null || typeId == 0)
	  	{
			alert("กรุณาเลือกเอกสารสิทธิ์");  
			return false;
	 	} else {
	 		  if(docArray[docDetailIndex] != null && docArray[docDetailIndex] != undefined) {	
	   	       		    itemDetail = "";
		        		for(var i=1; i< docArray.length; i++)
		        		{ 
				 			if(i == docDetailIndex)
				 			{
				 			    var prevKey = docArray[i].split(",")[3].replace(/'/g, '') + docArray[i].split(",")[1];
				  				if(!arrayDetail.hasOwnProperty(key))
				  				{
				  					 delete arrayDetail[prevKey];
				  					 var prevDDKey = docArray[i].split(",")[1] + "+" + docArray[i].split(",")[3].replace(/'/g, '');
				  					 if(dropDownArray.hasOwnProperty(prevDDKey)){
	  	 	  	 				    	  delete dropDownArray[prevDDKey];
	  	 	  	 				    	  removeOption("landRightTypeId", prevDDKey);
	  	 	  	 				    	  addObjectToDropdownList("landRightTypeId", typeId, typeName, docNo, docRai, docNgan, docWah, i);
	  	 	  	 				      }	
				  	 				  arrayDetail[key] = key;
				  	 				  docArray[i] =  i  + "," + typeId + "," + typeName + "," + docNo + "," + docRai + "," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + ownerShip + "," + sumtotal;
					  	 				
				  	 			} else {
				  	 				if(prevKey == key){
				  	 					var prevDDKey = docArray[i].split(",")[1] + "+" + docArray[i].split(",")[3].replace(/'/g, '');
							  		    if(dropDownArray.hasOwnProperty(prevDDKey)){
				  	 	  	 				 delete dropDownArray[prevDDKey];
				  	 	  	 				 removeOption("landRightTypeId", prevDDKey);
				  	 	  	 				 addObjectToDropdownList("landRightTypeId", typeId, typeName, docNo, docRai, docNgan, docWah, i);
							  		    }
				  	 					
				  	 					docArray[i] =  i  + "," + typeId + "," + typeName + "," + docNo + "," + docRai + "," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + ","  + ownerShip + "," + sumtotal;
				  	 				}else{
				  	 					alert("เลือกเอกสารสิทธิ์ซ้ำ : "+ typeName + " เลขที่ " + docNo.replace(/\^/g,',')); 
			      						return false; 
			      					}
				  	 			}  
				  	 		}
				  	 		itemDetail += "|" + docArray[i];
						}
						itemDetail = itemDetail.substr(1);
						breedIndex = docDetailIndex;
	   	       		 	docDetailIndex = 0;	
	   	       } else {
		     	   	
		     	   	if(arrayDetail.hasOwnProperty(key)){   
	 	       			alert("เลือกเอกสารสิทธิ์ซ้ำ : "+ typeName + " เลขที่ " + docNo.replace(/\^/g,',')); 
			      		return false; 	
	   	       		} else {
		    	 		intDetailCnt = intDetailCnt + 1;
		     	   	    arrayDetail[key] = key;
		     	   	    docArray[intDetailCnt] = intDetailCnt + "," + typeId + "," + typeName + "," + docNo + "," + docRai + "," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + ownerShip + "," + sumtotal;
		     	   		if(intDetailCnt == 1){
					 		itemDetail += intDetailCnt + "," + typeId + "," + typeName + "," + docNo + "," + docRai + "," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + ownerShip + "," + sumtotal;
		     	 		} else { 
					 		itemDetail += "|" + intDetailCnt + "," + typeId + "," + typeName + "," + docNo + ","+docRai +"," + docNgan + "," + docWah + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo + "," + ownerShip + "," + sumtotal; 
			     		}
			       		addObjectToDropdownList("landRightTypeId", typeId, typeName, docNo, docRai, docNgan, docWah, intDetailCnt);// --- add value to dropdownlist in plantDetail tab
			       }
			  }
	  	}
	  	// fromLandRowIndex = 0;
}

//====== ข้อมูลการเพาะปลูกพืช ======= //
function savePlantBreed(tabPage)
{
	if(tabPage == "tab1"){
		var landRightTypeId = document.getElementById("landRightTypeId").value;
		var landDocNo = document.getElementById("landDocNo").value;
		var breedGroupId = document.getElementById("breedGroupId").value;
	 	var objective = document.getElementsByName('objective');
	 	var count1=0;
	 	for(var i = 0; i < objective.length; i++){
	 		if(objective[i].checked){
	 			count1++;
	 		}
	 	}
	 	var agricultureType = document.getElementsByName('agricultureType');
	 	var count2=0;
	 	for(var i = 0; i < agricultureType.length; i++){
	 		if(agricultureType[i].checked){
	 			count2++;
	 		}
	 	}
		if(landRightTypeId == ""){
			alert("กรุณาเลือกกลุ่มเอกสารสิทธิ์"); 
			document.getElementById("landRightTypeId").focus();
			return false;
		} 
		if(landDocNo == ""){
			alert("กรุณาใส่เลขที่เอกสารสิทธิ์"); 
			document.getElementById("landDocNo").focus();
			return false;
		} 
		if(document.getElementById("landDocRai").value == "" && document.getElementById("landDocNgan").value == "" && document.getElementById("landDocWah").value == ""){
			alert("กรุณาใส่ขนาดที่ดินของเอกสารสิทธิ์"); 
			document.getElementById("landDocRai").focus();
			return false;
		} 
		if(breedGroupId == ""){
			alert("กรุณาเลือกกลุ่มชื่อพันธุ์พืชที่ปลูก "); 
			document.getElementById("breedGroupId").focus();
			return false;
		} 
		if(count1 == 0){
			alert("กรุณาใส่วัตถุประสงค์ในการปลูก");
			document.getElementById("objective_1").focus();
			return false;
		}
		if(count2 == 0){
			alert("กรุณาใส่ลักษณะการเกษตร");
			document.getElementById("agricultureType_1").focus();
			return false;
		}
		if(document.getElementById("plantDate").value == ""){
			alert("กรุณาใส่วันที่เพาะปลูก"); 
			document.getElementById("plantDate").focus();
			return false;
		} 
		if(document.getElementById("forecastRecordDoc").value == ""){
			alert("กรุณาใส่ประมาณการผลผลิตที่คาดว่าจะได้รับ"); 
			document.getElementById("forecastRecordDoc").focus();
			return false;
		} 
		if(document.getElementById("forecastDate").value == ""){
			alert("กรุณาใส่เดือนที่คาดว่าจะเก็บเกี่ยว"); 
			document.getElementById("forecastDate").focus();
			return false;
		}
		 var breedGroupDD = document.getElementById("breedGroupId");
	     var globBreedGroupName = breedGroupDD.value;     
	     globBreedGroupName  = breedGroupDD.options[breedGroupDD.selectedIndex].text;
	     document.getElementById("lblManureBreedGroupName").innerHTML = globBreedGroupName.replace(/'/g , "");
	     var lDocRai = document.getElementById("landDocRai").value;
	     var lDocNgan = document.getElementById("landDocNgan").value;
	     var lDocWah = document.getElementById("landDocWah").value
	     if(lDocRai == ""){
	          lDocRai = 0;
	     } if(lDocNgan == ""){
	          lDocNgan = 0;
	     } if(lDocWah == ""){
	          lDocWah = 0;
	     }
	     var pRai = document.getElementById("plantRai").value;
	     var pNgan = document.getElementById("plantNgan").value;
	     var pWah = document.getElementById("plantWah").value;
	     
	     if(pRai == ""){
	         pRai = 0;
	     }if(pNgan == ""){
	         pNgan = 0;
	     }if(pWah == ""){
	         pWah = 0;
	     }
	     
	     // --- start : added on 08/02/2017 ---
	     if(pRai == 0 && pNgan == 0 && pWah == 0){
	    	    alert("กรุณาใส่ขนาดพื้นที่เพาะปลูก"); 
				document.getElementById("plantRai").focus();
				return false;
	     }
	     // --- finish : added on 08/02/2017 ---
	     
     	var docSum = convertToWah(parseInt(lDocRai), parseInt(lDocNgan), parseInt(lDocWah)); // land พื้นที่ในเอกสารสิทธิ์
	    var plantSum = convertToWah(parseInt(pRai), parseInt(pNgan), parseInt(pWah)); // plant พื้นที่ที่ใช้เพาะปลูกจริงๆ
	
	   	if(plantSum > docSum) {
	    	alert("จำนวนขนาดพื้นที่เพาะปลูกไม่สามารถมีค่ามากกว่าขนาดที่ดินทั้งหมดในเอกสารสิทธิ์ได้"); 
			return false;
	   	}
	   	
	    // --- start : added on 08/02/2017 ---
	   	if(plantSum <= 0){
	   	    alert("กรุณาใส่ขนาดพื้นที่เพาะปลูก"); 
			document.getElementById("plantRai").focus();
			return false;
	   	}
	 // --- finish : added on 08/02/2017 ---
	   	
     	var pDate = document.getElementById("plantDate").value;
	  	var fDate = document.getElementById("forecastDate").value;
	    if( pDate != "" && fDate != "" ) { 
	   		if(compareDate(pDate, fDate) == false){
	      		 alert('เดือนที่คาดว่าจะเก็บเกี่ยว ไม่สามารถเลือกย้อนหลังเดือนที่เพาะปลูกได้');
	      		 document.getElementById("forecastDate").focus();
	      		 return false;
	  		 } 
	    }
	    
	    var breedTypeDD = document.getElementById("breedTypeId");
	    var bTypeName = breedTypeDD.value;     
     	globBreedTypeName  = breedTypeDD.options[breedTypeDD.selectedIndex].text;
	    globPlantArea = lDocRai + "-" + lDocNgan + "-" + lDocWah;  
	    // for tab2 
	    document.getElementById("lblManureBreedTypeName").innerHTML  =  globBreedTypeName + "";// พืชที่ปลูก
		document.getElementById("lblManureBreedGroupName").innerHTML  =  globBreedGroupName.replace(/'/g , ""); // พันธุ์ที่ปลูก
	    // for tab3
		document.getElementById("lblChemiBreedTypeName").innerHTML  =  globBreedTypeName + ""; // พืชที่ปลูก
		document.getElementById("lblChemiBreedGroupName").innerHTML =  globBreedGroupName.replace(/'/g , "");
		// for tab5
		document.getElementById("noticeTypeId").value = landRightTypeId;
		document.getElementById("noticeDocNo").value = landDocNo;
		document.getElementById("noticeBreedGroupId").value = breedGroupId;
		document.getElementById("noticeBreedTypeId").value = breedTypeDD.value;
		// for tab5
	    document.getElementById("lblTabBreed5_1").innerHTML  =  globFarmerName + "";// ชื่อเกษตรกร
		document.getElementById("lblTabBreed5_2").innerHTML  =  globBreedTypeName + ""; // พืชที่ปลูก
		document.getElementById("lblTabBreed5_3").innerHTML  =  globBreedGroupName + ""; // พันธุ์ที่ปลูก
		document.getElementById("lblTabBreed5_4").innerHTML  =  pRai + " ไร่ " + pNgan + " งาน " + pWah + " วา";// ขนาดพื้นที่ปลูก    

		// --- check landRight area and plantDetail area : 09.01.2015
		if(landRightTypeId.indexOf("+") > -1) {
		    var splitArray = new Array();
			splitArray =  landRightTypeId.split("+");
			landRightTypeId = splitArray[0];
		}
		coKey = landRightTypeId + "" + landDocNo;

		
		if(!docAreaArray.hasOwnProperty(coKey)){             
			docAreaArray[coKey] = docSum;
		}
		
		if(!totalAreaArray.hasOwnProperty(coKey)){             
			totalAreaArray[coKey] = plantSum;
		} else {
			if(breedDetailIndex <= 0){
				totalAreaArray[coKey] += plantSum;
		    } else {
		    	// plantArea เก่า
		    	var rai = breedArrayTab1[breedDetailIndex].split(",")[10];
		    	var ngan = breedArrayTab1[breedDetailIndex].split(",")[11];
		    	var wah = breedArrayTab1[breedDetailIndex].split(",")[12];
		    	var prevSum = convertToWah(parseInt(rai), parseInt(ngan), parseInt(wah));
		    	totalAreaArray[coKey] = totalAreaArray[coKey] - prevSum;
		    	totalAreaArray[coKey] += plantSum;
			}
		}
		
		if(docAreaArray[coKey] != undefined && totalAreaArray[coKey] != undefined){ 
			 if(docAreaArray[coKey] < totalAreaArray[coKey]){
				alert("ไม่สามารถปลูกพืชเกินขนาดที่ดินของเอกสารสิทธิ์นี้ได้ กรุณากรอกใหม่อีกครั้ง!");
		        document.getElementById("plantRai").focus();
				totalAreaArray[coKey] = totalAreaArray[coKey] - plantSum;
				return false;
			 }
		}
		
		// --- 
		delete docAreaArray[coKey];
		delete totalAreaArray[coKey];
		// ---
	} else if(tabPage == "tab5") {
	    if(document.getElementById("publicMarketDate").value == ""){
			alert("กรุณาใส่เดือนที่ผลผลิตออกสู่ตลาด "); 
			document.getElementById("publicMarketDate").focus();
			return false;
		}if(document.getElementById("publicCrop").value == ""){
			alert("กรุณาใส่ค่าประมาณการผลผลิตที่คาดว่าจะออกสู่ตลาด "); 
			document.getElementById("publicCrop").focus();
			return false;
		}
	}
	
	if(saveDetailData() != false){
		 if(confirm("บันทึกข้อมูลพืชที่เพาะปลูกแล้ว ต้องการปิดหน้าจอหรือไม่?")){
			 breedIndex = 0;	
			 breedDetailIndex = 0;	
			 document.plantForm.isTab1Saved.value = "no";
			 document.getElementById("dynamicPlantBreed").innerHTML = generateTable(itemBreed1);
			 isSave++; // มีการ save ใน  PlantBreedPopUp
			 isDelete = 0;
			 closeWindow('PlantBreedPopup',0);
		 }else{
			if(breedDetailIndex == 0)
			   breedDetailIndex = intBreedCnt; // มีการ Add ตัวใหม่ล่าสุด
		 } 
		 detailIndex = 0;
	}
}

////---- rewrite function save PlantDetail ----///
function saveDetailData()
{	
	    var isEdit = 0;
	    var ddKey = "";
		var typeId = document.getElementById("landRightTypeId").value;
		var tObj = document.getElementById("landRightTypeId");
		var typeName = tObj.options[tObj.selectedIndex].text;
		var	docNo = document.getElementById("landDocNo").value.trim();
		docNo = docNo.replace(/\,/g,'^');
		var docRai = document.getElementById("landDocRai").value;
		var docNgan = document.getElementById("landDocNgan").value;
		var docWah = document.getElementById("landDocWah").value;
			
		if(docRai == "")
	       docRai = 0;
		if(docNgan == "")
		   docNgan = 0;
		if(docWah == "")
		   docWah = 0;
		var groupId = document.getElementById("breedGroupId").value;   
		     
		if(typeId.indexOf("+") > -1) {
		    var splitArray = new Array();
			splitArray =  typeId.split("+");
		    typeId = splitArray[0];
		}
		var row1 = typeId + ",'" + typeName + "','" + docNo + "'," + docRai + "," + docNgan + "," + docWah;
		
		var bObj =  document.getElementById("breedGroupId");
		var groupName = bObj.options[bObj.selectedIndex].text;
		var qty = document.getElementById("qtyPerRai").value.replace(/,/g , "");//qtyPerRai
		var plantRai = document.getElementById("plantRai").value;
		var plantNgan = document.getElementById("plantNgan").value;
		var plantWah = document.getElementById("plantWah").value;
			
		
		// --- Added by Yatphiroon.P on 29.04.2015
		// --- วางหลัง seedSource
		var corpGroupId = document.getElementById("corpGroupId").value; // สหกรณ์
		//alert("corpGroupId = " + corpGroupId);
		var corpGroupName = document.getElementById("corpGroupId").options[document.getElementById("corpGroupId").selectedIndex].text;
		//alert(corpGroupName+"!");
		if(corpGroupName == "")
			corpGroupName = "-";
		
		var noOfBreed = document.getElementById("noOfBreed").value // จำนวนเมล็ดพันธุ์
		if(noOfBreed == "")
			noOfBreed = "0";
		// --- 
		
	    if(plantRai == "" || plantRai == undefined)
	       plantRai = 0;
	    if(plantNgan == "" || plantNgan == undefined)
	       plantNgan = 0;
	    if(plantWah == "" || plantWah == undefined)
	       plantWah = 0;
	     
		var row2 = groupId + ",'" + groupName + "','" + qty + "'," + plantRai + "," + plantNgan + "," + plantWah;
			
		var seedResource = "";
		var seedPicking = ""; 
		var gap = "";
		var plantObjective = ""; 
		var agricultureType = "";
		var planting = "";
		var plotting = "";
		var manureUse = "";
		var chemiUse = "";
			
		var rad = document.querySelectorAll('input[type="radio"]');
		
		for (i = 0, l = rad.length; i < l; i++) {
			if(rad[i].name == "gap"){
				if(rad[i].checked){
	           	 	gap = rad[i].value;
	        	}
			} 
			if(rad[i].name == "objective"){
		        if(rad[i].checked){
		           	 plantObjective = rad[i].value;	 
		          
		        }
		    }
			if(rad[i].name == "subObjective" && plantObjective == "เพื่อทำเมล็ดพันธุ์"){
		        if(rad[i].checked){
		           	 subPlantObjective = rad[i].value;
		           	 plantObjective = plantObjective + "^" + subPlantObjective;
		        }
		    }
			if(rad[i].name == "agricultureType"){
		        if(rad[i].checked){
		        	agricultureType = rad[i].value;	 
		        }
		    }
		}
			
		var cbx = document.querySelectorAll('input[type="checkbox"]');
		for (i = 0, l = cbx.length; i < l; i++) 
		{
			 if(cbx[i].name == "seedResource"){
				  if(cbx[i].checked){
	           	 	seedResource += "^" + cbx[i].value;
	        	  }
	         } else if(cbx[i].name == "picking"){ // SeedSelect
			   	  if(cbx[i].checked){
	           	 	 var cbxId = cbx[i].id;
	           	  	 seedPicking += "^" + cbxId.substr(cbxId.indexOf("_") + 1);
	        	  }
	         } else if(cbx[i].name == "planting"){  // PlantMethod
	        	  if(cbx[i].checked){
		             var cbxId = cbx[i].id;
		           	 planting += "^" + cbxId.substr(cbxId.indexOf("_") + 1);	 
	        	  }
	         } else if(cbx[i].name == "plot"){     // PrepareArea
	              if(cbx[i].checked){
	            	 var cbxId = cbx[i].id;
	           	 	 plotting += "^" + cbxId.substr(cbxId.indexOf("_") + 1);	 
	        	  }
	         } else if(cbx[i].name == "manureUse"){  // PrepareManure
	              if(cbx[i].checked){
	                 var cbxId = cbx[i].id;
	           	 	 manureUse += "^" + cbxId.substr(cbxId.indexOf("_") + 1);		 
	        	   }
	         } 
		}
		
		seedResource = seedResource.substr(1);
		seedPicking = seedPicking.substr(1);
		planting = planting.substr(1);
		plotting = plotting.substr(1);
		var plottingName = "";
		var textBox = document.querySelectorAll('input[type="text"]');
		for (i = 0, l = textBox.length; i < l; i++){
			if(textBox[i].name == "plottingName"){
				plottingName = textBox[i].value;
			}
		}
		manureUse = manureUse.substr(1);
		var plantDate = document.getElementById("plantDate").value;
		var forecastDate = document.getElementById("forecastDate").value;
		var forecastRecordDoc = document.getElementById("forecastRecordDoc").value.replace(/,/g , "");
		var lastRow = "'" + seedResource + "','" + seedPicking + "','" + plantObjective + "','" + agricultureType 
						+ "','" + gap + "','" + planting + "','" + plotting + "','" + plottingName + "','" + manureUse
					    + "','" + plantDate + "','" + forecastDate + "','" + forecastRecordDoc+"'";
		
		var corpValue = corpGroupId + ",'" + corpGroupName + "','" + noOfBreed + "'"; // Added on 29.04.2015 by Yatphiroon.P
		var breedAllRow = row1 + "," + row2 + "," + lastRow + "," + corpValue; // Modified on 29.04.2015 by Yatphiroon.P
			
		// ==== Set Key ====
		if(typeId.indexOf("+") > -1) {
			var splitArray = new Array();
			splitArray =  typeId.split("+");
		    typeId = splitArray[0];
		}
        var currentKey = typeId + "" + docNo + "" + groupId;
        
        // ในกรณีที่ table ได้ถูก generate ไปแล้ว
        if(breedDetailArray[breedDetailIndex] != "undefined" && breedDetailArray[breedDetailIndex] != undefined && breedDetailArray[breedDetailIndex] != null) 
	    {
        	isEdit++;
        	// === Edit Mode ===
		    itemBreed1 = "";
		   // alert("Edit Mode");
			for(var i=1; i< breedDetailArray.length; i++){ 
			   if(i == breedDetailIndex) 
			   {
			        var prevTypeId = breedDetailArray[i].split(",")[1];
			        if(prevTypeId.indexOf("+") > -1) {
					    var splitArray = new Array();
						splitArray =  prevTypeId.split("+");
				        prevTypeId = splitArray[0];
				    }
			        var prevDocNo = breedDetailArray[i].split(",")[3].replace(/'/g, '');
			        var prevGroupId =  breedDetailArray[i].split(",")[7]; 
			        var prevKey = prevTypeId + "" + prevDocNo + "" + prevGroupId;
		
			        if(arrayBreed.hasOwnProperty(prevKey))
			        {
			           if(prevKey != currentKey) { //เปลี่ยนประเภททรัพย์สินแต่เป็น index เดิม
			               if(!arrayBreed.hasOwnProperty(currentKey)) {
			            	   breedDetailArray[i] = i + "," + breedAllRow;
			                   delete arrayBreed[prevKey];
			               } else {
			                   alert("เอกสารสิทธิ์ "+ typeName + "  เลขที่ " + docNo.replace(/\^/g,',') + " มีการเพาะปลูกพืชพันธุ์ '" + groupName + "' อยู่แล้ว"); 
			                   return false;
			               }
			           } else { 
			        	   breedDetailArray[i] = i + "," + breedAllRow;
			           }
			         } else {
			        	 breedDetailArray[i] = i + "," + breedAllRow;	
					 }
				}
				itemBreed1 += "|" + breedDetailArray[i];
				breedArrayTab1[i] = breedDetailArray[i];
			}
			itemBreed1 = itemBreed1.substr(1);
  	     } else {
  	    	 // ในกรณีที่ Add ครั้งแรกไปแล้ว กด cancel ทำให้ table ยังไม่ได้ถูก generate และมีข้อมูลอยู่แล้ว
  	    	 // alert("=== Addครั้งแรก แล้วกด cancel แล้วมา Add ครั้งต่อไป==");
  	    	 if(breedArrayTab1[breedDetailIndex] != "undefined" && breedArrayTab1[breedDetailIndex] != undefined && breedArrayTab1[breedDetailIndex] != null){
  	    		   isEdit++;
  	    		   var key = typeId + "" + docNo + "" + groupId;
  	    		   if(!arrayBreed.hasOwnProperty(key)){
  	    			   arrayBreed[key] = breedDetailIndex; //key	
  	    		   }
  	    		   
	    		   itemBreed1 = "";
	   			   for(var i=1; i< breedArrayTab1.length; i++){ 
	   			      if(i == breedDetailIndex) {
	   			    	  breedArrayTab1[i] = i + "," + breedAllRow;	
	   			      }
	   				
	   			      itemBreed1 += "|" + breedArrayTab1[i];
	   			   }
	   			   itemBreed1 = itemBreed1.substr(1);
	    		   
  	    	 } else {
	  	    	 // ในกรณีที่ Add แบบครั้งแรก ไม่มีการกด Cancel
  	    		// alert("=== Add ==");
  	    		 isEdit = 0;
	    	   	 var key = typeId + "" + docNo + "" + groupId;
	    		 if(arrayBreed.hasOwnProperty(key)){   
	    	 	     alert("เอกสารสิทธิ์ "+ typeName + "  เลขที่ " + docNo.replace(/\^/g,',') + " มีการเพาะปลูกพืชพันธุ์ '" + groupName + "' อยู่แล้ว"); 
	    	 	     warningMessage = "YES";
	    	 	     return false; 	
	      	   	 } else {
	    		     intBreedCnt = intBreedCnt + 1;
	    		     arrayBreed[key] = intBreedCnt; //key	
	    		     if(intBreedCnt == 1){
	    			    itemBreed1 += intBreedCnt + "," + breedAllRow;
	    		     } else {
	    			    itemBreed1 += "|" + intBreedCnt  + "," + breedAllRow;
	    			 }
	    		     breedArrayTab1[intBreedCnt] = intBreedCnt + "," + breedAllRow;
	      	   	 }	
  	    	 }
    	  }
        
      
		 // ==== TAB2 ====
         if(manureArray[breedDetailIndex] != undefined && manureArray[breedDetailIndex] != "undefined"){
        	 breedArrayTab2[breedDetailIndex] = manureArray[breedDetailIndex];
         } else{
        	 breedArrayTab2[intBreedCnt] = manureArray[intBreedCnt];
         }
         // ==== TAB3 ====
         if(chemArray[breedDetailIndex] != undefined && chemArray[breedDetailIndex] != "undefined"){
        	 breedArrayTab3[breedDetailIndex] = chemArray[breedDetailIndex];
         } else{
        	 breedArrayTab3[intBreedCnt] = chemArray[intBreedCnt];
         }
		 // ==== TAB4 ====
         globAssetDate = document.getElementById("dateAsset").value;
         globAssetAmount = document.getElementById("assetAmount").value;
         if(assetArray[breedDetailIndex] != undefined && assetArray[breedDetailIndex] != "undefined"){
        	 breedArrayTab4[breedDetailIndex] = assetArray[breedDetailIndex];
         } else{
        	 breedArrayTab4[intBreedCnt] = assetArray[intBreedCnt];
         }
		 // ==== TAB5 ====
		 var publicMarketDate = document.getElementById("publicMarketDate").value;
		 var publicCrop = document.getElementById("publicCrop").value.replace(/,/g , "");
		 var recordForecastDate = document.getElementById("recordForecastDate").value;
		 var recordForecastBy = document.getElementById("recordForecastBy").value;
		 recordForecastBy = recordForecastBy.replace(/ /g, '\u00a0');  
		 var noticeBeforeHarvest = "'" + publicMarketDate + "','" + publicCrop + "','" + recordForecastDate + "','" + recordForecastBy + "'";
		
		 if(breedArrayTab5[breedDetailIndex] != undefined && breedArrayTab5[breedDetailIndex] != "undefined"){
			 breedArrayTab5[breedDetailIndex]  = breedDetailIndex + "," + noticeBeforeHarvest; 
         } else{
        	 breedArrayTab5[intBreedCnt]  = intBreedCnt + "," + noticeBeforeHarvest; 
         }
		 
		 
		 // ==== รวม ====
		 if(isEdit == 0){ 
			 breedArray[intBreedCnt] = breedArrayTab1[intBreedCnt] + "#" +  breedArrayTab2[intBreedCnt] + "#" + breedArrayTab3[intBreedCnt] + "#" + breedArrayTab4[intBreedCnt] + "#" + breedArrayTab5[intBreedCnt];
		 }else{
			 breedArray[breedDetailIndex] = breedArrayTab1[breedDetailIndex] + "#" +  breedArrayTab2[breedDetailIndex] + "#" + breedArrayTab3[breedDetailIndex] + "#" + breedArrayTab4[breedDetailIndex] + "#" + breedArrayTab5[breedDetailIndex];
		 }
		 
			if(docNo.indexOf("^") > -1){
				docNo = docNo.replace(/\^/g,',');
			}
			 if(typeId.indexOf("+") > -1) {
				 var splitKeyArray = new Array();
				 splitKeyArray =  typeId.split("+");
		         typeId = splitKeyArray[0];
				 ddKey = typeId + "+" + docNo + "+" + groupId;
			 } else {
			     ddKey = typeId + "+" + docNo + "+" + groupId;
			 }
			 
			 if(isEdit == 0){ 
				 dropDownArray[ddKey] = breedArray[intBreedCnt];
			 }else{
				 dropDownArray[ddKey] = breedArray[breedDetailIndex];
			 }
			 document.plantForm.isTab1Saved.value = "yes";		
}


//edit LandRight
function edit(popUpName, rowIndex, typeId, typeName, docNo, docRai, docNgan, docWah, landMoo, provinceNo, districtNo, subDistrictNo, isOwn, rentPrice, waterSource, irrigation, soil, soilType, soilTypeName, groupId) 
{
    if(popUpName == "PlantBreedPopup") 
    {
    	breedIndex = rowIndex; // rowIndex ของ landright
    	if(breedDetailIndex > 0)
    		breedDetailIndex = 0;
    	  
    	docNo = docNo.replace(/'/g, '');
    	if(docNo.indexOf("^") > -1){
    		docNo = docNo.replace(/\^/g,',');
    	}
    	document.getElementById("landDocNo").value = docNo;
     	document.getElementById("landDocRai").value = docRai;
   	 	document.getElementById("landDocNgan").value = docNgan;
    	document.getElementById("landDocWah").value = docWah;
    	// fromLandRowIndex = rowIndex;
    	
    	document.getElementById("landRightTypeId").value = typeId + "+" + docNo;
    	document.getElementById("landRightTypeId").disabled = true;
    	
    	// reset all data 
    	document.getElementById("breedGroupId").value = groupId;
		document.getElementById("qtyPerRai").value = "";
		document.getElementById("plantRai").value = "";
		document.getElementById("plantNgan").value = "";
		document.getElementById("plantWah").value = ""
    	document.getElementById("plantDate").value = "";
    	document.getElementById("forecastDate").value = "";
		document.getElementById("forecastRecordDoc").value = "";
		document.getElementById("publicMarketDate").value = "";
		document.getElementById("recordForecastDate").value = "";
		document.getElementById("recordForecastBy").value = "";
		document.getElementById("publicCrop").value = "";
		document.getElementById("corpGroupId").value = "0";
		document.getElementById("noOfBreed").value = "0";
		document.getElementById('divCoorperative').style.display = 'none';
		document.getElementById("subPrepareArea").innerHTML = "";
		  
		loadNoticeTab(0);
		loadPlantManure(0);
		loadPlantChemi(0);
		loadAssetDetail();
	    loadHarvestDetail(0);
	    loadSellingDetail(0);
	      
		unCheckAllBoxes("radio", "gap");
		unCheckAllBoxes("checkbox", "seedResource");
		unCheckAllBoxes("checkbox", "picking");
		unCheckAllBoxes("radio", "objective");
		unCheckAllBoxes("radio", "agricultureType");
		unCheckAllBoxes("checkbox", "planting");
		unCheckAllBoxes("checkbox", "plot");
		unCheckAllBoxes("checkbox", "manureUse");
		
    } if(popUpName == "DocDetailPopup") {
    	// DocDetailPopup
    	docDetailIndex = rowIndex; 
   		 
    	// === set ไว้เผื่อกด X แทนปุ่มยกเลิก  ===
    	unCheckAllBoxes("radio", "waterSource");
    	unCheckAllBoxes("radio", "irrigation");
       	unCheckAllBoxes("checkbox", "soil");
       	unCheckAllBoxes("checkbox", "soilType");
		document.getElementById("soilTypeName").value = "";
		document.getElementById("soilTypeName").readOnly = true;
		// ======
    	  
   		hasValue = 0;
    	if(breedArray != undefined && breedArray != "" && breedArray != null) {
    		for(var i = 1; i < breedArray.length; i++) {
    			if(breedArray[i] != undefined && breedArray[i] != "") {
	    	    	if(breedArray[i].indexOf("#") > -1) {
	    	    		 var newItemData= new Array();
  						 newItemData = breedArray[i].split("#"); // เปลี่ยนจาก index เป็น y+1
  					     var itemData = newItemData[0].split(",");
  					     var plantTypeId = itemData[1];
  					     var plantDocNo = itemData[3].replace(/'/g , "");
  					     if(plantTypeId == typeId && plantDocNo == docNo){
  					    	hasValue++;
  					    	break;
  					     } 
	    	    	}
    			}
    		}
    	}
    	if(hasValue > 0){
			document.getElementById("typeId").disabled = true;
			document.getElementById("docNo").readOnly = true;
			document.getElementById("docRai").readOnly = true;
	    	document.getElementById("docNgan").readOnly = true;
	    	document.getElementById("docWah").readOnly = true;
    	}
   		
       	document.getElementById("typeId").value = typeId;
    	document.getElementById("docNo").value = docNo;
    	if(docRai == 0)
    		docRai = "";
    	if(docNgan == 0)
    		docNgan = "";
    	if(docWah == 0)
    		docWah = "";
    	document.getElementById("docRai").value = docRai;
    	document.getElementById("docNgan").value = docNgan;
    	document.getElementById("docWah").value = docWah;
 		document.getElementById("landMoo").value = landMoo;
    	document.getElementById("docProvinceNo").value = provinceNo;
    	objProvNoEdit = provinceNo;  // added by Jane 08.08.2014
    	getDistrictInfo('docProvinceNo', 'docDistrictNo', 'docSubDistrictNo', ''); 
    	document.getElementById("docDistrictNo").value = districtNo;
    	getSubDistrictInfo('docProvinceNo', 'docDistrictNo', 'docSubDistrictNo', ''); 
    	document.getElementById("docSubDistrictNo").value= subDistrictNo;
		if(isOwn == 1){
			document.getElementById("ownRai").checked = true;
			document.getElementById("rentRai").checked = false;
			document.getElementById("rentPrice").disabled = true;
			document.getElementById("rentPrice").value = "";
		} if(isOwn == 0){
			document.getElementById("ownRai").checked = false;
			document.getElementById("rentRai").checked = true;
			document.getElementById("rentPrice").disabled = false;
			document.getElementById("rentPrice").readOnly = false;
			document.getElementById("rentPrice").value = formatNumber(rentPrice);
		} if(isOwn == 2){
	     	document.getElementById("ownRai").checked = false;
	     	document.getElementById("rentRai").checked = false;
	     	document.getElementById("rentPrice").disabled = true;
	     	document.getElementById("rentPrice").value = "";
    	}
    	var rad = document.querySelectorAll('input[type="radio"]');
    	for (i = 0, l = rad.length; i < l; i++){
        	//alert(rad[i].name + ' : ' + rad[i].id);
    		if(rad[i].name == "waterSource"){
	    		var getId = rad[i].id;
	    		var inputId = "waterSource_" + waterSource;
	    		if(getId == inputId){
	    			document.getElementById(rad[i].id).checked = true;
	    		}
	    	}
		    if(rad[i].name == "irrigation"){
		    	var getId = rad[i].id;
		    	var inputId = "irrigation_" + irrigation;
		    	if(getId == inputId){
		    		document.getElementById(rad[i].id).checked = true;
		    		break;
		    	}
		    }
    	}
    	
    	var cbx = document.querySelectorAll('input[type="checkbox"]');
    	for (i = 0, l = cbx.length; i < l; i++){
    		if(cbx[i].name == "soil"){
    			if(soil.indexOf('^') > -1)
    				soil = soil.replace(/\^/g,','); // replace ^ with ,
			  
    			var s = soil.split(',');
    			for (j = 0, m = s.length; j < m; j++)
    			{
    				if("soil_" + s[j] == cbx[i].id){
    					document.getElementById(cbx[i].id).checked = true;
    				}
    			}
    		}
    		if(cbx[i].name == "soilType"){
			 	if(soilType.indexOf('^') > -1) 
				   soilType = soilType.replace(/\^/g,','); // replace ^ with ,
			 	
			 	var sType = soilType.split(',');
			 	for (j = 0, m = sType.length; j < m; j++){ 
				   if("soilType_" + sType[j] == cbx[i].id){
    	  				document.getElementById(cbx[i].id).checked = true;
    	  				if(document.getElementById(cbx[i].id).value == "อื่นๆ"){
    	  					document.getElementById("soilTypeName").readOnly = false;
    	  					document.getElementById("soilTypeName").value = soilTypeName;
    	  				}
				   }
				}
    		}
		}
    } if(popUpName == "LandCheckHistory" ){
    	var tmpLandCheck = itemLandCheck;
    	//alert("tmpLandCheck = " +tmpLandCheck+"!");
    	 if(tmpLandCheck !=null && tmpLandCheck.length>0)
         {
  	 		var newItemData= new Array();
  	 		newItemData = tmpLandCheck.split("|");
    	
  	 		tmpLandCheck = "";
		 	for (var i = 0; i < newItemData.length ; i++)
		 	{
		 		//alert(newItemData[i] + "!");
//		 		 var landCheckRowIndex = newItemData[i].split(",")[0];
//			     var landCheckId = newItemData[i].split(",")[1];
//			     var checkDate = newItemData[i].split(",")[2];
//			     var lastUpdate = newItemData[i].split(",")[3];
//			     var checkPeriod = newItemData[i].split(",")[4];
			     var landCheckTypeId = newItemData[i].split(",")[5];				     
//			     var breedtypeName = newItemData[i].split(",")[6];
			     var landCheckDocNo = newItemData[i].split(",")[7];
//			     var checkBy = newItemData[i].split(",")[8];
//			     var Result = newItemData[i].split(",")[9];
			   				   				  
			     if(landCheckTypeId == typeId && landCheckDocNo == docNo ){
			    	tmpLandCheck += "|" + newItemData[i];
			    	//tmpLandCheck += "|" + landCheckRowIndex + "," + landCheckId + "," + checkDate + "," + lastUpdate+ "," + checkPeriod+ "," + landCheckTypeId+ "," + breedtypeName+ "," +landCheckDocNo + "," + checkBy + "," + Result; 
			    	}else{
			    	tmpLandCheck += "";
			    	// document.getElementById("dynamicLandCheckHistory").innerHTML = "";
			     }//alert(tmpLandCheck);
			   }
		 	 document.getElementById("dynamicLandCheckHistory").innerHTML = generateCheckTable(tmpLandCheck);
         }
    	
    }
    showWindow(popUpName, rowIndex);
}

// edit PlantDetail
function editBreed(rowIndex, typeId, typeName, docNo, docRai, docNgan, docWah, groupId, groupName, qty
				, plantRai, plantNgan, plantWah, seedResource, seedPicking, plantObjective, agricultureType
				, gap, planting, plotting, plottingName, manureUse, plantDate, forecastDate, forecastRecordDoc, corpGroupId, corpGroupName, noOfSeed, popUpName){						
	  //breedIndex = rowIndex;
	  
	  // === set ไว้เผื่อตอนไม่ได้กดปุ่ม ยกเลิกเข้ามา ===
	  document.getElementById("subPrepareArea").innerHTML = "";
	  unCheckAllBoxes("radio", "gap");
   	  unCheckAllBoxes("checkbox", "seedResource");
   	  unCheckAllBoxes("checkbox", "picking");
   	  unCheckAllBoxes("radio", "objective");
   	  unCheckAllBoxes("radio", "subObjective");
   	  unCheckAllBoxes("radio", "agricultureType");
   	  unCheckAllBoxes("checkbox", "planting");
   	  unCheckAllBoxes("checkbox", "plot");
   	  unCheckAllBoxes("checkbox", "manureUse");
      document.getElementById("corpGroupId").value = "0";
	  document.getElementById("noOfBreed").value = "0";
	  document.getElementById('divCoorperative').style.display = 'none';
	 // =====
   	  
	  
      breedDetailIndex = rowIndex;
      docNo = docNo.replace(/'/g, '');
      if(docNo.indexOf("^") > -1){
    	  docNo = docNo.replace(/\^/g,',');
      }  
      
      document.getElementById("landRightTypeId").value = typeId + "+" + docNo;//key;
      document.getElementById("landRightTypeId").disabled = true;
      
      document.getElementById("landDocNo").value = docNo; 
      document.getElementById("landDocNo").readOnly = true; 
      
	  document.getElementById("landDocRai").value = docRai;
	  document.getElementById("landDocRai").readOnly = true;
	  
	  document.getElementById("landDocNgan").value = docNgan;
	  document.getElementById("landDocNgan").readOnly = true;
	  
	  document.getElementById("landDocWah").value = docWah;
	  document.getElementById("landDocWah").readOnly = true;
	  
      document.getElementById("breedGroupId").value = groupId;
      qty = qty.replace(/'/g, '');
      if(qty != ""){
    	  document.getElementById("qtyPerRai").value = formatNumber(qty);
      }else{
    	  document.getElementById("qtyPerRai").value = "";
      } 	  
      document.getElementById("plantRai").value = plantRai;
      document.getElementById("plantNgan").value = plantNgan;
      document.getElementById("plantWah").value = plantWah;
     
	  seedResource = seedResource.replace(/'/g, '');
	  seedPicking = seedPicking.replace(/'/g, '');
	  plantObjective =  plantObjective.replace(/'/g, '');
	  agricultureType = agricultureType.replace(/'/g, '');
      gap = gap.replace(/'/g, '');
      planting = planting.replace(/'/g, '');
      plotting = plotting.replace(/'/g, '');
	  manureUse= manureUse.replace(/'/g, '');
	   
	 	var plotOther = "";
	   	var rad = document.querySelectorAll('input[type="radio"]');
		for (i = 0, l = rad.length; i < l; i++)
		{
		   if(rad[i].name == "gap"){
			   var radValue = rad[i].value;
			   if(gap == radValue){
            	  document.getElementById(rad[i].id).checked = true;
            	  break;
			   }
           }
		   if(plantObjective.indexOf('^') > -1) 
			    plantObjective = plantObjective.replace(/\^/g,',');
		   var sType = plantObjective.split(',');
		   if(rad[i].name == "objective"){
			   for (j = 0, m = sType.length; j < m; j++){
				   if(sType[j] == rad[i].value){
					   document.getElementById(rad[i].id).checked = true;
					   var subObjectivePlant = "";
					   if(sType[j] == "เพื่อทำเมล็ดพันธุ์") {
						   subObjectivePlant  = getSubObjectivePlant();
					   }
					   document.getElementById("SubObjectivePlant").innerHTML = subObjectivePlant;
				   }
			   }
			   var sObjective = document.getElementsByName("subObjective");
			   for(k = 0; k < sObjective.length; k++) {
				   if(sType.length > 1 && sType[1] == sObjective[k].value) {
					   sObjective[k].checked = true;
				   }
			   }
		   }
		   if(rad[i].name == "agricultureType"){
			   var radValue = rad[i].value;
			   if(agricultureType.indexOf('^') > -1) 
				    agricultureType = agricultureType.replace(/\^/g,','); 
				 	var sType = agricultureType.split(',');
				 	for (j = 0, m = sType.length; j < m; j++){ 
					   if(sType[j] == rad[i].value){
         	  			document.getElementById(rad[i].id).checked = true;
         	  	    }
				}	
           }
		}
		
		var cbx = document.querySelectorAll('input[type="checkbox"]');
		for (i = 0, l = cbx.length; i < l; i++) 
		{  
			  if(cbx[i].name == "seedResource"){
			    	if(seedResource.indexOf('^') > -1) 
				       seedResource = seedResource.replace(/\^/g,','); // replace ^ with ,
					var s = seedResource.split(',');
	  				for (j = 0, m = s.length; j < m; j++){ 
	  				  	if(s[j] == cbx[i].value){
	              	  		  document.getElementById(cbx[i].id).checked = true;
		              	  	  // --- Added on 29.04.2015 by Yatphiroon.P 
	              	  		  showCooperative(document.getElementById(cbx[i].id));
	              	  		  if(document.getElementById(cbx[i].id).value == "ซื้อจากสหกรณ์"){
			              	      document.getElementById('corpGroupId').value = corpGroupId;
			              	      if(corpGroupId > 0){
			              	    	  	document.getElementById('noOfBreed').value = noOfSeed;
			              	     } else {
			              				document.getElementById("corpGroupId").value = "0";
			              				document.getElementById("noOfBreed").value = "0";
			              		  }
	              	  		  }
	              	  		  //---
	              	  		
	               	  	}
	  				}
         	  } if(cbx[i].name == "picking"){
					if(seedPicking.indexOf('^') > -1)
				   	   seedPicking = seedPicking.replace(/\^/g,','); // replace ^ with ,
				 	var sType = seedPicking.split(',');
					for (j = 0, m = sType.length; j < m; j++){ 
					   	if("picking_" + sType[j] == cbx[i].id){
            	  			document.getElementById(cbx[i].id).checked = true;
            	  	   	}
					}
         	  } 
         	
         	  if(cbx[i].name == "planting"){
				 	if(planting.indexOf('^') > -1) 
					   planting = planting.replace(/\^/g,','); // replace ^ with ,
				 	var sType = planting.split(',');
				 	for (j = 0, m = sType.length; j < m; j++){ 
					    if("planting_" + sType[j] == cbx[i].id){
            	  			document.getElementById(cbx[i].id).checked = true;
            	      	}
					}
         	  }  if(cbx[i].name == "plot"){
	  				if(plotting.indexOf('^') > -1) 
	 				   plotting = plotting.replace(/\^/g,','); // replace ^ with ,
	  				var sType = plotting.split(',');
	  				for (j = 0, m = sType.length; j < m; j++){ 
	  					if("plot_" + sType[j] == cbx[i].id){
	               	       document.getElementById(cbx[i].id).checked = true;
	               	       getSubPrepareAreaList(cbx[i].id);
	               	       checkChildBoxes(plotting, plottingName);
	               	  	}
	  				}
         	  }  if(cbx[i].name == "manureUse"){
				 	  if(manureUse.indexOf('^') > -1) 
					  	   manureUse = manureUse.replace(/\^/g,','); // replace ^ with ,
				 	  var sType = manureUse.split(',');
	  				  for (j = 0, m = sType.length; j < m; j++){ 
	  					  if("manureUse_" + sType[j] == cbx[i].id){
	               	  		document.getElementById(cbx[i].id).checked = true;
	               	  	  }
	  				  }
         	  } 
		}
			
        document.getElementById("plantDate").value = plantDate.replace(/'/g, "");
		document.getElementById("forecastDate").value = forecastDate.replace(/'/g, "");
		document.getElementById("forecastRecordDoc").value = forecastRecordDoc.replace(/'/g, "");
		document.plantForm.isTab1Saved.value = "yes";
		
		// ปุ๋ย
		document.getElementById("lblManureBreedGroupName").innerHTML = groupName.replace(/'/g , "");
		// สารเคมี
		document.getElementById("lblChemiBreedGroupName").innerHTML = groupName.replace(/'/g , "");
		// tab ที่ 5
		document.getElementById("lblTabBreed5_2").innerHTML = breedTypeObjName; // ประเภทพืชที่ปลูก
		document.getElementById("lblTabBreed5_3").innerHTML = groupName.replace(/'/g, ""); // พันธุ์พืชที่ปลูก
		document.getElementById("lblTabBreed5_4").innerHTML = plantRai + " ไร่   " + plantNgan + " งาน " + plantWah + " วา "; // ขนาดพื้นที่เพาะปลูก
		
		globBreedGroupName = groupName.replace(/'/g, "");
		globPlantArea = plantRai + " ไร่   " + plantNgan + " งาน " + plantWah + " วา ";
	
		// load table 
		loadNoticeTab(rowIndex);
		loadPlantManure(rowIndex);
		loadPlantChemi(rowIndex);
		loadAssetDetail();
		isSave = 0;
		
		if(popUpName == "PlantBreedPopup"){
			showWindow('PlantBreedPopup', rowIndex);
     }
}	

function checkChildBoxes(childStr, plotName)
{
	   var sType = childStr.split(',');
	   var cbx1 = document.querySelectorAll('input[type="checkbox"]');
	   for (k = 0, s = cbx1.length; k < s; k++){  
			  if(cbx1[k].name == "plot"){
				  for (x = 0, n = sType.length; x < n; x++){ 
					  if("plot_" + sType[x] == cbx1[k].id){
						   if(document.getElementById(cbx1[k].id).checked == false){
							  document.getElementById(cbx1[k].id).checked = true;
							  if(cbx1[k].value == "อื่นๆ"){
		               	  			document.getElementById("plottingName").value =	plotName.replace(/'/g, '');
		               	  	   }
						   }
					  }
		  	  	  }
			  }
		}
}
function showCostWindow(rowIndex, groupId, groupName)
{	
  		breedIndex = rowIndex;
		if(costArray[rowIndex]  != undefined ){
			 itemCost = costArray[rowIndex];
			 intCostCnt = costArray[rowIndex].length;
		}else{
			 itemCost = "";
			 intCostCnt = 0;
	   	}
		
		// cost ที่ Add แล้วยกเลิก
		costTempArray[rowIndex] = costArray[rowIndex];
		tempCost = costTempArray[rowIndex];
		// finish cast ที่ Add แล้วยกเลิก
		
	   	for (var id in arrayCost)   
		   delete arrayCost[id];  //alert(arrayCost[id]+"!");
		arrayCost = {};
		     
		isSave = 0; 
		
		
		showWindow('CostListForm', rowIndex);	
		document.getElementById("dynamicCostDetail").innerHTML = generateCostTable(itemCost);
		document.getElementById('lblCostListBreedType').innerHTML = breedTypeObjName;
		document.getElementById('lblCostBreedGroup').innerHTML = groupName.replace(/'/g, "");
		document.getElementById('lblCostDetailBreedGroup').innerHTML = groupName.replace(/'/g, ""); 
} 
function showSaleWindow(rowIndex, groupId, groupName)
{	
	    breedIndex = rowIndex;		
	    breedGroupObjName = groupName;
	    isSave = 0;
		showWindow('SaleListForm', rowIndex);
		document.getElementById('SaleListForm').style.visibility='visible'; 	
		
		var plantRai = breedArrayTab1[rowIndex].split(",")[10]; 
	    var plantNgan = breedArrayTab1[rowIndex].split(",")[11]; 
		var plantWah = breedArrayTab1[rowIndex].split(",")[12]; 
		globPlantArea = plantRai + " ไร่  " + plantNgan + " งาน  " + plantWah + " วา";
		
		var plantDate = breedArrayTab1[rowIndex].split(",")[22]; 
		globPlantDate = plantDate.replace(/'/g, "");
				
		document.getElementById('lblSaleBreedGroup').innerHTML = groupName.replace(/'/g, "");
		document.getElementById("lblSaleDetailBreedType").innerHTML = breedTypeObjName; 
		document.getElementById('lblSaleDetailBreedGroup').innerHTML = groupName.replace(/'/g, "");
		document.getElementById("lblSaleDetailArea").innerHTML = globPlantArea;

        document.getElementById("lblHarvestDetailBreedType").innerHTML = breedTypeObjName;
    	document.getElementById("lblHarvestDetailBreedGroup").innerHTML = breedGroupObjName;
		document.getElementById("lblHarvestDetailArea").innerHTML = globPlantArea;
		
		loadHarvestDetail(breedIndex);
		loadSellingDetail(breedIndex);
		
		
		// sell ที่ Add แล้วยกเลิก
	    sellTempArray[rowIndex] = saleDetailArray[rowIndex];
		tempSelling = sellTempArray[rowIndex];
		
	    // harvestDetail ที่ Add แล้วยกเลิก
		harvTempArray[rowIndex] = harvArray[rowIndex]
		tempHarvest = harvTempArray[rowIndex];
		
		
		// finish cast ที่ Add แล้วยกเลิก
} 
//แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยวจริง
function showDetailWindow(rowIndex, groupId, groupName)
{
	   itemBreed1 = breedArrayTab1[rowIndex];	   
	   if(itemBreed1 != null && itemBreed1.length > 0){
	         var newDataArr = new Array();
	         newDataArr = itemBreed1.split(",");
		  	 editBreed(newDataArr[0], newDataArr[1], newDataArr[2], newDataArr[3], newDataArr[4], newDataArr[5], newDataArr[6], newDataArr[7], newDataArr[8], newDataArr[9], newDataArr[10], newDataArr[11], newDataArr[12], newDataArr[13], newDataArr[14], newDataArr[15], newDataArr[16], newDataArr[17], newDataArr[18], newDataArr[19], newDataArr[20], newDataArr[21], newDataArr[22], newDataArr[23], newDataArr[24], newDataArr[25], newDataArr[26], newDataArr[27], newDataArr[28], '');   
	   }
	   
	   showWindow('PlantBreedPopup', rowIndex);
	   $("ul#detail_containBreedTab > li:visible").hide();			
	   $("ul#detail_containBreedTab > li").eq(4).show();
	   // added by MuSic
	   $("ul#navi_containBreedTab > li").css("background-color","#DDDDDD");
       $("ul#navi_containBreedTab > li").eq(4).css("background-color","#C4C4C4");
} 

// ---- Plant Manure ----//
function savePlantManure()
{ 
	  if(document.getElementById("manureTypeId").value == ""){
		  alert("กรุณาเลือกประเภทปุ๋ย");
		  document.getElementById("manureTypeId").focus();
		  return false;
	  } if(document.getElementById("mPeriodTime").value == "0"){
		  alert("กรุณาเลือกช่วงเวลาการใส่");
		  document.getElementById("mPeriodTime").focus();
		  return false;
	  } if(document.getElementById("manureDate").value == ""){
		  alert("กรุณาเลือกวันที่ใส่ปุ๋ย");
		  document.getElementById("mPeriodTime").focus();
		  return false;
	  }
	  
	  if(saveManureData() != false){
	  	 document.getElementById("dynamicPlantManure").innerHTML = generateManureTable(itemManure); 
	  	 isAdd++;
	  	 closeWindow('PlantManurePopup',0);
	  }
}
function saveManureData()
{
	 	 var manureTypeId = document.getElementById("manureTypeId").value;
	  	 var mObj =  document.getElementById("manureTypeId");
	  	 var manureTypeName = mObj.options[mObj.selectedIndex].text;
	  	 var ownProd = document.getElementById("ownProduce");
	  	 if(ownProd.checked == true){
	  		 ownProd = 1;
	  	 }else{ 
	  		 ownProd = 0;
	  	 }
	  	 var ownBuy = document.getElementById("ownBuy");
	  	 if(ownBuy.checked == true)
	  		 ownBuy = 1;
	  	 else 
	  		 ownBuy = 0;
      
	  	 var mName = document.getElementById("mName").value;
	   	 mName = mName.replace(/\,/g,'^'); // in case of ,
	   	 var mFormula = document.getElementById("mFormula").value;
	   	 mFormula = mFormula.replace(/\,/g,'^'); // in case of ,
	   	 var mCostPerRai = document.getElementById("mCostPerRai").value;
	   	 if (mCostPerRai != ""){
	   	     mCostPerRai = mCostPerRai.replace(/,/g , "");
	   	 }else{
	   	     mCostPerRai = 0;
	   	 }
	   	 var mPeriodTime = document.getElementById("mPeriodTime").value;
	   	 var manureDate = document.getElementById("manureDate").value;
	   	 
	  	 var mQtyPerRai = document.getElementById("mQtyPerRai").value;
	  	 if(mQtyPerRai != ""){
	  		mQtyPerRai = mQtyPerRai.replace(/,/g , "")
	  	 }else{
	  		mQtyPerRai = 0;
	  	 }
	  	 
	  	// var key = manureTypeId + "" + mName;//
	  	 if(manureDetailArray[manureIndex]!="" && manureDetailArray[manureIndex] != undefined){
		 	   itemManure = "";
		 	   var key = manureTypeId + "" + manureIndex;//
		        for(var i=1; i< manureDetailArray.length; i++){ 
		           if(i == manureIndex){
		                var prevManureIndex =  manureDetailArray[i].split(",")[0];
		        	    var prevManureId = manureDetailArray[i].split(",")[1];
		                var prevManureName = manureDetailArray[i].split(",")[5];
		                var prevManureKey = prevManureId + "" + prevManureIndex;
		                if(arrayManure.hasOwnProperty(prevManureKey)){
		                   if(prevManureKey != key){ //เปลี่ยนประเภท manure หรือชื่อ manureแต่เป็น index เดิม
		                   		if(!arrayManure.hasOwnProperty(key)){
		                   		   manureDetailArray[i] =  i  + "," + manureTypeId + "," + manureTypeName  + "," + ownBuy + "," + ownProd + "," + mName + "," + mFormula + "," + mCostPerRai + "," + mPeriodTime + "," + manureDate + "," + mQtyPerRai;
		                   		   delete arrayManure[prevManureKey];
		                   		}else{
		                   		   alert("มีข้อมูลปุ๋ยซ้ำกัน!");
		                	       return false;
		                	     }
		               	   }else{
		               	       manureDetailArray[i] =  i  + "," + manureTypeId + "," + manureTypeName  + "," + ownBuy + "," + ownProd + "," + mName + "," + mFormula + "," + mCostPerRai + "," + mPeriodTime + "," + manureDate + "," + mQtyPerRai;
		                   }
		                }else{
				  			   manureDetailArray[i] =  i  + "," + manureTypeId + "," + manureTypeName  + "," + ownBuy + "," + ownProd + "," + mName + "," + mFormula + "," + mCostPerRai + "," + mPeriodTime + "," + manureDate + "," + mQtyPerRai;
				  		}
				  	}
				  	itemManure += "|" + manureDetailArray[i];
				}
				itemManure = itemManure.substr(1);
			    manureArray[breedDetailIndex] = itemManure;
	   	        manureIndex = 0;
		} else {
			  intManureCnt++;
			  var key =  manureTypeId + "" + intManureCnt;
			  if(arrayManure.hasOwnProperty(key)) {
				    alert("มีข้อมูลปุ๋ยซ้ำกัน!");
			  		return false;
			  }
			  arrayManure[key] = intManureCnt;	
		      if(intManureCnt == 1){
			       itemManure +=  intManureCnt + "," + manureTypeId + "," + manureTypeName + "," + ownBuy + "," + ownProd + "," + mName + "," + mFormula + "," + mCostPerRai + "," + mPeriodTime + "," + manureDate + "," + mQtyPerRai;
		      } else {
				   itemManure += "|" + intManureCnt + "," + manureTypeId + "," + manureTypeName + "," + ownBuy + "," + ownProd + "," + mName + "," + mFormula + "," + mCostPerRai + "," + mPeriodTime + "," + manureDate  + "," + mQtyPerRai;
			  }	  
			  manureArray[breedDetailIndex] = itemManure;
		}
}

//--------- PlantChemical --------------
function savePlantChemical()
{
		if(document.getElementById("chemicalTypeId").value == ""){
			alert("กรุณาเลือกประเภทสารเคมี");
			document.getElementById("chemicalTypeId").focus();
			return false;
		} if(document.getElementById("cPeriodTime").value == 0){
			alert("กรุณาเลือกช่วงเวลาการใส่สารเคมี");
			document.getElementById("cPeriodTime").focus();
			return false;
		} if(document.getElementById("chemicalDate").value == ""){
			alert("กรุณาเลือกวันที่ใช้สารเคมี");
			document.getElementById("chemicalDate").focus();
			return false;
		}
		
	    if(saveChemicalData() != false){
	   		document.getElementById("dynamicPlantChemical").innerHTML = generateChemicalTable(itemChemi);
	   		isAdd++;
	   		closeWindow('PlantChemicalPopup',0);
	    }
}
function saveChemicalData()
{    	
	   var chemicalTypeId = document.getElementById("chemicalTypeId").value;
	   var cObj =  document.getElementById("chemicalTypeId");
	   var chemTypeName = cObj.options[cObj.selectedIndex].text;
	   var cFormula = document.getElementById("cFormula").value;
	   if(cFormula.indexOf(','))
		   cFormula = cFormula.replace(/\,/g,'^'); 
	   var cCostPerRai = document.getElementById("cCostPerRai").value;
	   if (cCostPerRai != ""){
		   cCostPerRai = cCostPerRai.replace(/,/g , "");
	   } else{	 
	   	   cCostPerRai = 0;
	   }
	   var cPeriodTime = document.getElementById("cPeriodTime").value;
	   var chemicalDate = document.getElementById("chemicalDate").value;
	   var cQtyPerRai = document.getElementById("cQtyPerRai").value;
	   if(cQtyPerRai != ""){
		   cQtyPerRai = cQtyPerRai.replace(/,/g , "");
	   }else{
	   	   cQtyPerRai = 0;
	   }
      
       if(chemDetailArray[chemIndex] != "" && chemDetailArray[chemIndex] != undefined) { 
		 	    itemChemi = "";
		 	    var key = chemicalTypeId + "" + chemIndex;
		        for( var i=1; i < chemDetailArray.length; i++ ){ 
		           if(i == chemIndex){
		        	    var prevChemIndex = chemDetailArray[i].split(",")[0]; 
		        	    var prevChemId = chemDetailArray[i].split(",")[1];
		                var prevChemFormula = chemDetailArray[i].split(",")[5];
		                var prevChemKey = prevChemId + "" + prevChemIndex;
		                if(arrayChem.hasOwnProperty(prevChemKey))
		                {
		                   if(prevChemKey != key){ //เปลี่ยนประเภททรัพย์สินแต่เป็น index เดิม
		                   		if(!arrayChem.hasOwnProperty(key)){
		                   		   chemDetailArray[i] =  i  + "," + chemicalTypeId + "," + chemTypeName + "," + cFormula + "," + cCostPerRai + "," + cPeriodTime + "," + chemicalDate + "," + cQtyPerRai;
		                   		   delete arrayChem[prevChemKey];
		                   		}else{
		                   			alert("มีสารเคมีซ้ำกัน!");
		                	        return false;
		                	     }
		               	   }else{
		               	       chemDetailArray[i] = i + "," + chemicalTypeId + "," + chemTypeName + "," + cFormula + "," + cCostPerRai + "," + cPeriodTime + "," + chemicalDate + "," + cQtyPerRai;
		                   }
		                }else{
				  			   chemDetailArray[i] = i + "," + chemicalTypeId + "," + chemTypeName + "," + cFormula + "," + cCostPerRai + "," + cPeriodTime + "," + chemicalDate + "," + cQtyPerRai;
				  		}
				  	}
				  	itemChemi += "|" + chemDetailArray[i];
				}
				itemChemi = itemChemi.substr(1);
				chemArray[breedDetailIndex] = itemChemi;
				chemIndex = 0;
		} else {
			 intChemCnt++;
			 var key = chemicalTypeId + "" + intChemCnt;
			 if(arrayChem.hasOwnProperty(key)){
				 alert("มีสารเคมีซ้ำกัน!");
			  	 return false;
			 }
			 arrayChem[key] = intChemCnt;	
		   		 
		     if(intChemCnt == 1){
			    itemChemi += intChemCnt + "," + chemicalTypeId + "," + chemTypeName + "," +  cFormula + "," + cCostPerRai + "," + cPeriodTime + "," + chemicalDate + "," + cQtyPerRai;
		     } else {
				 itemChemi += "|" + intChemCnt + "," + chemicalTypeId +"," + chemTypeName + "," +  cFormula + "," + cCostPerRai + "," + cPeriodTime + "," + chemicalDate + "," + cQtyPerRai;
			 }
			 chemArray[breedDetailIndex] = itemChemi;
		}
}
//------------------Asset-------------------------
function saveAssetDetail()
{
       if( document.getElementById("assetId").value == ""){
			alert("กรุณาเลือกทรัพย์สิน");  
			document.getElementById("assetId").focus();
			return false;
	   } if( document.getElementById("assetDate").value == ""){
			alert("กรุณาใส่วันที่ซื้อ");  
			document.getElementById("assetDate").focus();
			return false;
	   } if( document.getElementById("amount").value == ""){
			alert("กรุณาใส่จำนวนเงิน");  
			document.getElementById("amount").focus();
			return false;
	   } 
	   
	   if(saveAssetData() != false){
	   		document.getElementById("dynamicPlantAsset").innerHTML = generateAssetTable(itemAsset);
	   	    isAdd++;
	   		document.getElementById("assetAmount").value = formatNumber(totalAmount);	// ยอดรวม
	   		closeWindow('PlantAssetPopup',0);
	   }
}
function saveAssetData()
{
       var assetId = document.getElementById("assetId").value;
	   var aObj =  document.getElementById("assetId");
	   var assetName = aObj.options[aObj.selectedIndex].text;
	   var assetDate = document.getElementById("assetDate").value;
	   var amount = document.getElementById("amount").value.replace(/,/g , "");
	   if(assetArray[assetIndex]!="" && assetArray[assetIndex] != undefined)
	   {
	  		     itemAsset = "";
			     for(var i = 1; i < assetArray.length; i++){
					if(i == assetIndex){
 		                var prevAssetId =  assetArray[i].split(",")[1];
 		                if(arrayAsset.hasOwnProperty(prevAssetId)){
 		                   if(prevAssetId != assetId){ //เปลี่ยนประเภททรัพย์สินแต่เป็น index เดิม
 		                   		if(!arrayAsset.hasOwnProperty(assetId)){
 		                   			assetArray[i] = i + "," + assetId + "," + assetName + "," + assetDate + "," + amount;
 		                   		    delete arrayAsset[prevAssetId];
 		                   		}else{
 		                	       alert("เลือกทรัพย์สินซ้ำ  : " + assetName);
 		                	       return false;
 		                	     }
 		               	   }else{
 		               	      assetArray[i] = i + "," + assetId + "," + assetName + "," + assetDate + "," + amount;
 		                   }
 		                } else {
				  		  	assetArray[i] = i + "," + assetId + "," + assetName + "," + assetDate + "," + amount;	
				  		}
				  	}
				  	itemAsset += "|" + assetArray[i];
			    }
				itemAsset = itemAsset.substr(1);
			    assetArray[assetIndex] = itemAsset;
  	   	        assetIndex = 0;
	   } else { 
	   	    if(arrayAsset.hasOwnProperty(assetId)){
	 	       	alert("เลือกทรัพย์สินซ้ำ : "+ assetName); 
			    return false; 
  	   	    }
		    intAssetCnt = intAssetCnt + 1;
		    arrayAsset[assetId] = intAssetCnt;	
		    
		    if(intAssetCnt == 1){
			    itemAsset += intAssetCnt + "," + assetId + "," + assetName + "," + assetDate + "," + amount;
		    }else{
			    itemAsset += "|" + intAssetCnt + "," + assetId +"," + assetName + "," + assetDate + "," + amount; 
			}
			assetArray[intAssetCnt] = intAssetCnt + "," + assetId + "," + assetName + "," + assetDate + "," + amount;
		    assetIndex = 0;
	   }
}
function checkEmailFormat(str)
{
	  var RE = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      if(!RE.test(str.value)){
		 	alert("รูปแบบอีเมล์แอดเดรสของท่านไม่ถูกต้อง");  
		 	$('#'+str.id).css('border-color', 'black');  // just added 22.07.2014
		    	setTimeout(function() {
		     		 	document.getElementById(str.id).focus();
		   	}, 0);
    		return false;
	  }else{
		 $('#'+ str.id).css('border-color', '');
		 return true;
	  }
}
function checkTel(str)    
{
    var RE = /^\d+$/;
    var len = str.value.length;
    if(RE.test(str.value)){
    	if(str.id == "mobile"){
	    	if(len != 10){
	    		 alert("กรุณาใส่เบอร์โทรศัพท์มือถือ 10 หลัก");
	        	 $('#'+str.id).css('border-color', 'black');  // just added 22.07.2014
	    		 setTimeout(function() {
	     		 	document.getElementById(str.id).focus();
	   			 }, 0);
	    		 return false;
	    	}else{
	       		$('#'+str.id).css('border-color', '');  // just added 22.07.2014
	        }
    	}
    }else{
        alert("กรุณาใส่เบอร์โทรศัพท์ที่เป็นตัวเลขเท่านั้น");
        document.getElementById(str.id).value = "";
        $('#'+str.id).css('border-color', 'black');  // just added 22.07.2014
    	setTimeout(function() {
     		 	document.getElementById(str.id).focus();
   		}, 0);
    }
}
function formatNumber(n) 
{
        var num = parseFloat(n);
        return num.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
}
function checkNumberComma(str) 
{
	   str.value = (str.value).replace(/,/g,"");
	   
       var RE = /^\d*(\.\d{1})?\d{0,1}$/;
       var RE2 = /^\d{1,3}(?:,\d{3})*(?:\.\d+)?$/;
       var myStr = str.value.split(".");
        if(str.value != "" && myStr[0].length<=10){
            if(RE.test(str.value)){
                document.getElementById(str.id).value = formatNumber(str.value);
                
                if(str.id == "salePrice"){
                	 var valCrop = document.getElementById("saleCrop").value;
                	 var valueCrop = 0;
                	 var valuePrice = 0;
                	 
                	 if(valCrop == ""){
                		 valueCrop = Number(0);
                	 }else{
                		 valueCrop = Number(valCrop.replace(/[^0-9\.]+/g,""));
                	 }
                	 var valPrice = document.getElementById(str.id).value;
                	 valuePrice = Number(valPrice.replace(/[^0-9\.]+/g,""));
                	 
                	 var valAmount = valuePrice*valueCrop;
                	 document.getElementById("saleAmount").value = formatNumber(valAmount);
                } if(str.id == "saleCrop"){
	               	 var valPrice = document.getElementById("salePrice").value;
	               	 var valueCrop = 0;
                	 var valuePrice = 0;
	               	 if(valPrice == ""){
	               		 valuePrice = Number(0);
	               	 }else{
	            		 valuePrice = Number(valPrice.replace(/[^0-9\.]+/g,""));
	            	 }
	            	 var valCrop = document.getElementById(str.id).value;
	            	 valueCrop = Number(valCrop.replace(/[^0-9\.]+/g,""));
	            	 
	            	 var valAmount = valuePrice*valueCrop;
	            	 document.getElementById("saleAmount").value = formatNumber(valAmount);
                } if(str.id == "saleDryPrice"){
	               	 var valCrop = document.getElementById("saleDryCrop").value;
	            	 var valueCrop = 0;
	            	 var valuePrice = 0;
	            	 
	            	 if(valCrop == ""){
	            		 valueCrop = Number(0);
	            	 }else{
	            		 valueCrop = Number(valCrop.replace(/[^0-9\.]+/g,""));
	            	 }
	            	 var valPrice = document.getElementById(str.id).value;
	            	 valuePrice = Number(valPrice.replace(/[^0-9\.]+/g,""));
	            	 
	            	 var valAmount = valuePrice*valueCrop;
	            	 document.getElementById("saleDryAmount").value = formatNumber(valAmount);
	            } if(str.id == "saleDryCrop"){
	               	 var valPrice = document.getElementById("saleDryPrice").value;
	               	 var valueCrop = 0;
	            	 var valuePrice = 0;
	               	 if(valPrice == ""){
	               		 valuePrice = Number(0);
	               	 }else{
	            		 valuePrice = Number(valPrice.replace(/[^0-9\.]+/g,""));
	            	 }
	            	 var valCrop = document.getElementById(str.id).value;
	            	 valueCrop = Number(valCrop.replace(/[^0-9\.]+/g,""));
	            	 
	            	 var valAmount = valuePrice*valueCrop;
	            	 document.getElementById("saleDryAmount").value = formatNumber(valAmount);
	            }
                
            }else if(RE2.test(str.value)){
                var sttNew = str.value.replace(/,/g , "");
                document.getElementById(str.id).value = sttNew;
            }else{
            	document.getElementById(str.id).value = "";
            	alert("กรุณากรอกข้อมูลเป็นตัวเลขและทศนิยมสองตำแหน่งเท่านั้น");
            	window.setTimeout(function () {document.getElementById(str.id).focus();}, 0);
            }
        }else{
        	if(str.value=="") {
            } else if(myStr[0].length>10) {
            	alert("ข้อมูลที่กรอกไม่ถูกต้อง !!");
            } else {
            	alert("กรุณากรอกข้อมูลเป็นตัวเลขและทศนิยมสองตำแหน่งเท่านั้น");
            }
            document.getElementById(str.id).value="";
            window.setTimeout(function () {
            	document.getElementById(str.id).focus();
            }, 0);
        }  
}
function checkNumber(str)
{
    var RE = /^\d+$/;
    if(RE.test(str.value)){
    	$('#'+str.id).css('border-color', '');
    }else{
    	alert("กรุณาใส่ค่าที่เป็นตัวเลข");
      	document.getElementById(str.id).value="";
        $('#'+str.id).css('border-color', 'black'); 
		setTimeout(function() {
     		 document.getElementById(str.id).focus();
   		}, 0);
    }
}
function checkInvalidText(str) 
{
	var canPass = 1;
	var checkStr = '<>&#$^|+฿*:'; //ตัวอักษรที่ไม่ต้องการให้มี
	if (str.value.indexOf("'")!= -1)
		canPass++; //เครื่องหมาย '
	if (str.value.indexOf('"')!= -1)
		canPass++; //เครื่องหมาย "
	for (i = 0; i < checkStr.length; i++) {
		if (str.value.indexOf(checkStr.charAt(i))!= -1) {
			canPass=0;
		}
	}
	if(canPass == 0){
		alert("กรุณากรอกข้อมูลที่ไม่มีอักขระพิเศษ!");
		document.getElementById(str.id).value="";
		$('#'+str.id).css('border-color', 'black');
		setTimeout(function() {
			document.getElementById(str.id).focus();
		}, 0);
	}else{
		$('#'+str.id).css('border-color', '');
	}
}

function checkFamilyIdCard(id)
{
	var RE = /^\d+$/;
	var txt = "";
	var idLen = id.value.length;
	if(RE.test(id.value)){
		if(idLen == 13){
			validateIdCard(id);
		} else {
			alert("กรุณาใส่รหัสบัตรประจำตัวประชาชนเป็นตัวเลข 13 หลัก");
			window.setTimeout(function () {id.focus();}, 0);
		}
	} else {
		id.value="";
		alert("กรุณาใส่รหัสบัตรประจำตัวประชาชนเป็นตัวเลข 13 หลัก");
		window.setTimeout(function () {id.focus();}, 0);
	}
	return false;
}
function validateIdCard(id)
{
	var idCard = id.value;
	var idLen = id.value.length;
    var total = 0;
    var mul = 13;
    for(i=0;i<idLen-1;i++) {
    	total = total + idCard.charAt(i) * mul;
        mul = mul -1;
    }
    mod = 11 - (total % 11);
    mod2 = mod % 10;
     
    if(mod2 != idCard.charAt(12)) {
    	alert("รหัสบัตรประจำตัวประชาชนไม่ถูกต้อง");
    	window.setTimeout(function () {id.focus();}, 0);
    	return false;
    } else {
    	return true;
    }
}
function isStatus(obj){
	if(document.getElementById("stPass").checked){
		document.getElementById("reason").disabled = true;
	}
	if(document.getElementById("stNotPass").checked){
		document.getElementById("reason").disabled = false;
	}
}
function setPlantNo(objPeriodPlant, objPlantNo){
	var periodDD = document.getElementById(objPeriodPlant);
    var varPeriodPlant = periodDD.options[periodDD.selectedIndex].text;
	var plantYear = document.getElementById("plantYear").value;
	if(plantYear=="9999") {
		periodDD.value = "นาปี";
		varPeriodPlant = "นาปี";
	}
	if(varPeriodPlant == "นาปี"){
		document.getElementById(objPlantNo).value = 1; 
	}else if(varPeriodPlant == "นาปรัง"){
		document.getElementById(objPlantNo).value = 2;
	}else{
		document.getElementById(objPlantNo).value = "";
	}
}
function setPeriodPlant(objPeriodPlant, objPlantNo){
	var plantNoText = document.getElementById(objPlantNo).value;
	var plantYear = document.getElementById("plantYear").value;
	if(plantYear=="9999") {
		document.getElementById(objPlantNo).value = 1;
		plantNoText = 1;
	}
	if(plantNoText > 2){
		 alert("ท่านสามารถใส่ครั้งที่ได้มากที่สุดคือ 2");
		 return false;
	}else{
		if(plantNoText == 1){
			document.getElementById(objPeriodPlant).value = "นาปี";
		}else if(plantNoText == 2){
			document.getElementById(objPeriodPlant).value = "นาปรัง";
		}else{
			document.getElementById(objPeriodPlant).value = 0;
		}
	}
}
function addLandcheck(typeId, docNo, docRai, docNgan, docWah){

	var plantYear = document.plantForm.plantYear.value;
	var periodPlant = document.getElementById("periodPlant").value;
	var idCard = document.getElementById("idCard").value;
	var firstName = document.getElementById("firstName").value;
	var lastName = document.getElementById("lastName").value;
	var breedTypeId = document.getElementById("breedTypeId").value;
	var refPlantId = document.plantForm.refPlantId.value;
	var plantNo = document.getElementById("plantNo").value;
	
		document.landCheckForm.cmd.value = "addFromPlant";
		document.landCheckForm.landCheckId.value = "0";
		document.landCheckForm.typeId.value = typeId;
		document.landCheckForm.docNo.value = docNo;
		document.landCheckForm.docRai.value = docRai;
		document.landCheckForm.docNgan.value = docNgan;
		document.landCheckForm.docWah.value = docWah;
		document.landCheckForm.plantYear.value = plantYear;
		document.landCheckForm.season.value = periodPlant;
		document.landCheckForm.idCard.value =idCard;
		document.landCheckForm.firstName.value = firstName;
		document.landCheckForm.lastName.value = lastName;
		document.landCheckForm.breedTypeId.value = breedTypeId;
		document.landCheckForm.refPlantId.value = refPlantId;
		document.landCheckForm.plantNo.value = plantNo;
		document.forms["landCheckForm"].submit();
	
}