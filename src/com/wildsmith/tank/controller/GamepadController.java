package com.wildsmith.tank.controller;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GamepadController {

    /**
     * Positions of the two joy sticks for the current and previous frames.
     */
    private float mJoystickPositions[][];

    /**
     * The button states for the current and previous frames.
     */
    private Map<Integer, Boolean> mButtonState;

    /**
     * The d pad states for the current and previous frames.
     */
    private Map<Integer, Boolean> mDPadState;

    /**
     * The device that we are tuned to.
     */
    private int mDeviceId = -1;

    @SuppressLint("UseSparseArrays")
    public GamepadController() {
        mDPadState = new HashMap<Integer, Boolean>(5);
        mButtonState = new HashMap<Integer, Boolean>(8);
        mJoystickPositions = new float[GamepadConstants.JOYSTICK_COUNT][GamepadConstants.AXIS_COUNT];
    }

    private void resetState() {
        for (int dpad = 0; dpad < GamepadConstants.DPAD_COUNT; dpad++) {
            mDPadState.put(dpad, false);
        }

        for (int button = 0; button < GamepadConstants.BUTTON_COUNT; button++) {
            mButtonState.put(button, false);
        }

        for (int joystick = 0; joystick < GamepadConstants.JOYSTICK_COUNT; joystick++) {
            for (int axis = 0; axis < GamepadConstants.AXIS_COUNT; axis++) {
                mJoystickPositions[joystick][axis] = 0.0f;
            }
        }
    }

    public void handleMotionEvent(MotionEvent motionEvent) {
        mDPadState.put(GamepadConstants.DPAD_LEFT, false);
        mDPadState.put(GamepadConstants.DPAD_RIGHT, false);
        mDPadState.put(GamepadConstants.DPAD_UP, false);
        mDPadState.put(GamepadConstants.DPAD_DOWN, false);
        mButtonState.put(GamepadConstants.BUTTON_R2, false);
        mButtonState.put(GamepadConstants.BUTTON_L2, false);

        float rTrigger = motionEvent.getAxisValue(MotionEvent.AXIS_RTRIGGER);
        float lTrigger = motionEvent.getAxisValue(MotionEvent.AXIS_LTRIGGER);
        float xAxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_X);
        float yAxis = motionEvent.getAxisValue(MotionEvent.AXIS_HAT_Y);
        if (Float.compare(xAxis, -1.0f) == 0) {
            mDPadState.put(GamepadConstants.DPAD_LEFT, true);
        } else if (Float.compare(xAxis, 1.0f) == 0) {
            mDPadState.put(GamepadConstants.DPAD_RIGHT, true);
        } else if (Float.compare(yAxis, -1.0f) == 0) {
            mDPadState.put(GamepadConstants.DPAD_UP, true);
        } else if (Float.compare(yAxis, 1.0f) == 0) {
            mDPadState.put(GamepadConstants.DPAD_DOWN, true);
        } else if (rTrigger > 0) {
            mButtonState.put(GamepadConstants.BUTTON_R2, true);
        } else if (lTrigger > 0) {
            mButtonState.put(GamepadConstants.BUTTON_L2, true);
        } else {
            mJoystickPositions[GamepadConstants.JOYSTICK_1][GamepadConstants.AXIS_X] = motionEvent.getAxisValue(MotionEvent.AXIS_X);
            mJoystickPositions[GamepadConstants.JOYSTICK_1][GamepadConstants.AXIS_Y] = motionEvent.getAxisValue(MotionEvent.AXIS_Y);

            mJoystickPositions[GamepadConstants.JOYSTICK_2][GamepadConstants.AXIS_X] = motionEvent.getAxisValue(MotionEvent.AXIS_Z);
            mJoystickPositions[GamepadConstants.JOYSTICK_2][GamepadConstants.AXIS_Y] = motionEvent.getAxisValue(MotionEvent.AXIS_RZ);
        }
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        boolean keyIsDown = keyEvent.getAction() == KeyEvent.ACTION_DOWN;

        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            mDPadState.put(GamepadConstants.DPAD_LEFT, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            mDPadState.put(GamepadConstants.DPAD_RIGHT, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            mDPadState.put(GamepadConstants.DPAD_UP, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            mDPadState.put(GamepadConstants.DPAD_DOWN, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            mDPadState.put(GamepadConstants.DPAD_CENTER, keyIsDown);
        }

        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_A) {
            mButtonState.put(GamepadConstants.BUTTON_A, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_B) {
            mButtonState.put(GamepadConstants.BUTTON_B, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_X) {
            mButtonState.put(GamepadConstants.BUTTON_X, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_Y) {
            mButtonState.put(GamepadConstants.BUTTON_Y, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R1) {
            mButtonState.put(GamepadConstants.BUTTON_R1, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R2) {
            mButtonState.put(GamepadConstants.BUTTON_R2, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_L1) {
            mButtonState.put(GamepadConstants.BUTTON_L1, keyIsDown);
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_L2) {
            mButtonState.put(GamepadConstants.BUTTON_L2, keyIsDown);
        }
    }

    public float getJoystickPosition(int joystickIndex, int axis) {
        return mJoystickPositions[joystickIndex][axis];
    }

    public boolean isButtonDown(int buttonId) {
        final Boolean isButtonDown = mButtonState.get(buttonId);
        if (isButtonDown == null) {
            return false;
        }

        return isButtonDown;
    }

    public void setButtonDown(int buttonId, boolean isButtonDown) {
        mButtonState.put(buttonId, isButtonDown);
    }

    public boolean isDPadDown(int dpadId) {
        return mDPadState.get(dpadId);
    }

    public void setDeviceId(int newId) {
        if (newId != mDeviceId && newId != -1) {
            mDeviceId = newId;
            resetState();
        }
    }
}