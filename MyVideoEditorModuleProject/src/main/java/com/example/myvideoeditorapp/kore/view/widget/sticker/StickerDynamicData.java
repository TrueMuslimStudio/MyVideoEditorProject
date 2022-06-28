// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.sticker;

public class StickerDynamicData
{
    public long starTimeUs;
    public long stopTimeUs;
    public float offsetX;
    public float offsetY;
    private StickerData a;
    
    public StickerDynamicData(final StickerData a) {
        this.a = a;
    }
    
    public StickerData getStickerData() {
        return this.a;
    }
    
    public boolean isContains(final float n) {
        return this.isContains((long)n * 100000L);
    }
    
    public boolean isContains(final long n) {
        return this.starTimeUs <= n && this.stopTimeUs >= n;
    }
}
