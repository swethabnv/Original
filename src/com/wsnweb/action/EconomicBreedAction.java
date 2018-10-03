package com.wsnweb.action;

import java.util.ArrayList;
import java.util.Date;
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
import com.wsndata.data.BreedType;
import com.wsndata.data.EconomicBreed;
import com.wsndata.data.Province;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.EconomicBreedHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsnweb.form.EconomicBreedForm;
import com.wsnweb.util.Utility;

public class EconomicBreedAction extends Action {
	private static final Logger log = Logger
			.getLogger(EconomicBreedAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EconomicBreedForm eform = (EconomicBreedForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		 	if (userLogin == null) {
			 return mapping.findForward("unauthorize");
		 	} else {
		 		if ("Save".equals(eform.getCmd())) {
		 			return save(mapping, form, request, response);
		 		} else if ("Edit".equals(eform.getCmd())) {
		 			return edit(mapping, form, request, response);
		 		} else {
		 			return load(mapping, form, request, response);
		 		}
		   }
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EconomicBreedForm eform = (EconomicBreedForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		String msg = Utility.get("SAVE_FAIL");//String msg = "การบันทึกล้มเหลว";
		try {
			sf.getCurrentSession().beginTransaction();
			EconomicBreedHome home = new EconomicBreedHome();

			String[] ProReg = eform.getProvince().split("\\|");
			
			EconomicBreed ebObj = home.findByBreedTypeId(eform.getBreedTypeId(), 
					Long.parseLong(eform.getProvince().split("\\|")[0]));
			
			if(eform.getTmpEdit().equals("")) { //เพิ่ม
				if(ebObj!=null) {
					eform.setMsg(Utility.get("SAVE_DUPLICATE"));//msg = "มีข้อมูลนี้อยู่แล้ว";
					sf.getCurrentSession().getTransaction().rollback();
					return mapping.findForward("economicBreed");
				}
				// create New EconomicBreed
				ebObj = new EconomicBreed();
				ebObj.setBreedTypeId(eform.getBreedTypeId());
				ebObj.setRegionNo(Long.parseLong(eform.getProvince().split("\\|")[1]));
				ebObj.setProvinceNo(Long.parseLong(eform.getProvince().split("\\|")[0]));
				ebObj.setLastUpdateBy(userLogin.getUserName());
				ebObj.setLastUpdateDate(new Date());
			} else { //แก้ไข
				String[] tmpData = eform.getTmpEdit().split("\\|");
				if(eform.getBreedTypeId()!=Long.parseLong(tmpData[0]) || Long.parseLong(tmpData[1])!=Long.parseLong(ProReg[0])) {
					if(ebObj!=null) {
						eform.setMsg(Utility.get("SAVE_DUPLICATE"));//msg = "มีข้อมูลนี้อยู่แล้ว";
						sf.getCurrentSession().getTransaction().rollback();
						return mapping.findForward("economicBreed");
					}
					EconomicBreed ecoBreedObj = home.findByBreedTypeId(Long.parseLong(tmpData[0]), Long.parseLong(tmpData[1]));
					home.delete(ecoBreedObj); //Can't update must delete and add new
					ebObj = new EconomicBreed();
					ebObj.setBreedTypeId(eform.getBreedTypeId());
					ebObj.setRegionNo(Long.parseLong(eform.getProvince().split("\\|")[1]));
					ebObj.setProvinceNo(Long.parseLong(eform.getProvince().split("\\|")[0]));
					ebObj.setLastUpdateBy(userLogin.getUserName());
					ebObj.setLastUpdateDate(new Date());
				}
			}
			home.saveOrUpdate(ebObj);
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS"));//eform.setMsg("บันทึกสำเร็จ");
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(msg);
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("economicBreed");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		load(mapping, form, request, response);
		EconomicBreedForm eform = (EconomicBreedForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		try {
			sf.getCurrentSession().beginTransaction();
			eform.setBreedTypeId(eform.getBreedTypeId());
			eform.setProvince(eform.getProvince());
			eform.setTmpEdit(eform.getBreedTypeId()+"|"+eform.getProvince());
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("economicBreed");
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EconomicBreedForm eform = (EconomicBreedForm) form;
		HttpSession session = request.getSession();
		//session.removeAttribute("ecoBreedTypeList");
		session.removeAttribute("ecoProvinceList");
		
		SessionFactory sf = HibernateHome.getSessionFactory();
		
		try {
			sf.getCurrentSession().beginTransaction();
			BreedTypeHome bhome = new BreedTypeHome();
			List<BreedType> bList = new ArrayList<BreedType>();
			BreedType b = new BreedType();
			bList = bhome.findAll();
			b.setBreedTypeId(0);
			b.setBreedTypeName("กรุณาเลือก");
			bList.add(0,b);
			session.setAttribute("ecoBreedTypeList", bList);

			ProvinceHome phome = new ProvinceHome();
			List<Province> pList = new ArrayList<Province>();
			Province p = new Province();
			pList = phome.findAll();
			p.setProvinceNo(0);
			p.setThaiName("กรุณาเลือก");
			pList.add(0,p);
			session.setAttribute("ecoProvinceList", pList);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("economicBreed");
	}
}
