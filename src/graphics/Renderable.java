package graphics;

public interface Renderable extends Comparable<Renderable>{
    public void render(GraphicsFX gfx);
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
