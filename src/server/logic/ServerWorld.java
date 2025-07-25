package server.logic;

import elements.Element;
import graphics.ImageManager;
import physics.CollisionSystem;
import server.Server;
import world.Tiles;

import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerWorld{
    Server server;
    public CopyOnWriteArrayList<S_Player> players = new CopyOnWriteArrayList<>();
    Tiles tiles = new Tiles();
    int[][] world;
    int cols;
    int rows;

    public CollisionSystem collSys = new CollisionSystem();

    public ServerWorld(Server server){
        this.server = server;
        BufferedImage tileData = ImageManager.load("/resources/tileData3.png");

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

                world[y][x] = tiles.getCode(hexRGB);
            }
        }

        S_Barrier barrier = new S_Barrier(this,0,32,32 * cols, 32 * 2);

        for(int i = 0; i < 15; i++){
            new S_Pillar(this,32 + 64*i,64);
        }
    }
    public void update(double dt){
        collSys.update();
        for(S_Player p : players){
            p.update(dt);
        }
    }
}
