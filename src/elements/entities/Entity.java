package elements.entities;

import elements.Element;
import game.GamePanel;
import utilities.Global;
import world.World;

import java.awt.image.BufferedImage;

public abstract class Entity extends Element {
    public double speed;
    public double realSpeed;
    BufferedImage spriteSheet;;

    public Entity(GamePanel gp,World world){
        super(gp,world);
    }

    public void setSpeed(double speed){
        if(speed >= 0)
            this.speed = speed* Global.SCALE;
    }
}
