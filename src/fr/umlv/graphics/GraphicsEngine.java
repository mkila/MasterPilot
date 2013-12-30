package fr.umlv.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import fr.umlv.space.object.Fire;
import fr.umlv.space.object.SpaceObject;
import fr.umlv.space.service.Service;
import fr.umlv.zen3.ApplicationContext;

public class GraphicsEngine {

	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	public static void drawSpaceObject(ApplicationContext context,SpaceObject object,Vec2 heroPosition){
		if(!object.getService().getBody().isActive())
			return;
		Fixture fixture = object.getService().getBody().getFixtureList();
		if(fixture!=null){
			switch (fixture.getShape().getType()) {
			//Draw POLYGON
			case POLYGON:
				context.render(graphics -> {
					graphics.translate(WIDTH/2 - heroPosition.x, HEIGHT/2 - heroPosition.y);
					int[] x;
					int[] y;
					if(fixture!=null){
						PolygonShape poly =	(PolygonShape)	fixture.getShape();
						x = new int[poly.getVertices().length];
						y= new int[poly.getVertices().length];
						int i = 0;
						for(Vec2 vertice : poly.getVertices()){
							x[i]=(int) object.getService().getBody().getWorldPoint(vertice).x;
							y[i]=(int) object.getService().getBody().getWorldPoint(vertice).y;
							//graphics.drawOval(x[i], y[i], 5, 5);
							i++;
						}
						graphics.fillPolygon(x, y, poly.getVertices().length);
					}
				});
				break;


				//TODO Draw CHAIN	
			case CHAIN:
				context.render(graphics -> {
					graphics.translate(WIDTH/2 - heroPosition.x, HEIGHT/2 - heroPosition.y);
					graphics.drawString("Not fixture", 10, 10);
				});
				break;

				//Draw CIRCLE
			case CIRCLE:
				context.render(graphics -> {
					graphics.translate(WIDTH/2 - heroPosition.x, HEIGHT/2 - heroPosition.y);
					CircleShape circle =	(CircleShape)fixture.getShape();
					graphics.fillOval((int)object.getService().getBody().getPosition().x - (int)circle.getRadius(),
							(int)object.getService().getBody().getPosition().y - (int)circle.getRadius(), 
							(int)circle.getRadius()*2,
							(int)circle.getRadius()*2);
				});
				break;

				//TODO Draw EDGE
			case EDGE:
				context.render(graphics -> {
					graphics.translate(WIDTH/2 - heroPosition.x, HEIGHT/2 - heroPosition.y);
					graphics.drawString("Not fixture", 10, 10);
				});
				break;

			default:
				break;
			}

		}
	}




	public static void setBackground(ApplicationContext context, Color color) {
		context.render(graphics -> {
			Graphics2D g = graphics;
			g.setBackground(color);
		});

	}

	public static void graphicClear(ApplicationContext context) {
		context.render(graphics -> {
			Graphics2D g = graphics;
			g.clearRect(0, 0, 800, 600);
		});

	}

	public static void drawFire(ApplicationContext context,SpaceObject object,Vec2 heroPosition){
		LinkedList<Fire> fires = object.getService().getListFire();
		for (Fire fire : fires) {
			drawSpaceObject(context, fire, heroPosition);
		}
	}

	public static void drawBomb(ApplicationContext context,SpaceObject object,Vec2 heroPosition){
		Fixture fixture = object.getService().getBody().getFixtureList();

		context.render(graphics -> {
			graphics.translate(WIDTH/2 - heroPosition.x, HEIGHT/2 - heroPosition.y);
			int[] x;
			int[] y;
			if(fixture!=null){
				PolygonShape poly =	(PolygonShape)	fixture.getShape();
				x = new int[poly.getVertices().length];
				y= new int[poly.getVertices().length];
				int i = 0;
				for(Vec2 vertice : poly.getVertices()){
					x[i]=(int) object.getService().getBody().getWorldPoint(vertice).x;
					y[i]=(int) object.getService().getBody().getWorldPoint(vertice).y;
					i++;
				}
				if(object.getService().getType() == Service.TYPEBONUS.BOMB){
					graphics.setColor(Color.RED);
				}
				else
					graphics.setColor(Color.YELLOW);
				graphics.fillPolygon(x, y, poly.getVertices().length);
			}
		});
	}
/***
 * Méthode qui dessine le bouclier en fonction des collision
 * @param context
 * @param hero
 */
	public static void drawBouclier(ApplicationContext context,SpaceObject hero){
		if(hero.getService().getFlagCollision()){
			context.render(graphics -> {
				graphics.translate(WIDTH/2 - hero.getService().getBody().getWorldCenter().x, HEIGHT/2 - hero.getService().getBody().getWorldCenter().y);
				Graphics2D g=graphics;
				g.drawOval((int)hero.getService().getBody().getWorldCenter().x-30,(int)hero.getService().getBody().getWorldCenter().y-30, 60, 60);
			});
			hero.getService().collision();
		}
	}

	public static void drawTIE(ApplicationContext context,SpaceObject object,Vec2 heroPosition){
		if(!object.getService().getBody().isActive())
			return;
		context.render(graphics -> {
			graphics.translate(WIDTH/2 - heroPosition.x, HEIGHT/2 - heroPosition.y);
			Graphics2D g=graphics;
			g.setColor(Color.WHITE);
			AffineTransform rotation = new AffineTransform();
			rotation.rotate(object.getService().getBody().getAngle(),22,17.5);
			int[] x = {5,10,15,30,35,40,35,30,15,10};
			int[] y = {15,40,17,17,40,15,0,12,12,0};
			g.translate(object.getService().getBody().getWorldCenter().x-22, object.getService().getBody().getWorldCenter().y-17.5);
			g.transform(rotation);
			graphics.fillPolygon(x, y, x.length);

		});
	}
}