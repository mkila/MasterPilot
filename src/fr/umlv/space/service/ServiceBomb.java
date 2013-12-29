package fr.umlv.space.service;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import fr.umlv.collision.TypeObject;
import fr.umlv.physics.CategoriesSpaceObject;

public class ServiceBomb implements Service{

	private Body bombBody; 
	private TYPEBONUS type;
	private boolean used;

	public ServiceBomb(World world,Vec2 position,TYPEBONUS type) {
		bombBody = createBodyDef(world,position,type);
	}

	private Body createBodyDef(World world,Vec2 position,TYPEBONUS type){
		this.used = false;
		BodyDef bodyDef = new  BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position = position;
		bodyDef.userData = TypeObject.BOMB;
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
		fixtureDef.filter.categoryBits = CategoriesSpaceObject.BONUS;
		fixtureDef.filter.maskBits = CategoriesSpaceObject.HERO;
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

}
