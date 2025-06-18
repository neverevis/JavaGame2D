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
        play.loadImage("/resources/UI/playbutton.png","/resources/UI/playbutton_pressed.png","/resources/UI/playbutton_hovered.png");
        play.setSize(96,32);
        play.setPosition(1280/2,720/2);
    }

    public void update(Point cursorPoint){
        play.update(gp.cursorPoint,gp.mouseInput);

        if(play.released) {
            gp.gameState = GameState.INWORLD;
            gp.mouseInput.mouseReleased = false;
        }
    }

    public void render(Graphics2D g){
        play.render(g);
    }
}
