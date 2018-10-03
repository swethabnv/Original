// EnableDisableImage.js

// newFunction
function EnableDisableImage(obj, target_obj, table_obj, table_obj_disable) {
	//this.is_enable = true;
	this.swapEnableDisable = swapEnableDisable;
	this.image_tag = obj;
	this.target = target_obj;
	this.tableObj = table_obj;
	this.tableObjDisable = table_obj_disable;
	
	return this;
} //end EnableDisableImage


function swapEnableDisable() {

// edit by Ngek 10/06/2013
document.getElementById(this.target).style.display =  "block";
document.getElementById(this.tableObj).style.display =  "block";
document.getElementById(this.tableObjDisable).style.display =  "none";
/// end edit 

////comment by Ngek  ***************************************
	//this.target.style.display =  "inline"; // this.is_enable?"inline":"none";	
	//this.tableObj.style.display =  "inline";//this.is_enable?"inline":"none";	
	//this.tableObjDisable.style.display = "none"; //!this.is_enable?"inline":"none";	
/// end comment ***********************************************
	
//	this.is_enable = !this.is_enable;
} //end swapEnableDisable

