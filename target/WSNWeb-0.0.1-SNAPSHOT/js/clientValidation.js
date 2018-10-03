

///////// some session and login related functions

// This function has to be called from a .esp file and not a .html file. For now we don't have
// .html files. If we need to later then we will have to move this function out.
function isSessionValid(ses) {

	//alert("isSessionValid: session: " + ses);

	if (ses == undefined ||
			ses == null ||
			ses == "") {
		return false;
	} else {
		return true;
	}
}

/*
 * main window pages like objects.esp, dashboard.esp etc. should pass this as true. Others like
 * editApplicationInRule.esp etc. which open up in a child window should pass this as false
 */
function goToLoginPage(isMainWindow) {
	if (isMainWindow) {
		window.top.location = "/esp/login.esp";
	} else {
		window.opener.top.location = "/esp/login.esp";
		window.close();
	}
}

/*
 * main window pages like objects.esp, dashboard.esp etc. should pass this as true. Others like
 * editApplicationInRule.esp etc. which open up in a child window should pass this as false
 */
function checkSession(ses, isMainWindow) {
	if (!isSessionValid(ses)) {
		if (!isMainWindow) {
			alert(PAN_MSG_1000);
		}
		goToLoginPage(isMainWindow);
	}
}

////////// end of session and login related functions //////////////////

function trim(str) {
	var tmp = str.replace(/^\s+/g, "");
	var tmp = tmp.replace(/\s+$/g, "");

	return tmp;
}

function trimStr(str) {
	return trim(str);
}

function isEmptyStr(str) {
	var tmp = trim(str);

	if (tmp.length == 0) {
		return true;
	} else {
		return false;
	}
}

function isEmptyString(str) {
	return isEmptyStr(str);
}

function getValidObjectNameMsg(objType) {
	return "A valid " + (objType || '') + " object name must start with an alphanumeric character and can contain zero or more alphanumeric characters, underscore '_', hyphen '-', dot '.' or spaces.";
}

function validObjectName(str) {
	if (str.match(/^[0-9a-zA-Z]{1}([0-9a-zA-Z_-]|[ ]|[.])*$/) == null) {
		return false;
	}

	return true;
}

// this will also show the alert msg
function checkValidObjectName(name, objType) {
	var ret = validObjectName(name);

	if (!ret) {
		var msg = getValidObjectNameMsg(objType);
		alert(msg);
	}

	return ret;
}

// return errmsg string if not valid, otherwise return true
function checkValidObjectNameMsg(name, objType) {
	var ret = validObjectName(name);
	if (!ret) {
		var msg = getValidObjectNameMsg(objType);
		return(msg);
	}
	return true;
}

function getValidFileNameMsg() {
	return "A valid file name must start with an alphanumeric character and can contain zero or more alphanumeric characters, underscore '_', hyphen '-' or dot '.'.";
}

function validFileName(str) {
	if (str.match(/^[0-9a-zA-Z]{1}([0-9a-zA-Z_-]|[.])*$/) == null) {
		return false;
	}

	return true;
}

// this will also show the alert msg
function checkValidFileName(name) {
	var ret = validFileName(name);

	if (!ret) {
		var msg = getValidFileNameMsg();
		alert(msg);
	}

	return ret;
}


function getValidUserNameMsg() {
return "A valid user name must start with an alphanumeric character and can \
contain zero or more alphanumeric characters, ampersand, underscore '_', tilde, hash, \
dollar, dash, dot, at, percent, caret, asterisk, greater than, parentheses, bracket, \
braces, exclamation, grave accent, slash or backslash.";
}


function validUserName(str) {
	if (str.match(/^[0-9a-zA-Z]{1}([{}`[\]()0-9a-zA-Z/!&$._~#@%^*<>\\-])*$/) == null) {
		return false;
	}

	return true;
}

// this will also show the alert msg
function checkValidUserName(name) {

	var ret = validUserName(name);

	if (!ret) {
		var msg = getValidUserNameMsg();
		alert(msg);
	}

	return ret;
}

function getValidLocalUserNameMsg() {
	return "A valid user name must start with an alphanumeric character and can contain zero or more alphanumeric characters, underscore, dash, or dot.";
}


function validLocalUserName(str) {
	if (str.match(/^[0-9a-zA-Z]{1}([0-9a-zA-Z-_\.])*$/) == null) {
		return false;
	}

	return true;
}

// this will also show the alert msg
function checkValidLocalUserName(name) {

	var ret = validLocalUserName(name);

	if (!ret) {
		var msg = getValidLocalUserNameMsg();
		alert(msg);
	}

	return ret;
}

function getValidDescriptionMsg(objType) {
	return "A valid " + objType + " description cannot have &, < or >";
}

function validDescription(str) {
	if (str.search(/&|<|>/) >= 0) {
		return false;
	}

	return true;
}

// this will also show the alert msg
function checkValidDescription(desc, objType) {
	var ret = validDescription(desc);

	if (!ret) {
		var msg = getValidDescriptionMsg(objType);
		alert(msg);
	}

	return ret;
}

function getValidVPNObjectNameMsg(objType) {
	return "A valid " + objType + " object name must start with an alphabet and can contain zero or more alphanumeric characters, underscore '_', hyphen '-' or dot '.'";
}

function validVPNObjectName(str) {
	if (str.match(/^[a-zA-Z]{1}([0-9a-zA-Z_-]|[.])*$/) == null) {
		return false;
	}

	return true;
}

// this will also show the alert msg
function checkValidVPNObjectName(name, objType) {
	var ret = validVPNObjectName(name);

	if (!ret) {
		var msg = getValidVPNObjectNameMsg(objType);
		alert(msg);
	}

	return ret;
}

function getValidDomainNameMsg() {
	return "A valid domain name can contain alphanumeric characters, underscore '_', hyphen '-', dot '.' or spaces.";
}

function validDomainName(str) {
	if (str.match(/^([0-9a-zA-Z_-]|[ ]|[.])*$/) == null) {
		return false;
	}

	return true;
}

// this will also show the alert msg
function checkValidDomainName(name) {
	var ret = validDomainName(name);

	if (!ret) {
		var msg = getValidDomainNameMsg();
		alert(msg);
	}

	return ret;
}


/**
 * Returns true if it is a integer between min and max. 
 */
function validNumber(str, min, max) {
	var n = Number(str);

	if (isNaN(n))
		return false;

	// check to make sure that there is only digits 
	if (str.match(/^([0-9])+$/) == null) {
		return false;
	}

	if (min != undefined && n < min)
		return false;

	if (max != undefined && n > max)
		return false;

	return true;
}

/**
 * Returns true if it is a floating pt value between min and max. 
 */
function validFloat(str, min, max) {
	var n = Number(str);

	if (isNaN(n))
		return false;

	if (min != undefined && n < min)
		return false;

	if (max != undefined && n > max)
		return false;

	return true;
}

/**
 * the number range list is expected to be of the form
 * <x>,.. where each x can be either <number> or <number>-<number>
 */
function validNumberRangeList(str, min, max) {
	var tokensArray = str.split(",");

	for(var i=0; i<tokensArray.length; i++) {
		if (tokensArray[i].charAt(tokensArray[i].length-1) == '-') {
			// cannot end in -
			return false;
		}

		if (tokensArray[i].charAt(0) == '-') {
			// if the first char is - then it is a negative number, consider it as a number
			// instead of a range.
			if (!validNumber(tokensArray[i], min, max)) {
				return false;
			}
		} else {
			var numbersArray = tokensArray[i].split("-");
			for(var j=0; j<numbersArray.length; j++) {
				if (!validNumber(numbersArray[j], min, max)) {
					return false;
				}
			} // end for j
			if (numbersArray.length == 2) {
				var num1 = numbersArray[0] - 0;
				var num2 = numbersArray[1] - 0;

				if (num1 > num2) {
					return false;
				}
			}
		}
	} // end for i

	return true;
}

/**
 * expect the date to be of the form yyyy/mm/dd
 */
function validDate(str) {
	if (isEmptyStr(str)) {
		return false;
	}

	var m_arrDate = str.split("/");
	if (m_arrDate.length != 3) {
		return false;
	}

	var m_year = m_arrDate[0];
	if (m_year.length != 4) {
		return false;
	}
	var m_month = m_arrDate[1];
	var m_day = m_arrDate[2];

	var testDate = new Date(str);
	if (testDate.getMonth()+1 != m_month) {
		return false;
	}

	return true;
}

/**
 * expect the date to be of the form yyyy/mm/dd
 *
 * returns true if date1 is before date2. If date2 is not passed then it assumes
 * date2 to be start of epoch Jan 1, 1970 (GMT).
 *
 * NOTE: This does not check for the validity of the dates passed. Caller should have
 * called validDate() to check the validity of the dates before calling this.
 */
function tooEarlyDate(date1, date2) {
	var d1 = new Date(date1);
	var d2;

	if (date2) {
		d2 = new Date(date2);
	} else {
		d2 = new Date(0);
	}

	return (d1.getTime() < d2.getTime());
}

/**
 * expect the date to be of the form yyyy/mm/dd
 *
 * returns true if date1 is after date2. If date2 is not passed then it assumes
 * date2 to be 2034/09/30 (overflow for UNIX time)
 *
 * NOTE: This does not check for the validity of the dates passed. Caller should have
 * called validDate() to check the validity of the dates before calling this.
 */
function tooLateDate(date1, date2) {
	var d1 = new Date(date1);
	var d2;

	if (date2) {
		d2 = new Date(date2);
	} else {
		d2 = new Date('2034/09/30');
	}

	return (d1.getTime() > d2.getTime());
}

/**
 * expect the time to be in hh:mm or h:mm or hh:mm:ss or h:mm:ss format
 */
function validTime(str) {
	if (isEmptyStr(str)) {
		return false;
	}

	var strArray = str.split(":");
	if (strArray.length != 2 && 
			strArray.length != 3) {
		return false;
	}

	var m_hour = strArray[0];
	var m_min = strArray[1];

	if (m_min.length != 2) {
		return false;
	}

	if (!validNumber(m_hour, 0, 23)) {
		return false;
	}

	if (!validNumber(m_min, 0, 59)) {
		return false;
	}

	if (strArray.length == 3) {
		var m_sec = strArray[2];

		if (m_sec.length != 2)
			return false;

		if (!validNumber(m_sec, 0, 59))
			return false;
	}

	return true;
}

/**
 * str is expected to be in hh:mm or hh:mm:ss format. If hh is 
 * just h then I just add a 0 at the front. This function does not check the
 * validity of the hh and mm values. That will be done in validTime()
 */
function adjustTimeFormat(str) {
	var strArray = str.split(":");
	var ret = str;

	if (strArray[0].length == 1) {
		ret = "0" + str;
	}

	return ret;
}

/**
 * This function will check that the date range is valid i.e. the dates and times
 * are valid and that the end date is later than the start date.
 *
 * This also prints out the alerts if asked
 *
 * startDate and endDate format yyyy/mm/dd
 * startTime and endTime format hh:mm or hh:mm:ss
 */
function checkValidDateRange(startDate, startTime, endDate, endTime, printAlerts) {

	if (!validDate(startDate)) {
		if (printAlerts)
			alert("Invalid start date '" + startDate + "'");
		return false;
	}

	if (!validTime(startTime)) {
		if (printAlerts)
			alert("Invalid start time '" + startTime + "'");
		return false;
	}

	if (!validDate(endDate)) {
		if (printAlerts)
			alert("Invalid end date '" + endDate + "'");
		return false;
	}

	if (!validTime(endTime)) {
		if (printAlerts)
			alert("Invalid end time '" + endTime + "'");
		return false;
	}

	var d1 = new Date(startDate + " " + startTime);
	var d2 = new Date(endDate + " " + endTime);

	if (d1.getTime() > d2.getTime()) {
		if (printAlerts) 
			alert("End date is earlier than start date");
		return false;
	}

	return true;
}

function checkValidTimeRange(startTime, endTime, printAlerts) {

	if (!validTime(startTime)) {
		if (printAlerts)
			alert("Invalid start time '" + startTime + "'");
		return false;
	}

	if (!validTime(endTime)) {
		if (printAlerts)
			alert("Invalid end time '" + endTime + "'");
		return false;
	}

	// Since we are comparing the time I am putting an arbitrary date
	var d1 = new Date("2000/01/01 " + startTime);
	var d2 = new Date("2000/01/01 " + endTime);

	if (d1.getTime() > d2.getTime()) {
		if (printAlerts) 
			alert("End time is earlier than start time");
		return false;
	}

	return true;
}

var gMinPasswordLen = 6;
function validPasswordPan(str, fips)
{
   if ( (fips == 'yes') && 
      (str.length < gMinPasswordLen))
      return false;

   return true;
}

function getValidPasswordPanMsg()
{
   var msg = "The minimum length of the password is " + gMinPasswordLen;
   return msg;
}


/**
 * replaces blank with a +, use for query string in url's
 */
function replaceBlankWithPlus(str) {
	return str.replace(/ /g, "+");
}

function userAllowedEditByRoleOnly(role, xpath) {
	var ret = true;

	if (role == "superreader" || role == "devicereader" || role == "vsysreader") {
		// readers cannot edit.
		ret = false;
	} else if (role == 'custom'){
        //ret = Pan.rolebasePermission(xpath);
        var permission = Pan.rolebasePermission(xpath);
        ret = (permission == "enable");
	}

	return ret;
}

/**
 * is a user allowed to edit stuff in a particular location depending on role. 
 * NOTE: for device admin /vsys admin it does not check if the user has access to that 
 * device/vsys. This is just a generic role based checker
 *
 * loc is of form type:device:vsys where device, vsys may or may not be present
 * role is the role in string format
 *
 * NOTE: There is a similar function in panEspUsers.c - change in logic here must also
 * be reflected there.
 */
function userAllowedEditByRole(loc, role, xpath) {
	var ret = true;

	if (!userAllowedEditByRoleOnly(role, xpath)) {
		ret = false;
	} else if (role == 'custom'){
        //ret = Pan.rolebasePermission(xpath);
        var permission = Pan.rolebasePermission(xpath);
        ret = (permission == "enable");
    } else {
		var locArray = loc.split(":");
		var locType = locArray[0];

		if (locType <= 3) {
			// global
			if (role != "superuser")
				ret = false;
		} else if (locType == 8) {
			// device
			// KK TODO - should not we be adding panorama-admin in the check here?
			if (role != "superuser" && role != "deviceadmin")
				ret = false;
		}
	}

	return ret;
}

/**
 * is a user allowed to create/edit stuff in shared
 */
function userAllowedSharedEdit(role,xpath) {
	if (role == "superuser") {
		return true;
	} else if (xpath != '' && Pan.rolebasePermission(xpath) == 'enable' && !Pan.isVsysRolebasedAdmin()) {
		return true;
        }
	return false;
}

function listObjProperties(obj) {
	var str = "";
	var count = 0;
	for(var prop in obj) {
		try {
			if (obj[prop] instanceof Object) {
				str += prop + ":{";
				// recursion
				str += listObjProperties(obj[prop]);
				str += "}";
			} else {
				str += prop + ":"; 
				str += obj[prop] 
			}
		} catch (e) {
			str += "exception - could not get";
		}
		str += ",";
		//str += prop + ",";
		if ((count % 10) == 0)
			str += "\n";
		count++;
	}
	return str;
}

function disableTextFld(ctrl, disable) {
	var color;

	if (disable)
		color = "#F3F3F3";
	else
		color = "white";
	ctrl.disabled = disable;
	ctrl.style.backgroundColor = color;
}

function disableAllFlds(thisForm, disable) {
	var idx;
	var ctrl;
	for (idx = 0; idx < thisForm.elements.length; ++idx) {
		ctrl = thisForm.elements[idx];
		if (ctrl.type == "text" || ctrl.type == "password" || ctrl.type == 
				"textarea")
			disableTextFld(ctrl, disable);
		else
			ctrl.disabled = disable;
	}
}

/* assuming ip is valid */
function inet_addr(ip) {
	var i;
	var j;
	var ipNum = 0;
	var seg = ip.split(".");

	for (i=0; i< 4; i++) {
		j = new Number(seg[i]);

		ipNum = ipNum << 8;
		ipNum += j;
	}

	return ipNum;
}

/* This function can be used for comparing ip addresses. It assumes the params to be of the 
 * form x1.x2.x3.x4/y1 (i.e. dotted decimal)
 */
function ipOrder(a, b) {
	var ip1, ip2;
	var mask1, mask2;
	var arr1, arr2;

	arr1 = a.split("/");
	ip1 = arr1[0];
	mask1 = 0; // default
	if (arr1.length == 2)
		mask1 = arr1[1];

	arr2 = b.split("/");
	ip2 = arr2[0];
	mask2 = 0; // default
	if (arr2.length == 2)
		mask2 = arr2[1];

	var inetAddr1, inetAddr2;
	var iMask1 = mask1 - 0; // make into a number
	var iMask2 = mask2 - 0; // make into a number

	inetAddr1 = inet_addr(ip1);
	inetAddr2 = inet_addr(ip2);

	var retval = inetAddr1 - inetAddr2;
	if (retval == 0) {
		// now compare the masks if the ips are same
		retval = mask1 - mask2;
	}

	return retval;
}

function numOccurrencesInStr(str, ch) {
	var count = 0;
	for(var i=0; i<str.length; i++) {
		if (str.charAt(i) == ch)
			count++;
	}

	return count;
}

/*
 * str can be a ipv6/prefix format.
 *
 * NOTE: in ipv6 the :: compressed form can occur only once.
 */
function normalizeIpV6Address(str) {
	var arr = str.split("/");
	var ipv6Str = trim(arr[0]);

	var prefixStr = "";
	if (arr.length == 2)
		prefixStr = trim(arr[1]);

	var doubleColonSplitArr = ipv6Str.split("::");
	var retIpV6Str = "";
	if (doubleColonSplitArr.length > 1) {
		// if the length is more than 1 it means :: was there
		//
		// Since there can only be a single instance of :: we assume a pre and post str.
		var preDoubleColonStr = doubleColonSplitArr[0];
		var postDoubleColonStr = doubleColonSplitArr[1];

		var preDoubleColonSingleColonCount = numOccurrencesInStr(preDoubleColonStr, ":");
		var postDoubleColonSingleColonCount = numOccurrencesInStr(postDoubleColonStr, ":");

		var numPreFields;
		if (preDoubleColonStr.length > 0)
			numPreFields = preDoubleColonSingleColonCount + 1; // if there are 3 : it means a:b:c:d i.e. 4 fields
		else
			numPreFields = 0;

		var numPostFields;
		if (postDoubleColonStr.length > 0)
			numPostFields = postDoubleColonSingleColonCount + 1;
		else
			numPostFields = 0;

		var numFieldsToFill = 8 - (numPreFields+numPostFields);
		var strToFill = "";
		if (numFieldsToFill > 0) {
			// it should always be > 0, if there were eight fields then there would not be a :: and so we would not be here.
			// this is just an extra check
			for(var i=0; i<numFieldsToFill; i++) {
				if (i > 0)
					strToFill += ":";
				strToFill += "0";
			}
		}

		if (preDoubleColonStr.length > 0) {
			retIpV6Str += preDoubleColonStr;
		}

		if (strToFill.length > 0) {
			if (retIpV6Str.length > 0)
				retIpV6Str += ":";

			retIpV6Str += strToFill;
		}

		if (postDoubleColonStr.length > 0) {
			if (retIpV6Str.length > 0)
				retIpV6Str += ":";

			retIpV6Str += postDoubleColonStr;
		}

	} else {
		// no ::, not in compressed form and so we just return the original string
		retIpV6Str = ipv6Str;
	}

	if (prefixStr.length > 0)
		return retIpV6Str + "/" + prefixStr;
	else
		return retIpV6Str;

}

function ipv6Order(a, b) {
	var ip1, ip2;
	var prefix1, prefix2;
	var arr1, arr2;

	arr1 = a.split("/");
	ip1 = arr1[0];
	prefix1 = 0; // default
	if (arr1.length == 2)
		prefix1 = arr1[1];

	arr2 = b.split("/");
	ip2 = arr2[0];
	prefix2 = 0; // default
	if (arr2.length == 2)
		prefix2 = arr2[1];

	var normalizedIp1 = normalizeIpV6Address(ip1);
	var normalizedIp2 = normalizeIpV6Address(ip2);

	var normalizedIp1Arr = normalizedIp1.split(":");
	var normalizedIp2Arr = normalizedIp2.split(":");

	var h1, h2;
	var retval = 0;
	for(var i=0; i<8; i++) {
		h1 = Number('0x' + normalizedIp1Arr[i]);
		h2 = Number('0x' + normalizedIp2Arr[i]);

		if (h1 != h2) {
			retval = h1 - h2;
			break;
		}
	}
	if (retval == 0) {
		// now compare the prefix if the ips are same
		retval = prefix1 - prefix2;
	}

	return retval;
}

function validIpAddress(str) {
	var numArray = str.split(".");

	if (numArray.length != 4) {
		return false;
	}

	var lastChar = str.charAt(str.length-1);
	if (lastChar == '.') {
		return false; // cannot end with a .
	}

	for(var i=0; i<numArray.length; i++) {
		if (!validNumber(numArray[i], 0, 255)) {
			return false;
		}
	}

	return true;
}

//////////// Functions for IP V6 validation ////////////

// From http://ipv6blog.net/ipv6-validation-javascript/

function substr_count (haystack, needle, offset, length)
{
    var pos = 0, cnt = 0;
 
    haystack += '';
    needle += '';
    if (isNaN(offset)) {offset = 0;}
    if (isNaN(length)) {length = 0;}
    offset--;
 
    while ((offset = haystack.indexOf(needle, offset+1)) != -1) {
        if (length > 0 && (offset+needle.length) > length) {
            return false;
        } else {
            cnt++;
        }
    }
 
    return cnt;
}

function test_ipv4(ip)
{
   var match = ip.match(/(([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([1-9]?[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])/);
   return match != null;
}

/* KK
 * I added this for bug 24240 but we are not calling it as it may be too risky now to put 
 * a completely different validator function for ipv6. Leaving it in here though in case 
 * we want to use it in future
 */
function test_ipv6(ip)
{
  // Test for empty address
  if (ip.length<3)
  {
	return ip == "::";
  }
 
  // Check if part is in IPv4 format
  if (ip.indexOf('.')>0)
  {
        var lastcolon = ip.lastIndexOf(':');
 
        if (!(lastcolon && test_ipv4(ip.substr(lastcolon + 1))))
            return false;
 
        // replace IPv4 part with dummy
        ip = ip.substr(0, lastcolon) + ':0:0';
  } 
 
  // Check uncompressed
  if (ip.indexOf('::')<0)
  {
    var match = ip.match(/^(?:[a-f0-9]{1,4}:){7}[a-f0-9]{1,4}$/i);
    return match != null;
  }
 
  // Check colon-count for compressed format
  if (substr_count(ip, ':') < 8) 
  {
    var match = ip.match(/^(?::|(?:[a-f0-9]{1,4}:)+):(?:(?:[a-f0-9]{1,4}:)*[a-f0-9]{1,4})?$/i);
    return match != null;
  } 
 
  // Not a valid IPv6 address
  return false;
}



//////////// End Functions for IP V6 validation ////////////

function validIPv6Address(str) {

	/* Bug 24240 allow ::
	if (str.match(/^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:))$/) == null) {
	*/
	if (str.match(/^((([0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){5}:([0-9A-Fa-f]{1,4}:)?[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){4}:([0-9A-Fa-f]{1,4}:){0,2}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){3}:([0-9A-Fa-f]{1,4}:){0,3}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){2}:([0-9A-Fa-f]{1,4}:){0,4}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){6}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(([0-9A-Fa-f]{1,4}:){0,5}:((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|(::([0-9A-Fa-f]{1,4}:){0,5}((\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b)\.){3}(\b((25[0-5])|(1\d{2})|(2[0-4]\d)|(\d{1,2}))\b))|([0-9A-Fa-f]{1,4}::([0-9A-Fa-f]{1,4}:){0,5}[0-9A-Fa-f]{1,4})|(::([0-9A-Fa-f]{1,4}:){0,6}[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,7}:)|(::))$/) == null) {
		return false;
	}

	return true;
}

function validIPAddrV4orV6(str)
{
   return validIpAddress(str) || validIPv6Address(str);
}


/*
 * assumes the str is in the dotted decimal format like 255.255.255.0
 */
function validDottedDecimalSubnetMask(str) {
	// first check if it is a valid ip address
	var validIp = validIpAddress(str);
	if (!validIp) {
		return false;
	}

	// if so convert it to a number
	var mask = inet_addr(str);

	// then make sure that the left most bits are 1. We do this using the following logic.
	// 1. do a negate - so now the right most bits are 1.
	// 2. In a loop start filling the right most bits with 1s and do an xor with the mask got in 1
	//    e.g. the numbers in the subsequent loops are 0, 1, 11, 111, etc.
	// 3. If in any of the comparision we get 0 then the mask is the same as the number being xored against i.e.
	//    it is of the form <0000....><11....>
	// 4. So its negated number (remember we did a negate first in step 1) is of the form <11...><00...>
	var i=0;
	var f=0;
	var mask = inet_addr(str);
	var m = ~mask;

	for (i=0; i<32; i++) {
		if ((m ^ f) == 0)
			return true;

		f = (f << 1)+1;
	}
	return false;
}

function validIntegerSubnetMask(str) {
	return validNumber(str, 0, 32);
}

function validIPv6IntegerSubnetMask(str) {
	// Bug 24240 - subnet 0,1,2 is also valid
	//return validNumber(str, 3, 128);
	return validNumber(str, 0, 128);
}


/*
 * expect string to be of the form <ip>/<integer mask>
 * By default both must be present. If mask is to be optional we must
 * pass 'true' in maskOptional. If this is not passed then maskOptional is
 * assumed to be false.
 */

function validIpAndIntegerSubnetMask(str, maskOptional) 
{
	if (isEmptyStr(str))
		return false;

	var mOpt = false;
	if (maskOptional != undefined) {
		// mask optional is passed
		mOpt = maskOptional;
	}

	var arr = str.split("/");
	if (mOpt) {
		// mask is optional
		if (arr.length == 1) {
			// only ip
			return validIpAddress(arr[0]);
		} else if (arr.length == 2) {
			return (validIpAddress(arr[0]) && validIntegerSubnetMask(arr[1]));
		} else {
			return false;
		}
	} else {
		// mask is not optional
		if (arr.length == 2) {
			return (validIpAddress(arr[0]) && validIntegerSubnetMask(arr[1]));
		} else {
			return false;
		}
	}

	return true;
}

function validIPv6AndIntegerSubnetMask(str, maskOptional) 
{
	if (isEmptyStr(str))
		return false;

	var mOpt = false;
	if (maskOptional != undefined) {
		// mask optional is passed
		mOpt = maskOptional;
	}

	var arr = str.split("/");
	if (mOpt) {
		// mask is optional
		if (arr.length == 1) {
			// only ip
			return validIPv6Address(arr[0]);
		} else if (arr.length == 2) {
			return (validIPv6Address(arr[0]) && validIPv6IntegerSubnetMask(arr[1]));
		} else {
			return false;
		}
	} else {
		// mask is not optional
		if (arr.length == 2) {
			return (validIPv6Address(arr[0]) && validIPv6IntegerSubnetMask(arr[1]));
		} else {
			return false;
		}
	}

	return true;
}

function validIPAndIntegerSubnetMaskV4orV6(str, maskOptional) {
	return validIpAndIntegerSubnetMask(str, maskOptional) || validIPv6AndIntegerSubnetMask(str, maskOptional);
}



/*
 * ip/mask or ip only format. 
 * Used by log filter
 */
function validIpAndIntegerSubnetMaskEx(str)
{
	return validIpAndIntegerSubnetMask(str, true);
}


/*
 * expect string to be of the form <ip>/<dotted decimal mask>
 * where the /<dotted decimal mask> part is optional
 */
function validIpAndDottedDecimalSubnetMask(str) 
{
	if (isEmptyStr(str))
		return false;

	var arr = str.split("/");

	if (arr.length == 1) {
		// only ip
		return validIpAddress(arr[0]);
	} else if (arr.length == 2) {
		return (validIpAddress(arr[0]) && validDottedDecimalSubnetMask(arr[1]));
	} else {
		return false;
	}

	return true;
}

/*
 * expect string to be of the form <ip>-<ip> [NOTE: no spaces around the - ]
 */
function validIpRange(str) 
{
	if (isEmptyStr(str)) 
		return false;

	var arr = str.split("-");
	if (arr.length != 2)
		return false;

	return (validIpAddress(arr[0]) && validIpAddress(arr[1]));
}

/*
 * expect string to be of the form <ipv6>-<ipv6> [NOTE: no spaces around the - ]
 */
function validIPv6Range(str) 
{
	if (isEmptyStr(str)) 
		return false;

	var arr = str.split("-");
	if (arr.length != 2)
		return false;

	return (validIPv6Address(arr[0]) && validIPv6Address(arr[1]));
}

/*
 * This returns the selected checkbox/radio button values in a form,
 * in a string format separated by the separator.
 * e.g. value1,value2,value3,...
 *
 * Returns "" if nothing checked
 */
function getCheckedValues(formName, fieldName, separator) {

	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return "";
	}

	str = "";
	if (obj) {
		if (obj.checked) {
			str += obj.value;
		} else {
			for(var i=0; i<obj.length; i++) {
				if (obj[i].checked) {
					if (str != "") {
						// not the first one, add a separator 
						str += separator;
					}
					str += obj[i].value;
				}
			}
		}
	}

	return str;
}

function checkFirstRadioOrCheckbox(formName, fieldName) {

	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return;
	}

	if (obj) {
		if (obj.length == undefined) {
			// there is only one 
			obj.checked = true;
		} else {
			obj[0].checked = true;
		}
	}
}

function checkMatchingRadioOrCheckbox(formName, fieldName, fieldValue) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return;
	}

	if (obj) {
		if (obj.length == undefined) {
			// there is only one 
			if (obj.value == fieldValue)
				obj.checked = true;
		} else {
			for(var i=0; i<obj.length; i++) {
				if (obj[i].value == fieldValue) {
					obj[i].checked = true;
					break;
				}
			}
		}
	}
}

function disableMatchingRadioOrCheckbox(formName, fieldName, fieldValue, newValue) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return;
	}

	if (obj) {
		if (obj.length == undefined) {
			// there is only one 
			if (obj.value == fieldValue)
				obj.checked = true;
		} else {
			for(var i=0; i<obj.length; i++) {
				if (obj[i].value == fieldValue) {
					obj[i].disabled = newValue;
					break;
				}
			}
		}
	}
}

function checkMatchingCheckboxes(formName, fieldName, dataArray) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return;
	}

	if (obj) {
		if (obj.length == undefined) {
			// there is only one 

			// initialize to unchecked
			obj.checked = false;

			for(var i=0; i<dataArray.length; i++) {
				if (obj.value == dataArray[i]) {
					obj.checked = true;
					break;
				}
			}
		} else {
			for(var i=0; i<obj.length; i++) {
				// initialize to unchecked
				obj[i].checked = false;

				for(var j=0; j<dataArray.length; j++) {
					if (obj[i].value == dataArray[j]) {
						obj[i].checked = true;
						break; // out of for j loop
					}
				}
			}
		}
	}
}

// This will either check or uncheck (depending on the 'doCheck' param)
// all the checkboxes with the name 'fieldName'
function checkAllCheckboxes(formName, fieldName, doCheck) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return;
	}

	if (obj) {
		if (obj.length == undefined) {
			// there is only one 

			obj.checked = doCheck;

		} else {
			for(var i=0; i<obj.length; i++) {
				obj[i].checked = doCheck;
			}
		}
	}
}

function selectMatchingSelectOption(formName, fieldName, fieldValue) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return;
	}

	for(var i=0; i<obj.options.length; i++) {
		if (obj.options[i].value == fieldValue) {
			obj.options[i].selected = true;
			break;
		}
	}
}

// this takes the select obj as a parameter
function selectMatchingSelectOption2(selObj, selValue) {
	for(var i=0; i<selObj.options.length; i++) {
		if (selObj.options[i].value == selValue) {
			selObj.options[i].selected = true;
			break;
		}
	}
}

function selectMatchingSelectOptionMultiple(formName, fieldName, valArray) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return;
	}

	for(var i=0; i<obj.options.length; i++) {
		for(var j=0; j<valArray.length; j++) {
			if (obj.options[i].value == valArray[j]) {
				obj.options[i].selected = true;
				break; // out of for j loop
			}
		}
	}
}

/* this is only for a single selected option in a select menu */
function getSelectedValue(formName, fieldName) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return "";
	}

	str = "";

	if (obj) {
		if (obj.selectedIndex >= 0) {
			str = obj.options[obj.selectedIndex].value;
		}
	}

	return str;
}

/* Returns the text of the option corresponding to the value provided. 
 * Useful in cases where the value and text is different.
 */
function getTextForValue(formName, fieldName, value) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return "";
	}

	str = "";

	if (obj) {
		for(var i=0; i<obj.options.length; i++) {
			if (obj.options[i].value == value) {
				str = obj.options[i].text;
				break; // out of for i loop
			}
		}
	}

	return str;
}

/* this is only for a single selected option in a select menu, same as above except
 * it takes the select object as a param
 */
function getSelectedValue2(obj) {

	var str = "";

	if (obj) {
		if (obj.selectedIndex >= 0) {
			str = obj.options[obj.selectedIndex].value;
		}
	}

	return str;
}

/* this is for multiple selected options in a select menu with multiple selections allowed */
function getMultipleSelectedValues(formName, fieldName, separator) {
	var str = "document." + formName + "." + fieldName;

	var obj = eval(str);
	if (!obj) {
		return "";
	}

	str = "";
	if (obj) {
		for(var i=0; i<obj.options.length; i++) {
			if (obj.options[i].selected) {
				if (str != "") {
					// not the first one, add a separator 
					str += separator;
				}
				str += obj.options[i].value;
			}
		}
	}

	return str;
}

function validMacAddress(mac) {
	var COLON = ":";
	var seg;
	var charDomain = "0123456789abcdefABCDEF:";
	var pos = 0;
	var i = 0;
	var j = 0;
	var c;

	mac = trim(mac);
	if (mac.length < 11)
		return false;

	/* correct the problem like 12.+34.-22.0 */
	for (i=0; i<mac.length; i++) {
		c = mac.charAt(i);
		if ( charDomain.indexOf(c) < 0)
			return false;
	}

	seg = mac.split(COLON);
	if (seg.length != 6)
		return false;

	for (i=0; i < 6; i++) {
		if (seg[i].length < 1 || seg[i].length > 2)
			return false;

		j = parseInt(seg[i], 16);
		if (j < 0 || j > 255)
			return false;
	}

	return true;
}

function validInterface(str)
{
	if (str.match(/^([ 0-9a-zA-Z:@./_-])+$/) == null) {
		return false;
	}

	return true;

}

function validProtoName(str)
{
	return (validObjectName(str) || validNumber(str, 0, 255));
}


///////////// some ui utilities /////////////////

function getFirstAncestorByHtmlTag(target,tag) {
	var parent = target;
	while (parent = parent.parentNode) {
		if (parent.nodeName == tag.toUpperCase()) {
			return parent;
		}
	}
	return null;
}

/* This depends on the way the table is created, trying to use a more generice one below
   function selectRow(thisCheckbox) {
   if (thisCheckbox.checked) {
   thisCheckbox.parentNode.parentNode.style.backgroundColor = "#FFee99";
   }
   else {
   thisCheckbox.parentNode.parentNode.style.backgroundColor = "";
   }
   }
 */
function selectRow(thisCheckbox) {
	var rowObj = getFirstAncestorByHtmlTag(thisCheckbox, "tr");

	// checked to see if there is a row on click handler. if there is then let that handle
	// the click
	var rowClickHandlerPresent = false;
	if (rowObj.attributes) {
		var attrObj;
		for(var i=0; i<rowObj.attributes.length; i++) {
			attrObj = rowObj.attributes[i];
			if (attrObj.nodeName.toLowerCase() == "onclick" && attrObj.nodeValue != null && attrObj.nodeValue.length > 0) {
				rowClickHandlerPresent = true;
				break;
			}
		}
	}

	if (!rowClickHandlerPresent) {
		if (thisCheckbox.checked) {
			rowObj.style.backgroundColor = "#B6C8D5"; // Changed for 4.0 "#FFee99";
		} else {
			rowObj.style.backgroundColor = "";
		}
	}
}

/* This depends on the way the table is created, trying to use the more generic
 * one below
 function selectRowRadio(thisCheckbox) {
// First deselect all the other rows
for (var i = 0; i < something; i++) {
// KK TODO this does not work - fix it
}

if (thisCheckbox.checked) {
thisCheckbox.parentNode.parentNode.style.backgroundColor = "#FFee99";
}
else {
thisCheckbox.parentNode.parentNode.style.backgroundColor = "";
}
}
 */
function selectRowRadio(thisCheckbox) {
	// currently the row one does nothing different from the checkbox one i.e. we
	// are not deselecting the other ones, so just call the one for checkbox.
	selectRow(thisCheckbox);
}

/* Use the more generic one below
   function disableRow(thisCheckbox) {
   if (thisCheckbox.checked) {
   thisCheckbox.parentNode.parentNode.style.backgroundColor = "#dddddd";
   thisCheckbox.parentNode.parentNode.style.color = "#808080";
   } else {
   thisCheckbox.parentNode.parentNode.style.backgroundColor = "";
   thisCheckbox.parentNode.parentNode.style.color = "";
   }
   }
 */
function disableRow(thisCheckbox) {
	var rowObj = getFirstAncestorByHtmlTag(thisCheckbox, "tr");

	if (thisCheckbox.checked) {
		rowObj.style.backgroundColor = "#dddddd";
		rowOb.style.color = "#808080";
	} else {
		rowObj.style.backgroundColor = "";
		rowObj.style.color = "";
	}
}


//////////// location related functions //////////////////

/**
 * locStr is of the form
 * <X> for global where X = 1,2 or 3
 * <X>:<deviceName> for device where X is 8
 * <X>:<deviceName>:<vsysName> for vsys where X is 16
 * <X>:<deviceName>:<deviceGroupName> for dg where X is 32
 * <X>:<deviceName>:<sharedGatewayName> for shared gateway where X is 128
 */

function getDeviceFromLocation(locStr) {
	if (isEmptyStr(locStr)) {
		return "";
	}

	var arr = locStr.split(":");
	var locVal = arr[0];
	if (locVal <= 3) {
		return "";
	} else {
		return arr[1];
	}
}

/*
 * Will get the device group too.
 */
function getVsysFromLocation(locStr) {
	if (isEmptyStr(locStr)) {
		return "";
	}

	var arr = locStr.split(":");
	var locVal = arr[0];
	if (locVal == 16) {
		return arr[2];
	}

	return "";
}

function getDeviceGroupFromLocation(locStr) {
	return getVsysFromLocation(locStr);
}

function getSharedGatewayFromLocation(locStr) {
	return getVsysFromLocation(locStr);
}

function isGlobalLocation(locStr) {
	if (isEmptyStr(locStr)) {
		return false; 
	}

	var arr = locStr.split(":");
	var locVal = arr[0];
	if (locVal <= 3) {
		return true;
	} else {
		return false;
	}
}

function isSharedLocation(locStr) {
	if (isEmptyStr(locStr)) {
		return false; 
	}

	var arr = locStr.split(":");
	var locVal = arr[0];
	if (locVal == 2) {
		return true;
	} else {
		return false;
	}
}

function isCmsPrivateLocation(locStr) {
	if (isEmptyStr(locStr)) {
		return false; 
	}

	var arr = locStr.split(":");
	var locVal = arr[0];
	if (locVal == 512) {
		return true;
	} else {
		return false;
	}
}

function isCmsLocation(locStr) {
	var device = getDeviceFromLocation(locStr);

	if (device == "panorama")
		return true;
	else
		return false;
}

function isVsysGatewayLocation(locStr) {
	if (isEmptyStr(locStr)) {
		return false; 
	}

	var arr = locStr.split(":");
	var locVal = arr[0];
	if (locVal == 16) {
		return true;
	} else {
		return false;
	}
}

function isSharedGatewayLocation(locStr) {
	if (isEmptyStr(locStr)) {
		return false; 
	}

	var arr = locStr.split(":");
	var locVal = arr[0];
	if (locVal == 128) {
		return true;
	} else {
		return false;
	}
}

function getGlobalLocationStr() {
	return "1";
}

function getCustomGlobalLocationStr() {
	return "2";
}

function getCmsPrivateLocationStr() {
	return "512";
}

function getDeviceLocationStr(deviceName) {
	return "8:"+deviceName;
}

function getVsysLocationStr(deviceName, vsysName) {
	return "16:"+deviceName+":"+vsysName;
}

function getSharedGatewayLocationStr(deviceName, sharedGatewayName) {
	return "128:"+deviceName+":"+sharedGatewayName;
}

/*
 * returns "vsys", "sg" or "neither"
 */
function isVsysOrSharedGateway(vsysOrSharedGatewayName) {
	if (vsysOrSharedGatewayName.substr(0,4) == "vsys")
		return "vsys";
 	else if (vsysOrSharedGatewayName.substr(0,2) == "sg")
		return "sg";
	else
		return "neither";
}

function getSharedGatewayLocationStr(deviceName, sharedGatewayName) {
	return "128:"+deviceName+":"+sharedGatewayName;
}

function getVsysOrSharedGatewayLocationStr(deviceName, vsysOrSharedGatewayName) {
	var vsysOrSharedGateway = isVsysOrSharedGateway(vsysOrSharedGatewayName);

	if (vsysOrShareGateway == "sg")
		return getSharedGatewayLocationStr(deviceName, vsysOrSharedGatewayName);
	else
		// not checking for "neither", assuming vsys if not shared gateway - is this ok???
		return getVsysLocationStr(deviceName, vsysOrSharedGatewayName);
		
}

function getCmsLocationStr() {
	return "8:panorama";
}

////////// functions related to clipped table cells ////////////


var selectedObj;		// Global holds reference to selected element
var divWidth;				// Global stores width of clipping DIV
//
function handlerOver(evt) {
	evt = (evt) ? evt : event;
	// Set global reference to the event target
	selectedObj = (evt.target) ? evt.target : evt.srcElement;
	// Determine the target class and take appropriate action
	switch (selectedObj.className) {
		// Clipping DIV elements
		case "tooltip":			// When you mouseover a piece of text in a clipping DIV
			var divObj = selectedObj.parentNode; 				// Determine the enclosing DIV
		divObj.className = "visible"; 							// Change the class name to "visible"
		divWidth = divObj.style.width; 							// Capture the current width
		break;
		// Menu item rollover highlight
		default: 			// When you mouseover anything else
		break;
	}
}
//
function handlerOut(evt) {
	evt = (evt) ? evt : event;
	// Set global reference to the event target
	selectedObj = (evt.target) ? evt.target : evt.srcElement;
	// Determine the target class and take appropriate action
	switch (selectedObj.className) {
		// Clipping DIV elements
		case "tooltip":			// When you mouseout of a piece of text in a clipping DIV
			// Determine the enclosing DIV
			var divObj = selectedObj.parentNode;
		// Restore the class name to "clipping"
		divObj.className = "clipping";
		// Restore the width
		divObj.style.width = divWidth;
		break;
		// Menu item rollover highlight
		default:
		break;
	}
}
//
function initToolTips() {
	// Assign event handlers
	document.onmouseover = handlerOver; 
	document.onmouseout = handlerOut; 
}

//////////////////////////////// end of function related to clipped table cells /////////////

function Coordinates() {
	this._x;
	this._y;
	if (1 != arguments.length && 2 != arguments.length) {
		throw "IllegalArgumentException: Coordinates constructor accepts " +
			"one or two arguments";
	}
	if (1 == arguments.length) {
		var that = arguments[0];
		if (!(that instanceof Coordinates)) {
			throw "IllegalArgumentException: Incorrect usage of Rectangle's " +
				"copy constructor";
		}
		this.setX(that.getX());
		this.setY(that.getY());
	} else {
		this.setX(arguments[0]);
		this.setY(arguments[1]);
	}	
}

/** 
 *	@param number x
 *	@returns void
 */
Coordinates.prototype.setX = function (x) {
	this._x = x;
};

Coordinates.prototype.getX = function () {
	return this._x;
};

/** 
 *	@param number y
 *	@returns void
 */
Coordinates.prototype.setY = function (y) {
	this._y = y;
};

Coordinates.prototype.getY = function () {
	return this._y;
};

function getWindowOpenCoordinates(clickX, clickY, width, height, offsetX, offsetY) {
	var offX = -100;
	if (offsetX != undefined)
		offX = offsetX;

	var offY = -100;
	if (offsetY != undefined)
		offY = offsetY;

	return new Coordinates(clickX+offX, clickY+offY);
}

function openModalWindow(url, name, width, height, clickX, clickY) {

	var openCoord = getWindowOpenCoordinates(clickX, clickY, width, height);

	var newWin;
	if (window.showModalDialog) {
		// NOTE: pass the window as the second parameter so that we can retrieve it in 
		// the opened window.
		var myObject = new Object();
		myObject.openerWindow = window;
		myObject.windowName = name;
		// diaglogHeight and dialogWidth are just the values of the content area and not the
		// top and bottom bars of the window. So going to add 40 to the height and 5 to the width
		//window.showModalDialog(url, myObject, "dialogHeight:"+(height+40)+"px;dialogWidth:"+(width+5)+"px;dialogTop:"+openCoord.getY()+";dialogLeft:"+openCoord.getX()+";resizable:yes;scroll:yes;status:no;help:no;edge:sunken;unadorned:yes");
		try {
			window.showModalDialog(url, myObject, "dialogHeight:"+height+" px;dialogWidth:"+width+" px;dialogTop:"+openCoord.getY()+";dialogLeft:"+openCoord.getX()+";resizable:yes;scroll:yes;status:no;help:no;edge:sunken;unadorned:yes");
		} catch (e) {
		  	alert("Cannot open popup dialog. Please check browser settings.");
			return;
		}
	} else {
		newWin = window.open(url,name, "height="+height+",width="+width+",screenX="+openCoord.getX()+",screenY="+openCoord.getY()+",resizable=yes,toolbar=no,directories=no,status=no,linemenubar=no,scrollbars=yes,modal=yes");
		if (!newWin) {
		  	alert("Cannot open popup dialog. Please check browser settings.");
		  	return;
		}
		newWin.focus();
	}
}

/* For commit and save we will open non modal windows because in IE the timer on the dashboard
 * monitor pages stop as long as a modal window is open. In Firefox though things work ok
 * even in modal window
 */
function openNonModalWindow(url, name, width, height, clickX, clickY) {

	var newWin;
	if (isIEBrowser()) {
		var left = clickX-100;
		if (left < 0)
			left = 0;

		var top = clickY-100;
		if (top < 0)
			top = 0;

		var winWidth = getInsideWindowWidth(); // this is in divManagement_02.js
		var winHeight = getInsideWindowHeight(); // this is in divManagement_02.js

		if ((left + width) > winWidth) {
			left = winWidth - width; 
		}

		if ((top + height) > winHeight) {
			top = winHeight - height;
		}
		newWin = window.open(url,name, "height="+height+",width="+width+",left="+left+",top="+top+",resizable=yes,toolbar=no,directories=no,status=no,linemenubar=no,scrollbars=yes");
		if (!newWin) {
		  alert("Cannot open popup dialog. Please check browser settings.");
		  return;
		}
		newWin.focus();
	} else {
		var openCoord = getWindowOpenCoordinates(clickX, clickY, width, height);
		newWin = window.open(url,name, "height="+height+",width="+width+",screenX="+openCoord.getX()+",screenY="+openCoord.getY()+",resizable=yes,toolbar=no,directories=no,status=no,linemenubar=no,scrollbars=yes");
		if (!newWin) {
		  alert("Cannot open popup dialog. Please check browser settings.");
		  return;
		}
		newWin.focus();
	}
}

function setModalWindowTarget(thisForm) {
	// For modal windows in IE we have to set the window name and form target so that it does not
	// open up another window. For Firefox we do the open with window.open and nothing
	// special needs to be done there.
	if (window.dialogArguments) {
		var windowName = window.dialogArguments.windowName;
		window.name = windowName;
		thisForm.target = windowName;
	}
}

function getOpenerWindow() {
	var openerWindow;
	if (window.dialogArguments) {
		openerWindow = window.dialogArguments.openerWindow;
	} else {
		openerWindow = window.opener;
	}

	return openerWindow;
}

function openerWindowStuffContent(tab, msg) {
	var openerWindow;
	if (window.dialogArguments) {
		openerWindow = window.dialogArguments.openerWindow;
	} else {
		openerWindow = window.opener;
	}
    if (openerWindow && openerWindow.stuffContent) {
	    openerWindow.stuffContent(tab, msg);
    }
}

/**
 * names is an array of variable names
 * values is an array of variable values
 *
 * The called function 'stuffContentWithParams' should use the names and value pairs to
 * do any extra thing.
 *
 * Example usage:
 * This is currently used in addDefaultRuleZoneSelect.esp to call stuffContentWithParams in 
 * policies.esp to determine which policy row should be highlighted
 *
 */ 
function openerWindowStuffContentWithParams(tab, msg, names, values) {
	var openerWindow;
	if (window.dialogArguments) {
		openerWindow = window.dialogArguments.openerWindow;
	} else {
		openerWindow = window.opener;
	}
	openerWindow.stuffContentWithParams(tab, msg, names, values);
}

function openerWindowGoToLogout() {
	var openerWindow;
	if (window.dialogArguments) {
		openerWindow = window.dialogArguments.openerWindow;
	} else {
		openerWindow = window.opener;
	}
	// Bug 23376. Now do a top for safety as some of the old top level pages
	// like device.esp etc. is now in a IFrame in the Ext Framework
	//openerWindow.location.href = "/esp/logout.esp";
	openerWindow.top.location = "/php/logout.php";

	// Just window.close() did not work with Firefox. Giving a sleep
	// seems to make it work.
	setTimeout("window.close();", 100);
}

// if callFunc is true then it will call the function doReload() in 
// the opener window. Had to do it this way because if the opener window
// was opened by showModalDialog(in IE) then reload() does not work
// 
// Sometimes from a popup window we open another popup window to create a new element.
// When the second popup window finished it calls this function and passes the parameters
// fieldName and newFieldValue. The first popup should then select this value in the provided
// field.
function openerWindowReload(callReloadFunc,fieldName,newFieldValue) {
	var openerWindow = getOpenerWindow() || window;
	if (callReloadFunc) {
        // if for some reason the reload function is not defined, dont call it
        if (!openerWindow.doReload) {return};
		if (fieldName != undefined) {
			openerWindow.doReload(fieldName, newFieldValue);
		} else {
			openerWindow.doReload();
		}
	} else {
		openerWindow.location.reload(true);
	}
}

//
// Now sometimes we have popups from popup windows where we are adding/editing
// sets of values (e.g. in the virtual router redistribution profile tab, when a user chooses to
// edit/add a new redistribution profile we open a second popup window. The second popup window
// should call this to pass information back to the original window. It will call the function
// doReloadMultipleParams() in the original window. This function must return "" if things
// are ok or "the error message" on error
// 
// The actionTaken field indicates what was done so that the original window knows. This is 
// because it is possible that there are multiple secondary popups doing different things.
function openerWindowReloadMultipleParams(actionTaken, fieldNamesArray, fieldValuesArray) {
	var openerWindow = getOpenerWindow();

	var errMsg = openerWindow.doReloadMultipleParams(actionTaken, fieldNamesArray, fieldValuesArray);
	return errMsg;
}

function openerWindowLoadUrl(url) {
	var openerWindow;
	if (window.dialogArguments) {
		openerWindow = window.dialogArguments.openerWindow;
	} else {
		openerWindow = window.opener;
	}
	openerWindow.location.href = url;

	// Just window.close() did not work with Firefox. Giving a sleep
	// seems to make it work.
	setTimeout("window.close();", 100);
}




/////////////////////////////// code for modal dialogs /////////////////

/* Not working yet
// KK Once this is working we can move it to ajaxComm.js
function panGetDialogContent(url, handlerFunc)
{
// Use this if you want to use GetXmlHttpObject
gObjXml = GetXmlHttpObject();
// A lot of web sites mention to use open before setting the event listeners.
// e.g. http://www.xulplanet.com/references/objref/XMLHttpRequest.html
gObjXml.open("GET", url, true); 

gObjXml.onreadystatechange = handlerFunc;
// end of of use this for GetXmlHttpObject


// Use this if we want to use KKGetXmlHttpObject
//gObjXml = KKGetXmlHttpObject(url, handlerFunc);

gObjXml.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

gObjXml.send(null);

}

function dialogAjaxHandler() {
var msg;
if (gObjXml.readyState==4) {
if (gObjXml.status == 200) {
// ok
msg = gObjXml.responseText;
//alert(msg);
if (msg.indexOf("__LOGIN_PAGE__") >= 0) {
// HACK: page returned is logout page
window.top.location = "/esp/logout.esp";
} else {
var myobj = document.getElementById("dDialogContent");
// KK Commenting out for now
myobj.innerHTML = msg;
}
} else {
alert(PAN_MSG_1001 + gObjXml.statusText);
}
}
}

function openDialog(url, name, width, height, left, top) {
var dialogObj = document.getElementById("dDialog");
if (dialogObj) {
panGetDialogContent(url, dialogAjaxHandler);

dialogObj.style.display = "block";

dialogObj.style.width = width + "px";
dialogObj.style.height = height + "px";
dialogObj.style.left = left + "px";
dialogObj.style.top = top + "px";

var dialogHeaderObj = document.getElementById("dDialogHeader");
dialogHeaderObj.innerHTML = name;

} else {
alert(PAN_MSG_1002);
}
}

function closeDialog() {
var dialogObj = document.getElementById("dDialog");
if (dialogObj) {

dialogObj.style.display = "none";
} else {
alert(PAN_MSG_1002);
}
}
*/

/////////////////// code for completion radio and checkbox handlers ///////////////

/*
 * Even though we have a two pane version of the multiple selection we still need the
 * single pane version for some pages like (editProfileInRule.esp). There we have 4 such 
 * choices (one for every type of profile and one for profile group) and so there is no place
 * to do a double pane version for each
 */
function completionRadioOnClickHandler(thisForm, radioClicked, cbName) {
	var disableCheckBoxes;
	if (radioClicked.value == "__select__") {
		// user chose to select from a list, enable all the checkboxes
		disableCheckBoxes = false;
	} else {
		disableCheckBoxes = true;
	}

	var str = "document." + thisForm.name + "." + cbName;
	var obj = eval(str);
	if (obj) {
		if (obj.length == undefined) {
			// there is only one checkbox
			obj.disabled = disableCheckBoxes;
		} else {
			for(var i=0; i<obj.length; i++) {
				obj[i].disabled = disableCheckBoxes;
			}
		}
	}
}

/////////////////// end code for completion radio and checkbox handlers ///////////////


/* 
 * See comment in falcon_content.css for style div#dPopupContentRound about why this
 * function is needed
 */
function adjustPopupContentRoundWidth() {
	if (isIEBrowser()) {
		var obj = document.getElementById("dPopupContentRound");
		if (obj) {
			obj.style.width = "100%";
		}
	}
}


function isReadOnlyUser(role, xpath) {
	if (role == "superuser" || role == "deviceadmin" || role == "vsysadmin") {
		return false;
	} else if (role == 'custom') {
        // note, the original logic of isReadOnlyUser assume that 
        // the user is has either full access or readonly permission
        // however, it is possible that the user does not even
        // have readonly permission
        if (!xpath) {
            //debugger
        }
        var permission = Pan.rolebasePermission(xpath);
        return !(permission == "enable");
	} else {
		return true;
    }
}

function setHelpLinkBase(elid, url)
{
	var el = document.getElementById(elid);
	if (el) {
		el.href = url;
	}
}

function setHelpLink(topicid)
{
	var el;
	var url = "/PAN_help/help.html";
	if (topicid) {
		url +=  "?context=PAN_help&topic=" + topicid;
	}

	setHelpLinkBase("ohelp", url);	
	setHelpLinkBase("ohelp_t", url);
}

function formHelpTopic(topic) 
{
	var helptopic;

	helptopic = topic;
	helptopic = helptopic.toLowerCase();
	helptopic = helptopic.replace(/ /g, "_");
	helptopic = helptopic.replace(/-/g, "_");

	return helptopic;
}


function showelement(id)
{
	var el = document.getElementById(id);
	if (!el)
		return;

	if (el.style.display == 'none')
	{
		el.style.display = '';
	} 
}


///////////////////////// functions related to search suggest //////////////////

/**
 * Requirements: 
 *  	- The search text box must have name and id as 'txtSearch'
 *		- The div must be called 'search_suggest'
 *
 *   
 */

/* the current row in the suggest which is highlighted */
var gCurSuggestRow = -1;
var gCurText = "";
/* the total number of such rows */
var gNumSuggestRows = 0;

var gSuggestHighlightBgColor = "#E7E9E8";
var gSuggestNormalBgColor = "#FFFFFF";

/* This should be called when the page having the search text is loaded. It initializes some
 * of the globals
 */
function initSearchSuggest() {
	gCurSuggestRow = -1;
	gNumSuggestRows = 0;
	gCurText = "";
}

/* ascending means we are going upwards in the list */
function highlightSuggestRow(rowNum, ascending) {
	var divObj = document.getElementById("suggest"+rowNum);

	if (divObj) {
		divObj.style.backgroundColor = gSuggestHighlightBgColor;
		// bring that row into view, when ascending we want to show at the top
		divObj.scrollIntoView(ascending);
	}
}

function unhighlightSuggestRow(rowNum) {
	var divObj = document.getElementById("suggest"+rowNum);

	if (divObj)
		divObj.style.backgroundColor = gSuggestNormalBgColor;
}

/* This will fill the text box with the current highlighted suggestion. Probably not used for now */
function showSuggestText(rowNum) {

	var textObj = document.getElementById("txtSearch");
	if (textObj) {
		var divObj = document.getElementById("suggest"+rowNum);

		if (divObj) {
			textObj.value = divObj.innerHTML;
		}
	}
}

function goToNextSuggestRow() {

	// unhighlight the current row only if we have one and it is not the last row
	if (gCurSuggestRow >= 0) {
		if (gCurSuggestRow != gNumSuggestRows-1) {
			unhighlightSuggestRow(gCurSuggestRow);
		}
	}

	// highlight the next row, if it is not the last one
	if (gCurSuggestRow <= gNumSuggestRows-1) {
		gCurSuggestRow++;
		highlightSuggestRow(gCurSuggestRow, false);
	}
}

function goToPreviousSuggestRow() {
	// unhighlight the current row, if it is not the first one
	if (gCurSuggestRow > 0)
		unhighlightSuggestRow(gCurSuggestRow);

	// highlight the previous row, only if it is not the first row
	if (gCurSuggestRow > 0) {
		gCurSuggestRow--;
		highlightSuggestRow(gCurSuggestRow, true);
	}
}

function suggestTextChanged(newText) {
	unhighlightSuggestRow(gCurSuggestRow);

	gCurSuggestRow = -1;


	gCurText = newText;
}

/**
 * This is called on keyup in the search text box 
 *
 * The dataArray should either be an array of strings (in which case pass false
 * as the second param) or should be an array of  objects having a field called 'value' 
 * (in this case pass true as the second parameter)
 *
 * setSearchLocalFunc is the function which will be called 
 * after a selection in the suggest has been made. This function must call
 * setSearch() first and then do its local stuff. If this param is null or undefined then
 * the default setSearch() will be called.
 *
 * NOTE: somehow with IE using setSearchLocalFunc.name returned 'undefined' and so I had to pass
 * the name of the function too (setSearchLocalFuncName).
 *
 * enterKeyFunc - function called when user hits enter in the search text. setSearch/setSearchLocal
 * 				  function will always be called. If this param is not null then it will be called after
 *                that.
 *
 */
function searchSuggest(evt, dataArray, isArrayOfObjects, setSearchLocalFunc, setSearchLocalFuncName, enterKeyFunc) {

	var searchText = trimStr(document.getElementById('txtSearch').value);
	if (searchText != gCurText) {
		// if the text has changed then it cannot be an up/down key
		suggestTextChanged(searchText);
	} else {

		evt = (evt) ? evt : ((event) ? event : null);

		if (evt.keyCode == 38) {
			// key up
			goToPreviousSuggestRow();
			return;
		} else if (evt.keyCode == 40) {
			// key down
			goToNextSuggestRow();
			return;
		} else if (evt.keyCode == 13) {
			// enter
			if (gCurSuggestRow >= 0) {
				var divObj = document.getElementById("suggest"+gCurSuggestRow);
				var str = "";
				if (divObj) {
					str = divObj.innerHTML;
					if (setSearchLocalFunc == undefined ||
							setSearchLocalFunc == null) { 
						setSearch(str);
					} else {
						setSearchLocalFunc(str);
					}
				}

				if (enterKeyFunc != undefined && enterKeyFunc != null) {
					enterKeyFunc();
				}

				return;
			}
		}
	}

	var ss = document.getElementById('search_suggest');
	ss.innerHTML = '';	

	// no need to do anything if there is no text
	if (searchText.length == 0) {
		// get rid of  border on the suggest div
		//ss.style.border = "0px solid #FFFFFF";
		ss.className = "search_suggest_invisible";
		return;
	}

	var chkStr;
	var valueStr;
	var oneSuggestFound = false;
	var idx = 0;
	for(var i=0; i<dataArray.length; i++) {

		if (isArrayOfObjects) {
			valueStr = jsDataLeft[i].value;
		} else {
			valueStr = jsDataLeft[i];
		}

		chkStr = valueStr.substr(0, searchText.length);

		if (chkStr.toLowerCase() == searchText.toLowerCase()) {
			//Build our element string.  This is cleaner using the DOM, but
			//IE does not support dynamically added attributes.
			var suggest = '<div id="suggest' + idx + '" onmouseover="javascript:suggestOver(this);" ';	
			suggest += 'onmouseout="javascript:suggestOut(this);" ';
			if (setSearchLocalFuncName == undefined ||
					setSearchLocalFuncName == null) {
				suggest += 'onclick="javascript:setSearch(this.innerHTML);" ';
			} else {
				suggest += 'onclick="javascript:'+setSearchLocalFuncName+'(this.innerHTML);" ';
			}
			suggest += 'class="suggest_link">' + valueStr + '</div>';	
			ss.innerHTML += suggest;	

			oneSuggestFound = true;
			idx++;
		}
	}

	// idx now has the value of number of suggestions, update the global variable
	gNumSuggestRows = idx;

	if (oneSuggestFound) {
		// put a border on the suggest div
		//document.getElementById("search_suggest").style.border = "1px solid #000000";
		ss.className = "search_suggest_visible";
	} else {
		// get rid of the border
		//document.getElementById("search_suggest").style.border = "0px solid #FFFFFF";
		ss.className = "search_suggest_invisible";
	}

}

// Mouse over function
function suggestOver(div_value) {	
	div_value.className = 'suggest_link_over';
}

// Mouse out function
function suggestOut(div_value) {
	div_value.className = 'suggest_link';
}

// Click function, when user clicks on a choice in the suggest div
function setSearch(value) {	

	document.getElementById('txtSearch').value = value;	
	document.getElementById('search_suggest').innerHTML = '';

	// get rid of  border on the suggest div
	//document.getElementById("search_suggest").style.border = "0px solid #FFFFFF";
	document.getElementById("search_suggest").className = "search_suggest_invisible";

	// reinitialize the globals
	initSearchSuggest();
}


///////////////////////// end of functions related to search suggest /////////////////


////////////////////// functions related to completion dual pane selection /////////////

/**
 * This part makes a lot of assumptions about the names of fields, divs etc. However, this is
 * all taken care of in the function panEspPrintCompletionsMultipleTwoPane().
 */

/* globals one for left pane, one for right pane */
var jsDataLeft = new Array(); // entries of form {ischecked: ..., html: ..., value: ...}
var jsDataRight = new Array(); // entries of form {ischecked: ..., html: ..., value: ...}


/* 
 * get the id of the table body in left pane or right pane 
 *
 * col should be either 'left' or 'right'
 *
 */
function getTableBodyId(col) {
	var tableBodyId;

	if (col == "left") {
		tableBodyId = "tableLeftBody";
	} else {
		tableBodyId = "tableRightBody";
	}

	return tableBodyId;
}

/* 
 * This fills the global arrays jsDataLeft or jsDataRight by reading the stuff from the html 
 * document.
 *
 * col should be either 'left' or 'right'
 *
 */
function fillJSDataFromDoc(col) {

	var jsData = new Array();

	var tableBodyId = getTableBodyId(col); 
	var tbodyObj = document.getElementById(tableBodyId);
	if (!tbodyObj)
		return;

	var cbObj;
	var spObj;
	var suffix = (col == "left")?"Left":"Right";

	for(var i=0; i<tbodyObj.rows.length; i++) {

		cbObj = document.getElementById("cb"+suffix+i);
		spObj = document.getElementById("sp"+suffix+i);

		if (cbObj.checked) {
			jsData[i] = { ischecked: true, html: spObj.innerHTML, value: cbObj.value };
		} else {
			jsData[i] = { ischecked: false, html: spObj.innerHTML, value: cbObj.value };
		}
	}

	if (col == "left") {
		jsDataLeft = jsData;
	} else {
		jsDataRight = jsData;
	}
}

/*
 * Removes the rows of a table
 */
function clearTable(tbodyObj) {
	while ((tbodyObj.rows.length) > 0) {
		tbodyObj.deleteRow(0);
	}
}

/*
 * draws the table on the right pane. It looks at the data in jsDataRight
 */
function drawRightTable() {
	var tbody = getTableBodyId("right"); 
	var jsData = jsDataRight;

	var i, tr, td;
	var val;
	var cbName;

	// Obtain an object reference to the table body
	var tbodyObj = document.getElementById(tbody);
	// remove existing rows, if any
	clearTable(tbodyObj);

	// loop through data source
	for (i = 0; i < jsData.length; i++) {
		tr = tbodyObj.insertRow(tbodyObj.rows.length);
		tr.setAttribute("onclick", "rowCheckboxOrRadioSelect(this,event)");

		// blank cell
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("width", "5px");
		td.innerHTML = "&nbsp;";
		// checkbox
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("width", "20px");

		cbName = "cbRight"+i;
		// Bug 20204 - the checkboxes may have values with <, > etc and so we need to escape it.
		if (jsData[i].ischecked) {
			td.innerHTML = "<input type='checkbox' id='cbRight"+i+"' name='"+cbName+"' value='"+escape(jsData[i].value)+"' onclick=\"cbClicked(this, '"+i+"')\" checked>";
		} else {
			td.innerHTML = "<input type='checkbox' id='cbRight"+i+"' name='"+cbName+"' value='"+escape(jsData[i].value)+"' onclick=\"cbClicked(this, '"+i+"')\">";
		}

		//
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("className", "listNoSideBorder");
		td.setAttribute("class", "listNoSideBorder");
		td.innerHTML = jsData[i].html;
	}
}

/*
 * called from addToRightPane(). After adding to the right pane we need 
 * to unhighlight the rows from the left pane
 */
function unHighlightLeftRow(rowNum) {
	var cbObj = document.getElementById("cbLeft"+rowNum);
	var rowObj = getFirstAncestorByHtmlTag(cbObj, "tr");
	rowObj.style.backgroundColor = "";
}

/*
 * This is called when a checkbox on either the left or right column is clicked. We figure
 * out which column from the checkbox name (left column ones start with 'cbLeft' and the right
 * column ones start with 'cbRight');
 */
function cbClicked(cbObj, rowNum) {

	var cbId = cbObj.id;

	selectRow(cbObj);

	if (cbId.substr(0, 6) == "cbLeft") {
		if (cbObj.checked)
			jsDataLeft[rowNum].ischecked = true;
		else
			jsDataLeft[rowNum].ischecked = false;
	} else {
		if (cbObj.checked)
			jsDataRight[rowNum].ischecked = true;
		else
			jsDataRight[rowNum].ischecked = false;
	}

	// If user clicks on any of the checkboxes in the left/right pane we want the __select__
	// radio button to be selected.
	var formObj = getFirstAncestorByHtmlTag(cbObj, "form");
	checkMatchingRadioOrCheckbox(formObj.name, "rOpt", "__select__");
}

/*
 * add the data from row 'rowNum' of the left pane to the right pane. Add only if it
 * is not already in the right pane.
 *
 * This function just addes to the end of the right data. We will sort it later.
 */
function addRowDataToRight(rowNum) {
	var leftRowData = jsDataLeft[rowNum];

	// add to the end, we will sort later
	for(var j=0; j<jsDataRight.length; j++) {
		if (jsDataRight[j].value == leftRowData.value) {
			// it is already there on the right side, no need to do anything
			return;
		}
	}

	// add it to the end
	jsDataRight[jsDataRight.length] = leftRowData;

	// uncheck it
	jsDataRight[jsDataRight.length-1].ischecked = false;

}

/*
 * Function to determine what to sort by since the members of each array are objects
 */
function dataSortFunc(a, b) {
	var retval = 0;

	if (a.value < b.value)
		retval = -1;
	else if (a.value > b.value)
		retval = 1;

	return retval;
}

/*
 * This is called when the user clicks on the "Add >" button to add selected choices to 
 * the right pane
 */
function addToRightPane() {

	var tableBodyId = getTableBodyId('left'); 
	var tbodyObj = document.getElementById(tableBodyId);

	for(var i=0; i < jsDataLeft.length; i++) {
		if (jsDataLeft[i].ischecked) {
			// add it to the right hand side array

			// only consider the checks which are enabled. The disabled ones are already in 
			// the right side
			var cbObj = document.getElementById("cbLeft"+i);
			if (!cbObj.disabled) {
				addRowDataToRight(i);

				// disable the checkbox from the left hand table and unhighlight it
				cbObj.disabled = true;
				unHighlightLeftRow(i);
			}
		}
	}

	// sort the right table data
	jsDataRight.sort(dataSortFunc);

	// now draw the right table
	drawRightTable();

	// if there is a search text box then clear it
	var txtSearchObj = document.getElementById("txtSearch");
	if (txtSearchObj) {
		txtSearchObj.value = '';
	}
}

/*
 * sometimes from a popup window a new object is created (in a second popup window). when the second popup
 * is closed then the object created must be added to the right side of the two pane selection too.
 *
 * This function should be called in such cases
 */
function addDataToRightPane(thisForm, newValue) {
	//alert("addDataToRightPane: form: " + thisForm.name + ", newValue: " + newValue);

	var tableBodyId = getTableBodyId('left'); 
	var tbodyObj = document.getElementById(tableBodyId);

	for(var i=0; i < jsDataLeft.length; i++) {
		if (jsDataLeft[i].value == newValue) {
			// add it to the right hand side array

			addRowDataToRight(i);

			var cbObj = document.getElementById("cbLeft"+i);
			// disable the checkbox from the left hand table and unhighlight it
			cbObj.disabled = true;
			unHighlightLeftRow(i);

			// also need to check it on the left side
			cbObj.checked = true;
			jsDataLeft[i].ischecked = true;
		}
	}

	// sort the right table data
	jsDataRight.sort(dataSortFunc);

	// now draw the right table
	drawRightTable();

	// we also want to make "__select__" the selected option
	checkMatchingRadioOrCheckbox(thisForm.name, "rOpt", "__select__");
}

/*
 * This takes the data in dataArray and puts it in the right pane. This should be called when
 * on form submit we get an error and we need to revert to what the user had changed before submitting
 * the form
 */
function initRightPaneData(dataArray) {

	var cbObj;

	for(var i=0; i<dataArray.length; i++) {
		for(var j=0; j<jsDataLeft.length; j++) {
			if (jsDataLeft[j].value == dataArray[i]) {
				addRowDataToRight(j);

				// disable the checkbox from the left hand table and unhighlight it, also 
				// mark it as checked since it has moved to the right
				cbObj = document.getElementById("cbLeft"+j);
				cbObj.disabled = true;
				cbObj.checked = true;
				unHighlightLeftRow(j);
			}
		}
	}

	// sort the right table data
	jsDataRight.sort(dataSortFunc);

	// now draw the right table
	drawRightTable();

}

/*
 * This function decides on which divs to show and what to enable/disable 
 */
function completionSelectHandler(thisForm, radioName) {
	var twoPaneSelectChosen = false;

	var selObj = eval("document." + thisForm.name + "." + radioName);
	if (!selObj) {
		// sometimes the select menu will not be present when there is only one select option, but
		// this is as if the user chose to select from the two panes
		twoPaneSelectChosen = true;
	} else {
		var selectValue = getCheckedValues(thisForm.name, radioName, "");
		if (selectValue == "__select__") {
			twoPaneSelectChosen = true;
		}
	}

	if (twoPaneSelectChosen) {
		document.getElementById("bAdd").disabled = false;
		document.getElementById("bRemove").disabled = false;

		// you have to only enable those which are not already on right side
		var found;
		for(var i=0; i<jsDataLeft.length; i++) {
			found = false;
			for(var j=0; j<jsDataRight.length; j++) {
				if (jsDataLeft[i].value == jsDataRight[j].value) {
					found = true;
					break; // out of for j loop
				}
			}
			if (!found) 
				document.getElementById("cbLeft"+i).disabled = false;
		}

		for(var i=0; i<jsDataRight.length; i++) {
			document.getElementById("cbRight"+i).disabled = false;
		}

		var txtSearchObj = document.getElementById('txtSearch');
		if (txtSearchObj)
			//txtSearchObj.disabled = false;
			disableTextFld(txtSearchObj, false);

		document.getElementById('divLeft').style.backgroundColor = "#FFFFFF";
		// This does not seem to work
		//document.getElementById('divLeft').style.color = "#000000";
		document.getElementById('tableLeftBody').style.color = "#000000";

		document.getElementById('divRight').style.backgroundColor = "#FFFFFF";
		// This does not seem to work
		//document.getElementById('divRight').style.color = "#000000";
		document.getElementById('tableRightBody').style.color = "#000000";
	} else {
		/* KK Now that we have radio buttons for the keywords instead of a dropdown we do not want
		 * to disable this part. We want the user to be able to select something here and then we 
		 * will make __select__ the selected radio button
		 document.getElementById("bAdd").disabled = true;
		 document.getElementById("bRemove").disabled = true;

		 for(var i=0; i<jsDataLeft.length; i++) {
		 document.getElementById("cbLeft"+i).disabled = true;
		 }

		 for(var i=0; i<jsDataRight.length; i++) {
		 document.getElementById("cbRight"+i).disabled = true;
		 }

		 var txtSearchObj = document.getElementById('txtSearch');
		 if (txtSearchObj)
		//txtSearchObj.disabled = true;
		disableTextFld(txtSearchObj, true);

		document.getElementById('divLeft').style.backgroundColor = "#F3F3F3";
		// This does not seem to work
		//document.getElementById('divLeft').style.color = "#C0C0C0";
		document.getElementById('tableLeftBody').style.color = "#C0C0C0";

		document.getElementById('divRight').style.backgroundColor = "#F3F3F3";
		// This does not seem to work
		//document.getElementById('divRight').style.color = "#C0C0C0";
		document.getElementById('tableRightBody').style.color = "#C0C0C0";
		 */
	}
}

/* 
 * called from removeFromRightPane.
 *
 * it enables the left checkbox after the item is removed from the right pane
 */
function enableLeftCheckbox(value) {
	var cbObj;
	for(var i=0; i<jsDataLeft.length; i++) {
		if (jsDataLeft[i].value == value) {
			cbObj = document.getElementById("cbLeft"+i);
			cbObj.disabled = false;

			// uncheck checkbox and the javascript data.
			cbObj.checked = false;
			jsDataLeft[i].ischecked = false;

			break;
		}
	}
}

/*
 * called when the user clicks on the "< Remove" button
 *
 * delete data from the right pane. 
 */
function removeFromRightPane() {

	// delete one row at a time until all rows to delete have been deleted.
	var idx = rowNumStillToDelete(jsDataRight);
	while(idx >= 0) {
		// we also need to enable the item in the left side
		enableLeftCheckbox(jsDataRight[idx].value);

		jsDataRight.splice(idx,1);

		idx = rowNumStillToDelete(jsDataRight);
	}

	drawRightTable();
}

/* 
 * returns -1 if there is no more rows to delete or returns the index of the 
 */
function rowNumStillToDelete(jsData) {
	var retval = -1;
	for(var i=0; i<jsData.length; i++) {
		if (jsData[i].ischecked) {
			retval = i;
			break;
		}
	}

	return retval;
}

/*
 * returns the string for the selected values separated by the 'separator'
 */
function getTwoPaneSelectedValues(formName, radioName, separator) {
	var str = "";

	// if there is only one entry in the select option (i.e. __select__) we do not show it. 
	// So here we check for it
	var str = "document." + formName + "." + radioName;
	var selectObj = eval(str);
	str = ""; // reset str
	var selectFromRightPane = false;

	if (!selectObj) {
		selectFromRightPane = true;
	} else {
		//var optValue = getMultipleSelectedValues(formName, radioName, "");
		var optValue = getCheckedValues(formName, radioName, "");
		if (optValue.length > 0) {
			if (optValue == "__select__") {
				selectFromRightPane = true;
			} else {
				str = optValue; // user did not choose __select__ but chose another select option
			}
		}
	}

	if (selectFromRightPane) {
		// user chose to select from a list
		str = "";
		for(var i=0; i<jsDataRight.length; i++) {
			if (i > 0)
				str += separator;

			str += jsDataRight[i].value;
		}
	}

	return str;
}


/*
 * When the user selects a choice from the search suggest, then we need to some specific
 * stuff for the dual pane completions like selecting it in the left pane etc. This is
 * done here
 */
function setSearchTwoPane(value) {	

	// must call the default first
	setSearch(value);

	// then do the local stuff
	// highlight it and select it on the left pane
	var cbObj;
	for(var i=0; i<jsDataLeft.length; i++) {
		if (jsDataLeft[i].value == value) {
			jsDataLeft[i].ischecked = true;

			cbObj = document.getElementById("cbLeft"+i);
			// need to check it first before calling selectRow to get the highlighting
			cbObj.checked = true;
			selectRow(cbObj);

			// also scroll to that row
			cbObj.scrollIntoView(true);
		}
	}

}

///////////////////// end of functions related to completion two pane selection //////////

/* for some tables (where we use completions) we have a radio/checkbox in the first 
 * column and then the value in other columns. We want to be able to click on the
 * value cell to select the radio/checkbox
 */
function rowCheckboxOrRadioSelect(rowObj, evt) {

	evt = (evt) ? evt : event;

	var i;

	//debugger;
	var cellObj;
	var cellContentObj;
	for(i=0; i<rowObj.cells.length; i++) {
		cellObj = rowObj.cells[i];
		cellContentObj = cellObj.firstChild;
		if (cellContentObj.type == "radio") {
			// for radio button always select
			if (!cellContentObj.disabled)
				//cellContentObj.checked = true;
				cellContentObj.click(); // simulate click on the radio button

			break;
		} else if ( cellContentObj.type == "checkbox") {
			// for checkbox we need to see if the user clicked on the checkbox or another row in
			// the table. If he clicked on the checkbox then we dont need to do anything as 
			// the browser will take care of checking/unchecking. We will just undo it if we do anything
			// here
			var targetObj = evt.target;
			if (!targetObj) {
				targetObj = evt.srcElement; // for IE
			}
			if (targetObj && targetObj.type == "checkbox") {
				// do nothing
			} else {
				// for checkbox toggle
				if (!cellContentObj.disabled) {
					cellContentObj.click(); // simulate click on the checkbox
					/*
					   if (cellContentObj.checked)
					   cellContentObj.checked = false;
					   else
					   cellContentObj.checked = true;
					 */
				}
			}

			break;
		}
	}
}

//////////////////// functions for dealing with and highlighting rows in tables on policies page/interfaces and other pages ///////

// Our logic requires these to be different, so we will make it slightly different
/* Changed for 4.0
var gTableRowClickHighlightColor = "rgb(255,238,153)"; // #FFee99
var gTableRowMouseHighlightColor = "rgb(255,237,153)"; 
*/
var gTableRowClickHighlightColor = "rgb(198,216,229)"; // #B6C8D5
var gTableRowMouseHighlightColor = "rgb(182,200,213)"; 

// going to keep track of the currently clicked row so that we can go to that row after an edit or 
// something (it will be the name of the rule like 'rule1'). In contextMenus.js there is a global variable
// gObjIdClicked but that stores thing like (luRuleMenu:rule1) i.e. the context menu and the rule name. 
// That is needed for the right click context sensitive menus. I am using another one here so that for
// non context sensitive cells (only the first column is context sensitive now, others are not) we 
// can keep track of the row clicked.
var gTableRowClickedId=""; 

// This will keep track of the state of the row tracked by gTableRowClickedId
var gTableRowClickedStateHighlighted = false;

function resetCurrentClickedRow() {
	gTableRowClickedId = "";
	gTableRowClickedStateHighlighted = false;
}

/*
 * Sometimes a row which is currently highlighted may need to be re-highlighted especially if the
 * page gets automatically refreshed.
 */
function reHighlightCurrentRow() {
	// KK Test
	//alert("reHighlightCurrentRow(): gTableRowClickedId " + gTableRowClickedId + ", gTableRowClickedStateHighlighted " + gTableRowClickedStateHighlighted);
	// end KK Test
	if (gTableRowClickedId.length > 0) {

		// make sure row is there
		var rowObj = document.getElementById(gTableRowClickedId);
		if (!rowObj) {
			// KK Test
			//alert("reHighlightCurrentRow(): could NOT find row with id " + gTableRowClickedId);
			// end KK Test
			return;
		}

		if (gTableRowClickedStateHighlighted)
			// if it was already highlighted then keep it highlighted
			highlightTableRow(gTableRowClickedId);
		else
			// if it was not highlighted then keep it unhighlighted
			unhighlightTableRow(gTableRowClickedId);
	}
}

// this will highlight or unhighlight i.e. make the row toggle
//
// NOTE: Does not change global variables gTableRowClickedId and gTableRowClickedStateHighlighted
function highlightUnhighlightTableRow(rowId) {
	// KK Test
	//alert("in highlightUnhighlightTableRow("+rowId+"), gTableRowClickedId:\n" + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	//printStackTrace();
	// end KK Test

	var rowObj = document.getElementById(rowId);
	if (!rowObj) {
		// KK Test
		//alert("in highlightUnhighlightTableRow("+rowId+"), COULD NOT FIND row returning"); 
		// end KK Test
		return;
	}

	// even if you give the color as #FFFF00 the browsers store it in rgb format. Also IE gets rid of 
	// the spaces i.e. rgb(255, 255, 0) becomes rgb(255,255,0). Firefox seems to always store it with the 
	// spaces (even if I set it with values without spaces).
	var bgColor = rowObj.style.backgroundColor;
	bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

	if (bgColor == gTableRowClickHighlightColor) {
		rowObj.style.backgroundColor = ""; // set back to normal
		// if we unhighlight then we need to reset the global variable
		// KK Bug Fix 8678 - we should not reset the global variable because that
		// is still the currently clicked row. It will get changed when the user
		// clicks on another row. 
		// resetCurrentClickedRow();
		// end KK 
	} else {
		rowObj.style.backgroundColor = gTableRowClickHighlightColor;  // this is the click color
	}
}

// This returns true if the row is currently highlighted
//
// NOTE: Does not change global variables gTableRowClickedId and gTableRowClickedStateHighlighted
function isRowHighlighted(rowId) {
	var rowObj = document.getElementById(rowId);
	if (!rowObj) {
		return;
	}

	// even if you give the color as #FFFF00 the browsers store it in rgb format. Also IE gets rid of 
	// the spaces i.e. rgb(255, 255, 0) becomes rgb(255,255,0). Firefox seems to always store it with the 
	// spaces (even if I set it with values without spaces).
	var bgColor = rowObj.style.backgroundColor;
	bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

	if (bgColor == gTableRowClickHighlightColor) {
		return true;
	} else {
		return false;
	}
}

// This will highlight the row always
function highlightTableRow(rowId) {
	// KK Test
	//alert("in highlightTableRow("+rowId+"), gTableRowClickedId: " + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	//printStackTrace();
	// end KK Test

	var rowObj = document.getElementById(rowId);
	if (!rowObj) {
		return;
	}

	rowObj.style.backgroundColor = gTableRowClickHighlightColor;  // this is the click color
	// set the global variable and state
	gTableRowClickedId = rowId;
	gTableRowClickedStateHighlighted = true;

	// KK Test
	//alert("in highlightTableRow("+rowId+") END, gTableRowClickedId: " + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	// end KK Test
}

// This will unhighlight the row always
function unhighlightTableRow(rowId) {

	// KK Test
	//alert("in unhighlightTableRow("+rowId+"), gTableRowClickedId: " + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	//printStackTrace();
	// end KK Test

	var rowObj = document.getElementById(rowId);
	if (!rowObj) {
		return;
	}

	rowObj.style.backgroundColor = "";
	// set the global variable and state
	gTableRowClickedId = rowId;
	gTableRowClickedStateHighlighted = false;
}


// NOTE: Does not change global variables gTableRowClickedId and gTableRowClickedStateHighlighted
function unhighlightOtherTableRows(rowId) {

	// KK Test
	//alert("in unhighlightOtherTableRows("+rowId+"), gTableRowClickedId: " + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	//printStackTrace();
	// end KK Test

	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	var tbodyOrTableObj = getFirstAncestorByHtmlTag(rowObj, "tbody");
	if (!tbodyOrTableObj) {
		// no tbody, check if there is a table obj
		tbodyOrTableObj = getFirstAncestorByHtmlTag(rowObj, "table");

		if (!tbodyOrTableObj)
			return;
	}

	for(var i=0; i<tbodyOrTableObj.rows.length; i++) {
		rowObj = tbodyOrTableObj.rows[i];
		if (rowObj.id != rowId)
			rowObj.style.backgroundColor = ""; // set back to normal
	}

}

/*
 * This will highlight the given rowId and unhighlight the others in the table
 */
function highlightTableOnlyThisRow(rowId) {
	unhighlightOtherTableRows(rowId);
	highlightTableRow(rowId);
}

function scrollToTableRow(rowId, showAtTop) {
	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	// set the showAtTop or things do not work with FF 3.0
	if (showAtTop == undefined || showAtTop == null)
		showAtTop = false;

	rowObj.scrollIntoView(showAtTop);
}

function mouseClickedTableRow(rowId) {
	gTableRowClickedId = rowId;

	// then highlight or unhiglight this row
	highlightUnhighlightTableRow(rowId);

	// set the global variable gTableRowClickedStateHighlighted depending on whether the row got 
	// highlighted or not
	if (isRowHighlighted(rowId)) 
		gTableRowClickedStateHighlighted = true;
	else
		gTableRowClickedStateHighlighted = false;

	// unhighlight all the other rows in the table, this has to be after the
	// highlight
	unhighlightOtherTableRows(rowId);

}

function mouseOverTableRow(rowId) {
	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	var bgColor = rowObj.style.backgroundColor;
	bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

	if (bgColor == gTableRowClickHighlightColor) {
		// already highlihted with click color, do nothing
	} else {
		rowObj.style.backgroundColor = gTableRowMouseHighlightColor;
	}
}

function mouseOutTableRow(rowId) {
	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	var bgColor = rowObj.style.backgroundColor;
	bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

	if (bgColor == gTableRowClickHighlightColor) {
		// already highlihted do nothing
	} else {
		rowObj.style.backgroundColor = "";
	}
}

// can return "" if there is no next row
function getNextTableRowId(rowId) {
	var tbodyObj = document.getElementById("panTableBody");
	if (!tbodyObj) {
		// try bodyScrollingTable 
		tbodyObj = document.getElementById("bodyScrollingTable");
	}
	if (!tbodyObj)
		return "";

	var numRows = tbodyObj.rows.length;
	var i;
	for(i=0; i<numRows; i++) {
		if (tbodyObj.rows[i].id == rowId)
			break;
	}

	// i is index of the row with id 'rowId'. We want the next row.
	i++;
	if (i < numRows) {
		return tbodyObj.rows[i].id;
	} else {
		return "";
	}
}

// can return "" if there is no previous row
function getPreviousTableRowId(rowId) {
	var tbodyObj = document.getElementById("panTableBody");
	if (!tbodyObj) {
		// try bodyScrollingTable 
		tbodyObj = document.getElementById("bodyScrollingTable");
	}
	if (!tbodyObj)
		return "";

	var numRows = tbodyObj.rows.length;
	var i;
	for(i=0; i<numRows; i++) {
		if (tbodyObj.rows[i].id == rowId)
			break;
	}

	// i is index of the row with id 'rowId'. We want the previous row.
	i--;
	if (i >= 0) {
		return tbodyObj.rows[i].id;
	} else {
		return "";
	}
}

// can return "" if there is no entries
function getFirstTableRowId() {
	var tbodyObj = document.getElementById("panTableBody");
	if (!tbodyObj) {
		// try bodyScrollingTable 
		tbodyObj = document.getElementById("bodyScrollingTable");
	}
	if (!tbodyObj)
		return "";

	var numRows = tbodyObj.rows.length;

	if (numRows == 0)
		return "";

	return tbodyObj.rows[0].id;
}

// can return "" if there is no entries
function getLastTableRowId() {
	var tbodyObj = document.getElementById("panTableBody");
	if (!tbodyObj) {
		// try bodyScrollingTable 
		tbodyObj = document.getElementById("bodyScrollingTable");
	}
	if (!tbodyObj)
		return "";

	var numRows = tbodyObj.rows.length;

	if (numRows == 0)
		return "";

	return tbodyObj.rows[numRows-1].id;
}

/*
 * Sometimes a row group which is currently highlighted may need to be re-highlighted especially if the
 * page gets automatically refreshed.
 */
function reHighlightCurrentRowGroup() {
	// KK Test
	//alert("reHighlightCurrentRowGroup(): gTableRowClickedId " + gTableRowClickedId + ", gTableRowClickedStateHighlighted " + gTableRowClickedStateHighlighted);
	// end KK Test
	if (gTableRowClickedId.length > 0) {

		// make sure row is there
		var rowObj = document.getElementById(gTableRowClickedId);
		if (!rowObj) {
			// KK Test
			//alert("reHighlightCurrentRow(): could NOT find row with id " + gTableRowClickedId);
			// end KK Test
			return;
		}

		if (gTableRowClickedStateHighlighted)
			// if it was already highlighted then keep it highlighted
			highlightTableRowGroup(gTableRowClickedId);
		else
			// if it was not highlighted then keep it unhighlighted
			unhighlightTableRowGroup(gTableRowClickedId);
	}
}

// sometimes we can have multiple rows theoretically grouped as one row (like for system config, each
// of the logsets is one row in the table but we should highlight and group the whole thing).
// for such things the rowIds will be id (NOTE: no ;0 here), id;1, id;2 and so on. 
// I use ; as the delimiter because it cannot be used in an object name.

// this will highlight/unhighlight i.e. toggle the row
// 
// NOTE: Does not change global variables gTableRowClickedId and gTableRowClickedStateHighlighted
function highlightUnhighlightTableRowGroup(rId) {

	// KK Test
	//alert("in highlightUnhighlightTableRowGroup("+rId+"), gTableRowClickedId:\n" + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	//printStackTrace();
	// end KK Test

	// we want to get the 'pure' part of the rowId i.e. the part before the ; and work with that.
	var rowArray = rId.split(";");
	var rowId = rowArray[0];

	var rowObj = document.getElementById(rowId);
	if (!rowObj) {
		// KK Test
		//alert("in highlightUnhighlightTableRowGroup("+rId+"), could NOT FIND ROW");
		//printStackTrace();
		// end KK Test
		return;
	}

	// even if you give the color as #FFFF00 the browsers store it in rgb format. Also IE gets rid of 
	// the spaces i.e. rgb(255, 255, 0) becomes rgb(255,255,0). Firefox seems to always store it with the 
	// spaces (even if I set it with values without spaces).
	var bgColor = rowObj.style.backgroundColor;
	bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

	if (bgColor == gTableRowClickHighlightColor) {
		rowObj.style.backgroundColor = ""; // set back to normal
		// reset the global variable
		// KK Bug Fix 8678 - we should not reset the global variable because that
		// is still the currently clicked row. It will get changed when the user
		// clicks on another row. 
		//resetCurrentClickedRow();
		// end KK 
	} else {
		rowObj.style.backgroundColor = gTableRowClickHighlightColor;  // this is the click color
	}

	// do the other rows in the group
	var idx = 1;
	var keepGoing = true;
	while(keepGoing) {
		rowObj = document.getElementById(rowId + ";" + idx);

		if (!rowObj) {
			// no more left in group
			keepGoing = false;
			return;
		}

		// even if you give the color as #FFFF00 the browsers store it in rgb format. Also IE gets rid of 
		// the spaces i.e. rgb(255, 255, 0) becomes rgb(255,255,0). Firefox seems to always store it with the 
		// spaces (even if I set it with values without spaces).
		bgColor = rowObj.style.backgroundColor;
		bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

		if (bgColor == gTableRowClickHighlightColor) {
			rowObj.style.backgroundColor = ""; // set back to normal
		} else {
			rowObj.style.backgroundColor = gTableRowClickHighlightColor;  // this is the click color
		}

		idx++;
	}
}

// this will highlight the row always
function highlightTableRowGroup(rId) {

	// KK Test
	//alert("in highlightTableRowGroup("+rId+"), gTableRowClickedId:\n" + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	//printStackTrace();
	// end KK Test

	// we want to get the 'pure' part of the rowId i.e. the part before the ; and work with that.
	var rowArray = rId.split(";");
	var rowId = rowArray[0];

	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	rowObj.style.backgroundColor = gTableRowClickHighlightColor;  // this is the click color
	// set the global variable and state
	gTableRowClickedId = rowId;
	gTableRowClickedStateHighlighted = true;

	// do the other rows in the group
	var idx = 1;
	var keepGoing = true;
	while(keepGoing) {
		rowObj = document.getElementById(rowId + ";" + idx);

		if (!rowObj) {
			// no more left in group
			keepGoing = false;
			return;
		}

		rowObj.style.backgroundColor = gTableRowClickHighlightColor;  // this is the click color

		idx++;
	}
}

// this will unhighlight the row always
function unhighlightTableRowGroup(rId) {

	// KK Test
	//alert("in unhighlightTableRowGroup("+rId+"), gTableRowClickedId:\n" + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	//printStackTrace();
	// end KK Test

	// we want to get the 'pure' part of the rowId i.e. the part before the ; and work with that.
	var rowArray = rId.split(";");
	var rowId = rowArray[0];

	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	rowObj.style.backgroundColor = "";
	// set the global variable and state
	gTableRowClickedId = rowId;
	gTableRowClickedStateHighlighted = false;

	// do the other rows in the group
	var idx = 1;
	var keepGoing = true;
	while(keepGoing) {
		rowObj = document.getElementById(rowId + ";" + idx);

		if (!rowObj) {
			// no more left in group
			keepGoing = false;
			return;
		}

		rowObj.style.backgroundColor = "";

		idx++;
	}
}
/* The rowId expected here is the 'pure' part that is before the ; (NOTE: id's of the group 
 * is of the form id, id;1, id;2, ... and so on
 */
function unhighlightOtherTableRowsGroup(rowId) {

	// KK Test
	//alert("in unhighlightOtherTableRowsGroup("+rId+"), gTableRowClickedId:\n" + gTableRowClickedId + ", gTableRowClickedStateHighlighted: " + gTableRowClickedStateHighlighted);
	//printStackTrace();
	// end KK Test

	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	var tbodyOrTableObj = getFirstAncestorByHtmlTag(rowObj, "tbody");
	if (!tbodyOrTableObj) {
		// no tbody, check if there is a table obj
		tbodyOrTableObj = getFirstAncestorByHtmlTag(rowObj, "table");

		if (!tbodyOrTableObj)
			return;
	}

	for(var i=0; i<tbodyOrTableObj.rows.length; i++) {
		rowObj = tbodyOrTableObj.rows[i];
		var rowArray = rowObj.id.split(";");
		var pureRowId = rowArray[0];
		if (pureRowId != rowId) {
			rowObj.style.backgroundColor = ""; // set back to normal

			// No need to do other rows in the group here because we will come to them anyway
			// in the 'i' loop
		}
	}

}

/*
 * This will highlight the given rowId and unhighlight the others in the table
 */
function highlightTableOnlyThisRowGroup(rowId) {
	unhighlightOtherTableRowsGroup(rowId);
	highlightTableRowGroup(rowId);
}

function scrollToTableRowGroup(rId, showAtTop) {
	// we want to get the 'pure' part of the rowId i.e. the part before the ; and work with that.
	var rowArray = rId.split(";");
	var rowId = rowArray[0];

	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	rowObj.scrollIntoView(showAtTop);
}

function mouseClickedTableRowGroup(rId) {

	// we want to get the 'pure' part of the rowId i.e. the part before the ; and work with that.
	var rowArray = rId.split(";");
	var rowId = rowArray[0];

	gTableRowClickedId = rowId;

	highlightUnhighlightTableRowGroup(rowId);

	// set the global variable gTableRowClickedStateHighlighted depending on whether the row got 
	// highlighted or not
	if (isRowHighlighted(rowId)) 
		gTableRowClickedStateHighlighted = true;
	else
		gTableRowClickedStateHighlighted = false;

	// unhighlight all the other rows in the table, this has to be after the highlight
	unhighlightOtherTableRowsGroup(rowId);

}

function mouseOverTableRowGroup(rId) {

	// we want to get the 'pure' part of the rowId i.e. the part before the ; and work with that.
	var rowArray = rId.split(";");
	var rowId = rowArray[0];

	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	var bgColor = rowObj.style.backgroundColor;
	bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

	if (bgColor == gTableRowClickHighlightColor) {
		// already highlihted with click color, do nothing
	} else {
		rowObj.style.backgroundColor = gTableRowMouseHighlightColor;
	}

	// do the other rows in the group
	var idx = 1;
	var keepGoing = true;
	while(keepGoing) {
		rowObj = document.getElementById(rowId + ";" + idx);

		if (!rowObj) {
			// no more left in group
			keepGoing = false;
			return;
		}

		// even if you give the color as #FFFF00 the browsers store it in rgb format. Also IE gets rid of 
		// the spaces i.e. rgb(255, 255, 0) becomes rgb(255,255,0). Firefox seems to always store it with the 
		// spaces (even if I set it with values without spaces).
		bgColor = rowObj.style.backgroundColor;
		bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

		if (bgColor == gTableRowClickHighlightColor) {
			// already highlihted with click color, do nothing
		} else {
			rowObj.style.backgroundColor = gTableRowMouseHighlightColor;
		}

		idx++;
	}

}

function mouseOutTableRowGroup(rId) {
	// we want to get the 'pure' part of the rowId i.e. the part before the ; and work with that.
	var rowArray = rId.split(";");
	var rowId = rowArray[0];

	var rowObj = document.getElementById(rowId);
	if (!rowObj)
		return;

	var bgColor = rowObj.style.backgroundColor;
	bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

	if (bgColor == gTableRowClickHighlightColor) {
		// already highlihted do nothing
	} else {
		rowObj.style.backgroundColor = "";
	}

	// do the other rows in the group
	var idx = 1;
	var keepGoing = true;
	while(keepGoing) {
		rowObj = document.getElementById(rowId + ";" + idx);

		if (!rowObj) {
			// no more left in group
			keepGoing = false;
			return;
		}

		bgColor = rowObj.style.backgroundColor;
		bgColor = bgColor.replace(/\s/g, ""); // get rid of the blanks

		if (bgColor == gTableRowClickHighlightColor) {
			// already highlihted do nothing
		} else {
			rowObj.style.backgroundColor = "";
		}

		idx++;
	};
}

////////////////// end of functions dealing with and highlighting rows on the policy page ///////////////

function scrollPageTop(divname)
{
	var el = document.getElementById(divname);
	if (el) {
		el.scrollIntoView(true);
	}
}



////////////////// stuff for getting pending config changes //////////////////

// the global variable gPendingChangeObjXml is defined in ajaxComm.js
function ajaxPendingConfigHandler() {
	var msg;
	try {
		// In firefox you we sometimes get a NS_ERROR_NOT_AVAILABLE (0x80040111). In
		// that case try again after X secs
		if (gPendingChangeObjXml.readyState==4) {
			if (gPendingChangeObjXml.status == 200) {
				// ok
				msg = gPendingChangeObjXml.responseText;
				//alert("ajaxPendingConfigHandler: " + msg);

				handlePendingConfigResp(msg);
			} else {
				// on error do not do anything.
			}
		}
	} catch (e) {
		// retry one time after 100 msecs
		setTimeout("checkPendingConfigChangeRetry()", 100);
	}
}

function ajaxPendingConfigHandlerRetry() {
	var msg;
	try {
		if (gPendingChangeObjXml.readyState==4) {
			if (gPendingChangeObjXml.status == 200) {
				// ok
				msg = gPendingChangeObjXml.responseText;
				//alert("ajaxPendingConfigHandler: " + msg);

				handlePendingConfigResp(msg);
			} else {
				// on error do not do anything.
			}
		}
	} catch (e) {
		// no retries do nothing
	}
}

function checkPendingConfigChangeRetry() {
	var url = "/esp/configPendingChanges.esp";

	// this function is in ajaxComm.js
	panPendingConfigChangeGetContent(url, ajaxPendingConfigHandlerRetry);
}

// called from ajaxPendingConfigHandler when resp received
function handlePendingConfigResp(data) {
	// the dCommitImg div is in header.esp and is not present for vsys admins etc. so 
	// check to see if it is present
	var divCommitImg = document.getElementById("dCommitImg");
	if (!divCommitImg) {
		return;
	}

	var divCommitImgDisabled = document.getElementById("dCommitImgDisabled");
	var divCommitText = document.getElementById("dCommitText");
	var divCommitTextDisabled = document.getElementById("dCommitTextDisabled");

	var startToken = "@startPendingChange@";
	var endToken = "@endPendingChange@";
	var startIdx = data.indexOf(startToken);
	if (startIdx >= 0) {
		var endIdx = data.indexOf(endToken);
		var pendingChange = data.substring(startIdx+startToken.length, endIdx);
		var htm;
		if (pendingChange == "yes") {
			// need to show the commit as a link
			divCommitImg.style.display = "block";
			divCommitText.style.display = "block";
			divCommitImgDisabled.style.display = "none";
			divCommitTextDisabled.style.display = "none";
		} else {
			// show the commit as disabled
			divCommitImg.style.display = "none";
			divCommitText.style.display = "none";
			divCommitImgDisabled.style.display = "block";
			divCommitTextDisabled.style.display = "block";
		}
	}
}


///////////////// end of stuff for getting pending config changes /////////////

////////////////// stuff for switching device context in Panorama //////////////////

/*
 * NOTE: The function handleDeviceChangeThruAjax needs to be called by some function in each of the top level tab pages
 * (like dashboard, acc, monitor, ...etc.). This will do the setting of the device context thru ajax. Once done it will
 * call a function called postDeviceChangeHandler(errorOccured, errorMsg). Each of the top level pages must provide 
 * their own implementation of postDeviceChangeHandler() to do the correct thing for their respective page
 */

// global

var gDeviceChangeObjXml = null;

/* called from deviceChanged() in each of the top level tab pages */
function handleDeviceChangeThruAjax(selectedDevice) {
	var url = "/esp/cms_changeDeviceContext.esp?device=" + selectedDevice;

	if (gDeviceChangeObjXml) gObjXml.abort();
	gDeviceChangeObjXml = GetXmlHttpObject();
	panGetContentGen(url, ajaxDeviceChangeHandler, gDeviceChangeObjXml);
}

// the global variable gDeviceChangedObjXml is defined above
function ajaxDeviceChangeHandler() {
	var msg;
	try {
		// In firefox you we sometimes get a NS_ERROR_NOT_AVAILABLE (0x80040111). In
		// that case try again after X secs
		if (gDeviceChangeObjXml.readyState==4) {
			if (gDeviceChangeObjXml.status == 200) {
				// ok
				msg = gDeviceChangeObjXml.responseText;
				// KK Test
				//alert("ajaxDeviceChangeHandler: " + msg);
				// end KK Test

				var startToken = "@start@";
				var endToken = "@end@";
				var startIdx = msg.indexOf(startToken);
				if (startIdx >= 0) {
					var endIdx = msg.indexOf(endToken);
					var deviceChangeMsg = msg.substring(startIdx+startToken.length, endIdx);
					if (deviceChangeMsg == "Success" || deviceChangeMsg == "success") {
						// successfully changed the device context
						postDeviceChangeHandler(false, "");
					} else {
						postDeviceChangeHandler(true, deviceChangeMsg);
					}
				} else {
					postDeviceChangeHandler(true, "Could not find start token '@start@'");
				}
			} else {
				postDeviceChangeHandler(true, gObjXml.statusText);
			}
		}
	} catch (e) {
		postDeviceChangeHandler(true, e.message);
	}
}

///////////////// end of stuff for switching device context in Panorama /////////////


//// show and hide div elements ////
function panhidediv(elem)
{
	var el = document.getElementById(elem);
	if (el) {
		el.style.display = "none";
	}
}

function panshowdiv(elem)
{
	var el = document.getElementById(elem);
	if (el) {
		el.style.display = "block";
	}
}

function panshowdivtr(elem)
{
	var el = document.getElementById(elem);
	if (el) {
		el.style.display = '';
	}
}

// msg to show in div 'dContent'. This should be used to show a msg when a page is loading in the
// top level pages
function panShowLoadingMsg(page) {

	var dPageHeader = document.getElementById('pageHeader');
	if (dPageHeader) {
		dPageHeader.innerHTML = page;
	}
	var dContentInner = document.getElementById('dContentInner');
	if (dContentInner) {
		var html = "	  <div style=\"margin-top: 4px; margin-left: 4px;\">\n";
		html += "			Loading " + page + " ...\n";
		html += "	  </div>\n";

		dContentInner.innerHTML = html;
	}
}

//// end - show and hide div elements ////

function panElemOnOff(elem, val)
{
	var el = document.getElementById(elem);
	if (el) {
		el.disabled = val;
	}
}

/* This is called when user clicks on the commit button on the top */
function doConfigCommit(evt) {
	evt = (evt) ? evt : ((event) ? event : null);

	var msg = "Doing a commit will overwrite the running configuration.\n\nDo you want to continue?";

	if (confirm(msg)) {
		var url = "/esp/save.esp?bAction=Commit"
			// opening a non modal window so that timers in the parent window still keep working.
			// this is a problem only in IE
			openNonModalWindow(url, "configCommit", 600, 300, evt.screenX, evt.screenY);
	}
}

/* This is called when user clicks on the save button on the top */
function doConfigSave(evt) 
{
	evt = (evt) ? evt : ((event) ? event : null);

	var msg = "Saving this configuration will overwrite the previously saved configuration.\n\n Do you want to continue?";

	if (confirm(msg)) {
		var url = "/esp/saveAndRollback.esp?bAction=Save";
		// opening a non modal window so that timers in the parent window still keep working.
		// this is a problem only in IE
		openNonModalWindow(url, "saveAndRollback", 500, 200, evt.screenX, evt.screenY);
	}
}


function panShowThreatDetailExt(threatid, subtype, vsys, serial, rule, evt)
{
	evt = (evt) ? evt : ((event) ? event : null);

	var url = "/esp/threatDetail.esp?threatid="+threatid;
	if (vsys != undefined) {
		url += "&vsys="+vsys;
	}

	var height = 300;

	if ((subtype == 'virus') || (subtype == 'spyware') || (subtype == 'vulnerability'))  {
		url += "&showExceptions=1&subtype=" + subtype + "&serial=" + serial + "&rule=" + rule;
		height = 530;
	}

	openModalWindow(url, "threatDetail", 780, height, evt.screenX, evt.screenY);
}


// called from profiles
function panShowThreatDetail(threatid, vsys, evt)
{
	return panShowThreatDetailExt(threatid, null, vsys, null, null, evt);
}


/**************** Code to get list of connected devices periodically in CMS **************************/

// var gConnectedDevicesObjXml is defined in ajaxComm.js

var gCurDeviceLoc;
var gConnectedDevicesFreqInMs = 60000; // how frequently to check, in ms
var gConnectedDevicesTimeoutId; 

// called from checkConnectedDevices
function ajaxHandlerConnectedDevicesCheck() {
	var msg;
	if (gConnectedDevicesObjXml.readyState==4) {
		if (gConnectedDevicesObjXml.status == 200) {
			// ok
			msg = gConnectedDevicesObjXml.responseText;
			// KK Test
			//alert("ajaxHandlerConnectedDevicesCheck:\n" + msg);
			// end KK Test
			handleConnectedDevicesCheck(msg);
		} else {
			// No need to alert, just do not do anything
			//alert("ajaxHandlerConnectedDevicesCheck:: Error in retrieving data: " + gConnectedDevicesObjXml.statusText);
		}
	}
}

// called from getConnectedDevices
function ajaxHandlerConnectedDevicesGet() {
	var msg;
	if (gConnectedDevicesObjXml.readyState==4) {
		if (gConnectedDevicesObjXml.status == 200) {
			// ok
			msg = gConnectedDevicesObjXml.responseText;
			// KK Test
			//alert("ajaxHandlerConnectedDevicesGet:\n" + msg);
			// end KK Test
			handleConnectedDevicesGet(msg);
		} else {
			// No need to alert, just do not do anything
			//alert("ajaxHandlerConnectedDevicesGet:: Error in retrieving data: " + gConnectedDevicesObjXml.statusText);
		}
	}
}

function ajaxHandlerSetDeviceLocCheck() {
	var msg;
	if (gConnectedDevicesObjXml.readyState==4) {
		if (gConnectedDevicesObjXml.status == 200) {
			// ok, there is no response really.
			window.top.location = "/esp/dashboard.esp";
		} else {
			// No need to alert, just do not do anything
			//alert("ajaxHandlerSetDeviceLocCheck:: Error in retrieving data: " + gConnectedDevicesObjXml.statusText);
		}
	}
}

// called from ajaxHandlerConnectedDevicesCheck()
function handleConnectedDevicesCheck(msg) {

	// sometimes we have seen the box return a comfort message. So in case of any errors, do not 
	// do anything, just try again later.
	try {
		// the returned array is of the form
		// devices: [ { loc: "...", name: "...", current: "yes|no"}, ... more entries ... ]
		// NOTE: This format can never be changed because of backward compatibility. (see bug 19193)
		eval("var devicesArray="+msg);
	} catch (e) {
		gConnectedDevicesTimeoutId = setTimeout("checkConnectedDevicesInternal()", gConnectedDevicesFreqInMs);
		return;
	}

	if (devicesArray.length == 0) {
		// we expect at least panorama to be there in the list of devices. If there is no
		// devices returned then there must have been an error of some sort. Do not do 
		// anything, just try again later
		gConnectedDevicesTimeoutId = setTimeout("checkConnectedDevicesInternal()", gConnectedDevicesFreqInMs);
		return;
	}

	var i;
	/* KK For testing
	   var str="";
	   for(i=0; i<devicesArray.length; i++) {
	   str += "device["+i+"]:: loc: " + devicesArray[i].loc + ", name: " + devicesArray[i].name + "\n";
	   }
	   alert(str);
	 */

	var docSelObj = document.getElementById("sDevice");
	var docDevicesArray = new Array();
	if (docSelObj) {
		for(i=0; i<docSelObj.options.length; i++) {
			docDevicesArray[i] = {loc: docSelObj.options[i].value, name: docSelObj.options[i].text};
		}
		/* KK For Testing
		   str = "";
		   for(i=0; i<docDevicesArray.length; i++) {
		   str += "docDevice["+i+"]:: loc: " + docDevicesArray[i].loc + ", name: " + docDevicesArray[i].name + "\n";
		   }
		   alert(str);
		 */
	}

	var devicesDiffer = false;
	if (devicesArray.length != docDevicesArray.length) {
		devicesDiffer = true;
	} else {
		// we can assume that the order of devices in devicesArray and docDevicesArray will
		// be the same since they come from the same backend call
		for(i=0; i<devicesArray.length; i++) {
			if (devicesArray[i].loc != docDevicesArray[i].loc) {
				devicesDiffer = true;
				break;
			}
		}
	}

	if (devicesDiffer) {
		var curDevicePresent = false;
		var curDeviceLoc = gCurDeviceLoc;
		for(i=0; i<devicesArray.length; i++) {
			if (devicesArray[i].loc == curDeviceLoc) {
				curDevicePresent = true;
				break;
			}
		}

		if (curDevicePresent) {
			// update the sDevice obj with the new list
			docSelObj.options.length = devicesArray.length;
			for(i=0; i<devicesArray.length; i++) {
				if (devicesArray[i].loc == curDeviceLoc)
					docSelObj.options[i] = new Option(devicesArray[i].name, devicesArray[i].loc, true, true);		
				else
					docSelObj.options[i] = new Option(devicesArray[i].name, devicesArray[i].loc, false, false);		
			}
		} else {
			// current device is not connected, show an alert and redirect to Panorama
			var cmsg = PAN_MSG_1003;
			alert(cmsg);
			// we need to set the device location to that of panorama
			var cmsLoc = getCmsLocationStr();
			var url = "/esp/cms_setDeviceLocation.esp?deviceLoc=" + cmsLoc;
			panCmsConnectedDevicesGetContent(url, ajaxHandlerSetDeviceLocCheck);
			return;
		}
	}

	// need to do it again
	gConnectedDevicesTimeoutId = setTimeout("checkConnectedDevicesInternal()", gConnectedDevicesFreqInMs);
}

// called from ajaxHandlerConnectedDevicesGet()
function handleConnectedDevicesGet(msg) {

	// sometimes we have seen the box return a comfort message. So in case of any errors, do not 
	// do anything, just try again later.
	try {
		// the returned array is of the form
		// devices: [ { loc: "...", name: "...", current: "yes|no"}, ... more entries ... ]
		// NOTE: This format can never be changed because of backward compatibility. (see bug 19193)
		eval("var devicesArray="+msg);
	} catch (e) {
		alert("handleConnectedDevicesGet: " + e.message);
		return;
	}

	if (devicesArray.length == 0) {
		// we expect at least panorama to be there in the list of devices. If there is no
		// devices returned then there must have been an error of some sort. Do not do 
		// anything, just try again later
		alert("handleConnectedDevicesGet: Could not get any devices???");
		return;
	}

	// getConnectedDevices is called in device context in Panorama. In this context the top level pages
	// are generated in the device itself and there is not expected to be any options in sDevice. So we will
	// fill it always
	var docSelObj = document.getElementById("sDevice");
	// update the sDevice obj with the new list
	docSelObj.options.length = devicesArray.length;
	var selectedIndex = -1;
	for(var i=0; i<devicesArray.length; i++) {
		if (devicesArray[i].current == "yes") {
			docSelObj.options[i] = new Option(devicesArray[i].name, devicesArray[i].loc, true, true);		
			selectedIndex = i;
		} else {
			docSelObj.options[i] = new Option(devicesArray[i].name, devicesArray[i].loc, false, false);		
		}
	}

	if (selectedIndex >= 0)
		docSelObj.selectedIndex = selectedIndex;

	// need to start checking for connected devices - NOTE: we call checkConnectedDevicesInternal and
	// not getConnectedDevices out here. This is because we need to do replace only if there is any change 
	// in the list of connected devices etc. and all that is done in the 'check' flow. 
	gConnectedDevicesTimeoutId = setTimeout("checkConnectedDevicesInternal()", gConnectedDevicesFreqInMs);
}

// this should not be called from outside
function checkConnectedDevicesInternal() {
	panCmsConnectedDevicesGetContent("/esp/cms_getConnectedDevices.esp", ajaxHandlerConnectedDevicesCheck);
}

// should be called in the loadPage() of each of the top level pages
function checkConnectedDevices(curDeviceLoc) {
	gCurDeviceLoc = curDeviceLoc;
	gConnectedDevicesTimeoutId = setTimeout("checkConnectedDevicesInternal()", gConnectedDevicesFreqInMs);
}

// this is called from the loadPage() of each of the top level pages in a device context, i.e. when the top 
// level pages is actually coming from the device itself and is not generated in panorama
function getConnectedDevices() {
	panCmsConnectedDevicesGetContent("/esp/cms_getConnectedDevices.esp", ajaxHandlerConnectedDevicesGet);
}

function stopCheckConnectedDevices() {
	if (gConnectedDevicesTimeoutId)
		clearTimeout(gConnectedDevicesTimeoutId);
}

/**************** End of code to get list of connected devices periodically in CMS **************************/



/************** Functions releated to a double pane tree ***************************/

/*
 * A double pane tree is like a double pane (used in pages like editServiceInRule.esp) except
 * that the choices on the left hand pane is shown in a tree format (e.g. editApplicationInRule.esp)
 *
 * The data on the left hand table is printed in a particular format which is all taken care of in 
 * the function panEspPrintCompletionsMultipleTwoPaneTree().
 *
 * The complicated part is the left pane and that is documented here. The right pane is like that
 * of the regular double pane.
 *
 * <tableLeftBody>
 * 		- Top level nodes have a table id like 'tableLeft_0', 'tableLeft_1' and so on.
 * 		- Check boxes on this level have id like 'cbLeft_0', 'cbLeft_1' and so on.
 *      - The span (across the image and the text) has id like 'spanLeft_0', and so on.
 *		- There may also be a hidden field 'rightDisplay_<X>_<Y>'  and rightImage_<X>_<Y> (this field is optional)
 *		- All the children of each node is enclosed in a div like 'divLeft_0', and so on.
 *
 *		- The nodes in the 2nd level have table id's like 'tableLeft_<X>_<Y> where <X> denotes
 *        the parent and <Y> denotes which child of the parent it is.
 *		- The checkboxes have id's like 'cbLeft_<X>_<Y>' (<X> and <Y> same as the table id)
 *		- The span has id 'spanLeft_<X>_<Y>' (<X> and <Y> same as the table id)
 *		- There may also be a hidden field 'rightDisplay_<X>_<Y>'  and rightImage_<X>_<Y> (this field is optional)
 *		- All children of a node is enclosed in div like 'divLeft_<X>_<Y>' (<X> and <Y> same as table id)
 *
 */

/* globals one for left pane, one for right pane */
// NOTE: extra fields on left array - cbId 
var jsDataLeftTree = new Array(); // entries of form {ischecked: ..., cbId: ..., html: ..., value: ..., rightValueObj: (object with fields img and txt)} 
var jsDataRightTree = new Array(); // entries of form {ischecked: ..., html: ..., value: ...}


/* 
 * get the id of the table body in left pane or right pane 
 *
 * col should be either 'left' or 'right'
 *
 */
function dptGetTableBodyId(col) {
	var tableBodyId;

	if (col == "left") {
		tableBodyId = "tableLeftBody";
	} else {
		tableBodyId = "tableRightBody";
	}

	return tableBodyId;
}

/* 
 * This fills the global arrays jsDataLeftTree or jsDataRightTree by reading the stuff from the html 
 * document.
 *
 * col should be either 'left' or 'right'
 *
 */
function dptFillJSDataFromDoc(col) {

	if (col == 'left')
		dptFillJSDataFromDocLeft();
	else
		dptFillJSDataFromDocRight();

}

function dptFillJSDataFromDocLeft() {

	jsDataLeftTree = new Array();

	var tableBodyId = dptGetTableBodyId("left"); 
	var tbodyObj = document.getElementById(tableBodyId);
	if (!tbodyObj)
		return;

	var cbObj;
	var spObj;
	var rightDisplayObj;
	var rightDisplayStr;
	var rightImageStr;
	var cbId;
	var spId;
	var rightDisplayId;
	var rightImageId;
	var tableId;
	var suffix = "Left";

	var tableList = tbodyObj.getElementsByTagName("table");
	for(var i=0; i<tableList.length; i++) {
		tableId = tableList[i].id;
		cbId = tableId.replace(/table/, "cb");
		spId = tableId.replace(/table/, "sp");
		rightDisplayId = tableId.replace(/tableLeft/, "rightDisplay");
		rightImageId = tableId.replace(/tableLeft/, "rightImage");

		cbObj = document.getElementById(cbId);
		if (!cbObj)
			continue; // safety

		spObj = document.getElementById(spId);

		rightDisplayObj = new Object();

		rightDisplayStr = document.getElementById(rightDisplayId);
		if (rightDisplayStr) {
			rightDisplayObj.txt = rightDisplayStr.value;
		}

		rightImageStr = document.getElementById(rightImageId);
		if (rightImageStr) {
			rightDisplayObj.img = rightImageStr.value;
		}

		if (cbObj.checked) {
			jsDataLeftTree[i] = { ischecked: true, cbId: cbId, html: spObj.innerHTML, value: cbObj.value, rightDisplayObj: rightDisplayObj};
		} else {
			jsDataLeftTree[i] = { ischecked: false, cbId: cbId, html: spObj.innerHTML, value: cbObj.value, rightDisplayObj: rightDisplayObj };
		}
	}

	//alert("jsDataLeftTree.length: " + jsDataLeftTree.length);
}

function dptFillJSDataFromDocRight() {

	jsDataRightTree = new Array();

	var tableBodyId = dptGetTableBodyId("right"); 
	var tbodyObj = document.getElementById(tableBodyId);
	if (!tbodyObj)
		return;

	var cbObj;
	var spObj;
	var cbId;
	var spId;
	var suffix = "Right";

	for(var i=0; i<tbodyObj.rows.length; i++) {
		cbId = "cb"+suffix+"_"+i;
		spId = "sp"+suffix+"_"+i;

		cbObj = document.getElementById(cbId);
		if (!cbObj)
			continue; // safety

		spObj = document.getElementById(spId);

		if (cbObj.checked) {
			jsDataRightTree[i] = { ischecked: true, html: spObj.innerHTML, value: cbObj.value };
		} else {
			jsDataRightTree[i] = { ischecked: false, html: spObj.innerHTML, value: cbObj.value };
		}
	}

	//alert("jsDataRightTree.length: " + jsDataRightTree.length);
}


/*
 * Removes the rows of a table
 */
function dptClearTable(tbodyObj) {
	while ((tbodyObj.rows.length) > 0) {
		tbodyObj.deleteRow(0);
	}
}

/*
 * draws the table on the right pane. It looks at the data in jsDataRight
 */
function dptDrawRightTable() {
	var tbody = dptGetTableBodyId("right"); 
	var jsData = jsDataRightTree;

	var i, tr, td;
	var val;
	var cbName;

	// Obtain an object reference to the table body
	var tbodyObj = document.getElementById(tbody);
	// remove existing rows, if any
	clearTable(tbodyObj);

	// loop through data source
	for (i = 0; i < jsData.length; i++) {
		tr = tbodyObj.insertRow(tbodyObj.rows.length);
		tr.setAttribute("onclick", "rowCheckboxOrRadioSelect(this,event)");

		// blank cell
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("width", "5px");
		td.innerHTML = "&nbsp;";
		// checkbox
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("width", "20px");

		cbName = "cbRight_"+i;
		if (jsData[i].ischecked) {
			td.innerHTML = "<input type='checkbox' id='cbRight_"+i+"' name='"+cbName+"' value='"+jsData[i].value+"' onclick=\"dptCbClickedRight(this, '"+i+"')\" checked>";
		} else {
			td.innerHTML = "<input type='checkbox' id='cbRight_"+i+"' name='"+cbName+"' value='"+jsData[i].value+"' onclick=\"dptCbClickedRight(this, '"+i+"')\">";
		}

		//
		td = tr.insertCell(tr.cells.length);
		td.setAttribute("className", "listNoSideBorder");
		td.setAttribute("class", "listNoSideBorder");
		td.innerHTML = jsData[i].html;
	}
}

/*
 * called from addToRightPane(). After adding to the right pane we need 
 * to unhighlight the rows from the left pane
 */
function dptUnHighlightLeftRow(cbId) {
	var cbObj = document.getElementById(cbId);
	var rowObj = getFirstAncestorByHtmlTag(cbObj, "tr");
	rowObj.style.backgroundColor = "";
}

/*
 * This is called when a checkbox on the left column is clicked.
 */
function dptCbClickedLeft(cbObj) {

	//selectRow(cbObj);

	var rowNum = -1; // invalid index
	for(rowNum=0; rowNum<jsDataLeftTree.length; rowNum++) {
		if (cbObj.value == jsDataLeftTree[rowNum].value)
			break;
	}

	if (rowNum >= 0) {
		if (cbObj.checked) {
			jsDataLeftTree[rowNum].ischecked = true;
			// disable the children
			dptEnableOrDisableChildNodes(cbObj.id, false);
		} else {
			jsDataLeftTree[rowNum].ischecked = false;
			// enable the children
			dptEnableOrDisableChildNodes(cbObj.id, true); 
		}

	}

	// If user clicks on any of the checkboxes in the left/right pane we want the __select__
	// radio button to be selected.
	var formObj = getFirstAncestorByHtmlTag(cbObj, "form");
	checkMatchingRadioOrCheckbox(formObj.name, "rOpt", "__select__");
}

/*
 * This is called when a checkbox on the right column is clicked.
 */
function dptCbClickedRight(cbObj, rowNum) {

	//selectRow(cbObj);

	if (cbObj.checked)
		jsDataRightTree[rowNum].ischecked = true;
	else
		jsDataRightTree[rowNum].ischecked = false;

	// If user clicks on any of the checkboxes in the left/right pane we want the __select__
	// radio button to be selected.
	var formObj = getFirstAncestorByHtmlTag(cbObj, "form");
	checkMatchingRadioOrCheckbox(formObj.name, "rOpt", "__select__");
}

/*
 * add the data from row 'rowNum' of the left pane to the right pane. Add only if it
 * is not already in the right pane.
 *
 * This function just addes to the end of the right data. We will sort it later.
 */
function dptAddRowDataToRight(rowNum) {
	var leftRowData = jsDataLeftTree[rowNum];

	// add to the end, we will sort later
	for(var j=0; j<jsDataRightTree.length; j++) {
		if (jsDataRightTree[j].value == leftRowData.value) {
			// it is already there on the right side, no need to do anything
			return;
		}
	}

	var rightHtml = leftRowData.html;
	if (leftRowData.rightDisplayObj) {
		rightHtml = "";
		if (leftRowData.rightDisplayObj.img.length > 0) {
			rightHtml = "<img src='" + leftRowData.rightDisplayObj.img + "' border='0'>&nbsp;";
		}

		rightHtml += leftRowData.rightDisplayObj.txt;
	}

	// add it to the end, and the checkbox should be unchecked
	jsDataRightTree[jsDataRightTree.length] = {ischecked: false, html: rightHtml, value: leftRowData.value};

}

/*
 * Function to determine what to sort by since the members of each array are objects
 */
function dptDataSortFunc(a, b) {
	var retval = 0;

	var aDisplayValue = dptGetDisplayedValueFromActualValue(a.value);
	var bDisplayValue = dptGetDisplayedValueFromActualValue(b.value);

	if (aDisplayValue < bDisplayValue)
		retval = -1;
	else if (aDisplayValue > bDisplayValue)
		retval = 1;

	return retval;
}

/*
 * This is called when the user clicks on the "Add >" button to add selected choices to 
 * the right pane
 */
function dptAddToRightPane() {

	var tableBodyId = getTableBodyId('left'); 
	var tbodyObj = document.getElementById(tableBodyId);

	for(var i=0; i < jsDataLeftTree.length; i++) {
		if (jsDataLeftTree[i].ischecked) {
			// add it to the right hand side array

			// only consider the checks which are enabled. The disabled ones are already in 
			// the right side
			var cbObj = document.getElementById(jsDataLeftTree[i].cbId);
			if (!cbObj.disabled) {
				dptAddRowDataToRight(i);

				// disable the checkbox from the left hand table and unhighlight it
				cbObj.disabled = true;
				dptUnHighlightLeftRow(cbObj.id);
			}
		}
	}

	// sort the right table data
	jsDataRightTree.sort(dptDataSortFunc);

	// now draw the right table
	dptDrawRightTable();

	// if there is a search text box then clear it
	var txtSearchObj = document.getElementById("txtSearch");
	if (txtSearchObj) {
		txtSearchObj.value = '';
	}
}

/*
 * sometimes from a popup window a new object is created (in a second popup window). when the second popup
 * is closed then the object created must be added to the right side of the two pane selection too.
 *
 * This function should be called in such cases
 */
function dptAddDataToRightPane(thisForm, newValue) {
	//alert("addDataToRightPane: form: " + thisForm.name + ", newValue: " + newValue);

	var tableBodyId = getTableBodyId('left'); 
	var tbodyObj = document.getElementById(tableBodyId);

	for(var i=0; i < jsDataLeftTree.length; i++) {
		if (jsDataLeftTree[i].value == newValue) {
			// add it to the right hand side array

			dptAddRowDataToRight(i);

			var cbObj = document.getElementById(jsDataLeftTree[i].cbId);
			// disable the checkbox from the left hand table and unhighlight it
			cbObj.disabled = true;
			dptUnHighlightLeftRow(cbObj.id);

			// also need to check it on the left side
			cbObj.checked = true;
			jsDataLeftTree[i].ischecked = true;
		}
	}

	// sort the right table data
	jsDataRightTree.sort(dptDataSortFunc);

	// now draw the right table
	dptDrawRightTable();

	// we also want to make "__select__" the selected option
	checkMatchingRadioOrCheckbox(thisForm.name, "rOpt", "__select__");
}

/*
 * This takes the data in dataArray and puts it in the right pane. This should be called when
 * on form submit we get an error and we need to revert to what the user had changed before submitting
 * the form
 */
function dptInitRightPaneData(dataArray) {

	var cbObj;

	for(var i=0; i<dataArray.length; i++) {
		for(var j=0; j<jsDataLeftTree.length; j++) {
			if (jsDataLeftTree[j].value == dataArray[i]) {
				dptAddRowDataToRight(j);

				// disable the checkbox from the left hand table and unhighlight it, also 
				// mark it as checked since it has moved to the right
				cbObj = document.getElementById(jsDataLeftTree[j].cbId);
				cbObj.disabled = true;
				cbObj.checked = true;
				// disable the children
				dptEnableOrDisableChildNodes(cbObj.id, false);
				dptUnHighlightLeftRow(cbObj.id);
			}
		}
	}

	// sort the right table data
	jsDataRightTree.sort(dptDataSortFunc);

	// now draw the right table
	dptDrawRightTable();

}

/*
 * This function looks at the data in the right pane and 
 * a. checks the corresponding checkbox on the left side. If the checkbox has children then it 
 *    will disable all the children 
 *
 * This should be used when the page is loaded the first time
 */
function dptCheckAndDisableLeftPaneFromSelectedValues() {
	for(var i=0; i<jsDataRightTree.length; i++) {
		for(var j=0; j<jsDataLeftTree.length; j++) {
			if (jsDataRightTree[i].value == jsDataLeftTree[j].value) {
				var cbObj = document.getElementById(jsDataLeftTree[j].cbId);
				if (cbObj) {
					cbObj.checked = true;
					jsDataLeftTree[j].ischecked = true;

					// disable this checkbox and those of its children (if any)
					cbObj.disabled = true;
					dptEnableOrDisableChildNodes(cbObj.id, false);

					// open all parent nodes (if any)
					dptOpenParentNodes(cbObj);
				}

				break; // out of for j loop
			}
		}
	}
}

/*
 * This function decides on which divs to show and what to enable/disable 
 */
function dptCompletionSelectHandler(thisForm, radioName) {
	var twoPaneSelectChosen = false;

	var selObj = eval("document." + thisForm.name + "." + radioName);
	if (!selObj) {
		// sometimes the select menu will not be present when there is only one select option, but
		// this is as if the user chose to select from the two panes
		twoPaneSelectChosen = true;
	} else {
		var selectValue = getCheckedValues(thisForm.name, radioName, "");
		if (selectValue == "__select__") {
			twoPaneSelectChosen = true;
		}
	}

	if (twoPaneSelectChosen) {
		document.getElementById("bAdd").disabled = false;
		document.getElementById("bRemove").disabled = false;

		// you have to only enable those which are not already on right side
		var found;
		for(var i=0; i<jsDataLeftTree.length; i++) {
			found = false;
			for(var j=0; j<jsDataRightTree.length; j++) {
				if (jsDataLeftTree[i].value == jsDataRightTree[j].value) {
					found = true;
					break; // out of for j loop
				}
			}
			if (!found) 
				document.getElementById(jsDataLeftTree[i].cbId).disabled = false;
		}

		for(var i=0; i<jsDataRightTree.length; i++) {
			document.getElementById("cbRight_"+i).disabled = false;
		}

		var txtSearchObj = document.getElementById('txtSearch');
		if (txtSearchObj)
			disableTextFld(txtSearchObj, false);

		document.getElementById('divLeft').style.backgroundColor = "#FFFFFF";
		// This does not seem to work
		//document.getElementById('divLeft').style.color = "#000000";
		document.getElementById('tableLeftBody').style.color = "#000000";

		document.getElementById('divRight').style.backgroundColor = "#FFFFFF";
		// This does not seem to work
		//document.getElementById('divRight').style.color = "#000000";
		document.getElementById('tableRightBody').style.color = "#000000";
	} else {
		/* KK Now that we have radio buttons for the keywords instead of a dropdown we do not want
		 * to disable this part. We want the user to be able to select something here and then we 
		 * will make __select__ the selected radio button
		 document.getElementById("bAdd").disabled = true;
		 document.getElementById("bRemove").disabled = true;

		 for(var i=0; i<jsDataLeft.length; i++) {
		 document.getElementById("cbLeft"+i).disabled = true;
		 }

		 for(var i=0; i<jsDataRight.length; i++) {
		 document.getElementById("cbRight"+i).disabled = true;
		 }

		 var txtSearchObj = document.getElementById('txtSearch');
		 if (txtSearchObj)
		//txtSearchObj.disabled = true;
		disableTextFld(txtSearchObj, true);

		document.getElementById('divLeft').style.backgroundColor = "#F3F3F3";
		// This does not seem to work
		//document.getElementById('divLeft').style.color = "#C0C0C0";
		document.getElementById('tableLeftBody').style.color = "#C0C0C0";

		document.getElementById('divRight').style.backgroundColor = "#F3F3F3";
		// This does not seem to work
		//document.getElementById('divRight').style.color = "#C0C0C0";
		document.getElementById('tableRightBody').style.color = "#C0C0C0";
		 */
	}
}

/* 
 * called from dptRemoveFromRightPane.
 *
 * it enables the left checkbox after the item is removed from the right pane
 */
function dptEnableLeftCheckbox(value) {
	var cbObj;
	for(var i=0; i<jsDataLeftTree.length; i++) {
		if (jsDataLeftTree[i].value == value) {
			cbObj = document.getElementById(jsDataLeftTree[i].cbId);

			// uncheck checkbox.
			cbObj.checked = false;

			// if its parent is checked or disabled (parent being disabled means that some ancestor
			// further up has probably been selected) then this should still be disabled, else enable
			// it and its children
			var enableChildren = true;
			var enableCheckbox = true;
			var parentCbObj = getParentCheckbox(cbObj);
			if (parentCbObj) {
				if (parentCbObj.checked || parentCbObj.disabled) {
					enableCheckbox = false;
					enableChildren = false;
				}
			}

			// enable the checkbox
			if (enableCheckbox) {
				cbObj.disabled = false;
				// also mark it as checked, if it is to be enabled
				cbObj.checked = true;
				// also check the javascript data
				jsDataLeftTree[i].ischecked = true;
			} else {
				// disable the checkbox, do not check it either
				cbObj.disabled = true;
			}

			// enable children
			if (enableChildren) {
				dptEnableOrDisableChildNodes(cbObj.id, true);
			}

			break;
		}
	}
}

/*
 * called when the user clicks on the "< Remove" button
 *
 * delete data from the right pane. 
 */
function dptRemoveFromRightPane() {

	// delete one row at a time until all rows to delete have been deleted.
	var idx = dptRowNumStillToDelete(jsDataRightTree);
	while(idx >= 0) {
		// we also need to enable the item in the left side
		dptEnableLeftCheckbox(jsDataRightTree[idx].value);

		jsDataRightTree.splice(idx,1);

		idx = dptRowNumStillToDelete(jsDataRightTree);
	}

	dptDrawRightTable();
}

/* 
 * returns -1 if there is no more rows to delete or returns the index of the 
 */
function dptRowNumStillToDelete(jsData) {
	var retval = -1;
	for(var i=0; i<jsData.length; i++) {
		if (jsData[i].ischecked) {
			retval = i;
			break;
		}
	}

	return retval;
}

/*
 * returns the string for the selected values separated by the 'separator'
 */
function dptGetTwoPaneSelectedValues(formName, radioName, separator) {
	var str = "";

	// if there is only one entry in the select option (i.e. __select__) we do not show it. 
	// So here we check for it
	var str = "document." + formName + "." + radioName;
	var selectObj = eval(str);
	str = ""; // reset str
	var selectFromRightPane = false;

	if (!selectObj) {
		selectFromRightPane = true;
	} else {
		//var optValue = getMultipleSelectedValues(formName, radioName, "");
		var optValue = getCheckedValues(formName, radioName, "");
		if (optValue.length > 0) {
			if (optValue == "__select__") {
				selectFromRightPane = true;
			} else {
				str = optValue; // user did not choose __select__ but chose another select option
			}
		}
	}

	if (selectFromRightPane) {
		// user chose to select from a list
		str = "";
		for(var i=0; i<jsDataRightTree.length; i++) {
			if (i > 0)
				str += separator;

			str += jsDataRightTree[i].value;
		}
	}

	return str;
}


/*
 * When the user selects a choice from the search suggest, then we need to some specific
 * stuff for the dual pane completions like selecting it in the left pane etc. This is
 * done here
 */
function dptSetSearchTwoPane(value) {	
	// In dtpSearchSuggest() we sometimes put "<img ....>&nbsp;" just before the actual value. So here
	// we need to remove that
	var idx = value.indexOf("<img");
	if (idx < 0)
		idx = value.indexOf("<IMG"); // for IE

	if (idx >=0) {
		idx = value.indexOf("&nbsp;");
		value = value.substring(idx+6); // + 6 because we need to go past the &nbsp;
	}

	// must call the default first
	setSearch(value);

	// then do the local stuff
	// highlight it and select it on the left pane
	var cbObj;
	var displayValue;
	for(var i=0; i<jsDataLeftTree.length; i++) {
		displayValue = dptGetDisplayedValueFromActualValue(jsDataLeftTree[i].value);
		if (displayValue == value) {
			jsDataLeftTree[i].ischecked = true;

			cbObj = document.getElementById(jsDataLeftTree[i].cbId);
			// need to check it first before calling selectRow to get the highlighting
			cbObj.checked = true;
			//selectRow(cbObj);

			// also need to disable all children
			dptEnableOrDisableChildNodes(cbObj.id, false);

			// open all parent nodes (if any)
			dptOpenParentNodes(cbObj);

			// also scroll to that row
			cbObj.scrollIntoView(true);
		}
	}

}

/*
 * If the id of the node is like 'cbLeft_<x>' then it is a top level node. If it is like
 * 'cbLeft_<x>_<y>' then it is in the first subtree (i.e. it has one parent). If it is like
 * 'cbLeft_<x>_<y>_<z>' then it is in the second subtree (i.e it has two parents) and so on.
 *
 * This function returns the parent checkbox obj or null if there is no parent
 */
function getParentCheckbox(cbObj) {

	var cbId = cbObj.id;
	var tokensArray = cbId.split("_");
	var retval = null;

	if (tokensArray.length > 2) {
		// when it is like 'cbLeft_<x>_<y>_<z>' the tokensArray will be of size 4. We only need to only 
		// go upto the 3rd element. The 4th element is itself (not a parent)
		var divId;
		var cbIdStr = "cbLeft";
		for(var i=1; i < tokensArray.length-1; i++) {
			cbIdStr += "_" + tokensArray[i]; // now of the form "cbLeft_..."
		}
		retval = document.getElementById(cbIdStr);
	}

	return retval;
}

/*
 * If the id of the node is like 'cbLeft_<x>' then it is a top level node. If it is like
 * 'cbLeft_<x>_<y>' then it is in the first subtree (i.e. it has one parent). If it is like
 * 'cbLeft_<x>_<y>_<z>' then it is in the second subtree (i.e it has two parents) and so on.
 */
function dptOpenParentNodes(cbObj) {

	var cbId = cbObj.id;
	var tokensArray = cbId.split("_");

	if (tokensArray.length <= 2) {
		// nothing to do, it is like 'cbLeft_<x>'
		return;
	} else {
		// when it is like 'cbLeft_<x>_<y>_<z>' the tokensArray will be of size 4. We only need to only 
		// go upto the 3rd element. The 4th element is itself (not a parent)
		var divId;
		var cbIdStr = "cbLeft";
		for(var i=1; i < tokensArray.length-1; i++) {
			cbIdStr += "_" + tokensArray[i]; // now of the form "cbLeft_..."
			divId = cbIdStr.replace(/cb/,"div");
			dptNodeOpenOrClose(divId, true);
		}
	}

}

/*
 * When we have trees the values may be of the form 'category:<x>:subcategory:<y>:<z>' or
 * 'z' or 'category:<x>:z' The displayed field just has 'z' and so we need to pick that out.
 */
function dptGetDisplayedValueFromActualValue(val) {

	var rightDisplayObj = null;

	// Find the corresponding node in the left tree.
	for(var i=0; i<jsDataLeftTree.length; i++) {
		if (jsDataLeftTree[i].value == val) {
			rightDisplayObj = jsDataLeftTree[i].rightDisplayObj;
			break;
		}
	}

	var valueStr;
	// If there is a rightDisplayObj found then use it else calculate
	if (rightDisplayObj) {
		valueStr = rightDisplayObj.txt;
	} else {
		valueStr = val;
		var lastColon = valueStr.lastIndexOf(':');
		if (lastColon >= 0)
			valueStr = valueStr.substring(lastColon+1);
	}

	return valueStr;
}

/**
 * This is called on keyup in the search text box 
 *
 * The dataArray should either be an array of strings (in which case pass false
 * as the second param) or should be an array of  objects having a field called 'value' 
 * (in this case pass true as the second parameter)
 *
 * setSearchLocalFunc is the function which will be called 
 * after a selection in the suggest has been made. This function must call
 * setSearch() first and then do its local stuff. If this param is null or undefined then
 * the default setSearch() will be called.
 *
 * NOTE: somehow with IE using setSearchLocalFunc.name returned 'undefined' and so I had to pass
 * the name of the function too (setSearchLocalFuncName).
 *
 * enterKeyFunc - function called when user hits enter in the search text. setSearch/setSearchLocal
 * 				  function will always be called. If this param is not null then it will be called after
 *                that.
 *
 */
function dptSearchSuggest(evt, dataArray, isArrayOfObjects, setSearchLocalFunc, setSearchLocalFuncName, enterKeyFunc) {

	var searchText = trimStr(document.getElementById('txtSearch').value);
	if (searchText != gCurText) {
		// if the text has changed then it cannot be an up/down key
		suggestTextChanged(searchText);
	} else {

		evt = (evt) ? evt : ((event) ? event : null);

		if (evt.keyCode == 38) {
			// key up
			goToPreviousSuggestRow();
			return;
		} else if (evt.keyCode == 40) {
			// key down
			goToNextSuggestRow();
			return;
		} else if (evt.keyCode == 13) {
			// enter
			if (gCurSuggestRow >= 0) {
				var divObj = document.getElementById("suggest"+gCurSuggestRow);
				var str = "";
				if (divObj) {
					str = divObj.innerHTML;
					if (setSearchLocalFunc == undefined ||
							setSearchLocalFunc == null) { 
						setSearch(str);
					} else {
						setSearchLocalFunc(str);
					}
				}

				if (enterKeyFunc != undefined && enterKeyFunc != null) {
					enterKeyFunc();
				}

				return;
			}
		}
	}

	var ss = document.getElementById('search_suggest');
	ss.innerHTML = '';	

	// no need to do anything if there is no text
	if (searchText.length == 0) {
		// get rid of  border on the suggest div
		//ss.style.border = "0px solid #FFFFFF";
		ss.className = "search_suggest_invisible";
		return;
	}

	var chkStr;
	var valueStr;
	var imgSrc;
	var cbId;
	var spanId;
	var spanObj;
	var imgList;
	var oneSuggestFound = false;
	var idx = 0;
	for(var i=0; i<dataArray.length; i++) {

		if (isArrayOfObjects) {
			valueStr = dataArray[i].value;
			cbId = dataArray[i].cbId;
		} else {
			valueStr = dataArray[i];
			cbId = "";
		}

		imgSrc = "";
		if (cbId.length > 0) {
			spanId = cbId.replace(/cb/,"sp");
			spanObj = document.getElementById(spanId);
			if (spanObj) {
				imgList = spanObj.getElementsByTagName("img");
				if (imgList.length)
					imgSrc = imgList[0].src;
			}
		}

		valueStr = dptGetDisplayedValueFromActualValue(valueStr);

		chkStr = valueStr.substr(0, searchText.length);

		if (chkStr.toLowerCase() == searchText.toLowerCase()) {
			//Build our element string.  This is cleaner using the DOM, but
			//IE does not support dynamically added attributes.
			var suggest = '<div id="suggest' + idx + '" onmouseover="javascript:suggestOver(this);" ';	
			suggest += 'onmouseout="javascript:suggestOut(this);" ';
			if (setSearchLocalFuncName == undefined ||
					setSearchLocalFuncName == null) {
				suggest += 'onclick="javascript:setSearch(this.innerHTML);" ';
			} else {
				suggest += 'onclick="javascript:'+setSearchLocalFuncName+'(this.innerHTML);" ';
			}
			suggest += 'class="suggest_link">';

			if (imgSrc.length) {
				suggest += '<img src="' + imgSrc + '" border="0">&nbsp;';
			}

			suggest += valueStr + '</div>';	
			ss.innerHTML += suggest;	

			oneSuggestFound = true;
			idx++;
		}
	}

	// idx now has the value of number of suggestions, update the global variable
	gNumSuggestRows = idx;

	if (oneSuggestFound) {
		// put a border on the suggest div
		//document.getElementById("search_suggest").style.border = "1px solid #000000";
		ss.className = "search_suggest_visible";
	} else {
		// get rid of the border
		//document.getElementById("search_suggest").style.border = "0px solid #FFFFFF";
		ss.className = "search_suggest_invisible";
	}

}

function dptNodeClicked(divId, imgObj) {
	var divObj = document.getElementById(divId);
	var imgSrc = imgObj.src;

	if (divObj.style.display == "none") {
		// row is not being shown, show it and put the - img
		divObj.style.display = "";
		imgSrc = imgSrc.replace(/plus/g, "minus");
		imgObj.src = imgSrc;
	} else {
		// row is currently being shown, hide it and put the + img
		divObj.style.display = "none";
		imgSrc = imgSrc.replace(/minus/g, "plus");
		imgObj.src = imgSrc;
	}
}

function dptNodeOpenOrClose(divId, open) {
	var divObj = document.getElementById(divId);

	// if a divId is like 'divLeft_<x>' then there is a table 'tableLeft_<x>' whose first image is the
	// + or - image.
	var tableId = divId.replace(/div/,"table");
	var tableObj = document.getElementById(tableId);
	var imgList = tableObj.getElementsByTagName("img");

	var imgObj = imgList[0];
	var imgSrc = imgObj.src;

	if (open) {
		divObj.style.display = "";
		imgSrc = imgSrc.replace(/plus/g, "minus");
		imgObj.src = imgSrc;
	} else {
		divObj.style.display = "none";
		imgSrc = imgSrc.replace(/minus/g, "plus");
		imgObj.src = imgSrc;
	}

}

/*
 * returns an array of checkbox ids - those of the children. If no child then an empty array is returned.
 */
function dptGetAllChildNodes(cbId) {
	var childArray = new Array();

	var id;
	for(var i=0; i<jsDataLeftTree.length; i++) {
		id = jsDataLeftTree[i].cbId;

		// KK TODO potential for improvement, now we go through the whole array
		// if it is a child then it will start with the same as cbId but be longer
		if (id.length > cbId.length) {
			// we want to check that if cbId is 'cbLeft_<x>' then this is 'cbLeft_<x>_...'. This is
			// so that for cbLeft_1 we do not consider cbLeft_11_... to be its children
			if (id.substr(0, cbId.length+1) == (cbId+"_")) {
				childArray.push(id);
			}
		}
	}

	return childArray;
}

function dptEnableOrDisableChildNodes(cbId, enable) {
	var childArray = dptGetAllChildNodes(cbId);

	if (childArray.length > 0) {
		var cbObj;
		for(var i=0; i<childArray.length; i++) {
			cbObj = document.getElementById(childArray[i]);
			if (enable)
				cbObj.disabled = false;
			else
				cbObj.disabled = true;
		}
	}
}

/********************** end of double pane tree functions *************************/


/**************** some common functions related to application filtering **************/


/* 
 * Assumptions:
 * 
 * <type> can have values : 'category' | 'subcategory' | 'technology' | 'risk' | 'characteristic'
 *
 * The names of the filter checkboxes are like cb_<type>, so all the checkboxes in the category column will be
 * called 'cb_category'.
 *
 * The ids of the filter checboxes are like cb_<type>_<value>
 *
 * Each checkbox is wrapped in a div like d_<type>_<value>
 */

/* 
 * This is called when a checkbox in the filter is clicked. 
 */
function appFilterCbClicked(type, cbValue) {
	var cbObj = document.getElementById('cb_'+type+'_'+cbValue);
	var thisForm = cbObj.form;
	var divObj = document.getElementById('d_'+type+'_'+cbValue);

	if (cbValue == 'all') {
		// the all checkbox (i.e. the one at the top was clicked). 
		if (cbObj.checked) {
			// for the all checkbox this should not happen, because it only shows up when there is 
			// at least one filter checked in that column (i.e. it always shows up as checked). So if the 
			// user clicked it it means he unchecked it and so we should not come to this part
			// which says that the checkbox is checked. So if we come here do nothing, just return.
			return;
		} else {
			// the all checkbox is unchecked, so get rid of all the filters for this type and get the values
			// uncheck all the checkboxes of this type
			checkAllCheckboxes(thisForm.name, cbObj.name, false);
		}
	} else {
		// for the non 'all' case we need to get the filters and the new apps content, so 
		// nothing to do in the else part
	}

	var categoryFilters = getSelectedAppFilters('category');
	var subcategoryFilters = getSelectedAppFilters('subcategory');
	var technologyFilters = getSelectedAppFilters('technology');
	var riskFilters = getSelectedAppFilters('risk');
	var characteristicFilters = getSelectedAppFilters('characteristic');
	var searchFilter = document.getElementById('tAppSearch').value;

	var customAppOnly = "";
	var customAppObj = document.getElementById('cbCustomAppOnly'); 
	if (customAppObj && customAppObj.checked)
		customAppOnly = "yes";

	var sortby = document.getElementById('h_app_sort_by').value;
	var sortorder = document.getElementById('h_app_sort_order').value;

	getFilteredAndSortedAppsContent(categoryFilters, subcategoryFilters, technologyFilters, riskFilters, characteristicFilters, searchFilter, customAppOnly, sortby, sortorder);
}

/* 
 * This is called when the user clicks on a cell other than the checkbox for the filter (like the name or the count). We
 * want to simulate as if the user clicked on the checkbox.
 */
function appFilterCbClickedAsIf(type, cbValue) {
	var cbObj = document.getElementById('cb_'+type+'_'+cbValue);
	// to simulate as if the user clicked on the checkbox we first reverse its state and then call
	// appFilterCbClicked
	if (cbObj.checked) 
		cbObj.checked = false;
	else
		cbObj.checked = true;

	appFilterCbClicked(type, cbValue);
}

// This will return a comma separated string for the filters of a particular type.
// type : 'category' | 'subcategory' | 'technology' | 'risk' | 'characteristic'
function getSelectedAppFilters(type) {
	// we will get the form name by getting the form for the cb_<type>_all checkbox
	var cbAllObj = document.getElementById('cb_'+type+'_all');
	var formName = cbAllObj.form.name;
	var cbName = 'cb_' + type;

	// before getting the filter string we will temporarily turn off the all checkbox or else it shows up in the
	// filter list which we do not want
	var cbAllObjState = cbAllObj.checked; // save the current state
	cbAllObj.checked = false; 

	var filterStr = getCheckedValues(formName, cbName, ",");

	// reset to old state
	cbAllObj.checked = cbAllObjState;

	return filterStr;
}

// If the cbCustomAppOnly button is checked this returns "yes" else it returns ""
function getCustomAppOnlyChecked() {
	var customAppOnly = "";
	var customAppObj = document.getElementById('cbCustomAppOnly'); 
	if (customAppObj && customAppObj.checked)
		customAppOnly = "yes";

	return customAppOnly;
}

function getCurrentAppFilterSortBy() {
	var obj = document.getElementById('h_app_sort_by');
	if (obj) {
		return document.getElementById('h_app_sort_by').value;
	} else {
		return "name"; // default is sort by name
	}
}

function getCurrentAppFilterSortOrder() {
	var obj = document.getElementById('h_app_sort_order');
	if (obj) {
		return document.getElementById('h_app_sort_order').value;
	} else {
		return "ascending"; // default is sort ascending 
	}
}

/*
 * This either shows or hides the "working ..." div
 */
function showWaitDiv(doShow) {
	var divWait = document.getElementById("dWait");
	if (!divWait)
		return;

	if (doShow) {
		var wdHeight = getInsideWindowHeight() - 0;
		var wdWidth = getInsideWindowWidth() - 0;

		divWait.style.position = "absolute";
		divWait.style.left = (wdWidth-20)/2;
		divWait.style.top = (wdHeight-5)/2;

		divWait.style.display = "block";
	} else {
		divWait.style.display = "none";
	}
}

/* This needs to be a callback. Each page using app filters must provide this. The main applications
 * page will need to do something different from other popup pages
 function getFilteredAndSortedAppsContent(categoryFilters, subcategoryFilters, technologyFilters, riskFilters, characteristicFilters, searchFilter, sortby, sortorder) {
 var url = "/esp/applications.esp?f_category="+categoryFilters+"&f_subcategory="+subcategoryFilters+"&f_technology="+technologyFilters+"&f_risk="+riskFilters+"&f_characteristic="+characteristicFilters+"&f_search="+searchFilter+"&app_sort_by="+sortby+"&app_sort_order="+sortorder;
// Each page where this is shown must provide this function to do stuff specific to that page
getAppContentWorkingDiv(url);
}
 */

/*
 * This is called when the user clicks on the "Clear all filters" link
 */
function clearApplicationFilters() {
	getFilteredAndSortedAppsContent('', '', '', '', '', '', '', '', '');
}

function doAppSearch(evt, txtFldObj) {
	evt = (evt) ? evt : ((event) ? event : null);

	var charCode = (evt.charCode) ? evt.charCode : evt.keyCode;

	if (charCode == 13) {
		// enter key pressed
		var searchFilterObj = document.getElementById('tAppSearch');
		var searchFilter = searchFilterObj.value;
		if (searchFilter.indexOf("\"") >= 0) {
			alert(PAN_MSG_1004);
			searchFilterObj.focus();
			searchFilterObj.select();
			return;
		}
		var categoryFilters = getSelectedAppFilters('category');
		var subcategoryFilters = getSelectedAppFilters('subcategory');
		var technologyFilters = getSelectedAppFilters('technology');
		var riskFilters = getSelectedAppFilters('risk');
		var characteristicFilters = getSelectedAppFilters('characteristic');

		var customAppOnly = "";
		var customAppObj = document.getElementById('cbCustomAppOnly'); 
		if (customAppObj && customAppObj.checked)
			customAppOnly = "yes";

		var sortby = document.getElementById('h_app_sort_by').value;
		var sortorder = document.getElementById('h_app_sort_order').value;

		getFilteredAndSortedAppsContent(categoryFilters, subcategoryFilters, technologyFilters, riskFilters, characteristicFilters, searchFilter, customAppOnly, sortby, sortorder);

		// do not propogate the event upwards 
		// clicked
		evt.cancelBubble = true;
		if (evt.stopPropagation) evt.stopPropagation();
	}
}

/*
 * This is called when the user clicks to sort a particular column
 */
function doAppSort(sortby, sortorder) {
	var categoryFilters = getSelectedAppFilters('category');
	var subcategoryFilters = getSelectedAppFilters('subcategory');
	var technologyFilters = getSelectedAppFilters('technology');
	var riskFilters = getSelectedAppFilters('risk');
	var characteristicFilters = getSelectedAppFilters('characteristic');
	var searchFilter = document.getElementById('tAppSearch').value;

	var customAppOnly = "";
	var customAppObj = document.getElementById('cbCustomAppOnly'); 
	if (customAppObj && customAppObj.checked)
		customAppOnly = "yes";

	getFilteredAndSortedAppsContent(categoryFilters, subcategoryFilters, technologyFilters, riskFilters, characteristicFilters, searchFilter, customAppOnly, sortby, sortorder);
}

/*
 * This is called when the user clicks on the Custom App Only checkbox
 */
function doCustomAppOnly(cbObj) {
	var categoryFilters = getSelectedAppFilters('category');
	var subcategoryFilters = getSelectedAppFilters('subcategory');
	var technologyFilters = getSelectedAppFilters('technology');
	var riskFilters = getSelectedAppFilters('risk');
	var characteristicFilters = getSelectedAppFilters('characteristic');
	var searchFilter = document.getElementById('tAppSearch').value;

	var customAppOnly = "";
	if (cbObj.checked) 
		customAppOnly = "yes";

	var sortby = document.getElementById('h_app_sort_by').value;
	var sortorder = document.getElementById('h_app_sort_order').value;

	getFilteredAndSortedAppsContent(categoryFilters, subcategoryFilters, technologyFilters, riskFilters, characteristicFilters, searchFilter, customAppOnly, sortby, sortorder);
}

/*
 * This just copies the hidden value to a span so that after we get the list of filtered
 * applications, we can show the number of such applications
 */
function copyNumberFilteredApplications() {
	// copy the number of apps to the matching filters span
	var numAppsObj = document.getElementById("h_numApps");
	var matchingNumObj = document.getElementById("spMatchingNum");
	if (numAppsObj && matchingNumObj) {
		matchingNumObj.innerHTML = numAppsObj.value;
	}
}

// called from applications.esp, editAppFilter.esp editApplicationGroup.esp,
// editApplicationInRuleMultiple.esp etc. (i.e. where ever we show the app browser)
function panShowApplicationDetail(loc, appName, all, evt)
{
	evt = (evt) ? evt : ((event) ? event : null);

	// In IE clicking on <href> in a <tr> calls the onclick handler of href but not that
	// of <tr>. So I need to explicitly call it here
	mouseClickedTableRow(appName);

	var url = "/esp/applicationDetail.esp?loc="+loc+"&appname="+appName+"&all="+all;
	openModalWindow(url, "applicationDetail", 1000, 450, evt.screenX, evt.screenY);
}

/***************** end of functions related to application filtering ******************/

/* check if the expression contains characters - space, parenthesis etc that requires 
   to be enclosed in quote */

function valueNeedsQuote(valu) {
        var re = /[ ()<>{}~!@#$%^&*+=|;:?\\\[\]]/;

	if (valu.search(re) == -1) {
           if (panEscapeURI(valu) == valu) {
		return false;
           }
	}

	return true;
}

/* In device or in panorama device context the rulebase is just passed as 'type'. However
 * in panorama panorama context the rulebase is passed as 'type'/'sequence' and so this 
 * will help us get type from the str
 */
function getRuleBaseTypeFromStr(str) {
	var arr = str.split("/");

	return arr[0];
}

function getRuleBaseSequenceFromStr(str) {
	var arr = str.split("/");

	if (arr.length == 2)
		return arr[1];
	else
		return "";
}

function getRuleBaseTypePrintableStr(ruleBaseType) {
	var str;

	if (ruleBaseType == "security") {
		str = "Security";
	} else if (ruleBaseType == "application-override") {
		str = "Application Override";
	} else if (ruleBaseType == "ssl-decryption") {
		str = "SSL Decryption";
	} else if (ruleBaseType == "nat") {
		str = "NAT";
	} else if (ruleBaseType == "captive-portal") {
		str = "Captive Portal";
	} else if (ruleBaseType == "qos") {
		str = "QoS";
	}

	return str;
}


function dummyLink(showAlert) {
	if (showAlert)
		alert("Edit not allowed.");

	return false;
}

/*////////////// Functions to print the stack trace //////////////*/

function printStackTrace() {   
    var callstack = [];   
    var isCallstackPopulated = false;   
    try {   
        i.dont.exist+=0; //does not exist - thats the point   
    } catch(e) {   
        if (e.stack) { //Firefox   
            var lines = e.stack.split("\n");   
            for (var i = 0, len = lines.length; i < len; i++) {   
                if (lines[i].match(/^\s*[A-Za-z0-9\-_\$]+\(/)) {   
                    callstack.push(lines[i]);   
                }   
            }   
            //Remove call to printStackTrace()   
            callstack.shift();   
            isCallstackPopulated = true;   
        }   
        else if (window.opera && e.message) { //Opera   
            var lines = e.message.split("\n");   
            for (var i = 0, len = lines.length; i < len; i++) {   
                if (lines[i].match(/^\s*[A-Za-z0-9\-_\$]+\(/)) {   
                    var entry = lines[i];   
                    //Append next line also since it has the file info   
                    if (lines[i+1]) {   
                        entry += " at " + lines[i+1];   
                        i++;   
                    }   
                    callstack.push(entry);   
                }   
            }   
            //Remove call to printStackTrace()   
            callstack.shift();   
            isCallstackPopulated = true;   
        }   
    }   
    if (!isCallstackPopulated) { //IE and Safari   
        var currentFunction = arguments.callee.caller;   
        while (currentFunction) {   
            var fn = currentFunction.toString();   
            //If we cant get the function name set to "anonymous"   
            var fname = fn.substring(fn.indexOf("function") + 8, fn.indexOf("(")) || "anonymous";   
            callstack.push(fname);   
            currentFunction = currentFunction.caller;   
        }   
    }   
    outputStackTrace(callstack);   
}   
  
function outputStackTrace(arr) {   
    //Optput however you want   
    alert(arr.join("\n\n"));   
}  

/*///////////// End Functions to print the stack trace /////////////*/


/*///////////// Functions dealing with clone objects /////////////*/

/*
 * This is called by the popup windows which show up when we want to clone objects
 *
 * This will return the selected checkbox values separated by 'separator'
 */
function getObjectsToBeCloned(formName, checkboxName, separator) {
	var str = getCheckedValues(formName, checkboxName, separator);

	return str;
}

// called from various pages where cloning of objects is required
// 1. thisForm - the form with the checkboxes
// 2. cbName is the name of the checkbox
// 3. objType will be the node in the schema under the location which refers to the object (e.g.
// address or profiles/data-objects
// 4. objTypeStr - a printable version of the object string to be used in the GUI
// 5. rolebasexpath will be name on the tree in the left menu (this is needed for role based admin) like 'objects/addresses' or 'objects/data-objects',
// 6. tab - top level tab
// 7. returnTo will have the page to return to like '/esp/addresses.esp'
// 8. showShared (optional) - if "no" then we will not show the "shared" checkbox
function cloneObjects(thisForm, cbName, evt, objType, objTypeStr, rbxpath, tab, returnTo, showShared)
{
	evt = (evt) ? evt : ((event) ? event : null);
    var permission = Pan.rolebasePermission(rbxpath);
	if (tab == "objects" || tab == "device") {
		if (!permission || permission == 'read-only') {
			return false;
        }
	}

	// we need to calculate zaddrC (the last C is for calculated). 
	// We are not going to use the checkboxes because multiple values 
	// are separated by a blank and if we have blanks in the name we run
	// into problems. So I am going to create a tab separated string and
	// fill zaddrC with it.
	var str = getCheckedValues(thisForm.name, cbName, "@");;

	if (str == "") {
		// nothing checked
		alert("You must choose at least one " + objTypeStr + ".");
		return false;
	} else {
		var url = "/esp/cloneObjects.esp?formName=" + thisForm.name + "&checkboxName=" + cbName + "&objType=" + objType + "&objTypeStr=" + objTypeStr + "&returnTo=" + returnTo + "&rolebasexpath="+ rbxpath + "&tab=" + tab;
		if (showShared != undefined && showShared == "no") {
			url += "&showShared=no";
		}
		url = replaceBlankWithPlus(url);
		openModalWindow(url, "cloneObjects", 500, 200, evt.screenX, evt.screenY);
	}
}

/*///////////// End Functions dealing with clone objects /////////////*/


/*
 * Now we sanitize form variables when they are submitted (see sessionCheck.esp)
 * This is to do the reverse of that. 
 * &lt; --> <
 * &gt; --> >
 * &amp; --> &
 * &quot; --> "
 * &#x27; --> '
 *
 *
 * If escapeForJS is true then it will also replace
 * \ --> \\
 * & --> \&
 * ' --> \'
 * " --> \"
 * 
 */
function unsanitizeFormField(str, escapeForJS) {
	var val = str.replace(/&lt;/g, "<");
	val = val.replace(/&gt;/g, ">");
	val = val.replace(/&amp;/g, "&");
	val = val.replace(/&quot;/g, "\"");
	val = val.replace(/&#x27;/g, "\'");

	if (escapeForJS) {
		val = val.replace(/\\/g, "\\\\");
		val = val.replace(/&/g, "\&");
		val = val.replace(/\'/g, "\\\'");
		val = val.replace(/\"/g, "\\\"");
	}

	return val;
}









