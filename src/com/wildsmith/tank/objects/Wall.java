package com.wildsmith.tank.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.wildsmith.tank.attributes.SoundManager;

public class Wall extends ViewObject {

    public Wall(Bitmap bitmap, Resources resources, SoundManager sound) {
        super(bitmap, resources, sound);
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
    }
}