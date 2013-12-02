package fr.umlv.space.object;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import fr.umlv.space.service.Service;


public interface SpaceObject {
	 public int getX();
	 public int getY();
	 default public Service getService(){
			 throw new NotImplementedException();
	 }
}
