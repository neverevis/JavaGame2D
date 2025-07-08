package elements.enviroment;

import elements.Element;
import elements.entities.Entity;
import game.GamePanel;
import utilities.Global;
import utilities.Sprite;
import utilities.TextCollider;
import world.World;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Grass extends Element {
    double rotationa;
    double rotationb;
    double distance = 16;
    double rotationOffset;
    Random random = new Random(System.nanoTime());
    Sprite r_blade;
    Sprite l_blade;
    Sprite shadow;
    double targetOffset = 0.7;
    double stepOffset = 0;
    double leaningSpeed = 6;
    double time = 0;

    public Grass(GamePanel gp, World world){
        super(gp,world);
        rotationOffset = random.nextDouble(900);
    }

    @Override
    public void setAttributes() {
        r_blade = new Sprite("/resources/elements/grassrightblade.png",32,32,1f);
        l_blade = new Sprite("/resources/elements/grassleftblade.png",32,32,1f);
        shadow = new Sprite("/resources/elements/grassshadow.png",32,32,1f);
        setSize(32,32);
        setAnchor(16,32);
        setFeetLine(32);
    }

    @Override
    public void update(double deltaTime) {
        time += deltaTime;
        double maxAngle = 8;
        rotationa = Math.sin(time * 4 + rotationOffset) * maxAngle;
        rotationb = Math.sin(time * 4 + rotationOffset + 7) * maxAngle;

        for(Element elm : gp.activeWorld.elements){
            if(elm instanceof Entity){
                double dx = position.x - elm.position.x;
                double dy = position.y - elm.position.y;
                double distance = Math.sqrt(dx*dx + dy*dy);
                if(distance < 12*Global.SCALE){
                    if(elm.position.x < position.x){
                        if(stepOffset < 0.5){
                            stepOffset += (targetOffset - stepOffset) * leaningSpeed * deltaTime;
                        }
                    }else{
                        if(stepOffset > -0.5){
                            stepOffset -= (targetOffset - stepOffset) * leaningSpeed * deltaTime;
                        }
                    }
                }
                else{
                    if(stepOffset != 0){
                        //isso aq é genial tá
                        stepOffset *= Math.pow(0.95, deltaTime * Global.FPS);
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        shadow.render(g2d,(int)(position.x - world.camera.x),(int)(position.y - world.camera.y + 3*Global.SCALE),width,height);
        AffineTransform original = g2d.getTransform();
        g2d.rotate(Math.toRadians(rotationa) + stepOffset,width*anchorX* Global.SCALE + position.x - world.camera.x,height*anchorY*Global.SCALE + position.y - world.camera.y);
        l_blade.render(g2d, (int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y),width,height);
        g2d.setTransform(original);
        g2d.rotate(Math.toRadians(rotationb) + stepOffset,width*anchorX* Global.SCALE + position.x - world.camera.x,height*anchorY*Global.SCALE + position.y - world.camera.y);
        r_blade.render(g2d, (int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y),width,height);
        g2d.setTransform(original);
        if(gp.activeWorld.showElementsAnchor) {
            renderAnchor(g2d);
            renderFeetLine(g2d);
        }
    }
}
