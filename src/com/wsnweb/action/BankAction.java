package com.wsnweb.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.wsndata.data.Bank;
import com.wsndata.data.BankBranch;
import com.wsndata.data.Branch;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BankBranchHome;
import com.wsndata.dbaccess.BankHome;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsnweb.form.BankForm;
import com.wsnweb.util.Utility;

public class BankAction extends Action {
	private static final Logger log = Logger.getLogger(BankAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		BankForm eform = (BankForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			 if ("Save".equals(eform.getCmd())){ 
				 return save(mapping, form, request, response);
			 } else if( "Edit".equals(eform.getCmd()) ){
				 return edit(mapping, form, request, response);
			 } else if( "GetBank".equals(eform.getCmd())){
				 return getBank(mapping, form, request, response);
		     }else{
				 return load(mapping, form, request, response);
			 }
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		BankForm eform = (BankForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		BankHome home = new BankHome();
		BranchHome bHome = new BranchHome();
		BankBranchHome bBHome = new BankBranchHome();
		PlantHome pHome = new PlantHome();
		String bankName = "";
		String msg = "";
		try{
			sf.getCurrentSession().beginTransaction();
			bankName = eform.getBankName() == null? "":eform.getBankName();
			
            if (eform.getBankName()==null || "".equals(eform.getBankName())) {
            	msg = "ข้อมูลไม่ถูกต้อง";
				eform.setMsg(msg);
				return mapping.findForward("bank");
            }
            Bank checkBank = home.findByBankName(eform.getBankName());
            if (checkBank!=null && checkBank.getBankId()!=eform.getBankId()) {
    			//sf.getCurrentSession().getTransaction().commit();
    			msg = "ไม่สามารถบันทึกได้ เนื่องจากมี " + eform.getBankName() + " อยู่แล้วในระบบ";
				eform.setMsg(msg);
				return mapping.findForward("bank");
            }
            
            //bHome.deleteAllBranch(eform.getBankId());
            Bank bankObj = home.findByBankId(eform.getBankId());
            if(bankObj ==null) {
				bankObj = new Bank();
			    bankObj.setCreateBy(userLogin.getUserName());
				bankObj.setCreateDate(new Date());
				
                if(!"".equals(eform.getBranchCode())){
                    Set<Branch> branchSet = new HashSet<Branch>();
                	String[] buff = eform.getBranchCode().split(",");
                	
    	            for(int i=0; i<buff.length; i++) {
    	            	Branch branch = new Branch();
    	            	branch.setBranchCode(Long.parseLong(buff[i]));
    	            	branchSet.add(branch);
    	            }
                    bankObj.setBankBranch(branchSet);
                }
    			bankObj.setBankName(bankName);
    		    bankObj.setLastUpdateBy(userLogin.getUserName());
    			bankObj.setLastUpdateDate(new Date());
    			
    			home.persist(bankObj);
            } else {
    			bankObj.setBankName(bankName);
    		    bankObj.setLastUpdateBy(userLogin.getUserName());
    			bankObj.setLastUpdateDate(new Date());
    			
    			home.update(bankObj);
            }
            
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(Utility.get("SAVE_SUCCESS")); //eform.setMsg("Save Bank Successful.");

			sf.getCurrentSession().beginTransaction();
            String bCode="", bName="";
            int num=0;
            if(!"".equals(eform.getBranchCode()) && eform.getBankId()>0){
                List<BankBranch> branchList = bBHome.searchByBankId(eform.getBankId());
            	for(int j=0;j<branchList.size();j++) {
    				if (j+1==branchList.size()) {
    					bCode += branchList.get(j).getBranchCode();
    					break;
    				}
    				bCode += branchList.get(j).getBranchCode()+",";
            	}
            	String[] aBuff = eform.getBranchCode().split(",");
            	String[] bBuff = bCode.split(",");
        		
        		for(int x = 0; x < aBuff.length; x++){
        			num = 0;
        			for(int y = 0; y < bBuff.length; y++){
        				if(aBuff[x].equals(bBuff[y])) {
        					num++;
        					break;
        				}
        			}
        			if(num==0) {
        				bBHome.addBranchCode(Long.parseLong(aBuff[x]), eform.getBankId());
        				//System.out.println(aBuff[x]+" add in db");
        			}
        		}
        		
        		for(int x = 0; x < bBuff.length; x++){
        			num = 0;
        			for(int y = 0; y < aBuff.length; y++){
        				if(bBuff[x].equals(aBuff[y])) {
        					num++;
        					break;
        				}
        			}
        			if(num==0 && !"".equals(bBuff[x])) {
        				//System.out.println(bBuff[x]+" delete in DB "+bankObj.getBankId());
        				
        				Object plant = pHome.findByBankBranch(bankObj.getBankId(), Long.parseLong(bBuff[x]));
        				if(plant.toString().equals("0")){
        					bBHome.deleteByBranchCode(Long.parseLong(bBuff[x]), eform.getBankId());
        				} else {
        					bName += "\\n- "+bHome.findByBranchCode(Long.parseLong(bBuff[x])).getBranchName();
        				}
        			}
     			}
    			if(!bName.equals(""))
    				msg = "\\nแต่ไม่สามารถลบข้อมูลสาขาดังต่อไปนี้ได้"+bName+"\\nเนื่องจากมีการใช้งานอยู่ในขณะนี้";
            }
			sf.getCurrentSession().getTransaction().commit();
            eform.setMsg(Utility.get("SAVE_SUCCESS")+msg);
		}catch (Exception e) {
			eform.setMsg(msg);
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("bank");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		BankForm eform = (BankForm)form;
		HttpSession session = request.getSession();
		List<Branch> leftBranchList = new ArrayList<Branch>();
		List<Branch> rightBranchList = new ArrayList<Branch>();
		SessionFactory sf = HibernateHome.getSessionFactory();
		try{			
			sf.getCurrentSession().beginTransaction();
			BranchHome bHome = new BranchHome();
			
			List leftList = bHome.searchByNotIn(eform.getBankId());
			List rightList = bHome.searchByIn(eform.getBankId());
			
			Branch bObj = null;
			for(int i=0;i<leftList.size();i++) {
				Object[] obj = (Object[])leftList.get(i);
				bObj = new Branch();
				bObj.setBranchCode(Long.parseLong(obj[0].toString()));
				bObj.setBranchName(obj[1].toString());
				bObj.setAddress(obj[2].toString());
				bObj.setProvinceName(obj[3].toString());
				bObj.setDistrictName(obj[4].toString());
				bObj.setSubDistrictName(obj[5].toString());
				leftBranchList.add(bObj);
			}
			for(int i=0;i<rightList.size();i++) {
				Object[] obj = (Object[])rightList.get(i);
				bObj = new Branch();
				bObj.setBranchCode(Long.parseLong(obj[0].toString()));
				bObj.setBranchName(obj[1].toString());
				bObj.setAddress(obj[2].toString());
				bObj.setProvinceName(obj[3].toString());
				bObj.setDistrictName(obj[4].toString());
				bObj.setSubDistrictName(obj[5].toString());
				rightBranchList.add(bObj);
			}
			
			session.setAttribute("branchList", leftBranchList);
			session.setAttribute("newBranchList", rightBranchList);
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL")); //eform.setMsg("Load Data failed.");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("bank");
	}
	
	public ActionForward load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BankForm eform = (BankForm) form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		List<Branch> branchList = new ArrayList<Branch>();
		List<Branch> newBranchList = new ArrayList<Branch>();
		try {
			sf.getCurrentSession().beginTransaction();
			BranchHome branchHome = new BranchHome();
			
			List branchLst = branchHome.searchByAll();
			for(int i=0;i<branchLst.size();i++) {
				Object[] obj = (Object[])branchLst.get(i);
				Branch bObj = new Branch();
				bObj.setBranchCode(Long.parseLong(obj[0].toString()));
				bObj.setBranchName(obj[1].toString());
				bObj.setAddress(obj[2].toString());
				bObj.setProvinceName(obj[3].toString());
				bObj.setDistrictName(obj[4].toString());
				bObj.setSubDistrictName(obj[5].toString());
				branchList.add(bObj);
			}
			
			session.setAttribute("branchList", branchList);
			session.setAttribute("newBranchList", newBranchList);
		} catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward("bank");
	}

	
	//Ajax Method
	public ActionForward getBank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
			SessionFactory sf = HibernateHome.getSessionFactory();
			BankForm eform = (BankForm)form;
			Bank bank = null, bankObj = null;
			long bankId = 0;
			try{

				sf.getCurrentSession().beginTransaction();
				BankHome home = new BankHome();
				bankId = eform.getBankId();
				if(bankId != 0)
					bankObj = home.findByBankId(bankId);
				if(bankId == 0 || (bankObj!=null && !bankObj.getBankName().equals(eform.getBankName())))
					bank = home.findByBankName(eform.getBankName());
			
				response.setCharacterEncoding("UTF-8");
			    PrintWriter wt = response.getWriter();
			    JsonConfig jsonConfig = new JsonConfig();   
			    jsonConfig.setExcludes(new String[]{ "lastUpdateDate", "lastUpdateBy", "createDate", "createBy", "bankBranch", "checkBox", "linkImageEdit"});
			    String result = JSONArray.fromObject(bank, jsonConfig).toString();
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
