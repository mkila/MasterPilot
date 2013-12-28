package fr.umlv.space.service;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.umlv.physics.CategoriesSpaceObject;

public class ServiceFire implements Service{

	private final Body missileBody;

	public ServiceFire(World world,float angle, Vec2 shipPosition) {
		missileBody=createBodyDef(world,angle,shipPosition);
		Vec2 tmp = new Vec2(-(float)Math.sin(angle)*1000,
				(float)Math.cos(angle)*1000);
		missileBody.applyForceToCenter(tmp.mul(100));
	}


	private Body createBodyDef(World world,float angle, Vec2 shipPosition) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.position.set(shipPosition.x, shipPosition.y);
		def.angle = angle;



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
		fixture.filter.categoryBits=CategoriesSpaceObject.HERO;
		fixture.filter.maskBits = CategoriesSpaceObject.PLANET |CategoriesSpaceObject.ENEMIS;
		missileSpace.createFixture(fixture);
		return missileSpace;

	}


	@Override
	public Body getBody() {
		return missileBody;
	}
}
