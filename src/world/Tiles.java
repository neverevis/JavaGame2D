package world;

import game.GamePanel;
import utilities.Global;
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
            tile[0] = ImageManager.getScaled(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/grass.png")),(int)Global.SCALE);
            tile[1] = ImageManager.getScaled(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/flowergrass.png")),(int)Global.SCALE);
            tile[2] = ImageManager.getScaled(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/bush.png")),(int)Global.SCALE);
            tile[3] = ImageManager.getScaled(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/stone.png")),(int)Global.SCALE);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawTile(Graphics g,int id,int x,int y){
        g.drawImage(tile[id],x,y, null);
    }
}
