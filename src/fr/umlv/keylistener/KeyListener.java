package fr.umlv.keylistener;


import java.awt.Color;
import java.awt.Font;

import org.jbox2d.common.Vec2;

import fr.umlv.space.object.SpaceObject;
import fr.umlv.zen3.ApplicationContext;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;

public class KeyListener {

	/**
	 * This class drawn hero movements and actions
	 */
	
	/**
	 * Allow to wait and perform an action
	 * @param hero, the SpaceShipHero used to perfrom action
	 * @param context, use for the keyboard event
	 */
	public static void listen(SpaceObject hero, ApplicationContext context) {
		context.render(graphics -> {
			KeyboardEvent event = context.pollKeyboard();
			if (event == null) {
				return;
			}
			if(event.getKey() == KeyboardKey.DOWN){
				Vec2 tmp = new Vec2((-(float)Math.sin(hero.getService().getBody().getAngle()))*5000,
						((float)Math.cos(hero.getService().getBody().getAngle()))*5000);
				hero.getService().getBody().applyForceToCenter(tmp.mul(-500));

			}
			if(event.getKey() == KeyboardKey.UP){
				Vec2 tmp = new Vec2((-(float)Math.sin(hero.getService().getBody().getAngle()))*5000,
						((float)Math.cos(hero.getService().getBody().getAngle()))*5000);
				hero.getService().getBody().applyForceToCenter(tmp.mul(500));

			}
			if(event.getKey() == KeyboardKey.RIGHT){
				float time = 1; // one second
				float torque = hero.getService().getBody().getInertia() * (1 - hero.getService().getBody().getAngularVelocity() ) / time;
				hero.getService().getBody().applyTorque(torque);
			}
			if(event.getKey() == KeyboardKey.LEFT){
				float time = 1; // one second
				float torque = hero.getService().getBody().getInertia() * (-1 - hero.getService().getBody().getAngularVelocity() ) / time;
				hero.getService().getBody().applyTorque(torque);
			}

			if(event.getKey() == KeyboardKey.SPACE){
				hero.getService().fire(null);
			}

			if(event.getKey() == KeyboardKey.B){
				if(hero.getService().getMunition().getMunitionBomb()==0 && hero.getService().getMunition().getMunitionMega()==0){
					graphics.setColor(Color.RED);
					graphics.setFont(new Font("Courrier", Font.BOLD, 50));
					graphics.drawString("No more bomb !!!",200,200);
				}
				hero.getService().explosion();
			}
		});
	}
}