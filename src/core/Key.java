package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {
    public static boolean W = false, S = false, A = false, D = false, toggleAnchorDisplay = false, escapeKey = false;
    public boolean interactKey = false;
    public boolean zoom = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 90)
            zoom = true;
        if(e.getKeyCode() == 69)
            interactKey = true;
        if(e.getKeyCode() == 27)
            escapeKey = true;
        if(e.getKeyCode() == 87)
            W = true;
        if(e.getKeyCode() == 83)
            S = true;
        if(e.getKeyCode() == 65)
            A = true;
        if(e.getKeyCode() == 68)
            D = true;

        if(e.getKeyCode() == 80){
            if(!toggleAnchorDisplay)
                toggleAnchorDisplay = true;
            else
                toggleAnchorDisplay = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 90)
            zoom = false;
        if(e.getKeyCode() == 69)
            interactKey = false;
        if(e.getKeyCode() == 27)
            escapeKey = false;
        if(e.getKeyCode() == 87)
            W = false;
        if(e.getKeyCode() == 83)
            S = false;
        if(e.getKeyCode() == 65)
            A = false;
        if(e.getKeyCode() == 68)
            D = false;
    }
}
