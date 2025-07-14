package math;

import utilities.MathUtils;

public class Vector {
    public double x;
    public double y;

    public static Vector ZERO = new Vector();

    public Vector(){
        this.x = 0;
        this.y = 0;
    }

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector reset(){
        this.x = 0;
        this.y = 0;

        return this;
    }

    public Vector copy(){
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

    public Vector set(double x, double y){
        setX(x);
        setY(y);

        return this;
    }

    public Vector set(Vector vector){
        setX(vector.x);
        setY(vector.y);

        return this;
    }

    public double getDistance(Vector target){
        double dx = MathUtils.getDelta(this.x ,target.x);
        double dy = MathUtils.getDelta(this.y, target.y);

        return Math.sqrt((dx*dx) + (dy*dy));
    }

    public double length(){
        return Math.sqrt( x * x + y * y );
    }

    public Vector normalize(){
        double vectorLength = getDistance(ZERO);

        if(vectorLength == 0){
            set(0,0);
            return this;
        }

        set(x/vectorLength,y/vectorLength);

        return this;
    }

    public Vector add(Vector vector){
        this.x += vector.x;
        this.y += vector.y;

        return this;
    }

    public Vector sub(Vector vector){
        this.x -= vector.x;
        this.y -= vector.y;

        return this;
    }

    public Vector multiply(double value){
        this.set(this.x * value, this.y * value);

        return this;
    }

    public Vector clamp(double max){
        if(this.getDistance(Vector.ZERO) > max){
            this.normalize().multiply(max);
        }

        return this;
    }

    public Vector applyDirection(Vector target, double multiplier){
        Vector direction = target.copy();
        direction.sub(this);
        direction.normalize();
        this.x += direction.x * multiplier;
        this.y += direction.y * multiplier;

        return this;
    }

    public Vector applyOppositeDirection(Vector target, double multiplier){
        applyDirection(target, -multiplier);

        return this;
    }

    public double cross(Vector vector){
        return this.x * vector.y - this.y * vector.x;
    }
}
