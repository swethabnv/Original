<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=0, maximum-scale=1">
<meta name="viewport" content="width=1280, user-scalable=yes">
<title>ธนาคารเพื่อการเกษตรและสหกรณ์การเกษตร</title>
</head>

		<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/js/hoverIntent.js"></script>
		<script src="<%=request.getContextPath()%>/js/superfish.js"></script>
		<script>

		(function($){ //create closure so we can safely use $ as alias for jQuery

			$(document).ready(function(){

				// initialise plugin
				var example = $('#example').superfish({
					//add options here if required
				});

				// buttons to demonstrate Superfish's public methods
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
			});

		})(jQuery);


		</script>
        
        

        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <script type="text/javascript" src="js/scripts.js"></script>
        <body>

<div class="main-inside">

	<div class="header">
    	<div class="inside">
    		<div class="logo-inside"><img src="images/logo.png" width="337" height="51" /></div>
            <div class="logout">
            	<div class="nav-logout">
                	<ul>
                    	<li class="welcome"><img src="images/welcome-text.png" width="68" height="14" /></li>
                        <li><img src="images/arrow-logout.png" width="5" height="10" /></li>
                        <li>คุณธนาคาร เพื่อการเกษตรฯ</li>
                    </ul>
                </div>
                <div class="btn-logout"><a href="#"><img src="images/btn-logout.png" width="33" height="33" /></a></div>
            </div>
            <div class="clear"></div>  
        </div>
    </div>
    <div class="clear"></div>
    
  	<div class="nav-menu">
    	<div class="inside">
        	<ul class="sf-menu" id="example">
                <li class="management current">
                    <a href="#"></a>
                    <ul class="sf-submenu">
                        <li>
                            <a href="#">การจัดการสาขา</a>
                        </li>
                        <li>
                            <a href="#">เมนูย่อยที่ 1</a>
                        </li>
                        <li class="current">
                            <a href="#">เมนูย่อยที่ 1</a>
                            <ul>
                                <li><a href="#">เมนูย่อยที่ 1</a></li>
                                <li><a href="#">เมนูย่อยที่ 1</a></li>
                                <li><a href="#">เมนูย่อยที่ 1</a></li>
                            </ul>
                        </li>
                        
                    </ul>
                </li>
                <li class="agriculture"><a href="#"></a></li>
                <li class="report"><a href="#"></a></li>
			</ul>
            <ul><input class="search-box" /></ul>
        </div>    	
    </div>
    <div class="clear"></div>
    
    <div class="navigator">
    	<div class="inside">
            <ul>
                <li>หน้าหลัก</li>
                <li><img src="images/arrow-navigator.png" width="4" height="8" /></li>
                <li>ค้นหาข้อมูล</li>
            </ul>
        </div>
    </div>
    
    
    
    <!-- content -->
    <div class="content">
    	<div class="inside">
        
        	<div class="content-search">
                <div class="search-header"><p>ค้นหาข้อมูล</p></div>
                <div class="line-input">
	                <ul>
	                	<li class="topic" style="width: 80px">ชื่อผู้ใช้งาน : </li><li class="boxinput"><input style="width: 200px" /></li>
	                	<li class="topic" style="width: 20px">ชื่อ : </li><li class="boxinput"><input style="width: 200px"/></li>
	                	<li class="topic" style="width: 60px">นามสกุล : </li><li class="boxinput"><input style="width: 200px"/></li>
                	</ul>
                    
                </div>
                
                
       
                
                
                <div class="line-input">
                   
                    <ul>
                    	<li class="topic" style="width: 80px">Email : </li><li class="boxinput"><input style="width: 200px" /></li>
                    	<li class="topic" style="width: 20px">สาขา : </li>
                        <li class="boxinput" style="width: 200px">
                        	 <select>
                                <option selected>กรุณาเลือก</option>
                                <option>สันทราย</option>
                                <option>สันกำแพง</option>
                            </select>
                        </li>
                    </ul>
                    
                </div>

                
            
                <div class="line-input">           
                	<ul class="btn-search-box">
                    	<li>
                		<button class="btn-search"></button>
                    	</li>
                    </ul>
                </div>

            </div>
            <div class="clear"></div>
            
            

            <div class="btn-manage">
            	   <a class="btn-add" href="#"></a>
                   <a class="btn-del" href="#"></a>
            </div>
              <div class="clear"></div>
         
            
                        <div class="topic-header-search" style=" width:980px;">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="selecctall" class="checkbox2" /><label for="selecctall"></label></li>
                    <li class="tel-sr">ชื่อผู้ใช้งาน</li>
                    <li class="address-sr">ชื่อ-สกุล</li>
                    <li class="tel-sr">Email</li>
                    <li class="tel-sr">สาขา</li>
                    <li class="age-sr">สถานะ</li>
                    <li class="manage-sr">การจัดการ</li>
                </ul>
            </div>
            <!-- Squared FOUR -->
            <div class="detail-header-search" style=" width:980px;">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                    <li class="tel-sr">USER1</li>
                    <li class="address-sr">สมชาย ใจดี</li>
                    <li class="tel-sr">xx@hotmail.com</li>
                    <li class="tel-sr">โกสัมพีนคร</li>
                    <li class="age-sr">Active</li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search color" style=" width:980px;">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                     <li class="tel-sr">USER2</li>
                    <li class="address-sr">นฤมล มากมี</li>
                    <li class="tel-sr">yy@hotmail.com</li>
                    <li class="tel-sr">คลองลาน</li>
                    <li class="age-sr">Active</li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search" style=" width:980px;">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                     <li class="tel-sr">USER3</li>
                    <li class="address-sr">สมฤดี สุขใจ</li>
                    <li class="tel-sr">aa@hotmail.com</li>
                    <li class="tel-sr">คลองขลุง</li>
                    <li class="age-sr">Active</li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search color" style=" width:980px;">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                     <li class="tel-sr">USER4</li>
                    <li class="address-sr">กมลพร พิพัฒน์</li>
                    <li class="tel-sr">bb@hotmail.com</li>
                    <li class="tel-sr">คลองแม่ลาย</li>
                    <li class="age-sr">Active</li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search" style=" width:980px;">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                     <li class="tel-sr">USER5</li>
                    <li class="address-sr">วสันต์ อยู่ดี</li>
                    <li class="tel-sr">cc@hotmail.com</li>
                    <li class="tel-sr">โค้งไผ่</li>
                    <li class="age-sr">Inactive</li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search color" style=" width:980px;">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                     <li class="tel-sr">USER6</li>
                    <li class="address-sr">จรัส ศรีวารี</li>
                    <li class="tel-sr">dd@hotmail.com</li>
                    <li class="tel-sr">เชียงของ</li>
                    <li class="age-sr">Active</li>
                    <li class="manage-sr">
                    	    <a class="btn-edit" href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a class="btn-delete" href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="clear"></div>
            
            
            
            
            
        </div>
    </div>
    
    <!--footer -->
    <div class="container-footer">
    	<div class="line-footer"></div>
        <div class="clear"></div>
        <div class="footer">
        	<div class="inside">
        		<div class="copyright">2014 สงวนลิขสิทธิ์ 2557 ธนาคารเพื่อการเกษตรและสหกรณ์การเกษตร (ธ.ก.ส.)</div>
            	<div class="logo-footer"><img src="images/logo-footer.png" width="215" height="32" /></div>
            </div>
        </div>
    </div>
    
    
</div>

</body>
</html>