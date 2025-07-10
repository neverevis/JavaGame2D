package gui;

import elements.states.Direction;
import game.MouseInputs;
import utilities.C;
import utilities.Sprite;

import java.awt.*;

public class Button {
    private int x;
    private int y;
    private int width;
    private int height;
    private double time;
    private Sprite btn;
    public boolean hovered = false;
    public boolean clicked = false;
    public boolean done = false;


    public void setSize(int width, int height){
        this.width = (int)(width * C.SCALE);
        this.height = (int)(height * C.SCALE);
    }

    public void setPosition(int x, int y){
        int halfW = width/2;
        int halfH = height/2;

        this.x = x - halfW;
        this.y = y - halfH;
    }

    public void loadSprite(String spriteSheetPath){
        btn = new Sprite(spriteSheetPath,96,32,0.4f);
    }

    public void render(Graphics2D g){
        btn.render(g,x,y);
    }

    public void update(double dt, Point cursorPoint,MouseInputs mouseInput){
        btn.update(dt);

        if(done)
            done = false;

        if(clicked){
            time += dt;
        }

        if(cursorPoint != null) {
            if (cursorPoint.x >= this.x && cursorPoint.y >= this.y && cursorPoint.x <= this.x + width && cursorPoint.y <= this.y + height) {
                hovered = true;
                btn.setDirection(Direction.UP);
            }
            else if(!clicked){
                hovered = false;
                btn.setDirection(Direction.DOWN);
            }
        }

        if(mouseInput.mouseClicked && hovered) {
            clicked = true;
            btn.moving = true;
        }

        if(time >= 0.3) {
            btn.moving = false;
            clicked = false;
            time = 0.0;
            done = true;
        }
    }
}
