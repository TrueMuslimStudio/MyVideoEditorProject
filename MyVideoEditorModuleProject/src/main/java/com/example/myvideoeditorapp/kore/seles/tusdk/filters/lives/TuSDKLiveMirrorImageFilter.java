// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.filters.lives;

import java.nio.FloatBuffer;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;

public class TuSDKLiveMirrorImageFilter extends SelesFilter
{
    public TuSDKLiveMirrorImageFilter() {
        super("-slive12f");
    }
    
    @Override
    protected void onInitOnGLThread() {
        super.onInitOnGLThread();
        this.checkGLError("TuSDKLiveMirrorImageFilter");
    }
    
    @Override
    protected void renderToTexture(final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        super.renderToTexture(floatBuffer, floatBuffer2);
        this.checkGLError("TuSDKLiveMirrorImageFilter");
        this.captureFilterImage("TuSDKLiveMirrorImageFilter", this.mInputTextureSize.width, this.mInputTextureSize.height);
    }
}
