// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKNormalFilter extends SelesFilter
{
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
    
    @Override
    public void newFrameReady(final long n, final int n2) {
        super.newFrameReady(n, n2);
    }
    
    @Override
    public void addTarget(final SelesContext.SelesInput selesInput, final int n) {
        super.addTarget(selesInput, n);
    }
}
