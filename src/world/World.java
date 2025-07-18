package world;

import core.Core;
import core.G;
import elements.*;
import graphics.GraphicsFX;
import graphics.ImageManager;
import graphics.Renderable;
import physics.CollisionSystem;

import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class World implements Renderable{
    public Camera camera;
    Tiles tiles = new Tiles();

    public Core core;
    public int cols;
    public int rows;
    public int width, height;
    public int[][] world;

    public CopyOnWriteArrayList<Element> elements = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<ELM_Player> players = new CopyOnWriteArrayList<>();

    public CollisionSystem collSys = new CollisionSystem();

    public ELM_Player player;

    public World(String path, Core core){
        BufferedImage tileData = ImageManager.load(path);
        this.core = core;

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
                else if(hexRGB.equalsIgnoreCase("323c39")){
                    world[y][x] = 4;
                }
                else if(hexRGB.equalsIgnoreCase("639284")){
                    world[y][x] = 5;
                }
                else if(hexRGB.equalsIgnoreCase("3f7565")){
                    world[y][x] = 6;
                }
                else if(hexRGB.equalsIgnoreCase("257960")){
                    world[y][x] = 7;
                }
            }
        }

        player = new ELM_Player(this,true);
        player.pos.set((double)cols/2*G.TILESIZE,(double)rows/2*G.TILESIZE);
        elements.add(player);
        players.add(player);
        //elements.add(new ELM_Tree(this));
        elements.add(new ELM_Pillar(this,25,150));
        elements.add(new ELM_Pillar(this,75,150));
        elements.add(new ELM_Pillar(this,125,150));
        elements.add(new ELM_Box(this));
        elements.add(new ELM_Emmiter(this,player.pos,0.6,0,16,15,1.2,0,-10,0.1));
        collSys.display(true);

        camera = new Camera(this);
        camera.pos.set(player.pos.copy());
    }

    public void update(double dt){
        camera.update(dt);
        for(Element elm : elements)
            elm.update(dt);
        elements.sort(null);
        collSys.update();
    }

    @Override
    public void render(GraphicsFX gfx){
        gfx.save();
        gfx.translate(-camera.pos.x,-camera.pos.y);

        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                double tileX = j* 32.0;
                double tileY = i* 32.0;

                if(tileX - camera.pos.x> - G.TILESIZE && tileX - camera.pos.x < G.S_WIDTH && tileY - camera.pos.y > - G.TILESIZE && tileY - camera.pos.y < G.S_HEIGHT) {
                    tiles.drawTile(gfx, world[i][j], tileX, tileY);
                }
            }
        }

        for(Element elm : elements){
            elm.render(gfx);
        }

        collSys.render(gfx);

        gfx.restore();
    }

    @Override
    public double getZIndex() {
        return 0;
    }

    @Override
    public int getLayer() {
        return 0;
    }
}
