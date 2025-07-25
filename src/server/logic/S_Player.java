package server.logic;

import math.Vector;
import physics.Collider;
import server.ClientHandler;

public class S_Player {
    ClientHandler clientHandler;
    ServerWorld world;

    public Vector pos;
    Vector direction = new Vector();
    Vector velocity = new Vector();
    Vector knockback = new Vector();
    Vector movement = new Vector();
    Vector nextPos = new Vector();

    public double health = 100;

    double acceleration = 800;
    double maxSpeed = 100;

    Collider collider;

    double attackCoolDown;
    boolean damageDealt;
    boolean invincibility;

    public int state;
    public int facing;

    final int IDLE = 0;
    final int RUNNING = 1;
    final int ATTACKING = 2;
    final int TAKING_DAMAGE = 3;
    final int DEAD = 4;

    final int UP = 0;
    final int DOWN = 1;
    final int LEFT = 2;
    final int RIGHT = 3;

    final double ATTACK_COOLDOWN_TIME = 1.0;
    final double PUNCH_DAMAGE = 20;
    final int ATTACK_RANGE = 32;
    final double DAMAGE_KNOCKBACK = 300;

    public S_Player(ClientHandler clientHandler, Vector pos, ServerWorld world){
        this.clientHandler = clientHandler;
        this.pos = pos;
        this.world = world;
        collider = new Collider(pos,10,8,-5,8,false);
        world.collSys.register(collider);
    }

    public void update(double dt) {
        if(state != DEAD) {
            handleAttack(dt);
            handleMovement(dt);
            handleTakingDamage(dt);
            applyVelocity(dt);
        }
        if(health <= 0)
            state = DEAD;
    }

    void handleAttack(double dt){
        updateAttackState(dt);

        if(shouldStartAttacking()){
            startAttack();
        }
        if(shouldDealDamage()){
            dealDamageToPlayers();
        }
    }

    void handleMovement(double dt){
        if (state != ATTACKING && state != TAKING_DAMAGE) {
            direction.reset();

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
                decelerate(movement,dt);
                state = IDLE;
            }

            direction.normalize();
            movement.add(direction.multiply(acceleration * dt));
            movement.clamp(maxSpeed);
        }
    }

    void applyVelocity(double dt){
        knockback.multiply(Math.pow(0.03,dt));
        velocity.set(movement.copy().add(knockback));

        nextPos.set(pos);
        nextPos.add(velocity.multiply(dt));

        if(!world.collSys.predictCollision_x(collider,nextPos.x)){

            pos.x = nextPos.x;
        }
        if(!world.collSys.predictCollision_y(collider,nextPos.y)){
            pos.y = nextPos.y;
        }
    }

    void updateAttackState(double dt){
        if(attackCoolDown > 0) {
            attackCoolDown -= dt;
        }

        if(state == ATTACKING) {
            if (attackCoolDown <= 0.45) {
                state = IDLE;
            }
            decelerate(movement,dt);
        }
    }

    boolean shouldStartAttacking(){
        return state != TAKING_DAMAGE && clientHandler.click && attackCoolDown <= 0;
    }

    public void startAttack(){
        state = ATTACKING;
        attackCoolDown = ATTACK_COOLDOWN_TIME;
        damageDealt = false;
    }

    boolean shouldDealDamage(){
        if(state == ATTACKING && !damageDealt && attackCoolDown < 0.95){
            return true;
        }

        return false;
    }

    void dealDamageToPlayers(){
        for(S_Player player : world.players){
            if(player != this) {
                if (!player.invincibility && isOnAttackRange(player) && isOnFacingDirection(player)) {
                    player.takeDamage(pos, PUNCH_DAMAGE);
                }
            }
        }

        damageDealt = true;
    }

    boolean isOnAttackRange(S_Player player){
        return pos.getDistance(player.pos) <= ATTACK_RANGE;
    }

    boolean isOnFacingDirection(S_Player player){
        double dx = pos.x - player.pos.x;
        double dy = pos.y - player.pos.y;

        if(facing == UP && dy > 0){
            return true;
        }
        else if(facing == DOWN && dy <= 0){
            return true;
        }
        else if(facing == LEFT && dx > 0){
            return true;
        }
        else if(facing == RIGHT && dx <= 0){
            return true;
        }

        return false;
    }

    public void takeDamage(Vector originPosition, double damage){
        state = TAKING_DAMAGE;
        knockback = pos.copy().sub(originPosition).normalize().multiply(DAMAGE_KNOCKBACK);
        health -= damage;
    }

    public void handleTakingDamage(double dt){
        if(state == TAKING_DAMAGE) {
            if (knockback.length() <= 10) {
                state = IDLE;
            }
        }
    }

    public void decelerate(Vector force, double dt){
        force.multiply(Math.pow(0.03, dt * 2));
    }

}
