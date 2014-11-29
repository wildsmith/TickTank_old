package com.wildsmith.tank.game;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.ScreenReceiver;

public class GameActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our GameView as the view
        setContentView(R.layout.tank_game);

        // creates broadcast listener for if the screen is locked or forced off by user
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ScreenReceiver.wasScreenOn) {
            // this is the case when onPause() is called by the system due to a screen state change
            // to screen off stop the game thread and stop the sound
            GameView gameView = (GameView) findViewById(R.id.gameView);
            gameView.pauseGame();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ScreenReceiver.wasScreenOn) {
            // this is the case when onResume() is called by the system due to a screen state change
            // to screen off stop the game thread and stop the sound
            GameView gameView = (GameView) findViewById(R.id.gameView);
            gameView.resumeGame();
        }
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        if (ScreenReceiver.wasScreenOn) {
            // this is the case when onResume() is called by the system due to a screen state change
            // to screen off stop the game thread and stop the sound
            GameView gameView = (GameView) findViewById(R.id.gameView);
            return gameView.handleMotionEvent(event);
        }

        return super.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (ScreenReceiver.wasScreenOn) {
            // this is the case when onResume() is called by the system due to a screen state change
            // to screen off stop the game thread and stop the sound
            GameView gameView = (GameView) findViewById(R.id.gameView);
            return gameView.handleKeyEvent(event);
        }

        return super.dispatchKeyEvent(event);
    }
}