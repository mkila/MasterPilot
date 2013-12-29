package fr.umlv.space.object;

import fr.umlv.space.service.Service;

public class SpaceShip implements SpaceObject {

	private final Service service;
	
	public SpaceShip(Service service) {
		this.service = service;
	}
	
	@Override
	public Service getService() {
		return service;
	}
	
}
