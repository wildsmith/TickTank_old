package com.wildsmith.tank.controller;

import android.util.SparseBooleanArray;
import android.view.InputDevice;
import android.view.InputDevice.MotionRange;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GamepadController {

    /**
     * Positions of the two joy sticks
     */
    private float mJoystickPositions[][];

    /**
     * The button states
     */
    private SparseBooleanArray mButtonState;

    /**
     * The d pad states
     */
    private SparseBooleanArray mDPadState;

    /**
     * The trigger states
     */
    private SparseBooleanArray mTriggerState;

    /**
     * The device that we are tuned to.
     */
    private int mDeviceId = -1;

    public GamepadController() {
        mDPadState = new SparseBooleanArray(GamepadConstants.DPAD_COUNT);
        mTriggerState = new SparseBooleanArray(GamepadConstants.TRIGGER_COUNT);
        mButtonState = new SparseBooleanArray(GamepadConstants.BUTTON_COUNT);
        mJoystickPositions = new float[GamepadConstants.JOYSTICK_COUNT][GamepadConstants.AXIS_COUNT];
    }

    private void resetState() {
        for (int dpad = 0; dpad < GamepadConstants.DPAD_COUNT; dpad++) {
            mDPadState.put(dpad, false);
        }

        for (int trigger = 0; trigger < GamepadConstants.TRIGGER_COUNT; trigger++) {
            mTriggerState.put(trigger, false);
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
        mTriggerState.put(GamepadConstants.TRIGGER_L2, false);
        mTriggerState.put(GamepadConstants.TRIGGER_R2, false);

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
        }

        float lTrigger = motionEvent.getAxisValue(MotionEvent.AXIS_LTRIGGER);
        float rTrigger = motionEvent.getAxisValue(MotionEvent.AXIS_RTRIGGER);
        if (lTrigger > 0.30) {
            mTriggerState.put(GamepadConstants.TRIGGER_L2, true);
        }
        if (rTrigger > 0.30) {
            mTriggerState.put(GamepadConstants.TRIGGER_R2, true);
        }

        mJoystickPositions[GamepadConstants.JOYSTICK_1][GamepadConstants.AXIS_X] = getAxisValue(motionEvent, MotionEvent.AXIS_X);
        mJoystickPositions[GamepadConstants.JOYSTICK_1][GamepadConstants.AXIS_Y] = getAxisValue(motionEvent, MotionEvent.AXIS_Y);

        mJoystickPositions[GamepadConstants.JOYSTICK_2][GamepadConstants.AXIS_X] = getAxisValue(motionEvent, MotionEvent.AXIS_Z);
        mJoystickPositions[GamepadConstants.JOYSTICK_2][GamepadConstants.AXIS_Y] = getAxisValue(motionEvent, MotionEvent.AXIS_RZ);
    }

    private float getAxisValue(MotionEvent motionEvent, int axis) {
        InputDevice device = InputDevice.getDevice(mDeviceId);
        if (device == null) {
            return 0f;
        }

        MotionRange range = device.getMotionRange(axis, motionEvent.getSource());
        if (range == null) {
            return 0f;
        }

        final float flat = range.getFlat();
        final float value = motionEvent.getAxisValue(axis);
        if (Math.abs(value) > flat) {
            return value;
        }

        return 0f;
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        boolean keyIsDown = keyEvent.getAction() == KeyEvent.ACTION_DOWN;

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
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_L1) {
            mButtonState.put(GamepadConstants.BUTTON_L1, keyIsDown);
        }
    }

    public void setButtonDown(int buttonId, boolean isButtonDown) {
        mButtonState.put(buttonId, isButtonDown);
    }

    public float getJoystickPosition(int joystickIndex, int axis) {
        return mJoystickPositions[joystickIndex][axis];
    }

    public boolean isButtonDown(int buttonId) {
        final Boolean isDown = mButtonState.get(buttonId);
        if (isDown == null) {
            return false;
        }

        return isDown;
    }

    public boolean isDPadDown(int dpadId) {
        final Boolean isDown = mDPadState.get(dpadId);
        if (isDown == null) {
            return false;
        }

        return isDown;
    }

    public boolean isTriggerDown(int triggerId) {
        final Boolean isDown = mTriggerState.get(triggerId);
        if (isDown == null) {
            return false;
        }

        return isDown;
    }

    public void setDeviceId(int newId) {
        if (newId != mDeviceId && newId != -1) {
            mDeviceId = newId;
            resetState();
        }
    }
}