package com.wildsmith.tank.objects;

import android.content.Context;
import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadConstants;
import com.wildsmith.tank.controller.GamepadController;
import com.wildsmith.tank.utils.MathHelpter;

public class Tank extends ViewObject {

    private static final float DRAG = 0.05f;

    private static final float MINIMUM_VELOCITY = 0.05f;

    private static final float VELOCITY_MULTIPLIER = 4.0f;

    private float mVelocityX;

    private float mVelocityY;

    public Tank(float left, float right, float top, float bottom, Context context, SoundManager sound, GamepadController gamepadController) {
        super(R.drawable.tank, left, right, top, bottom, context.getResources(), sound, gamepadController);
    }

    @Override
    public void update(float frameDelta) {
        float newHeadingX = gamepadController.getJoystickPosition(GamepadConstants.JOYSTICK_1, GamepadConstants.AXIS_X);
        float newHeadingY = gamepadController.getJoystickPosition(GamepadConstants.JOYSTICK_1, GamepadConstants.AXIS_Y);

        float magnitude = MathHelpter.vector2DLength(newHeadingX, newHeadingY);

        // Joystick being used at least a little bit
        if (magnitude > GamepadConstants.JOYSTICK_MOVEMENT_THRESHOLD) {
            setVelocity(newHeadingX, newHeadingY);

            if (magnitude > 1.0f) {
                // Sets a cap velocity
                mVelocityX = mVelocityX / magnitude;
                mVelocityY = mVelocityY / magnitude;
            }
        }

        setPosition(left + mVelocityX * frameDelta, top + mVelocityY * frameDelta);

        // Use drag so that the ship will coast to a stop after the movement controller
        // is released.
        mVelocityX *= 1.0f - frameDelta * DRAG;
        mVelocityY *= 1.0f - frameDelta * DRAG;
        if (MathHelpter.vector2DLength(mVelocityX, mVelocityY) < MINIMUM_VELOCITY) {
            setVelocity(0.0f, 0.0f);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, left, top, paint);
    }

    public void setVelocity(float velocityX, float velocityY) {
        setVelocityX(velocityX);
        setVelocityY(velocityY);
    }

    public void setVelocityX(float velocity) {
        mVelocityX = velocity * VELOCITY_MULTIPLIER;
    }

    public void setVelocityY(float velocity) {
        mVelocityY = velocity * VELOCITY_MULTIPLIER;
    }
}