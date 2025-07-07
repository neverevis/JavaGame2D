package utilities;

import java.awt.*;

public class Global {
    public static final double FPS = 120;
    public static final double ORIGINAL_TILESIZE = 32;
    public static final double SCALE = 4;
    public static final int TILESIZE = (int)(ORIGINAL_TILESIZE*SCALE);
    public static final int GAMECOLS = 16;
    public static final int GAMEROWS = 9;
    static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    //public static final int SCREENWIDTH = dimension.width;
    //public static final int SCREENHEIGHT = dimension.height;
    public static final int SCREENWIDTH = 800;
    public static final int SCREENHEIGHT = 800;

    //variáveis pra facilitar a orientação (ordem dos spritesheets)
    public static final int DOWN = 0;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int LEFT = 1;
}
