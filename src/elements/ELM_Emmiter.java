package elements;

import graphics.GraphicsFX;
import math.Vector;
import world.World;

import java.util.concurrent.CopyOnWriteArrayList;

public class ELM_Emmiter extends Element{
    CopyOnWriteArrayList<ELM_Particle> particles = new CopyOnWriteArrayList<>();
    int spawnRate;
    double spawnPerSecond;
    double timer = 0;
    Vector pos;
    World world;

    public ELM_Emmiter(World world, Vector pos ,int spawnRate){
        this.world = world;
        this.pos = pos;
        this.spawnRate = spawnRate;
        spawnPerSecond = 1.0/spawnRate;
    }

    @Override
    public void update(double dt){
        timer += dt;

        if(timer >= spawnPerSecond){
            ELM_Particle p = new ELM_Particle(world,this,"/resources/particles/dust.png",10, 0.01,new Vector(0,-0.4));
            particles.add(p);
            world.core.renSys.register(p);

            timer = 0;
        }

        for(ELM_Particle p : particles){
            if(p.lifeTime > 0) {
                p.update(dt);
            }
            else {
                particles.remove(p);
                world.core.renSys.unregister(p);
            }
        }
    }

    @Override
    public void render(GraphicsFX gfx) {

    }

    @Override
    public double getZIndex() {
        return this.pos.y;
    }
}
