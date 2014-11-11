package com.wildsmith.tank.objects;

import android.content.Context;
import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;

public class Tower extends ViewObject {

    public Tower(Context context, SoundManager sound, GamepadController gamepadController) {
        super(context.getResources(), R.drawable.tower, sound, gamepadController);
    }

    @Override
    public void update(float frameDelta) {
        // TODO Auto-generated method stub
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, left, top, paint);
    }
}