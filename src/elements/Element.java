package elements;

import graphics.Renderable;
import math.Vector;

public abstract class Element implements Renderable {
    public Vector pos = new Vector();
    public abstract void update(double dt);

    @Override
    public int getLayer() {
        return 1;
    }
}
