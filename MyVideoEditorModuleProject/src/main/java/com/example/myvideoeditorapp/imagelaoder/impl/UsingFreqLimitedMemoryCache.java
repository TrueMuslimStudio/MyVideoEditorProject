package com.example.myvideoeditorapp.imagelaoder.impl;

import android.graphics.Bitmap;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UsingFreqLimitedMemoryCache extends LimitedMemoryCache {
    private final Map<Bitmap, Integer> usingCounts = Collections.synchronizedMap(new HashMap());

    public UsingFreqLimitedMemoryCache(int sizeLimit) {
        super(sizeLimit);
    }

    public boolean put(String key, Bitmap value) {
        if (super.put(key, value)) {
            this.usingCounts.put(value, 0);
            return true;
        } else {
            return false;
        }
    }

    public Bitmap get(String key) {
        Bitmap value = super.get(key);
        if (value != null) {
            Integer usageCount = (Integer)this.usingCounts.get(value);
            if (usageCount != null) {
                this.usingCounts.put(value, usageCount + 1);
            }
        }

        return value;
    }

    public Bitmap remove(String key) {
        Bitmap value = super.get(key);
        if (value != null) {
            this.usingCounts.remove(value);
        }

        return super.remove(key);
    }

    public void clear() {
        this.usingCounts.clear();
        super.clear();
    }

    protected int getSize(Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    protected Bitmap removeNext() {
        Integer minUsageCount = null;
        Bitmap leastUsedValue = null;
        Set<Map.Entry<Bitmap, Integer>> entries = this.usingCounts.entrySet();
        synchronized(this.usingCounts) {
            Iterator var6 = entries.iterator();

            while(true) {
                if (!var6.hasNext()) {
                    break;
                }

                Map.Entry<Bitmap, Integer> entry = (Map.Entry)var6.next();
                if (leastUsedValue == null) {
                    leastUsedValue = (Bitmap)entry.getKey();
                    minUsageCount = (Integer)entry.getValue();
                } else {
                    Integer lastValueUsage = (Integer)entry.getValue();
                    if (lastValueUsage < minUsageCount) {
                        minUsageCount = lastValueUsage;
                        leastUsedValue = (Bitmap)entry.getKey();
                    }
                }
            }
        }

        this.usingCounts.remove(leastUsedValue);
        return leastUsedValue;
    }

    protected Reference<Bitmap> createReference(Bitmap value) {
        return new WeakReference(value);
    }
}

