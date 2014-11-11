package com.wildsmith.tank.objects;

import android.content.Context;
import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;

public class Wall extends ViewObject {

    public Wall(Context context, SoundManager sound, GamepadController gamepadController) {
        super(context, R.drawable.wall, sound, gamepadController);
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