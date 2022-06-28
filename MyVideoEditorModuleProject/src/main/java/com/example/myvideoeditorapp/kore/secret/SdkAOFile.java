// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.secret;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import java.util.HashMap;

public class SdkAOFile
{
    private static final boolean a;
    private String b;
    private long c;
    
    public SdkAOFile(final String b, final boolean b2) {
        this.b = b;
        this.c = jniLoadFile(this.b, b2);
    }
    
    public HashMap<String, Object> readHeader(final String s, final int n) {
        return jniReadHeader(this.c, s, n);
    }
    
    public Bitmap loadImage(final String s) {
        return jniLoadImage(this.c, s);
    }
    
    public Bitmap loadImage(final String s, final BitmapFactory.Options options) {
        return jniLoadImage2(this.c, s, options);
    }
    
    public String loadText(final String s) {
        return jniLoadEvaText(this.c, s);
    }
    
    public byte[] loadBinary(final String s) {
        return jniLoadBinary(this.c, s);
    }
    
    public void release() {
        jniRelease(this.c);
    }
    
    private static native long jniLoadFile(final String p0, final boolean p1);
    
    private static native HashMap<String, Object> jniReadHeader(final long p0, final String p1, final int p2);
    
    private static native Bitmap jniLoadImage(final long p0, final String p1);
    
    private static native Bitmap jniLoadImage2(final long p0, final String p1, final BitmapFactory.Options p2);
    
    private static native String jniLoadEvaText(final long p0, final String p1);
    
    private static native byte[] jniLoadBinary(final long p0, final String p1);
    
    private static native void jniRelease(final long p0);
    
    static {
        a = SdkValid.isInit;
    }
}
