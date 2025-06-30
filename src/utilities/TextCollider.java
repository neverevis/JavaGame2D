package utilities;

import game.GamePanel;

import java.util.ArrayList;

public class TextCollider extends Collider{
    boolean triggered = false;
    GamePanel gp;
    ArrayList<String> dialogue;

    public TextCollider(GamePanel gp, ArrayList<String> dialogue){
        super();
        this.gp = gp;
        this.dialogue = dialogue;
    }
    @Override
    public void onCollision(){
        if(gp.kh.interactKey){
            gp.activeWorld.dialogueManager.start(dialogue);
        }
    }
}
