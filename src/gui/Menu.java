package gui;

import graphics.GraphicsFX;
import graphics.Renderable;

public abstract class Menu implements Renderable {
    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public double getZIndex() {
        return 0;
    }
}
