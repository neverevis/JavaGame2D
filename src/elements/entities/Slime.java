package elements.entities;

import game.GamePanel;
import utilities.Global;
import utilities.Sprite;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Slime extends Entity{

    Player player;
    double nextX;
    double nextY;
    double distance;
    double dx;
    double dy;
    double dirX;
    double dirY;
    BufferedImage shadowsheet;
    Sprite shadow;
    int frameCounter;
    boolean jumping = false;

    int knockBackTimer = 0;

    public Slime(GamePanel gp, World world, Player player){
        super(gp,world);
        this.player = player;
    }
    @Override
    public void setAttributes() {
        try{
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/entities/monsters/slime_placeholder.png"));
            shadowsheet = ImageIO.read(getClass().getResourceAsStream("/entities/monsters/shadow.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setSpeed(90);
        setSize((int)Global.ORIGINAL_TILESIZE,(int)Global.ORIGINAL_TILESIZE);
        sprite = new Sprite(spriteSheet,width,height,1f);
        shadow = new Sprite(shadowsheet,width,height,1f);
        setAnchor(16,16);
        setPositionByAnchor(0, 0);
    }

    @Override
    public void update(double deltaTime) {
        if(player.attacking && knockBackTimer <= 0) {
            sprite.toggleDamageState();
            knockBackTimer = (int)(0.5*Global.FPS);
        }
        frameCounter++;

        //intervalo entre animações
        if(frameCounter >= 1 && !jumping) {
            jumping = true;
            frameCounter = 0;
        }
        if(frameCounter >= Global.FPS - 6 && jumping) {
            jumping = false;
            frameCounter = 0;
        }


        //delay de movimento
        if(jumping && frameCounter > 10 && frameCounter < 35 && knockBackTimer <= 0) {
            nextX = getAnchorX();
            nextY = getAnchorY();

            realSpeed = speed * deltaTime;

            dx = player.getAnchorX() - getAnchorX();
            dy = player.getAnchorY() - getAnchorY();
            distance = (float) Math.sqrt(dx * dx + dy * dy);

            dirX = dx / distance;
            dirY = dy / distance;

            nextX += dirX * speed * deltaTime;
            nextY += dirY * speed * deltaTime;
        }

        if(knockBackTimer > 0){
            nextX += -dirX * 0.2*Global.TILESIZE*knockBackTimer * deltaTime;
            nextY += -dirY * 0.2*Global.TILESIZE*knockBackTimer * deltaTime;
            knockBackTimer--;
        }

        if (nextX <= world.width && nextX >= 0 && nextY <= world.height && nextY >= 0)
            setPositionByAnchor(nextX,nextY);
    }

    @Override
    public void render(Graphics2D g2d) {
        shadow.render(g2d,(int)(x - world.camera.x), (int)(y - world.camera.y+ 1*Global.SCALE));
        if(jumping)
            sprite.moving = true;
        else
            sprite.moving = false;
        sprite.render(g2d,(int)(x - world.camera.x) ,(int)(y - world.camera.y));

        if(gp.world.showElementsAnchor)
            renderAnchor(g2d);
    }
}
