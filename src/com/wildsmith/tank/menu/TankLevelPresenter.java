package com.wildsmith.tank.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wildsmith.tank.R;
import com.wildsmith.tank.core.CoreTankPresenter;

public class TankLevelPresenter extends CoreTankPresenter {

    @Override
    protected void populateView(View view, Object item) {
        TankLevelItem tankLevel = ((TankLevelItem) item);

        ImageView imageView = (ImageView) view.findViewById(R.id.level_image);
        if (imageView != null) {
            imageView.setImageDrawable(tankLevel.getBackground());
        }

        TextView textView = (TextView) view.findViewById(R.id.level_description);
        if (textView != null) {
            textView.setText(tankLevel.getDescription());
        }

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.tank_level_presenter_layout, parent, false);
        return view;
    }
}