// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.beauty;

import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesTwoInputFilter;

public class TuSDKBoxBlur2Filter extends SelesTwoInputFilter implements SelesParameters.FilterParameterInterface
{
    private int a;
    private int b;
    
    public TuSDKBoxBlur2Filter() {
        super("-sbeautyv1", "-sbeautyf2");
        this.disableSecondFrameCheck();
    }
    
    @Override
    protected void onInitOnGLThread() {
        this.a = this.mFilterProgram.uniformIndex("widthOffset");
        this.b = this.mFilterProgram.uniformIndex("heightOffset");
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
        final float n = 0.0f;
        final float n2 = 1.0f / this.mFirstInputFramebuffer.getSize().height;
        GLES20.glUniform1f(this.a, n);
        GLES20.glUniform1f(this.b, n2);
    }
}
