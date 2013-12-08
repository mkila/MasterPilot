package fr.umlv.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class PhysicsEngine {
	private static final World world = new World(new Vec2(0, 0));
	
	public static World getWorld(){
		return world;
	}
	
}
