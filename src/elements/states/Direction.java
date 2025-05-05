package elements.states;

public enum Direction {
    DOWN(0),
    UP(1),
    LEFT(2),
    RIGHT(3);

    public final int code;

    Direction(int code){
        this.code = code;
    }
}
