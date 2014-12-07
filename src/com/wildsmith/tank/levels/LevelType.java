package com.wildsmith.tank.levels;

public enum LevelType {

    LAND(1), WATER(2);

    private int value;

    private LevelType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}