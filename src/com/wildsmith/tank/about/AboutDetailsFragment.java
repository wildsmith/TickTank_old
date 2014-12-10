package com.wildsmith.tank.about;

import android.app.Activity;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.Row;

import com.wildsmith.tank.R;
import com.wildsmith.tank.core.CoreTankDetailsFragment;

public class AboutDetailsFragment extends CoreTankDetailsFragment {

    public static final String TAG = AboutDetailsFragment.class.getSimpleName();

    private static final int JOIN_GDG = 0;

    public AboutDetailsFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        setupUIElements();
    }

    private void setupUIElements() {
        setAdapter(buildAboutAdapter());

        setDefaultBackground(getResources().getDrawable(R.drawable.hd_default_background));
        clearBackground();
    }

    private ArrayObjectAdapter buildAboutAdapter() {
        DetailsOverviewRowPresenter rowPresenter = new DetailsOverviewRowPresenter(new AboutDetailsDescriptionPresenter());
        rowPresenter.setBackgroundColor(getResources().getColor(R.color.about_background));
        rowPresenter.setOnActionClickedListener(new OnActionClickedListener() {

            @Override
            public void onActionClicked(Action action) {}
        });

        ClassPresenterSelector selector = new ClassPresenterSelector();
        selector.addClassPresenter(DetailsOverviewRow.class, rowPresenter);
        selector.addClassPresenter(ListRow.class, new ListRowPresenter());

        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(selector);

        rowsAdapter.add(generateAboutMeRow());
        rowsAdapter.add(generateAboutGDGRow());

        return rowsAdapter;
    }

    private Row generateAboutMeRow() {
        AboutItem aboutItem = new AboutItem();
        aboutItem.setTitle(getString(R.string.about_me_title));
        aboutItem.setSubtitle(getString(R.string.about_me_subtitle));
        aboutItem.setBody(getString(R.string.about_me_body));

        DetailsOverviewRow overviewRow = new DetailsOverviewRow(aboutItem);
        overviewRow.setImageDrawable(getResources().getDrawable(R.drawable.about_me_photo));

        return overviewRow;
    }

    private Row generateAboutGDGRow() {
        AboutItem aboutItem = new AboutItem();
        aboutItem.setTitle(getString(R.string.about_gdg_title));
        aboutItem.setSubtitle(getString(R.string.about_gdg_subtitle));
        aboutItem.setBody(getString(R.string.about_gdg_body));

        DetailsOverviewRow overviewRow = new DetailsOverviewRow(aboutItem);
        overviewRow.setImageDrawable(getResources().getDrawable(R.drawable.about_gdg_photo));
        overviewRow.addAction(new Action(JOIN_GDG, getString(R.string.about_gdg_action_one)));

        return overviewRow;
    }
}