package game;

import utilities.Collider;

import java.util.ArrayList;

public class CollisionSystem {
    ArrayList<Collider> colliders = new ArrayList<>();

    public void update(){
        for(Collider c : colliders){
            c.update();
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
                if(self.predictXCollision(other,futureX))
                    return true;
            }
        }
        return false;
    }

    public boolean willCollideY(Collider self,double futureY){
        for(Collider other : colliders){
            if(other != self){
                if(self.predictYCollision(other,futureY)) {
                    System.out.printf(other.owner.toString());
                    return true;
                }
            }
        }
        return false;
    }
}
