package elements;

import game.GamePanel;
import utilities.Global;
import utilities.Sprite;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Element {
    protected Sprite sprite;

    public double x;
    public double y;
    public double anchorX = 0.5;
    public double anchorY = 0.5;
    protected int width;
    protected int height;
    int feetLine;

    public boolean collision = false;
    public Rectangle collisionBox;
    int collisionX;
    int collisionY;

    protected GamePanel gp;
    protected World world;

    public Element(GamePanel gp, World world){
        this.gp = gp;
        this.world = world;
        setAttributes();
    }

    public abstract void setAttributes();

    public abstract void update(double deltaTime);
    public abstract void render(Graphics2D g2d);

    public void setSprite(BufferedImage spritesheet){
        sprite = new Sprite(spritesheet,width,height,1);
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setCollisionBox(int x, int y, int width, int height){
        collisionX = (int)(x*Global.SCALE);
        collisionY = (int)(y*Global.SCALE);
        collisionBox = new Rectangle((int)(x*Global.SCALE),(int)(y*Global.SCALE),(int)(width*Global.SCALE),(int)(height*Global.SCALE));
    }

    public void setPositionByAnchor(double x, double y){
        this.x = x - anchorX*width*Global.SCALE;
        this.y = y - anchorY*height*Global.SCALE;
    }

    public void setPositionXByAnchor(double x){
        this.x = x - anchorX*width*Global.SCALE;
    }

    public void setPositionYByAnchor(double y){
        this.y = y - anchorY*height*Global.SCALE;
    }

    public double getAnchorX(){
        return x + width*anchorX*Global.SCALE;
    }

    public double getAnchorY(){
        return y + height*anchorY*Global.SCALE;
    }

    public void renderAnchor(Graphics2D g2d){
        g2d.setColor(Color.yellow);
        g2d.drawRect((int)(x - world.camera.x),(int)(y - world.camera.y),(int)(width* Global.SCALE),(int)(height*Global.SCALE));
        g2d.setColor(Color.black);
        g2d.fillRect((int)(getAnchorX() - 4 - world.camera.x),(int)(getAnchorY() - 1 - world.camera.y),8,2);
        g2d.fillRect((int)(getAnchorX() - 1 - world.camera.x),(int)(getAnchorY() - 4 - world.camera.y),2,8);
    }

    protected void setAnchor(double x, double y){
        double scaledWidth = width*Global.SCALE;
        double scaledHeight = height*Global.SCALE;

        anchorX = 1.0/scaledWidth*x*Global.SCALE;
        anchorY = 1.0/scaledHeight*y*Global.SCALE;
    }

    public void updateCollisionBox(Rectangle collisionBox, double x, double y){
        collisionBox.setLocation((int)x + collisionX,(int)y + collisionY);
    }

    public void renderCollisionBox(Graphics2D g2d,Rectangle collisionBox){
        g2d.drawRect((int)(collisionBox.getX() - world.camera.x),(int)(collisionBox.getY() - world.camera.y),(int)collisionBox.getWidth(),(int)collisionBox.getHeight());
    }

    public void setCollision(boolean collision){
        this.collision = collision;
    }

    public void setFeetLine(double feetLine){
        this.feetLine = (int)(feetLine*Global.SCALE);
    }

    public double getX(){
        return x;
    }

    public double getFeetLine(){
        return y + feetLine;
    }

    public void renderFeetLine(Graphics2D g2d){
        g2d.setColor(Color.green);
        g2d.drawLine((int)(x - world.camera.x),(int)(getFeetLine() - world.camera.y),(int)(x+width*Global.SCALE - world.camera.x),(int)(getFeetLine() - world.camera.y));
    }
}
