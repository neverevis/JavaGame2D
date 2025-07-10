package world;

import utilities.C;
import utilities.Vector;

import java.util.Random;

public class Camera {
    public double x;
    public double y;

    double screenCenterX = C.SCREENWIDTH/2;
    double screenCenterY = C.SCREENHEIGHT/2;
    double targetX;
    double targetY;
    double smoothing = 6.25;
    boolean lock = true;
    public boolean shaking = false;
    double time = 0;
    double mouseX;
    double mouseY;

    Random random = new Random();
    Vector point = new Vector();
    World world;

    public Camera(World world){
        this.world = world;
    }

    public void update(double deltaTime){
        updateTarget(deltaTime);
        if(lock) {
            x += (targetX - x) * smoothing * deltaTime;
            y += (targetY - y) * smoothing * deltaTime;
        }

        if(shaking) {
            time += deltaTime;
            x += -4 + random.nextInt(9);
            y += -4 + random.nextInt(9);

            if(time >= 0.25){
                time = 0;
                shaking = false;
            }
        }
    }

    public void lockOnPlayer(boolean lock){
        this.lock = lock;
    }

    void updateTarget(double deltaTime){
        double nextTarX = world.player.getAnchorX();
        double nextTarY = world.player.getAnchorY();

        double tolerance = 0* C.TILESIZE;

        if(!world.pause) {
            if (world.gp.cursorPoint != null) {
                mouseX = world.gp.cursorPoint.getX();
                mouseY = world.gp.cursorPoint.getY();
            }

            double moveX = (mouseX - screenCenterX) * 0;
            double moveY = (mouseY - screenCenterY) * 0;

            if (nextTarX - screenCenterX > 0 - tolerance - moveX && nextTarX + screenCenterX < world.width + tolerance + moveX)
                targetX = world.player.getAnchorX() - screenCenterX + moveX;
            if (nextTarY - screenCenterY > 0 - tolerance - moveY && nextTarY + screenCenterY < world.height + tolerance + moveY)
                targetY = world.player.getAnchorY() - screenCenterY + moveY;
        }else{
            smoothing = 2;
            targetX = world.player.getAnchorX() - screenCenterX;
            targetY = world.player.getAnchorY() - screenCenterY;
        }
    }

    public int relativeX(double x){
        return (int)(x - this.x);
    }

    public int relativeY(double y){
        return (int)(y - this.y);
    }

    public Vector relativeWorld(Vector point){

        this.point.x = point.get().x + x;
        this.point.y = point.get().y + y;

        return this.point.get();
    }
}
