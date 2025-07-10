package game;

import elements.states.GameState;
import gui.MainMenu;
import server.Client;
import utilities.C;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class GamePanel extends Canvas
{
    GameLoop gl;
    public boolean isVirtual;
    public double time;

    public KeyHandler kh = new KeyHandler();
    public MouseInputs mouseInput = new MouseInputs();
    public Client client;
    public World activeWorld = new World(this,"/resources/tileData.png");
    Cursor c;
    public Point cursorPoint;
    BufferedImage mouseImg;
    public Thread game;
    public GameState gameState = GameState.MENU;
    MainMenu mainMenu = new MainMenu(this);
    Font font = new Font("Arial", Font.BOLD, 30);
    double zoom = 1;
    BufferedImage frameBuffer = new BufferedImage(C.SCREENWIDTH,C.SCREENHEIGHT,BufferedImage.TYPE_INT_ARGB);

    public GamePanel(boolean isVirtual)
    {
        this.isVirtual = isVirtual;
        if(!isVirtual)
            client = new Client(this);
        setPreferredSize(new Dimension(C.SCREENWIDTH, C.SCREENHEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(kh);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        try{
            mouseImg = ImageIO.read(getClass().getResourceAsStream("/resources/cursor/cursor.png"));
        } catch (IOException e){}

        c = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),new Point(0,0),"cursor");
        setCursor(c);

        gl = new GameLoop(this);
        game = new Thread(gl);
    }

    public void render() {

        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        if(zoom > 1) {
            g2d.translate(-(int)(C.SCREENWIDTH*zoom - C.SCREENWIDTH)/2,-(int)(C.SCREENHEIGHT*zoom - C.SCREENHEIGHT)/2);
            g2d.scale(zoom, zoom);
        }

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (gameState == GameState.MENU) {
            g2d.setColor(Color.blue);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            mainMenu.render(g2d);
        }

        if (gameState == GameState.INWORLD) {
            activeWorld.render(g2d);

            if (activeWorld.showElementsAnchor) {
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("FPS: " + String.valueOf(gl.finalFps), 10, 40);
            }
        }
        cursorPoint = getMousePosition();

        if (cursorPoint != null)
            g2d.drawImage(mouseImg, (int) (cursorPoint.x - mouseImg.getWidth() * C.SCALE / 2), (int) (cursorPoint.y - mouseImg.getHeight() * C.SCALE / 2), (int) (mouseImg.getWidth() * C.SCALE), (int) (mouseImg.getHeight() * C.SCALE), null);

        g2d.dispose();
        bufferStrategy.show();
    }

    public void update(double deltaTime){
        time += deltaTime;

        if(gameState == GameState.MENU){
            mainMenu.update(deltaTime, cursorPoint);
        }
        if(gameState == GameState.INWORLD){
            if(kh.zoom){
                if(zoom < 3)
                    zoom += (3 - zoom) * 1 * deltaTime;
            }
            else{
                if(zoom != 1) {
                    zoom -= (zoom - 1) * 4 * deltaTime;
                    if(zoom < 1.001){
                        zoom = 1;
                    }
                }
            }
            if(kh.escapeKey)
                gameState = GameState.MENU;
            activeWorld.update(deltaTime);
        }
    }
}
