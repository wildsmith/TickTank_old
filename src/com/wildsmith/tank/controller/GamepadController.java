package com.wildsmith.tank.controller;

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
    private boolean mButtonState[][];

    /**
     * The d pad states for the current and previous frames.
     */
    private boolean mDPadState[][];

    /**
     * The device that we are tuned to.
     */
    private int mDeviceId = -1;

    public GamepadController() {
        mDPadState = new boolean[GamepadConstants.DPAD_COUNT][GamepadConstants.FRAME_INDEX_COUNT];
        mButtonState = new boolean[GamepadConstants.BUTTON_COUNT][GamepadConstants.FRAME_INDEX_COUNT];
        mJoystickPositions = new float[GamepadConstants.JOYSTICK_COUNT][GamepadConstants.AXIS_COUNT];
    }

    private void resetState() {
        for (int dpad = 0; dpad < GamepadConstants.DPAD_COUNT; dpad++) {
            for (int frame = 0; frame < GamepadConstants.FRAME_INDEX_COUNT; frame++) {
                mDPadState[dpad][frame] = false;
            }
        }

        for (int button = 0; button < GamepadConstants.BUTTON_COUNT; button++) {
            for (int frame = 0; frame < GamepadConstants.FRAME_INDEX_COUNT; frame++) {
                mButtonState[button][frame] = false;
            }
        }

        for (int joystick = 0; joystick < GamepadConstants.JOYSTICK_COUNT; joystick++) {
            for (int axis = 0; axis < GamepadConstants.AXIS_COUNT; axis++) {
                mJoystickPositions[joystick][axis] = 0.0f;
            }
        }
    }

    public void handleMotionEvent(MotionEvent motionEvent) {
        mJoystickPositions[GamepadConstants.JOYSTICK_1][GamepadConstants.AXIS_X] = motionEvent.getAxisValue(MotionEvent.AXIS_X);
        mJoystickPositions[GamepadConstants.JOYSTICK_1][GamepadConstants.AXIS_Y] = motionEvent.getAxisValue(MotionEvent.AXIS_Y);

        mJoystickPositions[GamepadConstants.JOYSTICK_2][GamepadConstants.AXIS_X] = motionEvent.getAxisValue(MotionEvent.AXIS_Z);
        mJoystickPositions[GamepadConstants.JOYSTICK_2][GamepadConstants.AXIS_Y] = motionEvent.getAxisValue(MotionEvent.AXIS_RZ);
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        boolean keyIsDown = keyEvent.getAction() == KeyEvent.ACTION_DOWN;

        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
            mDPadState[GamepadConstants.DPAD_LEFT][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
            mDPadState[GamepadConstants.DPAD_RIGHT][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
            mDPadState[GamepadConstants.DPAD_UP][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
            mDPadState[GamepadConstants.DPAD_DOWN][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            mDPadState[GamepadConstants.DPAD_CENTER][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        }

        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_A) {
            mButtonState[GamepadConstants.BUTTON_A][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_B) {
            mButtonState[GamepadConstants.BUTTON_B][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_X) {
            mButtonState[GamepadConstants.BUTTON_X][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_Y) {
            mButtonState[GamepadConstants.BUTTON_Y][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R1) {
            mButtonState[GamepadConstants.BUTTON_R1][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_R2) {
            mButtonState[GamepadConstants.BUTTON_R2][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_L1) {
            mButtonState[GamepadConstants.BUTTON_L1][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        } else if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BUTTON_L2) {
            mButtonState[GamepadConstants.BUTTON_L2][GamepadConstants.FRAME_INDEX_CURRENT] = keyIsDown;
        }
    }

    public float getJoystickPosition(int joystickIndex, int axis) {
        return mJoystickPositions[joystickIndex][axis];
    }

    public boolean isButtonDown(int buttonId) {
        return mButtonState[buttonId][GamepadConstants.FRAME_INDEX_CURRENT];
    }

    public boolean isDPadDown(int dpadId) {
        return mDPadState[dpadId][GamepadConstants.FRAME_INDEX_CURRENT];
    }

    public void setDeviceId(int newId) {
        if (newId != mDeviceId && newId != -1) {
            mDeviceId = newId;
            resetState();
        }
    }
}