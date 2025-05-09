package world;

import elements.Dust;
import elements.Element;
import elements.entities.Player;
import elements.entities.Slime;
import elements.enviroment.Fence;
import elements.enviroment.Tree;
import game.GamePanel;
import utilities.Global;
import utilities.Vector;

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
    public List<Element> elements = new ArrayList<Element>();
    Player player;
    Tree tree;
    Tree tree2;
    Slime slime1;
    Slime slime2;
    Dust dust;
    Fence fence;

    public World(GamePanel gp){
        this.gp = gp;
        player = new Player(this.gp,this);
        dust = new Dust(this.gp,this,player);
        tree = new Tree(this.gp,this);
        fence = new Fence(this.gp,this);
        tree.setPositionByAnchor(new Vector(700,500));
        tree2 = new Tree(this.gp,this);
        slime1 = new Slime(this.gp,this,this.player);
        slime2 = new Slime(this.gp,this,this.player);
        slime1.setPositionByAnchor(new Vector(600,600));
        tree2.setPositionByAnchor(new Vector(500,500));
        //elements.add(slime2);
        elements.add(fence);
        elements.add(player);
        //elements.add(tree2);
        elements.add(tree);
        elements.add(dust);
        elements.add(slime1);
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
                world[i][j] = 0;
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
            if(elm.position.x - camera.x < Global.SCREENWIDTH && elm.position.y - camera.y < Global.SCREENHEIGHT && elm.position.x+elm.width*Global.SCALE - camera.x > 0 && elm.position.y+elm.height*Global.SCALE - camera.y > 0)
                elm.render(g2d);
        }
    }

    public void update(double deltaTime){
        camera.update(deltaTime);
        for(Element elm : elements){
            elm.update(deltaTime);
        }
        //organizar a lista de elementos por ordem de Y
        elements.sort(Comparator.comparingInt(a -> (int) a.getFeetCenterY()));
    }
}
