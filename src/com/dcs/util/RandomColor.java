package com.dcs.util;

import java.util.Random;

public class RandomColor {
	private static Random rand = new Random();
	private static String colors[] = {"#8A0808","#868A08","#088A08","#088A85","#08088A","#8A0886",
									  "#B40404","#AEB404","#04B404","#04B4AE","#0404B4","#B404AE",
									  "#FF0000","#FFFF00","#00FF00","#00FFFF","#0000FF","#FF00FF",
									  "#F78181","#F3F781","#81F781","#81F7F3","#8181F7","#F781F3",
									  "#F781BE","#F7BE81","#BEF781","#81F7BE","#81BEF7","#BE81F7",
									  "#FF0080","#FF8000","#80FF00","#00FF80","#0080FF","#8000FF",
									  "#B4045F","#B45F04","#5FB404","#04B45F","#045FB4","#5F04B4"};
	
	public static String getColor() {
		return colors[rand.nextInt(colors.length)];
	}
	
	public static String getColor(long i) {
		int x = (int)(i % 42);
		return colors[x];
	}
}
