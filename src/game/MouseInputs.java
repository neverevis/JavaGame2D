package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseInputs implements MouseListener, MouseMotionListener{

    /*=========== ATRIBUTOS ===========*/
    private GamePanel gp;
    public boolean mouseClicked = false;

    public int x = 0;
    public int y = 0;

    /*=========== MÉTODOS DA INTERFACE IMPLEMENTADA ===========*/

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) { // Mouse foi pressionado
        mouseClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) { // Mouse foi solto
        mouseClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
