package elements;

import graphics.Animator;
import graphics.GraphicsFX;
import graphics.ImageManager;
import graphics.Sprite;
import world.World;

import java.awt.image.BufferedImage;
import java.util.Random;

public class ELM_Torch extends Element{
    Sprite sprite;
    Animator animation;
    ELM_Emmiter particles;
    BufferedImage glow;
    World world;
    Random random = new Random();

    double time;
    double glowSize;

    public ELM_Torch(World world, double x, double y){
        this.world = world;
        pos.set(x,y);
        sprite = new Sprite("/resources/elements/torch/torch.png",32,32);
        particles = new ELM_Emmiter(world,"/resources/elements/torch/fireParticle.png",pos,1.5,1.6,1,0,-2,25,3,0,-5,0);
        animation = new Animator(sprite,0,0,2,0.2);
        glow = ImageManager.load("/resources/elements/torch/glow.png");
        time = random.nextDouble(15);
    }

    @Override
    public void update(double dt) {
        time += dt;

        glowSize = Math.sin(time*10)*0.1 + 1;
        animation.update(dt);
        particles.update(dt);
    }

    @Override
    public void render(GraphicsFX gfx) {
        gfx.draw(sprite,pos.x,pos.y);
        gfx.opacity(0.5f);
        gfx.draw(glow,pos.x,pos.y-3,glowSize);
        gfx.opacity(1f);
    }

    @Override
    public double getZIndex() {
        return pos.y;
    }
}
