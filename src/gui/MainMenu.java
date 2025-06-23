package gui;

import elements.states.GameState;
import game.GamePanel;
import game.MouseInputs;

import java.awt.*;

public class MainMenu {
    Button play = new Button();
    GamePanel gp;

    public MainMenu(GamePanel gp){
        this.gp = gp;
        play.loadSprite("/resources/UI/button.png");
        play.setSize(96,32);
        play.setPosition(1280/2,720/2);
    }

    public void update(double dt, Point cursorPoint){
        play.update(dt,gp.cursorPoint,gp.mouseInput);

        if(play.done) {
            gp.gameState = GameState.INWORLD;
            gp.mouseInput.mouseReleased = false;
        }
    }

    public void render(Graphics2D g){
        play.render(g);
    }
}
