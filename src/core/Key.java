package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {
    public static boolean W = false, S = false, A = false, D = false, toggleAnchorDisplay = false, escapeKey = false;
    public static boolean SPACE = false;
    public boolean interactKey = false;
    public boolean zoom = false;

    TextField textField;
    Core core;

    @Override
    public void keyTyped(KeyEvent e) {
        if(textField != null && !Character.isISOControl(e.getKeyChar()) && textField.charactersCount <= textField.characterLimit) {
            textField.text += e.getKeyChar();
            textField.charactersCount++;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 32)
            SPACE = true;
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

        if(textField != null && e.getKeyCode() == 8){
            if(!textField.text.isEmpty()){
                textField.text = textField.text.substring(0,textField.text.length()-1);
                textField.charactersCount--;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 32)
            SPACE = false;
        if(e.getKeyCode() == 90)
            zoom = false;
        if(e.getKeyCode() == 69)
            interactKey = false;
        if(e.getKeyCode() == 87)
            W = false;
        if(e.getKeyCode() == 83)
            S = false;
        if(e.getKeyCode() == 65)
            A = false;
        if(e.getKeyCode() == 68)
            D = false;
    }

    public void setTextField(TextField textField){
        this.textField = textField;
    }

    public void removeTextField(){
        this.textField = null;
    }
}
