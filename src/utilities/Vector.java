package utilities;

public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
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

    public void setPosition(double x, double y){
        setX(x);
        setY(y);
    }

    public void setPosition(Vector vector){
        setX(vector.x);
        setY(vector.y);
    }

    public double getDistance(Vector target){
        double dx = MathUtils.getDelta(this.x ,target.x);
        double dy = MathUtils.getDelta(this.y, target.y);

        return Math.sqrt((dx*dx) + (dy*dy));
    }

    public Vector normalize(){
        double vectorLength = getDistance(new Vector(0,0));

        if(vectorLength == 0){
            return new Vector(0,0);
        }

        return new Vector(x/vectorLength,y/vectorLength);
    }

    public void add(Vector vector){
        this.x += vector.x;
        this.y += vector.y;
    }

    public void add(double x, double y){
        this.x += x;
        this.y += y;
    }

    public void subtract(Vector vector){
        this.x -= vector.x;
        this.y -= vector.y;
    }

    public Vector multiply(double value){
        return new Vector(this.x * value, this.y * value);
    }

    public void applyDirection(Vector target, double multiplier){
        Vector direction = target.getDeltaVector(this).normalize();
        this.x += direction.x * multiplier;
        this.y += direction.y * multiplier;
    }

    public void applyOppositeDirection(Vector target, double multiplier){
        applyDirection(target, -multiplier);
    }

    public Vector getDeltaVector(Vector vector){
        return new Vector(this.x - vector.x, this.y - vector.y);
    }
}
