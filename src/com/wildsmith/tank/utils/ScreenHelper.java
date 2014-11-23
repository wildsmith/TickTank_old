package com.wildsmith.tank.utils;


public class ScreenHelper {

    public static boolean isInYPlane(float position, float size, int screenBottom) {
        return screenBottom - size < position && position < 0 + size;
    }

    public static boolean isOffScreenTop(float position, float size) {
        return position > 0 + size;
    }

    public static boolean isOffScreenBottom(float position, float size, int screenBottom) {
        return position < screenBottom - size;
    }

    public static boolean isInXPlane(float position, float size, int screenWidth) {
        return 0 - size < position && position < screenWidth + size;
    }

    public static boolean isOffScreenLeft(float position, float size) {
        return position < 0 - size;
    }

    public static boolean isOffScreenRight(float position, float size, int screenWidth) {
        return position > screenWidth + size;
    }
}