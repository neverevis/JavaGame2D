package elements.entities;

import elements.Element;
import elements.states.Direction;
import elements.states.PlayerState;
import game.GamePanel;
import game.KeyHandler;
import server.Client;
import utilities.Global;
import utilities.Sound;
import utilities.Sprite;
import utilities.Vector;
import world.World;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity {

    Vector velocity = new Vector(0,0);
    Vector directionVector = new Vector(0,0);
    Client client;

    double acceleration = 1100*Global.SCALE;
    double deceleration = 10;

    boolean attacking = false;
    boolean coolDown = false;
    double slashTime = 0.5; //segundos
    double coolDownTime = 0.05;
    double elapsedTime;
    public double health = 100;
    public double maxHealth = 100;
    public double dealt = health;

    boolean invulnerable = true;

    PlayerState playerState = PlayerState.IDLE;
    KeyHandler key;
    public Direction direction;
    BufferedImage shadowsheet;
    BufferedImage attackSheet;
    Sprite attack;
    Sprite shadow;
    double knockBackForce = 500*Global.SCALE;
    boolean serverOriented = false;

    Sound sound = new Sound();

    public Player(GamePanel gp, World world, Client client, boolean serverOriented){
        super(gp, world);
        this.key = gp.kh;
        this.serverOriented = serverOriented;
        this.client = client;

        try{
            if(serverOriented)
                spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/players/playersheet2.png"));
            else
                spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/players/playersheet.png"));
            shadowsheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/monsters/shadow.png"));
            attackSheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/players/attack.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setSize((int)Global.ORIGINAL_TILESIZE,(int)Global.ORIGINAL_TILESIZE);
        setPositionByAnchor(new Vector(500,700));
        setSpeed(100);
        sprite = new Sprite(spriteSheet,width,height,1f);
        attack = new Sprite(attackSheet, 160, 160, 0.67f);
        collider.setBounds(this,11,26,10,6);
        collider.collision = true;
        setAnchor(16,18);
        shadow = new Sprite(shadowsheet,32,32,1f);
    }

    @Override
    public void setAttributes() {
        //carregando o spritesheet
        setFeetLine(32);
    }

    @Override
    public void update(double deltaTime) {
        if(!serverOriented) {
            dealt += (health - dealt) * 0.06;

            if (!attacking) {
                updateMovement(deltaTime);
            }

            if (gp.kh.toggleAnchorDisplay) {
                world.showElementsAnchor = true;
            } else {
                world.showElementsAnchor = false;
            }

            if (playerState == PlayerState.IDLE) {
                sprite.moving = false;
            } else if (playerState == PlayerState.MOVING) {
                sprite.moving = true;

            }
        }

        sprite.update(deltaTime);
        attack.update(deltaTime);
        collider.update();
    }

    public void dealDamage(Vector originPosition){
        if(!invulnerable) {
            sound.setSound(0);
            sound.play();
            Vector knockback = collider.center.sub(originPosition).normalize();

            sprite.toggleDamageState();
            knockback.multiply(knockBackForce);
            velocity.set(knockback);

            if (health > 0) {
                health -= 10;
            } else {
                this.world.pause = true;
            }
        }

    }

    @Override
    public void render(Graphics2D g2d){
        shadow.render(g2d,(int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y+ 2*Global.SCALE),width,height);
        if(direction == Direction.RIGHT || direction == Direction.UP) {
            renderSwordAndSlash(g2d);
        }
        sprite.render(g2d, (int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y),width,height);
        if(gp.world.showElementsAnchor) {
            renderAnchor(g2d);
            collider.render(g2d);
            renderFeetLine(g2d);
        }

        if(direction == Direction.LEFT || direction == Direction.DOWN) {
            renderSwordAndSlash(g2d);
        }
    }

    public void updateMovement(double dt){
        directionVector.reset();

        if (key.upKey) {
            directionVector.y --;
            setPlayerDirection(Direction.UP);
        }
        if (key.downKey) {
            directionVector.y ++;
            setPlayerDirection(Direction.DOWN);
        }
        if (key.leftKey) {
            directionVector.x --;
            setPlayerDirection(Direction.LEFT);
        }

        if (key.rightKey) {
            directionVector.x ++;
            setPlayerDirection(Direction.RIGHT);
        }

        if(!key.leftKey && !key.upKey && !key.downKey && !key.rightKey){
            playerState = PlayerState.IDLE;
            elapsedTime += dt;

            velocity.multiply(0.95);

        }else{
            playerState = PlayerState.MOVING;
            velocity.add(directionVector.normalize().multiply(acceleration * dt));

            if(velocity.length() > speed)
                velocity.normalize().multiply(speed);
        }


        nextPosition.set(position);
        nextPosition.add(velocity.get().multiply(dt));


        boolean canMoveX = true;
        boolean canMoveY = true;

        for(Element element : world.elements){
            if(element != this && element.collider.collision && collider.predictXCollision(element.collider,nextPosition.x)) {
                canMoveX = false;
            }
            if(element != this && element.collider.collision && collider.predictYCollision(element.collider,nextPosition.y)) {
                canMoveY = false;
            }
        }
        if(canMoveX)
            position.setX(nextPosition.x);
        if(canMoveY)
           position.setY(nextPosition.y);
    }

    public void setAttackDir(){
        //diferença dx e dy do centro da tela em relação ao clique
        double clickDx = (double)Global.SCREENWIDTH/2 - gp.mI.x;
        double clickDy = (double)Global.SCREENHEIGHT/2 - gp.mI.y;

        //angulo em radianos
        double angle = Math.atan2(clickDy,clickDx);
        //converter para graus
        angle = Math.toDegrees(angle);

        if(angle >= 45 && angle <= 135){
            setPlayerDirection(Direction.UP);
        }
        else if((angle >= 135 && angle <= 180) || (angle >= -180 && angle <= -135)){
            setPlayerDirection(Direction.RIGHT);
        }
        else if(angle >= -135 && angle <= -45){
            setPlayerDirection(Direction.DOWN);
        }
        else if(angle >= -45 && angle <= 45){
            setPlayerDirection(Direction.LEFT);
        }
    }

    private void renderSwordAndSlash(Graphics2D g2d){
        if (playerState == PlayerState.ATTACKING)
            attack.render(g2d, (int)(position.getX() - world.camera.x - 64*Global.SCALE), (int)(position.getY() - world.camera.y - 64*Global.SCALE),160,160);
    }

    private void setPlayerDirection(Direction direction){
        sprite.setDirection(direction);
    }
}
