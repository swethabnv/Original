function ResourceSchedule() {
	this.setPath = setPath
	this.setName = setName;
	this.setMinYear = setMinYear;
	this.setMinMonth = setMinMonth;
	this.setMaxYear = setMaxYear;
	this.setMaxMonth = setMaxMonth;
	this.setYear = setYear;
	this.getYear = getYear;
	this.setMonth = setMonth;
	this.getMonth = getMonth;
	this.drawMonthView = drawMonthView;
	this.drawSchedule = drawSchedule;
	this.setWidth = setWidth;
	this.getResource = getResource;
	this.setResource = setResource;
	this.setResourceHeader = setResourceHeader;
	this.setDetailHeader = setDetailHeader;
	this.setSchedule = setSchedule;
	this.getSchedule = getSchedule;
	this.setScheduleItem = setScheduleItem;
	this.getScheduleItem = getScheduleItem;
	this.setHoliday = setHoliday;
	this.setScheduleDiv = setScheduleDiv;
	this.nextMonth = nextMonth;
	this.previousMonth = previousMonth;
	this.createSchedule = createSchedule;
	this.updateSchedule = updateSchedule;
	this.createMoveSchedule = createMoveSchedule;
	this.editMoveSchedule = editMoveSchedule;
	this.moveSchedule = moveSchedule;
	this.setOnCreateSchedule = setOnCreateSchedule;
	this.setOnUpdateSchedule = setOnUpdateSchedule;
	this.setOnMouseOverSchedule = setOnMouseOverSchedule;
	this.setOnMouseOutSchedule = setOnMouseOutSchedule;
	this.setOnMouseClickSchedule = setOnMouseClickSchedule;
	this.setOnChangeMonth = setOnChangeMonth;
	this.removeSchedule = removeSchedule;
	this.removeDiv = removeDiv;
	this.isHoliday = isHoliday;
	this.scheduleOverlap = scheduleOverlap;
	this.setAllowPastScheduleDate = setAllowPastScheduleDate;
	this.setCurrentDate = setCurrentDate;
	this.writeScheduleSkipHoliday = writeScheduleSkipHoliday;
	
	var idCount = 1;
	var idList = new Array();
	var path;
	var name;
	var minYear;
	var minMonth;
	var maxYear;
	var maxMonth;
	var year;
	var month;
	var width;
	var resource;
	var resourceHeader;
	var detailHeader;
	var holiday;
	var schedule;
	var scheduleDiv;
	var onCreateSchedule;
	var onUpdateSchedule;
	var onMouseOverSchedule;
	var onMouseOutSchedule;
	var onMouseClickSchedule;
	var onChangeMonth;
	var allowPassScheduleDate = false;
	var currentDate = null;
	var moving_object = null;
	var moving_schedule = null;
	var moving_idx;
	var mX, mY, devX, devY;
	var startX = 0;
	var startY = 0;

	var months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
	
	function getDaysInMonth(year, month) {
		return [31,((!(year % 4 ) && ( (year % 100 ) || !( year % 400 ) ))?29:28),31,30,31,30,31,31,30,31,30,31][month-1];
	}
	
	function setPath(p) {
		path = p;
	}
	
	function setName(n) {
		name = n;
	}
	
	function setMinYear(i) {
		minYear = i;
	}
	
	function setMinMonth(i) {
		minMonth = i;
	}
	
	function setMaxYear(i) {
		maxYear = i;
	}
	
	function setMaxMonth(i) {
		maxMonth = i;
	}
	
	function setYear(i) {
		year = i;
	}
	
	function getYear() {
		return year;
	}
	
	function setMonth(i) {
		month = i;
	}
	
	function getMonth() {
		return month;
	}
	
	function setWidth(i) {
		width = i;
	}
	
	function setScheduleDiv(i) {
		scheduleDiv = i;
	}
	
	function getResource(rid) {
		for(var i=0; i<resource.length; i++) {
			if (resource[i].resourceID==rid) {
				return resource[i];
			}
		}
		return null;
	}
	
	function setResource(i) {
		resource = i;
	}
	
	function setResourceHeader(i) {
		resourceHeader = i;
	}
	
	function setDetailHeader(i) {
		detailHeader = i;
	}
	
	function setHoliday(i) {
		holiday = i;
	}
	
	function setSchedule(s) {
		removeDiv();
		schedule = s;
	}
	
	function getSchedule() {
		return schedule;
	}
	
	function setScheduleItem(i, s) {
		removeDiv();
		schedule[i] = s;
	}
	
	function getScheduleItem(i) {
		return schedule[i];
	}
	
	function setOnCreateSchedule(e) {
		onCreateSchedule = e;
	}
	
	function setOnUpdateSchedule(e) {
		onUpdateSchedule = e;
	}
	
	function setOnMouseOverSchedule(e) {
		onMouseOverSchedule = e;
	}
	
	function setOnMouseOutSchedule(e) {
		onMouseOutSchedule = e;
	}
	
	function setOnMouseClickSchedule(e) {
		onMouseClickSchedule = e;
	}
	
	function setOnChangeMonth(e) {
		onChangeMonth = e;
	}
	
	function setAllowPastScheduleDate(e) {
		allowPastScheduleDate = e;
	}
	
	function setCurrentDate(y, m, d) {
		currentDate = new Date(y, m, d);
	}
	
	function removeDiv() {
		for(var i=0; i<idList.length; i++) {
			document.body.removeChild(document.getElementById(idList[i]));
		}
		idList = new Array();
	}
	
	function nextMonth() {
		if (month==12) {
			month=1;
			year++;
		} else {
			month++;
		}
		removeDiv();
		drawMonthView();
		drawSchedule();
		if (onChangeMonth!=null) eval(onChangeMonth+"()");
	}
	
	function previousMonth() {
		if (month==1) {
			month=12;
			year--;
		} else {
			month--;
		}
		removeDiv();
		drawMonthView();
		drawSchedule();
		if (onChangeMonth!=null) eval(onChangeMonth+"()");
	}
	
	function drawSchedule() {
		var time = "/"+month.numberFormat("00")+"/"+year;
		var sch;
		if (resource.lenght>0) calculateStartXY(document.getElementById(resource[0].resourceID+"_1"));
		for(var i=0; i<schedule.length; i++) {			
			if(schedule[i]!=null) {		
			if(schedule[i].startDate.indexOf(time)>0 || schedule[i].endDate.indexOf(time)>0) {
				var obj = document.createElement("DIV");
				obj.id = schedule[i].scheduleID+"_DIV_"+i;
				obj.style.position = "absolute";
				obj.style.zIndex = 98;
				obj.style.left = startX;
				obj.style.top = startY;
				obj.style.cursor = "pointer";
				document.body.appendChild(obj);
				sch = schedule[i];	
				obj.onmousedown = function(event){editMoveSchedule(this, event);};
				if (onMouseOverSchedule!=null) {
					var tmp = onMouseOverSchedule+"(document.getElementById('"+obj.id+"'),"+name+".getSchedule()["+i+"], event, "+i+")";
					eval("obj.onmouseover = function(event){return "+tmp+"};");
				}
				if (onMouseOutSchedule!=null) {
					var tmp = onMouseOutSchedule+"(document.getElementById('"+obj.id+"'),"+name+".getSchedule()["+i+"], event, "+i+")";
					eval("obj.onmouseout = function(event){return "+tmp+"};");
				}
				if (onMouseClickSchedule!=null) {
					var tmp = onMouseClickSchedule+"(document.getElementById('"+obj.id+"'),"+name+".getSchedule()["+i+"], event, "+i+")";
					eval("obj.onclick = function(event){return "+tmp+"};");
				}
				idList[idList.length]=obj.id;
				var d = parseInt(schedule[i].startDate.substring(0,2));
				var scheduleCell = document.getElementById("R_"+schedule[i].resourceID+"_"+d);
				if (schedule.time==2) {
					snapToSchedule(obj, scheduleCell, 12);
				} else {
					snapToSchedule(obj, scheduleCell, 0);
				}
				writeScheduleDateList(obj, schedule[i]);
			} 
			} else {
				schedule.remove(i);
				i--;
			}
		}
	}
	
	function drawMonthView() {
		var buf = "";
		var daysInMonth = getDaysInMonth(year, month);
		var css = new Array();
		var col = 1;
		if (detailHeader!="") col=2;
		buf += "<table cellspacing=\"0\" cellpadding=\"0\" border=\"1\" width=\""+width+"\">";
		buf += "<tr><td colspan=\""+(daysInMonth+col)+"\" class=\"scheduleMonth\" align=\"center\"><table class=\"scheduleMonth\"><tr><td width=\"30\" align=\"left\">";
		if (month>minMonth || year>minYear) {
			buf += "<img src=\""+path+"/dcswc/images/Previous.gif\" style=\"cursor: pointer\"";
			buf += " onclick=\""+name+".previousMonth()\">";
		} else {
			buf += "&nbsp";
		}
		buf += "</td><td width=\"120\" align=\"center\">"+months[month-1]+" "+year+"</td><td width=\"30\" align=\"right\">";
		if (month<maxMonth || year<maxYear) {
			buf += "<img src=\""+path+"/dcswc/images/Next.gif\" style=\"cursor: pointer\"";
			buf += " onclick=\""+name+".nextMonth()\">";
		} else {
			buf += "&nbsp";
		}
		buf += "</td></tr></table>";
		buf += "</td></tr>";
		buf += "<tr><td height=\"22\" class=\"scheduleResourceHeader\">"+resourceHeader+"</td>";
		if (detailHeader!="") {
			buf += "<td height=\"22\" class=\"scheduleResourceHeader\">"+detailHeader+"</td>";
		}
		for (var i=1; i<=daysInMonth; i++) {
			var time = i.numberFormat("00")+"/"+month.numberFormat("00")+"/"+year;
			var c = "weekday";
			if (isHoliday(time)) {
				c = "weekend";
			}
			css[i-1]=c;
			buf += "<td width=\"20\" class=\""+c+"\">"+i+"</td>";
		}
		
		for (var i=0; i<resource.length; i++) {
			buf += "</tr><tr>";
			buf += "<td height=\"22\" class=\"scheduleResource\">"+resource[i].resourceName+"</td>";
			if (detailHeader!="") {
				if (resource[i].detail=="") { 
					buf += "<td height=\"22\" class=\"scheduleResource\">&nbsp;</td>";
				} else {
					buf += "<td height=\"22\" class=\"scheduleResource\">"+resource[i].detail+"</td>";
				}
			}
			for (var j=0; j<daysInMonth; j++) {
				buf += "<td class=\""+css[j]+"\" id=\"R_"+resource[i].resourceID+"_"+(j+1)+"\">&nbsp;</td>";
			}
		}
		buf += "</tr></table>";
		scheduleDiv.innerHTML=buf;		
	}
	
	function rollBackSchedule() {
		moving_idx = moving_object.id.substring(moving_object.id.indexOf("_DIV_")+5, moving_object.id.length);
	    moving_object.style.zIndex=98;
		moving_object.style.filter="";
		if (onMouseOverSchedule!=null) {
			var tmp = onMouseOverSchedule+"(document.getElementById('"+moving_object.id+"'),"+name+".getSchedule()["+moving_idx+"], event, "+moving_idx+")";
			eval("moving_object.onmouseover = function(event){return "+tmp+"};");
		}
		if (onMouseOutSchedule!=null) {
			var tmp = onMouseOutSchedule+"(document.getElementById('"+moving_object.id+"'),"+name+".getSchedule()["+moving_idx+"], event, "+moving_idx+")";
			eval("moving_object.onmouseout = function(event){return "+tmp+"};");
		}
		if (onMouseClickSchedule!=null) {
			var tmp = onMouseClickSchedule+"(document.getElementById('"+moving_object.id+"'),"+name+".getSchedule()["+moving_idx+"], event, "+moving_idx+")";
			eval("moving_object.onclick = function(event){return "+tmp+"};");
		}
		writeScheduleDateList(moving_object, moving_schedule);
		moving_object = null;
		moving_schedule = null;
		document.onmouseup = "";
	}
	
	function removeSchedule(div, sch) {
		moving_idx = div.id.substring(div.id.indexOf("_DIV_")+5, div.id.length);
		for(var i=0; i<idList.length; i++) {
			if (idList[i]==div.id) {
				idList.remove(i);
				break;
			}
		}
		schedule[moving_idx]=null;
		document.body.removeChild(div);
	}
	
	function createSchedule() {
		if (resource.length>0) calculateStartXY(document.getElementById("R_"+resource[0].resourceID+"_1"));
		var offsetY = 0;
		if (moving_schedule.time==2)
			offsetY = 12
		var x = Math.round((moving_object.offsetLeft-startX)/22);
		var y = Math.round((moving_object.offsetTop-startY-offsetY)/22);
				
		if (y==-1) y=0;
		var maxdate = getDaysInMonth(year, month);
		if (x==maxdate) x=maxdate-1;
		if (y==resource.length) y=resource.length-1;
		
		if (y<0 || x>maxdate-1 || y>resource.length-1) {
			document.body.removeChild(moving_object);
			document.onmouseup = "";
			document.onmousemove = "";
			moving_object = null;
			moving_schedule = null;		
			return;
		}
		
		var scheduleDate;
		if (x<0) {
			var yy = year;
			var mm = month-2;
			if (mm<0) {
				mm=11;
				yy--;
			}
			var max = getDaysInMonth(yy, mm+1);
			scheduleDate = new Date(yy, mm, max+x+1);
		} else {
			scheduleDate = new Date(year, month-1, x+1);
		}
		if (scheduleOverlap(moving_schedule, resource[y].resourceID, scheduleDate) || (!allowPastScheduleDate && scheduleDate<currentDate) || (resource[y].dropAble==false)) {
			document.body.removeChild(moving_object);
			document.onmouseup = "";
			document.onmousemove = "";
			moving_object = null;
			moving_schedule = null;			
			return;
		}
		
		moving_schedule.startDate = scheduleDate.getDate().numberFormat("00")+"/"+(scheduleDate.getMonth()+1).numberFormat("00")+"/"+scheduleDate.getFullYear();
		moving_schedule.resourceID = resource[y].resourceID;
		moving_schedule.moveAble = true;
		writeScheduleSkipHoliday(moving_object, moving_schedule);
		moving_object.style.zIndex = 98;
		moving_object.style.filter = "";
		moving_object.style.cursor = "pointer";
		if (onMouseOverSchedule!=null) {
			var tmp = onMouseOverSchedule+"(document.getElementById('"+moving_object.id+"'),"+name+".getSchedule()["+schedule.length+"], event, "+ schedule.length +")";
			eval("moving_object.onmouseover = function(event){return "+tmp+"};");
		}
		if (onMouseOutSchedule!=null) {
			var tmp = onMouseOutSchedule+"(document.getElementById('"+moving_object.id+"'),"+name+".getSchedule()["+schedule.length+"], event, "+ schedule.length +")";
			eval("moving_object.onmouseout = function(event){return "+tmp+"};");
		}
		if (onMouseClickSchedule!=null) {
			var tmp = onMouseClickSchedule+"(document.getElementById('"+moving_object.id+"'),"+name+".getSchedule()["+schedule.length+"], event, "+schedule.length+")";
			eval("moving_object.onclick = function(event){return "+tmp+"};");
		}
		moving_object.onmousedown = function(event){editMoveSchedule(this, event);};
		schedule[schedule.length] = moving_schedule;
		idList[idList.length] = moving_object.id;		
		document.onmouseup = "";
		document.onmousemove = "";

		if (onCreateSchedule!=null) eval(onCreateSchedule+"(document.getElementById('"+moving_object.id+"'),moving_schedule)");
		
		moving_object = null;
		moving_schedule = null;		
	}
	
	function updateSchedule() {
		if (resource.length>0) calculateStartXY(document.getElementById("R_"+resource[0].resourceID+"_1"));
		var offsetY = 0;
		if (moving_schedule.time==2)
			offsetY = 12
		var x = Math.round((moving_object.offsetLeft-startX)/22);
		var y = Math.round((moving_object.offsetTop-startY-offsetY)/22);
		var z = Math.round((moving_object.offsetLeft-startX+moving_object.offsetWidth)/22);	
		if (y==-1) y=0;
		var maxdate = getDaysInMonth(year, month);
		if (x==maxdate) x=maxdate-1;
		if (y==resource.length) y=resource.length-1;
		
		if (z<1 || y<0 || x>maxdate-1 || y>resource.length-1) {
			rollBackSchedule();
			document.onmouseup = "";
			document.onmousemove = "";
			moving_object = null;
			moving_schedule = null;		
			return;
		}
		
		var scheduleDate;
		if (x<0) {
			var yy = year;
			var mm = month-2;
			if (mm<0) {
				mm=11;
				yy--;
			}
			var max = getDaysInMonth(yy, mm+1);
			scheduleDate = new Date(yy, mm, max+x+1);
		} else {
			scheduleDate = new Date(year, month-1, x+1);
		}
		if (scheduleOverlap(moving_schedule, resource[y].resourceID, scheduleDate) || (!allowPastScheduleDate && scheduleDate<currentDate) || (resource[y].dropAble==false)) {
			rollBackSchedule();
			document.onmouseup = "";
			document.onmousemove = "";
			moving_object = null;
			moving_schedule = null;		
			return;
		}
		
		moving_schedule.startDate = scheduleDate.getDate().numberFormat("00")+"/"+(scheduleDate.getMonth()+1).numberFormat("00")+"/"+scheduleDate.getFullYear();
		moving_schedule.resourceID = resource[y].resourceID;
		writeScheduleSkipHoliday(moving_object ,moving_schedule);
		moving_object.style.zIndex = 98;
		moving_object.style.filter = "";
		if (onMouseOverSchedule!=null) {
			var tmp = onMouseOverSchedule+"(document.all."+moving_object.id+","+name+".getSchedule()["+moving_idx+"], event, "+moving_idx+")";
			eval("moving_object.onmouseover = function(event){return "+tmp+"};");
		}
		document.onmouseup = "";
		document.onmousemove = "";
		
		if (onUpdateSchedule!=null) eval(onUpdateSchedule+"(document.all."+moving_object.id+",moving_schedule)");
		
		moving_object = null;
		moving_schedule = null;
	}
	
	function editMoveSchedule(div, event) {
		if (!event) event = window.event;
		moving_idx = div.id.substring(div.id.indexOf("_DIV_")+5, div.id.length);
		mX = event.clientX;
		mY = event.clientY;
		devY = mY - div.offsetTop;
	    devX = mX - div.offsetLeft;
	    if (schedule[moving_idx].moveAble) {
		    moving_object = div;
		    moving_schedule = schedule[moving_idx];
	    	moving_object.onmouseover="";
	    	moving_object.style.zIndex=99;
			moving_object.style.filter="Alpha(Opacity=50)";
		    document.onmouseup = updateSchedule;
		    document.onmousemove = moveSchedule;
			writeSchedule(moving_object, moving_schedule);
		}
	}
	
	function createMoveSchedule(sch, obj, event) {
		if (!event) event = window.event;
		mX = event.clientX;
		mY = event.clientY;
		calculateStartXY(obj);
		moving_schedule = sch;
		moving_schedule.scheduleID = "JS"+idCount.numberFormat("0000");
		moving_object = document.createElement("DIV");
		moving_object.id = moving_schedule.scheduleID+"_DIV_"+schedule.length;
		moving_object.style.position = "absolute";
		moving_object.style.filter = "Alpha(Opacity=50)";
		moving_object.style.zIndex = 99;
		moving_object.style.left = startX;
		moving_object.style.top = startY;
		writeSchedule(moving_object, sch);
		idCount++;
		document.body.appendChild(moving_object);
		document.onmouseup = createSchedule;
		document.onmousemove = moveSchedule;
		devY = mY - moving_object.offsetTop;
    	devX = mX - moving_object.offsetLeft;    	
	}
	
	function moveSchedule(event) {
		if (!event) event = window.event;		
		mX = event.clientX;
		mY = event.clientY;
		if (moving_object!=null) {
			moving_object.style.left = mX-devX+"px";
			moving_object.style.top = mY-devY+"px";
	        return false;
		}
		return false;
	}
	
	function writeSchedule(div, sch) {
		var table = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\"><tr>";
		for(var i=1; i<=sch.duration; i++) {		
			var image;
			if (sch.duration==1) {
				//image = path+"/dcswc/images/dot"+sch.time+".gif";
				image = path+"/ScheduleImage?imgfile=dot"+sch.time+".gif";
			} else if (i==1) {
				//image = path+"/dcswc/images/startdot"+sch.time+".gif";
				image = path+"/ScheduleImage?imgfile=startdot"+sch.time+".gif";
			} else if (i==sch.duration) {
				//image = path+"/dcswc/images/enddot"+sch.time+".gif";
				image = path+"/ScheduleImage?imgfile=enddot"+sch.time+".gif";
			} else {
				//image = path+"/dcswc/images/centerdot"+sch.time+".gif";
				image = path+"/ScheduleImage?imgfile=centerdot"+sch.time+".gif";
			}
			table += "<td>";
			//table += "<img src=\""+image+"\" style=\"filter:Chroma(Color = #000000) Mask(color="+sch.color+")\">";
			table += "<img src=\""+image+"&color="+sch.color.substring(1,7)+"\">";
			table += "</td>";
		}
		table += "</tr></table>";
		div.innerHTML = table;
	}
	
	function isHoliday(strDate) {
		for(var i=0; i<holiday.length; i++) {			
			if (holiday[i]==strDate) {
				return true;
			}
		}
		return false
	}
	
	function writeScheduleSkipHoliday(div, sch) {
		var table = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\"><tr>";
		var d = sch.startDate.substring(0, 2);
		var m = sch.startDate.substring(3, 5);
		var y = sch.startDate.substring(6, 10);
		var scheduleDate = new Date(y, m-1, d);
		var z = 0;
		var startDate = 1;
		for(var i=1; i<=sch.duration; i++) {
			var strDate = scheduleDate.getDate().numberFormat("00")+"/"+(scheduleDate.getMonth()+1).numberFormat("00")+"/"+scheduleDate.getFullYear();
			if (isHoliday(strDate)) {
				i--;
				if (i>0 && scheduleDate.getMonth()+1==month) {
					table += "<td width=\"22\"><img src=\""+path+"/dcswc/images/1px.gif"+"\" width=\"22\" height=\"20\"></td>";
					if (z==0) startDate=scheduleDate.getDate();
					z++;
				}
			} else {
				if (scheduleDate.getMonth()+1==month) {
					var image;
					if (sch.duration==1) {
						//image = path+"/dcswc/images/dot"+sch.time+".gif";
						image = path+"/ScheduleImage?imgfile=dot"+sch.time+".gif";
					} else if (i==1) {
						//image = path+"/dcswc/images/startdot"+sch.time+".gif";
						image = path+"/ScheduleImage?imgfile=startdot"+sch.time+".gif";
					} else if (i==sch.duration) {
						//image = path+"/dcswc/images/enddot"+sch.time+".gif";
						image = path+"/ScheduleImage?imgfile=enddot"+sch.time+".gif";
					} else {
						//image = path+"/dcswc/images/centerdot"+sch.time+".gif";
						image = path+"/ScheduleImage?imgfile=centerdot"+sch.time+".gif";
					}
					table += "<td>";
					//table += "<img src=\""+image+"\" style=\"filter:Chroma(Color = #000000) Mask(color="+sch.color+")\">";
					table += "<img src=\""+image+"&color="+sch.color.substring(1,7)+"\">";
					table += "</td>";
					if (z==0) startDate=scheduleDate.getDate();
					z++;
				}
				sch.dateList[i-1]=scheduleDate.getDate().numberFormat("00")+"/"+(scheduleDate.getMonth()+1).numberFormat("00")+"/"+scheduleDate.getFullYear();
			}
			scheduleDate.setTime(scheduleDate.getTime()+86400000);
		}
		sch.startDate=sch.dateList[0];
		sch.endDate=sch.dateList[sch.dateList.length-1];
		table += "</tr></table>";
		var scheduleCell = document.getElementById("R_"+sch.resourceID+"_"+startDate);
		if (sch.time==2) {
			snapToSchedule(div, scheduleCell, 12);
		} else {
			snapToSchedule(div, scheduleCell, 0);
		}
		div.innerHTML = table;
	}
	
	function writeScheduleDateList(div, sch) {
		var table = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" padding=\"0\"><tr>";
		var startDate = 0;	
		var lasttime;
		var i;
		for(i=0; i<sch.dateList.length; i++) {
			var d = sch.dateList[i].substring(0, 2);
			var m = sch.dateList[i].substring(3, 5);
			var y = sch.dateList[i].substring(6, 10);
			var tmpDate = new Date(y, m-1, d);
			if (tmpDate.getMonth()+1==month && tmpDate.getFullYear()==year) {
				startDate = tmpDate.getDate();
				lasttime = tmpDate.getTime()-86400000;
				break;
			}
		}
		for(var j=i; j<sch.dateList.length; j++) {
			var d = sch.dateList[j].substring(0, 2);
			var m = sch.dateList[j].substring(3, 5);
			var y = sch.dateList[j].substring(6, 10);
			var tmpDate = new Date(y, m-1, d);
			if (tmpDate.getMonth()+1!=month || tmpDate.getFullYear()!=year) {
				break;
			}
			if (tmpDate.getTime() == (lasttime+86400000)) {
				var image;
				if (sch.duration==1) {
					//image = path+"/dcswc/images/dot"+sch.time+".gif";
					image = path+"/ScheduleImage?imgfile=dot"+sch.time+".gif";
				} else if (j==0) {
					//image = path+"/dcswc/images/startdot"+sch.time+".gif";
					image = path+"/ScheduleImage?imgfile=startdot"+sch.time+".gif";
				} else if (j==sch.dateList.length-1) {
					//image = path+"/dcswc/images/enddot"+sch.time+".gif";
					image = path+"/ScheduleImage?imgfile=enddot"+sch.time+".gif";
				} else {
					//image = path+"/dcswc/images/centerdot"+sch.time+".gif";
					image = path+"/ScheduleImage?imgfile=centerdot"+sch.time+".gif";
				}
				table += "<td>";
				//table += "<img src=\""+image+"\" style=\"filter:Chroma(Color = #000000) Mask(color="+sch.color+")\">";
				table += "<img src=\""+image+"&color="+sch.color.substring(1,7)+"\">";
				table += "</td>";
			} else {
				j--;
				table += "<td width=\"22\"><img src=\""+path+"/dcswc/images/1px.gif"+"\" width=\"22\" height=\"20\"></td>";
			}
			lasttime+=86400000;
		}
		
		table += "</tr></table>";
		//var scheduleCell = eval("document.all.R_"+sch.resourceID+"_"+startDate);
		var scheduleCell = document.getElementById("R_"+sch.resourceID+"_"+startDate);
		if (schedule.time==2) {
			snapToSchedule(div, scheduleCell, 12);
		} else {
			snapToSchedule(div, scheduleCell, 0);
		}
		div.innerHTML = table;
	}
	
	function scheduleOverlap(sch, resourceID, scheduleDate) {
		var tmpDate = new Date();
		for(var i=0; i<schedule.length; i++) {
			if(schedule[i]!=null) {			
			if(schedule[i].resourceID==resourceID && schedule[i].scheduleID!=sch.scheduleID) {
				var d=0;
				if (sch.time+schedule[i].time>3 || sch.time==schedule[i].time) {
					for(var k=0; k<sch.duration; k++) {
						tmpDate.setTime(scheduleDate.getTime()+(d*86400000));
						var datestr = tmpDate.getDate().numberFormat("00")+"/"+(tmpDate.getMonth()+1).numberFormat("00")+"/"+tmpDate.getFullYear();
						if (isHoliday(datestr)) {
							k--;
						}
						for(var j=0; j<schedule[i].dateList.length; j++) {
							if (schedule[i].dateList[j]==datestr) {
								return true;
							}
						}
						d++;
					}
				}
			}
			}
		}
		return false;
	}
	
	function snapToSchedule(obj1, obj2, offsetY) {
		var tmp = obj2
		var x = 0;
		var y = 0;
		while (tmp != null) {
	      x+= tmp.offsetLeft;
	      y+= tmp.offsetTop;
	      tmp = tmp.offsetParent;
	    }
		obj1.style.left = x;
	    obj1.style.top = y+offsetY;
	}
	
	function calculateStartXY(obj) {
		var tmp = obj
		var x = 0;
		var y = 0;
		while (tmp != null) {
	      x+= tmp.offsetLeft;
	      y+= tmp.offsetTop;
	      tmp = tmp.offsetParent;
	    }
	    startX = x;
	    startY = y;
	}
	
}

function mouseLeaves(element, evt) {
	if (!evt) evt=window.event;
	if (typeof evt.toElement != 'undefined' && typeof element.contains !='undefined') {
		return !element.contains(evt.toElement);
	} else if (typeof evt.relatedTarget != 'undefined' && evt.relatedTarget) {
		return !contains(element, evt.relatedTarget);
	}
}



