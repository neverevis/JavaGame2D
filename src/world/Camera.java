package world;

import utilities.Global;

public class Camera {
    public double x;
    public double y;

    double cameraPositionTargetX;
    double cameraPositionTargetY;

    double screenCenterX = Global.SCREENWIDTH/2;
    double screenCenterY = Global.SCREENHEIGHT/2;
    double targetX;
    double targetY;
    double smoothing = 0.08;
    boolean lock = true;

    World world;

    public Camera(World world){
        this.world = world;
    }

    public void update(){
        updateTarget();
        if(lock) {
            x += (targetX - x) * smoothing;
            y += (targetY - y) * smoothing;
        }
    }

    public void lockOnPlayer(boolean lock){
        this.lock = lock;
    }

    void updateTarget(){
        targetX = world.player.getAnchorX() - screenCenterX;
        targetY = world.player.getAnchorY() - screenCenterY;
    }
}
