package elements;

import graphics.GraphicsFX;
import graphics.Sprite;
import math.Vector;
import physics.Collider;
import world.World;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ELM_Pillar extends Element{
    World world;
    Sprite sprite = new Sprite("/resources/elements/pillar/pillar.png",64,64);
    Collider collider;
    float opacity = 1;
    float opacityTarget = 0.6f;

    public ELM_Pillar(World world, double x, double y){
        this.world = world;
        collider = new Collider(pos,32,16,-16,16){
            @Override
            public void onCollision(Collider other) {

            }
        };
        pos.x = x;
        pos.y = y;

        world.collSys.register(collider);
    }

    @Override
    public void update(double dt) {
        if(
                world.player.getZIndex() < getZIndex() &&
                world.player.pos.y > pos.y - 32 &&
                world.player.pos.x > pos.x - 16 &&
                world.player.pos.x < pos.x + 16
        ) {
            opacity += (float)((opacityTarget - opacity) * 5 * dt);
        }
        else{
            opacity += (float)((1 - opacity) * 5 * dt);
        }
    }

    @Override
    public void render(GraphicsFX gfx) {
        gfx.save();
        gfx.opacity(opacity);
        gfx.draw(sprite, pos.x, pos.y);
        gfx.restore();
    }

    @Override
    public double getZIndex() {
        return pos.y + 32;
    }
}
