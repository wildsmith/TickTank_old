package com.wildsmith.tank.levels;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.wildsmith.tank.R;
import com.wildsmith.tank.attributes.SoundManager;
import com.wildsmith.tank.controller.GamepadController;

/**
 * There are much better ways to do this, I wouldn't use switch statements.
 */
public class LevelFactory {

    public static int getLevelCount() {
        return 3;
    }

    public static Level getLevel(Context context, SoundManager sound, GamepadController gamepadController, int level) {
        switch (level) {
            case 1:
                return new LevelOne(context, sound, gamepadController);
            case 2:
                return new LevelTwo(context, sound, gamepadController);
            default:
                return new LevelOne(context, sound, gamepadController);
        }
    }

    public static Drawable getLevelBackground(Context context, int level) {
        switch (level) {
            case 1:
                return context.getDrawable(R.drawable.hd_default_background);
            case 2:
                return context.getDrawable(R.drawable.hd_level_two);
            default:
                return context.getDrawable(R.drawable.hd_default_background);
        }
    }

    public static String getDescription(Context context, int level) {
        switch (level) {
            case 1:
                return context.getString(R.string.level_one_description);
            case 2:
                return context.getString(R.string.level_two_description);
            default:
                return context.getString(R.string.level_one_description);
        }
    }

    public static int getLevelTypeCount() {
        return 3;
    }

    public static String getLevelType(Context context, int levelType) {
        switch (levelType) {
            case 1:
                return context.getString(R.string.level_type_one);
            case 2:
                return context.getString(R.string.level_type_two);
            default:
                return context.getString(R.string.level_settings);
        }
    }
}