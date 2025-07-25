package physics;

import elements.ELM_Player;
import math.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Attack {
    ELM_Player player;
    Collider hitbox;
    Vector pos;

    public Attack(ELM_Player player){
        this.player = player;
        this.pos = new Vector();
        this.hitbox = new Collider(pos,13,13,0,2.5,false){
            @Override
            public void onCollision(Collider other) {
                if(other.owner != null){
                    System.out.println(other.owner.nickname + "Tomou dano");
                }
            }
        };

        player.world.collSys.register(hitbox);
    }

    public void update(double dt){
        if(player.state == player.ATTACKING){
            
        }
    }
}
