package utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageManager {

    public static BufferedImage getCroppedImg(BufferedImage image,int x, int y, int width, int height){
        BufferedImage result = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2d = result.createGraphics();
        graphics2d.drawImage(image,0,0,width,height,x,y,width+x,height+y,null);
        graphics2d.dispose();
        return result;
    }

    public static BufferedImage getScaled(BufferedImage image, int scale){
        BufferedImage result = new BufferedImage(image.getWidth()*scale,image.getHeight()*scale,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2d = result.createGraphics();
        graphics2d.drawImage(image,0,0,image.getWidth()*scale,image.getHeight()*scale,null);
        graphics2d.dispose();
        return result;
    }

}
