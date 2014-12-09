package com.wildsmith.tank.utils;

import java.util.List;

import android.graphics.RectF;

import com.wildsmith.tank.objects.ViewObject;

public class IntersectionHelper {

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

    public static boolean isIntersectingWithViewObject(ViewObject viewObject, RectF bounds) {
        boolean isIntersecting = false;
        if (viewObject == null) {
            return isIntersecting;
        }

        return isIntersecting(bounds, viewObject.getBounds());
    }

    public static boolean isIntersectingWithViewObjects(List<? extends ViewObject> viewObjects, RectF bounds) {
        boolean isIntersecting = false;
        if (viewObjects == null || viewObjects.isEmpty()) {
            return isIntersecting;
        }

        for (ViewObject viewObject : viewObjects) {
            if (isIntersecting) {
                break;
            }

            isIntersecting = isIntersecting(bounds, viewObject.getBounds());
        }

        return isIntersecting;
    }
}