package com.wildsmith.tank.core;

import android.app.Fragment;
import android.os.Bundle;

public class CoreTankFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }
}