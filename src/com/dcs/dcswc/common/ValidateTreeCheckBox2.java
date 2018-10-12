package com.dcs.dcswc.common;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


public class ValidateTreeCheckBox2 extends AValidateTag implements IValidateTag {
	private static final long serialVersionUID = -8827964953564862202L;
	
	private Collection mlstDataSource = null;
	
	public Collection getDataSource() {
		return mlstDataSource;
	}

	public void setDataSource(Collection mlstDataSource) {
		this.mlstDataSource = mlstDataSource;
	}
	
	public int doStartTag() throws JspException
    {
		ValidateForm objTmp = getValidateForm(this);
        if (objTmp==null)
            throw new JspException("Error: ValidateTreeCheckBox2 tag needs to be a child of ValidateForm!");
        
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
			objBuf.append("<SCRIPT language=\"JavaScript\">\n");

			JsonConfig jconfig = new JsonConfig();
			String[] exclude = {"parent"};
			jconfig.setExcludes(exclude);
			String value = getPropertyValue();
			JSONArray json = JSONArray.fromObject(this.mlstDataSource, jconfig);
			objBuf.append(mstrName+"_tree = "+json+";\n");
			objBuf.append(mstrName+"_value = '"+value+"';\n");
			objBuf.append(mstrName+"_activediv = '';\n");
			objBuf.append("writeTreeCheckBox2('"+((HttpServletRequest)pageContext.getRequest()).getContextPath()+"','"+mstrName+"', document.all."+mstrName+"_div,[],[],[]);\n");
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
            throw new JspException("Error: ValidateTreeCheckBox2 is not a child of ValidateForm", CCEx);
        }
        finally
        {
            if (objTmp != null) objTmp = null;
        }
        return EVAL_PAGE;
    }
	
	private ValidateTreeCheckBox2 getCopy() {
		ValidateTreeCheckBox2 objItm = new ValidateTreeCheckBox2();
    	super.copyAttributesTo(objItm);
    	objItm.mlstDataSource = this.mlstDataSource;
    	return objItm;
	}
	
}
