// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.output;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateBuilder;
import com.example.myvideoeditorapp.kore.seles.sources.SelesWatermark;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

public interface SelesSurfaceDisplay extends SelesContext.SelesInput
{
    void setEnabled(final boolean p0);
    
    void setTextureCoordinateBuilder(final SelesVerticeCoordinateBuilder p0);
    
    void setBackgroundColor(final float p0, final float p1, final float p2, final float p3);
    
    void setWatermark(final SelesWatermark p0);
    
    void destroy();
    
    void setPusherRotation(final ImageOrientation p0, final int p1);
    
    void newFrameReadyInGLThread(final long p0);
    
    void duplicateFrameReadyInGLThread(final long p0);
}
