package fr.umlv.main;

import fr.umlv.zen3.ApplicationContext;

public interface GameManager {
	
	/**
	 * Interface which initialize a new game
	 * 
	 **/
	final int WIDTH = 800;
	final int HEIGHT = 600;
	
	/**
	 * This method load all the resource need for a game level :
	 * hero, enemies, weapon ...
	 * 
	 * @param context a context object that take the window
	 *  of the Menu class
	 */
	void printPlay(ApplicationContext context);

}
