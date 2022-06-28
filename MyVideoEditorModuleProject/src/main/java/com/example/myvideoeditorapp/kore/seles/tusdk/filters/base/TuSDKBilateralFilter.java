// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.base;

public class TuSDKBilateralFilter extends TuSDKGaussianBlurFiveRadiusFilter
{
    private int a;
    private int b;
    private int c;
    private int d;
    private float e;
    private float f;
    
    public TuSDKBilateralFilter() {
        super("-sbltv1", "-sbltf1");
        this.setBlurSize(1.0f);
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.a = this.mFilterProgram.uniformIndex("sigmaI");
        this.b = this.mSecondFilterProgram.uniformIndex("sigmaI");
        this.c = this.mFilterProgram.uniformIndex("sigmaS");
        this.d = this.mSecondFilterProgram.uniformIndex("sigmaS");
        this.setSigmaI(0.2f);
        this.setSigmaS(4.0f);
    }
    
    public void setSigmaI(final float e) {
        this.setFloat(this.e = e, this.a, this.mFilterProgram);
        this.setFloat(this.e, this.b, this.mSecondFilterProgram);
    }
    
    public void setSigmaS(final float f) {
        this.setFloat(this.f = f, this.c, this.mFilterProgram);
        this.setFloat(this.f, this.d, this.mSecondFilterProgram);
    }
}
