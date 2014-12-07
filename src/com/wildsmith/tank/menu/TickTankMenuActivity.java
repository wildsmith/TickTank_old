package com.wildsmith.tank.menu;

import android.app.FragmentManager;
import android.os.Bundle;

import com.wildsmith.tank.core.CoreTankActivity;

public class TickTankMenuActivity extends CoreTankActivity {

    private TickTankMenuFragment browseTankFragment;

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
        browseTankFragment = (TickTankMenuFragment) fm.findFragmentByTag(TickTankMenuFragment.TAG);

        if (browseTankFragment == null) {
            browseTankFragment = new TickTankMenuFragment();
            addFragment(browseTankFragment, TickTankMenuFragment.TAG);
        }
    }
}