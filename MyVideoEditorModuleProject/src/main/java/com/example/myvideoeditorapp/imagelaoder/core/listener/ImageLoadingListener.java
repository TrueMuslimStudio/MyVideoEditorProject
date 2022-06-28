package com.example.myvideoeditorapp.imagelaoder.core.listener;

import android.graphics.Bitmap;
import android.view.View;

import com.example.myvideoeditorapp.imagelaoder.core.deque.FailReason;

public interface ImageLoadingListener {
    void onLoadingStarted(String var1, View var2);

    void onLoadingFailed(String var1, View var2, FailReason var3);

    void onLoadingComplete(String var1, View var2, Bitmap var3);

    void onLoadingCancelled(String var1, View var2);
}