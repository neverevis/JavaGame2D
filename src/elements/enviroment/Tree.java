package elements.enviroment;

import elements.Element;
import game.GamePanel;
import utilities.Collider;
import utilities.Global;
import utilities.Sprite;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Tree extends Element {
    BufferedImage spriteSheet;
    Random random;
    public Tree(GamePanel gp, World world){
        super(gp,world);
    }
    @Override
    public void setAttributes() {
        setSize(96,96);
        setAnchor(48,48);
        setPosition(500,500);
        sprite = new Sprite("/resources/elements/arvore2.png",width,height,3.5f);
        setFeetLine(93);
        sprite.moving = true;
        random = new Random(System.nanoTime());
        sprite.setFrame(random.nextInt(6));
        collider.setBounds(this,38,80,19,10);
        collider.collision = true;
        world.collisionSystem.register(collider);
    }

    @Override
    public void update(double deltaTime) {
        sprite.update(deltaTime);
        collider.update();
    }

    @Override
    public void render(Graphics2D g2d) {
        sprite.render(g2d,(int)(position.x - world.camera.x),(int)(position.y - world.camera.y),width,height);
        if(world.showElementsAnchor) {
            renderAnchor(g2d);
            collider.render(g2d);
            renderFeetLine(g2d);
        }
    }
}
