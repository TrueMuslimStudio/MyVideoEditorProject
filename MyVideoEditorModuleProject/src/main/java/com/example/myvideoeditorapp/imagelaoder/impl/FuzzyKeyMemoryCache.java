package com.example.myvideoeditorapp.imagelaoder.impl;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.imagelaoder.cache.MemoryCache;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class FuzzyKeyMemoryCache implements MemoryCache {
    private final MemoryCache cache;
    private final Comparator<String> keyComparator;

    public FuzzyKeyMemoryCache(MemoryCache cache, Comparator<String> keyComparator) {
        this.cache = cache;
        this.keyComparator = keyComparator;
    }

    public boolean put(String key, Bitmap value) {
        synchronized(this.cache) {
            String keyToRemove = null;
            Iterator var6 = this.cache.keys().iterator();

            while(true) {
                if (var6.hasNext()) {
                    String cacheKey = (String)var6.next();
                    if (this.keyComparator.compare(key, cacheKey) != 0) {
                        continue;
                    }

                    keyToRemove = cacheKey;
                }

                if (keyToRemove != null) {
                    this.cache.remove(keyToRemove);
                }
                break;
            }
        }

        return this.cache.put(key, value);
    }

    public Bitmap get(String key) {
        return this.cache.get(key);
    }

    public Bitmap remove(String key) {
        return this.cache.remove(key);
    }

    public void clear() {
        this.cache.clear();
    }

    public Collection<String> keys() {
        return this.cache.keys();
    }
}

