package graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {

    public static BufferedImage load(String path){
        BufferedImage img = null;
        try{
            img = ImageIO.read(ImageManager.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        img = toCompatibleImage(img);
        return img;
    }

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

    public static BufferedImage toCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gfx_config = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        BufferedImage newImage = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return newImage;
    }

}
