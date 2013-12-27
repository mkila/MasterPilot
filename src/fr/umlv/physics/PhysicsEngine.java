package fr.umlv.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import fr.umlv.space.object.SpaceObject;

public class PhysicsEngine {
	private static final World world = new World(new Vec2(0, 0));

	public static World getWorld(){
		return world;
	}

	public static void tieBehavior(SpaceObject tie,Vec2 positionHero){
		Vec2 tmp = new Vec2((-(float)Math.sin(tie.getService().getBody().getAngle()))*1000,
				((float)Math.cos(tie.getService().getBody().getAngle()))*1000);
		tie.getService().getBody().applyForceToCenter(tmp.mul(2000));
		
		float time = 10;
		float torque = tie.getService().getBody().getInertia() * (1 - tie.getService().getBody().getAngularVelocity() ) / time;
		tie.getService().getBody().applyTorque(torque);
		
		Vec2 tmp2 = new Vec2(positionHero.x-tie.getService().getBody().getWorldCenter().x,
				positionHero.y-tie.getService().getBody().getWorldCenter().y);
		tie.getService().getBody().applyForceToCenter(tmp2.mul(1000));

	}

}
