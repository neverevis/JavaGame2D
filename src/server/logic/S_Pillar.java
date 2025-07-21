package server.logic;

import math.Vector;
import physics.Collider;

public class S_Pillar {
    Collider collider;
    Vector pos = new Vector();
    public S_Pillar(ServerWorld serverWorld, double x, double y){
        pos.set(x,y);
        collider = new Collider(pos,32,16,-16,16){
            @Override
            public void onCollision(Collider other) {

            }
        };

        serverWorld.collSys.register(collider);
    }
}
