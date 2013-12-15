package fr.umlv.main;

import java.awt.Color;

import org.jbox2d.collision.shapes.PolygonShape;
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
			     
			    SpaceObject hero = new SpaceShip(new ServiceHero(PhysicsEngine.getWorld()));
			    
			    SpaceObject planet1 = new Planet(new ServicePlanet(PhysicsEngine.getWorld(), 10, new Vec2(0,0)));
			    SpaceObject planet2 = new Planet(new ServicePlanet(PhysicsEngine.getWorld(), 65, new Vec2(300,300)));
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
			        PolygonShape poly =	(PolygonShape)	hero.getService().getBody().getFixtureList().getShape();
					int[] x = new int[poly.getVertices().length];
					int[] y= new int[poly.getVertices().length];
					int i = 0;
					for(Vec2 vertice : poly.getVertices()){
						x[i]=(int) hero.getService().getBody().getWorldVector(vertice).x;
						y[i]=(int) hero.getService().getBody().getWorldVector(vertice).y;
						i++;
					}
			        GraphicsEngine.graphicClear(context);
			        GraphicsEngine.setBackground(context,Color.BLACK);
			        GraphicsEngine.drawSpaceObject(context,hero);
			        GraphicsEngine.drawSpaceObject(context,planet1);
			        GraphicsEngine.drawSpaceObject(context,planet2);
			        KeyboardEvent event = context.waitKeyboard();
			        if (event == null) {
			          return;
			        }
			        context.render(graphics -> {
			          if(event.getKey() == KeyboardKey.DOWN){
			        	  Vec2 tmp = new Vec2(hero.getService().getBody().getPosition().x,hero.getService().getBody().getPosition().y--);
			        	  hero.getService().getBody().applyLinearImpulse(tmp.mul(10), hero.getService().getBody().getPosition());
			        	  GraphicsEngine.drawSpaceObject(context,hero);
			        	  System.out.println(hero.getService().getBody().getPosition().toString());
			        	  
			          }
			          if(event.getKey() == KeyboardKey.UP){
			        	  Vec2 tmp = new Vec2(hero.getService().getBody().getPosition().x,hero.getService().getBody().getPosition().y++);
			        	  hero.getService().getBody().applyLinearImpulse(tmp.mul(10), hero.getService().getBody().getPosition());
			        	  GraphicsEngine.drawSpaceObject(context,hero);
			        	  System.out.println(hero.getService().getBody().getPosition().toString());
			        	  
			          }
			          if(event.getKey() == KeyboardKey.LEFT){
			        	  float bodyAngle = hero.getService().getBody().getAngle();
			        	  Vec2 tmp = new Vec2(-50-hero.getService().getBody().getPosition().x, 0+hero.getService().getBody().getPosition().y);
			        	  hero.getService().getBody().applyLinearImpulse(tmp.mul(100), hero.getService().getBody().getPosition());
			        	  float desiredAngle = (float) atan2( -tmp.x, tmp.y );
			        	  float totalRotation = desiredAngle - bodyAngle;
			        	  float change = (float) (1 * Math.toRadians(totalRotation)); 
			        	  float newAngle = bodyAngle + Math.min( change, Math.max(-change, totalRotation));
			        	  hero.getService().getBody().setTransform( hero.getService().getBody().getPosition(), newAngle );
			        	  GraphicsEngine.drawSpaceObject(context,hero);
			        	  
			        	  System.out.println(hero.getService().getBody().getPosition().toString());
			        	  System.out.println("angle :"+hero.getService().getBody().getAngle());
			        	  
			          }
			          if(event.getKey() == KeyboardKey.RIGHT){
			        	  float bodyAngle = hero.getService().getBody().getAngle();
			        	  Vec2 tmp = new Vec2(50+hero.getService().getBody().getPosition().x, 0+hero.getService().getBody().getPosition().y);
			        	  hero.getService().getBody().applyLinearImpulse(tmp.mul(100), hero.getService().getBody().getPosition());
			        	  float desiredAngle = (float) atan2( -tmp.x, tmp.y );
			        	  float totalRotation = desiredAngle - bodyAngle;
			        	  float change = (float) (1 * Math.toRadians(totalRotation)); 
			        	  float newAngle = bodyAngle + Math.min( change, Math.max(-change, totalRotation));
			        	  hero.getService().getBody().setTransform( hero.getService().getBody().getPosition(), newAngle );
			        	  GraphicsEngine.drawSpaceObject(context,hero);
			        	  
			        	  System.out.println(hero.getService().getBody().getPosition().toString());
			        	  System.out.println("angle :"+hero.getService().getBody().getAngle());
			        	  
			          }
			         	  
			        });
			      }
			    });
			  }

}
