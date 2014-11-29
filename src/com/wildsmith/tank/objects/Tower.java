package com.wildsmith.tank.objects;

import android.content.Context;
import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;

public class Tower extends ViewObject {

    public Tower(float left, float right, float top, float bottom, Context context, SoundManager sound, GamepadController gamepadController) {
        super(R.drawable.tower, left, right, top, bottom, context.getResources(), sound, gamepadController);
    }

    @Override
    public void update(float frameDelta) {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, left, top, paint);
    }

    @Override
    public void check(Object object) {
        // TODO We need to check to see if the Tower has been hit by one of the tanks bullets
    }
}