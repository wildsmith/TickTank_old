package com.wildsmith.tank.utils;

public class MathHelpter {

    public static float vector2DLength(float x, float y) {
        return (float) Math.sqrt(vector2DLengthSquared(x, y));
    }
    
    public static float vector2DLengthSquared(float x, float y) {
        return x * x + y * y;
    }
}