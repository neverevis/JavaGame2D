package graphics;

import world.Tiles;
import world.World;

import java.awt.*;
import java.awt.geom.AffineTransform;
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

    public void render(Graphics2D g){
        renderables.sort(null);

        AffineTransform og = g.getTransform();;

        for(Renderable r : renderables) {
            if(r.getLayer() == 0)
                r.render(g);
        }

        for(Renderable r : renderables) {
            if(r.getLayer() != 0)
                r.render(g);
        }
        g.setTransform(og);
    }
}
