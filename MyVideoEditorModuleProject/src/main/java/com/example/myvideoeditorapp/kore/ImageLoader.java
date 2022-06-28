package com.example.myvideoeditorapp.kore;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.myvideoeditorapp.imagelaoder.cache.DiskCache;
import com.example.myvideoeditorapp.imagelaoder.cache.ImageLoaderConfiguration;
import com.example.myvideoeditorapp.imagelaoder.cache.MemoryCache;
import com.example.myvideoeditorapp.imagelaoder.cache.MemoryCacheUtils;
import com.example.myvideoeditorapp.imagelaoder.core.DisplayImageOptions;
import com.example.myvideoeditorapp.imagelaoder.core.ImageLoaderEngine;
import com.example.myvideoeditorapp.imagelaoder.core.ImageLoadingInfo;
import com.example.myvideoeditorapp.imagelaoder.core.LoadAndDisplayImageTask;
import com.example.myvideoeditorapp.imagelaoder.core.ProcessAndDisplayImageTask;
import com.example.myvideoeditorapp.imagelaoder.core.deque.ImageSize;
import com.example.myvideoeditorapp.imagelaoder.core.deque.LoadedFrom;
import com.example.myvideoeditorapp.imagelaoder.core.deque.ViewScaleType;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageAware;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageViewAware;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.NonViewAware;
import com.example.myvideoeditorapp.imagelaoder.core.listener.ImageLoadingListener;
import com.example.myvideoeditorapp.imagelaoder.core.listener.ImageLoadingProgressListener;
import com.example.myvideoeditorapp.imagelaoder.core.listener.SimpleImageLoadingListener;
import com.example.myvideoeditorapp.imagelaoder.core.utils.ImageSizeUtils;

public class ImageLoader {
    public static final String TAG = ImageLoader.class.getSimpleName();
    static final String LOG_INIT_CONFIG = "Initialize ImageLoader with configuration";
    static final String LOG_DESTROY = "Destroy ImageLoader";
    static final String LOG_LOAD_IMAGE_FROM_MEMORY_CACHE = "Load image from memory cache [%s]";
    private static final String WARNING_RE_INIT_CONFIG = "Try to initialize ImageLoader which had already been initialized before. To re-init ImageLoader with new configuration call ImageLoader.destroy() at first.";
    private static final String ERROR_WRONG_ARGUMENTS = "Wrong arguments were passed to displayImage() method (ImageView reference must not be null)";
    private static final String ERROR_NOT_INIT = "ImageLoader must be init with configuration before using";
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "ImageLoader configuration can not be initialized with null";
    private static final Log L =null ;
    private ImageLoaderConfiguration configuration;
    private ImageLoaderEngine engine;
    private final ImageLoadingListener emptyListener = new SimpleImageLoadingListener();
    private static volatile ImageLoader instance;

    public static ImageLoader getInstance() {
        if (instance == null) {
            Class var0 = ImageLoader.class;
            synchronized(ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }

        return instance;
    }

    protected ImageLoader() {
    }

    @SuppressLint("LongLogTag")
    public synchronized void init(ImageLoaderConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("ImageLoader configuration can not be initialized with null");
        } else {
            if (this.configuration == null) {
                L.d("Initialize ImageLoader with configuration", String.valueOf(new Object[0]));
                this.engine = new ImageLoaderEngine(configuration);
                this.configuration = configuration;
            } else {
                L.w("Try to initialize ImageLoader which had already been initialized before. To re-init ImageLoader with new configuration call ImageLoader.destroy() at first.", String.valueOf(new Object[0]));
            }

        }
    }

    public boolean isInited() {
        return this.configuration != null;
    }

    public void displayImage(String uri, ImageAware imageAware) {
        this.displayImage(uri, (ImageAware)imageAware, (DisplayImageOptions)null, (ImageLoadingListener)null, (ImageLoadingProgressListener)null);
    }

    public void displayImage(String uri, ImageAware imageAware, ImageLoadingListener listener) {
        this.displayImage(uri, (ImageAware)imageAware, (DisplayImageOptions)null, listener, (ImageLoadingProgressListener)null);
    }

    public void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options) {
        this.displayImage(uri, (ImageAware)imageAware, options, (ImageLoadingListener)null, (ImageLoadingProgressListener)null);
    }

    public void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options, ImageLoadingListener listener) {
        this.displayImage(uri, (ImageAware)imageAware, options, listener, (ImageLoadingProgressListener)null);
    }

    @SuppressLint("LongLogTag")
    public void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        this.checkConfiguration();
        if (imageAware == null) {
            throw new IllegalArgumentException("Wrong arguments were passed to displayImage() method (ImageView reference must not be null)");
        } else {
            if (listener == null) {
                listener = this.emptyListener;
            }

            if (options == null) {
                options = this.configuration.defaultDisplayImageOptions;
            }

            if (TextUtils.isEmpty(uri)) {
                this.engine.cancelDisplayTaskFor(imageAware);
                listener.onLoadingStarted(uri, imageAware.getWrappedView());
                if (options.shouldShowImageForEmptyUri()) {
                    imageAware.setImageDrawable(options.getImageForEmptyUri(this.configuration.resources));
                } else {
                    imageAware.setImageDrawable((Drawable)null);
                }

                listener.onLoadingComplete(uri, imageAware.getWrappedView(), (Bitmap)null);
            } else {
                ImageSize targetSize = ImageSizeUtils.defineTargetSizeForView(imageAware, this.configuration.getMaxImageSize());
                String memoryCacheKey = MemoryCacheUtils.generateKey(uri, targetSize);
                this.engine.prepareDisplayTaskFor(imageAware, memoryCacheKey);
                listener.onLoadingStarted(uri, imageAware.getWrappedView());
                Bitmap bmp = this.configuration.memoryCache.get(memoryCacheKey);
                ImageLoadingInfo imageLoadingInfo;
                if (bmp != null && !bmp.isRecycled()) {
                    L.d("Load image from memory cache [%s]", String.valueOf(new Object[]{memoryCacheKey}));
                    if (options.shouldPostProcess()) {
                        imageLoadingInfo = new ImageLoadingInfo(uri, imageAware, targetSize, memoryCacheKey, options, listener, progressListener, this.engine.getLockForUri(uri));
                        ProcessAndDisplayImageTask displayTask = new ProcessAndDisplayImageTask(this.engine, bmp, imageLoadingInfo, defineHandler(options));
                        if (options.isSyncLoading()) {
                            displayTask.run();
                        } else {
                            this.engine.submit(displayTask);
                        }
                    } else {
                        options.getDisplayer().display(bmp, imageAware, LoadedFrom.MEMORY_CACHE);
                        listener.onLoadingComplete(uri, imageAware.getWrappedView(), bmp);
                    }
                } else {
                    if (options.shouldShowImageOnLoading()) {
                        imageAware.setImageDrawable(options.getImageOnLoading(this.configuration.resources));
                    } else if (options.isResetViewBeforeLoading()) {
                        imageAware.setImageDrawable((Drawable)null);
                    }

                    imageLoadingInfo = new ImageLoadingInfo(uri, imageAware, targetSize, memoryCacheKey, options, listener, progressListener, this.engine.getLockForUri(uri));
                    LoadAndDisplayImageTask displayTask = new LoadAndDisplayImageTask(this.engine, imageLoadingInfo, defineHandler(options));
                    if (options.isSyncLoading()) {
                        displayTask.run();
                    } else {
                        this.engine.submit(displayTask);
                    }
                }

            }
        }
    }

    public void displayImage(String uri, ImageView imageView) {
        this.displayImage(uri, (ImageAware)(new ImageViewAware(imageView)), (DisplayImageOptions)null, (ImageLoadingListener)null, (ImageLoadingProgressListener)null);
    }

    public void displayImage(String uri, ImageView imageView, DisplayImageOptions options) {
        this.displayImage(uri, (ImageAware)(new ImageViewAware(imageView)), options, (ImageLoadingListener)null, (ImageLoadingProgressListener)null);
    }

    public void displayImage(String uri, ImageView imageView, ImageLoadingListener listener) {
        this.displayImage(uri, (ImageAware)(new ImageViewAware(imageView)), (DisplayImageOptions)null, listener, (ImageLoadingProgressListener)null);
    }

    public void displayImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener) {
        this.displayImage(uri, (ImageView)imageView, options, listener, (ImageLoadingProgressListener)null);
    }

    public void displayImage(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        this.displayImage(uri, (ImageAware)(new ImageViewAware(imageView)), options, listener, progressListener);
    }

    public void loadImage(String uri, ImageLoadingListener listener) {
        this.loadImage(uri, (ImageSize)null, (DisplayImageOptions)null, listener, (ImageLoadingProgressListener)null);
    }

    public void loadImage(String uri, ImageSize targetImageSize, ImageLoadingListener listener) {
        this.loadImage(uri, targetImageSize, (DisplayImageOptions)null, listener, (ImageLoadingProgressListener)null);
    }

    public void loadImage(String uri, DisplayImageOptions options, ImageLoadingListener listener) {
        this.loadImage(uri, (ImageSize)null, options, listener, (ImageLoadingProgressListener)null);
    }

    public void loadImage(String uri, ImageSize targetImageSize, DisplayImageOptions options, ImageLoadingListener listener) {
        this.loadImage(uri, targetImageSize, options, listener, (ImageLoadingProgressListener)null);
    }

    public void loadImage(String uri, ImageSize targetImageSize, DisplayImageOptions options, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        this.checkConfiguration();
        if (targetImageSize == null) {
            targetImageSize = this.configuration.getMaxImageSize();
        }

        if (options == null) {
            options = this.configuration.defaultDisplayImageOptions;
        }

        NonViewAware imageAware = new NonViewAware(uri, targetImageSize, ViewScaleType.CROP);
        this.displayImage(uri, (ImageAware)imageAware, options, listener, progressListener);
    }

    public Bitmap loadImageSync(String uri) {
        return this.loadImageSync(uri, (ImageSize)null, (DisplayImageOptions)null);
    }

    public Bitmap loadImageSync(String uri, DisplayImageOptions options) {
        return this.loadImageSync(uri, (ImageSize)null, options);
    }

    public Bitmap loadImageSync(String uri, ImageSize targetImageSize) {
        return this.loadImageSync(uri, targetImageSize, (DisplayImageOptions)null);
    }

    public Bitmap loadImageSync(String uri, ImageSize targetImageSize, DisplayImageOptions options) {
        if (options == null) {
            options = this.configuration.defaultDisplayImageOptions;
        }

        options = (new DisplayImageOptions.Builder()).cloneFrom(options).syncLoading(true).build();
        SyncImageLoadingListener listener = new SyncImageLoadingListener((SyncImageLoadingListener)null);
        this.loadImage(uri, targetImageSize, options, listener);
        return listener.getLoadedBitmap();
    }

    private void checkConfiguration() {
        if (this.configuration == null) {
            throw new IllegalStateException("ImageLoader must be init with configuration before using");
        }
    }

    public MemoryCache getMemoryCache() {
        this.checkConfiguration();
        return this.configuration.memoryCache;
    }

    public void clearMemoryCache() {
        this.checkConfiguration();
        this.configuration.memoryCache.clear();
    }

    /** @deprecated */
    @Deprecated
    public DiskCache getDiscCache() {
        return this.getDiskCache();
    }

    public DiskCache getDiskCache() {
        this.checkConfiguration();
        return this.configuration.diskCache;
    }

    /** @deprecated */
    @Deprecated
    public void clearDiscCache() {
        this.clearDiskCache();
    }

    public void clearDiskCache() {
        this.checkConfiguration();
        this.configuration.diskCache.clear();
    }

    public String getLoadingUriForView(ImageAware imageAware) {
        return this.engine.getLoadingUriForView(imageAware);
    }

    public String getLoadingUriForView(ImageView imageView) {
        return this.engine.getLoadingUriForView(new ImageViewAware(imageView));
    }

    public void cancelDisplayTask(ImageAware imageAware) {
        this.engine.cancelDisplayTaskFor(imageAware);
    }

    public void cancelDisplayTask(ImageView imageView) {
        this.engine.cancelDisplayTaskFor(new ImageViewAware(imageView));
    }

    public void denyNetworkDownloads(boolean denyNetworkDownloads) {
        this.engine.denyNetworkDownloads(denyNetworkDownloads);
    }

    public void handleSlowNetwork(boolean handleSlowNetwork) {
        this.engine.handleSlowNetwork(handleSlowNetwork);
    }

    public void pause() {
        this.engine.pause();
    }

    public void resume() {
        this.engine.resume();
    }

    public void stop() {
        this.engine.stop();
    }

    public void destroy() {
        if (this.configuration != null) {
            L.d("Destroy ImageLoader", String.valueOf(new Object[0]));
        }

        this.stop();
        this.configuration.diskCache.close();
        this.engine = null;
        this.configuration = null;
    }

    private static Handler defineHandler(DisplayImageOptions options) {
        Handler handler = options.getHandler();
        if (options.isSyncLoading()) {
            handler = null;
        } else if (handler == null && Looper.myLooper() == Looper.getMainLooper()) {
            handler = new Handler();
        }

        return handler;
    }

    private static class SyncImageLoadingListener extends SimpleImageLoadingListener {
        private Bitmap loadedImage;

        private SyncImageLoadingListener(SyncImageLoadingListener syncImageLoadingListener) {
        }

        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            this.loadedImage = loadedImage;
        }

        public Bitmap getLoadedBitmap() {
            return this.loadedImage;
        }
    }
}
