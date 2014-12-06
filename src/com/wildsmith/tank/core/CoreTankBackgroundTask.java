package com.wildsmith.tank.core;

import java.util.TimerTask;

import android.graphics.drawable.Drawable;
import android.os.Handler;

public class CoreTankBackgroundTask extends TimerTask {

    private CoreTankBrowseFragment fragment;

    private Drawable drawable;

    private Handler handler;

    public CoreTankBackgroundTask(CoreTankBrowseFragment fragment, Drawable drawable) {
        this.fragment = fragment;
        this.drawable = drawable;
        handler = new Handler();
    }

    @Override
    public void run() {
        handler.post(new Runnable() {

            @Override
            public void run() {
                if (drawable != null && fragment != null) {
                    fragment.updateBackground(drawable);
                }
            }
        });
    }
}