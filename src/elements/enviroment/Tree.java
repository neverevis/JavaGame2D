package elements.enviroment;

import elements.Element;
import game.GamePanel;
import utilities.Global;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tree extends Element {
    BufferedImage spriteSheet;
    public Tree(GamePanel gp, World world){
        super(gp,world);
    }
    @Override
    public void setAttributes() {
        try{
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/elements/arvore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(96,96);
        setAnchor(48,48);
        setPosition(500,500);
        setSprite(spriteSheet);
        setFeetLine(93);
        setCollisionBox(40,82,16,10);
        collision = true;
    }

    @Override
    public void update(double deltaTime) {
        updateCollisionBox(collisionBox,x,y);
    }

    @Override
    public void render(Graphics2D g2d) {
        sprite.render(g2d,(int)(x - world.camera.x),(int)(y - world.camera.y),width,height);
        if(world.showElementsAnchor) {
            renderAnchor(g2d);
            g2d.setColor(Color.red);
            renderCollisionBox(g2d, collisionBox);
            renderFeetLine(g2d);
        }
    }
}
