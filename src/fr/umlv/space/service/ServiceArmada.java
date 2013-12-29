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
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.object.SpaceShip;

public class ServiceArmada implements Service {

	private final Body armadaBody;
	private final LinkedList<Fire> listFire;
	private final LinkedList<SpaceShip> listFantacin;
	private int temporisator;
	private boolean collision;

	public ServiceArmada(World world, Vec2 position) {
		armadaBody = createBodyDef(world, position);
		armadaBody.setAngularDamping(4f);
		listFantacin = new LinkedList<>();
		for (int i = 0; i < 7; i++) {
			listFantacin.add(new SpaceShip(new ServiceFantacin(PhysicsEngine
					.getWorld(), position.add(new Vec2(0, (i+1)*30)))));
		}
		listFire = new LinkedList<>();
		temporisator = 0;
		collision=false;
	}

	public Body createBodyDef(World world, Vec2 position) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DYNAMIC;
		def.position.set(position.x, position.y);

		// Construction d'un triangle
		PolygonShape spaceshipShape = new PolygonShape();
		Vec2[] vertices = new Vec2[4];
		vertices[0] = new Vec2(0, 0);
		vertices[1] = new Vec2(0, 20);
		vertices[2] = new Vec2(20, 0);
		vertices[3] = new Vec2(20, 20);
		spaceshipShape.set(vertices, vertices.length);

		// Creation du body
		Body armadaSpace = world.createBody(def);
		// Creation de la fixtureDef
		FixtureDef fixture = new FixtureDef();
		fixture.density = 10f;
		fixture.friction = 10f;
		fixture.restitution = 0.5f;
		fixture.userData = this;
		fixture.shape = spaceshipShape;
		fixture.filter.categoryBits = CategoriesSpaceObject.ENEMIS;
		fixture.filter.maskBits = CategoriesSpaceObject.PLANET
				| CategoriesSpaceObject.HERO;
		armadaSpace.createFixture(fixture);
		armadaSpace.setLinearDamping(0.05f);
		armadaSpace.setAngularDamping(0.5f);
		return armadaSpace;
	}

	@Override
	public Body getBody() {
		return armadaBody;
	}

	public Vec2 getPosition() {
		return this.armadaBody.getWorldCenter();
	}

	public Vec2 getLinearVelocity() {
		return this.armadaBody.getLinearVelocity();
	}

	@Override
	public void move(Vec2 implultion) {
		armadaBody.applyForceToCenter(implultion);
	}
	
	@Override
	public LinkedList<Fire> getListFire() {
		return listFire;
	}
	
	@Override
	public  LinkedList<SpaceShip> getListFantacin() {
		return listFantacin;
	}

	@Override
	public void fire(Vec2 positionHero) {
		if (temporisator == 60 && collision==false) {
			for (SpaceShip spaceShip : listFantacin) {
				spaceShip.getService().fire(positionHero);
			}
			Fire fire = new Fire(new ServiceFireEnemi(PhysicsEngine.getWorld(),
					armadaBody.getWorldCenter(), positionHero));
			listFire.offerFirst(fire);
			temporisator = 0;
		}
		temporisator++;
	}

	@Override
	public void destroy() {
		if(collision){
			PhysicsEngine.getWorld().destroyBody(armadaBody);
			for (SpaceObject sp : listFantacin) {
				sp.getService().collision();
				sp.getService().destroy();
			}
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