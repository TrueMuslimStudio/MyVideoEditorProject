package com.example.myvideoeditorapp.imagelaoder.core.display;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.imagelaoder.core.deque.LoadedFrom;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageAware;

public interface BitmapDisplayer {
    void display(Bitmap var1, ImageAware var2, LoadedFrom var3);
}
