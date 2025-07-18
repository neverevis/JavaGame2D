package elements;

import graphics.GraphicsFX;
import math.Vector;
import world.World;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ELM_Emmiter extends Element{
    ArrayList<ELM_Particle> particlePool = new ArrayList<>();
    int spawnRate;
    double lifeTime;
    int offsetX;
    int offsetY;
    double windX;
    double windY;
    double gravity;
    double size;

    double spawnPerSecond;
    double timer = 0;
    Vector pos;
    World world;

    public ELM_Emmiter(World world, Vector pos, double size ,int offsetX, int offsetY,int spawnRate, double lifeTime, double windX, double windY, double gravity){
        this.world = world;
        this.pos = pos;
        this.size = size;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.spawnRate = spawnRate;
        this.lifeTime = lifeTime;
        this.windX = windX;
        this.windY = windY;
        this.gravity = gravity;
        spawnPerSecond = 1.0/spawnRate;
    }

    @Override
    public void update(double dt){
        timer += dt;

        if(timer >= spawnPerSecond){
            spawnParticle();

            timer = 0;
        }

        for(ELM_Particle p : particlePool){
            if(p.active) {
                p.update(dt);
            }
        }
    }

    public void spawnParticle(){
        for(ELM_Particle p : particlePool){
            if(!p.active) {
                p.reset();
                return;
            }
        }

        ELM_Particle p = new ELM_Particle(this,"/resources/particles/dust.png",size,lifeTime);
        particlePool.add(p);
        world.elements.add(p);
    }

    @Override
    public void render(GraphicsFX gfx) {

    }

    @Override
    public double getZIndex() {
        return this.pos.y;
    }
}
