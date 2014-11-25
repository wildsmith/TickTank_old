package com.wildsmith.tank.maps;

import android.content.res.Resources;
import android.graphics.Canvas;

public abstract class Level {

    protected int canvasWidth, canvasHeight;

    public Level(Resources resources) {
        this.canvasWidth = resources.getDisplayMetrics().widthPixels;
        this.canvasHeight = resources.getDisplayMetrics().heightPixels;
    }

    public abstract void update(float frameDelta);

    public abstract void draw(Canvas canvas);
}