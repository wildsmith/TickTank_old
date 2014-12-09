package com.wildsmith.tank.core;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragment;

public abstract class CoreTankDetailsFragment extends DetailsFragment {

    private Drawable defaultBackground;

    private BackgroundManager backgroundManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
    }

    protected void updateBackground(Drawable drawable) {
        if (drawable != null && backgroundManager != null) {
            backgroundManager.setDrawable(drawable);
        }
    }

    protected void clearBackground() {
        if (defaultBackground != null && backgroundManager != null) {
            backgroundManager.setDrawable(defaultBackground);
        }
    }

    public Drawable getDefaultBackground() {
        return defaultBackground;
    }

    public void setDefaultBackground(Drawable defaultBackground) {
        this.defaultBackground = defaultBackground;
    }

    public BackgroundManager getBackgroundManager() {
        return backgroundManager;
    }
}