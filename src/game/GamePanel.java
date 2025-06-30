package game;

import elements.states.GameState;
import gui.MainMenu;
import server.Client;
import utilities.Global;
import world.World;

import javax.imageio.ImageIO;                   // Importa a classe ImageIO, usada para ler e escrever arquivos de imagem (como PNG, JPG, etc).
import java.awt.*;                              // Importa todo o conteudo da biblioteca AWT
import java.awt.image.BufferStrategy;           // Importa a classe de BufferStrategy, usada na classe GameFrame
import java.awt.image.BufferedImage;            // Importa a classe BufferedImage que representa uma imagem que pode ser manipulada pixel a pixel na memória.
import java.io.IOException;                     // Importa a classe IOException, usada para tratar erros que podem ocorrer durante operações de entrada/saída (como ler imagens de arquivos).


public class GamePanel extends Canvas
{
    GameLoop gl;

    public KeyHandler kh = new KeyHandler();
    public MouseInputs mouseInput = new MouseInputs();
    public Client client = new Client(this);
    public Thread clientThread = new Thread(client);
    public World activeWorld = new World(this,"/resources/tileData.png");
    Cursor c;
    public Point cursorPoint;
    BufferedImage mouseImg;
    Thread game;
    public GameState gameState = GameState.MENU;
    MainMenu mainMenu = new MainMenu(this);

    public GamePanel()
    {
        setPreferredSize(new Dimension(Global.SCREENWIDTH,Global.SCREENHEIGHT));
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
        clientThread.start();
    }

    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();

        g2d.setColor(Color.black);
        g2d.fillRect(0,0,getWidth(),getHeight());

        if(gameState == GameState.MENU){
            g2d.setColor(Color.blue);
            g2d.fillRect(0,0,getWidth(),getHeight());
            mainMenu.render(g2d);
        }

        if(gameState == GameState.INWORLD){
            activeWorld.render(g2d);

            if(activeWorld.showElementsAnchor) {
                g2d.setFont(new Font("Arial", Font.BOLD, 30));
                g2d.setColor(Color.WHITE);
                g2d.drawString("FPS: " + String.valueOf(gl.finalFps), 10, 40);
            }
        }
        cursorPoint = getMousePosition();

        if(cursorPoint != null)
            g2d.drawImage(mouseImg,(int)(cursorPoint.x-mouseImg.getWidth()* Global.SCALE /2),(int)(cursorPoint.y-mouseImg.getHeight()* Global.SCALE/2),(int)(mouseImg.getWidth()*Global.SCALE), (int)(mouseImg.getHeight()*Global.SCALE), null);

        g2d.dispose();
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void update(double deltaTime){
        if(gameState == GameState.MENU){
            mainMenu.update(deltaTime, cursorPoint);
        }
        if(gameState == GameState.INWORLD){
            if(kh.escapeKey)
                gameState = GameState.MENU;
            activeWorld.update(deltaTime);
        }

        //enviar atualização para o cliente enviar pro servidor
        if(client.connected){
            client.updatePosition(activeWorld.player.position.x, activeWorld.player.position.y);
            client.updateDirection(activeWorld.player.sprite.direction);

            if(activeWorld.player.sprite.moving){
                client.updateState(1);
            }else{
                client.updateState(0);
            }
        }
    }
}
