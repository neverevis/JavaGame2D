package world;

import utilities.Global;
import utilities.Vector;

public class Camera {
    public double x;
    public double y;

    double screenCenterX = Global.SCREENWIDTH/2;
    double screenCenterY = Global.SCREENHEIGHT/2;
    double targetX;
    double targetY;
    double smoothing = 6.25;
    boolean lock = true;
    double mouseX;
    double mouseY;

    Vector point = new Vector();
    World world;

    public Camera(World world){
        this.world = world;
    }

    public void update(double deltaTime){
        updateTarget();
        if(lock) {
            x += (targetX - x) * smoothing * deltaTime;
            y += (targetY - y) * smoothing * deltaTime;
        }
    }

    public void lockOnPlayer(boolean lock){
        this.lock = lock;
    }

    void updateTarget(){
        double nextTarX = world.player.getAnchorX();
        double nextTarY = world.player.getAnchorY();

        double tolerance = 3*Global.TILESIZE;

        if(world.gp.cursorPoint != null) {
            mouseX = world.gp.cursorPoint.getX();
            mouseY = world.gp.cursorPoint.getY();
        }

        double moveX = (mouseX - screenCenterX)*0.1;
        double moveY = (mouseY - screenCenterY)*0.1;

        if(nextTarX - screenCenterX > 0 - tolerance && nextTarX + screenCenterX < world.width + tolerance)
            targetX = world.player.getAnchorX() - screenCenterX + moveX;
        if(nextTarY - screenCenterY > 0 - tolerance&& nextTarY + screenCenterY < world.height + tolerance)
            targetY = world.player.getAnchorY() - screenCenterY + moveY;
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
