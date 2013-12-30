package fr.umlv.space.service;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.umlv.physics.CategoriesSpaceObject;
import fr.umlv.physics.PhysicsEngine;

public class ServiceFireEnemi implements Service{

	/**
	 * Create the fire shoot enemies
	 */

	private Body missileBody;
	private boolean collision;

	/**
	 * Create the fire shoot for the enemies ships
	 * @param world,the world to set the fire
	 * @param shipPosition, the position to fire
	 * @param positionHero, the target position where the fire reach
	 */
	public ServiceFireEnemi(World world, Vec2 shipPosition,Vec2 positionHero) {
		missileBody=createBodyDef(world,(float)Math.tanh((positionHero.y-shipPosition.y)/(positionHero.x-shipPosition.x)),shipPosition, positionHero);
		Vec2 tmp = new Vec2(positionHero.x-shipPosition.x,
				positionHero.y-shipPosition.y);
		missileBody.applyForceToCenter(tmp.mul(1000));
		missileBody.setActive(true);
		collision =false;
	}

	/**
	 * Create the fire shoot for the enemies ships
	 * @param world, the world to set the fire
	 * @param angle, the angle for shooting
	 * @param shipPosition, the position to fire
	 * @param positionHero, the target position where the fire reach
	 */
	private Body createBodyDef(World world,float angle, Vec2 shipPosition,Vec2 positionHero) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.position.set(shipPosition.x, shipPosition.y);
		def.angle=angle + (float)Math.PI/2;

		// Create shape
		PolygonShape spaceshipShape = new PolygonShape();
		Vec2[] vertices = new Vec2[4];
		vertices[0] = new Vec2(0,0);
		vertices[1] = new Vec2(0,10);
		vertices[2] = new Vec2(1,0);
		vertices[3] = new Vec2(1,10);
		spaceshipShape.set(vertices, vertices.length);

		// Create body
		Body missileSpace = world.createBody(def);

		// Create fixture
		FixtureDef fixture =new FixtureDef();
		fixture.density= 1f;
		fixture.friction= 1f;
		fixture.restitution=0.5f;
		fixture.userData = this;
		fixture.shape =spaceshipShape;
		fixture.filter.categoryBits=CategoriesSpaceObject.ENEMIS;
		fixture.filter.maskBits = CategoriesSpaceObject.PLANET |CategoriesSpaceObject.HERO;
		missileSpace.createFixture(fixture);
		return missileSpace;

	}

	@Override
	public Body getBody() {
		return missileBody;
	}

	@Override
	public void destroy() {
		if(collision){
			PhysicsEngine.getWorld().destroyBody(missileBody);
			missileBody.setActive(false);
		}
	}

	@Override
	public void collision() {
		collision = true;
	}

	@Override
	public boolean getFlagCollision() {
		return collision;
	}
}