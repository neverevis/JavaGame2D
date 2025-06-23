package world;

import elements.Dust;
import elements.Element;
import elements.entities.Player;
import elements.entities.Slime;
import elements.enviroment.Fence;
import elements.enviroment.Tree;
import game.GamePanel;
import server.Client;
import utilities.Global;
import utilities.Sound;
import utilities.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
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
    public Player player;
    public Player connectedPlayer;
    Tree tree;
    Tree tree2;
    Slime slime1;
    Slime slime2;
    Slime slime3;
    Slime slime4;
    Slime slime5;
    Slime slime6;
    Dust dust;
    Fence fence;
    float alpha = 0.0f;
    public boolean pause = false;
    Client client;

    Sound music = new Sound();

    public World(GamePanel gp){
        this.client = gp.client;
        this.gp = gp;
        player = new Player(this.gp,this,client,false);
        connectedPlayer = new Player(this.gp,this,client,true);
        connectedPlayer.setPositionByAnchor(new Vector(600,600));
        dust = new Dust(this.gp,this,player);
        tree = new Tree(this.gp,this);
        fence = new Fence(this.gp,this);
        tree.setPositionByAnchor(new Vector(700,500));
        tree2 = new Tree(this.gp,this);
        slime1 = new Slime(this.gp,this,this.player);
        slime2 = new Slime(this.gp,this,this.player);
        slime3 = new Slime(this.gp,this,this.player);
        slime4 = new Slime(this.gp,this,this.player);
        slime5 = new Slime(this.gp,this,this.player);
        slime6 = new Slime(this.gp,this,this.player);
        slime1.setPositionByAnchor(new Vector(600,700));
        slime2.setPositionByAnchor(new Vector(700,700));
        slime3.setPositionByAnchor(new Vector(800,700));
        slime4.setPositionByAnchor(new Vector(900,700));
        slime5.setPositionByAnchor(new Vector(1000,700));
        slime6.setPositionByAnchor(new Vector(1100,700));
        tree2.setPositionByAnchor(new Vector(500,500));
        fence.setPositionByAnchor(new Vector(1500,1500));
        elements.add(fence);
        elements.add(player);
        elements.add(connectedPlayer);
        elements.add(tree2);
        elements.add(tree);
        elements.add(dust);

        elements.add(slime1);
        /*elements.add(slime2);
        elements.add(slime3);
        elements.add(slime4);
        elements.add(slime5);
        elements.add(slime6);*/
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

        if(pause){
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, Global.SCREENWIDTH, Global.SCREENHEIGHT);

            // resetar alpha depois, se for desenhar mais coisas
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            player.render(g2d);
        }

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("health",20,30);
        g2d.setColor(Color.darkGray);
        g2d.fillRect(20,40,(int)player.maxHealth * 4, 15);
        g2d.setColor(Color.black);
        g2d.drawRect(20,40,(int)player.maxHealth * 4, 15);
        g2d.setColor(Color.orange);
        g2d.fillRect(20,40,(int)player.dealt * 4, 15);
        g2d.setColor(Color.red);
        g2d.fillRect(20,40,(int)player.health * 4, 15);
    }

    public void update(double deltaTime){
        if(client.connected){

        }
        camera.update(deltaTime);
        if(!pause) {
            for (Element elm : elements) {
                elm.update(deltaTime);
            }
        }
        else{
            player.update(deltaTime);
            if(alpha + 1f * deltaTime <= 0.9)
                alpha+= 1f * deltaTime;
        }
        //organizar a lista de elementos por ordem de Y
        elements.sort(Comparator.comparingInt(a -> (int) a.getFeetCenterY()));
    }
}
