package com.wsnweb.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

import com.dcs.hibernate.HibernateHome;
import com.dcs.util.DCSCompare;
import com.dcs.util.GridUtil;
import com.wsndata.data.Asset;
import com.wsndata.data.User;
import com.wsndata.dbaccess.AssetDetailHome;
import com.wsndata.dbaccess.AssetHome;
import com.wsnweb.form.AssetListForm;

public class AssetListAction extends Action {
	private static final Logger log = Logger.getLogger(AssetListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 AssetListForm eform = (AssetListForm)form;
		 HttpSession session = request.getSession();
		 User userLogin = (User)session.getAttribute("userLogin");
		 if(userLogin==null){			
			 return mapping.findForward("unauthorize");
		 }else{
			 if("Delete".equals(eform.getCmd())){
				 return delete(mapping, eform, request, response);
			 } else if("DirtyList".equals(eform.getCmd())){
				 return dirtyList(mapping, form, request, response);
			 } else{
				 return search(mapping, eform, request, response);
			 }
		 }
	}

	private ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		AssetListForm eform = (AssetListForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List assetList = new ArrayList();
		String assetName= "";
		try{
			AssetHome home = new AssetHome();
			sf.getCurrentSession().beginTransaction();
			assetName = eform.getAssetName()==null?"":eform.getAssetName();
			if("".equals(assetName))
				assetList=home.findAll();  			    
			else
				assetList=home.searchByAssetName(assetName); 
			
			Collections.sort(assetList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
			session.setAttribute("assetList", assetList);
			session.setAttribute("countAsset", assetList!=null?assetList.size():0);
		} catch(Exception e){
			 sf.getCurrentSession().getTransaction().rollback(); 
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("assetList");
	}

	private ActionForward dirtyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AssetListForm eform = (AssetListForm)form;
        HttpSession session = request.getSession();
        List assetList = (List) session.getAttribute("assetList");
        GridUtil.applyCheckValue(assetList, eform.getDelAsset(), "assetId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
        Collections.sort(assetList, new DCSCompare(eform.getSortColumn(),eform.isSortAscending()));
        return mapping.findForward("assetList");
	}

	private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		AssetListForm eform = (AssetListForm)form;
		HttpSession session = request.getSession();
		List assetList = (List) session.getAttribute("assetList");
		GridUtil.applyCheckValue(assetList, eform.getDelAsset(), "assetId", eform.getDisplayRow()*(eform.getLastPage()-1), eform.getDisplayRow());
		
		try{
			sf.getCurrentSession().beginTransaction();	
			AssetHome home = new AssetHome();
			AssetDetailHome assetDetailHome = new AssetDetailHome();
			
			for (int i=0; i<assetList.size(); i++) {
				Asset asset = (Asset)assetList.get(i);
				if (asset.getCheckBox()==true){
					List assetDetailList = assetDetailHome.findByAssetID(asset.getAssetId());
					if(assetDetailList != null && assetDetailList.size()>0){
				   		eform.setErrMessage(asset.getAssetName()+" มีการใช้งานอยู่ในขณะนี้");
						break;
					}else{
						 home.delete(asset);
					}
				}		
			}	
			sf.getCurrentSession().getTransaction().commit();
			log.info("ลบข้อมูลทรัพย์สินไปทั้งสิ้น " + eform.getDelAsset().length + " records");
		}catch(Exception e){
			sf.getCurrentSession().getTransaction().rollback();
	    	e.printStackTrace();
	    	log.error(e,e);
		}finally{
	    	sf.getCurrentSession().close();
	    }        
		return search(mapping, form, request, response);
	}
}
