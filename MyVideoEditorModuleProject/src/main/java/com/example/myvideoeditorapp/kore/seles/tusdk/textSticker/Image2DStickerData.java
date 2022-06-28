// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.textSticker;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.graphics.Bitmap;

public class Image2DStickerData
{
    private Bitmap a;
    @Deprecated
    private int b;
    @Deprecated
    private int c;
    private float d;
    private float e;
    private float f;
    private float g;
    private float h;
    private float i;
    
    public TuSdkSize size() {
        return TuSdkSize.create(this.b, this.c);
    }
    
    public Image2DStickerData() {
    }
    
    @Deprecated
    public Image2DStickerData(final Bitmap a, final int b, final int c, final float d, final float e, final float f, final float g) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
    }
    
    public Image2DStickerData(final Bitmap a, final float h, final float i, final float e, final float f, final float g, final float d) {
        this.a = a;
        this.h = h;
        this.i = i;
        this.e = e;
        this.f = f;
        this.g = g;
        this.d = d;
    }
    
    public Bitmap getBitmap() {
        return this.a;
    }
    
    public void setBitmap(final Bitmap a) {
        this.a = a;
    }
    
    public int getWidth() {
        return this.b;
    }
    
    public void setWidth(final int b) {
        this.b = b;
    }
    
    public int getHeight() {
        return this.c;
    }
    
    public void setHeight(final int c) {
        this.c = c;
    }
    
    public float getRatio() {
        return this.d;
    }
    
    public void setRatio(final float d) {
        this.d = d;
    }
    
    public float getOffsetX() {
        return this.e;
    }
    
    public void setOffsetX(final float e) {
        this.e = e;
    }
    
    public float getOffsetY() {
        return this.f;
    }
    
    public void setOffsetY(final float f) {
        this.f = f;
    }
    
    public float getRotation() {
        return this.g;
    }
    
    public void setRotation(final float g) {
        this.g = g;
    }
    
    public float getStickerWidth() {
        return this.h;
    }
    
    public void setStickerWidth(final float h) {
        this.h = h;
    }
    
    public float getStickerHeight() {
        return this.i;
    }
    
    public void setStickerHeight(final float i) {
        this.i = i;
    }
}
