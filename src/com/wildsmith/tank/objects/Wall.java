package com.wildsmith.tank.objects;

import android.content.Context;
import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;

public class Wall extends ViewObject {

    public Wall(float left, float right, float top, float bottom, Context context, SoundManager sound, GamepadController gamepadController) {
        super(R.drawable.wall, left, right, top, bottom, context.getResources(), sound, gamepadController);
    }

    @Override
    public void update(float frameDelta) {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, left, top, paint);
    }
}