package fr.umlv.space.service;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class ServiceHero implements Service{
	
	private final Body heroBody;
	
	public ServiceHero(World world) {
		heroBody=createBodyDef(world);
	}
	
	@Override
	public Body createBodyDef(World world) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
			//TODO Centrer le vaisseaux du hero au centre
		def.position.set(400, 300);
	
	//Construction d'un triangle
		PolygonShape spaceshipShape = new PolygonShape();
		Vec2[] vertices = new Vec2[3];
		vertices[0] = new Vec2(0, -1);
		vertices[1] = new Vec2(1, 1);
		vertices[2] = new Vec2(-1, 1);
		spaceshipShape.set(vertices, vertices.length);
		
	//Creation du body
		Body heroSpace = world.createBody(def);
		
	//Creation de la fixtureDef
		FixtureDef fixture =new FixtureDef();
		fixture.density= 0.1f;
		fixture.shape =spaceshipShape;
		heroSpace.createFixture(fixture);
		return heroSpace;
	}
	
	@Override
	public Body getBody() {
		return heroBody;
	}
	
	@Override
	public void move(Vec2 x, Vec2 y) {
		heroBody.applyLinearImpulse(x, y);
	}
	
	

}
