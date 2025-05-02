package elements;

import elements.entities.Player;
import elements.Particle;
import game.GamePanel;
import utilities.Global;
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
    List<Particle> particles = new ArrayList<Particle>();

    public Dust(GamePanel gp, World world, Player player){
        super(gp,world);
        this.player = player;
    }
    @Override
    public void setAttributes() {
        try {
            dust = ImageIO.read(getClass().getResourceAsStream("/particles/dust.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(16,16);
        setAnchor(16/2,16/2);
        setPositionByAnchor(300,300);
        setSprite(dust);
        sprite.moving = true;
    }

    @Override
    public void update(double deltaTime) {
        if(gp.kh.rightKey || gp.kh.upKey || gp.kh.downKey || gp.kh.leftKey){
            if(random.nextInt(20) == 0){
                particles.add(new Particle(player.getAnchorX(),player.getAnchorY()));
            }
        }

        for(int i = particles.size() - 1; i >= 0; i--){
            particles.get(i).sprite.update(deltaTime);
            particles.get(i).scale-= 20*deltaTime;
            particles.get(i).y+=particles.get(i).velocityY*deltaTime;
            particles.get(i).velocityY+=300*deltaTime;
            if(particles.get(i).scale <= 0)
                particles.remove(i);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        for(Particle particle : particles)
            particle.sprite.render(g2d,(int)(particle.getX() - world.camera.x),(int)(particle.getY() - world.camera.y),(int)particle.scale,(int)particle.scale);
    }
}
