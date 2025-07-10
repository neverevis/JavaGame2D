package game;

import elements.states.PlayerState;
import utilities.C;
import world.World;

import java.awt.*;
import java.util.ArrayList;

public class DialogueManager {
    ArrayList<String> activeDialogue;
    World world;
    GamePanel gp;
    int activeLine = 0;
    public boolean triggered = false;
    double triggerCD = 0.2;
    double time;
    Font font = new Font("Arial", Font.BOLD, 20);

    public DialogueManager(GamePanel gp, World world){
        this.gp = gp;
        this.world = world;
    }

    public void update(double dt){
        if(triggered){
            time += dt;
            if(gp.kh.interactKey && time >= triggerCD){
                time = 0;
                if(activeLine + 1 < activeDialogue.size())
                    activeLine++;
                else {
                    activeLine = 0;
                    triggered = false;
                }
            }
        }
        if(world.player.playerState == PlayerState.MOVING)
            triggered = false;
    }

    public void render(Graphics2D g){
        if(triggered) {
            g.setColor(Color.black);
            g.fillRect(100, C.SCREENHEIGHT - 300, C.SCREENWIDTH - 200, 200);
            g.setColor(Color.WHITE);
            g.drawRect(100, C.SCREENHEIGHT - 300, C.SCREENWIDTH - 200, 200);
            g.setFont(font);
            g.drawString(activeDialogue.get(activeLine), (C.SCREENWIDTH)/2 - (20 * activeDialogue.get(activeLine).length())/4, C.SCREENHEIGHT - 300 + 100);
        }
    }

    public void start(ArrayList<String> activeDialogue){
        this.activeDialogue = activeDialogue;
        if(!triggered)
            triggered = true;
    }
}
