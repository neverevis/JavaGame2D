package gui;

import core.G;
import core.Mouse;
import graphics.GraphicsFX;
import graphics.Renderable;
import graphics.Sprite;
import math.Vector;

public class Button implements Renderable {
    Sprite sprite;
    Vector pos;
    int w;
    int h;

    public Button(String path, int w, int h, Vector pos){
        this.w = w;
        this.h = h;
        sprite = new Sprite(path,w,h);
        this.pos = pos;
    }

    public void update(double dt){
        if(Mouse.pos.x*G.SCALE > (pos.x - w/2)*G.SCALE && Mouse.pos.x< (pos.x + w/2)*G.SCALE &&
            Mouse.pos.y > (pos.y - h/2)*G.SCALE && Mouse.pos.y < (pos.y + h/2)*G.SCALE){
                if(Mouse.mouseClicked){
                    sprite.row = 0;
                }else{
                    sprite.row = 1;
                }
        }else{
            sprite.row = 1;
        }
    }

    @Override
    public void render(GraphicsFX gfx) {
        gfx.draw(sprite,pos.x,pos.y);
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
