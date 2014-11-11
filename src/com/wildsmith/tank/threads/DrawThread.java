package com.wildsmith.tank.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.wildsmith.tank.game.GameView;

public class DrawThread extends Thread {

    private final static int MAX_FPS = 50;

    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    private boolean running = false;

    private SurfaceHolder surfaceHolder;

    private GameView gameView;

    public DrawThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning) {
        this.running = isRunning;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        Canvas canvas;

        long beginTime; // the time when the cycle begun

        long timeDiff; // the time it took for the cycle to execute

        int sleepTime; // ms to sleep (<0 if we're behind)

        sleepTime = 0;

        while (running) {
            canvas = null;
            // try locking the canvas for exclusive pixel editing
            // in the surface
            try {
                canvas = this.surfaceHolder.lockCanvas();
                if (canvas == null) {
                    break;
                }

                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();

                    // render state to the screen
                    // draws the canvas on the panel
                    gameView.render(canvas);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int) (FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                    }

                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            } // end finally
        }
    }
}