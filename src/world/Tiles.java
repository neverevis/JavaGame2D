package world;

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
    }

    public void drawTile(Graphics2D g,int id,double x,double y){
        AffineTransform at = AffineTransform.getTranslateInstance(x,y);
        g.drawImage(tile[id], at, null);
    }
}
