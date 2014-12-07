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

    public static int getLevelCount(LevelType levelType) {
        switch (levelType) {
            case LAND:
                return 2;
            case WATER:
                return 3;
            default:
                return 0;
        }
    }

    public static Level getLevel(Context context, SoundManager sound, GamepadController gamepadController, int level) {
        switch (level) {
            case 1:
                return new LevelOne(context, sound, gamepadController);
            case 2:
                return new LevelTwo(context, sound, gamepadController);
            default:
                return null;
        }
    }

    public static Drawable getLevelBackground(Context context, LevelType levelType, int level) {
        switch (levelType) {
            case LAND:
                return getLandLevelBackground(context, level);
            case WATER:
                return getWaterLevelBackground(context, level);
            default:
                return null;
        }
    }

    private static Drawable getWaterLevelBackground(Context context, int level) {
        switch (level) {
            case 1:
                return context.getDrawable(R.drawable.water_type_one_background);
            case 2:
                return context.getDrawable(R.drawable.water_type_two_background);
            case 3:
                return context.getDrawable(R.drawable.water_type_three_background);
            default:
                return null;
        }
    }

    private static Drawable getLandLevelBackground(Context context, int level) {
        switch (level) {
            case 1:
                return context.getDrawable(R.drawable.land_type_one_background);
            case 2:
                return context.getDrawable(R.drawable.land_type_two_background);
            default:
                return null;
        }
    }

    public static Drawable getLevelLargeBackground(Context context, LevelType levelType, int level) {
        switch (levelType) {
            case LAND:
                return getLandLevelLargeBackground(context, level);
            case WATER:
                return getWaterLevelLargeBackground(context, level);
            default:
                return null;
        }
    }

    private static Drawable getWaterLevelLargeBackground(Context context, int level) {
        switch (level) {
            case 1:
                return context.getDrawable(R.drawable.hd_water_type_one_background);
            case 2:
                return context.getDrawable(R.drawable.hd_water_type_two_background);
            case 3:
                return context.getDrawable(R.drawable.hd_water_type_three_background);
            default:
                return null;
        }
    }

    private static Drawable getLandLevelLargeBackground(Context context, int level) {
        switch (level) {
            case 1:
                return context.getDrawable(R.drawable.hd_land_type_one_background);
            case 2:
                return context.getDrawable(R.drawable.hd_land_type_two_background);
            default:
                return null;
        }
    }

    public static String getDescription(Context context, LevelType levelType, int level) {
        switch (levelType) {
            case LAND:
                return getLandLevelDescription(context, level);
            case WATER:
                return getWaterLevelDescription(context, level);
            default:
                return null;
        }
    }

    private static String getLandLevelDescription(Context context, int level) {
        switch (level) {
            case 1:
                return context.getString(R.string.land_level_one_description);
            case 2:
                return context.getString(R.string.land_level_two_description);
            default:
                return null;
        }
    }

    private static String getWaterLevelDescription(Context context, int level) {
        switch (level) {
            case 1:
                return context.getString(R.string.water_level_one_description);
            case 2:
                return context.getString(R.string.water_level_two_description);
            case 3:
                return context.getString(R.string.water_level_three_description);
            default:
                return null;
        }
    }
}