package com.wildsmith.tank.levels;

import android.app.Activity;
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
        return 2;
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

    public static int getMenuItemCount() {
        return 3;
    }

    public static String getMenuType(Context context, int levelType) {
        switch (levelType) {
            case 1:
                return context.getString(R.string.level_type_one);
            case 2:
                return context.getString(R.string.level_type_two);
            default:
                return context.getString(R.string.level_settings);
        }
    }

    public static String getMenuTypeIcon(Activity activity, int levelTypeIndex) {
        final String packageName = activity.getApplicationContext().getPackageName();

        switch (levelTypeIndex) {
            case 1:
                return "android.resource://" + packageName + "/" + R.drawable.land_icon;
            case 2:
                return "android.resource://" + packageName + "/" + R.drawable.water_icon;
            default:
                return "android.resource://" + packageName + "/" + R.drawable.settings_icon;
        }
    }
}