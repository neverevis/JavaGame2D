package core;

import javax.swing.*;

public class GameFrame extends JFrame {

    Core core = new Core();

    public GameFrame()
    {
        setTitle("");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(core);
        pack();
        setResizable(true);
        core.createBufferStrategy(3);
        core.animationTimer.start();
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
