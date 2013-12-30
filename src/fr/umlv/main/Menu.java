package fr.umlv.main;


import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import fr.umlv.zen3.Application;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;


public class Menu {

	/**
	 * Print the main menu of the application
	 * Allow you to choose  your start level
	 **/

	/**
	 * Load the primary resource of the game need
	 * Allow the selection of the level 
	 **/
	ArrayList<String> level = new ArrayList<>();

	/**
	 * Print the menu of the application
	 * @param fileName, a xml fileName or null (take all the xml file in .)
	 */
	public void printMenu(String fileName) {
		
		if(fileName == null){
			String path = "."; 
			String files;
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++){
				if (listOfFiles[i].isFile()){
					files = listOfFiles[i].getName();
					if (files.endsWith(".xml")){
						level.add(files);
					}
				}
			}
		}
		else
			level.add(fileName);

		Application.run("MasterPilot", GameManager.WIDTH, GameManager.HEIGHT, context -> {
			for(;;) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				context.render(graphics -> {
					graphics.setBackground(Color.WHITE);
					graphics.setColor(Color.BLACK);
					graphics.setFont(new Font("Courrier", Font.BOLD, 40));
					graphics.drawString("MASTER PILOT", 225,50);

					graphics.setFont(new Font("Courrier", Font.BOLD, 15));
					graphics.drawString("Start (press s)", 300,300);
					graphics.drawString("Exit (press e)", 300,320);
					graphics.drawString("Authors : Baulamon - Tran Master 1", 20,590);

				});

				KeyboardEvent event = context.waitKeyboard();
				if(event.getKey() == KeyboardKey.S){
					Game g = new Game(level);
					try {
						g.printPlay(context);
					} catch (Exception e) {
					}
				}
				if(event.getKey() == KeyboardKey.E){
					System.exit(0);
				}
			}
		});
	}
}
