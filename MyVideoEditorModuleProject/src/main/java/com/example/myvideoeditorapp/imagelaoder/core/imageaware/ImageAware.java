package com.example.myvideoeditorapp.imagelaoder.core.imageaware;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.myvideoeditorapp.imagelaoder.core.deque.ViewScaleType;

public interface ImageAware {
    int getWidth();

    int getHeight();

    ViewScaleType getScaleType();

    View getWrappedView();

    boolean isCollected();

    int getId();

    boolean setImageDrawable(Drawable var1);

    boolean setImageBitmap(Bitmap var1);
}
