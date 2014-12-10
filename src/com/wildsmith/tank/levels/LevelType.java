package com.wildsmith.tank.levels;

public enum LevelType {

    LAND(1, "LAND"), WATER(2, "Water");

    private int value;

    private String text;

    private LevelType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}