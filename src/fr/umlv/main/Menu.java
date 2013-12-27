package fr.umlv.main;


import java.awt.Color;
import java.awt.Font;

import fr.umlv.zen3.Application;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;


public class Menu {

	/**
	 *
	 * Print the main menu of the application
	 * Allow you to choose  your start level
	 * 
	 **/

	/**
	 * Load the primary resource of the game need
	 * Allow the selection of the level
	 * 
	 * */
	public static void printMenu() {
		
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
					Game g = new Game();
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
