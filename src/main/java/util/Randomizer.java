package util;

import java.util.Random;

public class Randomizer {
    private Random random;

    public Randomizer(){
        random = new Random();
    }

    public int getIntFromZeroTo(int upper){
        return Math.abs(random.nextInt()) % upper;
    }

    public int getIntFromTo(int from, int upper){
        assert from < upper : "Lower limit shouldn'n more that upper limit";
        return getIntFromZeroTo(upper - from) + from;
    }

    public float getFloatFromZeroTo(float upper){
        return random.nextFloat() * upper;
    }

    public float getFloatFromTo(float from, float upper){
        return getFloatFromZeroTo(upper - from) + from;
    }

    public double getDoubleFromZeroTo(double upper){
        return random.nextFloat() * upper;
    }

    public double getDoubleFromTo(double from, double upper){
        return getDoubleFromZeroTo(upper - from) + from;
    }

    public double doubleSign(double percent){
        return getDoubleFromZeroTo(100) < percent? -1 : 1;
    }

}
