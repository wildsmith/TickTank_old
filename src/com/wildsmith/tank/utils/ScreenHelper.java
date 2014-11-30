package com.wildsmith.tank.utils;

import android.graphics.RectF;

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

    /**
     * Check should be used to find out if this object has been hit or will hit another object.
     * Logic for handling that interaction should be done here. NO drawing should occur in this
     * method.
     * 
     * @param object
     */
    public static boolean isIntersecting(RectF boundsOne, RectF boundsTwo) {
        if (boundsOne == null || boundsTwo == null) {
            return false;
        }

        return boundsOne.intersect(boundsTwo);
    }
}