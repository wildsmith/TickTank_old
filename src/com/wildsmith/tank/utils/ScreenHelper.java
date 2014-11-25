package com.wildsmith.tank.utils;

public class ScreenHelper {

    public static boolean isInYPlane(float position, float size, int screenBottom) {
        final boolean isHigherThanBottom = screenBottom - size >= position;
        final boolean isLowerThanTop = position >= 0;
        return isHigherThanBottom && isLowerThanTop;
    }

    public static boolean isOffScreenTop(float position, float size) {
        return position > size;
    }

    public static boolean isOffScreenBottom(float position, float size, int screenBottom) {
        return position < screenBottom - size;
    }

    public static boolean isInXPlane(float position, float size, int screenWidth) {
        final boolean isNotBeyondLeftEdge = position >= 0;
        final boolean isNotBeyondRightEdge = position <= screenWidth - size;
        return isNotBeyondLeftEdge && isNotBeyondRightEdge;
    }

    public static boolean isOffScreenLeft(float position, float size) {
        return position < size;
    }

    public static boolean isOffScreenRight(float position, float size, int screenWidth) {
        return position > screenWidth + size;
    }
}