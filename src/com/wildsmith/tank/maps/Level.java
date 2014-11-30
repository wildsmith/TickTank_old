package com.wildsmith.tank.maps;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;

import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;
import com.wildsmith.tank.objects.Background;
import com.wildsmith.tank.objects.Bullet;
import com.wildsmith.tank.objects.Tank;
import com.wildsmith.tank.objects.Tower;
import com.wildsmith.tank.objects.Wall;

public abstract class Level {

    protected Context context;

    protected SoundManager sound;

    protected GamepadController gamepadController;

    protected Background background;

    protected Tank tank;

    protected Tower tower;

    protected List<Bullet> bullets;

    protected List<Wall> walls;

    protected int canvasWidth, canvasHeight;

    public Level(Resources resources) {
        this.canvasWidth = resources.getDisplayMetrics().widthPixels;
        this.canvasHeight = resources.getDisplayMetrics().heightPixels;
    }

    public abstract void update(float frameDelta);

    public abstract void draw(Canvas canvas);

    public Context getContext() {
        return context;
    }

    public SoundManager getSound() {
        return sound;
    }

    public GamepadController getGamepadController() {
        return gamepadController;
    }

    public Tank getTank() {
        return tank;
    }

    public Tower getTower() {
        return tower;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Wall> getWalls() {
        return walls;
    }
}