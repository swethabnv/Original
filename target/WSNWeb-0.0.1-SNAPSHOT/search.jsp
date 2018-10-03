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
                	<ul><li class="topic">ชื่อสาขา : </li><li class="boxinput"><input /></li></ul>
                    
                </div>
                
                
       
                
                
                <div class="line-input">
                   
                    <ul>
                    	<li class="topic">จังหวัด : </li>
                        <li class="boxinput" style="width: 250px">
                        	 <select>
                                <option selected>กรุณาเลือก</option>
                                <option>กรุงเทพมหานคร</option>
                                <option>ราชบุรี</option>
                            </select>
                        </li>
                   
                    	<li class="topic">อำเภอ : </li>
                        <li class="boxinput" style="width: 250px">
                        	 <select>
                                <option selected>กรุณาเลือก</option>
                                <option>สันทราย</option>
                                <option>สันกำแพง</option>
                            </select>
                        </li>
                  
                    	<li class="topic">ตำบล : </li>
                        <li class="boxinput" style="width: 250px">
                        	 <select>
                                <option selected>กรุณาเลือก</option>
                                <option>หนองหาร</option>
                                <option>หนองหอย</option>
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
         
            
                        <div class="topic-header-search">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="selecctall" class="checkbox2" /><label for="selecctall"></label></li>
                    <li class="topic-sr">สาขา</li>
                    <li class="address-sr">ภายใต้สาขา</li>
                    <li class="gender-sr">อำเภอ</li>
                    <li class="gender-sr">ตำบล</li>
                    <li class="gender-sr">จังหวัด</li>
                    <li class="tel-sr">เบอร์โทร</li>
                    <li class="manage-sr">การจัดการ</li>
                </ul>
            </div>
            <!-- Squared FOUR -->
            <div class="detail-header-search">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                    <li class="topic-sr">โกสัมพีนคร</li>
                    <li class="address-sr">ธ.ก.ส. จังหวัดกำแพงเพชร</li>
                    <li class="gender-sr">เมือง</li>
                    <li class="gender-sr">โกสัมพี</li>
                    <li class="gender-sr">กำแพงเพชร</li>
                    <li class="tel-sr">089-234-1725</li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search color">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                    <li class="topic-sr">คลองแม่ลาย</li>
                    <li class="address-sr">ธ.ก.ส. จังหวัดกำแพงเพชร</li>
                    <li class="gender-sr">เมือง</li>
                    <li class="gender-sr">คลองแม่ลาย</li>
                    <li class="gender-sr">กำแพงเพชร</li>
                    <li class="tel-sr">02-345-3728 ต่อ 234</li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                    <li class="topic-sr">คลองลาน</li>
                    <li class="address-sr">ธ.ก.ส. จังหวัดกำแพงเพชร</li>
                    <li class="gender-sr">คลองลาน </li>
                    <li class="gender-sr">คลองน้ำไหล</li>
                    <li class="gender-sr">กำแพงเพชร</li>
                    <li class="tel-sr">053-844-852</li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search color">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                    <li class="topic-sr">ไทรงาม</li>
                    <li class="address-sr">ธ.ก.ส. จังหวัดกำแพงเพชร </li>
                    <li class="gender-sr">ไทรงาม </li>
                    <li class="gender-sr">ไทรงาม</li>
                    <li class="gender-sr">กำแพงเพชร</li>
                    <li class="tel-sr"></li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                    <li class="topic-sr">ระหานรี</li>
                    <li class="address-sr">ธ.ก.ส. จังหวัดกำแพงเพชร </li>
                    <li class="gender-sr">บึงสามัคคี </li>
                    <li class="gender-sr">ระหาน</li>
                    <li class="gender-sr">กำแพงเพชร</li>
                    <li class="tel-sr"></li>
                    <li class="manage-sr">
                    	    <a href="#"><img src="images/btn-edit.png" width="17" height="17" /></a>
                            <a href="#"><img src="images/btn-delete.png" width="18" height="17" /></a>
                    </li>
                </ul>
            </div>
            <div class="detail-header-search color">
            	<ul>
                	<li class="check-list-seach"><input type="checkbox" id="checkbox1" class="checkbox1" /><label for="checkbox1"></label></li>
                    <li class="topic-sr">เขาคีริส</li>
                    <li class="address-sr">ธ.ก.ส. จังหวัดกำแพงเพชร</li>
                    <li class="gender-sr">พรานกระต่าย</li>
                    <li class="gender-sr">เขาคีริส</li>
                    <li class="gender-sr">กำแพงเพชร</li>
                    <li class="tel-sr"></li>
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