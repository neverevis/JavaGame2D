package world;
import core.G;
import math.Vector;

import java.util.Random;

public class Camera {
    public Vector pos = new Vector();
    public Vector center;
    public Vector target;


    Random random = new Random();
    World world;

    public Camera(World world){
        this.world = world;
        center = new Vector((double)(G.S_WIDTH/2/4),(double)(G.S_HEIGHT/2/4));
    }

    public void update(double dt) {
        target = world.player.pos.copy().sub(center);

        pos.add(target.sub(pos).multiply(5 * dt));
    }
}
