package com.wildsmith.tank.objects;

import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.levels.Level;

public class Bullet extends ViewObject {

    private static final float BULLET_VELOCITY = 5f;

    private float velocityY;

    public Bullet(float left, float right, float top, float bottom, Level level) {
        super(R.drawable.image_bullet, left, right, top, bottom, level);

        velocityY = BULLET_VELOCITY;
    }

    @Override
    public void update(float frameDelta) {
        setPosition(getLeft(), getTop() - (velocityY * frameDelta));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getLeft(), getTop(), paint);
    }
}