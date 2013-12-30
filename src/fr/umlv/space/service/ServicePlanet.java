package fr.umlv.space.service;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.umlv.physics.CategoriesSpaceObject;

public class ServicePlanet implements Service{
	
	/**
	 * Create the planet
	 */

	private final Body planetBody;

	/**
	 * Create the planet in the world at a position
	 * @param world, the world to put the ship
	 * @param radius, the radius of the planet
	 * @param position, it position
	 */
	public ServicePlanet(World world,float radius,Vec2 position) {
		planetBody=createBodyDef(world,radius,position);
	}

	/**
	 * Create the planet in the world at a position
	 * @param world, the world to put the ship
	 * @param radius, the radius of the planet
	 * @param position, it position
	 * @return the body of the planet
	 */
	private Body createBodyDef(World world,float radius,Vec2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.STATIC;
		def.position.set(position.x, position.y);

		// Create shape
		CircleShape planetShape = new CircleShape();
		planetShape.setRadius(radius);

		// Create body
		Body planetSpace = world.createBody(def);

		// Create fixture
		FixtureDef fixture =new FixtureDef();
		fixture.density= 0.1f;
		fixture.shape =planetShape;
		fixture.userData=this;
		fixture.filter.categoryBits=CategoriesSpaceObject.PLANET;
		fixture.filter.maskBits = CategoriesSpaceObject.HERO | CategoriesSpaceObject.ENEMIS;
		planetSpace.createFixture(fixture);
		return planetSpace;
	}

	@Override
	public Body getBody() {
		return planetBody;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void collision(){
	}

	@Override
	public boolean getFlagCollision() {
		return false;
	}
}