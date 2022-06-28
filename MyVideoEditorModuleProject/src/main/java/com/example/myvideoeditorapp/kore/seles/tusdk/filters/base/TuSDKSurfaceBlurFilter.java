// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.base;

public class TuSDKSurfaceBlurFilter extends TuSDKGaussianBlurFiveRadiusFilter
{
    private int a;
    private int b;
    private float c;
    
    public TuSDKSurfaceBlurFilter() {
        super("-ssbv1", "-ssbf1");
        this.setBlurSize(1.0f);
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.a = this.mFilterProgram.uniformIndex("thresholdLevel");
        this.b = this.mSecondFilterProgram.uniformIndex("thresholdLevel");
        this.setThresholdLevel(this.c);
    }
    
    public float getThresholdLevel() {
        return this.c;
    }
    
    public void setThresholdLevel(final float c) {
        this.setFloat(this.c = c, this.a, this.mFilterProgram);
        this.setFloat(c, this.b, this.mSecondFilterProgram);
    }
}
