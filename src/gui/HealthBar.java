package gui;

import elements.ELM_Player;
import graphics.GraphicsFX;
import graphics.Renderable;
import graphics.Sprite;

public class HealthBar implements Renderable {
    Sprite c1 = new Sprite("/resources/UI/life.png",16,16);
    Sprite c2 = new Sprite("/resources/UI/life.png",16,16);
    Sprite c3 = new Sprite("/resources/UI/life.png",16,16);
    Sprite c4 = new Sprite("/resources/UI/life.png",16,16);
    Sprite c5 = new Sprite("/resources/UI/life.png",16,16);
    Sprite c6 = new Sprite("/resources/UI/life.png",16,16);
    Sprite c7 = new Sprite("/resources/UI/life.png",16,16);
    ELM_Player owner;

    public HealthBar(ELM_Player owner){
        this.owner = owner;
    }

    public void update(){
        double containerValue = owner.maxHealth / 7;

        setContainerSprite(1,c1,containerValue);
        setContainerSprite(2,c2,containerValue);
        setContainerSprite(3,c3,containerValue);
        setContainerSprite(4,c4,containerValue);
        setContainerSprite(5,c5,containerValue);
        setContainerSprite(6,c6,containerValue);
        setContainerSprite(7,c7,containerValue);
    }

    public void setContainerSprite(int containerNumber,Sprite container, double containerValue){
        if(owner.health >= containerValue * containerNumber){
            container.col = 0;
        }
        else if(owner.health >= containerValue * containerNumber - containerValue/2){
            container.col = 1;
        }
        else{
            container.col = 2;
        }
    }


    @Override
    public void render(GraphicsFX gfx) {
        gfx.save();
        gfx.translate(10,10);
        gfx.draw(c1, 0, 0);
        gfx.draw(c2, 10, 0);
        gfx.draw(c3, 20, 0);
        gfx.draw(c4, 30, 0);
        gfx.draw(c5, 40, 0);
        gfx.draw(c6, 50, 0);
        gfx.draw(c7, 60, 0);
        gfx.restore();
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public double getZIndex() {
        return 0;
    }
}

