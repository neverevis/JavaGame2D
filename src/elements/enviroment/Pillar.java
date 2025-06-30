package elements.enviroment;

import elements.Element;
import game.GamePanel;
import utilities.Sprite;
import utilities.TextCollider;
import utilities.Vector;
import world.World;

import java.awt.*;
import java.util.ArrayList;

public class Pillar extends Element {

    ArrayList<String> dialogue = new ArrayList<>();
    TextCollider tc;

    public Pillar(GamePanel gp, World world){
        super(gp,world);
        dialogue.add("Um grande pilar de pedra, há escrituras de um idioma desconhecido gravado");
        dialogue.add("Você se pergunta há quanto tempo ele existe...");
        tc = new TextCollider(gp,dialogue);
        tc.setBounds(this,16,64,32,16);
        world.collisionSystem.register(tc);
    }

    @Override
    public void setAttributes() {
        sprite = new Sprite("/resources/elements/pillar.png",64,64,1f);
        setSize(64,64);
        setAnchor(32,32);
        setFeetLine(64);
        setPositionByAnchor(new Vector(900,1000));
        collider.setBounds(this,16,48,32,16);
        collider.collision = true;
        world.collisionSystem.register(collider);
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Graphics2D g2d) {
        sprite.render(g2d, (int)(position.getX() - world.camera.x), (int)(position.getY() - world.camera.y),width,height);
        if(gp.activeWorld.showElementsAnchor) {
            renderAnchor(g2d);
            collider.render(g2d);
            tc.render(g2d);
            renderFeetLine(g2d);
        }
    }
}
