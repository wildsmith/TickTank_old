package com.wildsmith.tank.about;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

public class AboutDetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder holder, Object item) {
        AboutItem aboutItem = (AboutItem) item;

        holder.getTitle().setText(aboutItem.getTitle());
        holder.getSubtitle().setText(aboutItem.getSubtitle());
        holder.getBody().setText(aboutItem.getBody());
    }
}