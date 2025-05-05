package game;

import javax.swing.*; // importa todos os recursos da biblioteca Swing do Java

public class GameFrame extends JFrame { // A classe GameFrame extende JFrame

    /*============ ATRIBUTOS ============*/
    GamePanel gp = new GamePanel();  // Instância do tipo GamePanel

    /*============ CONSTRUTOR ============*/
    public GameFrame()
    {
        setTitle("Game2D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(gp);
        pack();
        gp.createBufferStrategy(3);
        gp.game.start();
        setResizable(true);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
    A classe GameFrame será usada como o "Quadro" em que o jogo será pintado, e a "pintura" deste quadro será a
    classe GamePanel, responsável por desenhar os elementos.

    - Classes usadas -
    Classe BufferStrategy: É uma classe da biblioteca AWT em Java especializada em gerenciar buffers. Como funciona: Basicamente
    todos os desenhos são pintados em segundo plano, e mostrados todos de uma vez, o que evita tearing dos tiles por exemplo,
    tornando a experiência muito mais consistente pro jogador.
    */
}
