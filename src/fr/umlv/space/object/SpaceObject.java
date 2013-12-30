package fr.umlv.space.object;

import fr.umlv.space.service.Service;

public interface SpaceObject {
	 
	/***
	 * Allow to find the proper service to use
	 */
	
	/**
	 * Get a service
	 * @return a object service
	 */ 
	 public Service getService();
}
