package fr.umlv.main;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import fr.umlv.space.service.Service.TYPEBONUS;
import fr.umlv.space.service.ServiceHero;
import fr.umlv.zen3.ApplicationContext;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;

public class Game implements GameManager{

	/**
	 * This class load all you need for your level
	 *
	 **/

	private final Level lvl;
	ArrayList<String> stageName;

	/**
	 * The constructor take a level,
	 * @param Level, lvl load the level you have selected
	 **/

	public Game(ArrayList<String> stageName){
		lvl = new Level();
		this.stageName = stageName;
	}

	@Override
	public void printPlay(ApplicationContext context) throws JDOMException, IOException {

		float timeStep = 1.0f/60.0f; //the length of time passed to simulate (seconds)
		int velocityIterations = 2; //how strongly to correct velocity
		int positionIterations = 2;
		Timer t = new Timer();
		float start = t.getMilliseconds()/1000;
		float end;
		ServiceHero sH = new ServiceHero(PhysicsEngine.getWorld());
		SpaceObject hero = new SpaceShip(sH);

		// Perform stage
		for(int i=0;i<stageName.size();i++){
			File f = new File(stageName.get(i));
			lvl.createPlanet(Parsor.parserXML(f.toString(),"planet"));
			lvl.createBomb(Parsor.parserXML(f.toString(),"bomb"), Service.TYPEBONUS.BOMB);
			lvl.createBomb(Parsor.parserXML(f.toString(),"mega"), Service.TYPEBONUS.MEGA);
			int wave = Parsor.parserWave(f.toString());
			// Perform wave
			for(int j=0;j<wave;j++){
				StringBuilder s = new StringBuilder("ennemi");
				s.append(j+1);
				lvl.createEnnemy(Parsor.parserXML(f.toString(),s.toString()));
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
					for(int i1=0;i1<lvl.getListPlanet().size();i1++){
						GraphicsEngine.drawSpaceObject(context,lvl.getListPlanet().get(i1),hero.getService().getBody().getWorldCenter());        
					}

					// Print the bomb of the world
					for(int i1=0;i1<lvl.getListBomb().size();i1++){
						GraphicsEngine.drawBomb(context,lvl.getListBomb().get(i1),hero.getService().getBody().getWorldCenter());        
					}

					for(int i1=0;i1<lvl.getlistTIE().size();i1++){
						GraphicsEngine.drawSpaceObject(context, lvl.getlistTIE().get(i1), hero.getService().getBody().getWorldCenter());        
						GraphicsEngine.drawFire(context, lvl.getlistTIE().get(i1),hero.getService().getBody().getWorldCenter());
						lvl.getlistTIE().get(i1).getService().fire(hero.getService().getBody().getPosition());
						PhysicsEngine.tieBehavior(lvl.getlistTIE().get(i1), hero.getService().getBody().getWorldCenter());
					}
					
					for(int i1=0;i1<lvl.getListCroiser().size();i1++){
						GraphicsEngine.drawSpaceObject(context, lvl.getListCroiser().get(i1), hero.getService().getBody().getWorldCenter());        
						GraphicsEngine.drawFire(context, lvl.getListCroiser().get(i1),hero.getService().getBody().getWorldCenter());
						lvl.getListCroiser().get(i1).getService().fire(hero.getService().getBody().getPosition());
						PhysicsEngine.croiserBehavior(lvl.getListCroiser().get(i1), hero);
					}
					
					for(int i1=0;i1<lvl.getlistArmada().size();i1++){
						GraphicsEngine.drawSpaceObject(context, lvl.getlistArmada().get(i1), hero.getService().getBody().getWorldCenter());        
						GraphicsEngine.drawFire(context, lvl.getlistArmada().get(i1),hero.getService().getBody().getWorldCenter());
						lvl.getlistArmada().get(i1).getService().fire(hero.getService().getBody().getPosition());
						PhysicsEngine.croiserBehavior(lvl.getlistArmada().get(i1), hero);
						for(SpaceObject sp : lvl.getlistArmada().get(i1).getService().getListFantacin()){
							PhysicsEngine.croiserBehavior(sp, hero);
							GraphicsEngine.drawFire(context, sp,hero.getService().getBody().getWorldCenter());
							GraphicsEngine.drawSpaceObject(context, sp, hero.getService().getBody().getWorldCenter());
						}
					}
					
					//Print fire shoot
					GraphicsEngine.drawFire(context, hero,hero.getService().getBody().getWorldCenter());
					
					//Gestion de collision

//					for(SpaceObject sp : lvl.getListEnnemy().get(i1).getService().getListFire()){
//						sp.getService().destroy();
//					} 
//					for(SpaceObject sp : hero.getService().getListFire()){
//						sp.getService().destroy();
//					}

					// Manage the way of getting bomb
					for(SpaceObject sp : lvl.getListBomb()){
						if(sp.getService().getFlagCollision()){
							// No get type of bomb yet
							if(hero.getService().getMunition().getMunitionBomb()==0 && hero.getService().getMunition().getMunitionMega()==0)
								sp.getService().usedBonus(sH);
							// Only BOMB
							if(sp.getService().getType()==TYPEBONUS.BOMB &&
									hero.getService().getMunition().getMunitionBomb() > hero.getService().getMunition().getMunitionMega()
									&& hero.getService().getMunition().getMunitionBomb() != 1)
								sp.getService().usedBonus(sH);
							// Only MEGA
							if(sp.getService().getType()==TYPEBONUS.MEGA &&
									hero.getService().getMunition().getMunitionMega() > hero.getService().getMunition().getMunitionBomb()
									&& hero.getService().getMunition().getMunitionMega() != 1)
								sp.getService().usedBonus(sH);
							sp.getService().destroy();
							sp.getService().setCollision(false);
						}

					}
					lvl.refresh();
					lvl.getListBomb();
					int lv=i;
					int w=j;
					KeyListener.listen(hero, context);
					context.render(graphics -> {
						graphics.setColor(Color.BLUE);
						graphics.setFont(new Font("Courrier", Font.BOLD, 20));
						graphics.drawString("Timer: "+String.valueOf(String.format("%1.0f",t.getMilliseconds()/1000)), 675,20);
						//Print the bomb ammo
						graphics.setColor(Color.RED);
						graphics.drawString("Bomb: "+hero.getService().getMunition().getMunitionBomb(),675,40);
						graphics.setColor(Color.YELLOW);
						graphics.drawString("Mega: "+hero.getService().getMunition().getMunitionMega(),675,60);
						graphics.setColor(Color.GRAY);
						graphics.drawString("Stage "+(lv+1),10,20);
						graphics.drawString("Wave "+(w+1),10,40);
						graphics.drawString("Ennemies left: "+(lvl.getlistTIE().size()+lvl.getListCroiser().size()+
								lvl.getlistArmada().size()),10,60);
					});

					// Finish the stage
					if(lvl.getlistTIE().size()+lvl.getListCroiser().size()+lvl.getlistArmada().size() == 0
							&& stageName.size()==i+1 && wave==j+1){
						end = t.getMilliseconds()/1000;
						float tot = end-start;
						context.render(graphics -> {
							graphics.clearRect(0, 0, WIDTH, HEIGHT);
							graphics.setColor(Color.RED);
							graphics.setFont(new Font("Courrier", Font.BOLD, 30));
							graphics.drawString("Stage CLEAR in "+String.format("%1.0f",tot)+" seconds", 30,200);
							graphics.drawString("(Press E to exit)", 30,250);

						});
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}

						KeyboardEvent event = context.waitKeyboard();
						if(event.getKey() == KeyboardKey.E){
							System.exit(0);
						}
						break;
					}
					// Finish the wave
					if(lvl.getlistTIE().size()+lvl.getListCroiser().size()+lvl.getlistArmada().size()==0){
						break;
					}
				}
			}
		}
	}
}