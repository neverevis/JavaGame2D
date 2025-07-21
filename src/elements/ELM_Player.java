package elements;

import core.Key;
import core.Mouse;
import graphics.*;
import math.Vector;
import physics.Collider;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ELM_Player extends Element {
    public int id;
    public String nickname = "player";

    boolean isLocal;

    Vector nextPos = new Vector();
    Vector direction = new Vector();
    Vector velocity = new Vector();

    BufferedImage shadow;
    Sprite sprite;
    Animator animation;

    Animator idle;
    Animator runUp;
    Animator runDown;
    Animator runLeft;
    Animator runRight;
    Animator attackDown;
    public Collider collider;
    World world;
    ELM_Emmiter dust;
    ELM_Emmiter dash;

    public int state;
    public int facing;
    double acceleration = 800;
    double maxSpeed = 100;

    int IDLE = 0;
    int RUNNING = 1;
    public int ATTACKING = 2;
    int TAKING_DAMAGE = 3;

    int UP = 0;
    int DOWN = 1;
    int LEFT = 2;
    int RIGHT = 3;

    public ELM_Player(World world,boolean isLocal){
        this.world = world;
        this.isLocal = isLocal;
        dust = new ELM_Emmiter(this.world,"/resources/particles/dust.png",this.pos,3,0.7,0.2,0,16,27,1.2,0,-10,0.2);
        dash = new ELM_Emmiter(this.world,"/resources/elements/torch/fireParticle.png",this.pos,8,3,1,0,0,700,0.5,0,0,0);

        sprite = new Sprite("/resources/elements/players/spritesheet.png",96,96);
        shadow = ImageManager.load("/resources/elements/players/shadow.png");

        runDown = new Animator(sprite,0,12,23,0.8);
        runUp = new Animator(sprite,0,0,11,0.8);
        runLeft = new Animator(sprite,0,24,35,0.8);
        runRight = new Animator(sprite,0,36,47,0.8);
        idle = new Animator(sprite,0,48,60,1);
        attackDown = new Animator(sprite,0,61,70,0.55);

        collider = new Collider(pos,10,8,-5,8);

        world.collSys.register(collider);
    }

    @Override
    public void update(double dt) {
        if(Key.SPACE){
            dash.active = true;
        }else{
            dash.active = false;
        }
        if(state == TAKING_DAMAGE)
            System.out.println("TOMANDO DANO!");
        if(isLocal) {
            direction.reset();

            if(state != ATTACKING) {
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
                    state = IDLE;
                    velocity.multiply(Math.pow(0.03, dt * 2));
                }else{
                    velocity.multiply(Math.pow(0.03, dt * 2));
                }
            }

            direction.normalize();
            velocity.add(direction.multiply(acceleration * dt));
            velocity.clamp(maxSpeed);

            //validação de posição
            collider.pos = nextPos;

            nextPos.set(pos);
            nextPos.add(velocity.x * dt,0);

            collider.update();
            if(!world.collSys.testCollision(collider))
                pos.set(nextPos.x,pos.y);

            nextPos.set(pos);
            nextPos.add(0,velocity.y * dt);

            collider.update();
            if(!world.collSys.testCollision(collider))
                pos.set(pos.x, nextPos.y);

            collider.pos = pos;
        }

        if(facing == DOWN){
            if(state == RUNNING){
                animation = runDown;
                dust.active = true;
            }else if(state == IDLE){
                animation = idle;
                dust.active = false;
            }
        }
        else if(facing == UP){
            if(state == RUNNING){
                animation = runUp;
                dust.active = true;
            }else if(state == IDLE){
                animation = idle;
                dust.active = false;
            }
        }
        else if(facing == LEFT){
            if(state == RUNNING){
                animation = runLeft;
                dust.active = true;
            }else if(state == IDLE){
                animation = idle;
                dust.active = false;
            }
        }
        else if(facing == RIGHT){
            if(state == RUNNING){
                animation = runRight;
                dust.active = true;
            }else if(state == IDLE){
                animation = idle;
                dust.active = false;
            }
        }

        if(state == ATTACKING){
            animation = attackDown;
            dust.active = false;
        }

        if (animation != null)
            animation.update(dt);

        dust.update(dt);
        dash.update(dt);
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

    @Override
    public double getZIndex() {
        return pos.y + 16;
    }
}
