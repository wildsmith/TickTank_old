package com.wildsmith.tank.objects;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.Sound;
import com.wildsmith.tank.controller.GamepadConstants;
import com.wildsmith.tank.maps.Level;
import com.wildsmith.tank.utils.MathHelpter;
import com.wildsmith.tank.utils.ScreenHelper;

public class Tank extends ViewObject {

    private static final float DRAG = 0.05f;

    private static final float MINIMUM_VELOCITY = 0.05f;

    private static final float VELOCITY_MULTIPLIER = 4.0f;

    private float mVelocityX, mVelocityY;

    public Tank(float left, float right, float top, float bottom, Level level) {
        super(R.drawable.tank, left, right, top, bottom, level);
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

        if (gamepadController.isButtonDown(GamepadConstants.BUTTON_R2)) {
            sound.playSound(Sound.TANK_FIRE);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, left, top, paint);
    }

    @Override
    protected void setTop(float top) {
        if (ScreenHelper.isInYPlane(top, bottomToTopSize, canvasHeight)) {
            super.setTop(top);
        } else if (ScreenHelper.isOffScreenTop(top, bottomToTopSize) || ScreenHelper.isOffScreenBottom(top, bottomToTopSize, canvasHeight)) {
            setVelocity(0.0f, 0.0f);
        }
    }

    @Override
    protected void setLeft(float left) {
        if (ScreenHelper.isInXPlane(left, rightToLeftSize, canvasWidth)) {
            super.setLeft(left);
        } else if (ScreenHelper.isOffScreenLeft(left, rightToLeftSize) || ScreenHelper.isOffScreenRight(left, rightToLeftSize, canvasWidth)) {
            setVelocity(0.0f, 0.0f);
        }
    }

    @Override
    protected void setPosition(float left, float top) {
        RectF newBounds = new RectF(left, top, right, bottom);

        // We need to check to see if the tank has been hit by any of its own bullets or enemy
        // bullets
        boolean isIntersectingWithBullets = isIntersectingWithBullets(newBounds);

        // We need to check to see if the tank has hit the wall(s)
        boolean isIntersectingWithWalls = isIntersectingWithWalls(newBounds);

        if (isIntersectingWithBullets || isIntersectingWithWalls) {
            setVelocity(0.0f, 0.0f);
            return;
        }

        if (sound == null) {
            return;
        }

        if (this.left != left || this.top != top) {
            sound.playSound(Sound.TANK_MOVEMENT);
        } else {
            sound.pauseSound(Sound.TANK_MOVEMENT);
        }

        super.setPosition(left, top);
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

    private boolean isIntersectingWithBullets(RectF newBounds) {
        boolean isIntersecting = false;
        for (Bullet bullet : level.getBullets()) {
            if (isIntersecting) {
                break;
            }

            isIntersecting = ScreenHelper.isIntersecting(newBounds, bullet.bounds);
        }

        return isIntersecting;
    }

    private boolean isIntersectingWithWalls(RectF newBounds) {
        boolean isIntersecting = false;
        for (Wall wall : level.getWalls()) {
            if (isIntersecting) {
                break;
            }

            isIntersecting = ScreenHelper.isIntersecting(newBounds, wall.bounds);
        }

        return isIntersecting;
    }
}