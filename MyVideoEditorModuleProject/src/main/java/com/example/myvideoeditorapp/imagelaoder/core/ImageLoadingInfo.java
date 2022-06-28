package com.example.myvideoeditorapp.imagelaoder.core;

import com.example.myvideoeditorapp.imagelaoder.core.deque.ImageSize;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageAware;
import com.example.myvideoeditorapp.imagelaoder.core.listener.ImageLoadingListener;
import com.example.myvideoeditorapp.imagelaoder.core.listener.ImageLoadingProgressListener;

import java.util.concurrent.locks.ReentrantLock;

final public class ImageLoadingInfo {
    final String uri;
    final String memoryCacheKey;
    final ImageAware imageAware;
    final ImageSize targetSize;
    final DisplayImageOptions options;
    final ImageLoadingListener listener;
    final ImageLoadingProgressListener progressListener;
    final ReentrantLock loadFromUriLock;

    public ImageLoadingInfo(String uri, ImageAware imageAware, ImageSize targetSize, String memoryCacheKey, DisplayImageOptions options, ImageLoadingListener listener, ImageLoadingProgressListener progressListener, ReentrantLock loadFromUriLock) {
        this.uri = uri;
        this.imageAware = imageAware;
        this.targetSize = targetSize;
        this.options = options;
        this.listener = listener;
        this.progressListener = progressListener;
        this.loadFromUriLock = loadFromUriLock;
        this.memoryCacheKey = memoryCacheKey;
    }
}
