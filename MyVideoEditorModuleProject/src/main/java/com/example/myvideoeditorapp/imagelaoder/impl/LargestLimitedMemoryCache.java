package com.example.myvideoeditorapp.imagelaoder.impl;

import android.graphics.Bitmap;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LargestLimitedMemoryCache extends LimitedMemoryCache {
    private final Map<Bitmap, Integer> valueSizes = Collections.synchronizedMap(new HashMap());

    public LargestLimitedMemoryCache(int sizeLimit) {
        super(sizeLimit);
    }

    public boolean put(String key, Bitmap value) {
        if (super.put(key, value)) {
            this.valueSizes.put(value, this.getSize(value));
            return true;
        } else {
            return false;
        }
    }

    public Bitmap remove(String key) {
        Bitmap value = super.get(key);
        if (value != null) {
            this.valueSizes.remove(value);
        }

        return super.remove(key);
    }

    public void clear() {
        this.valueSizes.clear();
        super.clear();
    }

    protected int getSize(Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    protected Bitmap removeNext() {
        Integer maxSize = null;
        Bitmap largestValue = null;
        Set<Map.Entry<Bitmap, Integer>> entries = this.valueSizes.entrySet();
        synchronized(this.valueSizes) {
            Iterator var6 = entries.iterator();

            while(true) {
                if (!var6.hasNext()) {
                    break;
                }

                Map.Entry<Bitmap, Integer> entry = (Map.Entry)var6.next();
                if (largestValue == null) {
                    largestValue = (Bitmap)entry.getKey();
                    maxSize = (Integer)entry.getValue();
                } else {
                    Integer size = (Integer)entry.getValue();
                    if (size > maxSize) {
                        maxSize = size;
                        largestValue = (Bitmap)entry.getKey();
                    }
                }
            }
        }

        this.valueSizes.remove(largestValue);
        return largestValue;
    }

    protected Reference<Bitmap> createReference(Bitmap value) {
        return new WeakReference(value);
    }
}

