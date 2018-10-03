function calculateTable(sheetName) {	
	var sheet = eval(sheetName);
	for (var i=0; i<sheet.length; i++) {
		for (var j=0; j<sheet[i].length; j++) {
			if (sheet[i][j].formula!=null && sheet[i][j].formula.length>0) {
				calculateCell(sheet, i, j);
				//if (!sheet[i][j].editAble && validateExp("[-+]?[0-9]*\.?[0-9]+",sheet[i][j].value.toString())) {
				if (validateExp("[-+]?[0-9]*\.?[0-9]+",sheet[i][j].value.toString())) {
					if (sheet[i][j].editAble) {
						var cell = document.getElementById(sheetName+"_"+i+"_"+j+"_txt");
						if (cell!=null) {
							if (sheet[i][j].numberFormat.length>0) {
								var num = Number(sheet[i][j].value);
								cell.value = num.numberFormat(sheet[i][j].numberFormat);
							} else {
								cell.value = sheet[i][j].value;
							}
						}
					} else {
						var cell = document.getElementById(sheetName+"_"+i+"_"+j);
						if (cell!=null) {
							if (sheet[i][j].numberFormat.length>0) {
								var num = Number(sheet[i][j].value);
								cell.innerHTML = num.numberFormat(sheet[i][j].numberFormat);
							} else {
								cell.innerHTML = sheet[i][j].value;
							}
						}
					}
				}
			}
		}
	}
}

function formatTable(sheetName) {
	var sheet = eval(sheetName);
	for (var i=0; i<sheet.length; i++) {
		for (var j=0; j<sheet[i].length; j++) {
			if (validateExp("^-{0,1}[0-9]*\\.{0,1}[0-9]+$",sheet[i][j].loadValue.toString())) {				
				if (sheet[i][j].editAble) {
					var cell = document.getElementById(sheetName+"_"+i+"_"+j+"_txt");
					if (cell!=null) {
						if (sheet[i][j].numberFormat.length>0) {
							var num = Number(sheet[i][j].value);
							cell.value = num.numberFormat(sheet[i][j].numberFormat);
						}
					}
				} else {
					var num = Number(sheet[i][j].value);
					var cell = document.getElementById(sheetName+"_"+i+"_"+j);
					if (sheet[i][j].numberFormat.length>0) {
						cell.innerHTML = num.numberFormat(sheet[i][j].numberFormat);
					}
				}
			}
		}
	}
}

function clearValueTable(sheetName) {	
	var sheet = eval(sheetName);
	for (var i=0; i<sheet.length; i++) {		
		for (var j=0; j<sheet[i].length; j++) {
			if (sheet[i][j].formula!=null && sheet[i][j].formula.length>0) {
				sheet[i][j].value="";
			}
		}
	}
}

function calculateCell(sheet, row, col) {
	var formula = sheet[row][col].formula;

	if (sheet[row][col].value!=null && sheet[row][col].value.length!=0) {
		return sheet[row][col].value;
	}
	if (sheet[row][col].formula==null || sheet[row][col].formula.length==0) {
		return sheet[row][col].loadValue;
	}
	while(formula.indexOf("%R")>=0) {
		var i = formula.indexOf("%R");
		var a = formula.substring(0, i);
		var b = formula.substring(i+2, formula.length);
		formula = a+row+b;
	}
	while(formula.indexOf("%C")>=0) {
		var i = formula.indexOf("%C");
		var a = formula.substring(0, i);
		var b = formula.substring(i+2, formula.length);
		formula = a+col+b;
	}

	while(formula.indexOf("{")>=0) {
		var i = formula.indexOf("{")
		var j = formula.indexOf("}")
		var a = formula.substring(0, i);
		var b = formula.substring(j+1, formula.length);
		var cell = formula.substring(i+1, j);

		var ri = cell.indexOf("[")
		var rj = cell.indexOf("]")
		var checksheet = eval(cell.substring(0, ri));
		var checkrow = eval(cell.substring(ri+1, rj));
		var checkcol = eval(cell.substring(rj+2, cell.length-1));
		
		if (checkrow<0 || checkrow>checksheet.length-1) {
			sheet[row][col].value = sheet[row][col].loadValue;
			return sheet[row][col].loadValue;
		}
		
		if (checkcol<0 || checkcol>checksheet[0].length-1) {
			sheet[row][col].value = sheet[row][col].loadValue;
			return sheet[row][col].loadValue;
		}

		var value = calculateCell(checksheet, checkrow, checkcol);
		formula = a+value+b;		
	}
	
	if (formula.indexOf("--")>=0) {
		var i = formula.indexOf("--")
		var a = formula.substring(0, i);
		var b = formula.substring(i+2, formula.length);
		formula = a+"+"+b;
	}
	var result = eval(formula);
	if (isNaN(result)) result=0;
	sheet[row][col].value = result;
	return result;
}

function reCalculate(cell, tableList) {
	var cellName = cell.substring(0, cell.indexOf("["));
	var i;
	for (i=0; i<tableList.length; i++) {
		if (cellName==tableList[i]) break;
	}
	for (var x=i; x<tableList.length; x++) {
		var sheetName = tableList[x];
		reCalculateSheet(cell, tableList, sheetName);
	}
}

function reCalculateSheet(cell, tableList, sheetName) {
	var cellName = cell.substring(0, cell.indexOf("["));
	var sheet = eval(sheetName);
	for (var i=0; i<sheet.length; i++) {
		for (var j=0; j<sheet[i].length; j++) {
			if (sheet[i][j].formula!=null && sheet[i][j].formula.length>0) {
				if (sheet[i][j].formula.indexOf(cellName)>=0) {
					var decodeFormula = sheet[i][j].formula.replace(/%R/g,i).replace(/%C/g,j);
					decodeFormula = decodeArray(decodeFormula);
					if (decodeFormula.indexOf(cell)>0) {
						sheet[i][j].value="";
						calculateCell(sheet, i, j);
						if (!sheet[i][j].editAble && validateExp("[-+]?[0-9]*\.?[0-9]+",sheet[i][j].value.toString())) {
							var cell2 = document.getElementById(sheetName+"_"+i+"_"+j);
							if (cell2!=null) {
								if (sheet[i][j].numberFormat.length>0) {
									var num = Number(sheet[i][j].value);
									cell2.innerHTML = num.numberFormat(sheet[i][j].numberFormat);
								} else {
									cell2.innerHTML = sheet[i][j].value;
								}
							}
						}
						reCalculate(sheetName+"["+i+"]["+j+"]", tableList);
					}
				}
			}
		}
	}
}

function decodeArray(formula) {
	var ri = formula.indexOf("[");
	var rj = formula.indexOf("]");
	if (ri<0) {
		return formula;
	}	
	var v = formula.substring(0, ri);
	var a = eval(formula.substring(ri+1, rj));
	return v+"["+a+"]"+decodeArray(formula.substring(rj+1, formula.length));
}

function notUnderZero(v) {
	if (v<0) 
		return 0
	else
		return v 
}