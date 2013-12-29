package fr.umlv.keylistener;


import org.jbox2d.common.Vec2;

import fr.umlv.space.object.SpaceObject;
import fr.umlv.zen3.ApplicationContext;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;

public class KeyListener {


	public static void listen(SpaceObject hero, ApplicationContext context) {
		context.render(graphics -> {
			KeyboardEvent event = context.pollKeyboard();
			if (event == null) {
				return;
			}
			if(event.getKey() == KeyboardKey.DOWN){
				Vec2 tmp = new Vec2((-(float)Math.sin(hero.getService().getBody().getAngle()))*1000,
						((float)Math.cos(hero.getService().getBody().getAngle()))*1000);
				hero.getService().getBody().applyForceToCenter(tmp.mul(-500));

			}
			if(event.getKey() == KeyboardKey.UP){
				Vec2 tmp = new Vec2((-(float)Math.sin(hero.getService().getBody().getAngle()))*1000,
						((float)Math.cos(hero.getService().getBody().getAngle()))*1000);
				hero.getService().getBody().applyForceToCenter(tmp.mul(500));

			}
			if(event.getKey() == KeyboardKey.RIGHT){
				float time = 1; // one second
				float torque = hero.getService().getBody().getInertia() * (1 - hero.getService().getBody().getAngularVelocity() ) / time;
				hero.getService().getBody().applyTorque(torque);
				System.out.println("angle :"+hero.getService().getBody().getAngle());

			}
			if(event.getKey() == KeyboardKey.LEFT){
				float time = 1; // one second
				float torque = hero.getService().getBody().getInertia() * (-1 - hero.getService().getBody().getAngularVelocity() ) / time;
				hero.getService().getBody().applyTorque(torque);
				System.out.println("angle :"+hero.getService().getBody().getAngle());

			}

			if(event.getKey() == KeyboardKey.SPACE){
				hero.getService().fire(null);
			}
			

		});
	}
}