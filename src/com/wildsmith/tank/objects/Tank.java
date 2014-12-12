package com.wildsmith.tank.objects;

import java.util.Calendar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.Sound;
import com.wildsmith.tank.controller.GamepadConstants;
import com.wildsmith.tank.levels.Level;
import com.wildsmith.tank.utils.IntersectionHelper;
import com.wildsmith.tank.utils.MathHelpter;
import com.wildsmith.tank.utils.ScreenHelper;

public class Tank extends ViewObject {

    private static final float DRAG = 0.05f;

    private static final float MINIMUM_VELOCITY = 0.05f;

    private static final float VELOCITY_MULTIPLIER = 4.0f;

    private static final int BULLET_FIRE_MILLISECOND_INTERVAL = 500;

    private RectF barrelBounds;

    private Bitmap barrelBitmap;

    private Matrix barrelMatrix;

    private Calendar prevBulletFire;

    private float velocityX, velocityY, aimX, aimY;

    private float barrelHeight, barrelWidth;

    private boolean hasBarrelRotated;

    public Tank(float left, float right, float top, float bottom, Level level) {
        super(R.drawable.image_tank, left, right, top, bottom, level);

        setupTankBarrel(left, right, top, bottom, level);
    }

    private void setupTankBarrel(float left, float right, float top, float bottom, Level level) {
        barrelWidth = width / 5;
        barrelHeight = height / 2 + height / 4;

        final float barrelLeft = getLeft() + (width / 2) - (barrelWidth / 2);
        final float barrelTop = top - height / 4;

        barrelBounds = new RectF(barrelLeft, barrelTop, barrelWidth, barrelHeight);

        Resources resources = level.getContext().getResources();
        Bitmap fullImage = BitmapFactory.decodeResource(resources, R.drawable.image_tank_barrel);
        barrelBitmap = Bitmap.createScaledBitmap(fullImage, (int) barrelWidth, (int) barrelHeight, true);

        barrelMatrix = new Matrix();

        aimX = left + (barrelWidth / 2);
        aimY = top;
    }

    @Override
    public void update(float frameDelta) {
        updateTank(frameDelta);
        updateBarrel(frameDelta);
    }

    private void updateBarrel(float frameDelta) {
        if (gamepadController.isTriggerDown(GamepadConstants.TRIGGER_R2) && canFireNewBullet()) {
            sound.playSound(Sound.TANK_FIRE);
            createBullet();
        }

        return;

        // TODO this chunk of code was my attempt to map out the tank barrel rotation to the right
        // joy stick rotation. I didn't quite get it done before the presentation.

        // float newAimX = gamepadController.getJoystickPosition(GamepadConstants.JOYSTICK_2,
        // GamepadConstants.AXIS_X);
        // float newAimY = gamepadController.getJoystickPosition(GamepadConstants.JOYSTICK_2,
        // GamepadConstants.AXIS_Y);
        // if (aimX == newAimX && aimY == newAimY || newAimX == 0 && newAimY == 0) {
        // return;
        // }
        //
        // float deltaY = newAimY - aimY;
        // float deltaX = newAimX - aimX;
        // final double angle = Math.atan2(deltaY, deltaX) * (180 / Math.PI);
        // if (Double.isNaN(angle)) {
        // return;
        // }
        //
        // barrelMatrix.setRotate((float) angle, getTop() + barrelHeight / 2, getLeft() +
        // barrelWidth / 2);
        // RectF rectF = new RectF(0, 0, barrelWidth, barrelHeight);
        // barrelMatrix.mapRect(rectF);
        // barrelBitmap = Bitmap.createBitmap((int) rectF.width(), (int) rectF.height(),
        // Bitmap.Config.ARGB_8888);
        //
        // aimX = newAimX;
        // aimY = newAimY;
        //
        // hasBarrelRotated = true;
    }

    private boolean canFireNewBullet() {
        if (prevBulletFire == null) {
            prevBulletFire = Calendar.getInstance();
            return true;
        }

        Calendar newBulletFire = Calendar.getInstance();
        if (newBulletFire == null) {
            return false;
        }

        Calendar prevBulletFireClone = (Calendar) prevBulletFire.clone();
        prevBulletFireClone.add(Calendar.MILLISECOND, BULLET_FIRE_MILLISECOND_INTERVAL);

        if (newBulletFire.compareTo(prevBulletFireClone) >= 0) {
            prevBulletFire = newBulletFire;
            return true;
        }

        return false;
    }

    private void createBullet() {
        final float bulletWidth = barrelWidth / 1.5f;
        final float bulletHeight = barrelWidth / 1.5f;

        final float bulletLeft = getLeft() + (width / 2) - (bulletWidth / 2);
        final float bulletTop = barrelBounds.top - bulletHeight;

        Bullet bullet = new Bullet(bulletLeft, bulletLeft + bulletWidth, bulletTop, bulletTop + bulletHeight, level);
        level.addBullet(bullet);
    }

    private void updateTank(float frameDelta) {
        if (gamepadController.isTriggerDown(GamepadConstants.TRIGGER_L2)) {
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
                velocityX = velocityX / magnitude;
                velocityY = velocityY / magnitude;
            }
        }

        setPosition(getLeft() + velocityX * frameDelta, getTop() + velocityY * frameDelta);

        // Use drag so that the tank will coast to a stop after the movement controller is released.
        velocityX *= 1.0f - frameDelta * DRAG;
        velocityY *= 1.0f - frameDelta * DRAG;
        if (MathHelpter.vector2DLength(velocityX, velocityY) < MINIMUM_VELOCITY) {
            setVelocity(0.0f, 0.0f);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getLeft(), getTop(), paint);

        if (hasBarrelRotated) {
            canvas.drawBitmap(barrelBitmap, barrelMatrix, paint);
        } else {
            canvas.drawBitmap(barrelBitmap, barrelBounds.left, barrelBounds.top, paint);
        }
    }

    @Override
    protected void setTop(float top) {
        if (ScreenHelper.isInYPlane(top, height, canvasHeight)) {
            setBarrelTop(top);
            super.setTop(top);
        } else if (ScreenHelper.isOffScreenTop(top, height) || ScreenHelper.isOffScreenBottom(top, height, canvasHeight)) {
            setVelocity(0.0f, 0.0f);
        }
    }

    private void setBarrelTop(float top) {
        barrelBounds.top += top - getTop();
        barrelBounds.bottom = barrelBounds.top + barrelHeight;
    }

    @Override
    protected void setLeft(float left) {
        if (ScreenHelper.isInXPlane(left, width, canvasWidth)) {
            setBarrelLeft(left);
            super.setLeft(left);
        } else if (ScreenHelper.isOffScreenLeft(left, width) || ScreenHelper.isOffScreenRight(left, width, canvasWidth)) {
            setVelocity(0.0f, 0.0f);
        }
    }

    private void setBarrelLeft(float left) {
        barrelBounds.left += left - getLeft();
        barrelBounds.right = barrelBounds.left + barrelWidth;
    }

    @Override
    protected void setPosition(float left, float top) {
        RectF newBounds = new RectF(left, top, left + width, top + height);

        final boolean isIntersectingWithTower = IntersectionHelper.isIntersectingWithViewObject(level.getTower(), newBounds);
        final boolean isIntersectingWithWalls = IntersectionHelper.isIntersectingWithViewObjects(level.getWalls(), newBounds);

        if (isIntersectingWithWalls || isIntersectingWithTower) {
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
        velocityX = velocity * VELOCITY_MULTIPLIER;
    }

    public void setVelocityY(float velocity) {
        velocityY = velocity * VELOCITY_MULTIPLIER;
    }
}