// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.face;

import android.graphics.PointF;
import android.graphics.RectF;

public class FaceAligment
{
    private static int[] a;
    public RectF rect;
    private PointF[] b;
    private PointF[] c;
    private PointF[] d;
    private PointF[] e;
    public float pitch;
    public float yaw;
    public float roll;
    
    public PointF[] getMarks() {
        return this.b;
    }
    
    public PointF[] getOrginMarks() {
        return this.e;
    }
    
    public PointF[] getEyeMarks() {
        return this.c;
    }
    
    public PointF[] getMouthMarks() {
        return this.d;
    }
    
    public FaceAligment() {
    }
    
    public FaceAligment(final PointF[] orginMarks) {
        this.setOrginMarks(orginMarks);
    }
    
    public final void setOrginMarks(final PointF[] array) {
        this.b = array;
        this.e = array;
        if (array != null && array.length == 106) {
            this.b = this.a(array);
        }
    }
    
    private PointF[] a(final PointF[] array) {
        final PointF[] array2 = new PointF[68];
        for (int i = 0; i < 68; ++i) {
            array2[i] = array[FaceAligment.a[i]];
        }
        return array2;
    }
    
    public final void setEyeMarks(final PointF[] c) {
        this.c = c;
    }
    
    public final void setMouthMarks(final PointF[] d) {
        this.d = d;
    }
    
    static {
        FaceAligment.a = new int[] { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103 };
    }
}
