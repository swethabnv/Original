package com.dcs.dcswc.common;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;

public class ValidateTime extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = 6781386320771239193L;
	private int minRange = 5;
		
	public int getMinRange() {
		return minRange;
	}

	public void setMinRange(int minRange) {
		this.minRange = minRange;
	}

	public int doStartTag() throws JspException {
		 DecimalFormat format = new DecimalFormat("00");
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateTime tag needs to be a child of ValidateForm!");

        String value = getPropertyValue();
        StringBuffer objBuf = null;
        String hval = "00";
        String mval = "00";
		try {
			if (value!=null && value.length()>0) {
				int x = value.indexOf(":");
				hval = value.substring(0, x);
				mval = value.substring(x+1, value.length());
			}
			objBuf = new StringBuffer();
			objBuf.append("<span>");
			objBuf.append("<input type=\"hidden\"");
			if (this.mstrName!=null) {
				objBuf.append(" name=\""+this.mstrName+"\"");
				objBuf.append(" id=\""+this.mstrName+"\"");
			}
			objBuf.append(" value=\""+hval+":"+mval+"\">");	
			
			objBuf.append("<select");
			if (this.mstrName!=null)
				objBuf.append(" id=\""+mstrName+"_h\"");
			if (this.mstrCssClass!=null)
				objBuf.append(" class=\""+this.mstrCssClass+"\"");
			objBuf.append(" onchange=\"document.getElementById('"+this.mstrName+"').value");
			objBuf.append("=document.getElementById('"+mstrName+"_h').value+':'+document.getElementById('"+mstrName+"_m').value\"");
			objBuf.append(">");
			for(int i=0; i<=23; i++) {
				String h = format.format(i);
				objBuf.append("<option value=\""+h+"\"");
				if (hval.equals(h) || hval.equals(i+""))
					objBuf.append(" selected=\"selected\"");
				objBuf.append(">");
				objBuf.append(h);
				objBuf.append("</option>");
			}
			objBuf.append("</select>");
			
			objBuf.append("<select");
			if (this.mstrName!=null)
				objBuf.append(" id=\""+mstrName+"_m\"");
			if (this.mstrCssClass!=null)
				objBuf.append(" class=\""+this.mstrCssClass+"\"");
			objBuf.append(" onchange=\"document.getElementById('"+this.mstrName+"').value");
			objBuf.append("=document.getElementById('"+mstrName+"_h').value+':'+document.getElementById('"+mstrName+"_m').value\"");
			objBuf.append(">");
			for(int i=0; i<59; i+=this.minRange) {
				String m = format.format(i);
				objBuf.append("<option value=\""+m+"\"");
				if (mval.equals(m) || mval.equals(i+""))
					objBuf.append(" selected=\"selected\"");
				objBuf.append(">");
				objBuf.append(m);
				objBuf.append("</option>");
			}
			objBuf.append("</select>");
			objBuf.append("\n<SCRIPT language=\"JavaScript\">\n");
			objBuf.append("document.getElementById('"+mstrName+"_h').value='"+hval+"';\n");
			objBuf.append("document.getElementById('"+mstrName+"_m').value='"+mval+"';\n");
			objBuf.append("</SCRIPT>\n");
			objBuf.append("</span>");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }
	
	public int doEndTag() throws JspException {
        ValidateForm objTmp = null;

        try
        {
        	objTmp = getValidateForm(this);
            objTmp.addChild(getCopy());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: ValidateTime is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateTime getCopy() {
		ValidateTime objItm = new ValidateTime();
    	super.copyAttributesTo(objItm);
    	objItm.setPageContext(this.pageContext);
    	return objItm;
	}	
}
