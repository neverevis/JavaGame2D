package elements.entities;

import elements.Element;
import game.GamePanel;
import utilities.Collider;
import utilities.Global;
import utilities.Vector;
import world.World;

import java.awt.image.BufferedImage;

public abstract class Entity extends Element {
    public double speed;
    public double realSpeed;
    Vector nextPosition = new Vector(0,0);
    BufferedImage spriteSheet;

    public Entity(GamePanel gp,World world){
        super(gp,world);
    }

    public void setSpeed(double speed){
        if(speed >= 0)
            this.speed = speed* Global.SCALE;
    }
}
