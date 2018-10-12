package com.dcs.util;

import java.awt.Color;

public class ColorUtil {
	public static Color fromHtmlColor(String color){
		try {
			String c = null;
			if (color.startsWith("#")) {
				c = color.substring(1, color.length());
			} else {
				c = color;
			}
			String red = c.substring(0,2);
			String green = c.substring(2,4);
			String blue = c.substring(4,6);
			int r = Integer.parseInt(red, 16);
			int g = Integer.parseInt(green, 16);
			int b = Integer.parseInt(blue, 16);
			return new Color(r, g, b);
		} catch (Exception e) {
			return new Color(0,0,0);
		}
	}
}
