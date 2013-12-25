package fr.umlv.space.object;

import fr.umlv.space.service.Service;

public class Fire implements SpaceObject {

	private final Service service;
	
	public Fire(Service service) {
		this.service = service;
	}
	@Override
	public Service getService() {
		return service;
	}

}