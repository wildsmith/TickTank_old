package com.wildsmith.tank.menu;

import java.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.app.BackgroundManager;
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
import com.wildsmith.tank.levels.LevelType;

public class TickTankMenuFragment extends CoreTankBrowseFragment {

    public static final String TAG = TickTankMenuFragment.class.getSimpleName();

    private Timer backgroundTimer;

    /**
     * You should always have a default constructor. This allows the OS in reinvoke the fragment in
     * the case that the fragment was removed from memory but the user is navigating back to it.
     */
    public TickTankMenuFragment() {}

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
        setBadgeDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        setBrandColor(getResources().getColor(R.color.drawer_color));
        setSearchAffordanceColor(getResources().getColor(R.color.search_color));

        setAdapter(getBrowseAdapter());

        setDefaultBackground(getResources().getDrawable(R.drawable.hd_default_background));
        clearBackground();
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

        rowsAdapter.add(generateLandLevelRow());
        rowsAdapter.add(generateWaterLevelRow());
        rowsAdapter.add(generateSettingRow());

        return rowsAdapter;
    }

    private ListRow generateLandLevelRow() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new TankLevelPresenter());

        for (int x = 0; x < LevelFactory.getLevelCount(LevelType.LAND); x++) {
            TankLevelItem tankLevel = new TankLevelItem();

            final int levelIndex = x + 1;
            tankLevel.setDescription(LevelFactory.getDescription(getActivity(), LevelType.LAND, levelIndex));
            tankLevel.setBackground(LevelFactory.getLevelBackground(getActivity(), LevelType.LAND, levelIndex));
            tankLevel.setLevelIndex(levelIndex);
            tankLevel.setLevelType(LevelType.LAND);

            listRowAdapter.add(tankLevel);
        }

        final String packageName = getActivity().getApplicationContext().getPackageName();
        final String headerName = getString(R.string.menu_land);
        final String headerImageUri = "android.resource://" + packageName + "/" + R.drawable.menu_land_icon;

        HeaderItem header = new HeaderItem(headerName, headerImageUri);
        return new ListRow(header, listRowAdapter);
    }

    private ListRow generateWaterLevelRow() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new TankLevelPresenter());

        for (int x = 0; x < LevelFactory.getLevelCount(LevelType.WATER); x++) {
            TankLevelItem tankLevel = new TankLevelItem();

            final int levelIndex = x + 1;
            tankLevel.setDescription(LevelFactory.getDescription(getActivity(), LevelType.WATER, levelIndex));
            tankLevel.setBackground(LevelFactory.getLevelBackground(getActivity(), LevelType.WATER, levelIndex));
            tankLevel.setLevelIndex(levelIndex);
            tankLevel.setLevelType(LevelType.WATER);

            listRowAdapter.add(tankLevel);
        }

        final String packageName = getActivity().getApplicationContext().getPackageName();
        final String headerName = getString(R.string.menu_water);
        final String headerImageUri = "android.resource://" + packageName + "/" + R.drawable.menu_water_icon;

        HeaderItem header = new HeaderItem(headerName, headerImageUri);
        return new ListRow(header, listRowAdapter);
    }

    private Object generateSettingRow() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new SettingsPresenter());

        SettingsItem volumeSetting = new SettingsItem();
        volumeSetting.setBackground(getResources().getDrawable(R.drawable.volume_setting_background));
        listRowAdapter.add(volumeSetting);

        SettingsItem aboutSetting = new SettingsItem();
        aboutSetting.setBackground(getResources().getDrawable(R.drawable.about_setting_background));
        listRowAdapter.add(aboutSetting);

        final String packageName = getActivity().getApplicationContext().getPackageName();
        final String headerName = getString(R.string.menu_settings);
        final String headerImageUri = "android.resource://" + packageName + "/" + R.drawable.menu_settings_icon;

        HeaderItem header = new HeaderItem(headerName, headerImageUri);
        return new ListRow(header, listRowAdapter);
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
        if (item instanceof TankLevelItem) {
            final LevelType levelType = ((TankLevelItem) item).getLevelType();
            // I don't want to change the views background for land, just set it to default. This is
            // an attempt to show people just how pretty the background can be without changing it
            // as well.
            if (LevelType.LAND.equals(levelType)) {
                startBackgroundTimer(getDefaultBackground());
                return;
            }

            final int levelIndex = ((TankLevelItem) item).getLevelIndex();
            Drawable background = LevelFactory.getLevelLargeBackground(getActivity(), levelType, levelIndex);
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
        if (item instanceof TankLevelItem) {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            intent.putExtra(GameActivity.LEVEL_EXTRA, ((TankLevelItem) item).getLevelIndex());
            getActivity().startActivity(intent);
        } else if (item instanceof SettingsItem) {
            // TODO go to the details page
        }
    }

    private void startBackgroundTimer(Drawable background) {
        BackgroundManager backgroundManager = getBackgroundManager();
        if (backgroundManager == null) {
            return;
        }

        Drawable currentBackground = backgroundManager.getDrawable();
        if (currentBackground == null || currentBackground.equals(background)) {
            return;
        }

        if (backgroundTimer != null) {
            backgroundTimer.cancel();
        }

        backgroundTimer = new Timer();
        backgroundTimer.schedule(new CoreTankBackgroundTask(this, background), 300);
    }
}