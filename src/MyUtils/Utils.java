package MyUtils;

import java.util.ArrayList;
import java.util.Random;

public class Utils {
    private static Random r = new Random();
    public static int getRandomInt(int start, int end){
        int result = 0;
        try{
            result = r.nextInt(end - start) + start;
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return result;
    }

    public static boolean drawDecision(double chance){
        return r.nextDouble() < chance;
    }

    public static ArrayList<Integer> makeDeepCopyInteger(ArrayList<Integer> old){
        ArrayList<Integer> copy = new ArrayList<>(old.size());
        copy.addAll(old);
        return copy;
    }
}
