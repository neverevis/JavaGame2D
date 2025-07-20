package core;

import math.Vector;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Mouse implements MouseListener, MouseMotionListener{

    private Core gp;
    public static boolean mouseClicked = false;
    public static boolean mouseReleased = false;
    Vector pos = new Vector();

    public int clickedX = 0;
    public int clickedY = 0;



    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked = true;
        clickedX = e.getX();
        clickedY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = false;
        mouseReleased = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        pos.x = e.getX();
        pos.y = e.getY();
    }
}
