package utilities;

public class Vector {
    public double x;
    public double y;

    public static Vector ZERO = new Vector(0,0);

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector get(){
        return new Vector(this.x,this.y);
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public Vector setPosition(double x, double y){
        setX(x);
        setY(y);

        return this;
    }

    public Vector setPosition(Vector vector){
        setX(vector.x);
        setY(vector.y);

        return this;
    }

    public double getDistance(Vector target){
        double dx = MathUtils.getDelta(this.x ,target.x);
        double dy = MathUtils.getDelta(this.y, target.y);

        return Math.sqrt((dx*dx) + (dy*dy));
    }

    public Vector normalize(){
        double vectorLength = getDistance(ZERO);

        if(vectorLength == 0){
            setPosition(0,0);
            return this;
        }

        setPosition(x/vectorLength,y/vectorLength);

        return this;
    }

    public Vector add(Vector vector){
        this.x += vector.x;
        this.y += vector.y;

        return this;
    }

    public Vector add(double x, double y){
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector subtract(Vector vector){
        this.x -= vector.x;
        this.y -= vector.y;

        return this;
    }

    public Vector multiply(double value){
        this.setPosition(this.x * value, this.y * value);

        return this;
    }

    public Vector applyDirection(Vector target, double multiplier){
        Vector direction = target.get();
        direction.subtract(this);
        direction.normalize();
        this.x += direction.x * multiplier;
        this.y += direction.y * multiplier;

        return this;
    }

    public Vector applyOppositeDirection(Vector target, double multiplier){
        applyDirection(target, -multiplier);

        return this;
    }
}
