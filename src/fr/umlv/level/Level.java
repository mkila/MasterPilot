package fr.umlv.level;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.common.Vec2;

import fr.umlv.main.Random;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.Bomb;
import fr.umlv.space.object.Planet;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.service.Service;
import fr.umlv.space.service.ServiceBomb;
import fr.umlv.space.service.ServicePlanet;
import fr.umlv.space.service.ServiceTIE;

public class Level {

	/**
	 * The structure of a level
	 **/
	private ArrayList<Planet> listPlanet;
	private ArrayList<SpaceShip> listEnnemy;
	private static ArrayList<Bomb> listBonus;
	private HashMap<Integer, Integer> coordinate;

	/**
	 * The constructor initialize his attributes
	 * 
	 **/
	public Level(){
		listPlanet = new ArrayList<>();
		listEnnemy = new ArrayList<>();
		listBonus = new ArrayList<Bomb>();
		coordinate = new HashMap<>();
	}
	
	/**
	 * Get the list of the planet in the level
	 * @return an arraylist of planet
	 **/
	public ArrayList<SpaceShip> getListEnnemy() {
		return listEnnemy;
	}
	
	/**
	 * Get the list of the planet in the level
	 * @return an arraylist of planet
	 **/
	public ArrayList<Planet> getListPlanet() {
		return listPlanet;
	}
	
	/**
	 * Get the list of the bomb in the level
	 * @return an arraylist of bomb
	 **/
	public ArrayList<Bomb> getListBomb() {
		return listBonus;
	}

	/**
	 * Create the planete of the world with it density
	 * @param the density of planet in the world
	 **/
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
	
	public void createEnnemy(int density){
		int x,y;
		for(int i=0;i<density;i++){
			x = Random.GetIntRandom(0, 800);
			y = Random.GetIntRandom(0, 800);
				listEnnemy.add(new SpaceShip(new ServiceTIE(PhysicsEngine.getWorld(), new Vec2(x, y))));		
		}
	}
	
	/**
	 * Create the bomb of the world with it density
	 * @param the density of bomb in the world
	 **/
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
	
	/**
	 * Clear the bonus when change level
	 **/
	public static void clearBonus() {
		listBonus.clear();
	}
	
	/**
	 * Remove bonus when used
	 **/
	public void refresh() {
		for (int i = 0; i < listBonus.size(); i++) {
			if(listBonus.get(i).getService().getUsed())
				listBonus.remove(i);
		}
		for (int i = 0; i < listEnnemy.size(); i++) {
			if(listEnnemy.get(0).getService().getFlagCollision())
				listEnnemy.remove(i);
		}
	}
}
