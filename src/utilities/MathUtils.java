package utilities;

public class MathUtils {

    public static double getDelta(double a, double b){
        return a - b;
    }

    public static double clamp(double min, double max, double value){
        return Math.max(min,Math.min(max,value));
    }
}
