package elements.enviroment;

import elements.Element;
import elements.entities.Entity;
import game.GamePanel;
import utilities.C;
import utilities.ImageManager;
import utilities.Sprite;
import world.World;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Grass extends Element {
    double rotationa;
    double rotationb;
    double rotationOffset;
    final static Random random = new Random();
    static BufferedImage r_blade;
    static BufferedImage l_blade;
    static BufferedImage[] r_bladeR;
    static BufferedImage[] l_bladeR;
    static Sprite shadow;
    double targetOffset = 0.7;
    double stepOffset = 0;
    double leaningSpeed = 6;

    public Grass(GamePanel gp, World world){
        super(gp,world);
        rotationOffset = random.nextDouble(900);
    }

    @Override
    public void setAttributes() {
        r_blade = ImageManager.loadImage("/resources/elements/grassrightblade.png",32,32);
        l_blade = ImageManager.loadImage("/resources/elements/grassleftblade.png",32,32);
        r_blade = ImageManager.getScaled(r_blade,(int)C.SCALE);
        l_blade = ImageManager.getScaled(l_blade,(int)C.SCALE);
        shadow = new Sprite("/resources/elements/grassshadow.png",32,32,1f);

        if(r_bladeR == null) {
            r_bladeR = new BufferedImage[100];
            l_bladeR = new BufferedImage[100];

            int angle = -50;
            for (int i = 0; i < 100; i++) {
                r_bladeR[i] = new BufferedImage((int) (32 * C.SCALE), (int) (32 * C.SCALE), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) r_bladeR[i].getGraphics();
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                g.rotate(Math.toRadians(angle), (int) (32 / 2 * C.SCALE), (int) (32 * C.SCALE));
                g.drawImage(r_blade, 0, 0, null);
                g.dispose();
                angle++;
            }

            angle = -50;
            for (int i = 0; i < 100; i++) {
                this.l_bladeR[i] = new BufferedImage((int) (32 * C.SCALE), (int) (32 * C.SCALE), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) l_bladeR[i].getGraphics();
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                g.rotate(Math.toRadians(angle), (int) (32 / 2 * C.SCALE), (int) (32 * C.SCALE));
                g.drawImage(l_blade, 0, 0, null);
                g.dispose();
                angle++;
            }
        }

        setSize(32,32);
        setAnchor(16,32);
        setFeetLine(32);
    }

    @Override
    public void update(double deltaTime) {
        double maxAngle = 8;
        rotationa = Math.sin(gp.time * 4 + rotationOffset) * maxAngle;
        rotationb = Math.sin(gp.time * 4 + rotationOffset + 8) * maxAngle;

        for(Entity ent : gp.activeWorld.entities){
            double dx = position.x - ent.position.x;
            double dy = position.y - ent.position.y;
            double distance = Math.sqrt(dx*dx + dy*dy);
            if(distance < 12* C.SCALE){
                if(ent.position.x < position.x){
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
                    stepOffset *= Math.pow(0.95, deltaTime * C.FPS);
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        shadow.render(g2d,(int)(position.x - world.camera.x),(int)(position.y - world.camera.y + 3* C.SCALE));
        g2d.drawImage(l_bladeR[(int)(rotationa + 50 + Math.toDegrees(stepOffset))],(int)(position.x - world.camera.x),(int)(position.y - world.camera.y),null);
        g2d.drawImage(r_bladeR[(int)(rotationb + 50 + Math.toDegrees(stepOffset))],(int)(position.x - world.camera.x),(int)(position.y - world.camera.y),null);
        if(gp.activeWorld.showElementsAnchor) {
            renderAnchor(g2d);
            renderFeetLine(g2d);
        }
    }
}
