package server.logic;
import physics.Collider;
import server.logic.ServerWorld;

public class S_Barrier {
    ServerWorld world;
    Collider collider;
    double x;
    double y;
    int w;
    int h;

    public S_Barrier(ServerWorld world, double x, double y, int w, int h){
        collider = new Collider(x-16,y-16,w,h,true){
            @Override
            public void onCollision(Collider other) {

            }
        };
        world.collSys.register(collider);
    }
}
