package fr.umlv.main;

import java.awt.Color;

import org.jbox2d.common.Vec2;

import fr.umlv.graphics.GraphicsEngine;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.service.ServiceHero;
import fr.umlv.zen3.Application;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;

public class Main {

	
	public static void main(String[] args) {
			    int WIDTH = 800;
			    int HEIGHT = 600;
			     
			    SpaceObject hero = new SpaceShip(new ServiceHero(PhysicsEngine.getWorld()));
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
			        GraphicsEngine.setBackground(context,Color.BLACK);
			        GraphicsEngine.drawSpaceObject(context);
			        KeyboardEvent event = context.waitKeyboard();
			        if (event == null) {
			          return;
			        }
			        context.render(graphics -> {
			          if(event.getKey() == KeyboardKey.DOWN){
			        	  Vec2 tmp = new Vec2(hero.getService().getBody().getPosition().x,hero.getService().getBody().getPosition().y--);
			        	  hero.getService().getBody().applyLinearImpulse(tmp.mul(10), hero.getService().getBody().getPosition());
			        	  GraphicsEngine.drawSpaceObject(context);
			        	  System.out.println(hero.getService().getBody().getPosition().toString());
			        	  
			          }
			          if(event.getKey() == KeyboardKey.UP){
			        	  Vec2 tmp = new Vec2(hero.getService().getBody().getPosition().x,hero.getService().getBody().getPosition().y++);
			        	  hero.getService().getBody().applyLinearImpulse(tmp.mul(10), hero.getService().getBody().getPosition());
			        	  GraphicsEngine.drawSpaceObject(context);
			        	  System.out.println(hero.getService().getBody().getPosition().toString());
			        	  
			          }
			          if(event.getKey() == KeyboardKey.LEFT){
			        	  Vec2 tmp = new Vec2(hero.getService().getBody().getPosition().x--,hero.getService().getBody().getPosition().y);
			        	  hero.getService().getBody().applyLinearImpulse(tmp.mul(10), hero.getService().getBody().getPosition());
			        	  GraphicsEngine.drawSpaceObject(context);
			        	  System.out.println(hero.getService().getBody().getPosition().toString());
			        	  
			          }
			          if(event.getKey() == KeyboardKey.RIGHT){
			        	  Vec2 tmp = new Vec2(hero.getService().getBody().getPosition().x++,hero.getService().getBody().getPosition().y);
			        	  hero.getService().getBody().applyLinearImpulse(tmp.mul(10), hero.getService().getBody().getPosition());
			        	  GraphicsEngine.drawSpaceObject(context);
			        	  System.out.println(hero.getService().getBody().getPosition().toString());
			        	  
			          }
			         	  
			        });
			      }
			    });
			  }

}
