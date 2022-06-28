// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.flowabs;

import com.example.myvideoeditorapp.kore.struct.TuSdkSizeF;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKTfmEdgeFilter extends SelesFilter
{
    private float a;
    private int b;
    private int c;
    
    public TuSDKTfmEdgeFilter() {
        super("-stfm1edgev", "-stfm1edgef");
        this.a = 1.0f;
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.b = this.mFilterProgram.uniformIndex("stepOffset");
        this.c = this.mFilterProgram.uniformIndex("edgeStrength");
        this.setEdgeStrength(this.a);
        this.setupFilterForSize(this.sizeOfFBO());
    }
    
    @Override
    public void setupFilterForSize(final TuSdkSize tuSdkSize) {
        super.setupFilterForSize(tuSdkSize);
        if (tuSdkSize == null || !tuSdkSize.isSize()) {
            return;
        }
        this.setSize(TuSdkSizeF.create(1.0f / tuSdkSize.width, 1.0f / tuSdkSize.height), this.b, this.mFilterProgram);
    }
    
    public void setEdgeStrength(final float a) {
        this.setFloat(this.a = a, this.c, this.mFilterProgram);
    }
}
