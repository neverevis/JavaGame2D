package graphics;

import world.Tiles;
import world.World;

import java.util.ArrayList;

public class RenderSystem {
    World world;
    ArrayList<Renderable> renderables = new ArrayList<>();
    Tiles tiles = new Tiles();

    public void register(Renderable r){
        renderables.add(r);
    }

    public void unregister(Renderable r){
        renderables.remove(r);
    }

    public void render(GraphicsFX gfx){
        renderables.sort(null);

        gfx.save();

        for(Renderable r : renderables) {
            if(r.getLayer() == 0)
                r.render(gfx);
        }

        for(Renderable r : renderables) {
            if(r.getLayer() != 0)
                r.render(gfx);
        }

        gfx.restore();
    }
}
