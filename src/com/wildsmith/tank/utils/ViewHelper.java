package com.wildsmith.tank.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ViewHelper {

    public static int getPxFromDp(float dp, Context context) {
        if (context == null) {
            return -1;
        }

        Resources resources = context.getResources();
        if (resources == null) {
            return -1;
        }

        DisplayMetrics metrics = resources.getDisplayMetrics();
        if (metrics == null) {
            return -1;
        }

        return (int) Math.ceil(dp * metrics.density);
    }
}