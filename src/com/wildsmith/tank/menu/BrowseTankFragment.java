package com.wildsmith.tank.menu;

import java.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.wildsmith.tank.R;
import com.wildsmith.tank.core.CoreTankBackgroundTask;
import com.wildsmith.tank.core.CoreTankBrowseFragment;
import com.wildsmith.tank.game.GameActivity;
import com.wildsmith.tank.levels.LevelFactory;

public class BrowseTankFragment extends CoreTankBrowseFragment {

    public static final String TAG = BrowseTankFragment.class.getSimpleName();

    private Timer backgroundTimer;

    /**
     * You should always have a default constructor. This allows the OS in reinvoke the fragment in
     * the case that the fragment was removed from memory but the user is navigating back to it.
     */
    public BrowseTankFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        setupUIElements();
        setupEventListeners();
    }

    /**
     * We want to setup the default aspects of this browser fragment
     */
    private void setupUIElements() {
        setTitle(getString(R.string.app_name));
        setHeadersState(BrowseFragment.HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBadgeDrawable(getResources().getDrawable(R.drawable.tank_browser_icon));
        setBrandColor(getResources().getColor(R.color.drawer_color));
        setSearchAffordanceColor(getResources().getColor(R.color.search_color));
        // setHeaderPresenterSelector(headerPresenterSelector);
        setAdapter(getBrowseAdapter());
        setDefaultBackground(getResources().getDrawable(R.drawable.hd_default_background));
    }

    /**
     * We want to let the view know that we have implemented the appropriate event listeners, so use
     * our methods if one of the events occur.
     */
    private void setupEventListeners() {
        setOnItemViewSelectedListener(this);
        setOnItemViewClickedListener(this);
    }

    private ArrayObjectAdapter getBrowseAdapter() {
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        for (int i = 0; i < LevelFactory.getMenuItemCount(); i++) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new BrowseTankPresenter());

            final int levelTypeIndex = i + 1;
            for (int x = 0; x < LevelFactory.getLevelCount(); x++) {
                TankLevel tankLevel = new TankLevel();

                final int levelIndex = x + 1;
                tankLevel.setDescription(LevelFactory.getDescription(getActivity(), levelIndex));
                tankLevel.setBackground(LevelFactory.getLevelBackground(getActivity(), levelIndex));
                tankLevel.setLevelIndex(levelIndex);

                listRowAdapter.add(tankLevel);
            }

            final String levelType = LevelFactory.getMenuType(getActivity(), levelTypeIndex);
            final String levelTypeIconUri = LevelFactory.getMenuTypeIcon(getActivity(), levelTypeIndex);

            /**
             * Setting the icon uri doesn't currently work.
             * http://stackoverflow.com/questions/25803609/androidtv-headeritem-icon-cant-be-set
             */
            HeaderItem header = new HeaderItem(i, levelType, levelTypeIconUri);
            rowsAdapter.add(new ListRow(header, listRowAdapter));
        }

        return rowsAdapter;
    }

    /**
     * This should update the background image in accordance to what the user has hovered over, this
     * makes for a more cinematic experience.
     * 
     * @param itemViewHolder The view holder of item that is currently selected.
     * @param item The item that is currently selected.
     * @param rowViewHolder The view holder of row that is currently selected.
     * @param row The row that is currently selected.
     */
    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof TankLevel) {
            Drawable background = ((TankLevel) item).getBackground();
            startBackgroundTimer(background);
        }
    }

    /**
     * This should direct the user to the details page.
     * 
     * @param itemViewHolder The view holder of item that is currently selected.
     * @param item The item that is currently selected.
     * @param rowViewHolder The view holder of row that is currently selected.
     * @param row The row that is currently selected.
     */
    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof TankLevel) {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            intent.putExtra(GameActivity.LEVEL_EXTRA, ((TankLevel) item).getLevelIndex());
            getActivity().startActivity(intent);
        }
    }

    private void startBackgroundTimer(Drawable background) {
        if (backgroundTimer != null) {
            backgroundTimer.cancel();
        }

        backgroundTimer = new Timer();
        backgroundTimer.schedule(new CoreTankBackgroundTask(this, background), 300);
    }
}