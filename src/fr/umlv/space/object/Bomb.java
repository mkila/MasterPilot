package fr.umlv.space.object;

import fr.umlv.space.service.Service;

public class Bomb implements SpaceObject {

	/**
	 * The bomb service
	 */

	private final Service service;

	public Bomb(Service service) {
		this.service = service;
	}
	@Override
	public Service getService() {
		return service;
	}

}
