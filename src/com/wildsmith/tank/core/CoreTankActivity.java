package com.wildsmith.tank.core;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.wildsmith.tank.R;

public class CoreTankActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_tank_layout);
    }

    private void handleFragment(Fragment content, String tag, boolean add) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, content, tag);
        if (add && ft.isAddToBackStackAllowed() && fm.getBackStackEntryCount() != 0) {
            ft.addToBackStack(tag);
        }

        ft.commit();
    }

    public void addFragment(Fragment content, String fragmentTag) {
        handleFragment(content, fragmentTag, true);
    }

    public void replaceFragment(Fragment content, String fragmentTag) {
        handleFragment(content, fragmentTag, false);
    }
}