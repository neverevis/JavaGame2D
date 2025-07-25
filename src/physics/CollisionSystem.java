package physics;

import core.Key;
import graphics.GraphicsFX;
import graphics.Renderable;
import math.Vector;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionSystem implements Renderable{
    boolean display = false;
    CopyOnWriteArrayList<Collider> colliders = new CopyOnWriteArrayList<>();

    public void register(Collider c){
        colliders.add(c);
    }

    public void unregister(Collider c){
        colliders.remove(c);
    }

    public void display(boolean b){
        display = b;
    }

    public void update(){
        if(Key.toggleAnchorDisplay)
            display = true;
        else
            display = false;
        for(Collider self : colliders){
            self.update();
            boolean collided = false;
            for(Collider other : colliders){
                if(other != self) {
                    if (self.area.intersects(other.area)) {
                        collided = true;
                        self.onCollision(other);
                    }
                }
            }

            self.colliding = collided;
        }
    }

    @Override
    public void render(GraphicsFX gfx) {
        if(display) {
            for (Collider c : colliders) {
                c.render(gfx);
            }
        }
    }

    public boolean testCollision(Collider collider){
        for(Collider other : colliders){
            if(other != collider && collider.willCollide(other) && other.solid)
                return  true;
        }

        return false;
    }

    public boolean predictCollision_x(Collider collider, double futureX){
        double originalX = collider.pos.x;
        collider.pos.x = futureX;
        collider.update();
        boolean colliding = testCollision(collider);
        collider.pos.x = originalX;
        collider.update();

        return colliding;
    }

    public boolean predictCollision_y(Collider collider, double futureY){
        double originalY = collider.pos.y;
        collider.pos.y = futureY;
        collider.update();
        boolean colliding = testCollision(collider);
        collider.pos.y = originalY;
        collider.update();

        return colliding;
    }

    @Override
    public double getZIndex() {
        return 0;
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
