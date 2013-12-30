package fr.umlv.space.service;

import java.util.LinkedList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.space.object.Fire;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.object.munition.Munition;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Service{
	
	/**
	 * Provide differents services for each object 
	 **/

	enum TYPEBONUS {BOMB,MEGA};

	/**
	 * Move of an object
	 * @param impultion, the direction
	 */
	default public void move(Vec2 impultion){
		throw new NotImplementedException();
	}

	/**
	 * The bullet fire by an object
	 * @return a list of fire in the world
	 */
	default public LinkedList<Fire> getListFire(){
		throw new NotImplementedException();
	}
	
	/**
	 * The list of unit for the Fantacin Ship
	 * @return a list of unit
	 */
	default public LinkedList<SpaceShip> getListFantacin(){
		 throw new NotImplementedException();
	}

	/**
	 * The munition to use
	 * @return a type of Munition, BOMB or MEGA
	 */
	default public Munition getMunition() {
		throw new NotImplementedException();
	}

	/**
	 * Allow to fire
	 * @param positionHero, the position where the bullet start to move
	 */
	default public void fire(Vec2 positionHero){
		throw new NotImplementedException();
	}

	/**
	 * Get the type of bomb, MEGA or BOMB
	 * @return the type
	 */
	default public TYPEBONUS getType(){
		throw new NotImplementedException();
	}

	/**
	 * Increment the number of BOMB or MEGA
	 * @param hero, the object impacted
	 */
	default public void usedBonus(ServiceHero hero){
		throw new NotImplementedException();
	};

	/**
	 * Bomb used or not to remove it of the world
	 * @return true if used, false otherwise
	 */
	default public boolean getUsed() {
		throw new NotImplementedException();
	}
	
	/**
	 * Make an explosion (BOMB or MEGA)
	 */
	default public void explosion(){
		throw new NotImplementedException();
	};

	/**
	 * Get the object body
	 * @return the body concerned
	 */
	public Body getBody();
	
	/**
	 * Get the flag collision to remove it
	 * @return remove if true, else false
	 */
	public boolean getFlagCollision();
	
	/**
	 * Make the collision between bodies
	 */
	public void collision();
	
	/**
	 * Then destroyed them from the world
	 */
	public void destroy();
	
	/**
	 * Reset the flag collision at false.
	 * @param c, false to reset the flag collision
	 */
	default public void setCollision(boolean c){
		throw new NotImplementedException();
	};
}
