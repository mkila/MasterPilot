package fr.umlv.graphics;

import java.awt.Color;
import java.awt.Graphics2D;


import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;


import fr.umlv.space.object.SpaceObject;
import fr.umlv.zen3.ApplicationContext;

public class GraphicsEngine {

	public static int WIDTH = 800;
	public static int HEIGHT = 600;

	public static void drawSpaceObject(ApplicationContext context,SpaceObject object,Vec2 heroPosition){
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
	
	
	
}