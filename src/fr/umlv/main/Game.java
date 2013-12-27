package fr.umlv.main;

import java.awt.Color;







import java.awt.Font;

import org.jbox2d.common.Timer;
import org.jbox2d.common.Vec2;










import fr.umlv.graphics.GraphicsEngine;
import fr.umlv.keylistener.KeyListener;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.Planet;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.service.ServiceHero;
import fr.umlv.space.service.ServicePlanet;
import fr.umlv.zen3.ApplicationContext;

public class Game implements GameManager{

	@Override
	public void printPlay(ApplicationContext context) {

		float timeStep = 1.0f/60.0f;      //the length of time passed to simulate (seconds)
		int velocityIterations = 2;   //how strongly to correct velocity
		int positionIterations = 2; 
		Timer t = new Timer();
		SpaceObject hero = new SpaceShip(new ServiceHero(PhysicsEngine.getWorld()));
		SpaceObject planet1 = new Planet(new ServicePlanet(PhysicsEngine.getWorld(), 100, new Vec2(0,0)));
		SpaceObject planet2 = new Planet(new ServicePlanet(PhysicsEngine.getWorld(), 100, new Vec2(800,600)));
		for(;;){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			PhysicsEngine.getWorld().step(timeStep,velocityIterations,positionIterations);
			GraphicsEngine.graphicClear(context);
			GraphicsEngine.setBackground(context,Color.BLACK);
			GraphicsEngine.drawSpaceObject(context,hero,hero.getService().getBody().getWorldCenter());
			GraphicsEngine.drawSpaceObject(context,planet1,hero.getService().getBody().getWorldCenter());
			GraphicsEngine.drawSpaceObject(context,planet2,hero.getService().getBody().getWorldCenter());
			GraphicsEngine.drawFire(context, hero,hero.getService().getBody().getWorldCenter());
			KeyListener.listen(hero, context);
			context.render(graphics -> {
					graphics.setColor(Color.WHITE);
					graphics.setFont(new Font("Courrier", Font.BOLD, 20));
					graphics.drawString(String.valueOf(String.format("%1.0f",t.getMilliseconds()/1000)), 750,20);
			});


		}

	}

}
