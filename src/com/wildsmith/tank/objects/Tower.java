package com.wildsmith.tank.objects;

import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.levels.Level;

public class Tower extends ViewObject {

    public Tower(float left, float right, float top, float bottom, Level level) {
        super(R.drawable.image_tower, left, right, top, bottom, level);
    }

    @Override
    public void update(float frameDelta) {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getLeft(), getTop(), paint);
    }
}