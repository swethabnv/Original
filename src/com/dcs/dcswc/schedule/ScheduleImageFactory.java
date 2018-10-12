package com.dcs.dcswc.schedule;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;

import com.dcs.util.HexUtil;

public class ScheduleImageFactory {
		
	static String filelist[] = {"startdot1.gif","centerdot1.gif","enddot1.gif","dot1.gif","startdot2.gif","centerdot2.gif","enddot2.gif","dot2.gif","startdot3.gif","centerdot3.gif","enddot3.gif","dot3.gif"};
	static HashMap<String, byte[]> imageMap = null;
	
	private static void initMap(String path) {
		imageMap = new HashMap<String, byte[]>();
		try {
			for(int i=0; i<filelist.length; i++) {
				File file = new File(path+"dcswc\\images\\"+filelist[i]);
				byte[] rawimg = new byte[(int) file.length()];
				FileInputStream fileInput = new FileInputStream(file);
				fileInput.read(rawimg);
				imageMap.put(filelist[i], rawimg);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static byte[] loadImage(String path, String imgfile) {
		try {
			File file = new File(path+"dcswc\\images\\"+imgfile);
			byte[] rawimg = new byte[(int) file.length()];
			FileInputStream fileInput = new FileInputStream(file);
			fileInput.read(rawimg);
			imageMap.put(imgfile, rawimg);
			return rawimg;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage getImage(String path, String imgfile, String color) {
		String c = null;
		if (color.startsWith("#")) {
			c = color.substring(1, color.length());
		} else {
			c = color;
		}
		try {
			if (imageMap==null) {
				initMap(path);
			}
			byte[] rawimg = imageMap.get(imgfile);
			byte[] colorbyte = HexUtil.hexToByte(c);
			if (rawimg==null) {
				rawimg=loadImage(path, imgfile);
			}
			rawimg[13] = colorbyte[0];
			rawimg[14] = colorbyte[1];
			rawimg[15] = colorbyte[2];
			return ImageIO.read(new ByteArrayInputStream(rawimg));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static byte[] getBytes(String path, String imgfile, String color) {
		String c = null;
		if (color.startsWith("#")) {
			c = color.substring(1, color.length());
		} else {
			c = color;
		}
		try {
			if (imageMap==null) {
				initMap(path);
			}
			byte[] rawimg = imageMap.get(imgfile);
			byte[] colorbyte = HexUtil.hexToByte(c);
			if (rawimg!=null && colorbyte!=null) {
				rawimg[13] = colorbyte[0];
				rawimg[14] = colorbyte[1];
				rawimg[15] = colorbyte[2];
				return rawimg;
			} else {
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
