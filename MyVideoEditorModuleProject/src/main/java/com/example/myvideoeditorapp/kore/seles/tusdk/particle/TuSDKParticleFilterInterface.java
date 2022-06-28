// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.particle;

import android.graphics.PointF;

public interface TuSDKParticleFilterInterface
{
    void updateParticleEmitPosition(final PointF p0);
    
    void setParticleSize(final float p0);
    
    void setParticleColor(final int p0);
    
    void setActive(final boolean p0);
    
    void reset();
}
