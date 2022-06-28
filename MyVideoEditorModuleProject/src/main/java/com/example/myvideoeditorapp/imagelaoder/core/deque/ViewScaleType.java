package com.example.myvideoeditorapp.imagelaoder.core.deque;

import android.widget.ImageView;

public enum ViewScaleType {
    FIT_INSIDE,
    CROP;

    private ViewScaleType() {
    }

    public static ViewScaleType fromImageView(ImageView imageView) {
        switch(imageView.getScaleType().ordinal()) {
            case 1:
            case 2:
            case 8:
            default:
                return CROP;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return FIT_INSIDE;
        }
    }
}