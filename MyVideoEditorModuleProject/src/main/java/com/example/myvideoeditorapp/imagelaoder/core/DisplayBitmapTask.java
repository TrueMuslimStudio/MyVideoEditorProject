package com.example.myvideoeditorapp.imagelaoder.core;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.imagelaoder.core.deque.LoadedFrom;
import com.example.myvideoeditorapp.imagelaoder.core.display.BitmapDisplayer;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageAware;
import com.example.myvideoeditorapp.imagelaoder.core.listener.ImageLoadingListener;
import com.example.myvideoeditorapp.imagelaoder.core.utils.L;

final class DisplayBitmapTask implements Runnable {
    private static final String LOG_DISPLAY_IMAGE_IN_IMAGEAWARE = "Display image in ImageAware (loaded from %1$s) [%2$s]";
    private static final String LOG_TASK_CANCELLED_IMAGEAWARE_REUSED = "ImageAware is reused for another image. Task is cancelled. [%s]";
    private static final String LOG_TASK_CANCELLED_IMAGEAWARE_COLLECTED = "ImageAware was collected by GC. Task is cancelled. [%s]";
    private final Bitmap bitmap;
    private final String imageUri;
    private final ImageAware imageAware;
    private final String memoryCacheKey;
    private final BitmapDisplayer displayer;
    private final ImageLoadingListener listener;
    private final ImageLoaderEngine engine;
    private final LoadedFrom loadedFrom;

    public DisplayBitmapTask(Bitmap bitmap, ImageLoadingInfo imageLoadingInfo, ImageLoaderEngine engine, LoadedFrom loadedFrom) {
        this.bitmap = bitmap;
        this.imageUri = imageLoadingInfo.uri;
        this.imageAware = imageLoadingInfo.imageAware;
        this.memoryCacheKey = imageLoadingInfo.memoryCacheKey;
        this.displayer = imageLoadingInfo.options.getDisplayer();
        this.listener = imageLoadingInfo.listener;
        this.engine = engine;
        this.loadedFrom = loadedFrom;
    }

    public void run() {
        if (this.imageAware.isCollected()) {
            L.d("ImageAware was collected by GC. Task is cancelled. [%s]", new Object[]{this.memoryCacheKey});
            this.listener.onLoadingCancelled(this.imageUri, this.imageAware.getWrappedView());
        } else if (this.isViewWasReused()) {
            L.d("ImageAware is reused for another image. Task is cancelled. [%s]", new Object[]{this.memoryCacheKey});
            this.listener.onLoadingCancelled(this.imageUri, this.imageAware.getWrappedView());
        } else {
            L.d("Display image in ImageAware (loaded from %1$s) [%2$s]", new Object[]{this.loadedFrom, this.memoryCacheKey});
            this.displayer.display(this.bitmap, this.imageAware, this.loadedFrom);
            this.engine.cancelDisplayTaskFor(this.imageAware);
            this.listener.onLoadingComplete(this.imageUri, this.imageAware.getWrappedView(), this.bitmap);
        }

    }

    private boolean isViewWasReused() {
        String currentCacheKey = this.engine.getLoadingUriForView(this.imageAware);
        return !this.memoryCacheKey.equals(currentCacheKey);
    }
}

