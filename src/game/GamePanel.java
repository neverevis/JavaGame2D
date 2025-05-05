package game;

// Importa as outras classes do projeto
import elements.Element;
import elements.entities.Player;
import utilities.Global;
import world.Camera;
import world.World;

import javax.imageio.ImageIO;                   // Importa a classe ImageIO, usada para ler e escrever arquivos de imagem (como PNG, JPG, etc).
import java.awt.*;                              // Importa todo o conteudo da biblioteca AWT
import java.awt.image.BufferStrategy;           // Importa a classe de BufferStrategy, usada na classe GameFrame
import java.awt.image.BufferedImage;            // Importa a classe BufferedImage que representa uma imagem que pode ser manipulada pixel a pixel na memória.
import java.io.IOException;                     // Importa a classe IOException, usada para tratar erros que podem ocorrer durante operações de entrada/saída (como ler imagens de arquivos).
import java.util.ArrayList;                     // Biblioteca de ArrayList
import java.util.List;                          // Biblioteca Lista do Java

public class GamePanel extends Canvas  // Classe GamePanel extende Canvas
{
    /*========== ATRIBUTOS ==========*/
    GameLoop gl;                                                                    // Instância do tipo GameLoop

    public KeyHandler kh = new KeyHandler();                                        // Objeto do tipo KeyHandler, que serve para os inputs do usuário no jogo

    public MouseInputs mI = new MouseInputs();

    public World world = new World(this);                                           // Objeto do tipo World     // Objeto do tipo Player

    Cursor c;                           // Atributo do tipo Cursor, que será usado para tratar do Mouse na janela gráfica

    public Point cursorPoint;                  // Atributo do tipo Point, que vai tratar o clicker do mouse na janela gráfica

    BufferedImage mouseImg;             // Atributo do tipo BufferedImage, usado para gerenciar as imagens e sprites

    Thread game;                        // A thread que fará o jogo rodar

    /*========== CONSTRUTOR ==========*/

    public GamePanel()
    {
        setPreferredSize(new Dimension(Global.SCREENWIDTH,Global.SCREENHEIGHT));  // Tamanho do Canvas
        setBackground(Color.black); // Fundo preto
        setFocusable(true); // Habilita inputs
        addKeyListener(kh); // Adiciona um leitor
        addMouseListener(mI);
        addMouseMotionListener(mI);
        // Carrega a textura do cursor
        try{
            mouseImg = ImageIO.read(getClass().getResourceAsStream("/resources/cursor/cursor.png"));
        } catch (IOException e){}

        //cria um cursor customizado INVISIVEL
        c = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),new Point(0,0),"cursor");
        /*Toolkit é uma classe cuja função é gerenciar recursos do sistema operacional, um deles por exemplo, o mouse.*/

        //seta o cursor invisivel para este componente (Canvas)
        setCursor(c);

        // Instanciado o objeto do GameLoop e iniciada a Thread.
        gl = new GameLoop(this);
        game = new Thread(gl);
    }

    /*========== MÉTODOS ==========*/

    // Render é chamado manualmente, e não pela máquina
    public void render() {
        //obtem o bufferStrategy do componente
        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics(); // Criação do pincel que será usado para pintar os sprites

        //começa a desenhar na imagem vazia
        g2d.setColor(Color.black);
        g2d.fillRect(0,0,getWidth(),getHeight());

        //renderizando o mundo e seus elementos
        world.render(g2d);

        // Definindo a string desenhada pelo pincel
        if(world.showElementsAnchor) {
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.setColor(Color.WHITE);
            g2d.drawString("FPS: " + String.valueOf(gl.finalFps), 10, 40);
        }

        //obter as coordenadas do mouse em relação e este componente e salvar em um objeto Point que tem x e y
        cursorPoint = getMousePosition();

        //se o cursor estiver na tela ele desenha nossa textura do mouse na posição do cursor
        if(cursorPoint != null)
            //basicamente desenha um cursor na posição dele menos a metade do tamanho da imagem que ele representa (pro centro da imagem ser o clique)
            g2d.drawImage(mouseImg,(int)(cursorPoint.x-mouseImg.getWidth()* Global.SCALE /2),(int)(cursorPoint.y-mouseImg.getHeight()* Global.SCALE/2),(int)(mouseImg.getWidth()*Global.SCALE), (int)(mouseImg.getHeight()*Global.SCALE), null);

        g2d.dispose();                                          // Liberando o desenho do pincel
        bufferStrategy.show();                                  // Liberando a imagem que foi pintada nos buffers
        Toolkit.getDefaultToolkit().sync();                     // Sincronizar com os recursos operacionais
    }

    public void update(double deltaTime){
        world.update(deltaTime);
    }
}
