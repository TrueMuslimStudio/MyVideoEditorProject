// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.beauty;

import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKBoxBlurAlphaFilter extends SelesFilter implements SelesParameters.FilterParameterInterface
{
    private int a;
    private int b;
    private BoxBlurAlphaOrientation c;
    
    public TuSDKBoxBlurAlphaFilter(final BoxBlurAlphaOrientation c) {
        super("-sbeautyv1", "-sbeautyf3");
        this.c = c;
    }
    
    @Override
    protected void onInitOnGLThread() {
        this.a = this.mFilterProgram.uniformIndex("widthOffset");
        this.b = this.mFilterProgram.uniformIndex("heightOffset");
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        super.inputFramebufferBindTexture();
        final float n = (this.c == BoxBlurAlphaOrientation.HORIZONTAL) ? (1.0f / this.mFirstInputFramebuffer.getSize().width) : 0.0f;
        final float n2 = (this.c == BoxBlurAlphaOrientation.VERTICAL) ? (1.0f / this.mFirstInputFramebuffer.getSize().height) : 0.0f;
        GLES20.glUniform1f(this.a, n);
        GLES20.glUniform1f(this.b, n2);
    }
    
    enum BoxBlurAlphaOrientation
    {
        HORIZONTAL, 
        VERTICAL;
    }
}
