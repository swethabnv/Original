package com.wsnweb.action;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

import com.dcs.hibernate.HibernateHome;
import com.dcs.util.StringUtil;
import com.wsndata.data.BreedGroup;
import com.wsndata.data.CheckPeriod;
import com.wsndata.data.CheckingDisease;
import com.wsndata.data.CheckingDiseaseChild;
import com.wsndata.data.Coordinates;
import com.wsndata.data.DiseaseType;
import com.wsndata.data.EliminateMixedBreed;
import com.wsndata.data.LandCheck;
import com.wsndata.data.LandCheckDisease;
import com.wsndata.data.LandCheckHarvest;
import com.wsndata.data.LandCheckImages;
import com.wsndata.data.LandCheckManure;
import com.wsndata.data.LandCheckMixed;
import com.wsndata.data.LandCheckPlant;
import com.wsndata.data.LandCheckPrepareSoil;
import com.wsndata.data.LandType;
import com.wsndata.data.ManureType;
import com.wsndata.data.MixedBreedType;
import com.wsndata.data.MixedBreedTypeChild;
import com.wsndata.data.Plant;
import com.wsndata.data.PlantMethod;
import com.wsndata.data.User;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.CheckPeriodHome;
import com.wsndata.dbaccess.CheckingDiseaseChildHome;
import com.wsndata.dbaccess.CheckingDiseaseHome;
import com.wsndata.dbaccess.CoordinatesHome;
import com.wsndata.dbaccess.DiseaseTypeHome;
import com.wsndata.dbaccess.EliminateMixedBreedHome;
import com.wsndata.dbaccess.LandCheckDiseaseHome;
import com.wsndata.dbaccess.LandCheckHarvestHome;
import com.wsndata.dbaccess.LandCheckHome;
import com.wsndata.dbaccess.LandCheckImagesHome;
import com.wsndata.dbaccess.LandCheckManureHome;
import com.wsndata.dbaccess.LandCheckMixedHome;
import com.wsndata.dbaccess.LandCheckPlantHome;
import com.wsndata.dbaccess.LandCheckPrepareSoilHome;
import com.wsndata.dbaccess.LandRightHome;
import com.wsndata.dbaccess.LandTypeHome;
import com.wsndata.dbaccess.ManureTypeHome;
import com.wsndata.dbaccess.MixedBreedTypeChildHome;
import com.wsndata.dbaccess.MixedBreedTypeHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.PlantMethodHome;
import com.wsnweb.form.LandCheckForm;
import com.wsnweb.util.Utility;

public class LandCheckAction extends Action {
	private static final Logger log = Logger.getLogger(LandCheckAction.class);
	DateFormat dfTimeMillisec = new SimpleDateFormat("ddMMyyHHmmssSSS");
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		LandCheckForm eform = (LandCheckForm) form;
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if (userLogin == null) {
				return mapping.findForward("unauthorize");
		} else {
			if("Save".equals(eform.getCmd())){ 
				return save(mapping, form, request, response);
			}else if( "Edit".equals(eform.getCmd()) ){
				load(mapping, form, request, response);
				return edit(mapping, form, request, response);
			}else if("reload".equals(eform.getCmd()) || "reloadClose".equals(eform.getCmd()) ){
				return reload(mapping, eform, request, response);
			} else if("image".equals(eform.getCmd())) {
				return showImage(mapping, eform, request, response);
			}else if("imageObject".equals(eform.getCmd())) {				//show image session to newsLayout.jsp
				return getImageObject(mapping, eform, request, response);
			}else if("setValueSession".equals(eform.getCmd())) {			//keep json to session (ajax)
				return setValueSession(mapping, eform, request, response);
			}else if("detailImage".equals(eform.getCmd())){					//open detailImagePopup.jsp
				return detailImage(mapping, eform, request, response);
			}else if("showImage".equals(eform.getCmd())) {
				return showImagePopup(mapping, eform, request, response);
			}else if("getImage".equals(eform.getCmd())) {					//show image session to detailImagePopup.jsp
				return getImage(mapping, eform, request, response);
			}else if("checkSize".equals(eform.getCmd())) {					//check size not over 300kb detailImage.jsp
				return checkSize(mapping, eform, request, response);
			}else if("saveDetailImage".equals(eform.getCmd())){				//save image detailImagePopup.jsp to session
				return saveDetailImage(mapping, eform, request, response);
			} else if("deleteImage".equals(eform.getCmd())) {			//delete image per click
				return deleteImage(mapping, eform, request, response);
			}else if("deleteImageObject".equals(eform.getCmd())) {		//delete image per click
				return deleteImageObject(mapping, eform, request, response);
			}else if("getLandRightInfo".equals(eform.getCmd())) {
				return getLandRightInfo(mapping, eform, request, response);
			}else if("getCoordinateInfo".equals(eform.getCmd())) {
				return getCoordinate(mapping, eform, request, response);
			}else if("getMixedBreedInfo".equals(eform.getCmd())) {
				return getMixedBreed(mapping, eform, request, response);
			}else if("getMixedBreedTypeInfo".equals(eform.getCmd())) {
				return getMixedBreedType(mapping, eform, request, response);
			}else if("getDiseaseChildInfo".equals(eform.getCmd())) {
				return getDiseaseChild(mapping, eform, request, response);
			}else if("getDiseaseTypeInfo".equals(eform.getCmd())) {
				return getDiseaseType(mapping, eform, request, response);
			} else{
				return load(mapping, form, request, response);
			}
		}
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  
	{
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List<Plant> plantYearList = new ArrayList<Plant>(); // ปีการผลิต
		//User userLogin = (User) session.getAttribute("userLogin"); // ข้อมูลผู้ใช้

		session.removeAttribute("landCheckList");
		session.removeAttribute("coordinatesList");
		session.removeAttribute("imageList");
		session.removeAttribute("landCheckData");
		
		LandCheckForm eform = (LandCheckForm)form;
		try{
			sf.getCurrentSession().beginTransaction();

			PlantHome pHome = new PlantHome();
			BreedGroupHome bgHome = new BreedGroupHome();
			CheckPeriodHome cpHome = new CheckPeriodHome();
			CheckingDiseaseHome cdHome = new CheckingDiseaseHome();
			ManureTypeHome mHome = new ManureTypeHome();
			PlantMethodHome pmHome = new PlantMethodHome();
			LandTypeHome lHome = new LandTypeHome();
			EliminateMixedBreedHome emHome = new EliminateMixedBreedHome();
			
			 // --- ดึงปีการผลิตออกมา --- // 
			List<?> pList = pHome.getPlantYear();
			if(pList != null && pList.size()>0){
			  for(int a=0; a<pList.size(); a++){
				  if(pList.get(a) !=null){
					  Plant pYear = new Plant();
					  pYear.setPlantYear(Integer.parseInt(pList.get(a).toString(), 10)); 
					  plantYearList.add(pYear);
				  }
			  }
			}
			 // --- ดึงกลุ่มพันธุ์ออกมา --- // 
			List<BreedGroup> breedGroupList = bgHome.findAll();
			 // --- ดึงระยะการปลูกออกมา --- // 
			List<CheckPeriod> checkPeriodList = cpHome.findAll();
			 // --- ดึงการตรวจโรคออกมา --- // 
			List<CheckingDisease> checkingDiseaseList = cdHome.findAll();
			 // --- ดึงชนิดปุ๋ยออกมา --- // 
			List<ManureType> manureTypeList = mHome.findAll();
			 // --- ดึงวิธีการปลูกออกมา --- // 
			List<PlantMethod> plantMethodList = pmHome.findAll();
			 // --- ดึงประเภทของดินออกมา --- // 
			List<LandType> landTypeList = lHome.findAll();
			List<LandType> toRemove = new ArrayList<LandType>();
			for(LandType list: landTypeList){
			    if(list.getLandTypeName().equalsIgnoreCase("อื่นๆ")){
			        toRemove.add(list);
			    }
			}
			landTypeList.removeAll(toRemove);
			 // --- ดึงวิธีการปลูกออกมา --- // 
			List<EliminateMixedBreed> eliminateMixedBreedList = emHome.findAll();
			
			Date curDate = new Date();
			eform.setCheckHour(new SimpleDateFormat ("HH").format(curDate));
			eform.setCheckMinute(new SimpleDateFormat ("mm").format(curDate));
			//eform.setCheckBy(userLogin.getFirstName()+" "+userLogin.getLastName());
			
			session.setAttribute("landCheckYearList", plantYearList);
			session.setAttribute("allBreedGroupList", breedGroupList);
			session.setAttribute("checkPeriodList", checkPeriodList);
			session.setAttribute("checkingDiseaseList", checkingDiseaseList);
			session.setAttribute("manureTypeList", manureTypeList);
			session.setAttribute("plantMethodList", plantMethodList);
			session.setAttribute("landTypeList", landTypeList);
			session.setAttribute("eliminateMixedBreedList", eliminateMixedBreedList);
		} catch(Exception e){
	    	 e.printStackTrace();
	    	 log.error(e,e);
		} finally {
	    	 sf.getCurrentSession().close();
	    }       
		return mapping.findForward("landCheck");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		LandCheckForm eform = (LandCheckForm)form;
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		SessionFactory sf = HibernateHome.getSessionFactory();
		String msg = Utility.get("SAVE_SUCCESS");
		try{
			sf.getCurrentSession().beginTransaction();
			LandCheckHome home = new LandCheckHome();	
			CoordinatesHome cHome = new CoordinatesHome();
			LandCheckImagesHome iHome = new LandCheckImagesHome();
			
			LandCheck landCheckObj = home.findByLandCheckId(eform.getLandCheckId());
			Calendar checkDate = Calendar.getInstance();
			if(!"".equals(eform.getCheckDate())){
				checkDate.setTime(formatter.parse(eform.getCheckDate()));
				checkDate.set(Calendar.YEAR, checkDate.get(Calendar.YEAR)-543);
			}
			
			if(landCheckObj != null){  //Edit Case
				//eform.loadToBean(landCheckObj);
				landCheckObj.setCheckTime(eform.getCheckHour()+ ":" + eform.getCheckMinute());
				landCheckObj.setResult(eform.getResult());
				landCheckObj.setRemark(eform.getRemark());
				landCheckObj.setCheckBy(eform.getCheckBy());

				landCheckObj.setCheckDate(checkDate.getTime());
				landCheckObj.setLastUpdateBy(userLogin.getUserName());
				landCheckObj.setLastUpdateDate(new Date());
				
				cHome.deleteByCoordinatesKey(eform.getPlantYear(), eform.getPlantNo(), eform.getIdCard(), eform.getTypeId(), eform.getBreedTypeId(), eform.getDocNo());  // delete coordinate by landcheck id
	    		
				String[] coordinates = null;
				if(eform.getCoordinatesData() !=null && !"".equals(eform.getCoordinatesData())){
					coordinates = eform.getCoordinatesData().split("\\|");
					if(coordinates !=null && coordinates.length>0){
						for(int a=0; a<coordinates.length; a++){
							String[] coordinateByRow = coordinates[a].split("\\:");
							Coordinates coordinatesObj = new Coordinates();
							coordinatesObj.setSeq(Integer.parseInt(coordinateByRow[0]));
							coordinatesObj.setLatitude(coordinateByRow[1]);
							coordinatesObj.setLongitude(coordinateByRow[2]);
							coordinatesObj.setPlantYear(eform.getPlantYear());
							coordinatesObj.setPlantNo(eform.getPlantNo());
							coordinatesObj.setIdCard(eform.getIdCard());
							coordinatesObj.setTypeId(eform.getTypeId());
							coordinatesObj.setDocNo(eform.getDocNo());
							coordinatesObj.setBreedTypeId(eform.getBreedTypeId());
							cHome.saveCoordinates(coordinatesObj);
						}
					}
				}
				long checkPeriod = landCheckObj.getCheckPeriodId();
				if(checkPeriod==1) {
					savePrepareSoil("Edit", eform, landCheckObj);
				} else if(checkPeriod==2) {
					savePlant("Edit", eform, landCheckObj);
				} else if(checkPeriod==3 || checkPeriod==4) {
					saveManure("Edit", eform, landCheckObj);
					saveMixed("Edit", eform, landCheckObj);
					saveDisease("Edit", eform, landCheckObj);
				} else if(checkPeriod==5 || checkPeriod==6) {
					saveMixed("Edit", eform, landCheckObj);
					saveDisease("Edit", eform, landCheckObj);
				} else if(checkPeriod==7) {
					saveHarvest("Edit", eform, landCheckObj);
				}
				
				iHome.deleteImagebyLandCheckId(eform.getLandCheckId()); // delete image by landcheck id
				
				List imgList = (List)session.getAttribute("imageList");
				List landCheckImageList = new ArrayList();
				LandCheckImages newImage = null;
				if(imgList !=null && imgList.size() >0){
					for(int j=0; j<imgList.size(); j++){
						LandCheckImages image = (LandCheckImages)imgList.get(j);
						if(image !=null){
							BufferedImage img = ImageIO.read(image.getImageBlob().getBinaryStream());
							
							newImage = new LandCheckImages();
							newImage.setContentType(image.getContentType());
							//newImage.setImageBlob(image.getImageBlob());
							newImage.setImageBlob(resizeImage(img));
							newImage.setLandCheck(landCheckObj);
							newImage.setImageName(newImage.getImageId()+"");
							newImage.setSeq(j+1);
							landCheckImageList.add(newImage);
						}
					}
				}
				landCheckObj.setLandCheckImages(landCheckImageList);
				home.saveOrUpdate(landCheckObj);
			}else{
				landCheckObj = new LandCheck();
				eform.loadToBean(landCheckObj);
				
				List<LandCheck> duplicateList = null;
				duplicateList = home.findLandCheck(eform.getPlantYear(), eform.getPlantNo(), eform.getIdCard(), 
						eform.getTypeId(), eform.getBreedTypeId(), eform.getDocNo(), 
						checkDate.getTime(), eform.getCheckPeriod());
				
				if(duplicateList != null && duplicateList.size() > 0) {
					msg = Utility.get("SAVE_DUPLICATE");
				} else {
					landCheckObj.setCheckDate(checkDate.getTime());
					landCheckObj.setLastUpdateBy(userLogin.getUserName());
					landCheckObj.setLastUpdateDate(new Date());
					
					String[] coordinates = null;
					if(eform.getCoordinatesData() !=null && !"".equals(eform.getCoordinatesData())){
						coordinates = eform.getCoordinatesData().split("\\|");
						if(coordinates !=null && coordinates.length>0){
							for(int a=0; a<coordinates.length; a++){
								String[] coordinateByRow = coordinates[a].split("\\:");
								Coordinates coordinatesObj = new Coordinates();
								coordinatesObj.setSeq(Integer.parseInt(coordinateByRow[0]));
								coordinatesObj.setLatitude(coordinateByRow[1]);
								coordinatesObj.setLongitude(coordinateByRow[2]);
								coordinatesObj.setPlantYear(eform.getPlantYear());
								coordinatesObj.setPlantNo(eform.getPlantNo());
								coordinatesObj.setIdCard(eform.getIdCard());
								coordinatesObj.setTypeId(eform.getTypeId());
								coordinatesObj.setDocNo(eform.getDocNo());
								coordinatesObj.setBreedTypeId(eform.getBreedTypeId());

								List<Coordinates> duplicateCooList = cHome.findDuplicateByKey(eform.getPlantYear(), eform.getPlantNo(), eform.getIdCard(), eform.getTypeId(), eform.getBreedTypeId(), eform.getDocNo(), coordinateByRow[1], coordinateByRow[2]);
								if(duplicateCooList==null || duplicateCooList.size()==0)
									cHome.saveCoordinates(coordinatesObj);
							}
						}
						//landCheckObj.setCoordinates(coList);
					}
					if(eform.getCheckPeriod()==1) {
						landCheckObj.setLandCheckPrepareSoil(savePrepareSoil("Add", eform, landCheckObj));
					} else if(eform.getCheckPeriod()==2) {
						landCheckObj.setLandCheckPlant(savePlant("Add", eform, landCheckObj));
					} else if(eform.getCheckPeriod()==3 || eform.getCheckPeriod()==4) {
						landCheckObj.setLandCheckManure(saveManure("Add", eform, landCheckObj));
						landCheckObj.setLandCheckMixed(saveMixed("Add", eform, landCheckObj));
						landCheckObj.setLandCheckDisease(saveDisease("Add", eform, landCheckObj));
					} else if(eform.getCheckPeriod()==5 || eform.getCheckPeriod()==6) {
						landCheckObj.setLandCheckMixed(saveMixed("Add", eform, landCheckObj));
						landCheckObj.setLandCheckDisease(saveDisease("Add", eform, landCheckObj));
					} else if(eform.getCheckPeriod()==7) {
						landCheckObj.setLandCheckHarvest(saveHarvest("Add", eform, landCheckObj));
					}
					
					List imgList = (List)session.getAttribute("imageList");
					List landCheckImageList = new ArrayList();
					LandCheckImages newImage = null;
					if(imgList !=null && imgList.size() >0){
						for(int j=0; j<imgList.size(); j++){
							LandCheckImages image = (LandCheckImages)imgList.get(j);
							if(image !=null){
								BufferedImage img = ImageIO.read(image.getImageBlob().getBinaryStream());
								
								newImage = new LandCheckImages();
								newImage.setContentType(image.getContentType());
								//newImage.setImageBlob(image.getImageBlob());
								newImage.setImageBlob(resizeImage(img));
								newImage.setLandCheck(landCheckObj);
								newImage.setImageName(newImage.getImageId()+"");
								newImage.setSeq(j+1);
								landCheckImageList.add(newImage);
							}
						}
					}
					
					landCheckObj.setLandCheckImages(landCheckImageList);
					home.persist(landCheckObj);
				}
			}
			sf.getCurrentSession().getTransaction().commit();
			eform.setMsg(msg);
		}catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg(Utility.get("SAVE_FAIL"));
			e.printStackTrace();
			log.error(e, e);
		} finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("landCheck");
	}
	
	private List<LandCheckPrepareSoil> savePrepareSoil(String mode, LandCheckForm eform, LandCheck landCheck)
	{
		List<LandCheckPrepareSoil> list = new ArrayList<LandCheckPrepareSoil>();
		long[] prepareSoilId = eform.getPrepareSoilId();
		String[] prepareSoilDate = eform.getPrepareSoilDate();
		String[] prepareSoilType = eform.getPrepareSoilType();
		String[] prepareSoilWater = eform.getPrepareSoilWater();
		String[] prepareSoilOperate = eform.getPrepareSoilOperate();
		String[] prepareSoilOperateOther = eform.getPrepareSoilOperateOther();
		String[] prepareSoilRemark = eform.getPrepareSoilRemark();
		String[] prepareSoilCheckBy = eform.getPrepareSoilCheckBy();
		String[] prepareSoilResult = eform.getPrepareSoilResult();
		
		if(mode.equals("Edit")) {
			LandCheckPrepareSoilHome home = new LandCheckPrepareSoilHome();
			String[] delPreId = eform.getDelPreId();
			if(delPreId!=null) {
				for(int i=0;i<delPreId.length;i++) {
					home.deleteLandCheckPrepareSoil(Long.parseLong(delPreId[i]));
				}
			}
	        if(prepareSoilOperate!=null && prepareSoilDate!=null){
	        	for(int i=0;i<prepareSoilOperate.length;i++) {
	        		LandCheckPrepareSoil prepareSoilObj = null;
	        		if(prepareSoilId[i] == 0) {
	        			prepareSoilObj = new LandCheckPrepareSoil();
	        		} else {
	        			prepareSoilObj = home.findByLandCheckSoilId(prepareSoilId[i]);
	        		}
	        		prepareSoilObj.setLandCheckId(landCheck.getLandCheckId());
	        		prepareSoilObj.setSeq(i+1);
	        		prepareSoilObj.setCheckDate(Utility.stringToDate(prepareSoilDate[i]));
	        		prepareSoilObj.setSoilType(prepareSoilType[i]);
	        		prepareSoilObj.setSourceWater(prepareSoilWater[i]);
	        		prepareSoilObj.setOperate(prepareSoilOperate[i]);
	        		prepareSoilObj.setOperateOther(prepareSoilOperateOther[i]);
	        		prepareSoilObj.setResult(prepareSoilResult[i].equals("ผ่าน")?"P":"F");
	        		prepareSoilObj.setRemark(prepareSoilRemark[i]);
	        		prepareSoilObj.setChecker(prepareSoilCheckBy[i]);
	        		
	        		home.saveOrUpdate(prepareSoilObj);
	        	}
	        }
		} else {
			if((prepareSoilOperate !=null && prepareSoilOperate.length>0) &&  (prepareSoilDate !=null && prepareSoilDate.length>0)){
				for(int i=0;i<prepareSoilOperate.length;i++) {
					LandCheckPrepareSoil prepareSoilObj = new LandCheckPrepareSoil();
	        		prepareSoilObj.setCheckDate(Utility.stringToDate(prepareSoilDate[i]));
	        		prepareSoilObj.setSoilType(prepareSoilType[i]);
	        		prepareSoilObj.setSourceWater(prepareSoilWater[i]);
	        		prepareSoilObj.setOperate(prepareSoilOperate[i]);
	        		prepareSoilObj.setOperateOther(prepareSoilOperateOther[i]);
	        		prepareSoilObj.setResult(prepareSoilResult[i].equals("ผ่าน")?"P":"F");
	        		prepareSoilObj.setRemark(prepareSoilRemark[i]);
	        		prepareSoilObj.setChecker(prepareSoilCheckBy[i]);
	        		prepareSoilObj.setLandCheck(landCheck);
	        		list.add(prepareSoilObj);
	        	}
			}
		}
        
		return list;
	}
	
	private List<LandCheckPlant> savePlant(String mode, LandCheckForm eform, LandCheck landCheck)
	{
		List<LandCheckPlant> list = new ArrayList<LandCheckPlant>();
		long[] plantId = eform.getPlantId();
		String[] plantDate = eform.getPlantDate();
		String[] plantMethod = eform.getPlantMethod();
		String[] plantType = eform.getPlantType();
		long[] plantName = eform.getPlantName();
		String[] plantSource = eform.getPlantSource();
		String[] plantSowDate = eform.getPlantSowDate();
		String[] plantThrowDate = eform.getPlantThrowDate();
		double[] plantUse = eform.getPlantUse();
		String[] plantRemark = eform.getPlantRemark();
		String[] plantCheckBy = eform.getPlantCheckBy();
		String[] plantResult = eform.getPlantResult();
		
		if(mode.equals("Edit")) {
			LandCheckPlantHome home = new LandCheckPlantHome();
			String[] delPlaId = eform.getDelPlaId();
			if(delPlaId!=null) {
				for(int i=0;i<delPlaId.length;i++) {
					home.deleteLandCheckPlant(Long.parseLong(delPlaId[i]));
				}
			}
	        if(plantMethod!=null && plantDate!=null){
	        	for(int i=0;i<plantMethod.length;i++) {
	        		LandCheckPlant plantObj = null;
	        		if(plantId[i] == 0) {
	        			plantObj = new LandCheckPlant();
	        		} else {
	        			plantObj = home.findByLandCheckPlantId(plantId[i]);
	        		}
	        		plantObj.setLandCheckId(landCheck.getLandCheckId());
	        		plantObj.setSeq(i+1);
	        		plantObj.setCheckDate(Utility.stringToDate(plantDate[i]));
	        		plantObj.setPlantMethod(plantMethod[i]);
	        		plantObj.setBreedTypeLevel(plantType[i]);
	        		plantObj.setBreedTypeId(plantName[i]);
	        		plantObj.setSourceSeed(plantSource[i]);
	        		plantObj.setSowDate(Utility.stringToDate(plantSowDate[i]));
	        		plantObj.setThrowDate(Utility.stringToDate(plantThrowDate[i]));
	        		plantObj.setUseRate(plantUse[i]);
	        		plantObj.setResult(plantResult[i].equals("ผ่าน")?"P":"F");
	        		plantObj.setRemark(plantRemark[i]);
	        		plantObj.setChecker(plantCheckBy[i]);
	        		
	        		home.saveOrUpdate(plantObj);
	        	}
	        }
		} else {
			if((plantMethod !=null && plantMethod.length>0) && (plantDate !=null && plantDate.length>0)){
				for(int i=0;i<plantMethod.length;i++) {
					LandCheckPlant plantObj = new LandCheckPlant();
	        		plantObj.setSeq(i+1);
	        		plantObj.setCheckDate(Utility.stringToDate(plantDate[i]));
	        		plantObj.setPlantMethod(plantMethod[i]);
	        		plantObj.setBreedTypeLevel(plantType[i]);
	        		plantObj.setBreedTypeId(plantName[i]);
	        		plantObj.setSourceSeed(plantSource[i]);
	        		plantObj.setSowDate(Utility.stringToDate(plantSowDate[i]));
	        		plantObj.setThrowDate(Utility.stringToDate(plantThrowDate[i]));
	        		plantObj.setUseRate(plantUse[i]);
	        		plantObj.setResult(plantResult[i].equals("ผ่าน")?"P":"F");
	        		plantObj.setRemark(plantRemark[i]);
	        		plantObj.setChecker(plantCheckBy[i]);
	        		plantObj.setLandCheck(landCheck);
	        		list.add(plantObj);
	        	}
			}
		}
        
		return list;
	}
	
	private List<LandCheckManure> saveManure(String mode, LandCheckForm eform, LandCheck landCheck)
	{
		List<LandCheckManure> list = new ArrayList<LandCheckManure>();
		long[] manureId = eform.getManureId();
		String[] manureDate = eform.getManureDate();
		long[] manureType = eform.getManureType();
		String[] manureOther = eform.getManureOther();
		String[] manureStatus = eform.getManureStatus();
		String[] manureName = eform.getManureName();
		String[] manureFormula = eform.getManureFormula();
		String[] manureBuyDate = eform.getManureBuyDate();
		String[] manureSourceBuy = eform.getManureSourceBuy();
		double[] manureUseRate = eform.getManureUseRate();
		double[] manureTotalUse = eform.getManureTotalUse();
		String[] manureRemark = eform.getManureRemark();
		String[] manureCheckBy = eform.getManureCheckBy();
		String[] manureResult = eform.getManureResult();
		
		if(mode.equals("Edit")) {
			LandCheckManureHome home = new LandCheckManureHome();
			String[] delManId = eform.getDelManId();
			if(delManId!=null) {
				for(int i=0;i<delManId.length;i++) {
					home.deleteLandCheckManure(Long.parseLong(delManId[i]));
				}
			}
	        if(manureType!=null && manureDate!=null){
	        	for(int i=0;i<manureType.length;i++) {
	        		LandCheckManure manureObj = null;
	        		if(manureId[i] == 0) {
	        			manureObj = new LandCheckManure();
	        		} else {
	        			manureObj = home.findByLandCheckManureId(manureId[i]);
	        		}
	        		manureObj.setLandCheckId(landCheck.getLandCheckId());
	        		manureObj.setSeq(i+1);
	        		manureObj.setCheckDate(Utility.stringToDate(manureDate[i]));
	        		manureObj.setManureTypeId(manureType[i]);
	        		manureObj.setManureOther(manureOther[i]);
	        		manureObj.setManureStatus(manureStatus[i].equals("ซื้อ")?"B":"P");
	        		manureObj.setManureName(manureName[i]);
	        		manureObj.setFormula(manureFormula[i]);
	        		manureObj.setBuyDate(Utility.stringToDate(manureBuyDate[i]));
	        		manureObj.setSourceBuy(manureSourceBuy[i]);
	        		manureObj.setUseRate(manureUseRate[i]);
	        		manureObj.setTotalUse(manureTotalUse[i]);
	        		manureObj.setResult(manureResult[i].equals("ผ่าน")?"P":"F");
	        		manureObj.setRemark(manureRemark[i]);
	        		manureObj.setChecker(manureCheckBy[i]);
	        		
	        		home.saveOrUpdate(manureObj);
	        	}
	        }
		} else {
			if((manureType !=null && manureType.length>0) && (manureDate !=null && manureDate.length>0)){
				for(int i=0;i<manureType.length;i++) {
					LandCheckManure manureObj = new LandCheckManure();
	        		manureObj.setSeq(i+1);
	        		manureObj.setCheckDate(Utility.stringToDate(manureDate[i]));
	        		manureObj.setManureTypeId(manureType[i]);
	        		manureObj.setManureOther(manureOther[i]);
	        		manureObj.setManureStatus(manureStatus[i].equals("ซื้อ")?"B":"P");
	        		manureObj.setManureName(manureName[i]);
	        		manureObj.setFormula(manureFormula[i]);
	        		manureObj.setBuyDate(Utility.stringToDate(manureBuyDate[i]));
	        		manureObj.setSourceBuy(manureSourceBuy[i]);
	        		manureObj.setUseRate(manureUseRate[i]);
	        		manureObj.setTotalUse(manureTotalUse[i]);
	        		manureObj.setResult(manureResult[i].equals("ผ่าน")?"P":"F");
	        		manureObj.setRemark(manureRemark[i]);
	        		manureObj.setChecker(manureCheckBy[i]);
	        		manureObj.setLandCheck(landCheck);
	        		list.add(manureObj);
	        	}
			}
		}
        
		return list;
	}
	
	private List<LandCheckMixed> saveMixed(String mode, LandCheckForm eform, LandCheck landCheck)
	{
		List<LandCheckMixed> list = new ArrayList<LandCheckMixed>();
		long[] mixedId = eform.getMixedId();
		String[] mixedDate = eform.getMixedDate();
		long[] mixedBreedType = eform.getMixedBreedType();
		long[] mixedChildId = eform.getMixedChildId();
		long[] mixedEliminateId = eform.getMixedEliminateId();
		String[] mixedEliminateOther = eform.getMixedEliminateOther();
		String[] mixedRemark = eform.getMixedRemark();
		String[] mixedCheckBy = eform.getMixedCheckBy();
		String[] mixedResult = eform.getMixedResult();
		
		if(mode.equals("Edit")) {
			LandCheckMixedHome home = new LandCheckMixedHome();
			String[] delMixId = eform.getDelMixId();
			if(delMixId!=null) {
				for(int i=0;i<delMixId.length;i++) {
					home.deleteLandCheckMixed(Long.parseLong(delMixId[i]));
				}
			}
	        if(mixedBreedType!=null && mixedDate!=null){
	        	for(int i=0;i<mixedBreedType.length;i++) {
	        		LandCheckMixed mixedObj = null;
	        		if(mixedId[i] == 0) {
	        			mixedObj = new LandCheckMixed();
	        		} else {
	        			mixedObj = home.findByLandCheckMixedId(mixedId[i]);
	        		}
	        		mixedObj.setLandCheckId(landCheck.getLandCheckId());
	        		mixedObj.setSeq(i+1);
	        		mixedObj.setCheckDate(Utility.stringToDate(mixedDate[i]));
	        		mixedObj.setMixedBreedTypeId(mixedBreedType[i]);
	        		mixedObj.setChildId(mixedChildId[i]);
	        		mixedObj.setEliminateMixedBreedId(mixedEliminateId[i]);
	        		mixedObj.setEliminateOther(mixedEliminateOther[i]);
	        		mixedObj.setResult(mixedResult[i].equals("ผ่าน")?"P":"F");
	        		mixedObj.setRemark(mixedRemark[i]);
	        		mixedObj.setChecker(mixedCheckBy[i]);
	        		
	        		home.saveOrUpdate(mixedObj);
	        	}
	        }
		} else {
			if((mixedBreedType !=null && mixedBreedType.length>0) && (mixedDate !=null && mixedDate.length>0)){
				for(int i=0;i<mixedBreedType.length;i++) {
					LandCheckMixed mixedObj = new LandCheckMixed();
	        		mixedObj.setSeq(i+1);
	        		mixedObj.setCheckDate(Utility.stringToDate(mixedDate[i]));
	        		mixedObj.setMixedBreedTypeId(mixedBreedType[i]);
	        		mixedObj.setChildId(mixedChildId[i]);
	        		mixedObj.setEliminateMixedBreedId(mixedEliminateId[i]);
	        		mixedObj.setEliminateOther(mixedEliminateOther[i]);
	        		mixedObj.setResult(mixedResult[i].equals("ผ่าน")?"P":"F");
	        		mixedObj.setRemark(mixedRemark[i]);
	        		mixedObj.setChecker(mixedCheckBy[i]);
	        		mixedObj.setLandCheck(landCheck);
	        		list.add(mixedObj);
	        	}
			}
		}
        
		return list;
	}
	
	private List<LandCheckDisease> saveDisease(String mode, LandCheckForm eform, LandCheck landCheck)
	{
		List<LandCheckDisease> list = new ArrayList<LandCheckDisease>();
		long[] diseaseId = eform.getDiseaseId();
		String[] diseaseDate = eform.getDiseaseDate();
		long[] diseaseChecking = eform.getDiseaseChecking();
		long[] diseaseChildId = eform.getDiseaseChildId();
		long[] diseaseTypeId = eform.getDiseaseTypeId();
		String[] diseaseLevel = eform.getDiseaseLevel();
		String[] diseaseOther = eform.getDiseaseOther();
		String[] diseaseTradingName = eform.getDiseaseTradingName();
		String[] diseaseCommonName = eform.getDiseaseCommonName();
		String[] diseaseDangerousName = eform.getDiseaseDangerousName();
		String[] diseaseManDate = eform.getDiseaseManDate();
		String[] diseaseExpDate = eform.getDiseaseExpDate();
		String[] diseaseSourceBuy = eform.getDiseaseSourceBuy();
		String[] diseaseUseDate = eform.getDiseaseUseDate();
		double[] diseaseUseRate1 = eform.getDiseaseUseRate1();
		double[] diseaseUseRate2 = eform.getDiseaseUseRate2();
		String[] diseaseRemark = eform.getDiseaseRemark();
		String[] diseaseCheckBy = eform.getDiseaseCheckBy();
		String[] diseaseResult = eform.getDiseaseResult();
		
		if(mode.equals("Edit")) {
			LandCheckDiseaseHome home = new LandCheckDiseaseHome();
			String[] delDisId = eform.getDelDisId();
			if(delDisId!=null) {
				for(int i=0;i<delDisId.length;i++) {
					home.deleteLandCheckDisease(Long.parseLong(delDisId[i]));
				}
			}
	        if(diseaseChecking!=null && diseaseDate!=null){
	        	for(int i=0;i<diseaseChecking.length;i++) {
	        		LandCheckDisease diseaseObj = null;
	        		if(diseaseId[i] == 0) {
	        			diseaseObj = new LandCheckDisease();
	        		} else {
	        			diseaseObj = home.findByLandCheckDiseaseId(diseaseId[i]);
	        		}
	        		diseaseObj.setLandCheckId(landCheck.getLandCheckId());
	        		diseaseObj.setSeq(i+1);
	        		diseaseObj.setCheckDate(Utility.stringToDate(diseaseDate[i]));
	        		diseaseObj.setCheckingDiseaseId(diseaseChecking[i]);
	        		diseaseObj.setDiseaseChildId(diseaseChildId[i]);
	        		diseaseObj.setDiseaseTypeId(diseaseTypeId[i]);
	        		diseaseObj.setLevel(diseaseLevel[i]);
	        		diseaseObj.setDiseaseOther(diseaseOther[i]);
	        		diseaseObj.setTradingName(diseaseTradingName[i]);
	        		diseaseObj.setCommonName(diseaseCommonName[i]);
	        		diseaseObj.setDangerousName(diseaseDangerousName[i]);
	        		diseaseObj.setManufactureDate(Utility.stringToDate(diseaseManDate[i]));
	        		diseaseObj.setExpireDate(Utility.stringToDate(diseaseExpDate[i]));
	        		diseaseObj.setSourceBuy(diseaseSourceBuy[i]);
	        		diseaseObj.setUseDate(Utility.stringToDate(diseaseUseDate[i]));
	        		diseaseObj.setUseRate1(diseaseUseRate1[i]);
	        		diseaseObj.setUseRate2(diseaseUseRate2[i]);
	        		diseaseObj.setResult(diseaseResult[i].equals("ผ่าน")?"P":"F");
	        		diseaseObj.setRemark(diseaseRemark[i]);
	        		diseaseObj.setChecker(diseaseCheckBy[i]);
	        		
	        		home.saveOrUpdate(diseaseObj);
	        	}
	        }
		} else {
			if((diseaseChecking !=null && diseaseChecking.length>0) && (diseaseDate !=null && diseaseDate.length>0)){
				for(int i=0;i<diseaseChecking.length;i++) {
					LandCheckDisease diseaseObj = new LandCheckDisease();
	        		diseaseObj.setSeq(i+1);
	        		diseaseObj.setCheckDate(Utility.stringToDate(diseaseDate[i]));
	        		diseaseObj.setCheckingDiseaseId(diseaseChecking[i]);
	        		diseaseObj.setDiseaseChildId(diseaseChildId[i]);
	        		diseaseObj.setDiseaseTypeId(diseaseTypeId[i]);
	        		diseaseObj.setLevel(diseaseLevel[i]);
	        		diseaseObj.setDiseaseOther(diseaseOther[i]);
	        		diseaseObj.setTradingName(diseaseTradingName[i]);
	        		diseaseObj.setCommonName(diseaseCommonName[i]);
	        		diseaseObj.setDangerousName(diseaseDangerousName[i]);
	        		diseaseObj.setManufactureDate(Utility.stringToDate(diseaseManDate[i]));
	        		diseaseObj.setExpireDate(Utility.stringToDate(diseaseExpDate[i]));
	        		diseaseObj.setSourceBuy(diseaseSourceBuy[i]);
	        		diseaseObj.setUseDate(Utility.stringToDate(diseaseUseDate[i]));
	        		diseaseObj.setUseRate1(diseaseUseRate1[i]);
	        		diseaseObj.setUseRate2(diseaseUseRate2[i]);
	        		diseaseObj.setResult(diseaseResult[i].equals("ผ่าน")?"P":"F");
	        		diseaseObj.setRemark(diseaseRemark[i]);
	        		diseaseObj.setChecker(diseaseCheckBy[i]);
	        		diseaseObj.setLandCheck(landCheck);
	        		list.add(diseaseObj);
	        	}
			}
		}
        
		return list;
	}
	
	private List<LandCheckHarvest> saveHarvest(String mode, LandCheckForm eform, LandCheck landCheck)
	{
		List<LandCheckHarvest> list = new ArrayList<LandCheckHarvest>();
		long[] harvestId = eform.getHarvestId();
		String[] harvestDain = eform.getHarvestDain();
		String[] harvestDainRemark = eform.getHarvestDainRemark();
		String[] harvestQuality = eform.getHarvestQuality();
		String[] harvestFarmStatus = eform.getHarvestFarmStatus();
		String[] harvester = eform.getHarvester();
		String[] harvestCleaning = eform.getHarvestCleaning();
		String[] harvestPackaging = eform.getHarvestPackaging();
		String[] harvestMoving = eform.getHarvestMoving();
		String[] harvestObservers = eform.getHarvestObservers();
		String[] harvestObserversDate = eform.getHarvestObserversDate();
		String[] harvestDainDate = eform.getHarvestDainDate();
		String[] harvestQualityDate = eform.getHarvestQualityDate();
		String[] harvestFarmStatusDate = eform.getHarvestFarmStatusDate();
		String[] harvesterDate = eform.getHarvesterDate();
		String[] harvestCleaningDate = eform.getHarvestCleaningDate();
		String[] harvestPackagingDate = eform.getHarvestPackagingDate();
		String[] harvestMovingDate = eform.getHarvestMovingDate();
		double[] harvestTotal = eform.getHarvestTotal();
		double[] harvestKeep = eform.getHarvestKeep();
		double[] harvestSell = eform.getHarvestSell();
		double[] harvestSalePrice = eform.getHarvestSalePrice();
		String[] harvestResult = eform.getHarvestResult();
		String[] harvestCheckBy = eform.getHarvestCheckBy();
		String[] harvestRemark = eform.getHarvestRemark();
		
		if(mode.equals("Edit")) {
			LandCheckHarvestHome home = new LandCheckHarvestHome();
			String[] delHarId = eform.getDelHarId();
			if(delHarId!=null) {
				for(int i=0;i<delHarId.length;i++) {
					home.deleteLandCheckHarvest(Long.parseLong(delHarId[i]));
				}
			}
	        if(harvestQuality!=null && harvestQualityDate!=null){
	        	for(int i=0;i<harvestQuality.length;i++) {
	        		LandCheckHarvest harvestObj = null;
	        		if(harvestId[i] == 0) {
	        			harvestObj = new LandCheckHarvest();
	        		} else {
	        			harvestObj = home.findByLandCheckHarvestId(harvestId[i]);
	        		}
	        		harvestObj.setLandCheckId(landCheck.getLandCheckId());
	        		harvestObj.setSeq(i+1);
	        		harvestObj.setDain(harvestDain[i]);
	        		harvestObj.setDainRemark(harvestDainRemark[i]);
	        		harvestObj.setHarvestQuality(harvestQuality[i]);
	        		harvestObj.setFarmStatus(harvestFarmStatus[i]);
	        		harvestObj.setHarvester(harvester[i]);
	        		harvestObj.setCleaning(harvestCleaning[i]);
	        		harvestObj.setPackaging(harvestPackaging[i]);
	        		harvestObj.setMoving(harvestMoving[i]);
	        		harvestObj.setObservers(harvestObservers[i]);
	        		harvestObj.setObserveDate(Utility.stringToDate(harvestObserversDate[i]));
	        		harvestObj.setDainDate(Utility.stringToDate(harvestDainDate[i]));
	        		harvestObj.setHarvestQualityDate(Utility.stringToDate(harvestQualityDate[i]));
	        		harvestObj.setFarmStatusDate(Utility.stringToDate(harvestFarmStatusDate[i]));
	        		harvestObj.setHarvesterDate(Utility.stringToDate(harvesterDate[i]));
	        		harvestObj.setCleaningDate(Utility.stringToDate(harvestCleaningDate[i]));
	        		harvestObj.setPackagingDate(Utility.stringToDate(harvestPackagingDate[i]));
	        		harvestObj.setMovingDate(Utility.stringToDate(harvestMovingDate[i]));
	        		harvestObj.setTotalHarvest(harvestTotal[i]);
	        		harvestObj.setKeepHarvest(harvestKeep[i]);
	        		harvestObj.setSell(harvestSell[i]);
	        		harvestObj.setSalePrice(harvestSalePrice[i]);
	        		harvestObj.setResult(harvestResult[i].equals("ผ่าน")?"P":"F");
	        		harvestObj.setChecker(harvestCheckBy[i]);
	        		harvestObj.setRemark(harvestRemark[i]);
	        		
	        		home.saveOrUpdate(harvestObj);
	        	}
	        }
		} else {
			if((harvestQuality !=null && harvestQuality.length>0) &&  (harvestQualityDate !=null && harvestQualityDate.length>0)){
				for(int i=0;i<harvestQuality.length;i++) {
					LandCheckHarvest harvestObj = new LandCheckHarvest();
	        		harvestObj.setSeq(i+1);
	        		harvestObj.setDain(harvestDain[i]);
	        		harvestObj.setDainRemark(harvestDainRemark[i]);
	        		harvestObj.setHarvestQuality(harvestQuality[i]);
	        		harvestObj.setFarmStatus(harvestFarmStatus[i]);
	        		harvestObj.setHarvester(harvester[i]);
	        		harvestObj.setCleaning(harvestCleaning[i]);
	        		harvestObj.setPackaging(harvestPackaging[i]);
	        		harvestObj.setMoving(harvestMoving[i]);
	        		harvestObj.setObservers(harvestObservers[i]);
	        		harvestObj.setObserveDate(Utility.stringToDate(harvestObserversDate[i]));
	        		harvestObj.setDainDate(Utility.stringToDate(harvestDainDate[i]));
	        		harvestObj.setHarvestQualityDate(Utility.stringToDate(harvestQualityDate[i]));
	        		harvestObj.setFarmStatusDate(Utility.stringToDate(harvestFarmStatusDate[i]));
	        		harvestObj.setHarvesterDate(Utility.stringToDate(harvesterDate[i]));
	        		harvestObj.setCleaningDate(Utility.stringToDate(harvestCleaningDate[i]));
	        		harvestObj.setPackagingDate(Utility.stringToDate(harvestPackagingDate[i]));
	        		harvestObj.setMovingDate(Utility.stringToDate(harvestMovingDate[i]));
	        		harvestObj.setTotalHarvest(harvestTotal[i]);
	        		harvestObj.setKeepHarvest(harvestKeep[i]);
	        		harvestObj.setSell(harvestSell[i]);
	        		harvestObj.setSalePrice(harvestSalePrice[i]);
	        		harvestObj.setResult(harvestResult[i].equals("ผ่าน")?"P":"F");
	        		harvestObj.setChecker(harvestCheckBy[i]);
	        		harvestObj.setRemark(harvestRemark[i]);
					harvestObj.setLandCheck(landCheck);
	        		list.add(harvestObj);
	        	}
			}
		}
        
		return list;
	}
	
	private Blob resizeImage(BufferedImage img)
	{
		Blob blob = null;
		try{
			int width = img.getWidth(), height = img.getHeight();
			int maxwidth = 1024, maxheight = 683;
			float t_width = 0, t_height = 0;
			
			if(width>maxwidth || height>maxheight){
		        if(width != height)
		        {
		            if(width > height)
		            {
		            	t_width = maxwidth;
		            	t_height = ((t_width * height)/width);
		                //fix height
		                if(t_height > maxheight)
		                {
		                	t_height = maxheight;
		                    t_width = ((width * t_height)/height);
		                }
		            }
		            else
		            {
		            	t_height = maxheight;
		                t_width = ((width * t_height)/height);
		                //fix width
		                if(t_width > maxwidth)
		                {
		                	t_width = maxwidth;
		                	t_height = ((t_width * height)/width);
		                }
		            }
		        } else
		        	t_width = t_height = Math.min(maxheight, maxwidth);
			} else {
				t_width = width;
				t_height = height;
			}
	        
			int newW = (int)t_width;
			int newH = (int)t_height;
			Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		    BufferedImage buffImg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
		    Graphics2D g2d = buffImg.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();
		    
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(buffImg, "jpg", baos);
		    blob = new SerialBlob(baos.toByteArray());
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e, e);
		} finally{
		}
	    return blob;
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		LandCheckForm eform = (LandCheckForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		List<Coordinates> getCoordinatesList = new ArrayList<Coordinates>();
		LandCheck landCheck = new LandCheck();
		try{
			sf.getCurrentSession().beginTransaction();
			LandCheckHome home = new LandCheckHome();
			CoordinatesHome cHome = new CoordinatesHome();
			MixedBreedTypeHome mHome = new MixedBreedTypeHome();
			MixedBreedTypeChildHome mcHome = new MixedBreedTypeChildHome();
			CheckingDiseaseHome cdHome = new CheckingDiseaseHome();
			CheckingDiseaseChildHome cdcHome = new CheckingDiseaseChildHome();
			landCheck = home.findByLandCheckId(eform.getLandCheckId());
			
			eform.loadFromBean(landCheck);
			
			Calendar checkDate = Calendar.getInstance();
			checkDate.setTime(landCheck.getCheckDate());
			checkDate.set(checkDate.YEAR, checkDate.get(checkDate.YEAR)+543);
			eform.setCheckDate(formatter.format(checkDate.getTime()));
			String[] time = landCheck.getCheckTime().split(":");
			eform.setCheckHour(time[0]);
			eform.setCheckMinute(time[1]);
			
			//image json
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
				public boolean apply(Object source,String name,Object value){
					if("imageId".equals(name)|| "landCheckId".equals(name) || "contentType".equals(name) 
		            		  || "seqIndex".equals(name) || "seq".equals(name) || "msg".equals(name)){
						return false;
					}
					return true;
				}
			});
			List jsonObjects = new ArrayList();
			List imageList= new ArrayList();
			long newIndex=0;
			int seqIndx=0;

			List<LandCheckImages> getLandCheckImagesList = landCheck.getLandCheckImages();
			if(getLandCheckImagesList!=null && getLandCheckImagesList.size()>0) {
            	for(LandCheckImages object : getLandCheckImagesList){
    				if(object!=null){
    					++seqIndx;
    					newIndex = Long.parseLong(dfTimeMillisec.format(new Date()))+seqIndx;
    					object.setSeqIndex(newIndex);
    					jsonObjects.add(JSONSerializer.toJSON(object,jsonConfig));					
    					imageList.add(object);
    				}
    			}
    			eform.setImageData(JSONArray.fromObject(jsonObjects,jsonConfig).toString());
			}
			session.setAttribute("imageList", getLandCheckImagesList);
			
			getCoordinatesList = cHome.findByCoordinatesKey(eform.getPlantYear(), eform.getPlantNo(), eform.getIdCard(), eform.getTypeId(), eform.getBreedTypeId(), eform.getDocNo());
			
			session.setAttribute("coordinatesList", getCoordinatesList);
			session.setAttribute("mixedBreedTypeList", mHome.findAll());
			session.setAttribute("mixedBreedTypeChildList", mcHome.findAll());
			session.setAttribute("checkingDiseaseList", cdHome.findAll());
			session.setAttribute("checkingDiseaseChildList", cdcHome.findAll());
			session.setAttribute("landCheckData", landCheck);
		}catch (Exception e) {
			eform.setMsg(Utility.get("SAVE_ACCESSFAIL"));//eform.setMsg("เกิดการดึงข้อมูลผิดพลาด");
			e.printStackTrace();
			log.error(e,e);
		}finally{
			sf.getCurrentSession().close();
		}
		return mapping.findForward("landCheck");
	}

	public ActionForward reload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{			
		LandCheckForm eform = (LandCheckForm)form;	
		 HttpSession session =  request.getSession(true);
		try{			
				// case save caption image  and reload main window with ajax
				//image json
				int seqIndx=0;
				long newIndex = 0;
				
				List imgList=(List)session.getAttribute("imageList"); 
				if(eform.getCmd().equals("reloadClose")){
					imgList.remove(imgList.size()-1);
				}
				//==============================================================================================================
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
					public boolean apply(Object source,String name,Object value){
						if("imageId".equals(name)|| "landCheckId".equals(name) || "contentType".equals(name) 
			            		  || "seqIndex".equals(name) || "seq".equals(name) || "msg".equals(name)){
							return false;
						}
						return true;
					}
				});
				List jsonObjects = new ArrayList();
				
				if(imgList!=null && imgList.size()>0)
				{
					for(int i=0;i<imgList.size();i++){
						LandCheckImages object =(LandCheckImages)imgList.get(i);
						if(object!=null){
							 if(object.getMsg()!=null && object.getMsg().trim().length()>0){
								 object.setMsg("ไม่มี คำบรรยายรูปภาพ");									
							}				
							 ++seqIndx;
							 newIndex = Long.parseLong(dfTimeMillisec.format(new Date()))+seqIndx;
							 object.setSeqIndex(newIndex);
							jsonObjects.add(JSONSerializer.toJSON(object,jsonConfig));
						}
					}
				}
				eform.setImageData(JSONArray.fromObject(jsonObjects,jsonConfig).toString());			
				
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();			
				writer.print(eform.getImageData());			
		}catch (Exception ex) {
				log.error(ex, ex);
				eform.setMsg(ex.toString());
		}finally{
		}
		return null;
	}

	public ActionForward setValueSession(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{	
		LandCheckForm eform = (LandCheckForm)form;
		HttpSession session = request.getSession();
		String result="";
		try{			
			if(eform.getImageData()!=null&&eform.getImageData().trim().length()>0)
			{
				session.setAttribute("imageData", eform.getImageData());
				result="ok";
			}
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();			
			writer.print(result);	
		}catch (Exception ex) {
			log.error(ex, ex);
			eform.setMsg(ex.toString());
		}finally{
		}
		return null;
	}

	public ActionForward detailImage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{			
		LandCheckForm eform = (LandCheckForm)form;	
		HttpSession session =  request.getSession(true);
		List imageList=new ArrayList();
		List imageOldList=(List)session.getAttribute("imageList");
		LandCheckImages imageTemp=new LandCheckImages();
		try{		
			
			HashMap map = new HashMap();
			if(imageOldList!=null && imageOldList.size()>0)
			{
				for(int i=0;i<imageOldList.size();i++)
				{
					LandCheckImages image =(LandCheckImages)imageOldList.get(i);
					if(image !=null){
						map.put(image.getSeq(), image);
					}
				}
			}
			//end set ==============================================
			if(session.getAttribute("imageData")!=null) ///get session to json
			{
				eform.setImageData((String)session.getAttribute("imageData"));
			}
			Object[] objImageDetail = {};
			objImageDetail = JSONArray.toCollection(JSONArray.fromObject(eform.getImageData()),LandCheckImages.class).toArray(); //convert json to object
			if(objImageDetail !=null && objImageDetail.length>0){			
				for(int i=0;i<objImageDetail.length;i++){
					LandCheckImages image = (LandCheckImages)objImageDetail[i];						
					if(image!=null)
					{
						LandCheckImages imageOld=  (LandCheckImages)map.get(image.getSeq());						
						if(eform.getIndex()==i) //รูปภาพที่ถูกเลือก
						 {
								 eform.setImageId(String.valueOf(image.getImageId()) );
								 imageTemp=image;
						 }
						
						image.setImageBlob(imageOld==null?null: imageOld.getImageBlob());//keep data t o session
						image.setContentType(imageOld==null?"": StringUtil.beString(imageOld.getContentType()) ); //keep data t o session						
						image.setSeq(i+1);/// reset seq ===================================

						imageList.add(image);
					}
				}
			}
			
			// ========================================================================================
			session.setAttribute("imageList", imageList);
			request.setAttribute("LandCheckForm", eform);
			request.setAttribute("ImageDetail", imageTemp);
		} catch (Exception ex) {
			log.error(ex, ex);
			ex.printStackTrace();
		} finally{
		}
		return mapping.findForward("detailImage");
	}

	public ActionForward showImagePopup(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		LandCheckForm eform = (LandCheckForm)form;
		HttpSession session = request.getSession();
		boolean check=true;
		try{
			List imageList = (List)session.getAttribute("imageList");
			if(imageList==null)
				imageList = new ArrayList();
			
			LandCheckImages image =new LandCheckImages();
			if(eform.getViewImageStatus()!=null && eform.getViewImageStatus().equals("view") && imageList!=null && imageList.size()>0)
			{
				image=(LandCheckImages)imageList.get(imageList.size()-1);
				check=false;
			}else{
				image.setSeq(imageList.size()+1);
			}
				image.setImageBlob( Hibernate.createBlob(eform.getImageFormFile().getInputStream()));		
				//image.setImageName("");
				image.setContentType(eform.getImageFormFile().getContentType());
			if(check)
			{
				imageList.add(image);	
			}
			eform.setViewImageStatus("view");
			session.setAttribute("imageList",imageList);
		}catch (Exception ex) {
			log.error(ex,ex);
		}finally{
		}		
		return mapping.findForward("detailImage");
	}
	
	public ActionForward getImage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{			
		// get  image in popup 
		HttpSession session = request.getSession();
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader ("Expires", 0);
		try{
			List imageList=(List)session.getAttribute("imageList");	
			String contentType = ""; 
			Blob image = null;
			if(imageList!=null && imageList.size()>0) // show image in popup so get last object in List
			{
				LandCheckImages temp= (LandCheckImages)imageList.get(imageList.size()-1);
				image = temp.getImageBlob();
				contentType = StringUtil.beString(temp.getContentType());
				if(image!=null){
					if (contentType!=null) {
	                      response.setContentType(contentType);
	                }
					int len = 1;
	                byte buf[] = new byte[4096];
	                
	                InputStream in = image.getBinaryStream();
	                while((len=in.read(buf))>0) {
	                   response.getOutputStream().write(buf, 0, len);
	                }
	                response.getOutputStream().close();                    
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		} finally{
		}
		return null;
	}

	public ActionForward checkSize(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{			
		LandCheckForm eform = (LandCheckForm)form;	
		HttpSession session =  request.getSession(true);
		try{	
				String error="";
				List imList= (List)session.getAttribute("imageList");
				if(imList!=null && imList.size()>0)
				{
					LandCheckImages image=(LandCheckImages) imList.get(imList.size()-1);
					if(image!=null){
						Blob img = (Blob)image.getImageBlob();
						if(img!=null && img.length()>10485760)
						{
							error="over weight";
						}
					}
				}
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();			
				writer.print(error);	
		}catch (Exception ex) {
				log.error(ex, ex);
				eform.setMsg(ex.toString());
		}finally{
		}
		return null;
	}
	
	public ActionForward saveDetailImage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{			
		LandCheckForm eform = (LandCheckForm)form;	
		LandCheckImages image = new LandCheckImages();
		HttpSession session =  request.getSession(true);
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader ("Expires", 0);
		List imageList=(List)session.getAttribute("imageList");
		try{		
			if(imageList !=null && imageList.size()>0){
				// save data to sesstion
				for(int i=0;i<imageList.size();i++){
					
				}
			}
			eform.setViewImageStatus("success");
			request.setAttribute("LandCheckForm", eform);
			request.setAttribute("ImageDetail", image);
		} catch (Exception ex) {
			log.error(ex, ex);
		} finally{
		}
		return mapping.findForward("detailImage");
	}
	
	public ActionForward showImage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		LandCheckForm eform = (LandCheckForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();			
		LandCheckImagesHome home = new LandCheckImagesHome();
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader ("Expires", 0);
		HttpSession session = request.getSession();
		try{
			sf.getCurrentSession().beginTransaction();
			if(eform.getIndex()==-1){
					List imageList=(List)session.getAttribute("imageList");
					if(imageList!=null && imageList.size()>0)
					{
						for(int i=0;i<imageList.size();i++)
						{
							LandCheckImages image= (LandCheckImages)imageList.get(i);
							if(image!=null && image.getSeq()==Integer.parseInt( eform.getImageId()))
							{
								String contentType = "";
								Blob blobImg =  image.getImageBlob();
								contentType = image.getContentType();
								if (contentType!=null) {
			                        response.setContentType(contentType);
								}
								int len = 1;
								byte buf[] = new byte[4096];
								InputStream in = blobImg.getBinaryStream();
								while((len=in.read(buf))>0) {
									response.getOutputStream().write(buf, 0, len);
								}
								response.getOutputStream().close();
							
								break;
							}
						}
					}
			}else{
				LandCheckImages image = home.findByImageId(Long.parseLong(eform.getImageId()));
				if(image != null){
					String contentType = "";
					Blob blobImg =  image.getImageBlob();
					contentType = image.getContentType();
					if (contentType!=null) {
                        response.setContentType(contentType);
					}
					int len = 1;
					byte buf[] = new byte[4096];
					InputStream in = blobImg.getBinaryStream();
					while((len=in.read(buf))>0) {
						response.getOutputStream().write(buf, 0, len);
					}
					response.getOutputStream().close();
				}
			}
		}catch (Exception ex) {
			log.error(ex,ex);
		}finally{
			sf.getCurrentSession().close();
		}		
		return null;
	}

	public ActionForward getImageObject(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{			
		// get  image in popup 
		LandCheckForm eform = (LandCheckForm)form;
		HttpSession session = request.getSession();
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader ("Expires", 0);

		try{
			List imageList=(List)session.getAttribute("imageList");	
			String contentType = ""; 
			Blob image = null;
			if(imageList!=null && imageList.size()>0 && Integer.parseInt(eform.getImageId()) >0) // show image in popup so get last object in List
			{
				LandCheckImages temp= (LandCheckImages)imageList.get(Integer.parseInt(eform.getImageId())-1);
				image = temp.getImageBlob();
				contentType = StringUtil.beString(temp.getContentType());
				if(image!=null){
					if (contentType!=null) {
	                      response.setContentType(contentType);
	                }
					int len = 1;
	                byte buf[] = new byte[4096];
	                
	                InputStream in = image.getBinaryStream();
	                while((len=in.read(buf))>0) {
	                   response.getOutputStream().write(buf, 0, len);
	                }
	                response.getOutputStream().close();   
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		} finally{
		}
		return null;
	}

	public ActionForward deleteImage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{			
		LandCheckForm eform = (LandCheckForm)form;	
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader ("Expires", 0);
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		LandCheckImagesHome home = new LandCheckImagesHome();
		LandCheckImages image = new LandCheckImages();
		try{
			sf.getCurrentSession().beginTransaction();			
			if(eform.getImageId()!= null && !eform.getImageId().trim().equals("") && Long.parseLong(eform.getImageId())>0)
			{
				image = home.findByImageId(Long.parseLong(eform.getImageId()));//keep value
				//contentHome.deleteImagebyImageId(image.getImageId());//delete image from ImageContent table
				home.deleteImagebyImageId(image.getImageId());//delete image from Image table 
				sf.getCurrentSession().getTransaction().commit();
				
				sf.getCurrentSession().beginTransaction();		
				List<LandCheckImages> lcImages = home.findImageByLandCheckId(eform.getLandCheckId());
				
				
				//set ลำดับ json ให้ถูกต้อง============================================================================
				session.setAttribute("imageList", lcImages);
				List imageOldList =(List)session.getAttribute("imageList");
				List imageList=new ArrayList();
				HashMap map=new HashMap();
				if(imageOldList!=null && imageOldList.size()>0)
				{
					for(int i=0;i<imageOldList.size();i++)
					{
						LandCheckImages im =(LandCheckImages)imageOldList.get(i);
						map.put(im.getSeq(), im);
					}
				}
				
				/*Object[] objImageDetail = {};
				objImageDetail = JSONArray.toCollection(JSONArray.fromObject(eform.getImageData()),LandCheckImages.class).toArray();//convert json to object
				if(objImageDetail !=null && objImageDetail.length>0){			
					for(int i=0;i<objImageDetail.length;i++){
						LandCheckImages im = (LandCheckImages)objImageDetail[i];						
						if(im!=null)
						{
							LandCheckImages imageOld=  (LandCheckImages)map.get(im.getSeq());
							im.setImageBlob(imageOld==null?null: imageOld.getImageBlob());//keep data t o session
							im.setContentType(imageOld==null?"": StringUtil.beString(imageOld.getContentType()) );//keep data t o session
							im.setSeq(i+1);//reset seq ===================================
							imageList.add(im);
						}
					}
				}	
				*/
				
				
				if(lcImages !=null && lcImages.size()>0){
					for(int n=0; n<lcImages.size(); n++){
						LandCheckImages landImg = (LandCheckImages)lcImages.get(n);
						landImg.setSeq(n+1);//reset seq 
						imageList.add(landImg);
					}
				}
				
				
				
				session.setAttribute("imageList", imageList);
				List imgList=(List)session.getAttribute("imageList");
				if( eform.getImageId().trim().length() >0 && Integer.parseInt(eform.getImageId())>0 && imgList!=null && imgList.size()>0) //imageid=seq เพราะยังไม่มีimageId เกิดขึ้นในdb
				{
					for(int i=0;i<imgList.size();i++)
					{
						LandCheckImages im=(LandCheckImages)imgList.get(i);
						if(im!=null && im.getImageId()== Integer.parseInt(eform.getImageId()))
						{
							imgList.remove(im);
						}						
					}					
				}
				long newIndex=0;
				int seqIndx=0;
				if(imgList!=null && imgList.size()>0)
				{
					for(int i=0;i<imgList.size();i++)
					{
						LandCheckImages im =(LandCheckImages)imgList.get(i);
						im.setSeq(i+1);
						++seqIndx;
						newIndex = Long.parseLong(dfTimeMillisec.format(new Date()))+seqIndx;
						im.setSeqIndex(newIndex);
					}
				}
				
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
					public boolean apply(Object source,String name,Object value){
						if("imageId".equals(name)|| "landCheckId".equals(name) || "contentType".equals(name) 
			            		  || "seqIndex".equals(name) || "seq".equals(name) || "msg".equals(name)){
							return false;
						}
						return true;
					}
				});
				List jsonObjects = new ArrayList();
				
				if(imgList!=null && imgList.size()>0)
				{
					for(int i=0;i<imgList.size();i++){
						LandCheckImages object =(LandCheckImages)imgList.get(i);
						if(object!=null){
							if(object.getMsg()!=null && object.getMsg().trim().length()>0){
								object.setMsg("ไม่มี คำบรรยายรูปภาพ");								
							}
							jsonObjects.add(JSONSerializer.toJSON(object,jsonConfig));
						}
					}
				}
				eform.setImageData(JSONArray.fromObject(jsonObjects,jsonConfig).toString());			
				
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();			
				writer.print(eform.getImageData());
			}
		}catch (Exception ex) {
			log.error(ex, ex);
			sf.getCurrentSession().getTransaction().rollback();
			eform.setMsg("ลบรูปภาพไม่สำเร็จ " + ex.getMessage());	
		}finally{
			sf.getCurrentSession().close();
		}
		return null;
	}

	public ActionForward deleteImageObject(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{			
		LandCheckForm eform = (LandCheckForm)form;	
		HttpSession session =  request.getSession(true);
		try{			
			//case delete image session with ajax
			//image json
			//set ลำดับ json ให้ถูกต้อง============================================================================
			List imageOldList =(List)session.getAttribute("imageList");
			List imageList=new ArrayList();
			HashMap map=new HashMap();
			if(imageOldList!=null && imageOldList.size()>0)
			{
				for(int i=0;i<imageOldList.size();i++)
				{
					LandCheckImages image =(LandCheckImages)imageOldList.get(i);
					map.put(image.getSeq(), image);
				}
			}
			Object[] objImageDetail = {};
			objImageDetail = JSONArray.toCollection(JSONArray.fromObject(eform.getImageData()),LandCheckImages.class).toArray(); //convert json to object
			if(objImageDetail !=null && objImageDetail.length>0){			
				for(int i=0;i<objImageDetail.length;i++){
					LandCheckImages image = (LandCheckImages)objImageDetail[i];						
					if(image!=null)
					{
						LandCheckImages imageOld=  (LandCheckImages)map.get(image.getSeq());
						image.setImageBlob(imageOld==null?null: imageOld.getImageBlob()); //keep data t o session
						image.setContentType(imageOld==null?"": StringUtil.beString(imageOld.getContentType()) ); //keep data t o session
						
						image.setSeq(i+1); //reset seq ===================================
						
						
						
						imageList.add(image);
						
					}
				}
			}	
			
			session.setAttribute("imageList", imageList);
			//end set ลำดับ json==========================================================================================
			List imgList=(List)session.getAttribute("imageList");
			if( eform.getImageId().trim().length() >0 && Integer.parseInt(eform.getImageId())>0 && imgList!=null && imgList.size()>0) //imageid=seq เพราะยังไม่มีimageId เกิดขึ้นในdb
			{
				for(int i=0;i<imgList.size();i++)
				{
					LandCheckImages im=(LandCheckImages)imgList.get(i);
					if(im!=null && im.getSeq()== Integer.parseInt(eform.getImageId()))
					{
						imgList.remove(im);
					}						
				}					
			}
			long newIndex=0;
			int seqIndx=0;
			if(imgList!=null && imgList.size()>0)
			{
				for(int i=0;i<imgList.size();i++)
				{
					LandCheckImages image =(LandCheckImages)imgList.get(i);
					++seqIndx;
					newIndex = Long.parseLong(dfTimeMillisec.format(new Date()))+seqIndx;
					image.setSeq(i+1);
					image.setSeqIndex(newIndex);
				}
			}
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
				public boolean apply(Object source,String name,Object value){
					if("imageId".equals(name)|| "landCheckId".equals(name) || "contentType".equals(name) 
		            		  || "seqIndex".equals(name) || "seq".equals(name) || "msg".equals(name)){
						return false;
					}
					return true;
				}
			});
			List jsonObjects = new ArrayList();
			
			if(imgList!=null && imgList.size()>0)
			{
				for(int i=0;i<imgList.size();i++){
					LandCheckImages object =(LandCheckImages)imgList.get(i);
					if(object!=null){
						if(object.getMsg()!=null && object.getMsg().trim().length()>0){
							object.setMsg("ไม่มี คำบรรยายรูปภาพ");								
						}				
						
						jsonObjects.add(JSONSerializer.toJSON(object,jsonConfig));
					}
				}
			}
			eform.setImageData(JSONArray.fromObject(jsonObjects,jsonConfig).toString());			
			
			response.setContentType("texthtml;charset=UTF-8");
			PrintWriter writer = response.getWriter();			
			writer.print(eform.getImageData());			
		}catch (Exception ex) {
			log.error(ex, ex);
			eform.setMsg(ex.toString());
		}finally{
		}
		return null;
	}
	
	//Ajax Method
	public ActionForward getLandRightInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		LandCheckForm eform = (LandCheckForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
		
			List landRightBoxList = new ArrayList();
			LandRightHome landRightHome = new LandRightHome();
			landRightBoxList = landRightHome.findLandRightByCondition(eform.getPlantYear(), eform.getIdCard(), eform.getPlantNo(), userLogin.getFarmerGroupId());

			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{});   

		    String result = JSONArray.fromObject(landRightBoxList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", " ");
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
	
	//Ajax Method
	public ActionForward getCoordinate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		LandCheckForm eform = (LandCheckForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
		
			List<Coordinates> getCoordinatesList = new ArrayList<Coordinates>();
			CoordinatesHome cHome = new CoordinatesHome();
			getCoordinatesList = cHome.findByCoordinatesKey(eform.getPlantYear(), eform.getPlantNo(), eform.getIdCard(), eform.getTypeId(), eform.getBreedTypeId(), eform.getDocNo());
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{});   

		    String result = JSONArray.fromObject(getCoordinatesList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", " ");
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
	
	//Ajax Method
	public ActionForward getMixedBreed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		LandCheckForm eform = (LandCheckForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			CheckPeriodHome home = new CheckPeriodHome();
			CheckPeriod checkPeriod = null;
			if(eform.getCheckPeriod() > 0) {
				checkPeriod = home.findByCheckPeriod(eform.getCheckPeriod());
			}
			Set<MixedBreedType> mixedBreedTypeSet = checkPeriod.getMixedBreedType();
			List<MixedBreedType> mixedBreedTypeList = new ArrayList<MixedBreedType>(mixedBreedTypeSet);
			Collections.sort(mixedBreedTypeList, new Comparator<MixedBreedType>() {
				public int compare(MixedBreedType typeListOne, MixedBreedType typeListTwo) {
					//use instanceof to verify the references are indeed of the type in question
					return (typeListOne.getDescription().compareTo(typeListTwo.getDescription()));
				}
			});
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{});   

		    String result = JSONArray.fromObject(mixedBreedTypeList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", " ");
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
	
	//Ajax Method
	public ActionForward getMixedBreedType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		LandCheckForm eform = (LandCheckForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			MixedBreedTypeHome home = new MixedBreedTypeHome();
			MixedBreedType mixedBreedType = null;
			if(eform.getMixedBreedType()[0] > 0) {
				mixedBreedType = home.findByMixedBreedTypeId(eform.getMixedBreedType()[0]);
			}
			Set<MixedBreedTypeChild> mixedBreedTypeChildSet = mixedBreedType.getMixedBreedTypeChild();
			List<MixedBreedTypeChild> mixedBreedTypeChildList = new ArrayList<MixedBreedTypeChild>(mixedBreedTypeChildSet);
			Collections.sort(mixedBreedTypeChildList, new Comparator<MixedBreedTypeChild>() {
				public int compare(MixedBreedTypeChild childListOne, MixedBreedTypeChild childListTwo) {
					//use instanceof to verify the references are indeed of the type in question
					if(childListOne.getChildId() <  childListTwo.getChildId()) return -1;
					if(childListOne.getChildId() == childListTwo.getChildId()) return 0;
					return 1;
				}
			}); 
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{});   

		    String result = JSONArray.fromObject(mixedBreedTypeChildList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", " ");
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
	
	//Ajax Method
	public ActionForward getDiseaseChild(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		LandCheckForm eform = (LandCheckForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			CheckingDiseaseChildHome home = new CheckingDiseaseChildHome();
			List<CheckingDiseaseChild> checkingDiseaseList = null;
			if(eform.getDiseaseChecking()[0] > 0) {
				checkingDiseaseList = home.findByCheckingDiseaseId(eform.getDiseaseChecking()[0]);
			}
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{});   

		    String result = JSONArray.fromObject(checkingDiseaseList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", " ");
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
	
	//Ajax Method
	public ActionForward getDiseaseType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		LandCheckForm eform = (LandCheckForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			DiseaseTypeHome home = new DiseaseTypeHome();
			List<DiseaseType> diseaseTypeList = null;
			if(eform.getDiseaseChildId()[0] > 0) {
				diseaseTypeList = home.findByDiseaseChildId(eform.getDiseaseChildId()[0]);
			}
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{});   

		    String result = JSONArray.fromObject(diseaseTypeList, jsonConfig).toString();
		    result = result.trim().replaceAll(" ", " ");
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
