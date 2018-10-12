package com.dcs.dcswc.common;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


public class ValidateTreeCheckBox extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -7460975968362529915L;
	
	private Collection mlstDataSource = null;
	private boolean mbolFirstLevelCheckBox = false;
	
	public Collection getDataSource() {
		return mlstDataSource;
	}

	public void setDataSource(Collection mlstDataSource) {
		this.mlstDataSource = mlstDataSource;
	}
	
	public boolean getFirstLevelCheckBox() {
		return mbolFirstLevelCheckBox;
	}
	
	public boolean isFirstLevelCheckBox() {
		return mbolFirstLevelCheckBox;
	}

	public void setFirstLevelCheckBox(boolean mbolFirstLevelCheckBox) {
		this.mbolFirstLevelCheckBox = mbolFirstLevelCheckBox;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateTreeCheckBox tag needs to be a child of ValidateForm!");
        
        StringBuffer objBuf = null;
		try {
			objBuf = new StringBuffer();
			objBuf.append("<div");

			if (mstrCssClass!=null)
				objBuf.append(" class=\""+mstrCssClass+"\"");
			if (mstrName!=null)
				objBuf.append(" id=\""+mstrName+"_div\"");
			
			objBuf.append(">");
			
			objBuf.append("</div>\n");
			objBuf.append("<div id=\""+mstrName+"_hid\" style=\"display:none\"></div>");
			objBuf.append("<SCRIPT language=\"JavaScript\">\n");

			JsonConfig jconfig = new JsonConfig();
			String[] exclude = {"parent"};
			jconfig.setExcludes(exclude);
			String[] value = getPropertyValues();
			JSONArray json = JSONArray.fromObject(this.mlstDataSource, jconfig);
			objBuf.append(mstrName+"_tree = "+json+";\n");
			objBuf.append(mstrName+"_firstLevelCheck = "+mbolFirstLevelCheckBox+";\n");
			objBuf.append(mstrName+"_value = "+JSONArray.fromObject(value)+";\n");
			objBuf.append("writeTreeRadio('"+((HttpServletRequest)pageContext.getRequest()).getContextPath()+"','"+mstrName+"', document.all."+mstrName+"_div,[],[],[],false);\n");
			objBuf.append("writeHiddenCheckbox('"+mstrName+"',document.all."+mstrName+"_hid,"+mstrName+"_value);\n");
			objBuf.append("</SCRIPT>\n");
			pageContext.getOut().println(objBuf);
		} catch (IOException IOEx) {
            IOEx.printStackTrace();
            throw new JspException("Error: Unable to write contents!", IOEx);
        }
        return SKIP_BODY;
    }

	public int doEndTag() throws JspException
    {
        ValidateForm objTmp = null;

        try
        {
        	objTmp = getValidateForm(this);
            objTmp.addChild(getCopy());
        }
        catch (ClassCastException CCEx)
        {
            throw new JspException("Error: ValidateTreeCheckBox is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateTreeCheckBox getCopy() {
		ValidateTreeCheckBox objItm = new ValidateTreeCheckBox();
    	super.copyAttributesTo(objItm);
    	objItm.mlstDataSource = this.mlstDataSource;
    	objItm.mbolFirstLevelCheckBox = this.mbolFirstLevelCheckBox;
    	return objItm;
	}
	
}
