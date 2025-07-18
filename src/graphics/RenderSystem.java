package graphics;

import world.World;
import java.util.concurrent.CopyOnWriteArrayList;

public class RenderSystem {
    World world;
    CopyOnWriteArrayList<Renderable> renderables = new CopyOnWriteArrayList<>();

    public void register(Renderable r){
        renderables.add(r);
    }

    public void unregister(Renderable r){
        renderables.remove(r);
    }

    public void render(GraphicsFX gfx){
        renderables.sort(null);

        for(Renderable r : renderables) {
            if(r.getLayer() == 0)
                r.render(gfx);
        }

        for(Renderable r : renderables) {
            if(r.getLayer() != 0)
                r.render(gfx);
        }
    }
}
