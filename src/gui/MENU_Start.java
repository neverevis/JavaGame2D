package gui;

import core.Core;
import core.G;
import core.Key;
import core.TextField;
import graphics.GraphicsFX;

import java.awt.*;

public class MENU_Start extends Menu{
    Core core;
    TextField tf = new TextField("");
    boolean active = true;

    public MENU_Start(Core core){
        this.core = core;
        core.key.setTextField(tf);
    }

    public void update(){
        if(Key.escapeKey){
            Key.escapeKey = false;
            if(active) {
                active = false;
                core.key.removeTextField();
            }
            else {
                tf.text = "";
                core.key.setTextField(tf);
                active = true;
            }
        }
    }

    @Override
    public void render(GraphicsFX gfx) {
        if(active) {
            gfx.setColor(Color.BLUE);
            gfx.fillRect(0, 0, G.S_WIDTH, G.S_HEIGHT);
            gfx.setColor(Color.WHITE);
            gfx.draw(tf.text,G.S_WIDTH/2/4,G.S_HEIGHT/2/4);
        }
    }
}
