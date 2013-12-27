package fr.umlv.main;

import java.awt.Color;






import org.jbox2d.common.Vec2;






import fr.umlv.graphics.GraphicsEngine;
import fr.umlv.keylistener.KeyListener;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.Planet;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.service.ServiceHero;
import fr.umlv.space.service.ServicePlanet;
import fr.umlv.space.service.ServiceTIE;
import fr.umlv.zen3.Application;

public class Main {


	public static void main(String[] args) {
		int WIDTH = 800;
		int HEIGHT = 600;
		  float timeStep = 1.0f/60.0f;      //the length of time passed to simulate (seconds)
		  int velocityIterations = 2;   //how strongly to correct velocity
		  int positionIterations = 2; 
		  
		SpaceObject hero = new SpaceShip(new ServiceHero(PhysicsEngine.getWorld()));
		SpaceObject planet1 = new Planet(new ServicePlanet(PhysicsEngine.getWorld(), 100, new Vec2(0,0)));
		SpaceObject planet2 = new Planet(new ServicePlanet(PhysicsEngine.getWorld(), 100, new Vec2(800,600)));
		SpaceObject enemiTIE = new SpaceShip(new ServiceTIE(PhysicsEngine.getWorld(),new Vec2(300,60)));
		System.out.println(hero.getService().getBody().getPosition().toString());
		Application.run("MasterPilot", WIDTH, HEIGHT, context -> {
			for(;;) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				enemiTIE.getService().fire(hero.getService().getBody().getWorldCenter());
				PhysicsEngine.getWorld().step(timeStep,velocityIterations,positionIterations);
				GraphicsEngine.graphicClear(context);
				GraphicsEngine.setBackground(context,Color.BLACK);
				GraphicsEngine.drawSpaceObject(context,hero,hero.getService().getBody().getWorldCenter());
				GraphicsEngine.drawSpaceObject(context,planet1,hero.getService().getBody().getWorldCenter());
				GraphicsEngine.drawSpaceObject(context,planet2,hero.getService().getBody().getWorldCenter());
				//GraphicsEngine.drawSpaceObject(context,enemiTIE,hero.getService().getBody().getWorldCenter());
				GraphicsEngine.drawTIE(context, enemiTIE, hero.getService().getBody().getWorldCenter());
				GraphicsEngine.drawFire(context, hero,hero.getService().getBody().getWorldCenter());
				GraphicsEngine.drawFire(context, enemiTIE,hero.getService().getBody().getWorldCenter());
				KeyListener.listen(hero, context);
				PhysicsEngine.tieBehavior(enemiTIE,hero.getService().getBody().getWorldCenter());

			}
		});
	}

}
