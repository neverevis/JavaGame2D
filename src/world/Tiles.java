package world;

import game.GamePanel;
import utilities.Global;

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
            tile[0] = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tile[1] = ImageIO.read(getClass().getResourceAsStream("/tiles/flowergrass.png"));
            tile[2] = ImageIO.read(getClass().getResourceAsStream("/tiles/bush.png"));

            //implementação do TileSet provisório (em breve será alterado pelo real)
            tile[3] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/cornerDownLeft.png"));
            tile[4] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/cornerDownRight.png"));
            tile[5] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/cornerUpLeft.png"));
            tile[6] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/cornerUpRight.png"));
            tile[7] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/tileDown.png"));
            tile[8] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/tileLeft.png"));
            tile[9] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/tileRight.png"));
            tile[10] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/tileUp.png"));
            tile[11] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/mainTile1.png")); //chão
            //portas
            tile[12] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/doorUpLeft.png"));
            tile[13] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/doorUpRight.png"));
            tile[14] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/doorDownLeft.png"));
            tile[15] = ImageIO.read(getClass().getResourceAsStream("/tiles/tileset_prototipo/doorDownRight.png"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawTile(Graphics g,int id,int x,int y){
        g.drawImage(tile[id],x,y,Global.TILESIZE, Global.TILESIZE, null);
    }
}
