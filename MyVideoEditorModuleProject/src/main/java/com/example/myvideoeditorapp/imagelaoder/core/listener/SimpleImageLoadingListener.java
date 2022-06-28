package com.example.myvideoeditorapp.imagelaoder.core.listener;

import android.graphics.Bitmap;
import android.view.View;

import com.example.myvideoeditorapp.imagelaoder.core.deque.FailReason;

public class SimpleImageLoadingListener implements ImageLoadingListener {
    public SimpleImageLoadingListener() {
    }

    public void onLoadingStarted(String imageUri, View view) {
    }

    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
    }

    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
    }

    public void onLoadingCancelled(String imageUri, View view) {
    }
}

