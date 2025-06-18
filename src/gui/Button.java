package gui;

import game.MouseInputs;
import utilities.Global;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Button {
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage btn;
    private BufferedImage btnHovered;
    public boolean hovered = false;
    public boolean clicked = false;


    public void setSize(int width, int height){
        this.width = (int)(width * Global.SCALE);
        this.height = (int)(height * Global.SCALE);
    }

    public void setPosition(int x, int y){
        int halfW = width/2;
        int halfH = height/2;

        this.x = x - halfW;
        this.y = y - halfH;
    }

    public void loadImage(String btnPath, String btnHoveredPath){
        try {
            btn = ImageIO.read(getClass().getResourceAsStream(btnPath));
            btnHovered = ImageIO.read(getClass().getResourceAsStream(btnHoveredPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g){
        if(!hovered)
            g.drawImage(btn,x,y,width,height,null);
        else
            g.drawImage(btnHovered,x,y,width,height,null);
    }

    public void update(Point cursorPoint,MouseInputs mouseInput){
        if(cursorPoint != null){
            if(cursorPoint.x >= this.x && cursorPoint.y >= this.y && cursorPoint.x <= this.x + width && cursorPoint.y <= this.y + height)
                hovered = true;
            else
                hovered = false;

            if(mouseInput.mouseClicked && hovered)
                clicked = true;
            else
                clicked = false;
        }
    }
}
