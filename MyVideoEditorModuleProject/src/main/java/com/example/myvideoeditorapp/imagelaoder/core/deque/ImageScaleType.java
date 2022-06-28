package com.example.myvideoeditorapp.imagelaoder.core.deque;

public enum ImageScaleType {
    NONE,
    NONE_SAFE,
    IN_SAMPLE_POWER_OF_2,
    IN_SAMPLE_INT,
    EXACTLY,
    EXACTLY_STRETCHED;

    private ImageScaleType() {
    }
}
