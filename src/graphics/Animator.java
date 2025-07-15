package graphics;

public class Animator {
    Sprite sprite;
    int start, end, row, frame;
    double duration, changeRatio, elapsed;

    public Animator(Sprite sprite,int row, int start, int end, double duration){
        this.sprite = sprite;
        this.row = row;
        this.start = start;
        this.end = end;
        this.duration = duration;

        changeRatio = duration / (end - start);
    }

    public void update(double dt){
        elapsed += dt;
        if(sprite.row != row){
            sprite.row = row;
            frame = start;
            elapsed = 0;
        }

        if(elapsed >= changeRatio){
            elapsed = 0;
            frame++;
        }

        if(frame > end){
            frame = start;
        }

        sprite.col = frame;
    }
}
