package utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Sprite {
    BufferedImage spriteSheet;
    BufferedImage[][] sprite;
    int width;
    int height;

    public int orientation;
    int frame;

    int totalOrientation;
    int totalFrame;

    public boolean moving = false;
    public boolean takingDamage = false;

    float fillOpacity = 1.0f;
    float fadeTime = 0.04f;

    int frameCounter;
    int changeRatio;

    public Sprite(BufferedImage spriteSheet, int width, int height, double seconds){
        this.spriteSheet = spriteSheet;
        this.width = width;
        this.height = height;

        totalOrientation = spriteSheet.getHeight()/height;
        totalFrame = spriteSheet.getWidth()/width;

        changeRatio = (int)(Global.FPS*seconds/totalFrame);

        sprite = new BufferedImage[totalOrientation][totalFrame];

        loadSprites();
    }

    private void loadSprites(){
        for(int i = 0; i < totalOrientation; i++){
            for(int j = 0; j < totalFrame; j++){
                sprite[i][j] = ImageManager.getCroppedImg(spriteSheet,j*width,i*height,width,height);
            }
        }
    }

    public void setOrientation(int orientation){
        this.orientation = orientation;
    }

    public void setFrame(int frame){
        this.frame = frame;
    }

    public void render(Graphics2D g, int x, int y){
        if(moving)
            frameCounter++;
        else
            frame = 0;
        if(moving && frameCounter % changeRatio == 0)
            frame++;
        if(frame >= totalFrame)
            frame = 1;
        g.drawImage(sprite[orientation][frame], x, y, (int)(width*Global.SCALE), (int)(height*Global.SCALE), null);

        if (takingDamage) {
            BufferedImage frameSprite = sprite[orientation][frame];

            // Cria um sprite branco com a opacidade atual
            BufferedImage whiteFlash = new BufferedImage(frameSprite.getWidth(), frameSprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = whiteFlash.createGraphics();

            // Set o alpha que você quer
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fillOpacity));

            // Aplica o "branco" por cima do sprite, preservando a shape
            float[] scale = {0f, 0f, 0f, 1f};
            float[] offset = {255f, 255f, 255f, 0f};
            RescaleOp op = new RescaleOp(scale, offset, null);
            g2.drawImage(op.filter(frameSprite, null), 0, 0, null);
            g2.dispose();

            // Desenha o efeito branco no local correto
            g.drawImage(whiteFlash, x, y, (int)(width*Global.SCALE), (int)(height*Global.SCALE), null);

            // Atualiza a opacidade
            fillOpacity -= fadeTime;
            if (fillOpacity <= 0f) {
                takingDamage = false;
                fillOpacity = 1f;
            }
        }
    }

    public void toggleDamageState(){
        takingDamage = true;
    }
}
