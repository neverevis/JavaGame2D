package elements.entities;

import elements.Element;
import elements.states.Direction;
import elements.states.PlayerState;
import game.GamePanel;
import game.KeyHandler;
import utilities.Global;
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

    double acceleration = 0.5;
    double deceleration = 10;

    boolean attacking = false;
    boolean coolDown = false;
    double slashTime = 0.5; //segundos
    double coolDownTime = 0.05;
    double elapsedTime;

    PlayerState playerState = PlayerState.IDLE;
    KeyHandler key;
    Direction direction;
    BufferedImage shadowsheet;
    BufferedImage attackSheet;
    Sprite attack;
    Sprite shadow;

    public Player(GamePanel gp, World world){
        super(gp, world);
        this.key = gp.kh;
    }

    @Override
    public void setAttributes() {
        //carregando o spritesheet
        try{
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/players/playersheet.png"));
            shadowsheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/monsters/shadow.png"));
            attackSheet = ImageIO.read(getClass().getResourceAsStream("/resources/entities/players/attack.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setSize((int)Global.ORIGINAL_TILESIZE,(int)Global.ORIGINAL_TILESIZE);
        setPositionByAnchor(new Vector(500,700));
        setSpeed(0.9);
        sprite = new Sprite(spriteSheet,width,height,1f);
        attack = new Sprite(attackSheet, 160, 160, 0.67f);
        collider.setBounds(this,11,26,10,6);
        collider.collision = true;
        setAnchor(16,18);
        shadow = new Sprite(shadowsheet,32,32,1f);
        setFeetLine(32);
    }

    @Override
    public void update(double deltaTime) {
        updateAttack(deltaTime);
        if(!attacking){
            updateMovement(deltaTime);
        }

        if(gp.kh.toggleAnchorDisplay){
            world.showElementsAnchor = true;
        }
        else {
            world.showElementsAnchor = false;
        }

        if(playerState == PlayerState.IDLE){
            sprite.moving = false;
        }
        else if(playerState == PlayerState.MOVING){
            sprite.moving = true;
        }
        sprite.update(deltaTime);
        attack.update(deltaTime);
        collider.update();
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

    public void updateAttack(double deltaTime){

        if(gp.mI.mouseClicked && playerState != PlayerState.ATTACKING && !coolDown) {
            attack.setFrame(1);
            playerState = PlayerState.ATTACKING;
            attack.moving = true;
            setAttackDir();
        }

        if(attacking) {
            elapsedTime += deltaTime;
        }

        if(attacking && elapsedTime >= slashTime){
            elapsedTime = 0;
            playerState = PlayerState.IDLE;
            coolDown = true;
        }

        if(coolDown){
            elapsedTime += deltaTime;
        }

        if(coolDown && elapsedTime >= coolDownTime){
            elapsedTime = 0;
            coolDown = false;
        }
    }

    public void updateMovement(double deltaTime){
        directionVector.setPosition(0,0);

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
            elapsedTime += deltaTime;

        }else{
            playerState = PlayerState.MOVING;
            velocity.add(directionVector.normalize().multiply(acceleration));
            if (velocity.getDistance(new Vector(0,0)) > speed) {
                velocity = velocity.normalize().multiply(speed);
            }
        }

        velocity.multiply(0.95);

        nextPosition.setPosition(position);
        nextPosition.add(velocity);


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
