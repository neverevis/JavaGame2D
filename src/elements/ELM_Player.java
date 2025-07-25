package elements;

import core.Key;
import graphics.*;
import math.Vector;
import physics.Attack;
import physics.Collider;
import utilities.Sound;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ELM_Player extends Element {
    public int id;
    public String nickname = "player";

    boolean isLocal;

    Vector nextPos = new Vector();
    Vector direction = new Vector();
    Vector movement = new Vector();
    Vector velocity = new Vector();

    BufferedImage shadow;
    Sprite sprite;
    Animator animation;

    Animator idleUp;
    Animator idleDown;
    Animator idleLeft;
    Animator idleRight;
    Animator runUp;
    Animator runDown;
    Animator runLeft;
    Animator runRight;
    Animator attackDown;
    Animator attackUp;
    public Collider collider;
    public World world;
    ELM_Emmiter dust;
    ELM_Emmiter dash;
    Sound sound = new Sound();

    Attack attack;

    public int state;
    public int facing;
    double acceleration = 800;
    double maxSpeed = 100;
    public double maxHealth = 100;
    public double health = maxHealth;

    boolean invincibility = false;
    boolean ready = false;
    boolean hitSoundPlayed = false;

    public final int IDLE = 0;
    public final int RUNNING = 1;
    public final int ATTACKING = 2;
    public final int TAKING_DAMAGE = 3;

    public final int UP = 0;
    public final int DOWN = 1;
    public final int LEFT = 2;
    public final int RIGHT = 3;

    public ELM_Player(World world,boolean isLocal){
        this.world = world;
        this.isLocal = isLocal;

        attack = new Attack(this);

        dust = new ELM_Emmiter(this.world,"/resources/particles/dust.png",this.pos,3,0.7,0.2,0,16,27,1.2,0,-10,0.2);
        dash = new ELM_Emmiter(this.world,"/resources/particles/dash.png",this.pos,8,1,1,0,0,100,3,0,0,0);
        dash.active = false;
        sprite = new Sprite("/resources/elements/players/spritesheet.png",96,96);
        shadow = ImageManager.load("/resources/elements/players/shadow.png");

        runDown = new Animator(sprite,0,12,23,0.8);
        runUp = new Animator(sprite,0,0,11,0.8);
        runLeft = new Animator(sprite,0,24,35,0.8);
        runRight = new Animator(sprite,0,36,47,0.8);
        idleDown = new Animator(sprite,0,48,60,1);
        idleUp = new Animator(sprite,0,61,73,1);
        idleLeft = new Animator(sprite,0,87,99,1);
        idleRight = new Animator(sprite,0,74,86,1);
        attackDown = new Animator(sprite,0,100,109,0.55);
        attackUp = new Animator(sprite,0,110,119,0.55);

        collider = new Collider(pos,10,8,-5,8,false);

        world.collSys.register(collider);
    }

    @Override
    public void update(double dt) {
        if(isLocal) {
            handleMovement(dt);
            applyVelocity(dt);
        }

        if(facing == DOWN){
            if(state == RUNNING){
                animation = runDown;
                dust.active = true;
            }else if(state == IDLE){
                animation = idleDown;
                dust.active = false;
            }else if(state == ATTACKING){
                animation = attackDown;
                dust.active = false;
            }
        }
        else if(facing == UP){
            if(state == RUNNING){
                animation = runUp;
                dust.active = true;
            }else if(state == IDLE){
                animation = idleUp;
                dust.active = false;
            }else if(state == ATTACKING){
                animation = attackUp;
                dust.active = false;
            }
        }
        else if(facing == LEFT){
            if(state == RUNNING){
                animation = runLeft;
                dust.active = true;
            }else if(state == IDLE){
                animation = idleLeft;
                dust.active = false;
            }
        }
        else if(facing == RIGHT){
            if(state == RUNNING){
                animation = runRight;
                dust.active = true;
            }else if(state == IDLE){
                animation = idleRight;
                dust.active = false;
            }
        }

        if (animation != null)
            animation.update(dt);

        if(state == TAKING_DAMAGE){
            if(!hitSoundPlayed) {
                sound.setSound(0);
                sound.play();
                hitSoundPlayed = true;
            }
        }else{
            hitSoundPlayed = false;
        }

        dust.update(dt);
        dash.update(dt);
        attack.update(dt);
    }


    @Override
    public void render(GraphicsFX gfx){
        gfx.draw(shadow,pos.x,pos.y+2);
        gfx.draw(sprite,pos.x,pos.y);

        gfx.save();
        gfx.setTextSize(2.8f);

        gfx.setColor(Color.BLACK);
        gfx.opacity(0.2f);
        gfx.fillRect(pos.x, pos.y - 20,gfx.stringWidth(nickname) + 3,6,2,2);
        gfx.opacity(0.8f);

        gfx.setColor(Color.WHITE);
        gfx.draw(nickname,pos.x, pos.y - 20);

        gfx.restore();
    }

    void handleMovement(double dt){
        if (state != ATTACKING && state != TAKING_DAMAGE) {
            direction.reset();

            if (Key.W) {
                direction.y--;
                state = RUNNING;
                facing = UP;
            }
            if (Key.S) {
                direction.y++;
                state = RUNNING;
                facing = DOWN;
            }
            if (Key.A) {
                direction.x--;
                state = RUNNING;
                facing = LEFT;
            }
            if (Key.D) {
                direction.x++;
                state = RUNNING;
                facing = RIGHT;
            }

            if (!Key.W && !Key.A && !Key.S && !Key.D) {
                decelerate(movement,dt);
                state = IDLE;
            }

            direction.normalize();
            movement.add(direction.multiply(acceleration * dt));
            movement.clamp(maxSpeed);
        }
    }

    void applyVelocity(double dt){
        velocity.set(movement);

        nextPos.set(pos);
        nextPos.add(velocity.multiply(dt));

        if(!world.collSys.predictCollision_x(collider,nextPos.x)){

            pos.x = nextPos.x;
        }
        if(!world.collSys.predictCollision_y(collider,nextPos.y)){
            pos.y = nextPos.y;
        }
    }

    public void decelerate(Vector force, double dt){
        force.multiply(Math.pow(0.03, dt * 2));
    }

    @Override
    public double getZIndex() {
        return pos.y + 16;
    }
}
