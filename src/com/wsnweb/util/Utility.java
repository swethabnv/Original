package com.wsnweb.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Utility {

	private static Properties properties;
	private static Utility _instance = new Utility();
	private static SimpleDateFormat  timeFormat = new SimpleDateFormat("HH.mm",java.util.Locale.US);
	private static SimpleDateFormat  dateFormat = new SimpleDateFormat("dd/MM/yyyy",java.util.Locale.US);
	private static SimpleDateFormat  shortDateFormat = new SimpleDateFormat("ddMMyy",java.util.Locale.US);
	public static String thaiMonth[] = new String[] {"", "มกราคม","กุมภาพันธ์","มีนาคม","เมษายน","พฤษภาคม","มิถุนายน","กรกฏาคม","สิงหาคม","กันยายน","ตุลาคม","พฤศจิกายน","ธันวาคม"};
	public static String thaiMonthShort[] = new String[] {"", "ม.ค.","ก.พ.","มี.ค.","เม.ย.","พ.ค.","มิ.ย.","ก.ค.","ส.ค.","ก.ย.","ต.ค.","พ.ย.","ธ.ค."};
	private static final Logger log = Logger.getLogger(Utility.class);
	
	private static String DBURL = "DBURL";
	private static String DBUSER = "DBUSER";
	private static String DBPASSWORD = "DBPASSWORD";
	
	public static String getDateThai(Date date)
	{
		String convertDate="";
		if(date!=null){			
			String dateStr=dateFormat.format(date);
			String str[]=dateStr.split("/");
			convertDate=Integer.parseInt(str[0])+" "+thaiMonth[Integer.parseInt(str[1])]+" "+String.valueOf(Integer.parseInt(str[2])+543);
		}
		return convertDate;
	}
	
	public static String getMonthYearThai(Date date)
	{
		String convertDate="";
		if(date!=null){			
			String dateStr=dateFormat.format(date);
			String str[]=dateStr.split("/");
			convertDate=thaiMonth[Integer.parseInt(str[1])]+" "+String.valueOf(Integer.parseInt(str[2])+543);
		}
		return convertDate;
	}
	
	public String getDateThaiShort(Date date)
	{
		String convertDate="";
		if(date!=null){			
			String dateStr=dateFormat.format(date);
			String str[]=dateStr.split("/");
			convertDate=Integer.parseInt(str[0])+" "+thaiMonthShort[Integer.parseInt(str[1])]+" "+String.valueOf(Integer.parseInt(str[2])+543).substring(2);
		}
		return convertDate;
	}
	
	public String getTime(Date date)
	{
		String convertDate="";
		if(date!=null){	
			convertDate=timeFormat.format(date)+" น.";
		}
		return convertDate;
	}
	
	public static String getShortDate(){
		return shortDateFormat.format(new Date());
	}
	
	public static String getShortThDate(Date d){
		return dateFormat.format(d);
	}
	
	public static String getThDate(Date d){
		if(d == null) return "";
		Locale localeTH = new Locale("th", "TH" );
		SimpleDateFormat dateFormatTH = new SimpleDateFormat("dd/MM/yyyy", localeTH);
		return dateFormatTH.format(d);
	}
	
	public static String get(String key){
		return _instance._get(key);
	}
	
	private String _get(String key){
		if(properties == null){
	    	properties = new Properties();
		    try {
		    	properties.load(Utility.class.getResourceAsStream("config.properties"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String keyValue = properties.getProperty(key);
		if(keyValue !=null && !"".equals(keyValue)){
			if(!key.equals(DBURL) && !key.equals(DBUSER) && !key.equals(DBPASSWORD) ){
				keyValue = unescape(keyValue);
			}
		}
        return keyValue ;	    	
	}
	
	public static String unescape(String s) {
		StringBuffer sbuf = new StringBuffer () ;
		if(s != null && !"".equals(s)){
			int l  = s.length() ;
			int ch = -1 ;
			int b, sumb = 0;
			for (int i = 0, more = -1 ; i < l ; i++) {
				/* Get next byte b from URL segment s */
				switch (ch = s.charAt(i)) {
				case '%':
					ch = s.charAt (++i) ;
					int hb = (Character.isDigit ((char) ch)
							? ch - '0'
							: 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
					ch = s.charAt (++i) ;
					int lb = (Character.isDigit ((char) ch) 
							? ch - '0'
							: 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
					b = (hb << 4) | lb ;
					break ;
				case '+':
					b = ' ' ;
					break ;
				default:
					b = ch ;
				}
				/* Decode byte b as UTF-8, sumb collects incomplete chars */
				if ((b & 0xc0) == 0x80) {			// 10xxxxxx (continuation byte)
					sumb = (sumb << 6) | (b & 0x3f) ;	// Add 6 bits to sumb
					if (--more == 0) sbuf.append((char) sumb) ; // Add char to sbuf
				} else if ((b & 0x80) == 0x00) {		// 0xxxxxxx (yields 7 bits)
					sbuf.append((char) b) ;			// Store in sbuf
				} else if ((b & 0xe0) == 0xc0) {		// 110xxxxx (yields 5 bits)
					sumb = b & 0x1f;
					more = 1;				// Expect 1 more byte
				} else if ((b & 0xf0) == 0xe0) {		// 1110xxxx (yields 4 bits)
					sumb = b & 0x0f;
					more = 2;				// Expect 2 more bytes
				} else if ((b & 0xf8) == 0xf0) {		// 11110xxx (yields 3 bits)
					sumb = b & 0x07;
					more = 3;				// Expect 3 more bytes
				} else if ((b & 0xfc) == 0xf8) {		// 111110xx (yields 2 bits)
					sumb = b & 0x03;
					more = 4;				// Expect 4 more bytes
				} else /*if ((b & 0xfe) == 0xfc)*/ {	// 1111110x (yields 1 bit)
					sumb = b & 0x01;
					more = 5;				// Expect 5 more bytes
				}
				/* No need to test if the UTF-8 encoding is well-formed */
			}
		}
		return sbuf.toString() ;
	}
	
	public static String toDateThai(String inputDate){
		String[] inputBuff = inputDate.split("/");

		String inputDay    = inputBuff[0];
		String inputMonth  = inputBuff[1];
		String inputYear   = inputBuff[2];
		
		int intYear        = Integer.parseInt(inputYear);
		inputYear		   = String.valueOf(intYear).substring(2);		 	

		GregorianCalendar selectedDate = new GregorianCalendar(Locale.US);		
		if(intYear > 2200){
			intYear -= 543;
		}
		selectedDate.set(intYear, Integer.parseInt(inputMonth)-1,Integer.parseInt(inputDay));
		Date date = selectedDate.getTime();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMMM yyyy", new Locale("th", "TH"));
		return simpleDateFormat.format(date);
	}
	
	public static Date toDate(String dateStr){
		String monthStr = "";
		int    year=0;
		Date    date = null;
		try{
			if(dateStr != null && !"".equals(dateStr)){
				String cutDateStr = dateStr.substring(0,dateStr.indexOf(" "));
				String tempCutM_YStr = dateStr.substring(dateStr.indexOf(" ")+1);
				String cutMonthStr = tempCutM_YStr.substring(0, tempCutM_YStr.indexOf(" "));
				String cutYearStr  = tempCutM_YStr.substring(tempCutM_YStr.indexOf(" "));
				
				if("มกราคม".equals(cutMonthStr)){ 			monthStr = "01"; 
				} else if("กุมภาพันธ์".equals(cutMonthStr)){ monthStr = "02";
				} else if("มีนาคม".equals(cutMonthStr)){ 	monthStr = "03"; 
				} else if("เมษายน".equals(cutMonthStr)){ 	monthStr = "04";
				} else if("พฤษภาคม".equals(cutMonthStr)){ 	monthStr = "05"; 
				} else if("มิถุนายน".equals(cutMonthStr)){ 	monthStr = "06";
				} else if("กรกฎาคม".equals(cutMonthStr)){ 	monthStr = "07";
				} else if("สิงหาคม".equals(cutMonthStr)){ 	monthStr = "08";
				} else if("กันยายน".equals(cutMonthStr)){ 	monthStr = "09";
				} else if("ตุลาคม".equals(cutMonthStr)){ 	monthStr = "10";
				} else if("พฤศจิกายน".equals(cutMonthStr)){ monthStr = "11";
				} else if("ธันวาคม".equals(cutMonthStr)){ 	monthStr = "12"; } 
				
				year = Integer.parseInt(cutYearStr.trim())-543;

				GregorianCalendar selectedDate = new GregorianCalendar(Locale.US);		
				if(year > 2200){
					year -= 543;
				}
				selectedDate.set(year,Integer.parseInt(monthStr)-1,Integer.parseInt(cutDateStr));
				date = selectedDate.getTime();
			}
		}catch(Exception ex){
			log.error(ex);		
		}
		return date;
	}
	
	public static String mimeToExtensionTypeMapping (String mimeType)
	{
		if(mimeType!=null && !"".equals(mimeType)){
			if(mimeType.equals("image/gif"))
			{
				return "gif";
			}else if(mimeType.equals("image/jpg"))
			{
				return "jpg";
			}else if(mimeType.equals("image/jpeg")||mimeType.equals("image/pjpeg"))
			{
				return "jpeg";
			}else if(mimeType.equals("image/png"))
			{
				return "png";
			}else if(mimeType.equals("image/tiff"))
			{
				return "tiff";
			}else if(mimeType.equals("image/bmp"))
			{
				return "bmp";
			}else
			{
				log.info("Extension Other Type ---> "+mimeType);
				return "jpg";
			}
		}
		return "";
	}
	
	public static Date stringToDate(String strDate)
	{
		Date date = null;
		Calendar cal = Calendar.getInstance();
		try {
			if(!strDate.equals("")) {
				cal.setTime(dateFormat.parse(strDate));
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-543);
				date = cal.getTime();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String convertToDateStr(String dtStr)
	{
		String dtFormat = "";
		if(dtStr.indexOf("/")!=-1){
			String[] convDate = dtStr.split("/");
			if(convDate[0].length()==1)
				convDate[0] = "0" + convDate[0];
			if(convDate[1].length()==1)
				convDate[1] = "0" + convDate[1];
			dtFormat = convDate[0]+convDate[1]+convDate[2];
		}
		return dtFormat;
	}
	
	public static String[] getYears(){
		String[] yearList = new String[5];
		int yearInt = Calendar.getInstance().get(Calendar.YEAR)+543;
		yearList[0] = yearInt+"";
		int yearTemp = yearInt;
		for(int i=1; i<5; i++){
			yearTemp--;
			yearList[i] = yearTemp+"";
		}
		return yearList;
	}
	
	public static String[] getConnections(){
		String[] connList = new String[3];
		connList[0] = Utility.get(DBURL);
		connList[1] = Utility.get(DBUSER);
		connList[2] = Utility.get(DBPASSWORD);
		
		return connList;
	}
	
}
