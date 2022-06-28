// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.sources;

import android.graphics.RectF;
import android.graphics.SurfaceTexture;

import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

public interface SelesSurfaceHolder
{
    void setInputSize(final TuSdkSize p0);
    
    void setInputRotation(final ImageOrientation p0);
    
    void setTextureCoordinateBuilder(final SelesVerticeCoordinateCorpBuilder p0);
    
    void setPreCropRect(final RectF p0);
    
    boolean isInited();
    
    SurfaceTexture requestSurfaceTexture();
    
    long getSurfaceTexTimestampNs();
    
    void setSurfaceTexTimestampNs(final long p0);
}
