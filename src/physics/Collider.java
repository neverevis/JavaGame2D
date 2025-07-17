package physics;

import graphics.GraphicsFX;
import graphics.Renderable;
import math.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Collider implements Renderable {
    protected Vector pos;
    Vector offset;
    double width;
    double height;
    public Rectangle2D.Double area;
    boolean colliding;

    public Collider(double x, double y, double w, double h){
        pos = new Vector(x,y);
        width = w;
        height = h;
        offset = new Vector();

        area = new Rectangle2D.Double(x,y,w,h);
    }

    public Collider(double x, double y,double w, double h, double offsetX, double offsetY){
        pos = new Vector(x,y);
        width = w;
        height = h;
        offset = new Vector(offsetX,offsetY);

        area = new Rectangle2D.Double(x + offsetX,y + offsetY,w,h);
    }

    public Collider(Vector pos, double w, double h){
        this.pos = pos;
        width = w;
        height = h;
        offset = new Vector();

        area = new Rectangle2D.Double(pos.x,pos.y,w,h);
    }

    public Collider(Vector pos, double w, double h, double offsetX, double offsetY){
        this.pos = pos;
        width = w;
        height = h;
        offset = new Vector(offsetX,offsetY);

        area = new Rectangle2D.Double(pos.x,pos.y,w,h);
    }

    public void update(){
        area.x = pos.x + offset.x;
        area.y = pos.y + offset.y;
    }

    public void render(GraphicsFX gfx){
        if(colliding)
            gfx.setColor(Color.WHITE);
        else
            gfx.setColor(Color.BLACK);
        
        gfx.draw(area);
    }

    public void onCollision(Collider other){
        Vector this_center = new Vector(this.area.width / 2 + this.area.x,this.area.height / 2 + this.area.y);
        Vector other_center = new Vector(other.area.width / 2 + other.area.x,other.area.height / 2 + other.area.y);

        Vector knockback = this_center.copy().sub(other_center).normalize();

        this.pos.add(knockback);
        other.update();
    }

    @Override
    public double getZIndex() {
        return pos.y + offset.y;
    }

    @Override
    public int getLayer() {
        return 1;
    }
}
