package graphics;

import core.Core;
import core.G;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class GraphicsFX {
    Graphics2D g;
    Core core;
    BufferStrategy bufferStrategy;

    Stack<AffineTransform> transformStack = new Stack<>();
    Stack<Composite> compositeStack = new Stack<>();
    Stack<Font> fontStack = new Stack<>();
    Stack<Color> colorStack = new Stack<>();

    public GraphicsFX(Core core){
        this.core = core;
    }

    public void begin(){
        bufferStrategy = core.getBufferStrategy();
        this.g = (Graphics2D) bufferStrategy.getDrawGraphics();
        transformStack.removeAllElements();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        try {
            g.setFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/UI/nokiafc22.ttf")).deriveFont(25f));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void end(){
        g.dispose();
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void save(){
        transformStack.push(g.getTransform());
        compositeStack.push(g.getComposite());
        colorStack.push(g.getColor());
        fontStack.push(g.getFont());
    }

    public void restore(){
        g.setTransform(transformStack.pop());
        g.setComposite(compositeStack.pop());
        g.setColor(colorStack.pop());
        g.setFont(fontStack.pop());
    }

    public void translate(double x, double y){
        g.translate(x,y);
    }

    public void scale(double scale){
        g.scale(scale,scale);
    }

    public void rotate(double degrees){
        g.rotate(Math.toRadians(degrees));
    }

    public void setColor(Color color){
        g.setColor(color);
    }

    public void setTextSize(float size){
        g.setFont(g.getFont().deriveFont(size));
    }

    public void clear(){
        save();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,G.S_WIDTH,G.S_HEIGHT);
        restore();
    }

    public void draw(BufferedImage image, double x, double y){
        AffineTransform at = AffineTransform.getTranslateInstance(x,y);
        g.drawImage(image,at,null);
    }

    public void draw(Sprite sprite, double x, double y){
        BufferedImage spr = sprite.sprite[sprite.row][sprite.col];

        draw(spr,x,y);
    }

    public void draw(Shape shape){
        g.draw(shape);
    }

    public void draw(String string, double x, double y){
        g.drawString(string,(float)x,(float)y);
    }

    public void fillRect(double x, double y, double w, double h){
        g.fill(new Rectangle2D.Double(x,y,w,h));
    }

    public void fillRect(double x, double y, double w, double h, double arcW, double arcH){
        g.fill(new RoundRectangle2D.Double(x,y,w,h,arcW,arcH));
    }

    public void opacity(float opacity){
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
    }

    public double stringWidth(String string){
        FontMetrics fm = g.getFontMetrics();
        return fm.stringWidth(string);
    }
}
