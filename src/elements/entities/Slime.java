package elements.entities;

import elements.Element;
import elements.states.EnemyState;
import game.GamePanel;
import utilities.Global;
import utilities.MathUtils;
import utilities.Sprite;
import utilities.Vector;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Slime extends Entity{

    Player player;
    Random random = new Random(System.nanoTime());
    EnemyState slimeState = EnemyState.IDLE;
    BufferedImage shadowsheet;
    Sprite shadow;
    Vector direction = new Vector(0,0);
    Vector target = new Vector(0,0);
    Vector velocity = new Vector(0,0);
    Vector desired = new Vector();
    Vector steering = new Vector();
    Vector mouse = new Vector();
    Vector nearestPoint = new Vector();
    Vector totalForce = new Vector();

    Vector toTarget = new Vector();
    Vector toObstacle = new Vector();
    Vector lateralForce;

    public Slime(GamePanel gp, World world, Player player){
        super(gp,world);
        this.player = player;
    }
    @Override
    public void setAttributes() {
        try{
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/monsters/redslime.png"));
            shadowsheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/monsters/shadow.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setSpeed(100);
        setSize((int)Global.ORIGINAL_TILESIZE,(int)Global.ORIGINAL_TILESIZE);
        sprite = new Sprite(spriteSheet,width,height,0.5f);
        shadow = new Sprite(shadowsheet,width,height,1f);
        setAnchor(16,27);
        collider.setBounds(this,9,22,14,10);
        collider.collision = true;
        setPositionByAnchor(new Vector(200,200));
        setFeetLine(height);
        sprite.moving = true;
    }

    @Override
    public void update(double deltaTime) {
        if(gp.cursorPoint != null) {
            mouse.setPosition(gp.world.camera.relativeWorld(mouse.setPosition(gp.cursorPoint.getX(),gp.cursorPoint.getY())));
            target.setPosition(mouse);
            target.setPosition(getInAnchorOffset(target));
        }
        else {
            target.setPosition(player.position);
        }

        nextPosition.setPosition(position);

        totalForce.setPosition(0,0);

        totalForce.add(seek(deltaTime).multiply(0.8));
        totalForce.add(avoid(deltaTime).multiply(0.2));

        steering = totalForce.get().sub(velocity).multiply(2);

        velocity.add(steering.multiply(deltaTime));

        nextPosition.add(velocity.get().multiply(deltaTime * 2));


        boolean canMoveX = true;
        boolean canMoveY = true;

        for (Element element : world.elements) {
            if (element != this && element.collider.collision && collider.predictXCollision(element.collider, nextPosition.x)) {
                canMoveX = false;
            }
            if (element != this && element.collider.collision && collider.predictYCollision(element.collider, nextPosition.y)) {
                canMoveY = false;
            }
        }

        if (canMoveX)
            position.setX(nextPosition.x);
        if (canMoveY)
            position.setY(nextPosition.y);

        collider.update();
        sprite.update(deltaTime);
    }

    public Vector seek(double deltaTime){
        double distance = position.getDistance(target);

        toTarget = target.get().sub(position);
        desired = target.get().sub(position).normalize().multiply(speed);

        return desired;
    }

    public Vector avoid(double deltaTime){
        for(Element element : world.elements){
            if(element != this && element != player && element.collider.collision){
                double minX = element.collider.colliderBox.getMinX();
                double minY = element.collider.colliderBox.getMinY();
                double maxX = element.collider.colliderBox.getMaxX();
                double maxY = element.collider.colliderBox.getMaxY();

                Vector anchorPos = new Vector(getAnchorX(),getAnchorY());

                nearestPoint = new Vector(MathUtils.clamp(minX,maxX,getAnchorX()),MathUtils.clamp(minY,maxY,getAnchorY()));

                toObstacle = nearestPoint.get().sub(anchorPos);

                double distance = anchorPos.getDistance(nearestPoint);

                if(distance < 200) {
                    if(lateralForce == null) {
                        lateralForce = new Vector();
                        if (toTarget.cross(toObstacle) >= 0) {
                            lateralForce.setPosition(-toObstacle.y, toObstacle.x);
                        } else {
                            lateralForce.setPosition(toObstacle.y, -toObstacle.x);
                        }
                    }

                    return lateralForce.get().normalize().multiply(speed*1200/distance);
                }
                else {
                    lateralForce = null;
                }
            }
        }

        return Vector.ZERO;
    }

    @Override
    public void render(Graphics2D g2d) {
        shadow.render(g2d,(int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y+ 1*Global.SCALE),width,height);
        sprite.render(g2d,(int)(position.getX() - world.camera.x) ,(int)(position.getY() - world.camera.y),width,height);

        if(gp.world.showElementsAnchor) {
            renderAnchor(g2d);
            collider.render(g2d);
            renderFeetLine(g2d);
        }
        g2d.setColor(Color.red);
        g2d.fillOval(world.camera.relativeX(nearestPoint.x),world.camera.relativeY(nearestPoint.y),5,5);
        g2d.setColor(Color.white);
        g2d.fillRect(world.camera.relativeX(direction.x),world.camera.relativeY(direction.y),50,50);

        g2d.setColor(Color.BLUE);
        g2d.drawLine(world.camera.relativeX(getAnchorX()), world.camera.relativeY(getAnchorY()),
                world.camera.relativeX(position.x + velocity.x * 10), (int) (position.y + velocity.y * 10));

        g2d.setColor(Color.GREEN);
        g2d.drawLine(world.camera.relativeX(getAnchorX()), world.camera.relativeY(getAnchorY()),
                world.camera.relativeX(position.x + desired.x * 10), world.camera.relativeY(position.y + desired.y * 10));
    }
}
