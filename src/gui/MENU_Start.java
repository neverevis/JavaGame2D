package gui;

import core.Core;
import core.G;
import core.Key;
import core.TextField;
import graphics.GraphicsFX;
import math.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MENU_Start extends Menu{
    Core core;
    boolean active = true;
    InputField ip;

    Button start = new Button("/resources/UI/button.png",96,32,new Vector((double)G.S_WIDTH/2/4,(double)G.S_HEIGHT/2/4));

    public MENU_Start(Core core){
        this.core = core;
        ip = new InputField(core.key,10,G.S_WIDTH/2/4,50);
    }

    public void update(double dt){
        start.update(dt);
        if(Key.escapeKey){
            Key.escapeKey = false;
            if(active) {
                ip.selected = true;
                active = false;
            }
            else {
                ip.selected = false;
                active = true;
            }
        }
        ip.update();
    }

    @Override
    public void render(GraphicsFX gfx) {
        if(active) {
            gfx.setColor(Color.BLUE);
            gfx.fillRect(0, 0, G.S_WIDTH, G.S_HEIGHT);
            gfx.setTextSize(10f);
            gfx.setColor(Color.WHITE);
            gfx.draw("Enter your username:",G.S_WIDTH/2/4,25);
            ip.render(gfx);
            gfx.setColor(Color.WHITE);
            start.render(gfx);
        }
    }
}
