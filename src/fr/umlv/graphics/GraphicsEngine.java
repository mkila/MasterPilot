package fr.umlv.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import fr.umlv.physics.PhysicsEngine;
import fr.umlv.zen3.ApplicationContext;

public class GraphicsEngine {
	public static void drawSpaceObject(ApplicationContext context){
		Body bodyList =PhysicsEngine.getWorld().getBodyList();
		while(bodyList!=null){
			final Body body = bodyList; 
			Fixture fixture =  bodyList.getFixtureList();
			if(fixture!=null){
				switch (fixture.getShape().getType()) {
				//Draw POLYGON
				case POLYGON:
					context.render(graphics -> {
						int[] x;
						int[] y;
					
						if(fixture!=null){
							PolygonShape poly =	(PolygonShape)	fixture.getShape();
							x = new int[poly.getVertices().length];
							y= new int[poly.getVertices().length];
							int i = 0;
							for(Vec2 vertice : poly.getVertices()){
								x[i]=(int) body.getWorldVector(vertice).x;
								y[i]=(int) body.getWorldVector(vertice).y;
								i++;
							}
							graphics.drawPolygon(x, y, poly.getVertices().length);
						}
					});
					break;
					
					
					//TODO Draw CHAIN	
				case CHAIN:
					context.render(graphics -> {
						graphics.drawString("Not fixture", 10, 10);
					});
					break;
					
					//TODO Draw CIRCLE
				case CIRCLE:
					context.render(graphics -> {
						graphics.drawString("Not fixture", 10, 10);
					});
					break;
					
					//TODO Draw EDGE
				case EDGE:
					context.render(graphics -> {
						graphics.drawString("Not fixture", 10, 10);
					});
					break;

				default:
					break;
				}

			}
			bodyList = bodyList.getNext();
		}
	}



	public static void setBackground(ApplicationContext context, Color color) {
		context.render(graphics -> {
			Graphics2D g = graphics;
			g.setBackground(color);
			g.clearRect(0, 0, 800, 600);
		});

	}	
}
