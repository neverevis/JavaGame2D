package elements;

import game.GamePanel;
import utilities.Collider;
import utilities.Global;
import utilities.Sprite;
import utilities.Vector;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Element {
    public Sprite sprite;

    public Vector position = new Vector(0,0);
    public double anchorX = 0.5;
    public double anchorY = 0.5;
    public int width;
    public int height;
    int feetLine;
    public Collider collider = new Collider();

    protected GamePanel gp;
    public World world;

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

    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setPositionByAnchor(Vector position){
        this.position.set(position.x - anchorX*width*Global.SCALE,position.y - anchorY*height*Global.SCALE);
    }

    public void setPositionXByAnchor(double x){
        position.setX(x - anchorX*width*Global.SCALE);
    }

    public void setPositionYByAnchor(double y){
        position.setY(y - anchorY*height*Global.SCALE);
    }

    public Vector getInAnchor(Vector position){
        return position.get().add(new Vector(anchorX*width*Global.SCALE,anchorY*height*Global.SCALE));
    }

    public Vector getInAnchorOffset(Vector position){
        return position.get().sub(new Vector(anchorX*width*Global.SCALE,anchorY*height*Global.SCALE));
    }

    public double getAnchorX(){
        return position.getX() + width*anchorX*Global.SCALE;
    }

    public double getAnchorY(){
        return position.getY() + height*anchorY*Global.SCALE;
    }

    public void renderAnchor(Graphics2D g2d){
        g2d.setColor(Color.yellow);
        g2d.drawRect((int)(position.getX() - world.camera.x),(int)(position.getY() - world.camera.y),(int)(width* Global.SCALE),(int)(height*Global.SCALE));
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

    public void renderCollisionBox(Graphics2D g2d,Rectangle collisionBox){
        g2d.drawRect((int)(collisionBox.getX() - world.camera.x),(int)(collisionBox.getY() - world.camera.y),(int)collisionBox.getWidth(),(int)collisionBox.getHeight());
    }

    public void setFeetLine(double feetLine){
        this.feetLine = (int)(feetLine*Global.SCALE);
    }

    public double getX(){
        return position.getX();
    }

    public double getFeetCenterY(){
        return position.getY() + feetLine;
    }

    public double getFeetCenterX(){
        return position.getX() + width*Global.SCALE/2;
    }



    public void renderFeetLine(Graphics2D g2d){
        g2d.setColor(Color.green);
        g2d.drawLine((int)(position.getX() - world.camera.x),(int)(getFeetCenterY() - world.camera.y),(int)(position.getX() + width*Global.SCALE - world.camera.x),(int)(getFeetCenterY() - world.camera.y));
    }
}
