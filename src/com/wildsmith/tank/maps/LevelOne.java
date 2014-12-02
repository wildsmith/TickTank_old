package com.wildsmith.tank.maps;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.Sound;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;
import com.wildsmith.tank.objects.Background;
import com.wildsmith.tank.objects.Tank;
import com.wildsmith.tank.objects.Tower;
import com.wildsmith.tank.objects.Wall;
import com.wildsmith.tank.utils.ViewHelper;

public class LevelOne extends Level {

    public LevelOne(Context context, SoundManager sound, GamepadController gamepadController) {
        super(context.getResources());

        this.context = context;
        this.sound = sound;
        this.sound.playSound(Sound.FINAL_COUNT_DOWN);

        this.gamepadController = gamepadController;

        setupBackground();
        setupTank();
        setupTower();
        setupWalls();
    }

    private void setupBackground() {
        background = new Background(R.drawable.grass_background, 0, canvasWidth, 0, canvasHeight, this);
    }

    private void setupTank() {
        final float tankWidth = ViewHelper.getPxFromDp(40, context);
        final float tankHeight = ViewHelper.getPxFromDp(50, context);
        tank =
                new Tank(canvasWidth / 2 - (tankWidth / 2), canvasWidth / 2 + (tankWidth / 2), canvasHeight - tankHeight, canvasHeight,
                        this);
    }

    private void setupTower() {
        final float towerWidth = canvasWidth / 12;
        final float towerHeight = canvasWidth / 12;
        tower = new Tower(canvasWidth / 2 - (towerWidth / 2), canvasWidth / 2 + (towerWidth / 2), 0, towerHeight, this);
    }

    private void setupWalls() {
        walls = new ArrayList<Wall>(2);

        final float levelGrid = canvasWidth / 14;
        final float wallWidth = ViewHelper.getPxFromDp(50, context);

        final float leftWallPosition = levelGrid * 5;
        Wall leftWall = new Wall(leftWallPosition, leftWallPosition + wallWidth, 0, canvasHeight, this);
        walls.add(leftWall);

        final float rightWallPosition = levelGrid * 8;
        Wall rightWall = new Wall(rightWallPosition, rightWallPosition + wallWidth, 0, canvasHeight, this);
        walls.add(rightWall);
    }

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