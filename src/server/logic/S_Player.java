package server.logic;

import core.Key;
import core.Mouse;
import math.Vector;
import physics.Collider;
import server.ClientHandler;

public class S_Player {
    ClientHandler clientHandler;
    ServerWorld world;

    public Vector pos;
    Vector direction = new Vector();
    Vector velocity = new Vector();
    Vector nextPos = new Vector();

    double acceleration = 800;
    double maxSpeed = 100;

    Collider collider;

    double attackCoolDown;

    public int state;
    public int facing;

    int IDLE = 0;
    int RUNNING = 1;
    public int ATTACKING = 2;

    int UP = 0;
    int DOWN = 1;
    int LEFT = 2;
    int RIGHT = 3;

    public S_Player(ClientHandler clientHandler, Vector pos, ServerWorld world){
        this.clientHandler = clientHandler;
        this.pos = pos;
        this.world = world;
        collider = new Collider(pos,10,8,-5,8);
        world.collSys.register(collider);
    }

    public void update(double dt) {
        if (attackCoolDown > 0) {
            attackCoolDown -= dt;
        }

        if (state == ATTACKING) {
            velocity.multiply(Math.pow(0.03, dt * 2));
            if (attackCoolDown < 0.45) {
                state = IDLE;
            }
        }

        if (clientHandler.click && attackCoolDown <= 0) {
            state = ATTACKING;
            velocity.add(0,700);
            System.out.println(state);
            attackCoolDown = 1;
        }

        direction.reset();

        if (state != ATTACKING) {
            if (clientHandler.W) {
                direction.y--;
                state = RUNNING;
                facing = UP;
            }
            if (clientHandler.S) {
                direction.y++;
                state = RUNNING;
                facing = DOWN;
            }
            if (clientHandler.A) {
                direction.x--;
                state = RUNNING;
                facing = LEFT;
            }
            if (clientHandler.D) {
                direction.x++;
                state = RUNNING;
                facing = RIGHT;
            }

            if (!clientHandler.W && !clientHandler.A && !clientHandler.S && !clientHandler.D) {
                state = IDLE;
                velocity.multiply(Math.pow(0.03, dt * 2));
            }
        }

        direction.normalize();
        velocity.add(direction.multiply(acceleration * dt));
        velocity.clamp(maxSpeed);

        //validação de posição
        collider.pos = nextPos;

        nextPos.set(pos);
        nextPos.add(velocity.x * dt, 0);

        collider.update();
        if (!world.collSys.testCollision(collider))
            pos.set(nextPos.x, pos.y);

        nextPos.set(pos);
        nextPos.add(0, velocity.y * dt);

        collider.update();
        if (!world.collSys.testCollision(collider))
            pos.set(pos.x, nextPos.y);

        collider.pos = pos;
    }

}
