// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.flowabs;

import com.example.myvideoeditorapp.kore.struct.TuSdkSizeF;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKTfmLicFilter extends SelesFilter
{
    private float a;
    private int b;
    private int c;
    
    public TuSDKTfmLicFilter() {
        super("-stfm3lic");
        this.a = 1.5f;
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.b = this.mFilterProgram.uniformIndex("stepOffset");
        this.c = this.mFilterProgram.uniformIndex("stepLength");
        this.setupFilterForSize(this.sizeOfFBO());
        this.setStepLength(this.a);
    }
    
    @Override
    public void setupFilterForSize(final TuSdkSize tuSdkSize) {
        super.setupFilterForSize(tuSdkSize);
        if (tuSdkSize == null || !tuSdkSize.isSize()) {
            return;
        }
        this.setSize(TuSdkSizeF.create(1.0f / tuSdkSize.width, 1.0f / tuSdkSize.height), this.b, this.mFilterProgram);
    }
    
    public void setStepLength(final float a) {
        this.setFloat(this.a = a, this.c, this.mFilterProgram);
    }
}
