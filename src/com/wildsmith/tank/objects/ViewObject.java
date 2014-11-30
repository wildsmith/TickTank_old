package com.wildsmith.tank.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;
import com.wildsmith.tank.maps.Level;

public abstract class ViewObject {

    protected SoundManager sound;

    protected Bitmap bitmap;

    protected GamepadController gamepadController;

    protected Paint paint;

    protected RectF bounds;

    protected Level level;

    protected float left, right, top, bottom, bottomToTopSize, rightToLeftSize;

    protected int canvasWidth, canvasHeight;

    public ViewObject(int imageResourceId, float left, float right, float top, float bottom, Level level) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;

        this.bounds = new RectF(left, top, right, bottom);

        this.bottomToTopSize = bottom - top;
        this.rightToLeftSize = right - left;

        this.level = level;
        this.sound = level.getSound();
        this.gamepadController = level.getGamepadController();

        Resources resources = level.getContext().getResources();

        Bitmap fullImage = BitmapFactory.decodeResource(resources, imageResourceId);
        this.bitmap = Bitmap.createScaledBitmap(fullImage, (int) (right - left), (int) (bottom - top), true);

        this.paint = new Paint();

        this.canvasWidth = resources.getDisplayMetrics().widthPixels;
        this.canvasHeight = resources.getDisplayMetrics().heightPixels;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public float getLeft() {
        return left;
    }

    protected void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    protected void setTop(float top) {
        this.top = top;
    }

    protected void setPosition(float left, float top) {
        setLeft(left);
        setTop(top);
    }

    /**
     * Update should be used to move the object around the canvas, NO drawing should occur in this
     * method.
     * 
     * @param frameDelta
     * @param level
     */
    public abstract void update(float frameDelta);

    /**
     * This method should draw the object, NO movement calculations should occur in this method.
     * 
     * @param canvas
     */
    public abstract void draw(Canvas canvas);
}