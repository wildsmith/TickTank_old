package com.wildsmith.tank.search;

import android.content.Context;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Row;

import com.wildsmith.tank.R;
import com.wildsmith.tank.levels.LevelFactory;
import com.wildsmith.tank.levels.LevelType;
import com.wildsmith.tank.menu.TankLevelItem;
import com.wildsmith.tank.menu.TankLevelPresenter;

public class TankLevelSearchRunnable implements Runnable {

    private ArrayObjectAdapter rowAdapter;

    private Context context;

    private String searchQuery;

    public TankLevelSearchRunnable(ArrayObjectAdapter rowAdapter, Context context) {
        this.rowAdapter = rowAdapter;
        this.context = context;
    }

    @Override
    public void run() {
        if (searchQuery == null || "".equals(searchQuery)) {
            return;
        }

        LevelType levelType = null;
        if (searchQuery.equalsIgnoreCase(LevelType.WATER.getText())) {
            levelType = LevelType.WATER;
        } else if (searchQuery.equalsIgnoreCase(LevelType.LAND.getText())) {
            levelType = LevelType.LAND;
        }

        if (levelType == null) {
            return;
        }

        rowAdapter.add(generateSearchResults(levelType));
    }

    private Row generateSearchResults(LevelType levelType) {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new TankLevelPresenter());

        for (int x = 0; x < LevelFactory.getLevelCount(levelType); x++) {
            TankLevelItem tankLevel = new TankLevelItem();

            final int levelIndex = x + 1;
            tankLevel.setDescription(LevelFactory.getDescription(context, levelType, levelIndex));
            tankLevel.setBackground(LevelFactory.getLevelBackground(context, levelType, levelIndex));
            tankLevel.setLevelIndex(levelIndex);
            tankLevel.setLevelType(levelType);

            listRowAdapter.add(tankLevel);
        }

        final String packageName = context.getApplicationContext().getPackageName();
        final String headerName = context.getString(R.string.menu_land);
        final String headerImageUri = "android.resource://" + packageName + "/" + R.drawable.menu_land_icon;

        HeaderItem header = new HeaderItem(headerName, headerImageUri);
        return new ListRow(header, listRowAdapter);
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}