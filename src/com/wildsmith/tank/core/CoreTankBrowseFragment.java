package com.wildsmith.tank.core;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;

public abstract class CoreTankBrowseFragment extends BrowseFragment implements OnItemViewSelectedListener, OnItemViewClickedListener {

    private Drawable defaultBackground;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
    }

    protected void updateBackground(Drawable drawable) {
        BackgroundManager.getInstance(getActivity()).setDrawable(drawable);
    }

    protected void clearBackground() {
        BackgroundManager.getInstance(getActivity()).setDrawable(defaultBackground);
    }

    public Drawable getDefaultBackground() {
        return defaultBackground;
    }

    public void setDefaultBackground(Drawable defaultBackground) {
        this.defaultBackground = defaultBackground;
    }
}