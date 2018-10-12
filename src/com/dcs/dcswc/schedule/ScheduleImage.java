package com.dcs.dcswc.schedule;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

 public class ScheduleImage extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	private static final long serialVersionUID = 3644693155112094509L;
	private static final Logger log = Logger.getLogger(ScheduleImage.class);
	
	public ScheduleImage() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}  	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}
	
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String imgfile=request.getParameter("imgfile");
			String color=request.getParameter("color");
			String path = getServletContext().getRealPath("\\");
			byte[] buffImg = ScheduleImageFactory.getBytes(path, imgfile, color);
			if (buffImg!=null) {
				Date d = new Date();
				d.setTime(d.getTime()+86400000);
				response.setContentType("image/gif");
				response.setHeader("Cache-Control", "mag-age=86400");
				response.setHeader("Expires", d.toString());
				OutputStream out = response.getOutputStream();
				out.write(buffImg);			
				out.close();
			} else {
				log.error("Error load schedule image "+imgfile+" color "+color);
			}
		} catch(Exception ex) {		
		}
	}
}