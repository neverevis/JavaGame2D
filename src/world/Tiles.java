package world;

import graphics.GraphicsFX;
import graphics.ImageManager;
import graphics.Renderable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tiles {
    int totalTiles = 18;
    Random random = new Random();
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
        tile[8] = ImageManager.load("/resources/tiles/brickfloor1.png");
        tile[9] = ImageManager.load("/resources/tiles/brickfloor2.png");
        tile[10] = ImageManager.load("/resources/tiles/brickfloor3.png");
        tile[11] = ImageManager.load("/resources/tiles/brickwall1.png");
        tile[12] = ImageManager.load("/resources/tiles/brickwall2.png");
        tile[13] = ImageManager.load("/resources/tiles/border.png");
        tile[14] = ImageManager.load("/resources/tiles/brickfloor4.png");
        tile[15] = ImageManager.load("/resources/tiles/borderVertical.png");
        tile[16] = ImageManager.load("/resources/tiles/borderTopCorner.png");

    }

    public void drawTile(GraphicsFX gfx, int id, double x, double y){
        gfx.draw(tile[id], x,y);
    }

    public int getCode(String color){
        if(color.equalsIgnoreCase("ffffff")){
            int code = random.nextInt(1) + 8;
            if(random.nextInt(10) == 0)
                code = 9;
            return code;
        }else if(color.equalsIgnoreCase("ff0000")){
            return 11;
        }else if(color.equalsIgnoreCase("ff4000")){
            return 12;
        }else if(color.equalsIgnoreCase("6600ff")){
            return 13;
        }else if(color.equalsIgnoreCase("f0f0f0")){
            return 14;
        }else if(color.equalsIgnoreCase("29ff00")){
            return 16;
        }else if(color.equalsIgnoreCase("0063ff")){
            return 15;
        }

        return -1;
    }
}
