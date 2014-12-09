package com.wildsmith.tank.about;

import android.app.FragmentManager;
import android.os.Bundle;

import com.wildsmith.tank.core.CoreTankActivity;

public class AboutDetailsActivity extends CoreTankActivity {

    private AboutDetailsFragment aboutDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAboutDetailsFragment();
    }

    private void createAboutDetailsFragment() {
        if (aboutDetailsFragment != null) {
            return;
        }

        FragmentManager fm = getFragmentManager();
        aboutDetailsFragment = (AboutDetailsFragment) fm.findFragmentByTag(AboutDetailsFragment.TAG);

        if (aboutDetailsFragment == null) {
            aboutDetailsFragment = new AboutDetailsFragment();
            addFragment(aboutDetailsFragment, AboutDetailsFragment.TAG);
        }
    }
}