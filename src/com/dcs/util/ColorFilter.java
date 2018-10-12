package com.dcs.util;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class ColorFilter extends RGBImageFilter {
	Color color;
	
	public ColorFilter() {
		canFilterIndexColorModel = true;
	}
	
	public ColorFilter(Color color) {
		canFilterIndexColorModel = true;
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public int filterRGB(int x, int y, int rgb) {
		if (rgb==Color.BLACK.getRGB()) return this.color.getRGB();
		else if (rgb==Color.WHITE.getRGB()) return Color.TRANSLUCENT;
		else return rgb;
	}
}
