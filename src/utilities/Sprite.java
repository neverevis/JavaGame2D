package utilities;

import elements.states.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;

public class Sprite {
    BufferedImage[][] sprite;
    int width;
    int height;

    public Direction direction = Direction.DOWN;
    int frame;

    int totalDirection;
    int totalFrame;

    public boolean moving = false;
    public boolean takingDamage = false;

    float fillOpacity = 1.0f;
    float fadeTime = 6f;

    double timeElapsed;
    int changeRatio;

    public Sprite(String path, int width, int height, double seconds){
        BufferedImage spriteSheet;
        try{
            spriteSheet = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        this.width = width;
        this.height = height;

        if(spriteSheet != null) {
            totalDirection = spriteSheet.getHeight() / height;
            totalFrame = spriteSheet.getWidth() / width;
        }

        changeRatio = (int)(Global.FPS*seconds/totalFrame);

        sprite = new BufferedImage[totalDirection][totalFrame];

        loadSprites(spriteSheet);
    }

    private void loadSprites(BufferedImage spriteSheet){
        for(int i = 0; i < totalDirection; i++){
            for(int j = 0; j < totalFrame; j++){
                sprite[i][j] = ImageManager.getCroppedImg(spriteSheet,j*width,i*height,width,height);
            }
        }
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setFrame(int frame){
        if(frame < totalFrame)
            this.frame = frame;
    }

    public int getFrame(){
        return frame;
    }

    public int getTotalFrame(){
        return totalFrame;
    }

    public void update(double deltaTime){
        if(moving)
            timeElapsed += deltaTime;
        else
            frame = 0;
        if(moving && timeElapsed > (1.0/Global.FPS) * changeRatio) {
            frame++;
            timeElapsed = 0;
        }
        if(frame >= totalFrame)
            frame = 1;

        if(takingDamage){
            fillOpacity -= fadeTime*(float)deltaTime;
            if (fillOpacity <= 0f) {
                takingDamage = false;
                fillOpacity = 1f;
            }
        }
    }

    public void render(Graphics2D g, int x, int y,int width, int height){
        g.drawImage(sprite[direction.code][frame], x, y, (int)(width*Global.SCALE), (int)(height*Global.SCALE), null);

        if (takingDamage) {
            BufferedImage frameSprite = sprite[direction.ordinal()][frame];

            // Cria um sprite branco com a opacidade atual
            BufferedImage whiteFlash = new BufferedImage(frameSprite.getWidth(), frameSprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = whiteFlash.createGraphics();

            // Set o alpha
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fillOpacity));

            // Aplica o "branco" por cima do sprite, preservando a shape
            float[] scale = {0f, 0f, 0f, 1f};
            float[] offset = {255f, 255f, 255f, 0f};
            RescaleOp op = new RescaleOp(scale, offset, null);
            g2.drawImage(op.filter(frameSprite, null), 0, 0, null);
            g2.dispose();

            // Desenha o efeito branco no local correto
            g.drawImage(whiteFlash, x, y, (int)(width*Global.SCALE), (int)(height*Global.SCALE), null);
        }
    }

    public void setAnimationSpeed(double seconds){
        changeRatio = (int)(Global.FPS*seconds/totalFrame);
    }

    public void toggleDamageState(){
        takingDamage = true;
    }
}
