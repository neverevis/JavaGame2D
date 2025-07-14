package graphics;

import java.awt.*;

public interface Renderable extends Comparable<Renderable>{
    public void render(Graphics2D g);
    public int getLayer();
    public double getZIndex();

    @Override
    default int compareTo(Renderable other) {
        if(getLayer() == other.getLayer()){
            if(getZIndex() > other.getZIndex()){
                return 1;
            }else if(getZIndex() < other.getZIndex()){
                return -1;
            }else{
                return 0;
            }
        }

        return getLayer() - other.getLayer();
    }
}
