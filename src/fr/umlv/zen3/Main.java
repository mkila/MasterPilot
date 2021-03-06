package fr.umlv.zen3;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Main {
  public static void main(String[] args) {
    int WIDTH = 800;
    int HEIGHT = 600;
    int SIZE = 30;
    int STRIDE = 100;

    Application.run("Colors", WIDTH, HEIGHT, context -> {
      Random random = new Random(0);
      for(;;) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        context.render(graphics -> {
          for(int i = 0; i < STRIDE; i++) {
            float x = random.nextInt(WIDTH);
            float y = random.nextInt(HEIGHT);

            Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), random.nextInt(255));
            RadialGradientPaint paint = new RadialGradientPaint(x, y, SIZE, new float[]{0f, 1f}, new Color[]{color, Color.WHITE});
            graphics.setPaint(paint);
            graphics.setBackground(Color.BLACK);
            graphics.fill(new Ellipse2D.Float(x - SIZE/2, y - SIZE/2, SIZE, SIZE));
          }
        });
      }
    });
  }
}
