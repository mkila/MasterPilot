package fr.umlv.space.object;

import fr.umlv.space.service.Service;

public class SpaceShip implements SpaceObject {
	private int x;
	private int y;
	private final Service service;
	
	public SpaceShip(Service service) {
		this.service = service;
	}
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	@Override
	public Service getService() {
		return service;
	}
	
}
