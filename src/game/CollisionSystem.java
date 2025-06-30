package game;

import utilities.Collider;

import java.util.ArrayList;

public class CollisionSystem {
    ArrayList<Collider> colliders = new ArrayList<>();

    public void update(){
        for(Collider c : colliders){
            c.update();
        }

        for(int i = 0; i < colliders.size(); i++){
            for(int j = 0; j < colliders.size(); j++){
                Collider a = colliders.get(i);
                Collider b = colliders.get(j);

                if(a != b && a.colliding(b)){
                    a.onCollision();
                }
            }
        }
    }

    public void register(Collider c){
        colliders.add(c);
    }

    public void unregister(Collider c){
        colliders.remove(c);
    }

    public boolean willCollideX(Collider self,double futureX){
        for(Collider other : colliders){
            if(other != self){
                if(self.predictXCollision(other,futureX)) {
                    if(other.collision)
                        return true;
                }
            }
        }
        return false;
    }

    public boolean willCollideY(Collider self,double futureY){
        for(Collider other : colliders){
            if(other != self){
                if(self.predictYCollision(other,futureY)) {
                    if(other.collision)
                        return true;
                }
            }
        }
        return false;
    }
}
