package com.wsnweb.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.SessionFactory;

import com.dcs.form.PdfForm.PdfForm;
import com.dcs.hibernate.HibernateHome;
import com.wsndata.data.Asset;
import com.wsndata.data.AssetDetail;
import com.wsndata.data.Bank;
import com.wsndata.data.BankBranch;
import com.wsndata.data.Branch;
import com.wsndata.data.BreedGroup;
import com.wsndata.data.BreedType;
import com.wsndata.data.Buyer;
import com.wsndata.data.ChemicalType;
import com.wsndata.data.CloseDue;
import com.wsndata.data.Coordinates;
import com.wsndata.data.Cost;
import com.wsndata.data.CostDetail;
import com.wsndata.data.Farmer;
import com.wsndata.data.FarmerFamily;
import com.wsndata.data.FarmerGroup;
import com.wsndata.data.FarmerGroupChild;
import com.wsndata.data.FarmerGroupFarmer;
import com.wsndata.data.HarvestDetail;
import com.wsndata.data.IrrigationArea;
import com.wsndata.data.LandCheck;
import com.wsndata.data.LandRight;
import com.wsndata.data.LandRightType;
import com.wsndata.data.LandStatus;
import com.wsndata.data.LandType;
import com.wsndata.data.ManureType;
import com.wsndata.data.Plant;
import com.wsndata.data.PlantChemical;
import com.wsndata.data.PlantDetail;
import com.wsndata.data.PlantManure;
import com.wsndata.data.PlantMethod;
import com.wsndata.data.Prefix;
import com.wsndata.data.PrepareArea;
import com.wsndata.data.PrepareManure;
import com.wsndata.data.Province;
import com.wsndata.data.ProvinceDistrict;
import com.wsndata.data.Region;
import com.wsndata.data.SeedSelect;
import com.wsndata.data.SellingDetail;
import com.wsndata.data.SubDistrict;
import com.wsndata.data.User;
import com.wsndata.data.UserProvince;
import com.wsndata.data.UserRegion;
import com.wsndata.dbaccess.AssetHome;
import com.wsndata.dbaccess.BankBranchHome;
import com.wsndata.dbaccess.BankHome;
import com.wsndata.dbaccess.BranchHome;
import com.wsndata.dbaccess.BreedGroupHome;
import com.wsndata.dbaccess.BreedTypeHome;
import com.wsndata.dbaccess.BuyerHome;
import com.wsndata.dbaccess.ChemicalTypeHome;
import com.wsndata.dbaccess.CloseDueHome;
import com.wsndata.dbaccess.CoordinatesHome;
import com.wsndata.dbaccess.CostHome;
import com.wsndata.dbaccess.DistrictHome;
import com.wsndata.dbaccess.FarmerGroupChildHome;
import com.wsndata.dbaccess.FarmerGroupFarmerHome;
import com.wsndata.dbaccess.FarmerGroupHome;
import com.wsndata.dbaccess.FarmerHome;
import com.wsndata.dbaccess.IrrigationAreaHome;
import com.wsndata.dbaccess.LandCheckHome;
import com.wsndata.dbaccess.LandRightHome;
import com.wsndata.dbaccess.LandRightTypeHome;
import com.wsndata.dbaccess.LandStatusHome;
import com.wsndata.dbaccess.LandTypeHome;
import com.wsndata.dbaccess.ManureTypeHome;
import com.wsndata.dbaccess.PlantHome;
import com.wsndata.dbaccess.PlantMethodHome;
import com.wsndata.dbaccess.PrefixHome;
import com.wsndata.dbaccess.PrepareAreaHome;
import com.wsndata.dbaccess.PrepareManureHome;
import com.wsndata.dbaccess.ProvinceDistrictHome;
import com.wsndata.dbaccess.ProvinceHome;
import com.wsndata.dbaccess.RegionHome;
import com.wsndata.dbaccess.SeedSelectHome;
import com.wsndata.dbaccess.SubDistrictHome;
import com.wsnweb.form.PlantForm;
import com.wsnweb.form.PlantListForm;
import com.wsnweb.util.Utility;

public class PlantAction extends Action {

	private static final Logger log = Logger.getLogger(PlantAction.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
	private double SUMTOTAL;
	private boolean isCheckSum = false;
	private int COUNTING;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HttpSession session = request.getSession();
		PlantForm eform = (PlantForm)form;
		User userLogin = (User)request.getSession().getAttribute("userLogin");
		if(userLogin==null){			
			return mapping.findForward("unauthorize");
		}else{
			if ("Add".equals(eform.getCmd())) {
				session.removeAttribute("plant");
				return retrievePlant(mapping, form, request, response);
			} else if ("Save".equals(eform.getCmd())) {
				return addPlant(mapping, form, request, response);
			} else if ("Delete".equals(eform.getCmd())) {
				return deletePlant(mapping, form, request, response);
			} else if ("GetProvince".equals(eform.getCmd())) {
				return getProvinceInfoFromBranch(mapping, form, request, response);
			} else if ("GetDistrict".equals(eform.getCmd())) {
				return getDistrictInfo(mapping, eform, request, response);
			} else if ("GetSubDistrict".equals(eform.getCmd())) {
				return getSubDistrictInfo(mapping, eform, request, response);
			} else if("GetBankBranch".equals(eform.getCmd())){
				return getBankBranchInfo(mapping, eform, request, response);
			} else if ("GetPostCode".equals(eform.getCmd())) {
				return getPostCode(mapping, eform, request, response);
			} else if ("GetTargetInfo".equals(eform.getCmd())) {
				return getTarget(mapping, eform, request, response);
			} else if ("GetBreedGroup".equals(eform.getCmd())){
				return getBreedGroup(mapping, eform, request, response);
			} else if ("GetFarmer".equals(eform.getCmd())){
				return getFarmer(mapping, eform, request, response);
			} else if ("GetFarmerGroupFromBranch".equals(eform.getCmd())){
				return getFarmerGroupFromBranch(mapping, eform, request, response);
			} else if ("GetPeriod".equals(eform.getCmd())) {
				return getPlantPeriod(mapping, form, request, response);
			} else if("GetPrepareArea".equals(eform.getCmd())){
				return getSubPrepareArea(mapping, eform, request, response);
			} else if("GetBuyerAddress".equals(eform.getCmd())) {
				return getBuyerAddressInfo(mapping, eform, request, response);
			} else if("GetFarmerGroupChild".equals(eform.getCmd())){
				return getFarmerGroupChild(mapping, eform, request, response);
			} else if ("Print".equals(eform.getCmd())) {
				return print(mapping, form, request, response);
			} else {
				return retrievePlant(mapping, form, request, response);
			}
		}
	}
	
	//  after click dropdown to choose ข้อมูลเกษตรกร 
	public ActionForward retrievePlant(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 	
	{
		PlantForm eform = (PlantForm)form;
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		User userLogin = (User) session.getAttribute("userLogin");
		
		FarmerHome fHome = new FarmerHome();
		PlantHome home = new PlantHome();
		ManureTypeHome mHome = new ManureTypeHome();
		ChemicalTypeHome chemHome =  new ChemicalTypeHome();
		FarmerGroupHome fGrpHome = new FarmerGroupHome();
		PrefixHome prefHome = new PrefixHome();
		ProvinceHome provHome = new ProvinceHome();
		BankHome bankHome = new BankHome();
		IrrigationAreaHome irrHome = new IrrigationAreaHome();
		AssetHome aHome = new AssetHome();
		LandRightTypeHome lTypeHome = new LandRightTypeHome();
		CostHome costHome = new CostHome();
		SeedSelectHome seedHome = new SeedSelectHome();
		PlantMethodHome methodHome = new PlantMethodHome();
		PrepareAreaHome areaHome = new PrepareAreaHome();
		PrepareManureHome manureHome = new PrepareManureHome();
		LandStatusHome statusHome = new LandStatusHome();
		LandTypeHome typeHome = new LandTypeHome();
		BranchHome branchHome = new BranchHome();
		BuyerHome buyerHome = new BuyerHome();
		LandCheckHome landCheckHome = new LandCheckHome();
		BreedTypeHome breedTypeHome = new BreedTypeHome();
		RegionHome rHome = new RegionHome();
		// For LandCheck
		// SymtomHome sHome = new SymtomHome();
		// CoordinatesHome cHome = new CoordinatesHome();
		// LandCheckImagesHome imgHome = new LandCheckImagesHome();
		
		Plant plant = null;
		Farmer farmer = null;
	
		List<Prefix> prefixList 			= null; // คำนำหน้า
		List<FarmerGroup> farmerGroupList 	= new ArrayList<FarmerGroup>(); // ชื่อกลุ่ม(เกษตรกร)
		List<FarmerGroup> corpGroupList 	= new ArrayList<FarmerGroup>(); // ชื่อกลุ่ม(สหกรณ์)
		
		List<Province> masterProvinceList 	= null; // จังหวัด
		List<ManureType> manureList 		= null; // 
		List<ChemicalType> chemicalList  	= null;
		List<Asset> assetList 				= null;
		List<LandRightType> landrightList 	= null;
		List<Cost> costList 				= null; 
		
		List<Bank> bankList = null;
		List<IrrigationArea> irrList		= null; // ข้อมูลเขตชลประทาน
		
		List<SeedSelect> seedList 			= null; // ข้อมูลการคัดเมล็ดพันธุ์
		List<PlantMethod> methodList 		= null; // ข้อมูลวิธีการปลูก
		List<PrepareArea> prepareAreaList   = null; // ข้อมูลการจัดเตรียมแปลง
		List<PrepareManure> prepareManureList = null;//ข้อมูลการใส่ปุ๋ย
		List<LandStatus> landStatusList 	= null;
		List<LandType> landTypeList 		= null;
		List<Branch> plantBranchList        = null;
		List<Buyer> plantBuyerList 			= null;
		List<LandCheck> landCheckList		= new ArrayList<LandCheck>();
	    
		// For LandCheck popup
	
		// --- ดึงปีการผลิตออกมา --- // 
		String[] yearList = new String[6];
		yearList = Utility.getYears();
		yearList[yearList.length-1] = "9999";
		
		long plantId = 0;
		long regionNo = 0, provinceNo = 0, districtNo = 0, subDistrictNo = 0, userLevel = 0, farmerGroupId = 0;
		try {
			sf.getCurrentSession().beginTransaction();
			  
			userLevel = userLogin.getLevel();
			farmerGroupId = userLogin.getFarmerGroupId();
			if(userLevel>1)
				regionNo = userLogin.getRegionNo();
			if(provinceNo==0 && userLevel>2)
				provinceNo = userLogin.getProvinceNo();
			if(districtNo==0 && userLevel>3)
				districtNo = userLogin.getDistrictNo();
			if(subDistrictNo==0 && userLevel>4)
				subDistrictNo = userLogin.getSubDistrictNo();
			  
			// ------------------------- ดึงลง dropdownlist --------------------- //
			 
			landrightList     =  lTypeHome.findAll();  	// เอกสารสิทธิ์ 
			costList          =  costHome.findAll();    // ต้นทุนค่าใช้จ่าย
			manureList        =  mHome.findAll();       // ปุ๋ย
		    chemicalList      =  chemHome.findAll();    // สารเคมี
		    irrList           =  irrHome.findAll();     // ข้อมูลเขตชลประทาน
		    seedList          =  seedHome.findAll(); 	// ข้อมูลการคัดเมล็ดพันธุ์
			methodList 		  =  methodHome.findAll();  // ข้อมูลวิธีการปลูก
			prepareAreaList   =  areaHome.findPPrepareArea(); 	// ข้อมูลการจัดเตรียมแปลง
			prepareManureList =  manureHome.findAll();	// ข้อมูลการใส่ปุ๋ย
			List fgList =  fGrpHome.searchByCriteria("F", "", "", regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId, userLogin.getUserName(), 0); // ชื่อกลุ่มเกษตรกร
			if(fgList!=null && fgList.size()>0) {
				for(int i=0;i<fgList.size();i++) {
					Object[] fgObj = (Object[])fgList.get(i);
					FarmerGroup fg = new FarmerGroup();
					fg.setFarmerGroupId(Long.parseLong(fgObj[0].toString()));
					fg.setFarmerGroupName(fgObj[1].toString());
					farmerGroupList.add(fg);
				}
			}
			FarmerGroup fGroup = new FarmerGroup();
			fGroup.setFarmerGroupId(0);
			fGroup.setFarmerGroupName("กรุณาเลือก");
			if (farmerGroupList != null && farmerGroupList.size() > 0)
				farmerGroupList.add(0, fGroup);
				
			bankList          =  bankHome.findAll();
			landStatusList    =  statusHome.findAll();
			landTypeList 	  =  typeHome.findAll();
			plantBranchList   =  branchHome.findAll(); // ธนาคาร
			plantBuyerList    =  buyerHome.findAll();  // ผู้ซื้อ
			
			Buyer buyer = new Buyer();
			buyer.setBuyerId(0);
			buyer.setBuyerName("กรุณาเลือก");
			if (plantBuyerList != null && plantBuyerList.size() > 0)
				plantBuyerList.add(0, buyer);
				
			// -----
			List corpList =fGrpHome.searchByCriteria("C", "", "", regionNo, provinceNo, districtNo, subDistrictNo, farmerGroupId, userLogin.getUserName(), 0);
			if(corpList !=null && corpList.size() > 0){
				for(int i=0; i< corpList.size(); i++){
					FarmerGroup coGroup = new FarmerGroup();
					Object[] obj = (Object[])corpList.get(i);
					coGroup.setFarmerGroupId(Long.parseLong(obj[0].toString()));
					coGroup.setFarmerGroupName(obj[1].toString());
					corpGroupList.add(coGroup);
				}
			}
			FarmerGroup cGroup = new FarmerGroup();
			cGroup.setFarmerGroupId(0);
			cGroup.setFarmerGroupName("กรุณาเลือก");
			if (corpGroupList != null && corpGroupList.size() > 0)
				corpGroupList.add(0, cGroup);
			// ----------
				
			assetList = aHome.findAll();  		   // ทรัพย์สิน
			prefixList = prefHome.findAll();      // คำนำหน้าชื่อ  
			
			
			// ---- start :  added by Yatphiroon.P on 18/1/2017 ---
			/*if(userLevel <= 1){ //comment for Test all user not depend role 09/08/2017
				masterProvinceList = provHome.findAll();
			}else if(userLevel <= 2){
				 List<UserRegion> userRegionList  = new ArrayList<UserRegion>(userLogin.getUserRegion());
				 List<Long> paramList = new ArrayList<Long>();
				 for (UserRegion rObj : userRegionList) {
						if(rObj != null)
						{
							paramList.add(rObj.getRegionNo());
						}
				}
				masterProvinceList = provHome.retrieveByRegionNoList(paramList);
			} else {
				masterProvinceList = new ArrayList<Province>();
			    List<UserProvince> userProvinceList  = new ArrayList<UserProvince>(userLogin.getUserProvince());
				for (UserProvince userProv : userProvinceList) {
					Province pObj = provHome.searchByProvinceNo(userProv.getProvinceNo());
					if(pObj != null)
						masterProvinceList.add(pObj);
				}
			}*/
			// ---- finish : added by Yatphiroon.P on 18/1/2017 ---
			
			masterProvinceList = provHome.findAll(); //for Test 09/08/2017
			
			
			
			sf.getCurrentSession().flush();
			if(eform.getLandCheckPage() != null && "Y".equals(eform.getLandCheckPage())){
				plantId = home.getMaxPlantIdFromRefPlant(Long.parseLong(eform.getRefPlantId()));
				request.setAttribute("LAND_CHECK_PAGE", "Y");
				request.setAttribute("landCheckDocNo", eform.getLandCheckDocNo());	
				
				BreedGroupHome groupHome = new BreedGroupHome();
				List<BreedType> breedTypeList = breedTypeHome.findAll();   //ประเภทพันธุ์พืช
				List<BreedGroup> breedGroupList = groupHome.findAll(); //กลุ่มพันธุ์พืช 
				session.setAttribute("breedTypePlantList", breedTypeList);
				session.setAttribute("breedGroupPlantList", breedGroupList);
			}else{
				plantId = Long.parseLong(eform.getPlantId()==null?"0":eform.getPlantId());
			}
			
			
			sf.getCurrentSession().flush();
			
			plant = home.findById(plantId);
			if(plant != null){
				farmer = fHome.findByKey(plant.getIdCard(), plant.getEffectiveDate());
				BreedType bType = breedTypeHome.findByBreedTypeId(plant.getBreedTypeId());
				eform.setRefPlantId(plant.getRefPlantId()+"");
				plant.setBreedTypeId(bType.getBreedTypeId()); // just added on 19.08.2014
				plant.setBreedTypeName(bType.getBreedTypeName());
				
				Set<FarmerFamily> familySet = plant.getFarmerFamily();
				String getFamilyString = "";
				if(familySet != null && familySet.size() > 0){
					getFamilyString =  getFarmerFamilyStr(familySet);
					plant.setFamilyItem(getFamilyString);
				}
				
				List<LandRight> lrList = plant.getLandright();
				String getLandRightStr = "";
				String getDetailStr = "";
				if(lrList != null && lrList.size() > 0) 
				{
					COUNTING = 1;
					getLandRightStr = getLandRightStr(lrList);
				    plant.setLandrightItem(getLandRightStr); // set landright
				    // int counting = 1;
				    for(int i = 0; i < lrList.size(); i++) 
				    {
				    	LandRight land = (LandRight)lrList.get(i);
				    	if(land != null) 
				    	{
					    	if(land.getPlantDetail() != null && land.getPlantDetail().size() > 0) 
					    	{	 
								Set<PlantDetail> detailSet = land.getPlantDetail();
								getDetailStr += getPlantDetailStr(detailSet, land); 
							}
				    	}
				    }
				    plant.setDetailItem(getDetailStr);
				    COUNTING = 1;
				}
				  
				List<AssetDetail> assetDetailList = plant.getAssetDetail();
				String getAssetDetailString = "";
				if(assetDetailList != null && assetDetailList.size() > 0){
					getAssetDetailString = getDetailString(assetDetailList);
					if(isCheckSum == true){
						String[] assetVal = getAssetDetailString.split("\\,");
						plant.setAssetDate(assetVal[3]);
						plant.setAssetAmount(assetVal[4]);
						eform.setDateAsset(assetVal[3]);
						eform.setAssetAmount(assetVal[4]);
					}else{
						plant.setAssetItem(getAssetDetailString);
						String sumAmount = String.valueOf(SUMTOTAL);
						plant.setAssetAmount(sumAmount);
					}
				}
				  
				// to retrieve landcheck
				List<?> lCheckList =  landCheckHome.getLandChecked(plant.getIdCard(), plant.getPlantYear(), plant.getPlantNo(), plant.getBreedTypeId());
				
				if(lCheckList!=null && lCheckList.size()>0) {
					for(int i=0;i<lCheckList.size();i++) {
						Object[] lObj = (Object[])lCheckList.get(i);
						LandCheck lCheck = new LandCheck();
						
						if(lCheck != null){
							lCheck.setLandCheckId(Long.parseLong(lObj[0].toString()));
							lCheck.setPlantYear(Integer.parseInt(lObj[1].toString()));
							lCheck.setPlantNo(Integer.parseInt(lObj[2].toString()));
							lCheck.setIdCard(lObj[3].toString());
							lCheck.setFirstName(lObj[4].toString());
							lCheck.setLastName(lObj[5].toString());
							lCheck.setTypeId(Long.parseLong(lObj[6].toString()));
							lCheck.setDocNo(lObj[7].toString());
							
							BigDecimal bigD1 = new BigDecimal(lObj[8].toString());
							long docRai = bigD1.longValue();
							lCheck.setDocRai(docRai);
							//lCheck.setDocRai(Long.parseLong(lObj[8].toString()));
							
							BigDecimal bigD2 = new BigDecimal(lObj[9].toString());
							long docNgan = bigD2.longValue();
							lCheck.setDocNgan(docNgan);
							//lCheck.setDocNgan(Long.parseLong(lObj[9].toString()));
							
							BigDecimal bigD3 = new BigDecimal(lObj[10].toString());
							long docWah = bigD3.longValue();
							lCheck.setDocWah(docWah);
							//lCheck.setDocWah(Long.parseLong(lObj[10].toString()));
							
							//lCheck.setCheckPeriod(lObj[11].toString());
							// -- check date
							//Date retreiveDate = (Date)lObj[11];
							Date checkDate = DateUtils.addYears((Date)lObj[11], 543);
							//String dateStr = lObj[11].toString();
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							Date date;
							String changedFormatDate;
							try {
								changedFormatDate = formatter.format(checkDate);
								date = formatter.parse(changedFormatDate);
								lCheck.setCheckDate(date); // set harvest date
							} catch (ParseException e) {
								log.error(e,e);
								e.printStackTrace();
							}
							// -- finish check date
							lCheck.setCheckTime(lObj[12].toString());
							lCheck.setResult(lObj[13].toString());
							//lCheck.setSymtomId(Long.parseLong(lObj[15].toString()));
							lCheck.setRemark(lObj[14].toString());
							lCheck.setCheckBy(lObj[15].toString());
							// -- last update 
							//String upDateStr = lObj[16].toString();
							Date lastUpdateDate = DateUtils.addYears((Date)lObj[16], 543);
							SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd");
							Date upDate;
							try {
								changedFormatDate = formatter.format(lastUpdateDate);
								upDate = formatter1.parse(changedFormatDate);
								lCheck.setLastUpdateDate(upDate); // set harvest date
							} catch (ParseException e) {
								log.error(e,e);
								e.printStackTrace();
							}
							// finish last update
							lCheck.setLastUpdateBy(lObj[17].toString());
							lCheck.setBreedTypeId(Long.parseLong(lObj[18].toString()));
							lCheck.setOnwer(lObj[19]==null?"":lObj[19].toString());//owner [19]
							lCheck.setCommittee1(lObj[20]==null?"":lObj[20].toString());//committee1[20]
							lCheck.setCommittee2(lObj[21]==null?"":lObj[21].toString());//committee2[21]
							lCheck.setCheckPeriodId(Integer.parseInt(lObj[22].toString()));//checkpreiodId[22]
							lCheck.setRefPlantId(Long.parseLong(lObj[23].toString()));//refPlantId[23]
							lCheck.setBreedTypeName(lObj[24].toString());
							lCheck.setDescription(lObj[25].toString());//description[25]
							lCheck.setPlantId(Long.parseLong(lObj[26].toString()));//plantId[26]
							//refPlandId[27]
							lCheck.setTypeName(lObj[28].toString());
							landCheckList.add(lCheck);
						}
					}
					String landCheckStr = getLandCheckString(landCheckList);
					plant.setLandCheckItem(landCheckStr);
				}
			}
			/// ---------- END 1st step : retrieve all information on Plant page ---------
			
			session.setAttribute("plant", plant);
			session.setAttribute("plantFarmer", farmer);
			session.setAttribute("pYearList", yearList);
			session.setAttribute("plantPrefixList", prefixList);
			session.setAttribute("plantProvinceList", masterProvinceList);
			session.setAttribute("plantManureList", manureList);
			session.setAttribute("plantChemicalList", chemicalList);
			session.setAttribute("plantAssetList", assetList);
			session.setAttribute("plantLandrightList",landrightList);
			session.setAttribute("plantCostList",costList);
			session.setAttribute("plantBankList",bankList);
			session.setAttribute("plantIrrList", irrList);
			session.setAttribute("plantBuyerList", plantBuyerList);
			session.setAttribute("plantFarmerGroupList", farmerGroupList);
			session.setAttribute("plantCorpGroupList", corpGroupList);
			session.setAttribute("plantSeedList", seedList);
			session.setAttribute("plantMethodList", methodList);
			session.setAttribute("plantPrepareAreaList", prepareAreaList);
			session.setAttribute("plantPrepareManureList", prepareManureList);
			session.setAttribute("plantBranchList", plantBranchList);
			session.setAttribute("plantLandStatusList", landStatusList);
			session.setAttribute("plantLandTypeList", landTypeList);
			//session.setAttribute("landCheckSymtomList", symtomList);
			//session.setAttribute("landCheckCoordinateList", coordinatesList);
			
			
			
			//เช็คการคีย์เพิ่มข้อมูลเกษตรกรผู้เพาะปลูกซ้ำ
			if("Edit".equals(eform.getCmd()))
				eform.setStatus("Edit");
			else
				eform.setStatus("Add");
			
		} catch(Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally {
	    	sf.getCurrentSession().close();
		} 
	    return mapping.findForward("plant");
	}
	
	private String getDetailString(List<AssetDetail> aDetailList){
		String assetStr = "";
		SUMTOTAL = 0;
		for(int i=0; i<aDetailList.size(); i++){
			AssetDetail asset = (AssetDetail)aDetailList.get(i);
			 
			if(asset!=null){
				  if(!"Y".equals(asset.getAsset().getIsTotal())){
					  isCheckSum = false;
					  assetStr += "|" + asset.getSeq() + "," + asset.getAssetId()+ "," + asset.getAsset().getAssetName() + "," + setDateString(asset.getAssetDate()) + "," + asset.getAmount();
					  SUMTOTAL +=  asset.getAmount();
				  }else{
					  if("Y".equals(asset.getAsset().getIsTotal())){
						  isCheckSum = true;
						  assetStr = asset.getSeq() + "," + asset.getAssetId() + "," + asset.getAsset().getAssetName() + "," + setDateString(asset.getAssetDate()) + "," + asset.getAmount();
					  }
				  }
			}
		}
		if(assetStr.indexOf("|") > -1)
			assetStr = assetStr.substring(1);
		return assetStr;
	}
	
	private String getLandCheckString(List<LandCheck> landCheckList){
		String landStr = "";
		int index = 0;
		for(int i=0; i< landCheckList.size(); i++){
			LandCheck lc = (LandCheck)landCheckList.get(i);
			
			if(lc!=null){
				index++;
				landStr += "|" + index + "," + lc.getLandCheckId() + "," + setDateString(lc.getCheckDate()) + "," + setDateString(lc.getLastUpdateDate()) + "," + lc.getDescription() + "," + lc.getTypeId() + "," + lc.getTypeName() + "," + lc.getDocNo() + "," + lc.getCheckBy() + "," + lc.getResult();
			}
		}
		if(landStr.indexOf("|") > -1)
			landStr = landStr.substring(1);
		return landStr;
	}
	
	// การใช้สารเคมีคุมและกำจัดวัชพืช
	private String getChemicalString(List<PlantChemical> chemList){
		  String chemStr = "";
		  for(int i=0; i < chemList.size(); i++){
			  PlantChemical chem = (PlantChemical)chemList.get(i);
			  if(chem!=null){
				  String formula = "";
				  if(chem.getFormula()!=null){
					  if(chem.getFormula().indexOf(",") > -1)
						  formula = chem.getFormula().replace(',', '^');
					  else
						  formula = chem.getFormula();
				  }
				  
				  int period = 0;
				  if(chem.getPeriod()!=null){
					  if("ก่อนการเพาะปลูก".equals(chem.getPeriod()))
						  period = 1;
					  if("ระหว่างการเพาะปลูก".equals(chem.getPeriod()))
						  period = 2;
					  if("หลังการเพาะปลูก".equals(chem.getPeriod()))
					      period = 3;
				  }
				   
				  ChemicalTypeHome chome = new ChemicalTypeHome();
				  ChemicalType chemical = chome.findByTypeId(chem.getChemicalTypeId());
				  chemStr += "|" + chem.getCseq() + "," + chem.getChemicalTypeId() +"," +chemical.getTypeName() +  "," + formula + "," +  chem.getCostPerRai() + "," + period + "," + setDateString(chem.getDateUse())+ "," + chem.getQtyPerRai();
			  }
		  }
		  return chemStr.substring(1);
	}
	   
	// การใช้ปุ๋ยเคมีและปุ๋ยอินทรีย์
	private String getManureString(List<PlantManure> manureList){
		  String manureStr = "";
		  for(int i=0; i<manureList.size(); i++){
			  PlantManure manure = (PlantManure)manureList.get(i);
			  int ownBuy = 0, ownProduce = 0;
			  if(manure!=null)
			  {
				  String formula = "";
				  if(manure.getFormula() != null){
					  if(manure.getFormula().indexOf(",") > -1)
						  formula = manure.getFormula().replace(',', '^');
					  else
						  formula = manure.getFormula();
				  }
				
				  if("B".equals(manure.getManureStatus().toUpperCase())){
					  ownBuy = 1;
				  }
				  if("P".equals(manure.getManureStatus().toUpperCase())){
					  ownProduce = 1;
				  }
				  int period = 0;
				  if(manure.getPeriod()!=null){
					  if("ก่อนการเพาะปลูก".equals(manure.getPeriod()))
						  period = 1;
					  if("ระหว่างการเพาะปลูก".equals(manure.getPeriod()))
						  period = 2;
					  if("หลังการเพาะปลูก".equals(manure.getPeriod()))
					      period = 3;
				  }
				  
				  ManureTypeHome mtypeHome = new ManureTypeHome();
				  ManureType mtype = mtypeHome.findByTypeId(manure.getManureTypeId());
				
				  manureStr += "|" + manure.getMseq() + "," + manure.getManureTypeId() + "," + mtype.getTypeName() + "," + ownBuy + "," + ownProduce + "," + manure.getManureName()+ "," + formula + "," + manure.getCostPerRai() + "," + period + "," + setDateString(manure.getDateUse()) + "," + manure.getQtyPerRai();
				  
			  }
		  }
		  return manureStr.substring(1);
	}
	
	private String setDateString(Date originalDate){	
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reportDate = "";
		if(originalDate != null)
			reportDate = df.format(originalDate); 
		return reportDate;
	}
	
	
	// เอกสารสิทธิ์
	private String getLandRightStr(List<LandRight> landList){
		  String landDetailStr = ""; // landStr = "" 
		  int isOwn = 0; 
		  
		  for(int i=0; i < landList.size(); i++){
			  LandRight land = (LandRight)landList.get(i);
			  if(land != null){
				  if("O".equals(land.getOwnRent())) {
					  isOwn = 1;
				  } else if("R".equals(land.getOwnRent())) {
					  isOwn = 0;
				  } else {
					  isOwn = 2;
				  }
				    
				  String docNo = "";
				  if(land.getDocNo().indexOf(",") > -1){
					  docNo = land.getDocNo().replace(',', '^');
				  } else {
					  docNo = land.getDocNo();
				  }
				  
				  String landType = "";
				  if(land.getLandType().indexOf(",") > -1){
					  landType = land.getLandType().replace(',', '^');
				  } else {
					  landType = land.getLandType();
				  }
				  
				  String landStatus = "";
				  if(land.getLandStatus().indexOf(",") > -1){
					  landStatus = land.getLandStatus().replace(',', '^');  
				  } else {
					  landStatus = land.getLandStatus();
				  }
				  
				  String landMoo = "";
				  if(land.getMoo() <= 0){
					  landMoo = "''";
				  }else{ 
					  landMoo = land.getMoo()+"";
				  }
				  
				  String provinceNo = "";
				  if(land.getProvinceNo() <= 0){
					  provinceNo = "''";
				  }else{
					  provinceNo = land.getProvinceNo() + "";
				  }
				  
				  String districtNo = "";
				  if(land.getDistrictNo() <= 0){
					  districtNo = "''";
				  }else{
					  districtNo = land.getDistrictNo() + "";
				  }
					  
				  String subDistrictNo = "";
				  if(land.getSubDistrictNo() <= 0){
					  subDistrictNo = "''";
				  }else{
					  subDistrictNo = land.getSubDistrictNo() + "";
				  }
				  
				  landDetailStr +=  "|" + land.getSeq() + "," + land.getTypeId() 
				  				  + "," + land.getLandRightType().getTypeName()  + "," + docNo 
					  	          + "," + land.getDocRai() + "," + land.getDocNgan() + "," + land.getDocWah()
					  			  + "," + landMoo + "," + provinceNo + "," + districtNo + "," + subDistrictNo 
					  			  + "," + isOwn  + ",'" + land.getRentPrice() + "'," + land.getWaterSource() 
					  			  + "," + land.getIrrigationAreaId() + "," + landStatus 
					  			  + "," + landType+ "," + land.getLandTypeOther();
				 
			  }
		  }
		  
		  if("".equals(landDetailStr))
			  landDetailStr = null;
		  else
			  landDetailStr = landDetailStr.substring(1);
		  return landDetailStr;
	}
	
	
	private String getCostDetailStr(Set<CostDetail> costSet)
	{
		 List<CostDetail> costList = new ArrayList<CostDetail>(costSet);
		  String costStr = "";
		  int index = 0;
		  for(int i=0; i< costSet.size(); i++){
			  CostDetail cost = (CostDetail)costList.get(i);
			  if(cost != null){
				  index++;
				  
				  String costName = cost.getCost().getCostName();
				  if(costName.indexOf(",") > -1){
					  costName = cost.getCost().getCostName().replace(',', '-');  
				  }
				  
				  if(index == 1){
					  costStr += index + "," + cost.getCostId() + "," + costName + "," + cost.getAmount() + "," + setDateString(cost.getCostDate());	               
				  }else{
					  /* move this part to above by sedtapong.n DCS 15062017
					  String costName = cost.getCost().getCostName();
					  if(costName.indexOf(",") > -1){
						  costName = cost.getCost().getCostName().replace(',', '-');  
					  }
					  */
					  costStr +=  "|" + index + "," + cost.getCostId() + "," + costName + "," + cost.getAmount()+ "," + setDateString(cost.getCostDate());	         
				  }
			  }
		  }
		  if("".equals(costStr))
			  costStr = null;
		  String costDetailStr = costStr;
		  return costDetailStr;
	}

	
	private String getHarvDetailSet(Set<HarvestDetail> harvSet)
	{
		List<HarvestDetail> harvList = new ArrayList<HarvestDetail>(harvSet);
		String harvStr = "";
		int index = 0;
		for(int i=0; i< harvList.size(); i++){
			HarvestDetail harvDetail = (HarvestDetail)harvList.get(i);
			if(harvDetail != null){
				index++;
				harvStr +=  "|" + index + "," + setDateString(harvDetail.getHarvestDate()) + "," + harvDetail.getTotalHarvest() + "," + harvDetail.getRemainSell() + "," + harvDetail.getConsumeProduct() + "," + harvDetail.getBreedProduct();
			}
		}
		if("".equals(harvStr))
			harvStr = null;
		else
			harvStr = harvStr.substring(1);
		return harvStr;
	}
	
	private String getSellingDetailStr(Set<SellingDetail> sellSet)
	{
		List<SellingDetail> sellList = new ArrayList<SellingDetail>(sellSet);
		String sellStr = "";
		int index = 0;
		if(sellList != null && sellList.size() > 0){
			for(int i=0; i< sellList.size(); i++){
				SellingDetail sell = (SellingDetail)sellList.get(i);
				if(sell != null){
					index++;//+1;
					sellStr +=  "|" + index + "," + setDateString(sell.getSaleDate()) + "," + sell.getSaleCrop() + "," +  sell.getSalePrice() + "," + sell.getAmount() + "," +  sell.getBuyerId() + "," +  sell.getBuyer() + "," +  sell.getBuyerAddress() + "," +  sell.getProvinceNo() + "," + sell.getDistrictNo() + "," + sell.getSubDistrictNo() + "," + sell.getSaleDryCrop()+ "," + sell.getSaleDryPrice() + "," + sell.getDryAmount() + "," + sell.getHumidDry() + "," + sell.getHumid();
				}
			}
		}
		if("".equals(sellStr))
			sellStr = null;
		else
			sellStr = sellStr.substring(1);
		  
		return sellStr;
	}
	
	private String setValueWithoutNotation(double douValue)
    {
		NumberFormat f = NumberFormat.getInstance();
		f.setGroupingUsed(false);
		String refinedNumber = f.format(douValue);
		return refinedNumber;
	}
	
	private String getPlantDetailStr(Set<PlantDetail> detailSet, LandRight land)
	{
		  List<PlantDetail> detailList = new ArrayList<PlantDetail>(detailSet);
		  
		  FarmerGroupHome home = new FarmerGroupHome();
		 // SessionFactory sf = HibernateHome.getSessionFactory();
		  String detailStr = "";
		  if(detailList != null && detailList.size() > 0){
			  for(int i=0; i< detailList.size(); i++){
				  PlantDetail plantDetail = (PlantDetail)detailList.get(i);
				  if(plantDetail != null)
				  {
					     String detailTab1= "", detailTab5 = "";
						 String seedFrom = "";
						 if(plantDetail.getSeedFrom()!= null){
							 if(plantDetail.getSeedFrom().indexOf(",") > -1){
								 seedFrom = plantDetail.getSeedFrom().replace(',', '^');
							 }else{
								 seedFrom = plantDetail.getSeedFrom();
							 }
						 }
						 
						 String seedSelect = "";
						 if(plantDetail.getSeedSelect()!= null){
							 if(plantDetail.getSeedSelect().indexOf(",") > -1){
								 seedSelect = plantDetail.getSeedSelect().replace(',', '^');
							 }else{
								 seedSelect = plantDetail.getSeedSelect();
							 }
						 }
						 
						 String plantObjective = "";
						 if(plantDetail.getPlantObjective() != null){
							 if(plantDetail.getPlantObjective().indexOf(",") > -1){
								 plantObjective = plantDetail.getPlantObjective().replace(',', '^');
							 }else{
								 plantObjective = plantDetail.getPlantObjective();
								 
							 }
						 }
						 String qualification = "";
						 if(plantDetail.getQualification() != null){
							 if(plantDetail.getQualification().indexOf(",") > -1){
								 qualification = plantDetail.getQualification().replace(',', '^');
							 }else{
								 qualification = plantDetail.getQualification();
							 }
						 }
						 
						 String plantMethod = "";
						 if(plantDetail.getPlantMethod() != null){
							 if(plantDetail.getPlantMethod().indexOf(",") > -1){
								 plantMethod = plantDetail.getPlantMethod().replace(',', '^');
							 }else{
								 plantMethod = plantDetail.getPlantMethod();
							 }
						 }
						 
						 String prepareArea = "";
						 if(plantDetail.getPrepareArea() != null){
							 if(plantDetail.getPrepareArea().indexOf(",") > -1){
								 prepareArea = plantDetail.getPrepareArea().replace(',', '^');
							 }else{
								 prepareArea = plantDetail.getPrepareArea();
							 }
						 }
						  
						 String prepareManure = "";
						 if(plantDetail.getPrepareManure() != null){
							 if(plantDetail.getPrepareManure().indexOf(",") > -1){
								  prepareManure = plantDetail.getPrepareManure().replace(',', '^');
							 }else{
								  prepareManure = plantDetail.getPrepareManure();
							 }
						 }
						 
						 String standard = "";
						 
						 if(plantDetail.getStandard() != null){
								if("1".equals(plantDetail.getStandard()))
									standard = "ทำ";
								if("0".equals(plantDetail.getStandard()))
									standard = "ไม่ทำ";
						 }
						 
						 String docNo = land.getDocNo();
						 if(land.getDocNo().indexOf(",") > -1)
							  docNo = land.getDocNo().replace(',', '^');
						 //แทน plantDetail.getSeq() ด้วย count
						 
						 //
						 String farmerGroupId = "", farmerGroupName = "";
						 if( plantDetail.getCooperativeId() > 0 ){
							 farmerGroupId = String.valueOf(plantDetail.getCooperativeId());
							 farmerGroupName = home.getByCooperativeId(Long.parseLong(farmerGroupId));
						 } else {
							 farmerGroupId = "0";
							 farmerGroupName = "-";
						 }
						 
						 String noOfSeed = "";
						 if( plantDetail.getNoOfSeed() > 0 ){
							 noOfSeed = String.valueOf(plantDetail.getNoOfSeed());
						 } else {
							 noOfSeed = "0";
						 }
						 
						 
						 detailTab1 = COUNTING + "," + plantDetail.getTypeId() 
						 				   + ",'" + plantDetail.getLandRight().getLandRightType().getTypeName()
						 				   + "','" + docNo + "'," + land.getDocRai() + "," + land.getDocNgan() + "," + land.getDocWah()
						 				   + "," + plantDetail.getBreedGroupId() + ",'" + plantDetail.getBreedGroup().getBreedGroupName()
						 				   + "','" + plantDetail.getQtyPerRai() + "'," + plantDetail.getPlantRai()
						 				   + "," + plantDetail.getPlantNgan() + "," + plantDetail.getPlantWah() + ",'" + seedFrom
						 				   + "','" + seedSelect + "','" + plantObjective+ "','" + qualification 
						 				   + "','" + standard + "','" + plantMethod + "','" + prepareArea
						 				   + "','" + plantDetail.getPrepareAreaOther() + "','" + prepareManure
						 				   + "','" + setDateString(plantDetail.getPlantDate()) + "','" + setDateString(plantDetail.getForecastDate())
						 				   + "','" + plantDetail.getForecastRecord()
						 				   + "'," + farmerGroupId + ",'" + farmerGroupName + "','" + noOfSeed + "'";
					  
						   String getManure = "";
						   if(plantDetail.getPlantManure() != null && plantDetail.getPlantManure().size() > 0) {
								  List<PlantManure> pManureList = plantDetail.getPlantManure();
								  getManure = getManureString(pManureList);
						   }
							 
						   String getChemical = "";
						   if(plantDetail.getPlantChemical() != null && plantDetail.getPlantChemical().size() > 0) {
								  List<PlantChemical> pChemicalList = plantDetail.getPlantChemical();
							      getChemical = getChemicalString(pChemicalList);		 
						   }
							 
						   String getDetailList = "";
						   if(plantDetail.getAssetDetail() != null && plantDetail.getAssetDetail().size() > 0) {
								  List<AssetDetail> assetDetailList = plantDetail.getAssetDetail();
								  getDetailList = getDetailString(assetDetailList);		
						   }
							
						   detailTab5 = COUNTING + "," + setDateString(plantDetail.getPublicMarketDate()) + "," + setValueWithoutNotation(plantDetail.getPublicCrop()) + "," + setDateString(plantDetail.getRecordForecastDate()) + "," + plantDetail.getRecordForecastBy();
							 
						   String getCostDetailStr = "";
						   if(plantDetail.getCostDetail() != null && plantDetail.getCostDetail().size() > 0) {	
								Set<CostDetail> costList = plantDetail.getCostDetail();
							    if(costList != null && costList.size() > 0)
									 getCostDetailStr = getCostDetailStr(costList);
						   }
							 
						   String getHarvDetailStr = "";
						   if(plantDetail.getHarvestDetail() != null && plantDetail.getHarvestDetail().size() > 0){
								 Set<HarvestDetail> harvSet = plantDetail.getHarvestDetail();
								 if(harvSet != null && harvSet.size() > 0)
									 getHarvDetailStr = getHarvDetailSet(harvSet);
							}
						   
						   String getSellingDetailStr = "";
						   if(plantDetail.getSellingDetail() != null && plantDetail.getSellingDetail().size() > 0){
								 Set<SellingDetail> sellingSet = plantDetail.getSellingDetail();
								 if(sellingSet != null && sellingSet.size() > 0)
									 getSellingDetailStr = getSellingDetailStr(sellingSet);
						   }   
						   detailStr += "$" + detailTab1 + "#" + getManure + "#" + getChemical+"#" + getDetailList + "#" + detailTab5 + "#" + getCostDetailStr + "#" +  getHarvDetailStr + "#" + getSellingDetailStr;
						   COUNTING++;
				  }
			  }
		  }	  
		  return detailStr;
	}
	
	
	private String getFarmerFamilyStr(Set<FarmerFamily> familySet)
	{
		  List<FarmerFamily> familyList = new ArrayList<FarmerFamily>(familySet);
		  String familyStr = "";
		  int index = 0;
		  for(int i=0; i< familyList.size(); i++){
			  FarmerFamily family = (FarmerFamily)familyList.get(i);
			  if(family != null){
				  index++;
				  if(index == 1){
					  familyStr += index + "," + family.getIdCard() + "," + family.getFirstName() + "," + family.getLastName();	               
				  }else{
					  familyStr +=  "|" + index + "," + family.getIdCard() + "," +  family.getFirstName() + "," + family.getLastName();	       
				  }
			  }
		  }
		  if("".equals(familyStr))
			  familyStr = null;
		  return familyStr;
	}
	
	
	
	private Set<CostDetail> setCostDetailSet(String costItems, PlantDetail detail)
	{
		Set<CostDetail> costSet = new HashSet<CostDetail>();
		CostHome home = new CostHome();
		String[] costItem = costItems.split("\\|");
		if(costItem !=null && costItem.length>0){
			for(int i=0; i< costItem.length; i++){
				if(!"".equals(costItem[i]) && !"undefined".equals(costItem[i]) && costItem[i] != null && !"null".equals(costItem[i]))
				{
					String[] docValue = costItem[i].split("\\,");
					CostDetail costDetail = new CostDetail();
					costDetail.setPlantDetail(detail); 
					
					Cost cost = null;
					if(!"".equals(docValue[1]) && docValue[1]!=null)
					   cost = home.findByCostId(Long.parseLong(docValue[1]));
					if(cost!=null){
					   costDetail.setCostId(cost.getCostId());
					   costDetail.setCost(cost);
					}
					if(!"".equals(docValue[3]) && docValue[3]!=null)
						costDetail.setAmount(Double.parseDouble(docValue[3]));
					
					
					if(!"".equals(docValue[4]) && docValue[4] != null)
					{
						String dateStr = docValue[4];
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date date;
						try {
							date = formatter.parse(dateStr);
							costDetail.setCostDate(date); // set harvest date
						} catch (ParseException e) {
							  log.error(e,e);
							e.printStackTrace();
						}
					}
					
					costSet.add(costDetail);
				}
			}
		} 
		return costSet;
	}
	
	private Set<HarvestDetail> setHarvestDetailSet(String harvItems, PlantDetail detail)
	{
		Set<HarvestDetail> harvSet = new HashSet<HarvestDetail>();
		String[] harvItem = harvItems.split("\\|");
		if(harvItem != null && harvItem.length > 0){
			for(int j = 0; j < harvItem.length; j++){
				if(!"".equals(harvItem[j]) && !"undefined".equals(harvItem[j]) && harvItem[j] != null && !"null".equals(harvItem[j])){
					String[] value = harvItem[j].split("\\,");
					HarvestDetail harvDetail = new HarvestDetail();
				    harvDetail.setPlantDetail(detail);
				 	
				    if(!"".equals(value[1]) && value[1] != null)
					{
						String dateStr = value[1];
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date date;
						try {
							date = formatter.parse(dateStr);
							harvDetail.setHarvestDate(date); // set harvest date
						} catch (ParseException e) {
							  log.error(e,e);
							e.printStackTrace();
						}
					}
					if(!"".equals(value[2]) && value[2] != null)
						harvDetail.setTotalHarvest(Double.parseDouble(value[2]));
					if(!"".equals(value[3]) && value[3] != null)
						harvDetail.setRemainSell(Double.parseDouble(value[3]));
					
					if(!"".equals(value[4]) && value[4] != null)
						harvDetail.setConsumeProduct(Double.parseDouble(value[4]));
					if(!"".equals(value[5]) && value[5] != null)
						harvDetail.setBreedProduct(Double.parseDouble(value[5]));
					
					
					harvSet.add(harvDetail);
			}
		  }
		}
		return harvSet;
	}
	

	private Set<SellingDetail> setSellingDetailSet(String saleItems, PlantDetail detail)
	{
		Set<SellingDetail> sellingSet = new HashSet<SellingDetail>();
		String[] saleItem = saleItems.split("\\|");
		int count = 1;
		if(saleItem !=null && saleItem.length>0){
			for(int i=0; i< saleItem.length; i++){
			  if(!"".equals(saleItem[i]) && !"undefined".equals(saleItem[i]) && !"null".equals(saleItem[i]) && saleItem[i]!=null)
			  {
				  String[] docValue = saleItem[i].split("\\,");
				  SellingDetail sell = new SellingDetail();
				  sell.setPlantDetail(detail);
				 
				  SubDistrictHome home = new SubDistrictHome();
				  SubDistrict subdistrict = null;
				  if(docValue[10]!=null && !"".equals(docValue[10]))
					  subdistrict = home.findBySubDistrictNo(Long.parseLong(docValue[10]));
				  if(subdistrict != null){
					  sell.setRegionNo(subdistrict.getRegionNo());
					  sell.setProvinceNo(subdistrict.getProvinceNo());
					  sell.setDistrictNo(subdistrict.getDistrictNo());
					  sell.setSubDistrictNo(subdistrict.getSubDistrictNo());
				  }
				  
				  if(docValue[1]!=null && !"".equals(docValue[1]))
				  {
					    String dateStr = docValue[1];
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date date;
						try {
							date = formatter.parse(dateStr);
							sell.setSaleDate(date); // set sale date
						} catch (ParseException e) {
							e.printStackTrace();
							  log.error(e,e);
						}
				  }
				  
				  if(docValue[2]!=null && !"".equals(docValue[2]))
					  sell.setSaleCrop(Double.parseDouble(docValue[2]));
				  
				  if(docValue[3]!=null && !"".equals(docValue[3]))
					  sell.setSalePrice(Double.parseDouble(docValue[3]));
				  
				  if(docValue[4]!=null && !"".equals(docValue[4]))
					  sell.setAmount(Double.parseDouble(docValue[4]));
				  
				  if(docValue[5]!=null && !"".equals(docValue[5]))
					  sell.setBuyerId(Long.parseLong(docValue[5]));
				  
				  if(docValue[6]!=null && !"".equals(docValue[6]))
					  sell.setBuyer(docValue[6]);
				  
				  if(docValue[7]!=null && !"".equals(docValue[7]))
					  sell.setBuyerAddress(docValue[7]);
				  
				  if(docValue[11]!=null && !"".equals(docValue[11]))
					  sell.setSaleDryCrop(Double.parseDouble(docValue[11]));
				  
				  if(docValue[12]!=null && !"".equals(docValue[12]))
					  sell.setSaleDryPrice(Double.parseDouble(docValue[12]));
				  
				  if(docValue[13]!=null && !"".equals(docValue[13]))
					  sell.setDryAmount(Double.parseDouble(docValue[13]));
				
				  // ------- added on 5/1/2017
				  if(docValue[14]!=null && !"".equals(docValue[14]))
					  sell.setHumidDry(Double.parseDouble(docValue[14]));
				  
				  if(docValue[15]!=null && !"".equals(docValue[15]))
					  sell.setHumid(Double.parseDouble(docValue[15]));
				  // --------- finish added on 5/1/2017
				  
				  sell.setSaleSeq(count);
				  sellingSet.add(sell);
				  count++;
			   }
			}
		} 
		return sellingSet;
	}

	private List<PlantManure> setManureList(String manureItems, PlantDetail plantDetail)
	{
		List<PlantManure> manureList = new ArrayList<PlantManure>();
		ManureTypeHome mHome = new ManureTypeHome();
		String[] manureItem = manureItems.split("\\|");
		if(manureItem !=null && manureItem.length>0){
			for(int k=0; k < manureItem.length; k++)
			{
				if(manureItem[k] != null && !"".equals(manureItem[k]) && !"undefined".equals(manureItem[k]) && !"null".equals(manureItem[k]))
				{
					String[] manureValue = manureItem[k].split("\\,");

					String rowIndex		= manureValue[0];
					long manureTypeId =0;
					if(!"undefined".equals(manureValue[1]) && !"".equals(manureValue[1]))
						manureTypeId =  Long.parseLong(manureValue[1]);
					
					String ownBuy 	= manureValue[3];
					String ownProd 	= manureValue[4];
					String formula  = "";
					String mName    = "";
					try{
						mName        = manureValue[5];
						formula      = manureValue[6];
					}catch(Exception ec){
						
					}
					String mCostPerRai  = manureValue[7];
					String mPeriodTime  = manureValue[8];
					String manureDateStr   = manureValue[9];
					String mQtyPerRai   = manureValue[10];
					
					PlantManure pManure = new PlantManure();
					pManure.setPlantDetail(plantDetail);
					
					ManureType mType = new ManureType();
					mType = mHome.findByTypeId(manureTypeId);
					if(mType != null){
						pManure.setManureTypeId(mType.getTypeId());
					}
					
					if(mName.indexOf("^") > -1)
						mName = mName.replace('^', ',');
					pManure.setManureName(mName);
					
					if(formula.indexOf("^") > -1)
						formula = formula.replace('^', ',');
					pManure.setFormula(formula);	
					
					if(!"".equals(mQtyPerRai) && mQtyPerRai!=null)
						pManure.setQtyPerRai(Double.parseDouble(mQtyPerRai));
					if(!"".equals(mCostPerRai) && mCostPerRai!=null)
						pManure.setCostPerRai(Double.parseDouble(mCostPerRai));
					pManure.setMseq(Integer.parseInt(rowIndex));
					
					if("1".equals(mPeriodTime))
						pManure.setPeriod("ก่อนการเพาะปลูก");
					if("2".equals(mPeriodTime))
						pManure.setPeriod("ระหว่างการเพาะปลูก");
					if("3".equals(mPeriodTime))
						pManure.setPeriod("หลังการเพาะปลูก");
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date dateUse;
					try {
						if(!"".equals(manureDateStr)){
							dateUse = formatter.parse(manureDateStr);
							pManure.setDateUse(dateUse);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					if(!"1".equals(ownBuy)){
						pManure.setManureStatus("B");
						
					}if(!"1".equals(ownProd)){
						pManure.setManureStatus("P");
					}
					manureList.add(pManure);
				}
			}
		} 
		return manureList;
	}

	private List<PlantChemical> setChemiList(String chemiItems, PlantDetail plantDetail)
	{
		List<PlantChemical> chemiList = new ArrayList<PlantChemical>();
		ChemicalTypeHome cHome = new ChemicalTypeHome();
		String[] chemItem = chemiItems.split("\\|");
		
		if(chemItem !=null && chemItem.length>0){
			for(int i=0; i < chemItem.length; i++){
				if(chemItem[i] != null && !"".equals(chemItem[i]) && !"undefined".equals(chemItem[i]) && !"null".equals(chemItem[i]))
				{
					String[] chemValue = chemItem[i].split("\\,");
					
					PlantChemical chem = new PlantChemical();
					chem.setPlantDetail(plantDetail);
						
					ChemicalType cType = new ChemicalType();
					if(!"".equals(chemValue[1]) && chemValue[1]!=null)
						cType = cHome.findByTypeId(Long.parseLong(chemValue[1]));
					if(cType!=null){
						chem.setChemicalTypeId(cType.getTypeId());
						chem.setChemicalName(cType.getTypeName());
					}
					
					if(!"".equals(chemValue[3]) && chemValue[3]!=null){	
						String formula = "";
						if(chemValue[3].indexOf("^") > -1){
							formula = chemValue[3].replace('^', ',');
						}else{
							formula = chemValue[3];	
						}
						chem.setFormula(formula);
					}
					
					if(!"".equals(chemValue[4]) && chemValue[4]!=null)
						chem.setCostPerRai(Double.parseDouble(chemValue[4]));
					
					String cPeriodTime = chemValue[5];
					if("1".equals(cPeriodTime))
						chem.setPeriod("ก่อนการเพาะปลูก");
					if("2".equals(cPeriodTime))
						chem.setPeriod("ระหว่างการเพาะปลูก");
					if("3".equals(cPeriodTime))
						chem.setPeriod("หลังการเพาะปลูก");
					
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date dateUse;
					try {
						if(!"".equals(chemValue[6].replace("'", "")) && chemValue[6]!=null ){
							dateUse = formatter.parse(chemValue[6].replace("'", ""));
							chem.setDateUse(dateUse);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					if(!"".equals(chemValue[7]) && chemValue[7]!=null)
						chem.setQtyPerRai(Double.parseDouble(chemValue[7]));
					
					int rowIndex = Integer.parseInt(chemValue[0]);
					chem.setCseq(rowIndex);
					
					chemiList.add(chem);	
				}
			}
		} 
		return chemiList;
	}
	private List<AssetDetail> setAssetList(String assetItems, Plant plant)
	{
		List<AssetDetail> assetList = new ArrayList<AssetDetail>();
		AssetHome aHome = new AssetHome();
		String[] assetItem = assetItems.split("\\|");
		int cnt = 1;
		//double SUMTOTAL = 0;
		if(assetItem !=null && assetItem.length>0){
			for(int i=0; i < assetItem.length; i++){
				if(assetItem[i] != null && !"".equals(assetItem[i]) && !"undefined".equals(assetItem[i]) && !"null".equals(assetItem[i]))
				{
					String[] asstValue = assetItem[i].split("\\,");
					AssetDetail assetDetail = new AssetDetail();
				    assetDetail.setPlant(plant);
					
				    Asset asset = null;
					if(!"".equals(asstValue[1]) && asstValue[1]!=null)
						asset = aHome.findByAssetId(Long.parseLong(asstValue[1]));
					if(asset!=null){
						assetDetail.setAsset(asset);
						assetDetail.setAssetId(asset.getAssetId());
					}
						
					assetDetail.setSeq(cnt);
					if(asstValue[3] !=null && !"".equals(asstValue[3])){
					    String dateStr = asstValue[3];
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date date;
						try {
							date = formatter.parse(dateStr);
							assetDetail.setAssetDate(date); // set sale date
						} catch (ParseException e) {
							e.printStackTrace();
							  log.error(e,e);
						}
					}else{
						assetDetail.setAssetDate(new Date());
					}
		    	
					if(asstValue[4] !=null && !"".equals(asstValue[4])){
						 assetDetail.setAmount(Double.parseDouble(asstValue[4]));
						 SUMTOTAL +=  Double.parseDouble(asstValue[4]);	
					}
					
					assetList.add(assetDetail);
					
					cnt++;
				}
				
			}
		} 
		return assetList;
	}
	
	public ActionForward deletePlant(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		PlantForm eform = (PlantForm)form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantHome pHome = new PlantHome();

		try {	
			sf.getCurrentSession().beginTransaction();
			if(!"".equals(eform.getPlantId()) &&  !"0".equals(eform.getPlantId())) {
				Plant plant = pHome.findById(Long.parseLong(eform.getPlantId()));
				List<Plant> plantList = pHome.findByRefId(plant.getRefPlantId());
				if(plantList !=null && plantList.size() > 0){
					for(int i = 0; i < plantList.size(); i++){
						Plant refPlant = (Plant)plantList.get(i);
						pHome.delete(refPlant);
					}
				}
				// use delete cascade in foreign key to use when delete mom and then the child, too
				sf.getCurrentSession().getTransaction().commit();
				session.removeAttribute("plant");
				eform.setCmd("Delete");
		 		eform.setMsg(Utility.get("DELETE_SUCCESS")); //Delete Successful
			}
		} catch (Exception e) {
			eform.setMsg(Utility.get("DELETE_FAIL")); //Delete Failed
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			 log.error(e,e);
			log.error("Delete Plant failed : " + new Date() + " Error Description : " + e.getMessage());		
		}
		return retrievePlant(mapping, eform, request, response);
	}
	
	//Ajax Method
	public ActionForward getTarget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		long farmerGroupId = 0;
		if(!"".equals(eform.getFarmerGroupId()) && eform.getFarmerGroupId()!= null)
			farmerGroupId = Long.parseLong(eform.getFarmerGroupId());
		try{

			sf.getCurrentSession().beginTransaction();
			FarmerGroupHome home = new FarmerGroupHome();
			FarmerGroup group = home.findByFarmerGroupId(farmerGroupId);
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"farmerGroupName", "lastUpdateDate", "lastUpdateBy", "farmerGroupFarmer", "farmerGroupAddress", "checkBox", "linkImageEdit"});
		    String result = JSONArray.fromObject(group, jsonConfig).toString();
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
	
	
	//Ajax Method
	public ActionForward getProvinceInfoFromBranch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		long branchCode = Long.parseLong(eform.getBranchCode() == null?"0":eform.getBranchCode());
		try{

			sf.getCurrentSession().beginTransaction();
			ProvinceHome pHome = new ProvinceHome();
			BranchHome bHome = new BranchHome();
			Branch branch = bHome.findByBranchCode(branchCode);
		
			Province province= pHome.searchByProvinceNo(branch.getProvinceNo());
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"engName", "lastUpdateDate", "lastUpdateBy", "regionNo", "district", "region", "checkBox", "linkImageDel", "linkImageEdit", "regionName"});   

		    String result = JSONArray.fromObject(province, jsonConfig).toString();
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
	
	
	public ActionForward getPlantPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();		
			BreedGroup breedGroup = null;
			breedGroup = home.findByBreedGroupId(Long.parseLong(eform.getBreedGroupId()));
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    
		    jsonConfig.setExcludes(new String[]{"breedTypeId", "breedGroupId", "lastUpdateDate", "lastUpdateBy", "breedType", "checkBox", "linkImageEdit", "breedTypeName"});

		    String result = JSONArray.fromObject(breedGroup, jsonConfig).toString();
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
	
	
	public ActionForward getBankBranchInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			BankBranchHome home = new BankBranchHome();
			List bankBranchList = new ArrayList();
			List<Branch> branchList = new ArrayList<Branch>();
			if(!"".equals(eform.getBankId()))
				bankBranchList = home.filterByBankId(Long.parseLong(eform.getBankId()));
		    
			if(bankBranchList != null && bankBranchList.size() > 0)
			{
				for(int i = 0; i < bankBranchList.size(); i++){
					Object[] obj = (Object[])bankBranchList.get(i);
					Branch branch = new Branch();
					branch.setBranchCode(Long.parseLong(obj[0].toString()));
					branch.setBranchName(obj[1].toString());
					branchList.add(branch);
				}
			
			}
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"pbranchCode", "address", "tel", "fax", "seq", "lastUpdateDate", "lastUpdateBy", "regionNo", "provinceNo", "districtNo", "subDistrictNo", "manager", "moo", "soi", "street", "postCode", "branchType", "childBranch", "parentBranch", "checkBox", "linkImageEdit", "pbranchName", "provinceName", "districtName", "subDistrictName"});  
		    
		    String result = JSONArray.fromObject(branchList, jsonConfig).toString();
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
	
	public ActionForward getBuyerAddressInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			BuyerHome home = new BuyerHome();	
			List buyerList = new ArrayList();
			if(!"".equals(eform.getBuyerId()))
				buyerList = home.searchByBuyerId(Long.parseLong(eform.getBuyerId()));
			Buyer buyer = new Buyer();
			if(buyerList!=null && buyerList.size() > 0){
				Object[] obj = (Object[])buyerList.get(0);
				buyer.setBuyerId(Long.parseLong(obj[0].toString()));
				buyer.setBuyerName(obj[1].toString());
				buyer.setAddressNo(obj[2].toString());
				buyer.setMoo(Integer.parseInt(obj[3].toString()));
				buyer.setSoi(obj[4].toString());
				buyer.setRoad(obj[5].toString());
				
				String address = "";
				address = buyer.getAddressNo() + "";
				if(buyer.getMoo() > 0)
					address += " หมู่ที่ " + buyer.getMoo();
				if(!"".equals(buyer.getSoi()))
					address += " ซ. " + buyer.getSoi();
				if(!"".equals(buyer.getRoad()))
					address += " ถ. " + buyer.getRoad();
				
				buyer.setBuyerAddress(address);
				buyer.setProvinceNo(Long.parseLong(obj[6].toString()));
				buyer.setDistrictNo(Long.parseLong(obj[7].toString()));
				buyer.setSubDistrictNo(Long.parseLong(obj[8].toString()));
				buyer.setProvinceName(obj[9].toString());
				buyer.setDistrictName(obj[10].toString());
				buyer.setSubDistrictName(obj[11].toString());
			}
		
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"lastUpdateDate","lastUpdateBy","addressNo","moo","soi","road","mobile","tel","fax","placeName","placeAddressNo","placeMoo", "placeSoi","placeRoad", "placeRegionNo","placeProvinceNo","placeDistrictNo", "placeSubDistrictNo", "breedTypeId", "breedGroupId", "qualification", "quantity","paymentCondition","checkBox","linkImageEdit"});   

		    String result = JSONArray.fromObject(buyer, jsonConfig).toString();
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
	
	//Ajax Method
	public ActionForward getDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();	
			List dtrcList = new ArrayList();
			if(!"".equals(eform.getProvinceNo()))
				dtrcList = home.filterByProvinceNo(Long.parseLong(eform.getProvinceNo()));
		    
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","provinceNo","subDistrictNo","provinceThai","provinceEng","subDistrictThai","subDistrictEng","postCode"});   

		    String result = JSONArray.fromObject(dtrcList, jsonConfig).toString();
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
	
	//Ajax Method
	public ActionForward getSubDistrictInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			List subList = new ArrayList();				
			if(!"".equals(eform.getDistrictNo()) && !"".equals(eform.getProvinceNo()))
				subList = home.filterByDistrictNo(Long.parseLong(eform.getDistrictNo()), Long.parseLong(eform.getProvinceNo()) );
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","provinceNo","districtNo","provinceThai","provinceEng","districtThai","districtEng"});   

		    String result = JSONArray.fromObject(subList, jsonConfig).toString();
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
	
	
	public ActionForward getPostCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			ProvinceDistrictHome home = new ProvinceDistrictHome();		
			ProvinceDistrict provinceDistrict = new ProvinceDistrict();
			if(!"".equals(eform.getDistrictNo()) && !"".equals(eform.getProvinceNo()) &&  !"".equals(eform.getSubDistrictNo()))
				provinceDistrict = home.getPostCode(Long.parseLong(eform.getDistrictNo()), Long.parseLong(eform.getProvinceNo()), Long.parseLong(eform.getSubDistrictNo()) );
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"regionNo","regionName","provinceNo","districtNo","provinceThai","provinceEng","districtThai","districtEng", "subDistrictThai", "subDistrictEng", "subDistrictNo"});   

		    String result = JSONArray.fromObject(provinceDistrict, jsonConfig).toString();
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
	
	
	public ActionForward getBreedGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			BreedGroupHome home = new BreedGroupHome();		
			List<BreedGroup> breedGroup = null;
			if(!"".equals(eform.getBreedTypeId()))
				breedGroup = home.findByBreedTypeId(Long.parseLong(eform.getBreedTypeId()));
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"breedTypeId", "period", "plantPeriodFrom", "plantPeriodTo", "forcastPeriodFrom", "forcastPeriodTo", "lastUpdateDate", "lastUpdateBy", "breedType", "checkBox", "linkImageEdit", "breedTypeName"});
		    String result = JSONArray.fromObject(breedGroup, jsonConfig).toString();
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
	
	
	// Ajax Method
	public ActionForward getFarmerGroupChild(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();
			FarmerGroupChildHome home = new FarmerGroupChildHome();
			FarmerGroupChild child = null;
			if(!"".equals(eform.getFarmerGroupId()))
				child = home.findByFarmerGroupId(Long.parseLong(eform.getFarmerGroupId()));
			FarmerGroupHome fgHome = new FarmerGroupHome();
			FarmerGroup fGroup = null;
			if(child != null)
				fGroup = fgHome.findByFarmerGroupId(child.getChildFarmerGroupId());
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"branchCode", "lastUpdateDate", "lastUpdateBy", "target", "strTarget", "farmerGroupType", "objective", "addressNo", "moo", "village", "soi", "road", "mobile", "tel", "fax", "createDate", "createBy", "farmerGroupFarmer", "farmerGroupAddress", "farmerGroupChild", "farmerGroupTeam", "checkBox", "linkImageEdit", "linkImageFarmerGroupEdit", "provinceName", "districtName", "subDistrictName", "joinCooperative", "countFarmer"});
		    String result = JSONArray.fromObject(fGroup, jsonConfig).toString();
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
	
	
	//Ajax Method
	public ActionForward getFarmer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		HttpSession session = request.getSession();
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		List<Branch> branchList = (List) session.getAttribute("userBranchList");
		try{
			sf.getCurrentSession().beginTransaction();
			FarmerHome home = new FarmerHome();		
			PlantHome pHome = new PlantHome();
			Farmer farmer = new Farmer();
			List fList = new ArrayList();
			List pList = null;
			if(!"".equals(eform.getBreedTypeId())){
				pList = pHome.findByIdCard(eform.getIdCard());
				if(pList.size() > 0){
				    fList = home.findByCriteriaAndGetCustomerNo(eform.getIdCard(), "", "",branchList,0);
				}else {
					fList = home.findByCriteria(eform.getIdCard(), "", "",branchList,0);
				}
				if(fList != null && fList.size() > 0)
				{
					Object[] obj = (Object[])fList.get(0);
					farmer.setFullPrefix(obj[0].toString());
					farmer.setIdCard(obj[1].toString());
					farmer.setAbbrPrefix(obj[3].toString());
					farmer.setFirstName(obj[4].toString());
					farmer.setLastName(obj[5].toString());
					farmer.setAddressNo(obj[6].toString());
					farmer.setMoo(Integer.parseInt(obj[7].toString()));
					farmer.setPostCode(Integer.parseInt(obj[8].toString()));
					if(obj[9] != null)
						farmer.setTel(obj[9].toString());
					if(obj[10] != null)
						farmer.setMobile(obj[10].toString());
					farmer.setRegionNo(Long.parseLong(obj[13].toString()));
					farmer.setProvinceNo(Long.parseLong(obj[14].toString()));
					farmer.setDistrictNo(Long.parseLong(obj[15].toString()));
					farmer.setSubDistrictNo(Long.parseLong(obj[16].toString()));
					farmer.setBranchCode(Long.parseLong(obj[17].toString()));
					if(pList.size() > 0 && obj[18] != null)
						farmer.setCustomerNo(obj[18].toString());
					if(obj[23] != null)
						farmer.setEmail(obj[23].toString());
					else
						farmer.setEmail("");
					
				}
			}	
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();   
		    jsonConfig.setExcludes(new String[]{"createDate", "createBy","checkBox", "linkImageEdit"});
		    String result = JSONArray.fromObject(farmer, jsonConfig).toString();
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
	
	public ActionForward getFarmerGroupFromBranch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();	
			FarmerGroupHome gHome = new FarmerGroupHome();
			List<FarmerGroup> fGroupList = gHome.getFarmerGroupId(Long.parseLong(eform.getBranchCode()));
			
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();    
		    jsonConfig.setExcludes(new String[]{"branchCode", "lastUpdateDate", "lastUpdateBy", "farmerGroupFarmer", "farmerGroupAddress", "checkBox", "linkImageEdit", "linkImageFarmerGroupEdit" });
		    String result = "";
		    if(fGroupList != null && fGroupList.size() > 0)
		       result = JSONArray.fromObject(fGroupList, jsonConfig).toString();
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
	
	
	public ActionForward getSubPrepareArea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantForm eform = (PlantForm)form;
		try{
			sf.getCurrentSession().beginTransaction();	
			PrepareAreaHome pAreaHome = new PrepareAreaHome();
			List subAreaList = pAreaHome.findSubPrepareArea(Long.parseLong(eform.getPrepareAreaId()));
			
			List areaList = new ArrayList();
			if(subAreaList != null && subAreaList.size() > 0)
			{
				for(int i = 0; i< subAreaList.size(); i++){
					PrepareArea area = new PrepareArea();
					Object[] obj = (Object[])subAreaList.get(i);
					area.setPrepareAreaId(Long.parseLong(obj[0].toString()));
					area.setPrepareAreaName(obj[1].toString());
					areaList.add(area);
				}
			}
		
			response.setCharacterEncoding("UTF-8");
		    PrintWriter wt = response.getWriter();
		    JsonConfig jsonConfig = new JsonConfig();    
		    jsonConfig.setExcludes(new String[]{ "pprepareAreaId", "pprepareAreaName", "breedTypeId", "breedGroupId", "breedTypeName", "breedGroupName", "lastUpdateDate", "lastUpdateBy", "checkBox", "childPrepareArea", "parentPrepareArea", "linkImageEdit" });
		    String result = "";
		    if(areaList != null && areaList.size() > 0)
		       result = JSONArray.fromObject(areaList, jsonConfig).toString();
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
	
	private Farmer setFarmerValue(HttpServletRequest request,ActionForm form, long regionNo, long provinceNo, long districtNo, long subDistrictNo, long branchCode){
		Farmer farmer = new Farmer();
		PlantForm eform = (PlantForm)form;
		User userLogin = (User)request.getSession().getAttribute("userLogin");
		try{  
			Calendar cal = Calendar.getInstance();
			int moo = 0;
			int postCode = 0;
		
			if(!"".equals(eform.getMoo()))
		 		moo = Integer.parseInt(eform.getMoo() == null?"0":eform.getMoo());
		 	if(!"".equals(eform.getPostCode()))
		 		postCode = Integer.parseInt(eform.getPostCode() == null ? "0":eform.getPostCode());
		 
			farmer = new Farmer();
			farmer.setIdCard(eform.getIdCard());
	 		farmer.setEffectiveDate(cal.getTime());
	 		farmer.setCreateBy(userLogin.getUserName());
	 		farmer.setCreateDate(cal.getTime());
	 		farmer.setAbbrPrefix(eform.getAbbrPrefix());
	 		farmer.setFirstName(eform.getFirstName());
	 		farmer.setLastName(eform.getLastName());
	 		farmer.setAddressNo(eform.getAddressNo());
	 		farmer.setMoo(moo);
	 		farmer.setPostCode(postCode);
	 		farmer.setTel(eform.getTel());
	 		farmer.setMobile(eform.getMobile());
	 		farmer.setEmail(eform.getEmail());
	 		farmer.setRegionNo(regionNo);
	 		farmer.setProvinceNo(provinceNo);
			farmer.setDistrictNo(districtNo);
			farmer.setSubDistrictNo(subDistrictNo);
			farmer.setBranchCode(branchCode);
			farmer.setSoi(eform.getSoi());
			farmer.setStreet(eform.getStreet());
			farmer.setVillage(eform.getVillage());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);
		}
		return farmer;
	}
	
	// ------------
	public ActionForward addPlant(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User userLogin = (User)session.getAttribute("userLogin");
		PlantForm eform = (PlantForm)form;
		Calendar cal = Calendar.getInstance();
		 
		SessionFactory sf = HibernateHome.getSessionFactory();
	    PlantHome plantHome = new PlantHome();
	    FarmerHome fHome = new FarmerHome();
	    FarmerGroupFarmerHome fgfHome = new FarmerGroupFarmerHome();
	    SubDistrictHome subDistrictHome = new SubDistrictHome();
	    BreedTypeHome bHome = new BreedTypeHome();
	    FarmerGroupHome fgroupHome = new FarmerGroupHome();
	    BankBranchHome bBranchHome = new BankBranchHome();
	    BreedGroupHome ghome = new BreedGroupHome();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
	    String pageForward = "plant";
	     
	 	sf.getCurrentSession().beginTransaction();	
	 	 
	    Plant plant = new Plant();
		Farmer farmer = null;
		FarmerGroupFarmer fgf = null;
	    FarmerGroup fgroup = null;
	    BankBranch bankBranch = null;
	     
		double target = 0.0, target2 = 0.0;
		int bookNo = 0;
		long plantId = 0, branchCode = 0, farmerGroupId = 0, farmerGroupId2 = 0, breedTypeId = 0, refPlantId = 0, cooperativeId = 0, bankId = 0, geRefPlantId = 0, breedGroupId = 0;
		String msg = "", cmd = "", idCard =  "", mobile = "", tel= "", email = "", customerFarmerGroupNo = "", customerCooperativeNo = "", accountName = "", accountNo = "";		
		String documentNo = "", plantStatus = "", reason = "";
		
	    try{
	    	int plantYear = Integer.parseInt(eform.getPlantYear()==null?"0":eform.getPlantYear());
			int plantNo = Integer.parseInt(eform.getPlantNo()==null?"0":eform.getPlantNo());
			
			String customerNo = eform.getCustomerNo();
			if(!"".equals(eform.getIdCard()))
				idCard = eform.getIdCard();
	    	if(!"".equals(eform.getBreedTypeId()))
	    		breedTypeId = Long.parseLong(eform.getBreedTypeId() == null?"0":eform.getBreedTypeId());
	    	if(!"".equals(eform.getPlantId()))
	    	    plantId = Long.parseLong(eform.getPlantId() == null?"0":eform.getPlantId());
			if(!"".equals(eform.getBranchCode()))
		 	    branchCode = Long.parseLong(eform.getBranchCode() == null?"0":eform.getBranchCode());
		 	if(!"".equals(eform.getTel()))
		 		tel = eform.getTel() == null?"":eform.getTel();
		 	
		 	if(eform.getFarmerGroupId() != null && !"".equals(eform.getFarmerGroupId())){
		 		if(!"0".equals(eform.getFarmerGroupId()))
		 			farmerGroupId = Long.parseLong(eform.getFarmerGroupId() == null?"0":eform.getFarmerGroupId());
		 	}
		 	
			if(eform.getTarget()!= null && !"".equals(eform.getTarget())){
				String strTarget = eform.getTarget().replaceAll("[^\\d.]+", "");
		 	    target = Double.parseDouble(strTarget);
			}
			
			if(eform.getChildFarmerGroupId() != null && !"".equals(eform.getChildFarmerGroupId())){
				if(!"0".equals(eform.getChildFarmerGroupId()))
					farmerGroupId2 = Long.parseLong(eform.getChildFarmerGroupId() == null?"0":eform.getChildFarmerGroupId());
			}
			
			if(!"".equals(eform.getBreedGroupId()))
		 		breedGroupId = Long.parseLong(eform.getBreedGroupId() == null?"0":eform.getBreedGroupId());
		 	
			if(eform.getRefPlantId() !=null && !"".equals(eform.getRefPlantId()))
		 		geRefPlantId = Long.parseLong(eform.getRefPlantId() == null?"0":eform.getRefPlantId());
		 	
		 	if(eform.getBankId() != null && !"".equals(eform.getBankId()))
		 		bankId = Long.parseLong(eform.getBankId());
		 	
		 	if(eform.getBookNo() != null && !"".equals(eform.getBookNo()))
		 		bookNo = Integer.parseInt(eform.getBookNo());
		 	
		 	if(eform.getPlantStatus() != null && !"".equals(eform.getPlantStatus()))
		 		plantStatus = eform.getPlantStatus();
		 	
		 	if("0".equals(eform.getPlantStatus()) && !"".equals(eform.getReason()) )
		 		reason = eform.getReason();
		 		
		 	eform.setRefPlantId(geRefPlantId+"");
		 	
			// <-- starting new transaction -->
		 	sf.getCurrentSession().beginTransaction();
		 	List cntList = null;
		 	List maxPlantList = null;
		 	String custNo = "";
		 	int canSave = 1;
		 	long refPId = 0;
		 	long maxPlantId = 0;
		 	boolean isDuplicate = false; 
		 	boolean isOver = false;
		 	int getPlantNo = 0;
		 	if("Add".equals(eform.getStatus())){
		 		// ------- เช็คการคีย์เพิ่มข้อมูลเกษตรกรผู้เพาะปลูกซ้ำ	09.07.2014 -------
		 		maxPlantList = plantHome.getRefPlantId(eform.getIdCard(), 0);
		 		if(maxPlantList != null && maxPlantList.size() > 0){
		 			for(int i = 0; i < maxPlantList.size(); i++)
		 			{	
		 				Plant pl = new Plant();
		 				BigInteger obj = (BigInteger)maxPlantList.get(i);
		 				pl.setRefPlantId(obj.longValue());
		 				maxPlantId = plantHome.getMaxPlantIdFromRefPlant(eform.getIdCard(), pl.getRefPlantId());
		 				isDuplicate = plantHome.isDuplicate(breedTypeId, plantYear, plantNo, eform.getIdCard(), pl.getRefPlantId(), maxPlantId);
		 				if(isDuplicate == true)
		 					break;	
		 			}
		 		}
		 		
			 	if(isDuplicate == true){
			 		canSave = 0;
			 		 msg = "ข้อมูลเกษตรกรผู้เพาะปลูกซ้ำ กรุณาตรวจสอบชนิดพืช, ปีการผลิต, ครั้งที่ และเลขที่บัตรประชาชน";
			 		 cmd = "Fail";
			 		 log.error("Duplicate BreedType, PlantYear, PlantNo and ID");
			 	}else{
			 		// --- เช็คการใส่ครั้งที่เข้ามา  26.08.2014 --- //
			 		BreedType btype = bHome.findByBreedTypeId(breedTypeId);
			 		int maxPerYear = btype.getMaxPerYear();
			 	    if(plantNo > maxPerYear) {
			 			canSave = 0;
			 			msg = "จำนวนครั้งในการปลูก"+ btype.getBreedTypeName() +" ไม่ควรเกิน " + maxPerYear +" ครั้งต่อปี";
						cmd = "Fail";
						log.error("จำนวนครั้งในการปลูก"+ btype.getBreedTypeName() +" ไม่ควรเกิน " + maxPerYear +" ครั้งต่อปี");
			 		} 
			 	}
		 	} else if("Edit".equals(eform.getStatus())) {
		 		Plant p = null;
	 			p = plantHome.findById(plantId);
	 		    refPId = p.getRefPlantId();
	 		    
	 		    if(geRefPlantId ==0){
	 		    	geRefPlantId = refPId;
	 		    	eform.setRefPlantId(refPId+"");
	 		    }
	 		   
	 		    if("Yes".equals(eform.getSetStatusCmd()) || "Change".equals(eform.getSetStatusCmd())){
	 		    	isDuplicate =	plantHome.isDuplicateMainOfPlant(p.getBreedTypeId(), p.getPlantYear(), p.getPlantNo(), p.getIdCard(), p.getRefPlantId());
		 		}else{
	 		    	isDuplicate =	plantHome.isDuplicateMainOfPlant(breedTypeId, plantYear, plantNo, idCard, geRefPlantId);
	 		    }
				if(isDuplicate == true) {
					canSave = 0;
					msg = "ข้อมูลเกษตรกรผู้เพาะปลูกซ้ำ กรุณาตรวจสอบชนิดพืช, ปีการผลิต, ครั้งที่ และเลขที่บัตรประชาชน";
					cmd = "Fail";
					log.error("Duplicate BreedType, PlantYear, PlantNo and ID");
				} else {
					// --- เช็คการใส่ครั้งที่เข้ามา  26.08.2014 --- //
					BreedType btype = new BreedType();
					if("Yes".equals(eform.getSetStatusCmd()) || "Change".equals(eform.getSetStatusCmd())){
						//BreedType btype = bHome.findByBreedTypeId(p.getBreedTypeId());
						btype = bHome.findByBreedTypeId(p.getBreedTypeId());
					}else{
						btype = bHome.findByBreedTypeId(breedTypeId);
					}
					int maxPerYear = btype.getMaxPerYear();
					if("Yes".equals(eform.getSetStatusCmd()) || "Change".equals(eform.getSetStatusCmd())){
						if(p.getPlantNo() > maxPerYear) {
							canSave = 0;
				 			msg = "จำนวนครั้งในการปลูก"+ btype.getBreedTypeName() +" ไม่ควรเกิน " + maxPerYear +" ครั้งต่อปี";
							cmd = "Fail";
							log.error("จำนวนครั้งในการปลูก"+ btype.getBreedTypeName() +" ไม่ควรเกิน " + maxPerYear +" ครั้งต่อปี");
						}
					}else{
						if(plantNo > maxPerYear) {
				 			canSave = 0;
				 			msg = "จำนวนครั้งในการปลูก"+ btype.getBreedTypeName() +" ไม่ควรเกิน " + maxPerYear +" ครั้งต่อปี";
							cmd = "Fail";
							log.error("จำนวนครั้งในการปลูก"+ btype.getBreedTypeName() +" ไม่ควรเกิน " + maxPerYear +" ครั้งต่อปี");
						}
					}
				}
				
				CloseDueHome cHome = new CloseDueHome();
				CloseDue cdObj = cHome.findByCriteria(p.getPlantYear(), p.getPlantNo(), p.getFarmerGroupId2());
				if(cdObj != null){
					canSave = 0;
					msg = "รายการนี้มีการปิดยอดแล้ว";//msg = "รายการนี้มีการปิดยอดแล้ว";
					cmd = "Close";
					PlantListForm plantListForm = new PlantListForm();
					plantListForm.setErrMessage(msg);
					request.setAttribute("PlantListForm", plantListForm);
				}
		 	}
		 	
			if(canSave > 0){
				Plant tempPlant = null;
				if(plantId > 0){
					tempPlant = plantHome.findById(plantId);
					refPlantId = tempPlant.getRefPlantId();
		 		}
			    
			    plant.setPlantId(plantId);
		 		plant.setCreateBy(userLogin.getUserName());  
		 		plant.setCreateDate(cal.getTime());		
		 		
		 		if(refPlantId > 0)
		 			plant.setRefPlantId(refPlantId);
		 		
		 		if("Yes".equals(eform.getSetStatusCmd())){ 
		 			pageForward = "list";
		 			// in case of edit information from set status popup
		 			int strLimit = (eform.getReason().length()<100)?eform.getReason().length():100;
		 			tempPlant.setReason(eform.getReason().substring(0, strLimit));
		 			tempPlant.setPlantStatus(eform.getPlantStatus());
		 		
		 			plantHome.save(tempPlant);
		 			//plantHome.persist(tempPlant);
		 		} else if("Change".equals(eform.getSetStatusCmd())) {
		 			pageForward = "list";
			  	 	plant.setBreedTypeId(tempPlant.getBreedTypeId());
				 	plant.setAccountNo(tempPlant.getAccountNo());  // เลขที่บัญชี
					plant.setAccountName(tempPlant.getAccountName());  // ชื่อบัญชี
					plant.setSeason(tempPlant.getSeason()); // ฤดูกาลปลูก
					plant.setCertificate(tempPlant.getCertificate());// เลขที่ใบรับรองเกษตรกร
					plant.setRegisterNo(tempPlant.getRegisterNo()); // เลขที่ลงทะเบียน
					plant.setBookNo(tempPlant.getBookNo()); // เล่มที่
					plant.setDocumentNo(tempPlant.getDocumentNo()); // เลขที่
					plant.setRegisterDate(tempPlant.getRegisterDate());
					plant.setRegisterBy(tempPlant.getRegisterBy());
					plant.setFarmerGroupId(tempPlant.getFarmerGroupId()); // กลุ่มเกษตกร
					plant.setMemberNo(tempPlant.getMemberNo()); // เลขที่สมาชิกกลุ่มเกษตรกร
					plant.setTarget(tempPlant.getTarget()); // เป้าหมายกลุ่มเกษตรกร
					plant.setFarmerGroupId2(tempPlant.getFarmerGroupId2()); // สหกรณ์
				    plant.setFlag(tempPlant.getFlag());
				    plant.setPlantYear(tempPlant.getPlantYear());
				    plant.setPlantNo(tempPlant.getPlantNo());
				    plant.setFta(tempPlant.getFta());
				    
				    plant.setPlantStatus(tempPlant.getPlantStatus());
					plant.setReason(tempPlant.getReason());
				    
				    plant.setRegister(tempPlant.getRegister());
				    plant.setMemberType(tempPlant.getMemberType());
				    
				 	// -- set into Farmer --
		 			plant.setIdCard(tempPlant.getIdCard());
			 		plant.setEffectiveDate(tempPlant.getEffectiveDate());
				 	
				 	// ----------------set BankBranch--------------------------
					plant.setBankId(tempPlant.getBankId());
					plant.setBranchCode(tempPlant.getBranchCode());
			
					if(tempPlant.getFarmerFamily().size() > 0){
		        		Set<FarmerFamily> familySet = new HashSet<FarmerFamily>();
		        		List<FarmerFamily> listFamily = new ArrayList<FarmerFamily>(tempPlant.getFarmerFamily());
	        			
	        			for(int i=0; i < listFamily.size(); i++){
	        				FarmerFamily obj = listFamily.get(0);
							if(obj != null){
								FarmerFamily family = new FarmerFamily();
								if(plant != null){
									family.setPlant(plant);
									family.setPlantId(plant.getPlantId());
								}
								family.setIdCard(obj.getIdCard());
								family.setFirstName(obj.getFirstName());
								family.setLastName(obj.getLastName());
								familySet.add(family);
							}
						}
		        		plant.setFarmerFamily(familySet);
			        }

					// Landright
					if(tempPlant.getLandright().size() > 0){
						List<LandRight> landrightList = new ArrayList<LandRight>();
						int numLandright = 1;
						for(int i=0; i < tempPlant.getLandright().size(); i++)
						{
							LandRight landright = tempPlant.getLandright().get(i);
							if(landright!=null) {
								LandRight land = new LandRight();
								land.setPlant(plant);
								land.setPlantDetail(new HashSet<PlantDetail>());

								land.setSeq(numLandright);
								land.setDocNo(landright.getDocNo());
								land.setTypeId(landright.getTypeId());
								land.setMoo(landright.getMoo());
								land.setRegionNo(landright.getRegionNo());
								land.setProvinceNo(landright.getProvinceNo());
								land.setDistrictNo(landright.getDistrictNo());
								land.setSubDistrictNo(landright.getSubDistrictNo());
								land.setOwnRent(landright.getOwnRent());
								land.setRentPrice(landright.getRentPrice());
								land.setDocRai(landright.getDocRai());
								land.setDocNgan(landright.getDocNgan());
								land.setDocWah(landright.getDocWah());
								land.setWaterSource(landright.getWaterSource());
								land.setIrrigationAreaId(landright.getIrrigationAreaId());
								land.setLandStatus(landright.getLandStatus());
								land.setLandType(landright.getLandType());
								land.setLandTypeOther(landright.getLandTypeOther());

								/// >> plantDetail <<
								if(landright.getPlantDetail().size() > 0){
									Set<PlantDetail> detailSet =  new HashSet<PlantDetail>();
									List<PlantDetail> plantDetailList = new ArrayList<PlantDetail>(landright.getPlantDetail());

				 					for(int j=0; j < plantDetailList.size(); j++)
				 					{
				 						PlantDetail plantDetail = plantDetailList.get(j);
				 						if(plantDetail!=null) {
				 							PlantDetail detail = new PlantDetail();
				 							
				 							detail.setLandRight(land); // set PlantDetail into LandRight
						 					
					 						detail.setBreedGroup(plantDetail.getBreedGroup());  // set PlantDetail into BreedGroup
					 						detail.setBreedTypeId(plantDetail.getBreedTypeId());
					 						detail.setBreedGroupId(plantDetail.getBreedGroupId());
					 						detail.setQtyPerRai(plantDetail.getQtyPerRai());
					 						detail.setCostPerRai(plantDetail.getCostPerRai());
					 						detail.setSeedFrom(plantDetail.getSeedFrom());
					 						detail.setSeedSelect(plantDetail.getSeedSelect());
					 						detail.setCooperativeId(plantDetail.getCooperativeId());
					 						detail.setNoOfSeed(plantDetail.getNoOfSeed());
					 						detail.setPlantObjective(plantDetail.getPlantObjective());
					 						detail.setQualification(plantDetail.getQualification());
					 						detail.setStandard(plantDetail.getStandard());
					 						detail.setPrepareArea(plantDetail.getPrepareArea());
					 						detail.setPlantMethod(plantDetail.getPlantMethod());
					 						detail.setPrepareAreaOther(plantDetail.getPrepareAreaOther());
					 						detail.setPrepareManure(plantDetail.getPrepareManure());
					 						detail.setPlantRai(plantDetail.getPlantRai());
					 						detail.setPlantNgan(plantDetail.getPlantNgan());
					 						detail.setPlantWah(plantDetail.getPlantWah());
					 						detail.setPlantDate(plantDetail.getPlantDate());
					 						detail.setForecastDate(plantDetail.getForecastDate());
					 						detail.setForecastRecord(plantDetail.getForecastRecord());
					 						
					 						// -- PlantManure --
					 						List<PlantManure> plantManure = new ArrayList<PlantManure>();
					 						for(int m=0;m<plantDetail.getPlantManure().size();m++) {
					 							List<PlantManure> plantManureList =  new ArrayList<PlantManure>(plantDetail.getPlantManure());
					 							if(plantManureList!=null) {
					 								PlantManure mDetail = plantManureList.get(m);
					 								PlantManure manure = new PlantManure();
					 								manure.setPlantDetail(mDetail.getPlantDetail());
					 								manure.setManureName(mDetail.getManureName());
					 								manure.setFormula(mDetail.getFormula());
					 								manure.setQtyPerRai(mDetail.getQtyPerRai());
					 								manure.setCostPerRai(mDetail.getCostPerRai());
					 								manure.setMseq(mDetail.getMseq());
					 								manure.setPeriod(mDetail.getPeriod());
					 								manure.setDateUse(mDetail.getDateUse());
					 								manure.setManureStatus(mDetail.getManureStatus());
					 								manure.setBreedGroupId(mDetail.getBreedGroupId());
					 								manure.setBreedTypeId(mDetail.getBreedTypeId());
					 								manure.setDocNo(mDetail.getDocNo());
					 								manure.setTypeId(mDetail.getTypeId());
					 								manure.setSeq(mDetail.getSeq());
					 								manure.setManureTypeId(mDetail.getManureTypeId());
					 								
					 								plantManure.add(manure);
					 							}
					 						}
					 						detail.setPlantManure(plantManure); // set list of landright into plantdetail
					 						
					 						// -- PlantChemical --
					 						List<PlantChemical> plantChemical = new ArrayList<PlantChemical>();
					 						for(int m=0;m<plantDetail.getPlantChemical().size();m++) {
					 							List<PlantChemical> plantChemicalList =  new ArrayList<PlantChemical>(plantDetail.getPlantChemical());
					 							if(plantChemicalList!=null) {
					 								PlantChemical pcDetail = plantChemicalList.get(m);
					 								PlantChemical chemical = new PlantChemical();
					 								chemical.setPlantDetail(pcDetail.getPlantDetail());
					 								chemical.setFormula(pcDetail.getFormula());
					 								chemical.setChemicalName(pcDetail.getChemicalName());
					 								chemical.setQtyPerRai(pcDetail.getQtyPerRai());
					 								chemical.setCostPerRai(pcDetail.getCostPerRai());
					 								chemical.setCseq(pcDetail.getCseq());
					 								chemical.setPeriod(pcDetail.getPeriod());
					 								chemical.setDateUse(pcDetail.getDateUse());
					 								chemical.setBreedGroupId(pcDetail.getBreedGroupId());
					 								chemical.setBreedTypeId(pcDetail.getBreedTypeId());
					 								chemical.setDocNo(pcDetail.getDocNo());
					 								chemical.setTypeId(pcDetail.getTypeId());
					 								chemical.setSeq(pcDetail.getSeq());
					 								chemical.setChemicalTypeId(pcDetail.getChemicalTypeId());
					 								
					 								plantChemical.add(chemical);
					 							}
					 						}
					 						detail.setPlantChemical(plantChemical);
					 						
					 						// -- CostDetail --
					 						Set<CostDetail> costDetail = new HashSet<CostDetail>();
					 						for(int m=0;m<plantDetail.getCostDetail().size();m++) {
					 							List<CostDetail> costDetailList =  new ArrayList<CostDetail>(plantDetail.getCostDetail());
					 							if(costDetailList!=null) {
					 								CostDetail cDetail = costDetailList.get(m);
					 								CostDetail cost = new CostDetail();
					 								cost.setPlantDetail(cDetail.getPlantDetail());
					 								cost.setCostId(cDetail.getCostId());
					 								cost.setBreedGroupId(cDetail.getBreedGroupId());
					 								cost.setBreedTypeId(cDetail.getBreedTypeId());
					 								cost.setAmount(cDetail.getAmount());
					 								cost.setSeq(cDetail.getSeq());
					 								cost.setDocNo(cDetail.getDocNo());
					 								cost.setTypeId(cDetail.getTypeId());
					 								cost.setCostDate(cDetail.getCostDate());
					 								
					 								costDetail.add(cost);
					 							}
					 						}
					 						detail.setCostDetail(costDetail);
					 						
					 						// -- HarvestDetail is in plantdetail --
					 						Set<HarvestDetail> harvestDetail = new HashSet<HarvestDetail>();
					 						for(int m=0;m<plantDetail.getHarvestDetail().size();m++) {
					 							List<HarvestDetail> harvestDetailList =  new ArrayList<HarvestDetail>(plantDetail.getHarvestDetail());
					 							if(harvestDetailList!=null) {
					 								HarvestDetail hDetail = harvestDetailList.get(m);
					 								HarvestDetail harvest = new HarvestDetail();
					 								harvest.setPlantDetail(hDetail.getPlantDetail());
					 								harvest.setHarvestDate(hDetail.getHarvestDate());
					 								harvest.setTotalHarvest(hDetail.getTotalHarvest());
					 								harvest.setRemainSell(hDetail.getRemainSell());
					 								harvest.setConsumeProduct(hDetail.getConsumeProduct());
					 								harvest.setBreedProduct(hDetail.getBreedProduct());
					 								
					 								harvest.setBreedGroupId(hDetail.getBreedGroupId());
					 								harvest.setBreedTypeId(hDetail.getBreedTypeId());
					 								harvest.setDocNo(hDetail.getDocNo());
					 								harvest.setTypeId(hDetail.getTypeId());
					 								harvest.setSeq(hDetail.getSeq());
					 								
					 								harvestDetail.add(harvest);
					 							}
					 						}
					 						detail.setHarvestDetail(harvestDetail);
					 						
					 					    // SaleDetail is in plantdetail
					 						Set<SellingDetail> sellingDetail = new HashSet<SellingDetail>();
					 						for(int m=0;m<plantDetail.getSellingDetail().size();m++) {
					 							List<SellingDetail> sellingDetailList =  new ArrayList<SellingDetail>(plantDetail.getSellingDetail());
					 							if(sellingDetailList!=null) {
					 								SellingDetail sDetail = sellingDetailList.get(m);
					 								SellingDetail sell = new SellingDetail();
					 								sell.setPlantDetail(sDetail.getPlantDetail());
					 								sell.setBreedTypeId(sDetail.getBreedTypeId());
					 								sell.setBreedGroupId(sDetail.getBreedGroupId());
					 								sell.setDocNo(sDetail.getDocNo());
					 								sell.setTypeId(sDetail.getTypeId());
					 								sell.setSeq(sDetail.getSeq());
		 											sell.setSaleDate(sDetail.getSaleDate());
					 								sell.setSaleSeq(sDetail.getSaleSeq());
		 											sell.setSaleCrop(sDetail.getSaleCrop());
					 								sell.setSalePrice(sDetail.getSalePrice());
					 								sell.setAmount(sDetail.getAmount());
					 								sell.setBuyer(sDetail.getBuyer());
				 									sell.setRegionNo(sDetail.getRegionNo());
				 									sell.setProvinceNo(sDetail.getProvinceNo());
				 									sell.setDistrictNo(sDetail.getDistrictNo());
				 									sell.setSubDistrictNo(sDetail.getSubDistrictNo());
					 								sell.setBuyerAddress(sDetail.getBuyerAddress());
					 								sell.setSoi(sDetail.getSoi());
					 								sell.setTel(sDetail.getTel());
					 								sell.setMoo(sDetail.getMoo());
					 								sell.setBuyerId(sDetail.getBuyerId());
					 								
					 								
					 								// added on 5/1/2017
					 							    sell.setSaleDryCrop(sDetail.getSaleDryCrop());
					 							    sell.setSaleDryPrice(sDetail.getSaleDryPrice());
					 						        sell.setDryAmount(sDetail.getDryAmount());
					 								sell.setHumidDry(sDetail.getHumidDry());
					 								sell.setHumid(sDetail.getHumid());
					 								
					 								///
					 								
					 								
					 								sellingDetail.add(sell);
					 							}
					 						}
					 						detail.setSellingDetail(sellingDetail);
					 						
					 						/// แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยวจริง
			 								// เดือนที่ผลผลิตออกสู่ตลาด
			 								detail.setPublicMarketDate(plantDetail.getPublicMarketDate());
			 								// ประมาณการผลผลิตที่คาดว่าจะออกสู่ตลาด
			 								detail.setPublicCrop(plantDetail.getPublicCrop());
			 								// วันที่บันทึก
			 								detail.setRecordForecastDate(plantDetail.getRecordForecastDate());
			 								// ลงชื่อผู้บันทึก
			 								detail.setRecordForecastBy(plantDetail.getRecordForecastBy());
					 						
					 						detailSet.add(detail);
					 						land.getPlantDetail().add(detail);
				 						}
				 					}
								}
								landrightList.add(land);
								numLandright++;
							}
						}
						plant.setLandright(landrightList); 
					}
					// -- AssetDetail --
					if(tempPlant.getAssetDetail().size()> 0){
						List<AssetDetail> plantAssetList = new ArrayList<AssetDetail>();
						int numAsset = 1;
						for(int i=0; i < tempPlant.getAssetDetail().size(); i++){
							AssetDetail assetDetail = tempPlant.getAssetDetail().get(i);
							if(assetDetail!=null) {
								AssetDetail detail = new AssetDetail();
								detail.setPlant(plant);

								detail.setAsset(assetDetail.getAsset());
								detail.setAssetId(assetDetail.getAssetId());
									
								detail.setSeq(numAsset);
								detail.setAssetDate(assetDetail.getAssetDate());

								detail.setAmount(assetDetail.getAmount());
								SUMTOTAL += assetDetail.getAmount();
								numAsset++;
								
								plantAssetList.add(detail);
							}
						}
						plant.setAssetDetail(plantAssetList);
					}
					////////
		 			// in case of edit information from set member status pop-up
		 			int strLimit = (eform.getRemark().length()<100)?eform.getRemark().length():100;
		 			plant.setRemark(eform.getRemark().substring(0, strLimit));
		 			plant.setStatus(eform.getMemberStatus());
						
					plantHome.save(plant);
		 		} else {
			 	    // -- set into plant -- //
			  	 	plant.setBreedTypeId(breedTypeId);
				 	plant.setAccountNo(eform.getAccountNo()==null?"":eform.getAccountNo());  // เลขที่บัญชี
					plant.setAccountName(eform.getAccountName()==null?"":eform.getAccountName());  // ชื่อบัญชี
				
					plant.setSeason(eform.getPeriodPlant()==null?"0":eform.getPeriodPlant()); // ฤดูกาลปลูก
					
					plant.setCertificate(eform.getCertificateFarmerNo()==null?"":eform.getCertificateFarmerNo());// เลขที่ใบรับรองเกษตรกร
					plant.setRegisterNo(eform.getRegistrationNo()); // เลขที่ลงทะเบียน
					plant.setBookNo(bookNo); // เล่มที่
					plant.setDocumentNo(eform.getDocumentNo()); // เลขที่
					
					if(!"".equals(eform.getProjectDate())){
						Date registerDate = formatter.parse(eform.getProjectDate());
						plant.setRegisterDate(registerDate);
					}
					plant.setRegisterBy(eform.getOfficerName());
					plant.setFarmerGroupId(farmerGroupId); // กลุ่มเกษตกร
					plant.setMemberNo(eform.getCustomerFarmerGroupNo()); // เลขที่สมาชิกกลุ่มเกษตรกร
					plant.setTarget(target); // เป้าหมายกลุ่มเกษตรกร
					plant.setFarmerGroupId2(farmerGroupId2); // สหกรณ์
				    plant.setFlag("S");
				    plant.setPlantYear(Integer.parseInt(eform.getPlantYear()));
				    plant.setPlantNo(Integer.parseInt(eform.getPlantNo()));
				    plant.setFta(eform.getFta());
				    if(tempPlant != null) {
				    	if(tempPlant.getPlantStatus() != null && !"".equals(tempPlant.getPlantStatus())) {
					    	plant.setPlantStatus(tempPlant.getPlantStatus());
						    plant.setReason(tempPlant.getReason());
				    	}
				    	if(tempPlant.getStatus() != null && !"".equals(tempPlant.getStatus())) {
					    	plant.setStatus(tempPlant.getStatus());
						    plant.setRemark(tempPlant.getRemark());
				    	}
				    }
				    plant.setStatus("A");
				    plant.setRegister(eform.getRegister());
				    plant.setMemberType(eform.getMemberType());
				    String income = eform.getIncome();
				    if(income != null) {
				    	if(!income.equals(""))
				    		plant.setIncome(Double.parseDouble(income.replaceAll(",", "")));
				    } else {
				    	plant.setIncome(0);
				    }
				    
				 	// -- set into Farmer --
			 	    long provinceNo=0;
			 		long regionNo=0;
			 		long districtNo = 0;
			 		long subDistrictNo = 0;
			 		if(eform.getSubDistrictNo() !=null && !"".equals(eform.getSubDistrictNo())){
			 			SubDistrict subDistrictObj = subDistrictHome.findBySubDistrictNo(Long.parseLong(eform.getSubDistrictNo()));
			 			regionNo = subDistrictObj.getRegionNo();
			 			provinceNo = subDistrictObj.getProvinceNo();
			 			districtNo = subDistrictObj.getDistrictNo();
			 			subDistrictNo =  subDistrictObj.getSubDistrictNo();
			 		}
			 		farmer = setFarmerValue(request, eform, regionNo, provinceNo, districtNo, subDistrictNo, branchCode); // branchCode is added
			 				
			 		Farmer getFarmer = fHome.findFarmerDetail(farmer);
			 		if(getFarmer !=null){
			 			plant.setIdCard(getFarmer.getIdCard());
				 		plant.setEffectiveDate(getFarmer.getEffectiveDate());
			 		}else{
			 			plant.setIdCard(farmer.getIdCard());
				 		plant.setEffectiveDate(farmer.getEffectiveDate());
				 		fHome.persist(farmer); // save Farmer
					 	sf.getCurrentSession().getTransaction().commit();  //just added
					 	sf.getCurrentSession().close();  
			 		}	
				 	sf.getCurrentSession().beginTransaction();        //just added
				 	
				 	// ----- Add farmer ลง farmerGroupFarmer -----
				 	if(farmerGroupId > 0 || farmerGroupId2 > 0){
				 		if(farmerGroupId > 0){
				 			fgroup = fgroupHome.findByFarmerGroupId(farmerGroupId);
				 		}else{ 
				 			fgroup = fgroupHome.findByFarmerGroupId(farmerGroupId2);
				 			farmerGroupId = farmerGroupId2;
				 		}
				 		List<FarmerGroupFarmer> getFgfList = fgfHome.findByIdCardAndFarmerGroupId(farmer.getIdCard(), farmerGroupId);
				 		if(getFgfList.size() <= 0){
				 			// Case : Add New 
				 			FarmerGroupFarmer fgfarmer = new FarmerGroupFarmer();
				 			if(getFarmer != null){
					 			fgfarmer.setFarmer(getFarmer); // farmer
					 			fgfarmer.setIdCard(getFarmer.getIdCard());
					 			fgfarmer.setEffectiveDate(getFarmer.getEffectiveDate());
				 			}else{
				 				fgfarmer.setFarmer(farmer); // farmer
					 			fgfarmer.setIdCard(farmer.getIdCard());
					 			fgfarmer.setEffectiveDate(farmer.getEffectiveDate());
				 			}
				 			fgfarmer.setFarmerGroup(fgroup);
				 			fgfarmer.setFarmerGroupId(fgroup.getFarmerGroupId());
				 			fgfHome.save(fgfarmer);
				 			sf.getCurrentSession().getTransaction().commit();  
						 	sf.getCurrentSession().close();                   
						 	sf.getCurrentSession().beginTransaction();
				 		}
				 	}
				 	
				 	// ----------------set BankBranch--------------------------
				 	if(bankId > 0 && branchCode > 0){
					 	bankBranch = bBranchHome.findByBranchCode(branchCode, bankId);
					 	if(bankBranch == null)
					 	{
						 	bankBranch = new BankBranch();
						 	bankBranch.setBankId(bankId);
						 	bankBranch.setBranchCode(branchCode);
						 	bBranchHome.persist(bankBranch);
						 	sf.getCurrentSession().getTransaction().commit();  
							sf.getCurrentSession().close();                   
						    sf.getCurrentSession().beginTransaction();   
					 	}
						plant.setBankId(bankBranch.getBankId());
						plant.setBranchCode(bankBranch.getBranchCode());
				 	}
			
					if(!"".equals(eform.getFamilyItem())){
		        		Set<FarmerFamily> familySet =  null;
		        		String[] familyItem = eform.getFamilyItem().split("\\|");
		        		if(familyItem != null && familyItem.length > 0){
		        			familySet = new HashSet<FarmerFamily>();
		        			for(int i=0; i < familyItem.length; i++){
								if(!"".equals(familyItem[i]) && !"undefined".equals(familyItem[i]) && familyItem[i] != null && !"null".equals(familyItem[i])){
									String[] docValue = familyItem[i].split("\\,");
									//String rowIndex = docValue[0]; 
									String idCardNo = docValue[1]; 
									String familyName = docValue[2]; 
									String familySurname = docValue[3];
									FarmerFamily family = new FarmerFamily();
									if(plant != null){
										family.setPlant(plant);
										family.setPlantId(plant.getPlantId());
									}
									family.setIdCard(idCardNo);
									family.setFirstName(familyName);
									family.setLastName(familySurname);
									familySet.add(family);
								}
							}
						}
		        		plant.setFarmerFamily(familySet);
			        }

					// Landright
					if(!"".equals(eform.getLandrightItem())){
						List<LandRight> landrightList = new ArrayList<LandRight>();
						String[] landrightItem = eform.getLandrightItem().split("\\|");
						if(landrightItem !=null && landrightItem.length > 0)
						{
							for(int i=0; i < landrightItem.length; i++)
							{
								String[] landRightItems = landrightItem[i].split("\\,");
		 						String rowIndex = landRightItems[0]; // landrightIndex
								String typeId = landRightItems[1];
								String typeName= landRightItems[2];
								String docNo = landRightItems[3];
								long docRai= Long.parseLong(landRightItems[4]);
								long docNgan = Long.parseLong(landRightItems[5]);
								long docWah = Long.parseLong(landRightItems[6]);
								
								String landMoo = landRightItems[7];
								String provNo = landRightItems[8];
								String distrNo = landRightItems[9];
								String subDistrNo = landRightItems[10];

								if(landRightItems[7].indexOf("'") > -1)
									landMoo = landRightItems[7].replace("'", "");
								else
									landMoo = landRightItems[7];
								
								if(landRightItems[8].indexOf("'") > -1)
									provNo = landRightItems[8].replace("'", "");
								else
									provNo = landRightItems[8];
								
								if(landRightItems[9].indexOf("'") > -1)
									distrNo = landRightItems[9].replace("'", "");
								else
									distrNo = landRightItems[9];
								
								if(landRightItems[10].indexOf("'") > -1)
									subDistrNo = landRightItems[10].replace("'", "");
								else
									subDistrNo = landRightItems[10];
								
								long lprovinceNo = 0, ldistrictNo = 0, lsubDistrictNo = 0;

								if(!"".equals(provNo))
								   lprovinceNo = Long.parseLong(provNo);
								if(!"".equals(distrNo))
								   ldistrictNo = Long.parseLong(distrNo);
								if(!"".equals(subDistrNo))
								   lsubDistrictNo = Long.parseLong(subDistrNo); 
								
								String isOwn = "";
								if(landRightItems.length > 11 )
									isOwn = landRightItems[11];
								
								double rentPrice = 0;
								if(landRightItems.length > 12 )
									rentPrice = Double.parseDouble(landRightItems[12].replace("'", ""));
								
								String waterSource = "";
								if(landRightItems.length > 13 )
									waterSource = (landRightItems[13].toString().equals("null"))?null:landRightItems[13].toString();
								
								long irrigationAreaId = 0;
								if(landRightItems.length > 14 )
									irrigationAreaId = Long.parseLong(landRightItems[14].toString());
								
								String landStatus = ""; //^
								if(landRightItems.length > 15 ){
									if( landRightItems[15].indexOf("^") > -1){
										landStatus = landRightItems[15].replace('^', ',');
									} else {
										landStatus = landRightItems[15];
									}
								}
								
								String landType = "";//^
								if(landRightItems.length > 16 ){
									if( landRightItems[16].indexOf("^") > -1){
										landType = landRightItems[16].replace('^', ',');
									} else {
										landType = landRightItems[16];
									}
								}
								String landTypeOther = "";
								if(landRightItems.length > 17)
								   landTypeOther= landRightItems[17];
									
								LandRight land = new LandRight();
								land.setPlantDetail(new HashSet<PlantDetail>());
								
								if(docNo.indexOf("^") > -1){
									docNo = docNo.replace("^",",");
								}

								land.setDocNo(docNo);
								land.setPlant(plant);
								land.setSeq(Integer.parseInt(rowIndex));
								land.setTypeId(Long.parseLong(typeId));
								
								if(!"".equals(landMoo))
									land.setMoo(Integer.parseInt(landMoo));
								
								SubDistrict subd = subDistrictHome.findBySubDistrictNo(lsubDistrictNo);
								if(subd!=null){
									land.setRegionNo(subd.getRegionNo());
									land.setProvinceNo(subd.getProvinceNo());
									land.setDistrictNo(subd.getDistrictNo());
									land.setSubDistrictNo(subd.getSubDistrictNo());
								}
								String ownRent = "";
								if("1".equals(isOwn))
									ownRent = "O";
								if("0".equals(isOwn))
									ownRent = "R";
								land.setOwnRent(ownRent);
								land.setRentPrice(rentPrice);
								land.setDocRai(docRai);
								land.setDocNgan(docNgan);
								land.setDocWah(docWah);
								land.setWaterSource(waterSource);
								land.setIrrigationAreaId(irrigationAreaId);
								land.setLandStatus(landStatus);
								land.setLandType(landType);
								land.setLandTypeOther(landTypeOther);

								/// >> plantDetail <<
								if(!"".equals(eform.getDetailItem())){
									Set<PlantDetail> detailSet =  new HashSet<PlantDetail>();
					 				String[] detailItem = eform.getDetailItem().split("\\$");
					 				if(detailItem !=null && detailItem.length > 0){
					 					for(int j=0; j < detailItem.length; j++)
					 					{
					 						String[] allDetail = detailItem[j].split("\\#");
					 						if(!"".equals(allDetail[0]) && allDetail[0] != null && !"undefined".equals(allDetail[0]) &&  !"null".equals(allDetail[0]))
					 						{
					 							// เอกสารสิทธิ์  + พันพืชที่ปลูก
						 						String[] plantDetailItems = allDetail[0].split("\\,"); // plantDetail
						 						String detailIndex 		= plantDetailItems[0];
						 						long detailTypeId 		= Long.parseLong(plantDetailItems[1]);
						 						String detailTypeName   = plantDetailItems[2];
						 						String detailDocNo 		= plantDetailItems[3].replace("'", "");
						 						
						 						if(detailDocNo.indexOf("^") > -1){
						 							detailDocNo = detailDocNo.replace("^",",");
												}
						 						
						 						String detailDocRai		= plantDetailItems[4];
						 						String detailDocNgan 	= plantDetailItems[5];
						 						String detailDocWah 	= plantDetailItems[6];
						 						long groupId   		= Long.parseLong(plantDetailItems[7].toString());
						 						String groupName 	= plantDetailItems[8].replace("'", "");
						 						String qty 		 	= plantDetailItems[9].replace("'", "");
						 						String plantRai  	= plantDetailItems[10].replace("'", "");
						 						String plantNgan 	= plantDetailItems[11].replace("'", "");
						 						String plantWah  	= plantDetailItems[12].replace("'", "");
						 						
						 						String seedResource	= "";
						 						if(plantDetailItems[13].indexOf("^") > -1){
						 							seedResource = plantDetailItems[13].replace("^",",");
						 						}else{
													seedResource = plantDetailItems[13];	
						 						}
						 						
						 						String seedPicking = "";
												if(plantDetailItems[14].indexOf("^") > -1){
													seedPicking = plantDetailItems[14].replace("^",",");
												}else{
													seedPicking = plantDetailItems[14];	
												}
												
						 						String pObj  = plantDetailItems[15].replace("'", "");
						 						String plantObjective  = "";
												if(pObj.indexOf("^") > -1){
													plantObjective = pObj.replace( "^" , "," );
												}else{
													plantObjective = pObj;
												}
											
						 						String agricultureType = plantDetailItems[16].replace("'", "");
						 						String qualification  = "";
												if(agricultureType.indexOf("^") > -1){
													qualification = agricultureType.replace("^",",");
												}else{
													qualification = agricultureType;
												}
						 						
						 						String gap = plantDetailItems[17].replace("'", ""); // standard
						 						if("ทำ".equals(gap))
						 							gap = "1";
						 						else
						 							gap = "0";
						 						
						 						String plantMethod = "";     
						 						if(plantDetailItems[18].indexOf("^") > -1)
						 							plantMethod = plantDetailItems[18].replace("^",",");
												else
													plantMethod = plantDetailItems[18];	
						 						
						 						String prepareArea 	 = "";  
						 						if(plantDetailItems[19].indexOf("^") > -1){
						 							prepareArea = plantDetailItems[19].replace("^",",");
						 						}else{
													prepareArea = plantDetailItems[19];	
						 						}
						 						
						 						String prepareAreaOther = plantDetailItems[20];
						 						
						 						String prepareManure = "";
						 						if(plantDetailItems[21].indexOf("^") > -1){
						 							prepareManure = plantDetailItems[21].replace("^",",");
						 						}else{
													prepareManure = plantDetailItems[21];	
						 						}
						 						
						 						String plantDate 	   = plantDetailItems[22].replace("'", "");
						 						String forecastDate    = plantDetailItems[23].replace("'", "");
						 						String forecastRecordDoc = plantDetailItems[24].replace("'", "");
						 						String corpId = plantDetailItems[25].replace("'", "");
						 						String corpName = plantDetailItems[26].replace("'", "");
						 						String noOfSeed = plantDetailItems[27].replace("'", "");

						 						// set key ที่เหมือนกัน
						 						if(land.getDocNo().equals(detailDocNo) && land.getTypeId() == detailTypeId)
						 						{
						 							PlantDetail detail = new PlantDetail();
						 							
						 							detail.setLandRight(land); // set PlantDetail into LandRight
								 					
							 						BreedGroup bGroup = ghome.findByBreedGroupId(groupId);
							 						detail.setBreedGroup(bGroup);  // set PlantDetail into BreedGroup
							 						detail.setBreedTypeId(bGroup.getBreedTypeId());
							 						detail.setBreedGroupId(bGroup.getBreedGroupId());
							 						
							 						if(!"".equals(qty) && qty!=null)
							 							detail.setQtyPerRai(Double.parseDouble(qty));
							 						detail.setCostPerRai(0.0);
							 						
							 						detail.setSeedFrom(seedResource.replace("'", ""));
							 						detail.setSeedSelect(seedPicking.replace("'", ""));
							 						
							 						// --- Added on 29.04.2015 ---
							 						if(!"".equals(corpId) && !"0".equals(corpId)){
							 							detail.setCooperativeId(Long.parseLong(corpId));
							 						}else
							 							detail.setCooperativeId(0);
							 						
							 						if(!"".equals(noOfSeed) && !"0".equals(noOfSeed) && !"undefined".equals(noOfSeed)){
							 							detail.setNoOfSeed(Long.parseLong(noOfSeed));
							 						}else{
							 							detail.setNoOfSeed(0);
							 						}
							 						// ---------------------------
							 						
							 						detail.setPlantObjective(plantObjective);
							 						detail.setQualification(qualification);
							 						
							 						detail.setStandard(gap);
							 						detail.setPrepareArea(prepareArea.replace("'", ""));
							 						detail.setPlantMethod(plantMethod.replace("'", ""));
							 						detail.setPrepareAreaOther(prepareAreaOther.replace("'", ""));
							 						detail.setPrepareManure(prepareManure.replace("'", ""));
							 						
							 						if(!"".equals(plantRai))
							 							detail.setPlantRai(Long.parseLong(plantRai));
							 						if(!"".equals(plantNgan))
							 							detail.setPlantNgan(Long.parseLong(plantNgan));
							 						if(!"".equals(plantWah))
							 							detail.setPlantWah(Long.parseLong(plantWah));
							 					
							 						// วันที่เพาะปลูก
							 						if(!"".equals(plantDate)){
							 							Date pDate = formatter.parse(plantDate);
							 							detail.setPlantDate(pDate);
							 						}
							 						// วันที่คาดว่าจะเก็บเกี่ยว 
							 						if(!"".equals(forecastDate)){
							 							Date fDate = formatter.parse(forecastDate);
							 							detail.setForecastDate(fDate);
							 						}
							 						// ประมาณการผลผลิตที่คาดว่าจะได้รับ
							 						if(!"".equals(forecastRecordDoc)){
							 							detail.setForecastRecord(Double.parseDouble(forecastRecordDoc));
							 						}
							 						
							 						// -- PlantManure --
							 						if(allDetail.length >= 2){
							 							String tab2Value = allDetail[1]; 
							 							if(!"".equals(tab2Value) && tab2Value != null && !"undefined".equals(tab2Value) && !"null".equals(tab2Value)){
							 								List<PlantManure> manureList = setManureList(tab2Value, detail);
							 								detail.setPlantManure(manureList); // set list of landright into plantdetail
							 							}
							 						}
							 						// -- PlantChemical --
							 						if(allDetail.length >= 3){
							 							String tab3Value = allDetail[2];
							 							if(!"".equals(tab3Value) && tab3Value != null && !"undefined".equals(tab3Value) && !"null".equals(tab3Value)){
							 								List<PlantChemical> chemiList = setChemiList(tab3Value, detail);
							 								detail.setPlantChemical(chemiList);
							 							}
							 						}
							 						
							 						
							 						/// แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยวจริง
							 						if(allDetail.length >= 5)
							 						{
							 							String tab5Value = allDetail[4];
							 							if(!"".equals(tab5Value) && tab5Value != null && !"undefined".equals(tab5Value) && !"null".equals(tab5Value))
							 							{
							 								 String[] noticeBeforeHarvest = allDetail[4].split("\\,");
							 								 String publicMarketDateStr ="",recordForecastDateStr ="",recordForecastBy="";
							 								 double publicCrop = 0;
							 								 Date publicMarketDate = null, recordForecastDate =null;
							 								 if(noticeBeforeHarvest != null && !"".equals(noticeBeforeHarvest))
							 								 {
								 								 if(noticeBeforeHarvest.length >= 2)
								 								 {
									 								 if(noticeBeforeHarvest[1] != null && !"".equals(noticeBeforeHarvest[1]))
									 								 {
									 									 publicMarketDateStr = noticeBeforeHarvest[1].replace("'", "");
									 									 if(!"".equals(publicMarketDateStr))
									 										 publicMarketDate = formatter.parse(publicMarketDateStr);
									 								 }
									 								 if(noticeBeforeHarvest.length >= 3)
									 								 {
										 								 if(noticeBeforeHarvest[2] != null && !"".equals(noticeBeforeHarvest[2]))
										 								 {
										 									 String publicCropStr = noticeBeforeHarvest[2].replace("'", "");
										 									 if(!"".equals(publicCropStr))
										 										 publicCrop  = Double.parseDouble(publicCropStr);
										 								 }
										 								 if(noticeBeforeHarvest.length >= 4)
										 								 {
											 								 if(noticeBeforeHarvest[3] != null && !"".equals(noticeBeforeHarvest[3]) && !"null".equals(noticeBeforeHarvest[3]))
											 								 {
											 									 recordForecastDateStr  = noticeBeforeHarvest[3].replace("'", "");
											 									 if(!"".equals(recordForecastDateStr))
											 										 recordForecastDate = formatter.parse(recordForecastDateStr);
											 								 }
											 								 if(noticeBeforeHarvest.length >= 5)
											 								 {
												 								 if(noticeBeforeHarvest[4] != null && !"".equals(noticeBeforeHarvest[4])){
												 									 recordForecastBy	 = noticeBeforeHarvest[4].replace("'", "");
												 								 }
											 								 }
										 								 }
									 								}
								 								 }
								 								// เดือนที่ผลผลิตออกสู่ตลาด
								 								detail.setPublicMarketDate(publicMarketDate);
								 								// ประมาณการผลผลิตที่คาดว่าจะออกสู่ตลาด
								 								detail.setPublicCrop(publicCrop);
								 								// วันที่บันทึก
								 								detail.setRecordForecastDate(recordForecastDate);
								 								// ลงชื่อผู้บันทึก
								 								detail.setRecordForecastBy(recordForecastBy);
							 								 }
							 							}
							 						}
							 						
							 						// -- CostDetail --
							 						if(allDetail.length >= 6){
							 							String costItems = allDetail[5];
							 							if(!"".equals(costItems) && costItems != null && !"undefined".equals(costItems) && !"null".equals(costItems)){
							 								Set<CostDetail> costDetailList = setCostDetailSet(costItems, detail);
							 								detail.setCostDetail(costDetailList);
							 							}
							 						}
							 						// -- HarvestDetail is in plantdetail --
							 						if(allDetail.length >= 7){
							 							String harvItems= allDetail[6];
							 							if(!"".equals(harvItems) && harvItems != null && !"undefined".equals(harvItems) && !"null".equals(harvItems)){
							 							    Set<HarvestDetail> harvDetail = setHarvestDetailSet(harvItems,detail); //= setHarvestDetailList(harvItems, detail);
							 							    detail.setHarvestDetail(harvDetail);
							 							}
							 						}
							 					    // SaleDetail is in plantdetail
							 						if(allDetail.length >= 8){
							 							String sellingItem= allDetail[7];
							 							if(!"".equals(sellingItem) && sellingItem != null && !"undefined".equals(sellingItem) && !"null".equals(sellingItem)){
							 								Set<SellingDetail> sellDetail = setSellingDetailSet(sellingItem, detail);
							 								detail.setSellingDetail(sellDetail);
							 							}
							 						}
							 						detailSet.add(detail);
							 						land.getPlantDetail().add(detail);
							 					}
					 						}
					 					}
					 				}
								}
								landrightList.add(land);
							}
							plant.setLandright(landrightList); 
						}
					}
					// -- AssetDetail --
					if(!"".equals(eform.getAssetItem())){
						List<AssetDetail> plantAssetList = setAssetList(eform.getAssetItem(), plant);
						plant.setAssetDetail(plantAssetList);
					}else{
						if("true".equals(eform.getSumCheck())){
							List<AssetDetail> plantAssetList = new ArrayList<AssetDetail>();
							AssetDetail detail = new AssetDetail();
							AssetHome aHome = new AssetHome();
							detail.setPlant(plant);
						
							Asset asset = aHome.checkTotal();
							if(asset!=null)
								detail.setAsset(asset);
							else{
								asset = new Asset();
								asset.setIsTotal("Y");
								asset.setAssetName("ยอดรวม");
								asset.setLastUpdateBy(userLogin.getUserName());
								asset.setLastUpdateDate(new Date());
								aHome.saveOrUpdate(asset);	 
								sf.getCurrentSession().getTransaction().commit();  //just added
							 	sf.getCurrentSession().close();                   //just added
							 	sf.getCurrentSession().beginTransaction(); 
							 	detail.setAsset(asset);
							}
							
							if(!"".equals(eform.getAssetDetailDate())){
								Date date = formatter.parse(eform.getAssetDetailDate());
							    detail.setAssetDate(date);
							}
							if(!"".equals(eform.getAssetDetailAmount())){
								detail.setAmount(Double.parseDouble(eform.getAssetDetailAmount()));
							}
							detail.setSeq(1);
					    	plantAssetList.add(detail);
							plant.setAssetDetail(plantAssetList);
						}
					}
					////////
						
					plantHome.save(plant);
			 	}
		 		sf.getCurrentSession().getTransaction().commit(); 
		 	    sf.getCurrentSession().close();
		 		//// ---- end save plant ----///
		 	    
		 	    // update refplantid
		 		sf.getCurrentSession().beginTransaction();
		 		if(plant.getRefPlantId() == null || tempPlant.getRefPlantId() == null){
		 			if("Yes".equals(eform.getSetStatusCmd()))
		 				plantHome.updateRefPlantId(tempPlant.getPlantId(), tempPlant.getPlantId());
		 			else
		 				plantHome.updateRefPlantId(plant.getPlantId(), plant.getPlantId());
		 		}else{
		 			if("Yes".equals(eform.getSetStatusCmd()))
		 				plantHome.updateRefPlantId(tempPlant.getPlantId(), tempPlant.getRefPlantId());
		 			else
		 				plantHome.updateRefPlantId(plant.getPlantId(), plant.getRefPlantId());
		 		}
		 		sf.getCurrentSession().getTransaction().commit(); 
		 	    sf.getCurrentSession().close();
		 	    
		 		eform.setPlantId(String.valueOf(plant.getPlantId()));
		 		eform.setStatus("Edit");
		 		
		 		cmd = "Add";
		 		msg = Utility.get("SAVE_SUCCESS");
		 		session.setAttribute("plant", plant);
			}
	 		eform.setCmd(cmd);
	 		eform.setMsg(msg);
	 	} catch(Exception e) {
	 		sf.getCurrentSession().getTransaction().rollback(); //if get an error,then rollback
			e.printStackTrace();
			log.error(e,e);
			eform.setCmd("Fail");
			eform.setMsg(Utility.get("SAVE_PLANT_FAILED"));
			request.setAttribute("PlantForm", eform);
		} finally {
			sf.getCurrentSession().close();
		}
		return mapping.findForward(pageForward);
	}

	private ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlantForm eform = (PlantForm) form;
		SessionFactory sf = HibernateHome.getSessionFactory();
		PlantHome home = new PlantHome();
		FarmerHome fHome = new FarmerHome();
		LandRightHome lHome = new LandRightHome();
		FarmerGroupHome fgHome = new FarmerGroupHome();
		SubDistrictHome sdHome = new SubDistrictHome();
		DistrictHome dHome = new DistrictHome();
		ProvinceHome pHome = new ProvinceHome();
		CoordinatesHome cHome = new CoordinatesHome();
		Plant plant = new Plant();
		Plant temp = new Plant();

		try {
			sf.getCurrentSession().beginTransaction();
			long plantId = eform.getPlantId()==null?0:Integer.parseInt(eform.getPlantId());
			String[] parts = eform.getDocAndType().split(":");
			
			if (plantId == 0) {
				//saveReceive(mapping, eform, request, response);
			} else {
				temp = home.findById(plantId);

				plant.setPlantYear(temp.getPlantYear());
				plant.setPlantNo(temp.getPlantNo());
				plant.setCitizenId(temp.getIdCard().split(""));
				plant.setRegisterId(temp.getRegisterNo().split(""));
				plant.setMemberNo(temp.getMemberNo());
				plant.setFarmerGroupName(fgHome.findByFarmerGroupId(temp.getFarmerGroupId()).getFarmerGroupName());
				Farmer farmer = fHome.findByKey(temp.getIdCard(), temp.getEffectiveDate());
				farmer.setFirstName(farmer.getAbbrPrefix()+" "+farmer.getFirstName());
				farmer.setSubDistrict(sdHome.findBySubDistrictNo(farmer.getSubDistrictNo()));
				farmer.setDistrict(dHome.findByDistrictNo(farmer.getDistrictNo()));
				farmer.setProvince(pHome.searchByProvinceNo(farmer.getProvinceNo()));
				plant.setFarmer(farmer);
				LandRight landRight = lHome.findByCriteria(plantId, Long.parseLong(parts[1]), 0, parts[0]);
				landRight.setSubDistrict(sdHome.findBySubDistrictNo(landRight.getSubDistrictNo()));
				landRight.setDistrict(dHome.findByDistrictNo(landRight.getDistrictNo()));
				landRight.setProvince(pHome.searchByProvinceNo(landRight.getProvinceNo()));
				//List<Coordinates> coo = cHome.findByCoordinatesKey(temp.getPlantYear(), temp.getPlantNo(), temp.getIdCard(), Long.parseLong(parts[1]), temp.getBreedTypeId(), parts[0]);
				Coordinates coo = cHome.findOneByCoordinatesKey(temp.getPlantYear(), temp.getPlantNo(), temp.getIdCard(), Long.parseLong(parts[1]), temp.getBreedTypeId(), parts[0]);
				if(coo != null) {
					coo.setLatitude("X"+coo.getLatitude()+" ,");
					coo.setLongitude("Y"+coo.getLongitude());
					landRight.setCoordinates(coo);
				} else {
					coo = new Coordinates();
					coo.setLatitude("");
					coo.setLongitude("");
					landRight.setCoordinates(coo);
				}
				List<LandRight> lList = new ArrayList<LandRight>();
				lList.add(landRight);
				plant.setLandright(lList);
			}
			InputStream is = PlantAction.class.getResourceAsStream("/pdf/Landcheck.pdf");
			ByteArrayOutputStream pdf = PdfForm.print(is, plant);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=" + "Landcheck_" + sdf.format(new Date()) + ".pdf");
			OutputStream out = response.getOutputStream();
			out.write(pdf.toByteArray());
		} catch (Exception e) {
			sf.getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			log.error(e, e);
		} finally {
			sf.getCurrentSession().close();
		}
		return null;
	}
}
