package fr.umlv.space.service;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.umlv.physics.CategoriesSpaceObject;


public class ServiceHero implements Service{
	
	private final Body heroBody;
	
	public ServiceHero(World world) {
		heroBody=createBodyDef(world);
	}
	
	
	public Body createBodyDef(World world) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.position.set(400, 300);
		def.angle = (float)Math.PI;

	
	
	//Construction d'un triangle
		PolygonShape spaceshipShape = new PolygonShape();
		Vec2[] vertices = new Vec2[3];
		vertices[0] = new Vec2(0, 0);
		vertices[1] = new Vec2(15,30);
		vertices[2] = new Vec2(30, 0);
		spaceshipShape.set(vertices, vertices.length);
		
		
	//Creation du body
		Body heroSpace = world.createBody(def);
	//Creation de la fixtureDef
		FixtureDef fixture =new FixtureDef();
		fixture.density= 10f;
		fixture.friction= 10f;
		fixture.restitution=0.5f;
		fixture.userData = this;
		fixture.shape =spaceshipShape;
		fixture.filter.categoryBits=CategoriesSpaceObject.HERO;
		fixture.filter.maskBits = CategoriesSpaceObject.PLANET |CategoriesSpaceObject.ENEMIS;
		heroSpace.createFixture(fixture);
		heroSpace.setLinearDamping(0.05f);
		heroSpace.setAngularDamping(0.5f);
		return heroSpace;
	}
	
	@Override
	public Body getBody() {
		return heroBody;
	}
	
	public Vec2 getPosition() {
		return this.heroBody.getPosition();
	}
	
	public Vec2 getLinearVelocity() {
		return this.heroBody.getLinearVelocity();
	}
	
	@Override
	public void move(Vec2 implultion) {
		heroBody.applyForceToCenter(implultion);
	}
	
}
