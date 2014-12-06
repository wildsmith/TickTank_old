package com.wildsmith.tank.menu;

import android.graphics.drawable.Drawable;

public class TankLevel {

    private Drawable background;

    private String description;

    private int levelIndex;

    public int getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(int levelIndex) {
        this.levelIndex = levelIndex;
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}