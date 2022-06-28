package com.example.myvideoeditorapp.imagelaoder.core.display;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.imagelaoder.core.deque.LoadedFrom;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageAware;

public final class SimpleBitmapDisplayer implements BitmapDisplayer {
    public SimpleBitmapDisplayer() {
    }

    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        imageAware.setImageBitmap(bitmap);
    }
}

