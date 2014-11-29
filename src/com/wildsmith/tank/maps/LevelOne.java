package com.wildsmith.tank.maps;

import java.util.ArrayList;
import java.util.List;

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

    private Background background;

    private Tank tank;

    private Tower tower;

    private List<Wall> walls;

    public LevelOne(Context context, SoundManager sound, GamepadController gamepadController) {
        super(context.getResources());

        sound.playSound(Sound.FINAL_COUNT_DOWN);

        setupBackground(context, sound, gamepadController);
        setupTank(context, sound, gamepadController);
        setupTower(context, sound, gamepadController);
        setupWalls(context, sound, gamepadController);
    }

    private void setupBackground(Context context, SoundManager sound, GamepadController gamepadController) {
        background = new Background(R.drawable.graph_paper, 0, canvasWidth, 0, canvasHeight, context, sound, gamepadController);
    }

    private void setupTank(Context context, SoundManager sound, GamepadController gamepadController) {
        final float tankWidth = canvasWidth / 12;
        final float tankHeight = canvasWidth / 12;
        tank =
                new Tank(canvasWidth / 2 - (tankWidth / 2), canvasWidth / 2 + (tankWidth / 2), canvasHeight - tankHeight, canvasHeight,
                        context, sound, gamepadController);
    }

    private void setupTower(Context context, SoundManager sound, GamepadController gamepadController) {
        final float towerWidth = canvasWidth / 12;
        final float towerHeight = canvasWidth / 12;
        tower =
                new Tower(canvasWidth / 2 - (towerWidth / 2), canvasWidth / 2 + (towerWidth / 2), 0, towerHeight, context, sound,
                        gamepadController);
    }

    private void setupWalls(Context context, SoundManager sound, GamepadController gamepadController) {
        walls = new ArrayList<Wall>(2);

        final float levelGrid = canvasWidth / 14;
        final float wallWidth = ViewHelper.getPxFromDp(50, context);

        final float leftWallPosition = levelGrid * 5;
        Wall leftWall = new Wall(leftWallPosition, leftWallPosition + wallWidth, 0, canvasHeight, context, sound, gamepadController);
        walls.add(leftWall);

        final float rightWallPosition = levelGrid * 8;
        Wall rightWall = new Wall(rightWallPosition, rightWallPosition + wallWidth, 0, canvasHeight, context, sound, gamepadController);
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