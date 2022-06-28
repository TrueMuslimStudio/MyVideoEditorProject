package com.example.myvideoeditorapp.imagelaoder.cache;

import android.graphics.Bitmap;

import java.util.Collection;

public interface MemoryCache {
    boolean put(String var1, Bitmap var2);

    Bitmap get(String var1);

    Bitmap remove(String var1);

    Collection<String> keys();

    void clear();
}
