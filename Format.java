package YNWA;

public class Format {
    public static String formatSpeed(double speed) {
        return "     Speed: "+ ((int) (speed * 50)+"%");
    }
}
