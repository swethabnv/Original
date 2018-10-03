	$(document).ready(function() {
	    $("ul#navi_containTab > li").click(function(event){  
	             var menuIndex=$(this).index();
	             $("ul#tab_container > li:visible").hide();
	             if(menuIndex  > 0){
	             	if(document.getElementById("docId").value == 0
	             	 || document.getElementById("checkPeriod").value == 0){
	             		alert("กรุณากรอกข้อมูลการตรวจแปลงให้เรียบร้อย");
	             		menuIndex = 0;
	             	}
	             }
	             hideCalendar();
	             $("ul#tab_container > li").eq(menuIndex).show();   
		         $("ul#navi_containTab > li").css("background-color","#DDDDDD");
		         $("ul#navi_containTab > li").eq(menuIndex).css("background-color","#C4C4C4");
	    });
	});
	
	function setTab(objName) {
		var checkPeriod = document.getElementById(objName);
		var index = checkPeriod.selectedIndex;
		chkPeriod = checkPeriod.options[checkPeriod.selectedIndex].text;
		setDoc();
		
		for(var i=0;i<6;i++) {
			$("ul#navi_containTab > li").eq(i).hide();
			$("ul#tab_container > li").eq(i).hide();
		}
        $("ul#navi_containTab > li").css("background-color","#DDDDDD");
        
		if(index==1) {
			$("ul#navi_containTab > li").eq(0).show();
			$("ul#tab_container > li").eq(0).show();
	        $("ul#navi_containTab > li").eq(0).css("background-color","#C4C4C4");
		} else if(index==2) {
			$("ul#navi_containTab > li").eq(1).show();
			$("ul#tab_container > li").eq(1).show();
	        $("ul#navi_containTab > li").eq(1).css("background-color","#C4C4C4");
		} else if(index==3 || index==4) {
			$("ul#navi_containTab > li").eq(2).show();
			$("ul#navi_containTab > li").eq(3).show();
			$("ul#navi_containTab > li").eq(4).show();
			$("ul#tab_container > li").eq(2).show();
	        $("ul#navi_containTab > li").eq(2).css("background-color","#C4C4C4");
		} else if(index==5 || index==6) {
			$("ul#navi_containTab > li").eq(3).show();
			$("ul#navi_containTab > li").eq(4).show();
			$("ul#tab_container > li").eq(3).show();
	        $("ul#navi_containTab > li").eq(3).css("background-color","#C4C4C4");
		} else if(index==7) {
			$("ul#navi_containTab > li").eq(5).show();
			$("ul#tab_container > li").eq(5).show();
	        $("ul#navi_containTab > li").eq(5).css("background-color","#C4C4C4");
		}
	}
	
	function openPopup(name, h, w) {
		if(document.getElementById("docId").value == 0 || document.getElementById("checkPeriod").value == 0){
			alert("กรุณากรอกข้อมูลการตรวจแปลงให้เรียบร้อย");
			menuIndex = 0;
		} else {
			$("#"+name+"Popup").dialog({
				position:{
					my:'center center',
					at:'center center'
				},
				height: h,
				width: w,
				modal: true,
				close: function() {
					  hideCalendar();
				}
			});
		}
	}
    
    function generateRow(noRow, name, data, hide) {
    	var addRow = "";
    	var nameLower = name.substring(0, 1).toLowerCase()+name.substring(1);
    	var initials = nameLower.substring(0, 3);
    	if(noRow%2==1) {
    		addRow += "<tr id=\""+initials+noRow+"\" class=\"gridRowEven\">";
    	} else {
    		addRow += "<tr id=\""+initials+noRow+"\" class=\"gridRowOdd\">";
    	}

    	if(data['Id']==0) { data['Id'] = ""; }
    	var addHidden = "";
		addRow += "<td><input type=\"checkbox\" name=\""+initials+"Del\" value=\""+noRow+"\">";
    	addRow += "<input type=\"hidden\" id=\""+nameLower+"Id"+noRow+"\" name=\""+nameLower+"Id\" value=\""+data['Id']+"\" />";
		for (var key2 in hide) {
			if (hide.hasOwnProperty(key2)) {
				var hText = (""+hide[key2]).split("$");
				addRow += "<input type=\"hidden\" id=\""+key2+noRow+"\" name=\""+key2+"\" value=\""+hText[0]+"\"/>";
			}
		}
    	for (var key in data) {
    		if (data.hasOwnProperty(key)) {
    			if(key != "Id") {
    				addHidden += "<td id=\"txt"+key+noRow+"\" onclick=\"edit"+name+"("+noRow+");\" ";
    				
    				var vText = (""+data[key]).split("$");
    				var align = "center";
    				var txt = vText[0];
    				var val = vText[0];
    				if(vText.length > 1) {
    					val = vText[0];
    					txt = vText[1];
    				}
    				var sText = txt.split("|");
    				if(sText.length > 1) {
    					if(vText.length == 1)
    						val = sText[0];
    					txt = sText[0];
    					align = sText[1];
    				}
    				addHidden += "style=\"cursor:pointer;text-align:"+align+";\">"+txt+"</td>";
    				var prop = key.substring(0, 1).toLowerCase()+key.substring(1);
    				addRow += "<input type=\"hidden\" id=\""+prop+noRow+"\" name=\""+prop+"\" value=\""+val+"\"/>";
    			}
    		}
    	}
    	addRow += "</td>"+addHidden;
    	addRow += "<td class=\"last-child\" onclick=\"edit"+name+"("+noRow+");\"><a class=\"btn-edit\"></a></td></tr>";
    	
    	return addRow;
    }
    
    function addCoordinates() {
        $("#inCoordinatesId").val("0");
        $("#inLatitude").val("");
        $("#inLongitude").val("");
        openPopup("Coordinates", 270, 600);
    }
    
    function editCoordinates(index) {
        $("#inCoordinatesId").val(index);
        $("#inLatitude").val(document.getElementsByName('latitude')[index-1].value);
        $("#inLongitude").val(document.getElementsByName('longitude')[index-1].value);
        openPopup("Coordinates", 270, 600);
    }
    
    function saveCoordinates() {
    	var x = document.getElementsByName("latitude");
    	var y = document.getElementsByName("longitude");
    	var duplicate = false;
    	var data = [];
    	var hData = [];
		var latitude = $("#inLatitude").val().trim();
		var longitude = $("#inLongitude").val().trim();
    	if(latitude!="" && longitude!="") {
    		var id = $("#inCoordinatesId").val();
    		for(var i=0;i < x.length;i++) {
    			if(id==0 && latitude==x[i].value && longitude==y[i].value) {
    				duplicate = true;
    				break;
    			}
    		}
    		if(!duplicate) {
        		if(id!=0) {
        			$("#latitude"+id).val(latitude);
        			$("#longitude"+id).val(longitude);
        			$("#txtLatitude"+id).html(latitude);
        			$("#txtLongitude"+id).html(longitude);
        		} else {
        			var size = document.getElementsByName('cooDel').length;
        			countRow = size+1;
        			data["Id"] = 0;
        			data["Latitude"] = latitude+"|left";
        			data["Longitude"] = longitude+"|left";
        			var addTable = generateRow(countRow, "Coordinates", data, hData);
        			$("#Coordinates tbody:first").append(addTable);
        			if(countRow==1) { $("#noData").remove(); }
        		}
        		$("#CoordinatesPopup").dialog("close");
    		} else {
    			alert("มีพิกัดนี้อยู่แล้ว");
    		}
    	} else {
    		if($("#inLatitude").val().trim()=="") { alert("กรุณาใส่ตำแหน่ง X"); }
    		else if($("#inLongitude").val().trim()=="") { alert("กรุณาใส่ตำแหน่ง Y"); }
    	}
    }
    
    function delCoordinates(rowNo) {
    	var delCoo = document.getElementsByName('cooDel');
    	var coordinatesId = document.getElementsByName('coordinatesId');
    	var latitude = document.getElementsByName('latitude');
    	var longitude = document.getElementsByName('longitude');
    	if(rowNo!="") {
			if(coordinatesId[rowNo-1].value!=0) {
    		$(".gridPageOfPage").append("<input type=\"hidden\" name=\"delCooId\" value=\""+coordinatesId[rowNo-1].value+"\" />");
    	}}
    	if(rowNo>0) { $("#coo"+rowNo).remove(); }
    	var addRow = "";
    	countRow = delCoo.length;
    	for(var i=1;i<=delCoo.length;i++) {
    		var data = {"Id":coordinatesId[i-1].value,"Latitude":latitude[i-1].value,"Longitude":longitude[i-1].value};
    		addRow += generateRow(i, "Coordinates", data);
    	}
    	$("#Coordinates tbody:first tr").remove();
    	if(countRow==0) {
    		$("#Coordinates tbody:first").append("<tr id=\"noData\" class=\"gridRowEven\"><td class=\"last-child\" colspan=\"4\">No records to display!</td></tr>");
    		document.getElementsByName('checkAll_cooDel')[0].checked = false;
    	} else {
    		$("#Coordinates tbody:first").append(addRow);
    	}
    }
    
    function delCoordinates2() {
    	var delCoo = document.getElementsByName('cooDel');
    	var coordinatesId = document.getElementsByName('coordinatesId');
    	var delItem = [];
    	var num = 0;
		for(var i = 0 ; i < delCoo.length ; i++){
			if(delCoo[i].checked){
    			if(coordinatesId[i].value!=0) {
    				$(".gridPageOfPage").append("<input type=\"hidden\" name=\"delCooId\" value=\""+coordinatesId[i].value+"\" />");
    			}
				delItem[num] = "#coo"+(i+1);
				num++;
			}
		}
		for(var j = 0 ; j < delItem.length ; j++){
			$(delItem[j]).remove();
		}
		delCoordinates("");
    }
    
    function addPrepareSoil() {
        $("#inPrepareSoilId").val("0");
        $("#inPrepareSoilDate").val("");
        $("#inPrepareSoilOperate").val("");
        $("#inPrepareSoilOperateOther").val("");
        $("#inPrepareSoilResult").val("");
        $("#inPrepareSoilRemark").val("");
        $("#inPrepareSoilCheckBy").val("");
		clearChecked(document.getElementsByName("inPrepareSoilType"));
		clearChecked(document.getElementsByName("inPrepareSoilWater"));
        openPopup("PrepareSoil", 710, 600);
    }
    
    function editPrepareSoil(index) {
        $("#inPrepareSoilId").val(index);
        $("#inPrepareSoilDate").val(document.getElementsByName('prepareSoilDate')[index-1].value);
        $("#inPrepareSoilOperate").val(document.getElementsByName('prepareSoilOperate')[index-1].value);
        $("#inPrepareSoilOperateOther").val(document.getElementsByName('prepareSoilOperateOther')[index-1].value);
        $("#inPrepareSoilResult").val(document.getElementsByName('prepareSoilResult')[index-1].value);
        $("#inPrepareSoilRemark").val(document.getElementsByName('prepareSoilRemark')[index-1].value);
        $("#inPrepareSoilCheckBy").val(document.getElementsByName('prepareSoilCheckBy')[index-1].value);
        var preType = document.getElementsByName('prepareSoilType')[index-1].value.split(",");
        var preWater = document.getElementsByName('prepareSoilWater')[index-1].value.split(",");
		var inputType = document.getElementsByName("inPrepareSoilType");
		var inputWater = document.getElementsByName("inPrepareSoilWater");
		setMultiChecked(inputType, preType);
		setMultiChecked(inputWater, preWater);
        openPopup("PrepareSoil", 710, 600);
    }
    
    function savePrepareSoil() {
    	var preDate = document.getElementsByName("prepareSoilDate");
    	var preSoil = document.getElementsByName("prepareSoilOperate");
    	var duplicate = false;
    	var data = [];
    	var hData = [];

		var prepareSoilDate = $("#inPrepareSoilDate").val().trim();
		var prepareSoilOperate = $("#inPrepareSoilOperate").val().trim();
		var prepareSoilResult = $("#inPrepareSoilResult").val().trim();
		var prepareSoilCheckBy = $("#inPrepareSoilCheckBy").val().trim();
    	if(prepareSoilDate!="" && prepareSoilOperate!=0 && prepareSoilResult!=0 && prepareSoilCheckBy!="") {
    		var id = $("#inPrepareSoilId").val();
    		var prepareSoilOperateOther = $("#inPrepareSoilOperateOther").val().trim();
    		var prepareSoilRemark = $("#inPrepareSoilRemark").val().trim();
    		var inputType =  document.getElementsByName("inPrepareSoilType");
    		var inputWater = document.getElementsByName("inPrepareSoilWater");
    		var prepareSoilType = getMultiChecked(inputType);
    		var prepareSoilWater = getMultiChecked(inputWater);
    		
    		for(var i=0;i < preSoil.length;i++) {
    			if(id==0 && prepareSoilDate==preDate[i].value && prepareSoilOperate==preSoil[i].value) {
    				duplicate = true;
    				break;
    			}
    		}
    		if(!duplicate) {
        		if(id!=0) {
        			$("#prepareSoilDate"+id).val(prepareSoilDate);
        			$("#prepareSoilOperate"+id).val(prepareSoilOperate);
        			$("#prepareSoilOperateOther"+id).val(prepareSoilOperateOther);
        			$("#prepareSoilResult"+id).val(prepareSoilResult);
        			$("#prepareSoilRemark"+id).val(prepareSoilRemark);
        			$("#prepareSoilCheckBy"+id).val(prepareSoilCheckBy);
        			$("#prepareSoilType"+id).val(prepareSoilType);
        			$("#prepareSoilWater"+id).val(prepareSoilWater);
        			$("#txtPrepareSoilDate"+id).html(prepareSoilDate);
        			$("#txtPrepareSoilOperate"+id).html(prepareSoilOperate);
        			$("#txtPrepareSoilCheckBy"+id).html(prepareSoilCheckBy);
        			$("#txtPrepareSoilResult"+id).html(prepareSoilResult);
        		} else {
        			var size = document.getElementsByName('preDel').length;
        			countRow = size+1;
        			data["Id"] = 0;
        			data["PrepareSoilDate"] = prepareSoilDate;
        			data["PrepareSoilOperate"] = prepareSoilOperate;
        			data["PrepareSoilCheckBy"] = prepareSoilCheckBy+"|left";
        			data["PrepareSoilResult"] = prepareSoilResult;
        			hData["prepareSoilType"] = prepareSoilType;
        			hData["prepareSoilWater"] = prepareSoilWater;
        			hData["prepareSoilOperateOther"] = prepareSoilOperateOther;
        			hData["prepareSoilRemark"] = prepareSoilRemark;
        			var addTable = generateRow(countRow, "PrepareSoil", data, hData);
        			$("#PrepareSoil tbody:first").append(addTable);
        			if(countRow==1) { $("#PrepareSoil #noData").remove(); }
        		}
        		$("#PrepareSoilPopup").dialog("close");
    		} else {
    			alert("มีข้อมูลนี้อยู่แล้ว");
    		}
    	} else {
    		if(prepareSoilDate=="") { alert("กรุณาใส่วันที่ปฏิบัติ"); }
    		else if(prepareSoilOperate==0) { alert("กรุณาใส่รายการปฏิบัติ"); }
    		else if(prepareSoilResult==0) { alert("กรุณาใส่ผลการปฏิบัติ"); }
    		else if(prepareSoilCheckBy=="") { alert("กรุณาใส่ผู้ตรวจการปฏิบัติ"); }
    	}
    }
    
    function delPrepareSoil() {
    	var del = document.getElementsByName('preDel');
    	var prepareSoilId = document.getElementsByName('prepareSoilId');
    	var delItem = [];
		for(var i = 0 ; i < del.length ; i++){
			if(del[i].checked){
    			if(prepareSoilId[i].value!=0) {
    				$("#PrepareSoil .gridPageOfPage").append("<input type=\"hidden\" name=\"delPreId\" value=\""+prepareSoilId[i].value+"\" />");
    			}
				delItem.push("#pre"+(i+1));
			}
		}
		for(var j = 0 ; j < delItem.length ; j++){
			$(delItem[j]).remove();
		}
		
    	var prepareSoilDate = document.getElementsByName("prepareSoilDate");
    	var prepareSoilType = document.getElementsByName("prepareSoilType");
    	var prepareSoilWater = document.getElementsByName("prepareSoilWater");
		var prepareSoilOperate = document.getElementsByName("prepareSoilOperate");
		var prepareSoilOperateOther = document.getElementsByName("prepareSoilOperateOther");
		var prepareSoilResult = document.getElementsByName("prepareSoilResult");
    	var prepareSoilRemark = document.getElementsByName("prepareSoilRemark");
		var prepareSoilCheckBy = document.getElementsByName("prepareSoilCheckBy");
    	
    	var addRow = "";
    	countRow = del.length;
    	for(var i=0;i<countRow;i++) {
    		//if(prepareSoilId[i].value=="") { prepareSoilId[i].value = 0; }
    		var data = {"Id":prepareSoilId[i].value,"PrepareSoilDate":prepareSoilDate[i].value,"PrepareSoilOperate":prepareSoilOperate[i].value,
    				"PrepareSoilCheckBy":prepareSoilCheckBy[i].value+"|left","PrepareSoilResult":prepareSoilResult[i].value};
    		var hData = {"prepareSoilType":prepareSoilType[i].value,"prepareSoilWater":prepareSoilWater[i].value,
    				"prepareSoilOperateOther":prepareSoilOperateOther[i].value,"prepareSoilRemark":prepareSoilRemark[i].value};
    		addRow += generateRow((i+1), "PrepareArea", data, hData);
    	}
    	$("#PrepareSoil tbody:first tr").remove();
    	if(countRow==null || countRow==0) {
    		$("#PrepareSoil tbody:first").append("<tr id=\"noData\" class=\"gridRowEven\"><td class=\"last-child\" colspan=\"6\">No records to display!</td></tr>");
    		document.getElementsByName('checkAll_preDel')[0].checked = false;
    	} else {
    		$("#PrepareSoil tbody:first").append(addRow);
    	}
    }
    
    function addPlant() {
        $("#inPlantId").val("0");
        $("#inPlantDate").val("");
        $("#inPlantResult").val("");
        $("#inPlantCheckBy").val("");
        $("#inPlantSource").val("");
        $("#inPlantUse").val("");
        $("#inPlantRemark").val("");
        $("#inPlantName").val("");
        $("#inPlantSowDate").val("");
        $("#inPlantThrowDate").val("");
		clearChecked(document.getElementsByName("inPlantMethod"));
		clearChecked(document.getElementsByName("inPlantType"));
        openPopup("Plant", 860, 600);
    }
    
    function editPlant(index) {
        $("#inPlantId").val(index);
		$("#inPlantDate").val(document.getElementsByName('plantDate')[index-1].value);
		$("#inPlantSource").val(document.getElementsByName('plantSource')[index-1].value);
		$("#inPlantUse").val(document.getElementsByName('plantUse')[index-1].value);
		$("#inPlantName").val(document.getElementsByName('plantName')[index-1].value);
		$("#inPlantSowDate").val(document.getElementsByName('plantSowDate')[index-1].value);
		$("#inPlantThrowDate").val(document.getElementsByName('plantThrowDate')[index-1].value);
		$("#inPlantResult").val(document.getElementsByName('plantResult')[index-1].value);
		$("#inPlantRemark").val(document.getElementsByName('plantRemark')[index-1].value);
		$("#inPlantCheckBy").val(document.getElementsByName('plantCheckBy')[index-1].value);
		var plantMethod = document.getElementsByName('plantMethod')[index-1].value.split(",");
        var plantType = document.getElementsByName('plantType')[index-1].value.split(",");
		var inputMethod =  document.getElementsByName("inPlantMethod");
		var inputType = document.getElementsByName("inPlantType");
		clearChecked(inputMethod);
		clearChecked(inputType);
		for(var i=0; inputMethod[i]; ++i){
			for(var j=0; j<plantMethod.length; j++){
				if(inputMethod[i].value == plantMethod[j]){
					inputMethod[i].checked = true;
					break;
				}
			}
		}
		for(var i=0; inputType[i]; ++i){
			for(var j=0; j<plantType.length; j++){
				if(inputType[i].value == plantType[j]){
					inputType[i].checked = true;
					break;
				}
			}
		}
        openPopup("Plant", 860, 600);
    }
    
    function savePlant() {
    	var plaDate = document.getElementsByName("plantDate");
    	var plaMethod = document.getElementsByName("plantMethod");
    	var duplicate = false;
    	var data = [];
    	var hData = [];

		var plantDate = $("#inPlantDate").val().trim();
		var plantResult = $("#inPlantResult").val().trim();
		var plantCheckBy = $("#inPlantCheckBy").val().trim();
    	var inputMethod =  document.getElementsByName("inPlantMethod");
		var plantMethod = [];
		for(var i=0; inputMethod[i]; ++i){
			if(inputMethod[i].checked){
				plantMethod.push(inputMethod[i].value);
			}
		}
    	if(plantDate!="" && plantMethod!="" && plantResult!=0 && plantCheckBy!="") {
    		var id = $("#inPlantId").val();
    		var plantSource = $("#inPlantSource").val().trim();
    		var plantUse = $("#inPlantUse").val().trim();
    		var plantRemark = $("#inPlantRemark").val().trim();
        	var plantName = $("#inPlantName").val().trim();
    		var plantSowDate = $("#inPlantSowDate").val().trim();
        	var plantThrowDate = $("#inPlantThrowDate").val().trim();
        	var inputType = document.getElementsByName("inPlantType");
    		var plantType = [];
    		for(var i=0; inputType[i]; ++i){
    			if(inputType[i].checked){
    				plantType.push(inputType[i].value);
    			}
    		}
    		for(var i=0;i < plaDate.length;i++) {
    			if(id==0 && plantDate==plaDate[i].value && plantMethod==plaMethod[i].value) {
    				duplicate = true;
    				break;
    			}
    		}
    		if(!duplicate) {
        		if(id!=0) {
        			$("#plantDate"+id).val(plantDate);
        			$("#plantMethod"+id).val(plantMethod);
        			$("#plantSource"+id).val(plantSource);
        			$("#plantUse"+id).val(plantUse);
        			$("#plantName"+id).val(plantName);
        			$("#plantSowDate"+id).val(plantSowDate);
        			$("#plantThrowDate"+id).val(plantThrowDate);
        			$("#plantType"+id).val(plantType);
        			$("#plantResult"+id).val(plantResult);
        			$("#plantRemark"+id).val(plantRemark);
        			$("#plantCheckBy"+id).val(plantCheckBy);
        			$("#txtPlantDate"+id).html(plantDate);
        			$("#txtPlantMethod"+id).html(""+plantMethod);
        			$("#txtPlantCheckBy"+id).html(plantCheckBy);
        			$("#txtPlantResult"+id).html(plantResult);
        		} else {
        			var size = document.getElementsByName('plaDel').length;
        			countRow = size+1;
        			data["Id"] = 0;
        			data["PlantDate"] = plantDate;
        			data["PlantMethod"] = plantMethod;
        			data["PlantCheckBy"] = plantCheckBy+"|left";
        			data["PlantResult"] = plantResult;
        			hData["plantSource"] = plantSource;
        			hData["plantUse"] = plantUse;
        			hData["plantRemark"] = plantRemark;
        			hData["plantName"] = plantName;
        			hData["plantSowDate"] = plantSowDate;
        			hData["plantThrowDate"] = plantThrowDate;
        			hData["plantType"] = plantType;
        			var addTable = generateRow(countRow, "Plant", data, hData);
        			$("#Plant tbody:first").append(addTable);
        			if(countRow==1) { $("#Plant #noData").remove(); }
        		}
        		$("#PlantPopup").dialog("close");
    		} else {
    			alert("มีข้อมูลนี้อยู่แล้ว");
    		}
    	} else {
    		if(plantDate=="") { alert("กรุณาใส่วันที่ปฏิบัติ"); }
    		else if(plantMethod==0) { alert("กรุณาใส่รายการปฏิบัติ"); }
    		else if(plantResult==0) { alert("กรุณาใส่ผลการปฏิบัติ"); }
    		else if(plantCheckBy=="") { alert("กรุณาใส่ผู้ตรวจการปฏิบัติ"); }
    	}
    }
    
    function delPlant() {
    	var del = document.getElementsByName('plaDel');
    	var plantId = document.getElementsByName('plantId');
    	var delItem = [];
		for(var i = 0 ; i < del.length ; i++){
			if(del[i].checked){
    			if(plantId[i].value!=0) {
    				$("#Plant .gridPageOfPage").append("<input type=\"hidden\" name=\"delPlaId\" value=\""+plantId[i].value+"\" />");
    			}
				delItem.push("#pla"+(i+1));
			}
		}
		for(var j = 0 ; j < delItem.length ; j++){
			$(delItem[j]).remove();
		}
		
    	var plantDate = document.getElementsByName("plantDate");
		var plantMethod = document.getElementsByName("plantMethod");
    	var plantType = document.getElementsByName("plantType");
    	var plantName = document.getElementsByName("plantName");
		var plantSource = document.getElementsByName("plantSource");
		var plantSowDate = document.getElementsByName("plantSowDate");
    	var plantThrowDate = document.getElementsByName("plantThrowDate");
		var plantUse = document.getElementsByName("plantUse");
		var plantResult = document.getElementsByName("plantResult");
		var plantRemark = document.getElementsByName("plantRemark");
		var plantCheckBy = document.getElementsByName("plantCheckBy");
    	
    	var addRow = "";
    	countRow = del.length;
    	for(var i=0;i<countRow;i++) {
    		if(plantId[i].value=="") { plantId[i].value = 0; }
    		var data = {"Id":plantId[i].value,"PlantDate":plantDate[i].value,"PlantMethod":plantMethod[i].value,
    				"PlantCheckBy":plantCheckBy[i].value+"|left","PlantResult":plantResult[i].value};
    		var hData = {"plantType":plantType[i].value,"plantName":plantName[i].value,"plantSource":plantSource[i].value,
    				"plantSowDate":plantSowDate[i].value,"plantThrowDate":plantThrowDate[i].value,"plantUse":plantUse[i].value,
    				"plantRemark":plantRemark[i].value};
    		addRow += generateRow((i+1), "Plant", data, hData);
    	}
    	$("#Plant tbody:first tr").remove();
    	if(countRow==null || countRow==0) {
    		$("#Plant tbody:first").append("<tr id=\"noData\" class=\"gridRowEven\"><td class=\"last-child\" colspan=\"6\">No records to display!</td></tr>");
    		document.getElementsByName('checkAll_plaDel')[0].checked = false;
    	} else {
    		$("#Plant tbody:first").append(addRow);
    	}
    }
    
    function addManure() {
        $("#inManureId").val("0");
        $("#inManureDate").val("");
        $("#inManureType").val("0");
        $("#inManureOther").val("");
        selectOther('inManureType','inManureOther');
        $("#inManureName").val("");
        $("#inManureFormula").val("");
        $("#inManureBuyDate").val("");
        $("#inManureSourceBuy").val("");
        $("#inManureUseRate").val("");
        $("#inManureTotalUse").val("");
        $("#inManureResult").val("0");
        $("#inManureRemark").val("");
        $("#inManureCheckBy").val("");
        clearChecked(document.getElementsByName("inManureStatus"));
        openPopup("Manure", 780, 600);
    }
    
    function editManure(index) {
        $("#inManureId").val(index);
        $("#inManureDate").val(document.getElementsByName('manureDate')[index-1].value);
        $("#inManureType").val(document.getElementsByName('manureType')[index-1].value);
        selectOther('inManureType','inManureOther');
        $("#inManureOther").val(document.getElementsByName('manureOther')[index-1].value);
        $("#inManureName").val(document.getElementsByName('manureName')[index-1].value);
        $("#inManureFormula").val(document.getElementsByName('manureFormula')[index-1].value);
        $("#inManureBuyDate").val(document.getElementsByName('manureBuyDate')[index-1].value);
        $("#inManureSourceBuy").val(document.getElementsByName('manureSourceBuy')[index-1].value);
        $("#inManureUseRate").val(document.getElementsByName('manureUseRate')[index-1].value);
        $("#inManureTotalUse").val(document.getElementsByName('manureTotalUse')[index-1].value);
        $("#inManureResult").val(document.getElementsByName('manureResult')[index-1].value);
        $("#inManureRemark").val(document.getElementsByName('manureRemark')[index-1].value);
        $("#inManureCheckBy").val(document.getElementsByName('manureCheckBy')[index-1].value);
		var manureStatus = document.getElementsByName('manureStatus')[index-1].value;
		var inputStatus = document.getElementsByName("inManureStatus");
		setChecked(inputStatus, manureStatus);
        openPopup("Manure", 780, 600);
    }
    
    function saveManure() {
    	var manDate = document.getElementsByName("manureDate");
    	var manType = document.getElementsByName("manureType");
    	var duplicate = false;
    	var data = [];
    	var hData = [];

		var manureDate = $("#inManureDate").val().trim();
		var manureResult = $("#inManureResult").val().trim();
		var manureCheckBy = $("#inManureCheckBy").val().trim();
    	var inputType = document.getElementById("inManureType");
    	var manureType = inputType.options[inputType.selectedIndex].value;
    	var manureTypeTxt = inputType.options[inputType.selectedIndex].text;
    	if(manureDate!="" && manureType!="" && manureResult!=0 && manureCheckBy!="") {
    		var id = $("#inManureId").val();
    		var manureOther = $("#inManureOther").val().trim();
    		var manureName = $("#inManureName").val().trim();
    		var manureFormula = $("#inManureFormula").val().trim();
    		var manureBuyDate = $("#inManureBuyDate").val().trim();
        	var manureSourceBuy = $("#inManureSourceBuy").val().trim();
    		var manureUseRate = $("#inManureUseRate").val().trim();
        	var manureTotalUse = $("#inManureTotalUse").val().trim();
        	var manureRemark = $("#inManureRemark").val().trim();
        	var inputStatus =  document.getElementsByName("inManureStatus");
    		var manureStatus = "";
    		for(var i=0; inputStatus[i]; ++i){
    			if(inputStatus[i].checked){
    				manureStatus = inputStatus[i].value;
    			}
    		}
    		for(var i=0;i < manDate.length;i++) {
    			if(id==0 && manureDate==manDate[i].value && manureType==manType[i].value) {
    				duplicate = true;
    				break;
    			}
    		}
    		if(!duplicate) {
        		if(id!=0) {
        			$("#manureDate"+id).val(manureDate);
        			$("#manureType"+id).val(manureType);
        			$("#manureOther"+id).val(manureOther);
        			$("#manureStatus"+id).val(manureStatus);
        			$("#manureName"+id).val(manureName);
        			$("#manureFormula"+id).val(manureFormula);
        			$("#manureBuyDate"+id).val(manureBuyDate);
        			$("#manureSourceBuy"+id).val(manureSourceBuy);
        			$("#manureUseRate"+id).val(manureUseRate);
        			$("#manureTotalUse"+id).val(manureTotalUse);
        			$("#manureResult"+id).val(manureResult);
        			$("#manureRemark"+id).val(manureRemark);
        			$("#manureCheckBy"+id).val(manureCheckBy);
        			$("#txtManureDate"+id).html(manureDate);
        			$("#txtManureType"+id).html(manureTypeTxt);
        			$("#txtManureCheckBy"+id).html(manureCheckBy);
        			$("#txtManureResult"+id).html(manureResult);
        		} else {
        			var size = document.getElementsByName('manDel').length;
        			countRow = size+1;
        			data["Id"] = 0;
        			data["ManureDate"] = manureDate;
        			data["ManureType"] = manureType+"$"+manureTypeTxt;
        			data["ManureCheckBy"] = manureCheckBy+"|left";
        			data["ManureResult"] = manureResult;
        			hData["manureOther"] = manureOther;
        			hData["manureStatus"] = manureStatus;
        			hData["manureName"] = manureName;
        			hData["manureFormula"] = manureFormula;
        			hData["manureBuyDate"] = manureBuyDate;
        			hData["manureSourceBuy"] = manureSourceBuy;
        			hData["manureUseRate"] = manureUseRate;
        			hData["manureTotalUse"] = manureTotalUse;
        			hData["manureRemark"] = manureRemark;
        			var addTable = generateRow(countRow, "Manure", data, hData);
        			$("#Manure tbody:first").append(addTable);
        			if(countRow==1) { $("#Manure #noData").remove(); }
        		}
        		$("#ManurePopup").dialog("close");
    		} else {
    			alert("มีข้อมูลนี้อยู่แล้ว");
    		}
    	} else {
    		if(manureDate=="") { alert("กรุณาใส่วันที่ปฏิบัติ"); }
    		else if(manureType==0) { alert("กรุณาใส่ชนิดปุ๋ยที่ใช้"); }
    		else if(manureResult==0) { alert("กรุณาใส่ผลการปฏิบัติ"); }
    		else if(manureCheckBy=="") { alert("กรุณาใส่ผู้ตรวจการปฏิบัติ"); }
    	}
    }
    
    function delManure() {
    	var del = document.getElementsByName('manDel');
    	var manureId = document.getElementsByName('manureId');
    	var delItem = [];
		for(var i = 0 ; i < del.length ; i++){
			if(del[i].checked){
    			if(manureId[i].value!=0) {
    				$("#Manure .gridPageOfPage").append("<input type=\"hidden\" name=\"delManId\" value=\""+manureId[i].value+"\" />");
    			}
				delItem.push("#man"+(i+1));
			}
		}
		for(var j = 0 ; j < delItem.length ; j++){
			$(delItem[j]).remove();
		}
		
    	var manureDate = document.getElementsByName("manureDate");
		var manureType = document.getElementsByName("manureType");
    	var manureOther = document.getElementsByName("manureOther");
    	var manureStatus = document.getElementsByName("manureStatus");
    	var manureName = document.getElementsByName("manureName");
    	var manureFormula = document.getElementsByName("manureFormula");
		var manureBuyDate = document.getElementsByName("manureBuyDate");
		var manureSourceBuy = document.getElementsByName("manureSourceBuy");
    	var manureUseRate = document.getElementsByName("manureUseRate");
		var manureTotalUse = document.getElementsByName("manureTotalUse");
		var manureResult = document.getElementsByName("manureResult");
		var manureRemark = document.getElementsByName("manureRemark");
		var manureCheckBy = document.getElementsByName("manureCheckBy");
    	
    	var addRow = "";
    	countRow = del.length;
    	for(var i=0;i<countRow;i++) {
    		var manureTypeTxt = document.getElementById("txtManureType"+del[i].value).innerHTML;
    		if(manureId[i].value=="") { manureId[i].value = 0; }
    		var data = {"Id":manureId[i].value,"ManureDate":manureDate[i].value,"ManureType":manureType[i].value+"$"+manureTypeTxt,
    				"ManureCheckBy":manureCheckBy[i].value+"|left","ManureResult":manureResult[i].value};
    		var hData = {"manureOther":manureOther[i].value,"manureStatus":manureStatus[i].value,"manureName":manureName[i].value,
    				"manureFormula":manureFormula[i].value,"manureBuyDate":manureBuyDate[i].value,"manureSourceBuy":manureSourceBuy[i].value,
    				"manureUseRate":manureUseRate[i].value,"manureTotalUse":manureTotalUse[i].value,"manureRemark":manureRemark[i].value};
    		addRow += generateRow((i+1), "Manure", data, hData);
    	}
    	$("#Manure tbody:first tr").remove();
    	if(countRow==null || countRow==0) {
    		$("#Manure tbody:first").append("<tr id=\"noData\" class=\"gridRowEven\"><td class=\"last-child\" colspan=\"6\">No records to display!</td></tr>");
    		document.getElementsByName('checkAll_manDel')[0].checked = false;
    	} else {
    		$("#Manure tbody:first").append(addRow);
    	}
    }
    
    function addMixed() {
        $("#inMixedId").val("0");
        $("#inMixedDate").val("");
        $("#inMixedBreedTypeId").val("0");
        getMixedBreedTypeInfo('inMixedBreedTypeId', 'inMixedChildId');
        $("#inMixedChildId").val("0");
        $("#inMixedEliminateId").val("0");
        $("#inMixedEliminateOther").val("");
        selectOther('inMixedEliminateId','inMixedEliminateOther');
        $("#inMixedResult").val("0");
        $("#inMixedRemark").val("");
        $("#inMixedCheckBy").val("");
        openPopup("Mixed", 660, 600);
    }
    
    function editMixed(index) {
        $("#inMixedId").val(index);
        $("#inMixedDate").val(document.getElementsByName('mixedDate')[index-1].value);
        $("#inMixedBreedTypeId").val(document.getElementsByName('mixedBreedType')[index-1].value);
        getMixedBreedTypeInfo('inMixedBreedTypeId', 'inMixedChildId');
        $("#inMixedChildId").val(document.getElementsByName('mixedChildId')[index-1].value);
        $("#inMixedEliminateId").val(document.getElementsByName('mixedEliminateId')[index-1].value);
        selectOther('inMixedEliminateId','inMixedEliminateOther');
        $("#inMixedEliminateOther").val(document.getElementsByName('mixedEliminateOther')[index-1].value);
        $("#inMixedResult").val(document.getElementsByName('mixedResult')[index-1].value);
        $("#inMixedRemark").val(document.getElementsByName('mixedRemark')[index-1].value);
        $("#inMixedCheckBy").val(document.getElementsByName('mixedCheckBy')[index-1].value);
		openPopup("Mixed", 660, 600);
    }
    
    function saveMixed() {
    	var mixDate = document.getElementsByName("mixedDate");
    	var mixType = document.getElementsByName("mixedBreedType");
    	var mixEliminate = document.getElementsByName("mixedEliminateId");
    	var duplicate = false;
    	var data = [];
    	var hData = [];

		var mixedDate = $("#inMixedDate").val().trim();
    	var inputType = document.getElementById("inMixedBreedTypeId");
    	var mixedBreedTypeId = inputType.options[inputType.selectedIndex].value;
    	var mixedBreedTypeTxt = inputType.options[inputType.selectedIndex].text;
    	var inputChild = document.getElementById("inMixedChildId");
    	var mixedChildId = inputChild.options[inputChild.selectedIndex].value;
    	var mixedChildTxt = inputChild.options[inputChild.selectedIndex].text;
    	var inputElim = document.getElementById("inMixedEliminateId");
    	var mixedEliminateId = inputElim.options[inputElim.selectedIndex].value;
		var mixedResult = $("#inMixedResult").val().trim();
		var mixedCheckBy = $("#inMixedCheckBy").val().trim();
    	if(mixedDate!="" && mixedBreedTypeId!=0 && mixedChildId!=0 && mixedEliminateId!=0 && mixedResult!=0 && mixedCheckBy!="") {
    		var id = $("#inMixedId").val();
    		var mixedEliminateOther = $("#inMixedEliminateOther").val().trim();
        	var mixedRemark = $("#inMixedRemark").val().trim();
    		for(var i=0;i < mixDate.length;i++) {
    			if(id==0 && mixedDate==mixDate[i].value && mixedBreedTypeId==mixType[i].value && mixedEliminateId==mixEliminate[i].value) {
    				duplicate = true;
    				break;
    			}
    		}
    		if(!duplicate) {
        		if(id!=0) {
        			$("#mixedDate"+id).val(mixedDate);
        			$("#mixedBreedType"+id).val(mixedBreedTypeId);
        			$("#mixedChildId"+id).val(mixedChildId);
        			$("#mixedEliminateId"+id).val(mixedEliminateId);
        			$("#mixedEliminateOther"+id).val(mixedEliminateOther);
        			$("#mixedResult"+id).val(mixedResult);
        			$("#mixedRemark"+id).val(mixedRemark);
        			$("#mixedCheckBy"+id).val(mixedCheckBy);
        			$("#txtMixedDate"+id).html(mixedDate);
        			$("#txtMixedBreedType"+id).html(mixedBreedTypeTxt+" / "+mixedChildTxt);
        			$("#txtMixedCheckBy"+id).html(mixedCheckBy);
        			$("#txtMixedResult"+id).html(mixedResult);
        		} else {
        			var size = document.getElementsByName('mixDel').length;
        			countRow = size+1;
        			data["Id"] = 0;
        			data["MixedDate"] = mixedDate;
        			data["MixedBreedType"] = mixedBreedTypeId+"$"+mixedBreedTypeTxt+" / "+mixedChildTxt+"|left";
        			data["MixedCheckBy"] = mixedCheckBy+"|left";
        			data["MixedResult"] = mixedResult;
        			hData["mixedChildId"] = mixedChildId;
        			hData["mixedEliminateId"] = mixedEliminateId;
        			hData["mixedEliminateOther"] = mixedEliminateOther;
        			hData["mixedRemark"] = mixedRemark;
        			var addTable = generateRow(countRow, "Mixed", data, hData);
        			$("#Mixed tbody:first").append(addTable);
        			if(countRow==1) { $("#Mixed #noData").remove(); }
        		}
        		$("#MixedPopup").dialog("close");
    		} else {
    			alert("มีข้อมูลนี้อยู่แล้ว");
    		}
    	} else {
    		if(mixedDate=="") { alert("กรุณาใส่วันที่ปฏิบัติ"); }
    		else if(mixedBreedTypeId==0) { alert("กรุณาใส่ประเภทลักษณะพันธุ์ปน"); }
    		else if(mixedChildId==0) { alert("กรุณาใส่ลักษณะพันธุ์ปน"); }
    		else if(mixedEliminateId==0) { alert("กรุณาใส่วิธีการกำจัดพันธุ์ปน"); }
    		else if(mixedResult==0) { alert("กรุณาใส่ผลการปฏิบัติ"); }
    		else if(mixedCheckBy=="") { alert("กรุณาใส่ผู้ตรวจการปฏิบัติ"); }
    	}
    }
    
    function delMixed() {
    	var del = document.getElementsByName('mixDel');
    	var mixedId = document.getElementsByName('mixedId');
    	var delItem = [];
		for(var i = 0 ; i < del.length ; i++){
			if(del[i].checked){
    			if(mixedId[i].value!=0) {
    				$("#Mixed .gridPageOfPage").append("<input type=\"hidden\" name=\"delMixId\" value=\""+mixedId[i].value+"\" />");
    			}
				delItem.push("#mix"+(i+1));
			}
		}
		for(var j = 0 ; j < delItem.length ; j++){
			$(delItem[j]).remove();
		}
		
    	var mixedDate = document.getElementsByName("mixedDate");
		var mixedBreedType = document.getElementsByName("mixedBreedType");
    	var mixedChildId = document.getElementsByName("mixedChildId");
    	var mixedEliminateId = document.getElementsByName("mixedEliminateId");
    	var mixedEliminateOther = document.getElementsByName("mixedEliminateOther");
    	var mixedResult = document.getElementsByName("mixedResult");
		var mixedRemark = document.getElementsByName("mixedRemark");
		var mixedCheckBy = document.getElementsByName("mixedCheckBy");
		
    	var addRow = "";
    	countRow = del.length;
    	for(var i=0;i<countRow;i++) {
    		var mixedBreedTypeTxt = document.getElementById("txtMixedBreedType"+del[i].value).innerHTML;
    		if(mixedId[i].value=="") { mixedId[i].value = 0; }
    		var data = {"Id":mixedId[i].value,"MixedDate":mixedDate[i].value,"MixedBreedType":mixedBreedType[i].value+"$"+mixedBreedTypeTxt+"|left",
    				"MixedCheckBy":mixedCheckBy[i].value+"|left","MixedResult":mixedResult[i].value};
    		var hData = {"mixedChildId":mixedChildId[i].value,"mixedEliminateId":mixedEliminateId[i].value,
    				"mixedEliminateOther":mixedEliminateOther[i].value,"mixedRemark":mixedRemark[i].value};
    		addRow += generateRow((i+1), "Mixed", data, hData);
    	}
    	$("#Mixed tbody:first tr").remove();
    	if(countRow==null || countRow==0) {
    		$("#Mixed tbody:first").append("<tr id=\"noData\" class=\"gridRowEven\"><td class=\"last-child\" colspan=\"6\">No records to display!</td></tr>");
    		document.getElementsByName('checkAll_mixDel')[0].checked = false;
    	} else {
    		$("#Mixed tbody:first").append(addRow);
    	}
    }
    
    function addDisease() {
        $("#inDiseaseId").val("0");
        $("#inDiseaseDate").val("");
        $("#inDiseaseCheckingId").val("0");
        getDiseaseChildInfo('inDiseaseCheckingId','inDiseaseChildId','inDiseaseTypeId');
        selectOther('inDiseaseChildId','inDiseaseOther');
        $("#inDiseaseOther").val("");
        $("#inDiseaseLevel").val("0");
        $("#inDiseaseTradingName").val("");
        $("#inDiseaseCommonName").val("");
        $("#inDiseaseDangerousName").val("");
        $("#inDiseaseManDate").val("");
        $("#inDiseaseExpDate").val("");
        $("#inDiseaseSourceBuy").val("");
        $("#inDiseaseUseDate").val("");
        $("#inDiseaseUseRate1").val("");
        $("#inDiseaseUseRate2").val("");
        $("#inDiseaseResult").val("");
        $("#inDiseaseRemark").val("");
        $("#inDiseaseCheckBy").val("");
        openPopup("Disease", 800, 600);
    }
    
    function editDisease(index) {
        $("#inDiseaseId").val(index);
        $("#inDiseaseDate").val(document.getElementsByName('diseaseDate')[index-1].value);
        $("#inDiseaseCheckingId").val(document.getElementsByName('diseaseChecking')[index-1].value);
        getDiseaseChildInfo('inDiseaseCheckingId','inDiseaseChildId','inDiseaseTypeId');
        $("#inDiseaseChildId").val(document.getElementsByName('diseaseChildId')[index-1].value);
        getDiseaseTypeInfo('inDiseaseCheckingId','inDiseaseChildId','inDiseaseTypeId');
        selectOther('inDiseaseChildId','inDiseaseOther');
        $("#inDiseaseTypeId").val(document.getElementsByName('diseaseTypeId')[index-1].value);
        $("#inDiseaseLevel").val(document.getElementsByName('diseaseLevel')[index-1].value);
        $("#inDiseaseOther").val(document.getElementsByName('diseaseOther')[index-1].value);
        $("#inDiseaseTradingName").val(document.getElementsByName('diseaseTradingName')[index-1].value);
        $("#inDiseaseCommonName").val(document.getElementsByName('diseaseCommonName')[index-1].value);
        $("#inDiseaseDangerousName").val(document.getElementsByName('diseaseDangerousName')[index-1].value);
        $("#inDiseaseManDate").val(document.getElementsByName('diseaseManDate')[index-1].value);
        $("#inDiseaseExpDate").val(document.getElementsByName('diseaseExpDate')[index-1].value);
        $("#inDiseaseSourceBuy").val(document.getElementsByName('diseaseSourceBuy')[index-1].value);
        $("#inDiseaseUseDate").val(document.getElementsByName('diseaseUseDate')[index-1].value);
        $("#inDiseaseUseRate1").val(document.getElementsByName('diseaseUseRate1')[index-1].value);
        $("#inDiseaseUseRate2").val(document.getElementsByName('diseaseUseRate2')[index-1].value);
        $("#inDiseaseResult").val(document.getElementsByName('diseaseResult')[index-1].value);
        $("#inDiseaseRemark").val(document.getElementsByName('diseaseRemark')[index-1].value);
        $("#inDiseaseCheckBy").val(document.getElementsByName('diseaseCheckBy')[index-1].value);
        openPopup("Disease", 800, 600);
    }
    
    function saveDisease() {
    	var disDate = document.getElementsByName("diseaseDate");
    	var disCheckingId = document.getElementsByName("diseaseCheckingId");
    	var disChildId = document.getElementsByName("diseaseChildId");
    	var disTypeId = document.getElementsByName("diseaseTypeId");
    	var duplicate = false;
    	var data = [];
    	var hData = [];

		var diseaseDate = $("#inDiseaseDate").val().trim();
    	var inputChecking = document.getElementById("inDiseaseCheckingId");
    	var diseaseCheckingId = inputChecking.options[inputChecking.selectedIndex].value;
    	var diseaseCheckingTxt = inputChecking.options[inputChecking.selectedIndex].text;
    	var inputChild = document.getElementById("inDiseaseChildId");
    	var diseaseChildId = inputChild.options[inputChild.selectedIndex].value;
    	var diseaseChildTxt = inputChild.options[inputChild.selectedIndex].text;
    	var inputType = document.getElementById("inDiseaseTypeId");
    	var diseaseTypeId = inputType.options[inputType.selectedIndex].value;
    	var inputLevel = document.getElementById("inDiseaseLevel");
    	var diseaseLevel = inputLevel.options[inputLevel.selectedIndex].value;
    	var diseaseLevelTxt = inputLevel.options[inputLevel.selectedIndex].text;
		var diseaseResult = $("#inDiseaseResult").val().trim();
		var diseaseCheckBy = $("#inDiseaseCheckBy").val().trim();
    	if(diseaseDate!="" && diseaseCheckingId!=0 && diseaseChildId!=0 && diseaseTypeId!=0 && diseaseLevel!=0 && diseaseResult!=0 && diseaseCheckBy!="") {
    		var id = $("#inDiseaseId").val();
    		var diseaseOther = $("#inDiseaseOther").val().trim();
    		var diseaseTradingName = $("#inDiseaseTradingName").val().trim();
    		var diseaseCommonName = $("#inDiseaseCommonName").val().trim();
    		var diseaseDangerousName = $("#inDiseaseDangerousName").val().trim();
    		var diseaseManDate = $("#inDiseaseManDate").val().trim();
    		var diseaseExpDate = $("#inDiseaseExpDate").val().trim();
    		var diseaseSourceBuy = $("#inDiseaseSourceBuy").val().trim();
    		var diseaseUseDate = $("#inDiseaseUseDate").val().trim();
    		var diseaseUseRate1 = $("#inDiseaseUseRate1").val().trim();
    		var diseaseUseRate2 = $("#inDiseaseUseRate2").val().trim();
        	var diseaseRemark = $("#inDiseaseRemark").val().trim();
    		for(var i=0;i < disDate.length;i++) {
    			if(id==0 && diseaseDate==disDate[i].value && diseaseCheckingId==disCheckingId[i].value
    					&& diseaseChildId==disChildId[i].value && diseaseTypeId==disTypeId[i].value) {
    				duplicate = true;
    				break;
    			}
    		}
    		if(!duplicate) {
        		if(id!=0) {
        			$("#diseaseDate"+id).val(diseaseDate);
        			$("#diseaseChecking"+id).val(diseaseCheckingId);
        			$("#diseaseChildId"+id).val(diseaseChildId);
        			$("#diseaseTypeId"+id).val(diseaseTypeId);
        			$("#diseaseOther"+id).val(diseaseOther);
        			$("#diseaseLevel"+id).val(diseaseLevel);
        			$("#diseaseTradingName"+id).val(diseaseTradingName);
        			$("#diseaseCommonName"+id).val(diseaseCommonName);
        			$("#diseaseDangerousName"+id).val(diseaseDangerousName);
        			$("#diseaseManDate"+id).val(diseaseManDate);
        			$("#diseaseExpDate"+id).val(diseaseExpDate);
        			$("#diseaseSourceBuy"+id).val(diseaseSourceBuy);
        			$("#diseaseUseDate"+id).val(diseaseUseDate);
        			$("#diseaseUseRate1"+id).val(diseaseUseRate1);
        			$("#diseaseUseRate2"+id).val(diseaseUseRate2);
        			$("#diseaseResult"+id).val(diseaseResult);
        			$("#diseaseRemark"+id).val(diseaseRemark);
        			$("#diseaseCheckBy"+id).val(diseaseCheckBy);
        			$("#txtDiseaseDate"+id).html(diseaseDate);
        			$("#txtDiseaseChecking"+id).html(diseaseCheckingTxt+" / "+diseaseChildTxt);
        			$("#txtDiseaseLevel"+id).html(diseaseLevelTxt);
        			$("#txtDiseaseCheckBy"+id).html(diseaseCheckBy);
        			$("#txtDiseaseResult"+id).html(diseaseResult);
        		} else {
        			var size = document.getElementsByName('disDel').length;
        			countRow = size+1;
        			data["Id"] = 0;
        			data["DiseaseDate"] = diseaseDate;
        			data["DiseaseChecking"] = diseaseCheckingId+"$"+diseaseCheckingTxt+" / "+diseaseChildTxt+"|left";
        			data["DiseaseLevel"] = diseaseLevel+"$"+diseaseLevelTxt;
        			data["DiseaseCheckBy"] = diseaseCheckBy+"|left";
        			data["DiseaseResult"] = diseaseResult;
        			hData["diseaseChildId"] = diseaseChildId;
        			hData["diseaseTypeId"] = diseaseTypeId;
        			hData["diseaseOther"] = diseaseOther;
        			hData["diseaseTradingName"] = diseaseTradingName;
        			hData["diseaseCommonName"] = diseaseCommonName;
        			hData["diseaseDangerousName"] = diseaseDangerousName;
        			hData["diseaseManDate"] = diseaseManDate;
        			hData["diseaseExpDate"] = diseaseExpDate;
        			hData["diseaseSourceBuy"] = diseaseSourceBuy;
        			hData["diseaseUseDate"] = diseaseUseDate;
        			hData["diseaseUseRate1"] = diseaseUseRate1;
        			hData["diseaseUseRate2"] = diseaseUseRate2;
        			hData["diseaseRemark"] = diseaseRemark;
        			var addTable = generateRow(countRow, "Disease", data, hData);
        			$("#Disease tbody:first").append(addTable);
        			if(countRow==1) { $("#Disease #noData").remove(); }
        		}
        		$("#DiseasePopup").dialog("close");
    		} else {
    			alert("มีข้อมูลนี้อยู่แล้ว");
    		}
    	} else {
    		if(diseaseDate=="") { alert("กรุณาใส่วันที่ปฏิบัติ"); }
    		else if(diseaseCheckingId==0) { alert("กรุณาใส่การตรวจ"); }
    		else if(diseaseChildId==0) { alert("กรุณาใส่รายการที่ระบาด"); }
    		else if(diseaseTypeId==0) { alert("กรุณาใส่ลักษณะอาการ"); }
    		else if(diseaseLevel==0) { alert("กรุณาใส่ความรุนแรง"); }
    		else if(diseaseResult==0) { alert("กรุณาใส่ผลการปฏิบัติ"); }
    		else if(diseaseCheckBy=="") { alert("กรุณาใส่ผู้ตรวจการปฏิบัติ"); }
    	}
    }
    
    function delDisease() {
    	var del = document.getElementsByName('disDel');
    	var diseaseId = document.getElementsByName('diseaseId');
    	var delItem = [];
		for(var i = 0 ; i < del.length ; i++){
			if(del[i].checked){
    			if(diseaseId[i].value!=0) {
    				$("#Disease .gridPageOfPage").append("<input type=\"hidden\" name=\"delDisId\" value=\""+diseaseId[i].value+"\" />");
    			}
				delItem.push("#dis"+(i+1));
			}
		}
		for(var j = 0 ; j < delItem.length ; j++){
			$(delItem[j]).remove();
		}
		
    	var diseaseDate = document.getElementsByName("diseaseDate");
		var diseaseCheckingId = document.getElementsByName("diseaseChecking");
    	var diseaseChildId = document.getElementsByName("diseaseChildId");
    	var diseaseTypeId = document.getElementsByName("diseaseTypeId");
    	var diseaseOther = document.getElementsByName("diseaseOther");
    	var diseaseLevel = document.getElementsByName("diseaseLevel");
		var diseaseTradingName = document.getElementsByName("diseaseTradingName");
		var diseaseCommonName = document.getElementsByName("diseaseCommonName");
		var diseaseDangerousName = document.getElementsByName("diseaseDangerousName");
		var diseaseManDate = document.getElementsByName("diseaseManDate");
		var diseaseExpDate = document.getElementsByName("diseaseExpDate");
		var diseaseSourceBuy = document.getElementsByName("diseaseSourceBuy");
		var diseaseUseDate = document.getElementsByName("diseaseUseDate");
		var diseaseUseRate1 = document.getElementsByName("diseaseUseRate1");
		var diseaseUseRate2 = document.getElementsByName("diseaseUseRate2");
		var diseaseResult = document.getElementsByName("diseaseResult");
		var diseaseRemark = document.getElementsByName("diseaseRemark");
		var diseaseCheckBy = document.getElementsByName("diseaseCheckBy");
		
    	var addRow = "";
    	countRow = del.length;
    	for(var i=0;i<countRow;i++) {
    		var diseaseCheckingTxt = document.getElementById("txtDiseaseChecking"+del[i].value).innerHTML;
    		var diseaseLevelTxt = document.getElementById("txtDiseaseLevel"+del[i].value).innerHTML;
    		if(diseaseId[i].value=="") { diseaseId[i].value = 0; }
    		var data = {"Id":diseaseId[i].value,"DiseaseDate":diseaseDate[i].value,"DiseaseChecking":diseaseCheckingId[i].value+"$"+diseaseCheckingTxt+"|left",
    				"DiseaseLevel":diseaseLevel[i].value+"$"+diseaseLevelTxt,"DiseaseCheckBy":diseaseCheckBy[i].value+"|left","DiseaseResult":diseaseResult[i].value};
    		var hData = {"diseaseChildId":diseaseChildId[i].value,"diseaseTypeId":diseaseTypeId[i].value,"diseaseOther":diseaseOther[i].value,
    				"diseaseTradingName":diseaseTradingName[i].value,"diseaseCommonName":diseaseCommonName[i].value,"diseaseDangerousName":diseaseDangerousName[i].value,
    				"diseaseManDate":diseaseManDate[i].value,"diseaseExpDate":diseaseExpDate[i].value,"diseaseSourceBuy":diseaseSourceBuy[i].value,
    				"diseaseUseDate":diseaseUseDate[i].value,"diseaseUseRate1":diseaseUseRate1[i].value,"diseaseUseRate2":diseaseUseRate2[i].value,
    				"diseaseResult":diseaseResult[i].value,"diseaseRemark":diseaseRemark[i].value,"diseaseCheckBy":diseaseCheckBy[i].value};
    		addRow += generateRow((i+1), "Disease", data, hData);
    	}
    	$("#Disease tbody:first tr").remove();
    	if(countRow==null || countRow==0) {
    		$("#Disease tbody:first").append("<tr id=\"noData\" class=\"gridRowEven\"><td class=\"last-child\" colspan=\"6\">No records to display!</td></tr>");
    		document.getElementsByName('checkAll_disDel')[0].checked = false;
    	} else {
    		$("#Disease tbody:first").append(addRow);
    	}
    }
    
    function addHarvest() {
        $("#inHarvestId").val("0");
        $("#inHarvestObservers").val("");
        $("#inHarvestObserversDate").val("");
        $("#inHarvestDainRemark").val("");
        $("#inHarvestDainDate").val("");
        $("#inHarvestQualityDate").val("");
        $("#inHarvestFarmStatusDate").val("");
        $("#inHarvesterDate").val("");
        $("#inHarvestCleaningDate").val("");
        $("#inHarvestPackagingDate").val("");
        $("#inHarvestMovingDate").val("");
        $("#inHarvestTotal").val("");
        $("#inHarvestKeep").val("");
        $("#inHarvestSell").val("");
        $("#inHarvestResult").val("");
        $("#inHarvestCheckBy").val("");
        $("#inHarvestSalePrice").val("");
        $("#inHarvestRemark").val("");
		clearChecked(document.getElementsByName("inHarvestDain"));
		clearChecked(document.getElementsByName("inHarvestQuality"));
		clearChecked(document.getElementsByName("inHarvestFarmStatus"));
		clearChecked(document.getElementsByName("inHarvester"));
		clearChecked(document.getElementsByName("inHarvestCleaning"));
		clearChecked(document.getElementsByName("inHarvestPackaging"));
		clearChecked(document.getElementsByName("inHarvestMoving"));
        openPopup("Harvest", 800, 960);
    }
    
    function editHarvest(index) {
        $("#inHarvestId").val(index);
        $("#inHarvestObservers").val(document.getElementsByName('harvestObservers')[index-1].value);
        $("#inHarvestObserversDate").val(document.getElementsByName('harvestObserversDate')[index-1].value);
        $("#inHarvestDainRemark").val(document.getElementsByName('harvestDainRemark')[index-1].value);
        $("#inHarvestDainDate").val(document.getElementsByName('harvestDainDate')[index-1].value);
        $("#inHarvestQualityDate").val(document.getElementsByName('harvestQualityDate')[index-1].value);
        $("#inHarvestFarmStatusDate").val(document.getElementsByName('harvestFarmStatusDate')[index-1].value);
        $("#inHarvesterDate").val(document.getElementsByName('harvesterDate')[index-1].value);
        $("#inHarvestCleaningDate").val(document.getElementsByName('harvestCleaningDate')[index-1].value);
        $("#inHarvestPackagingDate").val(document.getElementsByName('harvestPackagingDate')[index-1].value);
        $("#inHarvestMovingDate").val(document.getElementsByName('harvestMovingDate')[index-1].value);
        $("#inHarvestTotal").val(document.getElementsByName('harvestTotal')[index-1].value);
        $("#inHarvestKeep").val(document.getElementsByName('harvestKeep')[index-1].value);
        $("#inHarvestSell").val(document.getElementsByName('harvestSell')[index-1].value);
        $("#inHarvestResult").val(document.getElementsByName('harvestResult')[index-1].value);
        $("#inHarvestCheckBy").val(document.getElementsByName('harvestCheckBy')[index-1].value);
        $("#inHarvestSalePrice").val(document.getElementsByName('harvestSalePrice')[index-1].value);
        $("#inHarvestRemark").val(document.getElementsByName('harvestRemark')[index-1].value);
		var harvestDain = document.getElementsByName('harvestDain')[index-1].value;
		var inputDain = document.getElementsByName("inHarvestDain");
		setChecked(inputDain, harvestDain);
		var harvestQuality = document.getElementsByName('harvestQuality')[index-1].value;
		var inputQuality = document.getElementsByName("inHarvestQuality");
		setChecked(inputQuality, harvestQuality);
		var harvestFarmStatus = document.getElementsByName('harvestFarmStatus')[index-1].value;
		var inputStatus = document.getElementsByName("inHarvestFarmStatus");
		setChecked(inputStatus, harvestFarmStatus);
		var harvester = document.getElementsByName('harvester')[index-1].value;
		var inputHarvester = document.getElementsByName("inHarvester");
		setChecked(inputHarvester, harvester);
		var harvestCleaning = document.getElementsByName('harvestCleaning')[index-1].value;
		var inputCleaning = document.getElementsByName("inHarvestCleaning");
		setChecked(inputCleaning, harvestCleaning);
		var harvestPackaging = document.getElementsByName('harvestPackaging')[index-1].value;
		var inputPackaging = document.getElementsByName("inHarvestPackaging");
		setChecked(inputPackaging, harvestPackaging);
		var harvestMoving = document.getElementsByName('harvestMoving')[index-1].value;
		var inputMoving = document.getElementsByName("inHarvestMoving");
		setChecked(inputMoving, harvestMoving);
        openPopup("Harvest", 800, 960);
    }
    
    function saveHarvest() {
    	var duplicate = false;
    	var data = [];
    	var hData = [];

    	var inputQuality =  document.getElementsByName("inHarvestQuality");
		var harvestQualityDate = $("#inHarvestQualityDate").val().trim();
		var harvestQuality = getChecked(inputQuality);
		var harvestQualityId = harvestQuality.split("$")[0];
		var harvestQualityTxt = harvestQuality.split("$")[1];
		var harvestTotal = $("#inHarvestTotal").val().trim();
		var harvestKeep = $("#inHarvestKeep").val().trim(); // hide from screen
		var harvestResult = $("#inHarvestResult").val().trim();
		var harvestCheckBy = $("#inHarvestCheckBy").val().trim();
    	// if(harvestQualityDate!="" && harvestQuality!="" && harvestTotal!="" && harvestKeep!="" && harvestResult!=0 && harvestCheckBy!="") {
		if(harvestQualityDate!="" && harvestQuality!="" && harvestTotal!="" && harvestResult!=0 && harvestCheckBy!="") {
			var id = $("#inHarvestId").val();
    		var harvestObservers = $("#inHarvestObservers").val().trim();
    		var harvestObserversDate = $("#inHarvestObserversDate").val().trim();
    		var harvestDainRemark = $("#inHarvestDainRemark").val().trim();
    		var harvestDainDate = $("#inHarvestDainDate").val().trim();
    		var harvestFarmStatusDate = $("#inHarvestFarmStatusDate").val().trim();
    		var harvesterDate = $("#inHarvesterDate").val().trim();
    		var harvestCleaningDate = $("#inHarvestCleaningDate").val().trim();
    		var harvestPackagingDate = $("#inHarvestPackagingDate").val().trim();
        	var harvestMovingDate = $("#inHarvestMovingDate").val().trim();
    		var harvestSell = $("#inHarvestSell").val().trim();
        	var harvestSalePrice = $("#inHarvestSalePrice").val().trim();
        	var harvestRemark = $("#inHarvestRemark").val().trim();
        	var inputDain =  document.getElementsByName("inHarvestDain");
    		var harvestDain = getChecked(inputDain);
        	var inputStatus =  document.getElementsByName("inHarvestFarmStatus");
    		var harvestFarmStatus = getChecked(inputStatus);
        	var inputHarvester =  document.getElementsByName("inHarvester");
    		var harvester = getChecked(inputHarvester);
        	var inputCleaning =  document.getElementsByName("inHarvestCleaning");
    		var harvestCleaning = getChecked(inputCleaning);
        	var inputPackaging =  document.getElementsByName("inHarvestPackaging");
    		var harvestPackaging = getChecked(inputPackaging);
        	var inputMoving =  document.getElementsByName("inHarvestMoving");
    		var harvestMoving = getChecked(inputMoving);
    		
    		if(id!=0) {
    			$("#harvestObservers"+id).val(harvestObservers);
    			$("#harvestObserversDate"+id).val(harvestObserversDate);
    			$("#harvestDain"+id).val(harvestDain.split("$")[0]);
    			$("#harvestDainRemark"+id).val(harvestDainRemark);
    			$("#harvestDainDate"+id).val(harvestDainDate);
    			$("#harvestQuality"+id).val(harvestQualityId);
    			$("#harvestQualityDate"+id).val(harvestQualityDate);
    			$("#harvestFarmStatus"+id).val(harvestFarmStatus.split("$")[0]);
    			$("#harvestFarmStatusDate"+id).val(harvestFarmStatusDate);
    			$("#harvester"+id).val(harvester.split("$")[0]);
    			$("#harvesterDate"+id).val(harvesterDate);
    			$("#harvestCleaning"+id).val(harvestCleaning.split("$")[0]);
    			$("#harvestCleaningDate"+id).val(harvestCleaningDate);
    			$("#harvestPackaging"+id).val(harvestPackaging.split("$")[0]);
    			$("#harvestPackagingDate"+id).val(harvestPackagingDate);
    			$("#harvestMoving"+id).val(harvestMoving.split("$")[0]);
    			$("#harvestMovingDate"+id).val(harvestMovingDate);
    			$("#harvestTotal"+id).val(harvestTotal);
    			$("#harvestKeep"+id).val(harvestKeep);
    			$("#harvestSell"+id).val(harvestSell);
    			$("#harvestSalePrice"+id).val(harvestSalePrice);
    			$("#harvestResult"+id).val(harvestResult);
    			$("#harvestCheckBy"+id).val(harvestCheckBy);
    			$("#harvestRemark"+id).val(harvestRemark);
    			$("#txtHarvestQualityDate"+id).html(harvestQualityDate);
    			$("#txtHarvestQuality"+id).html(harvestQualityTxt);
    			$("#txtHarvestTotal"+id).html(harvestTotal);
    			$("#txtHarvestKeep"+id).html(harvestKeep);
    			$("#txtHarvestCheckBy"+id).html(harvestCheckBy);
    			$("#txtHarvestResult"+id).html(harvestResult);
    		} else {
    			var size = document.getElementsByName('harDel').length;
    			countRow = size+1;
    			data["Id"] = 0;
    			data["HarvestQualityDate"] = harvestQualityDate;
    			data["HarvestQuality"] = harvestQuality;
    			data["HarvestTotal"] = harvestTotal;
    			data["HarvestKeep"] = harvestKeep;
    			data["HarvestCheckBy"] = harvestCheckBy+"|left";
    			data["HarvestResult"] = harvestResult;
    			hData["harvestObservers"] = harvestObservers;
    			hData["harvestObserversDate"] = harvestObserversDate;
    			hData["harvestDain"] = harvestDain;
    			hData["harvestDainRemark"] = harvestDainRemark;
    			hData["harvestDainDate"] = harvestDainDate;
    			hData["harvestFarmStatus"] = harvestFarmStatus;
    			hData["harvestFarmStatusDate"] = harvestFarmStatusDate;
    			hData["harvester"] = harvester;
    			hData["harvesterDate"] = harvesterDate;
    			hData["harvestCleaning"] = harvestCleaning;
    			hData["harvestCleaningDate"] = harvestCleaningDate;
    			hData["harvestPackaging"] = harvestPackaging;
    			hData["harvestPackagingDate"] = harvestPackagingDate;
    			hData["harvestMoving"] = harvestMoving;
    			hData["harvestMovingDate"] = harvestMovingDate;
    			hData["harvestSell"] = harvestSell;
    			hData["harvestSalePrice"] = harvestSalePrice;
    			hData["harvestRemark"] = harvestRemark;
    			var addTable = generateRow(countRow, "Harvest", data, hData);
    			$("#Harvest tbody:first").append(addTable);
    			if(countRow==1) { $("#Harvest #noData").remove(); }
    		}
    		$("#HarvestPopup").dialog("close");
    	} else {
    		if(harvestQualityDate=="") { alert("กรุณาใส่วันที่ปฏิบัติ"); }
    		else if(harvestQuality=="") { alert("กรุณาใส่ลักษณะรวงข้าวที่เก็บเกี่ยว"); }
    		else if(harvestTotal=="") { alert("กรุณาใส่ผลผลิตที่ได้"); }
    		// else if(harvestKeep=="") { alert("กรุณาใส่ผลผลิตที่เก็บไว้ทำพันธุ์/บริโภค"); }
    		else if(harvestResult==0) { alert("กรุณาใส่ผลการปฏิบัติ"); }
    		else if(harvestCheckBy=="") { alert("กรุณาใส่ผู้ตรวจการปฏิบัติ"); }
    	}
    }
    
    function delHarvest() {
    	var del = document.getElementsByName('harDel');
    	var harvestId = document.getElementsByName('harvestId');
    	var delItem = [];
		for(var i = 0 ; i < del.length ; i++){
			if(del[i].checked){
    			if(harvestId[i].value!=0) {
    				$("#Harvest .gridPageOfPage").append("<input type=\"hidden\" name=\"delHarId\" value=\""+harvestId[i].value+"\" />");
    			}
				delItem.push("#har"+(i+1));
			}
		}
		for(var j = 0 ; j < delItem.length ; j++){
			$(delItem[j]).remove();
		}
		
    	var harvestObservers = document.getElementsByName("harvestObservers");
    	var harvestObserversDate = document.getElementsByName("harvestObserversDate");
		var harvestDain = document.getElementsByName("harvestDain");
		var harvestDainRemark = document.getElementsByName("harvestDainRemark");
    	var harvestDainDate = document.getElementsByName("harvestDainDate");
		var harvestQuality = document.getElementsByName("harvestQuality");
    	var harvestQualityDate = document.getElementsByName("harvestQualityDate");
		var harvestFarmStatus = document.getElementsByName("harvestFarmStatus");
		var harvestFarmStatusDate = document.getElementsByName("harvestFarmStatusDate");
    	var harvester = document.getElementsByName("harvester");
		var harvesterDate = document.getElementsByName("harvesterDate");
		var harvestCleaning = document.getElementsByName("harvestCleaning");
		var harvestCleaningDate = document.getElementsByName("harvestCleaningDate");
    	var harvestPackaging = document.getElementsByName("harvestPackaging");
		var harvestPackagingDate = document.getElementsByName("harvestPackagingDate");
		var harvestMoving = document.getElementsByName("harvestMoving");
		var harvestMovingDate = document.getElementsByName("harvestMovingDate");
    	var harvestTotal = document.getElementsByName("harvestTotal");
    	var harvestKeep = document.getElementsByName("harvestKeep");
    	var harvestSell = document.getElementsByName("harvestSell");
		var harvestSalePrice = document.getElementsByName("harvestSalePrice");
		var harvestResult = document.getElementsByName("harvestResult");
		var harvestRemark = document.getElementsByName("harvestRemark");
		var harvestCheckBy = document.getElementsByName("harvestCheckBy");
    	
    	var addRow = "";
    	countRow = del.length;
    	for(var i=0;i<countRow;i++) {
    		var harvestQualityTxt = document.getElementById("txtHarvestQuality"+del[i].value).innerHTML;
    		if(harvestId[i].value=="") { harvestId[i].value = 0; }
    		var data = {"Id":harvestId[i].value,"HarvestQualityDate":harvestQualityDate[i].value,"HarvestQuality":harvestQuality[i].value+"$"+harvestQualityTxt,
    				"HarvestTotal":harvestTotal[i].value,"HarvestKeep":harvestKeep[i].value,
    				"HarvestCheckBy":harvestCheckBy[i].value+"|left","HarvestResult":harvestResult[i].value};
    		var hData = {"harvestObservers":harvestObservers[i].value,"harvestDain":harvestDain[i].value,"harvestDainRemark":harvestDainRemark[i].value,
    				"harvestDainDate":harvestDainDate[i].value,"harvestObserversDate":harvestObserversDate[i].value,"harvestFarmStatus":harvestFarmStatus[i].value,
    				"harvestFarmStatusDate":harvestFarmStatusDate[i].value,"harvester":harvester[i].value,"harvesterDate":harvesterDate[i].value,
    				"harvestCleaning":harvestCleaning[i].value,"harvestCleaningDate":harvestCleaningDate[i].value,"harvestPackaging":harvestPackaging[i].value,
    				"harvestPackagingDate":harvestPackagingDate[i].value,"harvestMoving":harvestMoving[i].value,"harvestMovingDate":harvestMovingDate[i].value,
    				"harvestSell":harvestSell[i].value,"harvestSalePrice":harvestSalePrice[i].value,"harvestRemark":harvestRemark[i].value};
    		addRow += generateRow((i+1), "Harvest", data, hData);
    	}
    	$("#Harvest tbody:first tr").remove();
    	if(countRow==null || countRow==0) {
    		$("#Harvest tbody:first").append("<tr id=\"noData\" class=\"gridRowEven\"><td class=\"last-child\" colspan=\"6\">No records to display!</td></tr>");
    		document.getElementsByName('checkAll_harDel')[0].checked = false;
    	} else {
    		$("#Harvest tbody:first").append(addRow);
    	}
    }
    
    function selectOther(selectBox, textBox)
    {
    	var selectObj = document.getElementById(selectBox);
    	var inputObj = document.getElementById(textBox);
    	
		if(selectObj.options[selectObj.selectedIndex].value=="อื่นๆ" || selectObj.options[selectObj.selectedIndex].text=="อื่นๆ") {
			inputObj.readOnly = false;
			inputObj.className = "";
			inputObj.focus();
		} else {
			inputObj.readOnly = true;
			inputObj.className = "readonly";
		}
		
		inputObj.value = "";
    }
    
    function selectDrain(checkBox, txbRemark)
    {
    	var selectObj = document.getElementsByName(checkBox);
    	var inputObj = document.getElementById(txbRemark);

		for(var i=0; selectObj[i]; ++i){
			var checkValue = selectObj[i].value.split("$");
			if(selectObj[i].checked && checkValue[1].endsWith("เพราะ")){
				inputObj.readOnly = false;
				inputObj.className = "";
				inputObj.focus();
			} else {
				inputObj.readOnly = true;
				inputObj.className = "readonly";
			}
			inputObj.value = "";
		}
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
    
    function clearChecked(elementName)
    {
		for(var i=0; elementName[i]; ++i){
			elementName[i].checked = false;
		}
    }
    
    function setChecked(element, value)
    {
		for(var i=0; element[i]; ++i){
			if(value == "") value = 0;
			if(element[i].value == value || element[i].value.startsWith(value)){
				element[i].checked = true;
			} else {
				element[i].checked = false;
			}
		}
    }
    
    function setMultiChecked(element, value)
    {
    	for(var i=0; element[i]; ++i){
    		for(var j=0; j<value.length; j++){
    			if(element[i].value == value[j]){
    				element[i].checked = true;
    				break;
    			}
    			element[i].checked = false;
    		}
    	}
    }
    
    function getChecked(element)
    {
    	for(var i=0; element[i]; ++i){
			if(element[i].checked){
				return element[i].value;
			}
		}
    	return "";
    }
    
    function getMultiChecked(element)
    {
    	var value = [];
    	for(var i=0; element[i]; ++i){
			if(element[i].checked){
				value.push(element[i].value);
			}
		}
    	return value;
    }
    
    function manureStatusChange(elementName)
    {
    	var inputStatus =  document.getElementsByName(elementName);
    	var inputBuyDate =  document.getElementById("inManureBuyDate");
    	var inputSourceBuy =  document.getElementById("inManureSourceBuy");
		
		for(var i=0; inputStatus[i]; ++i){
			if(inputStatus[i].checked && inputStatus[i].value){
				inputBuyDate.readOnly = true;
				inputSourceBuy.readOnly = true;
			} else {
				inputBuyDate.readOnly = false;
				inputSourceBuy.readOnly = false;
			}
		}
    }

    function check2Decimal(el, evt)
    {
    	var charCode = (evt.which) ? evt.which : event.keyCode;

        if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
            return false;
        if (charCode == 46 && el.value.indexOf(".") !== -1)
            return false;
        if (charCode != 8 && el.value.indexOf(".") > -1)
        {
        	var number = el.value.split('.');
        	if (number.length == 2 && number[1].length > 1)
        		return false;
        }
        return true;
    }