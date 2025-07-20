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
        animator = new Animator(sprite,0,0,sprite.totalCol-1,0.5);

        double randX = random.nextDouble(emmiter.spawnRange*2)- emmiter.spawnRange;
        double randY = random.nextDouble(emmiter.spawnRange*2) - emmiter.spawnRange;

        pos.set(emmiter.pos.x + emmiter.offsetX + randX,emmiter.pos.y + emmiter.offsetY + randY);
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
                size *= Math.pow(0.1,dt / emmiter.lifeTime);
            }
        }else{
            active = false;
        }
    }

    @Override
    public void render(GraphicsFX gfx) {
        gfx.opacity((float)emmiter.opacity);
        if(active)
            gfx.draw(sprite, pos.x, pos.y,size);
        gfx.opacity(1);
    }

    public void reset(){
        active = true;
        lifeTime = emmiter.lifeTime;
        this.size = random.nextDouble(emmiter.size);
        velocity.reset();

        double randX = random.nextDouble(emmiter.spawnRange*2)- emmiter.spawnRange;
        double randY = random.nextDouble(emmiter.spawnRange*2) - emmiter.spawnRange;

        pos.set(emmiter.pos.x + emmiter.offsetX + randX,emmiter.pos.y + emmiter.offsetY + randY);
    }

    @Override
    public double getZIndex() {
        return pos.y;
    }
}
