import core.GameFrame;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        System.out.println("OpenGL pipeline ativo? " + javax.swing.RepaintManager.currentManager(null).isDoubleBufferingEnabled());
        new GameFrame();
    }
}