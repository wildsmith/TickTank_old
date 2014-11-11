package com.wildsmith.tank.utils;


public class TimeHelper {

    public static final float FRAME_RATE = 60.0f;

    public static float secondsToFrameDelta(float seconds) {
        return seconds * FRAME_RATE;
    }

    public static float millisToFrameDelta(long milliseconds) {
        return secondsToFrameDelta(milliseconds / 1000.0f);
    }
}