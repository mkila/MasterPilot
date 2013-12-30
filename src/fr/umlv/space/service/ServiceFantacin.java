package fr.umlv.space.service;

import java.util.LinkedList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import fr.umlv.physics.CategoriesSpaceObject;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.space.object.Fire;


public class ServiceFantacin implements Service  {

	private final Body fantacinBody;
	private final LinkedList<Fire> listFire;
	private boolean collision;
	


	public ServiceFantacin(World world,Vec2 position) {
		fantacinBody=createBodyDef(world,position);
		fantacinBody.setAngularDamping(4f);
		listFire = new LinkedList<Fire>();
		collision=false;
	}


	public Body createBodyDef(World world,Vec2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.position.set(position.x, position.y);



		//Construction d'un triangle
		PolygonShape spaceshipShape = new PolygonShape();
		Vec2[] vertices = new Vec2[3];
		vertices[0] = new Vec2(0,0);
		vertices[1] = new Vec2(5,8.7f);
		vertices[2] = new Vec2(10,0);
		spaceshipShape.set(vertices, vertices.length);


		//Creation du body
		Body tieSpace = world.createBody(def);
		//Creation de la fixtureDef
		FixtureDef fixture =new FixtureDef();
		fixture.density= 10f;
		fixture.friction= 10f;
		fixture.restitution=0.5f;
		fixture.userData = this;
		fixture.shape =spaceshipShape;
		fixture.filter.categoryBits=CategoriesSpaceObject.ENEMIS;
		fixture.filter.maskBits = CategoriesSpaceObject.PLANET |CategoriesSpaceObject.HERO;
		tieSpace.createFixture(fixture);
		tieSpace.setLinearDamping(0.05f);
		tieSpace.setAngularDamping(0.5f);
		return tieSpace;
	}

	@Override
	public Body getBody() {
		return fantacinBody;
	}

	public Vec2 getPosition() {
		return this.fantacinBody.getWorldCenter();
	}

	public Vec2 getLinearVelocity() {
		return this.fantacinBody.getLinearVelocity();
	}

	@Override
	public void move(Vec2 implultion) {
		fantacinBody.applyForceToCenter(implultion);
	}

	@Override
	public LinkedList<Fire> getListFire() {
		return listFire;
	}

	@Override
	public void fire(Vec2 positionHero) {
		if(!collision){
		Fire fire=  new Fire(new ServiceFireEnemi(PhysicsEngine.getWorld(),
					fantacinBody.getWorldCenter(),positionHero));
			listFire.offerFirst(fire);
		}
	}


	@Override
	public void destroy() {
		if(collision){
			PhysicsEngine.getWorld().destroyBody(fantacinBody);
			fantacinBody.setActive(false);
		}
	}
	
	
	@Override
	public void collision() {
		collision=true;
	
	}
	
	
	@Override
	public boolean getFlagCollision() {
		return collision;
	}

}

