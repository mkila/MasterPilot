package fr.umlv.level;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import fr.umlv.main.Random;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.Planet;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.service.ServicePlanet;

public class Level {
	
	private ArrayList<SpaceObject> listPlanet;
	
	
	public Level(){
		listPlanet = new ArrayList<>();
	}

	public ArrayList<SpaceObject> getListPlanet() {
		return listPlanet;
	}
	
	public void createPlanet(int density){
		int radius;
		int x,y;
		for(int i=0;i<density;i++){
			 radius = Random.GetIntRandom(20, 100);
			 x = Random.GetIntRandom(-5000, 5000);
			 y = Random.GetIntRandom(-5000, 5000);
			 listPlanet.add(new Planet(new ServicePlanet(PhysicsEngine.getWorld(), radius, new Vec2(x, y))));
		}
	}
}
