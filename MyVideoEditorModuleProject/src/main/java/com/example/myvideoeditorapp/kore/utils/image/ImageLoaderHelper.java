// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.image;


import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;
import com.example.myvideoeditorapp.imagelaoder.cache.DiskCache;
import com.example.myvideoeditorapp.imagelaoder.cache.FileNameGenerator;
import com.example.myvideoeditorapp.imagelaoder.cache.HashCodeFileNameGenerator;
import com.example.myvideoeditorapp.imagelaoder.cache.ImageLoaderConfiguration;
import com.example.myvideoeditorapp.imagelaoder.cache.MemoryCache;
import com.example.myvideoeditorapp.imagelaoder.cache.MemoryCacheUtils;
import com.example.myvideoeditorapp.imagelaoder.cache.UnlimitedDiskCache;
import com.example.myvideoeditorapp.imagelaoder.core.DisplayImageOptions;
import com.example.myvideoeditorapp.imagelaoder.core.decode.BaseImageDecoder;
import com.example.myvideoeditorapp.imagelaoder.core.decode.ImageDecoder;
import com.example.myvideoeditorapp.imagelaoder.core.deque.ImageSize;
import com.example.myvideoeditorapp.imagelaoder.core.deque.QueueProcessingType;
import com.example.myvideoeditorapp.imagelaoder.core.download.BaseImageDownloader;
import com.example.myvideoeditorapp.imagelaoder.core.download.ImageDownloader;
import com.example.myvideoeditorapp.imagelaoder.core.process.BitmapProcessor;
import com.example.myvideoeditorapp.imagelaoder.core.utils.DiskCacheUtils;
import com.example.myvideoeditorapp.imagelaoder.core.utils.StorageUtils;
import com.example.myvideoeditorapp.imagelaoder.impl.LargestLimitedMemoryCache;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.hardware.HardwareHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageLoaderHelper
{
    public static void remove(String var0) {
        if (var0 != null) {
            DiskCacheUtils.removeFromCache(var0, com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getDiskCache());
            MemoryCacheUtils.removeFromCache(var0, com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getMemoryCache());
        }
    }

    public static void remove(final String s, final TuSdkSize tuSdkSize) {
        if (s == null) {
            return;
        }
        DiskCacheUtils.removeFromCache(s,  com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getDiskCache());
        if (tuSdkSize == null) {
            return;
        }
        com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getMemoryCache().remove(MemoryCacheUtils.generateKey(s, new ImageSize(tuSdkSize.width, tuSdkSize.height)));
        com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getMemoryCache().remove(MemoryCacheUtils.generateKey(s, new ImageSize(tuSdkSize.height, tuSdkSize.width)));
    }

    public static void save(final String s, final Bitmap bitmap) {
        save(s, bitmap, 0);
    }

    public static void save(final String s, final Bitmap bitmap, final int n) {
        save(s, bitmap, null, n);
    }

    public static void save(final String s, final Bitmap bitmap, final TuSdkSize tuSdkSize, final int n) {
        saveToDiskCache(s, bitmap, n);
        saveToMemoryCache(s, bitmap, tuSdkSize);
    }

    public static void saveToDiskCache(final String s, final Bitmap bitmap, final int n) {
        if (bitmap == null || s == null) {
            return;
        }
        final DiskCache diskCache =  com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getDiskCache();
        if (diskCache == null) {
            return;
        }
        final File value = diskCache.get(s);
        if (n == 0) {
            BitmapHelper.saveBitmapAsPNG(value, bitmap, 0);
        }
        else {
            BitmapHelper.saveBitmap(value, bitmap, n);
        }
    }

    public static void saveToMemoryCache(final String s, final Bitmap bitmap, final TuSdkSize tuSdkSize) {
        if (bitmap == null || s == null) {
            return;
        }
        final MemoryCache memoryCache =  com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getMemoryCache();
        if (memoryCache == null) {
            return;
        }
        ImageSize imageSize;
        if (tuSdkSize != null) {
            imageSize = new ImageSize(tuSdkSize.width, tuSdkSize.height);
        }
        else {
            imageSize = new ImageSize(bitmap.getWidth(), bitmap.getHeight());
        }
        memoryCache.put(MemoryCacheUtils.generateKey(s, imageSize), bitmap);
    }

    public static boolean exists(final String s) {
        final File loadDiskCache = loadDiskCache(s);
        return loadDiskCache != null && loadDiskCache.exists() && loadDiskCache.isFile();
    }

    public static Bitmap loadDiscBitmap(final String s) {
        final File loadDiskCache = loadDiskCache(s);
        if (loadDiskCache == null || !loadDiskCache.isFile() || !loadDiskCache.exists()) {
            return null;
        }
        return BitmapHelper.getBitmap(loadDiskCache);
    }

    public static File loadDiskCache(final String s) {
        if (s == null) {
            return null;
        }
        return DiskCacheUtils.findInCache(s,  com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getDiskCache());
    }

    public static Bitmap loadMemoryBitmap(final String s, final TuSdkSize tuSdkSize) {
        if (s == null) {
            return null;
        }
        final MemoryCache memoryCache =  com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().getMemoryCache();
        if (memoryCache == null) {
            return null;
        }
        if (tuSdkSize != null) {
            final Bitmap value = memoryCache.get(MemoryCacheUtils.generateKey(s, new ImageSize(tuSdkSize.width, tuSdkSize.height)));
            if (value != null) {
                return value;
            }
        }
        final List<Bitmap> cachedBitmapsForImageUri = findCachedBitmapsForImageUri(s, memoryCache);
        if (cachedBitmapsForImageUri == null || cachedBitmapsForImageUri.isEmpty()) {
            return null;
        }
        return cachedBitmapsForImageUri.get(0);
    }

    public static List<Bitmap> findCachedBitmapsForImageUri(final String anObject, final MemoryCache memoryCache) {
        final ArrayList<Bitmap> list = new ArrayList<Bitmap>();
        for (final String s : memoryCache.keys()) {
            if (s.substring(0, s.lastIndexOf("_")).equals(anObject)) {
                list.add(memoryCache.get(s));
            }
        }
        return list;
    }

    public static void clearAllCache() {
        clearDiskCache();
        clearMemoryCache();
    }

    public static void clearDiskCache() {
        com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().clearDiskCache();
    }

    public static void clearMemoryCache() {
        com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().clearMemoryCache();
    }

    public static void initImageCache(final Context context, final TuSdkSize tuSdkSize) {
        final File cacheDirectory = StorageUtils.getCacheDirectory(context);
        File file = new File(cacheDirectory, "imageCache");
        if (!file.exists() && !file.mkdirs()) {
            file = cacheDirectory;
        }
        final int min = Math.min((int)(HardwareHelper.appMemoryBit() / 8L), 1703936);
        com.example.myvideoeditorapp.imagelaoder.core.ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(context).memoryCacheExtraOptions(tuSdkSize.width, tuSdkSize.height).diskCacheExtraOptions(tuSdkSize.width, tuSdkSize.height, (BitmapProcessor)null).threadPoolSize(2).threadPriority(4).tasksProcessingOrder(QueueProcessingType.FIFO).denyCacheImageMultipleSizesInMemory().memoryCache((MemoryCache)new LargestLimitedMemoryCache(min)).memoryCacheSize(min).memoryCacheSizePercentage(13).diskCache((DiskCache)new UnlimitedDiskCache(file)).diskCacheSize(209715200).diskCacheFileNameGenerator((FileNameGenerator)new HashCodeFileNameGenerator()).imageDownloader((ImageDownloader)new BaseImageDownloader(context)).imageDecoder((ImageDecoder)new BaseImageDecoder(false)).defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build());
    }
}
