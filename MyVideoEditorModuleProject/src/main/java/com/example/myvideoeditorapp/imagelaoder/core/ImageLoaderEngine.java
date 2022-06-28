package com.example.myvideoeditorapp.imagelaoder.core;

import com.example.myvideoeditorapp.imagelaoder.cache.ImageLoaderConfiguration;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageAware;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ImageLoaderEngine {
    final ImageLoaderConfiguration configuration;
    private Executor taskExecutor;
    private Executor taskExecutorForCachedImages;
    private Executor taskDistributor;
    private final Map<Integer, String> cacheKeysForImageAwares = Collections.synchronizedMap(new HashMap());
    private final Map<String, ReentrantLock> uriLocks = new WeakHashMap();
    private final AtomicBoolean paused = new AtomicBoolean(false);
    private final AtomicBoolean networkDenied = new AtomicBoolean(false);
    private final AtomicBoolean slowNetwork = new AtomicBoolean(false);
    private final Object pauseLock = new Object();

    public ImageLoaderEngine(ImageLoaderConfiguration configuration) {
        this.configuration = configuration;
        this.taskExecutor = configuration.taskExecutor;
        this.taskExecutorForCachedImages = configuration.taskExecutorForCachedImages;
        this.taskDistributor = DefaultConfigurationFactory.createTaskDistributor();
    }

   public void submit(final LoadAndDisplayImageTask task) {
        this.taskDistributor.execute(new Runnable() {
            public void run() {
                File image = ImageLoaderEngine.this.configuration.diskCache.get(task.getLoadingUri());
                boolean isImageCachedOnDisk = image != null && image.exists();
                ImageLoaderEngine.this.initExecutorsIfNeed();
                if (isImageCachedOnDisk) {
                    ImageLoaderEngine.this.taskExecutorForCachedImages.execute(task);
                } else {
                    ImageLoaderEngine.this.taskExecutor.execute(task);
                }

            }
        });
    }

    public void submit(ProcessAndDisplayImageTask task) {
        this.initExecutorsIfNeed();
        this.taskExecutorForCachedImages.execute(task);
    }

    private void initExecutorsIfNeed() {
        if (!this.configuration.customExecutor && ((ExecutorService)this.taskExecutor).isShutdown()) {
            this.taskExecutor = this.createTaskExecutor();
        }

        if (!this.configuration.customExecutorForCachedImages && ((ExecutorService)this.taskExecutorForCachedImages).isShutdown()) {
            this.taskExecutorForCachedImages = this.createTaskExecutor();
        }

    }

    private Executor createTaskExecutor() {
        return DefaultConfigurationFactory.createExecutor(this.configuration.threadPoolSize, this.configuration.threadPriority, this.configuration.tasksProcessingType);
    }

    public String getLoadingUriForView(ImageAware imageAware) {
        return (String)this.cacheKeysForImageAwares.get(imageAware.getId());
    }

    public void prepareDisplayTaskFor(ImageAware imageAware, String memoryCacheKey) {
        this.cacheKeysForImageAwares.put(imageAware.getId(), memoryCacheKey);
    }

    public ImageLoaderConfiguration getConfiguration() {
        return configuration;
    }

    public void cancelDisplayTaskFor(ImageAware imageAware) {
        this.cacheKeysForImageAwares.remove(imageAware.getId());
    }

    public void denyNetworkDownloads(boolean denyNetworkDownloads) {
        this.networkDenied.set(denyNetworkDownloads);
    }

    public void handleSlowNetwork(boolean handleSlowNetwork) {
        this.slowNetwork.set(handleSlowNetwork);
    }

    public void pause() {
        this.paused.set(true);
    }

    public void resume() {
        this.paused.set(false);
        synchronized(this.pauseLock) {
            this.pauseLock.notifyAll();
        }
    }

    public void stop() {
        if (!this.configuration.customExecutor) {
            ((ExecutorService)this.taskExecutor).shutdownNow();
        }

        if (!this.configuration.customExecutorForCachedImages) {
            ((ExecutorService)this.taskExecutorForCachedImages).shutdownNow();
        }

        this.cacheKeysForImageAwares.clear();
        this.uriLocks.clear();
    }

    void fireCallback(Runnable r) {
        this.taskDistributor.execute(r);
    }

    public ReentrantLock getLockForUri(String uri) {
        ReentrantLock lock = (ReentrantLock)this.uriLocks.get(uri);
        if (lock == null) {
            lock = new ReentrantLock();
            this.uriLocks.put(uri, lock);
        }

        return lock;
    }

    AtomicBoolean getPause() {
        return this.paused;
    }

    Object getPauseLock() {
        return this.pauseLock;
    }

    boolean isNetworkDenied() {
        return this.networkDenied.get();
    }

    boolean isSlowNetwork() {
        return this.slowNetwork.get();
    }
}

