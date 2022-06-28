// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.extend;

import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;

public interface SelesVerticeCoordinateCorpBuilder extends SelesVerticeCoordinateBuilder
{
    void setCropRect(final RectF p0);
    
    void setPreCropRect(final RectF p0);
    
    void setEnableClip(final boolean p0);
    
    TuSdkSize setOutputRatio(final float p0);
    
    float getOutputRatio(final float p0);
}
