package elements;

import core.Key;
import graphics.Animator;
import graphics.GraphicsFX;
import graphics.ImageManager;
import graphics.Sprite;
import math.Vector;
import physics.Collider;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ELM_Player extends Element {
    public int id;
    public String nickname = "player";
    double nickLabelWidth;

    boolean isLocal;

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
    public Collider collider;
    World world;

    public int state;
    double acceleration = 800;
    double maxSpeed = 100;

    public ELM_Player(World world,boolean isLocal){
        this.world = world;
        this.isLocal = isLocal;

        sprite = new Sprite("/resources/elements/players/spritesheet.png",96,96);
        shadow = ImageManager.load("/resources/elements/players/shadow.png");

        runDown = new Animator(sprite,0,12,23,0.8);
        runUp = new Animator(sprite,0,0,11,0.8);
        runLeft = new Animator(sprite,0,24,35,0.8);
        runRight = new Animator(sprite,0,36,47,0.8);
        idle = new Animator(sprite,0,48,60,1);

        collider = new Collider(pos,10,8,11,24);

        world.collSys.register(collider);
    }

    @Override
    public void update(double dt) {
        if(isLocal) {
            direction.reset();

            if (Key.W) {
                direction.y--;
                state = P.UP;
            }
            if (Key.S) {
                direction.y++;
                state = P.DOWN;
            }
            if (Key.A) {
                direction.x--;
                state = P.LEFT;
            }
            if (Key.D) {
                direction.x++;
                state = P.RIGHT;
            }

            if (!Key.W && !Key.A && !Key.S && !Key.D) {
                state = P.IDLE;
                velocity.multiply(Math.pow(0.03, dt * 2));
            }

            direction.normalize();
            velocity.add(direction.multiply(acceleration * dt));
            velocity.clamp(maxSpeed);

            pos.add(velocity.copy().multiply(dt));
        }

        if(state == P.DOWN){
            animation = runDown;
        }
        else if(state == P.UP){
            animation = runUp;
        }
        else if(state == P.LEFT){
            animation = runLeft;
        }
        else if(state == P.RIGHT){
            animation = runRight;
        }
        else if(state == P.IDLE){
            animation = idle;
        }

        if (animation != null)
            animation.update(dt);
    }


    @Override
    public void render(GraphicsFX gfx){
        gfx.draw(shadow,pos.x,pos.y+2);
        gfx.draw(sprite,pos.x-32,pos.y-32);

        gfx.save();
        gfx.setTextSize(2.8f);

        gfx.setColor(Color.BLACK);
        gfx.opacity(0.1f);
        gfx.fillRect(pos.x + 14.5 - gfx.stringWidth(nickname)/2,pos.y - 8,gfx.stringWidth(nickname) + 3,6,2,2);
        gfx.opacity(0.8f);

        gfx.setColor(Color.WHITE);
        gfx.draw(nickname,pos.x + 16 - gfx.stringWidth(nickname)/2,pos.y - 3.5);

        gfx.restore();
    }

    @Override
    public double getZIndex() {
        return pos.y;
    }
}
