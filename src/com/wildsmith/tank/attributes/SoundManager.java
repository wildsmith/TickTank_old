package com.wildsmith.tank.attributes;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.wildsmith.tank.R;

public class SoundManager implements OnCompletionListener {

    public static final int TANK_MOVEMENT = R.raw.tank_movement;

    public static final int TANK_FIRE = R.raw.tank_firing;

    private SparseArray<MediaPlayer> mediaPlayerMap;

    private SparseBooleanArray mediaPlayerPlayingMap;

    private MediaPlayer tankMovement;

    private MediaPlayer tankFire;

    private Context context;

    public void initSounds(Context context) {
        mediaPlayerMap = new SparseArray<MediaPlayer>(2);
        mediaPlayerPlayingMap = new SparseBooleanArray(2);

        this.context = context;

        tankMovement = new MediaPlayer();
        prepareMediaPlayer(context, TANK_MOVEMENT, tankMovement, true);

        tankFire = new MediaPlayer();
        prepareMediaPlayer(context, TANK_FIRE, tankFire, false);
    }

    private void prepareMediaPlayer(Context context, int resourceId, MediaPlayer mp, boolean isLooping) {
        if (context == null || mp == null) {
            return;
        }

        try {
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(resourceId);

            if (isLooping) {
                mp.setLooping(true);
            }
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mp.setOnCompletionListener(this);

            mediaPlayerMap.put(resourceId, mp);
            mediaPlayerPlayingMap.put(resourceId, false);

            afd.close();
        } catch (IOException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalStateException e) {

        }
    }

    public void playSound(int soundID) {
        if (mediaPlayerMap == null || mediaPlayerMap.size() == 0) {
            return;
        }

        if (mediaPlayerPlayingMap == null || mediaPlayerPlayingMap.size() == 0) {
            return;
        }

        MediaPlayer mp = mediaPlayerMap.get(soundID);
        Boolean isPlaying = mediaPlayerPlayingMap.get(soundID);
        if (mp == null || isPlaying == null || isPlaying == true) {
            return;
        }

        mediaPlayerPlayingMap.put(soundID, true);

        if (mp.isPlaying() == false) {
            try {
                mp.prepare();
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }

            mp.start();
        }
    }

    public void stopSound(int soundID) {
        if (mediaPlayerMap == null || mediaPlayerMap.size() == 0) {
            return;
        }

        if (mediaPlayerPlayingMap == null || mediaPlayerPlayingMap.size() == 0) {
            return;
        }

        MediaPlayer mp = mediaPlayerMap.get(soundID);
        Boolean isPlaying = mediaPlayerPlayingMap.get(soundID);
        if (mp == null || isPlaying == null) {
            return;
        }

        if (isPlaying) {
            mp.stop();
        }
    }

    public void pauseSound(int soundID) {
        if (mediaPlayerMap == null || mediaPlayerMap.size() == 0) {
            return;
        }

        if (mediaPlayerPlayingMap == null || mediaPlayerPlayingMap.size() == 0) {
            return;
        }

        MediaPlayer mp = mediaPlayerMap.get(soundID);
        Boolean isPlaying = mediaPlayerPlayingMap.get(soundID);
        if (mp == null || isPlaying == null) {
            return;
        }

        if (isPlaying) {
            mp.pause();
            mediaPlayerPlayingMap.put(soundID, false);
        }
    }

    public void pauseSounds() {
        if (mediaPlayerMap == null || mediaPlayerMap.size() == 0) {
            return;
        }

        if (mediaPlayerPlayingMap == null || mediaPlayerPlayingMap.size() == 0) {
            return;
        }

        for (int i = 0; i < mediaPlayerMap.size(); i++) {
            Integer soundID = mediaPlayerMap.keyAt(i);
            if (soundID == null) {
                return;
            }

            MediaPlayer mp = mediaPlayerMap.valueAt(i);
            Boolean isPlaying = mediaPlayerPlayingMap.get(soundID);
            if (mp == null || isPlaying == null) {
                return;
            }

            if (isPlaying) {
                mp.pause();
                mediaPlayerPlayingMap.put(soundID, false);
            }
        }
    }

    public void stopSounds() {
        if (mediaPlayerMap == null || mediaPlayerMap.size() == 0) {
            return;
        }

        if (mediaPlayerPlayingMap == null || mediaPlayerPlayingMap.size() == 0) {
            return;
        }

        for (int i = 0; i < mediaPlayerMap.size(); i++) {
            Integer soundID = mediaPlayerMap.keyAt(i);
            if (soundID == null) {
                return;
            }

            MediaPlayer mp = mediaPlayerMap.valueAt(i);
            Boolean isPlaying = mediaPlayerPlayingMap.get(soundID);
            if (mp == null || isPlaying == null) {
                return;
            }

            if (isPlaying) {
                mp.stop();
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp == null) {
            return;
        }

        mp.stop();
        mp.reset();

        if (mp == tankMovement) {
            prepareMediaPlayer(context, TANK_MOVEMENT, tankMovement, true);
            mediaPlayerPlayingMap.put(TANK_MOVEMENT, false);
        } else if (mp == tankFire) {
            prepareMediaPlayer(context, TANK_FIRE, tankFire, false);
            mediaPlayerPlayingMap.put(TANK_FIRE, false);
        }
    }
}