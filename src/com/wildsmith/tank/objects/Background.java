package com.wildsmith.tank.objects;

import android.content.Context;
import android.graphics.Canvas;

import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;

public class Background extends ViewObject {

    public Background(int imageResourceId, int left, int right, int top, int bottom, Context context, SoundManager sound,
            GamepadController gamepadController) {
        super(imageResourceId, left, right, top, bottom, context.getResources(), sound, gamepadController);
    }

    @Override
    public void update(float frameDelta) {}

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, left, top, paint);
    }
}