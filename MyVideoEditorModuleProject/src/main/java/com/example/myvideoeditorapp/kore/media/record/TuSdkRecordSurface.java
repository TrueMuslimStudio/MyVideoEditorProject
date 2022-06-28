// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.record;

import com.example.myvideoeditorapp.kore.seles.SelesContext;


public interface TuSdkRecordSurface
{
    void addTarget(final SelesContext.SelesInput p0, final int p1);
    
    void removeTarget(final SelesContext.SelesInput p0);
    
    void initInGLThread();
    
    void updateSurfaceTexImage();
    
    void newFrameReadyInGLThread(final long p0);
}
