package com.wildsmith.tank.maps;

import android.content.res.Resources;
import android.graphics.Canvas;

public abstract class TickTankMap {

    protected int canvasHeight;

    protected int canvasWidth;

    public TickTankMap(Resources resources) {
        this.canvasHeight = resources.getDisplayMetrics().heightPixels;
        this.canvasWidth = resources.getDisplayMetrics().widthPixels;
    }

    public abstract void update(float frameDelta);

    public abstract void draw(Canvas canvas);
}