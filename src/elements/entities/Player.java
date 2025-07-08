package elements.entities;

import elements.Dust;
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

import java.awt.*;

public class Player extends Entity {
    public int id;

    Vector velocity = new Vector(0,0);
    Vector directionVector = new Vector(0,0);

    double acceleration = 1100*Global.SCALE;
    boolean attacking = false;
    boolean coolDown = false;
    double slashTime = 0.5; //segundos
    double coolDownTime = 0.05;
    double elapsedTime;
    public double health = 100;
    public double maxHealth = 100;
    public double dealt = health;

    boolean invulnerable = true;

    public PlayerState playerState = PlayerState.IDLE;
    KeyHandler key;
    public Direction direction;
    Sprite attack;
    Sprite shadow;
    double knockBackForce = 300*Global.SCALE;
    boolean serverOriented = false;
    Dust dust = new Dust(gp,gp.activeWorld,this);

    Sound sound = new Sound();

    public Player(GamePanel gp, World world, boolean serverOriented){
        super(gp, world);
        this.key = gp.kh;
        this.serverOriented = serverOriented;

        setSize((int)Global.ORIGINAL_TILESIZE,(int)Global.ORIGINAL_TILESIZE);
        setPositionByAnchor(new Vector(500,700));
        setSpeed(100);
        sprite = new Sprite("/resources/entities/players/playersheet.png",width,height,1f);
        attack = new Sprite("/resources/entities/players/attack.png", 160, 160, 0.67f);
        shadow = new Sprite("/resources/entities/monsters/shadow.png",32,32,1f);
        collider.setBounds(this,11,26,10,6);
        collider.collision = true;
        world.collisionSystem.register(collider);
        setAnchor(16,18);
    }

    @Override
    public void setAttributes() {
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

        dust.update(deltaTime);
        sprite.update(deltaTime);
        attack.update(deltaTime);
    }

    public void dealDamage(Vector originPosition){
        if(!invulnerable) {
            sound.setSound(0);
            sound.play();
            Vector knockback = collider.center.sub(originPosition).normalize();
            world.camera.shaking = true;

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
        shadow.render(g2d,(int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y+ 2*Global.SCALE));
        dust.render(g2d);
        if(direction == Direction.RIGHT || direction == Direction.UP) {
            renderSwordAndSlash(g2d);
        }
        sprite.render(g2d, (int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y));
        if(gp.activeWorld.showElementsAnchor) {
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

        if(!world.collisionSystem.willCollideX(collider,nextPosition.x))
            position.setX(nextPosition.x);
        else{
            for(Player p : gp.activeWorld.connectedPlayers){
                if(this.collider.predictXCollision(p.collider, nextPosition.x)){
                    Vector knockback = collider.center.sub(p.collider.center).normalize();
                    knockback.multiply(20);
                    velocity.add(knockback);
                }
            }
        }
        if(!world.collisionSystem.willCollideY(collider,nextPosition.y))
            position.setY(nextPosition.y);
        else{
            for(Player p : gp.activeWorld.connectedPlayers){
                if(this.collider.predictYCollision(p.collider, nextPosition.y));{
                    Vector knockback = collider.center.sub(p.collider.center).normalize();
                    knockback.multiply(20);
                    velocity.add(knockback);
                }
            }
        }
    }

    public void setAttackDir(){
        //diferença dx e dy do centro da tela em relação ao clique
        double clickDx = (double)Global.SCREENWIDTH/2 - gp.mouseInput.x;
        double clickDy = (double)Global.SCREENHEIGHT/2 - gp.mouseInput.y;

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
            attack.render(g2d, (int)(position.getX() - world.camera.x - 64*Global.SCALE), (int)(position.getY() - world.camera.y - 64*Global.SCALE));
    }

    private void setPlayerDirection(Direction direction){
        sprite.setDirection(direction);
    }
}
