// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk;

import android.graphics.PointF;
import com.example.myvideoeditorapp.kore.seles.tusdk.particle.TuSDKParticleFilterInterface;
import com.example.myvideoeditorapp.kore.utils.TLog;

public class TuSDK2DParticleFilterWrap extends FilterWrap
{
    public static TuSDK2DParticleFilterWrap creat(final FilterOption filterOption) {
        if (filterOption == null) {
            TLog.e("Can not found FilterOption", new Object[0]);
            return null;
        }
        return new TuSDK2DParticleFilterWrap(filterOption);
    }
    
    protected TuSDK2DParticleFilterWrap(final FilterOption filterOption) {
        this.changeOption(filterOption);
    }
    
    @Override
    public boolean hasParticleFilter() {
        return this.mFilter != null && this.mFilter instanceof TuSDKParticleFilterInterface;
    }
    
    @Override
    public void updateParticleEmitPosition(final PointF pointF) {
        if (!this.hasParticleFilter()) {
            return;
        }
        ((TuSDKParticleFilterInterface)this.mFilter).updateParticleEmitPosition(pointF);
    }
    
    @Override
    public void setParticleSize(final float particleSize) {
        if (!this.hasParticleFilter()) {
            return;
        }
        ((TuSDKParticleFilterInterface)this.mFilter).setParticleSize(particleSize);
    }
    
    @Override
    public void setParticleColor(final int particleColor) {
        if (!this.hasParticleFilter()) {
            return;
        }
        ((TuSDKParticleFilterInterface)this.mFilter).setParticleColor(particleColor);
    }
}
