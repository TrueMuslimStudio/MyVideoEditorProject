package com.example.myvideoeditorapp.imagelaoder.cache;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.imagelaoder.core.DefaultConfigurationFactory;
import com.example.myvideoeditorapp.imagelaoder.core.utils.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LimitedAgeDiskCache extends BaseDiskCache {
    private final long maxFileAge;
    private final Map<File, Long> loadingDates;

    public LimitedAgeDiskCache(File cacheDir, long maxAge) {
        this(cacheDir, (File) null, DefaultConfigurationFactory.createFileNameGenerator(), maxAge);
    }

    public LimitedAgeDiskCache(File cacheDir, File reserveCacheDir, long maxAge) {
        this(cacheDir, reserveCacheDir, DefaultConfigurationFactory.createFileNameGenerator(), maxAge);
    }

    public LimitedAgeDiskCache(File cacheDir, File reserveCacheDir, FileNameGenerator fileNameGenerator, long maxAge) {
        super(cacheDir, reserveCacheDir, fileNameGenerator);
        this.loadingDates = Collections.synchronizedMap(new HashMap());
        this.maxFileAge = maxAge * 1000L;
    }

    public File get(String imageUri) {
        File file = super.get(imageUri);
        if (file != null && file.exists()) {
            Long loadingDate = (Long) this.loadingDates.get(file);
            boolean cached;
            if (loadingDate == null) {
                cached = false;
                loadingDate = file.lastModified();
            } else {
                cached = true;
            }

            if (System.currentTimeMillis() - loadingDate > this.maxFileAge) {
                file.delete();
                this.loadingDates.remove(file);
            } else if (!cached) {
                this.loadingDates.put(file, loadingDate);
            }
        }

        return file;
    }

    @Override
    public boolean save(String imageUri, InputStream imageStream, IoUtils.CopyListener listener) throws IOException {
        boolean saved = super.save(imageUri, imageStream, listener);
        this.rememberUsage(imageUri);
        return saved;
    }


    public boolean save(String imageUri, Bitmap bitmap) throws IOException {
        boolean saved = super.save(imageUri, bitmap);
        this.rememberUsage(imageUri);
        return saved;
    }

    public boolean remove(String imageUri) {
        this.loadingDates.remove(this.getFile(imageUri));
        return super.remove(imageUri);
    }

    public void clear() {
        super.clear();
        this.loadingDates.clear();
    }

    private void rememberUsage(String imageUri) {
        File file = this.getFile(imageUri);
        long currentTime = System.currentTimeMillis();
        file.setLastModified(currentTime);
        this.loadingDates.put(file, currentTime);
    }
}

