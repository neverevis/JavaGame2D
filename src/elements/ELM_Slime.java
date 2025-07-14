package elements;

import core.G;
import graphics.Animator;
import graphics.Sprite;
import math.Vector;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;


public class ELM_Slime extends Element {


    Sprite sprite;
    Animator goingRight;
    Animator goingLeft;
    ELM_Player player;

    Random random = new Random(System.nanoTime());

    BufferedImage shadowsheet;

    boolean dummy;
    Sprite shadow;
    Vector direction = new Vector();
    Vector target = new Vector();
    Vector velocity = new Vector();
    Vector desired = new Vector();
    Vector steering = new Vector();
    Vector mouse = new Vector();
    Vector nearestPoint = new Vector();
    Random r = new Random();

    double maxVelocity = 90 * G.SCALE;
    double avoidForce = 3000;
    Line2D.Double avoidLine = new Line2D.Double();
    Vector v = new Vector();


    Vector avoid = new Vector();

    public ELM_Slime(ELM_Player player){
        this.player = player;
        sprite = new Sprite("/resources/elements/redslime.png",32,32);

        goingRight = new Animator(sprite,0,0,6,1);
        goingLeft = new Animator(sprite,1,0,6,1);
    }

    @Override
    public void update(double deltaTime) {
        /*rayCasts.update(velocity);
        if (gp.cursorPoint != null) {
            mouse.set(gp.activeWorld.camera.relativeWorld(mouse.set(gp.cursorPoint.getX(), gp.cursorPoint.getY())));
            target.set(mouse);
            target.set(getInAnchorOffset(target));
        } else {
            target.set(player.position);
        }

        nextPosition.set(position);
        steering.set(0, 0);
        steering.add(seek());
        steering.add(avoid());

        velocity.add(steering.multiply(deltaTime));
        if (velocity.length() > maxVelocity)
            velocity.normalize().multiply(maxVelocity);

        nextPosition.add(velocity.get().multiply(deltaTime));

        nextPositionValidation();

        collider.update();
        sprite.update(deltaTime);*/
    }

    @Override
    public void render(Graphics2D g){
        sprite.render(g,pos.x,pos.y);
    }

    @Override
    public double getZIndex() {
        return pos.y;
    }

    /*public Vector seek(){
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
    }*/

}