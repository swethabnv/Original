package com.wsnweb.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Asset;
import com.wsndata.data.User;
import com.wsndata.dbaccess.AssetHome;
import com.wsnweb.form.AssetForm;
import com.wsnweb.util.Utility;

public class AssetAction extends Action {
	private static final Logger log = Logger.getLogger(AssetAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		AssetForm eform = (AssetForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ( "Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd())){
				 return edit(mapping, form, request, response);
			 } else if( "GetAsset".equals(eform.getCmd())){
				 return getAsset(mapping, form, request, response);
		     } else{
			 	 return mapping.findForward("asset"); 
			 }
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		AssetForm eform = (AssetForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		AssetHome home = new AssetHome();
		String msg = Utility.get("SAVE_FAIL");//msg = "การบันทึกล้มเหลว";
		try{			
			sf.getCurrentSession().beginTransaction();			
			Asset btObj = null;
			long assetId = eform.getAssetId();
			if(assetId>0)
				btObj = home.findByAssetId(assetId);
			else
				btObj = home.findByAssetName(eform.getAssetName());
			if(btObj !=null && !(assetId>0 && btObj.getAssetName().equals(eform.getAssetName()))){
				msg = Utility.get("SAVE_DUPLICATE");
			}else{
				if(btObj !=null)
				{
					btObj.setIsTotal("N");
					btObj.setAssetName(eform.getAssetName());
					btObj.setLastUpdateBy(userLogin.getUserName());
					btObj.setLastUpdateDate(new Date());
				}else{
					// create New Asset
					btObj = new Asset();
					btObj.setIsTotal("N");
					btObj.setAssetName(eform.getAssetName());
					btObj.setLastUpdateBy(userLogin.getUserName());
					btObj.setLastUpdateDate(new Date());
				}
				home.saveOrUpdate(btObj);
				msg = Utility.get("SAVE_SUCCESS");//eform.setMsg("บันทึกสำเร็จ");	
			}
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(msg);
		}catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("asset");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		AssetForm eform = (AssetForm)form;
		//HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		try{			
			sf.getCurrentSession().beginTransaction();
			eform.setAssetId(eform.getAssetId());
			eform.setAssetName(eform.getAssetName());
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("asset");
	}
	
	//Ajax Method
	public ActionForward getAsset(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
			SessionFactory sf = HibernateHome.getSessionFactory();
			AssetForm eform = (AssetForm)form;
			Asset asset = null, assetObj = null;
			long assetId = 0;
			try{

				sf.getCurrentSession().beginTransaction();
				AssetHome home = new AssetHome();
				assetId = eform.getAssetId();
				if(assetId != 0)
					assetObj = home.findByAssetId(assetId);
				if(assetId == 0 || (assetObj!=null && !assetObj.getAssetName().equals(eform.getAssetName())))
					asset = home.findByAssetName(eform.getAssetName());
			
				response.setCharacterEncoding("UTF-8");
			    PrintWriter wt = response.getWriter();
			    JsonConfig jsonConfig = new JsonConfig();   
			    jsonConfig.setExcludes(new String[]{ "lastUpdateDate", "lastUpdateBy", "isTotal", "assetDetail", "checkBox", "linkImageEdit"});
			    String result = JSONArray.fromObject(asset, jsonConfig).toString();
			    result = result.trim().replaceAll("&nbsp;", " ");
			    wt.print(result);
			    wt.flush();
			    wt.close();
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);
			}finally{
				sf.getCurrentSession().close();
			}
			return null;
	}
	
}
