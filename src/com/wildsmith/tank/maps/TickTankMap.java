package com.wildsmith.tank.maps;

import android.content.res.Resources;
import android.graphics.Canvas;

public abstract class TickTankMap {

    protected int canvasWidth, canvasHeight;

    public TickTankMap(Resources resources) {
        this.canvasWidth = resources.getDisplayMetrics().widthPixels;
        this.canvasHeight = resources.getDisplayMetrics().heightPixels;
    }

    public abstract void update(float frameDelta);

    public abstract void draw(Canvas canvas);
}