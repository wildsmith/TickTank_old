package com.wildsmith.tank.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.SearchFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.text.TextUtils;

import com.wildsmith.tank.game.GameActivity;
import com.wildsmith.tank.menu.TankLevelItem;

public class TankLevelSearchFragment extends SearchFragment implements SearchFragment.SearchResultProvider, OnItemViewClickedListener {

    public static final String TAG = TankLevelSearchFragment.class.getSimpleName();

    private static final int SEARCH_DELAY_MS = 300;

    private ArrayObjectAdapter mRowsAdapter;

    private Handler mHandler;

    private TankLevelSearchRunnable mDelayedLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setSearchResultProvider(this);
        setOnItemViewClickedListener(this);
        mDelayedLoad = new TankLevelSearchRunnable(mRowsAdapter, getActivity());
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        mRowsAdapter.clear();
        if (TextUtils.isEmpty(newQuery) == false) {
            mDelayedLoad.setSearchQuery(newQuery);
            mHandler.removeCallbacks(mDelayedLoad);
            mHandler.postDelayed(mDelayedLoad, SEARCH_DELAY_MS);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mRowsAdapter.clear();
        if (TextUtils.isEmpty(query) == false) {
            mDelayedLoad.setSearchQuery(query);
            mHandler.removeCallbacks(mDelayedLoad);
            mHandler.postDelayed(mDelayedLoad, SEARCH_DELAY_MS);
        }
        return true;
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof TankLevelItem) {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            intent.putExtra(GameActivity.LEVEL_EXTRA, ((TankLevelItem) item).getLevelIndex());
            getActivity().startActivity(intent);
        }
    }
}