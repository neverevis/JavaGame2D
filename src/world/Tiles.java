package world;

import graphics.GraphicsFX;
import graphics.ImageManager;
import graphics.Renderable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tiles {
    int totalTiles = 16;
    BufferedImage tile[] = new BufferedImage[totalTiles];

    public Tiles(){
        loadTiles();
    }

    private void loadTiles(){
        tile[0] = ImageManager.load("/resources/tiles/grass.png");
        tile[1] = ImageManager.load("/resources/tiles/flowergrass.png");
        tile[2] = ImageManager.load("/resources/tiles/bush.png");
        tile[3] = ImageManager.load("/resources/tiles/stone.png");

        tile[4] = ImageManager.load("/resources/tiles/floor1.png");
        tile[5] = ImageManager.load("/resources/tiles/floor2.png");
        tile[6] = ImageManager.load("/resources/tiles/floor3.png");
        tile[7] = ImageManager.load("/resources/tiles/floor4.png");

    }

    public void drawTile(GraphicsFX gfx, int id, double x, double y){
        gfx.draw(tile[id], x,y);
    }
}
