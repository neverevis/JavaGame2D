package utilities;

import elements.Element;

import java.awt.*;

public class Collider {

    double offsetX;
    double offsetY;
    public double x;
    public double y;
    public double width;
    public double height;
    public boolean collision = false;
    public Rectangle colliderBox;
    Rectangle nextColliderBox;
    public Vector centerOffset;
    public Vector center = new Vector();
    public Element owner;

    public Collider(){}

    public Collider(Element owner){
        this.owner = owner;
    }

    public void setBounds(Element owner, int offsetX, int offsetY, int width, int height){
        this.width = width*Global.SCALE;
        this.height = height*Global.SCALE;
        this.owner = owner;
        this.offsetX = offsetX *Global.SCALE;
        this.offsetY = offsetY *Global.SCALE;
        colliderBox = new Rectangle((int)this.offsetX,(int)this.offsetY,(int)this.width,(int)this.height);
        nextColliderBox = new Rectangle((int)this.offsetX,(int)this.offsetY,(int)this.width,(int)this.height);
        centerOffset = new Vector(this.width/2,this.height/2);
        update();
    }

    public void update(){
        x = owner.position.x + this.offsetX;
        y = owner.position.y + this.offsetY;

        colliderBox.setLocation((int) x,(int) y);
        center.set(x,y).add(centerOffset);
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.drawRect((int)(x - owner.world.camera.x),(int)(y - owner.world.camera.y),(int)width,(int)height);
        g2d.setColor(Color.white);
        g2d.fillRect(owner.world.camera.relativeX(center.x),owner.world.camera.relativeY(center.y),3,3);
    }

    public boolean predictXCollision(Collider collider, double futureX){
        update();
        nextColliderBox.setLocation((int)(futureX + this.offsetX), (int)(owner.position.y + this.offsetY));

        if(nextColliderBox.intersects(collider.colliderBox)) {
            return true;
        }
        return false;
    }

    public boolean predictYCollision(Collider collider, double futureY){
        update();
        nextColliderBox.setLocation((int)(owner.position.x + this.offsetX), (int)(futureY + this.offsetY));

        if(nextColliderBox.intersects(collider.colliderBox)) {
            return true;
        }
        return false;
    }

    public boolean predictCollision(Collider collider, Vector futurePosition){
        update();
        nextColliderBox.setLocation((int)(futurePosition.x + this.offsetX), (int)(futurePosition.y + this.offsetY));
        if(nextColliderBox.intersects(collider.colliderBox)) {
            return true;
        }
        return false;
    }

    public boolean colliding(Collider other){
        if(this.colliderBox.intersects(other.colliderBox)) {
            return true;
        }
        return false;
    }

    public void onCollision(){
    }
}
