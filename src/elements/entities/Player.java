package elements.entities;

import game.GamePanel;
import utilities.Global;
import utilities.Sprite;
import world.World;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity {

    /*=============== ATRIBUTOS ===============*/
    double nextX;
    double nextY;

    double swordX;
    double swordY;
    double swordTargetX;
    double swordTargetY;
    double swordSmoothing = 0.2;

    boolean attacking = false;
    boolean coolDown = false;
    double slashTime = 0.5; //segundos
    double coolDownTime = 0.05;

    BufferedImage slashSheet;
    BufferedImage shadowsheet;
    BufferedImage attackSheet;
    Sprite attack;
    Sprite shadow;
    Sprite slash;
    double swordOffset = Global.TILESIZE/2.5;

    int frameCounter;

    public Player(GamePanel gp, World world){
        super(gp, world);
        swordTargetX = x - swordOffset;
        swordTargetY = y;
    }

    public void attackToward(){

    }

    @Override
    public void setAttributes() {
        //carregando o spritesheet
        try{
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/entities/players/playersheet.png"));
            shadowsheet = ImageIO.read(getClass().getResourceAsStream("/entities/monsters/shadow.png"));
            attackSheet = ImageIO.read(getClass().getResourceAsStream("/entities/players/attack.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setSize((int)Global.ORIGINAL_TILESIZE,(int)Global.ORIGINAL_TILESIZE);
        setPositionByAnchor(500,700);
        setSpeed(100);
        sprite = new Sprite(spriteSheet,width,height,1f);
        attack = new Sprite(attackSheet, 160, 160, 0.67f);
        setAnchor(16,18);
        shadow = new Sprite(shadowsheet,32,32,1f);
    }

    @Override
    public void update(double deltaTime) {
        updateAttack();
        if(!attacking){
            updateMovement(deltaTime);
        }

        if(gp.kh.toggleAnchorDisplay){
            world.showElementsAnchor = true;
        }
        else {
            world.showElementsAnchor = false;
        }

        sprite.update(deltaTime);
        attack.update(deltaTime);
    }

    @Override
    public void render(Graphics2D g2d){
        shadow.render(g2d,(int)(x - world.camera.x), (int)(y - world.camera.y+ 2*Global.SCALE),width,height);
        if(sprite.orientation == Global.RIGHT || sprite.orientation == Global.UP) {
            renderSwordAndSlash(g2d);
        }

        if(!attacking)
            sprite.render(g2d, (int)(x - world.camera.x), (int)(y - world.camera.y),width,height);
        if(gp.world.showElementsAnchor)
            renderAnchor(g2d);

        if(sprite.orientation == Global.LEFT || sprite.orientation == Global.DOWN) {
            renderSwordAndSlash(g2d);
        }
    }

    public void updateAttack(){

        if(gp.mI.mouseClicked && !attacking && !coolDown) {
            attack.setFrame(1);
            attacking = true;
            attack.moving = true;
            setAttackDir();
        }

        if(attacking) {
            frameCounter++;
            if(frameCounter >= 10){
                x += 3;
            }
        }

        if(attacking && frameCounter >= Global.FPS*slashTime){
            frameCounter = 0;
            attacking = false;
            coolDown = true;
            swordX = x;
            swordY = y;
        }

        if(coolDown){
            frameCounter++;
        }

        if(coolDown && frameCounter >= Global.FPS*coolDownTime){
            frameCounter = 0;
            coolDown = false;
        }
    }

    public void updateMovement(double deltaTime){
        nextX = getAnchorX();
        nextY = getAnchorY();

        realSpeed = speed * deltaTime;

        if (gp.kh.upKey && (gp.kh.leftKey || gp.kh.rightKey) || gp.kh.downKey && (gp.kh.leftKey || gp.kh.rightKey)) {
            realSpeed = (realSpeed / Math.sqrt(2));
        }

        if (gp.kh.upKey) {
            nextY -= realSpeed;
            setPlayerOrientation(Global.UP);
            sprite.moving = true;
        }
        if (gp.kh.downKey) {
            nextY += realSpeed; //player
            setPlayerOrientation(Global.DOWN);
            sprite.moving = true;
        }
        if (gp.kh.leftKey) {
            nextX -= realSpeed; //player
            setPlayerOrientation(Global.LEFT);
            sprite.moving = true;
        }

        if (gp.kh.rightKey) {
            nextX += realSpeed;
            setPlayerOrientation(Global.RIGHT);
            sprite.moving = true;

        }

        if(!gp.kh.leftKey && !gp.kh.upKey && !gp.kh.downKey && !gp.kh.rightKey){
            sprite.moving = false;
        }

        // movimentação do player validada
        if (nextX <= world.width && nextX >= 0 && nextY <= world.height && nextY >= 0)
            setPositionByAnchor(nextX,nextY);

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
            setPlayerOrientation(Global.UP);
        }
        else if((angle >= 135 && angle <= 180) || (angle >= -180 && angle <= -135)){
            setPlayerOrientation(Global.RIGHT);
        }
        else if(angle >= -135 && angle <= -45){
            setPlayerOrientation(Global.DOWN);
        }
        else if(angle >= -45 && angle <= 45){
            setPlayerOrientation(Global.LEFT);
        }
    }

    private void renderSwordAndSlash(Graphics2D g2d){
        if (attacking)
            attack.render(g2d, (int)(x - world.camera.x - 64*Global.SCALE), (int)(y - world.camera.y - 64*Global.SCALE),160,160);
    }

    private void setPlayerOrientation(int orientation){
        sprite.setOrientation(orientation);
    }
}
