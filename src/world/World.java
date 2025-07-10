package world;

import elements.Element;
import elements.entities.Entity;
import elements.entities.Player;
import elements.entities.Slime;
import elements.enviroment.Fence;
import elements.enviroment.Grass;
import elements.enviroment.Pillar;
import elements.enviroment.Tree;
import game.CollisionSystem;
import game.DialogueManager;
import game.GamePanel;
import server.Client;
import utilities.C;
import utilities.Sound;
import utilities.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {
    int cols,rows;
    public int width, height;
    public int[][] world;
    Tiles tiles;
    Random random = new Random();
    public GamePanel gp;
    public Camera camera = new Camera(this);
    public boolean showElementsAnchor = false;
    public List<Element> elements = new CopyOnWriteArrayList<>();
    public List<Entity> entities = new ArrayList<>();
    public List<Player> connectedPlayers = new CopyOnWriteArrayList<>();
    public CollisionSystem collisionSystem = new CollisionSystem();
    public DialogueManager dialogueManager;
    public Player player;
    Tree tree;
    Tree tree2;
    Fence fence;
    Pillar pillar;
    Pillar p2;
    float alpha = 0.0f;
    public boolean pause = false;
    Client client;
    String tilePath;
    BufferedImage tileData;

    Sound music = new Sound();

    public World(GamePanel gp, String tilePath){
        this.client = gp.client;
        this.gp = gp;
        this.dialogueManager = new DialogueManager(gp,this);
        this.tilePath = tilePath;
        if(!gp.isVirtual)
            player = new Player(this.gp,this,false);
        player.setPosition(32/2* C.TILESIZE,32/2* C.TILESIZE);
        p2 = new Pillar(this.gp,this);
        p2.setPosition(1500,1500);
        tree = new Tree(this.gp,this);
        fence = new Fence(this.gp,this);
        tree.setPositionByAnchor(new Vector(700,500));
        tree2 = new Tree(this.gp,this);


        /*for (int i = 0; i < 4; i++) {
            Slime s = new Slime(gp, this, player,false);
            elements.add(s);
            entities.add(s);
            s.setPositionByAnchor(new Vector(70 * i, 900));
        }*/

        double centerX = 22* C.TILESIZE + 10 *8* C.SCALE;
        double centerY = 22* C.TILESIZE + 10 *8* C.SCALE;

        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                double px = 22* C.TILESIZE + j * 8* C.SCALE;
                double py = 22* C.TILESIZE + i * 8* C.SCALE;

                double dx = px - centerX;
                double dy = py - centerY;

                double dist = Math.sqrt(dx*dx + dy*dy);

                if(dist < 10 *8* C.SCALE) {
                    Grass grass = new Grass(gp, this);
                    grass.setPositionByAnchor(new Vector(px, py));
                    elements.add(grass);
                }
            }
        }

        tree2.setPositionByAnchor(new Vector(1000,1900));
        fence.setPositionByAnchor(new Vector(1500,1500));
        elements.add(p2);
        elements.add(fence);
        elements.add(player);
        entities.add(player);
        elements.add(tree2);
        elements.add(tree);

        tiles = new Tiles();
        cols = 100;
        rows = 100;
        width = cols* C.TILESIZE;
        height = rows* C.TILESIZE;
        convertWorld();
    }

    public void convertWorld() {
        try {
            tileData = ImageIO.read(getClass().getResourceAsStream(tilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int w = tileData.getWidth();
        int h = tileData.getHeight();

        cols = tileData.getWidth();
        rows = tileData.getHeight();

        world = new int[cols][rows];

        for(int y = 0; y < tileData.getWidth(); y++){
            for(int x = 0; x < tileData.getHeight(); x++){
                int pixel = tileData.getRGB(x,y);

                int alpha = (pixel >> 24) & 0xFF;
                int red   = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8)  & 0xFF;
                int blue  =  pixel        & 0xFF;

                String hexRGB = String.format("%02X%02X%02X", red, green, blue);

                if(hexRGB.equalsIgnoreCase("99e550")){
                    world[y][x] = 0;
                }
                else if(hexRGB.equalsIgnoreCase("daffb6")){
                    world[y][x] = 1;
                }
                else if(hexRGB.equalsIgnoreCase("585858")){
                    world[y][x] = 3;
                }
            }
        }
    }

    public void render(Graphics2D g2d){
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                double tileX = j* C.TILESIZE - camera.x;
                double tileY = i* C.TILESIZE - camera.y;

                if(tileX > - C.TILESIZE && tileX < C.SCREENWIDTH && tileY > - C.TILESIZE && tileY < C.SCREENHEIGHT) {
                    tiles.drawTile(g2d, world[i][j], (int)tileX, (int)tileY);
                }
            }
        }

        for(Element elm : elements){
            if(elm.position.x - camera.x < C.SCREENWIDTH && elm.position.y - camera.y < C.SCREENHEIGHT && elm.position.x+elm.width* C.SCALE - camera.x > 0 && elm.position.y+elm.height* C.SCALE - camera.y > 0)
                elm.render(g2d);
        }

        if(pause){
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, C.SCREENWIDTH, C.SCREENHEIGHT);

            // resetar alpha depois, se for desenhar mais coisas
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            player.render(g2d);
        }

        dialogueManager.render(g2d);

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
        camera.update(deltaTime);
        dialogueManager.update(deltaTime);
        if(!pause) {
            for (Element elm : elements) {
                elm.update(deltaTime);
            }
            collisionSystem.update();
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
