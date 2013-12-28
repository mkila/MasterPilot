package fr.umlv.main;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import org.jbox2d.common.Timer;
import org.jdom2.JDOMException;

import fr.umlv.graphics.GraphicsEngine;
import fr.umlv.keylistener.KeyListener;
import fr.umlv.level.Level;
import fr.umlv.parsor.Parsor;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.service.Service;
import fr.umlv.space.service.ServiceHero;
import fr.umlv.zen3.ApplicationContext;

public class Game implements GameManager{

	private final Level lvl;

	public Game(){
		lvl = new Level();
	}

	@Override
	public void printPlay(ApplicationContext context) throws JDOMException, IOException {

		float timeStep = 1.0f/60.0f;      //the length of time passed to simulate (seconds)
		int velocityIterations = 2;   //how strongly to correct velocity
		int positionIterations = 2; 
		Timer t = new Timer();
		SpaceObject hero = new SpaceShip(new ServiceHero(PhysicsEngine.getWorld()));
		lvl.createPlanet(Parsor.parserXML("stage1.xml","planet"));
		lvl.createBomb(Parsor.parserXML("stage1.xml","bomb"), Service.TYPEBONUS.BOMB);
		lvl.createBomb(Parsor.parserXML("stage1.xml","bomb"), Service.TYPEBONUS.MEGA);
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
			
			// Print the planet of the world
			for(int i=0;i<lvl.getListPlanet().size();i++){
				GraphicsEngine.drawSpaceObject(context,lvl.getListPlanet().get(i),hero.getService().getBody().getWorldCenter());	
			}

			// Print the bomb of the world
			for(int i=0;i<lvl.getListBomb().size();i++){
				GraphicsEngine.drawBomb(context,lvl.getListBomb().get(i),hero.getService().getBody().getWorldCenter());	
			}
			
			//Print fire shoot
			GraphicsEngine.drawFire(context, hero,hero.getService().getBody().getWorldCenter());

			KeyListener.listen(hero, context);
			context.render(graphics -> {
				graphics.setColor(Color.WHITE);
				graphics.setFont(new Font("Courrier", Font.BOLD, 20));
				graphics.drawString(String.valueOf(String.format("%1.0f",t.getMilliseconds()/1000)), 750,20);
				//Print the bomb ammo
				graphics.setColor(Color.RED);
				graphics.drawString("Bomb: "+hero.getService().getMunition().getMunitionBomb(),700,50);
				graphics.setColor(Color.YELLOW);
				graphics.drawString("Mega: "+hero.getService().getMunition().getMunitionMega(),700,80);
			});
		}
	}
}
