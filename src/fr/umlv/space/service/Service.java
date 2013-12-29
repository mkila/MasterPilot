package fr.umlv.space.service;

import java.util.LinkedList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.space.object.Fire;
import fr.umlv.space.object.SpaceShip;
import fr.umlv.space.object.munition.Munition;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Service{

	enum TYPEBONUS {BOMB,MEGA};

	default public void move(Vec2 impultion){
		throw new NotImplementedException();
	}

	default public LinkedList<Fire> getListFire(){
		throw new NotImplementedException();
	}
	
	default public LinkedList<SpaceShip> getListFantacin(){
		 throw new NotImplementedException();
	}

	default public Munition getMunition() {
		throw new NotImplementedException();
	}

	default public void fire(Vec2 positionHero){
		throw new NotImplementedException();
	}

	default public TYPEBONUS getType(){
		throw new NotImplementedException();
	}

	default public void usedBonus(ServiceHero hero){
		throw new NotImplementedException();
	};

	default public boolean getUsed() {
		throw new NotImplementedException();
	}
	
	default public void explosion(){
		throw new NotImplementedException();
	};

	public Body getBody();
	public boolean getFlagCollision();
	public void collision();
	public void destroy();

}
