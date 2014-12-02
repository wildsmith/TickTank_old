package com.wildsmith.tank.objects;

import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
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

    private RectF barrelBounds;

    private Bitmap barrelBitmap;

    private Matrix barrelMatrix;

    private float mVelocityX, mVelocityY, mAimX, mAimY;

    public Tank(float left, float right, float top, float bottom, Level level) {
        super(R.drawable.tank, left, right, top, bottom, level);

        setupTankBarrel(left, right, top, bottom, level);
    }

    private void setupTankBarrel(float left, float right, float top, float bottom, Level level) {
        final float barrelWidth = width / 5;
        final float barrelHeight = height / 2 + height / 4;

        final float barrelLeft = width / 2 - barrelWidth / 2;
        final float barrelTop = top - height / 4;

        barrelBounds = new RectF(barrelLeft, barrelTop, barrelWidth, barrelHeight);

        Resources resources = level.getContext().getResources();
        Bitmap fullImage = BitmapFactory.decodeResource(resources, R.drawable.tank_barrel);
        barrelBitmap = Bitmap.createScaledBitmap(fullImage, (int) barrelWidth, (int) barrelHeight, true);

        mAimX = left + (barrelWidth / 2);
        mAimY = top;
    }

    @Override
    public void update(float frameDelta) {
        updateTank(frameDelta);
        updateBarrel(frameDelta);
    }

    private void updateBarrel(float frameDelta) {
        float newAimX = gamepadController.getJoystickPosition(GamepadConstants.JOYSTICK_2, GamepadConstants.AXIS_X);
        float newAimY = gamepadController.getJoystickPosition(GamepadConstants.JOYSTICK_2, GamepadConstants.AXIS_Y);

        if (gamepadController.isButtonDown(GamepadConstants.BUTTON_R2)) {
            sound.playSound(Sound.TANK_FIRE);
        }

        if (mAimX == newAimX && mAimY == newAimY) {
            return;
        }

        final double angle = Math.atan(((newAimY * mAimX) - (newAimX - mAimY)) / ((newAimX * mAimX) + (newAimY * mAimY)));

        barrelMatrix = new Matrix();
        barrelMatrix.setRotate((float) angle, (bounds.bottom - bounds.top) / 2, (bounds.right - bounds.left) / 2);

        mAimX = newAimX;
        mAimY = newAimY;
    }

    private void updateTank(float frameDelta) {
        if (gamepadController.isButtonDown(GamepadConstants.BUTTON_L2)) {
            setVelocity(0.0f, 0.0f);
            sound.pauseSound(Sound.TANK_MOVEMENT);
            return;
        }

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

        setPosition(getLeft() + mVelocityX * frameDelta, getTop() + mVelocityY * frameDelta);

        // Use drag so that the tank will coast to a stop after the movement controller is released.
        mVelocityX *= 1.0f - frameDelta * DRAG;
        mVelocityY *= 1.0f - frameDelta * DRAG;
        if (MathHelpter.vector2DLength(mVelocityX, mVelocityY) < MINIMUM_VELOCITY) {
            setVelocity(0.0f, 0.0f);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getLeft(), getTop(), paint);

        // if (barrelMatrix != null) {
        // canvas.drawBitmap(barrelBitmap, barrelMatrix, paint);
        // } else {
        canvas.drawBitmap(barrelBitmap, barrelBounds.left, barrelBounds.top, paint);
        // }
    }

    @Override
    protected void setTop(float top) {
        if (ScreenHelper.isInYPlane(top, height, canvasHeight)) {
            super.setTop(top);
        } else if (ScreenHelper.isOffScreenTop(top, height) || ScreenHelper.isOffScreenBottom(top, height, canvasHeight)) {
            setVelocity(0.0f, 0.0f);
        }
    }

    @Override
    protected void setLeft(float left) {
        if (ScreenHelper.isInXPlane(left, width, canvasWidth)) {
            super.setLeft(left);
        } else if (ScreenHelper.isOffScreenLeft(left, width) || ScreenHelper.isOffScreenRight(left, width, canvasWidth)) {
            setVelocity(0.0f, 0.0f);
        }
    }

    @Override
    protected void setPosition(float left, float top) {
        RectF newBounds = new RectF(left, top, left + width, top + height);

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

        if (getLeft() != left || getTop() != top) {
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
        List<Bullet> bullets = level.getBullets();
        if (bullets == null || bullets.isEmpty()) {
            return isIntersecting;
        }

        for (Bullet bullet : bullets) {
            if (isIntersecting) {
                break;
            }

            isIntersecting = ScreenHelper.isIntersecting(newBounds, bullet.bounds);
        }

        return isIntersecting;
    }

    private boolean isIntersectingWithWalls(RectF newBounds) {
        boolean isIntersecting = false;
        List<Wall> walls = level.getWalls();
        if (walls == null || walls.isEmpty()) {
            return isIntersecting;
        }

        for (Wall wall : walls) {
            if (isIntersecting) {
                break;
            }

            isIntersecting = ScreenHelper.isIntersecting(newBounds, wall.bounds);
        }

        return isIntersecting;
    }
}