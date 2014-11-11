package com.wildsmith.tank.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.game.GameActivity;

public abstract class ViewObject {

    protected Bitmap bitmap;

    protected SoundManager sound;

    protected GameActivity gameActivity;

    protected float bitmapWidth = -1;

    protected float bitmapHeight = -1;

    protected float bitmapHalfWidth = -1;

    protected float bitmapHalfHeight = -1;

    protected float canvasWidth = -1;

    protected float canvasHeight = -1;

    protected int screenDensity = -1;

    protected float x, y;

    protected boolean initialState;

    public ViewObject(Bitmap bitmap, Resources resources, SoundManager sound) {
        this.bitmap = bitmap;
        this.sound = sound;

        recalculateBitmapProperties();

        if (resources != null && resources.getDisplayMetrics() != null) {
            this.screenDensity = resources.getDisplayMetrics().densityDpi;

            this.canvasHeight = resources.getDisplayMetrics().heightPixels;
            this.canvasWidth = resources.getDisplayMetrics().widthPixels;
        }

        this.initialState = true;
    }

    protected void recalculateBitmapProperties() {
        if (bitmap != null) {
            this.bitmapHeight = bitmap.getHeight();
            this.bitmapWidth = bitmap.getWidth();
        }

        if (bitmapHeight > 0) {
            this.bitmapHalfHeight = bitmapHeight / 2;
        }

        if (bitmapWidth > 0) {
            this.bitmapHalfWidth = bitmapWidth / 2;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getBitmapHalfHeight() {
        return bitmapHalfHeight;
    }

    public float getBitmapHalfWidth() {
        return bitmapHalfWidth;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public abstract void draw(Canvas canvas);

    public void setActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }
}