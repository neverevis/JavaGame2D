package elements;

import graphics.GraphicsFX;
import graphics.Sprite;
import math.Vector;
import physics.Collider;
import world.World;

public class ELM_Box extends Element{
    World world;
    Collider collider;
    Sprite sprite;

    public ELM_Box(World world){
        this.world = world;
        sprite = new Sprite("/resources/elements/box/box.png",32,32);
        collider = new Collider(pos,30,8,1,24){
            @Override
            public void onCollision(Collider other) {
                Vector this_center = new Vector(this.area.width / 2 + this.area.x, this.area.height / 2 + this.area.y);
                Vector other_center = new Vector(other.area.width / 2 + other.area.x, other.area.height / 2 + other.area.y);

                boolean right = false;
                boolean top = false;

                if (this_center.x - other_center.x > 0) {
                    right = true;
                }

                if (this_center.y - other_center.y < 0) {
                    top = true;
                }

                if (other_center.x > this.area.x && other_center.x < this.area.x + this.area.width) {
                    if (top)
                        this.pos.add(new Vector(0, -0.5));
                    else
                        this.pos.add(new Vector(0, 0.5));
                }
                if (other_center.y > this.area.y && other_center.y < this.area.y + this.area.height) {
                    if (right)
                        this.pos.add(new Vector(0.5, 0));
                    else
                        this.pos.add(new Vector(-0.5, 0));
                }
            }
        };

        world.collSys.register(collider);
    }
    @Override
    public void update(double dt) {

    }

    @Override
    public void render(GraphicsFX gfx) {
        gfx.draw(sprite,pos.x,pos.y);
    }

    @Override
    public double getZIndex() {
        return this.pos.y + 16;
    }
}
