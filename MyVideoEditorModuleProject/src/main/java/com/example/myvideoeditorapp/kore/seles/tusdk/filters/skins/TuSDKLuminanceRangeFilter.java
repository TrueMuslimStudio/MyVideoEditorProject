// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.skins;

import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKLuminanceRangeFilter extends SelesFilter
{
    private int a;
    private int b;
    private float c;
    private float d;
    
    public TuSDKLuminanceRangeFilter() {
        super("-slr1");
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.a = this.mFilterProgram.uniformIndex("rangeReduction");
        this.b = this.mFilterProgram.uniformIndex("saturation");
        this.setRangeReductionFactor((this.c > 0.0f) ? this.c : 0.6f);
        this.setSaturation((this.d > 0.0f) ? this.d : 0.1f);
    }
    
    public void setRangeReductionFactor(final float c) {
        this.setFloat(this.c = c, this.a, this.mFilterProgram);
    }
    
    public float getRangeReductionFactor() {
        return this.c;
    }
    
    public void setSaturation(final float d) {
        this.setFloat(this.d = d, this.b, this.mFilterProgram);
    }
    
    public float getSaturation() {
        return this.d;
    }
}
