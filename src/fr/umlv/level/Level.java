package fr.umlv.level;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.common.Vec2;

import fr.umlv.main.Random;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.Bomb;
import fr.umlv.space.object.Planet;
import fr.umlv.space.service.Service;
import fr.umlv.space.service.ServiceBomb;
import fr.umlv.space.service.ServicePlanet;

public class Level {

	private ArrayList<Planet> listPlanet;
	private static ArrayList<Bomb> listBonus;
	private HashMap<Integer, Integer> coordinate;


	public Level(){
		listPlanet = new ArrayList<>();
		listBonus = new ArrayList<Bomb>();
		coordinate = new HashMap<>();
	}

	public ArrayList<Planet> getListPlanet() {
		return listPlanet;
	}

	public ArrayList<Bomb> getListBomb() {
		return listBonus;
	}

	public void createPlanet(int density){
		int radius;
		int x,y;
		for(int i=0;i<density;i++){
			radius = Random.GetIntRandom(20, 100);
			x = Random.GetIntRandom(-7000, 7000);
			y = Random.GetIntRandom(-7000, 7000);
			if(coordinate.containsKey(x) && coordinate.containsValue(y)){
				if(x>0 && y>0){
					listPlanet.add(new Planet(new ServicePlanet(PhysicsEngine.getWorld(), radius, new Vec2(x+200, y+200))));
					coordinate.put(x+200, y+200);
				}
				if(x<0 && y<0){
					listPlanet.add(new Planet(new ServicePlanet(PhysicsEngine.getWorld(), radius, new Vec2(x-200, y-200))));
					coordinate.put(x-200, y-200);
				}
				if(x>0 && y<0){
					listPlanet.add(new Planet(new ServicePlanet(PhysicsEngine.getWorld(), radius, new Vec2(x+200, y-200))));
					coordinate.put(x+200, y-200);	
				}
				else{
					listPlanet.add(new Planet(new ServicePlanet(PhysicsEngine.getWorld(), radius, new Vec2(x-200, y+200))));
					coordinate.put(x-200, y+200);
				}
			}
			else{
				listPlanet.add(new Planet(new ServicePlanet(PhysicsEngine.getWorld(), radius, new Vec2(x, y))));
				coordinate.put(x, y);
			}
		}
	}

	public void createBomb(int density,Service.TYPEBONUS type) {
		int x,y;
		for(int i=0;i<density;i++){
			if(type == Service.TYPEBONUS.BOMB){
				x = Random.GetIntRandom(0, 400);
				y = Random.GetIntRandom(0, 400);
				listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x, y),type)));
			}
			else{
				x = Random.GetIntRandom(400, 800);
				y = Random.GetIntRandom(400, 800);
				listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x, y),type)));
			}
		}
	}

	public static void clearBonus() {
		listBonus.clear();
	}

	public static void refresh() {
		for (int i = 0; i < listBonus.size(); i++) {
			if(listBonus.get(i).getService().getUsed())
				listBonus.remove(i);
		}
	}
}
