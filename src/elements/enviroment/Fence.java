package elements.enviroment;

import elements.Element;
import game.GamePanel;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Fence extends Element {
    BufferedImage spriteSheet;
    public Fence(GamePanel gp, World world){
        super(gp,world);
    }
    @Override
    public void setAttributes() {
        try{
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/elements/fence.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(96,32);
        setAnchor((int)(width/2),(int)(height/2));
        setPosition(500,500);
        setFeetLine(height);
        setSprite(spriteSheet);
        collider.collision = true;
        collider.setBounds(this,0,28,96,4);
    }

    @Override
    public void update(double deltaTime) {
        collider.update();
    }

    @Override
    public void render(Graphics2D g2d) {
        sprite.render(g2d,world.camera.relativeX(position.x),world.camera.relativeY(position.y),width,height);

        if(world.showElementsAnchor){
            renderAnchor(g2d);
            renderFeetLine(g2d);
            collider.render(g2d);
        }
    }
}
