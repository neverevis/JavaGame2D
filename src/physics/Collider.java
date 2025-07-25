package physics;

import elements.ELM_Player;
import elements.Element;
import graphics.GraphicsFX;
import graphics.Renderable;
import math.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Collider implements Renderable {
    public Vector pos;
    Vector offset;
    double width;
    double height;
    public Rectangle2D.Double area;
    public boolean colliding;
    public boolean solid;
    ELM_Player owner = null;

    public Collider(double x, double y, double w, double h, boolean solid){
        pos = new Vector(x,y);
        width = w;
        height = h;
        offset = new Vector();
        this.solid = solid;

        area = new Rectangle2D.Double(x,y,w,h);
    }

    public Collider(double x, double y,double w, double h, double offsetX, double offsetY, boolean solid){
        pos = new Vector(x,y);
        width = w;
        height = h;
        offset = new Vector(offsetX,offsetY);
        this.solid = solid;

        area = new Rectangle2D.Double(x + offsetX,y + offsetY,w,h);
    }

    public Collider(Vector pos, double w, double h, boolean solid){
        this.pos = pos;
        width = w;
        height = h;
        offset = new Vector();
        this.solid = solid;

        area = new Rectangle2D.Double(pos.x,pos.y,w,h);
    }

    public Collider(Vector pos, double w, double h, double offsetX, double offsetY, boolean solid){
        this.pos = pos;
        width = w;
        height = h;
        offset = new Vector(offsetX,offsetY);
        this.solid = solid;

        area = new Rectangle2D.Double(pos.x,pos.y,w,h);
    }

    public Collider(Vector pos, double w, double h, double offsetX, double offsetY, boolean solid, ELM_Player owner){
        this.pos = pos;
        width = w;
        height = h;
        offset = new Vector(offsetX,offsetY);
        this.solid = solid;
        this.owner = owner;

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

    public boolean willCollide(Collider other){
        if(other.area.intersects(this.area))
            return true;

        return false;
    }

    public void onCollision(Collider other){
        Vector this_center = new Vector(this.area.width / 2 + this.area.x,this.area.height / 2 + this.area.y);
        Vector other_center = new Vector(other.area.width / 2 + other.area.x,other.area.height / 2 + other.area.y);

        Vector knockback = this_center.copy().sub(other_center).normalize();

        this.pos.add(knockback);
        other.update();
    }

    public Collider copy(){
        Collider copy = new Collider(this.pos.x,this.pos.y,this.width,this.height,this.offset.x,this.offset.y,this.solid);
        copy.update();

        return copy;
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
