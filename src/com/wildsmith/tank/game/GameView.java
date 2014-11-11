package com.wildsmith.tank.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.objects.Tank;
import com.wildsmith.tank.objects.Tower;
import com.wildsmith.tank.objects.Wall;
import com.wildsmith.tank.threads.DrawThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    private SoundManager sound;

    private Tank tank;

    private Tower tower;

    private Wall wall;

    private boolean isSurfaceCreated;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
        setFocusable(true);

        drawThread = new DrawThread(getHolder(), this);

        sound = new SoundManager();
        sound.initSounds(context);

        tank = new Tank(BitmapFactory.decodeResource(getResources(), R.drawable.tank), getResources(), sound);
        tower = new Tower(BitmapFactory.decodeResource(getResources(), R.drawable.tower), getResources(), sound);
        wall = new Wall(BitmapFactory.decodeResource(getResources(), R.drawable.wall), getResources(), sound);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isSurfaceCreated = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isSurfaceCreated = true;

        resumeGame();
    }

    public void resumeGame() {
        if (isSurfaceCreated == false) {
            return;
        }

        if (drawThread == null) {
            drawThread = new DrawThread(getHolder(), this);
        }

        if (drawThread.isRunning() == false) {
            drawThread.setRunning(true);
            drawThread.start();
        }
    }

    public void pauseGame() {
        boolean retry = true;

        if (sound != null) {
            sound.pauseSounds();
        }

        if (drawThread == null || drawThread.isRunning() == false) {
            return;
        }

        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                drawThread = null;
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        tank.draw(canvas);
        tower.draw(canvas);
        wall.draw(canvas);
    }

    public SoundManager getSound() {
        return sound;
    }

    @SuppressLint("WrongCall")
    public void render(Canvas canvas) {
        onDraw(canvas);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean handled = false;
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD)
                == InputDevice.SOURCE_GAMEPAD) {
            if (event.getRepeatCount() == 0) {
                switch (keyCode) {
                    // Handle gamepad and D-pad button presses to
                    // navigate the ship
                    ...

                    default:
                         if (isFireKey(keyCode)) {
                             // Update the ship object to fire lasers
                             ...
                             handled = true;
                         }
                     break;
                }
            }
            if (handled) {
                return true;
            }
        } else // Check that the event came from a game controller
            if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) ==
            InputDevice.SOURCE_JOYSTICK &&
            event.getAction() == MotionEvent.ACTION_MOVE) {

        // Process all historical movement samples in the batch
        final int historySize = event.getHistorySize();

        // Process the movements starting from the
        // earliest historical position in the batch
        for (int i = 0; i < historySize; i++) {
            // Process the event at historical position i
            processJoystickInput(event, i);
        }

        // Process the current movement sample in the batch (position -1)
        processJoystickInput(event, -1);
        return true;
    }
        return super.onKeyDown(keyCode, event);
    }
}