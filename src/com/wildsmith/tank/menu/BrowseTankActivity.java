package com.wildsmith.tank.menu;

import android.app.FragmentManager;
import android.os.Bundle;

import com.wildsmith.tank.core.CoreTankActivity;

public class BrowseTankActivity extends CoreTankActivity {

    private BrowseTankFragment browseTankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createBrowseTankFragment();
    }

    private void createBrowseTankFragment() {
        if (browseTankFragment != null) {
            return;
        }

        FragmentManager fm = getFragmentManager();
        browseTankFragment = (BrowseTankFragment) fm.findFragmentByTag(BrowseTankFragment.TAG);

        if (browseTankFragment == null) {
            browseTankFragment = new BrowseTankFragment();
            addFragment(browseTankFragment, BrowseTankFragment.TAG);
        }
    }
}