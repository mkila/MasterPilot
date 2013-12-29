package fr.umlv.main;

public class Random {
	public static int GetIntRandom(int lower, int higher) {
		return(int)(Math.random() * ((higher+1)-lower)) + lower;
	}
}
