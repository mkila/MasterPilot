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

private final Body planetBody;
	
	public ServicePlanet(World world,float radius,Vec2 position) {
		planetBody=createBodyDef(world,radius,position);
	}
	
	
	public Body createBodyDef(World world,float radius,Vec2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.STATIC;
		def.position.set(position.x, position.y);
	
	//Construction d'un cercle
		CircleShape planetShape = new CircleShape();
		planetShape.setRadius(radius);
		
	//Creation du body
		Body planetSpace = world.createBody(def);
		
	//Creation de la fixtureDef
		FixtureDef fixture =new FixtureDef();
		fixture.density= 0.1f;
		fixture.shape =planetShape;
		fixture.userData=this;
		fixture.filter.categoryBits=CategoriesSpaceObject.PLANET;
		fixture.filter.maskBits = CategoriesSpaceObject.HERO;
		planetSpace.createFixture(fixture);
		return planetSpace;
	}
	
	@Override
	public Body getBody() {
		return planetBody;
	}
	
	public Vec2 getPosition() {
		return this.planetBody.getPosition();
	}

}
