package fr.umlv.main;

import java.awt.Color;


import org.jbox2d.common.Vec2;


import fr.umlv.graphics.GraphicsEngine;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.Planet;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.service.ServiceHero;
import fr.umlv.space.service.ServicePlanet;
import fr.umlv.zen3.Application;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;
import static java.lang.Math.atan2;

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
		//			    hero.getService().getBody().setLinearVelocity(new Vec2(hero.getService().getBody().getPosition().x,
		//			    		hero.getService().getBody().getPosition().y--));

		System.out.println(hero.getService().getBody().getPosition().toString());
		Application.run("MasterPilot", WIDTH, HEIGHT, context -> {
			for(;;) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				PhysicsEngine.getWorld().step(timeStep,velocityIterations,positionIterations);
				GraphicsEngine.graphicClear(context);
				GraphicsEngine.setBackground(context,Color.BLACK);
				GraphicsEngine.drawSpaceObject(context,hero,hero.getService().getBody().getPosition());
				GraphicsEngine.drawSpaceObject(context,planet1,hero.getService().getBody().getPosition());
				GraphicsEngine.drawSpaceObject(context,planet2,hero.getService().getBody().getPosition());
				//System.out.println(hero.getService().getBody().getPosition().toString());
				
				context.render(graphics -> {
					KeyboardEvent event = context.pollKeyboard();
					if (event == null) {
						return;
					}
					if(event.getKey() == KeyboardKey.DOWN){
						Vec2 tmp = new Vec2(hero.getService().getBody().getWorldCenter().x,hero.getService().getBody().getWorldCenter().y--);
						hero.getService().getBody().applyLinearImpulse(tmp.mul(1), hero.getService().getBody().getWorldCenter());
						//GraphicsEngine.drawSpaceObject(context,hero);

					}
					if(event.getKey() == KeyboardKey.UP){
						Vec2 tmp = new Vec2(hero.getService().getBody().getWorldCenter().x,hero.getService().getBody().getWorldCenter().y++);
						hero.getService().getBody().applyLinearImpulse(tmp.mul(1), hero.getService().getBody().getWorldCenter());
						//GraphicsEngine.drawSpaceObject(context,hero);

					}
					if(event.getKey() == KeyboardKey.LEFT){
						float bodyAngle = hero.getService().getBody().getAngle();
						Vec2 tmp = new Vec2(-50-hero.getService().getBody().getPosition().x, 0+hero.getService().getBody().getPosition().y);
						//hero.getService().getBody().applyLinearImpulse(tmp.mul(10), hero.getService().getBody().getPosition());
						float desiredAngle = (float) atan2( -tmp.x, tmp.y );
						float totalRotation = desiredAngle - bodyAngle;
						float change = (float) (1 * Math.toRadians(totalRotation)); 
						float newAngle = bodyAngle + Math.min( change, Math.max(-change, totalRotation));
						hero.getService().getBody().setTransform( hero.getService().getBody().getPosition(), newAngle );
						//GraphicsEngine.drawSpaceObject(context,hero);

						System.out.println("angle :"+hero.getService().getBody().getAngle());

					}
					if(event.getKey() == KeyboardKey.RIGHT){
						float bodyAngle = hero.getService().getBody().getAngle();
						Vec2 tmp = new Vec2(50+hero.getService().getBody().getPosition().x, 0+hero.getService().getBody().getPosition().y);
						//hero.getService().getBody().applyLinearImpulse(tmp.mul(100), hero.getService().getBody().getPosition());
						float desiredAngle = (float) atan2( -tmp.x, tmp.y );
						float totalRotation = desiredAngle - bodyAngle;
						float change = (float) (1 * Math.toRadians(totalRotation)); 
						float newAngle = bodyAngle + Math.min( change, Math.max(-change, totalRotation));
						hero.getService().getBody().setTransform( hero.getService().getBody().getPosition(), newAngle );
						//GraphicsEngine.drawSpaceObject(context,hero);

						System.out.println("angle :"+hero.getService().getBody().getAngle());

					}

				});
			}
		});
	}

}
