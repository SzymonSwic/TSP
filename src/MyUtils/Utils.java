package MyUtils;

import java.util.Random;

public class Utils {
    private static Random r = new Random();
    public static int getRandomInt(int start, int end){
        return r.nextInt(end - start) + start;
    }

    public static boolean drawDecision(double chance){
        return r.nextDouble() < chance;
    }
}
