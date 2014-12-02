package com.wildsmith.tank.maps;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;
import com.wildsmith.tank.objects.Background;
import com.wildsmith.tank.objects.Tank;
import com.wildsmith.tank.objects.Tower;
import com.wildsmith.tank.objects.Wall;

public class LevelTwo extends Level {

    private Background background;

    private Tank tank;

    private Tower tower;

    private List<Wall> walls;

    public LevelTwo(Context context, SoundManager sound, GamepadController gamepadController) {
        super(context.getResources());

        setupBackground();
        setupTank();
        setupTower();
        setupWalls();
    }

    private void setupBackground() {
        background = new Background(R.drawable.grass_background, 0, canvasWidth, 0, canvasHeight, this);
    }

    private void setupTank() {}

    private void setupTower() {}

    private void setupWalls() {}

    @Override
    public void update(float frameDelta) {
        tank.update(frameDelta);
        tower.update(frameDelta);

        for (Wall wall : walls) {
            wall.update(frameDelta);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);

        for (Wall wall : walls) {
            wall.draw(canvas);
        }

        tower.draw(canvas);
        tank.draw(canvas);
    }
}