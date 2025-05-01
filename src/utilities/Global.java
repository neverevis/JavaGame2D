package utilities;

public class Global {
    public static final double FPS = 30;
    public static final double ORIGINAL_TILESIZE = 32;
    public static final double SCALE = 3;
    public static final int TILESIZE = (int)(ORIGINAL_TILESIZE*SCALE);
    public static final int GAMECOLS = 16;
    public static final int GAMEROWS = 9;
    public static final int SCREENWIDTH = GAMECOLS*TILESIZE;
    public static final int SCREENHEIGHT = GAMEROWS*TILESIZE;

    //variáveis pra facilitar a orientação (ordem dos spritesheets)
    public static final int DOWN = 0;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int LEFT = 1;
}
