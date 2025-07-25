package gui;

import core.G;
import core.Key;
import core.TextField;
import graphics.GraphicsFX;
import graphics.Renderable;
import math.Vector;

import java.awt.*;

public class InputField implements Renderable {
    Vector pos;
    boolean selected = false;
    TextField tf;
    Key key;

    public InputField(Key key, int characterLimit, double x, double y){
        this.tf = new TextField("",characterLimit);
        this.key = key;
        pos = new Vector(x,y);
    }

    public void update(){
        if(selected){
            key.setTextField(tf);
        }else{
            key.removeTextField();
        }
    }

    @Override
    public void render(GraphicsFX gfx) {
        gfx.setColor(Color.BLACK);
        gfx.fillRect(G.S_WIDTH/2/4,50,130,17,0,0);
        gfx.setColor(Color.WHITE);
        gfx.draw(tf.text,pos.x,pos.y);
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public double getZIndex() {
        return 0;
    }
}
