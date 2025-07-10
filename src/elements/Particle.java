package elements;

import java.util.Random;

public class Particle {
    private static final Random random = new Random();
    public double x;
    public double y;
    public double velocityY;
    public double scale;

    public Particle(double x, double y){
        scale = random.nextInt(5) + 10;
        this.x = x;
        this.y = y;
        this.velocityY = -100.0;
    }

    public double getX(){
        return x - scale;
    }

    public double getY(){
        return y - scale;
    }
}
