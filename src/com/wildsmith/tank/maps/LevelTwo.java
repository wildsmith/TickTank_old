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

        setupBackground(context, sound, gamepadController);
        setupTank(context, sound, gamepadController);
        setupTower(context, sound, gamepadController);
        setupWalls(context, sound, gamepadController);
    }

    private void setupBackground(Context context, SoundManager sound, GamepadController gamepadController) {
        background = new Background(R.drawable.graph_paper, 0, canvasWidth, 0, canvasHeight, context, sound, gamepadController);
    }

    private void setupTank(Context context, SoundManager sound, GamepadController gamepadController) {}

    private void setupTower(Context context, SoundManager sound, GamepadController gamepadController) {}

    private void setupWalls(Context context, SoundManager sound, GamepadController gamepadController) {}

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