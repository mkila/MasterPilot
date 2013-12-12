package fr.umlv.main;

import java.awt.Color;

import fr.umlv.graphics.GraphicsEngine;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.service.ServiceHero;
import fr.umlv.zen3.Application;

public class Main {

	
	public static void main(String[] args) {
			    int WIDTH = 800;
			    int HEIGHT = 600;
			     
			    SpaceObject hero = new SpaceShip(new ServiceHero(PhysicsEngine.getWorld()));
			    Application.run("MasterPilot", WIDTH, HEIGHT, context -> {
			    	for(;;) {
			        try {
			          Thread.sleep(10);
			        } catch (InterruptedException e) {
			          Thread.currentThread().interrupt();
			        }
			        GraphicsEngine.setBackground(context,Color.BLACK);
			        GraphicsEngine.drawSpaceObject(context);
			      }
			    });
			  }


}
