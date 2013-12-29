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

	private Body missileBody;
	private boolean collision;
	

	public ServiceFireEnemi(World world, Vec2 shipPosition,Vec2 positionHero) {
		missileBody=createBodyDef(world,(float)Math.tanh((positionHero.y-shipPosition.y)/(positionHero.x-shipPosition.x)),shipPosition, positionHero);
		Vec2 tmp = new Vec2(positionHero.x-shipPosition.x,
				positionHero.y-shipPosition.y);
		missileBody.applyForceToCenter(tmp.mul(1000));
		collision =false;
	}


	public Body createBodyDef(World world,float angle, Vec2 shipPosition,Vec2 positionHero) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.position.set(shipPosition.x, shipPosition.y);
		def.angle=angle + (float)Math.PI/2;



		//Construction d'un triangle
		PolygonShape spaceshipShape = new PolygonShape();
		Vec2[] vertices = new Vec2[4];
		vertices[0] = new Vec2(0,0);
		vertices[1] = new Vec2(0,10);
		vertices[2] = new Vec2(1,0);
		vertices[3] = new Vec2(1,10);
		spaceshipShape.set(vertices, vertices.length);


		//Creation du body
		Body missileSpace = world.createBody(def);

		//Creation de la fixtureDef
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
		if(collision)
			PhysicsEngine.getWorld().destroyBody(missileBody);
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
