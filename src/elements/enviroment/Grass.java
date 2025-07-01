package elements.enviroment;

import elements.Element;
import game.GamePanel;
import utilities.Global;
import utilities.Sprite;
import utilities.TextCollider;
import world.World;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Grass extends Element {
    double rotation;
    double distance = 16;
    double rotationOffset;
    Random random = new Random(System.nanoTime());

    public Grass(GamePanel gp, World world){
        super(gp,world);
        rotationOffset = random.nextDouble();
    }

    @Override
    public void setAttributes() {
        sprite = new Sprite("/resources/elements/grass.png",32,32,1f);
        setSize(32,32);
        setAnchor(16,32);
        setFeetLine(32);
    }

    @Override
    public void update(double deltaTime) {
        double time = System.currentTimeMillis() / 1000.0; // tempo em segundos
        double maxAngle = 30; // ângulo máximo de oscilação
        rotation = Math.sin(time+rotationOffset * 4) * maxAngle;

        double targetRotation = Math.sin(time + rotationOffset * 2) * maxAngle;

        double dx = world.player.position.x - position.x;
        double dy = world.player.position.y - position.y;

        double pDist = Math.sqrt(dx * dx + dy * dy);
        double extraRotation = 1500 / pDist;
        if (extraRotation > 100) extraRotation = 100;

        if (dx <= 0) targetRotation += extraRotation;
        else         targetRotation -= extraRotation;

        rotation += (targetRotation - rotation) * 0.2;

    }

    @Override
    public void render(Graphics2D g2d) {
        AffineTransform original = g2d.getTransform();
        g2d.rotate(Math.toRadians(rotation),width*anchorX* Global.SCALE + position.x - world.camera.x,height*anchorY*Global.SCALE + position.y - world.camera.y);
        sprite.render(g2d, (int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y),width,height);
        g2d.setTransform(original);
        if(gp.activeWorld.showElementsAnchor) {
            renderAnchor(g2d);
            renderFeetLine(g2d);
        }
    }
}
