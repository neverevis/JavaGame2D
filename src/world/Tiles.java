package world;

import utilities.C;
import utilities.ImageManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tiles {
    int totalTiles = 16;
    BufferedImage tile[] = new BufferedImage[totalTiles];

    public Tiles(){
        loadTiles();
    }

    private void loadTiles(){
        try {
            tile[0] = ImageManager.getScaled(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/grass.png")),(int) C.SCALE);
            tile[1] = ImageManager.getScaled(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/flowergrass.png")),(int) C.SCALE);
            tile[2] = ImageManager.getScaled(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/bush.png")),(int) C.SCALE);
            tile[3] = ImageManager.getScaled(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/stone.png")),(int) C.SCALE);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawTile(Graphics g,int id,int x,int y){
        g.drawImage(tile[id],x,y, null);
    }
}
