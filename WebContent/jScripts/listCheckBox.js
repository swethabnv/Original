$(function() {
	$.widget( "custom.listCheckBox", {
		options: {
			itemPerLine: 1,
			displayMember: "",
			valueMember: "",
			checkBoxClass: undefined,
			labelClass: undefined,
			source: [],
			value: []
		},
		
		_create: function() {		
			this._refresh();
		},
		
		value: function( value ) {
			if ( value == undefined ) {
				var elementId = this.element.attr('id');
				var value = [];
				for(var i=0; i<this.options.source.length; i++) {
					var id = eval("this.options.source[i]."+this.options.valueMember);
					if($("#"+elementId+"_"+id).prop('checked')) {
						value.push(id);
					}
				}
				this.options.value = value;
				return value;
			} else {
				this.options.value = value;
				this._check();
			}
		},
		
		_refresh: function() {
			var buf = "";
			var elementId = this.element.attr('id');
			for(var i=0; i<this.options.source.length; i++) {
				var text = eval("this.options.source[i]."+this.options.displayMember);
				var id = eval("this.options.source[i]."+this.options.valueMember);
				buf+="<input type='checkbox' id='"+elementId+"_"+id+"'";
				if (this.options.checkBoxClass!=undefined) buf+=" class='"+this.options.checkBoxClass+"'";
				buf+="><span";				
				if (this.options.labelClass!=undefined) buf+=" class='"+this.options.labelClass+"'";
				buf+=">"+text+"</span>";
				
				if ((i+1)%this.options.itemPerLine==0) 
				//if (this.options.newLine) 
					buf+="<br>"; 
				else 
					buf+="&nbsp;&nbsp;";
			}
			this.element.html(buf);
			this._check();
		},
		
		_check: function() {
			var elementId = this.element.attr('id');
			for(var i=0; i<this.options.source.length; i++) {
				var id = eval("this.options.source[i]."+this.options.valueMember);
				for(var j=0; j<this.options.value.length; j++) {
					if (id==this.options.value[j]) {
						$("#"+elementId+"_"+id).prop('checked', true);
						break;
					}
				}
			}
		}
	});
});
