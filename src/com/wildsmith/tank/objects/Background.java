package com.wildsmith.tank.objects;

import android.graphics.Canvas;

import com.wildsmith.tank.maps.Level;

public class Background extends ViewObject {

    public Background(int imageResourceId, int left, int right, int top, int bottom, Level level) {
        super(imageResourceId, left, right, top, bottom, level);
    }

    @Override
    public void update(float frameDelta) {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getLeft(), getTop(), paint);
    }
}