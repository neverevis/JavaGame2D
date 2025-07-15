package elements;

import core.Key;
import graphics.Animator;
import graphics.GraphicsFX;
import graphics.Sprite;
import math.Vector;
import physics.Collider;
import world.World;

import java.awt.*;

public class ELM_Player extends Element {
    public int id;
    public String nickname = "player";
    double nickLabelWidth;

    boolean isLocal;

    Vector direction = new Vector();
    Vector velocity = new Vector();

    Sprite sprite;
    Animator animation;

    Animator idle;
    Animator runUp;
    Animator runDown;
    Animator runLeft;
    Animator runRight;
    public Collider collider;
    World world;

    double acceleration = 800;
    double maxSpeed = 100;

    public ELM_Player(World world,boolean isLocal){
        this.world = world;
        this.isLocal = isLocal;

        sprite = new Sprite("/resources/elements/players/playersheet.png",32,32);

        runDown = new Animator(sprite,0,1,12,0.8);
        runUp = new Animator(sprite,1,1,12,0.8);
        runLeft = new Animator(sprite,2,1,12,0.8);
        runRight = new Animator(sprite,3,1,12,0.8);
        idle = new Animator(sprite,0,0,0,1);

        collider = new Collider(pos,10,8,11,24);

        world.collSys.register(collider);
    }

    @Override
    public void update(double dt) {
        if(isLocal) {
            direction.reset();

            if (Key.W) {
                direction.y--;
                animation = runUp;
            }
            if (Key.S) {
                direction.y++;
                animation = runDown;
            }
            if (Key.A) {
                direction.x--;
                animation = runLeft;
            }
            if (Key.D) {
                direction.x++;
                animation = runRight;
            }

            if (!Key.W && !Key.A && !Key.S && !Key.D) {
                animation = idle;
                velocity.multiply(Math.pow(0.03, dt * 2));
            }

            direction.normalize();
            velocity.add(direction.multiply(acceleration * dt));
            velocity.clamp(maxSpeed);

            pos.add(velocity.copy().multiply(dt));

            if (animation != null)
                animation.update(dt);
        }
    }


    @Override
    public void render(GraphicsFX gfx){
        gfx.draw(sprite,pos.x,pos.y);

        /*gfx.save();
        gfx.setTextSize(3f);

        gfx.setColor(Color.BLACK);
        gfx.opacity(0.2f);
        gfx.fillRect(pos.x + 14.5 - gfx.stringWidth(nickname)/2,pos.y - 8,gfx.stringWidth(nickname) + 3,6,2,2);
        gfx.opacity(0.8f);

        gfx.setColor(Color.WHITE);
        gfx.draw(nickname,pos.x + 16 - gfx.stringWidth(nickname)/2,pos.y - 3.5);

        gfx.restore();*/
    }

    @Override
    public double getZIndex() {
        return pos.y;
    }
}
