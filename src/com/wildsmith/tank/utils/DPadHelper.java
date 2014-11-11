package com.wildsmith.tank.utils;

import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class DPadHelper {

    public static final int UP = 0;

    public static final int LEFT = 1;

    public static final int RIGHT = 2;

    public static final int DOWN = 3;

    public final static int CENTER = 4;

    public static int getDirectionPressed(InputEvent event) {
        if (!isDPadHelperDevice(event)) {
            return -1;
        }

        // If the input event is a MotionEvent, check its hat axis values.
        if (event instanceof MotionEvent) {
            // Use the hat axis value to find the D-pad direction
            MotionEvent motionEvent = (MotionEvent) event;
            float xaxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
            float yaxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);

            // Check if the AXIS_HAT_X value is -1 or 1, and set the D-pad
            // LEFT and RIGHT direction accordingly.
            if (Float.compare(xaxis, -1.0f) == 0) {
                return KeyEvent.KEYCODE_DPAD_LEFT;
            } else if (Float.compare(xaxis, 1.0f) == 0) {
                return KeyEvent.KEYCODE_DPAD_RIGHT;
            }
            // Check if the AXIS_HAT_Y value is -1 or 1, and set the D-pad
            // UP and DOWN direction accordingly.
            else if (Float.compare(yaxis, -1.0f) == 0) {
                return KeyEvent.KEYCODE_DPAD_UP;
            } else if (Float.compare(yaxis, 1.0f) == 0) {
                return KeyEvent.KEYCODE_DPAD_DOWN;
            }
        } else if (event instanceof KeyEvent) {
            // If the input event is a KeyEvent, check its key code.

            // Use the key code to find the D-pad direction.
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                return DPadHelper.LEFT;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                return DPadHelper.RIGHT;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                return DPadHelper.UP;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                return DPadHelper.DOWN;
            } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
                return DPadHelper.CENTER;
            }
        }

        return -1;
    }

    public static boolean isDPadHelperDevice(InputEvent event) {
        // Check that input comes from a device with directional pads.
        if ((event.getSource() & InputDevice.SOURCE_DPAD) != InputDevice.SOURCE_DPAD) {
            return true;
        } else {
            return false;
        }
    }
}