package elements.entities;

import elements.Element;
import elements.states.Direction;
import elements.states.EnemyState;
import game.GamePanel;
import utilities.*;
import world.World;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Slime extends Entity{


    Player player;

    Random random = new Random(System.nanoTime());

    EnemyState slimeState = EnemyState.IDLE;

    BufferedImage shadowsheet;

    Sprite shadow;
    Vector direction = new Vector();
    Vector target = new Vector();
    Vector velocity = new Vector();
    Vector desired = new Vector();
    Vector steering = new Vector();
    Vector mouse = new Vector();
    Vector nearestPoint = new Vector();
    Random r = new Random();

    double maxVelocity = 90 * Global.SCALE;
    double avoidForce = 3000;
    RayCasts rayCasts;
    Line2D.Double avoidLine = new Line2D.Double();
    Vector v = new Vector();


    Vector avoid = new Vector();

    public Slime(GamePanel gp, World world, Player player){

        super(gp,world);

        this.player = player;
        sprite.setFrame(r.nextInt(5));
    }

    @Override

    public void setAttributes() {
        setSpeed(1.5);
        setSize((int)Global.ORIGINAL_TILESIZE,(int)Global.ORIGINAL_TILESIZE);
        sprite = new Sprite("/resources/entities/monsters/redslime.png",width,height,0.5f);
        shadow = new Sprite("/resources/entities/monsters/shadow.png",width,height,1f);
        setAnchor(16,27);
        collider.setBounds(this,9,22,14,10);
        collider.collision = true;
        world.collisionSystem.register(collider);
        setPositionByAnchor(new Vector(200,200));
        setFeetLine(height);
        sprite.moving = true;
        rayCasts = new RayCasts(this,0.4,32);
    }


    @Override

    public void update(double deltaTime) {
        rayCasts.update(velocity);
        if(gp.cursorPoint != null) {
            mouse.set(gp.activeWorld.camera.relativeWorld(mouse.set(gp.cursorPoint.getX(),gp.cursorPoint.getY())));
            target.set(mouse);
            target.set(getInAnchorOffset(target));
        }
        else {
            target.set(player.position);
        }

        nextPosition.set(position);
        steering.set(0,0);
        steering.add(seek());
        steering.add(avoid());

        velocity.add(steering.multiply(deltaTime));
        if(velocity.length() > maxVelocity)
            velocity.normalize().multiply(maxVelocity);

        nextPosition.add(velocity.get().multiply(deltaTime));

        nextPositionValidation();

        collider.update();
        sprite.update(deltaTime);
    }


    public Vector seek(){
        desired = target.get().sub(new Vector(position.x, position.y)).multiply(speed);
        return desired.sub(velocity);
    }


    public Vector avoid(){
        avoid.reset();
        for(Element element : world.elements){
            Collider c = element.collider;

            if(element != this && element != player && c.collision) {
                for (Line2D.Double ray : rayCasts.rays) {
                    if(c.colliderBox.intersectsLine(ray)){
                        v.set(getInAnchor(position)).add(velocity);
                        //linha
                        avoidLine.setLine(c.center.x,c.center.y,v.x,v.y);

                        avoid.add(v.get().add(velocity).sub(c.center).multiply(avoidForce));

                        return avoid;
                    }
                }
            }
        }


        return Vector.ZERO;

    }


    public void nextPositionValidation(){
        boolean canMoveX = true;
        boolean canMoveY = true;

        if(collider.predictCollision(player.collider, nextPosition))
            player.dealDamage(collider.center);

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
        if(velocity.x >= 0){
            sprite.setDirection(Direction.DOWN);
        }else{
            sprite.setDirection(Direction.UP);
        }
    }


    @Override
    public void render(Graphics2D g2d) {
        shadow.render(g2d,(int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y+ 1*Global.SCALE),width,height);
        sprite.render(g2d,(int)(position.getX() - world.camera.x) ,(int)(position.getY() - world.camera.y),width,height);
        if(gp.activeWorld.showElementsAnchor) {
            renderAnchor(g2d);
            collider.render(g2d);
            renderFeetLine(g2d);


            g2d.setColor(Color.red);
            g2d.fillOval(world.camera.relativeX(nearestPoint.x),world.camera.relativeY(nearestPoint.y),5,5);

            g2d.setColor(Color.white);
            rayCasts.render(g2d);

            g2d.setColor(Color.red);
            g2d.drawLine(world.camera.relativeX(avoidLine.x1),world.camera.relativeY(avoidLine.y1),world.camera.relativeX(avoidLine.x2),world.camera.relativeY(avoidLine.y2));

        }

    }

}