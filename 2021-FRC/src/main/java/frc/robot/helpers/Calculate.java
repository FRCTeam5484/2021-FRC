package frc.robot.helpers;

public class Calculate {
    public static boolean isBetween(double x, double lower, double upper) {
        return lower <= x && x <= upper;
    }
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
    public static boolean isSafe(double speed, double position, double lowLimit, double highLimit){
        return (speed < 0 && position > lowLimit) || (speed > 0 && position < highLimit); 
    }
}
