package com.wildsmith.tank.utils;

import android.annotation.SuppressLint;
import android.util.SparseBooleanArray;

@SuppressLint("RtlHardcoded")
public class MovementTracker {

    public static int LEFT = 0;

    public static int RIGHT = 1;

    public static int UP = 2;

    public static int DOWN = 3;

    private SparseBooleanArray tracker;

    public MovementTracker() {
        tracker = new SparseBooleanArray(4);
    }

    public boolean isMoving(int direction) {
        return tracker.get(direction);
    }

    public void setMoving(int direction, boolean isMoving) {
        tracker.put(direction, isMoving);
    }

    public void flipMovement() {
        SparseBooleanArray newTracker = new SparseBooleanArray(4);

        if (isMoving(MovementTracker.UP)) {
            newTracker.put(DOWN, true);
        } else if (isMoving(MovementTracker.DOWN)) {
            newTracker.put(UP, true);
        }

        if (isMoving(MovementTracker.LEFT)) {
            newTracker.put(RIGHT, true);
        } else if (isMoving(MovementTracker.RIGHT)) {
            newTracker.put(LEFT, true);
        }

        tracker = newTracker;
    }
}