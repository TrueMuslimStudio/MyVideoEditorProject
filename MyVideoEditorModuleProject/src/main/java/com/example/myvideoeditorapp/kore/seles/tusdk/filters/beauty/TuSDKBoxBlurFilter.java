// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.beauty;

import android.opengl.GLES20;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKBoxBlurFilter extends SelesFilter implements SelesParameters.FilterParameterInterface
{
    private int a;
    private int b;
    
    public TuSDKBoxBlurFilter() {
        super("-sbeautyv1", "-sbeautyf1");
    }
    
    @Override
    protected void onInitOnGLThread() {
        this.a = this.mFilterProgram.uniformIndex("widthOffset");
        this.b = this.mFilterProgram.uniformIndex("heightOffset");
    }
    
    @Override
    protected void inputFramebufferBindTexture() {
        super.inputFramebufferBindTexture();
        final float n = 1.0f / this.mFirstInputFramebuffer.getSize().width;
        final float n2 = 0.0f;
        GLES20.glUniform1f(this.a, n);
        GLES20.glUniform1f(this.b, n2);
    }
}
