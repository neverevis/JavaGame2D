package core;

import graphics.GraphicsFX;
import graphics.ImageManager;
import graphics.RenderSystem;
import server.Client;
import world.World;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Core extends Canvas
{
    public double secondCounter;
    public int frameCounter;
    public int frameRate;

    public Key key = new Key();
    public Mouse mouse = new Mouse();
    public World world = new World("/resources/tileData2.png",this);

    public AnimationTimer animationTimer;
    public RenderSystem renSys = new RenderSystem();
    public Point cursorPoint;
    BufferedImage mouseImg;

    Client client = new Client(this);
    GraphicsFX gfx = new GraphicsFX(this);

    public Core()
    {
        setPreferredSize(new Dimension(G.S_WIDTH, G.S_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        mouseImg = ImageManager.load("/resources/cursor/cursor.png");

        renSys.register(world);

        animationTimer = new AnimationTimer(G.FPS){
            @Override
            public void step(double dt) {
                update(dt);
                render();

                secondCounter += dt;
                frameCounter ++;

                if(secondCounter >= 1.0){
                    frameRate = frameCounter;

                    frameCounter = 0;
                    secondCounter = 0;
                }
            }
        };
    }

    public void update(double dt){
        G.time += dt;
        world.update(dt);
    }

    public void render() {
        gfx.begin();

        gfx.clear();
        gfx.save();

        gfx.scale(G.SCALE);

        renSys.render(gfx);

        gfx.restore();
        gfx.setColor(Color.WHITE);
        gfx.draw("FPS: " +  frameRate,5,25);

        gfx.end();
    }

    public void hideCursor(){
        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),new Point(0,0),"cursor");
        setCursor(c);
    }
}
