package physics;

import core.Key;
import graphics.GraphicsFX;
import graphics.Renderable;

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

    public boolean testCollision(Collider other){
        for(Collider self : colliders){
            if(other != self && other.willCollide(self))
                return  true;
        }

        return false;
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
