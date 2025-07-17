package elements;

import graphics.GraphicsFX;
import graphics.Sprite;
import math.Vector;
import world.World;

public class ELM_Particle extends Element {
    Sprite sprite;
    double lifeTime;
    double gravity;
    Vector wind;
    Vector velocity = new Vector();
    World world;

    public ELM_Particle(World world, ELM_Emmiter emmiter,String spritePath, double lifetime, double gravity, Vector wind){
        this.world = world;
        this.sprite = new Sprite(spritePath,16,16);
        this.lifeTime = lifetime;
        this.gravity = gravity;
        this.wind = wind;

        pos.set(emmiter.pos.x,emmiter.pos.y);
    }

    @Override
    public void update(double dt) {
        lifeTime -= dt;
        if(lifeTime > 0){
            velocity.add(0,gravity);
            pos.add(wind);
            pos.add(velocity);
        }
    }

    @Override
    public void render(GraphicsFX gfx) {
        if(lifeTime > 0) {
            gfx.draw(sprite, pos.x - world.camera.pos.x, pos.y - world.camera.pos.y);
        }
    }

    @Override
    public double getZIndex() {
        return pos.y;
    }
}
