package elements;

import core.G;
import graphics.ImageManager;
import world.World;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ELM_Grass extends Element {
    double rotationa;
    double rotationb;
    double rotationOffset;
    final static Random random = new Random();
    static BufferedImage r_blade;
    static BufferedImage l_blade;
    static BufferedImage shadow;
    World world;
    double targetOffset = 8;
    double stepOffset = 0;
    double leaningSpeed = 6;

    public ELM_Grass(World world){
        r_blade = ImageManager.load("/resources/elements/grass/right_blade.png");
        l_blade = ImageManager.load("/resources/elements/grass/left_blade.png");
        shadow = ImageManager.load("/resources/elements/grass/shadow.png");
        this.world = world;
        rotationOffset = random.nextDouble(900);
    }

    @Override
    public void update(double deltaTime) {
        double maxAngle = 8;
        rotationa = Math.sin(G.time * 4 + rotationOffset) * maxAngle;
        rotationb = Math.sin(G.time * 4 + rotationOffset + 8) * maxAngle;

        for(ELM_Player p : world.players){
            double dx = pos.x - p.pos.x;
            double dy = pos.y - p.pos.y;
            double distance = Math.sqrt(dx*dx + dy*dy);
            if(distance < 4){
                if(p.pos.x < pos.x){
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
                    stepOffset *= Math.pow(0.97, deltaTime * G.FPS);
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(shadow,(int)pos.x,(int)pos.y,null);

        AffineTransform original = g.getTransform();

        g.translate(16,32);
        g.rotate(rotationa);
        g.drawImage(l_blade,(int)pos.x,(int)pos.y,null);

        g.setTransform(original);

        g.translate(16,32);
        g.rotate(rotationa);
        g.drawImage(r_blade,(int)pos.x,(int)pos.y,null);
    }

    @Override
    public double getZIndex() {
        return pos.y;
    }
}
