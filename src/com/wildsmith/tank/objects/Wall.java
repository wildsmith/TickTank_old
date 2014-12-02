package com.wildsmith.tank.objects;

import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.maps.Level;

public class Wall extends ViewObject {

    public Wall(float left, float right, float top, float bottom, Level level) {
        super(R.drawable.wall, left, right, top, bottom, level);
    }

    @Override
    public void update(float frameDelta) {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getLeft(), getTop(), paint);
    }
}