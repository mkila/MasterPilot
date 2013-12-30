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
import fr.umlv.space.service.ServiceArmada;
import fr.umlv.space.service.ServiceBomb;
import fr.umlv.space.service.ServiceCroiser;
import fr.umlv.space.service.ServicePlanet;
import fr.umlv.space.service.ServiceTIE;

public class Level {

	/**
	 * The structure of a level
	 **/
	private ArrayList<Planet> listPlanet;
	private ArrayList<SpaceShip> listCroiser;
	private ArrayList<SpaceShip> listTIE;
	private ArrayList<SpaceShip> listArmada;
	private static ArrayList<Bomb> listBonus;
	private HashMap<Integer, Integer> coordinate;

	/**
	 * The constructor initialize his attributes
	 * 
	 **/
	public Level(){
		listPlanet = new ArrayList<>();
		listCroiser = new ArrayList<>();
		listTIE = new ArrayList<>();
		listArmada = new ArrayList<>();
		listBonus = new ArrayList<Bomb>();
		coordinate = new HashMap<>();
	}
	
	/**
	 * Get the list of the Croiser in the level
	 * @return an arraylist of Croiser
	 **/
	public ArrayList<SpaceShip> getListCroiser() {
		return listCroiser;
	}
	
	/**
	 * Get the list of the TIE in the level
	 * @return an arraylist of TIE
	 **/
	public ArrayList<SpaceShip> getlistTIE() {
		return listTIE;
	}
	
	/**
	 * Get the list of the Armada in the level
	 * @return an arraylist of Armada
	 **/
	public ArrayList<SpaceShip> getlistArmada() {
		return listArmada;
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
	 * @param density, the density of planet in the world
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
	
	/**
	 * Create the ennemy of the world with it density
	 * @param density, the density of ennemy in the world
	 **/
	public void createEnnemy(int density){
		int x,y,choice;
		for(int i=0;i<density;i++){
			x = Random.GetIntRandom(0, 800);
			y = Random.GetIntRandom(0, 800);
			choice = Random.GetIntRandom(0, 2);
			switch(choice){
			case 0:
				listTIE.add(new SpaceShip(new ServiceTIE(PhysicsEngine.getWorld(), new Vec2(x, y))));
				break;
			case 1:
				listCroiser.add(new SpaceShip(new ServiceCroiser(PhysicsEngine.getWorld(), new Vec2(x, y))));
				break;
			case 2:
				listArmada.add(new SpaceShip(new ServiceArmada(PhysicsEngine.getWorld(), new Vec2(x, y))));
				break;
			}			
		}
	}

	/**
	 * Create the bomb of the world with it density
	 * @param density, the density of bomb in the world
	 * @param type, the type of bonus to generate
	 */
	public void createBomb(int density,Service.TYPEBONUS type) {
		int x,y;
		for(int i=0;i<density;i++){
			x = Random.GetIntRandom(-200, 800);
			y = Random.GetIntRandom(-200, 800);
//			if(type == Service.TYPEBONUS.BOMB){
//				x = Random.GetIntRandom(0, 400);
//				y = Random.GetIntRandom(0, 400);
//				listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x, y),type)));
//			}
//			else{
//				x = Random.GetIntRandom(400, 800);
//				y = Random.GetIntRandom(400, 800);
//				listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x, y),type)));
//			}
			if(coordinate.containsKey(x) && coordinate.containsValue(y)){
				if(x>0 && y>0){
					listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x+200, y+200),type)));
					coordinate.put(x+200, y+200);
				}
				if(x<0 && y<0){
					listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x-200, y-200),type)));
					coordinate.put(x-200, y-200);
				}
				if(x>0 && y<0){
					listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x+200, y-200),type)));
					coordinate.put(x+200, y-200);	
				}
				else{
					listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x-200, y+200),type)));
					coordinate.put(x-200, y+200);
				}
			}
			else{
				listBonus.add(new Bomb(new ServiceBomb(PhysicsEngine.getWorld(), new Vec2(x, y),type)));
				coordinate.put(x, y);
			}
		}
	}
	
	/**
	 * Remove Objects when get collision
	 **/
	public void refresh() {
		for (int i = 0; i < listBonus.size(); i++) {
			if(listBonus.get(i).getService().getUsed())
				listBonus.remove(i);
		}
		for (int i = 0; i < listCroiser.size(); i++) {
			if(listCroiser.get(0).getService().getFlagCollision())
				listCroiser.remove(i);
		}
		for (int i = 0; i < listTIE.size(); i++) {
			if(listTIE.get(0).getService().getFlagCollision())
				listTIE.remove(i);
		}
		for (int i = 0; i < listArmada.size(); i++) {
			if(listArmada.get(0).getService().getFlagCollision())
				listArmada.remove(i);
		}
	}
}