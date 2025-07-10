package elements.entities;

import elements.Element;
import game.GamePanel;
import utilities.C;
import utilities.Vector;
import world.World;

public abstract class Entity extends Element {
    public double speed;
    public double realSpeed;
    Vector nextPosition = new Vector(0,0);

    public Entity(GamePanel gp,World world){

        super(gp,world);
    }

    public void setSpeed(double speed){
        if(speed >= 0)
            this.speed = speed* C.SCALE;
    }
}
