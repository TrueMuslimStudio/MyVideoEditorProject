// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.base;

import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKProgramFilter extends SelesFilter
{
    public TuSDKProgramFilter(final String s) {
        super(s);
    }
    
    public TuSDKProgramFilter(final String s, final String s2) {
        super(s, s2);
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.checkGLError(this.getClass().getSimpleName() + " onInitOnGLThread");
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        super.renderToTexture(floatBuffer, floatBuffer2);
        this.checkGLError(this.getClass().getSimpleName());
        this.captureFilterImage(this.getClass().getSimpleName(), this.mInputTextureSize.width, this.mInputTextureSize.height);
    }
}
