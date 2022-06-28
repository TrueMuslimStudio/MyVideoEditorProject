// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.extend;

import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.nio.FloatBuffer;

public interface SelesVerticeCoordinateBuilder
{
    TuSdkSize outputSize();
    
    void setOutputSize(final TuSdkSize p0);
    
    void setCanvasRect(final RectF p0);
    
    boolean calculate(final TuSdkSize p0, final ImageOrientation p1, final FloatBuffer p2, final FloatBuffer p3);
}
