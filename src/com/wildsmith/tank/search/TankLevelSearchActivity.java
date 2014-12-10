package com.wildsmith.tank.search;

import android.app.FragmentManager;
import android.os.Bundle;

import com.wildsmith.tank.core.CoreTankActivity;

public class TankLevelSearchActivity extends CoreTankActivity {

    private TankLevelSearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAboutDetailsFragment();
    }

    private void createAboutDetailsFragment() {
        if (searchFragment != null) {
            return;
        }

        FragmentManager fm = getFragmentManager();
        searchFragment = (TankLevelSearchFragment) fm.findFragmentByTag(TankLevelSearchFragment.TAG);

        if (searchFragment == null) {
            searchFragment = new TankLevelSearchFragment();
            addFragment(searchFragment, TankLevelSearchFragment.TAG);
        }
    }
}