package core;

import javax.swing.*;

public class GameFrame extends JFrame {

    Core gp = new Core();

    public GameFrame()
    {
        setTitle("");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(gp);
        pack();
        setResizable(true);
        gp.createBufferStrategy(3);
        gp.animationTimer.start();
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
