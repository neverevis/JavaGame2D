package utilities;

import elements.Element;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class RayCasts {
    Element owner;
    Vector direction = new Vector();
    double maxLength;
    double length;
    double raysDistance;
    public List<Line2D.Double> rays = new ArrayList<>();
    Vector leftPerpendicular = new Vector();
    Vector rightPerpendicular = new Vector();
    Vector central = new Vector();

    public RayCasts(Element owner, double maxLength){
        this.owner = owner;
        this.length = maxLength;
        raysDistance = (owner.width*Global.SCALE/3);
        for (int i = 0; i < 3; i++) {
            rays.add(new Line2D.Double());
        }
    }

    public void update(Vector velocity){
        direction = velocity.get().normalize().multiply(raysDistance);
        Vector rayRef = new Vector().set(velocity).normalize().multiply(velocity.length()*length);
        double anchorX = owner.getAnchorX();
        double anchorY = owner.getAnchorY();
        leftPerpendicular.set(-direction.y, direction.x);
        rightPerpendicular.set(direction.y,-direction.x);

        rays.get(0).setLine(anchorX,anchorY,anchorX + rayRef.x,anchorY + rayRef.y);
        rays.get(1).setLine(anchorX+leftPerpendicular.x,anchorY+leftPerpendicular.y,anchorX+rayRef.x+ leftPerpendicular.x,anchorY+rayRef.y+ leftPerpendicular.y);
        rays.get(2).setLine(anchorX+rightPerpendicular.x,anchorY+rightPerpendicular.y,anchorX+rayRef.x+ rightPerpendicular.x,anchorY+rayRef.y+ rightPerpendicular.y);
    }

    public void render(Graphics2D g2d){
        for(Line2D.Double line : rays){
            g2d.drawLine(owner.world.camera.relativeX(line.getX1()),owner.world.camera.relativeY(line.getY1()),owner.world.camera.relativeX(line.getX2()),owner.world.camera.relativeY(line.getY2()));
        }
    }
}
