package elements.entities;

import elements.Element;
import elements.states.Direction;
import elements.states.EnemyState;
import game.GamePanel;
import utilities.Global;
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
    Vector target = new Vector(0,0);
    Vector home;
    double distance;
    double sightRange = 4*Global.TILESIZE;
    boolean isOnLineOfSight = false;
    boolean isOnRange = false;
    double elapsedTime = 0;
    double patrolIterationTime = 3.0;
    double patrolRange = 5*Global.TILESIZE;
    Random random = new Random(System.nanoTime());
    EnemyState slimeState = EnemyState.IDLE;
    BufferedImage shadowsheet;
    Sprite shadow;

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
        setSpeed(65);
        setHome( 2130,1080);
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
        nextPosition.setPosition(position);

        distance = position.getDistance(player.position);

        if(distance < sightRange)
            isOnRange = true;
        else
            isOnRange = false;

        if(isOnRange) {
            isOnLineOfSight = true;

            for (Element element : world.elements) {
                if (element != this && element != player && element.collider.collision)
                    if (element.collider.colliderBox.intersectsLine(getAnchorX(), getAnchorY(), player.getAnchorX(), player.getAnchorY()))
                        isOnLineOfSight = false;
            }
        }

        if (isOnRange && isOnLineOfSight) {
            target.setPosition(player.getAnchorX(),player.getAnchorY());
        }
        else{
            slimeState = EnemyState.IDLE;
        }

        if(slimeState == EnemyState.IDLE){
            sprite.setAnimationSpeed(1f);
            elapsedTime += deltaTime;
            if(elapsedTime >= patrolIterationTime){
                target.setPosition(new Vector(home.x - patrolRange + random.nextDouble(patrolRange),home.y - patrolRange + random.nextDouble(patrolRange)));
                elapsedTime = 0;
            }
        }else{
            elapsedTime = 0;
            sprite.setAnimationSpeed(0.5f);
        }

        realSpeed = speed * deltaTime;
        nextPosition.setPosition(position);
        nextPosition.applyDirection(target,realSpeed);

        if(target.x > position.getX())
            sprite.setDirection(Direction.DOWN);
        else
            sprite.setDirection(Direction.UP);


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

    @Override
    public void render(Graphics2D g2d) {
        shadow.render(g2d,(int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y+ 1*Global.SCALE),width,height);
        sprite.render(g2d,(int)(position.getX() - world.camera.x) ,(int)(position.getY() - world.camera.y),width,height);

        if(gp.world.showElementsAnchor) {
            renderAnchor(g2d);
            collider.render(g2d);
            renderFeetLine(g2d);
            g2d.setColor(Color.orange);
            g2d.drawOval((int)(getAnchorX() - sightRange - world.camera.x),(int)(getAnchorY()  - sightRange - world.camera.y),(int)sightRange*2,(int)sightRange*2);
            if(isOnLineOfSight) {
                if(isOnRange)
                    g2d.setColor(Color.red);
                else
                    g2d.setColor(Color.white);
                g2d.drawLine((int) (getAnchorX() - world.camera.x), (int) (getAnchorY() - world.camera.y), (int) (target.x - world.camera.x), (int) (target.y - world.camera.y));
            }
        }
    }

    public void setHome(double x, double y){
        setPositionByAnchor(new Vector(x,y));
        home = new Vector(x,y);
    }
}
