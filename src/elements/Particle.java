package elements;

import utilities.Global;
import utilities.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Particle {
    Random random = new Random(System.nanoTime());
    public double x;
    public double y;
    public double velocityY;
    public double scale;
    BufferedImage dust;
    Sprite sprite;
    public Particle(double x, double y){
        scale = random.nextInt(5) + 10;
        this.x = x;
        this.y = y;
        this.velocityY = -100.0;
        try {
            dust = ImageIO.read(getClass().getResourceAsStream("/particles/dust.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sprite = new Sprite(dust,16,16,0.2f);
        sprite.moving = true;
    }

    public double getX(){
        return x - scale;
    }

    public double getY(){
        return y - scale;
    }
}
