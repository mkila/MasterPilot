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
import fr.umlv.space.object.munition.Munition;


public class ServiceHero implements Service{

	private final Body heroBody;
	private final LinkedList<Fire> listFire;
	private Munition munitionBomb;
	private boolean collision;

	public ServiceHero(World world) {
		heroBody=createBodyDef(world);
		listFire = new LinkedList<Fire>();
		munitionBomb = new Munition(0, 15);
		collision=false;
	}

	private Body createBodyDef(World world) {
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
	public Munition getMunition() {
		return this.munitionBomb;
	}

	@Override
	public Body getBody() {
		return heroBody;
	}


	@Override
	public void move(Vec2 implultion) {
		heroBody.applyForceToCenter(implultion);
	}

	@Override
	public LinkedList<Fire> getListFire() {
		return listFire;
	}
	

	@Override
	public void fire(Vec2 positionHero) {
		Fire fire=  new Fire(new ServiceFireHero(PhysicsEngine.getWorld(),
				heroBody.getAngle(), heroBody.getWorldCenter()));
		listFire.offerFirst(fire);
	}
	
	
	@Override
	public void destroy() {
		this.collision();
	}

	@Override
	public void collision() {	
		collision=!collision;
	}

	@Override
	public boolean getFlagCollision() {
		return collision;
	}

	public void explosion(){
		if(munitionBomb.getMunitionBomb()>0){
			bomb(50000.f);
			munitionBomb.setMunitionBomb(munitionBomb.getMunitionBomb()-1);
		}
		if(munitionBomb.getMunitionMega()>0){
			bomb(-50000.f);
			munitionBomb.setMunitionMega(munitionBomb.getMunitionMega()-1);
		}
	}
	
	public void bomb(float factor) {
		LinkedList<Body> bodyList = new LinkedList<>();
		System.out.println("hero"+heroBody.getPosition().toString());
		float rangeRadius = 400; 
		for (Body b = heroBody.getWorld().getBodyList(); b != null; b = b.getNext()) {
			if(b.getType() == BodyType.DYNAMIC && b.getFixtureList() != heroBody.getFixtureList())
				if((heroBody.getPosition().x-rangeRadius<b.getPosition().x) && (b.getPosition().x<heroBody.getPosition().x+rangeRadius)
					&&	(heroBody.getPosition().y-rangeRadius<b.getPosition().y) && (b.getPosition().y<heroBody.getPosition().y+rangeRadius))
				bodyList.add(b);
		}
		for(int i=0; i<bodyList.size();i++){
			double diffX = bodyList.get(i).getPosition().x - heroBody.getPosition().x;
			double diffY = bodyList.get(i).getPosition().y - heroBody.getPosition().y;
			double distance = Math.sqrt(diffX * diffX + diffY * diffY);
			double normalizedX = diffX / distance;
			double normalizedY = diffY / distance;
			Vec2 force = new Vec2((float)normalizedX * factor, (float)normalizedY * factor);
			bodyList.get(i).applyLinearImpulse(force.mul(5000), bodyList.get(i).getWorldCenter());	
		}
	}
}
