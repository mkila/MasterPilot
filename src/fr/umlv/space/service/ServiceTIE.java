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

public class ServiceTIE implements Service {

	private Body tieBody;
	private final LinkedList<Fire> listFire;
	private int temporisator;
	private boolean collision;


	public ServiceTIE(World world,Vec2 position) {
		tieBody=createBodyDef(world,position);
		listFire = new LinkedList<Fire>();
		temporisator = 0;
		collision=false;
	}


	public Body createBodyDef(World world,Vec2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.position.set(position.x, position.y);
		def.angle = (float)Math.PI*3/2;



		//Construction d'un triangle
		PolygonShape spaceshipShape = new PolygonShape();
		Vec2[] vertices = new Vec2[4];
		vertices[0] = new Vec2(0,0);
		vertices[1] = new Vec2(0,35);
		vertices[2] = new Vec2(35,0);
		vertices[3] = new Vec2(35,35);
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
		return tieBody;
	}

	public Vec2 getPosition() {
		return this.tieBody.getPosition();
	}

	public Vec2 getLinearVelocity() {
		return this.tieBody.getLinearVelocity();
	}

	@Override
	public void move(Vec2 implultion) {
		tieBody.applyForceToCenter(implultion);
	}

	@Override
	public LinkedList<Fire> getListFire() {
		return listFire;
	}

	@Override
	public void fire(Vec2 positionHero) {
		if(temporisator == 60 && collision==false){
			Fire fire=  new Fire(new ServiceFireEnemi(PhysicsEngine.getWorld(),
					tieBody.getWorldCenter(),positionHero));
			listFire.offerFirst(fire);
			temporisator=0;
		}
		temporisator++;
	}


	@Override
	public void destroy() {
		if(collision){
			PhysicsEngine.getWorld().destroyBody(tieBody);
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
