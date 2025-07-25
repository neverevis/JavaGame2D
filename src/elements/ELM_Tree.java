package elements;

import graphics.Animator;
import graphics.GraphicsFX;
import graphics.Sprite;
import physics.Collider;
import world.World;

import java.awt.*;

public class ELM_Tree extends Element {
    Sprite sprite;
    Animator animation;
    Collider collider;

    public ELM_Tree(World world){
        sprite = new Sprite("/resources/elements/tree/tree.png",96,96);
        animation = new Animator(sprite,0,0,23,2);

        collider = new Collider(pos,18,12,39,79,true);

        world.collSys.register(collider);
    }

    @Override
    public void update(double dt) {
        animation.update(dt);
    }

    @Override
    public void render(GraphicsFX gfx) {
        gfx.draw(sprite,pos.x,pos.y);
    }

    @Override
    public double getZIndex() {
        return pos.y + 64;
    }
}
