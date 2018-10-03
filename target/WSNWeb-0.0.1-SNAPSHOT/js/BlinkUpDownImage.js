// BlinkUpDownImage.js

// newFunction
function BlinkUpDownImage(obj, target_obj) {
	this.is_down = false;
	this.blink = blink;
	this.unblink = unblink;
	this.swapUpDown = swapUpDown;
	this.image_tag = obj;
	this.target = target_obj;
	
	return this;
} //end BlinkUpDownImage

function blink() {
	if (this.is_down)
		this.image_tag.src = "./images/down.png";
	else
		this.image_tag.src = "./images/up.png";
} //end blink

function unblink() {
	if (this.is_down)
		this.image_tag.src = "./images/up.png";
	else
		this.image_tag.src = "./images/down.png";
} //end unblink

function swapUpDown() {
	this.target.style.display = this.is_down?"inline":"none";	
	this.is_down = !this.is_down;
	this.unblink();
} //end swapUpDown

