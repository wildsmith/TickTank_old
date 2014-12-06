package com.wildsmith.tank.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wildsmith.tank.R;
import com.wildsmith.tank.core.CoreTankPresenter;

public class BrowseTankPresenter extends CoreTankPresenter {

    @Override
    protected void populateView(View view, Object item) {
        TankLevel tankLevel = ((TankLevel) item);

        TextView textView = (TextView) view.findViewById(R.id.browse_tank_level);
        if (textView != null) {
            textView.setText(tankLevel.getDescription());
        }
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.browse_tank_presenter_layout, parent, false);
        return view;
    }
}