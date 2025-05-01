package world;

import elements.Dust;
import elements.Element;
import elements.entities.Player;
import elements.entities.Slime;
import game.GamePanel;
import utilities.Global;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class World {
    int cols,rows;
    public int width, height;
    public int[][] world;
    Tiles tiles;
    Random random = new Random();
    public GamePanel gp;
    public Camera camera = new Camera(this);
    public boolean showElementsAnchor = false;
    List<Element> elements = new ArrayList<Element>();
    Player player;
    Slime slime1;
    Slime slime2;
    Dust dust;

    public World(GamePanel gp){
        this.gp = gp;
        player = new Player(this.gp,this);
        slime1 = new Slime(this.gp,this,player);
        dust = new Dust(this.gp,this,player);
        elements.add(player);
        elements.add(dust);
        tiles = new Tiles();
        cols = 100;
        rows = 100;
        world = new int[cols][rows];
        width = cols* Global.TILESIZE;
        height = rows* Global.TILESIZE;
        convertWorld();
    }

    public void convertWorld() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (random.nextInt(10) == 0)
                    world[i][j] = 1;
                else
                    world[i][j] = 0;
                if (random.nextInt(60) == 0)
                    world[i][j] = 2;
            }
        }
    }

    public void render(Graphics2D g2d){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                double tileX = i*Global.TILESIZE - camera.x;
                double tileY = j*Global.TILESIZE - camera.y;

                if(tileX > - Global.TILESIZE && tileX < Global.SCREENWIDTH && tileY > - Global.TILESIZE && tileY < Global.SCREENHEIGHT) {
                    tiles.drawTile(g2d, world[i][j], (int)tileX, (int)tileY);
                }
            }
        }

        for(Element elm : elements){
            elm.render(g2d);
        }
    }

    public void update(double deltaTime){
        camera.update(deltaTime);
        for(Element elm : elements){
            elm.update(deltaTime);
        }
        //organizar a lista de elementos por ordem de Y
        elements.sort(Comparator.comparingInt(a -> (int) a.getAnchorY()));
    }
}
