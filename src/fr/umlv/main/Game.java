package fr.umlv.main;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import org.jbox2d.common.Timer;
import org.jbox2d.common.Vec2;
import org.jdom2.JDOMException;

import fr.umlv.graphics.GraphicsEngine;
import fr.umlv.keylistener.KeyListener;
import fr.umlv.level.Level;
import fr.umlv.parsor.Parsor;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.service.Service;
import fr.umlv.space.service.Service.TYPEBONUS;
import fr.umlv.space.service.ServiceHero;
import fr.umlv.space.service.ServiceArmada;
import fr.umlv.zen3.ApplicationContext;

public class Game implements GameManager{

	/**
	 * This class load all you need for your level
	 * 
	 **/

	private final Level lvl;

	/**
	 * The constructor take a level, 
	 * @param Level, lvl load the level you have selected
	 **/

	public Game(){
		lvl = new Level();
	}

	@Override
	public void printPlay(ApplicationContext context) throws JDOMException, IOException {

		float timeStep = 1.0f/60.0f;      //the length of time passed to simulate (seconds)
		int velocityIterations = 2;   //how strongly to correct velocity
		int positionIterations = 2; 
		Timer t = new Timer();
		ServiceHero sH = new ServiceHero(PhysicsEngine.getWorld());
		SpaceObject hero = new SpaceShip(sH);
		SpaceObject enemi = new SpaceShip(new ServiceArmada(PhysicsEngine.getWorld(),new Vec2(300,60)));
		lvl.createPlanet(Parsor.parserXML("stage1.xml","planet"));
		lvl.createBomb(Parsor.parserXML("stage1.xml","bomb"), Service.TYPEBONUS.BOMB);
		lvl.createBomb(Parsor.parserXML("stage1.xml","mega"), Service.TYPEBONUS.MEGA);
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


			for(SpaceObject sp : enemi.getService().getListFantacin()){
				PhysicsEngine.croiserBehavior(sp, hero);
				GraphicsEngine.drawFire(context, sp,hero.getService().getBody().getWorldCenter());
				GraphicsEngine.drawSpaceObject(context, sp, hero.getService().getBody().getWorldCenter());
			}
			GraphicsEngine.drawFire(context, enemi,hero.getService().getBody().getWorldCenter());
			GraphicsEngine.drawSpaceObject(context, enemi, hero.getService().getBody().getWorldCenter());

			PhysicsEngine.croiserBehavior(enemi, hero);

			//Gestion de collision
			enemi.getService().destroy();
			for(SpaceObject sp : hero.getService().getListFire()){
				sp.getService().destroy();
			}

			for(SpaceObject sp : enemi.getService().getListFire()){
				sp.getService().destroy();
			}

			for(SpaceObject sp : enemi.getService().getListFantacin()){
				sp.getService().destroy();
			}

			for(SpaceObject sp : lvl.getListBomb()){
				if(sp.getService().getFlagCollision()){
					if(hero.getService().getMunition().getMunitionBomb()==0 && hero.getService().getMunition().getMunitionMega()==0)
						sp.getService().usedBonus(sH);
					if(sp.getService().getType()==TYPEBONUS.BOMB && hero.getService().getMunition().getMunitionBomb() > hero.getService().getMunition().getMunitionMega())
						sp.getService().usedBonus(sH);
					if(sp.getService().getType()==TYPEBONUS.MEGA && hero.getService().getMunition().getMunitionMega() > hero.getService().getMunition().getMunitionBomb())
						sp.getService().usedBonus(sH);
					sp.getService().destroy();
					sp.getService().setCollision(false);
				}

			}
			lvl.refresh();
			lvl.getListBomb();

			KeyListener.listen(hero, context);
			context.render(graphics -> {
				graphics.setColor(Color.WHITE);
				graphics.setFont(new Font("Courrier", Font.BOLD, 20));
				graphics.drawString(String.valueOf(String.format("%1.0f",t.getMilliseconds()/1000)), 750,20);
				//Print the bomb ammo
				graphics.setColor(Color.RED);
				graphics.drawString("Bomb: "+hero.getService().getMunition().getMunitionBomb(),675,50);
				graphics.setColor(Color.YELLOW);
				graphics.drawString("Mega: "+hero.getService().getMunition().getMunitionMega(),675,80);
			});
		}
	}
}
