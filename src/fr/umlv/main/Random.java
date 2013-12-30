package fr.umlv.main;

public class Random {
	
	/**
	 * This class provide random number between a specific range.
	 */
	
	/**
	 * Get a random number between lower and higher both inclusive
	 * @param lower, the lower range
	 * @param higher, the upper range
	 * @return a random number
	 */
	public static int GetIntRandom(int lower, int higher) {
		return(int)(Math.random() * ((higher+1)-lower)) + lower;
	}
}
