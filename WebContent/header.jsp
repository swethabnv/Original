<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@page import="com.wsndata.data.UserAuthorize"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.wsndata.data.User"%>
<%@page import="com.dcs.util.StringUtil"%>
<link rel="icon" href="favicon.ico" />
<% 
String userLoginName = "";
if( session.getAttribute("userLogin") !=null){
	User uLogin = (User)session.getAttribute("userLogin");
	userLoginName =  StringUtil.beString(uLogin.getFirstName()) + " " + StringUtil.beString(uLogin.getLastName());
}
List authorList = (ArrayList) session.getAttribute("authorList");
%>

<script>
(function($){ //create closure so we can safely use $ as alias for jQuery
	$(document).ready(function(){
		//initialise plugin
		var example = $('#example').superfish({
			//add options here if required
		});

		//buttons to demonstrate Superfish's public methods
		$('.destroy').on('click', function(){
			example.superfish('destroy');
		});

		$('.init').on('click', function(){
			example.superfish();
		});

		$('.open').on('click', function(){
			example.children('li:first').superfish('show');
		});

		$('.close').on('click', function(){
			example.children('li:first').superfish('hide');
		});
		
		$('html, body').animate({
			scrollTop: $('#example').offset().top
		}, 'slow');
		
		$('.gridRowOdd > td:last-child').addClass('last-child');
		$('.gridRowEven > td:last-child').addClass('last-child');
	});
})(jQuery);

function loadMenu(author){
	var read = "",write = "",del = "";
	<% if(authorList!=null){
		for(int i=0;i<authorList.size();i++){
			UserAuthorize userA = (UserAuthorize)authorList.get(i);
			%>

			if(author!=null){
				if(author=="<%=userA.getMenuId()%>"){
					read = "<%=userA.getAuthorize().split("")[1]%>";
					write = "<%=userA.getAuthorize().split("")[2]%>";
					del = "<%=userA.getAuthorize().split("")[3]%>";
					if (read == "0") {
						if(author == "M01"){
							window.location = "<%=request.getContextPath()%>/PlantList.do";
						}else{
							window.location = "<%=request.getContextPath()%>/notPermission.jsp";
						}
					}
					if (write == "0") {
						document.getElementById("write").style.display = "none";
						document.getElementById("write").outerHTML = "";
						//$('[id^="write"]').css('display','none');
						$('[id^="write"]').remove();
					}
					if (del == "0") {
						ele = document.getElementById("del");
						if (ele != null) {
							document.getElementById("del").style.display = "none";
							document.getElementById("del").outerHTML = "";
						}
					}
					
					if (write == "0") {
						if(author == "M24"){
							$('[id^="lc-manage"]').remove();
							$('#addLandCheckImages').remove();
						}
					}
				}
			}
			<%
		}
	}
	%>
}
</script>


<div class="header">
	<div class="inside">
		<div class="logo-inside"><img src="<%=request.getContextPath()%>/images/logo.png" width="337" /></div>

		<div class="logout">
			<div class="nav-logout">
				<ul>
					<li class="welcome"><img src="<%=request.getContextPath()%>/images/welcome-text.png" width="68" height="14" style="padding-bottom:4px;" /></li>
					<li><img src="<%=request.getContextPath()%>/images/arrow-logout.png" width="5" height="10" /></li>
					<li>&nbsp;คุณ <%= userLoginName %></li>
				</ul>
			</div>
			<div class="btn-logout"><a href="<%=request.getContextPath()%>/Login.do?cmd=Logout"><img src="<%=request.getContextPath()%>/images/btn-logout.png" title="Logout" width="33" height="33" ></a></div>
		</div>
		<div class="clear"></div>  
	</div>
</div>
<div class="clear"></div>

<div class="nav-menu">
	<div class="inside">
		<ul class="sf-menu" id="example">
			<% if(authorList!=null){
				UserAuthorize userA = (UserAuthorize)authorList.get(17);
				if("M19".equals(userA.getMenuId())){
					if("1".equals(userA.getAuthorize().split("")[1])){
						%>
						<li class="h-menu report" id="M19" ><a href="#"></a>
							<ul class="sf-submenu">
								<li>
									<a href="#">F-R000</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R000">F-R000 รายงานรายละเอียดข้อมูลของสมาชิกสหกรณ์ที่เข้าร่วมโครงการ</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R001</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R001">F-R001 รายงานประมาณการผลผลิตของเกษตรกร (แยกตามชนิดพืช)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R002</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R002">F-R002 รายงานประมาณการผลผลิตของเกษตรกร (ระบุกลุ่มพันธุ์)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R003</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R003">F-R003 รายงานประมาณการผลผลิตของเกษตรกร (แยกตามกลุ่มเกษตรกร)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R004</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R004">F-R004 รายงานสรุปประมาณการผลผลิตของเกษตรกร</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R005</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R005">F-R005 รายงานสรุปประมาณการผลผลิตของเกษตรกร (แยกตามกลุ่มพันธุ์)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R006</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R006">F-R006 รายงานสรุปประมาณการผลผลิตของเกษตรกร (แยกตามกลุ่มเกษตรกร)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R007</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R007">F-R007 รายละเอียดข้อมูลเกษตรกรที่แจ้งผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก (ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R008</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R008">F-R008 รายละเอียดข้อมูลเกษตรกรที่แจ้งผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก (แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R009</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R009">F-R009 รายงานปริมาณผลผลิตที่คาดว่าจะได้ตอนเริ่มเพาะปลูก (ตามช่วงเวลาที่คาดว่าจะเก็บเกี่ยว)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R010</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R010">F-R010 รายงานปริมาณผลผลิตที่คาดว่าจะออกสู่ตลาด (แจ้งล่วงหน้า 1 เดือนก่อนเก็บเกี่ยว)</a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R011</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R011">F-R011 รายงานผลผลิตเปรียบเทียบกับต้นทุนค่าใช้จ่ายของเกษตรกร </a></li>                     
									</ul>
								</li>
								<li>
									<a href="#">F-R012</a>
									<ul>
										<li style="width: 600px"><a href="<%=request.getContextPath() %>/Report.do?rep=R012">F-R012 รายงานผลผลิตเปรียบเที่ยบกับต้นทุนค่าใช้จ่ายของเกษตรกร </a></li>                     
									</ul>
								</li>
							</ul>
						</li>
					<%} 
				} %>

				<li class="h-menu management current">
					<a href="#"></a>
					<ul class="sf-submenu">
						<%if("1".equals(((UserAuthorize)authorList.get(2)).getAuthorize().split("")[1])||"1".equals(((UserAuthorize)authorList.get(3)).getAuthorize().split("")[1])||"1".equals(((UserAuthorize)authorList.get(4)).getAuthorize().split("")[1])||"1".equals(((UserAuthorize)authorList.get(5)).getAuthorize().split("")[1])){
							%>
							<li class="current" id="MRegion" >
								<a href="#" style="width: 200px;">การจัดการภูมิภาค จังหวัด อำเภอ ตำบล</a>
								<ul>
									<% if("1".equals(((UserAuthorize)authorList.get(5)).getAuthorize().split("")[1])){%>
									<li id="M06" ><a href="<%=request.getContextPath() %>/RegionList.do">การจัดการภูมิภาค</a></li> 
									<%} if("1".equals(((UserAuthorize)authorList.get(4)).getAuthorize().split("")[1])){%>
									<li id="M05" ><a href="<%=request.getContextPath() %>/ProvinceList.do">การจัดการจังหวัด</a></li> 
									<%} if("1".equals(((UserAuthorize)authorList.get(3)).getAuthorize().split("")[1])){%>
									<li id="M04" ><a href="<%=request.getContextPath() %>/DistrictList.do">การจัดการอำเภอ</a></li> 
									<%} if("1".equals(((UserAuthorize)authorList.get(2)).getAuthorize().split("")[1])){%>
									<li id="M03" ><a href="<%=request.getContextPath() %>/SubDistrictList.do">การจัดการตำบล</a></li>   
									<%}%>                       
								</ul>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(8)).getAuthorize().split("")[1])){%>  
							<li id="M09" >
								<a href="<%=request.getContextPath() %>/PrefixList.do">การจัดการคำนำหน้า</a>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(0)).getAuthorize().split("")[1])){%>  
							<li id="M01" >
								<a href="<%=request.getContextPath() %>/UserList.do">การจัดการผู้ใช้งาน</a>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(1)).getAuthorize().split("")[1])){%>
							<li id="M02" >
								<a href="<%=request.getContextPath() %>/password/changePassword.jsp">Change Password</a>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(9)).getAuthorize().split("")[1])||"1".equals(((UserAuthorize)authorList.get(10)).getAuthorize().split("")[1])){%>
							<li class="current" id="MGroup">
								<a href="#">การจัดการกลุ่มเกษตรกร/สหกรณ์</a>
								<ul>
									<%if("1".equals(((UserAuthorize)authorList.get(9)).getAuthorize().split("")[1])){%>
									<li id="M10"><a href="<%=request.getContextPath() %>/FarmerGroupList.do">การจัดการกลุ่มเกษตรกร</a></li>
									<%} if("1".equals(((UserAuthorize)authorList.get(10)).getAuthorize().split("")[1])){%>   
									<li id="M11"><a href="<%=request.getContextPath() %>/CooperativeGroupList.do">การจัดการสหกรณ์</a></li>
									<%} %>
								</ul>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(19)).getAuthorize().split("")[1])){%>
							<li id="M21">
								<a href="<%=request.getContextPath() %>/BuyerList.do">การจัดการผู้ซื้อ</a>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(11)).getAuthorize().split("")[1])||"1".equals(((UserAuthorize)authorList.get(12)).getAuthorize().split("")[1])||"1".equals(((UserAuthorize)authorList.get(13)).getAuthorize().split("")[1])){%>   
							<li class="current" id="MBreed">
								<a href="#">การจัดการพันธุ์พืช</a>
								<ul>
									<%if("1".equals(((UserAuthorize)authorList.get(11)).getAuthorize().split("")[1])){%>
									<li id="M12"><a href="<%=request.getContextPath() %>/BreedTypeList.do">การจัดการชนิดพืช</a></li>
									<%} if("1".equals(((UserAuthorize)authorList.get(12)).getAuthorize().split("")[1])){%>    
									<li id="M13"><a href="<%=request.getContextPath() %>/BreedGroupList.do">การจัดการกลุ่มพันธุ์พืช</a></li>
									<%} if("1".equals(((UserAuthorize)authorList.get(13)).getAuthorize().split("")[1])){%>   
									<li id="M14"><a href="<%=request.getContextPath() %>/EconomicBreedList.do">การจัดการพืชเศรษฐกิจ</a></li>
									<%} %>
								</ul>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(6)).getAuthorize().split("")[1])||"1".equals(((UserAuthorize)authorList.get(7)).getAuthorize().split("")[1])){%>   
							<li class="current" id="MBank">
								<a href="#">การจัดการธนาคาร</a>
								<ul>
									<%if("1".equals(((UserAuthorize)authorList.get(6)).getAuthorize().split("")[1])){%>
									<li id="M07"><a href="<%=request.getContextPath() %>/BankList.do">การจัดการธนาคาร</a></li>
									<%} if("1".equals(((UserAuthorize)authorList.get(7)).getAuthorize().split("")[1])){%>    
									<li id="M08"><a href="<%=request.getContextPath() %>/BranchList.do">การจัดการสาขาธนาคาร</a></li>
									<%} %>
								</ul>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(14)).getAuthorize().split("")[1])){%>
							<li id="M15">
								<a href="<%=request.getContextPath() %>/CostList.do">การจัดการต้นทุน/ค่าใช้จ่าย</a>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(15)).getAuthorize().split("")[1])){%>    
							<li id="M16">
								<a href="<%=request.getContextPath() %>/AssetList.do">การจัดการทรัพย์สิน</a>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(20)).getAuthorize().split("")[1])){%>    
							<li id="M22">
								<a href="<%=request.getContextPath() %>/PrepareAreaList.do">การจัดการแปลงก่อนเตรียมดิน</a>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(21)).getAuthorize().split("")[1])){%>    
							<li id="M23">
								<a href="<%=request.getContextPath() %>/PlantMethodList.do">การจัดการวิธีการปลูก</a>
							</li>
						<%} if("1".equals(((UserAuthorize)authorList.get(18)).getAuthorize().split("")[1])){%>    
							<li id="M20">
								<a href="<%=request.getContextPath() %>/FarmerList.do">การจัดการเกษตรกร</a>
							</li>
						<% } if("1".equals(((UserAuthorize)authorList.get(24)).getAuthorize().split("")[1])){%>    
							<li id="M26">
								<a href="<%=request.getContextPath() %>/CloseDueList.do">การปิดยอด</a>
							</li>
						<%} %>
					</ul>
				</li>
				
				<% if("1".equals(((UserAuthorize)authorList.get(16)).getAuthorize().split("")[1]) || "1".equals(((UserAuthorize)authorList.get(22)).getAuthorize().split("")[1])){%>
					<li class="h-menu agriculture" id="M17"><a href="#"></a>
						<ul>
							<li><a href="<%=request.getContextPath() %>/PlantList.do">ข้อมูลการเพาะปลูก</a></li>
						<% if("1".equals(((UserAuthorize)authorList.get(22)).getAuthorize().split("")[1])){%>    
							<li id="M24">
								<a href="<%=request.getContextPath() %>/LandCheckList.do">ข้อมูลการตรวจแปลง</a>
							</li>
						<% } if("1".equals(((UserAuthorize)authorList.get(23)).getAuthorize().split("")[1])){%>    
							<li id="M25">
								<a href="<%=request.getContextPath() %>/LandApproveList.do">การอนุมัติการตรวจแปลง</a>
							</li>
						<%} %>
						</ul>
					</li>
				<%}%>
			<%}%>
			<!--  end if   -->
		</ul>

	</div>    	
</div>
<div class="clear"></div>