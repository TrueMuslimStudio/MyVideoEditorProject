// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.beauty;

import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesTwoInputFilter;

public class TuSDKFaceSmoothFilter extends SelesTwoInputFilter implements SelesParameters.FilterParameterInterface
{
    private int a;
    private int b;
    private int c;
    private int d;
    private float e;
    private float f;
    
    public TuSDKFaceSmoothFilter() {
        super("-sbeautyv2", "-sbeautyf4");
        this.disableSecondFrameCheck();
    }
    
    @Override
    protected void onInitOnGLThread() {
        this.a = this.mFilterProgram.uniformIndex("widthOffset");
        this.b = this.mFilterProgram.uniformIndex("heightOffset");
        this.c = this.mFilterProgram.uniformIndex("uSmooth");
        this.d = this.mFilterProgram.uniformIndex("uSharpen");
        this.mFilterInputTextureUniform2 = this.mFilterProgram.uniformIndex("inputImageTexture2");
    }
    
    @Override
    protected void initializeAttributes() {
        this.mFilterProgram.addAttribute("position");
        this.mFilterProgram.addAttribute("inputTextureCoordinate");
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, (this.mFirstInputFramebuffer == null) ? 0 : this.mFirstInputFramebuffer.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform, 2);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, (this.mSecondInputFramebuffer == null) ? 0 : this.mSecondInputFramebuffer.getTexture());
        GLES20.glUniform1i(this.mFilterInputTextureUniform2, 3);
        final float n = 1.0f / this.mFirstInputFramebuffer.getSize().width;
        final float n2 = 1.0f / this.mFirstInputFramebuffer.getSize().height;
        GLES20.glUniform1f(this.a, n);
        GLES20.glUniform1f(this.b, n2);
        GLES20.glUniform1f(this.c, this.e);
        GLES20.glUniform1f(this.d, this.f);
    }
    
    public void setSmooth(final float e) {
        this.e = e;
    }
    
    public void setSharpen(final float f) {
        this.f = f;
    }
}
