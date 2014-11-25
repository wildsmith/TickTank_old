package com.wildsmith.tank.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;
import com.wildsmith.tank.maps.LevelOne;
import com.wildsmith.tank.maps.Level;
import com.wildsmith.tank.threads.DrawThread;
import com.wildsmith.tank.utils.TimeHelper;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    private SoundManager sound;

    private GamepadController gamepadController;

    private Level level;

    private long lastUpdateTimeMillis;

    private boolean isSurfaceCreated;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
        setFocusable(true);

        lastUpdateTimeMillis = System.currentTimeMillis();

        drawThread = new DrawThread(getHolder(), this);

        sound = new SoundManager();
        sound.initSounds(context);

        gamepadController = new GamepadController();

        // This should be loaded up dynamically
        level = new LevelOne(context, sound, gamepadController);
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
        final float frameDelta = getFrameDelta();

        level.update(frameDelta);
        level.draw(canvas);
    }

    /**
     * @return frame Delta, frame Delta = # of "ideal" frames that have occurred since the last
     *         update. "ideal" assumes a constant frame-rate (60 FPS or 16.7 milliseconds per
     *         frame). Since the delta doesn't depend on the "real" frame-rate, the animations
     *         always run at the same wall clock speed, regardless of what the real refresh rate is.
     * 
     *         frameDelta was used instead of a time delta in order to make the values passed to
     *         update easier to understand when debugging the code. For example, a frameDelta of
     *         "1.5" means that one and a half hypothetical frames have passed since the last
     *         update. In wall time this would be 25 milliseconds or 0.025 seconds.
     */
    private float getFrameDelta() {
        final long currentTimeMillis = System.currentTimeMillis();

        final float frameDelta = TimeHelper.millisToFrameDelta(currentTimeMillis - lastUpdateTimeMillis);

        lastUpdateTimeMillis = currentTimeMillis;

        return frameDelta;
    }

    public SoundManager getSound() {
        return sound;
    }

    @SuppressLint("WrongCall")
    public void render(Canvas canvas) {
        onDraw(canvas);
    }

    public boolean handleMotionEvent(MotionEvent motionEvent) {
        if (gamepadController != null) {
            gamepadController.setDeviceId(motionEvent.getDeviceId());
            gamepadController.handleMotionEvent(motionEvent);

            return true;
        }

        return false;
    }

    public boolean handleKeyEvent(KeyEvent keyEvent) {
        if (gamepadController != null) {
            gamepadController.setDeviceId(keyEvent.getDeviceId());
            gamepadController.handleKeyEvent(keyEvent);

            return true;
        }

        return false;
    }
}