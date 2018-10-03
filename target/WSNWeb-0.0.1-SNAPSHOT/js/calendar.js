function updateDayCheckBox(name) {
	var v = '';
	for(i=0; i<=6; i++) {
		var obj = document.getElementById(name+'_chk'+i);
		if (obj.checked) {
			v+='1';
		} else {
			v+='0';
		}
	}
	document.getElementById(name).value=v;
}

function applyDayCheckBox(name, value) {
	for(i=0; i<value.length; i++) {
		var obj = document.getElementById(name+'_chk'+i);
		if (value.charAt(i)!='0') {
			obj.checked = true;
		}
	}
}

function CalendarControl() {
	this.selectDate = selectDate;
	this.writeCalendar = writeCalendar;
	this.setDate = setDate;
	this.prevMonth = prevMonth;
	this.nextMonth = nextMonth;
	this.prevYear = prevYear;
	this.nextYear = nextYear;
	this.setHoliday = setHoliday;
	this.setNextImage = setNextImage;
	this.setPreviousImage = setPreviousImage;
	this.clear = clear;
	
	var txtbx = null;
	var now = new Date();
	var months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
	var dayofweek = ['S','M','T','W','T','F','S'];
	var currentYear = now.getYear();
	var currentMonth = now.getMonth();
	var currentDate = now.getDate();
	
	var txtselectedMonth = 0;
	var txtselectedYear = 0;
	var txtselectedDate = 0;
	
	var selectedYear = 0;
	var selectedMonth = 0;
	var selectedDate = 0;
	
	var holiday = null;
	
	var nextImage = null;
	var previousImage = null;
	
	function setHoliday(h) {
		holiday = h;
	}
	
	function setNextImage(i) {
		nextImage = i;
	}
	
	function setPreviousImage(i) {
		previousImage = i;
	}	
	
	function getDayOfWeek(year, month, day) {
		var date = new Date(year,month-1,day)
		return date.getDay();
	}
	
	function getDaysInMonth(year, month) {
		return [31,((!(year % 4 ) && ( (year % 100 ) || !( year % 400 ) ))?29:28),31,30,31,30,31,31,30,31,30,31][month-1];
	}
	
	function writeCalendar(tbDate) {
		txtbx = tbDate;		
		var dayOfMonth = 1;
		var validDay = 0;
		var daysInMonth = getDaysInMonth(selectedYear, selectedMonth+1);
		var startDayOfWeek = getDayOfWeek(selectedYear, selectedMonth+1, dayOfMonth);
		var table = "<table cellspacing='0' cellpadding='0' border='1' width='100%'>";
		table += "<tr class='month'><td colspan='7'><table cellspacing='0' cellpadding='0' border='0' width='100%'><tr height='20'>";
		
		if (previousImage!=null && nextImage!=null) {
			table += "<td align='left'>";
			table += "<img src='"+previousImage+"' onclick='calendarControl.prevMonth()' style='cursor: pointer'>";
			table += "<img src='"+nextImage+"' onclick='calendarControl.nextMonth()' style='cursor: pointer'>";	
			table += "</td>";
		}
		table += "<td align='left' class='month' width='50%'>"+months[selectedMonth]+"</td>";
		table += "<td align='right' class='month' width='50%'>"+selectedYear+"</td>";
		
		if (previousImage!=null && nextImage!=null) {
			table += "<td align='right'>";
			table += "<img src='"+previousImage+"' onclick='calendarControl.prevYear()' style='cursor: pointer'>";
			table += "<img src='"+nextImage+"' onclick='calendarControl.nextYear()' style='cursor: pointer'>";
			table += "</td>";
		}
		table += "</tr></table></td></tr>";
		table += "<tr class='daysofweek'>";
		for(var i=0; i<dayofweek.length; i++) {
			table += "<td width='30'>"+dayofweek[i]+"</td>";
		}
		table += "</tr>";
		
		for(var week=0; week < 6; week++) {
	      table = table + "<tr>";
	      for(var dayOfWeek=0; dayOfWeek < 7; dayOfWeek++) {
	        if(week == 0 && startDayOfWeek == dayOfWeek) {
	          validDay = 1;
	        } else if (validDay == 1 && dayOfMonth > daysInMonth) {
	          validDay = 0;
	        }
			
	        if(validDay) {
	          if (dayOfMonth == txtselectedDate && selectedMonth==txtselectedMonth && selectedYear==txtselectedYear) {
	            css_class = 'selected';
	          } else if (dayOfMonth == currentDate && selectedMonth == currentMonth && selectedYear == currentYear) {
	            css_class = 'current';
	          } else if (holiday==null) {
		            if (dayOfWeek == 0 || dayOfWeek == 6) {
		            	css_class = 'weekend';
		            } else {
			            css_class = 'weekday';
		            }
	          } else {
	          		if (holiday[dayOfWeek]) {
	          			css_class = 'weekend';
	          		} else {
		          		css_class = 'weekday';
	          		}
	          }
	
	          table = table + "<td class='"+css_class+"' onclick='calendarControl.selectDate("+dayOfMonth+")'"
	          table = table + ">"+dayOfMonth+"</td>";
	          dayOfMonth++;
	        } else {
	          table = table + "<td class='empty'>&nbsp;</td>";
	        }
	      }
	      table = table + "</tr>";
	    }
	    table += "<tr class='month'><td colspan='7' align='center'><a href='javascript:hideCalendar();'>Close</a>&nbsp;&nbsp;<a href='javascript:calendarControl.clear();'>Clear</a></td></tr>";
		table += "</table>";
		return table;
	}
	
	function clear() {
		txtbx.value="";
		if (txtbx.onchange!=null) {
			txtbx.onchange();
		}
		var popup = document.getElementById("CalendarPopup");
		popup.style.display="none";
	}
			
	function selectDate(sday) {
		txtbx.value=sday+"/"+(selectedMonth+1)+"/"+selectedYear;
		if (txtbx.onchange!=null) {
			txtbx.onchange();
		}
		var popup = document.getElementById("CalendarPopup");
		popup.style.display="none";
	}
	
	function prevYear() {
		selectedYear--;
		var popup = document.getElementById("CalendarPopup");
		popup.innerHTML=calendarControl.writeCalendar(txtbx);
	}
	
	function prevMonth() {			
		selectedMonth--;
		if (selectedMonth<0) {
			selectedMonth=11;
			selectedYear--;
		}
		var popup = document.getElementById("CalendarPopup");
		popup.innerHTML=calendarControl.writeCalendar(txtbx);
	}
	
	function nextYear() {
		selectedYear++;
		var popup = document.getElementById("CalendarPopup");
		popup.innerHTML=calendarControl.writeCalendar(txtbx);
	}
	
	function nextMonth() {
		selectedMonth++;
		if (selectedMonth>11) {
			selectedMonth=0;
			selectedYear++;
		}
		var popup = document.getElementById("CalendarPopup");
		popup.innerHTML=calendarControl.writeCalendar(txtbx);
	}
	
	function setDate(strDate) {
		if(strDate.length>0) {
			selectedDate = Number(strDate.substring(0, strDate.indexOf("/")));
			var temp = strDate.substring(strDate.indexOf("/")+1, strDate.length);
			selectedMonth = Number(temp.substring(0, temp.indexOf("/")))-1;
			selectedYear = Number(temp.substring(temp.indexOf("/")+1, temp.length));
			txtselectedMonth = selectedMonth;
			txtselectedYear = selectedYear;
			txtselectedDate = selectedDate;
		} else {
			selectedYear = currentYear;
			selectedMonth = currentMonth;
		}
	}
}

function ColorControl() {
	var txtbx = null;	
	this.writeColor = writeColor;
	this.selectColor = selectColor;
	
	function writeColor(tbColor) {		
		txtbx = tbColor;
		color = [["#8A0808","#868A08","#088A08","#088A85","#08088A","#8A0886"],
		["#B40404","#AEB404","#04B404","#04B4AE","#0404B4","#B404AE"],
		["#FF0000","#FFFF00","#00FF00","#00FFFF","#0000FF","#FF00FF"],
		["#F78181","#F3F781","#81F781","#81F7F3","#8181F7","#F781F3"],
		["#F781BE","#F7BE81","#BEF781","#81F7BE","#81BEF7","#BE81F7"],
		["#FF0080","#FF8000","#80FF00","#00FF80","#0080FF","#8000FF"],
		["#B4045F","#B45F04","#5FB404","#04B45F","#045FB4","#5F04B4"],
		];
		var table = "<table cellspacing='0' cellpadding='0' border='1' width='100%' style='cursor:pointer; font-size: 9'>";
		table += "<tr>"
		var r=0;
		for(i in color) {
			for(j in color[i]) {
				table += "<td bgcolor=\""+color[i][j]+"\" onclick=\"colorControl.selectColor('"+color[i][j]+"')\">&nbsp;</td>";			
			}
			table += "</tr><tr>"
		}
		table += "</tr>"
		table += "</table>";
		return table;
	}
			
	function selectColor(c) {
	var popup = document.getElementById("ColorPopup");
		popup.style.display="none";
		var ci = c.substring(1,c.lenght);
		var cj = parseInt(ci, 16);
		cj = 16777215 - cj;
		ci = cj.toString(16);		
		while (ci.length < 6) ci = "0"+ci;
		ci = "#"+ci;
		txtbx.value=c;
		txtbx.style.backgroundColor=c;
		txtbx.style.color=ci;
	}
}

var calendarControl = new CalendarControl();
var colorControl = new ColorControl();

function showCalendar1(txtbx) {
	var x = 0;
	var y = 0;
	var elm = txtbx;	
	while (elm != null) {
		alert(elm.offsetTop);	  
      x+= elm.offsetLeft;
      y+= elm.offsetTop;
      elm = elm.offsetParent;     
    }
    alert(y);
    alert(y+txtbx.offsetHeight);
    calendarControl.setDate(txtbx.value);
    var popup = document.getElementById("CalendarPopup");
    popup.innerHTML=calendarControl.writeCalendar(txtbx);
	popup.style.top=y+txtbx.offsetHeight;
	popup.style.left=x+txtbx.offsetWidth-175;
	popup.style.display="block";
}

function hideCalendar() {
	var popup = document.getElementById("CalendarPopup");
	popup.style.display="none";
}

function showColor(txtbx) {
	var x = 0;
	var y = 0;
	var elm = txtbx;
	while (elm != null) {
      x+= elm.offsetLeft;
      y+= elm.offsetTop;
      elm = elm.offsetParent;
    }
	document.all.ColorPopup.style.top=y+txtbx.offsetHeight;
	document.all.ColorPopup.style.left=x+txtbx.offsetWidth-150;
	document.all.ColorPopup.style.display="block";
	document.all.ColorPopup.innerHTML=colorControl.writeColor(txtbx);
}

function writeColor(txtbx, c) {
	var ci = c.substring(1,c.lenght);
	var cj = parseInt(ci, 16);
	cj = 16777215 - cj;
	ci = cj.toString(16);		
	while (ci.length < 6) ci = "0"+ci;
	ci = "#"+ci;
	txtbx.value=c;
	txtbx.style.backgroundColor=c;
	txtbx.style.color=ci;
}

function hideColor() {
	document.all.ColorPopup.style.display="none";
}

function addToList(txtbx, lslbx, regex) {
	var value = txtbx.value.replace(/^\s*/, "").replace(/\s*$/, "")
	if (regex!=null && !validateExp(regex, value)) {
		alert("Item format error");
		return;
	}	
	if (value=='') {
		alert("Add item to list error");
		return;
	}
	for (i=0; i<lslbx.options.length; i++) {
		if (value==lslbx.options[i].value) {
			alert("Item duplicate error");
			return;
		}
	}
	var oOption = new Option(txtbx.value, value);
	lslbx.options[lslbx.options.length]=oOption;
	txtbx.value = "";
}

function removeFromList(txtbx, lslbx) {
	if (lslbx.selectedIndex>=0) {
		txtbx.value = lslbx.options[lslbx.selectedIndex].value;
		lslbx.options[lslbx.selectedIndex] = null;
	}
}

function selectAllItem(lslbx) {
	for (i=0; i<lslbx.options.length; i++) {
		lslbx.options[i].selected = true;
	}
}

function getfileextension(filename) {
	if( filename.length == 0 ) return "";
	var dot = filename.lastIndexOf(".");
	if( dot == -1 ) return "";
	var extension = filename.substr(dot+1,filename.length);
	return extension;
} 

function checkfileType(fileext, txtfile) {
	if (txtfile=="") return true;
	var ext = getfileextension(txtfile);
	for(var i=0; i<fileext.length; i++) {
		if (ext.toLowerCase()==fileext[i].toLowerCase()) {
			return true;
		}
	}
	return false
}

function snapToObject(obj1, obj2, top, left, height, width) {
	var tmp = obj2
	var x = 0;
	var y = 0;
	while (tmp != null) {
      x+= tmp.offsetLeft;
      y+= tmp.offsetTop;
      tmp = tmp.offsetParent;
    }
    obj1.style.left = x+left;
    obj1.style.top = y+top;
    obj1.style.height = obj2.offsetHeight+height;
    obj1.style.width = obj2.offsetWidth+width;
}

function moveItem(lst1, lst2) {
	//if (lst1.selectedIndex>=0) {
	//	var oOption = new Option(lst1.options[lst1.selectedIndex].text, lst1.options[lst1.selectedIndex].value);
	//	lst1.options[lst1.selectedIndex] = null;
	//	lst2.options[lst2.options.length] = oOption;
	//}
	
	for(var i=0; i<lst1.options.length; i++) {
		if (lst1.options[i].selected==true) {
			var oOption = new Option(lst1.options[i].text, lst1.options[i].value);
			lst2.options[lst2.options.length] = oOption;
			lst1.options[i] = null;
			i--;
		}
	}
}

function addItem(txt, lslbx) {
	var oOption = new Option(txt, txt);
	lslbx.options[lslbx.options.length]=oOption;
}

function removeItem(txt, lslbx) {
	for (var i=0; i<lslbx.length; i++) {
		if (lslbx.options[i].value==txt) {
			lslbx.options[i] = null;
		}
	}
}

function writeTreeCheckBox2(path, name, div, keyList, indexList, preImgList) {
	if (div.innerHTML=="") {
		div.innerHTML = createTreeCheckBox2(path, name, keyList, indexList, preImgList, 0);
	}
	var activediv = eval(name+"_activediv");	
	while (activediv.indexOf("_")>=0) {
		var box = document.getElementById(name+activediv);
		var img = document.getElementById(name+"_img"+activediv);
		box.style.display = "block";
		img.src = path+"/dcswc/image/collapseminus.gif";
		activediv = activediv.substring(0, activediv.lastIndexOf("_"));
	}
}

function writeTreeRadio2(path, name, div, keyList, indexList, preImgList) {
	if (div.innerHTML=="") {
		div.innerHTML = createTreeRadio2(path, name, keyList, indexList, preImgList, 0);
	}
	var activediv = eval(name+"_activediv");	
	while (activediv.indexOf("_")>=0) {
		var box = document.getElementById(name+activediv);
		var img = document.getElementById(name+"_img"+activediv);
		box.style.display = "block";
		img.src = path+"/dcswc/image/collapseminus.gif";
		activediv = activediv.substring(0, activediv.lastIndexOf("_"));
	}
}

function createTreeCheckBox2(path, name, keyList, indexList, preImgList, idx) {
	var indexSuffix = "";
	for(var i=0; i<idx; i++) {
		indexSuffix += "["+indexList[i]+"].child";
	}
	var tree = eval(name+"_tree"+indexSuffix);
	var value = eval(name+"_value");
	var keyPrefix = "";
	for(var i=0; i<idx; i++) {
		keyPrefix += "_"+keyList[i];
	}
	//var idx=keyList.length;
	var buf="<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\" style=\"font-size: 9pt;\">";
	for(var i=0; i<tree.length; i++) {				
		keyList[idx]=tree[i].key;
		indexList[idx]=i;
		if (i<tree.length-1) {
			preImgList[idx]=1;
		} else {
			preImgList[idx]=0;
		}
		buf+="<tr><td style=\"vertical-align: top\" width=\"18\">";
		for(var j=0; j<idx; j++) {
			if (preImgList[j]==1) {
				buf+="<img src=\""+path+"/dcswc/image/collapseline.gif\">";
			} else {
				buf+="<img src=\""+path+"/dcswc/image/collapseblank.gif\">";
			}
			buf+="</td><td style=\"vertical-align: top\" width=\"18\">";
		}
		if (tree[i].child.length>0) {
			buf+="<img id=\""+name+"_img"+keyPrefix+"_"+tree[i].key+"\" src=\""+path+"/dcswc/image/collapseplus.gif\" onclick=\"treetoggle('"+path+"','"+name+"',this,'"+name+keyPrefix+"_"+tree[i].key+"',";
			buf+=JSON.stringify(keyList).replace(/"/g,"'")+","+JSON.stringify(indexList)+","+JSON.stringify(preImgList)+",false)\">";
		} else {
			if (i<tree.length-1) {
				buf+="<img src=\""+path+"/dcswc/image/collapsenull.gif\">";
			} else {
				buf+="<img src=\""+path+"/dcswc/image/collapseend.gif\">";
			}
		}
		buf+="</td><td style=\"vertical-align: bottom; text-align: left\" width=\"100%\">";
		//var checkValue = keyPrefix+"_"+tree[i].key;
		buf+="<input type=\"radio\" name=\""+name+"\" value=\""+tree[i].key+"\" ";
		if (tree[i].key==value) {
			buf+="checked ";
			eval(name+"_activediv=keyPrefix");
		}
		buf+=">";
		buf+=tree[i].value;
		buf+="</td></tr>\n";
		if (tree[i].child.length>0) {
			buf+="<tr><td colspan=\""+(2+keyList.length)+"\">";
			buf+="<span id=\""+name+keyPrefix+"_"+tree[i].key+"\" style=\"display : none;\">";			
			buf+=createTreeCheckBox2(path, name, keyList, indexList, preImgList, idx+1)
			buf+="</span>";
			buf+="</td></tr>\n";
		}
	}
	buf+="</table>";
	return buf;
}

function createTreeRadio2(path, name, keyList, indexList, preImgList, idx) {
	var indexSuffix = "";
	for(var i=0; i<idx; i++) {
		indexSuffix += "["+indexList[i]+"].child";
	}
	var tree = eval(name+"_tree"+indexSuffix);
	var value = eval(name+"_value");
	var keyPrefix = "";
	for(var i=0; i<idx; i++) {
		keyPrefix += "_"+keyList[i];
	}
	//var idx=keyList.length;
	var buf="<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\" style=\"font-size: 9pt;\">";
	for(var i=0; i<tree.length; i++) {				
		keyList[idx]=tree[i].key;
		indexList[idx]=i;
		if (i<tree.length-1) {
			preImgList[idx]=1;
		} else {
			preImgList[idx]=0;
		}
		buf+="<tr><td style=\"vertical-align: top\" width=\"18\">";
		for(var j=0; j<idx; j++) {
			if (preImgList[j]==1) {
				buf+="<img src=\""+path+"/dcswc/image/collapseline.gif\">";
			} else {
				buf+="<img src=\""+path+"/dcswc/image/collapseblank.gif\">";
			}
			buf+="</td><td style=\"vertical-align: top\" width=\"18\">";
		}
		if (tree[i].child.length>0) {
			buf+="<img id=\""+name+"_img"+keyPrefix+"_"+tree[i].key+"\" src=\""+path+"/dcswc/image/collapseplus.gif\" onclick=\"treetoggle('"+path+"','"+name+"',this,'"+name+keyPrefix+"_"+tree[i].key+"',";
			buf+=JSON.stringify(keyList).replace(/"/g,"'")+","+JSON.stringify(indexList)+","+JSON.stringify(preImgList)+",false)\">";
		} else {
			if (i<tree.length-1) {
				buf+="<img src=\""+path+"/dcswc/image/collapsenull.gif\">";
			} else {
				buf+="<img src=\""+path+"/dcswc/image/collapseend.gif\">";
			}
		}
		buf+="</td><td style=\"vertical-align: bottom; text-align: left\" width=\"100%\">";
		//var checkValue = keyPrefix+"_"+tree[i].key;
		buf+="<input type=\"radio\" name=\""+name+"\" value=\""+tree[i].key+"\" ";
		if (tree[i].key==value) {
			buf+="checked ";
			eval(name+"_activediv=keyPrefix");
		}
		buf+=">";
		buf+=tree[i].value;
		buf+="</td></tr>\n";
		if (tree[i].child.length>0) {
			buf+="<tr><td colspan=\""+(2+keyList.length)+"\">";
			buf+="<span id=\""+name+keyPrefix+"_"+tree[i].key+"\" style=\"display : none;\">";			
			buf+=createTreeRadio2(path, name, keyList, indexList, preImgList, idx+1)
			buf+="</span>";
			buf+="</td></tr>\n";
		}
	}
	buf+="</table>";
	return buf;
}

function treetoggle(p, name, img , divname, keyList, indexList, preImgList, createAll) {
	var box = document.getElementById(divname);
	box.style.display = (box.style.display=="block") ? "none" : "block";
	img.src = (box.style.display=="block") ? p+"/dcswc/image/collapseminus.gif" : p+"/dcswc/image/collapseplus.gif";
	if (box.innerHTML=="") {
		writeTreeRadio(p, name, box, keyList, indexList, preImgList, createAll);
	}
}

function writeTreeRadio(path, name, div, keyList, indexList, preImgList, createAll) {
	if (div.innerHTML=="") {
		div.innerHTML = createTreeRadio(path, name, keyList, indexList, preImgList, createAll);
	}
}

function writeHiddenCheckbox(name, div, list) {
	var buf="";
	for(var i=0; i<list.length; i++) {
		//buf+="<input type=\"checkbox\" name=\""+name+"\" value=\""+list[i]+"\" checked=\"true\">";
	}
	div.innerHTML = buf;
}

function createTreeRadio(path, name, keyList, indexList, preImgList, createAll) {
	var indexSuffix = "";
	for(var i=0; i<keyList.length; i++) {
		indexSuffix += "["+indexList[i]+"].child";
	}
	var tree = eval(name+"_tree"+indexSuffix);
	var firstCheck = eval(name+"_firstLevelCheck");
	var keyPrefix = "";	
	for(var i=0; i<keyList.length; i++) {
		keyPrefix += "_"+keyList[i];
	}
	var idx=keyList.length;
	var buf="<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\" style=\"font-size: 9pt;\">";
	for(var i=0; i<tree.length; i++) {
		keyList[idx]=tree[i].key;
		indexList[idx]=i;
		if (i<tree.length-1) {
			preImgList[idx]=1;
		} else {
			preImgList[idx]=0;
		}
		buf+="<tr><td style=\"vertical-align: top\" width=\"18\">";
		for(var j=0; j<preImgList.length-1; j++) {
			if (preImgList[j]==1) {
				buf+="<img src=\""+path+"/dcswc/image/collapseline.gif\">";
			} else {
				buf+="<img src=\""+path+"/dcswc/image/collapseblank.gif\">";
			}
			buf+="</td><td style=\"vertical-align: top\" width=\"18\">";
		}
		if (tree[i].child.length>0) {
			buf+="<img src=\""+path+"/dcswc/image/collapseplus.gif\" onclick=\"treetoggle('"+path+"','"+name+"',this,'"+name+keyPrefix+"_"+tree[i].key+"',";
			buf+=JSON.stringify(keyList).replace(/"/g,"'")+","+JSON.stringify(indexList)+","+JSON.stringify(preImgList)+",false)\">";
		} else {
			if (i<tree.length-1) {
				buf+="<img src=\""+path+"/dcswc/image/collapsenull.gif\">";
			} else {
				buf+="<img src=\""+path+"/dcswc/image/collapseend.gif\">";
			}
		}
		buf+="</td><td style=\"vertical-align: bottom; text-align: left\" width=\"100%\">";
		if (idx>0 || firstCheck) {
			var checkValue = keyPrefix+"_"+tree[i].key;
			buf+="<input type=\"checkbox\" name=\""+name+"\" value=\""+checkValue+"\" onclick=\"";
			if (tree[i].child.length>0) {
				buf+="writeTreeRadio('"+path+"','"+name+"',document.all."+name+keyPrefix+"_"+tree[i].key+","+JSON.stringify(keyList).replace(/"/g,"'")+","+JSON.stringify(indexList)+","+JSON.stringify(preImgList)+",true);";
				buf+="checkAllTree(this,'"+name+"','"+checkValue+"');\"";
			} else {
				buf+="checkTree(this,'"+name+"','"+checkValue+"');\"";
			}
			var value = eval(name+"_value");
			for(var j=0; j<value.length; j++) {
				if (value[j]==checkValue) {
					buf+=" checked=\"true\"";
					break;
				}
			}
			buf+=">";
		}
		buf+=tree[i].value;
		buf+="</td></tr>\n";
		if (tree[i].child.length>0) {
			buf+="<tr><td colspan=\""+(2+keyList.length)+"\">";
			buf+="<span id=\""+name+keyPrefix+"_"+tree[i].key+"\" style=\"display : none;\">";
			if (createAll) {
				buf+=createTreeRadio(path, name, keyList, indexList, preImgList, createAll)
			}
			buf+="</span>";
			buf+="</td></tr>\n";
		}
	}
	buf+="</table>";
	return buf;
}

function checkTree(checkbox, name, value) {
	var buf = ""
	var checklist = eval("document.all."+checkbox.name);
	var parentValue = value.substring(0, value.lastIndexOf("_"));
	for(var i=0; i<checklist.length; i++) {
		if(checklist[i].value==(value)) {
			checklist[i].checked = checkbox.checked;
		}
	}
	if(parentValue.length>0) {
		var parent
		var haveCheck = false;
		for(var i=0; i<checklist.length; i++) {
			if(checklist[i].value==(parentValue)) {
				parent = checklist[i];
			} else if(checklist[i].value.indexOf(parentValue)==0) {
				if (checklist[i].checked) {
					haveCheck = true;
					break;
				}
			}
		}
		if (parent!=null && !parent.checked && haveCheck) {
			parent.checked=true;
			checkTree(checkbox, name, parent.value);
		} else if (parent!=null && parent.checked && !haveCheck) {
			parent.checked=false;
			checkTree(checkbox, name, parent.value);
		}
	}
}

function checkAllTree(checkbox, name, value) {
	var buf = ""
	var checklist = eval("document.all."+checkbox.name);
	for(var i=0; i<checklist.length; i++) {
		if(checklist[i].value.indexOf(value+"_")==0) {
			checklist[i].checked=checkbox.checked;
		}
	}
}

function setTreeRadioValue(name, key, value) {
	var objs = eval(name);
	for(var i=0; i<objs.length; i++) {
		var obj = objs[i];
		if (key==obj.key) {
			obj.value = value;
		}
	}
	while((i=key.indexOf(","))>0) {
		var pname = key.substring(0,i);
		var obj = eval("document.all."+name+"_"+pname);
		obj.value="";
		for(var j=0; j<obj.length; j++) {
			obj[j].checked=false;
		}
		key = key.substring(i+1, key.length);
	}
}

function setChildRadioValue(pobj, name, key, value) {
	var pname = pobj.name;
	var oname = pname.substring(pname.indexOf("_")+1, pname.length);
	var obj = eval("document.all."+name+"_"+oname);
	var inx = 0;
	for(var j=0; j<obj.length; j++) {
		if (obj[j].checked) inx=j;
	}
	var objs = eval(name);
	for(var i=0; i<objs.length; i++) {
		var obj = objs[i];
		if (obj.key.indexOf(key)==0) {
			obj.value = value;
			var x = obj.key.indexOf(oname);
			if (x>0) {
				var parentkey = obj.key.substring(0, x+oname.length);
				setTreeRadioValue(name, parentkey, value);
			}
			var childkey = obj.key.substring(x+oname.length+1, obj.key.length)+",";
			while((j=childkey.indexOf(","))>0) {
				var cname = childkey.substring(0,j);
				var cobj = eval("document.all."+name+"_"+cname);
				childkey = childkey.substring(j+1, childkey.length);
				cobj[inx].checked=true;
			}
		}
	}
}

function YearCalendarControl() {
	this.writeCalendar = writeCalendar;
	this.setName = setName;
	this.setYear = setYear;
	this.setDIV = setDIV;
	this.applyHoliday = applyHoliday;
	this.togleHoliday = togleHoliday;
	this.getHoliday = getHoliday;
	this.setHoliday = setHoliday;

	var now = new Date();
	var months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
	var dayofweek = ['S','M','T','W','T','F','S'];
	var calendarYear = now.getYear();
	var currentYear = now.getYear();
	var currentMonth = now.getMonth();
	var currentDate = now.getDate();
	var name;
	var div;
	var holidayList = new Array();
	
	function setName(n) {
		name=n;
	}
	
	function setYear(year) {
		calendarYear = year;
	}
	
	function setDIV(d) {
		div = d;
	}
	
	function getHoliday() {
		return holidayList;
	}
	
	function setHoliday(h) {
		holidayList = h;
	}
	
	function getDayOfWeek(year, month, day) {
		var date = new Date(year, month-1, day)
		return date.getDay();
	}
	
	function getTime(year, month, day) {
		var date = new Date(year, month-1, day)
		return date.getTime();
	}
	
	function getDaysInMonth(year, month) {
		return [31,((!(year % 4 ) && ( (year % 100 ) || !( year % 400 ) ))?29:28),31,30,31,30,31,31,30,31,30,31][month-1];
	}
	
	function writeCalendar() {
		var table="<table><tr><td>";
		var month=0;
		for (var x=0; x<3; x++) {
		  table += "<tr>";
		  for (var y=0; y<4; y++) {
			var dayOfMonth = 1;
			var validDay = 0;
			var daysInMonth = getDaysInMonth(calendarYear, month+1);
			var startDayOfWeek = getDayOfWeek(calendarYear, month+1, dayOfMonth);
			table += "<td><table cellspacing='0' cellpadding='0' border='1' width='175' height='130'>";
			table += "<tr class='month'><td colspan='7'><table cellspacing='0' cellpadding='0' border='0' width='100%'><tr>";
			table += "<td onclick='calendarControl.prevMonth()' align='left' style='cursor: pointer'>";
	
			table += "</td>";
			table += "<td align='center' class='month'>"+months[month]+"</td>";
			table += "<td onclick='calendarControl.nextMonth()' align='right' style='cursor: pointer'>";
	
			table += "</td></tr></table></td></tr>";
			table += "<tr class='daysofweek'>";
			for(var i=0; i<dayofweek.length; i++) {
				table += "<td width='25'>"+dayofweek[i]+"</td>";
			}
			table += "</tr>";
		
			for(var week=0; week < 6; week++) {
		      table += "<tr>";
		      for(var dayOfWeek=0; dayOfWeek < 7; dayOfWeek++) {
		        if(week == 0 && startDayOfWeek == dayOfWeek) {
		          validDay = 1;
		        } else if (validDay == 1 && dayOfMonth > daysInMonth) {
		          validDay = 0;
		        }
				
		        if(validDay) {
		          if (dayOfMonth == currentDate && month == currentMonth && calendarYear == currentYear) {
		            css_class = 'current';
		          } 
	              css_class = 'weekday';

	              var time = dayOfMonth.numberFormat("00")+"/"+(month+1).numberFormat("00")+"/"+calendarYear;
	              for(var i=0; i<holidayList.length; i++) {
	              	if (holidayList[i]==time) {
	              		css_class = 'weekend';
	              		break;
	              	}
	              }
		
		          table += "<td class='"+css_class+"'"
	          	  table += " onclick=\""+name+".togleHoliday('"+time+"')\"";
		          table += ">"+dayOfMonth+"</td>";
		          dayOfMonth++;
		        } else {
		          table += "<td class='empty'>&nbsp;</td>";
		        }
		      }
		      table += "</tr>";
		    }
		    month++;
			table += "</table></td>";
		  }
		  table += "</tr>";
		}
		table += "</table>";
		div.innerHTML=table;
	}
	
	function applyHoliday(defaultHoliday) {
		var date = new Date();
		date.setTime(0);
		date.setFullYear(calendarYear);
		date.setMonth(0);
		date.setDate(1);
		holidayList = new Array();
		while(date.getFullYear()==calendarYear) {	
			if (defaultHoliday[date.getDay()]) {
				var time = date.getDate().numberFormat("00")+"/"+(date.getMonth()+1).numberFormat("00")+"/"+date.getFullYear();
				holidayList.push(time);
			}
			date.setTime(date.getTime()+86400000);
		}
		writeCalendar();
	}
	
	function togleHoliday(day) {
		var remove = false;
		for(var i=0; i<holidayList.length; i++) {
	       if (holidayList[i]==day) {
	       		holidayList.splice(i,1);
	       		remove = true;
	       		break;
	       }
	    }
	    if(!remove) holidayList.push(day);
	    writeCalendar();
	}
	
	function trim(str) {
	    while (str.substring(0,1) == ' ') {
	        str = str.substring(1,str.length);
	    }
	    while (str.substring(str.length-1,str.length) == ' ') {
	        str = str.substring(0,str.length-1);
	    }
	    return str;
	 }
}
