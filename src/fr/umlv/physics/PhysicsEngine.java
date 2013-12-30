package fr.umlv.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.service.Service;

public class PhysicsEngine {
	private static final World world = new World(new Vec2(0, 0));
	
	public static World getWorld() {
		ContactListener contactListener = new ContactListener() {
			
			@Override
			public void preSolve(Contact arg0, Manifold arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postSolve(Contact arg0, ContactImpulse arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void endContact(Contact arg0) {
				
			}
			
			@Override
			public void beginContact(Contact arg0) {
				((Service)arg0.getFixtureA().getUserData()).collision();
				((Service)arg0.getFixtureB().getUserData()).collision();
			}
		};
		world.setContactListener(contactListener);
		return world;
	}



	public static void tieBehavior(SpaceObject tie,Vec2 positionHero){
		if(!tie.getService().getBody().isActive())
			return;   
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
	
	public static void croiserBehavior(SpaceObject croiser, SpaceObject hero) {
		if(!croiser.getService().getBody().isActive())
			return;
		PolygonShape poly =	(PolygonShape)	croiser.getService().getBody().getFixtureList().getShape();
			float distance1 = distance(croiser.getService().getBody().getWorldPoint(poly.getVertex(0)),hero.getService().getBody().getWorldCenter());
			float distance2 = distance(croiser.getService().getBody().getWorldPoint(poly.getVertex(1)),hero.getService().getBody().getWorldCenter());
			
			if(distance1<distance2){
				float time = 1; // one second
				float torque = croiser.getService().getBody().getInertia() * (1 - hero.getService().getBody().getAngularVelocity() ) / time;
				croiser.getService().getBody().applyTorque(torque);
			}
			
			if(distance1>distance2){
				float time = 1; // one second
				float torque = croiser.getService().getBody().getInertia() * (-1 - hero.getService().getBody().getAngularVelocity() ) / time;
				croiser.getService().getBody().applyTorque(torque);
			}


			Vec2 tmp2 = new Vec2(hero.getService().getBody().getWorldCenter().x
				- croiser.getService().getBody().getWorldCenter().x, hero
				.getService().getBody().getWorldCenter().y
				- croiser.getService().getBody().getWorldCenter().y);
		croiser.getService().getBody().applyForceToCenter(tmp2.mul(300));

	}
	public static void croiserFantacin(SpaceObject croiser, SpaceObject hero) {
		if(!croiser.getService().getBody().isActive())
			return;
		PolygonShape poly =	(PolygonShape)	croiser.getService().getBody().getFixtureList().getShape();
			float distance1 = distance(croiser.getService().getBody().getWorldPoint(poly.getVertex(0)),hero.getService().getBody().getWorldCenter());
			float distance2 = distance(croiser.getService().getBody().getWorldPoint(poly.getVertex(2)),hero.getService().getBody().getWorldCenter());
			
			if(distance1<distance2){
				float time = 1; // one second
				float torque = croiser.getService().getBody().getInertia() * (1 - hero.getService().getBody().getAngularVelocity() ) / time;
				croiser.getService().getBody().applyTorque(torque);
			}
			
			if(distance1>distance2){
				float time = 1; // one second
				float torque = hero.getService().getBody().getInertia() * (-1 - hero.getService().getBody().getAngularVelocity() ) / time;
				croiser.getService().getBody().applyTorque(torque);
			}
		Vec2 tmp2 = new Vec2(hero.getService().getBody().getWorldCenter().x
				- croiser.getService().getBody().getWorldCenter().x, hero
				.getService().getBody().getWorldCenter().y
				- croiser.getService().getBody().getWorldCenter().y);
		croiser.getService().getBody().applyForceToCenter(tmp2.mul(300));

	}

	private static float distance(Vec2 a, Vec2 b) {
		return ((b.x-a.x)*(b.x-a.x))+((b.y-a.y)*(b.y-a.y));
	}
}