package elements;

import graphics.Animator;
import graphics.GraphicsFX;
import graphics.Sprite;
import math.Vector;
import world.World;

import java.util.Random;

public class ELM_Particle extends Element {
    Sprite sprite;
    double lifeTime;
    double size = 1;
    boolean active = true;
    Vector velocity = new Vector();
    ELM_Emmiter emmiter;
    Animator animator;
    Random random = new Random();

    public ELM_Particle(ELM_Emmiter emmiter,String spritePath,double size, double lifeTime){
        this.emmiter = emmiter;
        this.sprite = new Sprite(spritePath,16,16);
        this.lifeTime = lifeTime;
        this.size = random.nextDouble(emmiter.size);

        animator = new Animator(sprite,0,0,4,0.5);

        pos.set(emmiter.pos.x + emmiter.offsetX,emmiter.pos.y + emmiter.offsetY);
    }

    @Override
    public void update(double dt) {
        lifeTime -= dt;
        if(lifeTime > 0){
            animator.update(dt);
            velocity.add(0,emmiter.gravity * dt);
            pos.add(emmiter.windX * dt,emmiter.windY * dt);
            pos.add(velocity);
            if(size > 0){
                size -= 0.2 * dt;
            }
        }else{
            active = false;
        }
    }

    @Override
    public void render(GraphicsFX gfx) {
        if(active)
            gfx.draw(sprite, pos.x, pos.y,size);
    }

    public void reset(){
        active = true;
        lifeTime = emmiter.lifeTime;
        pos.set(emmiter.pos.x + emmiter.offsetX,emmiter.pos.y + emmiter.offsetY);
        this.size = random.nextDouble(emmiter.size);
        velocity.reset();
    }

    @Override
    public double getZIndex() {
        return pos.y + 8;
    }
}
