package elements;

import elements.entities.Player;
import elements.states.PlayerState;
import game.GamePanel;
import utilities.Sprite;
import utilities.Vector;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dust extends Element{

    BufferedImage dust;
    Player player;
    Random random = new Random();
    List<Particle> particles = new ArrayList<>();

    public Dust(GamePanel gp, World world, Player player){
        super(gp,world);
        this.player = player;
    }
    @Override
    public void setAttributes() {
        setSize(16,16);
        setAnchor(16/2,16/2);
        setPositionByAnchor(new Vector(0,0));
        setFeetLine(-500);
        sprite = new Sprite("/resources/particles/dust.png",16,16,1f);
        sprite.moving = true;
    }

    @Override
    public void update(double deltaTime) {
        if(player.playerState == PlayerState.MOVING){
            if(random.nextInt(20) == 0){
                particles.add(new Particle(player.getFeetCenterX(),player.getFeetCenterY() - 10));
            }
        }

        for(int i = particles.size() - 1; i >= 0; i--){
            sprite.update(deltaTime);
            particles.get(i).scale -= 20*deltaTime;
            particles.get(i).y += particles.get(i).velocityY*deltaTime;
            particles.get(i).velocityY+=300*deltaTime;
            if(particles.get(i).scale <= 0)
                particles.remove(i);
        }
        position.set(player.position);
    }

    @Override
    public void render(Graphics2D g2d) {
        for(Particle particle : particles)
            sprite.render(g2d,(int)(particle.getX() - gp.activeWorld.camera.x - particle.scale),(int)(particle.getY() - gp.activeWorld.camera.y - particle.scale),particle.scale);
    }
}
