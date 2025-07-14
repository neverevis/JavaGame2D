package gui;

import core.G;
import core.Core;

import java.awt.*;

public class MainMenu {
    Button play = new Button();
    Core gp;

    public MainMenu(Core gp){
        this.gp = gp;
        play.loadSprite("/resources/UI/button.png");
        play.setSize(96,32);
        play.setPosition(G.S_WIDTH/2, G.S_HEIGHT/2);
    }

    public void update(double dt, Point cursorPoint){
        play.update(dt,gp.cursorPoint,gp.mouse);
    }

    public void render(Graphics2D g){
        play.render(g);
    }
}
