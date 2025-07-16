package graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Sprite {
    BufferedImage[][] sprite;

    int width, height;
    public int row, col;
    int totalRow, totalCol;

    public Sprite(String path, int w, int h){
        BufferedImage spriteSheet = ImageManager.load(path);

        this.width = w;
        this.height = h;
        totalRow = spriteSheet.getHeight() / this.height;
        totalCol = spriteSheet.getWidth() / this.width;

        sprite = new BufferedImage[totalRow][totalCol];

        for(int i = 0; i < totalRow; i++){
            for(int j = 0; j < totalCol; j++){
                sprite[i][j] = ImageManager.getCroppedImg(spriteSheet,j*width,i*height,width,height);
            }
        }
    }
}
