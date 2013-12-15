package fr.umlv.space.object;

import fr.umlv.space.service.Service;

public class Planet implements SpaceObject {

private final Service service;
	
	public Planet(Service service) {
		this.service = service;
	}
	
	@Override
	public Service getService() {
		return service;
	}
}
