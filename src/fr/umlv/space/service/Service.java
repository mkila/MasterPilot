package fr.umlv.space.service;

import java.util.LinkedList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.space.object.Fire;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Service{
	default public void move(Vec2 impultion){
		 throw new NotImplementedException();
	}
	
	default public LinkedList<Fire> getListFire(){
		 throw new NotImplementedException();
	}
	
	default public void fire(Vec2 positionHero){
		 throw new NotImplementedException();
	}
	
	public Body getBody();
	
	
}
