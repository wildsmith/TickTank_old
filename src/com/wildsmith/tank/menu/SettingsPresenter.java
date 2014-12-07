package com.wildsmith.tank.menu;

import android.content.Context;
import android.graphics.Outline;
import android.support.v17.leanback.widget.ImageCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.wildsmith.tank.R;
import com.wildsmith.tank.core.CoreTankPresenter;
import com.wildsmith.tank.utils.ViewHelper;

public class SettingsPresenter extends CoreTankPresenter {

    @Override
    protected void populateView(View view, Object item) {
        SettingsItem settingsItem = ((SettingsItem) item);

        ImageCardView cardView = (ImageCardView) view.findViewById(R.id.settings_image_card_view);
        if (cardView != null) {
            cardView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            cardView.setBackground(settingsItem.getBackground());
        }

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.settings_presenter_layout, parent, false);

        ViewOutlineProvider outlineProvider = new CircularViewOutline(parent.getContext());
        view.setOutlineProvider(outlineProvider);
        view.setClipToOutline(true);

        return view;
    }

    /**
     * Little bit of the new lollipop api's
     */
    private class CircularViewOutline extends ViewOutlineProvider {

        private Context context;

        public CircularViewOutline(Context context) {
            this.context = context;
        }

        @Override
        public void getOutline(View view, Outline outline) {
            final float elevationPx = ViewHelper.getPxFromDp(1f, context);
            final int size = (int) context.getResources().getDimension(R.dimen.circular_button_size);
            outline.setOval(0, 0, size - (int) elevationPx, size - (int) elevationPx);
        }
    }
}