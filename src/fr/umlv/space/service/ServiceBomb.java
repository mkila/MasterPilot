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

public class ServiceBomb implements Service{
	
	/**
	 * This class is used for the bomb implementation in game.
	 **/

	private Body bombBody; 
	private TYPEBONUS type;
	private boolean used;
	private boolean collision;

	/**
	 * The constructor take a world, the position of the bomb in the world and his type
	 * @param World world,Vec2 position,TYPEBONUS type
	 **/
	public ServiceBomb(World world,Vec2 position,TYPEBONUS type) {
		bombBody = createBodyDef(world,position,type);
	}

	/**
	 * Create the body and fixture of the bomb
	 * @param World world,Vec2 position,TYPEBONUS type
	 **/
	private Body createBodyDef(World world,Vec2 position,TYPEBONUS type){
		this.used = false;
		BodyDef bodyDef = new  BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position = position;
		bodyDef.userData = this;
		Body bomb = world.createBody(bodyDef);
		PolygonShape polygon = new PolygonShape();
		Vec2[] vertices = new Vec2[4];
		vertices[0] = new Vec2(15, 15);
		vertices[1] = new Vec2(15,0);
		vertices[2] = new Vec2(15, 15);
		vertices[3] = new Vec2(0, 15);
		polygon.set(vertices, vertices.length);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.shape = polygon;
		fixtureDef.userData = this;
		fixtureDef.filter.categoryBits = CategoriesSpaceObject.ENEMIS;
		fixtureDef.filter.maskBits = CategoriesSpaceObject.PLANET | CategoriesSpaceObject.HERO;
		bomb.createFixture(fixtureDef);
		bomb.setLinearVelocity(new Vec2(0,2));
		bomb.setLinearDamping(0);
		this.type=type;

		return bomb;		
	}

	@Override
	public Body getBody() {
		return bombBody;
	}

	@Override
	public boolean getUsed() {
		return this.used;
	}

	@Override
	public TYPEBONUS getType(){
		return this.type;
	}

	@Override
	public void usedBonus(ServiceHero hero) {
		if(this.type == TYPEBONUS.BOMB)
			hero.getMunition().setMunitionBomb(hero.getMunition().getMunitionBomb() + 1);
		if(this.type == TYPEBONUS.MEGA)
			hero.getMunition().setMunitionMega(hero.getMunition().getMunitionMega() + 1);
		this.used = true;
	}

	@Override
	public boolean getFlagCollision() {
		return collision;
	}

	@Override
	public void collision() {
		collision=true;
	}
	@Override
	public void setCollision(boolean c){
		collision=c;
	}

	@Override
	public void destroy() {
		if(collision){
			PhysicsEngine.getWorld().destroyBody(bombBody);
		}	
	}
}
