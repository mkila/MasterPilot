package fr.umlv.space.service;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Service{
	default public void move(int x,int y){
		 throw new NotImplementedException();
	}
	
	default public void getImg(){
		 throw new NotImplementedException();
	}
	
	default public void getType(){
		 throw new NotImplementedException();
	}
	
}
