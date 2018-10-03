function validatePassword(currentPassword, newPassword, confirmPassword) { 
	if(currentPassword == null || currentPassword ==""){
		alert("Please input current password!!");
		return false;
	}
	
	if(newPassword == null || newPassword ==""){
		alert("Please input new password!!");
		return false;
	}
	
	if(confirmPassword == null || confirmPassword ==""){
		alert("Please input confirm password!!");
		return false;
	}
	
	if(newPassword != confirmPassword){
		alert("Password does not match the confirm password!!");
		return false;
	}
	
	if(checkSpecialChar(newPassword) && checkUpperCaseChar(newPassword) && checkLowerCaseChar(newPassword) && checkNumeric(newPassword) && checkLength(newPassword)){
		return true;
	}else{
		return false;
	}
} 


function checkSpecialChar(newPassword){
	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~_";
	var foundSpecialChar=0;
    for (var i = 0; i < newPassword.length; i++){
		if (iChars.indexOf(newPassword.charAt(i)) != -1) {
			foundSpecialChar = foundSpecialChar+1;
		}
	}
	
	if(foundSpecialChar>0){
		return true;
	}else{
		alert("Password must contain at least one special character!!");
		return false;
	}
}


function checkUpperCaseChar(newPassword){
	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~_";
	var upperChars = "";
	var foundChar=0;

	for (var i = 0; i < newPassword.length; i++){
	 	if (iChars.indexOf(newPassword.charAt(i)) == -1) {
	 		if(!regIsNumber(newPassword.charAt(i))){
	 			if (newPassword.charAt(i) == newPassword.charAt(i).toUpperCase()) {
					foundChar = foundChar+1;
				}			
	 		}	 	
		}
	}
	if(foundChar>0){
		return true;
	}else{
		alert("Password must contain at least one upper case character!!");
		return false;
	}
}

function checkLowerCaseChar(newPassword){
	var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~_";
	var upperChars = "";
	var foundChar=0;

	for (var i = 0; i < newPassword.length; i++){
	 	if (iChars.indexOf(newPassword.charAt(i)) == -1) {
	 		if(!regIsNumber(newPassword.charAt(i))){
	 			if (newPassword.charAt(i) == newPassword.charAt(i).toLowerCase()) {
					foundChar = foundChar+1;
				}			
	 		}	 	
		}
	}
	if(foundChar>0){
		return true;
	}else{
		alert("Password must contain at least one lower case character!!");
		return false;
	}
}

function checkNumeric(newPassword){
	var upperChars = "";
	var foundNumber=0;

	for (var i = 0; i < newPassword.length; i++){
 		if(regIsNumber(newPassword.charAt(i))){			
			foundNumber = foundNumber+1;			
 		}	 	
	}
	if(foundNumber>0){
		return true;
	}else{
		alert("Password must contain at least one numeric!!");
		return false;
	}
}

function regIsNumber(fData) {
 	var reg = new RegExp("^[0-9]$");  
 	return (reg.test(fData));  
} 

function checkLength(newPassword){
	if(newPassword.length > 7 && newPassword.length < 17 ){
		return true;
	}else{
		alert("Password must be 8-16 characters!!");
		return false;
	}
}
// below all are functions from page Plant.jsp by Jane on 18.11.2014
function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    return true;
}
function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}
function daysInFebruary (year){
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31;
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30;}
		if (i==2) {this[i] = 29;}
   } 
   return this;
}

function isDate(eleDate){
	var dtCh= "/";
	var minYear=2500;
	var maxYear=2600;

	var dtStr = document.getElementById(eleDate).value;
	var daysInMonth = DaysArray(12);
	var pos1=dtStr.indexOf(dtCh);
	var pos2=dtStr.indexOf(dtCh,pos1+1);
	var strDay=dtStr.substring(0,pos1);
	var strMonth=dtStr.substring(pos1+1,pos2);
	var strYear=dtStr.substring(pos2+1);
	strYr=strYear;
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
	}
	month=parseInt(strMonth);
	day=parseInt(strDay);
	year=parseInt(strYr);
	if (pos1==-1 || pos2==-1){
		alert("กรุณาใส่วันที่ในรูปแบบ วันที่/เดือน/ปี");
		document.getElementById(eleDate).value ="";
		focus(eleDate);
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("กรุณาใส่เดือนที่ถูกต้อง");
		document.getElementById(eleDate).value ="";
		focus(eleDate);
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("กรุณาใส่วันที่ที่ถูกต้อง");
		document.getElementById(eleDate).value ="";
		focus(eleDate);
		return false;
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("กรุณาเลือกปี "+minYear+" ถึงปี "+maxYear);
		document.getElementById(eleDate).value ="";
		focus(eleDate);
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("กรุณาใส่วันที่ในรูปแบบ วันที่/เดือน/ปี");
		document.getElementById(eleDate).value ="";
		focus(eleDate);
		return false;
	}
	return true;
}

function convertToDateString(dateStr){
    var splitDate = dateStr.split("-");
    
    var dStr = parseInt(splitDate[2]);
	var mStr = parseInt(splitDate[1]);
	var yStr = parseInt(splitDate[0]);
    
    return dStr + "/" + mStr + "/" + yStr;
}

function compareDate(val1, val2)
{ 
	var subStr1 = val1.split("/");
	var subStr2 = val2.split("/");
	
	var dateStr1 = parseInt(subStr1[0]);
	var monthStr1 = parseInt(subStr1[1]);
	var yearStr1 = parseInt(subStr1[2]);
	
	var dateStr2 = parseInt(subStr2[0]);
	var monthStr2 = parseInt(subStr2[1]);
	var yearStr2 = parseInt(subStr2[2]);
	
	var checkBoolean = 0;
	if(yearStr1 == yearStr2){
		if(monthStr1 == monthStr2){
			if(dateStr1 < dateStr2){ //วันที่เพาะปลูกมาก่อน วันที่เก็บเกี่ยว
				checkBoolean = 0;
			}else{ // วันที่เพาะปลูกมาหลังหรือเท่ากับวันที่เก็บเกี่ยว (เป็นไปไม่ได้)
				checkBoolean++;
			}
		}else{
			if(monthStr1 < monthStr2){ // เดือนที่เพาะปลูกมาก่อน เดือนที่เก็บเกี่ยว
				checkBoolean = 0;
			}if(monthStr1 > monthStr2){ // เดือนที่เพาะปลูกมาหลัง เดือนที่เก็บเกี่ยว
				checkBoolean++;
			}
		}
	}else{
		if(yearStr1 < yearStr2){ // ปีที่เพาะปลูกมาก่อน ปีที่เก็บเกี่ยว
			checkBoolean = 0;
		}if(yearStr1 > yearStr2){ // ปีที่เพาะปลูกมาหลัง ปีที่เก็บเกี่ยว
			checkBoolean++;
		}
	}
	
	if(checkBoolean > 0)
		return false;
	else
		return true;
	
}
function myToFixed(num, digits){
    var pow = Math.pow(10, digits);
    return Math.floor(num * pow) / pow;
}
function checkNumberComma(str){
    var RE = /^\d*(\.\d{1})?\d{0,1}$/;
   	var RE2 = /^\d{1,3}(?:,\d{3})*(?:\.\d+)?$/;
   	if(str.value != ""){
    	if(RE.test(str.value)){
    		document.getElementById(str.id).value = formatNumber(str.value);
   	    }else if(RE2.test(str.value)){
    		var sttNew = str.value.replace(/,/g , "");
    		document.getElementById(str.id).value = formatNumber(sttNew);
    	}else{
    		alert("กรุณาใส่ค่าที่เป็น 'ตัวเลข' และ 'ทศนิยมสองตำแหน่ง' เท่านั้น!");
    		document.getElementById(str.id).value="";
    	}
    }else{
   		 document.getElementById(str.id).value = "";
    }
}


function formatNumber(n){
	var num = parseFloat(n);
    return num.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
}


function checkNumber(str){
    var RE = /^\d+$/;
    if(RE.test(str.value)){
 	     $('#'+str.id).css('border-color', ''); // 22.07.2014
     }else{
      	alert("กรุณาใส่ค่าที่เป็นตัวเลข");
      	document.getElementById(str.id).value="";
        $('#'+str.id).css('border-color', 'black'); // 22.07.2014
		setTimeout(function() {
     		 document.getElementById(str.id).focus();
   		}, 0);
    }
}

function checkMoo(str)    {
    var RE = /^\d+$/;
    if(RE.test(str.value)){
    	$('#'+str.id).css('border-color', ''); // just added on 22.07.2014
    }else{
    	alert("กรุณาใส่ค่าที่เป็นตัวเลข");
    	document.plantForm.moo.value="";
    	$('#'+str.id).css('border-color', 'black'); // just added on 22.07.2014
    	setTimeout(function() {
     		 	document.getElementById(str.id).focus();
   		}, 0);
    }
}

function checkTel(str)
{
    var RE = /^\d+$/;
    var len = document.getElementById(str.id).value.length;
    if(RE.test(str.value)){
    	if(len != 10){
    		 alert("กรุณาใส่เบอร์โทรศัพท์มือถือ 10 หลัก");
       		 document.getElementById(str.id).value = "";
        	 $('#'+str.id).css('border-color', 'black');  // just added 22.07.2014
    		 setTimeout(function() {
     		 	document.getElementById(str.id).focus();
   			 }, 0);
    	}else{
       		$('#'+str.id).css('border-color', '');  // just added 22.07.2014
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

