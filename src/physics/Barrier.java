package physics;

import world.World;

public class Barrier {
    World world;
    Collider collider;
    double x;
    double y;
    int w;
    int h;

    public Barrier(World world, double x, double y, int w, int h){
        collider = new Collider(x-16,y-16,w,h,true){
            @Override
            public void onCollision(Collider other) {

            }
        };
        world.collSys.register(collider);
    }
}
