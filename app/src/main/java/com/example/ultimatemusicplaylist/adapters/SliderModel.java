package com.example.ultimatemusicplaylist.adapters;

public class SliderModel {
    private int imageResId; // drawable resource ID

    public SliderModel(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}