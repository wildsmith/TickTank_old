package com.wildsmith.tank.about;

import android.app.Activity;

import com.wildsmith.tank.R;
import com.wildsmith.tank.core.CoreTankDetailsFragment;

public class AboutDetailsFragment extends CoreTankDetailsFragment {

    public static final String TAG = AboutDetailsFragment.class.getSimpleName();

    /**
     * You should always have a default constructor. This allows the OS in reinvoke the fragment in
     * the case that the fragment was removed from memory but the user is navigating back to it.
     */
    public AboutDetailsFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        setupUIElements();
    }

    private void setupUIElements() {
        setDefaultBackground(getResources().getDrawable(R.drawable.hd_default_background));
        clearBackground();
    }
}