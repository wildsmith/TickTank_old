package com.wildsmith.tank.core;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CoreTankPresenter extends Presenter {

    protected Context context;

    @Override
    public void onBindViewHolder(ViewHolder holder, Object item) {
        populateView(holder.view, item);
    }

    protected abstract void populateView(View view, Object item);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        this.context = parent.getContext();

        View view = inflateView(LayoutInflater.from(context), parent);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    protected abstract View inflateView(LayoutInflater inflater, ViewGroup parent);

    @Override
    public void onUnbindViewHolder(ViewHolder holder) {}
}