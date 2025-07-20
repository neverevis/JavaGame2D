package core;

import graphics.GraphicsFX;
import graphics.ImageManager;
import graphics.RenderSystem;
import gui.MENU_Start;
import server.Client;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Core extends Canvas
{
    public double secondCounter;
    public int frameCounter;
    public int frameRate;

    public Key key = new Key();
    public Mouse mouse = new Mouse();
    public World world = new World("/resources/tileData3.png",this);
    public MENU_Start menuStart = new MENU_Start(this);

    public AnimationTimer animationTimer;
    public RenderSystem renSys = new RenderSystem();
    public Point cursorPoint;
    BufferedImage mouseImg;

    Client client = new Client(this);
    GraphicsFX gfx = new GraphicsFX(this);

    TextField text = new TextField("");

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
        renSys.register(menuStart);
        //key.setTextField(text);

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
        menuStart.update();
    }

    public void render() {
        gfx.begin();

        gfx.clear();
        gfx.save();

        renSys.render(gfx);

        gfx.restore();

        gfx.setColor(Color.WHITE);
        gfx.draw(text.text,(double)G.S_WIDTH/2,(double)G.S_HEIGHT/2);

        gfx.setColor(Color.WHITE);
        gfx.draw("FPS: " +  frameRate,5,25);

        gfx.end();
    }

    public void hideCursor(){
        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),new Point(0,0),"cursor");
        setCursor(c);
    }
}
