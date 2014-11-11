package com.wildsmith.tank.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;

public abstract class ViewObject {

    protected SoundManager sound;

    protected Bitmap bitmap;

    protected GamepadController gamepadController;

    protected Paint paint;

    protected RectF bounds;

    protected float left, right, top, bottom;

    public ViewObject(int left, int right, int top, int bottom, Resources resources, int imageResourceId, SoundManager sound,
            GamepadController gamepadController) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;

        this.bounds = new RectF(left, top, right, bottom);

        Bitmap fullImage = BitmapFactory.decodeResource(resources, imageResourceId);
        this.bitmap = Bitmap.createScaledBitmap(fullImage, right - left, bottom - top, true);

        this.sound = sound;
        this.gamepadController = gamepadController;
        this.paint = new Paint();
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

    public float getTop() {
        return top;
    }

    /**
     * Update should be used to move the object around the canvas, NO drawing should occur in this
     * method.
     * 
     * @param frameDelta
     */
    public abstract void update(float frameDelta);

    /**
     * This method should draw the object, NO movement calculations should occur in this method.
     * 
     * @param canvas
     */
    public abstract void draw(Canvas canvas);
}